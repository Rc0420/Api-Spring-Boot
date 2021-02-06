package com.springrestapi.restapi.service.userpost;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrestapi.restapi.dao.UserPostDao;
import com.springrestapi.restapi.entities.UserPost;

@Service
public class UserPostOpration implements UserPostService{
    @Autowired
	private UserPostDao userpostdao;

	
    
    
    
}
