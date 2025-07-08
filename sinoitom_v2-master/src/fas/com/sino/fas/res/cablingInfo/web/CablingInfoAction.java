package com.sino.fas.res.cablingInfo.web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.orm.PropertyFilter;

import smartlink.switchmgt.SwitchInfo;
import smartlink.switchmgt.SwitchInfoBase;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfoParam;
import com.sino.fas.res.cablingInfo.service.CablingInfoService;

@Controller
@RequestMapping(value = "/fas/res/cablingInfo")
public class CablingInfoAction {
	private static Logger logger = LoggerFactory.getLogger(CablingInfoAction.class);
	private String viewPath = "/fas/res/cablingInfo";
	
	@Autowired
	private CablingInfoService cablingInfoService;
	
	@Autowired
	private OrganService organService;
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/view.do")
	public String view(String id, ModelMap map) throws IOException{     
		logger.info( "Enter view.do ..." );
		NcmCablingInfo cablingInfo=cablingInfoService.getById(id);
		if(cablingInfo!=null){
			OrgOrganization org=organService.getOrgan(cablingInfo.getOrgId());
			if(org!=null){
				cablingInfo.setOrgId(org.getOrgName());
			}
		}
		map.put("cablingInfo", cablingInfo);
		return viewPath+"/view";
	} 
	
	@RequestMapping(value = "/viewInfo.do")
	public String viewInfo(String infoNo, ModelMap map) throws IOException{     
		logger.info( "Enter viewInfo.do ..." );
		NcmCablingInfo cablingInfo=cablingInfoService.getByNo(infoNo);
		if(cablingInfo!=null){
			OrgOrganization org=organService.getOrgan(cablingInfo.getOrgId());
			if(org!=null){
				cablingInfo.setOrgId(org.getOrgName());
			}
		}
		map.put("cablingInfo", cablingInfo);
		return viewPath+"/view";
	}
	
	@RequestMapping(value = "/getMainData.do")
	public void getMainData(ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter getMainData.do ..." );
		List<NcmCablingInfo> list =cablingInfoService.getAll();
		String jsonStr= cablingInfoService.getJsonListStr(list);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonStr);
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		NcmCablingInfo cablingInfo=new NcmCablingInfo();
		map.put("cablingInfo", cablingInfo);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map){
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		NcmCablingInfo cablingInfo=cablingInfoService.getById(id);
		map.put("id", id);
		map.put("cablingInfo", cablingInfo);
		return viewPath+"/edit";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/verifyPort.do")
	public String verifyPort(String id, ModelMap map){
		logger.info( "Enter verifyPort.do ..." );
		map.put("action", "verify");
		String[] ids = id.split(",");
		List<String> listids=new ArrayList<String>();
		for(String cid:ids){
			listids.add(cid);
		}
		List<NcmCablingInfo> infolist=cablingInfoService.getByIds(listids);
		System.out.println(""+infolist.size());
		List<NcmCablingInfoParam> paramlist=new ArrayList<NcmCablingInfoParam>();
		ArrayList<SwitchInfo> sinfolist=new ArrayList<SwitchInfo>();
		for(NcmCablingInfo info:infolist){
			NcmCablingInfoParam param=new NcmCablingInfoParam();
			BeanUtils.copyProperties(info, param);
			SwitchInfo sinfo=new SwitchInfo();
			sinfo.setSwitchIp(info.getSwitchIP());
			sinfo.setOriswitchInterfaceName(info.getIfName());
			sinfo.setSwitchInfoId(info.getCablingInfoID());
			sinfolist.add(sinfo);
			paramlist.add(param);
		}
		SwitchInfoBase base= new SwitchInfoBase();
		sinfolist = base.getSwitchMatchResult(sinfolist);
		for(int i=0,n=sinfolist.size();i<n;i++){
			SwitchInfo sinfo=sinfolist.get(i);
			for(int j=0,m=paramlist.size();j<m;j++){
				NcmCablingInfoParam param =paramlist.get(j);
				if(param.getCablingInfoID().equals(sinfo.getSwitchInfoId())){
					param.setUnVerifyIfName(sinfo.getswitchInterfaceName());
					param.setSwitchID(sinfo.getSwitchId());
					param.setIfIndex(sinfo.getSwitchPortIndex());
					param.setFlag(sinfo.getMatchResult());
				}
			}
		}
		String jsonStr= cablingInfoService.getParamJsonListStr(paramlist);
		map.put("jsonStr", jsonStr);
		return viewPath+"/verifyPort";
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action,String id, NcmCablingInfo cablingInfo, ModelMap map) {
		logger.info( "Enter save.do ..." );
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		/*else if( !ipHostService.isIpHostUnique(ipHostId, ipHost.getMacAddr())&& !ipHost.getMacAddr().equals("null")){
			validate = false;
			map.put("message", "该IP设备已经存在！");
		}*/
		if( validate ){
			NcmCablingInfo cablingInfoEdit=null;
			if( "add".equals(action)){
				cablingInfo.setBandWidth(StringUtil.getBandwidth(cablingInfo.getBaudRate()));
				cablingInfoService.addInfo(cablingInfo);
				cablingInfoEdit =cablingInfo;
			}
			else{
				cablingInfoEdit = cablingInfoService.getById(id);;
				if( cablingInfoEdit == null ){
					map.put("result", "error");								
					map.put("message", "该配置已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(cablingInfo, cablingInfoEdit);
				cablingInfoEdit.setBandWidth(StringUtil.getBandwidth(cablingInfoEdit.getBaudRate()));
				cablingInfoService.saveInfo(cablingInfoEdit);
				
			}
			NcmCablingInfo cabling=cablingInfoService.getById(cablingInfoEdit.getCablingInfoID());
			String jsonStr=cablingInfoService.getJsonObjStr(cabling);
			System.out.println("get the json:>>"+jsonStr);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("cablingInfo", cablingInfo);
			map.put("result", "error");			
		}
		
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveVerify.do")
	public String saveVerify(String action,String jsonString, ModelMap map) {
		logger.info( "Enter saveVerify.do ..." );
		boolean validate = true;
		if (validate) {
			List<NcmCablingInfoParam> infolist = JsonUtils.getDTOList(jsonString,
					NcmCablingInfoParam.class);
			if (infolist != null && !infolist.isEmpty()) {
				cablingInfoService.batchSave(infolist);
			}
			map.put("result", "success");
			map.put("jsonStr", "{}");
		}
		else {
			map.put("jsonStr", "{}");
			map.put("result", "error");
		}
		map.put("action", "verify");
		return viewPath + "/verifyPort";
	}
	
	@RequestMapping(value = "/importGo.do")
	public String importGo(ModelMap map) throws IOException{     
		logger.info( "Enter importGo.do ..." );
		return viewPath+"/import";
	}  
	
	@RequestMapping(value = "/importExcel.do")
	public String importExcel(MultipartFile impFile, ModelMap map) throws IOException{     
		logger.info( "Enter importExcel.do ..." );
		if( impFile.getSize()<=0 ){
			map.put("result", "error");
			map.put("message", "上传文件内容为空！");
		}
		else{
			if( cablingInfoService.impExcel(impFile.getInputStream()) ){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", cablingInfoService.getLastMassage());
		}
		return viewPath+"/import";      
	}            
	
	@RequestMapping(value = "/exportExcel.do")
	public String exportExcel(ModelMap map,HttpServletResponse response){
		logger.info("enter exportExcel....");
		List<NcmCablingInfo> listinfo =cablingInfoService.getAll();
		String[] headers = {"机构名称", "信息点编号", "楼号","房间号","介质类型","线缆类型","带宽","长度(米)","机架号","模块号","模块端口号","交换机IP","交换机端口","备注"};//导出字段名称
		String attr="orgId@OrgOrganization@orgId@orgName,infoPointNo,buildingNo,roomNo,mediaType,cableType,baudRate,length,cabinetNo,moduleNo,modulePort,switchIP,ifName,remark";//导出映射字段
		String fileName="综合布线信息_"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名
	    try {
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("综合布线信息", headers, listinfo, attr, out);
	          out.close();
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
	    return null;
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i])){
				cablingInfoService.deleteInfo(ids[i].trim());
			}
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<NcmCablingInfo> list = cablingInfoService.searchInfo(filters);
		String jsonStr= cablingInfoService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
}
