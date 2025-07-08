/*
 * 文件名： NetInfoManageService.java
 * 
 * 创建日期： 2014-4-18
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.dailyMaintain.netInfoManage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;
import com.sino.fas.res.cablingInfo.service.CablingInfoService;
import com.sino.fas.res.dailyMaintain.netInfoManage.entity.IpMacBind;
import com.sino.fas.res.dailyMaintain.netInfoManage.entity.IpMacBindInfo;
import com.sino.fas.res.dailyMaintain.netInfoManage.entity.ParamNetQuery;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.VlanInfo;
import com.sino.fas.res.host.service.IpHostService;
import com.sino.fas.res.host.service.VlanService;
import com.sino.fas.res.net.dao.SwitchPortDao;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmSwitchPort;
import com.sino.fas.res.net.entity.NcmSwitchPortInfo;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2014-4-18
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class NetInfoManageService {
	
	private static String objAttNames = "hostId,switchName,switchIp,loginName@OrgEmployee@empId@name,serialNO,vlanId,vlanName,iadminStatus@dev_AdminStatus,ioperStatus@dev_OperStatus,switchId,ifIndex,ifName,portNo,availablity,multiMacPort,location,ifAliasName,adminStatus,operStatus,lastChangeTime,portStatusChangeTime,macAddr,ipAddr,isoStatus,hostIP,hostMac,ipHostTypeId@IpHostType,vlan,hostId,infoPointNo";
	private static String jsonAttNames = "id,switchName,switchIp,loginName,serialNO,vlanId,vlanName,iadminStatus,ioperStatus,switchId,ifIndex,ifName,portNo,availablity,multiMacPort,location,ifAliasName,adminStatus,operStatus,lastChangeTime,portStatusChangeTime,macAddr,ipAddr,isoStatus,hostIP,hostMac,ipHostTypeId,vlan,hostId,infoPointNo";
	
	//bind
	private static String objBindAttNames = "hostId,switchName,switchIp,loginName@OrgEmployee@empId@name,serialNO,vlanNo,iadminStatus@dev_AdminStatus,ioperStatus@dev_OperStatus,switchId,ifIndex,ifName,portNo,availablity,multiMacPort,location,ifAliasName,adminStatus,operStatus,lastChangeTime,portStatusChangeTime,macAddr,ipAddr,isoStatus,hostIP,hostMac,ipHostTypeId@IpHostType,hostId,bindStatus,infoPointNo";
	private static String jsonBindAttNames = "id,switchName,switchIp,loginName,serialNO,vlanNo,iadminStatus,ioperStatus,switchId,ifIndex,ifName,portNo,availablity,multiMacPort,location,ifAliasName,adminStatus,operStatus,lastChangeTime,portStatusChangeTime,macAddr,ipAddr,isoStatus,hostIP,hostMac,ipHostTypeId,hostId,bindStatus,infoPointNo";
	
	
	private static String realAttNames="switchName#s.switchIp,p.portNo#loginName#serialNO#h.osType#vlanId#vlanName#iadminStatus#ioperStatus#switchId#p.ifIndex#s.switchIp,p.portNo#portNo#availablity#multiMacPort#location#ifAliasName#adminStatus#operStatus#lastChangeTime#portStatusChangeTime#h.macAddr#h.ipAddr#isoStatus";
	private static String orderAttNames="switchName,switchIp,loginName,serialNO,osType,vlanId,vlanName,iadminStatus,ioperStatus,switchId,ifIndex,ifName,portNo,availablity,multiMacPort,location,ifAliasName,adminStatus,operStatus,lastChangeTime,portStatusChangeTime,hostMac,hostIP,isoStatus";
	
	//bind order by
	private static String realBindAttNames="switchName#s.switchIp,p.portNo#loginName#serialNO#osType#p.id.ifIndex#iadminStatus#ioperStatus#switchId#ifIndex#ifName#portNo#availablity#multiMacPort#location#ifAliasName#adminStatus#operStatus#lastChangeTime#portStatusChangeTime#macAddr#p.id.ifIndex#isoStatus";
	private static String orderBindAttNames="switchName,switchIp,loginName,serialNO,osType,vlanNo,iadminStatus,ioperStatus,switchId,ifIndex,ifName,portNo,availablity,multiMacPort,location,ifAliasName,adminStatus,operStatus,lastChangeTime,portStatusChangeTime,hostMac,ipAddr,isoStatus";
	
	
	@Autowired
	private IpHostService ipHostService;
	
	@Autowired
	private VlanService vlanService;
	
	@Autowired
	private CablingInfoService cablingInfoService;
	
	@Autowired
	private SwitchPortDao switchPortDao;
	
	@Autowired
	private IpMacBindService IpMacBindService;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmSwitchPortInfo> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getDataByAjax(final Page<NcmSwitchPortInfo> page) {
		return JsonUtils.getJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getBindDataByAjax(final Page<IpMacBindInfo> page) {
		return JsonUtils.getJsonPageInfo(page, objBindAttNames, jsonBindAttNames);
	}
	
	//Ajax分页查询
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly = true)
		public Page<NcmSwitchPortInfo> searchByAjax(PageRequest page){
			
			List<NcmSwitchPortInfo> portlist=new ArrayList<NcmSwitchPortInfo>();
			
			StringBuffer hql=new StringBuffer("select s,p,h from NcmDevices s, NcmSwitchPort p ,IpHost h where p.id.switchId=h.devId and p.id.ifIndex=h.ifIndex and p.id.devId=s.devId and s.orgId<>:id ");
			//sql参数
			Map params=new HashMap();
			params.put("id", "-2");
			
			if (page != null && page.getOrderBy() != null) {
				String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderAttNames, realAttNames);
				page.setOrderBy(realOrderBy);
			}
			
			Page pageinfo=switchPortDao.findAjaxPage(page,hql.toString(),params);
			List list=pageinfo.getResult();
			Iterator it  =  list.iterator();
			
			//多表关联查询实体封装
			while(it.hasNext()){
				 Object[] tuple  =  (Object[]) it.next(); 
				 NcmDevices switchInfo=(NcmDevices)tuple[0];	
				 NcmSwitchPort protInfo=(NcmSwitchPort)tuple[1];	
				 IpHost ipHost =(IpHost)tuple[2];
				  Long switchId=protInfo.getId().getSwitchId()==null?0l:protInfo.getId().getSwitchId();
				  Long ifIndexId=protInfo.getId().getIfIndex()==null?0l:protInfo.getId().getIfIndex();
				  
				   VlanInfo vlaninfo=vlanService.getBy(switchId, ifIndexId);//获取布线信息
				   NcmCablingInfo cabinfo=cablingInfoService.getCablingInfoBy(switchId,ifIndexId);
				   NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(switchInfo,protInfo,vlaninfo,cabinfo,ipHost);
				   portlist.add(portinfo);
				  /* List<IpHost> ipHostList=ipHostService.getIpHostList(switchId,ifIndexId);
				   if(ipHostList!=null&&!ipHostList.isEmpty()){
					for(int j=0;j<ipHostList.size();j++){
						IpHost ipHost =ipHostList.get(j);
						NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(switchInfo,protInfo,vlaninfo,cabinfo,ipHost);
						portlist.add(portinfo);
					}
				   }else{
					   NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(switchInfo,protInfo,vlaninfo,cabinfo);
					   portlist.add(portinfo);
				   }*/
			}
			
			pageinfo.setResult(portlist);
			return pageinfo;
			
		}
		
		//Ajax  ipMacBind分页查询
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly = true)
		public Page<IpMacBindInfo> searchMacBind(PageRequest page){
			
			List<IpMacBindInfo> portlist=new ArrayList<IpMacBindInfo>();
			
			StringBuffer hql=new StringBuffer("select s,p from NcmSwitch s, NcmSwitchPort p where p.id.switchId=s.switchId and s.orgId<>:id ");
			//sql参数
			Map params=new HashMap();
			params.put("id", "-2");
			
			if (page != null && page.getOrderBy() != null) {
				String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderBindAttNames, realBindAttNames);
				page.setOrderBy(realOrderBy);
			}
			
			Page pageinfo=switchPortDao.findAjaxPage(page,hql.toString(),params);
			List list=pageinfo.getResult();
			Iterator it  =  list.iterator();
			
			//多表关联查询实体封装
			while(it.hasNext()){
				 Object[] tuple  =  (Object[]) it.next(); 
				 NcmDevices switchInfo=(NcmDevices)tuple[0];	
				 NcmSwitchPort protInfo=(NcmSwitchPort)tuple[1];	
				  Long switchId=protInfo.getId().getSwitchId()==null?0l:protInfo.getId().getSwitchId();
				  Long ifIndexId=protInfo.getId().getIfIndex()==null?0l:protInfo.getId().getIfIndex();
				  
				   NcmCablingInfo cabinfo=cablingInfoService.getCablingInfoBy(switchId,ifIndexId);
				   
				   List<IpMacBind> ipHostList=IpMacBindService.getList(switchId,ifIndexId);
				   if(ipHostList!=null&&!ipHostList.isEmpty()){
					for(int j=0;j<ipHostList.size();j++){
						IpMacBind ipHost =ipHostList.get(j);
						IpMacBindInfo portinfo=new IpMacBindInfo(switchInfo,protInfo,cabinfo,ipHost);
						portlist.add(portinfo);
					}
				   }else{
					   IpMacBindInfo portinfo=new IpMacBindInfo(switchInfo,protInfo,cabinfo);
					   portlist.add(portinfo);
				   }
			}
			
			pageinfo.setResult(portlist);
			return pageinfo;
			
		}
		
		
		//Ajax分页查询
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly = true)
		public Page<NcmSwitchPortInfo> searchByAjaxBy(PageRequest page,ParamNetQuery netQuery){
			
			List<NcmSwitchPortInfo> portlist=new ArrayList<NcmSwitchPortInfo>();
			
			StringBuffer hql=new StringBuffer("select s,p,h from NcmSwitch s, NcmDevicesPort p,IpHost h where s.orgId<>:id ");
			//sql参数
			Map params=new HashMap();
			
			//设备条件查询
			if(!StringUtil.isNullString(netQuery.getNet_IP_1())){
				hql.append(" and h.ipAddr like :net1");
				params.put("net1", netQuery.getNet_IP_1()+".%.%.%");
			}
			if(!StringUtil.isNullString(netQuery.getNet_IP_2())){
				hql.append(" and h.ipAddr like :net2");
				params.put("net2", "%."+netQuery.getNet_IP_2()+".%.%");
			}
			if(!StringUtil.isNullString(netQuery.getNet_IP_3())){
				hql.append(" and h.ipAddr like :net3");
				params.put("net3", "%.%."+netQuery.getNet_IP_3()+".%");
			}
			if(!StringUtil.isNullString(netQuery.getNet_IP_4())){
				hql.append(" and h.ipAddr like :net4");
				params.put("net4", "%.%.%."+netQuery.getNet_IP_4());
			}
			if(!StringUtil.isNullString(netQuery.getHostMac())){
				hql.append(" and h.macAddr like :hMac");
				params.put("hMac",netQuery.getHostMac());
			}
			hql.append(" and h.switchId =p.id.switchId and h.ifIndex = p.id.ifIndex ");
			
			//交换机条件查询
			if(!StringUtil.isNullString(netQuery.getSw_IP_1())){
				hql.append(" and s.switchIp like :sw1");
				params.put("sw1", netQuery.getSw_IP_1()+".%.%.%");
			}
			if(!StringUtil.isNullString(netQuery.getSw_IP_2())){
				hql.append(" and s.switchIp like :sw2");
				params.put("sw2", "%."+netQuery.getSw_IP_2()+".%.%");
			}
			if(!StringUtil.isNullString(netQuery.getSw_IP_3())){
				hql.append(" and s.switchIp like :sw3");
				params.put("sw3","%.%."+netQuery.getSw_IP_3()+".%");
			}
			if(!StringUtil.isNullString(netQuery.getSw_IP_4())){
				hql.append(" and s.switchIp like :sw4");
				params.put("sw4","%.%.%."+netQuery.getSw_IP_4());
			}
			if(!StringUtil.isNullString(netQuery.getIfName())){
				hql.append(" and p.ifName like :ifN ");
				params.put("ifN", "%"+netQuery.getIfName()+"%");
			}
			
			hql.append(" and p.id.switchId=s.switchId  ");
			params.put("id", "-2");
			
			if (page != null && page.getOrderBy() != null) {
				String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderAttNames, realAttNames);
				page.setOrderBy(realOrderBy);
			}
			
			Page pageinfo=switchPortDao.findAjaxPage(page,hql.toString(),params);
			List list=pageinfo.getResult();
			Iterator it  =  list.iterator();
			
			//多表关联查询实体封装
			while(it.hasNext()){
				 Object[] tuple  =  (Object[]) it.next(); 
				 NcmDevices switchInfo=(NcmDevices)tuple[0];	
				 NcmSwitchPort protInfo=(NcmSwitchPort)tuple[1];
				 Long switchId=protInfo.getId().getSwitchId()==null?0l:protInfo.getId().getSwitchId();
				 Long ifIndexId=protInfo.getId().getIfIndex()==null?0l:protInfo.getId().getIfIndex();
				 VlanInfo vlaninfo=vlanService.getBy(switchId, ifIndexId);//获取布线信息
				 NcmCablingInfo cabinfo=cablingInfoService.getCablingInfoBy(switchId,ifIndexId);
				 IpHost ipHost =(IpHost)tuple[2];
				 NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(switchInfo,protInfo,vlaninfo,cabinfo,ipHost);
				 portlist.add(portinfo);
				 
				 /*if(flag){
					 IpHost ipHost =(IpHost)tuple[2];
					 NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(switchInfo,protInfo,vlaninfo,cabinfo,ipHost);
					 portlist.add(portinfo);
				 }else{
					 List<NcmSwitchPortInfo> plist=portlistGet( switchInfo, protInfo, vlaninfo, cabinfo);
					 if(plist!=null&&!plist.isEmpty()){
						 portlist.addAll(plist);
					 }
				 }*/
				 
			}
		pageinfo.setResult(portlist);
		return pageinfo;
	}
		
		//Ajax bind分页查询
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly = true)
		public Page<IpMacBindInfo> searchBindByAjaxBy(PageRequest page,ParamNetQuery netQuery){
			
			List<IpMacBindInfo> portlist=new ArrayList<IpMacBindInfo>();
			boolean flag=false;
			boolean cabflag=false;
			boolean vlanflag=false;
			
			
			StringBuffer hql=new StringBuffer("select s,p# from NcmDevices s, NcmSwitchPort p");
			//sql参数
			Map params=new HashMap();
			
			//布线信息查询
			if(!StringUtil.isNullString(netQuery.getInfoPointNo())){
				cabflag=true;
				hql.append(",NcmCablingInfo c ");
			}
			//vlan信息查询
			if(!StringUtil.isNullString(netQuery.getVlanId())){
				vlanflag=true;
				hql.append(",VlanInfo v ");
			}
			
			//设备条件查询
			if(!StringUtil.isNullString(netQuery.getNet_IP_1())){
				flag=true;
				hql.append(",IpMacBind h where s.orgId<>:id ");
				hql.append(" and h.ipAddr like :net1");
				params.put("net1", netQuery.getNet_IP_1()+".%.%.%");
			}
			if(!StringUtil.isNullString(netQuery.getNet_IP_2())){
				if(!flag){
					hql.append(",IpMacBind h where s.orgId<>:id ");
					flag=true;
				}
				hql.append(" and h.ipAddr like :net2");
				params.put("net2", "%."+netQuery.getNet_IP_2()+".%.%");
			}
			if(!StringUtil.isNullString(netQuery.getNet_IP_3())){
				if(!flag){
					hql.append(",IpMacBind h where s.orgId<>:id ");
					flag=true;
				}
				hql.append(" and h.ipAddr like :net3");
				params.put("net3", "%.%."+netQuery.getNet_IP_3()+".%");
			}
			if(!StringUtil.isNullString(netQuery.getNet_IP_4())){
				if(!flag){
					hql.append(",IpMacBind h where s.orgId<>:id ");
					flag=true;
				}
				hql.append(" and h.ipAddr like :net4");
				params.put("net4", "%.%.%."+netQuery.getNet_IP_4());
			}
			if(!StringUtil.isNullString(netQuery.getHostMac())){
				if(!flag){
					hql.append(",IpMacBind h where s.orgId<>:id ");
					flag=true;
				}
				hql.append(" and h.macAddr like :hMac");
				params.put("hMac",netQuery.getHostMac());
			}
			if(netQuery.getBindStatus()!=null){
				if(!flag){
					hql.append(",IpMacBind h where s.orgId<>:id ");
					flag=true;
				}
				hql.append(" and h.bindStatus =:bStatus");
				params.put("bStatus",netQuery.getBindStatus());
			}
			if(!StringUtil.isNullString(netQuery.getSerialNO())){
				if(!flag){
					hql.append(",IpMacBind h where s.orgId<>:id ");
					flag=true;
				}
				hql.append(" and h.serialNO =:sNO");
				params.put("sNO",netQuery.getSerialNO());
			}
			if(netQuery.getIpHostTypeId()!=null){
				if(!flag){
					hql.append(",IpMacBind h where s.orgId<>:id ");
					flag=true;
				}
				hql.append(" and h.ipHostTypeId =:hostType");
				params.put("hostType",netQuery.getIpHostTypeId());
			}
			
			
			
			if(!flag){ 
				hql.append(" where s.orgId<>:id ");
				hql=new StringBuffer(hql.toString().replace("#", ""));
			}else{
				hql.append(" and h.switchId =p.id.switchId and h.ifIndex = p.id.ifIndex ");
				hql=new StringBuffer(hql.toString().replace("#", ",h"));
			}
			
			//交换机端口状态
			if(netQuery.getOperStatus()!=null){
				hql.append(" and p.operStatus =:oStatus ");
				params.put("oStatus",netQuery.getOperStatus());
			}
			
			if(cabflag){//查询布线号信息
				hql.append(" and c.switchID =p.id.switchId and c.ifIndex = p.id.ifIndex and infoPointNo like:cabNo ");
				params.put("cabNo", netQuery.getInfoPointNo());
			}
			if(vlanflag){//查询vlan信息
				hql.append(" and v.switch_Id =p.id.switchId and v.ifIndex = p.id.ifIndex and vlan =:vNo ");
				params.put("vNo", Long.valueOf(netQuery.getVlanId()));
			}
			
			//交换机条件查询
			if(!StringUtil.isNullString(netQuery.getSw_IP_1())){
				hql.append(" and s.switchIp like :sw1");
				params.put("sw1", netQuery.getSw_IP_1()+".%.%.%");
			}
			if(!StringUtil.isNullString(netQuery.getSw_IP_2())){
				hql.append(" and s.switchIp like :sw2");
				params.put("sw2", "%."+netQuery.getSw_IP_2()+".%.%");
			}
			if(!StringUtil.isNullString(netQuery.getSw_IP_3())){
				hql.append(" and s.switchIp like :sw3");
				params.put("sw3","%.%."+netQuery.getSw_IP_3()+".%");
			}
			if(!StringUtil.isNullString(netQuery.getSw_IP_4())){
				hql.append(" and s.switchIp like :sw4");
				params.put("sw4","%.%.%."+netQuery.getSw_IP_4());
			}
			if(!StringUtil.isNullString(netQuery.getIfName())){
				hql.append(" and p.ifName like :ifN ");
				params.put("ifN", "%"+netQuery.getIfName()+"%");
			}
			hql.append(" and p.id.switchId=s.switchId  ");
			params.put("id", "-2");
			
			if (page != null && page.getOrderBy() != null) {
				String realOrderBy = JsonUtils.getOrderObjAttName(page.getOrderBy(), orderBindAttNames, realBindAttNames);
				page.setOrderBy(realOrderBy);
			}
			
			Page pageinfo=switchPortDao.findAjaxPage(page,hql.toString(),params);
			List list=pageinfo.getResult();
			Iterator it  =  list.iterator();
			
			//多表关联查询实体封装
			while(it.hasNext()){
				 Object[] tuple  =  (Object[]) it.next(); 
				 NcmDevices switchInfo=(NcmDevices)tuple[0];	
				 NcmSwitchPort protInfo=(NcmSwitchPort)tuple[1];
				 Long switchId=protInfo.getId().getSwitchId()==null?0l:protInfo.getId().getSwitchId();
				 Long ifIndexId=protInfo.getId().getIfIndex()==null?0l:protInfo.getId().getIfIndex();
				  
				 NcmCablingInfo cabinfo=cablingInfoService.getCablingInfoBy(switchId,ifIndexId);
				 if(flag){
					 IpMacBind ipHost =(IpMacBind)tuple[2];
					 IpMacBindInfo portinfo=new IpMacBindInfo(switchInfo,protInfo,cabinfo,ipHost);
					 portlist.add(portinfo);
				 }else{
					 List<IpMacBindInfo> plist=portBindlistGet( switchInfo, protInfo, cabinfo);
					 if(plist!=null&&!plist.isEmpty()){
						 portlist.addAll(plist);
					 }
				 }
				 
			}
		pageinfo.setResult(portlist);
		return pageinfo;
	}
		
	//根据不同ip获取list
	public List<NcmSwitchPortInfo> portlistGet(NcmDevices switchInfo,NcmSwitchPort protInfo,VlanInfo vlaninfo,NcmCablingInfo cabinfo){
		  List<NcmSwitchPortInfo> portlist=new ArrayList<NcmSwitchPortInfo>();
			 
		  Long switchId=protInfo.getId().getSwitchId()==null?0l:protInfo.getId().getSwitchId();
		  Long ifIndexId=protInfo.getId().getIfIndex()==null?0l:protInfo.getId().getIfIndex();
		  
		  List<IpHost> ipHostList=ipHostService.getIpHostList(switchId,ifIndexId);
		  if(ipHostList!=null&&!ipHostList.isEmpty()){
		    for(int j=0;j<ipHostList.size();j++){
					IpHost ipHost =ipHostList.get(j);
					NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(switchInfo,protInfo,vlaninfo,cabinfo,ipHost);
					portlist.add(portinfo);
			}
		   }else{
			   NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(switchInfo,protInfo,vlaninfo,cabinfo);
			   portlist.add(portinfo);
		   }
	
		   return portlist;
		}
	
		//根据不同ip获取bind list
		public List<IpMacBindInfo> portBindlistGet(NcmDevices switchInfo,NcmSwitchPort protInfo,NcmCablingInfo cabinfo){
			  List<IpMacBindInfo> portlist=new ArrayList<IpMacBindInfo>();
				 
			  Long switchId=protInfo.getId().getSwitchId()==null?0l:protInfo.getId().getSwitchId();
			  Long ifIndexId=protInfo.getId().getIfIndex()==null?0l:protInfo.getId().getIfIndex();
			  
			  List<IpMacBind> ipHostList=IpMacBindService.getList(switchId,ifIndexId);
			  if(ipHostList!=null&&!ipHostList.isEmpty()){
			    for(int j=0;j<ipHostList.size();j++){
			    		IpMacBind ipHost =ipHostList.get(j);
			    		IpMacBindInfo portinfo=new IpMacBindInfo(switchInfo,protInfo,cabinfo,ipHost);
						portlist.add(portinfo);
				}
			   }else{
				   IpMacBindInfo portinfo=new IpMacBindInfo(switchInfo,protInfo,cabinfo);
				   portlist.add(portinfo);
			   }
			   return portlist;
			}
}
