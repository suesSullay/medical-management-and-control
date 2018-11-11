package com.mmc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@CrossOrigin
@RestController
public class FileController {
	@Value("${filePath}")
    private String filePath;
	
	@PostMapping("/uploadFile")
	public String multiUpload(HttpServletRequest request) {
		File fileP = new File(filePath);
		if(!fileP.exists()){
			System.out.println(fileP);
			fileP.mkdirs();
		} 
	    List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	    for (int i = 0; i < files.size(); i++) {
	        MultipartFile file = files.get(i);
	        if (file.isEmpty()) {
	            return "上传第" + (i++) + "个文件失败";
	        }
	        File dest = null;
	        while(dest==null||dest.exists()) {
		        String fileName = file.getOriginalFilename();
		        var date = new Date();  
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH时mm分ss秒");  
		        fileName=sdf.format(date)+"  "+fileName;
		        dest = new File(filePath+fileName);
	        }
	        try {
	            file.transferTo(dest);
	        } catch (IOException e) {
	            return "上传第" + (i++) + "个文件失败";
	        }
	    }

	    return "上传成功";

	}
}
