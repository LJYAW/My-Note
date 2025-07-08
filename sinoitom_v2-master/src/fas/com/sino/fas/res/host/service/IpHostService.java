package com.sino.fas.res.host.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;

import smartlink.utils.DBQueryUtil;
import smartlink.utils.Util;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.HSSFUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.NetworkUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.system.dao.OrgEmployeeDao;
import com.sino.base.system.dao.OrgOrganDao;
import com.sino.base.system.entity.OrgEmployee;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;
import com.sino.fas.res.cablingInfo.service.CablingInfoService;
import com.sino.fas.res.host.dao.IpHostDao;
import com.sino.fas.res.host.entity.IPDevice;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.host.entity.VlanInfo;
import com.sino.fas.res.net.dao.SnmpCredDao;
import com.sino.fas.res.net.dao.NcmDevicesDao;
import com.sino.fas.res.net.dao.SwitchPortDao;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmSwitchPort;
import com.sino.fas.res.net.entity.NcmSwitchPortId;
import com.sino.base.system.service.EmployeeService;
import com.sino.base.system.service.OrganService;
import com.sino.base.system.service.ResItemService;
//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class IpHostService {
	private static Logger logger = LoggerFactory.getLogger(IpHostService.class);
	
	private static String objAttNames = "ipHostId,orgId@OrgOrganization@orgId@orgName,ipHostTypeId@IpHostType,hostName,ipAddr,macAddr,userId@SysUser@userId@loginName,switchId@NcmSwitch@switchId@switchIp,switchIfIndex";
	private static String jsonAttNames = "id,orgId,ipHostTypeId,hostName,ipAddr,macAddr,userId,switchId,switchIfIndex";
	
	private static String excelAttNames = "orgId@OrgOrganization@orgId@orgName,ipHostTypeId@IpHostType,hostName,ipAddr,macAddr,userId@SysUser@userId@loginName,infoPointNo";
	
	private static String ajaxObjAttNames="ipHostId,orgId,orgName,ipHostType,hostName,ipAddr,ipValue,ipNetMask,macAddr,osType,osVersion,snmpRoString,assertNO,loginName,modify_Time,serialNO,switchStatus,switchIp,switchIpLong,switchIfIndex,ifIndexId,isoStatus,switchId,adminStatus,operStatus,ipIndex,infoPointNo,vlanId,vlanName,infoNo,userId";
	private static String ajaxJsonAttNames="id,orgId,orgName,ipHostType,hostName,ipAddr,ipValue,ipNetMask,macAddr,osType,osVersion,snmpRoString,assertNO,loginName,modify_Time,serialNO,switchStatus,switchIp,switchIpLong,switchIfIndex,ifIndexId,isoStatus,switchId,adminStatus,operStatus,ipIndex,infoPointNo,vlanId,vlanName,infoNo,userId";
	
	private static String realAttNames="i.orgId#i.userId#i.ipAddr#i.ipHostTypeId#i.macAddr#s.devIpLong#i.ifIndex#i.macAddr#i.macAddr#i.adminStatus#i.infoPointNo#i.modify_Time";
	private static String orderAttNames="orgName,loginName,ipAddr,ipHostType,macAddr,switchIpLong,switchIfIndex,vlanId,adminStatus,infoNo,modify_Time";
	
	
//	private static String realAttNames="i.orgId,i.userId,i.ipHostTypeId,i.ipAddr,i.macAddr,i.switchId,i.ifIndex,i.infoPointNo,i.infoPointNo,i.infoPointNo,i.modify_Time";
//	private static String orderAttNames="orgName,loginName,ipHostType,ipAddr,macAddr,switchIp,ipIndex,vlanId,adminStatus,infoNo,modify_Time";
	
	
	
	private String lastMassage = "";
	
	@Autowired
	private IpHostDao ipHostDao;
	
	@Autowired
	private SwitchPortDao switchPortDao;
	
	@Autowired
	private OrgOrganDao organDao;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private OrgEmployeeDao employeeDao;
	
	@Autowired
	private NcmDevicesDao ncmDevicesDao;
	
	@Autowired
	private SnmpCredDao snmpCredDao;
	
	@Autowired
	private OrganService organService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private VlanService vlanService;
	
	@Autowired
	private CablingInfoService cablingInfoService;
	
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<IpHost> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
//	@Transactional(readOnly = true)
//	public String getIpHostByAjax(final Page<IpHostInfo> page) {
//		return JsonUtils.getJsonPageInfo(page, ajaxObjAttNames, ajaxJsonAttNames);
//	}
	
	
	@Transactional(readOnly = true)
	public String getIpHostByAjax(final Page<IpHostInfo> page) {
		return JsonUtils.getJQJsonPageInfo(page, ajaxObjAttNames, ajaxJsonAttNames);
	}
	
	public String getIpHostInfoStr(final List<IpHostInfo> list) {
		return JsonUtils.getJsonListInfo(list, ajaxObjAttNames, ajaxJsonAttNames);
	}
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final IpHost obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<IpHost> searchInfo(final List<PropertyFilter> filters) {
		return ipHostDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public List<IpHost> getIpHostList(long switchId,long ifIndex) {
		String hql = "from IpHost where switchId=? and ifIndex=? order by ipAddr";
		return ipHostDao.find(hql, switchId,ifIndex);
	}
	
	@Transactional(readOnly = true)
	public List<NcmSnmpCredentials> getAllsnmpByIds(List ids) {
		return snmpCredDao.getSnmpByIds(ids);
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevices> getAllSwitchByIds(List ids) {
		return ncmDevicesDao.getSwitchsByIds(ids);
	}
	
	@Transactional(readOnly = true)
	public List<IpHost> getAllIpHostService() {
		return ipHostDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public List<IpHost> getAllIpHost() {
		String hql="from IpHost order by switchId,ifIndex";
		return ipHostDao.find(hql);
	}
	
	@Transactional(readOnly = true)
	public IpHost getIpHost(long id){
		return ipHostDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public NcmSwitchPort getSwitchPort(NcmSwitchPortId id) {
		return switchPortDao.get(id);
	}
	
	public void saveIpHost(IpHost IpHost) {
		logger.debug("saveIpHost...");
		ipHostDao.save(IpHost);
	}
	
	public void addIpHost(IpHost IpHost) {
		logger.debug("addIpHost...");
		IpHost.setIpHostId(DBQueryUtil.getInstance().getDeviceResourceUniqId());
		ipHostDao.save(IpHost);
	}
	
	public void deleteIpHost(long id){
		ipHostDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	public String getJsonPageStr(final Page<IpHost> page) {
		return JsonUtils.getJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	
	public void saveIfStatus(NcmSwitchPort entity,int status) {
		entity.setAdminStatus(status);
		switchPortDao.save(entity);
	}
	
	@Transactional(readOnly = true)
	public IpHost getIpHostBy(String macAddr) {
		if( StringUtils.isBlank(macAddr)){
			return null;
		}
		String hql = "from IpHost where macAddr=? order by switchId,ifIndex";
		return ipHostDao.findUnique(hql, macAddr);
	}
	
	@Transactional(readOnly = true)
	public IpHost getIpHostByIpAddr(String ipAddr) {
		if( StringUtils.isBlank(ipAddr)){
			return null;
		}
		String hql = "from IpHost where ipAddr=? ";
		return ipHostDao.findUnique(hql, ipAddr);
	}

	@Transactional(readOnly = true)
	public IpHost getIpHostByIfMac(String switchIp,String ifIndex,String macAddr) {
		if( StringUtils.isBlank(macAddr)){
			return null;
		}
		String hql = "from IpHost h where h.macAddr=? and h.ifIndex=? ";
		List<IpHost> list=ipHostDao.find(hql, macAddr,Long.valueOf(ifIndex));
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public Page<IpHost> searchIpHost(final PageRequest page,
			final List<PropertyFilter> filters) {
		return ipHostDao.findPage(page, filters);
	}
	
	@Transactional(readOnly = true)
	public List<IpHostInfo> getByIp(String ip) {
		//System.out.println("ip地址是："+ip);
		if( StringUtils.isBlank(ip)){
			return null;
		}
		String hql = "from IpHost where ipAddr like ? order by switchId,ifIndex";
		List<IpHost> ipHostList =ipHostDao.find(hql, ip);
		List<IpHostInfo> listInfo=new ArrayList<IpHostInfo>();
		for(int i=0;i<ipHostList.size();i++){
			IpHost host=ipHostList.get(i);
			IpHostInfo iphostinfo=new IpHostInfo();
			OrgOrganization org=new OrgOrganization();
			
			OrgEmployee user =new OrgEmployee();
			NcmDevices ncmSwitch=new NcmDevices();
			NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
			NcmSwitchPortId id =new NcmSwitchPortId();
			id.setSwitchId(host.getSwitchId());
			id.setIfIndex(host.getIfIndex());
			
			String orgName="";
			String userName="";
			//String switchStatus="";
			String switchIp="";
			String ifName="";
			String isoStatus="";
			String adminStatus="";
			String operStatus="";
			
			org=organDao.get(host.getOrgId());
			user=employeeDao.get(host.getUserId());
			ncmSwitch=ncmDevicesDao.get(host.getSwitchId()==null?0L:host.getSwitchId());
			ncmSwitchPort=switchPortDao.get(id);
			if(org!=null)
				orgName=org.getOrgName();
			if(user!=null)
				userName=user.getName();
			if(ncmSwitch!=null){
				switchIp=ncmSwitch.getDevIpAddr();
				//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
			}
			if(ncmSwitchPort!=null){
				ifName=ncmSwitchPort.getIfName();
			    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
			    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
			    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
			}
			String ifindex=String.valueOf(host.getIfIndex()+100000);
			ifindex=host.getSwitchId()+"." +ifindex;
			iphostinfo.setIpHostId(String.valueOf(host.getIpHostId()));
			iphostinfo.setOrgName(orgName);
			iphostinfo.setHostName(host.getHostName());
			iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(host.getIpHostTypeId())));
			iphostinfo.setIpAddr(host.getIpAddr());
			iphostinfo.setMacAddr(host.getMacAddr());
			iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(host.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
			iphostinfo.setLoginName(userName);
			iphostinfo.setSwitchIp(switchIp);
			iphostinfo.setSwitchIfIndex(ifName);
			iphostinfo.setIfIndexId(String.valueOf(host.getIfIndex()));
			iphostinfo.setSwitchId(String.valueOf(host.getSwitchId()));
			iphostinfo.setIsoStatus(isoStatus);
			iphostinfo.setAdminStatus(adminStatus);
			iphostinfo.setOperStatus(operStatus);
			iphostinfo.setIpIndex(ifindex);
			listInfo.add(iphostinfo);
		}
		return listInfo;
	}
	
	@Transactional(readOnly = true)
	public boolean isUnique(String macAddr){
		boolean flag=true;
		List<IpHost> listhost=getAllIpHost();
		if(listhost!=null&&listhost.size()>0)
			for(int i=0;i<listhost.size();i++){
				flag=ipHostDao.isIdAttUnique(listhost.get(i).getIpHostId(), new String[]{"macAddr"}, new String[]{macAddr});
				if(flag==false){
					break;
				}
		}
		return flag;
	}
	
	@Transactional(readOnly = true)
	public boolean isIpHostUnique(String ipHostId, String macAddr) {
		if(StringUtil.isNullString(ipHostId))
			return  true;
		return ipHostDao.isIdAttUnique(Long.valueOf(ipHostId), new String[]{"macAddr"}, new String[]{macAddr});
	}
	public void multiSaveIpHost(IpHost ipHost,String propt,String value) {
		Date now = new Date();
		ipHost.setModify_Time(now);
		if(propt.equals("orgOption")){
			ipHost.setOrgId(value);
		}else if(propt.equals("hostTypeOption")){
			ipHost.setIpHostTypeId(Integer.parseInt(value));
		}else if(propt.equals("snmpRoStrOption")){
			ipHost.setSnmpRoString(value);
		}else if(propt.equals("ipMaskOption")){
			ipHost.setIpNetMask(value);
		}else if(propt.equals("osClassOption")){
			if(Util.isNumeric(value)){
				ipHost.setOsClassCode(Integer.parseInt(value));
			}else{
				ipHost.setOsClass(value);
			}
			
		}else if(propt.equals("osTypeOption")){
			ipHost.setOsType(value);
		}else if(propt.equals("osVersionOption")){
			ipHost.setOsVersion(value);
		}else if(propt.equals("osFeatureOption")){
			ipHost.setOsFeature(value);
		}
		else if(propt.equals("accessnOption")){
			ipHost.setDevAccessId(value);
		}
		ipHostDao.save(ipHost);
	}
	
	
	
	@Transactional(readOnly = true)
	public List<IpHostInfo> getListIpHost(){
		List<IpHostInfo> listInfo=new ArrayList<IpHostInfo>();
		List<IpHost> ipHostList =getAllIpHost();
		for(int i=0;i<ipHostList.size();i++){
			IpHost host=ipHostList.get(i);
			IpHostInfo iphostinfo=new IpHostInfo();
			OrgOrganization org=new OrgOrganization();
			
			OrgEmployee user =new OrgEmployee();
			NcmDevices ncmSwitch=new NcmDevices();
			NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
			NcmSwitchPortId id =new NcmSwitchPortId();
			id.setSwitchId(host.getSwitchId());
			id.setIfIndex(host.getIfIndex());
			
			String orgName="";
			String userName="";
			String switchIp="";
			String ifName="";
			String isoStatus="";
			String adminStatus="";
			String operStatus="";
			org=organDao.get(host.getOrgId());
			user=employeeDao.get(host.getUserId());
			ncmSwitch=ncmDevicesDao.get(host.getSwitchId()==null?0L:host.getSwitchId());
			ncmSwitchPort=switchPortDao.get(id);
			String vlan="";
			String vlanId="";
			VlanInfo vlaninfo=null;
			NcmCablingInfo info=null;
			if(host.getSwitchId()!=null&&host.getIfIndex()!=null){
				vlaninfo=vlanService.getBy(host.getSwitchId(), host.getIfIndex());
				info=cablingInfoService.getCablingInfoBy(host.getSwitchId(),host.getIfIndex());
			}
			if(vlaninfo!=null){
				vlan=vlaninfo.getVlan_Name();
				vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
			}
			if(org!=null)
				orgName=org.getOrgName();
			if(user!=null)
				userName=user.getName();
			if(ncmSwitch!=null){
				switchIp=ncmSwitch.getDevIpAddr();
				//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
			}
			if(ncmSwitchPort!=null){
				ifName=ncmSwitchPort.getIfName();
			    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
			    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
			    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
			}
			String infoNo="";
			String infoPointNo="";
			if(info!=null){
				infoNo=info.getInfoPointNo();
				infoPointNo=info.getCablingInfoID();
			}
			
			String ifindex=String.valueOf((host.getIfIndex()!=null?host.getIfIndex():0)+100000);
			ifindex=host.getSwitchId()+"." +ifindex;
			iphostinfo.setIpHostId(String.valueOf(host.getIpHostId()));
			iphostinfo.setOrgName(orgName);
			iphostinfo.setHostName(host.getHostName());
			iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(host.getIpHostTypeId())));
			iphostinfo.setIpAddr(host.getIpAddr());
			iphostinfo.setMacAddr(host.getMacAddr());
			iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(host.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
			iphostinfo.setLoginName(userName);
			iphostinfo.setSwitchIp(switchIp);
			iphostinfo.setSwitchIfIndex(ifName);
			iphostinfo.setIfIndexId(String.valueOf(host.getIfIndex()));
			iphostinfo.setSwitchId(String.valueOf(host.getSwitchId()));
			iphostinfo.setIsoStatus(isoStatus);
			iphostinfo.setAdminStatus(adminStatus);
			iphostinfo.setOperStatus(operStatus);
			iphostinfo.setIpIndex(ifindex);
			iphostinfo.setVlanName(vlan);
			iphostinfo.setVlanId(vlanId);
			iphostinfo.setInfoPointNo(infoPointNo);
			iphostinfo.setInfoNo(infoNo);
			listInfo.add(iphostinfo);
		}
		return listInfo;
	}
	
	@Transactional(readOnly = true)
	public List<IpHostInfo> getListIpHost(List<String> orgIds,long sIp){
		//System.out.println("get the sIp:>>"+sIp);
		List<IpHostInfo> listInfo=new ArrayList<IpHostInfo>();
		List<IpHost> ipHostList =new ArrayList<IpHost>();
		if(orgIds!=null&&!orgIds.isEmpty()){
			ipHostList=ipHostDao.getIphostByOrgId(orgIds, sIp);
		}
		for(int i=0;i<ipHostList.size();i++){
			IpHost host=ipHostList.get(i);
			IpHostInfo iphostinfo=new IpHostInfo();
			OrgOrganization org=new OrgOrganization();
			
			OrgEmployee user =new OrgEmployee();
			NcmDevices ncmSwitch=new NcmDevices();
			NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
			NcmSwitchPortId id =new NcmSwitchPortId();
			id.setSwitchId(host.getSwitchId());
			id.setIfIndex(host.getIfIndex());
			
			String orgName="";
			String userName="";
			//String switchStatus="";
			String switchIp="";
			String ifName="";
			String isoStatus="";
			String adminStatus="";
			String operStatus="";
			
			String infoNo="";
			String infoPointNo="";
			NcmCablingInfo info=null;
			if(host.getSwitchId()!=null&&host.getIfIndex()!=null){
				info=cablingInfoService.getCablingInfoBy(host.getSwitchId(),host.getIfIndex());
			}
			if(info!=null){
				infoNo=info.getInfoPointNo();
				infoPointNo=info.getCablingInfoID();
			}
			
			org=organDao.get(host.getOrgId());
			user=employeeDao.get(host.getUserId());
			ncmSwitch=ncmDevicesDao.get(host.getSwitchId()==null?0L:host.getSwitchId());
			ncmSwitchPort=switchPortDao.get(id);
			String vlan="";
			String vlanId="";
			VlanInfo vlaninfo=vlanService.getBy(host.getSwitchId(), host.getIfIndex());
			if(vlaninfo!=null){
				vlan=vlaninfo.getVlan_Name();
				vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
			}
			if(org!=null)
				orgName=org.getOrgName();
			if(user!=null)
				userName=user.getName();
			if(ncmSwitch!=null){
				switchIp=ncmSwitch.getDevIpAddr();
				//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
			}
			
			if(ncmSwitchPort!=null){
				ifName=ncmSwitchPort.getIfName();
			    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
			    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
			    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
			}
			
			String ifindex=String.valueOf((host.getIfIndex()!=null?host.getIfIndex():0)+100000);
			ifindex=host.getSwitchId()+"." +ifindex;
			iphostinfo.setIpHostId(String.valueOf(host.getIpHostId()));
			iphostinfo.setOrgName(orgName);
			iphostinfo.setHostName(host.getHostName());
			iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(host.getIpHostTypeId())));
			iphostinfo.setIpAddr(host.getIpAddr());
			iphostinfo.setMacAddr(host.getMacAddr());
			iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(host.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
			iphostinfo.setLoginName(userName);
			iphostinfo.setSwitchIp(switchIp);
			iphostinfo.setSwitchIfIndex(ifName);
			iphostinfo.setIfIndexId(String.valueOf(host.getIfIndex()));
			iphostinfo.setSwitchId(String.valueOf(host.getSwitchId()));
			iphostinfo.setIsoStatus(isoStatus);
			iphostinfo.setAdminStatus(adminStatus);
			iphostinfo.setOperStatus(operStatus);
			iphostinfo.setIpIndex(ifindex);
			iphostinfo.setVlanName(vlan);
			iphostinfo.setVlanId(vlanId);
			iphostinfo.setInfoPointNo(infoPointNo);
			iphostinfo.setInfoNo(infoNo);
			listInfo.add(iphostinfo);
		}
		return listInfo;
	}
	
	@Transactional(readOnly = true)
	public IpHostInfo getListIpHost(long ipHostId){
			IpHost ipHost =ipHostDao.get(ipHostId);
			IpHostInfo iphostinfo=new IpHostInfo();
			OrgOrganization org=new OrgOrganization();
			
			OrgEmployee user =new OrgEmployee();
			NcmDevices ncmSwitch=new NcmDevices();
			NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
			NcmSwitchPortId id =new NcmSwitchPortId();
			id.setSwitchId(ipHost.getSwitchId());
			id.setIfIndex(ipHost.getIfIndex());
			
			String orgName="";
			String userName="";
			String switchIp="";
			String ifName="";
			String isoStatus="";
			String adminStatus="";
			String operStatus="";
			
			String infoNo="";
			String infoPointNo="";
			NcmCablingInfo info=null;
			VlanInfo vlaninfo=null;
			if(ipHost.getSwitchId()!=null&&ipHost.getIfIndex()!=null){
				info=cablingInfoService.getCablingInfoBy(ipHost.getSwitchId(),ipHost.getIfIndex());
				vlaninfo=vlanService.getBy(ipHost.getSwitchId(), ipHost.getIfIndex());
			}
			if(info!=null){
				infoNo=info.getInfoPointNo();
				infoPointNo=info.getCablingInfoID();
			}
			
			org=organDao.get(ipHost.getOrgId());
			user=employeeDao.get(ipHost.getUserId());
			ncmSwitch=ncmDevicesDao.get(ipHost.getSwitchId()==null?0L:ipHost.getSwitchId());
			ncmSwitchPort=switchPortDao.get(id);
			String vlan="";
			String vlanId="";
			
			if(vlaninfo!=null){
				vlan=vlaninfo.getVlan_Name();
				vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
			}
			if(org!=null)
				orgName=org.getOrgName();
			if(user!=null)
				userName=user.getName();
			if(ncmSwitch!=null)
				switchIp=ncmSwitch.getDevIpAddr();
			if(ncmSwitchPort!=null){
				ifName=ncmSwitchPort.getIfName();
			    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
			    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
			    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
			}
			String ifindex=String.valueOf((ipHost.getIfIndex()!=null?ipHost.getIfIndex():0)+100000);
			ifindex=ipHost.getSwitchId()+"." +ifindex;
			iphostinfo.setSwitchId(ipHost.getSwitchId()!=null?String.valueOf(ipHost.getSwitchId()):"0");
			iphostinfo.setIpHostId(String.valueOf(ipHost.getIpHostId()));
			iphostinfo.setOrgName(orgName);
			iphostinfo.setHostName(ipHost.getHostName());
			iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ipHost.getIpHostTypeId())));
			iphostinfo.setIpAddr(ipHost.getIpAddr());
			iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(ipHost.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
			iphostinfo.setMacAddr(ipHost.getMacAddr());
			iphostinfo.setIpNetMask(ipHost.getIpNetMask());
			iphostinfo.setSnmpRoString(ipHost.getSnmpRoString());
			iphostinfo.setSerialNO(ipHost.getSerialNO());
			iphostinfo.setAssertNO(ipHost.getAssertNO());
			iphostinfo.setOsClass(ipHost.getOsClass());
			iphostinfo.setOsType(ipHost.getOsType());
			iphostinfo.setOsVersion(ipHost.getOsVersion());
			iphostinfo.setOsFeature(ipHost.getOsFeature());
			iphostinfo.setLoginName(userName);
			iphostinfo.setSwitchIp(switchIp);
			iphostinfo.setSwitchIfIndex(ifName);
			iphostinfo.setIfIndexId(String.valueOf(ipHost.getIfIndex()));
			
			iphostinfo.setAdminStatus(adminStatus);
			iphostinfo.setOperStatus(operStatus);
			iphostinfo.setIsoStatus(isoStatus);
			iphostinfo.setIpIndex(ifindex);
			iphostinfo.setInfoPointNo(infoPointNo);
			iphostinfo.setVlanName(vlan);
			iphostinfo.setVlanId(vlanId);
			iphostinfo.setInfoNo(infoNo);
		return iphostinfo;
	}
	
	
	@Transactional(readOnly = true)
	public IpHostInfo getListIpHost(long ipHostId,IpHost host){
			IpHost ipHost =ipHostDao.get(ipHostId);
			IpHostInfo iphostinfo=new IpHostInfo();
			OrgOrganization org=new OrgOrganization();
			
			OrgEmployee user =new OrgEmployee();
			NcmDevices ncmSwitch=new NcmDevices();
			NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
			NcmSwitchPortId id =new NcmSwitchPortId();
			id.setSwitchId(ipHost.getSwitchId());
			id.setIfIndex(ipHost.getIfIndex());
			
			String orgName="";
			String userName="";
			String switchIp="";
			String ifName="";
			String isoStatus="";
			String adminStatus="";
			String operStatus="";
			
			String infoNo="";
			String infoPointNo="";
			NcmCablingInfo info=null;
			VlanInfo vlaninfo=null;
			if(ipHost.getSwitchId()!=null&&ipHost.getIfIndex()!=null){
				info=cablingInfoService.getCablingInfoBy(ipHost.getSwitchId(),ipHost.getIfIndex());
				vlaninfo=vlanService.getBy(ipHost.getSwitchId(), ipHost.getIfIndex());
			}
			if(info!=null){
				infoNo=info.getInfoPointNo();
				infoPointNo=info.getCablingInfoID();
			}
			
			org=organDao.get(ipHost.getOrgId());
			user=employeeDao.get(ipHost.getUserId());
			ncmSwitch=ncmDevicesDao.get(ipHost.getSwitchId()==null?0L:ipHost.getSwitchId());
			ncmSwitchPort=switchPortDao.get(id);
			String vlan="";
			String vlanId="";
			
			if(vlaninfo!=null){
				vlan=vlaninfo.getVlan_Name();
				vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
			}
			if(org!=null)
				orgName=org.getOrgName();
			if(user!=null)
				userName=user.getName();
			if(ncmSwitch!=null)
				switchIp=ncmSwitch.getDevIpAddr();
			if(ncmSwitchPort!=null){
				ifName=ncmSwitchPort.getIfName();
			    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
			    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
			    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
			}
			String ifindex=String.valueOf((ipHost.getIfIndex()!=null?ipHost.getIfIndex():0)+100000);
			ifindex=ipHost.getSwitchId()+"." +ifindex;
			iphostinfo.setSwitchId(ipHost.getSwitchId()!=null?String.valueOf(ipHost.getSwitchId()):"0");
			iphostinfo.setIpHostId(String.valueOf(ipHost.getIpHostId()));
			iphostinfo.setOrgName(orgName);
			iphostinfo.setHostName(ipHost.getHostName());
			iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ipHost.getIpHostTypeId())));
			iphostinfo.setIpAddr(ipHost.getIpAddr());
			iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(ipHost.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
			iphostinfo.setMacAddr(ipHost.getMacAddr());
			iphostinfo.setIpNetMask(ipHost.getIpNetMask());
			iphostinfo.setSnmpRoString(ipHost.getSnmpRoString());
			iphostinfo.setSerialNO(ipHost.getSerialNO());
			iphostinfo.setAssertNO(ipHost.getAssertNO());
			iphostinfo.setOsType(resItemService.findResContent("HostOSType",String.valueOf(ipHost.getOsType())));
			iphostinfo.setOsVersion(ipHost.getOsVersion());
			iphostinfo.setLoginName(userName);
			iphostinfo.setSwitchIp(switchIp);
			iphostinfo.setSwitchIfIndex(ifName);
			iphostinfo.setIfIndexId(String.valueOf(ipHost.getIfIndex()));
			iphostinfo.setAdminStatus(adminStatus);
			iphostinfo.setOperStatus(operStatus);
			iphostinfo.setIsoStatus(isoStatus);
			iphostinfo.setIpIndex(ifindex);
			iphostinfo.setInfoPointNo(infoPointNo);
			iphostinfo.setVlanName(vlan);
			iphostinfo.setVlanId(vlanId);
			iphostinfo.setInfoNo(infoNo);
		return iphostinfo;
	}
	
	@Transactional(readOnly = true)
	public IpHostInfo getIpHostStatus(long ipHostId,String status){
			IpHost ipHost =ipHostDao.get(ipHostId);
			IpHostInfo iphostinfo=new IpHostInfo();
			OrgOrganization org=new OrgOrganization();
			
			OrgEmployee user =new OrgEmployee();
			NcmDevices ncmSwitch=new NcmDevices();
			NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
			NcmSwitchPortId id =new NcmSwitchPortId();
			id.setSwitchId(ipHost.getSwitchId());
			id.setIfIndex(ipHost.getIfIndex());
			
			String orgName="";
			String userName="";
			String switchIp="";
			String ifName="";
			String isoStatus="";
			String adminStatus="";
			String operStatus="";

			String infoNo="";
			String infoPointNo="";
			NcmCablingInfo info=null;
			if(ipHost.getSwitchId()!=null&&ipHost.getIfIndex()!=null){
				info=cablingInfoService.getCablingInfoBy(ipHost.getSwitchId(),ipHost.getIfIndex());
			}
			if(info!=null){
				infoNo=info.getInfoPointNo();
				infoPointNo=info.getCablingInfoID();
			}
			
			org=organDao.get(ipHost.getOrgId());
			user=employeeDao.get(ipHost.getUserId());
			ncmSwitch=ncmDevicesDao.get(ipHost.getSwitchId()==null?0L:ipHost.getSwitchId());
			ncmSwitchPort=switchPortDao.get(id);
			String vlan="";
			String vlanId="";
			VlanInfo vlaninfo=vlanService.getBy(ipHost.getSwitchId(), ipHost.getIfIndex());
			if(vlaninfo!=null){
				vlan=vlaninfo.getVlan_Name();
				vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
			}
			if(org!=null)
				orgName=org.getOrgName();
			if(user!=null)
				userName=user.getName();
			if(ncmSwitch!=null)
				switchIp=ncmSwitch.getDevIpAddr();
			if(ncmSwitchPort!=null){
				ifName=ncmSwitchPort.getIfName();
			    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
			    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
			    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
			}
			
			String ifindex=String.valueOf((ipHost.getIfIndex()!=null?ipHost.getIfIndex():0)+100000);
			ifindex=ipHost.getSwitchId()+"." +ifindex;
			iphostinfo.setIpHostId(String.valueOf(ipHost.getIpHostId()));
			iphostinfo.setOrgName(orgName);
			iphostinfo.setHostName(ipHost.getHostName());
			iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ipHost.getIpHostTypeId())));
			iphostinfo.setIpAddr(ipHost.getIpAddr());
			iphostinfo.setMacAddr(ipHost.getMacAddr());
			iphostinfo.setLoginName(userName);
			iphostinfo.setSwitchIp(switchIp);
			iphostinfo.setSwitchId(String.valueOf(ipHost.getSwitchId()));
			iphostinfo.setSwitchIfIndex(ifName);
			iphostinfo.setIfIndexId(String.valueOf(ipHost.getIfIndex()));
			iphostinfo.setIsoStatus(status);
			iphostinfo.setAdminStatus(adminStatus);
			iphostinfo.setOperStatus(operStatus);
			iphostinfo.setIpIndex(ifindex);
			iphostinfo.setInfoPointNo(infoPointNo);
			iphostinfo.setVlanName(vlan);
			iphostinfo.setVlanId(vlanId); 
			iphostinfo.setInfoNo(infoNo);
		return iphostinfo;
	}
	
	
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		ExcelUtil<IpHost> excelUtil = new ExcelUtil<IpHost>(IpHost.class);
		List<IpHost> list = excelUtil.getObjListFrom(stream, excelAttNames);
		if( list==null || list.size()==0 ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			return false;
		}
		Date now = new Date();//获取系统当前时间
		int addNum=0;
		for(int i=0; i<list.size(); i++){
			IpHost ipHost = list.get(i);
			ipHost.setModify_Time(now);
			ipHost.setIpValue(Util.ip2long(ipHost.getIpAddr()));
			addIpHost(ipHost);
			addNum++;
		}

		this.lastMassage = String.format("导入完毕！新增%d条记录。", addNum);
		return true;
	}
	
	public boolean impExcel(InputStream stream) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
		HSSFSheet sheet = workbook.getSheetAt(0);// 获得一个sheet

		HSSFRow row = sheet.getRow(0);// 获取第一列
		int rowNum = sheet.getLastRowNum();// 获取总行数
		int CuRowCell = row.getLastCellNum();// 获取总列数
		String firstVal = HSSFUtil.getStringValue(row.getCell((short)(CuRowCell - 1)),"");// 获取列数
		int lastCell = (firstVal == null || firstVal == "" ? CuRowCell - 1 : CuRowCell);// 判断总列数

		List<IpHost> iphostlist = new ArrayList<IpHost>();
		// 把excel第一列（即标头）值 全部放在map中 key是标头值， value是列数
		HSSFCell val = null;
		Map<String, String> header = new HashMap<String, String>();
		for(int i = 0; i < lastCell; i++)
		{
			val = row.getCell((short)i);
			header.put(StringUtil.StringFilter(val.toString()),String.valueOf(i));
		}
		int addNum=0;
		int updateNum=0;
		for(int j = 1; j < rowNum + 1; j++)
		{
			Date now = new Date();//获取系统当前时间
			IpHost ipHost=new IpHost();
			HSSFRow rowData = sheet.getRow(j);
			int rowCount = j + 1;
			System.out.println("获取到的行数:>"+rowCount);
			String orgName = "";// 机构
			String ipHostType = "";// 设备类型
			String hostName = "";// 设备名称
			String ipAddr = "";// ip地址
			String macAddr = "";// mac地址
			String user="";//使用人
			String infoPointNo="";
			String serialNO ="";//序列号
			OrgOrganization org=new OrgOrganization();
			OrgEmployee emp=new OrgEmployee();
			
			
			if(!StringUtil.isNullString(header.get("机构名称")))
			{
				int a = Integer.parseInt(header.get("机构名称"));
				orgName =StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1")) ;
				org =organService.getOrganByName(orgName);//获取机构信息
				if(!StringUtil.isNullString(orgName)&&org!=null)
				{
					ipHost.setOrgId(org.getOrgId());
				}else{
					this.lastMassage = "第" + rowCount + "行,未找到_" + orgName + "_机构名";
					return false;
				}
			}else
			{
				this.lastMassage = "需要机构名称字段";
				return false;
			}
			
			if(!StringUtil.isNullString(header.get("设备类型")))
			{
				int a = Integer.parseInt(header.get("设备类型"));
				ipHostType =StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1")) ;
				String resCode=resItemService.findResCode("IpHostType", ipHostType);//获取机构信息
				if(!StringUtil.isNullString(resCode))
				{
					ipHost.setIpHostTypeId(Integer.parseInt(resCode));
				}
			}
			
			if(!StringUtil.isNullString(header.get("主机名称")))
			{
				int a = Integer.parseInt(header.get("主机名称"));
				hostName =StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1")) ;
				ipHost.setHostName(hostName);
			}
			
			if(!StringUtil.isNullString(header.get("信息点编号")))
			{
				int a = Integer.parseInt(header.get("信息点编号"));
				infoPointNo =StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1")) ;
				ipHost.setInfoPointNo(infoPointNo);
			}
			
			if(!StringUtil.isNullString(header.get("IP地址")))
			{
				int a = Integer.parseInt(header.get("IP地址"));
				ipAddr = StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1"));
				if(!StringUtil.isNullString(ipAddr)){
					if(NetworkUtils.isValidIP(ipAddr))
					{
						ipHost.setIpValue(Util.ip2long(ipAddr));
						ipHost.setIpAddr(ipAddr);
					}else{
						this.lastMassage = "第" + rowCount + "行,IP_" + ipAddr + "_有误";
						return false;
					}
				}
			}
			
			if(!StringUtil.isNullString(header.get("MAC地址")))
			{
				int a = Integer.parseInt(header.get("MAC地址"));
				macAddr = StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1"));
				if(NetworkUtils.isValidMAC(macAddr))
				{  
					ipHost.setMacAddr(macAddr);
					/*if(isUnique(macAddr)){
						ipHost.setMacAddr(macAddr);
					}else{
						this.lastMassage = "第" + rowCount + "行," + macAddr + "_mac地址已经存在";
						return false;
					}*/
				}else{
					this.lastMassage = "第" + rowCount + "行," + macAddr + "_mac地址有误";
					return false;
				}
			}else
			{
				this.lastMassage = "需要MAC地址字段";
				return false;
			}
			
			if(!StringUtil.isNullString(header.get("使用人")))
			{
				int a = Integer.parseInt(header.get("使用人"));
				user = StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1"));
				emp =employeeService.getEmpByName(user);//获取机构信息
				if(!StringUtil.isNullString(user)&&org!=null&&emp!=null)
				{
					ipHost.setUserId(emp.getEmpId());
				}
				/*else{
					this.lastMassage = "第" + rowCount + "行,未找到_" + user + "_使用人";
					return false;
				}*/
			}
			/*else
			{
				this.lastMassage = "需要使用人字段";
				return false;
			}*/
			
			if(!StringUtil.isNullString(header.get("序列号")))
			{
				int a = Integer.parseInt(header.get("序列号"));
				serialNO =StringUtil.trimAll(HSSFUtil.getStringValue(rowData.getCell((short)a),"1")) ;
				if(!StringUtil.isNullString(serialNO)){
						ipHost.setSerialNO(serialNO);
				}
			}
			
			IpHost ipHostFind=getIpHostBy(macAddr);
			if(ipHostFind!=null){
				ipHostFind.setModify_Time(now);
				BeanUtils.copyProperties(ipHost, ipHostFind);
				iphostlist.add(ipHostFind);
				updateNum++;
			}else{
				ipHost.setIpHostId(DBQueryUtil.getInstance().getDeviceResourceUniqId());
				iphostlist.add(ipHost);
				addNum++;
			}
			
		}
		ipHostDao.batchSave(iphostlist, 50);
		this.lastMassage = String.format("导入完毕！新增%d条记录,更新%d条记录!", addNum,updateNum);
		return true;
	}
	
	
	
	
	//Ajax分页查询
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public Page<IpHostInfo> searchByAjax(PageRequest page){
		
		List<IpHostInfo> IpHostInfos=new ArrayList<IpHostInfo>();
		
		//StringBuffer hql=new StringBuffer("select i,v from IpHost i,VlanInfo v where i.orgId<>:id and i.switchId=v.switch_Id and i.ifIndex=v.ifIndex ");
		StringBuffer hql=new StringBuffer("select i from IpHost i,NcmDevices s where i.orgId<>:id and i.switchId=s.devId ");
		//System.out.println("hql------>"+hql.toString());
		//sql参数
		Map params=new HashMap();
		params.put("id", "-1");
		
		if (page != null && page.getOrderBy() != null) {
			String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderAttNames, realAttNames);
			page.setOrderBy(realOrderBy);
			}
		
		Page pageinfo=ipHostDao.findAjaxPage(page,hql.toString(),params);
		List list=pageinfo.getResult();
		Iterator it  =  list.iterator();
		
		//多表关联查询实体封装
		while(it.hasNext()){
			 //Object[] tuple  =  (Object[]) it.next(); 
			 IpHost ip=(IpHost)it.next();
			 //VlanInfo vlaninfo=(VlanInfo)tuple[1];	
			  Long switchId=ip.getSwitchId()==null?0l:ip.getSwitchId();
			  Long ifIndexId=ip.getIfIndex()==null?0l:ip.getIfIndex();
			  
			   VlanInfo vlaninfo=vlanService.getBy(switchId, ifIndexId);
				IpHostInfo iphostinfo=new IpHostInfo();
				OrgOrganization org=new OrgOrganization();
				
				OrgEmployee user =new OrgEmployee();
				NcmDevices ncmSwitch=new NcmDevices();
				NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
				NcmSwitchPortId id =new NcmSwitchPortId();
				id.setSwitchId(ip.getSwitchId());
				id.setIfIndex(ip.getIfIndex());
				
				String orgName="";
				String userName="";
				//String switchStatus="";
				String switchIp="";
				String ifName="";
				String isoStatus="";
				String adminStatus="";
				String operStatus="";
				String switchIpLong="";
				String infoNo="";
				String infoPointNo="";
				NcmCablingInfo info=null;
//				VlanInfo vlaninfo=null;
//				if(ip.getSwitchId()!=null&&ip.getIfIndex()!=null){
//					info=cablingInfoService.getCablingInfoBy(ip.getSwitchId(),ip.getIfIndex());
//					vlaninfo=vlanService.getBy(ip.getSwitchId(), ip.getIfIndex());
//				}
//				
				info=cablingInfoService.getCablingInfoBy(switchId,ifIndexId);
				if(info!=null){
					infoNo=info.getInfoPointNo();
					infoPointNo=info.getCablingInfoID();
				}
				org=organDao.get(ip.getOrgId());
				user=employeeDao.get(StringUtil.isNullString(ip.getUserId())?"-1":ip.getUserId());
				ncmSwitch=ncmDevicesDao.get(switchId);
				ncmSwitchPort=switchPortDao.get(id);
				String vlan="";
				String vlanId="";
				
				if(vlaninfo!=null){
					vlan=vlaninfo.getVlan_Name();
					vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
				}
				if(org!=null)
					orgName=org.getOrgShortName();
				if(user!=null)
					userName=user.getName();
				if(ncmSwitch!=null){
					switchIp=ncmSwitch.getDevIpAddr();
					switchIpLong=String.valueOf(ncmSwitch.getDevIpLong());
					//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
				}
				
				if(ncmSwitchPort!=null){
					ifName=ncmSwitchPort.getIfName();
				    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
				    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
				    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
				}
				
				String ifindex=String.valueOf((ip.getIfIndex()!=null?ip.getIfIndex():0)+100000);
				ifindex=switchId+"." +ifindex;
				iphostinfo.setIpHostId(String.valueOf(ip.getIpHostId()));
				iphostinfo.setOrgName(orgName);
				iphostinfo.setHostName(ip.getHostName());
				iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ip.getIpHostTypeId())));
				iphostinfo.setIpAddr(ip.getIpAddr());
				iphostinfo.setMacAddr(ip.getMacAddr());
				iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(ip.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
				iphostinfo.setLoginName(userName);
				iphostinfo.setSwitchIp(switchIp);
				iphostinfo.setSwitchIfIndex(ifName);
				iphostinfo.setIfIndexId(String.valueOf(ip.getIfIndex()));
				iphostinfo.setSwitchId(String.valueOf(ip.getSwitchId()));
				iphostinfo.setSwitchIpLong(switchIpLong);
				iphostinfo.setIsoStatus(isoStatus);
				iphostinfo.setAdminStatus(adminStatus);
				iphostinfo.setOperStatus(operStatus);
				iphostinfo.setIpIndex(ifindex);
				iphostinfo.setVlanName(vlan);
				iphostinfo.setVlanId(vlanId);
				iphostinfo.setInfoPointNo(infoPointNo);
				iphostinfo.setInfoNo(infoNo);
				IpHostInfos.add(iphostinfo);
		}
		
		pageinfo.setResult(IpHostInfos);
		return pageinfo;
		
	}
	
	
	
	
	//Ajax分页查询（之前查询方法，用到orgID）
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly = true)
		public List<IpHostInfo> getHostInfos(int typeCode){
			
			List<IpHostInfo> IpHostInfos=new ArrayList<IpHostInfo>();
			
			List list=ipHostDao.getIphosts(typeCode);
			
			Iterator it  =  list.iterator();
			
			//多表关联查询实体封装
			while(it.hasNext()){
				 //Object[] tuple  =  (Object[]) it.next(); 
				 IpHost ip=(IpHost)it.next();
				 //VlanInfo vlaninfo=(VlanInfo)tuple[1];	
				  Long switchId=ip.getSwitchId()==null?0l:ip.getSwitchId();
				  Long ifIndexId=ip.getIfIndex()==null?0l:ip.getIfIndex();
				  
				   VlanInfo vlaninfo=vlanService.getBy(switchId, ifIndexId);
					IpHostInfo iphostinfo=new IpHostInfo();
					OrgOrganization org=new OrgOrganization();
					
					OrgEmployee user =new OrgEmployee();
					NcmDevices ncmSwitch=new NcmDevices();
					NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
					NcmSwitchPortId id =new NcmSwitchPortId();
					id.setSwitchId(ip.getSwitchId());
					id.setIfIndex(ip.getIfIndex());
					
					String orgName="";
					String userName="";
					//String switchStatus="";
					String switchIp="";
					String ifName="";
					String isoStatus="";
					String adminStatus="";
					String operStatus="";
					String switchIpLong="";
					String infoNo="";
					String infoPointNo="";
					NcmCablingInfo info=null;
//					VlanInfo vlaninfo=null;
//					if(ip.getSwitchId()!=null&&ip.getIfIndex()!=null){
//						info=cablingInfoService.getCablingInfoBy(ip.getSwitchId(),ip.getIfIndex());
//						vlaninfo=vlanService.getBy(ip.getSwitchId(), ip.getIfIndex());
//					}
//					
					info=cablingInfoService.getCablingInfoBy(switchId,ifIndexId);
					if(info!=null){
						infoNo=info.getInfoPointNo();
						infoPointNo=info.getCablingInfoID();
					}
					org=organDao.get(ip.getOrgId());
					user=employeeDao.get(StringUtil.isNullString(ip.getUserId())?"-1":ip.getUserId());
					ncmSwitch=ncmDevicesDao.get(switchId);
					ncmSwitchPort=switchPortDao.get(id);
					String vlan="";
					String vlanId="";
					
					if(vlaninfo!=null){
						vlan=vlaninfo.getVlan_Name();
						vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
					}
					if(org!=null)
						orgName=org.getOrgShortName();
					if(user!=null)
						userName=user.getName();
					if(ncmSwitch!=null){
						switchIp=ncmSwitch.getDevIpAddr();
						switchIpLong=String.valueOf(ncmSwitch.getDevIpLong());
						//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
					}
					
					if(ncmSwitchPort!=null){
						ifName=ncmSwitchPort.getIfName();
					    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
					    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
					    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
					}
					
					String ifindex=String.valueOf((ip.getIfIndex()!=null?ip.getIfIndex():0)+100000);
					ifindex=switchId+"." +ifindex;
					iphostinfo.setIpHostId(String.valueOf(ip.getIpHostId()));
					iphostinfo.setId(ip.getIpHostId());
					iphostinfo.setOrgId(ip.getOrgId());
					iphostinfo.setOrgName(orgName);
					iphostinfo.setHostName(ip.getHostName());
					iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ip.getIpHostTypeId())));
					iphostinfo.setIpAddr(ip.getIpAddr());
					iphostinfo.setMacAddr(ip.getMacAddr());
					iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(ip.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
					iphostinfo.setLoginName(userName);
					iphostinfo.setSwitchIp(switchIp);
					iphostinfo.setSwitchIfIndex(ifName);
					iphostinfo.setIfIndexId(String.valueOf(ip.getIfIndex()));
					iphostinfo.setSwitchId(String.valueOf(ip.getSwitchId()));
					iphostinfo.setSwitchIpLong(switchIpLong);
					iphostinfo.setIsoStatus(isoStatus);
					iphostinfo.setAdminStatus(adminStatus);
					iphostinfo.setOperStatus(operStatus);
					iphostinfo.setIpIndex(ifindex);
					iphostinfo.setVlanName(vlan);
					iphostinfo.setVlanId(vlanId);
					iphostinfo.setInfoPointNo(infoPointNo);
					iphostinfo.setInfoNo(infoNo);
					iphostinfo.setOsType(ip.getOsType().toString());
					iphostinfo.setOsVersion(ip.getOsVersion());
					IpHostInfos.add(iphostinfo);
			}
			
			return IpHostInfos;
			
		}
		
		
		
		
		//Ajax分页查询现在查询不用orgID
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly = true)
		public Page<IpHostInfo> searchDataByAjax(PageRequest page,List<String> orgIds,long switchId){
			
			List<IpHostInfo> IpHostInfos=new ArrayList<IpHostInfo>();
			
			StringBuffer hql=new StringBuffer(" select i,v from IpHost i,VlanInfo v,NcmDevices s where i.switchId=s.switchId ");
			
			if(switchId!=-1){
				hql.append(" and i.switchId=:switchId ");
			}else{
				hql.append(" and i.orgId in (:orgIds)  ");
			}
			hql.append(" and i.switchId=v.switch_Id and i.ifIndex=v.ifIndex ");
			hql.append(" order by i.modify_Time desc ");
			//System.out.println("hql------>"+hql.toString());
			//sql参数
			Map params=new HashMap();
			
			if(switchId!=-1){
				params.put("switchId", switchId);
			}else{
				params.put("orgIds", orgIds);
			}
			if (page != null && page.getOrderBy() != null) {
				String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderAttNames, realAttNames);
				page.setOrderBy(realOrderBy);
				}
			
			Page pageinfo=ipHostDao.findAjaxPage(page,hql.toString(),params);
			List list=pageinfo.getResult();
			
			//多表关联查询实体封装
		
			Iterator it  =  list.iterator();
			
			//多表关联查询实体封装
			while(it.hasNext()){
				 Object[] tuple  =  (Object[]) it.next(); 
				 IpHost ip=(IpHost)tuple[0];
				 VlanInfo vlaninfo=(VlanInfo)tuple[1];	
					IpHostInfo iphostinfo=new IpHostInfo();
					OrgOrganization org=new OrgOrganization();
					
					OrgEmployee user =new OrgEmployee();
					NcmDevices ncmSwitch=new NcmDevices();
					NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
					NcmSwitchPortId id =new NcmSwitchPortId();
					id.setSwitchId(ip.getSwitchId());
					id.setIfIndex(ip.getIfIndex());
					
					String orgName="";
					String userName="";
					//String switchStatus="";
					String switchIp="";
					String switchIpLong="";
					String ifName="";
					String isoStatus="";
					String adminStatus="";
					String operStatus="";
					
					String infoNo="";
					String infoPointNo="";
					NcmCablingInfo info=null;
//					VlanInfo vlaninfo=null;
//					if(ip.getSwitchId()!=null&&ip.getIfIndex()!=null){
//						info=cablingInfoService.getCablingInfoBy(ip.getSwitchId(),ip.getIfIndex());
//						vlaninfo=vlanService.getBy(ip.getSwitchId(), ip.getIfIndex());
//					}
//					
					info=cablingInfoService.getCablingInfoBy(ip.getSwitchId(),ip.getIfIndex());
					if(info!=null){
						infoNo=info.getInfoPointNo();
						infoPointNo=info.getCablingInfoID();
					}
					org=organDao.get(ip.getOrgId());
					user=employeeDao.get(ip.getUserId());
					ncmSwitch=ncmDevicesDao.get(ip.getSwitchId()==null?0L:ip.getSwitchId());
					ncmSwitchPort=switchPortDao.get(id);
					String vlan="";
					String vlanId="";
					
					if(vlaninfo!=null){
						vlan=vlaninfo.getVlan_Name();
						vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
					}
					if(org!=null)
						orgName=org.getOrgName();
					if(user!=null)
						userName=user.getName();
					if(ncmSwitch!=null){
						switchIp=ncmSwitch.getDevIpAddr();
						//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
						switchIpLong=String.valueOf(ncmSwitch.getDevIpLong());
					}
					
					if(ncmSwitchPort!=null){
						ifName=ncmSwitchPort.getIfName();
					    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
					    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
					    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
					}
					
					String ifindex=String.valueOf((ip.getIfIndex()!=null?ip.getIfIndex():0)+100000);
					ifindex=ip.getSwitchId()+"." +ifindex;
					iphostinfo.setIpHostId(String.valueOf(ip.getIpHostId()));
					iphostinfo.setOrgName(orgName);
					iphostinfo.setHostName(ip.getHostName());
					iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ip.getIpHostTypeId())));
					iphostinfo.setIpAddr(ip.getIpAddr());
					iphostinfo.setMacAddr(ip.getMacAddr());
					iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(ip.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
					iphostinfo.setLoginName(userName);
					iphostinfo.setSwitchIp(switchIp);
					iphostinfo.setSwitchIfIndex(ifName);
					iphostinfo.setIfIndexId(String.valueOf(ip.getIfIndex()));
					iphostinfo.setSwitchId(String.valueOf(ip.getSwitchId()));
					iphostinfo.setSwitchIpLong(switchIpLong);
					iphostinfo.setIsoStatus(isoStatus);
					iphostinfo.setAdminStatus(adminStatus);
					iphostinfo.setOperStatus(operStatus);
					iphostinfo.setIpIndex(ifindex);
					iphostinfo.setVlanName(vlan);
					iphostinfo.setVlanId(vlanId);
					iphostinfo.setInfoPointNo(infoPointNo);
					iphostinfo.setInfoNo(infoNo);
					IpHostInfos.add(iphostinfo);
			}
			
			pageinfo.setResult(IpHostInfos);
			return pageinfo;
			
		}
		
		
		
		
		//
		public List<IpHostInfo> getPageResult(List<IpHost> list){

			
			List<IpHostInfo> IpHostInfos=new ArrayList<IpHostInfo>();
            for(int i=0;i<list.size();i++){
				 IpHost ip=list.get(i);
					
					IpHostInfo iphostinfo=new IpHostInfo();
					OrgOrganization org=new OrgOrganization();
					
					OrgEmployee user =new OrgEmployee();
					NcmDevices ncmSwitch=new NcmDevices();
					NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
					NcmSwitchPortId id =new NcmSwitchPortId();
					id.setSwitchId(ip.getSwitchId());
					id.setIfIndex(ip.getIfIndex());
					
					String orgName="";
					String userName="";
					//String switchStatus="";
					String switchIp="";
					String ifName="";
					String isoStatus="";
					String adminStatus="";
					String operStatus="";
					
					String infoNo="";
					String infoPointNo="";
					NcmCablingInfo info=null;
					if(ip.getSwitchId()!=null&&ip.getIfIndex()!=null){
						info=cablingInfoService.getCablingInfoBy(ip.getSwitchId(),ip.getIfIndex());;
					}
					if(info!=null){
						infoNo=info.getInfoPointNo();
						infoPointNo=info.getCablingInfoID();
					}
					
					org=organDao.get(ip.getOrgId());
					user=employeeDao.get(ip.getUserId());
					ncmSwitch=ncmDevicesDao.get(ip.getSwitchId()==null?0L:ip.getSwitchId());
					ncmSwitchPort=switchPortDao.get(id);
					String vlan="";
					String vlanId="";
					VlanInfo vlaninfo=vlanService.getBy(ip.getSwitchId(), ip.getIfIndex());
					if(vlaninfo!=null){
						vlan=vlaninfo.getVlan_Name();
						vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
					}
					if(org!=null)
						orgName=org.getOrgName();
					if(user!=null)
						userName=user.getName();
					if(ncmSwitch!=null){
						switchIp=ncmSwitch.getDevIpAddr();
						//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
					}
					
					if(ncmSwitchPort!=null){
						ifName=ncmSwitchPort.getIfName();
					    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
					    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
					    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
					}
					
					String ifindex=String.valueOf((ip.getIfIndex()!=null?ip.getIfIndex():0)+100000);
					ifindex=ip.getSwitchId()+"." +ifindex;
					iphostinfo.setIpHostId(String.valueOf(ip.getIpHostId()));
					iphostinfo.setOrgName(orgName);
					iphostinfo.setHostName(ip.getHostName());
					iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ip.getIpHostTypeId())));
					iphostinfo.setIpAddr(ip.getIpAddr());
					iphostinfo.setMacAddr(ip.getMacAddr());
					iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(ip.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
					iphostinfo.setLoginName(userName);
					iphostinfo.setSwitchIp(switchIp);
					iphostinfo.setSwitchIfIndex(ifName);
					iphostinfo.setIfIndexId(String.valueOf(ip.getIfIndex()));
					iphostinfo.setSwitchId(String.valueOf(ip.getSwitchId()));
					iphostinfo.setIsoStatus(isoStatus);
					iphostinfo.setAdminStatus(adminStatus);
					iphostinfo.setOperStatus(operStatus);
					iphostinfo.setIpIndex(ifindex);
					iphostinfo.setVlanName(vlan);
					iphostinfo.setVlanId(vlanId);
					iphostinfo.setInfoPointNo(infoPointNo);
					iphostinfo.setInfoNo(infoNo);
					IpHostInfos.add(iphostinfo);
			}
            return IpHostInfos;
		}
	
		
		//Ajax分页查询
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly = true)
		public Page<IpHostInfo> searchByAjax2(PageRequest page){
			
			List<IpHostInfo> IpHostInfos=new ArrayList<IpHostInfo>();
			
			//StringBuffer hql=new StringBuffer("select i,v from IpHost i,VlanInfo v where i.orgId<>:id and i.switchId=v.switch_Id and i.ifIndex=v.ifIndex ");
			StringBuffer hql=new StringBuffer("select i from IPDevice i where i.orgOrganization.orgId<>:id ");
			//System.out.println("hql------>"+hql.toString());
			//sql参数
			Map params=new HashMap();
			params.put("id", "-2");
			
			if (page != null && page.getOrderBy() != null) {
				String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderAttNames, realAttNames);
				page.setOrderBy(realOrderBy);
				}
			
			Page pageinfo=ipHostDao.findAjaxPage(page,hql.toString(),params);
			List list=pageinfo.getResult();
			Iterator it  =  list.iterator();
			
			//多表关联查询实体封装
			while(it.hasNext()){
				 //Object[] tuple  =  (Object[]) it.next(); 
				 IPDevice ip=(IPDevice)it.next();
				 //VlanInfo vlaninfo=(VlanInfo)tuple[1];	
				  Long switchId=ip.getSwitchId()==null?0l:ip.getSwitchId();
				  Long ifIndexId=ip.getIfIndex()==null?0l:ip.getIfIndex();
				  
				   VlanInfo vlaninfo=vlanService.getBy(switchId, ifIndexId);
					IpHostInfo iphostinfo=new IpHostInfo();
					//OrgOrganization org=new OrgOrganization();
					
					OrgEmployee user =new OrgEmployee();
					NcmDevices ncmSwitch=new NcmDevices();
					NcmSwitchPort ncmSwitchPort =new NcmSwitchPort();
					NcmSwitchPortId id =new NcmSwitchPortId();
					id.setSwitchId(ip.getSwitchId());
					id.setIfIndex(ip.getIfIndex());
					
					String orgName="";
					String userName="";
					//String switchStatus="";
					String switchIp="";
					String ifName="";
					String isoStatus="";
					String adminStatus="";
					String operStatus="";
					
					String infoNo="";
					String infoPointNo="";
					NcmCablingInfo info=null;
//					VlanInfo vlaninfo=null;
//					if(ip.getSwitchId()!=null&&ip.getIfIndex()!=null){
//						info=cablingInfoService.getCablingInfoBy(ip.getSwitchId(),ip.getIfIndex());
//						vlaninfo=vlanService.getBy(ip.getSwitchId(), ip.getIfIndex());
//					}
//					
					info=cablingInfoService.getCablingInfoBy(switchId,ifIndexId);
					if(info!=null){
						infoNo=info.getInfoPointNo();
						infoPointNo=info.getCablingInfoID();
					}
					//org=organDao.get(ip.getOrgId());
					user=employeeDao.get(StringUtil.isNullString(ip.getUserId())?"-1":ip.getUserId());
					ncmSwitch=ncmDevicesDao.get(switchId);
					ncmSwitchPort=switchPortDao.get(id);
					String vlan="";
					String vlanId="";
					
					if(vlaninfo!=null){
						vlan=vlaninfo.getVlan_Name();
						vlanId=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"";
					}
					//if(org!=null)
						orgName=ip.getOrgOrganization().getOrgName();//org.getOrgName();
					if(user!=null)
						userName=user.getName();
					if(ncmSwitch!=null){
						switchIp=ncmSwitch.getDevIpAddr();
						//switchStatus=String.valueOf(ncmSwitch.getAdminStatus());
					}
					
					if(ncmSwitchPort!=null){
						ifName=ncmSwitchPort.getIfName();
					    isoStatus=String.valueOf(ncmSwitchPort.getAdminStatus());
					    adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(ncmSwitchPort.getAdminStatus()));
					    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(ncmSwitchPort.getOperStatus()));
					}
					
					String ifindex=String.valueOf((ip.getIfIndex()!=null?ip.getIfIndex():0)+100000);
					ifindex=switchId+"." +ifindex;
					iphostinfo.setIpHostId(String.valueOf(ip.getIpHostId()));
					iphostinfo.setOrgName(orgName);
					iphostinfo.setHostName(ip.getHostName());
					iphostinfo.setIpHostType(resItemService.findResContent("IpHostType",String.valueOf(ip.getIpHostTypeId())));
					iphostinfo.setIpAddr(ip.getIpAddr());
					iphostinfo.setMacAddr(ip.getMacAddr());
					iphostinfo.setModify_Time(DateUtil.getSelfTimeShow(ip.getModify_Time(), "yyyy-MM-dd HH:mm:ss"));
					iphostinfo.setLoginName(userName);
					iphostinfo.setSwitchIp(switchIp);
					iphostinfo.setSwitchIfIndex(ifName);
					iphostinfo.setIfIndexId(String.valueOf(ip.getIfIndex()));
					iphostinfo.setSwitchId(String.valueOf(ip.getSwitchId()));
					iphostinfo.setIsoStatus(isoStatus);
					iphostinfo.setAdminStatus(adminStatus);
					iphostinfo.setOperStatus(operStatus);
					iphostinfo.setIpIndex(ifindex);
					iphostinfo.setVlanName(vlan);
					iphostinfo.setVlanId(vlanId);
					iphostinfo.setInfoPointNo(infoPointNo);
					iphostinfo.setInfoNo(infoNo);
					IpHostInfos.add(iphostinfo);
			}
			
			pageinfo.setResult(IpHostInfos);
			return pageinfo;
			
		}
		
		
}
