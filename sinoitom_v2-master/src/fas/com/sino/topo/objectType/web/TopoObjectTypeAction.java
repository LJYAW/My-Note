package com.sino.topo.objectType.web;


import com.sino.base.common.listener.LoadPropertiesListener;
import com.sino.base.common.util.*;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.fas.res.resource.resourceType.entity.ParamResItem;
import com.sino.topo.objectType.entity.TopoObjectType;
import com.sino.topo.objectType.service.TopoObjectTypeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(value="/topo/objType")
@Controller
public class TopoObjectTypeAction {


	private static String viewPath = "/topo/topoObjType";

	private static Logger logger = LoggerFactory
			.getLogger(TopoObjectTypeAction.class);

	@Autowired
	private TopoObjectTypeService topoObjectTypeService;
	@Autowired
	private ResItemService resItemService;

	/**
	 * 
	 * 进入主页面
	 */
	@RequestMapping(value = "/main.do")
	public String index() {

		return viewPath + "/main";
	}

	/**
	 * 
	 * 查询所有
	 */
	@RequestMapping(value = "/getAllData.do")
	public String getAllData(HttpServletResponse response) throws IOException {

		logger.info("Enter getData.do ...");

		List<TopoObjectType> list = topoObjectTypeService
				.getAllTopoObjectType();

		String jsonStr = topoObjectTypeService
				.getJsonTopoObjectTypeListStr(list);
		JSONObject jo = new JSONObject();
		jo.put("jsonData", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}

	/**
	 * 
	 * 去添加
	 */
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info("Enter add.do ...");
		map.put("action", "add");
		TopoObjectType topoObjectType = new TopoObjectType();
		map.put("topoObjectType", topoObjectType);
		return viewPath + "/edit";
	}

	/**
	 * 
	 * 去修改
	 */
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info("Enter edit.do ...");
		map.put("action", "edit");
		TopoObjectType topoObjectType = topoObjectTypeService
				.getTopoObjectTypeByID(id);
		map.put("topoObjectType", topoObjectType);
		return viewPath + "/edit";
	}

	/**
	 * 
	 * 保存
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/save.do")
	public String save(String action, String id, String editName,
			TopoObjectType topoObjectType, ModelMap map,
			MultipartHttpServletRequest request) throws IOException {
		logger.info("Enter save.do ...");

		SysUser curUser = SystemUtils.getLoginUser();
		if (curUser == null) {
			map.put("message", "系统登录超时！");
		}

		
		String path=request.getSession().getServletContext().getRealPath("/");//取得服务器绝对路径
//		String imgpath = path+"icon/devsvg/";  //上传路径
		String imgpath = LoadPropertiesListener.UPLOAD_DEVIMG_URL;  //上传路径
		MultipartFile file = (MultipartFile) request.getFile("picture");// 把文件读入
		String imgName = file.getOriginalFilename();
		if (!file.isEmpty()) {

			String picName = file.getOriginalFilename();
			if (!StringUtil.isNullString(picName)) {

				File dirPath = new File(imgpath);
				if (!dirPath.exists()) {
					dirPath.mkdir();
				}

				if (!StringUtil.isNullString(picName)) {
					
					FileOutputStream fos = new FileOutputStream(imgpath + ""
							+ "/" + "" + imgName);

					fos.write(file.getBytes());
					fos.close();
				}

			}

		}

		String jsonStr = "";
		if (StringUtil.isNullString(editName)) {
			topoObjectType.setObjIcon(imgName);
		} else {
			topoObjectType.setObjIcon(editName);
			topoObjectTypeService.editPictureName(imgpath,editName, imgName);
		}
		
		if ("add".equals(action)) {
			//添加
			topoObjectTypeService.addTopoObjectType(topoObjectType);
			map.put("topoObjectType", topoObjectType);
			
		} else {
			//修改
			
			TopoObjectType editTopoObjectType = topoObjectTypeService.getTopoObjectTypeByID(id);
					
			
			if(StringUtil.isNullString(topoObjectType.getObjIcon())){
				topoObjectType.setObjIcon(editTopoObjectType.getObjIcon());
			}
			BeanUtils.copyProperties(topoObjectType, editTopoObjectType);
			topoObjectTypeService.saveTopoObjectType(editTopoObjectType);
			map.put("topoObjectType", editTopoObjectType);
			
		}
		jsonStr = topoObjectTypeService
				.getJsonTopoObjectTypeObjStr(topoObjectType);
		map.put("saveData", jsonStr);
		map.put("result", "success");

		map.put("action", action);
		return viewPath + "/edit";
	}

	

	/**
	 * 
	 * 批量删除
	 */

	@RequestMapping(value = "/mulitDelete.do")
	public String mulitDelete(String id, HttpServletResponse response)
			throws IOException {
		logger.info("Enter mulitDelete.do ...");

		topoObjectTypeService.deleteTopoObjectTypes(id.split(";"));
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		return null;
	}

	/**
	 * 
	 * 查询字典
	 */
	@RequestMapping(value = "/getclassName.do")
	public String getclassName(HttpServletResponse response) throws IOException {
		logger.info("Enter getclassName.do...");
		// 字典的SysResGroup
		SysResGroup resGroup = resItemService.getSysResGroup("IT_ResClass");

		List<SysResItem> resItems = resItemService.getResCode(resGroup
				.getResGrpId());
		List<ParamResItem> typeCodeList = new ArrayList<ParamResItem>();
		for (int i = 0; i < resItems.size(); i++) {
			ParamResItem pResItem = new ParamResItem();
			BeanUtils.copyProperties(resItems.get(i), pResItem);

			typeCodeList.add(pResItem);
		}
		JSONObject jo = new JSONObject();
		JSONArray jsArealist = JSONArray.fromObject(typeCodeList);
		jo.put("objName", jsArealist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}

	@RequestMapping(value = "/gettypeName.do")
	public String gettypeName(String groupCode, HttpServletResponse response)
			throws IOException {
		logger.info("Enter gettypeName.do...");
		
		SysResGroup resGroup = resItemService.getSysResGroup(groupCode);

		List<SysResItem> resItems = resItemService.getResCode(resGroup
				.getResGrpId());
		List<ParamResItem> typeCodeList = new ArrayList<ParamResItem>();
		for (int i = 0; i < resItems.size(); i++) {
			ParamResItem pResItem = new ParamResItem();
			// copy ,作用：SysResTtem 和SysResGroup是多对一的关系，所以才copy，方便json解析
			BeanUtils.copyProperties(resItems.get(i), pResItem);

			typeCodeList.add(pResItem);
		}
		JSONObject jo = new JSONObject();
		JSONArray jsArealist = JSONArray.fromObject(typeCodeList);
		jo.put("objName", jsArealist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}

	@RequestMapping(value = "/checkFileExists.do")
	public String checkFileExists(String picName, String editName,HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		logger.info("Enter checkFileExists.do ...");

		String path=request.getSession().getServletContext().getRealPath("/");//取得服务器绝对路径
//		String imgpath = path+"icon/devsvg/";  //上传路径
		String imgpath = LoadPropertiesListener.UPLOAD_DEVIMG_URL;  //上传路径
		JSONObject jo = new JSONObject();
		if (StringUtil.isNullString(editName)
				&& !StringUtil.isNullString(picName)) {
			if (picName.contains("\\")) {
				picName = new String(picName.getBytes("iso-8859-1"), "utf-8");//乱码处理
				picName = picName.substring(picName.lastIndexOf("\\") + 1);// 截取字符串
			}
			File dirPath = new File(imgpath + picName);

			if (!dirPath.exists()) {

				jo.put("result", "success");
			} else {
				jo.put("result", "error");
			}
		}
		if (!StringUtil.isNullString(editName)
				&& StringUtil.isNullString(picName)) {

			File dirPath = new File(imgpath + editName);
			if (!dirPath.exists()) {
				
				jo.put("result", "success");
			} else {
				jo.put("result", "error");
			}
		}
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;

	}
	
	@RequestMapping(value="/getTree.do")
	public String getTree(String menuId,HttpServletResponse response,ModelMap map) throws IOException{
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree p=new ParamTree();
		p.setId("0");
		p.setPid("");
		p.setText("IT资源类型");
		p.setIsexpand(true);
		total.add(p);
		
		List<ResItemParam> classCodes=topoObjectTypeService.getTreeByClassCodes();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"1_1");
				pc.setPid("0");
				pc.setText(paramClass.getText());
				total.add(pc);
				
				List<ResItemParam> typeCodes=topoObjectTypeService.getTreeByTypeCodes(Integer.parseInt(paramClass.getId()));
				if(!typeCodes.isEmpty()){
					for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = typeCodes.get(j);
						pt.setId(paramType.getId()+"_"+paramClass.getId()+"_2");
						pt.setPid(paramClass.getId()+"1_1");
						pt.setText(paramType.getText());
						total.add(pt);
						
					}
				}
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath + "/topoTypeTab";
	}
	
	@RequestMapping(value = "/getObjType.do")
	public String getObjType(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<TopoObjectType> list =new ArrayList<TopoObjectType>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		
		if(flag.equals("0")){
		    list = topoObjectTypeService.getAllTopoObjectType();	
		}
		else if(flag.equals("1")){
			String[] classCodes = id.split("1_1");
			
			list = topoObjectTypeService.getByClassCode(Integer.parseInt(classCodes[0]));
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
		
			list = topoObjectTypeService.getByClassAndType(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
		}
		
		String jsonStr = topoObjectTypeService.getJsonTopoObjectTypeListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/showbgPicture.do")
	public String showbgPicture(String id, ModelMap map) throws IOException {

		logger.info("Enter edit.do ...");
		map.put("action", "showbgPicture");
		TopoObjectType topoObjectType = topoObjectTypeService.getTopoObjectTypeByID(id);
		map.put("id", id);
		map.put("topoObjectType", topoObjectType);
		map.put("result", "success");
		return viewPath + "/showbgPicture";
	}
	
	@RequestMapping(value = "/audit.do")
	public String auditTopoType(String id, ModelMap map) throws IOException {

		logger.info("Enter audit.do ...");
		map.put("action", "audit");
		TopoObjectType topoObjectType = topoObjectTypeService.getTopoObjectTypeByID(id);
		map.put("topoObjectType", topoObjectType);
		return viewPath + "/audit";
	}
	
	
	@RequestMapping(value = "/saveTopoType.do")
	public String saveTopoType(String id, ModelMap map) throws IOException {

			TopoObjectType editTopoObjectType = topoObjectTypeService.getTopoObjectTypeByID(id);
			editTopoObjectType.setStatus(1);
			topoObjectTypeService.saveTopoObjectType(editTopoObjectType);
			map.put("topoObjectType", editTopoObjectType);
		    map.put("result", "success");
		    return viewPath + "/audit";
	}
	
}
