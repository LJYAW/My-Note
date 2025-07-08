package com.sino.base.system.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.sino.base.common.tree.TreeDao;
import com.sino.base.common.tree.TreeUtils;
import com.sino.base.system.entity.SysResItem;

@Component
public class SysResItemDao extends TreeDao<SysResItem, String> {
	
	public Integer getMaxNumber(final String grpId){
		String hql = "select max(resNumber) from SysResItem where sysResGroup.resGrpId=? ";
		Integer number = findUnique(hql, grpId);
		return number==null ? 0:number;		
	}
	
	public Integer getItemNumber(final String id){
		String hql = "select resNumber from SysResItem where resId=?";
		Integer number = findUnique(hql, id);
		return number;		
	}
	
	@SuppressWarnings("unchecked")
	public List<SysResItem> getGroupAll(final String grpId, final String orderByProperty, final boolean isAsc) {
		Criteria c = getSession().createCriteria(SysResItem.class)
				.add( Restrictions.eq("sysResGroup.resGrpId", grpId));
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}
	
	// tree对象专用,根据树编码获取该节点及所有子节点
	@SuppressWarnings("unchecked")
	public List<SysResItem> findSubTree(final String grpId, final String treeCode){
		return getSession().createCriteria(SysResItem.class)
			    .add( Restrictions.eq("sysResGroup.resGrpId", grpId) )
			    .add( Restrictions.like("treeCode", treeCode+"%") )
			    .addOrder( Order.asc("treeCode") )
			    .list();	
	}
	
	// tree对象专用,根据树编码获取该节点下的所有树节点
	@SuppressWarnings("unchecked")
	public List<SysResItem> findSubTrees(final String grpId, final String treeCode){
		return getSession().createCriteria(SysResItem.class)
			    .add( Restrictions.eq("sysResGroup.resGrpId", grpId) )
				.add( Restrictions.ne("treeCode", treeCode) )
			    .add( Restrictions.like("treeCode", treeCode+"%") )
			    .addOrder( Order.asc("treeCode") )
			    .list();	
	}
	
	// tree对象专用,获取指定父节点id的所有节点
	@SuppressWarnings("unchecked")
	public List<SysResItem> findChildById(final String grpId, final String parentId){
		if(StringUtils.isBlank(parentId)){
			return getSession().createCriteria(SysResItem.class)
				    .add( Restrictions.eq("sysResGroup.resGrpId", grpId) )
					.add( Restrictions.or(
							Restrictions.eq("parentId", ""),
							Restrictions.isNull("parentId") 
						))
				    .addOrder( Order.asc("treeCode") )
				    .list();	
		}
		return getSession().createCriteria(SysResItem.class)
				.add( Restrictions.eq("parentId", parentId) )
			    .addOrder( Order.asc("treeCode") )
			    .list();	
	}
	
	// tree对象专用,获取指定树编码下的所有直接子节点
	@SuppressWarnings("unchecked")
	public List<SysResItem> findChildByCode(final String grpId, final String parentCode, final int codeLength){
		String codeLike = parentCode;
		for( int i=0; i<codeLength; i++ ){
			codeLike += "_";
		}
		return getSession().createCriteria(SysResItem.class)
			    .add( Restrictions.eq("sysResGroup.resGrpId", grpId) )
				.add( Restrictions.like("treeCode", codeLike) )
			    .addOrder( Order.asc("treeCode") )
			    .list();	
	}
	
	// tree对象专用,获取指定树编码树节点以外的所有节点
	public List<SysResItem> findOtherTrees(final String grpId, final String treeCode){
		String hql = "from SysResItem where sysResGroup.resGrpId=? and treeCode not like ? order by treeCode";
		return find(hql, grpId, treeCode+"%");
	}
	
	
	// tree对象专用,获取指定父节点的所有节点的最大树编码
	public String getMaxCodeById(final String grpId, String parentId){
		String hql;
		String treeCode = null;
		if(StringUtils.isBlank(parentId)){
			hql = "select max(treeCode) from SysResItem where sysResGroup.resGrpId=? and parentId is null or parentId=''";
			treeCode = findUnique(hql, grpId);
		}
		else{
			hql = "select max(treeCode) from SysResItem where parentId=?";
			treeCode = findUnique(hql, parentId);		
		}
		return treeCode;
	}

	// tree对象专用,获取指定父节点的所有节点的最大树编码
	public String getMaxCodeByCode(final String grpId, final String parentCode, final int codeLength){
		String codeLike = parentCode;
		for( int i=0; i<codeLength; i++ ){
			codeLike += "_";
		}
		String hql = "select max(treeCode) from SysResItem where sysResGroup.resGrpId=? and treeCode like ?";
		String treeCode = findUnique(hql, grpId, codeLike);
		return treeCode;
	}
	
	// tree对象专用,获取指定编码节点以后的所有兄弟树
	@SuppressWarnings("unchecked")
	public List<SysResItem> findNextTrees(final String grpId, final String treeCode, final int codeLength){
		String nextCode = TreeUtils.getNextTreeCode(treeCode, codeLength);
		String parentCode = treeCode.substring(0, treeCode.length() - codeLength);
		return getSession().createCriteria(SysResItem.class)
			    .add( Restrictions.eq("sysResGroup.resGrpId", grpId) )
				.add( Restrictions.ge("treeCode", nextCode) )
				.add( Restrictions.like("treeCode", parentCode+"%") )
			    .addOrder( Order.asc("treeCode") )
			    .list();	
	}
	
	// tree对象专用,获取指定编码节点树及以后的所有兄弟树
	@SuppressWarnings("unchecked")
	public List<SysResItem> findMeNextTrees(final String grpId, final String treeCode, final int codeLength){
		String parentCode = treeCode.substring(0, treeCode.length() - codeLength);
		return getSession().createCriteria(SysResItem.class)
			    .add( Restrictions.eq("sysResGroup.resGrpId", grpId) )
				.add( Restrictions.ge("treeCode", treeCode) )
				.add( Restrictions.like("treeCode", parentCode+"%") )
			    .addOrder( Order.asc("treeCode") )
			    .list();	
	}
	
	// tree对象专用,删除指定编码的树（包括所有子节点）
	public void deleteTree(final String grpId, final String treeCode){
		String hql = "delete from SysResItem where sysResGroup.resGrpId=? and treeCode like ? ";
		batchExecute(hql, grpId, treeCode+"%");
	}
	
	// tree对象专用,计算指定id节点的新增加子节点的树编码
	public String getNewChildCode(final String grpId, final String id, final int treeLevelLength){
		String originalCode = TreeUtils.GetLevelKey(TreeUtils.TREE_KEY_ORIGINAL_NUMBER, treeLevelLength);
		String maxTreeCode = getMaxCodeById(grpId, id);
		if (StringUtils.isBlank(id)) {
			if (StringUtils.isBlank(maxTreeCode)) {
				maxTreeCode = originalCode;
			}
		} 
		else {
			String parentCode = getTreeCodeById(id);
			if (StringUtils.isBlank(maxTreeCode)) {
				maxTreeCode = parentCode + originalCode;
			}
		}
		return TreeUtils.getNextTreeCode(maxTreeCode, treeLevelLength);
	}
}
