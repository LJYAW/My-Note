package com.sino.cmdb.operation.devOp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.operation.devOp.entity.DevOperation;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-2-11
 */
@Component
public class DevOperationDao extends BaseDao<DevOperation, Integer> {

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getDevClassCodes() {
		String hql="select distinct accessModeCode,accessMode from DevOperation order by accessModeCode,devOpID";
		Query query=this.getSession().createQuery(hql);
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

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getDevTypeCodes(int code) {
		String hql="select distinct opID,operation from DevOperation where accessModeCode=:code order by accessModeCode,devOpID";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
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
	
	public void batchAudit(List<Integer> opIds) {
		String hql = "update DevOperation set status=1 where devOpID in(:opIds)";
		Query queryItem = this.getSession().createQuery(hql);
		queryItem.setParameterList("opIds", opIds);
		queryItem.executeUpdate();
	}

}
