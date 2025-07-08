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
<title>IP Host编辑</title>
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
			var message="${bindResult}";
			alert(message);
			window.parent.saveOK('', '');
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
			url: timeURL('${ctx}/system/employee/description.do?orgId='+orgId),
        	width: 200, selectBoxWidth: 200, selectBoxHeight: 200, 
			textField: 'name', 
			valueFieldID: 'userId',
			initValue: '${ipHost.userId}'
		});
		
		$("#ipNetMaskSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 300, 
        	valueFieldID: 'ipNetMask',
        	treeLeafOnly: false,
        	initValue: '${ipHost.ipNetMask}',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=subnetMask'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    		//checkIpMask();
	        }
        });
        
        $("#osTypeSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 200, 
        	valueFieldID: 'osType',
        	treeLeafOnly: false,
        	initValue: '${ipHost.osType}'==''?'0':'${ipHost.osType}',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=HostOSType'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    		//$("#osType").val(id);
	        }
        });
        
         $("#ipHostTypeSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 200, 
        	valueFieldID: 'ipHostTypeId',
        	treeLeafOnly: false,
        	initValue: '${ipHost.ipHostTypeId}'==''?'0':'${ipHost.ipHostTypeId}',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=IpHostType'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    		//$("#osType").val(id);
	        }
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
		$("#formSave").validationEngine();
		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
		
		});
		
		function checkIpMask() {
		var ip = $("#ipAddr").val();
		var mask = $("#ipNetMask").val();
		if( ip==null || ip=="" || mask==null || mask=="" ){
			return;
		}
		var masknum = getallmarknum(mask);
		var iplan = iptolan(ip+"/"+masknum);
		var ipMin = mininlan(iplan);
		ip = numtoip(ipMin);
		$("#ipAddr").val(ip);
		}
		
	  var countIndex=0;
		//查询条件控制
      function QueryBuilder(){
     	var url = timeURL('${ctx}/fas/res/host/ipHost/ajaxQuerySwitchIf.do');
        var switchIdIndex="${ipHost.switchId}";
        var inIndexId="${ipHost.ifIndex}";
        if(isNullStr(switchIdIndex)||isNullStr(inIndexId)){
        	switchIdIndex="${switchId}";
        	inIndexId="${indexId}";
        }
        var switchId =$("#switchId").val();
        var ifIndex=$("#ifIndex").val();
        <c:if test="${ipHost.switchId !=''}">
        	if (countIndex==0){
        		switchId=switchIdIndex;
        		ifIndex=inIndexId;
        		countIndex++;
        	}
		</c:if>
       	$.ajax({
        		url: url,
        		data: "id="+switchId+"&date="+new Date(),
        		dataType: 'json',
        		success: function(data) {
       				 $("#switchId").empty();
       				 if(data.listSwitch!=null&&data.listSwitch!=''){
       				 	$("#switchId").append("<option value='-1'>请选择设备</option>");
	        			 $.each(data.listSwitch,function(i,value){
	        			 	if(value.switchId==switchId){
	        					$("#switchId").append("<option value="+value.switchId+" selected>"+value.switchIp+"</option>");
	        				}else{
	        					$("#switchId").append("<option value="+value.switchId+">"+value.switchIp+"</option>");
	        				}
	       				 });
       				 }else{
       				 	$("#switchId").append("<option value='-1'></option>");
       				 }
       				 
       				 $("#ifIndex").empty();
       				 if(switchId==''||switchId==null||data.listPort==null||data.listPort==''){
       				 	 $("#ifIndex").append("<option value='-1'></option>");
       				 }else{
       				 	 $("#ifIndex").append("<option value='-1'>请选择接口</option>");
       				 	 $.each(data.listPort,function(i,value){
       				 	 	if(value.id.ifIndex==ifIndex){
       				 	 		$("#ifIndex").append("<option value="+value.id.ifIndex+" selected>"+value.ifName+"</option>");
       				 	 	}else{
       				 	 		$("#ifIndex").append("<option value="+value.id.ifIndex+" >"+value.ifName+"</option>");
       				 	 	}
       				 	});
       				 }
        		}
        	});        
     	}
     	
   function sendform(flag){
   			var text=$("#switchId").find("option:selected").text();  
   			$("#switchIp").val(text);
   			$("#flag").val(flag);
			document.getElementById("formSave").submit();
		}
   $(document).ready(function() {
  		$(".required").each(function() {
  			var $this  = $(this);
  			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
  		});
    });	
   //<c:if test="${action == 'edit' }">  </c:if>
</script>

</head>
<body onload="QueryBuilder()">
	<form id="formSave" modelAttribute="ipHost" action="${ctx}/fas/res/dailyMaintain/netInfoManage/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="ipHostId" value="${ipHost.ipHostId}"/>
		<input type="hidden" name="ipMacBindId" value="${ipHost.ipMacBindId}"/>
		<input type="hidden" id="switchIp" name="switchIp" value=""/>
		<input type="hidden" id="flag" name="flag" value=""/>
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
				<td colspan="6" align="left" style="font-weight:bold">交换机</td>
			</tr>
			<tr>
				<td align="right">接入交换机：</td>
				<td>
				<select onChange="QueryBuilder()" name="switchId" id="switchId" style="width:200px;">
						<option value=""></option>
				</select>
				</td>
				<td></td>
				<td align="right">交换机接口：</td>
				<td>
				<select name="ifIndex" id="ifIndex" style="width:200px;">
						<option value=""></option>
				</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td colspan=6 style="height:12px"></td>
			</tr>
			<tr>
				<td colspan="6" align="left" style="font-weight:bold">设备</td>
			</tr>
			<tr>
				<td align="right">所属机构：</td>
				<td><input id="mainOrg" name="mainOrg" type="text" class="ip1"/></td>
				<td></td>
				<td align="right">设备类型：</td>
				<td><input id="ipHostTypeSelect" type="text" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">使用者：</td>
				<td>
					<input id="user" type="text" class="ip1"/>
				</td>
				<td></td>
				<td align="right">MAC地址：</td>
				<td><input id="macAddr" name="macAddr" value="${ipHost.macAddr}" type="text" ltype="text" class="validate[required[MAC地址不能为空],custom[macAddr]] ip1" /></td>
				<td class="required"></td>
			</tr>
			<tr>
				<td align="right">IP地址：</td>
				<td><input id="ipAddr" name="ipAddr" value="${ipHost.ipAddr }" type="text" ltype="text" class="validate[optional,custom[ipv4]] ip1"  /></td>
				<td ></td>
				<td align="right">IP地址掩码：</td>
				<td><input id="ipNetMaskSelect" name="ipNetMaskSelect" type="text" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">设备名称：</td>
				<td><input id="hostName" name="hostName" value="${ipHost.hostName}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
				<td ></td>
				<td align="right">SNMP只读串：</td>
				<td><input id="snmpRoString" name="snmpRoString" value="${ipHost.snmpRoString}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">序列号：</td>
				<td><input id="serialNO" name="serialNO" value="${ipHost.serialNO}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
				<td></td>
				<td align="right">资产编号：</td>
				<td><input id="assertNO" name="assertNO" value="${ipHost.assertNO}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">操作系统：</td>
				<td><input id="osTypeSelect" type="text" class="ip1" /></td>
				<td></td>
				<td align="right">操作系统版本：</td>
				<td><input id="osVersion" name="osVersion" value="${ipHost.osVersion}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
					<td align="right">信息点编号：</td>
					<td><input id="infoPointNo" name="infoPointNo"
						value="${ipHost.infoPointNo}" type="text" ltype="text"
						class="validate[optional,maxSize[64]] ip1" />
					</td>
					<td></td>
					<td align="right">Vlan</td>
					<td>
						<input id="vlanNo" name="vlanNo"
						value="${ipHost.vlanNo}" type="text" ltype="text"
						class="validate[optional,maxSize[64]] ip1" />
					</td>
					<td></td>
				</tr>
			<tr>
           		<td align="right" colspan=3 >
           		<input id="bnRelease" type="button" value="释放" onclick="sendform('no')" class="l-button mg6" />
           		</td>
           		<td align="left" colspan=3 >
           		<input id="bnSave" type="button" value="保存" onclick="sendform('yes')" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form>
    <br/>
</body>
</html>

