package com.xxl.dao;

import java.util.List;

import com.xxl.core.model.CodeLog;

public interface ICodeLogDao {
	
	public int save(CodeLog codeLog);
	
	public List<CodeLog> loadLogsByName(String name);
	
}
