/*
 * 文件名： ResSnmpIndicatorService.java
 * 
 * 创建日期： 2014-9-4
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.monitor.res.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.indicator.product.entity.ProdSnmpIndItems;
import com.sino.cmdb.severity.entity.Severity;
import com.sino.cmdb.threshold.service.ThresholdLevelService;
import com.sino.monitor.intf.entity.MonLineInterface;
import com.sino.monitor.res.dao.MonResourceDao;
import com.sino.monitor.res.dao.ResSnmpIndicatorDao;
import com.sino.monitor.res.entity.MonResource;
import com.sino.monitor.res.entity.ParamSnmpRes;
import com.sino.monitor.res.entity.ResSnmpIndicator;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$indClassCode
 *
 * @since 2014-9-4
 */
@Service
@Transactional
public class ResSnmpIndicatorService {

	private static Logger logger = LoggerFactory.getLogger(ResSnmpIndicatorService.class);
	
	private static String objAttNames="resIndItemID,resID,resIP,resClassCode,resClassName,resTypeCode,resTypeName,subResType,subResName,subResID,poll_Interval,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,monFlag,thresholdID,thresholdID@ThresholdInfo@threshold_ID@thresholdName,subResInfo";
	private static String jsonAttNames="id,resID,resIP,resClassCode,resClassName,resTypeCode,resTypeName,subResType,subResName,subResID,poll_Interval,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,monFlag,thresholdID,thresholdName,subResInfo";
	
	@Autowired
	private MonResourceDao monResourceDao;
	
	@Autowired
	private ResSnmpIndicatorDao resSnmpIndicatorDao;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private ThresholdLevelService thresholdLevelService;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResSnmpIndicator> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResSnmpIndicator> getAll(){
		logger.debug("Enter getAll");
		return resSnmpIndicatorDao.getAll();
	}
	
	
	@Transactional(readOnly = true)
	public ResSnmpIndicator getById(int id){
		logger.debug("Enter getById...");
		return resSnmpIndicatorDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public List<ResSnmpIndicator> getByIds(List<Integer> ids){
		logger.debug("Enter getByIds...");
		return resSnmpIndicatorDao.getDataByIds(ids);
	}
	
	@Transactional(readOnly = true)
	public List<ResSnmpIndicator> getByTypeCode(int code){
		logger.debug("Enter getByTypeCode...");
		String hql=" from ResSnmpIndicator where resTypeCode=?";
		return resSnmpIndicatorDao.find(hql, code);
	}
	
	@Transactional(readOnly = true)
	public List<ResSnmpIndicator> getByTypeCodeAndIP(int code,String resIP){
		logger.debug("Enter getByTypeCode...");
		String hql=" from ResSnmpIndicator where resTypeCode=? and resIP=?";
		return resSnmpIndicatorDao.find(hql, code,resIP);
	}
	
	@Transactional(readOnly = true)
	public ResSnmpIndicator getByResId(Long id,String indicator){
		logger.debug("Enter getByResId...");
		String hql="from ResSnmpIndicator where resID =? and subResID = null and indicatorItem =? order by thresholdID";
		List<ResSnmpIndicator> list =resSnmpIndicatorDao.find(hql,id,indicator);
		ResSnmpIndicator ind=null;
		if(!list.isEmpty()){
			ind=list.get(0);
		}
		return ind;
	}
	
	
	
	@Transactional(readOnly = true)
	public ResSnmpIndicator getBySubResId(Long id,String indicator){
		logger.debug("Enter getByResId...");
		String hql="from ResSnmpIndicator where subResID = ? and indicatorItem =? order by thresholdID";
		List<ResSnmpIndicator> list =resSnmpIndicatorDao.find(hql,id,indicator);
		ResSnmpIndicator ind=null;
		if(!list.isEmpty()){
			ind=list.get(0);
		}
		return ind;
	}
	
	
	
	public Severity getSeverityByRes(Long id,String indicator,float readVal,String type){
		ResSnmpIndicator ind=null;
		if(type.equals("subRes")){
			ind =getBySubResId(id,indicator);
		}else{
			ind =getByResId(id,indicator);
		}
		Severity s=null;
		if(ind!=null){
			s =thresholdLevelService.getSeverityByScope(ind.getThresholdID(), readVal);
		}
		return s;
	}
	
	public void delete(int id){
		logger.debug("Enter delete...");
		resSnmpIndicatorDao.delete(id);
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	
	public void batchSetThtesholdID(List<Integer> resIndIds,Integer thresholdID){
		resSnmpIndicatorDao.batchSetThtesholdID(resIndIds, thresholdID);
	}
	
	
	public void save(String accessModeCode,String collectorID,String poll_Interval,List<ParamSnmpRes> resList,List<ProdSnmpIndItems> itemList){
		logger.debug("Enter save...");
		List<MonResource> resourceList=new ArrayList<MonResource>();
		List<ResSnmpIndicator> snmpIndList=new ArrayList<ResSnmpIndicator>();
		String  resName=resItemService.findResContent("Cmdb_DevAccessMode", String.valueOf(accessModeCode));
		for(int i=0;i<resList.size();i++){
			ParamSnmpRes r=(resList.get(i));
			MonResource res=new MonResource();
			BeanUtils.copyProperties(r, res);
			res.setAccessMode(accessModeCode);
			res.setCollector_ID(Long.parseLong(collectorID));
			res.setPoll_Interval(Integer.parseInt(poll_Interval));
			res.setMonFlag(1);
			res.setAccessMode(resName);
			res.setProdModel(r.getResModel());
			resourceList.add(res);
			
			for(int j=0;j<itemList.size();j++){
				ProdSnmpIndItems item=itemList.get(j);
				ResSnmpIndicator ind=new ResSnmpIndicator();
				BeanUtils.copyProperties(res, ind);
				BeanUtils.copyProperties(item, ind);
				snmpIndList.add(ind);
			}
			
		}
		monResourceDao.batchSave(resourceList, 20);
		monResourceDao.flush();
		
		resSnmpIndicatorDao.batchSave(snmpIndList, 20);
		
	}
	
	
	public void saveResSnmpInd(List<MonResource> monResList,List<ProdSnmpIndItems> itemList){
		logger.debug("Enter saveResSnmpInd...");
		List<ResSnmpIndicator> snmpIndList=new ArrayList<ResSnmpIndicator>();
		List<String> resIPs=new ArrayList<String>();
		
		//关联之前先清空原始关联数据
//		for(int i=0;i<monResList.size();i++){
//			MonResource res=monResList.get(i);
//			resIPs.add(res.getResIP());
//			resSnmpIndicatorDao.deleteByResIPs(resIPs);
//			resSnmpIndicatorDao.flush();
//		}
		
		for(int i=0;i<monResList.size();i++){
			
			MonResource res=monResList.get(i);
			for(int j=0;j<itemList.size();j++){
				ProdSnmpIndItems item=itemList.get(j);
				
				ResSnmpIndicator findInd=getUniqueData(res.getResID(),item.getIndItemID());
				if(findInd==null){
					if(item.getModelOID().equals("X")||res.getModelOID().equals(item.getModelOID())){
						ResSnmpIndicator ind=new ResSnmpIndicator();
						BeanUtils.copyProperties(res, ind);
						BeanUtils.copyProperties(item, ind);
						snmpIndList.add(ind);
					}
					
				}
				
			}
		}
		resSnmpIndicatorDao.batchSave(snmpIndList, 20);
	}
	
	public void saveIntfResSnmpInd(String type,List<MonLineInterface> intfList,List<ProdSnmpIndItems> itemList){
		logger.debug("Enter saveResSnmpInd...");
		List<ResSnmpIndicator> snmpIndList=new ArrayList<ResSnmpIndicator>();
		List<Long> subResID=new ArrayList<Long>();
		
		//关联之前先清空原始关联数据
//		if("interface".equals(type)){
//			for(int i=0;i<intfList.size();i++){
//				MonLineInterface intf=intfList.get(i);
//				subResID.add(intf.getIf_ID());
//			}
//			resSnmpIndicatorDao.deleteBySubIfIDs(subResID);
//			resSnmpIndicatorDao.flush();
//		}
//		
//		if("line".equals(type)){
//			for(int i=0;i<intfList.size();i++){
//				MonLineInterface intf=intfList.get(i);
//				subResID.add(intf.getLineID());
//			}
//			resSnmpIndicatorDao.deleteByLineIDs(subResID);
//			resSnmpIndicatorDao.flush();
//		}
		
		
		
		for(int i=0;i<intfList.size();i++){
			MonLineInterface intf=intfList.get(i);
			
			MonResource monRes=monResourceDao.get(intf.getResID());
			
			for(int j=0;j<itemList.size();j++){
				
				ProdSnmpIndItems item=itemList.get(j);
				
				
				if(intf.getLineID()==null&&item.getIndicatorItem().contains("Line")){
					continue;
				}
				
			
				
				ResSnmpIndicator resSnmpInd=new ResSnmpIndicator();
				BeanUtils.copyProperties(item, resSnmpInd);
				
				BeanUtils.copyProperties(monRes, resSnmpInd);
				
				if(intf.getLineID()!=null&&item.getIndicatorItem().contains("Line")){
					resSnmpInd.setSnmpOID(resSnmpInd.getSnmpOID().replaceAll("(?u)ifindex", Long.toString(intf.getIf_Index())));//(?u)忽略大小写
					resSnmpInd.setSnmpOID(resSnmpInd.getSnmpOID().replaceAll("(?u)LineSpeed", Long.toString(intf.getLineBandwidth())));
					
					resSnmpInd.setSubResID(intf.getLineID());
					resSnmpInd.setSubResType("line");
					resSnmpInd.setSubResName(intf.getLineName());
					resSnmpInd.setSubResInfo(intf.getIf_Name());
					
				}else{
					resSnmpInd.setSnmpOID(resSnmpInd.getSnmpOID().replaceAll("(?u)ifindex", intf.getIf_Index().toString()));
					resSnmpInd.setSubResType("interface");
					resSnmpInd.setSubResID(intf.getIf_ID());
					resSnmpInd.setSubResName(intf.getIf_Name());
					resSnmpInd.setSubResInfo(intf.getIf_IpAddr());
				}
				
				ResSnmpIndicator findInd=getUniqueData(monRes.getResID(),resSnmpInd.getSubResType(),resSnmpInd.getSubResID(), item.getIndItemID());
				if(findInd==null){
					snmpIndList.add(resSnmpInd);
				}
				
			}
		}
		resSnmpIndicatorDao.batchSave(snmpIndList, 20);
	}
	
	public ResSnmpIndicator getUniqueData(long resID,String subResType,long subResID,long indItemID){
		String hql=" from ResSnmpIndicator where resID=? and subResType=? and subResID=? and indItemID=?";
		return resSnmpIndicatorDao.findUnique(hql, resID,subResType,subResID,indItemID);
	}
	
	public ResSnmpIndicator getUniqueData(long resID,long indItemID){
		String hql=" from ResSnmpIndicator where resID=? and indItemID=?";
		return resSnmpIndicatorDao.findUnique(hql, resID,indItemID);
	}

	
}
