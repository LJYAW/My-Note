package com.sino.ack.devCheckItem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import com.sino.ack.devCheckItem.dao.AckDevCheckItemsDao;
import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;

@Service
@Transactional
public class AckDevCheckItemsService {
	
	private static String objAttNames ="devChkItemId,devId,devIp,indItemID,indItemEnName,valueType,length,decimals,measureUnit,checkCmd,resClassCode,resClassName,resTypeCode,resTypeName,indItemName,indGroupName,status,parseRuldId";
	private static String jsonAttNames="id,devId,devIp,indItemID,indItemEnName,valueType,length,decimals,measureUnit,checkCmd,resClassCode,resClassName,resTypeCode,resTypeName,indItemName,indGroupName,status,parseRuldId";

	@Autowired
	private AckDevCheckItemsDao ackDevCheckItemDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AckDevCheckItems> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final AckDevCheckItems obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	public List<AckDevCheckItems> getAll() {
		return ackDevCheckItemDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public String getJsonPageList(final Page<AckDevCheckItems> page) {
		return JsonUtils.getJQJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<AckDevCheckItems> getByTypeCode(int code){
		String hql=" from AckDevCheckItems where resTypeCode=?";
		return ackDevCheckItemDao.find(hql, code);
	}
	
	@Transactional(readOnly = true)
	public List<AckDevCheckItems> getByTypeCodeAndIP(int code,String devIp){
		String hql=" from AckDevCheckItems where resTypeCode=? and devIp=?";
		return ackDevCheckItemDao.find(hql, code,devIp);
	}
	
	public void saveResSnmpInd(List<AckResources> monResList,List<CmdbProdCmdChkItems> itemList){
		List<AckDevCheckItems> snmpIndList=new ArrayList<AckDevCheckItems>();
		
		//关联之前先清空原始关联数据
//		for(int i=0;i<monResList.size();i++){
//			MonResource res=monResList.get(i);
//			resIPs.add(res.getResIP());
//			resSnmpIndicatorDao.deleteByResIPs(resIPs);
//			resSnmpIndicatorDao.flush();
//		}
		
		for(int i=0;i<monResList.size();i++){
			
			AckResources res=monResList.get(i);
			for(int j=0;j<itemList.size();j++){
				CmdbProdCmdChkItems item=itemList.get(j);
				
				AckDevCheckItems findInd=getUniqueData(res.getResId(),item.getIndItemID());
				if(findInd==null){
					if(item.getModelOID().equals("X.X")||res.getModelOID().equals(item.getModelOID())){
						AckDevCheckItems ind=new AckDevCheckItems();
						BeanUtils.copyProperties(res, ind);
						BeanUtils.copyProperties(item, ind);
						ind.setDevIp(res.getMgmtIP());
						ind.setDevId(res.getResId());
						ind.setIndItemEnName(item.getIndItemEnName());
						ind.setCheckCmd(item.getCheckCmd());
						ind.setMeasureUnit(item.getMeasureUnit());
						ind.setParseRuldId(item.getChkItemId());
						ind.setStatus(0);
						snmpIndList.add(ind);
					}
					
				}
				
			}
		}
		ackDevCheckItemDao.batchSave(snmpIndList, 20);
	}
	
	public AckDevCheckItems getUniqueData(long devId,long chkItemId){
		String hql=" from AckDevCheckItems where devId=? and indItemID=?";
		return ackDevCheckItemDao.findUnique(hql, devId,chkItemId);
	}
	public AckDevCheckItems getById(Long id){
		String hql=" from AckDevCheckItems where devChkItemId=?";
		return ackDevCheckItemDao.findUnique(hql, id);
	}
	
	public Page<AckDevCheckItems> findAjaxSearch(PageRequest pageRequest,Integer resClassCode,Integer  resTypeCode,String devIp ){
		
		Map params=new HashMap();
		StringBuffer hql=new StringBuffer("from AckDevCheckItems ");
		if(resClassCode!=null){
			hql.append(" where resClassCode=:resClassCode ");
			params.put("resClassCode", resClassCode);
		}
		if(resTypeCode!=null){
			hql.append(" and resTypeCode=:resTypeCode ");
			params.put("resTypeCode", resTypeCode);
		}
		if(devIp!=null){
			hql.append(" and devIp=:devIp ");
			params.put("devIp", devIp);
		}
		
		Page pageinfo=ackDevCheckItemDao.findAjaxPage(pageRequest,hql.toString(), params);
		return pageinfo;
		
	}
	
	public void deletes(String [] ids){
		for(String id:ids){
			delete(Long.parseLong(id));
		}
	}
	public void delete(Long id){
		ackDevCheckItemDao.delete(id);
	}
	public void batchSave(List<AckDevCheckItems> list){
		ackDevCheckItemDao.batchSave(list, 10);
	}
	
}
