package com.xxl.admin.service;

import java.util.List;
import java.util.Map;

import com.xxl.admin.core.model.CodeInfo;
import com.xxl.admin.core.model.CodeLog;
import com.xxl.admin.core.result.ReturnT;

public interface ICodeService {

	public Map<String, Object> pageList(int offset, int pagesize, String name);
	
	public ReturnT<String> delete(int id);
	
	public ReturnT<String> saveCodeInfo(CodeInfo codeInfo);
	
	public ReturnT<String> updateCodeSource(CodeInfo codeInfo);
	
	public CodeInfo loadCode(int id);

	public List<CodeLog> loadLogs(String name);

}
