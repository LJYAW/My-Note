package com.sino.base.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class DownloadUtil {
	
	private static String lastErrorMsg="";
	
	public static String getLastErrorMsg(){
		return lastErrorMsg;
	}

	public static boolean downloadName(String fileName, HttpServletResponse response) throws IOException {
		if (StringUtils.isBlank(fileName)) {
			lastErrorMsg = "文件名为空！";
			return false;
		}

		String filePath = ConfigUtil.getFilePath(fileName);
		if (filePath == null) {
			lastErrorMsg = "找不到对应文件路径！";
			return false;
		}

		return downloadFile(SystemUtil.getRootPath()+filePath, response);
	}
	

	public static String getFileName(String pathName){
		if( StringUtils.isBlank(pathName) ){
			return pathName;
		}
		int n1 = pathName.lastIndexOf("\\");
		int n2 = pathName.lastIndexOf("/");
		int n = n2>n1 ? n2:n1;
		if (n >= 0) {
			return pathName.substring(n + 1);
		}
		return pathName;
	}

	public static boolean downloadFile(String filePath, HttpServletResponse response) throws IOException {
		File file = new File(filePath);
		if( !file.exists() ){
			lastErrorMsg = "下载文件不存在！";
			return false;
		}

		return downloadFile(getFileName(filePath), new FileInputStream(filePath), response);
	}
	
	public static boolean downloadFile(String fileName, FileInputStream stream, HttpServletResponse response) throws IOException {
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		try {
			long fileLength = stream.available();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes(), "iso-8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new java.io.BufferedInputStream(stream);
			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			lastErrorMsg = "文件传输出现错误！";
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
