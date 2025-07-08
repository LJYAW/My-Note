package com.sino.fas.res.net.service;

import java.util.ArrayList;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.service.IpHostService;
import com.sino.fas.res.net.dao.SwitchPortDao;
import com.sino.fas.res.net.entity.IpMacMap;
import com.sino.fas.res.net.entity.NcmSwitchPort;
import com.sino.fas.res.net.entity.NcmSwitchPortId;
import com.sino.fas.res.net.entity.NcmSwitchPortInfo;


//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class SwitchPortService {

	private static Logger logger = LoggerFactory.getLogger(SwitchPortService.class);
	
	private static String objAttNames = "id,ifName,portNo,availablity@SWPort_availablity,multiMacPort,location,ifAliasName,adminStatus@dev_AdminStatus,operStatus@dev_OperStatus,lastChangeTime,portStatusChangeTime";
	private static String jsonAttNames = "id,ifName,portNo,availablity,multiMacPort,location,ifAliasName,adminStatus,operStatus,lastChangeTime,portStatusChangeTime";
	//	private static String childName = "children";
	
	public String lastMassage = "";

	@Autowired
	private SwitchPortDao switchPortDao;
	
	@Autowired
	private IpHostService ipHostService;
	
	@Autowired
	private ResItemService resItemService;
	
	//-- Switch Manager --//
	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmSwitchPort obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmSwitchPort> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<NcmSwitchPort> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<NcmSwitchPort> getAllSwitchPort(Long switchId) {
		String hql = "from NcmSwitchPort where id.switchId=?";
		return switchPortDao.find(hql, switchId);
	}
	
	@Transactional(readOnly = true)
	public List<NcmSwitchPort> searchSwitchPort(final List<PropertyFilter> filters) {
		return switchPortDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<NcmSwitchPort> searchSwitchPort(final PageRequest page, final List<PropertyFilter> filters) {
		return switchPortDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public NcmSwitchPort getSwitchPort(NcmSwitchPortId id) {
		return switchPortDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public NcmSwitchPort getSwitchPort(String ifindex,long switchId) {
		String hql="from NcmSwitchPort where (ifName =? or ifAliasName=?)  and id.switchId=?";
		return switchPortDao.findUnique(hql,ifindex,ifindex,switchId);
	}
	
	@Transactional(readOnly = true)
	public boolean loadSwitchAtts(NcmSwitchPort entity, String attNames){
		return switchPortDao.loadObjAtts(entity, attNames);
	}

	public void addSwitchPort(NcmSwitchPort entity) {
		logger.debug("addSwitch...");
		switchPortDao.save(entity);
	}

	public void saveSwitchPort(NcmSwitchPort entity) {
		logger.debug("saveSwitch...");
		switchPortDao.save(entity);
	}
	
	public void saveIfStatus(NcmSwitchPort entity,int status,int operStatus) {
		logger.debug("saveSwitchStatus...");
		entity.setAdminStatus(status);
		entity.setOperStatus(operStatus);
		switchPortDao.save(entity);
	}
	
	public void deleteSwitchPort(NcmSwitchPortId id) {
		logger.debug("deleteSwitch(id:{})...", id);
		switchPortDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	public List<NcmSwitchPortInfo> getSwitchPortList(long switchId){
		List<NcmSwitchPortInfo> portInfoList=new ArrayList<NcmSwitchPortInfo>();
		List<NcmSwitchPort> listport=getAllSwitchPort(switchId);
		for(int i=0;i<listport.size();i++){
			IpHost ipHost =new IpHost();
			String adminStatus="";
			String operStatus="";
			String availablity="";
			List<IpMacMap> ipmaclist=new ArrayList<IpMacMap>();
			NcmSwitchPort port =listport.get(i);
			adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(port.getAdminStatus()));
		    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(port.getOperStatus()));
		    availablity =resItemService.findResContent("SWPort_availablity",String.valueOf(port.getAvailablity()));
			List<IpHost> ipHostList=ipHostService.getIpHostList(port.getId().getSwitchId(),port.getId().getIfIndex());
			if(ipHostList!=null&&ipHostList.size()>0){
				if(ipHostList.size()>1){
					for(int j=0;j<2;j++){
						IpMacMap ipmacmap=new IpMacMap();
						ipHost =ipHostList.get(j);
						ipmacmap.setMacAddr(ipHost.getMacAddr());
						ipmacmap.setIpAddr(ipHost.getIpAddr());
						ipmaclist.add(ipmacmap);
						}
					}else{
						IpMacMap ipmacmap=new IpMacMap();
						ipHost =ipHostList.get(0);
						ipmacmap.setMacAddr(ipHost.getMacAddr());
						ipmacmap.setIpAddr(ipHost.getIpAddr());
						ipmaclist.add(ipmacmap);
					}
			}
			NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(port,availablity,adminStatus,operStatus,ipmaclist);
			portInfoList.add(portinfo);
		}
		return portInfoList;
	}
	
	@Transactional(readOnly = true)
	public List<NcmSwitchPortInfo> getFilterSwitchPortList(List<PropertyFilter> filters){
		List<NcmSwitchPortInfo> portInfoList=new ArrayList<NcmSwitchPortInfo>();
		List<NcmSwitchPort> listport = searchSwitchPort(filters);
		for(int i=0;i<listport.size();i++){
			IpHost ipHost =new IpHost();
			String adminStatus="";
			String operStatus="";
			String availablity="";
			List<IpMacMap> ipmaclist=new ArrayList<IpMacMap>();
			NcmSwitchPort port =listport.get(i);
			adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(port.getAdminStatus()));
		    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(port.getOperStatus()));
		    availablity =resItemService.findResContent("SWPort_availablity",String.valueOf(port.getAvailablity()));
			List<IpHost> ipHostList=ipHostService.getIpHostList(port.getId().getSwitchId(),port.getId().getIfIndex());
			if(ipHostList!=null&&ipHostList.size()>0){
				if(ipHostList.size()>1){
					for(int j=0;j<2;j++){
						IpMacMap ipmacmap=new IpMacMap();
						ipHost =ipHostList.get(j);
						ipmacmap.setMacAddr(ipHost.getMacAddr());
						ipmacmap.setIpAddr(ipHost.getIpAddr());
						ipmaclist.add(ipmacmap);
						}
					}else{
						IpMacMap ipmacmap=new IpMacMap();
						ipHost =ipHostList.get(0);
						ipmacmap.setMacAddr(ipHost.getMacAddr());
						ipmacmap.setIpAddr(ipHost.getIpAddr());
						ipmaclist.add(ipmacmap);
					}
			}
			NcmSwitchPortInfo portinfo=new NcmSwitchPortInfo(port,availablity,adminStatus,operStatus,ipmaclist);
			portInfoList.add(portinfo);
		}
		return portInfoList;
	}
	
	@Transactional(readOnly = true)
	public NcmSwitchPortInfo getSwitchPortInfo(NcmSwitchPortId id){
		NcmSwitchPortInfo portInfo=new NcmSwitchPortInfo();
		NcmSwitchPort port=getSwitchPort(id);
			IpHost ipHost =new IpHost();
			List<IpMacMap> ipmaclist=new ArrayList<IpMacMap>();
			//String macAddr="";
			//String ipAddr="";
			String adminStatus="";
			String operStatus="";
			String availablity="";
			adminStatus=resItemService.findResContent("dev_AdminStatus",String.valueOf(port.getAdminStatus()));
		    operStatus=resItemService.findResContent("dev_OperStatus",String.valueOf(port.getOperStatus()));
		    availablity =resItemService.findResContent("SWPort_availablity",String.valueOf(port.getAvailablity()));
			List<IpHost> ipHostList=ipHostService.getIpHostList(id.getSwitchId(),id.getIfIndex());
			if(ipHostList!=null&&ipHostList.size()>0){
				if(ipHostList.size()>1){
				for(int j=0;j<2;j++){
					IpMacMap ipmacmap=new IpMacMap();
					ipHost =ipHostList.get(j);
					ipmacmap.setMacAddr(ipHost.getMacAddr());
					ipmacmap.setIpAddr(ipHost.getIpAddr());
					ipmaclist.add(ipmacmap);
					}
				}else{
					IpMacMap ipmacmap=new IpMacMap();
					ipHost =ipHostList.get(0);
					ipmacmap.setMacAddr(ipHost.getMacAddr());
					ipmacmap.setIpAddr(ipHost.getIpAddr());
					ipmaclist.add(ipmacmap);
				}
				portInfo=new NcmSwitchPortInfo(port,availablity,adminStatus,operStatus,ipmaclist);
		}
		return portInfo;
	}
	
}
