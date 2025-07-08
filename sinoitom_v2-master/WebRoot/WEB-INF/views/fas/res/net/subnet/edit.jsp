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
<title>子网编辑</title>
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

var canMaskSelect = false;

	$(function ()
	{		
		<c:if test="${result=='success'}">
		window.parent.saveOK();  
		</c:if>
		<c:if test="${result=='success' && closeDlg=='true'}">
		window.parent.closeDlg();  
		</c:if>

        $("#mainOrg").ligerComboBox({
        	width: 196,
        	selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'orgId',
        	treeLeafOnly: false,
        	initValue: '${subnet.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:subnet.orgId}',
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false }
        });

        $("#subnetMaskSelect").ligerComboBox({
        	width: 196,
        	selectBoxWidth: 210, selectBoxHeight: 300, 
        	valueFieldID: 'subnetMask',
        	treeLeafOnly: false,
        	initValue: '${subnet.subnetMask}',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=subnetMask'), 
        		checkbox: false, nodeWidth: 120 },
        		onBeforeOpen: function(){canMaskSelect = true;},
	    		onSelected: function (id, text){
	    		if( !canMaskSelect ){
	    			return;
	    		}
	    		checkIpMask();
	       		}
        	}
        );
        
        checkIpMask();
        
        $("#subnetAddr").change(function(){
        	checkIpMask();
        });

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
		$("#formSave").validationEngine();
		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
	});
	
	function checkIpMask() {
		var ip = $("#subnetAddr").val();
		var mask = $("#subnetMask").val();
		if( ip==null || ip=="" || mask==null || mask=="" ){
			$("#IpStart").val("");
			$("#IpEnd").val("");
			return;
		}
		
		var masknum = getallmarknum(mask);
		var iplan = iptolan(ip+"/"+masknum);
		var ipMin = mininlan(iplan);
		ip = numtoip(ipMin);
		var ipStart = masknum==32?numtoip(ipMin):numtoip(ipMin+1);
		var ipEnd = masknum==32?ipStart:numtoip(maxinlan(iplan)-1);

		$("#subnetAddr").val(ip);
		$("#IpStart").val(ipStart);
		$("#IpEnd").val(ipEnd);
	}
$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="subnet" action="${ctx}/fas/res/net/subnet/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="id" value="${subnet.netId}"/>
		<input type="hidden" id="orgId" name="orgId" value="${subnet.orgId}"/>
		<input type="hidden" id="subnetMask" name="subnetMask" value="${subnet.subnetMask}"/>
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
                <td align="right">所属机构:</td>
                <td colspan=4><input id="mainOrg" type="text" class="ip1"/></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">子网IP地址:</td>
                <td><input id="subnetAddr" name="subnetAddr" value="${subnet.subnetAddr}" type="text" ltype="text" class="validate[required[子网IP地址不能为空],custom[ipv4]] ip1" /></td>
                <td class="required"></td>
                <td align="right">子网掩码:</td>
                <td><input id="subnetMaskSelect" name="subnetMaskSelect" type="text" class="validate[required[子网掩码不能为空]] ip1" /></td>
                <td class="required"></td>
            </tr>
            <tr>
                <td align="right">子网起始IP:</td>
                <td><input id="IpStart" name="IpStart" value="${s:long2Ip(subnet.hostIpStart)}" type="text" ltype="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">子网结束IP:</td>
                <td><input id="IpEnd" name="IpEnd" value="${s:long2Ip(subnet.hostIpEnd)}" type="text" ltype="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">地址类型:</td>
                <td><s:resSelect name="subnetType" value="${subnet.subnetType}" code="Subnet_Type" ltype="select" /></td>
                <td></td>
                <td align="right">子网类型:</td>
                <td><s:resSelect name="subnetUsage" value="${subnet.subnetUsage}" code="Subnet_Usage" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">子网名称:</td>
                <td><input id="subnetName" name="subnetName" value="${subnet.subnetName}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
                <td></td>
                <td align="right">VLAN名称:</td>
                <td><input id="vlanName" name="vlanName" value="${subnet.vlanName}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">管理状态:</td>
                <td><s:resSelect name="adminStatus" value="${subnet.adminStatus}" code="Subnet_AdminStatus" ltype="select" /></td>
                <td></td>
                <td align="right">工作状态:</td>
                <td><s:resSelect name="operStatus" value="${subnet.operStatus}" code="Subnet_OperStatus" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">子网描述:</td>
                <td colspan=4><input id="subnetDesc" name="subnetDesc" value="${subnet.subnetDesc}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip2"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">扫描任务:</td>
                <td><input id="taskId" name="taskId" value="${subnet.taskId}" type="text" ltype="text" class="validate[optional,custom[integer]] ip1" /></td>
                <td></td>
                <td align="right">扫描标志:</td>
                <td><input id="scanDetails" name="scanDetails" value="${subnet.scanDetails}" type="text" ltype="text" class="validate[optional,custom[integer]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">是否采集:</td>
                <td colspan=4><s:resSelect name="isCollection" value="${subnet.isCollection}" code="IsCollection" ltype="select" /></td>
                <td></td>
            </tr>   
            <tr>
            	<td colspan=6 align="center">
            	<table align="center">
            		<tr>
            	<td><input id="bnSave" type="submit" value="保 存" class="l-button mg6" /></td>
            	<c:if test="${action=='add'}">
                <td><input id="bnSaveAdd" type="submit" value="保存并继续添加" class="l-button mg6"  style="width:108px;"/></td>
                </c:if>
                <td><input id="bnClose" type="button" value="取 消" class="l-button mg6" /></td>
            		</tr>
            	</table>
                </td>
            </tr>
		</table>
    </form:form>
</body>
</html>

