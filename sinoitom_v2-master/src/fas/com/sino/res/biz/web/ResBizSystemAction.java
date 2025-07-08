package com.sino.res.biz.web;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.graph.web.DBUtil;
import com.sino.res.biz.service.ResBizSystemService;
import com.sino.res.biz.entity.ResBizSystem;

@RequestMapping(value = "/res/biz")
@Controller
public class ResBizSystemAction {
	
	private String viewPath = "/res/biz";
	private static Logger logger = LoggerFactory.getLogger(ResBizSystemAction.class);

	@Autowired
	private ResBizSystemService resBizSystemService;
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info("Enter main.do ...");
		List<ResBizSystem> ackResources=resBizSystemService.getAll();
		String jsonStr = resBizSystemService.getJsonListStr(ackResources);
		map.put("jsonListData", jsonStr);
		return viewPath + "/main";
	}

	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info("Enter add.do ...");
		ResBizSystem resBizSystem = new ResBizSystem();
		map.put("resBizSystem", resBizSystem);
		map.put("action", "add");
		return viewPath + "/add";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(Long id, ModelMap map) {
		logger.info("Enter edit.do...");
		ResBizSystem resBizSystem=resBizSystemService.getById(id);
		map.put("action", "edit");
		map.put("id", id);
		map.put("resBizSystem", resBizSystem);
		return viewPath + "/add";
	}
	@RequestMapping(value = "/setService.do")
	public String setService(Long id, ModelMap map) {
		logger.info("Enter edit.do...");
//		ResBizSystem resBizSystem=resBizSystemService.getById(id);
		map.put("id", id);
//		map.put("resBizSystem", resBizSystem);
		return viewPath + "/setService";
	}
	@RequestMapping(value = "/save.do")
	public String save(Long id,String action, ResBizSystem resBizSystem, ModelMap map) {
		logger.info("Enter save.do...");

		if( "add".equals(action)){
			if(resBizSystem.getSvrType()!=0){
				resBizSystem.setSvrId(null);
			}
			if(resBizSystem.getAccessMode().equals("C/S")){
				resBizSystem.setAccessProt("");
			}
			resBizSystem.setResId(DBUtil.getUniqResId());
//			resBizSystem.setResClassCode(27);
//			resBizSystem.setResClassName("业务系统");
			resBizSystem.setSvcName("tcp");
			resBizSystem.setStatus(0);
			resBizSystem.setBizURLStatus(1);
			resBizSystemService.save(resBizSystem);
		}else{
			ResBizSystem resBizS=resBizSystemService.getById(id);
			BeanUtils.copyProperties(resBizSystem, resBizS);
			resBizSystemService.save(resBizS);
		}

		map.put("result", "success");

		map.put("action", action);
		return viewPath + "/add";
	}
	
	//详情
	@RequestMapping(value = "/view.do")
	public String view(Long id, ModelMap map) {
		logger.info("Enter detail ...");
		map.put("action", "detail");
		ResBizSystem resBizSystem = resBizSystemService.getById(id);
		map.put("resBizSystem", resBizSystem);
		return viewPath + "/view";
	}
	
	/**
	 * 删除
	 * @param ids
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete.do")
	public String delete(String ids, ModelMap map, HttpServletResponse response) throws IOException {
		logger.info("Enter delete.do..");
		if (!ids.isEmpty()) {
			String[] id = ids.split(",");
			resBizSystemService.deletes(id);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	/**
	 * 业务验证
	 * @param ipAddr
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkResBiz.do")
	public String checkResBiz(Long id,String ipAddr, HttpServletResponse response) throws IOException {
		logger.info( "Enter checkResBiz.do ..." );
		ResBizSystem resBizS=resBizSystemService.getById(id);
		Integer svcPort = resBizS.getSvcPort();
		String url = "http://" + ipAddr + ":" + svcPort;
		JSONObject jo = new JSONObject();
		String str = testHttpGet(url);
		if (str.equals("ok")) {
			resBizS.setBizURLStatus(0);
			resBizSystemService.save(resBizS);
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		} else {
			resBizS.setBizURLStatus(1);
			resBizSystemService.save(resBizS);
			jo.put("message", str);
			response.getWriter().print(jo.toString());
		}
		return null;
	}
	
	public String testHttpGet(String getUrl) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(getUrl);

			conn = (HttpURLConnection) url.openConnection();
			
			conn.connect();
			int respCode = conn.getResponseCode();
//			System.out.println("ResponseCode=" + respCode);
//			System.out.println("ResponseMsgs=" + conn.getResponseMessage());
			conn.disconnect();
			
			if (respCode == 200) {
				return "ok";
			} 

		} catch (MalformedURLException e) { //协议不支持
			return "the URL cat not access successfully! \n"+"Unknown protocol! \n"+"please check the protocol part of the URL!";
		} catch (UnknownHostException e) { // IP地址问题
			return "the URL cat not access successfully! \n"+"Unknown Host! \n"+"please check the host IP or domain name!";
		} catch (ConnectException e) { //服务端口未开启
			return "the URL cat not access successfully! \n"+"Connection refused by Service Port! \n"+"please check the service!";
		} catch (IOException e) {
			return "the URL cat not access successfully!";
		}
		return null;
	}
	
	
	@RequestMapping(value = "/getListByResTypeCode.do")
	public String getListByResTypeCode(Integer typeCode, HttpServletResponse response) throws IOException{
		logger.info( "Enter getListByResTypeCode.do ..." );
		List<ResBizSystem> list=resBizSystemService.getByResTypeCode(typeCode);
		String jsonStr=resBizSystemService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
		return null;
	}

}
