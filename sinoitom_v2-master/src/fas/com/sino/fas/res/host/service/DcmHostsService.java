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
import com.sino.fas.res.host.dao.DcmHostsDao;
import com.sino.fas.res.host.entity.DcmHosts;

/**
 * @author Mr.LP
 * @date 2019-8-21上午9:30:27
 * @className DcmHostsService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class DcmHostsService {
	
	private static Logger logger = LoggerFactory.getLogger(DcmHostsService.class);
	
	private static String objAttNames = "orgID,hostID,hostName,ipAddress,ipLong,ipNetMask,macAddress,vendorID,vendorName,prodClassCode,prodClassName,prodTypeCode,prodTypeName,prodSeriesID,prodSeries,prodModel,modelOID,productNO,productDesc,productSN,cpuStruct,cpuVendor,cpuModel,cpuDesc,cpuWidth,cpuQty,cpuCoreQty,memorySize,memoryDisplay,diskSize,diskDisplay,osName,osVersion,osRelase,assetNO,powerStatus,hostStatus,creator,createTime,modifier,modifyTime,description,administrator,connStatus";
	private static String jsonAttNames = "orgID,id,hostName,ipAddress,ipLong,ipNetMask,macAddress,vendorID,vendorName,prodClassCode,prodClassName,prodTypeCode,prodTypeName,prodSeriesID,prodSeries,prodModel,modelOID,productNO,productDesc,productSN,cpuStruct,cpuVendor,cpuModel,cpuDesc,cpuWidth,cpuQty,cpuCoreQty,memorySize,memoryDisplay,diskSize,diskDisplay,osName,osVersion,osRelase,assetNO,powerStatus,hostStatus,creator,createTime,modifier,modifyTime,description,administrator,connStatus";
	
	@Autowired
	private DcmHostsDao dcmHostsDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<DcmHosts> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final DcmHosts obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<DcmHosts> getAll() {
		return dcmHostsDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public DcmHosts getById(String id){
		return dcmHostsDao.get(id);
	}
	

}
