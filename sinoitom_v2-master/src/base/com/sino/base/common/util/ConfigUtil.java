package com.sino.base.common.util;

import java.io.IOException;
import java.util.Properties;

import org.springside.modules.utils.PropertiesLoader;


public class ConfigUtil
{
	static String propCommonPath = "/../conf/common.properties";
	static String propFilePath = "/../conf/file.properties";
	static Properties propCommon;
	static Properties propFile;
	static boolean inited = false;

	private static boolean init()
	{
		if( inited ){
			return true;
		}
		try {
			propCommon = PropertiesLoader.loadProperties(propCommonPath);
			propFile = PropertiesLoader.loadProperties(propFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		inited = true;
		return inited;
	}

	public static String getProp(String key)
	{
		if( !init() ){
			return null;
		}
		return propCommon.getProperty(key);
	}

	public static String getProp(String key, String defaultValue)
	{
		if( !init() ){
			return null;
		}
		return propCommon.getProperty(key, defaultValue);
	}

	public static String getFilePath(String key){
		if( !init() ){
			return null;
		}
		return propFile.getProperty(key);
	}

	public static String getFullPath(String key){
		String filePath = getFilePath(key);
		if( filePath == null ){
			return null;
		}
		return SystemUtil.getRootPath() + filePath;
	}
}
