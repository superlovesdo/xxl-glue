package com.xxl.glue.admin.service.impl;

import com.xxl.glue.admin.core.model.CodeInfo;
import com.xxl.glue.admin.core.model.CodeLog;
import com.xxl.glue.admin.core.result.ReturnT;
import com.xxl.glue.admin.dao.ICodeInfoDao;
import com.xxl.glue.admin.dao.ICodeLogDao;
import com.xxl.glue.admin.service.ICodeService;
import com.xxl.glue.core.broadcast.GlueMessage;
import com.xxl.glue.core.broadcast.GlueMessage.GlueMessageType;
import com.xxl.glue.core.broadcast.XxlGlueBroadcaster;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CodeServiceImpl implements ICodeService {
	
	@Autowired
	private ICodeInfoDao codeInfoDao;
	@Autowired
	private ICodeLogDao codeLogDao;

	@Override
	public Map<String, Object> pageList(int offset, int pagesize, String name) {
		List<CodeInfo> list = codeInfoDao.pageList(offset, pagesize, name);
		int list_count = codeInfoDao.pageListCount(offset, pagesize, name);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("recordsTotal", list_count);		// 总记录数
		map.put("recordsFiltered", list_count);		// 过滤后的总记录数
	    map.put("data", list);  					// 数据
	    
		return map;
	}

	@Override
	public ReturnT<String> delete(int id) {
		// valid
		CodeInfo codeInfo = codeInfoDao.loadCode(id);
		if (codeInfo==null) {
			return new ReturnT<String>(500, "“删除失败,Glue”不存在"); 
		}
		// delete
		int ret = codeInfoDao.delete(id);
		if (ret < 1) {
			return new ReturnT<String>(500, "删除失败");
		}
		codeLogDao.delete(codeInfo.getName());

		// save old (backup)
		/*try {
			CodeLog codeLog = new CodeLog();
			BeanUtils.copyProperties(codeLog, codeInfo);
			codeLog.setRemark(codeLog.getRemark() + "[backup for 手动删除]");
			codeLogDao.save(codeLog);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		// broadcast (pub)
		GlueMessage message = new GlueMessage();
		message.setName(codeInfo.getName());
		message.setType(GlueMessageType.DELETE);
		message.setAppnames(null);
		XxlGlueBroadcaster.getInstance().procuceMsg(message);

		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> saveCodeInfo(CodeInfo codeInfo) {
		// valid
		if (StringUtils.isBlank(codeInfo.getName())) {
			return new ReturnT<String>(500, "“Glue名称”不可为空");
		}
		if (StringUtils.isBlank(codeInfo.getAbout())) {
			return new ReturnT<String>(500, "“Glue简介”不可为空");
		}
		// check old
		CodeInfo codeInfo_old = codeInfoDao.loadCodeByName(codeInfo.getName());
		if (codeInfo_old != null) {
			return new ReturnT<String>(500, "“GLUE名称”已存在");
		}
		// save
		int ret = codeInfoDao.save(codeInfo);
		if (ret < 1) {
			return new ReturnT<String>(500, "新增失败");
		}
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> updateCodeSource(CodeInfo codeInfo) {
		// valid
		if (codeInfo.getId() < 1) {
			if (StringUtils.isBlank(codeInfo.getRemark())) {
				return new ReturnT<String>(500, "参数异常");
			}
		}
		if (StringUtils.isBlank(codeInfo.getRemark())) {
			return new ReturnT<String>(500, "“GLUE备注”不可为空");
		}
		// check old
		CodeInfo codeInfo_old = codeInfoDao.loadCode(codeInfo.getId());
		if (codeInfo_old==null) {
			return new ReturnT<String>(500, "“GLUE”不存在");
		}
		// update
		int ret = codeInfoDao.update(codeInfo);
		if (ret < 1) {
			return new ReturnT<String>(500, "更新失败");
		}
		// save old (backup)
		try {
			CodeLog codeLog = new CodeLog();
			BeanUtils.copyProperties(codeLog, codeInfo_old);
			codeLog.setRemark(codeLog.getRemark() + "[backup for 编辑更新]");
			codeLogDao.save(codeLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// remove log more than 10
		codeLogDao.removeOldLogs(codeInfo_old.getName());
		return ReturnT.SUCCESS;
	}

	@Override
	public CodeInfo loadCode(int id) {
		return codeInfoDao.loadCode(id);
	}

	@Override
	public List<CodeLog> loadLogs(String name) {
		return codeLogDao.loadLogsByName(name);
	}

	@Override
	public ReturnT<String> clearCache(int id, String appNames) {
		CodeInfo codeInfo = codeInfoDao.loadCode(id);
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

		// broadcast
		GlueMessage message = new GlueMessage();
		message.setName(codeInfo.getName());
		message.setType(GlueMessageType.CLEAR_CACHE);
		message.setAppnames(appList);
		XxlGlueBroadcaster.getInstance().procuceMsg(message);

		return ReturnT.SUCCESS;
	}

}
