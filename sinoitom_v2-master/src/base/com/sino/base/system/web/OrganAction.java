package com.sino.base.system.web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;
import org.springside.modules.utils.security.Cryptos;

import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.Global;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.OrgPosition;
import com.sino.base.system.entity.ParamArea;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.base.system.service.ResItemService;
import com.sino.base.system.service.UserService;
import com.sino.cmdb.vendor.entity.ParamVendor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping(value = "/system/organ")
public class OrganAction {
    private String viewPath = "/system/organ";
    private String operator_viewPath = "/crm/operator";
    private String operatorUser_viewPath = "/crm/operatorUser";

    private static Logger logger = LoggerFactory.getLogger(OrganAction.class);

    @Autowired
    private OrganService organService;
    
    @Autowired
	private UserService userService;
    @Autowired
	private ResItemService resItemService;
//	@Autowired
//	private AdvAreaService advAreaService;

//	private ServletContext servletContext;
//
//	public void setServletContext(ServletContext servletContext) { //实现接口中的setServletContext方法
//		this.servletContext = servletContext;
//	}

    @RequestMapping(value = "/main.do")
    public String main(String menuId,ModelMap map) {
        logger.info("Enter main.do ...");
        OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
        if (userOrg == null) {
            return "error/outtime";
        }

        List<OrgOrganization> list = new ArrayList<OrgOrganization>();
        SysUser user = SystemUtils.getLoginUser();
        String loginName = user.getLoginName();
        if (loginName.equals("admin") || loginName.equals("devadmin")) {
            list = organService.getAllOrgan();
        } else {
            list = organService.findOrganByCode(userOrg.getTreeCode());
        }
        //List<OrgOrganization> list = organService.getAllOrgan();不用

//		String jsonStr = organService.getJsonTreeStr(list);
        String jsonStr = organService.getJsonListStr(list);

        map.put("jsonTreeData", jsonStr);
        WebFuncUtils.titleContent(menuId,map);
        return viewPath + "/main";
    }

    @RequestMapping(value = "/getData.do")
    public String getData(String treeCode, HttpServletResponse response) throws IOException {
        logger.info("Enter getData.do ...");
        if (StringUtils.isBlank(treeCode)) {
            OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
            if (userOrg == null) {
                return "error/outtime";
            }
            treeCode = userOrg.getTreeCode();
        }

        List<OrgOrganization> list = new ArrayList<OrgOrganization>();
        SysUser user = SystemUtils.getLoginUser();
        String loginName = user.getLoginName();
        if (loginName.equals("admin") || loginName.equals("devadmin")) {
            list = organService.getAllOrgan();
        } else {
            list = organService.findOrganByCode(treeCode);
        }
        //List<OrgOrganization> list = organService.getAllOrgan();不用

//		String jsonStr = organService.getJsonTreeStr(list);
        String jsonStr = organService.getJsonListStr(list);

        response.getWriter().print(jsonStr);
        return null;
    }


    @RequestMapping(value = "/getMinData.do")
    public String getMinData(String treeCode, HttpServletResponse response) throws IOException {
        logger.info("Enter getMinData.do ...");
        if (StringUtils.isBlank(treeCode)) {
            OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
            if (userOrg == null) {
                return "error/outtime";
            }
            treeCode = userOrg.getTreeCode();
        }

        List<OrgOrganization> list = new ArrayList<OrgOrganization>();
        SysUser user = SystemUtils.getLoginUser();
        String loginName = user.getLoginName();
        if (loginName.equals("admin") || loginName.equals("devadmin")) {
            list = organService.getAllOrgan();
        	//员工页面机构过滤      1<=orgType<=8
//            list = organService.getSubOrganName();
        } else {
//            list = organService.findOrganByCode(treeCode);
        	//员工页面机构过滤      1<=orgType<=8
        	list = organService.getSubOrganName(treeCode);
        }

        String jsonStr = organService.getJsonMinTreeStr(list);
        response.getWriter().print(jsonStr);
        return null;
    }
    
    //获得机构树 orgType！=9 树形
    @RequestMapping(value = "/getMinDataNotNine.do")
    public String getMinDataNotNine(String treeCode, HttpServletResponse response) throws IOException {
        logger.info("Enter getMinData.do ...");
        if (StringUtils.isBlank(treeCode)) {
            OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
            if (userOrg == null) {
                return "error/outtime";
            }
            treeCode = userOrg.getTreeCode();
        }

        List<OrgOrganization> list = new ArrayList<OrgOrganization>();
        	//员工页面机构过滤      orgType!=9
        list = organService.getMinDataNotNine();

        String jsonStr = organService.getJsonMinTreeStr(list);
        response.getWriter().print(jsonStr);
        return null;
    }
    //获取部门
    @RequestMapping(value = "/getDptData.do")
	public String getDptData(String orgId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<OrgOrganization> list = organService.getSubOrgan(orgId);
		String jsonStr = organService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}  

    @RequestMapping(value = "/getSubOrgIds.do")
    public String getSubOrgIds(String orgId, HttpServletResponse response) throws IOException {
        logger.info("Enter getSubOrgIds.do ...");
        if (StringUtils.isBlank(orgId)) {
            OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
            if (userOrg == null) {
                return "error/outtime";
            }
            orgId = userOrg.getOrgId();
        }
        List<String> list = organService.getSubOrgIds(orgId);
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        if (list != null && !list.isEmpty()) {
            for (int i = 0, n = list.size(); i < n; i++) {
                sb.append(list.get(i));
                if (i != n - 1)
                    sb.append(",");
            }
        }
        jo.put("orgIds", sb.toString());
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value = "/getParOrgData.do")
    public String getParOrgData(String orgId, HttpServletResponse response) throws IOException {
        logger.info("Enter getParOrgData.do ...");

        List<OrgOrganization> list = new ArrayList<OrgOrganization>();
        System.out.println("get the orgId::>>" + orgId);
        if (orgId.equals("-1")) {
            list = organService.getAllOrgan();
        } else {
            String treeCode = "";
            if (StringUtils.isBlank(orgId)) {
                OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
                if (userOrg == null) {
                    return "error/outtime";
                }
                OrgOrganization parOrg = organService.getParOrg(userOrg.getOrgId());
                if (parOrg != null) {
                    treeCode = parOrg.getTreeCode();
                } else {
                    treeCode = userOrg.getTreeCode();
                }
            } else {
                OrgOrganization parOrg = organService.getParOrg(orgId);
                if (parOrg != null) {
                    treeCode = parOrg.getTreeCode();
                    if (StringUtil.isNullString(treeCode)) {
                        OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
                        treeCode = userOrg.getTreeCode();
                        System.out.println("获取到的treeCode3是：》" + treeCode);
                    }
                } else {
                    OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
                    treeCode = userOrg.getTreeCode();
                    System.out.println("获取到的treeCode2是：》" + treeCode);
                }
            }
            list = organService.findOrganByCode(treeCode);
        }
        String jsonStr = organService.getJsonMinTreeStr(list);
        System.out.println("get the jsonStr::>>" + jsonStr);
        response.getWriter().print(jsonStr);
        return null;
    }

    @RequestMapping(value = "/search.do")
    public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
        logger.info("Enter search.do ...");
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
        List<OrgOrganization> list = organService.searchOrgan(filters);
        String jsonStr = organService.getJsonListStr(list);
        response.getWriter().print(jsonStr);
        return null;
    }

    @RequestMapping(value = "/add.do")
    public String add(String parentId, ModelMap map, String treeCode) {
        logger.info("Enter add.do ...");
        map.put("action", "add");
        OrgOrganization parentOrgan = organService.getOrgan(parentId);
        map.put("parentOrgan", parentOrgan);
        OrgOrganization organ = new OrgOrganization();
        organ.setParentId(parentId);
        organ.setState(Global.STATE_ON);
        map.put("organ", organ);
        map.put("treeCode", treeCode);
        return viewPath + "/add";
    }

    @RequestMapping(value = "/edit.do")
    public String edit(String id, ModelMap map, String treeCode) {
        logger.info("Enter edit.do ...");
        map.put("action", "edit");
        OrgOrganization organ = organService.getOrgan(id);
        OrgOrganization parOrgan = organService.getParOrg(id);
        map.put("organ", organ);
        OrgOrganization parentOrgan = organService.getOrgan(organ.getParentId());
        map.put("parentOrgan", parentOrgan);
        map.put("treeCode", parOrgan.getTreeCode());
        return viewPath + "/add";
    }
  //详情
  		@RequestMapping(value = "/view.do")
  		public String view(String id, ModelMap map) {
  			logger.info( "Enter detail ..." );
  			map.put("action", "detail");
  			OrgOrganization organ = organService.getOrgan(id);
  			String  resName=resItemService.findResContent("organType", String.valueOf(organ.getOrgType()));
  			map.put("organ", organ);
  			map.put("resName", resName);
  			return viewPath+"/view";
  		}
    @RequestMapping(value = "/save.do")
    public String save(String action, String id, OrgOrganization organ, ModelMap map) {
        logger.info("Enter save.do ...");

        boolean validate = true;
        SysUser curUser = SystemUtils.getLoginUser();
        if (curUser == null) {
            return "error/outtime";
        } else if (StringUtils.isBlank(organ.getOrgName())) {
            validate = false;
            map.put("message", "机构名称不能为空！");
        }


        if (validate) {
            String operator = curUser.getLoginName();
            Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
            organ.setModifier(operator);
            organ.setModifyTime(now);
            String jsonStr = "";
            if ("add".equals(action)) {
                organ.setCreator(operator);
                organ.setCreateTime(now);
                organ.setOrgClass(0);//服务商
                organService.addOrgan(organ);
                map.put("organ", organ);
                jsonStr = organService.getJsonObjStr(organ);
            } else {
                OrgOrganization editOrgan = organService.getOrgan(id);
                if (editOrgan == null) {
                    map.put("result", "error");
                    map.put("message", "该机构已经被删除！");
                    return viewPath + "/edit";
                }
                BeanUtils.copyProperties(organ, editOrgan, new String[]{"orgPositions", "sysUsers"});
                organService.saveOrgan(editOrgan);
                map.put("organ", editOrgan);
                jsonStr = organService.getJsonObjStr(editOrgan);
            }
            map.put("saveData", jsonStr);
            map.put("result", "success");
        } else {
            map.put("organ", organ);
            map.put("result", "error");
        }

        map.put("action", action);
        return viewPath + "/edit";
    }

    @RequestMapping(value = "/moveGo.do")
    public String moveGo(String id, ModelMap map) throws IOException {
        logger.info("Enter moveGo.do ...");
        OrgOrganization organ = organService.getOrgan(id);
        map.put("organ", organ);
        List<OrgOrganization> list = organService.findMoveTrees(organ);
        String jsonStr = organService.getJsonTreeStr(list);
        map.put("jsonTreeData", jsonStr);
        return viewPath + "/move";
    }

    @RequestMapping(value = "/move.do", method = RequestMethod.GET)
    public String move(String moveId, String targetId, int moveType, HttpServletResponse response) throws IOException {
        logger.info("Enter move.do ...");
        String result = "fail";
        String jsonStr = "";
        if (organService.moveOrgan(moveId, targetId, moveType)) {
            List<OrgOrganization> list = new ArrayList<OrgOrganization>();
            SysUser user = SystemUtils.getLoginUser();
            String loginName = user.getLoginName();
            if (loginName.equals("admin") || loginName.equals("devadmin")) {
                list = organService.getAllOrgan();
            } else {
                OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
                String treeCode = userOrg.getTreeCode();
                list = organService.findOrganByCode(treeCode);
            }
            jsonStr = organService.getJsonTreeStr(list);
            result = "success";
        }
        JSONObject jo = new JSONObject();
        jo.put("result", result);
        jo.put("jsonlist", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value = "/delete.do")
    public String delete(String id, HttpServletResponse response) throws IOException {
        logger.info("Enter delete.do ...");
        OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
        if (id.equals(userOrg.getOrgId())) {
            response.getWriter().printf(JsonUtils.JSON_RESULT_FALSE_MSGMDL, "不能删除当前登录用户所属机构！");
            return null;
        }

        organService.deleteOrgan(id);
        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }

    @RequestMapping(value = "/deleteSubOrg.do")
    public String deleteSubOrg(String id, HttpServletResponse response) throws IOException {
        logger.info("Enter deleteSubOrg.do ...");
//		OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
//		if( id.equals(userOrg.getOrgId()) ){
//			response.getWriter().printf(JsonUtils.JSON_RESULT_FALSE_MSGMDL, "不能删除当前登录用户所属机构！");
//			return null;
//		}
//		
        organService.deleteSubOrg(id);
        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }


    @RequestMapping(value = "/importGo.do")
    public String importGo(ModelMap map) throws IOException {
        logger.info("Enter importGo.do ...");
        return viewPath + "/import";
    }


    @RequestMapping(value = "/exportExcel.do")
    public String exportExcel(ModelMap map, HttpServletResponse response) throws IOException {
        logger.info("Enter exportExcel.do ...");

        OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
        if (userOrg == null) {
            return "error/outtime";
        }
        String treeCode = userOrg.getTreeCode();
        List<OrgOrganization> list = new ArrayList<OrgOrganization>();
        SysUser user = SystemUtils.getLoginUser();
        String loginName = user.getLoginName();
        if (loginName.equals("admin") || loginName.equals("devadmin")) {
            list = organService.getAllOrgan();
        } else {
            list = organService.findOrganByCode(treeCode);
        }
        String[] headers = {"上级机构名称", "机构名称", "机构编码", "机构类型", "机构电话", "机构地址", "邮政编码", "机构说明"};//导出字段名称
        String attr = "parentId@OrgOrganization@orgId@orgName,orgName,orgCode,orgType@organType,phone,address,zipCode,description";//导出映射字段
        int[] exlColWidths = new int[]{4800, 4800, 4800, 4800, 4800, 4800, 4800, 6800};//excel 列宽132*36≈4800相当于132px
        String fileName = "机构信息列表_" + DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis())) + ".xls";//excel文件名
        try {
            response.setContentType("octets/stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
            OutputStream out = response.getOutputStream();
            ExpExcelUtil.expExcel("机构信息列表", headers, exlColWidths, list, attr, out);
            out.close();
        } catch (Exception e) {
            System.out.println("导出excel出错。。。");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/importExcel.do")
    public String importExcel(MultipartFile impFile, ModelMap map) throws IOException {
        logger.info("Enter importExcel.do ...");
        if (impFile.getSize() <= 0) {
            map.put("result", "error");
            map.put("message", "上传文件内容为空！");
        } else {
            if (organService.importExcel(impFile.getInputStream())) {
                map.put("result", "success");
            } else {
                map.put("result", "error");
            }
            map.put("message", organService.getLastMassage());
        }
        return viewPath + "/import";
    }

    @RequestMapping(value = "/checkOrgan.do")
    public String checkOrgan(String organName, String treeCode, HttpServletResponse response) throws IOException {
        logger.info("Enter checkOrgan.do...");
        organName = new String(organName.getBytes("iso-8859-1"), "utf-8");
        List<OrgOrganization> organs = organService.checkOrgan(organName, treeCode);
        JSONObject jo = new JSONObject();
        if (!organs.isEmpty()) {
            jo.put("organs", organs.get(0).getOrgName());
        } else {
            jo.put("organs", "");
        }

        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value = "/getSubOrg.do")
    public String getSubOrg(String treeCode, HttpServletResponse response) throws IOException {
        logger.info("Enter getSubOrg.do ...");
        SysUser curUser = SystemUtils.getLoginUser();
        if (StringUtils.isBlank(treeCode)) {
            if (curUser == null) {
                return null;
            }

            if (SystemUtils.isSuperUser(curUser)) {
                treeCode = "";
            } else {
                OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
                if (userOrg == null) {
                    return null;
                }
                treeCode = userOrg.getTreeCode();
            }
        }
        List<OrgOrganization> list = new ArrayList<OrgOrganization>();
        if (curUser.getLoginName().equals("admin")) {
            list = organService.getAllOrgan();
        } else {
            list = organService.findSubOrganByCode(treeCode);
        }
        String jsonStr = organService.getJsonMinTreeStr(list);
        response.getWriter().print(jsonStr);
        return null;
    }

    @RequestMapping(value = "/getAllOrgan.do")
    public String getAllOrgan(HttpServletResponse response) throws IOException {
        List<OrgOrganization> list = organService.getAllOrgan();

        String jsonStr = organService.getJsonListStr(list);
        response.getWriter().print(jsonStr);

        return null;
    }


    @RequestMapping(value = "/getParentOrgan.do")
    public String gerParentOrgan(HttpServletResponse response) throws IOException {

        List<OrgOrganization> list = organService.getParentOrgan();

        String jsonStr = organService.getJsonListStr(list);
        response.getWriter().print(jsonStr);

        return null;
    }

    //获得机构树orgType ！= 9 ，非树形
    @RequestMapping(value = "/getOrganNameNotNine.do")
    public String getOrganNameNotNine(HttpServletResponse response) throws IOException {

        List<OrgOrganization> orglist = organService.getOrganNameNotNine();
        
        JSONObject jo=new JSONObject();
        String jsonStr = organService.getJsonListStr(orglist);
        
		jo.put("orglist", jsonStr);
	    response.getWriter().print(jo.toString());
		return null;
		
		
    }
    

    
    @RequestMapping(value = "/getOrganName.do")
    public String getOrganName(HttpServletResponse response) throws IOException {

        List<OrgOrganization> list = organService.getOrganName();

        String jsonStr = organService.getJsonListStr(list);
        response.getWriter().print(jsonStr);

        return null;
    }

    @RequestMapping(value = "/getSubOrgan.do")
    public String getSubOrgan(String parentId, HttpServletResponse response) throws IOException {

        List<OrgOrganization> list = organService.getSubOrgan(parentId);

        String jsonStr = organService.getJsonListStr(list);

        response.getWriter().print(jsonStr);

        return null;
    }

    /**
     * @param OrgId
     * @param map
     * @throws Exception
     * getOrganByOrgId
     * @Description: 根据orgId获取所有的数据中心
     * @return: String
     */
    @RequestMapping("/getOrganByOrgId.do")
    public String getOrganByOrgId(String orgId, ModelMap map, HttpServletResponse response) throws Exception {
        List<OrgOrganization> organList = new ArrayList<OrgOrganization>();

        //根据orgID获得当前organizition
        OrgOrganization organ = organService.getOrgan(orgId);
//		if(organ != null){
//			if(organ.getTreeCode().length() == Global.ORGAN_DEFAULT_TREECODE_LENGTH){
        organList = organService.getAllOrgByOrgType(2);
//			}else{
//				organList.add(organ);
//			}
//		}
        String jsonStr = organService.getJsonListStr(organList);
        map.put("orgId", orgId);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonStr);
        return null;
    }


    //根据登陆人主机构ID获取 省、市、县
    @RequestMapping(value = "/getArea.do")
    public String getArea(int level, int parentId, HttpServletResponse response, ModelMap map) throws IOException {
        logger.info("Enter getArea.do ...");
        OrgOrganization organ = SystemUtil.getLoginUserMainOrg();
        String provCode = organ.getProvCode() + "";
        String cityCode = organ.getCityCode() + "";
        String countyCode = organ.getCountyCode() + "";
//			List<ParamArea> arealist =advAreaService.getArea(level, parentId);
        JSONObject jo = new JSONObject();
//			JSONArray jsArealist= JSONArray.fromObject(arealist);
        jo.put("provCode", provCode);
        jo.put("cityCode", cityCode);
        jo.put("countyCode", countyCode);
//			jo.put("Areas", jsArealist);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }


    //检查上级机构+机构名称唯一性
    @RequestMapping(value = "/checkOrgName.do")
    @ResponseBody
    public String checkOrgannName(String parentId, String orgName) throws IOException {
//					upper_OrgID = new String(fieldValue.getBytes("iso-8859-1"),"UTF-8");

        orgName = new String(orgName.getBytes("iso-8859-1"), "UTF-8");

        boolean flag = organService.checkOrganName(parentId, orgName);
        JSONObject jo = new JSONObject();
        if (flag) {
            jo.put("result", "true");
        }
        return jo.toString();
    }

    @RequestMapping(value = "/getCurrentLoginOrg.do")
    public void getCurrentLoginOrg(HttpServletResponse response){
        try {
            OrgOrganization org = SystemUtils.getLoginUserMainOrg();
            String jsonObjStr = organService.getJsonObjStr(org);
            JSONObject jo = new JSONObject();
            jo.put("currentOrg",jsonObjStr);
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    跳转到运营商列表页面
    @RequestMapping(value = "/operator/main.do")
    public String getAllOperator(String menuId,ModelMap map){
    	logger.info("Enter OrganAction getAllOperator.do ...");
		WebFuncUtils.titleContent(menuId,map);
		return operator_viewPath+"/main";
    }
//    获取运营商列表信息
    @RequestMapping(value = "/operator/getOperatorData.do")
    public String getOperatorData(String treeCode, HttpServletResponse response) throws IOException{
    	logger.info("Enter OrganAction getOperatorData.do ...");
    	
    	List<OrgOrganization> list = null;
	    if (StringUtils.isBlank(treeCode)) {
	        OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
	        if (userOrg == null) {
	            return "error/outtime";
	        }
	        
	        String userOrgTreeCode = userOrg.getTreeCode();
	        Integer orgClass = userOrg.getOrgClass();
	        
	        if(orgClass!=null && orgClass==1){
//	        	获取当前用户（运营商）所属机构及下属机构集合
	        	list = organService.getCurrentUserAllOperator(userOrgTreeCode);
	        }else{
//	        	获取所有的运营商机构
	        	list = organService.getAllOperator();
	        }

	    }else{
//	    	获取子机构
	    	list = organService.findOrganByCode(treeCode);
	    }
	   
	    String jsonStr = organService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;
    }
    
//	跳转到添加页面
	@RequestMapping(value = "/operator/addOperator.do")
	public String addOperator(ModelMap map) {
		logger.info( "Enter OrganAction addOperator.do ..." );
		OrgOrganization organization = new OrgOrganization();
		map.put("action", "add");
		map.put("organization", organization);
		return operator_viewPath+"/edit";
	}
	
//	保存
	@RequestMapping(value = "/operator/saveOperator.do")
	public String saveOperator(String action, String id, OrgOrganization orgOrganization, ModelMap map) {
		logger.info( "Enter OrganAction saveOperator.do ..." );
		
		try{
			String jsonStr = "";
			
//			获取当前登陆的用户名
			SysUser loginUser=SystemUtils.getLoginUser();
			String loginName = "";
			if(loginUser!=null){
				loginName = loginUser.getLoginName();
			}
			
			if( "add".equals(action)){
				orgOrganization.setOrgId(Identities.uuid());
				orgOrganization.setState(1);//起草状态
				orgOrganization.setOrgClass(1);

				if(loginName!=null){
					orgOrganization.setCreator(loginName);
				}else{
//					这里先写成null，也可以提示您没有登陆，不能添加，那就return一个字符串
					orgOrganization.setCreator("");
				}
				
//				创建时间为当前时间
				Timestamp createTime = new Timestamp(new Date().getTime());
				orgOrganization.setCreateTime(createTime);
				orgOrganization.setModifyTime(createTime);
				organService.addOperatorOrgan(orgOrganization);
			}else if(action.equals("addBranch")){
				orgOrganization.setOrgId(Identities.uuid());
				orgOrganization.setState(1);//起草状态
				orgOrganization.setOrgClass(1);//运营商

				if(loginName!=null){
					orgOrganization.setCreator(loginName);
				}else{
//					这里先写成null，也可以提示您没有登陆，不能添加，那就return一个字符串
					orgOrganization.setCreator("");
				}
				
//				创建时间为当前时间
				Timestamp createTime = new Timestamp(new Date().getTime());
				orgOrganization.setCreateTime(createTime);
				orgOrganization.setModifyTime(createTime);
				organService.addOrgan(orgOrganization);
			}
			else{
				OrgOrganization org = organService.getOrgan(id);

				if( org == null ){
					map.put("result", "error");								
					return viewPath+"/edit";
				}
//				修改人是当前用户
				if(loginName!=null){
					org.setModifier(loginName);
				}else{
//					这里先写成null，也可以提示您没有登陆，不能添加，那就return一个字符串
					org.setModifier("");
				}
				
//				修改时间为当前时间
				Timestamp modifyTime = new Timestamp(new Date().getTime());
				org.setModifyTime(modifyTime);
				
				BeanUtils.copyProperties(orgOrganization, org, new String[]{ "createTime" ,"creator"});
				organService.saveOrgan(org);					
				map.put("orgOrganization", org);
				jsonStr = organService.getJsonObjStr(org);
				
			}
			
			map.put("result", "success");
			map.put("saveData", jsonStr);
			map.put("action", action);
		}
		catch (BeansException e) {
			logger.info("Enter OrganAction saveOperator.do...异常..." + e.getMessage());
			map.put("result", "fail");
			e.printStackTrace();
		}
		return operator_viewPath + "/edit";
		 
	}
	
	//跳转到添加分支页面 
	@RequestMapping(value = "/operator/addOperatorBranch.do")
	public String addOperatorBranch(String id,ModelMap map) {
		logger.info( "Enter OrganAction addOperatorBranch.do ..." );
//		获取父节点对象
		OrgOrganization orgOrganization = organService.getOrgan(id);
		if(orgOrganization!=null){
			map.put("parentId",orgOrganization.getOrgId());
			map.put("pName", orgOrganization.getOrgName());
			map.put("action", "addBranch");
			return operator_viewPath+"/addBranch";
		}else{
			return operator_viewPath+"/main";
		}
	}
//	跳转到编辑
	@RequestMapping(value = "/operator/editOperator.do")
	public String editOperator(String id, ModelMap map) {
		logger.info( "Enter OrganAction editOperator.do ..." );
		map.put("action", "edit");
		OrgOrganization orgOrganization = organService.getOrgan(id);
		map.put("organization", orgOrganization);
//		map.put("mspUserId", customer.getMspUserId());
		map.put("action", "edit");
		return operator_viewPath+"/edit";
	}

//	详情
	@RequestMapping(value = "/operator/operatorDetail.do")
	public String operatorDetail(String id, ModelMap map) {
		logger.info( "Enter OrganAction operatorDetail.do ..." );
		OrgOrganization orgOrganization = organService.getOrgan(id);
		String jsonObjStr = organService.getJsonObjStr(orgOrganization);
		map.put("jsonObjStr", jsonObjStr);
		map.put("organization", orgOrganization);
		return operator_viewPath+"/detail";
	}
	
//	获取所有的运营商
	@RequestMapping(value = "/operator/getAllOperatorList.do")
	@ResponseBody
	public String getAllOperatorList(String id, ModelMap map) {
		logger.info( "Enter OrganAction getAllOperatorList.do ..." );
		try {
			List<OrgOrganization> list = organService.getAllOperator();
			String jsonListStr = organService.getJsonListStr(list);
			JSONObject jo = new JSONObject();
			jo.put("orgList",jsonListStr);
			return jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//	跳转到添加运营商用户页面
	@RequestMapping(value = "/operator/toAddOperatorUser.do")
	public String toAddOperatorUser(String orgId, ModelMap map) {
		logger.info( "Enter OrganAction toAddOperatorUser.do ..." );
		SysUser sysUser = new SysUser();
		sysUser.setMainOrgId(orgId);
		map.put("action", "add");
		map.put("user", sysUser);
		return operator_viewPath+"/addOperatorUser";
	}
//	添加运营商用户
	@RequestMapping(value = "/operator/saveOperatorUser.do")
	public String saveOperatorUser(String action, String id, boolean isEditPasswd, SysUser user, ModelMap map) {
		logger.info( "Enter OrganAction saveOperatorUser.do ..." );
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( StringUtils.isBlank(user.getLoginName()) ){
			validate = false;
			map.put("message", "用户账号不能为空！");
		}
		else if( !userService.isLoginNameUnique(id, user.getLoginName()) ){
			validate = false;
			map.put("message", "用户账号已经存在！");
		}
		else if( ("add".equals(action) && StringUtils.isBlank(user.getLoginPasswd())) 
			|| ("edit".equals(action) && isEditPasswd &&  StringUtils.isBlank(user.getLoginPasswd())) ){
			validate = false;
			map.put("message", "用户密码不能为空！");
		}
		
		if( validate ){
			String operator = curUser.getLoginName();
			Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			user.setModifier(operator);
			user.setModifyTime(now);
			if( "add".equals(action)){
				user.setLoginPasswd(Cryptos.desEncryptToHex(user.getLoginPasswd(),Global.AES_KEY));
				user.setCreator(operator);
				user.setCreatorType(curUser.getUserType());
				user.setCreateTime(now);
				user.setLastLoginTime(now);
				userService.addUser(user);		
				map.put("user", user);
				map.put("result", "success");
			}else{
				map.put("user", user);
				map.put("result", "error");			
			}
		}

		map.put("action", action);
		return operator_viewPath+"/addOperatorUser";
	}
//	运营商用户列表
	@RequestMapping(value = "/operator/user/main.do")
	public String getAllOperatorUserList(String menuId,ModelMap map) {
		logger.info( "Enter OrganAction getAllOperatorUserList.do ..." );
		List<SysUser> list = null;
//		获取当前登陆用户所在机构
		OrgOrganization loginUserMainOrg = SystemUtils.getLoginUserMainOrg();
		Integer orgClass = loginUserMainOrg.getOrgClass();
		String orgId = loginUserMainOrg.getOrgId();
		if(orgClass!=null){
			if(orgClass==1){//1：运营商：查看自己以及所有子机构的
				List<String> childOrgIds = organService.getIdsByOrgId(orgId);
				
				list = userService.getByOrgIds(childOrgIds);
			}else if(orgClass==0){//0：MSP服务商:可以查看所有的运营商的客户
				list = userService.getAllOperatorUser();
			}
		}
		
		String jsonStr = userService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		
		return operatorUser_viewPath+"/main";
	}
	
	//  获取所有的运营商，但是剔除掉部门
	  @RequestMapping(value = "getAllOperatorExceptDepart.do")
	  @ResponseBody
	  public String getAllOperatorExceptDepart(){
	  	List<OrgOrganization> list = organService.getAllExceptDepart(0);
	  	String jsonListStr = organService.getJsonMinTreeStr(list);
	  	return jsonListStr;
	  }
    
}
