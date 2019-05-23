package com.bk.sv.admin.service.impl;

import com.bk.sv.admin.core.model.ExecutionUnitLog;
import com.bk.sv.admin.core.model.ExecutionUnit;
import com.bk.sv.admin.core.model.Project;
import com.bk.sv.admin.core.result.ReturnT;
import com.bk.sv.admin.dao.IExecutionUnitLogDao;
import com.bk.sv.admin.dao.IExecutionUnitDao;
import com.bk.sv.admin.dao.IProjectDao;
import com.bk.sv.admin.service.IExecutionUnitService;
import com.bk.sv.core.broadcast.BkGlueBroadcaster;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ExecutionUnitServiceImpl implements IExecutionUnitService {

    @Resource
    private IExecutionUnitDao glueInfoDao;
    @Resource
    private IExecutionUnitLogDao codeLogDao;
    @Resource
    private IProjectDao projectDao;

    @Override
    public Map<String, Object> pageList(int offset, int pagesize, int projectId, String name) {
        List<ExecutionUnit> list = glueInfoDao.pageList(offset, pagesize, projectId, name);
        int list_count = glueInfoDao.pageListCount(offset, pagesize, projectId, name);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("recordsTotal", list_count);        // 总记录数
        map.put("recordsFiltered", list_count);        // 过滤后的总记录数
        map.put("data", list);                    // 数据

        return map;
    }

    @Override
    public ReturnT<String> delete(int id) {
        // valid
        ExecutionUnit codeInfo = glueInfoDao.load(id);
        if (codeInfo == null) {
            return new ReturnT<String>(500, "“删除失败,Glue”不存在");
        }
        // delete
        int ret = glueInfoDao.delete(id);
        if (ret < 1) {
            return new ReturnT<String>(500, "删除失败");
        }
        codeLogDao.delete(id);

        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> add(ExecutionUnit codeInfo) {
        // valid
        if (StringUtils.isBlank(codeInfo.getName())) {
            return new ReturnT<String>(500, "“Glue名称”不可为空");
        }
        if (StringUtils.isBlank(codeInfo.getAbout())) {
            return new ReturnT<String>(500, "“Glue描述”不可为空");
        }

        // project
        Project project = projectDao.load(codeInfo.getProjectId());
        if (project == null) {
            return new ReturnT<String>(500, "所属项目ID非法");
        }

        // check old
        ExecutionUnit codeInfo_old = glueInfoDao.loadCodeByName(codeInfo.getName());
        if (codeInfo_old != null) {
            return new ReturnT<String>(500, "“GLUE名称”不可重复");
        }

        // parse final name
        String finalName = project.getAppname().concat(".").concat(codeInfo.getName());
        codeInfo.setName(finalName);

        // save
        int ret = glueInfoDao.save(codeInfo);
        if (ret < 1) {
            return new ReturnT<String>(500, "新增失败");
        }
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> update(ExecutionUnit codeInfo) {

        ExecutionUnit oldCodeInfo = glueInfoDao.load(codeInfo.getId());
        if (oldCodeInfo == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "更新失败，GLUE记录不存在");
        }

        // update base info
        oldCodeInfo.setAbout(codeInfo.getAbout());
        int ret = glueInfoDao.update(oldCodeInfo);

        return ret > 0 ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @Override
    public ExecutionUnit load(int id) {
        return glueInfoDao.load(id);
    }

    @Override
    public ReturnT<String> clearCache(int id, String appNames) {
        ExecutionUnit codeInfo = glueInfoDao.load(id);
        if (codeInfo == null) {
            return new ReturnT<String>(500, "“GLUE”不存在");
        }
        // pub
        Set<String> appList = new HashSet<String>();
        if (StringUtils.isNotBlank(appNames)) {
            for (String appName : appNames.split(",")) {
                appList.add(appName);
            }
        }

        // broadcast (pub)
        boolean ret = BkGlueBroadcaster.getInstance().productMsg(codeInfo.getName(), appList, codeInfo.getUpdateTime().getTime());

        return ret ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @Override
    public ReturnT<String> updateCodeSource(ExecutionUnitLog executionUnitLog) {

        // valid glue
        ExecutionUnit codeInfo_old = glueInfoDao.load(executionUnitLog.getGlueId());
        if (codeInfo_old == null) {
            return new ReturnT<String>(500, "“GLUE”不存在");
        }

        // valid param
        if (StringUtils.isBlank(executionUnitLog.getRemark())) {
            return new ReturnT<String>(500, "“备注”不可为空");
        }

        // update glue source
        codeInfo_old.setSource(executionUnitLog.getSource());
        int ret = glueInfoDao.update(codeInfo_old);
        if (ret < 1) {
            return new ReturnT<String>(500, "更新失败");
        }

        // save old (backup)
        try {
            codeLogDao.save(executionUnitLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // remove log more than 10
        codeLogDao.removeOldLogs(executionUnitLog.getGlueId());
        return ReturnT.SUCCESS;
    }

    @Override
    public List<ExecutionUnitLog> loadLogs(int glueId) {
        return codeLogDao.findByGlueId(glueId);
    }

}
