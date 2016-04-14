package com.xxl.admin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.xxl.admin.core.model.CodeInfo;
import com.xxl.admin.dao.ICodeInfoDao;

@Repository
public class CodeInfoDaoImpl implements ICodeInfoDao {

	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<CodeInfo> pageList(int offset, int pagesize, String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("name", name);
		
		return sqlSessionTemplate.selectList("CodeInfoMapper.pageList", params);
	}

	@Override
	public int pageListCount(int offset, int pagesize, String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("name", name);
		
		return sqlSessionTemplate.selectOne("CodeInfoMapper.pageListCount", params);
	}

	@Override
	public int delete(int id) {
		return sqlSessionTemplate.delete("CodeInfoMapper.delete", id);
	}

	@Override
	public int save(CodeInfo codeInfo) {
		return sqlSessionTemplate.insert("CodeInfoMapper.save", codeInfo);
	}

	@Override
	public int update(CodeInfo codeInfo) {
		return sqlSessionTemplate.update("CodeInfoMapper.update", codeInfo);
	}

	@Override
	public CodeInfo loadCode(int id) {
		return sqlSessionTemplate.selectOne("CodeInfoMapper.load", id);
	}

	@Override
	public CodeInfo loadCodeByName(String name) {
		return sqlSessionTemplate.selectOne("CodeInfoMapper.loadByName", name);
	}

}
