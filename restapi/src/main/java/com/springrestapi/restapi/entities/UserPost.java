package com.springrestapi.restapi.entities;



import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.springframework.data.annotation.CreatedDate;


@Entity
public class UserPost {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int postId;
    private String title;
    private String urlpic;
    
    public String getUrlpic() {
		return urlpic;
	}
	public void setUrlpic(String urlpic) {
		this.urlpic = urlpic;
	}
	private String postContent;
    private LocalDate date;
    public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	private LocalTime time;
		
    
	public UserPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserPost(String title, String postContent) {
		super();
		this.title = title;
		this.postContent = postContent;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	
	
	
	
	
	
}
