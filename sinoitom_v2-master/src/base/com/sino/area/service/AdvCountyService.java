package com.sino.area.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.area.dao.AdvCountyDao;
import com.sino.area.entity.AdvCounty;
import com.sino.base.common.util.JsonUtils;

/**
* @ClassName: AdvCountyService 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:13:57 
*  
*/ 
@Service
@Transactional
public class AdvCountyService {
	@Autowired
	private AdvCountyDao countyDao;
	private static Logger logger = LoggerFactory.getLogger(AdvCountyService.class);
	
	private static String objAttNames = "cityCode,countyCode,countyType,countyName";
	private static String jsonAttNames = "cityCode,countyCode,countyType,countyName";
	
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final AdvCounty obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AdvCounty> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<AdvCounty> getAll(){
		logger.info("Enter AdvCityService getAll..");
		return countyDao.getAll();
	}
//	根据cityCode查找所有的区
	public List<AdvCounty> getByCity(Integer cityCode) {
		List<AdvCounty> list = countyDao.getByCity(cityCode);
		return list;
	}
}
