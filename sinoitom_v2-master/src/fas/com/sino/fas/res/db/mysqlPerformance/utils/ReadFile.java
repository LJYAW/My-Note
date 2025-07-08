package com.sino.fas.res.db.mysqlPerformance.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springside.modules.utils.Identities;

import com.sino.fas.res.db.mysqlPerformance.entity.SqlCommand;

public class ReadFile {
	private static String separator = File.separator;
	
	private String driver ="";
	private String url = "";
	private String username = "";
	private String password = "";
	
	private List<SqlCommand> sqlCommandList = new ArrayList<SqlCommand>();

	public static void main(String[] args) {
		ReadFile readFile = new ReadFile();
		String driver2 = readFile.getDriver();
		System.out.println(driver2);

		List<SqlCommand> sqlCommandList2 = readFile.getSqlCommandList();
		for (SqlCommand sqlCommand : sqlCommandList2) {
			System.out.println(sqlCommand);
		}
	}
	
	public ReadFile(){
//		读取application.properties获取数据库连接参数
		readApplicationPropertie();
		readTxtFile();
	}
	
//	读取application.properties文件
	private void readApplicationPropertie(){
		String filePath = getConfPath("application.properties");
//		System.out.println(filePath);
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(filePath));
			this.driver = p.getProperty("jdbc.driver");
			this.url = p.getProperty("jdbc.url");
			this.username = p.getProperty("jdbc.username");
			this.password = p.getProperty("jdbc.password");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    
	}
	
	
//	读取sqlCommand.txt文件
    private void readTxtFile() {
    	// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的sqlCommand.txt文件
    	String filePath = getConfPath("sqlCommand.txt");
        
        try {
        	
        	FileReader reader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(reader) ;// 建立一个对象，它把文件内容转成计算机能读懂的语言
        	
            String line;
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
            	String[] split = line.trim().split("#");
            	
            	SqlCommand sqlCommand = new SqlCommand();
            	sqlCommand.setCommand(split[0].trim());
            	sqlCommand.setDescription(split[1].trim());
            	sqlCommand.setCommandId(Identities.uuid());
            	sqlCommand.setMysqlVersion("5.6.18");
            	sqlCommandList.add(sqlCommand);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//	获取绝对路径
	private String getConfPath(String fileName){
//		String path = ReadFile.class.getResource("/").getPath();//		/F:/Users/Administrator/Workspaces-MyEclipse%2010/SinoACS/WebRoot/WEB-INF/classes/
		
//		path = (path.replace("%20"," ").replace("classes/", "conf/")).replaceFirst("/", "");
		String path = new File(ReadFile.class.getResource("/").getPath()).getParent().replace("%20"," ")+separator+"conf"+separator;
        // 如果有文件名，则在路径上加入文件名
        if (StringUtils.isNotBlank(fileName)) {
            path += fileName;
        }
        return path;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<SqlCommand> getSqlCommandList() {
		return sqlCommandList;
	}
	
	
}
