<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务系统详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">

$(function (){
	var svrType=$("#svrType").val();
    if(svrType==0){
    	$("#svrType").val("虚拟机");
    	$(".line").show();
    	
    }else if(svrType==1){
    	$("#svrType").val("物理机");
    	$(".line").hide();
    }else if(svrType==2){
    	$("#svrType").val("集群");
    	$(".line").hide();
    }
    
    $("input").filter(".ip1").ligerTextBox({ width: 200 });
});



</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="resBizSystem" action="" method="post">
	
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
			
              <tr>
                <td align="right">组织机构：</td>
                <td>
                <input id="orgName" name="orgName" value="${resBizSystem.orgName}" type="text" ltype="text" class=" ip1" readonly="readonly"/>
				</td>
                </td>
                <td></td>
               <td align="right">资源分类 ：</td> 
                <td>
                 <input id="resClassName" name="resClassName" value="${resBizSystem.resClassName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
                <td></td>
            </tr>
             <tr>
                <td align="right">系统类型：</td>
                <td>
                <input id="resTypeName" name="resTypeName" value="${resBizSystem.resTypeName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               <td></td>
               <td align="right">系统子类：</td>
               <td>
               <input id="bizTypeName" name="bizTypeName" value="${resBizSystem.bizTypeName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
               </td>  
               <td></td>
            </tr>
            
			
            <tr>
                <td align="right" nowrap>系统英文名称：</td>
                <td>
                <input id="sysEnName" name="sysEnName" value="${resBizSystem.sysEnName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               <td></td>
               <td align="right" nowrap>系统中文名称：</td>
                <td>
                <input id="sysName" name="sysName" value="${resBizSystem.sysName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               <td></td>
            </tr>
             <tr>
                <td align="right">业务系统描述：</td>
                <td colspan="4"><input id="bizDesc" name="bizDesc" value="${resBizSystem.bizDesc}" type="text" ltype="text" class=" ip2"readonly="readonly"/></td>
                <td ></td>
            </tr>
            
             <tr>
                <td align="right">系统访问方式：</td>
                <td>
                <input id="accessMode" name="accessMode" value="${resBizSystem.accessMode}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               <td></td>
               <td align="right">访问协议：</td>
               <td>
               <input id="accessProt" name="accessProt" value="${resBizSystem.accessProt}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
               </td>  
               <td></td>
            </tr>
            <tr>
                <td align="right">服务器类型：</td>
                <td>
                <input id="svrType" name="svrType" value="${resBizSystem.svrType}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               <td></td>
               <td align="right" class="line">宿主机：</td>
               <td>
               <input id="svrId" name="svrId" value="${resBizSystem.svrId}" type="text" ltype="text" class=" ip1 line"readonly="readonly"/>
               </td>  
               <td class="line"></td>
            </tr>
             <tr>
                <td align="right" nowrap>服务器IP地址：</td>
                <td>
                <input id="svrIpAddr" name="svrIpAddr" value="${resBizSystem.svrIpAddr}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               <td></td>
               <td align="right">服务端口：</td>
               <td>
                  <input id="svcPort" name="svcPort" value="${resBizSystem.svcPort}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>访问地址(URL)：</td>
                <td colspan="4"><input id="bizURL" name="bizURL" value="${resBizSystem.bizURL}" type="text" ltype="text" class=" ip2"readonly="readonly"/></td>
                <td ></td>
            </tr>
            
           
		</table>
    </form:form>
</body>
</html>

