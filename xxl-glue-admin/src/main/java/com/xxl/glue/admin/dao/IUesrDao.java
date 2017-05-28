package com.xxl.glue.admin.dao;

import com.xxl.glue.admin.core.model.User;

import java.util.List;

public interface IUesrDao {
	
	public int save(User user);

	public int update(User user);

	public int delete(int id);

	public List<User> loadAll();

	public User findByUserName(String userName);

}
