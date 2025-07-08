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
        
//         manager=$("#osClass").ligerComboBox({
//         	width: 200,
//         	selectBoxWidth: 200, selectBoxHeight: 200, 
        	valueFieldID: 'osClass',
//         	treeLeafOnly: false,
//         	initValue: '${ipHost.osClassCode}',
//         	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=HostOSType'), 
//         		checkbox: false, nodeWidth: 120 },
// 	    	onSelected: function (id, text){
// 	    		$("#osClassCode").val(id);
// 	    		osClass=text;
// 	    		getOsType();
// 	        }
//         });
        
        
         $("#ipHostTypeSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 200, 
        	valueFieldID: 'ipHostTypeId',
        	treeLeafOnly: false,
        	initValue: '${ipHost.ipHostTypeId}',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=IpHostType'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    		//$("#osType").val(id);
	        }
        });
        
		
	  	//$("#formSave").ligerForm({inputWidth:200});
	  	$("input").filter(".ip1").ligerTextBox({ width: 200 });
// 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
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
		  getOsType();
		  getDevAccess();
		});
		
		function sendform(){
			document.getElementById("formSave").submit();
		}
		
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
        var switchId =$("#switchId").val();
        var ifIndex=$("#ifIndex").val();
        <c:if test="${action=='edit'&&ipHost.switchId !=''}">
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
	        			 	if(value.devId==switchId){
	        					$("#switchId").append("<option value="+value.devId+" selected>"+value.devIpAddr+"</option>");
	        				}else{
	        					$("#switchId").append("<option value="+value.devId+">"+value.devIpAddr+"</option>");
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
   $(document).ready(function() {
  		$(".required").each(function() {
  			var $this  = $(this);
  			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
  		});
    });	
    
    
 	function getDevAccess(){
	   var myname="devAccessId";
           $.getJSON(
               "${ctx}/devices/accessUser/getData.do",
               function (result) {
                   if (result != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"\"  selected></option>");
                       for (var i = 0; i < result.length; i++) {
                          var access= result[i];
                          if(access.id=='${ipHost.devAccessId}'){
                            $("#" + myname).append("<option  value=\"" + access.id + "\" selected>" + access.acsUserName + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" + access.id + "\">" + access.acsUserName + "</option>");
                          }
                    	  
                       }
                   }
               }); 
	} 
	
	function getOsType(){
	   var myname="osType";
	   var osClassCode=$("#osClassCode").find("option:selected").val();
	   var osClass=$("#osClassCode").find("option:selected").text();
	   async: false ,
           $.getJSON(
               "${ctx}/cmdb/os/getByOsClass.do?osClass="+osClass,
               function (result) {
                   if (result != null) {
                       $("#" + myname).empty();
                       for (var i = 0; i < result.osTypes.length; i++) {
                          var osType= result.osTypes[i];
                          if(osType.osType=='${ipHost.osType}'){
                            $("#" + myname).append("<option  value=\"" + osType.osType + "\" selected>" + osType.osType + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" + osType.osType + "\">" + osType.osType + "</option>");
                          }
                       }
                       getOsVersion();
                   }
               }); 
               
             $("#osClass").val(osClass);  
	} 
	
	function  getOsVersion(){
	  var osType=$("#osType").find("option:selected").val();
	  var osClass=$("#osClassCode").find("option:selected").text();
	  var myname="osVersion";
	  async: false ,
           $.getJSON(
               "${ctx}/cmdb/vendorOSVersion/getOsVersionByOsClassAndOsType.do?osClass="+osClass+"&osType="+osType,
               function (result) {
                   if (result != null) {
                       $("#" + myname).empty();
                       for (var i = 0; i < result.list.length; i++) {
                          var version= result.list[i];
                          if(version.osVersion=='${ipHost.osVersion}'){
                            $("#" + myname).append("<option  value=\"" + version.osVersion + "\" osFeature=\"" + version.osFeature + "\" selected>" + version.osVersion + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" +version.osVersion + "\" osFeature=\"" + version.osFeature + "\">" + version.osVersion + "</option>");
                          }
                       }
                       
                       $("#osFeature").val($("#osVersion").find("option:selected").attr("osFeature"));
                   }
               }); 
	}
	
	
</script>

</head>
<body onload="QueryBuilder()">
	<form id="formSave" modelAttribute="ipHost" action="${ctx}/fas/res/host/ipHost/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="osClass" id="osClass" value=""/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="ipHostId" value="${ipHost.ipHostId}"/>
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
				<td><input id="ipAddr" name="ipAddr" value="${ipHost.ipAddr }" type="text" ltype="text" class="validate[required[IP地址不能为空],custom[ipv4]] ip1"  /></td>
				<td class="required"></td>
				<td align="right">IP地址掩码：</td>
				<td><input id="ipNetMaskSelect" name="ipNetMaskSelect" type="text" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">设备名称：</td>
				<td><input id="hostName" name="hostName" value="${ipHost.hostName}" type="text" ltype="text" class="validate[required[设备名称不能为空],maxSize[128]] ip1" /></td>
				<td class="required"></td>
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
				<td align="right">操作系统分类：</td>
				<td>
				 <s:resSelect id="osClassCode" name="osClassCode" value="${ipHost.osClassCode}" code="HostOSType"  ltype="select" style="width:202px" onchange=" getOsType()"/>
				</td>
				<td></td>
				<td align="right">操作系统类型：</td>
				<td>
					<select id="osType" name="osType"  style="width:200px;" onchange="getOsVersion()"></select>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right">操作系统版本：</td>
				<td>
					<select id="osVersion" name="osVersion"  style="width:200px;" onchange=""></select>
				</td>
				<td></td>
				<td align="right">操作系统特征：</td>
				<td><input id="osFeature" name="osFeature" value="${ipHost.osFeature}" type="text" ltype="text" class="ip1" /></td>
				<td></td>
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
				<!-- 
				<tr>
					<td align="right">接入交换机：</td>
					<td><input id="switchIdSelect" ltype='spinner' ligerui="{type:'int'}"  /></td>
					<td></td>
					<td align="right">交换机接口：</td>
					<td><input id="switchIfIndexSelect" ltype='spinner' ligerui="{type:'int'}"  /></td>
					<td></td>
				</tr>
				-->
			
			<tr>
					<td align="right">信息点编号：</td>
					<td><input id="infoPointNo" name="infoPointNo"
						value="${ipHost.infoPointNo}" type="text" ltype="text"
						class="validate[optional,maxSize[64]] ip1" />
					</td>
					<td></td>
					<td align="right">访问方式：</td>
					<td>
					<select id="devAccessId" name="devAccessId" style="width:202px" onchange="getAccessInfo()">
                  	</select>
                	</td>
                	<td></td>
				</tr>
			<tr>
           		<td align="right" colspan=3 >
           		<input id="bnSave" type="button" value="保 存" onclick="sendform()" class="l-button mg6" />
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

