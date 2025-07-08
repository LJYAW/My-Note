package org.springside.modules.orm.hibernate;

import com.sino.base.system.entity.JqPageBean;
import org.apache.commons.lang3.Validate;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.Reflections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API.
 *
 * @param <T>  DAO操作的对象类型
 * @param <ID> 主键类型
 * @author calvin
 *
 * @date 2017年5月3日 上午10:31:10
 */
public class SimpleHibernateDao<T, ID extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDao() {
		this.entityClass = Reflections.getSuperClassGenricType(getClass());
	}

	public SimpleHibernateDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * getSessionFactory
	 * @Description: 取得sessionFactory.
	 * @return: SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 * setSessionFactory
	 * @Description: 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
	 * @return: void
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * getSession
	 * @Description: 获取当前Session
	 * @return: Session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @param entity 实体类
	 * save
	 * @Description: 保存新增或修改的对象.
	 * @return: void
	 */
	public void save(final T entity) {
		Validate.notNull(entity, "entity不能为空");
		getSession().clear();
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
	}

	/**
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 * delete
	 * @Description: 删除对象
	 * @return: void
	 */
	public void delete(final T entity) {
		Validate.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/**
	 * @param list 对象集合
	 * delete
	 * @Description: 删除多个对象.
	 * @return: void
	 */
	public void delete(final List<T> list) {
		Validate.notNull(list, "list不能为空");
		for (int i = 0; i < list.size(); i++) {
			delete(list.get(i));
		}
	}

	/**
	 * @param id
	 * delete
	 * @Description: 按id删除对象.
	 * @return: void
	 */
	public void delete(final ID id) {
		Validate.notNull(id, "id不能为空");
		delete(get(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/**
	 * @param id
	 * get
	 * @Description: 按id获取对象.
	 * @return: T
	 */
	public T get(final ID id) {
		Validate.notNull(id, "id不能为空");
		//		return (T) getSession().load(entityClass, id);
		return (T) getSession().get(entityClass, id);
	}

	/**
	 * @param id
	 * load
	 * @Description: 按id加载对象.
	 * @return: T
	 */
	public T load(final ID id) {
		Validate.notNull(id, "id不能为空");
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * @param ids id集合
	 * get
	 * @Description: 按id列表获取对象列表.
	 * @return: List<T>
	 */
	public List<T> get(final Collection<ID> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 * @param ids id集合
	 * get
	 * @Description: 按id列表获取对象列表.
	 * @return: List<T>
	 */
	public List<T> get(JqPageBean pageBean, final Collection<ID> ids) {
		return find(pageBean, Restrictions.in(getIdName(), ids));
	}

	/**
	 * getAll
	 * @Description: 获取全部对象
	 * @return: List<T>
	 */
	public List<T> getAll() {
		return find();
	}

	/**
	 * 获取全部对象, 支持按属性行序.
	 *
	 * @param orderByProperty 排序字段名
	 * @param isAsc           是否升序
	 */
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/**
	 * getAll
	 * @Description: jqGrid分页查询所有数据
	 * @param pageBean 分页参数
	 * @return: List<T>
	 */
	//	public List<T> getAll(JqPageBean pageBean) {
	//		Criteria c = createCriteria();
	//
	//		//排序
	//		String sortIdx = pageBean.getSidx();
	//		if (sortIdx.length() > 0) {
	//			if ("asc".equalsIgnoreCase(pageBean.getSord())) {
	//				c.addOrder(Order.asc(sortIdx));
	//			} else {
	//				c.addOrder(Order.desc(sortIdx));
	//			}
	//		}
	//
	//		c.setFirstResult((pageBean.getPage() - 1) * pageBean.getRows());
	//		c.setMaxResults(pageBean.getRows());
	//
	//		return (List<T>)c.list();
	//	}


	public long count(Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) Reflections.getFieldValue(impl, "orderEntries");
			Reflections.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			Reflections.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;

	}

	public Criteria getSearchCriteria(List<Criterion> criList, Class cls) {
		Criteria c = getSession().createCriteria(cls);
		if (criList != null) {
			for (Criterion criterion : criList) {
				c.add(criterion);
			}
		}
		return c;
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Validate.notBlank(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(final String propertyName[], final Object value[]) {
		Validate.isTrue(propertyName.length == value.length, "propertyName长度与value不一致");
		Criteria c = getSession().createCriteria(entityClass);
		for (int i = 0; i < propertyName.length; i++) {
			c.add(Restrictions.eq(propertyName[i], value[i]));
		}
		return c.list();
	}

	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Validate.notBlank(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 *
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 *
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 *
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 *
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 *
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 *
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 *
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Validate.notBlank(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 *
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Validate.notBlank(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 *
	 * @param criterions 数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按Criteria查询对象列表.支持分页，排序
	 *
	 * @param criterions 数量可变的Criterion.
	 */
	public List<T> find(JqPageBean pageBean, final Criterion... criterions) {
		return createCriteria(pageBean, criterions).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 *
	 * @param criterions 数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 *
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 *
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(JqPageBean pageBean, final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}

		//排序
		String sortIdx = pageBean.getSidx();
		if (sortIdx.length() > 0) {
			if ("asc".equalsIgnoreCase(pageBean.getSord())) {
				criteria.addOrder(Order.asc(sortIdx));
			} else {
				criteria.addOrder(Order.desc(sortIdx));
			}
		}

		criteria.setFirstResult((pageBean.getPage() - 1) * pageBean.getRows());
		criteria.setMaxResults(pageBean.getRows());

		return criteria;
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 为Query添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * <p>
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 批量插入
	 *
	 * @param entityList 一共多少条数据
	 * @param size       每次插入多少条
	 */
	public synchronized void batchSave(List<T> entityList, int size) {
		Validate.notNull(entityList, "entityList不能为空");
		for (int i = 0; i < entityList.size(); i++) {
			getSession().saveOrUpdate(entityList.get(i));
			//	            getSession().merge()
			if (i % size == size - 1) {
				getSession().flush();
				getSession().clear();
			}
		}
	}

	/**
	 * 分页查询，可根据自己需求查询前N条数据
	 *
	 * @param index
	 * @param pageSize
	 */
	@SuppressWarnings("unchecked")
	public List<T> listQuery(String hql, int index, int pageSize, Object... values) {
		Query query = createQuery(hql, values);
		return query.setFirstResult(index).setMaxResults(pageSize).list();
	}
}