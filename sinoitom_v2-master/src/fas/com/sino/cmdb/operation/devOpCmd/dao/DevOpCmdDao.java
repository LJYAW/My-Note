package com.sino.cmdb.operation.devOpCmd.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmd;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.threshold.entity.ParamThresholdInfoAndType;
/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-2-12
 */
@Component
public class DevOpCmdDao extends BaseDao<DevOpCmd, Long>{
	public List<DevOpCmd> getData(Integer id,Integer vendorID,Integer osID,String osName,String osVersion,String modelOID){
		StringBuilder hql=new StringBuilder();
		hql.append("from DevOpCmd where devOpID=:devOpID");
		if(vendorID!=-1){
			hql.append(" and snmpEntID=:vendorID");
			if(osID!=-1){
				hql.append(" and osName=:osName");
				if(!"请选择".equals(osVersion)){
					hql.append(" and osVersion=:osVersion");
				}
			}
			if(!"-1".equals(modelOID)){
				hql.append(" and modelOID=:modelOID");
			}
		}
		hql.append(" order by cmdOrder");
		Query query=this.getSession().createQuery(hql.toString());
		query.setParameter("devOpID", id);
		if(vendorID!=-1){
			query.setParameter("vendorID", vendorID);
			if(osID!=-1){
				query.setParameter("osName", osName);
				if(!"请选择".equals(osVersion)){
					query.setParameter("osVersion", osVersion);
				}
			}
			if(!"-1".equals(modelOID)){
				query.setParameter("modelOID", modelOID);
			}
		}
		@SuppressWarnings("unchecked")
		List<DevOpCmd> list=query.list();
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getOperation() {
		String hql="select distinct a.devOpID,a.operation from DevOperation a,DevOpCmd b where a.devOpID = b.devOpID order by a.devOpID ";
		Query query=this.getSession().createQuery(hql);
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
	public List<ResItemParam> getVenderById(int devOpID) {
		String hql="select distinct a.vendorID,a.dispName from CmdbVendor a,DevOpCmd b where a.vendorID = b.snmpEntID and b.devOpID=:devOpID order by a.vendorID ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("devOpID", devOpID);
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
	public List<ResItemParam> getDevTypeByVendor(int devOpID) {
		String hql="select distinct a.devTypeCode,a.devTypeName from DevOperation a,DevOpCmd b where a.devOpID = b.devOpID and b.snmpEntID=:devOpID order by a.devTypeCode ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("devOpID", devOpID);
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
	public List<DevOpCmdParam> getAllDev() {
		StringBuffer hql=new StringBuffer("select a.devOpID,b.opCmdID,a.devTypeName,a.operateName,b.snmpEntID,c.dispName, ");
		hql.append(" b.osID,b.modelOID,b.osName,b.osVersion,b.cmdOrder,b.cmdTypeCode,b.cmdType,b.command,b.expectPrompt,b.paramFlag,b.description,b.status,b.devModel ");
		hql.append(" from DevOperation a,DevOpCmd b,CmdbVendor c where a.devOpID = b.devOpID and c.vendorID = b.snmpEntID ");
		hql.append(" order by a.opID,a.devOpID,b.snmpEntID,a.devTypeCode,b.devModel,b.osVersion,b.cmdOrder ");
		System.out.println(hql);
		List<DevOpCmdParam> paramList = new ArrayList<DevOpCmdParam>();
		Query query=this.getSession().createQuery(hql.toString());  
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			DevOpCmdParam param = new DevOpCmdParam();
			param.setDevOpID(tuple[0].toString());
			param.setOpCmdID(tuple[1].toString());
			param.setDevTypeName(tuple[2].toString());
			param.setOperateName(tuple[3].toString());
			param.setSnmpEntID(tuple[4].toString());
			param.setDispName(tuple[5].toString());
			param.setOsID(tuple[6].toString());
			param.setModelOID(tuple[7].toString());
			param.setOsName(tuple[8].toString());
			param.setOsVersion(tuple[9].toString());
			param.setCmdOrder(tuple[10].toString());
			param.setCmdTypeCode(tuple[11].toString());
			param.setCmdType(tuple[12].toString());
			param.setCommand(tuple[13].toString());
			param.setExpectPrompt(tuple[14].toString());
			param.setParamFlag(tuple[15].toString());
			if(null != tuple[16]){
				param.setDescription(tuple[16].toString());
			}
			if(tuple[17]!=null){
				param.setStatus(Integer.valueOf(tuple[17].toString()));
			}
			param.setDevModel(tuple[18].toString());
			paramList.add(param);
		}
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<DevOpCmdParam> getByOperation(int operation) {
		StringBuffer hql=new StringBuffer("select a.devOpID,b.opCmdID,a.devTypeName,a.operateName,b.snmpEntID,c.dispName, ");
		hql.append(" b.osID,b.modelOID,b.osName,b.osVersion,b.cmdOrder,b.cmdTypeCode,b.cmdType,b.command,b.expectPrompt,b.paramFlag,b.description,b.status,b.devModel ");
		hql.append(" from DevOperation a,DevOpCmd b,CmdbVendor c where a.devOpID = b.devOpID and c.vendorID = b.snmpEntID and b.devOpID=:operation ");
		hql.append(" order by a.opID,a.devOpID,b.snmpEntID,a.devTypeCode,b.devModel,b.osVersion,b.cmdOrder ");
		List<DevOpCmdParam> paramList = new ArrayList<DevOpCmdParam>();
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("operation", operation);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			DevOpCmdParam param = new DevOpCmdParam();
			param.setDevOpID(tuple[0].toString());
			param.setOpCmdID(tuple[1].toString());
			param.setDevTypeName(tuple[2].toString());
			param.setOperateName(tuple[3].toString());
			param.setSnmpEntID(tuple[4].toString());
			param.setDispName(tuple[5].toString());
			param.setOsID(tuple[6].toString());
			param.setModelOID(tuple[7].toString());
			param.setOsName(tuple[8].toString());
			param.setOsVersion(tuple[9].toString());
			param.setCmdOrder(tuple[10].toString());
			param.setCmdTypeCode(tuple[11].toString());
			param.setCmdType(tuple[12].toString());
			param.setCommand(tuple[13].toString());
			param.setExpectPrompt(tuple[14].toString());
			param.setParamFlag(tuple[15].toString());
			
			if(null != tuple[16]){
				param.setDescription(tuple[16].toString());
			}
			if(tuple[17]!=null){
				param.setStatus(Integer.valueOf(tuple[17].toString()));
			}
			param.setDevModel(tuple[18].toString());
			paramList.add(param);
		}
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<DevOpCmdParam> getByOperationAndVendor(int operation,
			int snmpEntID) {
		StringBuffer hql=new StringBuffer("select a.devOpID,b.opCmdID,a.devTypeName,a.operateName,b.snmpEntID,c.dispName, ");
		hql.append(" b.osID,b.modelOID,b.osName,b.osVersion,b.cmdOrder,b.cmdTypeCode,b.cmdType,b.command,b.expectPrompt,b.paramFlag,b.description,b.status,b.devModel ");
		hql.append(" from DevOperation a,DevOpCmd b,CmdbVendor c where a.devOpID = b.devOpID and c.vendorID = b.snmpEntID and b.devOpID=:operation and b.snmpEntID=:snmpEntID ");
		hql.append(" order by a.opID,a.devOpID,b.snmpEntID,a.devTypeCode,b.devModel,b.osVersion,b.cmdOrder ");
		List<DevOpCmdParam> paramList = new ArrayList<DevOpCmdParam>();
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("operation", operation);
		query.setParameter("snmpEntID", snmpEntID);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			DevOpCmdParam param = new DevOpCmdParam();
			param.setDevOpID(tuple[0].toString());
			param.setOpCmdID(tuple[1].toString());
			param.setDevTypeName(tuple[2].toString());
			param.setOperateName(tuple[3].toString());
			param.setSnmpEntID(tuple[4].toString());
			param.setDispName(tuple[5].toString());
			param.setOsID(tuple[6].toString());
			param.setModelOID(tuple[7].toString());
			param.setOsName(tuple[8].toString());
			param.setOsVersion(tuple[9].toString());
			param.setCmdOrder(tuple[10].toString());
			param.setCmdTypeCode(tuple[11].toString());
			param.setCmdType(tuple[12].toString());
			param.setCommand(tuple[13].toString());
			param.setExpectPrompt(tuple[14].toString());
			param.setParamFlag(tuple[15].toString());
			if(null != tuple[16]){
				param.setDescription(tuple[16].toString());
			}
			if(tuple[17]!=null){
				param.setStatus(Integer.valueOf(tuple[17].toString()));
			}
			param.setDevModel(tuple[18].toString());
			paramList.add(param);
		}
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<DevOpCmdParam> getByOperationAndVendorAndDevType(int operation,
			int snmpEntID, int devType) {
		StringBuffer hql=new StringBuffer("select a.devOpID,b.opCmdID,a.devTypeName,a.operateName,b.snmpEntID,c.dispName, ");
		hql.append(" b.osID,b.modelOID,b.osName,b.osVersion,b.cmdOrder,b.cmdTypeCode,b.cmdType,b.command,b.expectPrompt,b.paramFlag,b.description,b.status,b.devModel");
		hql.append(" from DevOperation a,DevOpCmd b,CmdbVendor c where a.devOpID = b.devOpID and c.vendorID = b.snmpEntID and b.devOpID=:operation and b.snmpEntID=:snmpEntID and a.devTypeCode=:devType");
		hql.append(" order by a.opID,a.devOpID,b.snmpEntID,a.devTypeCode,b.devModel,b.osVersion,b.cmdOrder ");
		List<DevOpCmdParam> paramList = new ArrayList<DevOpCmdParam>();
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("operation", operation);
		query.setParameter("snmpEntID", snmpEntID);
		query.setParameter("devType", devType);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			DevOpCmdParam param = new DevOpCmdParam();
			param.setDevOpID(tuple[0].toString());
			param.setOpCmdID(tuple[1].toString());
			param.setDevTypeName(tuple[2].toString());
			param.setOperateName(tuple[3].toString());
			param.setSnmpEntID(tuple[4].toString());
			param.setDispName(tuple[5].toString());
			param.setOsID(tuple[6].toString());
			param.setModelOID(tuple[7].toString());
			param.setOsName(tuple[8].toString());
			param.setOsVersion(tuple[9].toString());
			param.setCmdOrder(tuple[10].toString());
			param.setCmdTypeCode(tuple[11].toString());
			param.setCmdType(tuple[12].toString());
			param.setCommand(tuple[13].toString());
			param.setExpectPrompt(tuple[14].toString());
			param.setParamFlag(tuple[15].toString());
			if(null != tuple[16]){
				param.setDescription(tuple[16].toString());
			}
			if(tuple[17]!=null){
				param.setStatus(Integer.valueOf(tuple[17].toString()));
			}
			param.setDevModel(tuple[18].toString());
			paramList.add(param);
		}
		return paramList;
	}

	
	
	
}
