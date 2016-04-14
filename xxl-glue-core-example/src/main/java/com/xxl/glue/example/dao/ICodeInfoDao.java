package com.xxl.glue.example.dao;

import com.xxl.glue.example.core.model.CodeInfo;

public interface ICodeInfoDao {
	
	public CodeInfo loadCodeByName(String name);
	
}
