package com.sino.topo.topoLine.entity;

import java.io.Serializable;

public class TopoMapKey implements Serializable{

	 private static final long serialVersionUID = -8563921580193535942L;  
	 
	 private Long graphId;
	 private Long lineId;
	 
	 public TopoMapKey() {  
         
	    } 
	 
	 
	 public Long getGraphId() {
		return graphId;
	}


	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}


	public Long getLineId() {
		return lineId;
	}


	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}


	/** 
	     * 覆盖hashCode方法，必须要有 
	     */  
	    @Override  
	    public int hashCode() {  
	        final int PRIME = 31;  
	        int result = 1;  
	        result = PRIME * result + (graphId == null ? 0 : graphId.hashCode());  
	        result = PRIME * result + (lineId == null ? 0 : lineId.hashCode());  
	        return result;  
	    }  
	  
	    /** 
	     * 覆盖equals方法，必须要有 
	     */  
	    @Override  
	    public boolean equals(Object obj) {  
	        if(this == obj) return true;  
	        if(obj == null) return false;  
	        if(!(obj instanceof TopoMapKey)) return false;  
	        TopoMapKey objKey = (TopoMapKey)obj;  
	        if(graphId.toString().equalsIgnoreCase(objKey.graphId.toString()) &&  
	        		lineId.toString().equalsIgnoreCase(objKey.lineId.toString()))   
	                {  
	            return true;  
	        }  
	        return false;  
	    }  
}
