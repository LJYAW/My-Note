package com.sino.cmdb.indicator.group.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.indicator.group.entity.IndicatorGroup;
import com.sino.cmdb.product.prodType.entity.ResItemParam;


/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
@Component
public class IndGroupDao extends BaseDao<IndicatorGroup, Long> {

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllClassCode() {
		StringBuffer hql=new StringBuffer("select distinct indClassCode,indClassName ");
		hql.append("from IndicatorGroup order by indClassCode");
		Query query=this.getSession().createQuery(hql.toString());  
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}
	
}