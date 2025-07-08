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
import com.sino.fas.res.host.dao.ResHostCpuDao;
import com.sino.fas.res.host.entity.ResHostCpu;

/**
 * @author Mr.LP
 * @date 2019-9-12上午11:00:16
 * @className ResHostCpuService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResHostCpuService {
	
private static Logger logger = LoggerFactory.getLogger(ResHostCpuService.class);
	
	private static String objAttNames = "hostId,cpuId,cpuVendor,cpuModel,cpuCoreId,cpuWidth,cpuQty,cpuCoreQty,cpuStruct";
	private static String jsonAttNames = "hostId,id,cpuVendor,cpuModel,cpuCoreId,cpuWidth,cpuQty,cpuCoreQty,cpuStruct";
	
	@Autowired
	private ResHostCpuDao resHostCpuDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResHostCpu> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResHostCpu obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResHostCpu> getAll() {
		return resHostCpuDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public ResHostCpu getById(Integer id){
		return resHostCpuDao.get(id);
	}
	
	public void save(ResHostCpu resHostCpu) {
		logger.debug("save...");
		resHostCpuDao.save(resHostCpu);
	}
	
	public List<ResHostCpu> getByHostId(Long hostId){
		String hql = " from ResHostCpu where hostId = ?";
		return resHostCpuDao.find(hql,hostId);
	}
	
	public void deleteList(List<ResHostCpu> cpuList){
		resHostCpuDao.delete(cpuList);
	}

}
