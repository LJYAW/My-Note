package com.sino.area.web;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sino.area.entity.AdvCity;
import com.sino.area.entity.AdvCountry;
import com.sino.area.entity.AdvCounty;
import com.sino.area.entity.AdvProvince;
import com.sino.area.service.AdvCityService;
import com.sino.area.service.AdvCountryService;
import com.sino.area.service.AdvCountyService;
import com.sino.area.service.AdvProvinceService;


/**
* @ClassName: AdvCountryAction 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:17:04 
*  
*/
@Controller
@RequestMapping(value = "/country")
public class AdvCountryAction {
	@Autowired
	private AdvCountryService countryService;
//	@Autowired
//	private AdvAreaService areaService;
	@Autowired
	private AdvCityService cityService;
	@Autowired
	private AdvCountyService countyService;
	@Autowired
	private AdvProvinceService provinceService;
	
	private String viewPath = "/custom/info";
	
	private static Logger logger = LoggerFactory.getLogger(AdvCountryAction.class);
	
	
//	查找所有国家
	@RequestMapping(value = "/getAllCountry.do")
	@ResponseBody
	public String getAllCountry() {
		logger.info( "Enter AdvCountryAction getAllCountry.do ..." );
		List<AdvCountry> list = countryService.getAllbyFlag(1);
		String jsonStr = countryService.getJsonListStr(list);
		return jsonStr;
	} 
	
	/**
	 * 根据countryCode获取国家
	 * @param customerType	Mr.LP
	 * @return
	 */
	@RequestMapping(value = "/getAllCountryByCustomerType.do")
	@ResponseBody
	public String getAllCountryByCustomerType(Integer customerType) {
		logger.info( "Enter AdvCountryAction getAllCountry.do ..." );
		List<AdvCountry> list = countryService.getAllbyFlagAndCountryCode(1, customerType);
		String jsonStr = countryService.getJsonListStr(list);
		return jsonStr;
	} 
	
//	查找国家对应的所有省+直辖市
	@RequestMapping(value = "/getByCountry.do")
	@ResponseBody
	public String getByCountry(String countryCode) {
		logger.info( "Enter AdvCountryAction getByCountry.do ..." );
		List<AdvProvince> list = provinceService.getByCountry(countryCode);
		String jsonStr = provinceService.getJsonListStr(list);
		return jsonStr;
	} 
	
//	查找省对应的所有市
	@RequestMapping(value = "/getByProV.do")
	@ResponseBody
	public String getByProV(Integer provinceCode) {
		logger.info( "Enter AdvCountryAction getByProV.do ..." );
		
		JSONObject jo = new JSONObject();
		AdvProvince province = provinceService.getById(provinceCode);
		Integer type = province.getType();
		if(type!=2&&type!=4){
			List<AdvCity> list = cityService.getByProV(provinceCode);
			String jsonStr = cityService.getJsonListStr(list);
			return jsonStr;
			
		}else{
			List<AdvCity> list = cityService.getByProV(provinceCode);
			Integer ZcityCode = list.get(0).getCityCode();
			jo.put("ZcityCode", ZcityCode);
			return jo.toString();
		}
		
//		return null;
	}

//	查找市对应的所有区
	@RequestMapping(value = "/getByCity.do")
	@ResponseBody
	public String getByCity(Integer cityCode) {
		logger.info( "Enter AdvCountryAction getByCity.do ..." );
		List<AdvCounty> list = countyService.getByCity(cityCode);
		String jsonStr = countyService.getJsonListStr(list);
		return jsonStr;
	} 
	
	
}
