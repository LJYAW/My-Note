package com.sino.base.system.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.common.tree.TreeUtils;
import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JdbcConnection;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.dao.OrgOrganDao;
import com.sino.base.system.dao.SysUserDao;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysPowerGroup;
import com.sino.base.system.entity.SysUser;



/**
 * 系统机构管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class OrganService {
	private static Logger logger = LoggerFactory.getLogger(OrganService.class);

	private static String objAttNames = "orgId,orgName,orgClass,countryCode,orgShortName,orgCode,orgType@organType,description,state,treeCode,parentId,cityCode@AdvCity@cityCode@cityName,address,zipCode,phone,fax,hotLine,level,isLeaf,expanded";
	private static String jsonAttNames = "id,orgName,orgClass,countryCode,orgShortName,orgCode,orgType,description,state,treeCode,parentId,city,address,zipCode,phone,fax,hotLine,level,isLeaf,expanded";
	private static String objMinAttNames = "orgId,orgName,orgShortName";
	private static String jsonMinAttNames = "id,orgName,orgShortName";
	private static String childAttName = "children";
	
	private static String excelAttNames = "parentOrgName,orgName,orgCode,orgType@organType,phone,address,zipCode,description";

	private static int treeLevelLength = 3;
	
	private static int orgStateUp = 1;
	
	private String lastMassage = "";

	@Autowired
	private OrgOrganDao organDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	

	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}

	@Transactional(readOnly = true)
	public String getJsonObjStr(final OrgOrganization obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<OrgOrganization> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<OrgOrganization> list) {
		return JsonUtils.getJsonTreeInfo(list, objAttNames, jsonAttNames, childAttName);
	}

	@Transactional(readOnly = true)
	public String getJsonMinTreeStr(final List<OrgOrganization> list) {
		return JsonUtils.getJsonTreeInfo(list, objMinAttNames, jsonMinAttNames, childAttName);
	}

	@Transactional(readOnly = true)
	public List<OrgOrganization> getAllOrgan() {
		return organDao.getAll("treeCode", true);
	}
	
	public List<OrgOrganization> getSubOrganName(){
		String hql="from OrgOrganization where orgType>=1 and orgType<=8";
		return organDao.find(hql);
				
	}
	public List<OrgOrganization> getSubOrganName(String treeCode){
		String hql="from OrgOrganization where treeCode like ? and orgType>=1 and orgType<=8";
		return organDao.find(hql,treeCode+"%");
				
	}
	//获取机构树 orgType！=9 树形
	public List<OrgOrganization> getMinDataNotNine(){
		String hql="from OrgOrganization where  orgType !=9 order by treeCode ";
		return organDao.find(hql);
				
	}
	
	
	public List<OrgOrganization> getOrganName(){
		String hql="from OrgOrganization where orgType=2 ";
		return organDao.find(hql);
				
	}
	//获取机构树 orgType！=9 非树形
	public List<OrgOrganization> getOrganNameNotNine(){
		String hql="from OrgOrganization where orgType !=9 ";
		return organDao.find(hql);
				
	}
	
	public List<OrgOrganization> getParentOrgan(){
		String hql="from OrgOrganization where parentId='' and orgType=1";
		return organDao.find(hql);
				
	}
	
	public List<OrgOrganization> getSubOrgan(String parentId){
		String hql="from OrgOrganization where parentId=? and orgType=9";
		return organDao.find(hql,parentId);
				
	}

	@Transactional(readOnly = true)
	public List<OrgOrganization> findOrganByCode(String treeCode) {
		return organDao.findSubTree(treeCode);
	}
	/**
	 * 获取指定父节点下的所有子节点
	 * @param treeCode
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<OrgOrganization> findsomeOrganByCode(String treeCode) {
		return organDao.findChildById(treeCode);
	}
	
	/**
	 * 查询机构
	 * @param filters
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<OrgOrganization> searchOrgan(final List<PropertyFilter> filters) {
		return organDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<OrgOrganization> searchOrgan(final PageRequest page, final List<PropertyFilter> filters) {
		return organDao.findPage(page, filters);
	}

	/**
	 * getOrgan
	 * @Description: 根据orgID获得用户所属机构
	 * @param id
	 * @return: OrgOrganization
	 */
	@Transactional(readOnly = true)
	public OrgOrganization getOrgan(String id) {
		if( id==null ){
			return null;
		}
		return organDao.get(id);
	}
	
	/**
	 * findSubOrganByCode
	 * @Description: TODO根据treeCode获取机构树
	 * @param treeCode
	 * @return
	 * @return: List<OrgOrganization>
	 */
	@Transactional(readOnly = true)
	public List<OrgOrganization> findSubOrganByCode(String treeCode) {
		String sql = "from OrgOrganization where treeCode like ?  order by treeCode";
		return organDao.find(sql,treeCode+"%");
	}
	/**
	 * 查询数据中心有关的集合列表
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<OrgOrganization> getDccOrgan() {
		String sql="from OrgOrganization where orgType < 3 order by treeCode";
		List<OrgOrganization> list = organDao.find(sql);
		return list;
	}
	/**
	 * 查询数据中心有关的机构信息
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<OrgOrganization> getOrgName(){
		String sql="from OrgOrganization where orgType < 3 ";
		List<OrgOrganization> list = organDao.find(sql);
		return list;
	}
	
	/**
	 * getParOrg
	 * @Description: TODO根据机构id获得其父机构，如果没有父机构直接返回一个null父机构
	 * @param id
	 * @return: OrgOrganization
	 */
	@Transactional(readOnly = true)
	public OrgOrganization getParOrg(String id) {
		String orgId="";
		OrgOrganization parOrg =new OrgOrganization();
		if( id==null ){
			return null;
		}
		OrgOrganization Organ =getOrgan(id);
		if(Organ!=null)
			orgId=Organ.getParentId();
		if(!StringUtil.isNullString(orgId))
			parOrg =getOrgan(orgId);
		return parOrg;
	}
	
	/**
	 * getOrganByName
	 * @Description: 根据机构名字获得机构信息
	 * @param name
	 * @return: OrgOrganization
	 */
	@Transactional(readOnly = true)
	public OrgOrganization getOrganByName(String name) {
		if( StringUtils.isBlank(name) ){
			return null;
		}
		return organDao.findUniqueBy("orgName", name);
	}
	
	@Transactional(readOnly = true)
	public List<OrgOrganization> getSubOrgList(String parentId) {
		if( StringUtils.isBlank(parentId) ){
			return null;
		}
		SysUser user=SystemUtils.getLoginUser();
		String loginName=user.getLoginName();
		if(loginName.equals("admin")||loginName.equals("devadmin")){
			return organDao.getAll();
		}
		return organDao.findBy("parentId", parentId);
	}
	
	@Transactional(readOnly = true)
	public List<String> getSubOrgIds(String parentId) {
		if( StringUtils.isBlank(parentId) ){
			return null;
		}
		SysUser user=SystemUtils.getLoginUser();
		List<String> listids =new ArrayList<String>();
		if(user!=null){
			List<OrgOrganization> orglist=new ArrayList<OrgOrganization>();
			String loginName=user.getLoginName();
			if(loginName.equals("admin")||loginName.equals("devadmin")){
				orglist=organDao.getAll();
				if(orglist!=null&&!orglist.isEmpty()){
					for(OrgOrganization org:orglist){
						listids.add(org.getOrgId());
					}
				}
			}else{
				orglist=organDao.findBy("parentId", parentId);
				listids.add(parentId);
				if(orglist!=null&&!orglist.isEmpty()){
					for(OrgOrganization org:orglist){
						listids.add(org.getOrgId());
					}
				}
			}
		}
		return listids;
	}
	
	@Transactional(readOnly = true)
	public List<String> getIpHostSubOrgIds(String parentId) {
		if( StringUtils.isBlank(parentId) ){
			return null;
		}
		List<String> listids =new ArrayList<String>();
		List<OrgOrganization> orglist=new ArrayList<OrgOrganization>();
			orglist=organDao.findBy("parentId", parentId);
			listids.add(parentId);
			if(orglist!=null&&!orglist.isEmpty()){
				for(OrgOrganization org:orglist){
					listids.add(org.getOrgId());
				}
		}
		return listids;
	}
	
	@Transactional(readOnly = true)
	public List<String> getHostSubOrgIds(String parentId) {
		if( StringUtils.isBlank(parentId) ){
			return null;
		}
		List<String> listids =new ArrayList<String>();
		List<OrgOrganization> orglist=new ArrayList<OrgOrganization>();
		OrgOrganization oo=organDao.get(parentId);
		if(oo!=null){
			String hql=" from OrgOrganization where treeCode like ? ";
			orglist =organDao.find(hql, oo.getTreeCode()+"%");
				if(orglist!=null&&!orglist.isEmpty()){
					for(OrgOrganization org:orglist){
						listids.add(org.getOrgId());
					}
			}
		}
		return listids;
	}
	
	@Transactional(readOnly = true)
	public OrgOrganization getOrganBy(String name, String parentId) {
		if( StringUtils.isBlank(name) ){
			return null;
		}
		if( StringUtils.isBlank(parentId) ){
			String hql = "from OrgOrganization where orgName=? and (parentId is null or parentId='')";
			return organDao.findUnique(hql, name);
		}
		else{
			String hql = "from OrgOrganization where orgName=? and parentId=?";
			return organDao.findUnique(hql, name, parentId);			
		}
	}
	
	@Transactional(readOnly = true)
	public OrgOrganization getOrganByOrgName(String name) {
		if( StringUtils.isBlank(name) ){
			return null;
		}
		
		String hql = "from OrgOrganization where orgName=? ";
		List<OrgOrganization> list=organDao.find(hql, name);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public List<OrgOrganization> checkOrgan(String organName,String treeCode){
		logger.debug("Enter checkOrgan...");
		String hql=" from OrgOrganization where orgName=? and orgType<>9 and treeCode like ? ";
		return organDao.find(hql, organName,treeCode+"%");
	}
	
	public List<OrgOrganization> checkOrgCode(String orgCode,String treeCode){
		logger.debug("Enter checkOrgan...");
		String hql=" from OrgOrganization where orgCode=? and orgType<>9 and treeCode like ? ";
		return organDao.find(hql, orgCode,treeCode+"%");
	}
	
	@Transactional(readOnly = true)
	public boolean loadOrganAtts(OrgOrganization entity, String attNames){
		return organDao.loadObjAtts(entity, attNames);
	}
	
	public void addOrgan(OrgOrganization organ) {
		logger.debug("saveOrgan...");
		organ.setOrgId(Identities.uuid());
		String treeCode = organDao.getNewChildCode(organ.getParentId(), treeLevelLength);
		organ.setTreeCode(treeCode);
		
		organ.setLevel(treeCode.length()/3);
		organ.setLeaf("true");
		organ.setExpanded("true");
		
		
		if(treeCode.length()>3){
			OrgOrganization ogan=organDao.getObjByCode(treeCode.substring(0,treeCode.length()-3));
			if(ogan!=null){
				ogan.setLeaf("false");
				organDao.save(ogan);
				organDao.flush();
			}
		}
		organDao.save(organ);
		organDao.flush();
	}
	
	public void addOperatorOrgan(OrgOrganization organ) {
		logger.debug("addOperatorOrgan...");
		organ.setOrgId(Identities.uuid());
		String treeCode = organDao.getNewChildCode(organ.getParentId(), treeLevelLength);
		organ.setTreeCode(treeCode);
		
		organ.setLevel(treeCode.length()/3);
		organ.setLeaf("true");
		organ.setExpanded("true");
		
		organDao.save(organ);
		organDao.flush();
	}
	
	
//	public void save(OrgOrganization organ){
//		OrgOrganization org = new OrgOrganization();
//		String treeCode = organDao.getNewChildCode(organ.getParentId(), treeLevelLength);
//		organ.setTreeCode(treeCode);
//		org.setAddress(organ.getAddress());
//		org.setCity(organ.getCity());
//		org.setCounty(organ.getCounty());
//		org.setDescription(organ.getDescription());
//		org.setFax(organ.getFax());
//		org.setHotLine(organ.getHotLine());
//		org.setOrgId(organ.getOrgId());
//		org.setOrgName(organ.getOrgName());
//		org.setPhone(organ.getPhone());
//		organDao.save(org);
//		organDao.flush();
//	}

	public void saveOrgan(OrgOrganization organ) {
		logger.debug("saveOrgan...");
		organDao.save(organ);
	}

	public void deleteOrgan(String id) {
		logger.debug("deleteOrgan(id:{})...", id);
		OrgOrganization organ = organDao.get(id);
		
		Integer orgClass = organ.getOrgClass();
		if(orgClass!=null && orgClass==1){//运营商
//			获取子机构id的集合
			List<String> ids = getIdsByOrgId(id);
//			删除自己和子机构的用户
			sysUserDao.deleteByOrgIds(ids);
			sysUserDao.flush();
		}
		
		List<OrgOrganization> list = organDao.findNextTrees(organ.getTreeCode(), treeLevelLength);

		organDao.deleteTree(organ.getTreeCode());
		
		if(organ.getTreeCode().length()>3){
			OrgOrganization organ2=organDao.getObjByCode(organ.getTreeCode().substring(0,organ.getTreeCode().length()-3));
			
			List<OrgOrganization> orglist=findSubOrganByCode(organ2.getTreeCode());
			
			if(orglist.size()==1){
				organ2.setLeaf("true");
				organDao.save(organ2);
				organDao.flush();
			}
		}
		
		
		int levelLength = organ.getTreeCode().length();
		for (OrgOrganization obj : list)
		{
			String adjustCode = TreeUtils.getLevelLastTreeCode(obj.getTreeCode(), levelLength);
			obj.setTreeCode(adjustCode);
			organDao.save(obj);
		}
		
	}
	
	
	//删除子机构
	public void deleteSubOrg(String id){
		OrgOrganization organ = organDao.get(id);
		organDao.deleteSubTree(organ.getTreeCode());
	}
	
	@Transactional(readOnly = true)
	public List<OrgOrganization> findMoveTrees(OrgOrganization organ) {
		return organDao.findOtherTrees(organ.getTreeCode());
	}

	public boolean moveOrgan(String moveId, String targetId, int moveType){
		this.lastMassage = "";
		if ( !organDao.moveNode(moveId, targetId, moveType, treeLevelLength) )
		{
			this.lastMassage = "移动机构或目标机构已经不存在!";
			return false;
		}

		return true;
	}
	
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		ExcelUtil<OrgOrganization> excelUtil = new ExcelUtil<OrgOrganization>(OrgOrganization.class);
		List<OrgOrganization> list = excelUtil.getObjListFrom(stream, excelAttNames);
		if( list==null || list.size()==0 ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			return false;
		}
		SysUser curUser = SystemUtils.getLoginUser();
		String operator = curUser.getLoginName();
		Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		int addNum=0;
		int updateNum=0;
		List<OrgOrganization> orglist=new ArrayList<OrgOrganization>();
		for(int i=0; i<list.size(); i++){
			OrgOrganization org = list.get(i);
			//org.setModifier(operator);
			//org.setModifyTime(now);
			
			OrgOrganization findParent = getOrganByOrgName(org.getParentOrgName());//查找父级机构
			
			OrgOrganization orgFind=getOrganByOrgName(org.getOrgName());
			
			if(org.getOrgType()==null){
				//this.lastMassage = "导入失败！第"+(i+1)+"行数据中 机构类型不能为空，或有空数据行，请检查！";
				this.lastMassage = "导入失败！第"+(i+1)+"行数据中的机构类型在字典中未定义,请先在organType字典中定义,再导入！";
				return false;
			}
//			if(org.getOrgCode()==null){
//				this.lastMassage = "导入失败！第"+i+"行数据中 机构编码不能为空，或有空数据行，请检查！";
//				return false;
//			}
			
			if( orgFind == null ){
				try {
					saveOrgan(findParent,org);
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addNum++;
			}
			else{
				try {
					updateOrgan(orgFind,org);
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateNum++;
			}
			
			
		}
		
		this.lastMassage = String.format("导入完毕！新增%d条记录，更新%d条记录。", addNum,updateNum);
		return true;
	}
	
	@Transactional(readOnly = true)
	public boolean exportOrganXml(){
		
		return true;
	}
	
	
	public List<String> getIdsByOrgId(String id){
		List<String> listids =new ArrayList<String>();
		OrgOrganization org=organDao.get(id);
		String hql=" from OrgOrganization where treeCode like ? ";
		List<OrgOrganization> orglist=organDao.find(hql, org.getTreeCode()+"%");
			if(orglist!=null&&!orglist.isEmpty()){
				for(OrgOrganization o:orglist){
					listids.add(o.getOrgId());
				}
		}
			return listids;
	}
	
	
	
	public void saveOrgan(OrgOrganization findParent,OrgOrganization entity) throws SQLException {
		Connection conn = null ;		// 数据库连接
		JdbcConnection jconn = JdbcConnection.getInstance() ;
		String treeCode ="";
		SysUser curUser = SystemUtils.getLoginUser();
		String operator = curUser.getLoginName();
		Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		if(findParent==null){
			 treeCode = organDao.getNewChildCode("", treeLevelLength);
		}else{
			treeCode = organDao.getNewChildCode(findParent.getOrgId(), treeLevelLength);
		}
		conn =jconn.getConnection();
			PreparedStatement pstmt_monServers; 
			pstmt_monServers = conn.prepareStatement( "insert into Org_Organization(orgId, creator, createTime, state, " +
				"orgName, orgType, parentId, treeCode, orgCode, phone, zipCode,address,description) " +
				"values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );
			
			String pid="";
			if(findParent!=null&&findParent.getOrgId()!=null){
				pid=findParent.getOrgId();
			}
			
			pstmt_monServers.setString(1, Identities.uuid());
			pstmt_monServers.setString( 2, operator);
			pstmt_monServers.setTimestamp(3, now);
			//pstmt_monServers.setString( 4, entity.getModifier());
		    //pstmt_monServers.setTimestamp(5, entity.getModifyTime());
			pstmt_monServers.setInt(4, orgStateUp);
			pstmt_monServers.setString( 5, entity.getOrgName() );
			pstmt_monServers.setInt( 6, entity.getOrgType());
			pstmt_monServers.setString( 7, pid );
			pstmt_monServers.setString( 8, treeCode);
			pstmt_monServers.setString( 9, entity.getOrgCode() );
			pstmt_monServers.setString( 10, entity.getPhone() );
			pstmt_monServers.setString( 11, entity.getZipCode());
			pstmt_monServers.setString( 12, entity.getAddress());
			pstmt_monServers.setString( 13, entity.getDescription() );
			pstmt_monServers.executeUpdate();
			conn.close();
	}
	
	
	public void updateOrgan(OrgOrganization orgFind,OrgOrganization entity) throws SQLException {
		Connection conn = null ;		// 数据库连接
		JdbcConnection jconn = JdbcConnection.getInstance() ;
		conn =jconn.getConnection();
			PreparedStatement pstmt_monServers; 
			pstmt_monServers = conn.prepareStatement( "update  Org_Organization set creator=?, createTime=?,modifier=?, modifyTime=?, state=?, " +
				"orgName=?, orgType=?, parentId=?, treeCode=?, orgCode=?, phone=?, zipCode=?,address=?,description=? where orgId=? ");
			SysUser curUser = SystemUtils.getLoginUser();
			String operator = curUser.getLoginName();
			Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			pstmt_monServers.setString( 1, orgFind.getCreator());
			pstmt_monServers.setTimestamp(2, orgFind.getCreateTime());
			pstmt_monServers.setString( 3, operator);
			pstmt_monServers.setTimestamp(4, now);
			pstmt_monServers.setInt(5, orgStateUp);
			pstmt_monServers.setString( 6, entity.getOrgName() );
			pstmt_monServers.setInt( 7, entity.getOrgType());
			pstmt_monServers.setString( 8, orgFind.getParentId() );
			pstmt_monServers.setString( 9, orgFind.getTreeCode());
			pstmt_monServers.setString( 10, entity.getOrgCode() );
			
			String phone="";
			if(!StringUtil.isNullString(entity.getPhone())&&entity.getPhone().contains(".")){
				phone=entity.getPhone().substring(0, entity.getPhone().indexOf("."));
			}else{
				phone=entity.getPhone();
			}
			pstmt_monServers.setString( 11,phone);
			
			String zipCode="";
			if(!StringUtil.isNullString(entity.getZipCode())&&entity.getZipCode().contains(".")){
				zipCode=entity.getZipCode().substring(0, entity.getZipCode().indexOf("."));
			}else{
				zipCode=entity.getZipCode();
			}
			pstmt_monServers.setString( 12, zipCode);
			
			pstmt_monServers.setString( 13, entity.getAddress());
			pstmt_monServers.setString( 14, entity.getDescription() );
			pstmt_monServers.setString(15, orgFind.getOrgId());
			pstmt_monServers.executeUpdate();
			conn.close();
	}
//	public List<String> getParentOrg(){
//	return organDao.getParentOrg();
//}
public List<OrgOrganization> getChildOrg(String orgId){
	String hql="from OrgOrganization where parentId=? and orgType < 3";
	List<OrgOrganization> find = organDao.find(hql,orgId);
	return find;
}

	/**
	 * getAllPro
	 * @Description: 根据orgType获得所有机构
	 * @return: List<Organization>
	 */
	public List<OrgOrganization> getAllOrgByOrgType(Integer orgType) {
		String hql = "from OrgOrganization where orgType = ?";
		List<OrgOrganization> orgPro = organDao.find(hql,orgType);
		return orgPro;
	}
	
	/**
	 * checkOrgName
	 * @Description: 校验名字是否唯一
	 * @param fieldValue
	 * @return: boolean
	 */
	public boolean checkOrgName(String orgName) {
		String hql = "from OrgOrganization where orgName = ?";
		List<OrgOrganization> orgList = this.organDao.find(hql,orgName);
		if(orgList.size()>0){
			return true;
		}
		return false;
	}
	
	//检查上级机构+机构名称唯一性
		public boolean checkOrganName(String parentId,String orgName) {
			String hql = "from OrgOrganization where parentId =? and orgName =?";
			List<OrgOrganization> List = this.organDao.find(hql,parentId,orgName);
			if(List.size()>0){
				return true;
			}
			return false;
		}

//		获取所有的运营商
		public List<OrgOrganization> getAllOperator() {
			List<OrgOrganization> list = organDao.getAllOperator();
			return list;
		}
		
//		获取当前用户所在的运营商机构
		public List<OrgOrganization> getCurrentUserAllOperator(String treeCode) {
			List<OrgOrganization> list = organDao.getCurrentUserAllOperator(treeCode);
			return list;
		}
		
		public List<OrgOrganization> getAllExceptDepart(Integer orgClass) {
			List<OrgOrganization> list = organDao.getAllExceptDepart(orgClass);
			return list;
		}
	
}
