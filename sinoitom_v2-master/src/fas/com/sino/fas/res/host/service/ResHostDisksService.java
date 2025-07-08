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
import com.sino.fas.res.host.dao.ResHostDisksDao;
import com.sino.fas.res.host.entity.ResHostDisks;
import com.sino.fas.res.host.entity.ResHostFileSystem;

/**
 * @author Mr.LP
 * @date 2019-8-27下午7:24:34
 * @className ResHostDisksService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResHostDisksService {
	
	private static Logger logger = LoggerFactory.getLogger(ResHostDisksService.class);
	
	private static String objAttNames = "hostId,diskId,size,diskHeads,diskSectors,diskCylinders,cylinderSize,diskIdentifier,bytes,totalSectors,sectorsSize";
	private static String jsonAttNames = "hostId,id,size,diskHeads,diskSectors,diskCylinders,cylinderSize,diskIdentifier,bytes,totalSectors,sectorsSize";
	
	@Autowired
	private ResHostDisksDao resHostDisksDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResHostDisks> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResHostDisks obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResHostDisks> getAll() {
		return resHostDisksDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public ResHostDisks getById(Integer id){
		return resHostDisksDao.get(id);
	}
	
	public void save(ResHostDisks resHostDisks) {
		logger.debug("save...");
		resHostDisksDao.save(resHostDisks);
	}
	
	public List<ResHostDisks> getByHostId(Long hostId){
		String hql = " from ResHostDisks where hostId = ?";
		return resHostDisksDao.find(hql,hostId);
	}
	
	public void deleteList(List<ResHostDisks> diskList){
		resHostDisksDao.delete(diskList);
	}

}
