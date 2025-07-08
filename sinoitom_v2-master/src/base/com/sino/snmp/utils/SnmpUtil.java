package com.sino.snmp.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Properties;

import com.adventnet.snmp.beans.SnmpTarget;

import smartlink.discovery.util.DiscoveryUtil;
import smartlink.utils.DeviceSnmpLoad;

public abstract class SnmpUtil {

	public SnmpUtil()
	{
		
	}
	public static void main(String[] args) {
		
	}
	public final static Hashtable getInterfaceSnmpTraffic(String deviceIp, int port, String community, Hashtable interfaces)
	{
		System.out.println("我进来了："+deviceIp+"   "+community+"   "+port);
		Hashtable dataHash = new Hashtable();
        //int vendorId = DeviceSnmpLoad.getInstance().getVendorID(deviceIp, community, port);
		int vendorId =-1;
        if(vendorId <= 0)
        {
        	System.out.println("我进来了1：");
        	System.out.println("Device---Ip=" + deviceIp + " snmp error"); //lfc debug delete
            String ifInOct = "0";
            String ifInUcastPkt = "0";
            String ifInNUcastPkt = "0";
            String ifInDiscard = "0";
            String ifInError = "0";

            String ifOutOct = "0";
            String ifOutUcastPkt = "0";
            String ifOutNUcastPkt = "0";
            String ifOutDiscard = "0";
            String ifOutError = "0";

            SnmpTarget target = new SnmpTarget();
            target.setTargetHost(deviceIp);
            if(port <= 0)
                port = 161;
            target.setTargetPort(port);
            target.setCommunity(community);
            target.setSnmpVersion(1);
            
            for(Enumeration e = interfaces.keys(); e.hasMoreElements();)
            {
            	System.out.println("我进来了2：");
            	String ifaceId = e.nextElement().toString();
            	String ifIndex = interfaces.get(ifaceId).toString();
		        if(ifIndex != null && interfaces.contains(ifIndex))
                {
		        		String oids[] = new String[10];
		        	
		            	oids[0] = ".1.3.6.1.2.1.2.2.1.10";
		            	oids[1] = ".1.3.6.1.2.1.2.2.1.11";
		            	oids[2] = ".1.3.6.1.2.1.2.2.1.12";
		            	oids[3] = ".1.3.6.1.2.1.2.2.1.13";
		            	oids[4] = ".1.3.6.1.2.1.2.2.1.14";
		            	oids[5] = ".1.3.6.1.2.1.2.2.1.16";
		            	oids[6] = ".1.3.6.1.2.1.2.2.1.17";
		            	oids[7] = ".1.3.6.1.2.1.2.2.1.18";
		            	oids[8] = ".1.3.6.1.2.1.2.2.1.19";
		            	oids[9] = ".1.3.6.1.2.1.2.2.1.20";
		
		            	for(int k = 0; k < 10; k++)
		            	{
		            		oids[k] += "." + ifIndex;
		            	}
		            	
				        target.setObjectIDList(oids);
				        String result[] = target.snmpGetList();

				        if(result != null)
				        {
				        	if(result[0] != null && !result[0].equalsIgnoreCase("NULL"))
				        		ifInOct = result[0].trim();
				        	if(result[1] != null && !result[1].equalsIgnoreCase("NULL"))
				        		ifInUcastPkt = result[1].trim();
				        	if(result[2] != null && !result[2].equalsIgnoreCase("NULL"))
				        		ifInNUcastPkt = result[2].trim();
				        	if(result[3] != null && !result[3].equalsIgnoreCase("NULL"))
				        		ifInDiscard = result[3].trim();
				        	if(result[4] != null && !result[4].equalsIgnoreCase("NULL"))
				        		ifInError = result[4].trim();
				        	if(result[5] != null && !result[5].equalsIgnoreCase("NULL"))
				        		ifOutOct = result[5].trim();
				        	if(result[6] != null && !result[6].equalsIgnoreCase("NULL"))
				        		ifOutUcastPkt = result[6].trim();
				        	if(result[7] != null && !result[7].equalsIgnoreCase("NULL"))
				        		ifOutNUcastPkt = result[7].trim();
				        	if(result[8] != null && !result[8].equalsIgnoreCase("NULL"))
				        		ifOutDiscard = result[8].trim();
				        	if(result[9] != null && !result[9].equalsIgnoreCase("NULL"))
				        		ifOutError = result[9].trim();
				        }	
				        Properties dataProps = new Properties();
				        dataProps.put("ifInOct", ifInOct);
				        dataProps.put("ifInUcastPkt", ifInUcastPkt);
				        dataProps.put("ifInNUcastPkt", ifInNUcastPkt);
				        dataProps.put("ifInDiscard", ifInDiscard);
				        dataProps.put("ifInError", ifInError);
				        dataProps.put("ifOutOct", ifOutOct);
				        dataProps.put("ifOutUcastPkt", ifOutUcastPkt);
				        dataProps.put("ifOutNUcastPkt", ifOutNUcastPkt);
				        dataProps.put("ifOutDiscard", ifOutDiscard);
				        dataProps.put("ifOutError", ifOutError);
				        dataHash.put(ifaceId, dataProps);
                }
            }
	        if(target != null)
		        target.releaseResources();
        }
		
		return dataHash;
	}
	
	public final static Hashtable getDevicePerformance(Hashtable devices)
	{
		System.out.println("我进来了");
		Hashtable dataHash = new Hashtable();
		
		if(devices != null && devices.size() > 0)
		{
			for(Enumeration e = devices.keys(); e.hasMoreElements();)
			{
				String deviceIp = e.nextElement().toString();
				Properties deviceProps = (Properties) devices.get(deviceIp);
				int port = 161;
				if(deviceProps.get("snmpport") != null)
				{
					port = Integer.parseInt(deviceProps.get("snmpport").toString());
				}
				String community = "public";
				if(deviceProps.get("community") != null)
				{
					community = deviceProps.get("community").toString();
				}
				String vendorName = "Cisco";
				if(deviceProps.get("vendorname") != null)
				{
					vendorName = deviceProps.get("vendorname").toString();
				}
						
				if(deviceProps.get("oids") != null)
				{
					String[] oids = (String[]) deviceProps.get("oids");
					
					boolean isValid = true;
					if(oids != null)
					{
						for(int j = 0; j < oids.length; j++)
						{
			            	if(oids[j] == null || oids[j].length() == 0)
			            	{
			            		isValid = false;
			            		break;
			            	}
						}
						if(isValid)
						{
				            SnmpTarget target = new SnmpTarget();
				            target.setTargetHost(deviceIp);
				            if(port <= 0)
				                port = 161;
				            target.setTargetPort(port);
				            target.setCommunity(community);
				            target.setSnmpVersion(1);
							
					        target.setObjectIDList(oids);
					        String result[] = target.snmpGetList();
				            if(result == null)
				            {
				            	System.out.println("没有数据："+result);
				                String cpu5secondUtil = "0";
				                String memprocessusedSize = "0";
				                String memprocessfreeSize = "1";
				                String memiousedSize = "0";
				                String memiofreeSize = "1";
				            	
				                Properties devperfProps = new Properties();
				                devperfProps.put("cpu5secondUtil", cpu5secondUtil);
				                devperfProps.put("memprocessusedSize", memprocessusedSize);
				                devperfProps.put("memprocessfreeSize", memprocessfreeSize);
				                devperfProps.put("memiousedSize", memiousedSize);
				                devperfProps.put("memiofreeSize", memiofreeSize);
				                dataHash.put(deviceIp, devperfProps);
				            } else
				            {
				            	System.out.println("有数据："+result);
				                String cpu5secondUtil = "0";
				                String memprocessusedSize = "0";
				                String memprocessfreeSize = "1";
				                String memiousedSize = "0";
				                String memiofreeSize = "1";

				            	String memtotalStr = "0";
				            	String memusedStr = "0";
				            	String memfreeStr = "0";
				            	String mempercentStr = "0";
				            	
				            	if(vendorName.equalsIgnoreCase("cisco"))
				            	{
						        	for(int i = 0; i < oids.length; i++)
						            {
				                          if(result[i] != null && !result[i].equalsIgnoreCase("NULL"))
				                          {
				                        	  if(i == 0)
				                        		  cpu5secondUtil = result[i].trim();
				                        	  if(i == 1)
				                        		  memprocessusedSize = result[i].trim();
				                        	  if(i == 2)
				                        		  memprocessfreeSize = result[i].trim();
				                        	  if(i == 3)
				                        		  memiousedSize = result[i].trim();
				                        	  if(i == 4)
				                        		  memiofreeSize = result[i].trim();
				                          }
						            }
				            	} else
				            	if(vendorName.equalsIgnoreCase("H3C") || vendorName.equalsIgnoreCase("Huawei"))
				            	{
						        	for(int i = 0; i < oids.length; i++)
						            {
				                          if(result[i] != null && !result[i].equalsIgnoreCase("NULL"))
				                          {
				                        	  if(i == 0)
				                        		  cpu5secondUtil = result[i].trim();
				                        	  if(i == 1)
				                        		  memtotalStr = result[i].trim();
				                        	  if(i == 2)
				                        		  memtotalStr = result[i].trim();
				                        	  if(i == 3)
				                        		  memfreeStr = result[i].trim();
				                        	  if(i == 4)
				                        		  mempercentStr = result[i].trim();
				                          }
						            }
						        	long totalmem = Long.parseLong(memtotalStr);
						        	long memfree = Long.parseLong(memfreeStr);
						        	long mempercent = Long.parseLong(mempercentStr);
						        	if(memfree > 0)
						        	{
						        		memprocessusedSize = String.valueOf(memfree);
						        		memprocessfreeSize = String.valueOf(totalmem - memfree);
						        	}
						        	else
						        		memprocessfreeSize = "1";
				            	}
				                Properties devperfProps = new Properties();
				                devperfProps.put("cpu5secondUtil", cpu5secondUtil);
				                devperfProps.put("memprocessusedSize", memprocessusedSize);
				                devperfProps.put("memprocessfreeSize", memprocessfreeSize);
				                devperfProps.put("memiousedSize", memiousedSize);
				                devperfProps.put("memiofreeSize", memiofreeSize);
				                dataHash.put(deviceIp, devperfProps);
				            }
				            if(target != null)
				            	target.releaseResources();
						} else
						{
							System.out.println("没有数据：");
			                String cpu5secondUtil = "0";
			                String memprocessusedSize = "0";
			                String memprocessfreeSize = "1";
			                String memiousedSize = "0";
			                String memiofreeSize = "1";
			            	
			                Properties devperfProps = new Properties();
			                devperfProps.put("cpu5secondUtil", cpu5secondUtil);
			                devperfProps.put("memprocessusedSize", memprocessusedSize);
			                devperfProps.put("memprocessfreeSize", memprocessfreeSize);
			                devperfProps.put("memiousedSize", memiousedSize);
			                devperfProps.put("memiofreeSize", memiofreeSize);
			                dataHash.put(deviceIp, devperfProps);
						}
					}
				}
			}
		}
		return dataHash;
	}
	
	  
		public static SnmpTarget GetV1SnmpParameter(SnmpTarget snmpTarget,String host,String community) {
			snmpTarget.setTargetHost(host);
			snmpTarget.setCommunity(community);
			snmpTarget.setTargetPort(Constants.snmpport);
			snmpTarget.setTimeout(Constants.timeout); //缺省为3秒
			snmpTarget.setRetries(Constants.snmpretry); //缺省尝试一次 
			snmpTarget.setSnmpVersion(snmpTarget.VERSION1); //缺省为 版本0（0：V1；1：V2c；3：V3）
			return snmpTarget;
		}
		
		
		public static SnmpTarget GetV2cSnmpParameter(SnmpTarget snmpTarget,String host,String community) {
			snmpTarget.setTargetHost(host);
			snmpTarget.setCommunity(community);
			snmpTarget.setTargetPort(Constants.snmpport);
			snmpTarget.setTimeout(Constants.timeout); //缺省为3秒
			snmpTarget.setRetries(Constants.snmpretry); //缺省尝试一次 
			snmpTarget.setSnmpVersion(snmpTarget.VERSION2C); //缺省为 版本0（0：V1；1：V2c；3：V3）
			return snmpTarget;
		}
		
		public static SnmpTarget GetV3SnmpParameter(Properties props,SnmpTarget snmpTarget,String host) {
			
			snmpTarget.setTargetHost(host);
//			snmpTarget.setCommunity(community);
			snmpTarget.setTargetPort(Constants.snmpport);
			snmpTarget.setTimeout(Constants.timeout); //缺省为3秒
			snmpTarget.setRetries(Constants.snmpretry); //缺省尝试一次 
			snmpTarget.setSnmpVersion(snmpTarget.VERSION3); //缺省为 版本0（0：V1；1：V2c；3：V3）
			
			String userName=props.getProperty("userName").toString();
			snmpTarget.setPrincipal(userName);
			
			int level=Integer.parseInt(props.getProperty("security_level").toString());
			snmpTarget.setSecurityLevel((byte)level);
			
			if(level==1){
				String auth_protocol=props.getProperty("auth_protocol").toString();
				String passwd=props.getProperty("passwd").toString();
				if(auth_protocol.equals("MD5")){
					snmpTarget.setAuthProtocol(snmpTarget.MD5_AUTH);
				}else if(auth_protocol.equals("SHA")){
					snmpTarget.setAuthProtocol(snmpTarget.SHA_AUTH);
				}
				snmpTarget.setAuthPassword(passwd);
				
			}else if(level==3){
				String auth_protocol=props.getProperty("auth_protocol").toString();
				String passwd=props.getProperty("passwd").toString();
				if(auth_protocol.equals("MD5")){
					snmpTarget.setAuthProtocol(snmpTarget.MD5_AUTH);
				}else if(auth_protocol.equals("SHA")){
					snmpTarget.setAuthProtocol(snmpTarget.SHA_AUTH);
				}
				snmpTarget.setAuthPassword(passwd);
				int privacy_protocol=Integer.parseInt(props.getProperty("privacy_protocol").toString());
				String privacy_password=props.getProperty("privacy_password").toString();
				snmpTarget.setPrivProtocol(privacy_protocol);
				snmpTarget.setPrivPassword(privacy_password);
				
			}
			
			
			snmpTarget.create_v3_tables();
			return snmpTarget;
		}

}
