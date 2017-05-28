package com.xxl.glue.admin.dao.impl;

import com.xxl.glue.admin.core.model.CodeLog;
import com.xxl.glue.admin.dao.ICodeLogDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CodeLogDaoImpl implements ICodeLogDao {

	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int save(CodeLog codeLog) {
		return sqlSessionTemplate.insert("CodeLogMapper.save", codeLog);
	}
	
	@Override
	public List<CodeLog> loadLogsByName(String name) {
		return sqlSessionTemplate.selectList("CodeLogMapper.loadLogsByName", name);
	}

	@Override
	public int removeOldLogs(String name) {
		return sqlSessionTemplate.delete("CodeLogMapper.removeOldLogs", name);
	}

	@Override
	public int delete(String name) {
		return sqlSessionTemplate.delete("CodeLogMapper.delete", name);
	}

}
