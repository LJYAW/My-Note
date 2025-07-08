package com.sino.base.system.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.Global;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.ParamTree;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
/**
 * 
 * @author Mr.LP
 * @date 2017-5-16上午11:34:32
 * @className OrgDataAction
 *
 * @Description 数据中心信息
 *
 */
@Controller
@RequestMapping(value = "/datacenter")
public class OrgDataAction {
	private String viewPath = "/datacenter";
	private static Logger logger = LoggerFactory.getLogger(OrganAction.class);
	
	@Autowired
	private OrganService organService;

	/**
	 * 数据中心列表展示
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map){
		logger.info("Enter OrgDataAction main ...");
		OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
		if( userOrg == null ){
			return "error/outtime";
		}

		List<OrgOrganization> list =new ArrayList<OrgOrganization>();
		list= organService.getDccOrgan();
		String jsonStr = organService.getJsonTreeStr(list);
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/main";
	}

	/**
	 * 添加
	 * @param parentId
	 * @param map
	 * @param treeCode
	 * @return
	 */
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map,String treeCode) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		//获取当前登录用户所属的机构信息
		OrgOrganization loginUserMainOrg = SystemUtils.getLoginUserMainOrg();
		map.put("loginUserMainOrg", loginUserMainOrg);
		OrgOrganization organ = new OrgOrganization();
		organ.setParentId(loginUserMainOrg.getOrgId());
		organ.setState(Global.STATE_ON);
		map.put("organ", organ);
		map.put("treeCode", treeCode);
		return viewPath+"/add";
	}
	
	
	/**
	 * checkOrgName
	 * @Description: 框架ajax检查名称是否为空
	 * @param fieldValue
	 * @param fieldId
	 * @param response
	 * @throws IOException
	 * @return: String
	 */
	@RequestMapping(value="/checkOrgName.do")
	public String checkOrgName(String fieldValue,String fieldId, HttpServletResponse response) throws IOException{
		logger.info("Enter checkOrgName.do...");
		
		boolean flag = organService.checkOrgName(fieldValue);
		String message = "";
		if(flag){
			message = "[\""+fieldId + "\",false]";
		}else{
			message = "[\""+fieldId + "\",true]";
		}
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(message);
		return null;
	}
	
	/**
	 * 检查机构编码是否重复（未用）
	 * @param orgCode
	 * @param treeCode
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/checkOrgCode.do")
	public String checkOrgCode(String orgCode,String treeCode,HttpServletResponse response) throws IOException{
		logger.info("Enter checkOrgCode.do...");
		orgCode = new String(orgCode.getBytes("iso-8859-1"),"utf-8");
		List<OrgOrganization> organs=organService.checkOrgCode(orgCode, treeCode);
		JSONObject jo=new JSONObject();
		if(!organs.isEmpty()){
			jo.put("organs", organs.get(0).getOrgCode());
		}else{
			jo.put("organs", "");
		}
		
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	/**
	 * 添加、修改的保存验证
	 * @param action
	 * @param id
	 * @param organ
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/save.do")
	public String save(String action, String id, OrgOrganization organ, ModelMap map) {
		logger.info( "Enter save.do ..." );

		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			return "error/outtime";
		}
		else if( StringUtils.isBlank(organ.getOrgName()) ){
			validate = false;
			map.put("message", "机构名称不能为空！");
		}
		
		if( validate ){
			String operator = curUser.getLoginName();
			Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			organ.setModifier(operator);
			organ.setModifyTime(now);
			String jsonStr = "";
			if( "add".equals(action)){
				organ.setCreator(operator);
				organ.setCreateTime(now);
				organService.addOrgan(organ);
				map.put("organ", organ);
				jsonStr = organService.getJsonObjStr(organ);
			}
			else{
				try {
					if(StringUtils.isNotBlank(id)){
						OrgOrganization editOrgan = organService.getOrgan(id);
						if( editOrgan == null ){
							map.put("result", "error");			
							map.put("message", "该机构已经被删除！");
							return viewPath+"/edit";
						}
						BeanUtils.copyProperties(organ, editOrgan, new String[]{"orgPositions","sysUsers"});
						organService.saveOrgan(editOrgan);
						map.put("organ", editOrgan);
						jsonStr = organService.getJsonObjStr(editOrgan);
					}
				} catch (Exception e) {
					logger.info( "Enter save.do ...异常"+e.getMessage() );
					e.printStackTrace();
				}
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("organ", organ);
			map.put("result", "error");			
		}
		
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	/**
	 * 修改
	 * @param id
	 * @param map
	 * @param treeCode
	 * @return
	 */
		@RequestMapping(value = "/edit.do")
		public String edit(String id, ModelMap map,String treeCode) {
			logger.info( "Enter edit.do ..." );
			try {
				map.put("action", "edit");
				if(StringUtils.isNotBlank(id)){
					OrgOrganization organ = organService.getOrgan(id);
					OrgOrganization parOrgan = organService.getParOrg(id);
					map.put("organ", organ);
					OrgOrganization parentOrgan = organService.getOrgan(organ.getParentId());
					map.put("parentOrgan", parentOrgan);
					map.put("treeCode", parOrgan.getTreeCode());
					return viewPath+"/edit";
				}
			} catch (Exception e) {
				logger.info( "Enter edit.do ...异常"+e.getMessage() );
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * 详情
		 * @param id
		 * @param map
		 * @param treeCode
		 * @return
		 */
			@RequestMapping(value = "/detail.do")
			public String detail(String id, ModelMap map,String treeCode) {
				logger.info( "Enter detail.do ..." );
				try {
					if(StringUtils.isNotBlank(id)){
						OrgOrganization organ = organService.getOrgan(id);
						map.put("organ", organ);
						OrgOrganization parOrgan = organService.getParOrg(id);
						OrgOrganization parentOrgan = organService.getOrgan(organ.getParentId());
						map.put("action", "detail");
						map.put("parentOrgan", parentOrgan);
						map.put("treeCode", parOrgan.getTreeCode());
						return viewPath+"/detail";
					}
				} catch (Exception e) {
					logger.info( "Enter detail.do ...异常"+e.getMessage() );
					e.printStackTrace();
				}
				return null;
			}
		
		
		/**
		 * 查询
		 * @param resquest
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/search.do")
		public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
			logger.info( "Enter search.do ..." );
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
			String str = filters.get(0).getMatchValue().toString();
			if(str.equals(String.valueOf(-1))){	//此时为“请选择”，查询全部并展示
				List<OrgOrganization> list =new ArrayList<OrgOrganization>();
				list= organService.getDccOrgan();
				String jsonStr = organService.getJsonTreeStr(list);
				response.getWriter().print(jsonStr);
			}else{
				//此时根据所选机构名称，查询对应的机构信息并展示
				List<OrgOrganization> list = organService.searchOrgan(filters);
				String jsonStr = organService.getJsonListStr(list);
				response.getWriter().print(jsonStr);
			}
			
		    return null;         
		}
		
		//refresh
		@RequestMapping(value = "/getData.do")
		public String getData(String treeCode, HttpServletResponse response) throws IOException{     
			logger.info( "Enter getData.do ..." );
			if( StringUtils.isBlank(treeCode) ){
				OrgOrganization userOrg = SystemUtils.getLoginUserMainOrg();
				if( userOrg == null ){
					return "error/outtime";
				}
				treeCode = userOrg.getTreeCode();	
			}
			
			List<OrgOrganization> list = new ArrayList<OrgOrganization>();
			list= organService.getDccOrgan();
			String jsonStr = organService.getJsonTreeStr(list);
		    response.getWriter().print(jsonStr);
		    return null;         
		}

		/**
		 * 获取机构的树形菜单
		 * @param map
		 * @return
		 */
		@RequestMapping(value = "/mainTab.do")
		public String mainTab(ModelMap map) {
			logger.info( "Enter mainTab.do ..." );
			//获得第一级的 id
//			List<String> OrgNames=organService.getParentOrg();
			List<OrgOrganization> orgOrgan = organService.getParentOrgan();
			List<ParamTree> treeList=new ArrayList<ParamTree>();
			
			for(OrgOrganization org : orgOrgan){
				ParamTree tree1=new ParamTree();
//				if(!"-1".equals(org)){
					//第一级的 赋值
					tree1.setPid("0");//上一级的id
					tree1.setId(org.getOrgId()+"_1");//本级的id
					tree1.setText(org.getOrgName());//显示出来的描述
					treeList.add(tree1);//添加到树里
//					System.out.println(tree1.getId().split("_")[0]);
					//获得第二级的数据
					List<OrgOrganization> subOrgans=organService.getChildOrg(org.getOrgId());
					
					for(OrgOrganization subOrg:subOrgans){
						ParamTree tree2=new ParamTree();
//						if(!"-1".equals(org.getOrgName().toString())){
							//第二级的 赋值
							tree2.setPid(org.getOrgId()+"_1");//上一极的id
							tree2.setId(subOrg.getOrgId()+"_2");//本级的id
							tree2.setText(subOrg.getOrgName());
							treeList.add(tree2);
//							System.out.println(tree2.getId().split("_")[0]);
//						}
					}
//				}
			}
//			System.out.println(treeList.get(0).getId()+"********"+treeList.get(1).getId()+"*********");
			JSONArray json = JSONArray.fromObject(treeList);
			String treeData = json.toString();
			map.put("treeData", treeData);
			map.put("queryType", "org");
			return "/dncm/switch/mainTab";
		}
		
		/**
		 * 获取机构名称  orgType<3
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/getOrganName.do")
		public String getOrganName(HttpServletResponse response) throws IOException{
			
			List<OrgOrganization> list=organService.getOrgName();
			
			String jsonStr=organService.getJsonListStr(list);
			response.getWriter().print(jsonStr);
			
			return null;
		}

}
