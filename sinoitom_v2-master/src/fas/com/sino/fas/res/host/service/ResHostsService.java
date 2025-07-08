/**
 * 
 */
package com.sino.fas.res.host.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.dao.*;
import com.sino.fas.res.host.entity.*;
import com.sino.fas.res.net.dao.NcmDevAccessUserDao;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Mr.LP
 * @date 2019-8-22上午9:52:39
 * @className ResHostsService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResHostsService {
	
	private static Logger logger = LoggerFactory.getLogger(ResHostsService.class);
	
	private static String objAttNames = "hostType,homedHostId,homedHostIp,orgID,orgID@OrgOrganization@orgId@orgShortName,hostId,resClassCode,resClassName,resTypeCode,resTypeName,hostName,ipAddress,ipLong,ipNetMask,macAddress,assetNO,vendorID,vendorName,brand,prodSeriesID,prodSeries,prodModel,modelOID,productNO,productDesc,productSN,osName,osClass,osType,osVersion,osRelease,osFeature,cpuStruct,cpuVendor,cpuModel,cpuDesc,cpuWidth,cpuQty,cpuCoreQty,memorySize,memoryDisplay,diskSize,diskDisplay,powerStatus,hostStatus,creator,createTime,modifier,modifyTime,description,administrator,connStatus,accessmode,devAcsUserId,devAcsUserId@NcmDevAccessUser@devAcsUserId@userName,osVersionType,osStruct,accessPort,purpose,virtualizeSoft,vmName";
	private static String jsonAttNames = "hostType,homedHostId,homedHostIp,orgID,orgName,id,resClassCode,resClassName,resTypeCode,resTypeName,hostName,ipAddress,ipLong,ipNetMask,macAddress,assetNO,vendorID,vendorName,brand,prodSeriesID,prodSeries,prodModel,modelOID,productNO,productDesc,productSN,osName,osClass,osType,osVersion,osRelease,osFeature,cpuStruct,cpuVendor,cpuModel,cpuDesc,cpuWidth,cpuQty,cpuCoreQty,memorySize,memoryDisplay,diskSize,diskDisplay,powerStatus,hostStatus,creator,createTime,modifier,modifyTime,description,administrator,connStatus,accessmode,devAcsUserId,userName,osVersionType,osStruct,accessPort,purpose,virtualizeSoft,vmName";
	
	@Autowired
	private ResHostsDao resHostsDao;
	
	@Autowired
	private ResHostCpuDao resHostCpuDao;
	
	@Autowired
	private ResHostFileSystemDao resHostFileSystemDao;
	
	@Autowired
	private ResHostInterfaceDao resHostInterfaceDao;
	
	@Autowired
	private ResHostDisksDao resHostDisksDao;
	
	@Autowired
	private ResClusterHostsDao resClusterHostsDao;

	@Autowired
	private ResCompHostsDao resCompHostsDao;

	@Autowired
	private NcmDevAccessUserDao devAccessUserDao;

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResHosts> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResHosts obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResHosts> getAll() {
		String hql = " from ResHosts where hostType!=3 and hostType!=2 and ipAddress!=null order by ipLong";
		return resHostCpuDao.find(hql);
	}
	
//	@Transactional(readOnly = true)
//	public List<ResHosts> getLegalHosts() {
//		String hql = " from ResHosts where hostType!=3 and hostType!=2 order by ipLong";
//		return resHostCpuDao.find(hql);
//	}
	
	@Transactional(readOnly = true)
	public List<ResHosts> getLegalHosts() {
		//String hql = " from ResHosts where hostType!=3 and hostType!=2 order by ipLong";
		return resClusterHostsDao.getLegalHosts();
	}
	
	@Transactional(readOnly = true)
	public List<ResHosts> getByHostType() {
		String hql = " from ResHosts where hostType=3 order by ipLong";
		return resHostCpuDao.find(hql);
	}
	
	@Transactional(readOnly = true)
	public ResHosts getById(Long id){
		return resHostsDao.get(id);
	}
	
	public void save(ResHosts resHosts) {
		logger.debug("save...");
		resHostsDao.save(resHosts);
	}

	public void saveList(NcmDevAccessUser devAccess,List<ResHosts> list) {
		logger.debug("saveList...");
		if (list.size()>0){
//			resHostsDao.batchSave(list,list.size());

			List<ResCompHosts> compHostsList = new ArrayList<>();

			Long compHostId = null;
			List<Long> hostIds = new ArrayList<>();
			for(ResHosts host: list){
				if(StringUtils.isEmpty(host.getHomedHostIp())){
//					为空时宿主机，其他为虚拟机
					compHostId = host.getHostId();
					host.setDevAcsUserId(devAccess.getDevAcsUserId());
				}else{
					hostIds.add(host.getHostId());
				}
			}
			for(Long hostId: hostIds){
				ResCompHosts comphost = new ResCompHosts();
				comphost.setHostId(hostId);
				comphost.setCompHostId(compHostId);
				compHostsList.add(comphost);
			}

			if(compHostsList.size()>0){
				resCompHostsDao.batchSave(compHostsList,compHostsList.size());
			}

			resHostsDao.batchSave(list,list.size());

		}
	}
	
	public void batchSave(ResHosts resHosts,List<ResHostCpu> cpulist,List<ResHostFileSystem> fileSysList, List<ResHostInterface> infoList,List<ResHostDisks> disksList){
		logger.debug("batchSave...");
		
		if(resHosts!=null){
			resHostsDao.save(resHosts);
		}
		if(cpulist.size()>0){
			resHostCpuDao.batchSave(cpulist, cpulist.size());
		}
		if(fileSysList.size()>0){
			resHostFileSystemDao.batchSave(fileSysList, fileSysList.size());
		}
		if(infoList.size()>0){
			resHostInterfaceDao.batchSave(infoList, infoList.size());
		}
		if(disksList.size()>0){
			resHostDisksDao.batchSave(disksList, disksList.size());
		}
		
	}
	
	@Transactional(readOnly = true)
	public ResHosts getByIpAddress(String ipAddress) {
		if( StringUtils.isBlank(ipAddress)){
			return null;
		}
		String hql = "from ResHosts where ipAddress=? ";
		return resHostsDao.findUnique(hql, ipAddress);
	}
	
	public void delete(Long id){
		resHostsDao.delete(id);
	}
	
	public void batchDelete(Long id,List<ResHostCpu> hostCpulist,List<ResHostDisks> hostDisklist,List<ResHostFileSystem> hostFilelist,List<ResHostInterface> hostInfolist){
		resHostsDao.delete(id);
		
		resHostCpuDao.delete(hostCpulist);
		resHostFileSystemDao.delete(hostFilelist);
		resHostInterfaceDao.delete(hostInfolist);
		resHostDisksDao.delete(hostDisklist);
		
	}
	
	public void deleteHostAndClusterHost(Long id,List<ResClusterHosts> clusterHostList){
		resHostsDao.delete(id);
		resClusterHostsDao.delete(clusterHostList);
		
	}

	public void deleteHostAndCompHost(Long id,List<ResHosts> hostList , List<ResCompHosts> compHostList,NcmDevAccessUser devAccessUser){

		resHostsDao.delete(id);
		if(hostList.size()>0){
			resHostsDao.delete(hostList);
		}
		if(compHostList.size()>0){
			resCompHostsDao.delete(compHostList);
		}
		if(devAccessUser!=null){
			devAccessUserDao.delete(devAccessUser);
		}

	}

	public List<ResHosts> getHostsByOrgId(String orgID) {
		List<ResHosts> list = resHostsDao.getHostsByOrgId(orgID);
		return list;
	}

//	public List<ResHosts> getHostsByHomedHostIdAndParam(Long homedHostId){
//		String hql = " from ResHosts where homedHostId = ? ";
//		return resHostCpuDao.find(hql,homedHostId);
//	}

	public List<ResHosts> getHostsByHomedHostIdAndParam(Long homedHostId,String paramName,String sortorder){
		String hql = null;
		if(StringUtils.isNotEmpty(paramName)&&!("undefined".equals(paramName))){
			hql = " from ResHosts where homedHostId = ? order by " + paramName + " "+sortorder;
		}else{
			hql = " from ResHosts where homedHostId = ? ";
		}
		return resHostCpuDao.find(hql,homedHostId);
	}
	
	public List<ResHosts> getHostsByHostType(){
		String hql = " from ResHosts where hostType = 2 order by ipLong";
		return resHostCpuDao.find(hql);
	}
	//根据服务器类型和宿主机查询IP地址
	public List<ResHosts> getSvrIpAddrByIdAndType(Long homedHostId,Integer hostType){
		String hql = " from ResHosts where homedHostId = ? and hostType=?";
		return resHostCpuDao.find(hql,homedHostId,hostType);
	}
	//根据服务器类型查询IP地址
	public List<ResHosts> getSvrIpAddrByType(Integer hostType){
		String hql = " from ResHosts where  hostType=?";
		return resHostCpuDao.find(hql,hostType);
	}
	
	public List<ResHosts> getListByHostIds(List<Long> hostIds){
		return resHostsDao.getListByHostIds(hostIds);
	}
	
	public List<ResHosts> getListByIpAddress(Set set){
		return resHostsDao.getListByIpAddress(set);
	}

}
