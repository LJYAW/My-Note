package com.sino.snmp.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Test {

	public static void main(String[] args) {
		method5();
		
	}
	
	public static void method5(){
		System.out.println( System.getProperty("os.name") );
	    System.out.println( System.getProperty("os.version") );
	    System.out.println( System.getProperty("os.arch") );
	    
	    String sepa = System.getProperty("file.separator");
        System.out.println(sepa);
        
        String sepa2 = java.io.File.separator;
        System.out.println(sepa2);
        
        System.out.println(System.getProperty("catalina.home"));
	}
	
	public static void method4(){
		
		String oid1 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2";
		String oid2 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2.2";
		String oid3 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2.1.3";
		String oid4 = ".1.3.6.1.4.1.4881.1.1.10.2.2";
		
		String[] arr = {oid1,oid2,oid3,oid4};
		
		List<String> asList = Arrays.asList(arr);
		
	    Collections.sort(asList,new Comparator<String>(){  //排序
	        @Override
	        public int compare(String oid1, String oid2) {
	        	
	        	String[] split1 = oid1.split("\\.");
	        	String[] split2 = oid2.split("\\.");
	        	
	        	int size1 = split1.length;
	        	int size2 = split2.length;
	        	
	        	int size = size1 >size2 ? size2:size1;//size是两个中的最小长度
	        	
	        	for(int i = 0;i<size;i++){
	        		
	        		int parseInt1 = 0;
	        		int parseInt2 = 0;
	        		
	        		if(StringUtils.isNotBlank(split1[i])){
	        			parseInt1 = Integer.parseInt(split1[i]);
	        		}
	        		if(StringUtils.isNotBlank(split2[i])){
	        			parseInt2 = Integer.parseInt(split2[i]);
	        		}
	        		
	        		if( parseInt1 > parseInt2){
	        			return 1;
	        		}else if(parseInt1 < parseInt2){
	        			return -1;
	        		}else{
	        			continue;
	        		}
	        	}
	        	
//	        	如果一直相等，也就是continue到最后，那么就看长度，长的大，短的小
	        	if(split1.length>size){
	        		return 1;
	        	}else if(split2.length>size){
	        		return -1;
	        	}else {
	        		return 0;
	        	}
	        }
	    });
	    
	    System.out.println(asList);
		
	}
	
	
	public static void method3(){
		
		String oid1 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2";
		String oid2 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2.2";
		String oid3 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2.1.3";
		String oid4 = ".1.3.6.1.4.1.4881.1.1.10.2.2";
		
		String oidStr1 = oid1.replaceAll("\\.", "").trim();
		String oidStr2 = oid2.replaceAll("\\.", "").trim();
		String oidStr3 = oid3.replaceAll("\\.", "").trim();
		String oidStr4 = oid4.replaceAll("\\.", "").trim();
		
		BigDecimal bd1=new BigDecimal(oidStr1);
		BigDecimal bd2=new BigDecimal(oidStr2);
		BigDecimal bd3=new BigDecimal(oidStr3);
		BigDecimal bd4=new BigDecimal(oidStr4);
		
		BigDecimal [] arr = {bd1,bd2,bd3,bd4};
		List<BigDecimal> asList = Arrays.asList(arr);//这个排序也是不正确的
//		result:[1361414881111022, 136141488111102172, 1361414881111021722, 13614148811110217213]
		
		Collections.sort(asList);
		
		System.out.println(asList);
		
	}
	
	
	public static void method2(){
		
		String oid1 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2";
		String oid2 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2.1";
		String oid3 = ".1.3.6.1.4.1.4881.1.1.10.2.17.2.1.3";
		String oid4 = ".1.3.6.1.4.1.4881.1.1.10.2.2";
		
		String oidStr1 = oid1.replaceAll("\\.", "").trim();
		String oidStr2 = oid2.replaceAll("\\.", "").trim();
		String oidStr3 = oid3.replaceAll("\\.", "").trim();
		String oidStr4 = oid4.replaceAll("\\.", "").trim();
		
		Long long1 = Long.valueOf(oidStr1);
		Long long2 = Long.valueOf(oidStr2);
		Long long3 = Long.valueOf(oidStr3);
		Long long4 = Long.valueOf(oidStr4);//long型只能表示19位数字，所以当超过19位的时候就会报错
		
		System.out.println(long1);
		System.out.println(long2);
		System.out.println(long3);
		System.out.println(long4);
		
		Long [] arr = {long1,long2,long3,long4};
		List<Long> asList = Arrays.asList(arr);
		
		Collections.sort(asList);
		
		System.out.println(asList);
		
	}
	
	
	public static void method1(){
		//这里的顺序，是我自己定义的一个List<String>
		String[] regulation = {"诸葛亮","鲁班","貂蝉","吕布"};
		final List<String> regulationOrder = Arrays.asList(regulation);
		String[] ordered = {"貂蝉","诸葛亮","吕布","貂蝉","鲁班","诸葛亮","貂蝉","鲁班","诸葛亮"};
		List<String> orderedList = Arrays.asList(ordered);
	
		Collections.sort(orderedList, new Comparator<String>()
		{
		    public int compare(String o1, String o2)
		    {
		        int io1 = regulationOrder.indexOf(o1);
		        int io2 = regulationOrder.indexOf(o2);
		        return io1 - io2;
		    }
		});
		System.out.println(orderedList);
	}
}
