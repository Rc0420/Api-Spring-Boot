package com.springrestapi.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrestapi.restapi.entities.UserPost;

public interface UserPostDao extends JpaRepository<UserPost,Integer>{

}
