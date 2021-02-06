package com.springrestapi.restapi.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springrestapi.restapi.entities.User;
import com.springrestapi.restapi.entities.UserPost;
import com.springrestapi.restapi.helper.FileUploadHelper;
import com.springrestapi.restapi.service.EmailService;
import com.springrestapi.restapi.service.UserService;
import com.springrestapi.restapi.service.userpost.UserPostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "All User Opration", tags = "Admin")
public class MyController {
	@Autowired
	UserService userservice;
	@Autowired
	EmailService emailservice;
	
	@Autowired
    UserPostService userpostservice;
	
	
	@Autowired
	FileUploadHelper fuh;
	
	@GetMapping("/users")
	public List<User> getUsers()
	{
		return this.userservice.getUsers();
	}
	 
	 @ApiOperation(value = " Registration information ", tags = "Register")

	@PostMapping("/register")
	public  String addUser(@RequestParam String emailId,@RequestParam String name,@RequestParam String passwd) throws Exception
	{
     
		// validation of dublicate email id 
	 
		if(emailId!=null && !"".equals(emailId))
		{
			User userObj=userservice.fetchByUserEmailId(emailId);
			
			if(userObj!=null) {
		    return "User with "+emailId+" is already exist";
		     }
			 
			
		}
		     User user =new User(name,emailId,passwd);
		
		  // genrate 4 digit otp number
		             int  myotp=(int)(Math.random() * (8015)) + 2085;
	          		boolean status=emailservice.SendEmail("Otp is  "+myotp , emailId) ;
	       if(!status)
	       {
	    	   return "Not sending Mail";
	       }  
	          LocalTime time=LocalTime.now(); 
	          LocalDate date=LocalDate.now();
	          user.setOtp(myotp);
 	          user.setActive(false);
 	          user.setLocalDate(date);
 	          user.setLocalTime(time);
	          userservice.addUser(user);
	         
		return "verification Start.....";
	}
	
  
	 @ApiOperation(value = "Registration by email verification ", tags = "Register")
	@PostMapping("/register/{otp}")
	public String verifyByEmail(@RequestParam String emailId ,@PathVariable int otp) throws Exception
	{
		 User userObj=userservice.fetchByUserEmailId(emailId); 
		 if(userObj==null)
		 return "User not register";
		  int myotp=userObj.getOtp();
		  int t=LocalTime.now().minusMinutes(2).compareTo(userObj.getLocalTime());
	      int d=userObj.getLocalDate().compareTo(LocalDate.now());
	      
	      if(d!=0 || t>=0)
	      {
	       userservice.userDelete(emailId);
		   return "Expire Otp"+d+" "+t;
	      } 
		   
		 if(myotp!=otp)
		 {
		  return "Otp is incorrect";
		 }
		
		userObj.setActive(true);
		userservice.addUser(userObj);
        
		return "Successfull register in database";
	}
    
	
	 @ApiOperation(value = "Login User ", tags = "Login")
	    @GetMapping("/login")
	    public ResponseEntity<String> getVaildUser(@RequestParam  String email,@RequestParam  String passwd) 
	    {     
	    	 User userObj=userservice.fetchByUserEmailId(email);
	    	 if(userObj==null)  		 
	         return new ResponseEntity<>("Email is invaild",HttpStatus.BAD_REQUEST); 
	         if(userObj.getPasswd().equals(passwd))
	         { 
	        	 
	        	 // decode base64 url
	   	    	  byte[] decodedBytes = Base64.getUrlDecoder().decode(userObj.getProfile_pic_url());
	   	    	  String decodedUrl = new String(decodedBytes);
	   	    	  
	   	    	  //	 
	        	 
	        	 return ResponseEntity.status(HttpStatus.OK).body("Name:-"+userObj.getName()+"\n"
       				   +"User EmailId:- "+userObj.getEmail()+"\n"
       				   +"UserId- "+userObj.getUid()+"\n"
       				   +"Profile Url:-"+decodedUrl+"\n"
       				   );
	    	 }
	    	return new ResponseEntity<>("Incorrect Password",HttpStatus.BAD_REQUEST);
	    }
  
	 @ApiOperation(value = "Login User but not remember password ", tags = "Forgot Password")
	   @PostMapping("/login/forgot_passwd")
	    public ResponseEntity<String> resetPasswd(@RequestParam String email)
	    { 
		  
		    User userObj=userservice.fetchByUserEmailId(email);
	        
		   if(userObj!=null)
		   {
				int myotp=(int)(Math.random() * (8017)) + 2083;
				userObj.setOtp(myotp);
				  LocalTime time=LocalTime.now(); 
		          LocalDate date=LocalDate.now();
		   
	 	          userObj.setLocalDate(date);
	 	          userObj.setLocalTime(time);
		      
				userservice.addUser(userObj);    	  
			    boolean status=emailservice.SendEmail("Otp is  "+myotp ,email) ; 
				if(!status) 
				{
			    return new ResponseEntity<>("Otp is not sending",HttpStatus.BAD_REQUEST);
				}
				
		          
				return new ResponseEntity<>("Otp is sending ",HttpStatus.OK);				
		   }

				return new ResponseEntity<>("Email is invaild",HttpStatus.BAD_REQUEST);
			  
	    }
	
	  @ApiOperation(value = "Login User but not remember password ", tags = "Forgot Password")
		   
	   @PostMapping("/login/forgot_passwd/verify") 
	    public ResponseEntity<String> resetPasswdWithVerify(@RequestParam String email,@RequestParam int otp, @RequestParam String passwd)
	    {
		  
		       User userObj=userservice.fetchByUserEmailId(email);
		       if (userObj==null)
			   return new ResponseEntity<>("Incorrect User",HttpStatus.BAD_REQUEST); 
		       int myotp=userObj.getOtp();
			   int t=LocalTime.now().minusMinutes(2).compareTo(userObj.getLocalTime());
		       int d=userObj.getLocalDate().compareTo(LocalDate.now());
		      
		      if(d!=0 || t>=0)
			  return new ResponseEntity<>("Otp is expire",HttpStatus.BAD_REQUEST);
              		   
		     if(myotp!=otp)
		    return new ResponseEntity<>("Incorrect otp",HttpStatus.BAD_REQUEST);
		   
		   // reset passwd
		    userservice.updatePasswd(userObj,passwd);
            
		   return new ResponseEntity<>("Successfull reset Password ",HttpStatus.OK);
	   }
 
	  
	         @ApiOperation(value = "Create New Post ", tags = "Post")
	         @PostMapping("/create_post")
	         public ResponseEntity<String> createUserPost(@RequestParam int uid,@RequestParam MultipartFile file,@RequestParam String title,@RequestParam String content)
	         {
                           
	        	 User userObj=userservice.fetchByUserId(uid);
	        	 if(userObj==null)
	 	    	{
	 	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not Found User");
	 	    	}
	        	 UserPost userpost=new UserPost(title,content);
	        	 // pic url 
		    			
		    			if(file.isEmpty()) {
		    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Pic Not Found");
		    			}
		    			
		    			boolean f = fuh.uploadFile(file);
		    			
		    			if(!f) {
		    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile Not Upload");
		    			}
		    			
		    			String url=ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString();
		    		//encode base64 url

               String originalUrl = url;
               String encodedUrl = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes());
		    		          	 
	        	 //
	        	  LocalTime time=LocalTime.now(); 
		          LocalDate date=LocalDate.now();
		   
	 	          userpost.setDate(date);
	 	          userpost.setTime(time);
		   //
	 	         userpost.setUrlpic(encodedUrl);
	        	 userObj.getPost().add(userpost);
	        	 userservice.addUser(userObj);
	 	         return new ResponseEntity<>("Successfull create post ",HttpStatus.OK);
	         }
	     
	         
	     @ApiOperation(value = "Retrive post ", tags = "Post")
	     @GetMapping("/get_post")
	     public ResponseEntity<String> getAllPost(@RequestParam int uid,@RequestParam int postid) throws Exception
	     {
    	   	
	    	User userObj=userservice.fetchByUserId(uid); 
	    	if(userObj==null)
	    	{
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not found User");
	    	}
	    	
	        List<UserPost> userpost=userObj.getPost();
	          for(UserPost post:userpost)
	          {
	        	      if(post.getPostId()==postid) {
	        		  
	        	    	  // decode base64 url
	        	    	  byte[] decodedBytes = Base64.getUrlDecoder().decode(post.getUrlpic());
	        	    	  String decodedUrl = new String(decodedBytes);
	        	    	  
	        	    	  //
	        	    	  return ResponseEntity.status(HttpStatus.OK).body("Name:-"+userObj.getName()+"\n"
	        				   +"PostId:- "+post.getPostId()+"\n"
	        				   +"Title:- "+post.getTitle()+"\n"
	        				   +"Post Url:-"+decodedUrl+"\n"
	        				   +"Content:- "+post.getPostContent()+"\n"
	        				   +"Time and Date:- "+post.getTime()+" "+post.getDate() 
	        				  );
	        	      
	        	      }
	          }
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Post not contain");  
	     }
	     
	     @ApiOperation(value = "Upload Profile Picture ", tags = "Login")
	     @PostMapping("/upload_file")
	     public ResponseEntity<String> FileUpload(@RequestParam int uid,@RequestParam MultipartFile file)throws Exception
	     {

	    	 User userObj=userservice.fetchByUserId(uid);
        	 if(userObj==null)
 	    	{
 	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not Found User");
 	    	}
        
        	 // pic url 
	    			
	    			if(file.isEmpty()) {
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Pic Not Found");
	    			}
	    			
	    			boolean f = fuh.uploadFile(file);
	    			
	    			if(!f) {
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile Not Upload");
	    			}
	    			
	    	String url=ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString();
	    		//encode base64 url

           String originalUrl = url;
           String encodedUrl = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes());
	    		          	 
        	 //

           userObj.setProfile_pic_url(encodedUrl);      	
      	   userservice.addUser(userObj);  		
	    		
	    		
	    	return ResponseEntity.status(HttpStatus.OK).body("Successfull upload Profile picture");
	     } 

	         
}
