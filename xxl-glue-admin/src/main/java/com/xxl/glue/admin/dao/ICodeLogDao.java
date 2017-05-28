package com.xxl.glue.admin.dao;

import java.util.List;

import com.xxl.glue.admin.core.model.CodeLog;

public interface ICodeLogDao {
	
	public int save(CodeLog codeLog);
	
	public List<CodeLog> loadLogsByName(String name);

	public int removeOldLogs(String name);

	public int delete(String name);
	
}
