/**
 * 
 */
package com.sino.fas.res.host.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import smartlink.utils.Util;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.fas.res.host.entity.ParamResAppService;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.fas.res.host.entity.ResClusterHosts;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.fas.res.host.entity.ResServiceProcess;
import com.sino.fas.res.host.service.ResClusterHostsService;
import com.sino.fas.res.host.service.ResHostsService;
import com.sino.snmp.utils.JdbcConnection;

/**
 * @author Mr.LP
 * @date 2019-12-11上午10:59:50
 * @className ResClusterHostsAction
 * @version V1.0
 * @Description TODO
 *
 */

@Controller
@RequestMapping(value = "/res/host/clusterHosts")
public class ResClusterHostsAction {
	
	private static Logger logger = LoggerFactory.getLogger(ResClusterHostsAction.class);
	private String viewPath = "/res/compResource/clusterhost";
	
	@Autowired
	private ResHostsService resHostsService;
	
	@Autowired
	private ResClusterHostsService resClusterHostsService;
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		
		logger.info( "Enter main.do ..." );
		List<ResHosts> hostsList=resHostsService.getByHostType();
		String jsonStr = resHostsService.getJsonListStr(hostsList);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}
	
	/**
	 * 添加跳转
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		String action = "add";
		ResHosts resHosts=new ResHosts();
		map.put("resHosts", resHosts);
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	/**
	 * 修改跳转
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map){
		logger.info( "Enter edit.do ..." );
		ResHosts resHosts = resHostsService.getById(Long.parseLong(id));
		String purpose = resHosts.getPurpose();
		String action = "edit";
		map.put("resHosts", resHosts);
        map.put("id", id);
        map.put("action", action);
        map.put("purpose", purpose);
        return viewPath+"/edit";
	}
	
	/**
	 * 添加/修改保存
	 * @param action
	 * @param id
	 * @param resHosts
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/save.do")
	public String searchAddsave(String action,String id,ResHosts resHosts, ModelMap map) {
	   logger.info( "Enter searchAddsave.do ..." );
	   
	   SysUser curUser = null;
	   Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
	   
	   boolean validate = true;
		curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("result", "error");
			map.put("message", "系统登录超时！");			
		}
		if(validate){
			ResHosts editHosts = new ResHosts();
			if(action.equals("add")){
				   resHosts.setCreateTime(now);
				   resHosts.setPowerStatus(1);
				   resHosts.setHostStatus(1);
				   resHosts.setResTypeCode(1);
				   resHosts.setResTypeName("服务器");
				   resHosts.setResClassCode(11);
				   resHosts.setResClassName("计算资源");
				   resHosts.setCreator(curUser.getUserName());
				   resHosts.setHostId(JdbcConnection.getInstance().getDeviceResourceUniqId());
				   resHosts.setIpLong(Util.ip2long(resHosts.getIpAddress()));
				   System.out.println(resHosts.getPurpose());
				   resHostsService.save(resHosts);
				   map.put("result", "success");
		           map.put("resHosts", resHosts);
			   }else{
				   if(id!=null){
					   editHosts = resHostsService.getById(Long.parseLong(id));
					   resHosts.setModifyTime(now);
					   resHosts.setModifier(curUser.getUserName());
					   BeanUtils.copyProperties(resHosts, editHosts);
					   resHostsService.save(editHosts);
					   map.put("result", "success");
					   map.put("resHosts", resHosts);
				   }else{
					   map.put("result", "error");
			           map.put("message", "编辑失败！");
				   }
			   }
		}
	   
	   return viewPath+"/edit";
	}
	
	/**
	 * 删除
	 * @param id
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		if(!StringUtil.isNullString(id)){
			List<ResClusterHosts> list = new ArrayList<ResClusterHosts>();
			list = resClusterHostsService.getByClusterId(Long.parseLong(id));
			resHostsService.deleteHostAndClusterHost(Long.parseLong(id),list);
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		}
	    return null;
	}
	
	/**
	 * 详情
	 * @param id
	 * @param map
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/view.do")
	public String view(String id, ModelMap map) throws IOException{     
		logger.info( "Enter view.do ..." );
		
		if(!StringUtil.isNullString(id)){
			ResHosts resHosts =resHostsService.getById(Long.parseLong(id));
			map.put("resHosts", resHosts);
		}
		return viewPath+"/view";
	}
	
	/**
	 * 关联服务器
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/relationHost.do")
	public String relationHost(String id,ModelMap map) {
		logger.info( "Enter relationHost.do ..." );
		List<ResHosts> list=new ArrayList<ResHosts>();
		list = resHostsService.getLegalHosts();
		String jsonStr = resHostsService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("clusterId", id);
		return viewPath+"/relation";
	}
	
	/**
	 * 关联服务器保存
	 * @param ids
	 * @param clusterId
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/relationSave.do")
	public String relationSave(String ids,String clusterId, HttpServletResponse response) throws IOException {
		logger.info( "Enter relationSave.do ..." );
		if(StringUtils.isNotBlank(ids)){
			List<ResClusterHosts> list = new ArrayList<ResClusterHosts>();
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				ResClusterHosts clusterHosts = new ResClusterHosts();
				clusterHosts = resClusterHostsService.getByClusterIdAndHostId(Long.parseLong(clusterId),Long.parseLong(id[i]));
				if(clusterHosts!=null){
					resClusterHostsService.delete(clusterHosts.getId());
				}
			}
			for (int i = 0; i < id.length; i++) {
				ResClusterHosts clusterHosts = new ResClusterHosts();
				clusterHosts.setHostId(Long.parseLong(id[i]));
				clusterHosts.setClusterId(Long.parseLong(clusterId));
				list.add(clusterHosts);
			}
			resClusterHostsService.batchSave(list);
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		}
		
		return null;
	}
	
	
	/**
	 * 取消关联
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/cancelRelationHost.do")
	public String cancelRelationHost(String id,ModelMap map) {
		logger.info( "Enter cancelRelationHost.do ..." );
		
		List<ResHosts> list=new ArrayList<ResHosts>();
		List<ResClusterHosts> clusterHostsList = resClusterHostsService.getByClusterId(Long.parseLong(id));
		for (int i = 0; i < clusterHostsList.size(); i++) {
			ResHosts host = new ResHosts();
			host = resHostsService.getById(clusterHostsList.get(i).getHostId());
			list.add(host);
		}
		String jsonStr = resHostsService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("clusterId", id);
		return viewPath+"/cancelRelation";
	}
	
	/**
	 * 取消关联保存
	 * @param ids
	 * @param clusterId
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/cancelRelationSave.do")
	public String cancelRelationSave(String ids,String clusterId, HttpServletResponse response) throws IOException {
		logger.info( "Enter cancelRelationSave.do ..." );
		if(StringUtils.isNotBlank(ids)){
			List<ResClusterHosts> list = new ArrayList<ResClusterHosts>();
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				ResClusterHosts clusterHosts = new ResClusterHosts();
				clusterHosts = resClusterHostsService.getByClusterIdAndHostId(Long.parseLong(clusterId),Long.parseLong(id[i]));
				if(clusterHosts!=null){
					resClusterHostsService.delete(clusterHosts.getId());
				}
			}
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		}
		
		return null;
	}
	
	/**
	 * 根据hostId获取host（main页面子列表内容）
	 * @param id
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getResHostsByHostId.do")
    public String getResHostsByHostId(String id, HttpServletResponse response) throws IOException {
        logger.info("Enter getResHostsByHostId.do ...");

        if(StringUtils.isNotBlank(id)){
        	List<ResClusterHosts> clusterHostsList = new ArrayList<ResClusterHosts>();
        	clusterHostsList = resClusterHostsService.getByClusterId(Long.parseLong(id));
        	List<ResHosts> hostList = new ArrayList<ResHosts>();
        	for (int i = 0; i < clusterHostsList.size(); i++) {
        		ResHosts host = new ResHosts();
            	host = resHostsService.getById(clusterHostsList.get(i).getHostId());
            	hostList.add(host);
			}
        	String jsonStr = resHostsService.getJsonListStr(hostList);
        	response.getWriter().print(jsonStr);
        }
        return null;
    }
}
