package com.sino.snmp.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class SnmpConstants {

	 public static int SnmpPort=161;
	 public static String Host="";
	 public static String Community="";
	 public static int Version=1;
	 public static String SnmpUser="";
	 public static String SnmpPasswd="";
	 public static int Security_Level=1;
	 public static String Auth_Protocol="HMAC_MD5_96";
	 public static String Auth_Password="";
	 public static String Privacy_Protocol="DES";
	 public static String Privacy_Password="";
	 
	 public static List<MultipartFile> multipartFileList = new ArrayList<MultipartFile>();
	 public static MultipartHttpServletRequest request = null;
	 
}
