package com.sino.base.common;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.springside.modules.utils.Reflections;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * @ClassName: BaseDao
 * @Description: BaseDao，是所有dao层的父类，提取公共方法
 * @author .
 * @date 2017年5月3日 上午11:27:43
 *
 * @param <T> 对象实体类
 * @param <ID> 主键
 * @since Sino-Bridge From 2001-2017
 */
public class BaseDao<T, ID extends Serializable> extends HibernateDao<T, ID> {

	/**
	 * isIdBlank
	 * @Description: 判断id是否为空
	 * @param id
	 * @return: boolean
	 */
	public boolean isIdBlank(final ID id){
		if( id == null ){
			return true;
		}
		
		if(id.getClass()==String.class){
			return StringUtils.isBlank(id.toString());
		}
			
		return false;
	}
		
	/**
	 * isIdAttUnique
	 * @Description: 判断主键为id的对象的某属性值在数据库内是否唯一.
	 * @param id 主键id
	 * @param attName 属性字段名
	 * @param attValue 属性值
	 * @return: boolean
	 */
	@SuppressWarnings("rawtypes")
	public boolean isIdAttUnique(final ID id, final String attName, final Object attValue) {
		List list = null;
		if( isIdBlank(id) ){
			list = findBy(attName, attValue);
		}
		else{
			list = getSession().createCriteria(entityClass)
			    .add( Restrictions.ne(getIdName(), id) )
			    .add( Restrictions.eq(attName, attValue) )
			    .list();	
		}
		if( list==null || list.size()==0 ){
			return true;
		}
		return false;
	}
	
	/**
	 * isIdAttUnique
	 * @Description: 判断主键为id的对象的某属性值在数据库内是否唯一
	 * @param id 主键id
	 * @param attNames 多个属性字段名
	 * @param attValues 多个属性值，和字段名一一对应
	 * @return
	 * @return: boolean
	 */
	@SuppressWarnings("rawtypes")
	public boolean isIdAttUnique(final ID id, final String[] attNames, final Object[] attValues) {
		List list = null;
		if( isIdBlank(id) ){
			list = findBy(attNames, attValues);
		}
		else {
			Criteria c = getSession().createCriteria(entityClass).add( Restrictions.ne(getIdName(), id) );
			for (int i = 0; i < attNames.length; i++) {
				c.add(Restrictions.eq(attNames[i], attValues[i]));
			}
			list = c.list();
		}
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * getAttById
	 * @Description: 根据id获得某属性值
	 * @param id 主键id
	 * @param attrNames 属性字段名
	 * @return: Object[]
	 */
	public Object[] getAttById(final ID id, final String attrNames){
		String objName = entityClass.getName();
		String idName = getIdName();
		String hql = "select "+attrNames+" from "+objName+" where "+idName+"=?";
		return findUnique(hql, id);
	}
	
	/**
	 * loadObjAtts
	 * @Description: 重载对象属性值
	 * @param obj 对象实体类
	 * @param attNames 对象属性字段名
	 * @return: boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean loadObjAtts(T obj, String attNames){
		Object id = Reflections.getFieldValue(obj, getIdName());
		Object[] attrObjs = getAttById((ID)id, attNames);
		if( attrObjs == null ){
			return false;
		}
		String[] attrName = attNames.split(",");
		for( int i=0; i<attrName.length; i++ ){
			Reflections.setFieldValue(obj, attrName[i], attrObjs[i]);
		}
		return true;
	}

}