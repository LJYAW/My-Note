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
<title>端口安全</title>
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
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function ()
	{
		<c:if test="${!empty message}">
			var message='${message}';
			alert(message)
		</c:if>
		<c:if test="${result=='success'}">
			var action = '${action}';
			var data = ${saveData};
			window.parent.saveOK(action, data);
			window.parent.closeDlg();
		</c:if>
		
		$("#sysForm").ligerForm({inputWidth:420});
		$("#sysForm").validationEngine({promptPosition: "centerRight"});	

	});
	
	function bindMacs(ip,macIp,flag) 
	{
		var ip=$("#ip").val();
		var macIp=$("#macIp").val();
		var ifIndex=$("#ifIndex").val();
		$.ajax({
			url: timeURL("${ctx}/fas/res/host/ipHost/macBind.do?ip="+ip+"&macIp="+macIp+"&flag="+flag+"&ifIndex="+ifIndex),
			dataType: 'json',
			success: function(data) {
				if( data.status == 'true' ){
           			$.ligerDialog.success('搜索完毕！');
				}
			}
		});        			
	}
	 function bindMac(flag){
			document.sysForm.flag.value=flag;
			document.sysForm.submit();	
        }
</script>

</head>
<body> 
	<form name="sysForm" id="sysForm" action="${ctx}/fas/res/host/ipHost/bindMac.do" method="post">
		<input type="hidden" id="id" name="id" value="${id }"/>
		<input type="hidden" name="ifIndex" id="ifIndex" value="${ifIndex }"/>
		<input type="hidden" name="switchIp" id="switchIp" value="${switchIp }"/>
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="flag" id="flag" value=""/>
		<table id="tbSearch" align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr>
                <td align="right">IP地址：</td>
                <td colspan=4><input id="ip" name="ip" value="${ip }" type="text" ltype="text" class="validate[required,custom[ipv4]] ip2" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">Mac地址：</td>
                <td colspan=4><input id="macIp" name="macIp" value="${macIp }" type="text" ltype="text" class="validate[optional,maxSize[512]] ip2" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">Vlan：</td>
                <td colspan=4><input id="Vlan" name="Vlan" value="${Vlan }" type="text" ltype="text" class="validate[optional,maxSize[512]] ip2" /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSearch" type="submit" value="绑定"  onclick="bindMac('yes')" class="l-button mg6" style="width:88px;"/>
           		</td>
           		<td align="left" colspan=3 >
           		<input id="bnParamCfg" type="button" value="解绑" onclick="bindMac('no')" class="l-button mg6" style="width:88px;"/>
           		</td>
            </tr>
		</table>
    </form>
</body>
</html>