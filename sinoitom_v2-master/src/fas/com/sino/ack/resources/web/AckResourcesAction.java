package com.sino.ack.resources.web;

import com.alibaba.fastjson.JSON;
import com.sino.ack.resources.dao.AckResourcesDao;
import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.entity.ParamSnmpRes;
import com.sino.ack.resources.service.AckResourcesService;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.JqPageBean;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.service.OrganService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodModel.entity.VendorProdModel;
import com.sino.cmdb.product.prodModel.service.VendorProdModelService;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.dao.CmdbVendorDao;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.net.dao.NcmDevicesDao;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;
import com.sino.fas.res.net.service.NcmDevAccessUserService;
import com.sino.fas.res.net.service.NcmDevicesService;
import com.sino.fas.res.net.service.SnmpCredService;
import com.sino.utils.common.StringUtil;
import com.sinobridge.nms.controller.ack.AutoCheckController;
import com.sinobridge.nms.core.collection.ack.pojo.AckDevCheckData;
import com.sinobridge.nms.core.model.ResponseMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/ack/resources")
@Controller
public class AckResourcesAction {
	private String viewPath = "/ack/resources";
	private static Logger logger = LoggerFactory.getLogger(AckResourcesAction.class);

	@Autowired
	private AckResourcesService ackResourcesService;

	@Autowired
	private AckResourcesDao ackResourcesdao;
	@Autowired
	private CmdbVendorDao cmdbVendorDao;
	@Autowired
	private NcmDevicesDao ncmDevicesDao;
	@Autowired
	private NcmDevicesService ncmDevicesService;
	@Autowired
	private VendorProdModelService vendorProdModelService;
	@Autowired
	private OrganService organService;
	@Autowired
	private SnmpCredService snmpCredService;

	@Autowired
	private NcmDevAccessUserService ncmDevAccessUserService;

	@RequestMapping(value = "/main.do")
	public String main(String id, ModelMap map) {
		logger.info("Enter main.do ...");
		//		List<AckResources> ackResources=ackResourcesService.getAll();
		//		String jsonStr = ackResourcesService.getJsonListStr(ackResources);
		//		map.put("jsonListData", jsonStr);
		map.put("id", id);
		return viewPath + "/main";
	}

	@RequestMapping(value = "/mainTab.do")
	public String getTree(String menuId, ModelMap map, String type) {
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree t = new ParamTree();
		t.setId("1_1");
		t.setText("IT资源");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		List<ResItemParam> classCodes = ackResourcesdao.getResClassCodes();
		if (!classCodes.isEmpty()) {

			for (int i = 0; i < classCodes.size(); i++) {
				ParamTree pt = new ParamTree();
				ResItemParam rc = classCodes.get(i);
				pt.setPid("1_1");
				pt.setId(rc.getId() + "_2");
				pt.setText(rc.getText());
				total.add(pt);
				List<ResItemParam> typeCodes = ackResourcesdao.getResTypeCodes(Integer.parseInt(rc.getId()));

				if (!typeCodes.isEmpty()) {
					for (int j = 0; j < typeCodes.size(); j++) {
						ParamTree ptj = new ParamTree();
						ResItemParam rp = typeCodes.get(j);
						ptj.setPid(rc.getId() + "_2");
						ptj.setId(rc.getId() + "_" + rp.getId() + "_3");
						ptj.setText(rp.getText());
						total.add(ptj);
						List<Object[]> resouces = ackResourcesdao.getResNameByTypeCode(Integer.parseInt(rp.getId()));
						if (resouces.size() > 0) {
							for (int k = 0; k < resouces.size(); k++) {
								Object[] obj = resouces.get(k);
								ParamTree tree = new ParamTree();
								tree.setId(rc.getId() + "_" + rp.getId() + "_" + obj[0] + "_4");
								tree.setText(obj[1] + "");
								tree.setPid(rc.getId() + "_" + rp.getId() + "_3");
								total.add(tree);
							}
						}
					}
				}
			}
		}

		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		map.put("menuId", menuId);
		WebFuncUtils.titleContent(menuId, map);
		return "/ack/resources/mainTab";

	}

	@RequestMapping(value = "/search.do")
	public String search(String id, PageRequest pageRequest, JqPageBean page, HttpServletRequest resquest,
			HttpServletResponse response, ModelMap map) throws IOException {
		logger.info("Enter search.do ...");

		pageRequest.setPageNo(page.getPage());
		pageRequest.setPageSize(page.getRows());
		pageRequest.setOrderBy(page.getSidx());
		pageRequest.setOrderDir(page.getSord());
		String jsonStr = "{}";

		List<AckResources> list = new ArrayList<AckResources>();
		String flag = id.substring(id.lastIndexOf("_") + 1, id.length());

		Page<AckResources> pages = null;

		if (flag.equals("1")) {
			pages = ackResourcesService.findAjaxSearch(pageRequest, null, null, null);
		} else if (flag.equals("2")) {
			String[] classCodes = id.split("_");
			pages = ackResourcesService.findAjaxSearch(pageRequest, Integer.parseInt(classCodes[0]), null, null);
		} else if (flag.equals("3")) {
			String[] typeCodes = id.split("_");
			pages = ackResourcesService
					.findAjaxSearch(pageRequest, Integer.parseInt(typeCodes[0]), Integer.parseInt(typeCodes[1]), null);
		} else if (flag.equals("4")) {
			String[] typeCodes = id.split("_");

			pages = ackResourcesService
					.findAjaxSearch(pageRequest, Integer.parseInt(typeCodes[0]), Integer.parseInt(typeCodes[1]),
							typeCodes[2]);
		}

		jsonStr = ackResourcesService.getJsonPageList(pages);

		response.getWriter().print(jsonStr);
		return null;
	}

	@RequestMapping(value = "/add.do")
	public String add() {
		logger.info("Enter add.do...");
		return viewPath + "/addAckResources";
	}

	//获取和device,switch有关的厂商
	@RequestMapping(value = "/getVendorData.do")
	public String getVendorData(String devType, String classCode, HttpServletResponse response) throws IOException {
		logger.info("Enter getVendorData.do...");

		//List<String> vendorIds=new ArrayList<String>();   //从device,seitch表中获取的vendorID(String)
		List<Integer> vendorIDs = new ArrayList<Integer>();  //从VendorOID表中获取的vendorID(Integer)
		List<CmdbVendor> vendors = new ArrayList<CmdbVendor>();

		if ("13".equals(classCode)) {    //暂时满足前台要求（只支持路由器 交换机）
			if ("2".equals(devType)) {
				vendorIDs = ncmDevicesDao.getVendorIdBySwitch();
			} else {
				vendorIDs = ncmDevicesDao.getVendorIdByDevice();
			}
		}
		if (!vendorIDs.isEmpty()) {
			vendors = cmdbVendorDao.getByVendorIds(vendorIDs);
		}

		JSONObject jo = new JSONObject();
		jo.put("vendors", vendors);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());

		return null;
	}

	@RequestMapping(value = "/ajaxQuery.do")
	public String ajaxQuery(String devType, String devTypeName, String vendorID, String classCode, String classCodeName,
			HttpServletResponse response) throws IOException {
		logger.info("Enter ajaxQuery.do...");
		List<NcmDevices> deviceList = new ArrayList<NcmDevices>();
		List<NcmDevices> switchList = new ArrayList<NcmDevices>();
		List<ParamSnmpRes> pResList = new ArrayList<ParamSnmpRes>();

		//VendorOID vi=vendorOIDService.getByVendorID(vendorID);


		devTypeName = new String(devTypeName.getBytes("iso-8859-1"), "utf-8");
		classCodeName = new String(classCodeName.getBytes("iso-8859-1"), "utf-8");

		if (classCode.equals("13")) {
			if ("2".equals(devType)) {
				switchList = ncmDevicesService.getByVendorId(vendorID, devType);
			} else {
				deviceList = ncmDevicesService.getByVendorId(vendorID, devType);
			}
		}


		if (!deviceList.isEmpty()) {
			NcmDevices device = null;
			ParamSnmpRes pRes = null;
			for (int i = 0; i < deviceList.size(); i++) {
				device = deviceList.get(i);
				pRes = new ParamSnmpRes();
				NcmDevAccessUser access = ncmDevAccessUserService.getAccessByAccessId(device.getDevAccessId());
				if (access != null) {
					pRes.setUserName(access.getUserName());
					pRes.setAccessMode(access.getAccessTool());
				}
				//VendorOID v=vendorOIDService.getByVendorOID(String.valueOf(device.getVendorID()));
				//if(v!=null){
				pRes.setOrgID(device.getOrgId());
				OrgOrganization organ = organService.getOrgan(device.getOrgId());
				if (organ != null) {
					pRes.setOrgName(organ.getOrgShortName());
				}
				pRes.setVendorID(device.getVendorId());
				//}
				pRes.setResIP(device.getDevIpAddr());
				pRes.setResIpLong(device.getDevIpLong());
				pRes.setResID(device.getDevId());
				pRes.setResName(device.getDevName());
				pRes.setResClassCode(Integer.parseInt(classCode));
				pRes.setResClassName(classCodeName);
				pRes.setResTypeCode(Integer.parseInt(devType));
				pRes.setResTypeName(devTypeName);
				pRes.setModelOID(device.getModelOid());
				pRes.setOsType(device.getOsType());

				VendorProdModel model = vendorProdModelService.getByModelOID(device.getModelOid());
				if (model != null) {
					pRes.setResModel(model.getProdModelName());
				}

				NcmSnmpCredentials cred = snmpCredService.getSnmpCred(device.getSnmpCredId());
				if (cred != null) {
					pRes.setSnmpStr(cred.getSnmpRoStr());
				}

				pResList.add(pRes);
			}
		}

		if (!switchList.isEmpty()) {
			NcmDevices ncmSwitch = null;
			ParamSnmpRes pRes = null;
			for (int i = 0; i < switchList.size(); i++) {
				ncmSwitch = switchList.get(i);
				pRes = new ParamSnmpRes();
				NcmDevAccessUser access = ncmDevAccessUserService.getAccessByAccessId(ncmSwitch.getDevAccessId());

				if (access != null) {
					pRes.setUserName(access.getUserName());
					pRes.setAccessMode(access.getAccessTool());
				}
				//VendorOID v=vendorOIDService.getByVendorOID(String.valueOf(ncmSwitch.getVendorID()));
				pRes.setOrgID(ncmSwitch.getOrgId());
				OrgOrganization organ = organService.getOrgan(ncmSwitch.getOrgId());
				if (organ != null) {
					pRes.setOrgName(organ.getOrgShortName());
				}
				pRes.setVendorID(ncmSwitch.getVendorId());
				pRes.setResIP(ncmSwitch.getDevIpAddr());
				pRes.setResIpLong(ncmSwitch.getDevIpLong());
				pRes.setResID(ncmSwitch.getDevId());
				pRes.setResName(ncmSwitch.getDevName());
				pRes.setResClassCode(Integer.parseInt(classCode));
				pRes.setResClassName(classCodeName);
				pRes.setResTypeCode(Integer.parseInt(devType));
				pRes.setResTypeName(devTypeName);
				pRes.setModelOID(ncmSwitch.getModelOid());
				pRes.setOsType(ncmSwitch.getOsType());
				VendorProdModel model = vendorProdModelService.getByModelOID(ncmSwitch.getModelOid());
				if (model != null) {
					pRes.setResModel(model.getProdModelName());
				}
				NcmSnmpCredentials cred = snmpCredService.getSnmpCred(ncmSwitch.getSnmpCredId());
				if (cred != null) {
					pRes.setSnmpStr(cred.getSnmpRoStr());
				}
				pResList.add(pRes);
			}
		}
		String jsonStr = ackResourcesService.getParamJsonListStr(pResList);

		JSONObject jo = new JSONObject();
		jo.put("jsonStr", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo);
		return null;
	}

	@RequestMapping(value = "/saveAckResources.do")
	public String saveAckResources(int resClassCode, String resClassName, int resTypeCode, String resTypeName,
			String devJsonStrs, String hostJsonStrs, ModelMap map) {
		logger.info("Enter save.do..");
		if (!StringUtil.isNullString(devJsonStrs)) {
			List<ParamSnmpRes> resList = JsonUtils.getDTOList(devJsonStrs, ParamSnmpRes.class);
			ackResourcesService.saveAckResources(resList);
		} else if (!StringUtil.isNullString(hostJsonStrs)) {
			List<IpHostInfo> hostList = JsonUtils.getDTOList(hostJsonStrs, IpHostInfo.class);
			ackResourcesService.saveHostAckResource(hostList, resClassCode, resClassName, resTypeCode, resTypeName);
		}


		map.put("result", "success");
		return viewPath + "/addAckResources";
	}

	//关联检测指标
	@RequestMapping(value = "/addAckCheckItems.do")
	public String addAckCheckItems(String ids, String vendorID, String resTypeCode, String osType, String osVersion,
			String osFeature, ModelMap map) throws ParseException {
		logger.info("Enter addAckCheckItems.do ...");
		//			List<ThresholdInfo> threshold=thresholdInfoService.getAll();
		//			String jsonThresh = thresholdInfoService.getThreshJsonListStr(threshold);
		map.put("ids", ids);
		map.put("vendorID", vendorID);
		map.put("resTypeCode", resTypeCode);
		map.put("osType", osType);
		map.put("osVersion", osVersion);
		map.put("osFeature", osFeature);
		return viewPath + "/addAckCheckItems";
	}

	//详情
	@RequestMapping(value = "/view.do")
	public String view(Integer id, ModelMap map) {
		logger.info("Enter detail ...");
		map.put("action", "detail");
		AckResources ackResources = ackResourcesService.getById(id);
		map.put("ackResources", ackResources);
		return viewPath + "/view";
	}

	@RequestMapping(value = "/delete.do")
	public String delete(String ids, ModelMap map, HttpServletResponse response) throws IOException {
		logger.info("Enter delete.do..");
		if (!ids.isEmpty()) {
			String[] id = ids.split(",");
			ackResourcesService.deletes(id);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}

	/**
	 * 测试资源
	 * @throws IOException 远程调用异常
	 */
	@RequestMapping(value = "/experiment.do")
	public void experiment(@RequestBody long id, HttpServletResponse response) throws IOException {
		logger.info("ackResourcesAction enter experiment method");
		AutoCheckController ackRemoteService = AutoCheckController.getInstance();
		ResponseMessage<List<AckDevCheckData>> listResponseMessage = ackRemoteService.experimentAck(id);
		String result = JSON
				.toJSONString(listResponseMessage);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}

	@RequestMapping(value = "/toExperimentResult.do")
	public String experiment() {
		return viewPath + "/experiment";
	}
}
