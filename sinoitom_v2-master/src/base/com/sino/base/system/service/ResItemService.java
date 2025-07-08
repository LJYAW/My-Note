package com.sino.base.system.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.system.dao.SysResGroupDao;
import com.sino.base.system.dao.SysResItemDao;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.common.Global;
import com.sino.base.common.SampleResItem;
import com.sino.base.common.tree.TreeUtils;
import com.sino.base.common.util.JsonUtils;


/**
 * 系统资源管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResItemService {
	private static Logger logger = LoggerFactory.getLogger(ResItemService.class);

	private static String objAttNames = "resId,resName,resCode,resNumber,description,state@resItemState,treeCode";
	private static String jsonAttNames = "id,resName,resCode,resNumber,description,state,treeCode";
	private static String objAttNames1 = "resCode,resName";
	private static String jsonAttNames1 = "id,text";
	private static String childAttName = "children";
	
	private static String paramObjAttNames="resId,resGrpId,resCode,resName";
	private static String paramJsonAttNames="id,resGrpId,resCode,resName";

	private static int treeLevelLength = 3;
	
	public String lastMassage = "";
	
	@Autowired
	private SysResGroupDao resGroupDao;
	@Autowired
	private ResGroupService resGroupService;

	@Autowired
	private SysResItemDao resItemDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysResItem obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysResItem obj, int type) {
		if( type == 1 ){
			return JsonUtils.getJsonObjInfo(obj, objAttNames1, jsonAttNames1);			
		}
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	//根据数据字典名称获取数据字典
	public SysResGroup getSysResGroup(String grpCode){
		String hql="from SysResGroup where grpCode=?";
		return resGroupDao.findUnique(hql,grpCode);
	}
	
	//根据数据字典获取字段值
		public List<SysResItem> getResCode(String resGrpId){
			String hql="from SysResItem where resGrpId=? order by resNumber";
			return resItemDao.find(hql,resGrpId);
		}
		
		//根据数据字典获取字段值
				public List<SysResItem> getResCodeExceptZero(String resGrpId){
					String hql="from SysResItem where resGrpId=? and resCode <> 0 order by resNumber";
					return resItemDao.find(hql,resGrpId);
				}
		
		//根据数据字典获取字段值
				public List<SysResItem> getByGrpCodeAndresCode(String resGrpId,String resCode){
					String hql="from SysResItem where resGrpId=?";
					return resItemDao.find(hql,resGrpId);
				}
		//根据字典名称获取数据字典返回list对象
			public List<SysResItem> getSysResItem(String grpCode, int type){
				SysResGroup group = resGroupService.getResGroupByCode(grpCode);
				List<SysResItem> listItem = getGroupResItem(group.getResGrpId());
				return listItem;
			}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysResItem> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
//	@Transactional(readOnly = true)
//	public String getJsonParamRes(final List<ParamResItem> list) {
//		return JsonUtils.getJsonListInfo(list, paramObjAttNames, paramJsonAttNames);
//	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysResItem> list, int type) {
		if( type == 1 ){
			return JsonUtils.getJsonListInfo(list, objAttNames1, jsonAttNames1);
		}
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<SysResItem> list) {
		return JsonUtils.getJsonTreeInfo(list, objAttNames, jsonAttNames, childAttName);
	}
	
	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<SysResItem> list, int type) {
		if( type == 1 ){
			return JsonUtils.getJsonTreeInfo(list, objAttNames1, jsonAttNames1, childAttName);
		}
		return JsonUtils.getJsonTreeInfo(list, objAttNames, jsonAttNames, childAttName);
	}
	
	@Transactional(readOnly = true)
	public SysResItem getResItem(String id) {
		return resItemDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public boolean isItemCodeUnique(String grpId, String resId, String resCode) {
		String hql = "select resCode from SysResItem where sysResGroup.resGrpId=? and resId<>? and resCode=?";
		List<String> list = resItemDao.find(hql, grpId, resId, resCode);
		if( list==null || list.size()==0 ){
			return true;
		}
		return false;
	}

	public void addResItem(SysResItem entity) {
		logger.debug("addResItem...");
		entity.setResId(Identities.uuid());
		String grpId = entity.getSysResGroup().getResGrpId();
		if( entity.getSysResGroup().getGrpType()==Global.GROUP_TYPE_LIST ){
			Integer number = resItemDao.getMaxNumber(grpId)+1;
			entity.setResNumber(number);		
		}
		else if( entity.getSysResGroup().getGrpType()==Global.GROUP_TYPE_TREE ){
			String treeCode = resItemDao.getNewChildCode(grpId, entity.getParentId(), treeLevelLength);
			entity.setTreeCode(treeCode);
		}

		resItemDao.save(entity);
	}
	
	public void saveResItem(SysResItem entity) {
		logger.debug("saveResItem...");
		String grpId = entity.getSysResGroup().getResGrpId();
		if( entity.getSysResGroup().getGrpType()==Global.GROUP_TYPE_LIST ){
			Integer number = resItemDao.getItemNumber(entity.getResId());
			if( number == null ){
				number = resItemDao.getMaxNumber(grpId)+1;
			}
			entity.setResNumber(number);		
		}
		else if( entity.getSysResGroup().getGrpType()==Global.GROUP_TYPE_TREE ){
			String treeCode = resItemDao.getTreeCodeById(entity.getResId());
			if( treeCode == null ){
				treeCode = resItemDao.getNewChildCode(grpId, entity.getParentId(), treeLevelLength);
			}
			entity.setTreeCode(treeCode);
		}

		resItemDao.save(entity);
	}

	public void deleteResItem(String id) {
		SysResItem item = resItemDao.get(id);
		deleteResItem(item);
	}

	public void deleteResItem(SysResItem item) {
		logger.debug("deleteResItem(name:{})...", item.getResName());
		String grpId = item.getSysResGroup().getResGrpId();
		if( item.getSysResGroup().getGrpType()==Global.GROUP_TYPE_LIST ){
			String hql = "update SysResItem set resNumber=resNumber-1 where sysResGroup.resGrpId=? and resNumber>?";
			resItemDao.batchExecute(hql, grpId, item.getResNumber());
			resItemDao.flush();
			resItemDao.delete(item);
			resItemDao.flush();
		}
		else if( item.getSysResGroup().getGrpType()==Global.GROUP_TYPE_TREE ){
			List<SysResItem> list = resItemDao.findNextTrees(grpId, item.getTreeCode(), treeLevelLength);
			resItemDao.deleteTree(grpId, item.getTreeCode());	
			resItemDao.flush();
			int levelLength = item.getTreeCode().length();
			for (SysResItem obj : list)
			{
				String adjustCode = TreeUtils.getLevelLastTreeCode(obj.getTreeCode(), levelLength);
				obj.setTreeCode(adjustCode);
				resItemDao.save(obj);
				resItemDao.flush();
			}
		}
	}
	
	public void moveItem(SysResItem item, int movePos) {
		logger.debug("moveDownItem(name:{})...", item.getResName());
		String hql = "from SysResItem where sysResGroup.resGrpId=? and resNumber=?";
		SysResItem itemFind = resItemDao.findUnique(hql, item.getSysResGroup().getResGrpId(), item.getResNumber()+movePos);
		if( itemFind != null ){
			int number1 = itemFind.getResNumber();
			int number2 = item.getResNumber();
			item.setResNumber(number1);		
			itemFind.setResNumber(number2);
			resItemDao.save(itemFind);
			resItemDao.flush();
			resItemDao.save(item);
			resItemDao.flush();
		}
	}
	
	public void moveUpItem(SysResItem item) {
		logger.debug("moveUpItem(name:{})...", item.getResName());
		moveItem(item, -1);
	}
	
	public void moveDownItem(SysResItem item) {
		logger.debug("moveDownItem(name:{})...", item.getResName());
		moveItem(item, 1);
	}
	
	@Transactional(readOnly = true)
	public List<SysResItem> findMoveTrees(SysResItem item) {
		String grpId = item.getSysResGroup().getResGrpId();
		return resItemDao.findOtherTrees(grpId, item.getTreeCode());
	}

	public boolean moveResItem(String moveId, String targetId, int moveType){
		SysResItem moveResItem = resItemDao.get(moveId);
		SysResItem targetResItem = resItemDao.get(targetId);
		if (targetResItem == null)
		{
			this.lastMassage = "目标资源项已经不存在!";
			return false;
		}

		String grpId = moveResItem.getSysResGroup().getResGrpId();
		String moveItemCode = moveResItem.getTreeCode();

		// 获取所有要移动的资源项
		List<SysResItem> listMove = resItemDao.findSubTree(grpId, moveItemCode);
		// 获取所有假删除影响的资源项
		List<SysResItem> listChange1 = resItemDao.findNextTrees(grpId, moveItemCode, treeLevelLength);

		// 把要移动的资源项的树编码清空(假删除)
		String hql = "update SysResItem set treeCode='*' where sysResGroup.resGrpId=? and treeCode like '" + moveItemCode+"%'";
		resItemDao.batchExecute(hql, grpId);
		resItemDao.flush();

		// 更改假删除后影响的资源项
		for (SysResItem obj : listChange1)
		{
			String adjustCode = TreeUtils.getLevelLastTreeCode(obj.getTreeCode(), moveItemCode.length());
			obj.setTreeCode(adjustCode);
			resItemDao.save(obj);
			resItemDao.flush();
		}

		int levelLength = 0;
		String moveCode = "";
		String parentId = "";
		List<SysResItem> listChange2 = null;
		switch (moveType)
		{
			case TreeUtils.MOVE_TYPE_FIRST_SUB: // 移动到目标资源项的第一子资源项
				parentId = targetResItem.getResId();
				levelLength = targetResItem.getTreeCode().length() + treeLevelLength;
				listChange2 = resItemDao.findSubTrees(grpId, targetResItem.getTreeCode());
				moveCode = targetResItem.getTreeCode() + TreeUtils.GetLevelKey(TreeUtils.TREE_KEY_ORIGINAL_NUMBER + 1);
				break;
			case TreeUtils.MOVE_TYPE_LAST_SUB: // 移动到目标资源项的最后子资源项
				parentId = targetResItem.getResId();
				levelLength = targetResItem.getTreeCode().length() + treeLevelLength;
				moveCode = resItemDao.getNewChildCode(grpId, treeLevelLength);
				break;
			case TreeUtils.MOVE_TYPE_UP_OBJ: // 移动到目标资源项的上方
				parentId = targetResItem.getParentId();
				levelLength = targetResItem.getTreeCode().length();
				listChange2 = resItemDao.findMeNextTrees(grpId, targetResItem.getTreeCode(), treeLevelLength);
				moveCode = targetResItem.getTreeCode();
				break;
			case TreeUtils.MOVE_TYPE_DOWN_OBJ: // 移动到目标资源项的下方
				parentId = targetResItem.getParentId();
				levelLength = targetResItem.getTreeCode().length();
				listChange2 = resItemDao.findNextTrees(grpId, targetResItem.getTreeCode(), treeLevelLength);
				moveCode = TreeUtils.getNextTreeCode(targetResItem.getTreeCode());
				break;
		}

		// 受影响的资源项向下移
		if (listChange2 != null && listChange2.size() > 0)
		{
			for (SysResItem obj : listChange2)
			{
				String adjustCode = TreeUtils.getLevelNextTreeCode(obj.getTreeCode(), levelLength);
				obj.setTreeCode(adjustCode);
				resItemDao.save(obj);
				resItemDao.flush();
			}
		}

		// 最后插入要移动的资源项
		listMove.get(0).setParentId(parentId);
		resItemDao.save(listMove.get(0));
		resItemDao.flush();
		for (SysResItem obj : listMove)
		{
			String adjustCode = TreeUtils.getMoveTreeCode(obj.getTreeCode(), moveCode, moveItemCode.length());
			obj.setTreeCode(adjustCode);
			hql = "update SysResItem set treeCode=? where resId=? ";
			resItemDao.batchExecute(hql, adjustCode, obj.getResId());
			resItemDao.flush();
		}

		return true;
	}

	@Transactional(readOnly = true)
	public List<SysResItem> getGroupResItem(String grpId) {
		SysResGroup group = resGroupDao.get(grpId);
		if( group == null ){
			return null;
		}
		List<SysResItem> list = null;
		if( group.getGrpType() == Global.GROUP_TYPE_LIST ){
			list = resItemDao.getGroupAll(grpId, "resNumber", true);
		}
		else if( group.getGrpType() == Global.GROUP_TYPE_TREE ){
			list = resItemDao.getGroupAll(grpId, "treeCode", true);
		}
		return list;
	}
	
	@Transactional(readOnly = true)
	public String findResContent(String groupCode, String resCode)
	{
		String hql = "select resName from SysResItem where sysResGroup.grpCode=? and resCode=? order by resNumber,treeCode";
		List<String> list = resItemDao.find(hql, groupCode, resCode);
		if (list==null || list.size()==0 || list.get(0)==null)
		{
			return "";
		}
		return String.valueOf(list.get(0));
	}
	
	
	@Transactional(readOnly = true)
	public SysResItem findResDesc(String groupCode, String resCode)
	{
		String hql = " from SysResItem where sysResGroup.grpCode=? and resCode=? order by resNumber,treeCode";
//		List<String> list = resItemDao.find(hql, groupCode, resCode);
//		if (list==null || list.size()==0 || list.get(0)==null)
//		{
//			return "";
//		}
		return resItemDao.findUnique(hql, groupCode,resCode);
	}
	
	
	
	@Transactional(readOnly = true)
	public String findResCode(String groupCode, String resValue)
	{
		String hql = "select resCode from SysResItem where sysResGroup.grpCode=? and resName=? order by resNumber,treeCode";
		List<String> list = resItemDao.find(hql, groupCode, resValue);
		if (list==null || list.size()==0 || list.get(0)==null)
		{
			return "";
		}
		return String.valueOf(list.get(0));
	}
	
	@Transactional(readOnly = true)
	public List<SampleResItem> findSampleResItems(String grpId, String filter)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select new com.sino.base.common.SampleResItem(resName, resCode, treeCode) from SysResItem where state=1 and sysResGroup.grpCode='");
		sb.append(grpId).append("' ");
		if (filter != null && filter.length() > 0)
		{
			sb.append(" and (").append(filter).append(")");
		}
		sb.append(" order by resNumber,treeCode");

		String hql = sb.toString();
		List<SampleResItem> list = resItemDao.find(hql);
		return list; 
	}
	
	@Transactional(readOnly = true)
	public String getResJsonList(String grpId, String filter, String jsonCols)
	{
		String cols[] = jsonCols.split(",");
		Validate.isTrue(cols.length>=2);
		
		List<SampleResItem> list = findSampleResItems(grpId, filter);
		
		return JsonUtils.getJsonListInfo(list, "value,name", jsonCols);
	}
	
	@Transactional(readOnly = true)
	public String getResJsonTree(String grpId, String filter, String jsonCols, String childName)
	{
		String cols[] = jsonCols.split(",");
		Validate.isTrue(cols.length>=2);
		
		List<SampleResItem> list = findSampleResItems(grpId, filter);
		
		return JsonUtils.getJsonTreeInfo(list, "value,name", jsonCols, childName);
	}
	
	@Transactional(readOnly = true)
	public String getObjJsonList(String objName, String filter, String objCols, String jsonCols)
	{
		String cols[] = jsonCols.split(",");
		Validate.isTrue(cols.length>0);
		
		String hql = "from "+objName;
		if( filter!=null && filter.length()>0 ){
			hql += " where "+filter;
		}
		
		List<Object> list = resItemDao.find(hql);
		
		return JsonUtils.getJsonListInfo(list, objCols, jsonCols);
	}
	
	@Transactional(readOnly = true)
	public String getObjJsonTree(String objName, String filter, String objCols, String jsonCols, String childName)
	{
		String cols[] = jsonCols.split(",");
		Validate.isTrue(cols.length>0);
		
		String hql = "from "+objName;
		if( filter!=null && filter.length()>0 ){
			hql += " where "+filter;
		}
		
		List<Object> list = resItemDao.find(hql);
		
		return JsonUtils.getJsonTreeInfo(list, objCols, jsonCols, childName);
	}

	@Transactional(readOnly = true)
	public List<SampleResItem> findSampleResItems(String objName, String textAtt, String valueAtt, String filter, String order)
	{
		return findSampleResItems(objName, textAtt, valueAtt, null, filter, order);
	}

	@Transactional(readOnly = true)
	public List<SampleResItem> findSampleResItems(String objName, String textAtt, String valueAtt, String treeCodeAtt, String filter, String order)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select new com.sino.base.common.SampleResItem(").append(textAtt).append(",").append(valueAtt);
		if ( !StringUtils.isBlank(treeCodeAtt) ){
			sb.append(",").append(treeCodeAtt);
		}
		sb.append(") from ").append(objName);
		if ( !StringUtils.isBlank(filter) ){
			sb.append(" where ").append(filter);
		}
		if ( !StringUtils.isBlank(order)){
			sb.append(" order by ").append(order);
		}
		String hql = sb.toString();		
		List<SampleResItem> list = resItemDao.find(hql);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysResItem> searchResItem(final List<PropertyFilter> filters) {
		return resItemDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysResItem> searchResItem(final PageRequest page, final List<PropertyFilter> filters) {
		return resItemDao.findPage(page, filters);
	}

}
