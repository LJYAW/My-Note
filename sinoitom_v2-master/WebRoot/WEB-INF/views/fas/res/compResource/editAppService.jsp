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
<title>服务信息维护</title>
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
	$(function ()
		{
            <c:if test="${result=='success'}">
	        	window.parent.location.reload(); 
	        	var mylay = parent.layer.getFrameIndex(window.name);
	        	//parent.layer.alert("服务信息维护成功！");
	        	parent.layer.close(mylay);
            </c:if>
			$("#formSave").validationEngine({promptPosition: "centerRight"});
			<c:if test="${result=='error'}">
	        $.ligerDialog.error('${message}');
			</c:if>	
			$("#formSave").validationEngine();
			//表单验证
			$("#formSave").click(function(check) {    
		    	if($("#formSave").validationEngine()){
		    	}else{
		    		check.preventDefault();//此处阻止提交表单  
		    	}
			});
	});
		
</script>

</head>
<body style="overflow: hidden;">
	<form id="formSave" action="${ctx}/fas/res/host/resServiceProcess/appServiceSave.do" method="post">
		<input type="hidden" name="id" value="${id}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
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
				<td align="right">服务器IP：</td>
				<td><input id="hostIp" name="hostIp" value="${resAppService.hostIp}" type="text" readonly="readonly" class="ip1"/></td>
				<td></td>
				<td align="right">协议号：</td>
				<td><input id="protNo" name="protNo" value="${resAppService.protNo }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">协议名称：</td>
				<td>
					<input id="protName" name="protName" value="${resAppService.protName }" type="text" readonly="readonly" class="ip1"/>
				</td>
				<td></td>
				<td align="right">服务端口：</td>
				<td><input id="svcPort" name="svcPort" value="${resAppService.svcPort }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">服务名称：</td>
				<td><input id="svcName" name="svcName" value="${resAppService.svcName }" type="text" ltype="text" class="validate[required[服务名称不能为空]] ip1"  /></td>
				<td></td>
				<td align="right">服务命令：</td>
				<td><input id="command" name="command" value="${resAppService.command }" type="text" readonly="readonly" ltype="text" class="ip1"  /></td>
				<td></td>
			</tr>
			<tr>
			   	<td align="right">服务分类：</td>
				<td>
<%--		margin-left: -4px;			<input id="svcClass" name="svcClass" value="${resAppService.svcClass }" type="text"  ltype="text" class="ip1"  />--%>
					<select name="svcClass" id="svcClass" style="width: 200px;height:22px; ">
					    <option value="1" <c:if test="${resAppService.svcClass==1}">selected</c:if> >Web</option>   
  						<option value="2" <c:if test="${resAppService.svcClass==2}">selected</c:if> >App</option>
  						<option value="3" <c:if test="${resAppService.svcClass==3}">selected</c:if> >DB</option>
					</select>
				</td>
				<td></td>
				<td align="right">应用服务名称：</td>
				<td><input id="appSvcName" name="appSvcName" value="${resAppService.appSvcName }"  type="text" ltype="text" class="validate[required[应用服务名称不能为空]] ip1"  /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">服务描述：</td>
				<td><input id="appSvcDescr" name="appSvcDescr" value="${resAppService.appSvcDescr }" type="text" ltype="text" class="validate[required[服务描述不能为空]] ip1"  /></td>
				<td ></td>
				<td align="right">用途描述：</td>
				<td><input id="usageDescr" name="usageDescr"  type="text" value="${resAppService.usageDescr}" class="validate[required[用途描述不能为空]] ip1"  /></td>
				<td ></td>
			</tr>

			<tr>
           		<td align="right" nowrap="nowrap">服务访问URL：</td>
				<td colspan=5><input id="svcUrl" name="svcUrl" value="${resAppService.svcUrl }" type="text" ltype="text" class=" ip2"  /></td>
            </tr>
            
			<tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form>
    <br/>
</body>
</html>

