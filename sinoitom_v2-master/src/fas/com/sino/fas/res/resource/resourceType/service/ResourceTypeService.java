/*
 * 文件名： ResourceTypeService.java
 * 
 * 创建日期： 2013-12-21
 *
 * Copyright(C) 2013, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.resource.resourceType.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.fas.res.resource.resourceType.dao.ResourceTypeDao;
import com.sino.fas.res.resource.resourceType.entity.ParamResType;
import com.sino.fas.res.resource.resourceType.entity.ResourceType;


/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.13 $
 *
 * @since 2013-12-21
 */
//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResourceTypeService {
	
	private static Logger logger = LoggerFactory.getLogger(ResourceTypeService.class);

	private static String objAttNames = "classID,parentId,treeCode,expand,nodeFlag,privilegeLevel,dispName,className,description,objectType,objectName,objectCount,remark,icon,resClassName,resTypeName,resClassCode,resTypeCode";

	private static String jsonAttNames = "id,parentId,treeCode,expand,nodeFlag,privilegeLevel,dispName,className,description,objectType,objectName,objectCount,remark,icon,resClassName,resTypeName,resClassCode,resTypeCode";

	private static String childAttName = "children";
	
	private static String objMinAttNames = "classID,dispName,treeCode,objectName,className,resTypeCode,icon,isexpand";
	
	private static String jsonMinAttNames = "id,dispName,treeCode,objectName,className,resTypeCode,icon,isexpand";
	
	private static String objAttNames1 = "classID,parentId,treeCode,expand,nodeFlag,privilegeLevel,dispName,className,description,objectType,objectName,objectCount,remark";

	private static String jsonAttNames1 = "id,parentId,treeCode,expand,nodeFlag,privilegeLevel,dispName,className,description,objectType,objectName,objectCount,remark";
	private static int treeLevelLength = 3;
	
	private String lastMassage = "";
	@Autowired
	private ResourceTypeDao resourceTypeDao;
	
	
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}

	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResourceType obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResourceType> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<ResourceType> list) {
		return JsonUtils.getJsonTreeInfo(list, objAttNames, jsonAttNames, childAttName);
	}
	@Transactional(readOnly = true)
	public String getJsonTreeStr1(final List<ResourceType> list) {
		return JsonUtils.getJsonTreeInfo(list, objAttNames1, jsonAttNames1, childAttName);
	}
	
	@Transactional(readOnly = true)
	public String getJsonMinTreeStr(final List<ParamResType> list) {
		return JsonUtils.getJsonTreeInfo(list, objMinAttNames, jsonMinAttNames, childAttName);
	}

	@Transactional(readOnly = true)
	public List<ResourceType> getListData() {
		logger.debug("getListData...");
		return resourceTypeDao.getAll("treeCode",true);
	}
	
	
	
	public ResourceType getByParentId(String parentId){
		logger.debug("Enter getByParentId...");
		return resourceTypeDao.get(parentId);
	}
	
	public ResourceType getById(String id){
		logger.debug("Enter getById...");
		return resourceTypeDao.get(id);
	}
	
	public void save(ResourceType resType){
		logger.debug("Enter save...");
		 resourceTypeDao.save(resType);
	}
	
	public void delete(String id){
		logger.debug("Enter delete.do...");
		resourceTypeDao.delete(id);
	}
	
	public void deletes(String[] ids){
		for(String id:ids){
			delete(id);
		}
	}
	
	public boolean moveResType(String moveId, String targetId, int moveType){
		this.lastMassage = "";
		if ( !resourceTypeDao.moveNode(moveId, targetId, moveType, treeLevelLength) )
		{
			this.lastMassage = "移动机构或目标机构已经不存在!";
			return false;
		}

		return true;
	}
	
	public void add(ResourceType resType,String parentID){
		logger.debug("Enter add");
		if(StringUtil.isNullString(parentID)){
			resType.setParentId("");
			resType.setClassID(Identities.uuid());
			resType.setNodeFlag(0);
		}else{
			resType.setParentId(parentID);
			resType.setClassID(Identities.uuid());
			resType.setNodeFlag(1);
		}
		String treeCode = resourceTypeDao.getNewChildCode(parentID, treeLevelLength);
		resType.setTreeCode(treeCode);
		resourceTypeDao.save(resType);
	}
	public String getJsonServerTree(String treeCode){
		String hql="from ResourceType where treeCode like ?";
		List<ResourceType> resourceType=new ArrayList<ResourceType>();
		if(treeCode.length()==6){
			resourceType=resourceTypeDao.findSubTree(treeCode);
			return getJsonTreeStr1(resourceType);
		}else{
			resourceType=resourceTypeDao.find(hql,treeCode);
			return getJsonListStr(resourceType);
		}
	}
	
	public String getJsonTreeByName(String disName){
		String hql="from ResourceType where dispName like ?";
		List<ResourceType> resourceType=new ArrayList<ResourceType>();
		resourceType=resourceTypeDao.find(hql,disName);
		if(resourceType!=null&&!resourceType.isEmpty()){
			return getJsonServerTree(resourceType.get(0).getTreeCode());
		}
		return "";
	}
	
	
	public Long getResCount(String treeCode){
		String hql="select count(m) from ClassifyObjectMap m,ResourceType t WHERE t.treeCode LIKE :treecode AND m.classID=t.classID ";
		Query query=resourceTypeDao.createQuery(hql);
		query.setParameter("treecode", treeCode+"%");
		return ((Long) query.iterate().next()).longValue();
	}
	
	public Long getDevOrSwtCount(String objName){
		//objName=objName.replace("_", "");
		int i=3;
		if(objName.equals("Ncm_Switch"))
			i=2;
		String hql="select count(p) from NpmDevicePerfPoll p where p.deviceType="+i;//where n.orgId<>:oId
		Query query=resourceTypeDao.createQuery(hql);
		return ((Long) query.iterate().next()).longValue();
	}
	
	public long getCounts(String hql){
		return resourceTypeDao.getCounts(hql);
	}
}
