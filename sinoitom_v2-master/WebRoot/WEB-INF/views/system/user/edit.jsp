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
<title>用户编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script type="text/javascript">
	$(function ()
	{				
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.saveOK(action, data);     
		window.parent.closeDlg();
		</c:if>
		
		$("#mainOrg").ligerComboBox({
        	width: 196,
        	selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'mainOrgId',
        	treeLeafOnly: false,
        	initValue: '${user.mainOrgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:user.mainOrgId}',
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false }
        });

		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});	
		
		
		$("#chkEditPasswd").change(function()
	    {
			if( this.checked ) {
				$("#isEditPasswd").attr("value","1");
				$("#trEditPasswd").css({display:''});
			}else{
				$("#isEditPasswd").attr("value","0");
				$("#trEditPasswd").css({display:'none'});
			}
        });

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
		
		
		$('#loginNameHelp').helpInfo({ text:'用户账号不能为空' });
		$('#userNameHelp').helpInfo({ text:'用户名称不能为空' });
		$('#loginPasswdHelp').helpInfo({ text:'用户密码不能为空' });
		$('#chkEditPasswd').helpInfo({ text:'用户密码不能为空' });
	});
// 	$(document).ready(function() {
// 		$(".required").each(function() {
// 			var $this  = $(this);
// 			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
// 		});
//   });
  
  
   function helpInfoOver(domId){
    		$("#"+domId).prev('div').css("display","block");
    }
    function helpInfoOut(domId){
    		$("#"+domId).prev('div').css("display","none");
    } 
    
     function checkUserName(){
       if('${action}'=="add"){
          var loginName=$("#loginName").val();
	    $.getJSON(
	              "${ctx}/system/user/checkUserName.do?loginName="+loginName,
	              function (result) {
	                  if (result==true) {
	                       layer.msg('该用户帐号已存在!',{icon: 7,time:2000});
	                       $("#loginName").val("");
	                     }   
	              });
   
       }
	  
  } 
  
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="user" action="${ctx}/system/user/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="id" value="${user.userId}"/>
		<input type="hidden" id="mainOrgId" name="mainOrgId" value="${user.mainOrgId}"/>
		<input type="hidden" id="isEditPasswd" name="isEditPasswd" value="0"/>
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
                <td align="right">用户类型:</td>
                <td><s:resSelect id="userType" name="userType" value="${user.userType}" code="userType" filter="resCode<='${LOGIN_USER.userType}'" ltype="select" /></td>
                <td></td>
                <td align="right">用户状态:</td>
                <td><s:resSelect id="state"  name="state" value="${user.state}" code="userState" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">用户账号:</td>
                <td><input id="loginName" name="loginName" value="${user.loginName}" type="text" ltype="text" class="validate[required[用户账号不能为空]] ip1" onblur="checkUserName()"/></td>
                <td onmouseover="helpInfoOver(this.id)" onmouseout="helpInfoOut(this.id)" id="loginNameHelp" class="required"></td>
                <td align="right">用户名称:</td>
                <td><input id="userName" name="userName" value="${user.userName}" type="text" ltype="text" class="validate[required[用户名称不能为空]] ip1" /></td>
                <td onmouseover="helpInfoOver(this.id)" onmouseout="helpInfoOut(this.id)" id="userNameHelp" class="required"></td>
            </tr>
            <tr>
            <c:if test="${action=='add'}">
                <td align="right">用户密码:</td>
                <td><input id="loginPasswd" name="loginPasswd" value="${user.loginPasswd}" type="password" ltype="password" class="validate[required[用户密码不能为空]] ip1" /></td>
                <td onmouseover="helpInfoOver(this.id)" onmouseout="helpInfoOut(this.id)" id="loginPasswdHelp" class="required"></td>
            </c:if>
            <c:if test="${action=='edit'}">
                <td align="right">修改密码:</td>
                <td align="left"><input id="chkEditPasswd" type="checkbox" value="1" class="validate[required[修改密码不能为空]]"/></td>
                <td class="required"></td>
            </c:if>
                <td align="right">所属机构:</td>
                <td><input id="mainOrg" name="mainOrg" type="text" class="ip1"/></td>
                <td></td>
            </tr>
            <c:if test="${action=='edit'}">
            <tr id="trEditPasswd" style="display:none">
                <td align="right">用户密码:</td>
                <td colspan=4><input id="loginPasswd" name="loginPasswd" value="" type="password" ltype="password" class="validate[required[用户密码不能为空],maxSize[32]] ip1" /></td>
                <td></td>
            </tr>
            </c:if>
            <tr>
                <td align="right">用户说明:</td>
                <td colspan=4><input id="description" name="description" value="${user.description}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">手机号码:</td>
                <td><input id="mobile" name="mobile" value="${user.mobile}" type="text" ltype="text" class="validate[optional,custom[mobile2]] ip1" /></td>
                <td></td>
                <td align="right">电子邮箱:</td>
                <td><input id="email" name="email" value="${user.email}" type="text" ltype="text" class="validate[optional,custom[email]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">菜单风格:</td>
                <td><s:resSelect id="loginMenuStype" name="loginMenuStyle" value="${user.loginMenuStyle}" code="loginMenuStyle" ltype="select" /></td>
                <td></td>
                <td align="right">皮肤风格:</td>
                <td><s:resSelect id="loginSkinName" name="loginSkinName" value="${user.loginSkinName}" code="loginSkinName" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

