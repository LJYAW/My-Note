package com.sino.base.common.util;

import org.springside.modules.utils.spring.SpringContextHolder;

import com.sino.base.system.service.LogService;
import com.sino.base.system.service.MenuService;
import com.sino.base.system.service.OrganService;
import com.sino.base.system.service.ParamItemService;
import com.sino.base.system.service.ResGroupService;
import com.sino.base.system.service.ResItemService;
import com.sino.base.system.service.UserService;

public class ServiceUtil {
		private static LogService logService = null;
		private static ResGroupService resGrpService = null;
		private static ResItemService resService = null;
		private static ParamItemService paramService = null;
		private static OrganService organService = null;
		private static UserService userService = null;
		private static MenuService menuService = null;
	
	
		public static LogService getLogService() {
			if(logService == null ){
				logService = (LogService) SpringContextHolder.getBean(LogService.class);
			}
			return logService;
		}
		
		public static ResGroupService getResGrpService(){
			if(resGrpService == null ){
				resGrpService = (ResGroupService) SpringContextHolder.getBean(ResGroupService.class);
			}
			return resGrpService;
		}
		
		public static ResItemService getResService(){
			if(resService == null ){
				resService = (ResItemService) SpringContextHolder.getBean(ResItemService.class);
			}
			return resService;
		}	
	
		public static ParamItemService getParamService(){
			if(paramService == null ){
				paramService = (ParamItemService) SpringContextHolder.getBean(ParamItemService.class);
			}
			return paramService;
		}
			
		public static OrganService getOrganService(){
			if(organService == null ){
				organService = (OrganService) SpringContextHolder.getBean(OrganService.class);
			}
			return organService;
		}

		public static UserService getUserService(){
			if(userService == null ){
				userService = (UserService) SpringContextHolder.getBean(UserService.class);
			}
			return userService;
		}

		public static MenuService getMenuService(){
			if(menuService == null ){
				menuService = (MenuService) SpringContextHolder.getBean(MenuService.class);
			}
			return menuService;
		}

}
