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

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.dao.ResServiceProcessDao;
import com.sino.fas.res.host.entity.ResServiceProcess;

/**
 * @author Mr.LP
 * @date 2019-10-17下午2:24:08
 * @className ResServiceProcessService
 *
 * @Description TODO
 *
 */
//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResServiceProcessService {
	
private static Logger logger = LoggerFactory.getLogger(ResServiceProcessService.class);
	
	private static String objAttNames = "svcProcId,hostId,hostIp,pid,ppid,startCmd,procType,procName,procDescr,flag,status,creator,createTime,orgID,orgID@OrgOrganization@orgId@orgShortName,resClassCode,resClassName,resTypeCode,resTypeName,adminStatus,runStatus";
	private static String jsonAttNames = "id,hostId,hostIp,pid,ppid,startCmd,procType,procName,procDescr,flag,status,creator,createTime,orgID,orgName,resClassCode,resClassName,resTypeCode,resTypeName,adminStatus,runStatus";
	
	@Autowired
	private ResServiceProcessDao resServiceProcessDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResServiceProcess> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResServiceProcess obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResServiceProcess> getAll() {
		String hql = "from ResServiceProcess order by hostIp";
		return resServiceProcessDao.find(hql);
	}
	
	@Transactional(readOnly = true)
	public List<ResServiceProcess> getByHostId(Long hostId) {
		String hql = "from ResServiceProcess where hostId = ?";
		return resServiceProcessDao.find(hql,hostId);
	}
	
	@Transactional(readOnly = true)
	public ResServiceProcess getById(Long id){
		return resServiceProcessDao.get(id);
	}
	
	public void save(ResServiceProcess resServiceProcess) {
		logger.debug("save...");
		resServiceProcessDao.save(resServiceProcess);
	}
	
	public void batchSave(List<ResServiceProcess> list) {
		logger.debug("save...");
		resServiceProcessDao.batchSave(list, list.size());
	}
	
	public void delete(Long id){
		resServiceProcessDao.delete(id);
	}
	
	public void batchDelete(List<ResServiceProcess> list){
		resServiceProcessDao.delete(list);
	}
	
	public List<ResServiceProcess> getListByPids(List<Integer> pids){
		return resServiceProcessDao.getListByPids(pids);
	}

}
