/**
 * 
 */
package com.sino.fas.res.host.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.dao.ResAppServiceDao;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.res.biz.dao.ResAppServiceMapDao;
import com.sino.res.biz.entity.ResAppServiceMap;

/**
 * @author Mr.LP
 * @date 2019-10-17下午3:01:18
 * @className ResAppServiceService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResAppServiceService {
	
private static Logger logger = LoggerFactory.getLogger(ResAppServiceService.class);
	
	private static String objAttNames = "appSvcId,hostId,hostIp,svcProcId,protNo,protName,svcPort,svcName,command,svcClass,appSvcName,appSvcDescr,usageDescr,svcUrl,orgID,orgID@OrgOrganization@orgId@orgShortName,resClassCode,resClassName,resTypeCode,resTypeName,svcStatus,respTime,adminStatus,pid";
	private static String jsonAttNames = "id,hostId,hostIp,svcProcId,protNo,protName,svcPort,svcName,command,svcClass,appSvcName,appSvcDescr,usageDescr,svcUrl,orgID,orgName,resClassCode,resClassName,resTypeCode,resTypeName,svcStatus,respTime,adminStatus,pid";
	
	@Autowired
	private ResAppServiceDao resAppServiceDao;
	
	@Autowired
	private ResAppServiceMapDao resAppServiceMapDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResAppService> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResAppService obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResAppService> getAll() {
		String hql = "from ResAppService order by hostIp";
		return resAppServiceDao.find(hql);
	}
	
	public List<ResAppService> getDataList(Long resId){
		List<ResAppService> list = resAppServiceDao.getDataList(resId);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<ResAppService> getByHostId(Long hostId) {
		String hql = "from ResAppService where hostId = ?";
		return resAppServiceDao.find(hql ,hostId);
	}
	
	@Transactional(readOnly = true)
	public ResAppService getById(Long id){
		return resAppServiceDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public List<ResAppService> getBySvcProcId(Long svcProcId){
		String hql = "from ResAppService where svcProcId = ?";
		return resAppServiceDao.find(hql,svcProcId);
	}
	
	public void save(ResAppService resAppService) {
		logger.debug("save...");
		resAppServiceDao.save(resAppService);
		resAppServiceDao.flush();
	}
	
	public void batchSave(List<ResAppService> list) {
		logger.debug("save...");
		resAppServiceDao.batchSave(list, list.size());
	}
	
	public void delete(Long id){
		resAppServiceDao.delete(id);
	}
	
	public void batchDelete(List<ResAppService> list){
		resAppServiceDao.delete(list);
	}
	
	@Transactional(readOnly = true)
	public ResAppService getByHostIpAndSvcPort(String hostIp,Integer svcPort){
		String hql = "from ResAppService where hostIp = ? and svcPort = ?";
		return resAppServiceDao.findUnique(hql,hostIp,svcPort);
	}

	//查询hostIp
	public List<ResAppService> getHostIp(){
		String hql = "select hostIp from ResAppService group by hostIp";
		return resAppServiceDao.find(hql);
	}
	//查询hostIp获取右侧列表
//		public List<ResAppService> getListByHostIp(String hostIp){
//			String hql = "from ResAppService where hostIp=?";
//			return resAppServiceDao.find(hql,hostIp);
//		}
	//查询hostIp获取右侧列表	
	public List<ResAppService> getListByHostIp(String hostIp,Long resId){
			List<ResAppService> list = resAppServiceDao.getListByHostIp(hostIp,resId);
			return list;
		}	
		public ResAppService getListByAppSvcId(Long appSvcId){
			String hql = "from ResAppService where appSvcId=?";
			return resAppServiceDao.findUnique(hql,appSvcId);
		}
		
		public List<ResAppService> getByHostIpAndResIdAndSvcId(String hostIp,Long resId,List<Long> svcIdList){
			List<ResAppService> list = resAppServiceDao.getByHostIpAndResIdAndSvcId(hostIp,resId,svcIdList);
			return list;
		}
		
		
		public List<ResAppService> getByResIdAndSvcId(Long resId,List<Long> svcIdList){
			List<ResAppService> list = resAppServiceDao.getByResIdAndSvcId(resId,svcIdList);
			return list;
		}
		
		public List<ResAppService> getBybizAppId(Long bizAppId){
			return resAppServiceDao.getBybizAppId(bizAppId);
		}
	
}
