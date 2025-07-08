package com.sino.snmp.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.sino.snmp.entity.SnmpMibNode;
import com.sino.utils.common.ItUtil;

public class SaveMibProperties {
	
	public static void main(String[] args) {
		Long nodeId1 = ItUtil.getCRC32("iso"+".1");
		Long nodeId2 = ItUtil.getCRC32("org"+".1.3");
		Long nodeId3 = ItUtil.getCRC32("dod"+".1.3.6");
		Long nodeId4 = ItUtil.getCRC32("internet"+".1.3.6.1");
		Long nodeId5 = ItUtil.getCRC32("mgmt"+".1.3.6.1.2");
		Long nodeId6 = ItUtil.getCRC32("mib-2"+".1.3.6.1.2.1");
		Long nodeId7 = ItUtil.getCRC32("private"+".1.3.6.1.4");
		Long nodeId8 = ItUtil.getCRC32("enterprises"+".1.3.6.1.4.1");
		Long nodeId9 = ItUtil.getCRC32("cisco"+".1.3.6.1.4.1.9");
		Long nodeId10 = ItUtil.getCRC32("ruijie"+".1.3.6.1.4.1.4881");
		
		System.out.println(nodeId1);
		System.out.println(nodeId2);
		System.out.println(nodeId3);
		System.out.println(nodeId4);
		System.out.println(nodeId5);
		System.out.println(nodeId6);
		System.out.println(nodeId7);
		System.out.println(nodeId8);
		System.out.println(nodeId9);
		System.out.println(nodeId10);
	}
	
	public static void saveMethod(){
		String filePath = "";
		String tempString = "";
		
		try {
//			filePath = System.getProperty("catalina.home").replace("\\", "/")+GlobalVar.SNMPOBJECTOID_FILEPATH;
			
			// 使用InPutStream流读取properties文件
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			while (((tempString = bufferedReader.readLine()) != null)) {
				String[] split = tempString.trim().split("=");
				SnmpMibNode mibNode = new SnmpMibNode();
				mibNode.setNodeName(split[0]);
				mibNode.setOid(split[1]);
				Long nodeId = ItUtil.getCRC32(split[0]+split[1]);
				mibNode.setNodeId(nodeId);
				System.out.println(mibNode.getNodeName()+"----"+mibNode.getNodeId());
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
