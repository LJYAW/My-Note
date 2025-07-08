package com.sino.base.system.service;

import com.sino.base.common.Global;
import com.sino.base.common.tree.TreeUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.dao.SysMenuDao;
import com.sino.base.system.entity.SysMenu;
import com.sino.base.system.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




//Spring Bean的标识.
/**
 * 系统菜单管理类
 *
 * @author .
 * @date 2017年5月3日 下午4:01:00
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class MenuService {
	private static Logger logger = LoggerFactory.getLogger(MenuService.class);

	/**
	 * @fieldName: objAttNames
	 * @fieldType: String
	 * @Description: (实体类对应字段)	菜单Id，菜单名称，菜单路径URL，菜单描述，菜单状态，菜单状态树编码，菜单父ID
	 */
	private static String objAttNames = "menuId,menuName,menuUrl,menuDesc,state,treeCode,parentId,icon,level,isLeaf,expanded";
	/**
	 * @fieldName: jsonAttNames
	 * @fieldType: String
	 * @Description: （页面接收json串对应的字段）	菜单Id，菜单名称，菜单路径URL，菜单描述，菜单状态，菜单状态树编码，菜单父ID
	 */
	private static String jsonAttNames = "id,menuName,menuUrl,menuDesc,state,treeCode,parentId,icon,level,isLeaf,expanded";
	private static String childAttName = "children";

	/**
	 * @fieldName: treeLevelLength
	 * @fieldType: int
	 * @Description: 树等级，最高级，长度越长等级越低，最小为3
	 */
	private static int treeLevelLength = 3;
	
	public String lastMassage = "";

	@Autowired
	private SysMenuDao menuDao;

	/**
	 * getJsonObjStr
	 * @Description: 将系统菜单对象转换为json串
	 * @param obj
	 * @return: String
	 */
	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysMenu obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	/**
	 * getJsonListStr
	 * @Description: 将系统菜单集合转换为json串
	 * @param list
	 * @return: String
	 */
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysMenu> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	/**
	 * getJsonTreeStr
	 * @Description: 将系统菜单树转换为json串
	 * @param list
	 * @return: String
	 */
	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<SysMenu> list) {
		return JsonUtils.getJsonTreeInfo(list, objAttNames, jsonAttNames, childAttName);
	}
	
	/**
	 * getHtmlTreeStr
	 * @Description: 将菜单集合转化为'菜单树'，更换显示方式选项
	 * @param list	菜单树集合
	 * @return: String	返回html代码块
	 */
	@Transactional(readOnly = true)
	public String getHtmlTreeStr(final List<SysMenu> list) {
		return TreeUtils.getHtmlTreeStr(list, "menuName", "menuUrl", "icon","menuId" );
	}

	/**
	 * getAllMenu
	 * @Description: 根据treeCode获得该treeCode的所有菜单集合
	 * @return: List<SysMenu>	菜单树集合
	 */
	@Transactional(readOnly = true)
	public List<SysMenu> getAllMenu() {
		return menuDao.getAll("treeCode", true);
	}
	
	/**
	 * findUserAllMenu
	 * @Description: 根据用户ID获取该用户所有的菜单
	 * @param userId		用户ID
	 * @return: List<SysMenu>
	 */
	@Transactional(readOnly = true)
	public List<SysMenu> findUserAllMenu(String userId)
	{
		String hql = "from SysMenu where state=1 and menuId in ( select id.menuId from ViewUserMenu where id.userId=? ) order by treeCode";
		List<SysMenu> list = menuDao.find(hql, userId);
		menuDao.keepTreeList(list, 1, treeLevelLength);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysMenu> findSubMenu(String treeCode)
	{
		String hql = "from SysMenu where treeCode like ? order by treeCode";
		List<SysMenu> list = menuDao.find(hql, treeCode+"%");
		return list;
	}

	
	/**
	 * findUserSubMenu
	 * @Description: 根据用户权限查询菜单树编码
	 * @param user 登录用户
	 * @param treeCode 菜单树编码
	 * @return: List<SysMenu>
	 */
	@Transactional(readOnly = true)
	public List<SysMenu> findUserSubMenu(SysUser user, String treeCode)
	{
		List<SysMenu> list = null;
		if( user.getUserType() == Global.USER_TYPE_DEV ){
			list = findSubDevMenu(treeCode);
		}
		else{
			list = findUserSubMenu(user.getUserId(), treeCode);	
		}
		return list;
	}

	@Transactional(readOnly = true)
	public List<SysMenu> findUserSubMenu(String userId, String treeCode)
	{
		String hql = "from SysMenu where state=1 and treeCode like ?  and menuId in ( select id.menuId from ViewUserMenu where id.userId=? ) order by treeCode";
		List<SysMenu> list = menuDao.find(hql, treeCode+"%", userId);
		menuDao.keepTreeList(list, 1, treeLevelLength);
		return list;
	}

	
	@Transactional(readOnly = true)
	public List<SysMenu> findUserMenu(String userId, String treeCode)
	{
		String hql = "from SysMenu where state=1 and treeCode like ? and menuId in ( select id.menuId from ViewUserMenu where id.userId=? ) order by treeCode";
		List<SysMenu> list = menuDao.find(hql, treeCode+"%", userId);
		menuDao.keepTreeList(list, 1, treeLevelLength);
		return list;
	}
	
	/**
	 * getTopMenu
	 * @Description: 根据所有菜单等级，获取顶部显示菜单列表，长度为3的显示在顶级位置
	 * @param list	菜单集合
	 * @return: List<SysMenu>
	 */
	@Transactional(readOnly = true)
	public List<SysMenu> getTopMenu(List<SysMenu> list)
	{
		if( list == null ){
			return list;
		}
		List<SysMenu> listTop = new ArrayList<SysMenu>();
		for(SysMenu menu:list){
			if( menu.getTreeCode().length() == treeLevelLength ){
				listTop.add(menu);
			}
		}
		return listTop;
	}
	
	/**
	 * findUserTopMenu
	 * @Description: 根据用户ID获取其顶部菜单集合
	 * @param userId
	 * @return: List<SysMenu>
	 */
	@Transactional(readOnly = true)
	public List<SysMenu> findUserTopMenu(String userId)
	{
		List<SysMenu> list = findUserAllMenu(userId);
		return getTopMenu(list);
	}
	
	/**
	 * findUserAllMenu
	 * @Description: 动态根据登录用户ID查询其所有菜单
	 * @param user
	 * @return: List<SysMenu>
	 */
	@Transactional(readOnly = true)
	public List<SysMenu> findUserAllMenu(SysUser user)
	{
		List<SysMenu> list = null;
		if( user.getUserType() == Global.USER_TYPE_DEV ){
			list = findAllDevMenu();
		}
		else{
			list = findUserAllMenu(user.getUserId());	
		}
		return list;
	}
	
	/**
	 * findAllDevMenu
	 * @Description: 获取所有devadmin用户的菜单列表
	 * @return: List<SysMenu>
	 */
	@Transactional(readOnly = true)
	public List<SysMenu> findAllDevMenu()
	{
		String hql = "select distinct u from SysMenu u left join fetch u.sysPowers k where k.isDvPow=true order by treeCode";
		List<SysMenu> list = menuDao.find(hql);
		menuDao.keepTreeList(list, 1, treeLevelLength);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysMenu> findSubDevMenu(String treeCode)
	{
		String hql = "select distinct u from SysMenu u left join fetch u.sysPowers k where k.isDvPow=true and u.treeCode like ? order by treeCode";
		List<SysMenu> list = menuDao.find(hql, treeCode+"%");
		menuDao.keepTreeList(list, 1, treeLevelLength);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysMenu> searchMenu(final List<PropertyFilter> filters) {
		return menuDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysMenu> searchMenu(final PageRequest page, final List<PropertyFilter> filters) {
		return menuDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysMenu getMenu(String id) {
		return menuDao.get(id);
	}
	
	public void addMenu(SysMenu menu) {
		logger.debug("saveMenu...");
		menu.setMenuId(Identities.uuid());
		String treeCode = menuDao.getNewChildCode(menu.getParentId(), treeLevelLength);
		menu.setTreeCode(treeCode);
		menu.setLevel(treeCode.length()/3);
		
		menu.setLeaf("true");
		menu.setExpanded("true");

		menuDao.save(menu);
		menuDao.flush();
		
		
		if(treeCode.length()>3){
			SysMenu pmenuMenu=menuDao.getObjByCode(treeCode.substring(0,treeCode.length()-3));
			if(pmenuMenu!=null){
				pmenuMenu.setLeaf("false");
				menuDao.save(pmenuMenu);
				menuDao.flush();
			}
		}
		
	}

	public void saveMenu(SysMenu menu) {
		logger.debug("saveMenu...");
		menuDao.save(menu);
	}

	public void deleteMenu(String id) {
		logger.debug("deleteMenu(id:{})...", id);
		SysMenu menu = menuDao.get(id);
		List<SysMenu> list = menuDao.findNextTrees(menu.getTreeCode(), treeLevelLength);

		menuDao.deleteTree(menu.getTreeCode());
		
		
		if(menu.getTreeCode().length()>3){
			SysMenu pmenuMenu=menuDao.getObjByCode(menu.getTreeCode().substring(0,menu.getTreeCode().length()-3));
			
			List<SysMenu> menulist=findSubMenu(pmenuMenu.getTreeCode());
			
			if(menulist.size()==1){
				pmenuMenu.setLeaf("true");
				menuDao.save(pmenuMenu);
				menuDao.flush();
			}
		}
		
		
		int levelLength = menu.getTreeCode().length();
		for (SysMenu obj : list)
		{
			String adjustCode = TreeUtils.getLevelLastTreeCode(obj.getTreeCode(), levelLength);
			obj.setTreeCode(adjustCode);
			menuDao.save(obj);
		}
	}
	
	@Transactional(readOnly = true)
	public List<SysMenu> findMoveTrees(SysMenu menu) {
		return menuDao.findOtherTrees(menu.getTreeCode());
	}

	public boolean moveMenu(String moveId, String targetId, int moveType){
		if ( !menuDao.moveNode(moveId, targetId, moveType, treeLevelLength) )
		{
			this.lastMassage = "移动菜单或目标菜单已经不存在!";
			return false;
		}

		return true;
	}
	
	
	@Transactional(readOnly = true)
	public String getMenuPageList(final Page<SysMenu> page) {
		return JsonUtils.getJQJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public Page<SysMenu> findAjaxMenus(PageRequest pageReq){
		String hql=" from SysMenu ";
		Map params=new HashMap();
		Page pageinfo=menuDao.findAjaxPage(pageReq,hql.toString(), params);
		return pageinfo;
	}
}
