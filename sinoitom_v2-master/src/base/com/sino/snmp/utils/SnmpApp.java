package com.sino.snmp.utils;

import com.adventnet.snmp.beans.DataException;
import com.adventnet.snmp.beans.SnmpTarget;
import com.adventnet.snmp.mibs.MibModule;
import com.adventnet.snmp.mibs.MibNode;
import com.adventnet.snmp.mibs.MibOperations;
import com.adventnet.snmp.snmp2.SnmpAPI;
import com.adventnet.snmp.snmp2.SnmpOID;
import com.adventnet.snmp.snmp2.SnmpVar;
import com.sino.base.common.util.JdbcConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class SnmpApp {
	// RFC1213-MIB
	public static String sysDescr_OID = ".1.3.6.1.2.1.1.1.0";
	public static String sysObjectID_OID = ".1.3.6.1.2.1.1.2.0";
	public static String sysUpTime_OID = ".1.3.6.1.2.1.1.3.0";
	public static String sysContact_OID = ".1.3.6.1.2.1.1.4.0";
	public static String sysName_OID = ".1.3.6.1.2.1.1.5.0";
	public static String sysLocation_OID = ".1.3.6.1.2.1.1.6.0";
	public static String sysServices_OID = ".1.3.6.1.2.1.1.7.0";
	public static String enterprises = ".1.3.6.1.4.1.";
	public static String ifTable = ".1.3.6.1.2.1.2.2";
	// BRIDGE-MIB
	public static String dot1dTpFdbTable = ".1.3.6.1.2.1.17.4.3";
	public static String avgBusy5Sec = ".1.3.6.1.4.1.9.2.1.56.0";
	public static String avgBusy1Min = ".1.3.6.1.4.1.9.2.1.57.0";
	public static String avgBusy5Min = ".1.3.6.1.4.1.9.2.1.58.0";
	public static String ifInOctets  = ".1.3.6.1.2.1.2.2.1.10.";
	public static String ifOutOctets = ".1.3.6.1.2.1.2.2.1.16.";
	
	public SnmpApp() {
		
	}

	private static SnmpTarget SetSnmpTarget(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = new SnmpTarget();
		target.setTargetHost(nodeIp);
		if (snmpPort <= 0)
			snmpPort = 161;
		target.setTargetPort(snmpPort);
		target.setCommunity(community);
		target.setSnmpVersion(1);
		target.setTimeout(5);
		return target;
	}
	public static String getMibsDir(){
		  String fileSeparator = System.getProperty("file.separator");
		  String classPath = SnmpApp.class.getClassLoader().getResource("/").getPath() ;
		  String mibsDir  = "";
		  //windows下
		  if("\\".equals(fileSeparator)){   
			  mibsDir  = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"))+fileSeparator+ "mibs" + fileSeparator;
			  mibsDir = mibsDir.replace("/", "\\");
		  }
		  //linux下
		  if("/".equals(fileSeparator)){   
			  mibsDir  = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"))+fileSeparator+ "mibs" + fileSeparator;
			  mibsDir = mibsDir.replace("\\", "/");
		  }
		  
		//String mibsDir = System.getProperty("user.dir") + fileSeparator + "mibs" + fileSeparator;
		System.out.println("MibDir is: " + mibsDir);
		// /app/SinoBAS/tomcat/webapps/SinoBAS/mibs
		return mibsDir;
	}

	public static void main(String args[]) {
//		getSysDesc("10.1.1.253", 161, "cisco");
//		getSysName("10.1.1.253", 161, "cisco");
//		getEntID("10.1.1.253", 161, "cisco");
//		getEntOID("10.1.1.253", 161, "cisco");
//		getSysContact("10.1.1.253", 161, "cisco");
//		setSysContact("10.1.1.253", 161, "sinobridge", "lvxinghai");
//		getSysContact("10.1.1.253", 161, "cisco");
//		getAvgBusy5Sec("9.9.9.1", 161, "cisco");
//		Long ifindex = 14L;
//		getIfInOutOctets("10.10.1.1", ifindex, 161,"cisco");
//		String vendorName = "";
//		vendorName = getVendorName("10.1.1.253", 161, "cisco");
//		System.out.println("Vendor Name is: " + vendorName);
		boolean IF_ShutDown = true;
//		adminSwitchPort("10.1.1.253", 161, "sinobridge", "12", IF_ShutDown);
//		getPortAdminStatus("10.1.1.253", 161, "cisco", "12");
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		IF_ShutDown = true;
		adminSwitchPort("10.1.1.247", 161, "sinobridge", "4227658", IF_ShutDown);
		getPortAdminStatus("10.1.1.247", 161, "sinobridge", "4227658");
	}

	public static String getSysDesc(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysDescr_OID));
		SnmpVar sysDescr_GetResult = target.snmpGetVariable();
		String sysDescr = "";
		if (sysDescr_GetResult != null) {
			sysDescr = sysDescr_GetResult.toString();
			System.out.println("sysDescr is: " + sysDescr);
		}
		if (target != null)
			target.releaseResources();
		return sysDescr;
	}
	public static float getAvgBusy5Sec(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(avgBusy5Sec));
		SnmpVar snmpGetResult = target.snmpGetVariable();
		float snmpResult = 0.0f;
		try {
			snmpResult = Float.valueOf(snmpGetResult.toString()).floatValue() / 100;
		} catch (Exception ex) {
			System.out.println("snmpGetResult failed");
		}

		if (target != null)
			target.releaseResources();
		return snmpResult;
	}
	public static float getAvgBusy5Min(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(avgBusy5Min));
		SnmpVar snmpGetResult = target.snmpGetVariable();
		float snmpResult = 0.0f;
		try {
			snmpResult = Float.valueOf(snmpGetResult.toString()).floatValue() / 100;
			java.text.DecimalFormat df = new java.text.DecimalFormat("##%");
			System.out.println("avgBusy5(%) is: " + df.format(snmpResult));
		} catch (Exception ex) {
			System.out.println("snmpGetResult failed");
		}

		if (target != null)
			target.releaseResources();
		return snmpResult;
	}

	public static final String getSysName(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysName_OID));
		String sysName = "";
		SnmpVar sysName_GetResult = target.snmpGetVariable();
		if (sysName_GetResult != null) {
			sysName = sysName_GetResult.toString();
			System.out.println("sysName is: " + sysName);
		}
		if (target != null)
			target.releaseResources();
		return sysName;
	}

	public static void setSysContact(String nodeIp, int snmpPort,
			String community, String value) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		String MibsDir = getMibsDir();
		String mibFile = MibsDir + "RFC1213-MIB";
		try {
			target.loadMibs(mibFile);
		} catch (Exception ex) {
			System.out.println("loadMibs Error: " + ex.getMessage());
		}
		MibOperations mibOps = new MibOperations();
		if (mibOps != null && mibOps.getModuleSize() != 0) {
			target.setMibOperations(mibOps); // 很重要
		}
					
		try {
			target.setObjectID(sysContact_OID);
			target.snmpSet(value);
		} catch (DataException e) {
			System.out.println("SnmpSet SysContact " + e.getMessage());
			int errorCode = target.getErrorCode();
			System.out.println("The snmp set error=" + errorCode);
			if (target != null)
				target.releaseResources();
		}
		if (target != null)
			target.releaseResources();
	}

	public static final String getSysObjectID(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysObjectID_OID));
		String sysObjectID = "";
		SnmpVar sysObjectID_GetResult = target.snmpGetVariable();
		if (sysObjectID_GetResult != null) {
			sysObjectID = sysObjectID_GetResult.toString();
			System.out.println("sysObjectID is: " + sysObjectID);
		}
		if (target != null)
			target.releaseResources();
		return sysObjectID;
	}

	public static final int getEntID(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysObjectID_OID));
		String sysObjectID = "";
		int entID = 0;
		SnmpVar sysObjectID_GetResult = target.snmpGetVariable();
		if (sysObjectID_GetResult != null) {
			sysObjectID = sysObjectID_GetResult.toString();
			String sysObjectOID[] = sysObjectID.split("\\.");
			entID = Integer.parseInt(sysObjectOID[7]);
			System.out.println("entID is: " + entID);
		}
		if (target != null)
			target.releaseResources();
		return entID;
	}

	public static String getEntOID(String nodeIp, int snmpPort, String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysObjectID_OID));
		String sysObjectID = "";
		String entOID = "";
		SnmpVar sysObjectID_GetResult = target.snmpGetVariable();
		if (sysObjectID_GetResult != null) {
			sysObjectID = sysObjectID_GetResult.toString();
			String sysObjectOID[] = sysObjectID.split("\\.");
			entOID = enterprises + sysObjectOID[7];
			System.out.println("entOID is: " + entOID);
		}
		if (target != null)
			target.releaseResources();
		return entOID;
	}

	public static final String getSysUpTime(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysUpTime_OID));
		SnmpVar sysUpTime_GetResult = target.snmpGetVariable();
		String sysUpTime = "";
		if (sysUpTime_GetResult != null) {
			sysUpTime = sysUpTime_GetResult.toString();
			System.out.println("sysUpTime is: " + sysUpTime);
		}
		if (target != null)
			target.releaseResources();
		return sysUpTime;
	}

	public static final String getSysContact(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysContact_OID));
		SnmpVar sysContact_GetResult = target.snmpGetVariable();
		String sysContact = "";
		if (sysContact_GetResult != null) {
			sysContact = sysContact_GetResult.toString();
			System.out.println("sysContact is: " + sysContact);
		}
		if (target != null)
			target.releaseResources();
		return sysContact;
	}
	
	public static final Long getIfInOutOctets(String nodeIp, Long ifIndex,int snmpPort,String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(ifInOctets + ifIndex.toString()));
		SnmpVar ifInOctets_GetResult = target.snmpGetVariable();
		Long ifInOctets = 0L;
		
		if (ifInOctets_GetResult != null) {
			ifInOctets = Long.parseLong(ifInOctets_GetResult.toString());
			System.out.println("ifInOctets is: " + ifInOctets);
		}
		if (target != null)
			target.releaseResources();
		return ifInOctets;
	}

	public static final String getsysLocation(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);

		target.setSnmpOID(new SnmpOID(sysLocation_OID));
		SnmpVar sysLocation_GetResult = target.snmpGetVariable();
		String sysLocation = null;
		if (sysLocation_GetResult != null) {
			sysLocation = sysLocation_GetResult.toString();
			System.out.println("sysLocation is: " + sysLocation);
		}
		if (target != null)
			target.releaseResources();
		return sysLocation;
	}

	public static final String getsysServices(String nodeIp, int snmpPort,
			String community) {
		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, community);
		target.setSnmpOID(new SnmpOID(sysServices_OID));
		SnmpVar sysServices_GetResult = target.snmpGetVariable();
		String sysServices = "";
		if (sysServices_GetResult != null) {
			sysServices = sysServices_GetResult.toString();
			System.out.println("sysServices is: " + sysServices);
		}
		if (target != null)
			target.releaseResources();
		return sysServices;
	}

	public static String getVendorName(String nodeIp, int snmpPort,
			String community) {
		int entID = getEntID(nodeIp, snmpPort, community);
		String vendorName = "";
		JdbcConnection jdbcCon = new JdbcConnection();
		ResultSet rs = null;
		Connection conn;
		Statement stmt = null;
		try {
			conn = jdbcCon.getConnection();
			stmt = conn.createStatement();
			StringBuffer query = new StringBuffer();
			query.append(
					"select Vendor_Name from Snmp_Vendor where Vendor_EntID=")
					.append(entID);
			for (rs = stmt.executeQuery(query.toString()); rs.next();) {
				vendorName = rs.getString("Vendor_Name");
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {

			}
		}

		return vendorName;
	}

	public static void adminSwitchPort(String nodeIp, int snmpPort,
			String writeCommunity, String ifIndex, boolean ifShutdown) {
		MibOperations mibOps = new MibOperations();
		MibModule module;
		String MibsDir = getMibsDir();
		try {
			mibOps.loadMibModule(MibsDir + "RFC1213-MIB");
//		try {
//			mibOps.loadMibModule("d:\\RFC1213-MIB");
		} catch (Exception ex) {
			System.out.println("loadMibModule Error: " + ex.getMessage());
		}
		module = mibOps.getMibModule("RFC1213-MIB");
		System.out.println("Module Name is: " + module.toString());

		SnmpTarget target = new SnmpTarget();
		try {
			target.setTargetHost(nodeIp);
			if (snmpPort <= 0)
				snmpPort = 161;
			target.setTargetPort(snmpPort);
			target.setWriteCommunity(writeCommunity);
			target.setSnmpVersion(1);

			if (mibOps != null && mibOps.getModuleSize() != 0) {
				target.setMibOperations(mibOps);
				target.setObjectID((new StringBuilder())
						.append(".1.3.6.1.2.1.2.2.1.7.").append(ifIndex)
						.toString());
				if (ifShutdown)
					target.snmpSet("2");
				else
					target.snmpSet("1");

				System.out.println("The snmp set operation is successful!");
				if (target != null)
					target.releaseResources();
				return;
			} else {
				if (target != null)
					target.releaseResources();
				return;
			}
		} catch (Exception e) {
			int errorCode = target.getErrorCode();
			System.out.println("The snmp set error=" + errorCode);
			if (target != null)
				target.releaseResources();
			return;
		}
	}

	public static String getPortAdminStatus(String nodeIp, int snmpPort,
			String Community, String ifIndex) {

		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, Community);
		target.setSnmpOID(new SnmpOID(".1.3.6.1.2.1.2.2.1.7." + ifIndex));
		SnmpVar snmpGetResult = target.snmpGetVariable();
		String if_Status = "";
		if (snmpGetResult != null) {
			if_Status = snmpGetResult.toString();
			System.out.println("if_AdminStatus is: " + if_Status);
		}
		if (target != null)
			target.releaseResources();
		return if_Status;
	}
	
	public static String getPortOperStatus(String nodeIp, int snmpPort,
			String Community, String ifIndex) {

		SnmpTarget target = SetSnmpTarget(nodeIp, snmpPort, Community);
		target.setSnmpOID(new SnmpOID(".1.3.6.1.2.1.2.2.1.8." + ifIndex));
		SnmpVar snmpGetResult = target.snmpGetVariable();
		String if_Status = "";
		if (snmpGetResult != null) {
			if_Status = snmpGetResult.toString();
			System.out.println("if_OperStatus is: " + if_Status);
		}
		if (target != null)
			target.releaseResources();
		return if_Status;
	}

	public static final void getValueByTableOid(String mibFile, String nodeIp,
			int snmpPort, String community, String oid) {

		SnmpTarget target = new SnmpTarget();
		try {
			target.loadMibs(mibFile);
		} catch (Exception ex) {
			System.out.println("loadMibs Error: " + ex.getMessage());
		}
		MibOperations mibOps = target.getMibOperations();
		System.out.println("Mib Ops is: " + mibOps.getDatabaseName());
		SnmpOID tableOID = mibOps.getSnmpOID(oid); // get table OID

		System.out.println("SnmpOID Type is: " + tableOID.getType() + " "
				+ tableOID.getTypeString());
		System.out.println("TableOid is: " + tableOID.getVarObject());

		MibNode tableNode = mibOps.getMibNode(tableOID); // get table MIB node
		if (tableNode == null) { // could not get table MIB node
			System.err
					.println("Cannot find MIB node for table.  Correct MIB must be loaded");
			System.exit(1);
		} else
			System.out.println("Snmp Node is: " + tableNode.getDescription());

		Vector columns = tableNode.getTableItems();
		if ((columns == null) || (columns.size() == 0)) {
			System.err.println("Not a table.  No columns: " + oid);
			System.exit(1);
		}
		System.out.println("IfTable Columns are: " + columns.toString());

		// target.setLoadFromCompiledMibs(true);
		target.setTargetHost(nodeIp);
		target.setTargetPort(snmpPort);
		target.setCommunity(community);
		target.setSnmpVersion(1);
		target.setTimeout(3);
		while (columns.size() > 0) {
			SnmpOID firstOID = mibOps.getSnmpOID((String) columns.elementAt(0));
			MibNode firstNode = mibOps.getMibNode(firstOID);
			if ((firstNode.getAccess() != SnmpAPI.RONLY)
					&& (firstNode.getAccess() != SnmpAPI.RWRITE)
					&& (firstNode.getAccess() != SnmpAPI.RCREATE)) {
				System.err.println("Column inaccessible.  Drop: " + firstNode);
				columns.removeElementAt(0);
			} else
				break;
		}
		// create OID array from table columns
		String oids[] = new String[columns.size()];
		for (int i = 0; i < oids.length; i++)
			oids[i] = (String) columns.elementAt(i);

		target.setObjectIDList(oids);
		// get entire table by doing successive get nexts
		String result[][] = target.snmpGetAllList();

		if (result == null) {
			System.err.println("Request failed or timed out. \n"
					+ target.getErrorString());

		} else { // print the table
			System.out.println("Response received.  Table items:");
			StringBuffer sb = new StringBuffer();

			// first print the column names
			for (int i = 0; i < oids.length; i++)
				sb.append(oids[i] + " \t");
			sb.append("\n");
			System.out.println("Talbe Column Names are: \n" + sb.toString());
			// next pront each table item
			for (int j = 0; j < result.length; j++) { // for each row
				for (int i = 0; i < result[j].length; i++)
					// for each column
					sb.append(result[j][i] + " \t");
				sb.append("\n");
			}
			System.out.println(sb.toString());
		}
		if (target != null)
			target.releaseResources();
		return;

	}

}