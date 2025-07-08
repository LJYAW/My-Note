package com.sino.cmdb.vendor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.vendor.entity.CmdbVendor;
/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-1-13
 */
@Component
public class CmdbVendorDao extends BaseDao<CmdbVendor, Integer> {

	@SuppressWarnings("unchecked")
	public List<CmdbVendor> getByVendorIds(List<Integer> ids){
		Query query=this.getSession().createQuery("from CmdbVendor where vendorID in (:ids) ");  
		query.setParameterList("ids", ids); 
		return (List<CmdbVendor>)query.list();
	}

}
