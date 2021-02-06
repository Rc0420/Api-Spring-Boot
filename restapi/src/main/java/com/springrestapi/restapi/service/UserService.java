package com.springrestapi.restapi.service;

import java.util.List;

import com.springrestapi.restapi.entities.User;

public interface UserService {

	public User addUser(User user);

	public User fetchByUserEmailId(String emailId);
	public List<User> getUsers();	
	
	public User updatePasswd(User user,String passwd);

	public User fetchByUserId(int id);
	
	public void userDelete(String emailId);

}
