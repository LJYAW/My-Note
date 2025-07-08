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
import com.sino.fas.res.host.dao.ResHostFileSystemDao;
import com.sino.fas.res.host.entity.ResHostFileSystem;

/**
 * @author Mr.LP
 * @date 2019-8-26上午11:00:05
 * @className ResDiskService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResHostFileSystemService {
	
	private static Logger logger = LoggerFactory.getLogger(ResHostFileSystemService.class);
	
	private static String objAttNames = "hostId,fileSysId,filesystem,size,usedSize,availSize,diskUse,mountPoint,bytes";
	private static String jsonAttNames = "hostId,id,filesystem,size,usedSize,availSize,diskUse,mountPoint,bytes";
	
	@Autowired
	private ResHostFileSystemDao resHostFileSystemDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResHostFileSystem> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResHostFileSystem obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResHostFileSystem> getAll() {
		return resHostFileSystemDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public ResHostFileSystem getById(String id){
		return resHostFileSystemDao.get(id);
	}
	
	public void save(ResHostFileSystem resHostFileSystem) {
		logger.debug("save...");
		resHostFileSystemDao.save(resHostFileSystem);
	}
	
	public void branchSave(List<ResHostFileSystem> diskList) {
		logger.debug("save...");
		resHostFileSystemDao.batchSave(diskList, diskList.size());
	}
	
	public List<ResHostFileSystem> getByHostId(Long hostId){
		String hql = " from ResHostFileSystem where hostId = ?";
		return resHostFileSystemDao.find(hql,hostId);
	}
	
	public void deleteList(List<ResHostFileSystem> fileList){
		resHostFileSystemDao.delete(fileList);
	}

}
