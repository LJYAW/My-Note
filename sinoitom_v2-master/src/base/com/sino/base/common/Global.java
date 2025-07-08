package com.sino.base.common;

/**
 * 
 * @ClassName: Global
 * @Description: 全局变量定义类
 * @author .
 * @date 2017年5月2日 下午8:27:15
 *
 * @since Sino-Bridge From 2001-2017
 */
public class Global
{
	/**
	 * @fieldName: DEFAULT_SKINNAME
	 * @fieldType: String
	 * @Description: 默认登录皮肤名称
	 */
	public final static String DEFAULT_SKINNAME = "default"; 

	/**
	 * @fieldName: MENU_STYLE_DEFAULT
	 * @fieldType: String
	 * @Description: 默认菜单风格 index1
	 */
	public final static String MENU_STYLE_DEFAULT = "index1"; 
	
	/**
	 * @fieldName: MENU_STYLE_2
	 * @fieldType: String
	 * @Description: 菜单风格2 index2
	 */
	public final static String MENU_STYLE_2 = "index2";
	
	/**
	 * @fieldName: MENU_STYLE_3
	 * @fieldType: String
	 * @Description: 菜单风格3 index3
	 */
	public final static String MENU_STYLE_3 = "index3"; 
	
	/**
	 * @fieldName: MENU_STYLE_5
	 * @fieldType: String
	 * @Description: 菜单风格5 index5
	 */
	public final static String MENU_STYLE_5 = "index5"; 

	/**
	 * @fieldName: LOCAL_IP
	 * @fieldType: String
	 * @Description: 本地服务器IP地址
	 */
	public final static String LOCAL_IP = "LOCAL_IP";
	
	/**
	 * @fieldName: REMOTE_IP
	 * @fieldType: String
	 * @Description: 客户端IP地址
	 */
	public final static String REMOTE_IP = "REMOTE_IP"; 
	
	public final static String WEB_REMOTE_IP = "WEB_REMOTE_IP"; 
	
	/**
	 * @fieldName: LOGIN_USER
	 * @fieldType: String
	 * @Description: 登录用户
	 */
	public final static String LOGIN_USER = "LOGIN_USER"; // 登录用户
	
	
	public final static String WEB_LOGIN_USER = "WEB_LOGIN_USER"; // 网站用户
	
	public final static String MEUN_NAME = "MEUN_NAME"; // 网站用户
	
	/**
	 * @fieldName: LOGIN_USER_MAIN_ORGAN
	 * @fieldType: String
	 * @Description: 登录用户主机构
	 */
	public final static String LOGIN_USER_MAIN_ORGAN = "LOGIN_USER_MAIN_ORGAN"; 

	/**
	 * @fieldName: LOGIN_USER_SUB_ORGAN
	 * @fieldType: String
	 * @Description: 登录用户所有子机构
	 */
	public final static String LOGIN_USER_SUB_ORGAN = "LOGIN_USER_SUB_ORGAN"; 

	/**
	 * @fieldName: LOGIN_USER_SUB_ORGAN_IDS
	 * @fieldType: String
	 * @Description: 登录用户所有子机构IDs
	 */
	public final static String LOGIN_USER_SUB_ORGAN_IDS = "LOGIN_USER_SUB_ORGAN_IDS"; 
	
	/**
	 * @fieldName: LOCAL_USER_PASSWD
	 * @fieldType: String
	 * @Description: 用户真实密码
	 */
	public final static String LOCAL_USER_PASSWD = "LOCAL_USER_PASSWD"; 
	
	/**
	 * @fieldName: LOGIN_USER_POWCODEMAP
	 * @fieldType: String
	 * @Description: 登录用户的权限编码集
	 */
	public final static String LOGIN_USER_POWCODEMAP = "LOGIN_USER_POWCODEMAP"; 
	
	/**
	 * @fieldName: LOGIN_USER_POWURLMAP
	 * @fieldType: String
	 * @Description: 登录用户的权限链接内容集
	 */
	public final static String LOGIN_USER_POWURLMAP = "LOGIN_USER_POWURLMAP"; 
	
	/**
	 * @fieldName: LOGIN_USER_MENUURLMAP
	 * @fieldType: String
	 * @Description: 登录用户的菜单链接内容集
	 */
	public final static String LOGIN_USER_MENUURLMAP = "LOGIN_USER_MENUURLMAP";
	

	/**
	 * @fieldName: CURRENT_ACCESS_URL
	 * @fieldType: String
	 * @Description: 登录用户当前访问链接
	 */
	public final static String CURRENT_ACCESS_URL = "CURRENT_ACCESS_URL"; 
	
	/**
	 * @fieldName: CURRENT_LEFT_MENU_ROOT_CODE
	 * @fieldType: String
	 * @Description: 当前访问左边菜单根节点树编码
	 */
	public final static String CURRENT_LEFT_MENU_ROOT_CODE = "CURRENT_LEFT_MENU_ROOT_CODE"; 

	/**
	 * @fieldName: PARAM_TOPMENU_PARENT_TREE_CODE
	 * @fieldType: String
	 * @Description: top菜单的父ID树编码的参数编码 topMenuParentTreeCode
	 */
	public final static String PARAM_TOPMENU_PARENT_TREE_CODE = "topMenuParentTreeCode"; 

	/**
	 * @fieldName: RES_ITEM_BACK_URL
	 * @fieldType: String
	 * @Description: 资源项管理页面返回链接
	 */
	public final static String RES_ITEM_BACK_URL = "RES_ITEM_BACK_URL";
	
	/**
	 * @fieldName: PARAMETER_BACK_URL
	 * @fieldType: String
	 * @Description: 参数管理页面返回链接
	 */
	public final static String PARAMETER_BACK_URL = "PARAMETER_BACK_URL";
	

	/**
	 * @fieldName: FALSE
	 * @fieldType: byte
	 * @Description: 假 0
	 */
	public final static byte FALSE = 0;
	
	/**
	 * @fieldName: TRUE
	 * @fieldType: byte
	 * @Description: 真 1
	 */
	public final static byte TRUE = 1;
	
	/**
	 * @fieldName: USER_NAME
	 * @fieldType: String
	 * @Description: 用户名称
	 */
	public final static String USER_NAME = "USER_NAME";

	/**
	 * @fieldName: USER_ADMIN
	 * @fieldType: String
	 * @Description: 超级用户账号 admin
	 */
	public final static String USER_ADMIN = "admin";	
	
	/**
	 * @fieldName: USER_DEVADMIN
	 * @fieldType: String
	 * @Description: 开发管理员账号 devadmin
	 */
	public final static String USER_DEVADMIN = "devadmin";

	/**
	 * @fieldName: USER_TYPE_OP_MIN
	 * @fieldType: int
	 * @Description: 最低级别操作用户 1
	 */
	public final static int USER_TYPE_OP_MIN = 1;
	
	/**
	 * @fieldName: USER_TYPE_OP_MAX
	 * @fieldType: int
	 * @Description: 最高级别操作用户 4
	 */
	public final static int USER_TYPE_OP_MAX = 4;
	
	/**
	 * @fieldName: USER_TYPE_AD_MIN
	 * @fieldType: int
	 * @Description: 最低级别管理用户 5
	 */
	public final static int USER_TYPE_AD_MIN = 5;

	/**
	 * @fieldName: USER_TYPE_AD_MAX
	 * @fieldType: int
	 * @Description: 最高级别管理用户 8
	 */
	public final static int USER_TYPE_AD_MAX = 8;
	
	/**
	 * @fieldName: USER_TYPE_DEV
	 * @fieldType: int
	 * @Description: 开发用户 9
	 */
	public final static int USER_TYPE_DEV = 9;
	

	// public final static int ROLE_TYPE_OP = 1; // 操作用户角色
	// public final static int ROLE_TYPE_AD = 5; // 管理用户角色
	// public final static int ROLE_TYPE_DV = 9; // 开发用户角色

	/**
	 * @fieldName: STATE_OFF
	 * @fieldType: int
	 * @Description: 停用状态 0
	 */
	public final static int STATE_OFF = 0;
	
	/**
	 * @fieldName: STATE_ON
	 * @fieldType: int
	 * @Description: 启用状态 1
	 */
	public final static int STATE_ON = 1;

	/**
	 * @fieldName: GROUP_TYPE_LIST
	 * @fieldType: int
	 * @Description: 列表类型 （资源组类型） 0
	 */
	public final static int GROUP_TYPE_LIST = 0;
	
	/**
	 * @fieldName: GROUP_TYPE_TREE
	 * @fieldType: int
	 * @Description: 树状类型 1
	 */
	public final static int GROUP_TYPE_TREE = 1;

	// 资源项(编码)类型
	/**
	 * @fieldName: ITEM_TYPE_STRING
	 * @fieldType: int
	 * @Description: 字符串 0
	 */
	public final static int ITEM_TYPE_STRING = 0;
	
	/**
	 * @fieldName: ITEM_TYPE_INT
	 * @fieldType: int
	 * @Description: 整数 1
	 */
	public final static int ITEM_TYPE_INT = 1;
	

	// 参数类型
	/**
	 * @fieldName: PARAM_TYPE_STRING
	 * @fieldType: String
	 * @Description: 字符串 string
	 */
	public final static String PARAM_TYPE_STRING = "string";
	
	/**
	 * @fieldName: PARAM_TYPE_INT
	 * @fieldType: String
	 * @Description: 字符串 int
	 */
	public final static String PARAM_TYPE_INT = "int";
	
	/**
	 * @fieldName: PARAM_TYPE_FLOAT
	 * @fieldType: String
	 * @Description: 浮点数 fload
	 */
	public final static String PARAM_TYPE_FLOAT = "float";
	
	/**
	 * @fieldName: PARAM_TYPE_DATETIME
	 * @fieldType: String
	 * @Description: 日期时间 datatime
	 */
	public final static String PARAM_TYPE_DATETIME = "datatime";

	/**
	 * @fieldName: PARAM_TYPE_DATE
	 * @fieldType: String
	 * @Description: 日期 date
	 */
	public final static String PARAM_TYPE_DATE = "date";
	
	/**
	 * @fieldName: PARAM_TYPE_TIME
	 * @fieldType: String
	 * @Description: 时间 time
	 */
	public final static String PARAM_TYPE_TIME = "time";

	// 以下是资源组编码宏定义
	/**
	 * @fieldName: RES_GRP_TYPE
	 * @fieldType: String
	 * @Description: 资源组类型 resGrpType
	 */
	public final static String RES_GRP_TYPE = "resGrpType";
	
	/**
	 * @fieldName: RES_CODE_TYPE
	 * @fieldType: String
	 * @Description: 资源编码类型 resCodeType
	 */
	public final static String RES_CODE_TYPE = "resCodeType";
	
	/**
	 * @fieldName: FUNC_MENU_TYPE
	 * @fieldType: String
	 * @Description: 功能菜单类型 funcMenuType
	 */
	public final static String FUNC_MENU_TYPE = "funcMenuType"; 
	
	/**
	 * @fieldName: PARAM_VALUE_TYPE
	 * @fieldType: String
	 * @Description: 参数值类型 paramValueType
	 */
	public final static String PARAM_VALUE_TYPE = "paramValueType"; 

	/**
	 * @fieldName: USER_TYPE
	 * @fieldType: String
	 * @Description: 用户类型 userType
	 */
	public final static String USER_TYPE = "userType"; 

	/**
	 * @fieldName: ROLE_TYPE
	 * @fieldType: String
	 * @Description:角色类型 roleType
	 */
	public final static String ROLE_TYPE = "roleType";
	
	/**
	 * @fieldName: ORGAN_TYPE
	 * @fieldType: String
	 * @Description: 机构类型 organType
	 */
	public final static String ORGAN_TYPE = "organType"; // 机构类型
	
	/**
	 * @fieldName: SPLIT_KEY
	 * @fieldType: String
	 * @Description: 分隔字符 ;
	 */
	public final static String SPLIT_KEY = ";";
	
	/**
	 * @fieldName: AES_KEY
	 * @fieldType: byte[]
	 * @Description: 加密Sino-bridge_@#*!
	 */
	public final static byte[] AES_KEY = "Sino-bridge_@#*!".getBytes();
	
	/**
	 * @fieldName: RESULT_SUCCESS
	 * @fieldType: String
	 * @Description: 成功结果 success
	 */
	public final static String RESULT_SUCCESS = "success";
	
	/**
	 * @fieldName: RESULT_SUCCESS
	 * @fieldType: String
	 * @Description: 失败结果 error
	 */
	public final static String RESULT_ERROR = "error";
	
	/**
	 * @fieldName: RESULT_OUTTIME
	 * @fieldType: String
	 * @Description: 超时结果 outtime
	 */
	public final static String RESULT_OUTTIME = "outtime";
	
	/**
	 * @fieldName: RESULT_ERROR_OUTTIME
	 * @fieldType: String
	 * @Description: 超时返回错误页面 errot/outtime
	 */
	public final static String RESULT_ERROR_OUTTIME = "error/outtime";
	
	/**
	 * @fieldName: RESULT_ALL_ERROR_OUTTIME
	 * @fieldType: String
	 * @Description: 超时返回错误页面 /errot/outtime.jsp
	 */
	public final static String RESULT_ALL_ERROR_OUTTIME = "/views/error/outtime.jsp";

	// 公共信息定义
	/**
	 * @fieldName: EXCEL_IMP_MDL_NAME
	 * @fieldType: String
	 * @Description: EXCEL导入数据模版名称 
	 */
	public final static String EXCEL_IMP_MDL_NAME = "EXCEL_IMP_MDL_NAME"; 

	/**
	 * @fieldName: EXCEL_IMP_URL
	 * @fieldType: String
	 * @Description: EXCEL导入数据提交链接
	 */
	public final static String EXCEL_IMP_URL = "EXCEL_IMP_URL"; 
	

	/**
	 * @fieldName: EXCEL_EXP_COL_NAMES
	 * @fieldType: String
	 * @Description: EXCEL导出属性名称序列
	 */
	public final static String EXCEL_EXP_COL_NAMES = "EXCEL_EXP_COL_NAMES"; 
	
	/**
	 * @fieldName: EXCEL_EXP_URL
	 * @fieldType: String
	 * @Description: EXCEL导出数据提交链接
	 */
	public final static String EXCEL_EXP_URL = "EXCEL_EXP_URL";
	

	// 通知命令
	/**
	 * @fieldName: CMD_PARAM_GROUP_CHANGE
	 * @fieldType: String
	 * @Description: 修改参数组 <cmd><name>paramGroupChange</name><groupCode>%s</groupCode></cmd>
	 */
	public final static String CMD_PARAM_GROUP_CHANGE = "<cmd><name>paramGroupChange</name><groupCode>%s</groupCode></cmd>";
	
	/**
	 * @fieldName: CMD_RES_GROUP_CHANGE
	 * @fieldType: String
	 * @Description: 资源组变更 <cmd><name>resGroupChange</name><groupCode>%s</groupCode></cmd>
	 */
	public final static String CMD_RES_GROUP_CHANGE = "<cmd><name>resGroupChange</name><groupCode>%s</groupCode></cmd>";
	
	/**
	 * @fieldName: DBNAME
	 * @fieldType: String
	 * @Description: sdndb
	 */
	public final static String DBNAME="acsdb";
	
	
	/**
	 * @fieldName: VTEP_IP
	 * @fieldType: String
	 * @Description: 117.0.0.2
	 */
	public final static String VTEP_IP="117.0.0.2";
	
	/**
	 * @fieldName: ORGAN_DEFAULT_TREECODE_LENGTH
	 * @fieldType: Integer
	 * @Description: 3 默认机构树长度
	 */
	public final static Integer ORGAN_DEFAULT_TREECODE_LENGTH = 3;

	/**
	 * @fieldName: LOGIN_URL
	 * @fieldType: String
	 * @Description: 登录url
	 */
	public static final String LOGIN_URL = "/login.jsp";
	
	public static final String TIMEOUT_URL = "/timeout.jsp";
	
	public static final String WEB_TIMEOUT_URL = "/webTimeOut.jsp";
	// 图片
	public final static String LOAD_DEV_IMG_URL = "/SinoACS/upload_devImg/"; // 本地图片地址 D:/image在tomcatrver.xml中创建虚拟路径
	public final static String LOAD_BG_IMG_URL = "/SinoACS/upload_bgImg/"; // 本地图片地址 D:/image在tomcatrver.xml中创建虚拟路径
}
