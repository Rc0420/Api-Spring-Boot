package com.springrestapi.restapi.service;
import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;

import org.springframework.stereotype.Service;
 
@Service
public class  EmailService{  
	
 public boolean SendEmail(String mess,String email) {  
  String host="smtp.gmail.com";  
  final String user="rahulchoudhary420oo7@gmail.com";//change accordingly  
  final String password="Rahul@8209210799";//change accordingly  
    
  String to=email;//change accordingly  
  
   //Get the session object  
   Properties props = new Properties();  
   props.put("mail.smtp.host",host);  
   props.put("mail.smtp.port", "465");
   props.put("mail.smtp.ssl.enable", "true");
   props.put("mail.smtp.auth", "true");  
     
   Session session = Session.getDefaultInstance(props,  
    new javax.mail.Authenticator() {  
      protected PasswordAuthentication getPasswordAuthentication() {  
    return new PasswordAuthentication(user,password);  
      }  
    });  
  
   //Compose the message   
    try {  
     MimeMessage message = new MimeMessage(session);  
     message.setFrom(new InternetAddress(user));  
     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
     message.setSubject("User Verification");  
     message.setText(mess);  
       
    //send the message  
     Transport.send(message);  
  
     return true;  
   
     } catch (MessagingException e) {e.printStackTrace();}  
     return false;
 } 
 
 
 
 
}  
