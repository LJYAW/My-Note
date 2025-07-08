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
<title>综合布线编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script> 
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
        
		var orgId = '${ipHost.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:ipHost.orgId}';
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
	        }
        });
        
	  	//$("#formSave").ligerForm({inputWidth:200});
		//$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>	
		
		$("#formSave").validationEngine("attach"); 
		
		$("#bnClose").click(function(){
        	window.parent.closeDlg();
        });
	});
	$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });	
</script>

</head>
<body>
	<form id="formSave" modelAttribute="cablingInfo" action="${ctx}/fas/res/cablingInfo/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="id" value="${cablingInfo.cablingInfoID}"/>
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
				<td align="right">楼宇名称：</td>
				<td><input id="buildingNo" name="buildingNo" value="${cablingInfo.buildingNo}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">房间号：</td>
				<td><input id="roomNo" name="roomNo" value="${cablingInfo.roomNo}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1"  /></td>
				<td></td>
				<td align="right">信息点编号：</td>
				<td><input id="infoPointNo" name="infoPointNo" value="${cablingInfo.infoPointNo}" type="text" class="validate[required[不能为空],maxSize[128]] ip1" /></td>
				<td class="required"></td>
			</tr>
			<tr>
				<td align="right">介质类型：</td>
				<td><input id="mediaType" name="mediaType" value="${cablingInfo.mediaType}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
				<td></td>
				<td align="right">线缆类别：</td>
				<td><input id="cableType" name="cableType" value="${cablingInfo.cableType}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">带宽：</td>
				<td><input id="baudRate" name="baudRate" value="${cablingInfo.baudRate}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
				<td></td>
				<td align="right">长度(米)：</td>
				<td><input id="length" name="length" value="${cablingInfo.length}" type="text" ltype="text" class="validate[optional,maxSize[12],custom[number]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">模块号：</td>
				<td><input id="moduleNo" name="moduleNo" value="${cablingInfo.moduleNo}" type="text" ltype="text" class="ip1" /></td>
				<td></td>
				<td align="right">模块端口号：</td>
				<td><input id="modulePort" name="modulePort" type="text" value="${cablingInfo.modulePort}" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">交换机IP：</td>
				<td><input id="switchIP" name="switchIP" type="text" value="${cablingInfo.switchIP}" class="validate[optional,maxSize[32],custom[ipv4]] ip1"/></td>
				<td></td>
				<td align="right">交换机端口：</td>
				<td><input id="ifName" name="ifName" value="${cablingInfo.ifName}" type="text" ltype="text" class="validate[optional,maxSize[32]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">机架号：</td>
				<td><input id="cabinetNo" name="cabinetNo" type="text" value="${cablingInfo.cabinetNo}" class="ip1" /></td>
				<td></td>
				<td align="right">备注：</td>
				<td><input id="remark" name="remark" value="${cablingInfo.remark}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
           		<td align="right" colspan=3 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
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

