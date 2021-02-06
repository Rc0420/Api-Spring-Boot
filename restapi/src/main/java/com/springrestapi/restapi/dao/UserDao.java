package com.springrestapi.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrestapi.restapi.entities.User;
public interface UserDao extends JpaRepository<User,Integer>
{
	
	public User findByEmail(String emailId);

	public User findByUid(int uid);
	
}
