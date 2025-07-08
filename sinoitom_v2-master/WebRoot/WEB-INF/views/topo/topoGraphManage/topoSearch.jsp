<!DOCTYPE html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>拓扑搜索</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css"
	rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js"
	type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/help.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/index.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/edit.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/help.js"
	type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">

var timer;
	$(function() {

		$("#formSearch").validationEngine({
			promptPosition : "centerRight"
		});
		$("#formSearch").validationEngine();

		$('#ip_addressHelp').helpInfo({
			text : '必填，唯一，格式：192.168.1.1'
		});
		
		$('#ro_community_stringHelp').helpInfo({
			text : '团体自必填,public/private/cisco'
		});
		$('#snmp_versionV3Help').helpInfo({
			text : '默认使用V3进行设备搜索，当支持V3设备较少或没有，请选择V1/V2进一步搜索'
		});
		$('#snmp_versionV1Help').helpInfo({
			text : '支持V1/V2进行设备搜索，对V3不支持的设备进行进一步搜索'
		});
		$('#snmpPortHelp').helpInfo({
			text : '默认snmp端口号为161，若不清楚具体端口请勿修改！'
		});
		$('#usernameHelp').helpInfo({
			text : '用户名必填'
		});
		$('#auth_passwordHelp').helpInfo({
			text : '认证密码必填'
		});
		$('#privacy_passwordHelp').helpInfo({
			text : '加密密码必填'
		});
		var snmp_versionV1 = $("#snmp_versionV1").is(":checked");
		if(snmp_versionV1) {
			$("#td1,#td2").css("display", "");
			$("#tr0").css("display", "");
		}else{
			$("#td1,#td2").css("display", "none");
			$("#tr0").css("display", "none");
		}
		
		checkSecurityLevel();
		checSNMPVersion();
		$("#formSearch").ajaxForm({
			dataType : 'json',
			beforeSubmit : function() {
				$("#bnSearch").attr("disabled", "disabled");
				$("#bnSearch").css("cursor","not-allowed");
				$("#snmpV3_auth").attr("disabled", "disabled");
				$("#snmpV3_auth").css("cursor","not-allowed");
				$("#showlogs").css("display","");
				 $("#trTxt").removeClass("dN");
				timer=setInterval("showTopoLogs()",2000);//1000为1秒钟
			},
			success : function(data) {
				if (data.result == "success") {
					searchMsg = "";
				} else {
					$.ligerDialog.error(data.message);
					$("#bnSearch").removeAttr("disabled");
					$("#bnSearch").css("cursor","pointer");
					$("#snmpV3_auth").attr("disabled", "disabled");
					$("#snmpV3_auth").css("cursor","not-allowed");
				}
			},
		});
	});

	function submitForm() {
		var snmp_versionV1 = $("#snmp_versionV1").is(":checked");
		var snmp_versionV3 = $("#snmp_versionV3").is(":checked");
		if(!snmp_versionV3 && !snmp_versionV1){
			alert("必须选择一个搜索版本");
		}else{
			$("#formSearch").submit();
		}
	}

	function checSNMPVersion() {
		var snmp_versionV1 = $("#snmp_versionV1").is(":checked");
		var snmp_versionV3 = $("#snmp_versionV3").is(":checked");
		if (snmp_versionV3) {
			$("#tr1,#tr2,#tr3,#tr4,#tr5").css("display", "");
			$("#snmp_versionV3").val("3");
		}else{
			$("#username").val("");
			$("#tr1,#tr2,#tr3,#tr4,#tr5").css("display", "none");
			$("#snmp_versionV3").val("");
		}
		if(snmp_versionV1) {
			$("#td1,#td2").css("display", "");
			$("#tr0").css("display", "");
		}else{
			$("#rw_community_string").val("");
			$("#td1,#td2").css("display", "none");
			$("#tr0").css("display", "none");
		}
	}

	function checkModifyParam(){
		var modifyParam = $("#modifyParam").is(":checked");
		if(modifyParam){
			$("#tr6,#tr7").css("display", "");
		}else{
			$("#tr6,#tr7").css("display", "none");
		}
	}
	
	function checkSecurityLevel() {
		var securityLevel = $("#security_level").val();
		if (securityLevel == '0') {
			$("#auth_passwordHelp").css("display", "none");
			$("#auth_passwordHide").css("display", "");
			$("#privacy_passwordHelp").css("display", "none");
			$("#privacy_passwordHide").css("display", "");
			$("#auth_password,#privacy_password").val("");
			$("#auth_protocol,#privacy_protocol").attr("disabled", "disabled");
			$("#auth_protocol,#privacy_protocol").addClass(
					"l-text l-text-disabled formSelect");
			$("#auth_password,#privacy_password").attr("readonly", "readonly");
			$("#auth_password_td div:first-child").addClass("l-text-disabled");
			$("#privacy_password_td div:first-child").addClass(
					"l-text-disabled");
			$("#auth_password").removeClass("validate[required[认证密码不能为空]]");
			$("#privacy_password").removeClass("validate[required[私有密码不能为空]]");
		} else if (securityLevel == "1") {
			$("#privacy_passwordHelp").css("display", "none");
			$("#privacy_passwordHide").css("display", "");
			$("#auth_passwordHelp").css("display", "");
			$("#auth_passwordHide").css("display", "none");
			$("#privacy_password").val("");
			$("#auth_protocol").removeAttr("disabled");
			$("#auth_protocol").removeClass("l-text l-text-disabled");
			$("#privacy_protocol").attr("disabled", "disabled");
			$("#privacy_protocol")
					.addClass("l-text l-text-disabled formSelect");
			$("#auth_password").removeAttr("readonly");
			$("#privacy_password").val("");
			$("#auth_password_td div:first-child").removeClass(
					"l-text-disabled");
			$("#auth_password,#privacy_password").removeClass(
					"l-text l-text-disabled");
			$("#privacy_password").attr("readonly", "readonly");
			$("#privacy_password_td div:first-child").addClass(
					"l-text-disabled");
			$("#username").addClass("validate[required[用户名不能为空]]");
			$("#auth_password").addClass("validate[required[认证密码不能为空]]");
			$("#privacy_password").removeClass("validate[required[私有密码不能为空]]");
		} else {
			$("#auth_passwordHelp").css("display", "");
			$("#auth_passwordHide").css("display", "none");
			$("#privacy_passwordHelp").css("display", "");
			$("#privacy_passwordHide").css("display", "none");
			$("#auth_protocol,#privacy_protocol").removeAttr("disabled");
			$("#auth_protocol,#privacy_protocol").removeClass(
					"l-text l-text-disabled");
			$("#auth_password,#privacy_password").removeAttr("readonly");
			$("#auth_password_td div:first-child").removeClass(
					"l-text-disabled");
			$("#privacy_password_td div:first-child").removeClass(
					"l-text-disabled");
			$("#username").addClass("validate[required[用户名不能为空]]");
			$("#auth_password").addClass("validate[required[认证密码不能为空]]");
			$("#privacy_password").addClass("validate[required[私有密码不能为空]]");
		}
	}

	function ip_addressOver() {
		$("#ip_addressHelp").prev('div').css("display", "block");
	}
	function ip_addressOut() {
		$("#ip_addressHelp").prev('div').css("display", "none");
	}
	function snmp_versionV3Over() {
		$("#snmp_versionV3Help").prev('div').css("display", "block");
	}
	function snmp_versionV3Out() {
		$("#snmp_versionV3Help").prev('div').css("display", "none");
	}
	function snmp_versionV1Over() {
		$("#snmp_versionV1Help").prev('div').css("display", "block");
	}
	function snmp_versionV1Out() {
		$("#snmp_versionV1Help").prev('div').css("display", "none");
	}
	function ro_community_stringOver() {
		$("#ro_community_stringHelp").prev('div').css("display", "block");
	}
	function ro_community_stringOut() {
		$("#ro_community_stringHelp").prev('div').css("display", "none");
	}
	function snmpPortOver() {
		$("#snmpPortHelp").prev('div').css("display", "block");
	}
	function snmpPortOut() {
		$("#snmpPortHelp").prev('div').css("display", "none");
	}
	function usernameOver() {
		$("#usernameHelp").prev('div').css("display", "block");
	}
	function usernameOut() {
		$("#usernameHelp").prev('div').css("display", "none");
	}
	function auth_passwordOver() {
		$("#auth_passwordHelp").prev('div').css("display", "block");
	}
	function auth_passwordOut() {
		$("#auth_passwordHelp").prev('div').css("display", "none");
	}
	function privacy_passwordOver() {
		$("#privacy_passwordHelp").prev('div').css("display", "block");
	}
	function privacy_passwordOut() {
		$("#privacy_passwordHelp").prev('div').css("display", "none");
	}

	function checkLink() {
		$.ajax({
			url : timeURL("${ctx}/dcnm/switch/checkLink.do"),
			dataType : 'json',
			data : $('#formSearch').serialize(),
			cache : false,
			success : function(data) {
				if (data) {
					alert("连接成功！")
				} else {
					alert("连接失败！");
				}
			},
			error : function(request) {
				alert("连接失败！");
			}
		});
	}
	
	
// 	function startSearch(){
//        $.ajax({
// 			     type: "POST",
// 			     url: "${ctx}/topo/graphManage/topoDiscover.do",
// 			     contentType: "application/x-www-form-urlencoded; charset=utf-8",
// 			     data: $("#formSearch").serialize(),
// 			     success: function (date) {
			              
// 			              },
// 			     });
//        $("#trTxt").removeClass("dN");
//        $("#showlogs").css("display","");
//        timer=setInterval("showTopoLogs()",2000);//1000为1秒钟
//     }
    
//     var logStr="";
    function showTopoLogs(){
      $.ajax({
        url:"${ctx}/topo/graphManage/localOutputLogs.do",
        dataType:"json",
        success:function(data){
        if(data){
           var obj=data.outputstr;
  		  var str = JSON.stringify(obj) ;
		  str=str.replace(new RegExp("\"",'g'),"");
  		  if(str.length==0){
  		      str="...";
  		      logStr+=str;
  		  }else{
  		  var logStr="Topo搜索开始.......";
  		      logStr+="</br>"+str;
  		       $("#showlogs").html(logStr);
  		  }
  	   
          getScrollTop();
           if(logStr.indexOf("拓扑搜索结束")>0){  //搜索完成
//             window.parent.$.ligerDialog.success("拓扑搜索结束！");
            clearInterval(timer);
            $("#trTxt").addClass("dN");
//             window.parent.editOK(); 
          }
			
        }
        }
         
      });
    }
    
    function getScrollTop() {
		document.getElementById("trTxt").scrollIntoView(false);
	} 
	
</script>
</head>
<body scroll=no>
	<form id="formSearch" modelAttribute="snmpSearch" action="${ctx}/topo/graphManage/searchFullTopo.do" method="post">
	
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
				<td align="right" nowrap>种子节点IP：</td>
				<td><input id="ip_address" name="ip_address" type="text"
					class="validate[required[设备IP不能为空],custom[ipv4]] formInput-S" /></td>
				<td onmouseover="ip_addressOver()" onmouseout="ip_addressOut()"
					id="ip_addressHelp" align="right" class="required" />
			</tr>
			 <tr>
                <td align="right">设备IP地址：</td>
                <td colspan=4><input id="netRange" name="netRange" value="" type="text" ltype="text" class="validate[required] formInput-L" /></td>
                <td></td>
            </tr>
            <tr>
            	<td class="td_w1 hR vT">说明：</td>
           		<td colspan=5 class="td_w5">
           		搜索范围支持网段和单个IP搜索，多个搜索范围以分号","间隔;<br/>
           		网段支持10.1.1.0/24,10.1.1.0/255.255.255.0,10.1.1.1-10.1.1.254三种格式;<br/>
           		snmp通讯串多个以分号","间隔，为空的话，系统会以缺省参数设置进行搜索。
           		</td>
            </tr>
			<tr>
				<td align="right">V3版本：</td>
				<td colspan=""><input type="checkbox" id="snmp_versionV3" value="3"
					name="snmp_versionV3" onclick="checSNMPVersion()" >
					Version3</td>
				<td onmouseover="snmp_versionV3Over()" onmouseout="snmp_versionV3Out()"
					id="snmp_versionV3Help" align="right" class="help" />
			</tr>
			<tr id="tr1">
				<td colspan="6" align="center" style="font-weight:bolder; ">snmpVersion3 配置信息</td>
			</tr>
			<tr id="tr2">
				<td align="right">用户名：</td>
				<td><input id="username" name="username"
					 type="text" class="validate[required[用户名不能为空]] formInput-S" /></td>
				<td onmouseover="usernameOver()" onmouseout="usernameOut()"
					id="usernameHelp" align="right" class="required"></td>
				<td align="right">安全机制：</td>
				<td><select onchange="checkSecurityLevel()" id="security_level"
					name="security_level" class="formSelect">
						<option value="0">NoAuthNoPriv</option>
						<option value="1" selected="selected">AuthNoPriv</option>
						<option value="3">AuthPriv</option>
				</select></td>
				<td></td>
			</tr>

			<tr id="tr3">
				<td align="right">认证协议：</td>
				<td><select disabled="disabled" id="auth_protocol"
					name="auth_protocol" class="l-text l-text-disabled formSelect">
						<option value="MD5">HMAC_MD5_96</option>
						<option value="SHA">HMAC_SHA_96</option>
				</select></td>
				<td></td>
				<td align="right">加密算法：</td>
				<td><select disabled="disabled" id="privacy_protocol"
					name="privacy_protocol" class="l-text l-text-disabled formSelect">
						<option value="DES">CBC_DES</option>
						<option value="AES">CFB_AES_128</option>
						<option value="3DES">3DES</option>
				</select></td>
				<td></td>
			</tr>

			<tr id="tr4">
				<td align="right">认证密码：</td>
				<td id="auth_password_td"><input readonly="readonly"
					id="auth_password" name="auth_password" type="password"
					class="validate[required[认证密码不能为空]] formInput-S" /></td>
				<td onmouseover="auth_passwordOver()" onmouseout="auth_passwordOut()"
					id="auth_passwordHelp" align="right" class="required"></td>
				<td id="auth_passwordHide"></td>
				<td align="right">私有密码：</td>
				<td id="privacy_password_td"><input readonly="readonly"
					id="privacy_password" name="privacy_password" type="password"
					class="validate[required[私有密码不能为空]] formInput-S" /></td>
				<td onmouseover="privacy_passwordOver()" onmouseout="privacy_passwordOut()"
					id="privacy_passwordHelp" align="right" class="required"></td>
				<td id = "privacy_passwordHide"></td>
			</tr>
			<tr id="tr5">
				<td align="right">context：</td>
				<td><input id="context" name="context" type="text"
					class="formInput-S" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">V1/V2 版本：</td>
				<td ><input type="checkbox" id="snmp_versionV1" value="1"
					name="snmp_versionV1" checked="checked" onclick="checSNMPVersion()" >
					V1/V2C </td>
				<td onmouseover="snmp_versionV1Over()" onmouseout="snmp_versionV1Out()"
					id="snmp_versionV1Help" align="right" class="help" />
				<td id = "td1" align="right" >snmp版本：</td>
				<td id = "td2" >
					<input type="radio" id="v1" value="0" name="snmp_version"> V1 
						
					<input type="radio" id="v2C" value="1" name="snmp_version" checked="checked"> V2C 
				<td><td/>
			</tr>
			<tr id="tr0">
				<td align="right">只读团体字：</td>
				<td ><input id="ro_community_string"
					name="ro_community_string" type="text" value="cisco"
					class="validate[required[只读团体字段不能为空]] formInput-S" /></td>
				<td onmouseover="ro_community_stringOver()" onmouseout="ro_community_stringOut()"
					id="ro_community_stringHelp" align="right" class="required" />
				<td align="right">读写团体字：</td>
				<td ><input id="rw_community_string"
					name="rw_community_string" type="text"
					class="formInput-S" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right" nowrap>修改搜索参数：</td>
				<td colspan=""><input type="checkbox" id="modifyParam" value="1"
					name="modifyParam" onclick="checkModifyParam()">
					Modify Param</td>
			</tr>
			
			<tr id="tr6" style="display: none">
				<td align="right">最大搜索跳数：</td>
				<td><input id="max_hop" name="max_hop" type="text"
					class="formInput-S" value="3"/></td>
				<td></td>
				<td align="right">snmp端口号：</td>
				<td ><input id="snmpPort" name="snmpPort"
					 type="text" class="formInput-S" value="161"/>
				<td onmouseover="snmpPortOver()" onmouseout="snmpPortOut()"
					id="snmpPortHelp" align="right" class="required" />
			</tr>
			<tr id="tr7" style="display: none">
				<td align="right">超时时间(s)：</td>
				<td ><input id=""timeout"" name="timeout" type="text"
					class="formInput-S" value="3"/></td>
				<td></td>
				<td align="right">重试次数：</td>
				<td ><input id="retries" name="retries" type="text"
					class="formInput-S" value="3"/></td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right" nowrap>拓扑生成机制：</td>
				<td colspan="">
					<input type="checkbox" id="Lldp" value="Lldp" name="topoMethod" onclick="" checked="checked">
					LLDP
					<input type="checkbox" id="Cdp" value="Cdp" name="topoMethod" onclick="">
					CDP
					<input type="checkbox" id="IpRoute" value="IpRoute" name="topoMethod" onclick="">
					IpRoute
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td align="center" style="height: 80px;"><input id="bnSearch"
					type="button" value="设备搜索" class="l-button bn2 mg6" onclick="submitForm()"/></td>
				<td colspan="2" align="left" style="height: 80px;">
				<input id="snmpV3_auth" type="button" value="snmp验证" onclick="checkLink()"
					class="l-button bn1 mg10" /></td>
				<td align="left" style="height: 80px;"><input id="bnSave"
					type="button" value="取消" class="l-button bn2 mg6"
					onclick="window.parent.closeDlg();" /></td>
			</tr>
			
		</table>
	</form>
	<div id="showlogs" style="text-align: left;width:500px;height:auto;padding-left:15px;margin-left: auto;margin-right: auto;display: none">
	 Topo搜索开始.......
	</div>
	<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:center">
           		<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;搜索中...
           		</span>
     </div>
</body>
</html>

