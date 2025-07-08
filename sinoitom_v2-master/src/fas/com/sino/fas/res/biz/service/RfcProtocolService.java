package com.sino.fas.res.biz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.biz.dao.RfcProtocolDao;
import com.sino.fas.res.biz.entity.RfcProtocol;


//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class RfcProtocolService {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(RfcProtocolService.class);
	
	private static String objAttNames = "protId,protName,aliases,comment";
	private static String jsonAttNames = "protId,protName,aliases,comment";
	
	@Autowired
	private RfcProtocolDao rfcProtocolDao;

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<RfcProtocol> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public void deleteRfcProtocolTX(Integer id) {
		rfcProtocolDao.delete(id);
	}

	@Transactional(readOnly = true)
	public List<RfcProtocol> getAllRfcProtocolTX() {
		List<RfcProtocol> list = rfcProtocolDao.getAll("protName", true);
		return list;
	}

	@Transactional(readOnly = true)
	public RfcProtocol getRfcProtocolTX(Integer id) {
		RfcProtocol protocol = rfcProtocolDao.get(id);
		return protocol;
	}

	public void saveRfcProtocolTX(RfcProtocol protocol) {
		rfcProtocolDao.save(protocol);
	}

	public void updateRfcProtocolTX(RfcProtocol protocol) {
		rfcProtocolDao.save(protocol);
	}

}
