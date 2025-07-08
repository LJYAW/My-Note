package com.sino.fas.res.biz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.biz.dao.RfcServicesDao;
import com.sino.fas.res.biz.entity.RfcServices;


//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class RfcServicesService {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(RfcServicesService.class);
	
	private static String objAttNames = "serviceId,protId,serviceName,servicePort";
	private static String jsonAttNames = "serviceId,protId,serviceName,servicePort";
	
	private static String objAllAttNames = "serviceId,protId,serviceName,servicePort,comment";
	private static String jsonAllAttNames = "serviceId,protId,serviceName,servicePort,comment";
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<RfcServices> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getAllJsonListStr(final List<RfcServices> list) {
		return JsonUtils.getJsonListInfo(list, objAllAttNames, jsonAllAttNames);
	}
	
	@Autowired
	private RfcServicesDao rfcServicesDao;

	public void deleteRfcServicesTX(Integer id) {
		rfcServicesDao.delete(id);
	}

	@Transactional(readOnly = true)
	public List<RfcServices> getAllRfcServicesTX() {
		List<RfcServices> list = rfcServicesDao.getAll("serviceName", true);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<RfcServices> getLegalRfcServicesTX() {
		String hql="from RfcServices where servicePort >= 0 order by serviceName";
		List<RfcServices> list = rfcServicesDao.find(hql);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<RfcServices> getListRfcServicesTX() {
		String hql="from RfcServices where serviceId>4 order by serviceName";
		List<RfcServices> list = rfcServicesDao.find(hql);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<RfcServices> getRfcServicesByProtIdTX(int id) {
		List<RfcServices> list = rfcServicesDao.find("from RfcServices where protId=? order by serviceName",id);
		return list;
	}

	@Transactional(readOnly = true)
	public RfcServices getRfcServicesTX(Integer id) {
		RfcServices rfcServices = rfcServicesDao.get(id);
		return rfcServices;
	}

	public void saveRfcServicesTX(RfcServices services) {
		this.rfcServicesDao.save(services);
	}

	public void updateRfcServicesTX(RfcServices services) {
		this.rfcServicesDao.save(services);
	}

	@Transactional(readOnly = true)
	public RfcServices getServIdByName(String name){
		List<RfcServices> list = rfcServicesDao.find("from RfcServices where serviceName=?",name);
		RfcServices servInfo = list.get(0);
		return servInfo;
	}
	
	@Transactional(readOnly = true)
	public List<RfcServices> getBySvcPort(Integer servicePort) {
		String hql = "from RfcServices where servicePort = ?";
		return rfcServicesDao.find(hql,servicePort);
	}
}
