package com.sino.fas.res.net.web;

import com.sino.base.common.util.*;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.fas.res.net.entity.NcmSubNetwork;
import com.sino.fas.res.net.entity.ParamSubnetwork;
import com.sino.fas.res.net.service.SubnetService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.orm.PropertyFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/fas/res/net/subnet")
public class SubnetAction {
	private static Logger logger = LoggerFactory.getLogger(SubnetAction.class);
	
	private String viewPath = "/fas/res/net/subnet";

	@Autowired
	private SubnetService subnetService;	
	
	@Autowired
	private OrganService organService;	
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<NcmSubNetwork> list = subnetService.getAllSubnet();
//		List<ParamSubnetwork> plist=subnetService.getTreeData(list);
		String jsonStr = subnetService.getJsonTreeStr(list);
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/main";
	}                                              

	@RequestMapping(value = "/getData.do")
	public String getData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<NcmSubNetwork> list = subnetService.getAllSubnet();
		String jsonStr = subnetService.getJsonTreeStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<NcmSubNetwork> list = subnetService.searchSubnet(filters);
		String jsonStr = subnetService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		NcmSubNetwork subnet = new NcmSubNetwork();
		map.put("subnet", subnet);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(Long id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		NcmSubNetwork subnet = subnetService.getSubnet(id);
		map.put("subnet", subnet);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, String closeDlg, Long id, NcmSubNetwork subnet, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		OrgOrganization org = organService.getOrgan(subnet.getOrgId());
		if( org == null ){
			validate = false;
			map.put("message", "子网机构不能为空！");			
		}
		else if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( StringUtils.isBlank(subnet.getSubnetAddr()) ){
			validate = false;
			map.put("message", "子网IP不能为空！");
		}
		else if( StringUtils.isBlank(subnet.getSubnetMask()) ){
			validate = false;
			map.put("message", "子网掩码不能为空！");
		}
		else if( !subnetService.isSubnetUnique(id, subnet.getSubnetAddr(), subnet.getSubnetMask()) ){
			validate = false;
			map.put("message", "该子网已经存在！");
		}

		
		
		if( validate ){
			subnet.setOrgName(org.getOrgName());
			long addr = NetworkUtils.ip2long(subnet.getSubnetAddr());
			long mask = NetworkUtils.ip2long(subnet.getSubnetMask());
			long[] range = NetworkUtils.getNetworkRange(addr, mask);
			long size = range[1] - range[0] +1;
			subnet.setSubnetAddr(NetworkUtils.str_addr(range[0]));
			subnet.setHostIpStart(range[0]);
			subnet.setHostIpEnd(range[1]);
			subnet.setSubnetSize(size);
			String operator = curUser.getLoginName();
			subnet.setModifier(operator);
			Date now = new Date();
			subnet.setModifyTime(now.getTime());
			String jsonStr = "";
			if( "add".equals(action)){
				subnet.setCreator(operator);
				subnet.setCreateTime(now.getTime());
				subnetService.addSubnet(subnet);		
				map.put("subnet", subnet);
				jsonStr = subnetService.getJsonObjStr(subnet);
			}
			else{
				NcmSubNetwork editSubnet = subnetService.getSubnet(id);
				if( editSubnet == null ){
					map.put("result", "error");								
					map.put("message", "该子网已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(subnet, editSubnet);
				subnetService.saveSubnet(editSubnet);			
				map.put("subnet", editSubnet);
				jsonStr = subnetService.getJsonObjStr(editSubnet);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("subnet", subnet);
			map.put("result", "error");			
		}

		map.put("action", action);
		map.put("closeDlg", closeDlg);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(Long id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		NcmSubNetwork subnet = subnetService.getSubnet(id);
		subnetService.deleteSubnet(subnet);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/view.do")
	public String view(Long id, ModelMap map) throws IOException{     
		logger.info( "Enter view.do ..." );
		NcmSubNetwork subnet = subnetService.getSubnet(id);
		map.put("subnet", subnet);

		return viewPath+"/view";
	}  
    
	@RequestMapping(value = "/importGo.do")
	public String importGo(ModelMap map) throws IOException{     
		logger.info( "Enter importGo.do ..." );
		return viewPath+"/import";
	}
	
	@RequestMapping(value = "/exportExcel.do")
	public String exportExcel(ModelMap map,HttpServletResponse response) throws IOException{     
		logger.info( "Enter exportExcel.do ..." );
		List<NcmSubNetwork> listsub=new ArrayList<NcmSubNetwork>();
		List<NcmSubNetwork> list = subnetService.getAllSubnet();
		for(NcmSubNetwork sub:list){
			sub.setSubnetAddr(sub.getSubnetAddr()+"/"+sub.getSubnetMask());
			listsub.add(sub);
		}
		String[] headers = {"机构名称","IP子网","IP地址类型","子网类型","管理状态","工作状态","vlan名称","子网名称","子网说明"};//导出字段名称
		String attr="orgId@OrgOrganization@orgId@orgName,subnetAddr,subnetType@Subnet_Type,subnetUsage@Subnet_Usage,adminStatus@Subnet_AdminStatus,operStatus@Subnet_OperStatus,vlanName,subnetName,subnetDesc";//导出映射字段
		int[] exlColWidths=new int[]{4800,6800,4800,4800,4800,4800,4800,4800,4800,4800};//excel 列宽132*36≈4800相当于132px
		String fileName="IP子网清单_"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名
	    try {  
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("IP子网信息", headers, exlColWidths, listsub, attr, out);
	          out.close();
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
	    return null;
	}
	
	@RequestMapping(value = "/importExcel.do")
	public String importExcel(MultipartFile impFile, ModelMap map) throws IOException{     
		logger.info( "Enter importExcel.do ..." );
		if( impFile.getSize()<=0 ){
			map.put("result", "error");
			map.put("message", "上传文件内容为空！");
		}
		else{
			if( subnetService.importExcel(impFile.getInputStream()) ){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", subnetService.getLastMassage());
		}
		return viewPath+"/import";      
	}               
}
