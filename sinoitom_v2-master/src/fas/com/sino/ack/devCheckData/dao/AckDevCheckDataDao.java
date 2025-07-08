/**
 * 
 */
package com.sino.ack.devCheckData.dao;

import com.sino.ack.devCheckData.entity.AckDevCheckData;
import com.sino.base.common.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springside.modules.columnbase.DBCPUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mr.LP
 * @date 2019-7-11下午2:16:11
 *
 */

@Component
public class AckDevCheckDataDao extends BaseDao<AckDevCheckData, Long> {

	
	public List<ParamCheckData> getDevCheckData(Integer ackTaskId,Timestamp ts,String devIp,String checkCmd){
		
		String hql="SELECT d.indItemName,d.indvalue,res.chkResult FROM AckDevCheckData as d,AckDevCheckResult as res " +
				"where d.ackTaskId=:ackTaskId and d.taskStartTime=:ts and d.devIp=:devIp and d.checkCmd=:checkCmd " +
				"AND d.checkCmdId=res.checkCmdId and d.devIp=res.devIp group by d.indItemName";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("ackTaskId", ackTaskId);
		query.setParameter("ts", ts);
		query.setParameter("devIp", devIp);
		query.setParameter("checkCmd", checkCmd);
		
		List list=query.list();
		Iterator it = list.iterator();
		List<ParamCheckData> paramList = new ArrayList<ParamCheckData>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ParamCheckData itemParam = new ParamCheckData();
			itemParam.setIndItemName(tuple[0].toString());
			itemParam.setIndvalue(tuple[1].toString());
			itemParam.setChkResult(tuple[2].toString());
			paramList.add(itemParam);
		}
		
		
		return paramList;
	}

	/**
	 * Get device auto check data from column database,
	 * data has parse by rule!
	 *
	 * @param ackTaskId Task id
	 * @param ackTaskStartTime Start time
	 * @param devIp2Long Device ip to long
	 * @param checkCmdId Check command id
	 * @return Device check data
	 */
	public List<ParamCheckData> getDevCheckDataFromColumnBase(int ackTaskId,long ackTaskStartTime,long devIp2Long,int checkCmdId){

		String sql="Select ind_item_cn_name, ind_val from DevAutoCheckData "
				+ " where ack_task_start_time_gmt = ? and ack_task_id = ? and dst_dev_ip_long = ? and check_cmd_id =? "
				+ " order by collection_time";
		ResultSet resultSet = null;
		List<ParamCheckData> paramList = new ArrayList<ParamCheckData>();
		try{
			
			Connection connection = DBCPUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, ackTaskStartTime);
			statement.setInt(2, ackTaskId);
			statement.setLong(3, devIp2Long);
			statement.setInt(4, checkCmdId);

			resultSet = statement.executeQuery();
			while (resultSet.next()){
				ParamCheckData data = new ParamCheckData();
				String indItemCnName = resultSet.getString("ind_item_cn_name");
				String indVal = resultSet.getString("ind_val");
				data.setChkResult("");
				data.setIndItemName(indItemCnName);
				data.setIndvalue(indVal);
				paramList.add(data);
			}
		}catch (SQLException e){
			logger.error("AckDevCheckDataDao catch SQLException: {}", e.getMessage());
			return paramList;
		} finally{
			try {
				if(resultSet !=null ) resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return paramList;
	}
}
