package com.sino.base.common;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseAction {

	@InitBinder  
	protected void initBinder(HttpServletRequest request,  ServletRequestDataBinder binder) throws Exception {  
		binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
	}  
	
	@ExceptionHandler(Exception.class)       
	public String handleException(Exception ex, HttpServletResponse response) throws Exception {     
	    if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){  
			String outStr = getCheckResult(Global.RESULT_ERROR,"上传文件超过限定大小！");
			response.getWriter().print(outStr);
	    }  
	    return null;         
	}  
	
	public String getCheckResult(String result, String message){
		return "<script>parent.checkResult('"+result+"','"+message+"')</script>";
	}
}
