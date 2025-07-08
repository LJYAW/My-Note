/**
 * 
 */
package com.sino.fas.res.host.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.entity.ParamPerformance;

/**
 * @author Mr.LP
 * @date 2019-12-3上午11:23:33
 * @className ParamPerformanceService
 * @version V1.0
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ParamPerformanceService {
	
	private static Logger logger = LoggerFactory.getLogger(ParamPerformanceService.class);
	
	private static String objAttNames = "memorySize,freeMemorySize,usedMemorySize,usedMem,userUsed,sysUsed";
	private static String jsonAttNames = "memorySize,freeMemorySize,usedMemorySize,usedMem,userUsed,sysUsed";
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ParamPerformance> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ParamPerformance obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

}
