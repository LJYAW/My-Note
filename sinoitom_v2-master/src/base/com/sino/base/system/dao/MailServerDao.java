/*
 * 文件名： MailServerDao.java
 * 
 * 创建日期： 2014-10-20
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.base.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.base.system.entity.MailServer;


/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-20
 */
@Component
public class MailServerDao extends BaseDao<MailServer, Integer>  {
	
	public  List<MailServer>  getMailServerList(){
	    Query	query=this.getSession().createQuery("from MailServer");  
		return query.list();
	}
	
	public MailServer  getMailServerById(int id){
		  Query	query=this.getSession().createQuery("from MailServer where mailServerID="+id);  
			return (MailServer) query.list().get(0);
	}

}
