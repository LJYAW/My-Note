package com.sino.snmp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sino.snmp.entity.SnmpMibNode;
import com.sino.snmp.utils.GlobalVar.KeyWord;
import com.sino.snmp.utils.GlobalVar;
import com.sino.snmp.utils.mib.JdbcConnection;
import com.sino.utils.common.ItUtil;

public class ParseMibFilesList {
	
//	list中存放的是读取一次文件，该文件和该文件所涉及的文件中的所有节点对象
	private static List<SnmpMibNode> list = new ArrayList<SnmpMibNode>();
//	MultipartFileList是你所选的所有文件
	private static List<MultipartFile> MultipartFileList = new ArrayList<MultipartFile>();
	
//	文件的后缀，标识文件的类型，比如.mib
//	private static String SUFFIX = "";
//	文件的前缀，标识不同厂商的文件，比如RUIJIE、hh3c
	private static String PREFIX = "";
	
	public static void main(String[] args) {
		
//		parseMethod(fileNameList,suffix);
		
	}
	public static void parseMethod(List<MultipartFile> fileList){
		
		MultipartFileList = fileList;
		
		for (MultipartFile multipartFile : fileList) {
			//hh3c-oid.mib/RUIJIE-SMI.mib
			String fileName = multipartFile.getOriginalFilename().trim();
			
//			判断该文件是否已经解析过并存入库了
			boolean isExist = queryFileName(fileName);
			if(isExist){//isExist为true就是已经在库中了,跳过
				continue;
			}else{//没有入库就读取文件并解析
				
//				int dotIndex = fileName.indexOf(".");
//				SUFFIX = fileName.substring(dotIndex+1, fileName.length());//mib
				
				String[] split = fileName.split("-");
				PREFIX = split[0];//获取前缀:hh3c
				
//				获取该文件中所有的节点和引入的节点
				readMibFile(multipartFile,"");
				
//				输出
				for (SnmpMibNode mib : list) {
					System.out.println(mib.toString());
				}
				System.out.println(list.size());
				
//				保存
				saveMibNode(list);
				list = new ArrayList<SnmpMibNode>();
			}
		}
		
	}
	
//	下面这个方法就是给小弟赋值oid，list是所有的节点，parentName就是父节点的名称，parentOid是父节点的oid
//	过程就是找到父节点的oid，然后给小弟赋值
	public static void putOid(List<SnmpMibNode> list,String parentName,String parentOid) {
		
		long parentId = ItUtil.getCRC32(parentName+parentOid);//该节点的nodeId
		
		for (SnmpMibNode mib : list) {
			
			String parent = mib.getParent();
			Integer nodeNo = mib.getNodeNo();
			String nodeName = mib.getNodeName();
			
			if(nodeName.equals(parentName)){//如果父节点这个对象在当前文件中，那么就给该对象赋值nodeId，不在的话就往下走
				mib.setNodeId(parentId);//赋值nodeId
				
//				赋值flag属性
		    	String oidStr = parentOid.replaceAll("\\.", "");
				Pattern pattern = Pattern.compile("[0-9]+");
//				如果oid不是空的且全是数字，那么说明已经解析 
				if(StringUtils.isNotBlank(parentOid) || pattern.matcher(oidStr).matches()){
					mib.setFlag(1);
				}else{//否则说明没有解析
					mib.setFlag(0);
				}
				continue;
			}
			
			if(parent.equals(parentName)){//该节点的小弟
//				如果父节点的oid已经解析，那么就赋值oid，如果父节点oid没有解析，那么oid保持原状
		    	String parentOidStr = parentOid.replaceAll("\\.", "");
				Pattern pattern = Pattern.compile("[0-9]+");
//				如果parentOidStr不是空的且全是数字，那么说明已经解析 
				if(StringUtils.isNotBlank(parentOid) || pattern.matcher(parentOidStr).matches()){
					
					mib.setOid(parentOid+"."+nodeNo);
					mib.setFlag(1);
					
				}else{//否则说明没有解析
					mib.setFlag(0);
				}
				
				mib.setParentId(parentId);
				long nodeId = ItUtil.getCRC32(mib.getNodeName()+mib.getOid());
				mib.setNodeId(nodeId);
				
//				然后以当前小弟为父节点给他的子节点赋值
				putOid(list,mib.getNodeName(),mib.getOid());
			}
		}
	}

	//	拿到当前文件中所有的节点
	public static void readMibFile(MultipartFile impFile,String rootName){
		
//		--------------------解析构建mibNode涉及到的变量start---------------------------------------------------------
		InputStream inputStream = null;
		 
//		 当前文件中引入的节点和节点所在的文件名
		 List<String> importObjectList = new ArrayList<String>();
		 
//		所求文件中引入的节点和该节点所在文件
		Map<String,List<String>> importsMap = new HashMap<String,List<String>>();
		 
//		 获取description内容
//		StringBuilder descripSb = new StringBuilder();
		
//		 读取description内容的标识
//		 boolean descripFlag = false;
		 
//		 读取imports内容的标识
		 boolean flag = false;
		 
//		 这个文件对象的名字
		 String moduleName = "";
		 
		 SnmpMibNode mib = new SnmpMibNode();
//		 当前文件中的所有节点
		 List<SnmpMibNode> mibList = new ArrayList<SnmpMibNode>();

//		 -----------------------解析构建mibNode end---------------------------------------------
		
//		 ------------------------构造Snmp_MibNode对象start----------------------------------------------
	    try {
	    	inputStream = impFile.getInputStream();
	    	List<String> allRowList = IOUtils.readLines(inputStream);
	    	if(allRowList!=null && allRowList.size()>0){
	    		for (String tempString : allRowList) {
//		        	注释忽略和空字符串，不解析
		        	if(StringUtils.isBlank(tempString) || tempString.startsWith(GlobalVar.ANNOTATE)  || tempString.equals(GlobalVar.END)){
		        		continue;
		        	}
		        	
//		        	得到moduleName
		        	if(tempString.contains(GlobalVar.BEGIN)){
		        		moduleName = tempString.trim().replace(GlobalVar.BEGIN, "").trim();
		        		continue;
		        	}
		        	
//		        	获取imports的信息
		        	if(flag){
		        		
		        		if(tempString.contains(";")){//import部分结束
	        				flag = false;
	        			}
		        		
		        		if(tempString.contains(GlobalVar.FROM) ){//例如 tempString = FROM HH3C-OID-MIB、FROM RUIJIE-SMI
		        			
//		        			如果不包含HH3C-(RUIJIE-) 或者是	包含HH3C-(RUIJIE-)但包含-TC的不要
//		        			if(!tempString.contains(PREFIX.toUpperCase()+"-") || (tempString.contains(PREFIX.toUpperCase()+"-") && tempString.endsWith("TC"))){
//		        				importObjectList = new ArrayList<String>();
//		        				continue;
//		        			}
		        			
//		        			去掉FROM
		        			String importMibStr = tempString.replaceAll(GlobalVar.FROM, "").trim();
		        			if(importObjectList!=null && importObjectList.size()>0 && StringUtils.isNotBlank(importMibStr)){
		        				if(importMibStr.contains(";")){
		        					importMibStr = importMibStr.replace(";", "");
		        				}
		        				importsMap.put(importMibStr, importObjectList);
		        				importObjectList = new ArrayList<String>();
		        				
		        				if(tempString.contains(";")){
		        					continue;
		        				}
		        			}
		        			
		        		}else{//拿到对象名
		        			String[] split = tempString.trim().split(",");
	        				for (String string : split) {
	        					importObjectList.add(string.trim());
							}
		        		}
		        	}
		        	
		        	if(tempString.contains(GlobalVar.IMPORTS)){
		        		flag = true;
		        		continue;
		        	}
		        	
		        	if(!flag){
		        		
		        		String nodeName2 = mib.getNodeName();
		        		
		        		if(StringUtils.isBlank(nodeName2)){
				        	for(KeyWord keyWord : GlobalVar.KeyWord.values()){
				        		String des = keyWord.getDes();
				        		if(tempString.contains(des)){
//				        			如果在结点的描述中出现了这个关键词要区分一下
				        			if(tempString.contains(GlobalVar.OID_START)){
				        				mib.setNodeType(des);
				        				int indexOf = tempString.indexOf(des);
			            				mib.setNodeName(tempString.substring(0, indexOf).trim());
			            				break;
				        			}
				        			if(tempString.split(des).length==1){
				        				String nodeName = tempString.replace(des, "").trim();
				        				mib.setNodeType(des);
				        				mib.setNodeName(nodeName);
				        				break;
				        			}
				        			continue;
				        		}
				        	}
		        		}
		        		
			        	if(tempString.contains(GlobalVar.SYNTAX)){
			        		String syntaxStr = tempString.replace(GlobalVar.SYNTAX, "").trim();
			        		if(tempString.contains("{")){
			        			syntaxStr = syntaxStr.replace("{", "").trim();
			        		}
			        		mib.setSyntax(syntaxStr);
			        		continue;
			        	}
			        	
			        	if(tempString.contains(GlobalVar.ACCESS)){
			        		int indexOf = tempString.indexOf(GlobalVar.ACCESS);
			        		String access = tempString.substring(indexOf+6).trim();
			        		
			        		mib.setAccess(access);
			        		continue;
			        	}
			        	
			        	if(tempString.contains(GlobalVar.STATUS)){
			        		mib.setStatus(tempString.replace(GlobalVar.STATUS,"").trim());
			        		continue;
			        	}
			        	
//			        	if(tempString.contains(GlobalVar.DESCRIPTION)){
//			        		descripFlag = true;
//			        		continue;
//			        	}
//			        	
//			        	if(descripFlag){ 
//			        		tempString = tempString.trim();
//			        		descripSb.append(tempString);
//			        		if(tempString.endsWith("\"")){
//			        			mib.setDescription(descripSb.toString().replaceAll("\"", "").trim());
//			        			descripFlag = false;
//			        			descripSb = new StringBuilder();
//			        		}
//			        		continue;
//			        	}
			        	
			        	if(tempString.contains(GlobalVar.OID_START)){
			        		
			        		int index1 = tempString.indexOf("{");
			        		int index2 = tempString.indexOf("}");
			        		
			        		String substring = tempString.substring(index1+1, index2).trim();
			        		
			        		String regEx = "[' ']+"; // 一个或多个空格
			        		Pattern p = Pattern.compile(regEx);
			        		Matcher m = p.matcher(substring);
			        		substring = m.replaceAll(" ").trim();
			        		
			        		mib.setOid(substring);
			        		
			        		String[] split = substring.split(" ");
			        		mib.setParent(split[0].trim());
			        		mib.setNodeNo(Integer.parseInt(split[1].trim()));
			        		
			        		
			        		if(mib!=null){
//			        			赋值moduleName
			        			mib.setModuleName(moduleName);
			        			
			        			mibList.add(mib);
			        		}
			        		mib = new SnmpMibNode();
			        		continue;
			        	}
			        	
		        	}
	    		}
	    	}
	        
//	        --------------------------构造Snmp_MibNode对象 end------------------------------------
	        
	    	inputStream.close();
	        list.addAll(mibList);
		 
	    }catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (inputStream != null) {
	            try {
	            	inputStream.close();
	            } catch (IOException e1) {
	            	e1.printStackTrace();
	            }
	        }
	    }
	    
//	    ----------------------这里给节点寻找并解析oid start--------------------
	    for (SnmpMibNode mibNode : mibList) {
	    	
//	    	如果一个结点的oid已经解析过了那么就跳过
	    	Integer oidIsParse = mibNode.getFlag();
	    	if(oidIsParse!=null && oidIsParse==1){
	    		continue;
	    	}
			
//	    	获取 parentName
	    	String parentName = mibNode.getParent();
//	    	获取该节点在该文件中最顶级的父节点，该节点不是标准的就是私有的
	    	String rootParentName = getRootParent(mibList,parentName);
	    	
//	    	1、看是不是snmp标准的
	    	String propertiesOid = loadPresetFile(rootParentName);
//	    	如果是标准的赋值oid，然后下一个
	    	if(StringUtils.isNotBlank(propertiesOid)){
	    		putOid(mibList, rootParentName, propertiesOid);
	    		continue;
	    	}
	    	
	    	String rootParentOid = "";
	    	
//	    	2、看是不是引入的私有的
	    	if(importsMap!=null && importsMap.size()>0){
	    		for(String key : importsMap.keySet()){//key:RUIJIE-SMI/HH3C-OID-MIB
	    			List<String> importMibList = importsMap.get(key);//importMibList:[hh3clswCommon, hh3cRhw]
					for (String importMibName : importMibList) {
						
						if(rootParentName.equals(importMibName)){//找到rootParent所在的文件
							
	//						2.1、看引入的该节点是否已经在数据库中
							rootParentOid = queryOid(rootParentName,key);
							if(StringUtils.isNotBlank(rootParentOid)){
								putOid(mibList, rootParentName, rootParentOid);
								break;
							}
							
//							2.2、如果没有在数据库中，那么看该文件我选了没有，如果选了，读取该文件
							if(PREFIX.equalsIgnoreCase("hh3c") && key.endsWith("-MIB")){
								key = key.substring(0, key.length()-4).toLowerCase();
							}
							
							for (MultipartFile file : MultipartFileList) {
								String fullName = file.getOriginalFilename().trim();
								String[] split = fullName.split("\\.");
								if(split[0].equalsIgnoreCase(key)){
//									在所选文件中找到该文件并继续读
									readMibFile(file,importMibName);
									break;
								}
							}
							
							break;
						}
					}
	    		}
	    		continue;
	    	}
	    	
//	    	3、如果propertiesOid和importsMap都是空的，说明既不是标准的也不是私有的，
//	    	       那oid就放着不解析,但是nodeId什么的还是要赋值的，要不无法保存
//	    	在给文件中找到rootParentName对应的节点
	    	putOid(mibList, rootParentName, mibNode.getOid());
		}
//	    	----------------------------给节点寻找并解析oid end----------------------------------------
	}
	
//	如果当前节点的父节点还在当前文件中就继续找他的父节点，当在当前文件中找不到他的父节点了那么就返回
	public static String getRootParent(List<SnmpMibNode> mibList,String parentName){
		
		String findStr = parentName;
		
		for(SnmpMibNode midNode:mibList){
    		String nodeName = midNode.getNodeName();
    		if(nodeName.equals(parentName)){
    			String parent = midNode.getParent();
    			findStr = getRootParent(mibList,parent);
    		}
    	}
		
		return findStr;
	}
	
//	保存
	public static void saveMibNode(List<SnmpMibNode> mibList){
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		PreparedStatement pstmt = null;
		if(conn!=null){
			try {
				for (SnmpMibNode mibNode : mibList) {
					
//					首先去数据库中查找是否存在该节点
					
					String oid = mibNode.getOid();
					
					String regex = ".*[a-zA-z].*";  
					
//					如果oid是空的或者oid中包含字母说明该节点没有解析
					if(StringUtils.isBlank(oid) || oid.matches(regex)){
						mibNode.setFlag(0);
					}else{
						mibNode.setFlag(1);
					}
					
					 String sql = "REPLACE into Snmp_MibNode (nodeId,moduleName,nodeNo,nodeName,nodeType,syntax,access,status,oid,description,flag,parentId) values(?,?,?,?,?,?,?,?,?,?,?,?)";
					 pstmt = (PreparedStatement) conn.prepareStatement(sql);
					 pstmt.setLong(1, mibNode.getNodeId());
					 pstmt.setString(2, mibNode.getModuleName());
					 pstmt.setInt(3, mibNode.getNodeNo());
					 pstmt.setString(4, mibNode.getNodeName());
					 pstmt.setString(5, mibNode.getNodeType());
					 pstmt.setString(6, mibNode.getSyntax());
					 pstmt.setString(7, mibNode.getAccess());
					 pstmt.setString(8, mibNode.getStatus());
					 pstmt.setString(9, mibNode.getOid());
					 pstmt.setString(10, mibNode.getDescription());
					 pstmt.setInt(11, mibNode.getFlag());
					 pstmt.setLong(12, mibNode.getParentId());
					 pstmt.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	finally{
				JdbcConnection.free(null, pstmt, conn);
			}
		}
	}
	
//	查询oid
	public static String queryOid(String rootParentName,String rootParentFileName){
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String oid = "";

		if(conn!=null){
			try {
				
				String sql = "SELECT oid FROM Snmp_MibNode where moduleName='"+rootParentFileName+"' and nodeName = '"+rootParentName+"';";
				pstmt = conn.prepareStatement(sql);

	            resultSet = pstmt.executeQuery();
	            
	            while(resultSet.next()){
	                oid = resultSet.getString(1);
	            }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	finally{
				JdbcConnection.free(resultSet, pstmt, conn);
			}
		}
		return oid;
	}
//	查询某个文件是否已经被解析入库
	public static boolean queryFileName(String fileName){//hh3c-oid/RUIJIE-SMI.mib
		
		fileName = fileName.split("\\.")[0];
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		boolean next = false;
		
		if(conn!=null){
			try {
				
				if(PREFIX.toLowerCase().equals("hh3c")){
					fileName = fileName.toUpperCase()+"-MIB";
				}
				
				String sql = "SELECT * FROM Snmp_MibNode where moduleName='"+fileName+"';";
				pstmt = conn.prepareStatement(sql);
				
				resultSet = pstmt.executeQuery();
				
				next = resultSet.next();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	finally{
				JdbcConnection.free(resultSet, pstmt, conn);
			}
		}
		return next;
	}
	
//	根据moduleName查找所有节点对象
	public static List<SnmpMibNode> getByModuelName(String fileName){//hh3c-oid/RUIJIE-SMI.mib
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		List<SnmpMibNode> mibNodeList = new ArrayList<SnmpMibNode>();
		
		if(conn!=null){
			try {
				
				String sql = "SELECT * FROM Snmp_MibNode where moduleName='"+fileName+"';";
				pstmt = conn.prepareStatement(sql);
				
				resultSet = pstmt.executeQuery();
				
				while(resultSet.next()){  
                    SnmpMibNode mibNode=new SnmpMibNode();  
                    mibNode.setNodeName(resultSet.getString("nodeName"));
                    mibNode.setOid(resultSet.getString("oid"));
                    mibNodeList.add(mibNode);  
                } 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	finally{
				JdbcConnection.free(resultSet, pstmt, conn);
			}
		}
		return mibNodeList;
	}
	
//	加载并解析SnmpObjectOid.properties文件
	public static String loadPresetFile(String mibName){
		Properties props = new Properties();
		String value = "";
		String filePath = "";
		String separator = "";
		
		try {
			separator = File.separator;
			String snmpobjectoid_filepath = separator+"webapps"+separator+"SinoITOM"+separator+"WEB-INF"+separator+"mibs"+separator+"SnmpObjectOid.properties";
			
			filePath = System.getProperty("catalina.home")+snmpobjectoid_filepath;
			
			// 使用InPutStream流读取properties文件
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			props.load(bufferedReader);
			// 获取key对应的value值
			value = props.getProperty(mibName);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return value;
		
	}
	

}
