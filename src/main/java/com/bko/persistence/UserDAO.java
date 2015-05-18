package com.bko.persistence;

import java.util.List;

import com.bko.domain.User;


public interface UserDAO {
	public List<User> list();
	
	public User get(int id);
	
	public void saveOrUpdate(User user);
	
	public void delete(int id);
}