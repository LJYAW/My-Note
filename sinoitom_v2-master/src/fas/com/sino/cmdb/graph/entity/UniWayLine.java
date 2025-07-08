package com.sino.cmdb.graph.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sino.snmp.utils.Util;

public class UniWayLine {
	private long nodeFrom;
	private long nodeTo;
	private String nodeAIP;
	private String nodeBIP;
	private String if_fromName;
	private String if_toName;
	private int depth;
	public long getNodeFrom() {
		return nodeFrom;
	}
	public void setNodeFrom(long nodeFrom) {
		this.nodeFrom = nodeFrom;
	}
	public long getNodeTo() {
		return nodeTo;
	}
	public void setNodeTo(long nodeTo) {
		this.nodeTo = nodeTo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getIf_fromName() {
		return if_fromName;
	}
	public void setIf_fromName(String if_fromName) {
		this.if_fromName = if_fromName;
	}
	public String getIf_toName() {
		return if_toName;
	}
	public void setIf_toName(String if_toName) {
		this.if_toName = if_toName;
	}
	public String getNodeAIP() {
		return nodeAIP;
	}
	public void setNodeAIP(String nodeAIP) {
		this.nodeAIP = nodeAIP;
	}
	public String getNodeBIP() {
		return nodeBIP;
	}
	public void setNodeBIP(String nodeBIP) {
		this.nodeBIP = nodeBIP;
	}
	
	public static List<UniWayLine> getSortList(List<UniWayLine> list){
        Collections.sort(list, new Comparator<UniWayLine>() {
            @Override
            public int compare(UniWayLine o1, UniWayLine o2) {
                if(o1.getNodeFrom()>o2.getNodeFrom()){
                    return 1;
                }
                return -1;
            }
        });
        return list;
    }


}
