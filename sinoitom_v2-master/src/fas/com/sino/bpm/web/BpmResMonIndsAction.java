package com.sino.bpm.web;

import com.sino.base.common.util.WebFuncUtils;
import com.sino.bpm.entity.BpmResMonInds;
import com.sino.bpm.entity.BpmTaskMonRes;
import com.sino.bpm.service.BpmResMonIndsService;
import com.sino.bpm.service.BpmTaskMonResService;
import com.sino.bpm.service.CmdbResIndCmdParseService;
import com.sino.bpm.service.CmdbResIndSnmpOidService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BpmResMonIndsAction
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/13 9:57
 * @Version 1.0
 **/
@RequestMapping(value="/bpm/resMonInds")
@Controller
public class BpmResMonIndsAction {
    private String viewPath = "/bpm/resMonInds";
    private static Logger logger= LoggerFactory.getLogger(BpmResMonIndsAction.class);

    @Autowired
    private BpmResMonIndsService bpmResMonIndsService;
    @Autowired
    private BpmTaskMonResService bpmTaskMonResService;
    @Autowired
    private CmdbResIndCmdParseService cmdbResIndCmdParseService;
    @Autowired
    private CmdbResIndSnmpOidService cmdbResIndSnmpOidService;

    /*
    * @author fengyao
    * @Description 列表
    * @Date 16:04 2020/5/13
    * @Param [menuId, map]
    * return java.lang.String
    **/
    @RequestMapping(value = "/mainTab.do")
    public String mainTab(String menuId, ModelMap map) {
        logger.info("Enter mainTab.do...");
        List<ParamTree> total = new ArrayList<ParamTree>();
        ParamTree t = new ParamTree();
        t.setId("1_1");
        t.setText("IT资源");
        t.setPid("0");
        t.setIsexpand(true);
        total.add(t);
        List<ResItemParam> classCodes = bpmTaskMonResService.getResClassCodes();
        if (!classCodes.isEmpty()) {

            for (int i = 0; i < classCodes.size(); i++) {
                ParamTree pt = new ParamTree();
                ResItemParam rc = classCodes.get(i);
                pt.setPid("1_1");
                pt.setId(rc.getId() + "_2");
                pt.setText(rc.getText());
                total.add(pt);
                List<ResItemParam> typeCodes = bpmTaskMonResService.getResTypeCodes(Integer.parseInt(rc.getId()));

                if (!typeCodes.isEmpty()) {
                    for (int j = 0; j < typeCodes.size(); j++) {
                        ParamTree ptj = new ParamTree();
                        ResItemParam rp = typeCodes.get(j);
                        ptj.setPid(rc.getId() + "_2");
                        ptj.setId(rc.getId() + "_" + rp.getId() + "_3");
                        ptj.setText(rp.getText());
                        total.add(ptj);
                        List<Object[]> resouces = bpmTaskMonResService.getResNameByTypeCode(Integer.parseInt(rp.getId()));
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
        return viewPath+"/mainTab";
    }

    /*
    * @author fengyao
    * @Description TODO
    * @Date 10:55 2020/5/21
    * @Param [id, map]
    * return java.lang.String
    **/
    @RequestMapping(value = "/cmdMain.do")
    public String cmdMain(String id, ModelMap map) {
        logger.info("Enter cmdMain.do ...");
        map.put("id", id);
        return viewPath + "/cmdMain";
    }

    /*
    * @author fengyao
    * @Description TODO
    * @Date 10:55 2020/5/21
    * @Param [id, map]
    * return java.lang.String
    **/
    @RequestMapping(value = "/snmpMain.do")
    public String snmpMain(String id, ModelMap map) {
        logger.info("Enter snmpMain.do ...");
        map.put("id", id);
        return viewPath + "/snmpMain";
    }

}
