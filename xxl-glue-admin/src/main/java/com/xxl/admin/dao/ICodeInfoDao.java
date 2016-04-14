package com.xxl.admin.dao;

import java.util.List;

import com.xxl.admin.core.model.CodeInfo;

public interface ICodeInfoDao {
	
	public List<CodeInfo> pageList(int offset, int pagesize, String name);
	public int pageListCount(int offset, int pagesize, String name);
	
	public int delete(int id);
	
	public int save(CodeInfo codeInfo);
	
	public int update(CodeInfo codeInfo);
	
	public CodeInfo loadCode(int id);
	public CodeInfo loadCodeByName(String name);
	
}
