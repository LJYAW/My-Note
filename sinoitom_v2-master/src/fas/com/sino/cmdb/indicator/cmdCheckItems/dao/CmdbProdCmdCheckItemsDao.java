package com.sino.cmdb.indicator.cmdCheckItems.dao;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CmdbProdCmdCheckItemsDao extends BaseDao<CmdbProdCmdChkItems,Integer>{
	
	public List<CmdbProdCmdChkItems> getByVendorIDAndModelOID(String vendorID,String resTypeCode,List<String> modelOIDs,String osType,String osVersion,String osFeature){
		//String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
		String hql=" from CmdbProdCmdChkItems where status=1 " +
				"and (vendorId=:vendorID or vendorId= -1) " +
				"and (devTypeCode=:resTypeCode or devTypeCode = -1) " +
				"and osType=:osType "+
				"and (osVersion='V.R' or osVersion=:osVersion) "+
				"and (osFeature='default' or osFeature=:osFeature) "+
				"and  (modelOID='X.X' or modelOID in(:modelOIDs)) " +
				"and indClassCode<>8 and indClassCode<>9 ";
		System.out.println("hql---"+hql);
		Query query=this.getSession().createQuery(hql);
		query.setParameter("vendorID", Integer.parseInt(vendorID)); 
		query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
		query.setParameterList("modelOIDs", modelOIDs); 
		query.setParameter("osType", osType);
		query.setParameter("osVersion", osVersion);
		query.setParameter("osFeature", osFeature);
		return query.list();
	}
	
	public List<CmdbProdCmdChkItems> getByVendorIDAndModelOID(String vendorID,String resTypeCode,List<String> modelOIDs,String osType,String osVersion,String osFeature,List<Long> indItemIds){
		//String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
		StringBuffer hql=new StringBuffer(" from CmdbProdCmdChkItems where status=1 " +
				"and (vendorId=:vendorID or vendorId= -1) " +
				"and (devTypeCode=:resTypeCode or devTypeCode = -1) " +
				"and osType=:osType "+
				"and (osVersion='V.R' or osVersion=:osVersion) "+
				"and (osFeature='all' or osFeature=:osFeature) "+
				"and  (modelOID='X.X' or modelOID in(:modelOIDs)) " +
				"and indClassCode<>8 and indClassCode<>9 ");
		if(!indItemIds.isEmpty()){
			hql.append("and indItemID not in(:indItemIds)");
		}
		System.out.println("hql---"+hql.toString());
		Query query=this.getSession().createQuery(hql.toString());
		query.setParameter("vendorID", Integer.parseInt(vendorID)); 
		query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
		query.setParameterList("modelOIDs", modelOIDs); 
		query.setParameter("osType", osType);
		query.setParameter("osVersion", osVersion);
		query.setParameter("osFeature", osFeature);
		if(!indItemIds.isEmpty()){
			query.setParameterList("indItemIds", indItemIds); 
		}
		return query.list();
	}
	
	public List<CmdbProdCmdChkItems> getByOsTypeAndOsVersion(String osType,String osVersion){
		//String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
		String hql=" from CmdbProdCmdChkItems where status=1 " +
				"and osType=:osType "+
				"and (osVersion='V.R' or osVersion=:osVersion) "+
				"and indClassCode<>8 and indClassCode<>9 ";
		System.out.println("hql---"+hql);
		Query query=this.getSession().createQuery(hql);
		query.setParameter("osType", osType);
		query.setParameter("osVersion", osVersion);
		return query.list();
	}
	
	public List<CmdbProdCmdChkItems> getByOsTypeAndOsVersion(String osType,String osVersion,List<Long> indItemIds){
		//String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
		StringBuffer hql=new StringBuffer(" from CmdbProdCmdChkItems where status=1 " +
				"and osType=:osType "+
				"and (osVersion='V.R' or osVersion=:osVersion) "+
				"and indClassCode<>8 and indClassCode<>9 ");
		
		if(!indItemIds.isEmpty()){
			hql.append("and indItemID not in(:indItemIds)");
		}
		System.out.println("hql---"+hql.toString());
		Query query=this.getSession().createQuery(hql.toString());
		
		query.setParameter("osType", osType);
		query.setParameter("osVersion", osVersion);
		if(!indItemIds.isEmpty()){
			query.setParameterList("indItemIds", indItemIds); 
		}
		return query.list();
	}
}
