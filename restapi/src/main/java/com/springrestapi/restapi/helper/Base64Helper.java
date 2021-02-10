package com.springrestapi.restapi.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
 @Component
public class Base64Helper {
//		static path
		public final String upload_dir="C:\\Users\\HP\\Downloads\\restapi\\restapi\\src\\main\\resources\\static\\image";
		
		public Base64Helper() throws IOException {}
		
		
		public String uploadEncodedImage(String encodedImage,String filename) {	
			String path = null;
			try {
				System.out.println("reading");
				//System.out.println(encodedImage);
				byte contains[] = Base64.getDecoder().decode(encodedImage);
				String directory = upload_dir+File.separator+filename+".jpg";
				System.out.println("Start writing >>>>>>>>");
				new FileOutputStream(directory).write(contains);
				System.out.println("End Writing>>>>>>>>>>");
				path=ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(filename+".jpg").toUriString();

			} catch (Exception e) {
				return path;
			}
			   return path;
		}
		
}
 