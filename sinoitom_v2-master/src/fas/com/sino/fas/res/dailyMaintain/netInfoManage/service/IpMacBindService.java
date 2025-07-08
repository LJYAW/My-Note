/*
 * 文件名： IpMacBindService.java
 * 
 * 创建日期： 2014-5-4
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.dailyMaintain.netInfoManage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import smartlink.utils.DBQueryUtil;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.StringUtil;
import com.sino.base.system.dao.OrgEmployeeDao;
import com.sino.base.system.dao.OrgOrganDao;
import com.sino.base.system.entity.OrgEmployee;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.service.EmployeeService;
import com.sino.base.system.service.OrganService;
import com.sino.base.system.service.ResItemService;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;
import com.sino.fas.res.cablingInfo.service.CablingInfoService;
import com.sino.fas.res.dailyMaintain.netInfoManage.dao.IpMacBindDao;
import com.sino.fas.res.dailyMaintain.netInfoManage.entity.IpMacBind;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.host.entity.VlanInfo;
import com.sino.fas.res.host.service.VlanService;
import com.sino.fas.res.net.dao.NcmDevicesDao;
import com.sino.fas.res.net.dao.SnmpCredDao;
import com.sino.fas.res.net.dao.SwitchPortDao;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmSwitchPort;
import com.sino.fas.res.net.entity.NcmSwitchPortId;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2014-5-4
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class IpMacBindService {
	
	@Autowired
	private IpMacBindDao ipMacBindDao;
	
	@Autowired
	private SwitchPortDao switchPortDao;
	
	@Autowired
	private OrgOrganDao organDao;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private OrgEmployeeDao employeeDao;
	
	@Autowired
	private NcmDevicesDao switchDao;
	
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
	
	
	public void deleteBind(long id){
		ipMacBindDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	public IpMacBind getIpMacBind(long id){
		return ipMacBindDao.get(id);
	}
	
	public void saveIpMacBind(IpMacBind IpHost) {
		ipMacBindDao.save(IpHost);
	}
	
	public void addIpMacBind(IpMacBind IpHost) {
		IpHost.setIpMacBindId(DBQueryUtil.getInstance().getDeviceResourceUniqId());
		ipMacBindDao.save(IpHost);
	}
	
	public void multiDelete(List<IpHost>  hostlist) {
		for(IpHost info:hostlist){
			IpMacBind obind=isIpMacBindUnique(info.getSwitchId(),info.getIfIndex(),info.getMacAddr());
			if(obind!=null){
				ipMacBindDao.delete(obind);
			}
		}
	}
	
	public void multiDels(List<IpMacBind> bindlist) {
		for(IpMacBind info:bindlist){
			ipMacBindDao.delete(info.getIpMacBindId());
		}
	}
	
	public void multiSave(List<IpHost>  hostlist) {
		List<IpMacBind> bindlist=new ArrayList<IpMacBind>();
		for(IpHost info:hostlist){
			IpMacBind obind=isIpMacBindUnique(info.getSwitchId(),info.getIfIndex(),info.getMacAddr());
			if(obind==null&&!StringUtil.isNullString(info.getMacAddr())){
				IpMacBind bind=new IpMacBind();
				BeanUtils.copyProperties(info, bind);
				bind.setIpMacBindId(DBQueryUtil.getInstance().getDeviceResourceUniqId());
				bind.setBindStatus(1);
				bindlist.add(bind);
			}
		}
		if(!bindlist.isEmpty())
			ipMacBindDao.batchSave(bindlist, 10);
	}
	
	@Transactional(readOnly = true)
	public IpMacBind isIpMacBindUnique(Long switchId,Long indexId, String macAddr) {
		String hql = "from IpMacBind where switchId=? and ifIndex=? and macAddr=? ";
		List<IpMacBind> list=ipMacBindDao.find(hql,switchId,indexId,macAddr);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public List<IpMacBind> getList(long switchId,long ifIndex) {
		String hql = "from IpMacBind where switchId=? and ifIndex=? order by ipAddr";
		return ipMacBindDao.find(hql, switchId,ifIndex);
	}
	
	
	@Transactional(readOnly = true)
	public IpHostInfo getListIpMacBind(long ipBindId,IpMacBind bind){
			IpMacBind ipHost =ipMacBindDao.get(ipBindId);
			BeanUtils.copyProperties(ipHost, bind);
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
			ncmSwitch=switchDao.get(ipHost.getSwitchId()==null?0L:ipHost.getSwitchId());
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
			//iphostinfo.setIpHostId(String.valueOf(ipHost.getIpHostId()));
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
}
