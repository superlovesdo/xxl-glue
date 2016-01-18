package com.xxl.service;

import java.util.List;
import java.util.Map;

import com.xxl.core.model.CodeInfo;
import com.xxl.core.model.CodeLog;
import com.xxl.core.result.ReturnT;

public interface ICodeService {

	public Map<String, Object> pageList(int offset, int pagesize, String name);
	
	public ReturnT<String> delete(int id);
	
	public ReturnT<String> saveCodeInfo(CodeInfo codeInfo);
	
	public ReturnT<String> updateCodeSource(CodeInfo codeInfo);
	
	public CodeInfo loadCode(int id);

	public List<CodeLog> loadLogs(String name);

}
