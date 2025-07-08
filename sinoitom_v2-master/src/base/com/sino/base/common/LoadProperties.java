package com.sino.base.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

 public class LoadProperties implements ServletContextListener{
	
	public  static String WEB_URL="http://192.168.99.160";
	public  static String OS_TYPE="linux";
	public  static String FILE_SEPARATOR = "/";  //非 Windows操作系统
	public  static String UPLOAD_DEVIMG_URL="/opt/SinoMSP/icon/devImg/";
	public  static String UPLOAD_BGIMG_URL="/opt/SinoMSP/icon/bgImg/";
	static{
		init();
	}
	
	
	public static void init(){
		Properties props=new Properties();
//		String urlFile = "/../conf/application.properties";
//		String urlFile = Global.LOAD_PRPO+"application.properties";
//		String urlFile ="D:/tomcat/apache-tomcat-6.0.35/conf/application.properties";
//		String urlFile = "/../conf/application.properties";
		
//		Properties sp = System.getProperties();
//		Enumeration e = sp.propertyNames();
//		while (e.hasMoreElements()) {
//			String key = (String) e.nextElement();
//			System.out.println(key + " = " + sp.getProperty(key));
//		}
//        
		
		FILE_SEPARATOR = System.getProperty("file.separator");
		
		String sysParameterFile = "";
		
		String osType = System.getProperty("os.name");
		if (osType.toLowerCase().contains("windows")){
			OS_TYPE = "windows";
			sysParameterFile=new File(System.getProperty("user.dir")).toString().replace("bin", "conf")+"\\application.properties";
		}else{
			sysParameterFile=new File(System.getProperty("user.dir")).toString().replace("bin", "conf")+"/application.properties";
		}
		System.out.println("osType is: " + osType);
		System.out.println("FILE_SEPARATOR is: " + FILE_SEPARATOR);
		System.out.println("Database Config File Path is：" + sysParameterFile);
		
		try {
			FileInputStream  inputFile = new FileInputStream(sysParameterFile);  
			props.load(inputFile);  
	        inputFile.close();  
	        if (props != null) {
//				WEB_URL = props.getProperty("WEB_URL");
//				UPLOAD_DEVIMG_URL = props.getProperty("UPLOAD_DEVIMG_URL");
//				UPLOAD_BGIMG_URL = props.getProperty("UPLOAD_BGIMG_URL");
//				System.out.println("WEB_URL=" + WEB_URL);
				System.out.println("UPLOAD_DEVIMG_URL=" + UPLOAD_DEVIMG_URL);
				System.out.println("UPLOAD_BGIMG_URL=" + UPLOAD_BGIMG_URL);
				System.out.println(new File(System.getProperty("user.dir").toString()));
			}
	        
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		

	}


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
