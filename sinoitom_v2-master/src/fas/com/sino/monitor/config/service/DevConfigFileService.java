package com.sino.monitor.config.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.monitor.config.dao.DevConfigFileDao;
import com.sino.monitor.config.entity.DevConfigFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DevConfigFileService {
	
	private static String objAttName="id,devID,devIpAddr,cfgFileType,cfgFileName,cfgContent,collection_Time,baseLine";
	private static String jsonAttName="id,devID,devIpAddr,cfgFileType,cfgFileName,cfgContent,collection_Time,baseLine";
	
	@Autowired
	private DevConfigFileDao devConfigFileDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<DevConfigFile> list) {
		return JsonUtils.getJsonListInfo(list, objAttName, jsonAttName);
	}
	
	public List<DevConfigFile> getByDevID(long devID){
		String hql=" from DevConfigFile where devID=?";
		return devConfigFileDao.find(hql, devID);
	}
	
	public DevConfigFile getByID(long id){
		return devConfigFileDao.get(id);
	}
	
	public void delete(long id){
		devConfigFileDao.delete(id);
	}
	public void deletes(List<Long> ids){
		devConfigFileDao.deletes(ids);
	}
	
	public void setBaseLine(long id){
		devConfigFileDao.setBaseLine(id);
	}
	
	
	public void save(DevConfigFile file){
		devConfigFileDao.save(file);
	}
}
