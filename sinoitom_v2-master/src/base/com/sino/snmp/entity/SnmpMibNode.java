package com.sino.snmp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "Snmp_MibNode")
public class SnmpMibNode implements Comparable<SnmpMibNode>{
	
	private Long parentId;	   //'父节点Id',	
	private Long nodeId  ;    //'表主键',自己生成
	
	private String moduleName;//'Mib模块名（一般对应MIB文件名，不包括文件扩展名）',
	private Integer nodeNo ;  //'节点号（定义该节点是其父节点下的第几个子节点）',
	private String nodeName ;   //'节点名称（管理对象名）',switchMib
	private String nodeType ;   //'节点类型（MODULE-IDENTITY，OBJECT TYPE等）',
	private String syntax ;     //'语法，用来指定数据类型',
	private String units  ;     //'单位',不懂对应哪个属性，暂时不用
	private String access ;     //'访问权限，：定义这个特定对象的访问权限。合法的值有：只读、读写、只写和不可存取。',
	private String status ;     //'状态,定义对象的实现需要(必备的;可选的;已废弃的)',
	private String description; //'描述',
	private String sequence ;   //'序列（用来定义表的各列属性',暂时不用
	private String oid   ;      //'oid，定义对象节点的OID，如.1.3.6.1.2.1',
	private Integer flag  ;   //'标志（1：oid已解析；0：未全部解析）',这个怎么判断已解析和未解析呢，暂时不用

	private String parent;// 父节点名称,数据库中没有这个字段
	
	@Id
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "moduleName")
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Integer getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getSyntax() {
		return syntax;
	}
	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	public String toString(){
		return "Snmp_MibNode [ nodeName=" + nodeName + ", nodeType=" + nodeType+",parentId="+parentId
				+ ", description=" + description + ", oid=" + oid+ "]";
	}
	@Override
	public int compareTo(SnmpMibNode o) {
		String[] split1 = this.oid.split("\\.");
    	String[] split2 = o.getOid().split("\\.");
    	
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
    	
//    	如果一直相等，也就是continue到最后，那么就看长度，长的大，短的小
    	if(split1.length>size){
    		return 1;
    	}else if(split2.length>size){
    		return -1;
    	}else {
    		return 0;
    	}
	}
	
}
