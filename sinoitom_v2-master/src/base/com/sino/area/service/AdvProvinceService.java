package com.sino.area.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.area.dao.AdvProvinceDao;
import com.sino.area.entity.AdvProvince;
import com.sino.base.common.util.JsonUtils;

/**
* @ClassName: AdvProvinceService 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午10:53:49 
*  
*/ 
@Service
@Transactional
public class AdvProvinceService {
	@Autowired
	private AdvProvinceDao provinceDao;
	private static Logger logger = LoggerFactory.getLogger(AdvProvinceService.class);
	
	private static String objAttNames = "countryCode,areaID,provCode,alphaCode,name,shortName,displayName,type,typeName";
	private static String jsonAttNames = "countryCode,areaID,provCode,alphaCode,name,shortName,displayName,type,typeName";
	
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final AdvProvince obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AdvProvince> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<AdvProvince> getAll(){
		logger.info("Enter AdvProvinceService getAll..");
		return provinceDao.getAll();
	}

	public List<AdvProvince> getByCountry(String countryCode) {
		List<AdvProvince> list = provinceDao.getByCountry(countryCode);
		return list;
	}
	
	@Transactional(readOnly = true)
	public AdvProvince getById(Integer provCode){
		return provinceDao.get(provCode);
	}
	
	
}
