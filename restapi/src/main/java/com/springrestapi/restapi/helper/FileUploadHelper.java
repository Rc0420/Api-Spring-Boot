package com.springrestapi.restapi.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	
//	static path
	public final String upload_dir="C:\\Users\\HP\\Downloads\\restapi\\restapi\\src\\main\\resources\\static\\image";
	
	//dynamic path
	
//	public final String upload_dir=new ClassPathResource("/static/Image").getFile().getAbsolutePath();
		
	
	public boolean uploadFile(MultipartFile file) 
	{
        boolean f = false;
		
		try {
//			InputStream is = multipartFile.getInputStream();
//			byte data[] = new byte[is.available()];
//			is.read(data);
//			
//			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename());
//		fos.write(data);
//			
//		fos.flush();
//		fos.close();
			
		Files.copy(file.getInputStream(), Paths.get(upload_dir+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		f = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return f;
		
		
		
	}
    
}
                       