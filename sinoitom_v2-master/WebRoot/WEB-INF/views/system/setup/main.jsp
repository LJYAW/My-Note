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
<title>系统配置</title>
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
var canItfSelect = false;
var manager;
var count=0;
	$(function ()
	{	
       manager= $("#itfNameList").ligerComboBox({
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'name',
        	valueField: 'name',
        	valueFieldID: 'name',
        	treeLeafOnly: false,
        	url: timeURL('${ctx}/system/setup/sysItfList.do'),
        	/*initValue: '${interfacename}',
        	 onBeforeOpen: function(){canItfSelect = true;}, */
	        onSelected: function (id, name)
	        {
        		/*  if( !canItfSelect ){
        			return;
        		} */
        		/* if(id!=null&&id!=''&&count==0){
        			manager.selectValue(name);
        			count++;
        		} */
	        	var url = timeURL('${ctx}/system/setup/sysItfInfo.do?interfaceName='+id);
	        	$.ajax({
	        		url: url,
	        		dataType: 'json',
	        		success: function(data) {
	        			emptyval();
	        			var value=eval(data);
	        			$("#ipAddr").val(value.ipAddr);
	        			$("#ipMask").val(value.ipMask);
	        			$("#gateway").val(value.gateway);
	        			$("#dnsIpaddr").val(value.dnsIpaddr);
	        			$("#macaddr").val(value.macAddr);
	        			$("#status").val(value.status);
	        		}
	        	});        			
	        }
        });
        
		$("#formItfSet").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formItfSet").validationEngine({promptPosition: "centerRight"});	

				
		<c:if test="${result=='success'}">
		window.parent.$.ligerDialog.success('保存成功'); 
		</c:if>
		<c:if test="${result=='error'}">
		window.parent.$.ligerDialog.error('${message}');
		</c:if>

	});
	
   function onRestart(){
   	window.parent.$.ligerDialog.confirm('是否确认重启服务器？', function (yes)
      {
      if(yes){
	    	var url = timeURL('${ctx}/system/setup/onRestart.do');
	    	$.ajax({
	        		url: url,
	        		data: "date="+new Date(),
	        		dataType: 'json',
	        		success: function(data) {
	        		}
	    	});
    	}
    });
   }
   
   function onRestartBas(){
   	window.parent.$.ligerDialog.confirm('是否确认重启SinoBaas？', function (yes)
      {
      if(yes){
	    	var url = timeURL('${ctx}/system/setup/onRestartBas.do');
	    	$.ajax({
	        		url: url,
	        		data: "date="+new Date(),
	        		dataType: 'json',
	        		success: function(data) {
	        		}
	    	});
    	}
    });
   }
   
   function onClose(){
   		window.parent.$.ligerDialog.confirm('是否确认关机？', function (yes)
        {
       		if(yes){
       			var url = timeURL('${ctx}/system/setup/onClose.do');
    			$.ajax({
        		url: url,
        		data: "date="+new Date(),
        		dataType: 'json',
        		success: function(data) {
        		}
   			});
        };
     });
   }
   
   function emptyval(){
 		$("#ipAddr").val('');
		$("#ipMask").val('');
		$("#gateway").val('');
		$("#dnsIpaddr").val('');
		$("#macaddr").val('');
		$("#status").val('');
   }
</script>

</head>
<body> 
	<div class="wFull hL">系统配置：</div>
	<form id="formItfSet" action="${ctx}/system/setup/sysItfSet.do" method="post">
		<input type="hidden" id="interfacename" name="interfacename" value="${interfacename}"/>
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
                <td align="right">网络接口:</td>
                <td colspan=4><input id="itfNameList" name="itfNameList" value="" type="text" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">接口状态:</td>
                <td colspan=4><input id="status" name="status" value="" type="text" class="ip1" readonly/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">MAC地址:</td>
                <td colspan=4><input id="macaddr" name="macaddr" value="${macaddr}" type="text" ltype="text" class="ip1" readonly/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">IP地址:</td>
                <td colspan=4><input id="ipAddr" name="ipAddr" value="${ipAddr}" type="text" ltype="text" class="validate[optional,custom[ipv4]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">子网掩码:</td>
                <td colspan=4><input id="ipMask" name="ipMask" value="${ipMask}" type="text" ltype="text" class="validate[optional,custom[ipv4]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">网关IP:</td>
                <td colspan=4><input id="gateway" name="gateway" value="${gateway}" type="text" ltype="text" class="validate[optional,custom[ipv4]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">DNS服务地址:</td>
                <td colspan=4><input id="dnsIpaddr" name="dnsIpaddr" value="${dnsIpaddr}" type="text" ltype="text" class="validate[optional,custom[ipv4]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align=right>
                <input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
                </td>
           		<td align=center>
           		<input id="restart" type="button" value="重启机器" onclick="onRestart()" class="l-button mg6" style="width:100px"/>
           		</td>
                <td align=left>
                <input id="restart" type="button" value="重启SinoBaaS" onclick="onRestartBas()" class="l-button mg6"  style="width:100px"/>
                </td>
                <td align="right">
                <input id="close" type="button" value="关机" onclick="onClose()" class="l-button mg6" />
                </td>
                <td colspan=2></td>
            </tr>
		</table>
    </form>
</body>
</html>

