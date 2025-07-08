package com.sino.fas.res.net.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.net.dao.FDBTableDao;
import com.sino.fas.res.net.entity.NcmFDBTable;
import com.sino.fas.res.net.entity.NcmFDBTableInfo;
import com.sino.fas.res.net.entity.NcmSwitchPort;

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class FDBTableService {
	private static String portObjAttNames = "FDBTable_ID,switch_ID,portName,vlan_ID,macAddress,fdbPort,is_TrunkPort,port_Status,is_MultiVlan";
	private static String portJsonAttNames = "id,switch_ID,portName,vlan_ID,macAddress,fdbPort,is_TrunkPort,port_Status,is_MultiVlan";
	
	@Autowired
	private FDBTableDao fDBTableDao;
	
	//交换机接口
	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmFDBTableInfo obj) {
		return JsonUtils.getJsonObjInfo(obj, portObjAttNames, portJsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmFDBTableInfo> list) {
		return JsonUtils.getJsonListInfo(list, portObjAttNames, portJsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<NcmFDBTableInfo> list) {
		return JsonUtils.getJsonListInfo(list, portObjAttNames, portJsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public NcmFDBTable getFDBTable(long ifId) {
		return fDBTableDao.get(ifId);
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public List<NcmFDBTableInfo> getAllFDBTable(long switchId) {
		List<NcmFDBTableInfo> fdblist=new ArrayList<NcmFDBTableInfo>();
		Collection result = new ArrayList(); 
		String hql = "from NcmFDBTable as fdb,NcmSwitchPort as port where fdb.switch_ID=? and fdb.switch_ID=port.id.switchId and fdb.fdbPort=port.portNo order by fdb.fdbPort,fdb.is_TrunkPort";
		Query query = fDBTableDao.createQuery(hql, switchId);
		result=query.list();
		ArrayList sList = (ArrayList) result; 
		Iterator iterator1 = sList.iterator(); 
		while (iterator1.hasNext()) {
			Object[] o = (Object[]) iterator1.next(); 
			NcmFDBTable fdbtab = (NcmFDBTable) o[0]; 
			NcmSwitchPort porttab = (NcmSwitchPort) o[1];
			NcmFDBTableInfo fdb =new NcmFDBTableInfo(fdbtab,porttab.getIfAliasName());
			fdblist.add(fdb);
		} 
		return fdblist;
	}
}
