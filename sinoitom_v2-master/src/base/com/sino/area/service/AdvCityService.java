package com.sino.area.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.area.dao.AdvCityDao;
import com.sino.area.entity.AdvCity;
import com.sino.base.common.util.JsonUtils;

/**
* @ClassName: AdvCityService 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:02:49 
*  
*/ 
@Service
@Transactional
public class AdvCityService {
	@Autowired
	private AdvCityDao cityDao;
	private static Logger logger = LoggerFactory.getLogger(AdvCityService.class);
	
	private static String objAttNames = "provCode,cityCode,CityType,cityName";
	private static String jsonAttNames = "provCode,cityCode,CityType,cityName";
	
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final AdvCity obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AdvCity> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<AdvCity> getAll(){
		logger.info("Enter AdvCityService getAll..");
		return cityDao.getAll();
	}

//	根据provinceCode查找市
	public List<AdvCity> getByProV(Integer provinceCode) {
		List<AdvCity> list = cityDao.getByProV(provinceCode);
		return list;
	}
}
