package com.springrestapi.restapi.entities;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class User {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int uid;
private String name;
private String email;
private String passwd;
private String profile_pic_url;
@OneToMany(cascade=CascadeType.ALL)
@JoinColumn(name="post_fid",referencedColumnName="uid")
private List<UserPost> post=new ArrayList();
private boolean active;
private int otp;
private LocalDate localDate;
private LocalTime localTime;


public String getProfile_pic_url() {
	return profile_pic_url;
}
public void setProfile_pic_url(String profile_pic_url) {
	this.profile_pic_url = profile_pic_url;
}

public LocalDate getLocalDate() {
	return localDate;
}
public void setLocalDate(LocalDate localDate) {
	this.localDate = localDate;
}
public LocalTime getLocalTime() {
	return localTime;
}
public void setLocalTime(LocalTime localTime) {
	this.localTime = localTime;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}

public int getOtp() {
	return otp;
}
public void setOtp(int otp) {
	this.otp = otp;
}
public List<UserPost> getPost() {
	return post;
}
public void setPost(List<UserPost> post) {
	this.post = post;
}
public int getUid() {
	return uid;
}
public void setUid(int uid) {
	this.uid = uid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPasswd() {
	return passwd;
}
public void setPasswd(String passwd) {
	this.passwd = passwd;
}
public User(String name, String email, String passwd) {
	super();
	this.name = name;
	this.email = email;
	this.passwd = passwd;
}
public User() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "User [uid=" + uid + ", name=" + name + ", email=" + email + ", passwd=" + passwd + "]";
}
	
}
