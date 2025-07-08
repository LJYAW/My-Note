/**
 * created on 2012-12-6
 */
package com.sino.snmp.utils;

/**
 * @author LvXingHai
 *
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;

public class SortedOidValue {

	final static boolean DEBUG = false;
	static int MAX_OID_LENGTH = 25;
	static int MAX_RECORD_COUNT = 20000;
	public SortedOidValue(){
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<String, String>();
		Long st = System.currentTimeMillis();

		map.put(".1.3.6.1.4.1.2011.1.23.2.0", "1");
		map.put(".1.3.6.1.4.1.2011.5.23.2", "2");
		map.put(".1.3.6.1.4.1.2011.2.23.1.1.1.1.5.1", "1");
		map.put(".1.3.6.1.4.1.2011.2.23.1.1.1.1.5.2", "6");
		map.put(".1.3.6.1.4.1.2011.2.23.1.1.1.1.5.16", "10");
		map.put(".1.3.6.1.4.1.2011.2.23.1.1.1.1.5.10", "3");
		map.put(".1.3.6.1.4.1.2011.2.23.1.1.1.1.3.4227614", "5");
		map.put(".1.3.6.1.4.1.2011.2.23.1.1.1.1.6.4227614", "4");
		map.put(".1.3.6.1.4.1.2011.2.23.1.1.1.1.5.4227614", "3");

		Long et = System.currentTimeMillis();
		if (DEBUG)
			System.out.println("TreeMap put data spend: " + (et - st)
					+ " MilliSecond");

		st = System.currentTimeMillis();
		TreeMap<Double, OidValue> treeMap = new TreeMap<Double, OidValue>();
		
		String rootOid = "1.3.6.1.4.1.2011.1"; // rootOid为snmpWalk时输入的oid
		treeMap = sortOidValue(rootOid, map);
		List<Map.Entry<Double, OidValue>> oidValueTreeMapList = new ArrayList<Map.Entry<Double, OidValue>>(
				treeMap.entrySet());
		if (DEBUG)
			System.out.println("Oid Counts: " + oidValueTreeMapList.size());
		String oid, value;
		for (int i = 0; i < oidValueTreeMapList.size(); i++) {
			Double key = oidValueTreeMapList.get(i).getKey();
			oid = oidValueTreeMapList.get(i).getValue().getOid();
			value = oidValueTreeMapList.get(i).getValue().getValue();
			System.out.println(key + "\t\t" + oid + ": " + value);
		}
		et = System.currentTimeMillis();
		System.out.println("Sort Oid Values spend: " + (et - st)
				+ " MilliSecond");
	}

	public static TreeMap<Double, OidValue> sortOidValue(String rootOid,Map<String, String> map) {

		if (rootOid.charAt(0) != '.') {
			String rootOid1 = ".".concat(rootOid);
			rootOid = rootOid1;
		}// 修正首字符不是"."的OID

		String[] rootOidArray = rootOid.split("\\.");
		// 在转换为Double型时，要忽略的OID长度
		int ignoreOidLength = rootOidArray.length - 1;

		int maxOidLength = 0;

		List<Map.Entry<String, String>> oid_values = new ArrayList<Map.Entry<String, String>>(
				map.entrySet());
		String oid;
		String[] oidArray;
		
		int oidMaxLen[] = new int[MAX_OID_LENGTH];
		int factor[] = new int[MAX_OID_LENGTH];
		for (int i = 0; i < MAX_OID_LENGTH; i++) {
			oidMaxLen[i] = 0;
			factor[i] = 1;
		}
		for (int i = 0; i < oid_values.size(); i++) {
			
			oid = oid_values.get(i).getKey();
			oidArray = oid.split("\\.");

			for (int j = 1; j < oidArray.length; j++) {
				if (oidMaxLen[j] < oidArray[j].length())
					oidMaxLen[j] = oidArray[j].length();
			}
			if (maxOidLength < oidArray.length)
				maxOidLength = oidArray.length;
		} // 获取oid各段数字字符的最大长度

		if (DEBUG)
			System.out.println("maxOidLength = " + (maxOidLength - 1));
		for (int j = 1; j < maxOidLength; j++) {
			for (int k = 1; k <= oidMaxLen[j]; k++)
				factor[j] = factor[j] * 10;
//			if (DEBUG)
				System.out.println("maxOidLength: " + maxOidLength);
				System.out.println("factor[" + j + "] = " + factor[j]);
				
		} // 构造oid各段的转换因子
	
		TreeMap<Double, OidValue> treeMap = new TreeMap<Double, OidValue>();

		if (DEBUG)
			System.out.println("Oid map Counts: " + oid_values.size());
		
		Double oid2Double[] = new Double[MAX_RECORD_COUNT];
		for (int i = 0; i < oid_values.size(); i++) {
			 oid2Double[i] = 0d;
			OidValue ov = new OidValue();
			oid = oid_values.get(i).getKey();
			oidArray = oid.split("\\.");
//			if (DEBUG) {
//				System.out.println(oid);
//				System.out.print("oid[" + i + "]-->Double: ");
//			}

			for (int j = ignoreOidLength; j < oidArray.length; j++) {
//				System.out.println("ignore 长度：》》"+ignoreOidLength+"  oidarray:》》"+oidArray.length+"  oidvalsiz:>>"+oid_values.size());
				oid2Double[i] = oid2Double[i] * factor[j]
						+ Integer.parseInt(oidArray[j]);
//				if (DEBUG)
//					System.out.print("->" + oid2Double[i]);
			}
			for (int j = oidArray.length; j < maxOidLength; j++) {
				oid2Double[i] = oid2Double[i] * factor[j];
//				if (DEBUG)
//					System.out.print("->" + oid2Double[i]);
			}
			System.out.println(oid2Double[i] + "\t\t" + oid);
			ov.setOid(oid);
			ov.setValue(oid_values.get(i).getValue());
			treeMap.put(oid2Double[i], ov);
		} // 将OID转换为Double,并通过TreeMap进行排序
		return (treeMap);
	}

	
	
	public static class OidValue {
		String oid;
		String value;

		public OidValue() {

		}

		public String getOid() {
			return oid;
		}

		public String getValue() {
			return value;
		}

		public void setOid(String oid) {
			this.oid = oid;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
