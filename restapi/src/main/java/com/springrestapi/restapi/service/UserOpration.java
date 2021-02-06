package com.springrestapi.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrestapi.restapi.dao.UserDao;
import com.springrestapi.restapi.entities.User;

@Service
public class UserOpration implements UserService {
	
	@Autowired
	private UserDao userdao;
	public User addUser(User user)
	{

	    userdao.save(user);
	    return user;
	}
	
	@Override
	public List<User> getUsers() {
	
		return userdao.findAll();

	}
	

	@Override
	public User fetchByUserEmailId(String emailId) {
	
		return userdao.findByEmail(emailId);
	
	}

	@Override
	public User updatePasswd(User user,String passwd) {
		user.setPasswd(passwd);
		userdao.save(user);
		return user;
	}

	@Override
	public User fetchByUserId(int uid) {
		
		return userdao.findByUid(uid);
	}

	@Override
	public void userDelete(String emailId) {
		// TODO Auto-generated method stub
		User user=userdao.findByEmail(emailId);
		userdao.delete(user);
		
	}
	
	
	
}
