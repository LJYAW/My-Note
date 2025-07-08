/*
 * 文件名： ProdIndItemDao.java
 * 
 * 创建日期： 2014-7-26
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Identities;

import com.sino.base.common.BaseDao;
import com.sino.base.common.util.JdbcConnection;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.cmdb.indicator.product.entity.ProdSnmpIndItems;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
@Component
public class ProdSnmpIndItemDao extends BaseDao<ProdSnmpIndItems,Integer>{

	public List<ProdSnmpIndItems> getByVendorIDAndModelOID(String vendorID,String resTypeCode,List<String> modelOIDs){
		//String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
		String hql=" from ProdSnmpIndItems where status=1 and (vendorID=:vendorID or vendorID= -1) and (devTypeCode=:resTypeCode or devTypeCode = -1) and  (modelOID='X' or modelOID in(:modelOIDs)) and indClassCode<>8 and indClassCode<>9 ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("vendorID", Integer.parseInt(vendorID)); 
		query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
		query.setParameterList("modelOIDs", modelOIDs); 
		return query.list();
	}
	
	public List<ProdSnmpIndItems> getDataByVendorIDAndModelOID(String vendorID,String resTypeCode,List<String> modelOIDs){
		//String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
		String hql="select distinct indItemName from ProdSnmpIndItems where status=1 and (vendorID=:vendorID or vendorID= -1) and (devTypeCode=:resTypeCode or devTypeCode = -1) and  (modelOID='X' or modelOID in(:modelOIDs)) and indClassCode<>8 and indClassCode<>9 ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("vendorID", Integer.parseInt(vendorID)); 
		query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
		query.setParameterList("modelOIDs", modelOIDs); 
		return query.list();
	}
	
	public List<ProdSnmpIndItems> getByVendorIDAndModelOID(String vendorID,List<String> modelOIDs){
		String hql=" from ProdSnmpIndItems where vendorID=:vendorID and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("vendorID", Integer.parseInt(vendorID)); 
		query.setParameterList("modelOIDs", modelOIDs); 
		return query.list();
	}
	
	public void batchAudit(List<Integer> ids) {
		String hql = "update ProdSnmpIndItems set status=1 where prodIndItemID in(:ids)";
		Query queryItem = this.getSession().createQuery(hql);
		queryItem.setParameterList("ids", ids);
		queryItem.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getVendor() {
		StringBuffer hql=new StringBuffer("select distinct a.vendorID,a.dispName ");
		hql.append("from CmdbVendor a,CmdbProdCmdChkItems b where a.vendorID=b.vendorId order by b.vendorId asc,b.devClassCode asc,b.devTypeCode asc");
		Query query=this.getSession().createQuery(hql.toString());  
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getByVendor(int vendor) {
		String hql="select distinct devClassCode,devClassName from CmdbProdCmdChkItems where vendorId=:vendor";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("vendor", vendor);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getByClassCode(int code,int vendor) {
		String hql="select distinct devTypeCode,devTypeName from CmdbProdCmdChkItems where devClassCode=:code and vendorId=:vendor";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		query.setParameter("vendor", vendor);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}
	
	/*public void addProdSnmpIndItems(ProdSnmpIndItems item) throws SQLException {
		Connection conn = null ;		// 数据库连接
		JdbcConnection jconn = JdbcConnection.getInstance() ;
		conn =jconn.getConnection();
			PreparedStatement pstmt_monServers; 
			pstmt_monServers = conn.prepareStatement("insert into Cmdb_ProdSnmpIndItems(vendorID,prodModelID,prodModel,modelOID,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,snmpObjName,snmpOID,getMethod,oidFlag," +
			  "valueType,length,decimals,measureUnit,remark,status,mibFile) " +
				"values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)" );
			

			
			
			pstmt_monServers.setInt(1, item.getVendorID());
			pstmt_monServers.setLong( 2, item.getProdModelID());
			pstmt_monServers.setString(3, item.getProdModel());
			pstmt_monServers.setString(4, item.getModelOID());
			pstmt_monServers.setInt( 5, item.getDevClassCode() );
			pstmt_monServers.setString( 6, item.getDevClassName());
			pstmt_monServers.setInt( 7, item.getDevTypeCode());
			pstmt_monServers.setString( 8, item.getDevTypeName());
			pstmt_monServers.setInt( 9, item.getIndClassCode());
			pstmt_monServers.setString( 10, item.getIndClassName());
			pstmt_monServers.setLong( 11, item.getIndGroupID());
			pstmt_monServers.setString( 12, item.getIndGroupName());
			pstmt_monServers.setLong( 13, item.getIndItemID());
			
			pstmt_monServers.setString( 14, item.getIndItemName());
			pstmt_monServers.setString( 15, item.getIndicatorItem());
			pstmt_monServers.setString( 16, item.getSnmpObjName());
			pstmt_monServers.setString( 17, item.getSnmpOID());
			pstmt_monServers.setString( 18, item.getGetMethod());
			pstmt_monServers.setInt( 19, item.getOidFlag());
			pstmt_monServers.setString( 20, item.getValueType());
			pstmt_monServers.setInt( 21, item.getLength());
			pstmt_monServers.setInt( 22, item.getDecimals());
			pstmt_monServers.setString( 23, item.getMeasureUnit());
			pstmt_monServers.setString( 24, item.getRemark());
			pstmt_monServers.setInt( 25, item.getStatus());
			pstmt_monServers.setString( 26, item.getMibFile());
			pstmt_monServers.executeUpdate();
			conn.close();
	}*/
}
