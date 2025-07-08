package com.sino.fas.res.host.web;

import com.sino.base.common.util.*;
import com.sino.base.system.entity.JqPageBean;
import com.sino.base.system.entity.SysUser;
import com.sino.cmdb.indicator.cmdCheckItems.service.JschUtils;
import com.sino.fas.res.host.entity.*;
import com.sino.fas.res.host.service.*;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.service.NcmDevAccessUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.utils.Identities;
import smartlink.utils.Util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @ProjectName: SinoACS
 * @Package: com.sino.fas.res.host.web
 * @ClassName: ResComputerHostsAction
 * @auther: Mr.Lp
 * @date: 2020/5/9 9:44
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Controller
@RequestMapping(value = "/fas/res/compHosts")
public class ResComputerHostsAction {

    private static Logger logger = LoggerFactory.getLogger(ResComputerHostsAction.class);
    private String viewPath = "/fas/res/compHosts";

    @Autowired
    private ResHostsService resHostsService;

    @Autowired
    private ResCompHostsService resCompHostsService;

    @Autowired
    private NcmDevAccessUserService devAccessUserService;

    @Autowired
    private IpHostService ipHostService;

    @Autowired
    private ResHostCpuService resHostCpuService;

    @Autowired
    private ResHostDisksService resHostDisksService;

    @Autowired
    private ResHostInterfaceService resHostInterfaceService;

    @Autowired
    private ResHostFileSystemService resHostFileSystemService;


    @RequestMapping(value = "/main.do")
    public String main(String menuId, ModelMap map) {

        logger.info( "Enter main.do ..." );
        List<ResHosts> hostsList=resHostsService.getHostsByHostType();
        String jsonStr = resHostsService.getJsonListStr(hostsList);
        map.put("jsonListData", jsonStr);
        WebFuncUtils.titleContent(menuId,map);
        return viewPath+"/main";
    }

    /**
     * @auther: Mr.Lp
     * @desc: 根据宿主机Id获取对应的虚拟机信息（子列表展示）
     * @data: 2020/5/13
     * @param: [id, response]
     * @return:
     */
    @RequestMapping(value = "/getResHostsByHostId.do")
    public String getResHostsByHostId(String id, HttpServletResponse response, JqPageBean page) throws IOException {
        logger.info("Enter getResHostsByHostId.do ...");
        if(StringUtils.isNotBlank(id)){
            List<ResHosts> hostList = new ArrayList<ResHosts>();
            hostList = resHostsService.getHostsByHomedHostIdAndParam(Long.parseLong(id),page.getSidx(),page.getSord());
            String jsonStr = resHostsService.getJsonListStr(hostList);
            response.getWriter().print(jsonStr);
        }
        return null;
    }

    /**
     * @auther: Mr.Lp
     * @desc: 宿主机监测添加页面跳转
     * @data: 2020/5/13
     * @param: [map]
     * @return:
     */
    @RequestMapping(value = "/hostCompAdd.do")
    public String hostAdd(ModelMap map) {
        logger.info( "Enter hostCompAdd.do ..." );
        ResHosts resHosts=new ResHosts();
        map.put("ipHost", resHosts);
        return viewPath+"/hostCompadd";
    }

    /**
     * @auther: Mr.Lp
     * @desc: 宿主机监测
     * @data: 2020/4/24
     * @param: [action, resHosts, map]
     * @return:
     */
    @RequestMapping(value = "/hostCompAddsave.do")
    public String hostCompAddsave(ResHosts entity ,String username,String password, String orgName, ModelMap map) throws Exception{
        logger.info( "Enter searchAddsave.do ..." );
        String serverName = entity.getIpAddress();
        String url = "https://" + serverName + "/sdk/vimService";

        IpHost ipHost = ipHostService.getIpHostByIpAddr(serverName);
        List<ResHosts> list = PCollector.hostCompMonitor(entity,ipHost,username,password,url);

        if(list!=null&&list.size()>0){

            NcmDevAccessUser devAccess = new NcmDevAccessUser();
            SysUser curUser = SystemUtils.getLoginUser();
            String operator = curUser.getLoginName();
            devAccess.setOrgId(entity.getOrgID());
            devAccess.setAccessTool(entity.getAccessmode());
            devAccess.setAccessPort(entity.getAccessPort());
            devAccess.setUserName(username);
            devAccess.setPassWord(password);
            devAccess.setAcsUserName(orgName+"-"+entity.getIpAddress()+"-"+entity.getAccessmode()+"-"+"普通用户");
            Date now = new Date();
            devAccess.setModifyTime(now);
            devAccess.setCreator(operator);
            devAccess.setCreateTime(now);
            devAccess.setStatus(1);
            devAccess.setUserType(0);
            devAccess.setAccessTool("VMware VSphere API");
            devAccessUserService.addDevAccess(devAccess);
            resHostsService.saveList(devAccess,list);

            map.put("result", "success");

        }else{
            map.put("result", "error");
        }

        return viewPath+"/hostCompadd";
    }

    /**
     * @auther: Mr.Lp
     * @desc: 宿主机监测添加时查询是否添加重复
     * @data: 2020/5/13
     * @param: [map, ipAddress, response]
     * @return: 
     */
    @RequestMapping(value="/checkHost.do")
    public String checkHost(ModelMap map,String ipAddress,HttpServletResponse response) throws IOException {
        logger.info("Enter checkHost.do..");
        JSONObject jo = new JSONObject();
        boolean validate = true;
        SysUser curUser = SystemUtils.getLoginUser();
        if( curUser == null ){
            validate = false;
            jo.put("message", "系统登录超时！");
        }
        if( validate ){
            ResHosts host = resHostsService.getByIpAddress(ipAddress);
            if(host!=null){
                jo.put("error", "此IP资源已经添加监测，请重新添加！");
            }else{
                jo.put("success", "success");
            }
        }

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    /**
     * @auther: Mr.Lp
     * @desc: 级联删除:删除宿主机和其下所有宿主机，以及添加宿主机时所添加的对应的用户名密码信息（Ncm_DevAccessUser）
     * @data: 2020/5/13
     * @param: [id, response]
     * @return: 
     */
    @RequestMapping(value = "/delete.do")
    public String delete(String id, HttpServletResponse response) throws IOException {
        logger.info( "Enter delete.do ..." );

        if(!StringUtil.isNullString(id)){

            //以下为子表的级联删除操作，注销是因为直接在表中添加了外键级联（在数据库表中删除主表数据后会直接自动删除相关子表数据），如果不注销会报错
            ResHosts host = resHostsService.getById(Long.parseLong(id));
            NcmDevAccessUser devAccessUser = devAccessUserService.getById(host.getDevAcsUserId());

            List<ResHosts> hostList = new ArrayList<>();
            hostList = resHostsService.getSvrIpAddrByIdAndType(Long.parseLong(id),0);

            List<ResCompHosts> compHostList = new ArrayList<ResCompHosts>();
            compHostList = resCompHostsService.getByCompHostId(Long.parseLong(id));

            resHostsService.deleteHostAndCompHost(Long.parseLong(id),hostList,compHostList,devAccessUser);
            response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        }
        return null;
    }

    /**
     * @auther: Mr.Lp
     * @desc: 获取配置信息页面跳转
     * @data: 2020/5/13
     * @param: [id, map]
     * @return:
     */
    @RequestMapping(value = "/getVMInfo.do")
    public String getVMInfo(Long id,ModelMap map) {
        logger.info( "Enter getVMInfo.do ..." );
        ResHosts resHosts=resHostsService.getById(id);
        String devAcsUserId = resHosts.getDevAcsUserId();
        if(devAcsUserId!=null){
            NcmDevAccessUser devAccess = devAccessUserService.getById(devAcsUserId);
            map.put("devAccess",devAccess);
            map.put("devAcsUserId",devAcsUserId);
        }
        String ip = resHosts.getIpAddress();
        map.put("ip",ip);
        map.put("ipHost", resHosts);
        map.put("id", id);
        return viewPath+"/addVmInfo";
    }

    /**
     * @auther: Mr.Lp
     * @desc: 获取配置信息并保存
     * @data: 2020/5/13
     * @param: [id, username, password, orgName, resHosts, map]
     * @return:
     */
    @RequestMapping(value = "/addVmInfoSave.do")
    public String addVmInfoSave(Long id,String devAcsUserId,String userName,String passWord,String orgName,ResHosts resHosts,ModelMap map) {
        logger.info( "Enter addVmInfoSave.do ..." );
        ResHosts editHost = new ResHosts();
        System.out.println(resHosts);
        boolean validate = true;
        SysUser curUser = SystemUtils.getLoginUser();
        if( curUser == null ){
            validate = false;
            map.put("message", "系统登录超时！");
        }

        if(validate){
            editHost = resHostsService.getById(id);
            String homedHostIp = editHost.getHomedHostIp();
            if(id!=null&&editHost!=null){
                BeanUtils.copyProperties(resHosts, editHost);
                editHost.setHomedHostIp(homedHostIp);
                editHost.setIpLong(Util.ip2long(editHost.getIpAddress()));

                String operator = curUser.getLoginName();
                Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
                editHost.setModifier(operator);
                editHost.setModifyTime(now);

                Date datenow = new Date();
                NcmDevAccessUser devAccess = new NcmDevAccessUser();
                if(StringUtils.isEmpty(devAcsUserId)){
//                    NcmDevAccessUser devAccess = new NcmDevAccessUser();
                    devAccess.setOrgId(editHost.getOrgID());
                    devAccess.setAccessTool(editHost.getAccessmode());
                    devAccess.setAccessPort(editHost.getAccessPort());
                    devAccess.setUserName(userName);
                    devAccess.setPassWord(passWord);
                    devAccess.setAcsUserName(orgName+"-"+editHost.getIpAddress()+"-"+editHost.getAccessmode()+"-"+"普通用户");
                    devAccess.setCreator(operator);
                    devAccess.setCreateTime(datenow);
                    devAccess.setStatus(1);
                    devAccess.setUserType(0);
                    devAccess.setDevAcsUserId(Identities.uuid());
//                    devAccessUserService.addDevAccess(devAccess);

                    editHost.setDevAcsUserId(devAccess.getDevAcsUserId());

                }else{
                    devAccess = devAccessUserService.getById(devAcsUserId);
                    devAccess.setAccessPort(editHost.getAccessPort());
                    devAccess.setUserName(userName);
                    devAccess.setPassWord(passWord);
                    devAccess.setAccessTool(editHost.getAccessmode());
                    devAccess.setModifier(operator);
                    devAccess.setModifyTime(datenow);

                    editHost.setDevAcsUserId(devAccess.getDevAcsUserId());

                }

                //保存虚拟机信息时同步各项指标信息
                String str = hostResSync(id.toString(),userName,passWord,editHost.getAccessPort());
                if(str.equals("success")){
                    devAccessUserService.saveDevAccess(devAccess);
                    resHostsService.save(editHost);
                    map.put("result", "success");
                }else{
                    map.put("result", "error");
                    map.put("message", str);
                    if(id!=null){
                        map.put("id", id);
                    }
                }


            }else{
                map.put("result", "error");
                map.put("message", "编辑失败！");
            }
        }

        return viewPath+"/addVmInfo";
    }


    /**
     * @auther: Mr.Lp
     * @desc: 保存虚拟机信息时，抓取虚拟机的各性能指标
     * @data: 2020/5/21
     * @param: [id, userName, passWord, port]
     * @return:
     */
    public String hostResSync(String id,String userName,String passWord,Integer port) {

        String message = null;
        boolean validate = true;
        JSONObject jo = new JSONObject();
        SysUser curUser = SystemUtils.getLoginUser();
        if( curUser == null ){
            validate = false;
            message = "系统登录超时,同步失败！";
        }
        if( validate ){
            //新的
            ResHosts hosts = new ResHosts();

            if(!StringUtil.isNullString(id)){
                //原来的
                ResHosts resHosts = resHostsService.getById(Long.parseLong(id));
                String hostIp = resHosts.getIpAddress();

                JschUtils jschUtils = null;
                try {
                    jschUtils = new JschUtils(hostIp, userName, passWord,port);

                    jschUtils.initShellChannel();
                    jschUtils.getPrompt();

                    hosts = LinuxTargetUtil.getCmdResult(jschUtils,resHosts);		//获取命令结果解析并存储到实体类中
                    if(hosts!=null){
                        BeanUtils.copyProperties(hosts, resHosts);

                        //SysUser curUser = SystemUtils.getLoginUser();
                        resHosts.setModifier(curUser.getUserName());
                        Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
                        resHosts.setModifyTime(now);
//                        resHosts.setOsClass("Linux");

                        List<ResHostCpu> cpuList = resHostCpuService.getByHostId(resHosts.getHostId());
                        resHostCpuService.deleteList(cpuList);

                        List<ResHostFileSystem> fileList = resHostFileSystemService.getByHostId(resHosts.getHostId());
                        resHostFileSystemService.deleteList(fileList);

                        List<ResHostInterface> interfaceList = resHostInterfaceService.getByHostId(resHosts.getHostId());
                        resHostInterfaceService.deleteList(interfaceList);

                        List<ResHostDisks> hostDiskList = resHostDisksService.getByHostId(resHosts.getHostId());
                        resHostDisksService.deleteList(hostDiskList);

                        List<ResHostCpu> hostCpulist = LinuxTargetUtil.getCpus(jschUtils,resHosts);
                        List<ResHostFileSystem> fileSyslist = LinuxTargetUtil.getfileSys(jschUtils,resHosts);
                        List<ResHostInterface> infoList = LinuxTargetUtil.getInterfaces(jschUtils,resHosts);
                        List<ResHostDisks> disksList = null;
                        if(resHosts.getOsType().equals("SUSE")){		//(操作系统类型为：SUSE)
                            disksList = LinuxTargetUtil.getSUSEDisks(jschUtils,resHosts);
                        }else{		//(操作系统类型为：CentOS，redhat)
                            disksList = LinuxTargetUtil.getDisks(jschUtils,resHosts);
                        }
//							List<ResHostDisks> disksList = getDisks(jschUtils,resHosts);

                        resHostsService.batchSave(resHosts, hostCpulist,fileSyslist, infoList, disksList);
                        message = "success";
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    message = "请检查服务器是否开启，或账号密码是否错误！";
                }


            }
        }

        return message;
    }


}
