package com.sino.cmdb.graph.test;

import java.util.HashSet;
import java.util.Set;

public class TestMethod<T> {
	
	
	public static void main(String[] args) {
		
		Long temp = 112L;
		Set<Long> set = new HashSet<Long>();
		set.add(112L);
		System.out.println(set.contains(temp));
		
	}
	

}
