package com.sino.fas.res.net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.net.dao.NcmDevIfDao;
import com.sino.fas.res.net.entity.NcmDevInterfaces;

@Transactional
@Service
public class NcmDevIfService {

	private static String portObjAttNames ="devId,intfId,ifIndex,ifType,ifName,ifAlias,ifDisplay,ifSpeed,baudRate,ifPhysAddress,ifMTU,ifAdminStatus,ifOperStatus,ipAddr,netMask,netAddr,isTrunk";
	private static String portJsonAttNames ="devId,id,ifIndex,ifType,ifName,ifAlias,ifDisplay,ifSpeed,baudRate,ifPhysAddress,ifMTU,ifAdminStatus,ifOperStatus,ipAddr,netMask,netAddr,isTrunk";
	
	@Autowired
	private NcmDevIfDao ncmDevIfDao;
	
	@Transactional(readOnly = true)
	public List<NcmDevInterfaces> getAllSnmpIfByDevId(Long devId) {
		String hql = "from NcmDevInterfaces t where t.devId=? order by ifIndex";
		return ncmDevIfDao.find(hql, devId);
	}
	
	@Transactional(readOnly = true)
	public String getPortJsonListStr(final List<NcmDevInterfaces> list) {
		return JsonUtils.getJsonListInfo(list, portObjAttNames, portJsonAttNames);
	}
}
