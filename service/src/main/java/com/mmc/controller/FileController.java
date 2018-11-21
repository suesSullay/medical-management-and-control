package com.mmc.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mmc.model.Msg;

@CrossOrigin
@RestController
public class FileController {
	@Value("${filePath}")
    private String filePath;
	
	@PostMapping("/uploadFile")
	public Msg multiUpload(HttpServletRequest request) {
		File fileP = new File(filePath);
		Msg msg = Msg.success();
		if(!fileP.exists()){
			System.out.println(fileP);
			fileP.mkdirs();
		} 
	    List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	    for (int i = 0; i < files.size(); i++) {
	        MultipartFile file = files.get(i);
	        if (file.isEmpty()) {
	            return Msg.fail();
	        }
	        File dest = null;
	        while(dest==null||dest.exists()) {
		        String fileName = file.getOriginalFilename();
		        msg.add("fileName", fileName);
		        String uuid= UUID.randomUUID().toString();
		        msg.add("value", uuid+fileName);
		        dest = new File(filePath+uuid+fileName);
	        }
	        try {
	            file.transferTo(dest);
	        } catch (IOException e) {
	            return Msg.fail();
	        }
	    }

	    return msg;

	}
	@GetMapping("/download")
	public void downloadFileByOutputStream(@RequestParam("value") String value,@RequestParam("fileName") String fileName,HttpServletRequest request, HttpServletResponse response) {
	 
		 response.setCharacterEncoding(request.getCharacterEncoding());
	        response.setContentType("application/octet-stream");
	        FileInputStream fis = null;
	        try {
	            File file = new File(filePath+value);
	            fis = new FileInputStream(file);
	            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	            IOUtils.copy(fis,response.getOutputStream());
	            response.flushBuffer();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fis != null) {
	                try {
	                    fis.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	 
	}
}
