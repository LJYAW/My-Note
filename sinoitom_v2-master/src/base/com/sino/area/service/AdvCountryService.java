package com.sino.area.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.area.dao.AdvCountryDao;
import com.sino.area.entity.AdvCountry;
import com.sino.base.common.util.JsonUtils;

/**
* @ClassName: AdvCountryService 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午10:50:23 
*  
*/ 
@Service
@Transactional
public class AdvCountryService {
	@Autowired
	private AdvCountryDao countryDao;
	private static Logger logger = LoggerFactory.getLogger(AdvCountryService.class);
	
	private static String objAttNames = "countryName,enName,countryCode,telCode,flag";
	private static String jsonAttNames = "countryName,enName,countryCode,telCode,flag";
	
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final AdvCountry obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AdvCountry> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<AdvCountry> getAll(){
		logger.info("Enter CrmCustomerService getAll..");
		return countryDao.getAll();
	}

	public List<AdvCountry> getAllbyFlag(Integer flag) {
		List<AdvCountry> list = countryDao.getAllbyFlag(flag);
		return list;
	}
	
	/**
	 * 根据countryCode获取国家
	 * @param flag
	 * @param customerType	Mr.LP
	 * @return
	 */
	public List<AdvCountry> getAllbyFlagAndCountryCode(Integer flag , Integer customerType) {
		List<AdvCountry> list = countryDao.getAllbyFlagAndCountryCode(flag,customerType);
		return list;
	}

}
