package com.sino.base.common.tree;

import com.sino.base.common.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springside.modules.utils.Reflections;

import java.io.Serializable;
import java.util.List;


/**
 * TODO	树对象Dao类，进行菜单树相关操作
 *
 * @author .
 * @date 2017年5月2日 下午8:25:25
 */
public class TreeDao<T, ID extends Serializable> extends BaseDao<T, ID> {

	// 树对象专用，根据树编码获取该对象
	public T getObjByCode(final String treeCode){
		return findUniqueBy(TreeUtils.TREE_DEFAULT_COL_NAME, treeCode);
	}
	
	/**
	 * getTreeCode
	 * @Description: TODO	树对象专用，获取树对象树编码值
	 * @param obj	对应的实体类
	 * @return: String
	 */
	public String getTreeCode(final T obj){
		return Reflections.getFieldValue(obj, TreeUtils.TREE_DEFAULT_COL_NAME).toString();
	}
	
	/**
	 * findSubTree
	 * @Description: TODO	tree对象专用,根据树编码获取该节点及所有子节点
	 * @param treeCode	树编码
	 * @return: List<T>	节点集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> findSubTree(final String treeCode){
		return getSession().createCriteria(entityClass)
			    .add( Restrictions.like(TreeUtils.TREE_DEFAULT_COL_NAME, treeCode+"%") )
			    .addOrder( Order.asc(TreeUtils.TREE_DEFAULT_COL_NAME) )
			    .list();	
	}
	
	/**
	 * findSubTrees
	 * @Description: TODO	tree对象专用,根据树编码获取该节点下的所有树节点
	 * @param treeCode	树编码
	 * @return: List<T>	该节点下所有树节点
	 */
	@SuppressWarnings("unchecked")
	public List<T> findSubTrees(final String treeCode){
		return getSession().createCriteria(entityClass)
				.add( Restrictions.ne(TreeUtils.TREE_DEFAULT_COL_NAME, treeCode) )
			    .add( Restrictions.like(TreeUtils.TREE_DEFAULT_COL_NAME, treeCode+"%") )
			    .addOrder( Order.asc(TreeUtils.TREE_DEFAULT_COL_NAME) )
			    .list();	
	}
	
	// tree对象专用,获取指定父节点id的所有节点
	/**
	 * findChildById
	 * @Description: TODO	tree对象专用,获取指定父节点id的所有节点
	 * @param parentId	父节点ID
	 * @return: List<T>	所有子节点集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> findChildById(final ID parentId){
		if(isIdBlank(parentId)){
			return getSession().createCriteria(entityClass)
					.add( Restrictions.or(
							Restrictions.eq(TreeUtils.TREE_PARENTID_COL_NAME, ""),
							Restrictions.isNull(TreeUtils.TREE_PARENTID_COL_NAME) 
						))
				    .addOrder( Order.asc(TreeUtils.TREE_DEFAULT_COL_NAME) )
				    .list();	
		}
		return getSession().createCriteria(entityClass)
				.add( Restrictions.eq(TreeUtils.TREE_PARENTID_COL_NAME, parentId) )
			    .addOrder( Order.asc(TreeUtils.TREE_DEFAULT_COL_NAME) )
			    .list();	
	}
	
	/**
	 * findChildByCode
	 * @Description: TODO	tree对象专用,获取指定树编码下的所有直接子节点
	 * @param parentCode		父节点ID
	 * @param treeLevelLength	树等级
	 * @return: List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> findChildByCode(final String parentCode, final int treeLevelLength){
		String codeLike = parentCode;
		for( int i=0; i<treeLevelLength; i++ ){
			codeLike += "_";
		}
		return getSession().createCriteria(entityClass)
				.add( Restrictions.like(TreeUtils.TREE_DEFAULT_COL_NAME, codeLike) )
			    .addOrder( Order.asc(TreeUtils.TREE_DEFAULT_COL_NAME) )
			    .list();	
	}
	
	/**
	 * findOtherTrees
	 * @Description: TODO	tree对象专用,获取指定树编码树节点以外的所有节点
	 * @param treeCode	树编码
	 * @return: List<T>
	 */
	public List<T> findOtherTrees(final String treeCode){
		String objName = entityClass.getName();
		String hql = "from "+objName+" where treeCode not like ? order by treeCode";
		return find(hql, treeCode+"%");
	}
	
	/**
	 * getTreeCodeById
	 * @Description: TODO	tree对象专用,获取指定节点的树编码
	 * @param id		根据id获取树节点编码
	 * @return: String
	 */
	public String getTreeCodeById(final ID id){
		if(isIdBlank(id)){
			return null;
		}
		
		String objName = entityClass.getName();
		String hql = "select treeCode from "+objName+" where "+getIdName()+"=?";
		String treeCode = findUnique(hql, id);
		return treeCode;
	}
	
	/**
	 * getMaxCodeById
	 * @Description: TODO	tree对象专用,获取指定父节点的所有节点的最大树编码
	 * @param parentId	父节点
	 * @return: String	最大树编码
	 */
	public String getMaxCodeById(final ID parentId){
		String hql;
		String treeCode = null;
		String objName = entityClass.getName();
		if(isIdBlank(parentId)){
			hql = "select max(treeCode) from "+objName+" where parentId is null or parentId=''";
			treeCode = findUnique(hql);
		}
		else{
			hql = "select max(treeCode) from "+objName+" where parentId=?";
			treeCode = findUnique(hql, parentId);		
		}
		return treeCode;
	}

	/**
	 * getMaxCodeByCode
	 * @Description: TODO	tree对象专用,获取指定父节点的所有节点的最大树编码
	 * @param parentCode		父编码
	 * @param treeLevelLength	树等级
	 * @return: String
	 */
	public String getMaxCodeByCode(final String parentCode, final int treeLevelLength){
		String codeLike = parentCode;
		for( int i=0; i<treeLevelLength; i++ ){
			codeLike += "_";
		}
		String objName = entityClass.getName();
		String hql = "select max(treeCode) from "+objName+" where treeCode like ?";
		String treeCode = findUnique(hql, codeLike);
		return treeCode;
	}
	
	/**
	 * findNextTrees
	 * @Description: TODO	tree对象专用,获取指定编码节点以后的所有兄弟树
	 * @param treeCode	树编码
	 * @param treeLevelLength	树等级
	 * @return: List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> findNextTrees(final String treeCode, final int treeLevelLength){
		String nextCode = TreeUtils.getNextTreeCode(treeCode, treeLevelLength);
		String parentCode = treeCode.substring(0, treeCode.length() - treeLevelLength);
		return getSession().createCriteria(entityClass)
				.add( Restrictions.ge(TreeUtils.TREE_DEFAULT_COL_NAME, nextCode) )
				.add( Restrictions.like(TreeUtils.TREE_DEFAULT_COL_NAME, parentCode+"%") )
			    .addOrder( Order.asc(TreeUtils.TREE_DEFAULT_COL_NAME) )
			    .list();	
	}
	
	/**
	 * findMeNextTrees
	 * @Description: TODO	tree对象专用,获取指定编码节点树及以后的所有兄弟树
	 * @param treeCode	树编码
	 * @param treeLevelLength	树等级
	 * @return
	 * @return: List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> findMeNextTrees(final String treeCode, final int treeLevelLength){
		String parentCode = treeCode.substring(0, treeCode.length() - treeLevelLength);
		return getSession().createCriteria(entityClass)
				.add( Restrictions.ge(TreeUtils.TREE_DEFAULT_COL_NAME, treeCode) )
				.add( Restrictions.like(TreeUtils.TREE_DEFAULT_COL_NAME, parentCode+"%") )
			    .addOrder( Order.asc(TreeUtils.TREE_DEFAULT_COL_NAME) )
			    .list();	
	}
	
	/**
	 * deleteTree
	 * @Description: TODO	tree对象专用,删除指定编码的树（包括所有子节点）
	 * @param treeCode	树编码
	 * @return: void
	 */
	public void deleteTree(final String treeCode){
		String objName = entityClass.getName();
		String hql = "delete from "+objName+" where treeCode like ? ";
		batchExecute(hql, treeCode+"%");
	}
	
	/**
	 * deleteSubTree
	 * @Description: TODO	tree对象专用,删除指定编码的树（包括所有子节点，但不包括根节点）
	 * @param treeCode	树编码
	 * @return: void
	 */
	public void deleteSubTree(final String treeCode){
		String objName = entityClass.getName();
		String hql = "delete from "+objName+" where treeCode like ? and treeCode<>?  ";
		batchExecute(hql, treeCode+"%",treeCode);
	}
	
	/**
	 * getNewChildCode
	 * @Description: TODO	tree对象专用,计算指定id节点的新增加子节点的树编码
	 * @param id		
	 * @param treeLevelLength	树等级
	 * @return: String
	 */
	public String getNewChildCode(final ID id, final int treeLevelLength){
		String originalCode = TreeUtils.GetLevelKey(TreeUtils.TREE_KEY_ORIGINAL_NUMBER, treeLevelLength);
		String maxTreeCode = getMaxCodeById(id);
		if (isIdBlank(id)) {//根节点
			if (StringUtils.isBlank(maxTreeCode)) {
				maxTreeCode = originalCode;
			}
		} 
		else {//分支节点
			String parentCode = getTreeCodeById(id);
			if (StringUtils.isBlank(maxTreeCode)) {
				maxTreeCode = parentCode + originalCode;
			}
		}
		return TreeUtils.getNextTreeCode(maxTreeCode, treeLevelLength);
	}
	
	/**
	 * moveNode
	 * @Description: TODO	tree对象专用,移动树节点
	 * @param moveId		移动节点ID
	 * @param targetId	目标ID
	 * @param moveType	移动类型
	 * @param treeLevelLength	树等级
	 * @return: boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean moveNode(final ID moveId, final ID targetId, int moveType, int treeLevelLength){
		T moveNode = get(moveId);
		if( moveNode == null ){
			return false;
		}
		T targetNode = get(targetId);
		if( targetNode == null ){
			return false;
		}
		
		String moveNodeCode = getTreeCode(moveNode);

		// 获取所有要移动的节点
		List<T> listMove = findSubTree(moveNodeCode);
		// 获取所有假删除影响的节点
		List<T> listChange1 = findNextTrees(moveNodeCode, treeLevelLength);

		// 把要移动的节点的树编码清空(假删除)
		String objName = entityClass.getName();
		String hql = "update "+objName+" set "+TreeUtils.TREE_DEFAULT_COL_NAME+"='*' where "+TreeUtils.TREE_DEFAULT_COL_NAME+" like '" + moveNodeCode + "%'";
		batchExecute(hql);
		flush();

		// 更改假删除后影响的节点
		String treeCode;
		String adjustCode;
		for (T obj : listChange1)
		{
			treeCode = getTreeCode(obj);
			adjustCode = TreeUtils.getLevelLastTreeCode(treeCode, moveNodeCode.length());
			Reflections.setFieldValue(obj, TreeUtils.TREE_DEFAULT_COL_NAME, adjustCode);
			save(obj);
			flush();
		}

		ID parentId = null;
		String moveCode = null;
		int levelLength = 0;
		List<T> listChange2 = null;
		switch (moveType)
		{
			case TreeUtils.MOVE_TYPE_FIRST_SUB: // 移动到目标节点的第一子节点
				parentId = targetId;
				treeCode = getTreeCode(targetNode);
				levelLength = treeCode.length() + treeLevelLength;
				listChange2 = findSubTrees(treeCode);
				moveCode = treeCode + TreeUtils.GetLevelKey(TreeUtils.TREE_KEY_ORIGINAL_NUMBER + 1);
				break;
			case TreeUtils.MOVE_TYPE_LAST_SUB: // 移动到目标节点的最后子节点
				parentId = targetId;
				treeCode = getTreeCode(targetNode);
				levelLength = treeCode.length() + treeLevelLength;
				moveCode = getNewChildCode(targetId, treeLevelLength);
				break;
			case TreeUtils.MOVE_TYPE_UP_OBJ: // 移动到目标节点的上方
				parentId = (ID)Reflections.getFieldValue(targetNode, TreeUtils.TREE_PARENTID_COL_NAME);
				treeCode = getTreeCode(targetNode);
				levelLength = treeCode.length();
				listChange2 = findMeNextTrees(treeCode, treeLevelLength);
				moveCode = treeCode;
				break;
			case TreeUtils.MOVE_TYPE_DOWN_OBJ: // 移动到目标节点的下方
				parentId = (ID)Reflections.getFieldValue(targetNode, TreeUtils.TREE_PARENTID_COL_NAME);
				treeCode = getTreeCode(targetNode);
				levelLength = treeCode.length();
				listChange2 = findNextTrees(treeCode, treeLevelLength);
				moveCode = TreeUtils.getNextTreeCode(treeCode);
				break;
		}

		// 受影响的节点向下移
		if (listChange2 != null && listChange2.size() > 0)
		{
			for (T obj : listChange2)
			{
				treeCode = getTreeCode(obj);
				adjustCode = TreeUtils.getLevelNextTreeCode(treeCode, levelLength);
				Reflections.setFieldValue(obj, TreeUtils.TREE_DEFAULT_COL_NAME, adjustCode);
				save(obj);
				flush();
			}
		}	

		// 最后插入要移动的节点
		Reflections.setFieldValue(listMove.get(0), TreeUtils.TREE_PARENTID_COL_NAME, parentId);
		save(listMove.get(0));
		flush();
		
		String idName = getIdName();
		ID id;
		for (T obj : listMove)
		{
			id = (ID)Reflections.getFieldValue(obj, idName );
			treeCode = getTreeCode(obj);
			adjustCode = TreeUtils.getMoveTreeCode(treeCode, moveCode, moveNodeCode.length());
			Reflections.setFieldValue(obj, TreeUtils.TREE_DEFAULT_COL_NAME, adjustCode);
			hql = "update "+objName+" set "+TreeUtils.TREE_DEFAULT_COL_NAME+"=? where "+getIdName()+"=? ";
			batchExecute(hql, adjustCode, id);
			flush();
		}

		return true;
	}
	
	/**
	 * keepTreeList
	 * @Description: TODO	检查树列表是否完整,不完整则补齐以保持树列表
	 * @param list	用户菜单集合
	 * @param minLevel	菜单树最小等级
	 * @param treeLevelLength	菜单树等级，最小为3
	 * @return: void
	 */
	public void keepTreeList(List<T> list, int minLevel, int treeLevelLength)
	{
		if (list == null){
			return;
		}

		String lastLevelCode[] = { "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?" };
		for (int i = 0; i < list.size(); i++)
		{
			T item = list.get(i);
			String nowCode = getTreeCode(item);
			int nowLevel = nowCode.length() / treeLevelLength;
			lastLevelCode[nowLevel] = nowCode;
			if (nowLevel > minLevel){
				int addCount = 0;
				for (int j = nowLevel - 1; j >= minLevel; j--){
					if (getTreeCode(item).startsWith(lastLevelCode[j])){
						break;
					}
					else{
						String levelCode = nowCode.substring(0, j * treeLevelLength);
						T addItem = getObjByCode(levelCode);
						if (addItem != null){
							addCount++;
							list.add(i, addItem);
							lastLevelCode[j] = levelCode;
						}
					}
				}
				i += addCount;
			}
		}
	}
}