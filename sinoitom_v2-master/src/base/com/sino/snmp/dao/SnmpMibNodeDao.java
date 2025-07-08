package com.sino.snmp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.snmp.entity.SnmpMibNode;

@Component
public class SnmpMibNodeDao extends BaseDao<SnmpMibNode, Long>{

	public List<SnmpMibNode> getAllData() {
		
		String hql="select nodeId,parentId,nodeName,nodeNo,oid,flag from SnmpMibNode";
		Query query = this.getSession().createQuery(hql);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<SnmpMibNode> mibNodeList = new ArrayList<SnmpMibNode>();
		
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			SnmpMibNode mibNode = new SnmpMibNode();
			mibNode.setNodeId(Long.valueOf(tuple[0].toString()));
			mibNode.setParentId(Long.valueOf(tuple[1].toString()));
			mibNode.setNodeName(tuple[2].toString());
			mibNode.setNodeNo(Integer.parseInt(tuple[3].toString()));
			mibNode.setOid(tuple[4].toString());
			mibNode.setFlag(Integer.parseInt(tuple[5].toString()));
			mibNodeList.add(mibNode);
		}
		
		return mibNodeList;
		
	}
	
	public List<SnmpMibNode> getDataByPid(long pid) {
		
		String hql="select nodeId,parentId,nodeName,nodeNo,oid,flag from SnmpMibNode where parentId=:pid";
		
		Query query = this.getSession().createQuery(hql);
		query.setParameter("pid", pid);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<SnmpMibNode> mibNodeList = new ArrayList<SnmpMibNode>();
		
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			SnmpMibNode mibNode = new SnmpMibNode();
			mibNode.setNodeId(Long.valueOf(tuple[0].toString()));
			mibNode.setParentId(Long.valueOf(tuple[1].toString()));
			mibNode.setNodeName(tuple[2].toString());
			mibNode.setNodeNo(Integer.parseInt(tuple[3].toString()));
			mibNode.setOid(tuple[4].toString());
			mibNode.setFlag(Integer.parseInt(tuple[5].toString()));
			mibNodeList.add(mibNode);
		}
		
		return mibNodeList;
		
	}

}
