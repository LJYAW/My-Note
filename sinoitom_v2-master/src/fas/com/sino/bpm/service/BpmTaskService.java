package com.sino.bpm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.dao.OrgOrganDao;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.bpm.entity.BpmTask;
import com.sino.cmdb.vendor.dao.CmdbVendorDao;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.fas.res.net.dao.NcmDevAccessUserDao;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NaturalId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.BeanUtils;
import com.sino.bpm.dao.BpmResMonIndsDao;
import com.sino.bpm.dao.BpmTaskDao;
import com.sino.bpm.dao.BpmTaskMonBizDao;
import com.sino.bpm.dao.BpmTaskMonResDao;
import com.sino.bpm.entity.BpmResMonInds;
import com.sino.bpm.entity.BpmTaskMonBiz;
import com.sino.bpm.entity.BpmTaskMonRes;
import com.sino.cmdb.graph.dao.CmdbResNodeDao;
import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.indicator.device.dao.DevIndicatorItemsDao;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.fas.res.host.dao.ResHostsDao;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.fas.res.net.dao.NcmDevicesDao;
import com.sino.fas.res.net.entity.NcmDevices;

@Service
@Transactional
public class BpmTaskService {

	private static Logger logger= LoggerFactory.getLogger(BpmTaskService.class);

	@Autowired
	private BpmTaskDao bpmTaskDao;
	@Autowired
	private CmdbResNodeDao cmdbResNodeDao;
	@Autowired
	private NcmDevicesDao ncmDevicesDao;
	@Autowired
	private ResHostsDao resHostsDao;
	@Autowired
	private BpmTaskMonBizDao bpmTaskMonBizDao;
	@Autowired
	private BpmTaskMonResDao bpmTaskMonResDao;
	@Autowired
	private OrgOrganDao orgOrganDao;
	@Autowired
	private CmdbVendorDao cmdbVendorDao;
	@Autowired
	private NcmDevAccessUserDao ncmDevAccessUserDao;


	private static String objAttNames ="taskId,taskClass,taskType,taskName,taskDescr,pollCycle,cycleUnit,pollCycleSecs,checkTimes,firstChkTime,tmplId,tmplName,status";
	private static String jsonAttNames="id,taskClass,taskType,taskName,taskDescr,pollCycle,cycleUnit,pollCycleSecs,checkTimes,firstChkTime,tmplId,tmplName,status";

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<BpmTask> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}


	//	任务关联业务系统，将相关资源信息保存到库
	public void taskRelateBiz(Integer taskId, String bizIdListStr) {
		logger.info( "Enter BpmTaskService.taskRelateBiz ..." );
//		1、关联关系存入数据库
		String[] split = bizIdListStr.split(",");
		if(split!=null && split.length>0){
			List<BpmTaskMonBiz> bpmTaskMonBizList = new ArrayList<>();

			BpmTask bpmTask = bpmTaskDao.get(taskId);
			String taskName = bpmTask.getTaskName();

			for(String bizIdStr : split){
				Long bizId = Long.parseLong(bizIdStr);
				if(bizId!=null){
//					创建关联关系实体
					BpmTaskMonBiz bpmTaskMonBiz = new BpmTaskMonBiz();
					bpmTaskMonBiz.setTaskId(taskId);
					bpmTaskMonBiz.setBizId(bizId);
					bpmTaskMonBizList.add(bpmTaskMonBiz);
				}
			}

//			2、设备信息入库

//			根据bizIdListStr获取所有的节点集合(并集)
			List<CmdbResNode> nodeList = cmdbResNodeDao.getByBizList(bizIdListStr);
			//这里重写了CmdbResNode的equals方法，resId（关联的设备的id）不一样视为不同
			Set<CmdbResNode> nodeSet= new HashSet<>(nodeList);
			List<BpmTaskMonRes> bpmTaskMonResList = new ArrayList<>();

			if(nodeSet!=null && nodeSet.size()>0){
				for(CmdbResNode node: nodeSet){
					if(node!=null){
						String mgmtIP = "";
						Long mgmtIpLong = null;

						Integer resClassCode = node.getResClassCode();
						Long resId = node.getResId();

//						看关联的设备是否在表里面已经有了，如果已经有了就不存了
						List<BpmTaskMonRes> resList = bpmTaskMonResDao.getByResId(resId);
						if(resList!=null && resList.size()>0){
							continue;
						}

						BpmTaskMonRes bpmTaskMonRes = new BpmTaskMonRes();
						bpmTaskMonRes.setTaskId(taskId);
						bpmTaskMonRes.setTaskName(taskName);
						bpmTaskMonRes.setIndCollMode("cmd");
						bpmTaskMonRes.setFlag(0);//默认暂停检测

						BeanUtils.copyProperties(node, bpmTaskMonRes);

//						如果是网络资源，节点关联的实体是NcmDevices表
						if(resClassCode!=null && resClassCode==13 && resId!=null){
							NcmDevices ncmDevices = ncmDevicesDao.get(resId);
							if(ncmDevices!=null){
								BeanUtils.copyProperties(ncmDevices, bpmTaskMonRes);

								String devAccessId = ncmDevices.getDevAccessId();
								if(StringUtils.isNotBlank(devAccessId)){
									NcmDevAccessUser ncmDevAccessUser = ncmDevAccessUserDao.get(devAccessId);
									if(ncmDevAccessUser!=null){
										String accessTool = ncmDevAccessUser.getAccessTool();
										bpmTaskMonRes.setResAcsMode(accessTool);
										String userName = ncmDevAccessUser.getUserName();
										bpmTaskMonRes.setUserName(userName);
									}
								}

								String orgId = ncmDevices.getOrgId();
								if(StringUtils.isNotBlank(orgId)){
									OrgOrganization orgOrganization = orgOrganDao.get(orgId);
									if(orgOrganization!=null){
										bpmTaskMonRes.setOrgName(orgOrganization.getOrgShortName());
									}
								}
								bpmTaskMonRes.setOrgID(orgId);

								Integer vendorId = ncmDevices.getVendorId();
								if(vendorId!=null){
									CmdbVendor cmdbVendor = cmdbVendorDao.get(vendorId);
									if(cmdbVendor!=null){
										bpmTaskMonRes.setVendorName(cmdbVendor.getDispName());
									}
								}

								bpmTaskMonRes.setVendorID(vendorId);
								bpmTaskMonRes.setModelOID(ncmDevices.getModelOid());

								mgmtIP = ncmDevices.getDevIpMgmt();
								bpmTaskMonRes.setResIp(mgmtIP);

								mgmtIpLong = ncmDevices.getDevIpLong();
								bpmTaskMonRes.setResIpLong(mgmtIpLong);

								bpmTaskMonRes.setResAcsUserId(ncmDevices.getDevAccessId());
								bpmTaskMonRes.setResTypeCode(ncmDevices.getDevTypeCode());
								bpmTaskMonRes.setResTypeName(ncmDevices.getDevTypeName());

								bpmTaskMonResList.add(bpmTaskMonRes);
							}

						}
//						如果是计算资源，节点关联的实体是表
						if(resClassCode!=null && resClassCode==11 && resId!=null){
							ResHosts resHosts = resHostsDao.get(resId);
							if(resHosts!=null){
								BeanUtils.copyProperties(resHosts, bpmTaskMonRes);
								bpmTaskMonRes.setOsVersion(resHosts.getOsVersionType());

								String orgId = resHosts.getOrgID();
								if(StringUtils.isNotBlank(orgId)){
									OrgOrganization orgOrganization = orgOrganDao.get(orgId);
									if(orgOrganization!=null){
										bpmTaskMonRes.setOrgName(orgOrganization.getOrgShortName());
									}
								}
								Integer vendorID = resHosts.getVendorID();
								if(vendorID!=null){
									CmdbVendor cmdbVendor = cmdbVendorDao.get(vendorID);
									if(cmdbVendor!=null){
										bpmTaskMonRes.setVendorName(cmdbVendor.getDispName());
									}
								}
								String devAcsUserId = resHosts.getDevAcsUserId();
								bpmTaskMonRes.setResAcsUserId(devAcsUserId);
								if(StringUtils.isNotBlank(devAcsUserId)){
									NcmDevAccessUser ncmDevAccessUser = ncmDevAccessUserDao.get(devAcsUserId);
									if(ncmDevAccessUser!=null){
										bpmTaskMonRes.setUserName(ncmDevAccessUser.getUserName());
									}
								}

								mgmtIP = resHosts.getIpAddress();
								bpmTaskMonRes.setResIp(mgmtIP);
								mgmtIpLong = resHosts.getIpLong();
								bpmTaskMonRes.setResIpLong(mgmtIpLong);
								bpmTaskMonRes.setResAcsMode(resHosts.getAccessmode());

								bpmTaskMonResList.add(bpmTaskMonRes);
							}
						}

					}
				}
			}

			bpmTaskMonBizDao.batchSave(bpmTaskMonBizList, 20);
			bpmTaskMonBizDao.flush();

			bpmTaskMonResDao.batchSave(bpmTaskMonResList, 20);
			bpmTaskMonResDao.flush();
		}
	}

	/*
	 * @author fengyao
	 * @Description 获取所有的检测任务
	 * @Date 17:38 2020/5/12
	 * @Param []
	 * return java.util.List<com.sino.bpm.entity.BpmTask>
	 **/
	public List<BpmTask> getAll() {
		logger.info("Enter BpmTaskService.getAll ...");
		List<BpmTask> list = bpmTaskDao.getAll();
		return list;
	}
}
