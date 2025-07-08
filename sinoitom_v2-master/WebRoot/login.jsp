<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>${s:paramVal("SystemName")}</title>
<meta http-equiv="X-UA-TextLayoutMetrics" content="natural" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/main.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-cookies/jquery.cookies.2.2.0.min.js" type="text/javascript"></script>

<script type="text/javascript">

var cookie_login = '${ctx}/loginName';
$(document).ready(function ()
{	
  var loginName = document.frmLogin.loginName.value;
  document.frmLogin.loginName.value = "";
  document.frmLogin.chkSave.checked = true;
  document.frmLogin.loginPasswd.value = "";

  if($.cookies.get(cookie_login) != null){
	 loginName = $.cookies.get(cookie_login);
  }	
});

$(function ()
{
  if( window != top ){
    top.location.href = location.href;
  }
  
  var loginName = $.cookies.get(cookie_login);
  if(loginName != null){
    document.frmLogin.loginName.value = loginName;
    document.frmLogin.chkSave.checked = true;
    document.frmLogin.loginPasswd.focus();
  }
  else{
	document.frmLogin.loginName.focus();	  
  }
  
  $("#loginName").keypress(function(e)
  {
    return next_ctrl_onfocus($("#loginPasswd"), e);
  });
  
  $("#login").click(function()
  {
    return login();
  });

});

function login()
{
	
  var nameObj = document.frmLogin.loginName;
  if(nameObj.value.length == 0) {
    alert('请输入用户名！');
    nameObj.focus();
    return false;
  }
  var passwdObj = document.frmLogin.loginPasswd;
  if(passwdObj.value.length == 0) {
    alert('请输入密码！');
    passwdObj.focus();
    return false;
  }

  var chkSaveObj = document.frmLogin.chkSave;
  if(chkSaveObj.checked==true) {
	  $.cookies.set(cookie_login, nameObj.value, { hoursToLive: 180*24 }); /* 保存180天 */
  }
  else{
	  $.cookies.del(cookie_login);
  }
  document.frmLogin.submit();
  return false;
}

/*
	 *键盘点击事件
	 */
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) { // enter 键
			login();
		}
	}


</script>

</head>

    <body>
    <div class="loginInScreen">
        <div class="loginInContainer">
            <div class="loginInCtop">
            </div>
            <div class="loginInCName">SinoITOM</div>
             <div class="loginInCName">IT运维管理系统</div>
            <div class="loginInVersion">Version 2.0</div>
            <div class="loginInCInput">
              <form name="frmLogin" action="${ctx}/login.do"  method="post">
                <div class="loginInFields">
                    <div class="usename">
                        <label>用户名：</label>
                        <input type="text" id="loginName" name="loginName" style="width:220px;border-radius:5px;line-height:22px;border:1px solid #ccc;" value="" autocomplete="off"/>
                    </div>

                    <div class="password">
                        <label>密　码：</label>
                        <input id="loginPasswd" name="loginPasswd" type="text" style="width:220px;border-radius:5px;line-height:22px;border:1px solid #ccc;" value="" autocomplete="off" onfocus="this.type='password'">
                         
                         <p id="msg">${message}</p>
                    </div>
                    <div class="save">
                        <input type="checkbox" id="chk" name="chkSave" value="1">
                        <label>保存用户名</label>
                        <button>登录</button>
                    </div>
                </div>
               </form> 
            </div>
            <div class="loginInCBottom">${s:paramVal("SystemCopyRight")}</div>
        </div>
    </div>
    </body>
</html>