package com.xxl.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxl.admin.core.model.CodeInfo;
import com.xxl.admin.core.model.CodeLog;
import com.xxl.admin.core.result.ReturnT;
import com.xxl.admin.dao.ICodeInfoDao;
import com.xxl.admin.dao.ICodeLogDao;
import com.xxl.admin.service.ICodeService;
import com.xxl.admin.service.IJmsSendService;

@Service
public class CodeServiceImpl implements ICodeService {
	
	@Autowired
	private ICodeInfoDao codeInfoDao;
	@Autowired
	private ICodeLogDao codeLogDao;
	@Autowired
	private IJmsSendService jmsSendService;

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
		CodeInfo codeInfo = codeInfoDao.loadCode(id);
		if (codeInfo==null) {
			return new ReturnT<String>(500, "“删除失败,Glue”不存在"); 
		}
		int ret = codeInfoDao.delete(id);
		if (ret < 1) {
			return new ReturnT<String>(500, "删除失败");
		}
		jmsSendService.glueTopicPub(codeInfo.getName());
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> saveCodeInfo(CodeInfo codeInfo) {
		// valid
		if (StringUtils.isBlank(codeInfo.getName())) {
			return new ReturnT<String>(500, "“Glue名称”不可为空");
		}
		if (StringUtils.isBlank(codeInfo.getRemark())) {
			return new ReturnT<String>(500, "“Glue备注”不可为空");
		}
		// check old
		CodeInfo codeInfo_old = codeInfoDao.loadCodeByName(codeInfo.getName());
		if (codeInfo_old != null) {
			return new ReturnT<String>(500, "“Code名称”已存在");
		}
		// save
		int ret = codeInfoDao.save(codeInfo);
		if (ret < 1) {
			return new ReturnT<String>(500, "新增失败");
		}
		jmsSendService.glueTopicPub(codeInfo.getName());
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
			return new ReturnT<String>(500, "“Glue备注”不可为空");
		}
		// check old
		CodeInfo codeInfo_old = codeInfoDao.loadCode(codeInfo.getId());
		if (codeInfo_old==null) {
			return new ReturnT<String>(500, "“Code”不存在");
		}
		// update
		int ret = codeInfoDao.update(codeInfo);
		if (ret < 1) {
			return new ReturnT<String>(500, "更新失败");
		}
		// save old
		try {
			CodeLog codeLog = new CodeLog();
			BeanUtils.copyProperties(codeLog, codeInfo_old);
			codeLogDao.save(codeLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// remove log more than 10
		codeLogDao.removeOldLogs(codeInfo_old.getName());
		jmsSendService.glueTopicPub(codeInfo_old.getName());
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

}
