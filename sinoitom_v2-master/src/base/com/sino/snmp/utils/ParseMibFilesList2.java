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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sino.snmp.entity.SnmpMibNode;
import com.sino.snmp.utils.GlobalVar.KeyWord;
import com.sino.snmp.utils.mib.JdbcConnection;
import com.sino.utils.common.ItUtil;

public class ParseMibFilesList2 {
	
//	文件的后缀，标识文件的类型，比如.mib
	private static String SUFFIX = "";
//	文件的前缀，标识不同厂商的文件，比如RUIJE、hh3c
	private static String PREFIX = "";
//	思科的文件名是moduelName-V1SMI，跟其他的不太一样
	private static String CISOCO_V1_SUFFIX = "";
//	所有选的multipartFile
	private static List<MultipartFile> mpfList = new  ArrayList<MultipartFile>();
	
//	参数说明：你所选择的文件的集合fileNameList
	public static void parseMethod(List<MultipartFile> fileList){
		
		mpfList = fileList ;
		
		for (MultipartFile mpf : fileList) {//fileName=hh3c-splat-devm.mib
			
			String fileName = mpf.getOriginalFilename().trim();
			
//			判断该文件是否已经解析过并存入库了
			boolean isExist = queryFileName(fileName.split("\\.")[0].trim());
			if(isExist){//isExist为true就是已经在库中了,跳过
				continue;
			}else{//没有入库就读取文件并解析
				
//				前缀和后缀一般读取一遍就行了
				int dotIndex = fileName.indexOf(".");
				SUFFIX = fileName.substring(dotIndex+1, fileName.length());//mib
				String substring = fileName.substring(0, dotIndex);
				
				String[] split = substring.split("-");
				PREFIX = split[0];//获取前缀:hh3c
				CISOCO_V1_SUFFIX = split[split.length-1];
				
//				读取文件并解析
				readMibFile(mpf,"");
				
			}
		}
	}

	//	拿到当前文件中所有的节点
	public static void readMibFile(MultipartFile mpf,String rootName){
		
//		--------------------解析构建mibNode涉及到的变量start---------------------------------------------------------
		
		InputStream inputStream = null;
		
//		 当前文件中引入的节点和节点所在的文件名
		 List<String> importObjectList = new ArrayList<String>();
		 
//		所求文件中引入的节点和该节点所在文件
		Map<String,List<String>> importsMap = new HashMap<String,List<String>>();
		 
//		 自定义的Integer的标识，里面有一个关键字会干扰解析,比如下面：
		 /*
		  * 	   LightStreamValidation ::= INTEGER {
					     valid(1),
					     invalid(2)
					     }
		  */
		 boolean integerFlag = false;
		 
//		 如果nodeName换行
		 StringBuilder nodeNameSb = new StringBuilder();
		 
//		 如果sequence换行
		 StringBuilder sequenceSb = new StringBuilder();
		 
//		 获取description内容
//		StringBuilder descripSb = new StringBuilder();
		
//		 读取description内容的标识
		 boolean descripFlag = false;
		 
//		 读取SEQUENCE的标识，里面有一个关键字会干扰解析
		 boolean sequenceFlag = false;
		 
//		 读取imports内容的标识
		 boolean flag = false;
		 
//		 这个文件对象的名字
		 String moduleName = "";
		 StringBuilder moduleNameSb = new StringBuilder();
		 
		 StringBuilder nodetypeSb = new StringBuilder();
		 
		 SnmpMibNode mib = new SnmpMibNode();
//		 当前文件中的所有节点
		 List<SnmpMibNode> mibList = new ArrayList<SnmpMibNode>();

//		 -----------------------解析构建mibNode end---------------------------------------------
		 
//		 ------------------------构造SnmpMibNode对象start----------------------------------------------
	    try {
	        inputStream = mpf.getInputStream();
	    	List<String> allRowList = IOUtils.readLines(inputStream);
	    	if(allRowList!=null && allRowList.size()>0){
	    		for (String tempString : allRowList) {
//	        	注释忽略和空字符串，不解析
	        	tempString = tempString.replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
	        	if(StringUtils.isBlank(tempString) || tempString.startsWith(GlobalVar.ANNOTATE)){
	        		continue;
	        	}
	        	
	        	if(StringUtils.isBlank(moduleName)){
		        	moduleNameSb.append(tempString);
		        	if(tempString.startsWith(GlobalVar.BEGIN)){
		        		moduleName= moduleNameSb.toString().replace(GlobalVar.BEGIN, "").trim();
		        		moduleNameSb = new StringBuilder();
		        		continue;
		        	}
		        	
//		        	得到moduleName
		        	if(tempString.contains(GlobalVar.BEGIN)){
		        		moduleName = tempString.replace(GlobalVar.BEGIN, "").trim();
		        		continue;
		        	}
	        	}
	        	
	        	if(tempString.contains(GlobalVar.ANNOTATE)){
	        		int indexOf = tempString.indexOf(GlobalVar.ANNOTATE);
	        		tempString = tempString.substring(0, indexOf).trim();
	        	}
	        	
//	        	tempString.equals(GlobalVar.END):V2文件夹下面有一个CISCO-ATM-CELL-LAYER-CAPABILITY.my文件
//	        	这个文件中一个同一个内容重复了3遍，也就是end重复了三遍,
	        	if(tempString.equals(GlobalVar.END)){
	        		inputStream.close();
	        		break;
	        	}
	        	
//	        	获取imports的信息
	        	if(flag){
	        		
	        		if(tempString.contains(";")){//import部分结束
        				flag = false;
        			}
	        		
	        		if(tempString.contains(GlobalVar.FROM) ){//例如 tempString = FROM HH3C-OID-MIB、FROM RUIJIE-SMI
	        			
//	        			去掉FROM
	        			String importMibStr = tempString.replaceAll(GlobalVar.FROM, "").trim();
	        			if(importObjectList!=null && importObjectList.size()>0 && StringUtils.isNotBlank(importMibStr)){
	        				if(importMibStr.contains(";")){
	        					importMibStr = importMibStr.replace(";", "");
	        				}
	        				importsMap.put(importMibStr, importObjectList);
	        				importObjectList = new ArrayList<String>();
	        				
	        				if(tempString.contains(";")){
	        					importsMap = new HashMap<String, List<String>>();
	        					continue;
	        				}
	        			}
	        			
	        		}else{//拿到对象名
	        			String[] split = tempString.split(",");
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
	        		
	        		if(tempString.contains(GlobalVar.INTEGER_DEF)){
	        			integerFlag = true;
	        		}
	        		
		        	if(tempString.contains(GlobalVar.DESCRIPTION)){//当第一次遇到包含DESCRIPTION的时候，说明description开始了
		        		descripFlag = true;
		        	}
		        	/*
		        	 * ExpErrorEntry ::= SEQUENCE {
		        	 * 或者
		        	 * ExpErrorEntry ::= 
		        	 * 		SEQUENCE {
		        	 */
		        	
		        	if(tempString.replaceAll("\\s+", "").contains(GlobalVar.SEQUENCE)){
	        			sequenceFlag = true;
	        		}
	        		
	        		if(sequenceFlag){
	        			sequenceSb.append(tempString);
	        		}
	        		
	        		if(!descripFlag && !sequenceFlag && !integerFlag && StringUtils.isNotBlank(moduleName)){//descripFlag是true说明在description范围，那么可能包含一些关键字，所以当不在description范围的时候解析
//	        			有一种变异类型：VARIATION catmIntfPvcDownTrap  -- TRAP-TYPE，跟其他不一样，单独处理
	        			String nodeName = mib.getNodeName();
	        			if(tempString.startsWith(GlobalVar.VARIATION)){
	        				mib.setNodeType(GlobalVar.VARIATION);
	        				mib.setNodeName(tempString.replace(GlobalVar.VARIATION, "").trim());
//	        				continue;
	        			}else if(StringUtils.isBlank(nodeName)){
	        				
	        				if(StringUtils.isNotBlank(nodetypeSb.toString())){
	        					nodetypeSb.append(tempString);
	        				}
	        				
	        			/*
	        				 *使用nodeNameSb针对以下两种情况
	        						   rptrBasicPackage
	        					   OBJECT IDENTIFIER ::= { snmpDot3RptrMgt 1 }
	        				  			或者
	        				 snmpFddiMACChipSets   -- Chips primarily responsible
	        				               -- for implementing the
	        				                 -- MAC function.
	        				        OBJECT IDENTIFIER ::= { snmpFddiChipSets 2 }
	        			*/
	        				if(tempString.split(" ").length==1 && !tempString.trim().equals("}")){
	        					nodeNameSb.append(tempString+" ");
	        					continue;//只有一行
	        				}
	        				String rowStr = "";
	        				String nodeNameSbStr = nodeNameSb.toString();

	        				if(StringUtils.isNotBlank(nodeNameSbStr)){
	        					rowStr = nodeNameSb.append(tempString).toString();
	        				}else{
	        					rowStr = tempString;
	        				}
	        				
	        				nodeNameSb = new StringBuilder();
	        				for(KeyWord keyWord : GlobalVar.KeyWord.values()){
	        					String des = keyWord.getDes();
	        					
	        					String str = rowStr.replaceAll(des, "").trim();
	        					if(rowStr.contains(des) && StringUtils.isNotBlank(str)){
	        						if(rowStr.contains(GlobalVar.DEFINITION_SYMBOL+" "+des)){
			        					break;
			        				}
	        						
	        						if(str.contains(",")){
	        							continue;
	        						}
//	        						一共有以下几种情况
//	        						DataCollectionSubtree ::= OBJECT IDENTIFIER，这个不要，怎么区分呢
//	        						acctngCompliances OBJECT IDENTIFIER ::= { acctngConformance 2 }
	        						
//	        						cvH320CallHistory        OBJECT IDENTIFIER ::=
//	        					        { ciscoH320DialControlMIBObjects 1 }这个要想办法把两行给拼起来
	        						
//	        						acctngSelectionTable OBJECT-TYPE
	        						
	        						if(rowStr.replaceAll("\\s+", " ").endsWith(des+" "+GlobalVar.DEFINITION_SYMBOL)){
	        							nodetypeSb.append(rowStr+" ");
	        						}
	        						
//	        						如果在结点的描述中出现了这个关键词要区分一下
	        						int indexOf = rowStr.indexOf(des);
	        						String substring = rowStr.substring(0, indexOf).trim();
	        						mib.setNodeType(des);
	        						mib.setNodeName(substring);
	        						break;
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
			        	
//			        	dot3MauType     OBJECT IDENTIFIER ::= { mib-2 snmpDot3MauMgt(26) 4 }
//			        	::= { dot3MauType 2 }
			        	
			        	if(nodetypeSb.toString().replaceAll(" ", "").trim().contains(GlobalVar.OID_START)){
			        		tempString = nodetypeSb.toString();
			        		nodetypeSb = new StringBuilder();
			        	}
			        	
			        	if(tempString.replaceAll(" ", "").trim().contains(GlobalVar.OID_START)){
			        		
			        		int index1 = tempString.indexOf("{");
			        		int index2 = tempString.indexOf("}");
			        		
			        		String substring = tempString.substring(index1+1, index2).trim();
			        		
			        		 String[] split2 = substring.replaceAll("\\s+", " ").trim().split(" ");
			        		 String oid = "";
			        		 if(split2.length>0){
			        			 for (String item : split2) {
									if(item.contains("(") && item.contains(")")){
										int first = item.indexOf("(");
										int end = item.indexOf(")");
										item = item.substring(first+1, end).trim();
									}
									oid += "."+item;
								}
			        			oid = oid.substring(1);
			        			mib.setOid(oid);
			        			int lastIndexOf = oid.lastIndexOf(".");
			        			mib.setParent(split2[0].trim());
			        			mib.setNodeNo(Integer.parseInt(oid.substring(lastIndexOf+1,oid.length())));
								
					        	if(mib!=null){
////			        			赋值moduleName
				        			mib.setModuleName(moduleName);
				        			mibList.add(mib);
				        			mib = new SnmpMibNode();
				        		}
			        		 }
			        	}
	        	   }
	        		
	        		if(integerFlag && tempString.endsWith("}")){
	        			integerFlag = false;
	        			continue;
	        		}
	        		
		        	if(descripFlag && tempString.endsWith("\"")){//当第一次遇到以"结尾的时候，说明description结束了
		        		descripFlag = false;
		        		continue;
		        	}
		        	
		        	if(sequenceFlag && sequenceSb.toString().endsWith("}")){
		        		sequenceFlag = false;
		        		continue;
		        	}
	        	}
	        } 
//	        --------------------------构造SnmpMibNode对象 end------------------------------------
	    		inputStream.close();
	    	}
	    } catch (IOException e) {
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
	    
//	    for (SnmpMibNode SnmpMibNode : mibList) {
//			System.out.println(SnmpMibNode);
//		}
	    
//	    ----------------------这里给节点寻找并解析oid start--------------------
	    /*
	     * 根节点的oid的情况有以下几种：
	     * 1、标准的oid--已经有定义在一个properties文件中
	     * 2、在数据库中已经存了，直接查询数据库进行赋值
	     * 3、引入的另一个文件的，那么读取该文件
	     * 4、oid已经给了，就是已经解析好了的
	     */
	    for (SnmpMibNode mibNode : mibList) {
	    	
//	    	如果一个结点的oid已经解析过了那么就跳过
	    	Integer oidIsParse = mibNode.getFlag();
	    	if(oidIsParse!=null && oidIsParse==1){
	    		continue;
	    	}
	    	
	    	String oid = mibNode.getOid();
	    	String nodeName = mibNode.getNodeName();
	    	
//			如果oid不是空的且全是数字，那么说明已经解析 --那么直接去给小弟赋值oid
	    	String regex=".*[a-zA-Z]+.*";
	    	 Matcher m1=Pattern.compile(regex).matcher(oid);
	    	 if(StringUtils.isNotBlank(oid) && !m1.matches()){
//					该节点的oid已经是解析好的了，那么flag==1
					mibNode.setFlag(1);
//					查询该节点的父节点是否在数据库中，如果在，查询并赋值parentId
					Long parentId = queryParentId(oid);
					if(parentId!=null){
						mibNode.setParentId(parentId);
					}//没在数据库中就不管了
					
					if(StringUtils.isNotBlank(nodeName)){
						putOid(mibList, nodeName, oid);
					}
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
	    	
//	    	2.1、看引入的该节点是否已经在数据库中
	    	rootParentOid = queryOid(rootParentName);
			if(StringUtils.isNotBlank(rootParentOid)){
				putOid(mibList, rootParentName, rootParentOid);
				continue;
			}
//	    	2、看是不是引入的私有的
	    	if(importsMap!=null && importsMap.size()>0){
	    		for(String key : importsMap.keySet()){//key:RUIJIE-SMI/HH3C-OID-MIB
	    			List<String> importMibList = importsMap.get(key);//importMibList:[hh3clswCommon, hh3cRhw]
					for (String importMibName : importMibList) {
						
						if(rootParentName.equals(importMibName)){//找到rootParent所在的文件
							
//							2.2、如果没有在数据库中，那么看该文件我选了没有，如果选了，读取该文件
							for (MultipartFile file : mpfList) {
								String filename2 = file.getOriginalFilename();
								if(filename2.contains(key.toUpperCase())){//如果该imports的文件被选择了，那么读取该文件
									
									readMibFile(file,importMibName);
//									break;
								}
							}
						}
					}
	    		}
	    		continue;
	    	}
	    	
//	    	3、如果以上情况都不是，那么说明oid解析不了，但是还是得赋值nodeId等值以便存入库中
	    	putOid(mibList, rootParentName, mibNode.getOid());
	    	
		}
//	    System.out.println(fileName+"----"+mibList.size());
//	    for (SnmpMibNode SnmpMibNode : mibList) {
//			System.out.println(SnmpMibNode);
//		}
	    
//		保存
	    if(mibList!=null && mibList.size()>0){
	    	saveMibNode(mibList);
	    }
//	    	----------------------------给节点寻找并解析oid end----------------------------------------
	}
	
//	下面这个方法就是给小弟赋值oid，list是所有的节点，parentName就是父节点的名称，parentOid是父节点的oid
//	过程就是找到父节点的oid，然后给小弟赋值
	public static void putOid(List<SnmpMibNode> list,String parentName,String parentOid) {
		
		long parentId = ItUtil.getCRC32(parentName+parentOid);//该节点的nodeId
		
		for (SnmpMibNode mib : list) {
			
			if(mib.getNodeId()!=null){
				continue;
			}
			
			if(StringUtils.isNotBlank(mib.getNodeName()) && mib.getNodeName().equals("null")){
				long nodeId = ItUtil.getCRC32(mib.getNodeName()+mib.getOid());//该节点的nodeId
				mib.setNodeId(nodeId);
				mib.setFlag(1);
				continue;
			}
			
			String parent = mib.getParent();
			String nodeName = mib.getNodeName();
			String regex=".*[a-zA-Z]+.*";
			
			if(StringUtils.isNotBlank(nodeName) &&  nodeName.equals(parentName)){//如果父节点这个对象在当前文件中，那么就给该对象赋值nodeId，不在的话就往下走
				mib.setNodeId(parentId);//赋值nodeId
				if(mib.getFlag()==null || mib.getFlag()==0){
//					赋值flag属性
				    Matcher m1=Pattern.compile(regex).matcher(parentOid);
					
//					如果oid不是空的且全是数字，那么说明已经解析 
					if(StringUtils.isNotBlank(parentOid) && !m1.matches()){
						mib.setFlag(1);
//						System.out.println(mib);
					}else{//否则说明没有解析
						mib.setFlag(0);
					}
				}
				
				continue;
			}
			
			if(parent.equals(parentName)){//该节点的小弟
//				如果父节点的oid已经解析，那么就赋值oid，如果父节点oid没有解析，那么oid保持原状
				 Matcher m1=Pattern.compile(regex).matcher(parentOid);
//				如果parentOid不是空的且全是数字，那么说明已经解析 
				if(StringUtils.isNotBlank(parentOid) && !m1.matches()){
					
					mib.setOid(mib.getOid().replace(parent, parentOid));
					mib.setFlag(1);
				}else{//否则说明没有解析
					mib.setFlag(0);
				}
				
				mib.setParentId(parentId);
				long nodeId = ItUtil.getCRC32(mib.getNodeName()+mib.getOid());
				mib.setNodeId(nodeId);
				
//				System.out.println(mib);
//				然后以当前小弟为父节点给他的子节点赋值
				if(StringUtils.isNotBlank(mib.getNodeName()) && StringUtils.isNotBlank(mib.getOid())){
					putOid(list,mib.getNodeName(),mib.getOid());
				}
			}
		}
	}
	
//	如果当前节点的父节点还在当前文件中就继续找他的父节点，当在当前文件中找不到他的父节点了那么就返回
	public static String getRootParent(List<SnmpMibNode> mibList,String parentName){
		
		String findStr = parentName;
		
		for(SnmpMibNode midNode:mibList){
    		String nodeName = midNode.getNodeName();
    		try {
				if (nodeName.equals(parentName)) {
					String parent = midNode.getParent();
					findStr = getRootParent(mibList, parent);
				}
			} catch (Exception e) {
				System.out.println(midNode);
				System.out.println(findStr);
			}
    	}
		
		return findStr;
	}
	
//	保存
	public static void saveMibNode(List<SnmpMibNode> mibList){
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		PreparedStatement pstmt = null;
		if(conn!=null ){
			try {
				int count = ifSave(mibList.get(0).getModuleName());
				System.out.println(mibList.get(0).getModuleName()+"---"+mibList.size());
				if(count==mibList.size()){
					return;
				}
				
				for (SnmpMibNode SnmpMibNode : mibList) {
					System.out.println(SnmpMibNode);
				}
				
				for (SnmpMibNode mibNode : mibList) {
					if(mibNode.getNodeId()!=null){
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
						 if(mibNode.getParentId()!=null){
							 pstmt.setLong(12, mibNode.getParentId());
						 }else{
							 pstmt.setLong(12, 0);
						 }
						 pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	finally{
				JdbcConnection.free(null, pstmt, conn);
			}
		}
	}
	
//	根据nodeName查询oid
	public static String queryOid(String rootParentName){
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String oid = "";

		if(conn!=null){
			try {
				String sql = "SELECT oid FROM Snmp_MibNode where nodeName = '"+rootParentName+"';";
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
//	根据自己的oid查询parent的id
	public static Long queryParentId(String oid){
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		Long parentId = null;
		
		if(conn!=null){
			try {
				
				int lastIndexOf = oid.lastIndexOf(".");
				String parentOid = oid.substring(0, lastIndexOf);
				
				String sql = "SELECT parentId FROM Snmp_MibNode where oid = '"+parentOid+"';";
				pstmt = conn.prepareStatement(sql);
				
				resultSet = pstmt.executeQuery();
				
				while(resultSet.next()){
					parentId = resultSet.getLong(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	finally{
				JdbcConnection.free(resultSet, pstmt, conn);
			}
		}
		return parentId;
	}
//	查询某个文件是否已经被解析入库
	public static boolean queryFileName(String fileName){//hh3c-oid
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
				
				if(CISOCO_V1_SUFFIX.equals(GlobalVar.CISOCO_V1_SUFFIX)){
					fileName = fileName.replaceAll("-"+CISOCO_V1_SUFFIX, "");
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
	
//	查询某个文件是否已经被解析入库
	public static int ifSave(String fileName){//hh3c-oid
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = jdbcConnection.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		int count = 0;
		
		if(conn!=null){
			try {
				
				if(PREFIX.toLowerCase().equals("hh3c")){
					fileName = fileName.toUpperCase()+"-MIB";
				}
				
				if(CISOCO_V1_SUFFIX.equals(GlobalVar.CISOCO_V1_SUFFIX)){
					fileName = fileName.replaceAll("-"+CISOCO_V1_SUFFIX, "");
				}
				
				String sql = "SELECT count(*) FROM Snmp_MibNode where moduleName='"+fileName+"';";
				pstmt = conn.prepareStatement(sql);
				
				resultSet = pstmt.executeQuery();
				
				while(resultSet.next()){
					count = resultSet.getInt(1);
	            }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	finally{
				JdbcConnection.free(resultSet, pstmt, conn);
			}
		}
		return count;
	}
	
//	加载并解析SnmpObjectOid.properties文件
	public static String loadPresetFile(String mibName){
		Properties props = new Properties();
		String value = "";
		String filePath = "";
		String separator = "";
		
		try {
			separator = File.separator;
			String snmpobjectoid_filepath = separator+"webapps"+separator+"SinoACS"+separator+"WEB-INF"+separator+"mibs"+separator+"SnmpObjectOid.properties";
			
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

}
