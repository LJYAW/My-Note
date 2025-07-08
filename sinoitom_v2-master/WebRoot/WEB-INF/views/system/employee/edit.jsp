<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>职员编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">

var canOrgSelect = false;

	$(function ()
	{				
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.saveOK(action, data);    
		//window.parent.close();
		window.parent.closeDlg();
		</c:if>
		
		$('#nameHelp').helpInfo({text:'姓名不能为空' });

		var orgId = '${employee.mainOrgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:employee.mainOrgId}';
		$("#mainOrg").ligerComboBox({
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'mainOrgId',
        	treeLeafOnly: false,
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false },
        	initValue: orgId,
        	onBeforeOpen: function(){canOrgSelect = true;},
        	onSelected: function (orgId)
	        {
        		if( !canOrgSelect ){
        			return;
        		}
        		$("#mainOrgId").val(orgId);
            	//var url = timeURL('${ctx}/system/organ/position/getData.do?orgId='+orgId); 
            	//根据机构oId获取部门
            	var url = timeURL('${ctx}/system/organ/getDptData.do?orgId='+orgId); 
            	
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			$("#mainDpt").ligerGetComboBoxManager().setData(data);
            		}
            	}); 
            }
        });
		
		
		
		$("#mainDpt").ligerComboBox({ 
			url: timeURL('${ctx}/system/organ/getDptData.do?orgId='+orgId),
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
			textField: 'orgName', 
			valueFieldID: 'id', 
	        initValue: '${employee.mainDptId}',
	        onBeforeOpen: function(){canOrgSelect = true;},
	        onSelected: function (orgId)
	        {
        		if( !canOrgSelect ){
        			return;
        		}
        		$("#mainDptId").val(orgId);
            	var url = timeURL('${ctx}/system/organ/position/getData.do?orgId='+orgId);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			$("#mainPos").ligerGetComboBoxManager().setData(data);
            		}
            	});  
            	
            	
            }
		});
		
		$("#mainPos").ligerComboBox({ 
			url: timeURL('${ctx}/system/organ/position/getData.do?orgId='+orgId),
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
			textField: 'posName', 
			valueFieldID: 'mainPosId', 
	        initValue: '${employee.mainPosId}',
		});
		
		$("#enterTime").ligerDateEditor({
			width: 130,
			initValue: '<fmt:formatDate value="${employee.enterTime}" pattern="yyyy-MM-dd" ></fmt:formatDate>'
		});

		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});	

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
	});
	
	function nameOver(){
		$("#nameHelp").prev('div').css("display","block");
	}
	function nameOut(){
		$("#nameHelp").prev('div').css("display","none");
	} 

</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="employee" action="${ctx}/system/employee/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="empId" value="${employee.empId}"/>
		<input type="hidden" id="mainOrgId" name="mainOrgId" />
		<input type="hidden" id="mainPosId" name="mainPosId" />
		<input type="hidden" id="mainDptId" name="mainDptId" />
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
                <td align="right">姓名:</td>
                <td><input id="name" name="name" value="${employee.name}" type="text" ltype="text" class="validate[required[姓名不能为空],maxSize[32]] ip1" /></td>
                <td class="required" onmouseover="nameOver()" onmouseout="nameOut()" id="nameHelp"></td>
                <td align="right">性别:</td>
                <td><s:resSelect name="sex" value="${employee.sex}" code="sex" ltype="select" /></td>
                <td></td>
            </tr>
           
            <tr>
                <td align="right">机构:</td>
                <td><input id="mainOrg" name="mainOrg" type="text" class="ip1"/></td>
                <td></td>
                <td align="right">部门:</td>
                <td><input id="mainDpt" name="mainDpt" type="text" class="ip1"/></td>
                <td></td>
            </tr>
             <tr>
               <td align="right">职位:</td>
                <td><input id="mainPos" name="mainPos" type="text" class="ip1"/></td>
                <td></td>
                <td align="right">状态:</td>
                <td><s:resSelect name="status" value="${employee.status}" code="employeeStatus" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">入职日期:</td>
                <td><input id="enterTime" name="enterTime" type="text" class="validate[optional,custom[date]] ip1" /></td>
                <td></td>
                <td align="right">工号:</td>
                <td><input id="workNo" name="workNo" value="${employee.workNo}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">电话号码:</td>
                <td><input id="phone" name="phone" value="${employee.phone}" type="text" ltype="text" class="validate[optional,custom[phone]] ip1" /></td>
                <td></td>
                <td align="right">手机号码:</td>
                <td><input id="mobile" name="mobile" value="${employee.mobile}" type="text" ltype="text" class="validate[optional,custom[mobile]] ip1" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">电子邮箱:</td>
                <td colspan=4><input id="email" name="email" value="${employee.email}" type="text" class="validate[optional,custom[email]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
<%--            <tr>--%>
<%--                <td align="right">个人经验:</td>--%>
<%--                <td colspan=4><input id="experience" name="experience" value="${employee.experience}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>--%>
<%--                <td></td>--%>
<%--            </tr>--%>
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

