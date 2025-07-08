package com.sino.snmp.utils;

import com.adventnet.snmp.beans.SnmpTarget;
import com.adventnet.snmp.mibs.MibNode;
import com.adventnet.snmp.mibs.MibOperations;
import com.adventnet.snmp.snmp2.SnmpOID;

public class GlobalVar {
	
//	所有的相关文件都在同一个固定的目录下
//	public static final String ROOTPATH = "C:/Users/Administrator/Desktop/mib/Cisco-Mib/V2/";
	public static final String ROOTPATH = "C:/Users/Administrator/Desktop/mib/Cisco-Mib/fail/";
//	public static final String ROOTPATH = "C:/Users/Administrator/Desktop/mib/Cisco-Mib/wait/";
//	public static final String ROOTPATH = "C:/Users/Administrator/Desktop/mib/Cisco-Mib/test/";
//	public static final String ROOTPATH = "E:/";
	public static final String SUFFIX = ".mib";
	public static final String 	SNMPOBJECTOID_FILEPATH = "/mibs/SnmpObjectOid.properties";
	
//	注释是以--开头的，注释中的东西不分析
	public static final String ANNOTATE = "--";
	
//	mib文件中有一个定义的开始语句
	public static final String BEGIN = "DEFINITIONS ::= BEGIN";
	
//	OID那一行中关键字
	public static final String OID_START="::={";
//	SEQUENCE
	public static final String SEQUENCE = "SEQUENCE{";
	
//	SEQUENCEru如果换行
	public static final String DEFINITION_SYMBOL = "::=";
	public static final String INTEGER_DEF = "::= INTEGER {";
	
//	mib文件中有结束语句
	public static final String END = "END";
//	思科v1类型的文件的结尾
	public static final String CISOCO_V1_SUFFIX = "V1SMI";
	
//imports这一块的关键字定义
	public static final String IMPORTS="IMPORTS";
	public static final String FROM="FROM";
	public static final String RUIJIE = "RUIJIE-";
	
	
	public static final String VARIATION = "VARIATION";
	
//	以下是节点定义中ruijieAclMIB MODULE-IDENTITY后者的一些定义，一般节点的位置不同，定义就不同
	public enum KeyWord{
		
		MODULE_IDENTITY("MODULE-IDENTITY"),
		OBJECT_IDENTIFIER("OBJECT IDENTIFIER"),
		OBJECT_TYPE("OBJECT-TYPE"),
		MODULE_COMPLIANCE("MODULE-COMPLIANCE"),
		OBJECT_GROUP("OBJECT-GROUP"),
		OBJECT_IDENTITY("OBJECT-IDENTITY"),
		NOTIFICATION_TYPE("NOTIFICATION-TYPE"),
		NOTIFICATION_GROUP("NOTIFICATION-GROUP"),
		AGENT_CAPABILITIES("AGENT-CAPABILITIES"),
		
		;
		
		private String des;

		private KeyWord(String des) {
			this.des = des;
		}

		public String getDes() {
			return des;
		}

	}
	
//	以下是节点的属性关键字
	public static final String SYNTAX="SYNTAX";
	public static final String ACCESS="ACCESS";
	public static final String STATUS="STATUS";
	public static final String DESCRIPTION="DESCRIPTION";
	public static final String INDEX="INDEX";
	public static final String DEFVAL="DEFVAL";
	public static final String ORGANIZATION="ORGANIZATION";
	public static final String REVISION="REVISION";
	public static final String LAST_UPDATED="LAST-UPDATED";
	public static final String CONTACT_INFO="CONTACT-INFO";
	public static final String UNITS="UNITS";
	
	public static void main(String[] args) throws Exception {
		
		String tempString ="FROM RUIJIE-SMI;";
		String filePrefix = "RUIJIE";
		System.out.println(!tempString.toLowerCase().contains(filePrefix+"-"));
//		System.out.println(tempString.toLowerCase().contains(filePrefix+"-"));
//		System.out.println(tempString.toLowerCase().endsWith("TC"));
	}
	
	
	private void getOid(){
		SnmpTarget target = new SnmpTarget();
		
		try {
			target.loadMibs("E:/hh3c-entity-ext.mib");
		} catch (Exception ex) {
			System.out.println(ex);
		}
		MibOperations mibOps = target.getMibOperations();
		SnmpOID rootOid = mibOps.getSnmpOID("hh3cEntityExtend");
		MibNode mibNode = mibOps.getMibNode(rootOid);
		
		System.out.println(mibNode+"-----------"+rootOid);
		
		System.exit(0);
	}
	


}
