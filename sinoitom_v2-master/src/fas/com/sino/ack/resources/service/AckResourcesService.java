package com.sino.ack.resources.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.ack.devCheckReport.entity.AckDevCheckReport;
import com.sino.ack.resources.dao.AckResourcesDao;
import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.entity.ParamSnmpRes;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.host.service.IpHostService;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.service.NcmDevAccessUserService;
import com.sino.fas.res.net.service.NcmDevicesService;
import com.sino.snmp.utils.Util;
import com.sino.utils.common.StringUtil;

@Service
@Transactional
public class AckResourcesService {
	private static Logger logger = LoggerFactory.getLogger(AckResourcesService.class);
	
	private static String objAttNames ="ackResId,orgID,orgName,resId,resName,resClassCode,resClassName,resTypeCode,resTypeName,vendorID,vendorName,modelOID,prodModel,osClass,osType,osVersion,osFeature,osRelease,mgmtIP,mgmtIpLong,accessMode,userName,snmpStr,flag";
	private static String jsonAttNames="id,orgID,orgName,resId,resName,resClassCode,resClassName,resTypeCode,resTypeName,vendorID,vendorName,modelOID,prodModel,osClass,osType,osVersion,osFeature,osRelease,mgmtIP,mgmtIpLong,accessMode,userName,snmpStr,flag";
		
	
	//用于设置检测对象列表的显示
		private static String paramObjAttNames="orgID,orgName,vendorID,vendorID@CmdbVendor@vendorID@dispName,resClassCode,resClassName,resTypeCode,resTypeName,resModel,modelOID,resID,resIP,resIpLong,resName,accessMode,snmpStr,desc,devUse,osType,userName";
		private static String paramJsonAttNames="orgID,orgName,vendorID,vendorName,resClassCode,resClassName,resTypeCode,resTypeName,resModel,modelOID,resID,resIP,resIpLong,resName,accessMode,snmpStr,desc,devUse,osType,userName";
		
	@Autowired
	private AckResourcesDao ackResourcesDao;
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private NcmDevicesService ncmDevicesService;
	
	@Autowired
	private IpHostService ipHostService;
	
	@Autowired
	private NcmDevAccessUserService ncmAccessUser;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AckResources> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getParamJsonListStr(final List<ParamSnmpRes> list) {
		return JsonUtils.getJsonListInfo(list, paramObjAttNames, paramJsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonPageList(final Page<AckResources> page) {
		return JsonUtils.getJQJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	public List<AckResources> getAll() {
		return ackResourcesDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public List<AckResources> getByTypeCode(int code){
		logger.debug("Enter getByTypeCode...");
		String hql=" from AckResources where resTypeCode=?";
		return ackResourcesDao.find(hql, code);
	}
	
	@Transactional(readOnly = true)
	public List<AckResources> getByClassCode(int classcode){
		logger.debug("Enter getByClassCode...");
		String hql=" from AckResources where resClassCode=?";
		return ackResourcesDao.find(hql, classcode);
	}
	
	@Transactional(readOnly = true)
	public List<AckResources> getByClassCodeAndTypeCode(int classcode,int typeCode){
		logger.debug("Enter getByClassCode...");
		String hql=" from AckResources where resClassCode=? and resTypeCode=?";
		return ackResourcesDao.find(hql, classcode,typeCode);
	}
	
	
	@Transactional(readOnly = true)
	public List<AckResources> getByTypeCodeAndIP(int code,String resIP){
		logger.debug("Enter getByTypeCode...");
		String hql=" from AckResources where resTypeCode=? and mgmtIP=?";
		return ackResourcesDao.find(hql, code,resIP);
	}
	
	public void saveAckResources(List<ParamSnmpRes> resList){
		logger.debug("Enter saveAckResources...");
		List<AckResources> resourceList=new ArrayList<AckResources>();
//		String  resName=resItemService.findResContent("Cmdb_DevAccessMode", String.valueOf(accessModeCode));
		for(int i=0;i<resList.size();i++){
			
			ParamSnmpRes r=(resList.get(i));
			NcmDevices device =ncmDevicesService.getById(r.getResID());
			AckResources res=new AckResources();
			BeanUtils.copyProperties(r, res);
			res.setAccessMode(r.getAccessMode());
			res.setMgmtIP(r.getResIP());
			res.setMgmtIpLong(r.getResIpLong());
			res.setFlag(1);
			res.setResId(r.getResID());
			res.setProdModel(r.getResModel());
			res.setOsType(r.getOsType());
			res.setDevAcsUserId(device.getDevAccessId());
			res.setSnmpCredId(device.getSnmpCredId());
			res.setUserName(r.getUserName());
			res.setAccessMode(r.getAccessMode());
			res.setOsFeature(device.getOsFeature());
			res.setOsRelease(device.getOsRelease());
			res.setOsVersion(device.getOsVersion());
			resourceList.add(res);
		}
		ackResourcesDao.batchSave(resourceList, 20);
	}
	
	public void saveHostAckResource(List<IpHostInfo> list,int resClassCode,String className,int resTypeCode,String devType){
		logger.debug("Enter saveHostAckResource...");
		List<AckResources> resourceList=new ArrayList<AckResources>();
		for(int i=0;i<list.size();i++){
			IpHostInfo  info=list.get(i);
			
			
			IpHost host=ipHostService.getIpHost(info.getId());
			
			AckResources res=new AckResources();
			BeanUtils.copyProperties(host, res);
			res.setOrgID(info.getOrgId());
			res.setOrgName(info.getOrgName());
			res.setResId(info.getId());
			res.setResName(info.getHostName());
			res.setResClassCode(resClassCode);
			res.setResClassName(className);
			res.setResTypeCode(resTypeCode);
			res.setResTypeName(devType);
			res.setMgmtIP(info.getIpAddr());
			res.setMgmtIpLong(Util.ip2long(info.getIpAddr()));
			res.setFlag(0);
			
			IpHost iphost=ipHostService.getIpHost(info.getId());
			
			if(!StringUtil.isNullString(iphost.getDevAccessId())){
				NcmDevAccessUser accessUser=ncmAccessUser.getAccessByAccessId(iphost.getDevAccessId());
				if(accessUser!=null){
					res.setAccessMode(accessUser.getAccessTool());
					res.setUserName(accessUser.getUserName());
					res.setDevAcsUserId(iphost.getDevAccessId());
				}
			}
			
			resourceList.add(res);
			
		}
		ackResourcesDao.batchSave(resourceList, 20);
	}
	
	public List<AckResources> getAckResList(int id){
		List<AckResources> list = ackResourcesDao.getAckResList(id);
		return list;
	}
	public List<AckResources> searchAckResList(int id){
		List<AckResources> list = ackResourcesDao.searchAckResList(id);
		return list;
	}
	
	public AckResources getById(int id){
		String hql=" from AckResources where ackResId=?";
		return ackResourcesDao.findUnique(hql, id);
	}
	
	public AckResources getByResId(Long id){
		String hql=" from AckResources where resId=?";
		return ackResourcesDao.findUnique(hql, id);
	}
	public void deletes(String [] ids){
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	public void delete(Integer id){
		ackResourcesDao.delete(id);
	}

	public Page<AckResources> findAjaxSearch(PageRequest pageRequest,Integer classCode,Integer resTypeCode,String resIP ){
		
		Map params=new HashMap();
		StringBuffer hql=new StringBuffer("from AckResources ");
		
		if(classCode!=null){
			hql.append(" where resClassCode=:resClassCode ");
			params.put("resClassCode", classCode);
		}
		
		if(resTypeCode!=null){
			hql.append(" and  resTypeCode=:resTypeCode ");
			params.put("resTypeCode", resTypeCode);
		}
		if(resIP!=null){
			hql.append(" and mgmtIP=:mgmtIP ");
			params.put("mgmtIP", resIP);
		}
		
		Page pageinfo=ackResourcesDao.findAjaxPage(pageRequest,hql.toString(), params);
		return pageinfo;
		
	}
}
