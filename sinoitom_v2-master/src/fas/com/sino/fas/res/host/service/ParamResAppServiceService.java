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
import com.sino.fas.res.host.entity.ParamResAppService;

/**
 * @author Mr.LP
 * @date 2019-10-21下午3:17:27
 * @className ParamResAppServiceService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ParamResAppServiceService {
	
private static Logger logger = LoggerFactory.getLogger(ParamResAppServiceService.class);
	
	private static String objAttNames = "id,hostId,hostIp,svcProcId,protNo,protName,svcPort,svcName,command,svcClass,appSvcName,appSvcDescr,usageDescr,svcUrl,pid";
	private static String jsonAttNames = "id,hostId,hostIp,svcProcId,protNo,protName,svcPort,svcName,command,svcClass,appSvcName,appSvcDescr,usageDescr,svcUrl,pid";
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ParamResAppService> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ParamResAppService obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

}
