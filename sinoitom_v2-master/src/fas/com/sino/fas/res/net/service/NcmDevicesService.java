package com.sino.fas.res.net.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;

import smartlink.utils.Util;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.net.dao.NcmDevicesDao;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.ParamDevHostMap;

@Transactional
@Service
public class NcmDevicesService {
	
	
	
	private static String objAttNames="orgId,orgId@OrgOrganization@orgId@orgShortName,devId,devTypeCode,devName,devIpAddr,devIpLong,vendorId,vendorId@CmdbVendor@vendorID@dispName,modelOid@VendorProdModel@prodModelOid@prodModelName,modelOid@VendorProdModel@prodModelOid@prodSeries,modelOid@VendorProdModel@prodModelOid@prodTypeName,snmpEnabled@flag_SnmpEnabled,devAccessId@NcmDevAccessUser@devAcsUserId@accessTool,getArpMode,getMacMode,gatewayIP,gatewaySnmp,gatewayType";
	private static String jsonAttNames="orgId,orgName,id,devTypeCode,devName,devIpAddr,devIpLong,vendorId,vendorName,prodModel,prodSeries,typeName,snmpEnabled,devAccess,getArpMode,getMacMode,gatewayIP,gatewaySnmp,gatewayType";

	
	private static String realAttNames="orgId#orgId,devIpAddr#devIpAddr,devName#devName,vendorIdId#vendorId,devTypeCode#devTypeCode,modelOid#modelOid,,modelOid#modelOid,snmpEnabled#snmpEnabled,getArpMode#getArpMode,gatewayIP#gatewayIP";
	private static String orderAttNames="orgName,devIpAddr,devName,vendorId,devTypeCode,modelOid,modelOid,snmpEnabled,getArpMode,gatewayIP";
	@Autowired
	private NcmDevicesDao ncmDevicesDao;
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmDevices obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getDevicePageList(final Page<NcmDevices> page) {
		return JsonUtils.getJQJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmDevices> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevices> searchSwitch(final List<PropertyFilter> filters) {
		return ncmDevicesDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevices> getDeviesListByOrgCode(String treeCode,String devType) {
		String hql ="";
		if(devType.equals("2")||devType.equals("3")){
			 hql = "select a from NcmDevices a, OrgOrganization b where a.orgId=b.orgId and b.treeCode like ? and (a.devTypeCode=? or a.devTypeCode=5)  order by b.treeCode,a.devIpLong, a.devName";
		}else{
			 hql = "select a from NcmDevices a, OrgOrganization b where a.orgId=b.orgId and b.treeCode like ? and a.devTypeCode=? order by b.treeCode,a.devIpLong, a.devName";
		}
		
		return ncmDevicesDao.find(hql,treeCode+"%",Integer.parseInt(devType));
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevices> getDeviesListByCode(String treeCode,String devType) {
		String hql ="";
		if(devType.equals("2") ||devType.equals("3")){
			 hql = "select a from NcmDevices a, OrgOrganization b where a.orgId=b.orgId and b.treeCode = ?  and (a.devTypeCode=? or a.devTypeCode=5) order by b.treeCode,a.devIpLong,a.devName";
		}else{
			 hql = "select a from NcmDevices a, OrgOrganization b where a.orgId=b.orgId and b.treeCode = ?  and a.devTypeCode=? order by b.treeCode,a.devIpLong,a.devName";
		}
		
		return ncmDevicesDao.find(hql,treeCode,Integer.parseInt(devType));
	}
	
	public NcmDevices getById(long devId){
		return ncmDevicesDao.get(devId);
	}
	
	@Transactional(readOnly = true)
	public boolean isDeviceUnique(Long subnetId, String devName, String devIp) {
		return ncmDevicesDao.isIdAttUnique(subnetId, new String[]{"devName","devIpAddr"}, new String[]{devName,devIp});
	}
	
	public void addSwitch(NcmDevices entity) {
		entity.setDevId(Util.getCRC32(entity.getDevIpAddr()));
		ncmDevicesDao.save(entity);
	}
	
	public void saveSwitch(NcmDevices entity) {
		ncmDevicesDao.save(entity);
	}
	
	public void deleteDevice(Long id) {
//		npmDevicePerfPollService.deleteAllDevPoll(id);
		ncmDevicesDao.delete(id);
	}
	
	public List<NcmDevices> getDataByDevIds(List<Long> devIds){
		return ncmDevicesDao.getDataByDevIds(devIds);
	}
	
	public List<NcmDevices> getByOrgAndDevTypeAndVendor(String orgId,int devType,int vendorId){
		String hql = " from NcmDevices where orgId=? and devTypeCode=? and vendorId=?";
		return ncmDevicesDao.find(hql, orgId,devType,vendorId);
	}
	
	//自动巡检-巡检设备页面使用
	@Transactional(readOnly = true)
	public List<NcmDevices> getByVendorId(String vendorId){
		String hql=" from NcmDevices where vendorId=? and devId not in(select resId from AckResources) ";
		System.out.println("HQL---->"+hql);
		return ncmDevicesDao.find(hql, Integer.parseInt(vendorId));
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevices> getByVendorId(String vendorId,String devType){
		String hql=" from NcmDevices where vendorId=? and devTypeCode=? and devId not in(select resId from AckResources)";
		System.out.println("HQL---->"+hql);
		return ncmDevicesDao.find(hql, Integer.parseInt(vendorId),Integer.parseInt(devType));
	}


	public List<NcmDevices> getAll() {
		List<NcmDevices> list = ncmDevicesDao.getAll();
		return list;
	}
	
	public List<NcmDevices> getAllSwitch(){
		String hql=" from NcmDevices where devTypeCode=2 or devTypeCode=5  order by devIpLong" ;
		List<NcmDevices> list=ncmDevicesDao.find(hql);
		return list;
	}
	
	@Transactional(readOnly = true)
	public Long getSwitchIdByIp(String ip) {
		String hql="from NcmDevices where devIpAddr =?";
		List<NcmDevices> list= ncmDevicesDao.find(hql,ip);
		Long swiId=-2L;
		if(list!=null&&!list.isEmpty()){
			swiId=list.get(0).getDevId();
		}
		return swiId;
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevices> listSwitchsByOrgIds(List<String> orgIds) {
		return ncmDevicesDao.getSwitchsByOrgIds(orgIds);
	}
	
	
	public Page<NcmDevices> searchByAjaxPage(PageRequest page,boolean check,String treeCode,String devType){
		Map params=new HashMap();
		params.put("devTypeCode",Integer.parseInt(devType));
		
		String hql ="";
		if(devType.equals("2") ||devType.equals("3")){
			 hql = " select a from NcmDevices a, OrgOrganization b where a.orgId=b.orgId and b.treeCode=:treeCode  and (a.devTypeCode=:devTypeCode or a.devTypeCode=5) ";//order by b.treeCode,a.devIpLong,a.devName
		}else{
			 hql = " select a from NcmDevices a, OrgOrganization b where a.orgId=b.orgId and b.treeCode =:treeCode  and a.devTypeCode=:devTypeCode ";//order by b.treeCode,a.devIpLong,a.devName
		}
		
//		if (page != null && page.getOrderBy() != null) {
//			String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderAttNames, realAttNames);
//			page.setOrderBy(realOrderBy);
//			}
		if(check){
			params.put("treeCode", treeCode+"%");
		}else{
			params.put("treeCode", treeCode);
		}
		Page pageinfo=ncmDevicesDao.findAjaxPage(page,hql.toString(),params);
		return pageinfo;
	}
	
	
	public List getDeviceByIphostId(Set set){
		return ncmDevicesDao.getDeviceByIphostId(set);
	}
	
}
