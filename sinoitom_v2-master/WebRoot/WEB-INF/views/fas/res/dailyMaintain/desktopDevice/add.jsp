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
<title>添加设备</title>
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
		var action = '${action}';
		var data = ${saveData};
		window.parent.saveOK(action, data);
		window.parent.closeDlg();
		</c:if>
		
		var orgId = '${LOGIN_USER_MAIN_ORGAN.orgId}';
        $("#mainOrg").ligerComboBox({
        	width: 200, selectBoxWidth: 200, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'orgId',
        	treeLeafOnly: false,
        	initValue: orgId,
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false },
	        onSelected: function (orgId)
	        {
	        	var url = timeURL('${ctx}/system/employee/getMinData.do?orgId='+orgId);
	        	$.ajax({
	        		url: url,
	        		dataType: 'json',
	        		success: function(data) {
	        			$("#user").ligerGetComboBoxManager().setData(data);
	        		}
	        	});        			
	        }
        });
        
		$("#user").ligerComboBox({ 
			url: timeURL('${ctx}/system/employee/getMinData.do?orgId='+orgId),
        	width: 200, selectBoxWidth: 200, selectBoxHeight: 200, 
			textField: 'name', 
			valueFieldID: 'userId',
			initValue: '${ipHost.userId}'
		});
		
        
	  	//$("#formSave").ligerForm({inputWidth:200});
		//$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>	
		
		$("#bnClose").click(function(){
        	window.parent.closeDlg();
        });
		});
		
		function sendform(){
			document.getElementById("formSave").submit();
		}
		
		
</script>

</head>
<body >
	<form id="formSave" action="${ctx}/fas/res/dailyMaintain/desktopDevice/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="ipHostId" value="${ipHostInfo.ipHostId }"/>
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
				<td align="right">所属机构：</td>
				<td><input id="mainOrg" name="mainOrg" type="text" class="ip1"/></td>
				<td></td>
				<td align="right">使用者：</td>
				<td>
					<input id="user" type="text" class="ip1"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">接入交换机：</td>
				<td><input id="switchIp" name="switchIp" value="${ipHostInfo.switchIp }" type="text" ltype="text" class="validate[required[接入交换机不能为空],custom[ipv4]] ip1" /></td>
				<td></td>
				<td align="right">交换机接口：</td>
				<td><input id="ifIndex" name="ifIndex" value="${ipHostInfo.switchIfIndex }" type="text" ltype="text" class="validate[required[交换机接口不能为空],maxSize[128]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">IP地址：</td>
				<td><input id="ipAddr" name="ipAddr" value="${ipHostInfo.ipAddr }" type="text" ltype="text" class="validate[required[IP地址不能为空],custom[ipv4]] ip1"  /></td>
				<td></td>
				<td align="right">MAC地址：</td>
				<td><input id="macAddr" name="macAddr" value="${ipHostInfo.macAddr }" type="text" ltype="text" class="validate[required[MAC地址不能为空],custom[macAddr]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">Vlan号：</td>
				<td><input id="vlanId" name="vlanId" value="${ipHostInfo.vlanId }" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
				<td></td>
				<td align="right">信息点编号：</td>
					<td><input id="infoPointNo" name="infoPointNo"
						value="${ipHostInfo.infoPointNo }" type="text" ltype="text"
						class="validate[optional,maxSize[64]] ip1" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">设备名称：</td>
				<td><input id="hostName" name="hostName" value="" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
				<td></td>
				<td align="right">设备序列号：</td>
				<td><input id="serialNO" name="serialNO" value="" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">资产编号：</td>
				<td><input id="assertNO" name="assertNO" value="" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
				<td colspan="4"></td>
			</tr>
			<tr>
           		<td align="right" colspan=3 >
           		<input id="bnSave" type="button" value="保 存" onclick="sendform()" class="l-button mg6" />
           		</td>
           		<td align="left" colspan=3>
           		<input id="bnClose" type="button" value="取 消" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form>
    <br/>
</body>
</html>

