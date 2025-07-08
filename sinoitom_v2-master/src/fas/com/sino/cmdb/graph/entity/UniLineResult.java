package com.sino.cmdb.graph.entity;

import java.util.ArrayList;
import java.util.List;

public class UniLineResult {

	private int depth;
	private int []lineArray=new int[10];
	private List<UniWayLine> unilinelist=new ArrayList<UniWayLine>();
	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int[] getLineArray() {
		return lineArray;
	}
	public void setLineArray(int[] lineArray) {
		this.lineArray = lineArray;
	}
	public List<UniWayLine> getUnilinelist() {
		return unilinelist;
	}
	public void setUnilinelist(List<UniWayLine> unilinelist) {
		this.unilinelist = unilinelist;
	}
	
	
	
}
