package com.sino.base.common.timer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimerTask;


import com.sino.base.common.Global;
import com.sino.base.common.TelnetPool;
import com.sino.base.common.telnet.TelnetConnection;
import com.sino.base.common.util.JdbcConnection;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtil;
import com.sino.base.common.util.TelnetUtil;
import com.sino.base.db.entity.TableInfo;

public class SaveConfigTask extends TimerTask{

	private static boolean isRunning = false;
	
	private static String ip1="";
	private static String ip2="";
	
	public static String getIp1(){
		return ip1;
	}
	public static String getIp2(){
		return ip2;
	}
	
	public void run() {

		try {
			  if(StringUtil.isNullString(ip1)&&StringUtil.isNullString(ip2)){
				   findMgmtIp();
			   }    
			   
				if(SystemUtil.getCount()>0){
					String [] ips={ip1,ip2};
					for (int i = 0; i < ips.length; i++) {
						if (!StringUtil.isNullString(ips[i])) {
							TelnetConnection telnet = TelnetPool.getTelnet(ips[i]);
							if(telnet!=null){
								if(telnet.isConnect(ips[i])){
									telnet.initPrompt("Copy complete.");
									telnet.sendExitCommand("copy running startup");
									System.out.println(ips[i]+"执行保存配置成功。。。。");
							        SystemUtil.initCount();
								}else{
									System.out.println(ips[i]+"出现网络连接故障，请排查。。。");
								}
							}
						}
					}
				}
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		 

	}
	
	
	public void findMgmtIp(){
		JdbcConnection conn=new JdbcConnection();
		Connection con = conn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql=" select Mgmt_Ip1,Mgmt_Ip2 from Sdn_VxLanGW where Vtep_Ip=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Global.VTEP_IP);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ip1=rs.getString(1);
				ip2=rs.getString(2);
				System.out.println(ip1);
				System.out.println(ip2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcConnection.free(rs, pstmt, con);
		}
	}
}
