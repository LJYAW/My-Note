package com.sino.bpm.web;

import com.sino.base.system.entity.JqPageBean;
import com.sino.bpm.entity.BpmResIndCmdParse;
import com.sino.bpm.service.BpmResIndCmdParseService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName BpmResIndCmdParseAction
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/21 13:54
 * @Version 1.0
 **/
@RequestMapping(value="/bpm/bpmResIndCmdParse")
@Controller
public class BpmResIndCmdParseAction {
    private String viewPath = "/bpm/resMonInds";
    private static Logger logger= LoggerFactory.getLogger(BpmResIndCmdParseAction.class);

    @Autowired
    private BpmResIndCmdParseService bpmResIndCmdParseService;

    @RequestMapping(value="/search.do")
    public String search(String id, PageRequest pageRequest, JqPageBean page, HttpServletResponse response) throws IOException {
        logger.info( "Enter search.do ..." );

        pageRequest.setPageNo(page.getPage());
        pageRequest.setPageSize(page.getRows());
        pageRequest.setOrderBy(page.getSidx());
        pageRequest.setOrderDir(page.getSord());
        String jsonStr="{}";

        String flag=id.substring(id.lastIndexOf("_")+1, id.length());

        Page<BpmResIndCmdParse> pages=null;

        if(flag.equals("1")){
            pages=bpmResIndCmdParseService.findAjaxSearch(pageRequest,null,null,null);
        }else if(flag.equals("2")){
            String [] classCodes=id.split("_");
            pages=bpmResIndCmdParseService.findAjaxSearch(pageRequest,Integer.parseInt(classCodes[0]),null,null);
        }else if(flag.equals("3")){
            String [] typeCodes=id.split("_");
            pages=bpmResIndCmdParseService.findAjaxSearch(pageRequest,Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),null);
        }else if(flag.equals("4")){
            String [] typeCodes=id.split("_");
            pages=bpmResIndCmdParseService.findAjaxSearch(pageRequest,Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),typeCodes[2]);
        }

        jsonStr=bpmResIndCmdParseService.getJsonPageList(pages);

        response.getWriter().print(jsonStr);
        return null;
    }

    @RequestMapping(value = "/delete.do")
    public String delete(String ids,  HttpServletResponse response) throws IOException {
        logger.info("Enter delete.do..");
        if (StringUtils.isNotBlank(ids)) {
            String[] idArr = ids.split(",");
            bpmResIndCmdParseService.deletes(idArr);
        }
        JSONObject jo = new JSONObject();
        jo.put("success", "0");
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }
}
