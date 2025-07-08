<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户个人设置</title>
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
		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});	
				
    	<shiro:hasPermission name="userSetup:passwd"> 
		$("#chkEditPasswd").change(function()
	    {
			if( this.checked ) {
				$("#isEditPasswd").attr("value","1");
				$("#trEditPasswd").css({display:''});
				$("#newEditPasswd").css({display:''});
				
			}else{
				$("#isEditPasswd").attr("value","0");
				$("#trEditPasswd").css({display:'none'});
				$("#newEditPasswd").css({display:'none'});
			}
        });
    	</shiro:hasPermission>  

		<c:if test="${result=='success'}">
		window.parent.$.ligerDialog.success('保存成功'); 
		</c:if>
		<c:if test="${result=='error'}">
		window.parent.$.ligerDialog.error('${message}');
		</c:if>
		<c:if test="${MenuStyleChange=='true'}">
		window.parent.location.href='${ctx}/index.do';
		</c:if>
		
	});
	
	function checkPassword(){
		var newPassword=$("#loginPasswd").val();
		var okPassword=$("#newloginPasswd").val();
		if(newPassword!=okPassword){
			window.parent.$.ligerDialog.error("两次密码不相同，请重新输入！");
			return false;
		}
		
	}

</script>

</head>
<body> 
	<div class="wFull hL">当前用户：${curUser.loginName}</div>
	<form:form id="formSave" modelAttribute="user" action="${ctx}/system/user/setup.do" method="post">
		<input type="hidden" name="userId" value="${curUser.userId}"/>
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
    		<shiro:hasPermission name="userSetup:passwd"> 
            <tr>
                <td align="right">修改密码:</td>
                <td align="left" colspan=4><input id="chkEditPasswd" type="checkbox" value="1"/></td>
                <td></td>
            </tr>
            <tr id="trEditPasswd" style="display:none">
                <td align="right">新密码:</td>
                <td colspan=4><input id="loginPasswd" name="loginPasswd" value="" type="password" ltype="password" class="validate[required[新密码不能为空],maxSize[32]] ip2" /></td>
                <td></td>
            </tr>
             <tr id="newEditPasswd"  style="display:none">
                <td align="right">确认新密码:</td>
                <td colspan=4><input id="newloginPasswd" name="newloginPasswd" value="" type="password" ltype="password" class="validate[required[确认密码不能为空],maxSize[32]] ip2" onblur="checkPassword()"/></td>
                <td></td>
            </tr>
            </shiro:hasPermission>  
    		<shiro:hasPermission name="userSetup:menuStyle"> 
            <tr>
                <td align="right">菜单风格:</td>
                <td colspan=4><s:resSelect id="loginMenuStype" name="loginMenuStyle" value="${curUser.loginMenuStyle}" code="loginMenuStyle" ltype="select" /></td>
                <td></td>
            </tr>
            </shiro:hasPermission>  
    		<shiro:hasPermission name="userSetup:skinName"> 
            <tr>
                <td align="right">皮肤风格:</td>
                <td colspan=4><s:resSelect id="loginSkinName" name="loginSkinName" value="${curUser.loginSkinName}" code="loginSkinName" ltype="select" /></td>
                <td></td>
            </tr>
            </shiro:hasPermission>  
    		<shiro:hasPermission name="userSetup:mobile"> 
            <tr>
                <td align="right">手机号码:</td>
                <td colspan=4><input id="mobile" name="mobile" value="${curUser.mobile}" type="text" ltype="text" class="validate[optional,maxSize[32]] ip2" /></td>
                <td></td>
            </tr>
            </shiro:hasPermission>  
    		<shiro:hasPermission name="userSetup:email"> 
            <tr>
                <td align="right">电子邮箱:</td>
                <td colspan=4><input id="email" name="email" value="${curUser.email}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip2" /></td>
                <td></td>
            </tr>
            </shiro:hasPermission>  
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" style="line-height:23px;"/>
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

