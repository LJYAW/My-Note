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
<title>交换机接口查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">

	$(function ()
	{		
		<c:if test="${result=='success'}">
		window.parent.saveOK();  
		</c:if>
		<c:if test="${result=='success' && closeDlg=='true'}">
		window.parent.closeDlg();  
		</c:if>

        $("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
        $("#bnSave").click(function(){
        	$("#closeDlg").val("true");
        });
        
        $("#bnSaveAdd").click(function(){
        	$("#closeDlg").val("false");
        });

        $("#bnClose").click(function(){
        	window.parent.closeDlg();
        });

		<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>	
		<c:if test="${result=='success' && closeDlg=='false'}">
        $.ligerDialog.success('保存成功！'); 
		</c:if>
		<c:if test="${result=='success' && action=='add'}">
		$("input[ltype='text']").val("");
		</c:if>
	});
	
</script>

</head>
<body> 
	<form id="formSave" action="${ctx}/fas/res/net/switch/interface/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="switchId" value="${itf.id.switchId}"/>
		<input type="hidden" name="ifIndex" value="${itf.id.ifIndex}"/>
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
                <td align="right">接口名称:</td>
                <td><input id="ifName" name="ifName" value="${itf.ifName}" type="text" ltype="text" class="validate[required,maxSize[128]] ip1" /></td>
                <td></td>
                <td align="right">接口别名:</td>
                <td><input id="ifAliasName" name="ifAliasName" value="${itf.ifAliasName}" type="text" ltype="text" class="validate[required,maxSize[128]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">接口号:</td>
                <td><input id="portNo" name="portNo" value="${itf.portNo}" ltype='spinner' ligerui="{type:'int'}" class="validate[optional,min[1]]"/></td>
                <td></td>
                <td align="right">是否可用:</td>
                <td><s:resSelect name="availablity" value="${itf.availablity}" code="SWPort_availablity" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">连接设备位置:</td>
                <td><input id="location" name="location" value="${itf.location}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
                <td></td>
                <td align="right">多MCA端口:</td>
                <td><s:resSelect name="multiMacPort" value="${itf.multiMacPort}" code="SWPort_multiMacPort" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">管理状态:</td>
                <td><s:resSelect name="adminStatus" value="${itf.adminStatus}" code="Subnet_AdminStatus" ltype="select" /></td>
                <td></td>
                <td align="right">工作状态:</td>
                <td><s:resSelect name="operStatus" value="${itf.operStatus}" code="Subnet_OperStatus" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">上次变更时间:</td>
                <td><s:date value="${itf.lastChangeTime}" /></td>
                <td></td>
                <td align="right">状态更新时间:</td>
                <td><s:date value="${itf.portStatusChangeTime}" /></td>
                <td></td>
            </tr>
		</table>
    </form>
</body>
</html>

