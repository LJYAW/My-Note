package com.sino.base.system.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.common.Global;
import com.sino.base.common.tree.TreeUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.dao.SysPowerGroupDao;
import com.sino.base.system.entity.SysMenu;
import com.sino.base.system.entity.SysPower;
import com.sino.base.system.entity.SysPowerGroup;



/**
 * 系统权限组管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class PowerGroupService {
	private static Logger logger = LoggerFactory.getLogger(PowerGroupService.class);

	private static String objAttNames = "powGrpId,grpName,description,state,treeCode,parentId,level,isLeaf,expanded";
	private static String jsonAttNames = "id,grpName,description,state,treeCode,parentId,level,isLeaf,expanded";
	private static String childAttName = "children";

	private static int treeLevelLength = 3;
	
	public String lastMassage = "";

	@Autowired
	private SysPowerGroupDao powGrpDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysPowerGroup obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysPowerGroup> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<SysPowerGroup> list) {
		return JsonUtils.getJsonTreeInfo(list, objAttNames, jsonAttNames, childAttName);
	}

	/**
	 * getAllPowerGroup
	 * @Description: 根据树编码，获得所有权限集合，已排序
	 * @return: List<SysPowerGroup>
	 */
	@Transactional(readOnly = true)
	public List<SysPowerGroup> getAllPowerGroup() {
		return powGrpDao.getAll("treeCode", true);
	}
	
	@Transactional(readOnly = true)
	public List<SysPowerGroup> searchPowerGroup(final List<PropertyFilter> filters) {
		return powGrpDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysPowerGroup> searchPowerGroup(final PageRequest page, final List<PropertyFilter> filters) {
		return powGrpDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysPowerGroup getPowerGroup(String id) {
		return powGrpDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public List<SysPowerGroup> findSubMenu(String treeCode)
	{
		String hql = "from SysPowerGroup where treeCode like ? order by treeCode";
		List<SysPowerGroup> list = powGrpDao.find(hql, treeCode+"%");
		return list;
	}
	
	public void addPowerGroup(SysPowerGroup powGrp) {
		logger.debug("savePowGrp...");
		powGrp.setPowGrpId(Identities.uuid());
		String treeCode = powGrpDao.getNewChildCode(powGrp.getParentId(), treeLevelLength);
		powGrp.setTreeCode(treeCode);
		powGrp.setLevel(treeCode.length()/3);
		powGrp.setLeaf("true");
		powGrp.setExpanded("true");
		
		powGrpDao.save(powGrp);
		powGrpDao.flush();
		
		if(treeCode.length()>3){
			SysPowerGroup pGrp=powGrpDao.getObjByCode(treeCode.substring(0,treeCode.length()-3));
			if(pGrp!=null){
				pGrp.setLeaf("false");
				powGrpDao.save(pGrp);
				powGrpDao.flush();
			}
		}
		
		
	}

	public void savePowerGroup(SysPowerGroup powGrp) {
		logger.debug("savePowGrp...");
		
		
		SysPowerGroup editPowGrp=powGrpDao.get(powGrp.getPowGrpId());
		editPowGrp.setDescription(powGrp.getDescription());
		editPowGrp.setGrpName(powGrp.getGrpName());
		editPowGrp.setState(powGrp.getState());
		String treeCode = editPowGrp.getTreeCode();//powGrpDao.getTreeCodeById(powGrp.getPowGrpId());
		
		if( treeCode == null ){
			treeCode = powGrpDao.getNewChildCode(powGrp.getParentId(), treeLevelLength);
			powGrp.setTreeCode(treeCode);
		}
	
		powGrpDao.save(editPowGrp);
		
	}

	public void deletePowerGroup(String id) {
		logger.debug("deletePowerGroup(id:{})...", id);
		SysPowerGroup powGrp = powGrpDao.get(id);
		List<SysPowerGroup> list = powGrpDao.findNextTrees(powGrp.getTreeCode(), treeLevelLength);

		powGrpDao.deleteTree(powGrp.getTreeCode());
		
		if(powGrp.getTreeCode().length()>3){
			SysPowerGroup rgp=powGrpDao.getObjByCode(powGrp.getTreeCode().substring(0,powGrp.getTreeCode().length()-3));
			
			List<SysPowerGroup> grplist=findSubMenu(rgp.getTreeCode());
			
			if(grplist.size()==1){
				rgp.setLeaf("true");
				powGrpDao.save(rgp);
				powGrpDao.flush();
			}
		}
		
		
		int levelLength = powGrp.getTreeCode().length();
		for (SysPowerGroup obj : list)
		{
			String adjustCode = TreeUtils.getLevelLastTreeCode(obj.getTreeCode(), levelLength);
			obj.setTreeCode(adjustCode);
			powGrpDao.save(obj);
		}
	}
	
	@Transactional(readOnly = true)
	public List<SysPowerGroup> findMoveTrees(SysPowerGroup powGrp) {
		return powGrpDao.findOtherTrees(powGrp.getTreeCode());
	}

	public boolean movePowerGroup(String moveId, String targetId, int moveType){
		if( powGrpDao.moveNode(moveId, targetId, moveType, treeLevelLength) )
		{
			this.lastMassage = "移动权限组或目标权限组已经不存在!";
			return false;
		}
		
		return true;
	}
	@Transactional(readOnly = true)
	public String getJsonMenuPowChk(final List<SysPowerGroup> listNode, Set<SysPower> setMapPower) {
		powGrpDao.initProxyObject(setMapPower);
		String lastTreeCode = "";
		String curTreeCode = "";
		int nChild = 0;
		int nodeNum = listNode.size();
		
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0; i < nodeNum; i++)
		{
			SysPowerGroup group = listNode.get(i);
			if( group.getState() == 0 ){
				continue;
			}
			powGrpDao.initProxyObject(group.getSysPowers());
			curTreeCode = group.getTreeCode();
			if( i>0 ){
				if( curTreeCode.length()>lastTreeCode.length()){
					sb.append(", \"").append(childAttName).append("\": [ ");
					nChild++;
				}
				else if( curTreeCode.length()<lastTreeCode.length() ){
					int nLevel = (lastTreeCode.length()-curTreeCode.length())/treeLevelLength;
					for( int k=0; k<nLevel; k++ ){
						sb.append("} ]");
						nChild--;
					}
					sb.append(" }, ");
				}
				else{
					sb.append(" }, ");
				}
			}
			sb.append("{ \"grpName\":\"").append(group.getGrpName()).append("\"");
			sb.append(", \"grpChk\":\"<input type='checkbox' id='")
			.append(group.getTreeCode()).append("' name='chkGrp' onclick='chkGroup(this)' >\"");
			sb.append(", \"grpPowCheck\":\"").append(getMenuGrpPowCheck(group.getSysPowers(), setMapPower)).append("\"");
			lastTreeCode = curTreeCode;			
		}
		while (nChild > 0) {
			sb.append(" } ]");
			nChild--;
		}
		if( nodeNum>0 ){
			sb.append(" }");			
		}	
		sb.append(" ]");
		
		return sb.toString();
	}
	
	@Transactional(readOnly = true)
	private String getMenuGrpPowCheck(final Set<SysPower> setGrpPower, Set<SysPower> setChkPower) {
		StringBuilder sb = new StringBuilder();
		Iterator<SysPower> it = setGrpPower.iterator();
		while (it.hasNext()){ 
			SysPower power = it.next();
			sb.append(getPowerCheck(power, setChkPower));
		}
		return sb.toString();
	}
	
	@Transactional(readOnly = true)
	private String getPowerCheck(final SysPower power, Set<SysPower> setChkPower) {
		StringBuilder sb = new StringBuilder();

		sb.append("<input type='checkbox' group='")
			.append(power.getSysPowerGroup().getTreeCode())
			.append("' name='chkPower' value='")
			.append(power.getPowId()).append("' ");
		if (setChkPower.contains(power)) {
			sb.append("checked ");
		}
		sb.append(">").append(power.getPowName()).append("　");

		return sb.toString();
	}

	
	@Transactional(readOnly = true)
	public String getJsonUserPowChk(final List<SysPowerGroup> listNode, Set<SysPower> setMapPower, Integer userType) {
		powGrpDao.initProxyObject(setMapPower);
		String lastTreeCode = "";
		String curTreeCode = "";
		int nChild = 0;
		int nodeNum = listNode.size();
		
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0; i < nodeNum; i++)
		{
			SysPowerGroup group = listNode.get(i);
			if( group.getState() == 0 ){
				continue;
			}
			powGrpDao.initProxyObject(group.getSysPowers());
			curTreeCode = group.getTreeCode();
			if( i>0 ){
				if( curTreeCode.length()>lastTreeCode.length()){
					sb.append(", \"").append(childAttName).append("\": [ ");
					nChild++;
				}
				else if( curTreeCode.length()<lastTreeCode.length() ){
					int nLevel = (lastTreeCode.length()-curTreeCode.length())/treeLevelLength;
					for( int k=0; k<nLevel; k++ ){
						sb.append("} ]");
						nChild--;
					}
					sb.append(" }, ");
				}
				else{
					sb.append(" }, ");
				}
			}
			sb.append("{ \"grpName\":\"").append(group.getGrpName()).append("\"");
			sb.append(", \"grpChk\":\"<input type='checkbox' id='")
			.append(group.getTreeCode()).append("' name='chkGrp' onclick='chkGroup(this)' >\"");
			sb.append(", \"grpPowCheck\":\"").append(getUserGrpPowCheck(group.getSysPowers(), setMapPower, userType)).append("\"");
			lastTreeCode = curTreeCode;			
		}
		while (nChild > 0) {
			sb.append(" } ]");
			nChild--;
		}
		if( nodeNum>0 ){
			sb.append(" }");			
		}	
		sb.append(" ]");
		
		return sb.toString();
	}
	
	@Transactional(readOnly = true)
	private String getUserGrpPowCheck(final Set<SysPower> setGrpPower, Set<SysPower> setChkPower, Integer userType) {
		StringBuilder sb = new StringBuilder();
		Iterator<SysPower> it = setGrpPower.iterator();
		while (it.hasNext()){ 
			SysPower power = it.next();
			if( (userType==Global.USER_TYPE_DEV && power.getIsDvPow())
					|| (userType!=Global.USER_TYPE_DEV && power.getIsBsPow()) ){
				sb.append(getPowerCheck(power, setChkPower));
			}
		}
		return sb.toString();
	}
	
	@Transactional(readOnly = true)
	public String getJsonRolePowChk(final List<SysPowerGroup> listNode, Set<SysPower> setMapPower) {
		powGrpDao.initProxyObject(setMapPower);
		String lastTreeCode = "";
		String curTreeCode = "";
		int nChild = 0;
		int nodeNum = listNode.size();
		
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0; i < nodeNum; i++)
		{
			SysPowerGroup group = listNode.get(i);
			if( group.getState() == 0 ){
				continue;
			}
			powGrpDao.initProxyObject(group.getSysPowers());
			curTreeCode = group.getTreeCode();
			if( i>0 ){
				if( curTreeCode.length()>lastTreeCode.length()){
					sb.append(", \"").append(childAttName).append("\": [ ");
					nChild++;
				}
				else if( curTreeCode.length()<lastTreeCode.length() ){
					int nLevel = (lastTreeCode.length()-curTreeCode.length())/treeLevelLength;
					for( int k=0; k<nLevel; k++ ){
						sb.append("} ]");
						nChild--;
					}
					sb.append(" }, ");
				}
				else{
					sb.append(" }, ");
				}
			}
			sb.append("{ \"grpName\":\"").append(group.getGrpName()).append("\"");
			sb.append(", \"grpChk\":\"<input type='checkbox' id='")
			.append(group.getTreeCode()).append("' name='chkGrp' onclick='chkGroup(this)' >\"");
			sb.append(", \"grpPowCheck\":\"").append(getRoleGrpPowCheck(group.getSysPowers(), setMapPower)).append("\"");
			lastTreeCode = curTreeCode;			
		}
		while (nChild > 0) {
			sb.append(" } ]");
			nChild--;
		}
		if( nodeNum>0 ){
			sb.append(" }");			
		}	
		sb.append(" ]");
		
		return sb.toString();
	}
	
	@Transactional(readOnly = true)
	private String getRoleGrpPowCheck(final Set<SysPower> setGrpPower, Set<SysPower> setChkPower) {
		StringBuilder sb = new StringBuilder();
		Iterator<SysPower> it = setGrpPower.iterator();
		while (it.hasNext()){ 
			SysPower power = it.next();
			if( power.getIsRoleSee() ){
				sb.append(getPowerCheck(power, setChkPower));
			}
		}
		return sb.toString();
	}
}
