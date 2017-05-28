package com.xxl.glue.admin.dao.impl;

import com.xxl.glue.admin.core.model.User;
import com.xxl.glue.admin.dao.IUesrDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDaoImpl implements IUesrDao {

	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int save(User user) {
		return sqlSessionTemplate.insert("UserMapper.save", user);
	}

	@Override
	public int update(User user) {
		return sqlSessionTemplate.update("UserMapper.update", user);
	}

	@Override
	public int delete(int id) {
		return sqlSessionTemplate.delete("UserMapper.delete", id);
	}

	@Override
	public List<User> loadAll() {
		return sqlSessionTemplate.selectList("UserMapper.loadAll");
	}

	@Override
	public User findByUserName(String userName) {
		return sqlSessionTemplate.selectOne("UserMapper.findByUserName", userName);
	}
}
