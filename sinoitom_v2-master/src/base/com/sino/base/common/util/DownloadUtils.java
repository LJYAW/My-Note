package com.sino.base.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springside.modules.utils.PropertiesLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * 下载文件工具类，根据文件名下载文件，根据文件路径下载文件
 *
 * @author .
 * @date 2017年5月3日 上午9:55:32
 *
 */
public class DownloadUtils {	
	
		/**
		 * getFilePathBy
		 * @Description: 根据文件名获取文件路径
		 * @param name 文件名
		 * @throws IOException
		 * @return: String
		 */
		public static String getFilePathBy(String name) throws IOException {
			Properties p = PropertiesLoader.loadProperties("../conf/download.properties");
			return p.getProperty(name);
		}
		
		/**
		 * downloadName
		 * @Description: 根据文件名下载文件
		 * @param name 文件名
		 * @param request http请求
		 * @param response http响应
		 * @throws IOException 抛IO异常
		 * @return: boolean 是否下载成功
		 */
		public static boolean downloadName(String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
	        if( StringUtils.isBlank(name) ){
	        	return false;
	        }
	        
	        String filePath = DownloadUtils.getFilePathBy(name);
	        if( filePath == null ){
	        	return false;
	        }
	        
			String webPath = request.getSession().getServletContext().getRealPath("/");
	        return downloadFile(webPath+filePath, response);
		}	
		
		/**
		 * downloadFile
		 * @Description: 根据文件路径下载文件
		 * @param filePath 文件路径
		 * @param response 
		 * @throws IOException IO异常
		 * @return: boolean 是否下载成功
		 */
		public static boolean downloadFile(String filePath, HttpServletResponse response) throws IOException {
	        java.io.BufferedInputStream bis = null;  
	        java.io.BufferedOutputStream bos = null;  
	        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
	        try {  
	            long fileLength = new File(filePath).length();  
	            response.setContentType("application/x-msdownload;");  
	            response.setHeader("Content-disposition", "attachment; filename="  
	                    + new String(fileName.getBytes(), "iso-8859-1"));  
	            response.setHeader("Content-Length", String.valueOf(fileLength));  
	            bis = new java.io.BufferedInputStream(new FileInputStream(filePath));  
	            bos = new java.io.BufferedOutputStream(response.getOutputStream());  
	            byte[] buff = new byte[2048];  
	            int bytesRead;  
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	                bos.write(buff, 0, bytesRead);  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return false;
	        } finally {  
	            if (bis != null)  
	                bis.close();  
	            if (bos != null)  
	                bos.close();  
	        }  
	        
	        return true;
		}

}
