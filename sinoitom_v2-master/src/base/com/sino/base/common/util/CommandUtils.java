package com.sino.base.common.util;

import smartlink.netflow.common.ProvisionUtil;


public class CommandUtils {	
	
		public static void checkParamChange(String paramCode, String value) {
			if( "SnmpDefaultRoString".equals(paramCode)){
				ProvisionUtil.getInstance().updateGlobalSnmpRoCommunity(value);
			}
			else if( "xFlowGlobalSwitch".equals(paramCode)){
				int status = 0;
				if( "1".equals(value) || "true".equals(value.toLowerCase())){
					status = 1;
				}
				ProvisionUtil.getInstance().updatexFlowCollectingStatus(status);
			}
			else if( "baseLineSwitch".equals(paramCode)){
				int status = 0;
				if( "1".equals(value) || "true".equals(value.toLowerCase())){
					status = 1;
				}
				ProvisionUtil.getInstance().updateGlobalBaselineStatus(status);
			}
		}

}
