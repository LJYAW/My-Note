package com.sino.cmdb.indicator.cmdCheckItems.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.cmdb.indicator.cmdCheckItems.dao.AckDevCheckItemDao;
import com.sino.cmdb.indicator.cmdCheckItems.dao.CmdbProdCmdCheckItemsDao;
import com.sino.cmdb.indicator.cmdCheckItems.entity.AckDevCheckItem;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;
import com.sino.fas.res.net.dao.NcmDevAccessUserDao;
import com.sino.fas.res.net.dao.NcmDevicesDao;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.entity.NcmDevices;

@Service
@Transactional
public class AckDevCheckItemService {
	
	@Autowired
	private AckDevCheckItemDao ackDevCheckItemDao;
	
	@Autowired
	private NcmDevicesDao ncmDevicesDao;
	
	@Autowired
	private NcmDevAccessUserDao devAccessUserDao;
	
	@Autowired
	private CmdbProdCmdCheckItemsDao cmdbProdCmdCheckItemsDao;
	
	
//	验证CmdbProdCmdCheckItems这张表中定义的规则
	public boolean checkCmdParseRule(Long devId,String checkResult) {
		
		boolean flag = false;
		
//		查找该设备对应的指标项
		List<Integer> checkItemIdList = ackDevCheckItemDao.getByDevId(devId);

		NcmDevices ncmDevices = ncmDevicesDao.get(devId);
//		获取设备的ip地址
		String hostIp = ncmDevices.getDevIpAddr();
		
		if(ncmDevices!=null){
			String devAccessId = ncmDevices.getDevAccessId();
			
			if(StringUtils.isNotBlank(devAccessId)){
//			查找该设备的连接方式
				NcmDevAccessUser ncmDeviceAccess = devAccessUserDao.get(devAccessId);
				if(ncmDeviceAccess!=null){
//					是ssh还是telnet连接
					String accessTool = ncmDeviceAccess.getAccessTool();
//					用户名
					String userName = ncmDeviceAccess.getUserName();
//					密码
					String passWord = ncmDeviceAccess.getPassWord();
//					进入特权模式的命令
//					String privUserCmd = ncmDeviceAccess.getPrivUserCmd();
					String privUserCmd = ncmDeviceAccess.getPrivModeCmd();
//					特权模式下用户符是什么
//					String privUserPromt = ncmDeviceAccess.getPrivUserPromt();
					String privUserPromt = ncmDeviceAccess.getUserName();
//					特权模式的密码
//					String enablePassWord = ncmDeviceAccess.getEnablePassWord();
					String enablePassWord = ncmDeviceAccess.getPassWord();
					
					JschUtils jschUtils = null;
					if(accessTool.equalsIgnoreCase("ssh")){
//						初始化连接设备的工具类
						try {
							jschUtils = new JschUtils(hostIp, userName, passWord, privUserCmd, enablePassWord, privUserPromt);
						} catch (Exception e) {
							System.out.println(hostIp+" 设备连接出现问题，请检查网络！");
							e.printStackTrace();
						}
					}
					
					String cmdResult= "";
					Map<String,String> cmdAndResultMap = new HashMap<String, String>();
					
//					遍历指标项
					if(checkItemIdList!=null && checkItemIdList.size()>0){
						for (Integer checkItemId : checkItemIdList) {
							CmdbProdCmdChkItems cmdbProdCmdCheckItems = cmdbProdCmdCheckItemsDao.get(checkItemId);
							if(cmdbProdCmdCheckItems!=null){
//								获取指标项要执行的命令
								String checkCmd = cmdbProdCmdCheckItems.getCheckCmd();
								
//								获取解析规则中的关键字
								String resultKeyWords = cmdbProdCmdCheckItems.getResultKeyWords();
//								获取解析规则中的解析方法
								String indParseRule = cmdbProdCmdCheckItems.getRegEx();
//								获取要解析出的指标项
								String indicatorItem = cmdbProdCmdCheckItems.getIndItemEnName();
//								获取要解析出的指标组
								String indClassName = cmdbProdCmdCheckItems.getIndClassName();
								
//								如果这个命令没有执行过，那么就去执行，获取执行结果
								if(!cmdAndResultMap.containsKey(checkCmd)){
									if(jschUtils!=null){//使用ssh连接
//										获取命令结果
										cmdResult = jschUtils.getCmdResult(checkCmd);
										
//										将该命令和结果放到map中
										cmdAndResultMap.put(checkCmd, cmdResult);
										
//										如果结果是空字符串，那么就跳过，去执行下一个命令
										if(StringUtils.isBlank(cmdResult)){
											System.out.println("执行"+checkCmd+"命令后，结果为空，请检查该命令是否正确！");
											continue;
										}
										
									}
								}else{
									cmdResult = cmdAndResultMap.get(checkCmd);
								}
//								获取到的解析结果
								String result = handleResult(cmdResult,indClassName,indicatorItem,resultKeyWords,indParseRule);
								
								System.out.println(indicatorItem+"----"+result);
							}
						}
					}
				}
			}
		}
		return flag;
	}
	
	/*		cmdResult:解析的结果，比如：
	 * 				show process cpu | include CPU utilization
					CPU utilization for five seconds: 29%/0%; one minute: 28%; five minutes: 27%
					SINO-BJ-SW29-15#
			indClassName:指标组，比如  CPU
			indicatorItem：指标项，比如  Cpu_Util_5Min
			resultKeyWords：解析规则中的关键字， 比如one minute
			indParseRule：规则中定义的解析方法，比如subString
			
	 * 
	 */
	private String handleResult(String cmdResult,String indClassName,String indicatorItem,String resultKeyWords,String indParseRule){
		String result = "";
		
		if(indClassName.equalsIgnoreCase("cpu")){
			String[] cmdResultSplit = cmdResult.split("\r\n");
//			cmdResult:CPU utilization for five seconds: 29%/0%; one minute: 28%; five minutes: 27%
			String lineStr = cmdResultSplit[1];
			String[] lineStrSplit = lineStr.split(";");
			for (String item : lineStrSplit) {
				if(item.contains(resultKeyWords)){
					result = item.split(":")[1].trim();
					break;
				}
			}
		}
		return result;
	}
	
	public List<AckDevCheckItems> getByDevId(long devId){
		String hql=" from AckDevCheckItems where devId=?"; 
		return ackDevCheckItemDao.find(hql, devId);
	}
	

}
