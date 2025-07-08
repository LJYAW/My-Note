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
<title>设备访问信息编辑</title>
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

	var orgName = '';
	var toolName = '';
	var modeName = '';
	var count = 0;
	<c:if test="${action=='add'}">
	count++;
	</c:if>

	$(function ()
	{		
		<c:if test="${result=='success'}">
		window.parent.saveOK();  
		</c:if>
		<c:if test="${result=='success' && closeDlg=='true'}">
		window.parent.closeDlg();  
		</c:if>
		
		toolName = $("#accessTool").find("option:selected").text();
		modeName = $("#userType").find("option:selected").text();

		var orgId = '${devAccess.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:devAccess.orgId}';
		$("#mainOrg").ligerComboBox({
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'orgId',
        	treeLeafOnly: false,
        	initValue: orgId,
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false },
    	    onSelected: function (id, name)
    	    {
    	    	orgName = name;     
    	    	if( id != "" && id != null ){
    	    		if( count>=2 ){
    	    			autoSetName();
    	    		}
        	    	else{
        	    		count++;
        	    	}
    	    	}
    	    }
        });

        $("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		$("#accessTool").ligerGetComboBoxManager().bind('selected', function (id, name){
			toolName = name;
			autoSetName();
		});
		
		$("#userType").ligerGetComboBoxManager().bind('selected', function (id, name){
			modeName = name;
			autoSetName();
		});
		
        $("#userName").change(function(){
			autoSetName();
        });
        
		        
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
	
    function autoSetName(){
        var accessName = orgName+"-"+$("#userName").val()+"-"+toolName+"-"+modeName;
        $("#acsUserName").val(accessName);
    }
	
	
</script>

</head>
<body> 
	<form id="formSave" action="${ctx}/devices/accessUser/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="id" value="${devAccess.devAcsUserId}"/>
		<input type="hidden" id="orgId" name="orgId" value="${devAccess.orgId}"/>
		<input type="hidden" id="oprType" name="oprType" value="${oprType}"/>
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
                <td><input id="mainOrg" type="text" class="ip1"/></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">访问方式:</td>
                <td><s:resSelect id="accessTool" name="accessTool" value="${devAccess.accessTool}" code="Dev_AccessTool" ltype="select" /></td>
                <td></td>
                <td align="right">访问端口:</td>
                <td><input id="accessPort" name="accessPort" value="${devAccess.accessPort}" ltype='spinner' ligerui="{type:'int'}"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">用户名称:</td>
                <td><input id="userName" name="userName" value="${devAccess.userName}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
                <td align="right">登录密码:</td>
                <td><input id="passWord" name="passWord" value="${devAccess.passWord}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">用户类型:</td>
                <td><s:resSelect id="userType" name="userType" value="${devAccess.userType}" code="dev_UserPrivilege" ltype="select" /></td>
                <td></td>
               <td align="right">状态:</td>
                <td><s:resSelect name="status" value="${devAccess.status}" code="devAccess_status" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
            	<td align="right">特权模式命令:</td>
                <td><input id="privModeCmd" name="privModeCmd" value="${devAccess.privModeCmd}" type="text" ltype="text" class="validate[optional,maxSize[64] ip1" /></td>
                <td></td>
                <td align="right">特权模式密码:</td>
                <td>
                	<input id="privModePasswd" name="privModePasswd" value="${devAccess.privModePasswd}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">访问信息命名:</td>
                <td><input id="acsUserName" name="acsUserName" value="${devAccess.acsUserName}" type="text" ltype="text" class="validate[required,maxSize[32]] ip1"/></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">访问信息说明:</td>
                <td colspan=4><input id="acsUserDescr" name="acsUserDescr" value="${devAccess.acsUserDescr}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip2"/></td>
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
    </form>
</body>
</html>

