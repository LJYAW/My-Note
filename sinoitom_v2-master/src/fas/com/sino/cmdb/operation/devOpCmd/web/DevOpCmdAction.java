package com.sino.cmdb.operation.devOpCmd.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import smartlink.utils.Util;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.operation.devCmdParam.entity.DevCmdParam;
import com.sino.cmdb.operation.devCmdParam.entity.DevOpCmdParam;
import com.sino.cmdb.operation.devCmdParam.service.DevCmdParamService;
import com.sino.cmdb.operation.devCmdParam.service.DevOpCmdParamService;
import com.sino.cmdb.operation.devOp.entity.DevOperation;
import com.sino.cmdb.operation.devOp.service.DevOperationService;
import com.sino.cmdb.operation.devOpCmd.dao.DevOpCmdDao;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmd;
import com.sino.cmdb.operation.devOpCmd.service.DevOpCmdService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.cmdb.vendor.service.CmdbVendorService;

/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-2-12
 */
@Controller
@RequestMapping(value="/cmdb/devOpCmd")
public class DevOpCmdAction {
	private static Logger logger = LoggerFactory.getLogger(DevOpCmdAction.class);
	private String viewPath = "/cmdb/devOpCmd";
	
	private String newViewPath = "/cmdb/operation/opCmd";
	
	@Autowired
	private DevOpCmdService devOpCmdService;
	@Autowired
	private DevOperationService devOperationService;
	@Autowired
	private ResItemService resItemService;
	@Autowired
	private CmdbVendorService cmdbVendorService;
	
	@Autowired
	private DevCmdParamService devCmdParamService;
	
	@Autowired
	private DevOpCmdParamService devOpCmdParamService;
	
	@Autowired
	private DevOpCmdDao devOpCmdDao;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("设备操作（命令）");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> classCodes=devOpCmdDao.getOperation();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=devOpCmdDao.getVenderById(Integer.parseInt(paramClass.getId()));
				if(!typeCodes.isEmpty()){
					for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = typeCodes.get(j);
						pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
						pt.setPid(paramClass.getId()+"_2");
						pt.setText(paramType.getText());
						total.add(pt);
						List<ResItemParam> groupNames=devOpCmdDao.getDevTypeByVendor(Integer.parseInt(paramType.getId()));
						if(!groupNames.isEmpty()){
							for(int k=0;k<groupNames.size();k++){//指标组名称 4级节点
								ParamTree pg=new ParamTree();
								ResItemParam paramGroup = groupNames.get(k);
								pg.setId(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_4");
								pg.setText(paramGroup.getText());
								pg.setPid(paramClass.getId()+"_"+paramType.getId()+"_3");
								total.add(pg);
							}
						}
					}
				}
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return newViewPath + "/opCmdTab";
		
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam> list =new ArrayList<com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			list = devOpCmdService.getAll();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = devOpCmdService.getByOperation(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = devOpCmdService.getByOperationAndVendor(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
		}else if(flag.equals("4")){
			String [] typeCodes=id.split("_");
			list = devOpCmdService.getByOperationAndVendorAndDevType(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Integer.parseInt(typeCodes[2]));
		}
		
		String jsonStr = devOpCmdService.getParamJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return newViewPath+"/main";
	}
	
	
	@RequestMapping(value = "/opCmdMain.do")
	public String opCmdMain(ModelMap map,String id) {
		logger.info( "Enter opCmdMain.do ..." );
		return newViewPath+"/main";
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		logger.info("Enter DevOpCmd edit.do...");
		DevOpCmd opCmd=devOpCmdService.getByID(Long.valueOf(id));
		DevOperation devOperation = devOperationService.getDevOperationById(opCmd.getDevOpID());
		CmdbVendor vendor=cmdbVendorService.findVendor(opCmd.getSnmpEntID());
		map.put("id", id);
		map.put("opCmd", opCmd);
		map.put("devOperation", devOperation);
		map.put("vendor", vendor);
	
		return newViewPath+"/edit";
	}
	
	@RequestMapping(value="/view.do")
	public String view(String id,ModelMap map){
		logger.info("Enter DevOpCmd edit.do...");
		DevOpCmd opCmd=devOpCmdService.getByID(Long.valueOf(id));
		DevOperation devOperation = devOperationService.getDevOperationById(opCmd.getDevOpID());
		CmdbVendor vendor=cmdbVendorService.findVendor(opCmd.getSnmpEntID());
		String cmdType=resItemService.findResContent("Cmdb_DevCmdType", opCmd.getCmdTypeCode().toString());
		map.put("id", id);
		map.put("opCmd", opCmd);
		map.put("devOperation", devOperation);
		map.put("vendor", vendor);
		map.put("cmdType", cmdType);
		return newViewPath+"/view";
	}
	
	
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map,String id) {
		logger.info( "Enter add.do ..." );
		DevOperation  devOperation=devOperationService.getDevOperationById(Integer.valueOf(id));
		SysResGroup resGroup=resItemService.getSysResGroup("Cmdb_DevCmdType");
		List<SysResItem> resItems=resItemService.getResCode(resGroup.getResGrpId());
		String cmdTypeStr=resItemService.getJsonListStr(resItems);
		map.put("cmdTypeStr", cmdTypeStr);
		map.put("devOperation", devOperation);
		map.put("action", "add");
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value="/delete.do")
	public String delete(String devOpCmdIds,ModelMap map,HttpServletResponse response) throws IOException{
		logger.info("Enter DevOpCmd delete.do...");
		String []arrIds=devOpCmdIds.split(";");
		for(String id:arrIds){
			devOpCmdService.delete(Long.valueOf(id));
		}
		JSONObject jo=new JSONObject();
		jo.put("result", "success");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	//维护
	@RequestMapping(value = "/maintain.do")
	public String maintain(ModelMap map,String id) {
		logger.info( "Enter add.do ..." );
		DevOperation  devOperation=devOperationService.getDevOperationById(Integer.valueOf(id));
		SysResGroup resGroup=resItemService.getSysResGroup("Cmdb_DevCmdType");
		List<SysResItem> resItems=resItemService.getResCode(resGroup.getResGrpId());
		String cmdTypeStr=resItemService.getJsonListStr(resItems);
		map.put("cmdTypeStr", cmdTypeStr);
		map.put("devOperation", devOperation);
		map.put("action", "add");
		map.put("id", id);
		return newViewPath+"/maintain";
	}
	
	
	@RequestMapping(value = "/getData.do")
	public String getData(String id,String vendorID,String osID,String oSName,String osVersion,String modelOID,HttpServletResponse response) throws IOException{
		osVersion = new String(osVersion.getBytes("iso-8859-1"),"utf-8");
		osVersion = java.net.URLDecoder.decode(osVersion,"UTF-8");
		List<DevOpCmd> list=new ArrayList<DevOpCmd>();
		String jsonStr;
		if(id!=null){
			list=devOpCmdService.getDataBy(Integer.valueOf(id),Integer.valueOf(vendorID),Integer.valueOf(osID),oSName,osVersion,modelOID);
			if(!list.isEmpty()){
				jsonStr=devOpCmdService.getJsonListStr(list);
			}else{
				jsonStr="[{\"devOpID\":11, \"id\":6, \"snmpEntID\":\"2\", \"dispName\":\"IBM\", \"osID\":15, \"osName\":\"IOS\", \"osVersionID\":\"X\", \"cmdOrder\":6, \"cmdTypeCode\":null, \"cmdType\":\"\", \"command\":\"\", \"parmFlag\":\"\", \"description\":null}]";
			}
			response.getWriter().print(jsonStr);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save.do")
	public String save(String devOpID,String jsonStr,ModelMap map,DevOpCmd devOpCmd,String vendor,String osID,String osVersionText,String osName,String modelOID) throws UnsupportedEncodingException{
		osVersionText = new String(osVersionText.getBytes("iso-8859-1"),"utf-8");
		osVersionText = java.net.URLDecoder.decode(osVersionText,"UTF-8");
		List<DevOpCmd> devOpCmdlist=JsonUtils.getDTOList(jsonStr, DevOpCmd.class);
		
		List<DevOpCmdParam> devOpCmdParamList=new ArrayList<DevOpCmdParam>();
		
		if(devOpID!=null&&!"-1".equals(osID)){
		    List<DevOpCmd> list=devOpCmdService.getDataBy(Integer.valueOf(devOpID),Integer.valueOf(vendor),Integer.valueOf(osID),osName,osVersionText,modelOID);
			if(!list.isEmpty()){
				for(int j=0;j<list.size();j++){
					long opCmdID=list.get(j).getOpCmdID();
					devOpCmdService.delete(opCmdID);//按ID删除页面上显示的数据
				}
			}
		    for(int i=0;i<devOpCmdlist.size();i++){
				DevOpCmd entityDevOpCmd=devOpCmdlist.get(i);
				DevOpCmd saveDevOpCmd=new DevOpCmd();
				//saveDevOpCmd.setOpCmdID(DBQueryUtil.getInstance().getDeviceResourceUniqId());
				saveDevOpCmd.setCmdType(entityDevOpCmd.getCmdType());
				saveDevOpCmd.setCommand(entityDevOpCmd.getCommand());
				saveDevOpCmd.setParamFlag(entityDevOpCmd.getParamFlag());
				saveDevOpCmd.setDescription(entityDevOpCmd.getDescription());
				saveDevOpCmd.setDevOpID(Integer.valueOf(devOpID));//设备操作ID
				saveDevOpCmd.setSnmpEntID(Integer.valueOf(vendor));//厂商id
				saveDevOpCmd.setOsID(Integer.valueOf(osID));//系统id
				saveDevOpCmd.setOsVersion(osVersionText);
				saveDevOpCmd.setOsName(osName);
				saveDevOpCmd.setCmdOrder(Integer.valueOf(i+1));
				saveDevOpCmd.setModelOID(modelOID);//型号OID
				
	
				
				devOpCmdService.save(saveDevOpCmd);//保存新添加的数据
				
				if (entityDevOpCmd.getParamFlag() > 0){
					long devOpCmdID= devOpCmdService.getCurIntId();
					//cmdParamList = Cmdb_DevCmdParam() //参数字典表 
					List<DevCmdParam> devCmdParams=devCmdParamService.getAll();
					
					for(int j=0;j<devCmdParams.size();j++){   ///Cmdb_DevOpCmdParam
						DevCmdParam cmdParam=devCmdParams.get(j);
						if(entityDevOpCmd.getCommand().equalsIgnoreCase(cmdParam.getParamName())){
							DevOpCmdParam param=new DevOpCmdParam();
							param.setOpCmdID(devOpCmdID);
							param.setParam_Value(cmdParam.getParamName());
							devOpCmdParamList.add(param);
						}
					}
				}
			}
		    
		    devOpCmdParamService.batchSave(devOpCmdParamList);
		}
		
			return viewPath+"/edit";
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveDevCmd.do")
	public String saveDevCmd(String devOpID,String jsonStr,ModelMap map,DevOpCmd devOpCmd,String vendor,String osID,String osVersionText,String osName,String modelOID) throws UnsupportedEncodingException{
		osVersionText = new String(osVersionText.getBytes("iso-8859-1"),"utf-8");
		osVersionText = java.net.URLDecoder.decode(osVersionText,"UTF-8");
		List<DevOpCmd> templist=JsonUtils.getDTOList(jsonStr, DevOpCmd.class);
		
		List<DevOpCmd> devOpCmdlist=new ArrayList<DevOpCmd>();  //  
		
		SysResGroup resGroup=resItemService.getSysResGroup("Cmdb_DevCmdType");
		
		for(DevOpCmd cmd:templist){
			int cmdTypeCode=Integer.parseInt(resItemService.findResCode(resGroup.getGrpCode(), cmd.getCmdType()));
			DevOpCmd tcmd=new DevOpCmd();
			BeanUtils.copyProperties(cmd, tcmd);
			tcmd.setCmdTypeCode(cmdTypeCode);
			tcmd.setCommand(cmd.getCommand().trim());
			devOpCmdlist.add(tcmd);
		}
		
		if(devOpID!=null&&!"-1".equals(osID)){
		    List<DevOpCmd> list=devOpCmdService.getDataBy(Integer.valueOf(devOpID),Integer.valueOf(vendor),Integer.valueOf(osID),osName,osVersionText,modelOID);
			if(!list.isEmpty()){
				for(int j=0;j<list.size();j++){
					long opCmdID=list.get(j).getOpCmdID();
					devOpCmdService.delete(opCmdID);//按ID删除页面上显示的数据
				}
			}
			int cmdOrder=0;
		    for(int i=0;i<devOpCmdlist.size();i++){
				DevOpCmd entityDevOpCmd=devOpCmdlist.get(i);
				
				
				DevOpCmd saveDevOpCmd=new DevOpCmd();
				cmdOrder=i+1;
				saveDevOpCmd.setCmdOrder(cmdOrder);
				long devOpCmdId=Util.getCRC32(vendor +"_"+ modelOID +"_"+ osName +"_"+ osVersionText +"_"+ devOpID +"_"+ String.valueOf(cmdOrder));
				saveDevOpCmd.setOpCmdID(devOpCmdId);
				saveDevOpCmd.setCmdType(entityDevOpCmd.getCmdType());
				
				saveDevOpCmd.setCmdTypeCode(entityDevOpCmd.getCmdTypeCode());
				
				if(entityDevOpCmd.getExpectPrompt()==null){
					saveDevOpCmd.setExpectPrompt("");
				}else{
					saveDevOpCmd.setExpectPrompt(entityDevOpCmd.getExpectPrompt());
				}
				
				saveDevOpCmd.setCommand(entityDevOpCmd.getCommand());
				
				if(entityDevOpCmd.getParamFlag()==null){
					saveDevOpCmd.setParamFlag(0);
				}else{
					saveDevOpCmd.setParamFlag(entityDevOpCmd.getParamFlag());
				}
				
				saveDevOpCmd.setDescription(entityDevOpCmd.getDescription());
				saveDevOpCmd.setDevOpID(Integer.valueOf(devOpID));//设备操作ID
				saveDevOpCmd.setSnmpEntID(Integer.valueOf(vendor));//厂商id
				saveDevOpCmd.setOsID(Integer.valueOf(osID));//系统id
				saveDevOpCmd.setOsVersion(osVersionText);
				saveDevOpCmd.setOsName(osName);
				
				
				if(devOpCmd.getDevModel().equals("X")){
					saveDevOpCmd.setDevModel("所有型号");
				}else{
					saveDevOpCmd.setDevModel(devOpCmd.getDevModel());
				}
				
				saveDevOpCmd.setModelOID(modelOID);//型号OID
				devOpCmdService.save(saveDevOpCmd);//保存新添加的数据
				
				
				if ((entityDevOpCmd.getParamFlag()==null?0:entityDevOpCmd.getParamFlag()) > 0){
					List<DevOpCmdParam> devOpCmdParamList=new ArrayList<DevOpCmdParam>();
					//cmdParamList = Cmdb_DevCmdParam() //参数字典表 
					List<DevCmdParam> devCmdParams=devCmdParamService.getAll();
					
					for(int j=0;j<devCmdParams.size();j++){   ///Cmdb_DevOpCmdParam
						DevCmdParam cmdParam=devCmdParams.get(j);
						if(entityDevOpCmd.getCommand().contains(cmdParam.getParamName())){
							DevOpCmdParam param=new DevOpCmdParam();
							param.setOpCmdID(devOpCmdId);
							param.setCmdParameter(cmdParam.getParamName());
							devOpCmdParamList.add(param);
						}
					}
					 devOpCmdParamService.batchSave(devOpCmdParamList);
				}
				
				
			}
		     
		      map.put("result", "success");
		      map.put("cmdTypeStr", "0");//防止添加成功后页面报错
		}
		
			return newViewPath+"/maintain";
		
		
	}
	
	
//	@RequestMapping(value = "/search.do")
//	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
//		logger.info( "Enter search.do ..." );
//		//List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
//		List<DevOpCmd> list = devOpCmdService.getAll();
//		String jsonStr = devOpCmdService.getJsonListStr(list);
//		response.getWriter().print(jsonStr);
//	    return null;         
//	}
	
	
	@RequestMapping(value="/saveEditDevOpCmd.do")
	public String saveEditDevOpCmd(String id,DevOpCmd cmd,ModelMap map){
		logger.info("Enter saveEditDevOpCmd.do...");
		DevOpCmd opCmd=devOpCmdService.getByID(Integer.valueOf(id));
		opCmd.setCmdTypeCode(cmd.getCmdTypeCode());
		String  resName=resItemService.findResContent("Cmdb_DevCmdType", String.valueOf(cmd.getCmdTypeCode()));
		opCmd.setCmdType(resName);
		opCmd.setParamFlag(cmd.getParamFlag());
		opCmd.setCommand(cmd.getCommand());
		opCmd.setExpectPrompt(cmd.getExpectPrompt());
		opCmd.setDescription(cmd.getDescription());
		devOpCmdService.save(opCmd);
		String jsonStr = devOpCmdService.getJsonObjStr(opCmd);
		map.put("result", "success");
		map.put("saveData", jsonStr);
		map.put("action", "edit");
		return newViewPath+"/edit";
		
	}
	
	
}
