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
<title>交换机编辑</title>
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

var dlg = null;
var lastDlg = null;
var cbSnmpCred = null;
var cbDevAccess = null;

var canOrgSelect = false;
var canVendorSelect = false;

	$(function ()
	{	
	
		if('${topoEditFlag}'==1){
		  <c:if test="${result=='success' && closeDlg=='true'}">
		    alert('修改成功');
			window.parent.closeWin();  
			</c:if>
		}else{
		   <c:if test="${result=='success'}">
			window.parent.saveOK();  
			</c:if>
			<c:if test="${result=='success' && closeDlg=='true'}">
			window.parent.closeDlg();  
			</c:if>
		}	
		

		var orgId = '${device.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:device.orgId}';
        $("#mainOrg").ligerComboBox({
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'orgId',
        	treeLeafOnly: false,
        	initValue: orgId,
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false },
            	onBeforeOpen: function(){canOrgSelect = true;},
            	onSelected: function (orgId)
    	        {
            		if( !canOrgSelect ){
            			return;
            		}
            		if( orgId==null || orgId=='' ){
            			return;
            		}
            		
		        	var url = timeURL('${ctx}/system/employee/getMinData.do?orgId='+orgId);
		        	$.ajax({
		        		url: url,
		        		dataType: 'json',
		        		success: function(data) {
		        			$("#contact").ligerGetComboBoxManager().setData(data);
		        		}
		        	});        			
		        }
        	}
        );
        
		$("#contact").ligerComboBox({ 
			url: timeURL('${ctx}/system/employee/getMinData.do?orgId='+orgId),
        	width: 130, selectBoxWidth: 196, selectBoxHeight: 200, 
			textField: 'name', 
			valueFieldID: 'contactId',
			initValue: '${device.contactId}'
		});




        
        
        $("#snmpDevTypeSelect").ligerComboBox({  
                data: [
                    { text: '交换机', id: '3' },
                    { text: '路由器', id: '2' }
                ], 
                valueFieldID: 'gatewayType',
                initValue:"${device.gatewayType}",
                width: 200,selectBoxWidth: 200
        });


       // $("#formSave").ligerForm({inputWidth:200});
        $("#formSave input").filter(".ip1").ligerTextBox({width: 200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		        
        $("#bnSnmpCfg").click(function(){
        	lastDlg = 'SnmpCfg';
        	var url = '${ctx}/fas/res/net/snmp/cred/add.do?oprType='+'switch';
     		window.parent.layer_show("添加SNMP信息 ", url, 800, 500); 
     		
     	});
		
        $("#bnAccessCfg").click(function(){
        	lastDlg = 'AccessCfg';
        	var url = '${ctx}/devices/accessUser/add.do';
     		window.parent.layer_show("添加设备访问信息 ", url, 800, 500); 
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
        
        $("#bnTest").click(function(){
        	$("#maingrid").empty();
        	$("#trMsg").removeClass("dN");
        	$("#trTxt").removeClass("dN");
        	$.ajax({
	    		url: timeURL('${ctx}/fas/res/net/device/access/accessModeTest.do'),
	    		dataType: 'json',
	    		success: function(data) {
	            	$("#trTxt").addClass("dN");
	            	/**
	    			if(data.result!=null&&data.result=='success'){
	    				aceessTestDis('yes');
	    			}else{
	    				aceessTestDis('no');
	    			}**/
	    			$("#maingrid").append(data.message);
	    		}
	    	});   
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
		$("#formSave").validationEngine();
		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
		
		refreshVendor();
		getDevAccess();
		getSnmpCred();
	});
	
	 //刷新厂商
 function refreshVendor() {
          $.getJSON(
              "${ctx}/cmdb/prodType/getVendor.do?date="+new Date(),
              function (result) {
                  if (result.vendors != null) {
                      $("#vendorId").empty();
                      for (var i = 0; i < result.vendors.length; i++) {
                          var vendor = result.vendors[i];
                          if(vendor.vendorID=='${prodModel.vendorID}'){
                           $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                          }
                          else{
                           $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                        }
                          
                      }
                      refreshClassCode();getOSName();
                  }
              });
  	}
  	
	//设备分类
function refreshClassCode() {
  	var vendorId=$("#vendorId").find("option:selected").val();
          $.getJSON(
              "${ctx}/cmdb/prodType/getCodeByVendor.do?vendorId="+vendorId,
              function (result) {
                  if (result.pClass != null) {
                      $("#devClassCode").empty();
                      for (var i = 0; i < result.pClass.length; i++) {
                          var pClass = result.pClass[i];
                          if(pClass.prodClassCode=='${prodModel.prodClassCode}'){
                            $("#devClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" selected>" + pClass.prodClassName + "</option>");
                          }else{
                            $("#devClassCode").append("<option  value=\"" + pClass.prodClassCode + "\">" + pClass.prodClassName + "</option>");
                          }
                          
                      }
                      refreshProdType();
                  }
              });
  	} 
  	
  	//产品类型
 function refreshProdType() {
  	var vendorId=$("#vendorId").find("option:selected").val();
  	var prodClassCode=$("#devClassCode").find("option:selected").val();
  	var devClassName=$("#devClassCode").find("option:selected").text();
  	$("#devClassName").val(devClassName);
          $.getJSON(
              "${ctx}/cmdb/prodType/getProdTypeByVendorAndClassCode.do?vendorId="+vendorId+"&prodClassCode="+prodClassCode,
              function (result) {
                  if (result.prodType != null) {
                      $("#prodTypeID").empty();
                      for (var i = 0; i < result.prodType.length; i++) {
                          var prodType = result.prodType[i];
                          if(prodType.prodTypeID=='${prodModel.prodTypeID}'){
                            $("#prodTypeID").append("<option  value=\"" + prodType.prodTypeID + "\" typeCode=\"" + prodType.typeCode + "\" selected>" + prodType.dispName + "</option>");
                          }else{
                            $("#prodTypeID").append("<option  value=\"" + prodType.prodTypeID + "\" typeCode=\"" + prodType.typeCode + "\">" + prodType.dispName + "</option>");
                          }
                          
                      }
                     
                     refreshProdSeries();
                  }
              });
  	}	
  	
 	//产品类型
 function refreshProdSeries() {
   var typeCode=$("#prodTypeID").find("option:selected").attr("typeCode");
   var typeCodeName=$("#prodTypeID").find("option:selected").text();
   $("#devTypeCode").val(typeCode);
   $("#devTypeName").val(typeCodeName);
 
  	var vendorId=$("#vendorId").find("option:selected").val();
  	var prodClassCode=$("#devClassCode").find("option:selected").val();
  	var prodTypeId=$("#prodTypeID").find("option:selected").val();
          $.getJSON(
            "${ctx}/cmdb/vendorProdSeries/getData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId,
              function (result) {
                  if (result.seriesList != null) {
                      $("#prodSeries").empty();
                      for (var i = 0; i < result.seriesList.length; i++) {
                          var series = result.seriesList[i];
                          if(series.prodSeries=='${prodModel.prodSeries}'){
                            $("#prodSeries").append("<option  value=\"" + series.prodSeries + "\"  selected>" + series.prodSeries + "</option>");
                          }else{
                            $("#prodSeries").append("<option  value=\"" + series.prodSeries + "\" >" + series.prodSeries + "</option>");
                          }
                          
                      }
                    refreshProdModel();
                  }
              });
  	} 
  	
  	//产品型号
 function refreshProdModel() {
  	var vendorId=$("#vendorId").find("option:selected").val();
  	var prodClassCode=$("#devClassCode").find("option:selected").val();
  	var prodTypeId=$("#prodTypeID").find("option:selected").val();
  	var prodSeries=$("#prodSeries").find("option:selected").val();
          $.getJSON(
            "${ctx}/cmdb/vendorProdModel/getModelData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId+"&prodSeries="+prodSeries,
              function (result) {
                  if (result.modelOIDlist != null) {
                      $("#modelOid").empty();
                      for (var i = 0; i < result.modelOIDlist.length; i++) {
                          var model = result.modelOIDlist[i];
                          if(model.prodModelOid=='${prodModel.prodModelOid}'){
                            $("#modelOid").append("<option  value=\"" + model.prodModelOid + "\"  selected>" + model.prodModelName + "</option>");
                          }else{
                            $("#modelOid").append("<option  value=\"" + model.prodModelOid + "\" >" + model.prodModelName + "</option>");
                          }
                          
                      }
                    
                  }
              });
  	} 		  	 	
	
	//操作系统名称
    function getOSName() {  
       var myname="osType";
       var vendorID=$("#vendorId").find("option:selected").val();
           $.getJSON(
               "${ctx}/cmdb/os/getOSNameByVendorID.do?id="+vendorID+"&date="+new Date(),
               function (result) {
                   if (result.oSNamelist != null) {
                       $("#" + myname).empty();
                      // $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       for (var i = 0; i < result.oSNamelist.length; i++) {
                          var oSName= result.oSNamelist[i];
                          if(oSName.osType=='${device.osType}'){
                            $("#" + myname).append("<option  value=\"" + oSName.osType + "\" selected>" + oSName.osType + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" + oSName.osType + "\">" + oSName.osType + "</option>");
                          }
                    	  
                       }
                       getOSVersion();
                   }
               });
   	}	
   	
   	//操作系统版本
   function getOSVersion(){
   	   var myname="osVersion";
       var vendorID=$("#vendorId").find("option:selected").val();
       var oSName=$("#osType").find("option:selected").val();
           $.getJSON(
               "${ctx}/cmdb/vendorOSVersion/getOSVersion.do?vendorID="+vendorID+"&oSName="+oSName+"&date="+new Date(),
               function (result) {
                   if (result.vendorOSVersionlist != null) {
                       $("#" + myname).empty();
                      // $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       //$("#" + myname).append("<option value=\"X.X\"  selected>所有版本</option>");
                       for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
                          var vendorOSVersion= result.vendorOSVersionlist[i];
                          if(vendorOSVersion.osVersion=='${device.osVersion}'){
                        
                           	$("#" + myname).append("<option  value=\"" + vendorOSVersion.osVersion + "\" selected>" + vendorOSVersion.osVersion + "</option>");
                          }else{
                             $("#" + myname).append("<option  value=\"" + vendorOSVersion.osVersion + "\">" + vendorOSVersion.osVersion + "</option>");
                          }
                    	  
                       }
                       getDevAccessPrompt_ID();
                       refeshosFeature();
                   }
               });
   	}
	
	
	//获取访问方式
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
                          if(access.id=='${device.devAccessId}'){
                            $("#" + myname).append("<option  value=\"" + access.id + "\" selected>" + access.acsUserName + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" + access.id + "\">" + access.acsUserName + "</option>");
                          }
                    	  
                       }
                     getAccessInfo();
                   }
               }); 
		 
	}
	
	
	//当devAccessId访问方式不为空时
	function getAccessInfo(){
	 var id=$("#devAccessId").find("option:selected").val();
	 if( id==null || id=='' ){
			$(".devAccess").addClass("dN");
				}
				else{
					$(".devAccess").removeClass("dN");
					//aceessTestDis();//访问方式需要测试；
		        	var url = '${ctx}/devices/accessUser/getItem.do?id='+id;
		        	$.ajax({
		        		url: url,
		        		dataType: 'json',
		        		success: function(data) {
		        			$("#isPermitAccess").val(1);
		        			$("#accessTool").val(data.accessTool);
		        			$("#accessPort").val(data.accessPort);
		        			
		        			if(data.userType==0){
		        				$("#userType").val("普通用户");
		        			}else{
		        				$("#userType").val("特权用户");
		        			}
		        			$("#userName").val(data.userName);
		        			$("#passWord").val(data.passWord);
		        			$("#enablePassWord").val(data.privModePasswd);
		        		}
		        	});        			
				}
	}
	
	
	//获取SNMP配置信息
	function getSnmpCred(){
	   var myname="snmpCredId";
           $.getJSON(
               "${ctx}/fas/res/net/snmp/cred/getSnmpCredData.do",
               function (result) {
                   if (result.credlist != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"\"  selected></option>");
                       for (var i = 0; i < result.credlist.length; i++) {
                          var cred= result.credlist[i];
                          if(cred.snmpCredId=='${device.snmpCredId}'){
                            $("#" + myname).append("<option  value=\"" + cred.snmpCredId + "\" selected>" + cred.snmpCredName + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" + cred.snmpCredId + "\">" + cred.snmpCredName + "</option>");
                          }
                    	  
                       }
                     getSnmpCredInfo();
                   }
               }); 
	}
	
	
	//当snmpCredId不为空时
	function getSnmpCredInfo(){
	 var id=$("#snmpCredId").find("option:selected").val();
		if( id==null || id=='' ){
					$("#isSnmpEnabled").val(0);
					$(".devSnmp123").addClass("dN");
					$(".devSnmp12").addClass("dN");
					$(".devSnmp3").addClass("dN");
				}
				else{
					$(".devSnmp123").removeClass("dN");	
		        	var url = timeURL('${ctx}/fas/res/net/snmp/cred/getItem.do?id='+id);
		        	$.ajax({
		        		url: url,
		        		dataType: 'json',
		        		success: function(data) {
		        			$("#isSnmpEnabled").val(1);
		        			$("#snmpVersion").val(data.snmpVersion);
		        			$("#snmpPort").val(data.snmpPort);
		        			$("#snmpRoStr").val(data.snmpRoStr);
		        			$("#snmpRwStr").val(data.snmpRwStr);
		        			$("#credName").val(data.credName);
		        			$("#credDesc").val(data.credDesc);
		        			$("#credUserName").val(data.credUserName);
		        			$("#credContextName").val(data.credContextName);
		        			$("#credAuthProt").val(data.credAuthProt);
		        			$("#credAuthPassword").val(data.credAuthPassword);
		        			$("#credEncryptProt").val(data.credEncryptProt);
		        			$("#credEncryptPassword").val(data.credEncryptPassword);
		        			if( data.snmpVersion == 'V3'){
		    					$(".devSnmp3").removeClass("dN");	
		    					$(".devSnmp12").addClass("dN");	
		        			}
		        			else{
		        				$(".devSnmp3").addClass("dN");
		    					$(".devSnmp12").removeClass("dN");	
		        			}
		        		}
		        	});        			
				}
	}
	
	
	
	function getDevAccessPrompt_ID(){
	 var vendorID=$("#vendorId").find("option:selected").val();
	 var oSName=$("#osName").find("option:selected").val();
	 var osVersion=$("#osVersion").find("option:selected").val();
	 var url = "${ctx}/devices/accessCmd/getPromptByVendorAndOsNameAndOsVersion.do?vendorID="+vendorID+"&osName="+oSName+"&osVersion="+osVersion;
	 $.ajax({
        		 type: "POST",
	             url: url,
	             contentType: "application/x-www-form-urlencoded; charset=utf-8",
	             dataType: "json",
        		 success: function(data) {
           			$("#devPromptId").val(data.id);
        		}
        	});    
	}
	
	
	
	
	function showDlg(title, width, height, url)
	{
		dlg = $.ligerDialog.open({ title:title, width:width, height:height, url:url, isHidden:false, showMax: true, isResize: true });
	}

	function saveOK(action, data)
	{
		if( lastDlg == 'SnmpCfg' ){
	    	$.ajax({
	    		url: timeURL('${ctx}/fas/res/net/snmp/cred/getMinData.do'),
	    		dataType: 'json',
	    		success: function(data) {
	       			cbSnmpCred.setData(data);
	    		}
	    	});        						
		}
		
		else if( lastDlg == 'AccessCfg' ){
	    	$.ajax({
	    		url: timeURL('${ctx}/fas/res/net/device/access/getMinData.do'),
	    		dataType: 'json',
	    		success: function(data) {
	       			cbDevAccess.setData(data);
	    		}
	    	});        						
		}
	}

	function closeDlg()
	{
		if( dlg ){
			dlg.close();
		}
	}
$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });	
  
  
 function checkSwitchIp(){
   var switchIp=$("#switchIp").val();
   $.getJSON(
              "${ctx}/fas/res/net/switch/checkSwitchIp.do?switchIp="+switchIp,    
              function (data) {
                  if (data.result=="0") {
                     window.parent.$.ligerDialog.warn("该设备管理地址已存在,请重新填写!");
                  }else if(data.result=="1"){
                       window.parent.$.ligerDialog.warn("设备管理地址已不能为空!");
                  }
              });
  } 
  
  
  function refeshosFeature(){
  var vendorId=$("#vendorId").find("option:selected").val();
  var osType=$("#osType").find("option:selected").val();
   var osVersion=$("#osVersion").find("option:selected").val();
 $.getJSON(
     "${ctx}/cmdb/vendorOSVersion/getOsFeature.do?vendorID="+vendorId+"&osName="+osType+"&osVersion="+osVersion,
     function (result) {
         if (result.list != null) {
             $("#osFeature").empty();
             for (var i = 0; i < result.list.length; i++) {
                 var osFeature = result.list[i].osFeature;
                 if(osFeature=='${device.osFeature}'){
                   $("#osFeature").append("<option  value=\"" + osFeature + "\"  selected>" + osFeature + "</option>");
                 }else{
                   $("#osFeature").append("<option  value=\"" + osFeature + "\" >" + osFeature + "</option>");
                 }
                 
             }
         }
     });
}
</script>

</head>
<body> 
	<form id="formSave" action="${ctx}/fas/res/net/devices/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="id" value="${device.devId}"/>
		<input type="hidden" id="orgId" name="orgId" value="${device.orgId}"/>
		<input type="hidden" id="contactId" name="contactId" value="${device.contactId}"/>
		<input type="hidden" id=devPromptId name="devPromptId" value="${device.devPromptId }"/>
		<input type="hidden" id="snmpEnabled" name="snmpEnabled" value="${device.snmpEnabled}"/>
		<input type="hidden" id="permitAccess" name="permitAccess" value="${device.permitAccess}"/>
		<input type="hidden" id="devTypeCode" name="devTypeCode" value="${device.devTypeCode}"/>
		<input type="hidden" id="devClassName" name="devClassName" value="${device.devClassName}"/>
		<input type="hidden" id="devTypeName" name="devTypeName" value="${device.devTypeName}"/>
		
		<input type="hidden" name="topoEditFlag" value="${topoEdit}"/>
		
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
                <td align="right" nowrap>所属机构:</td>
                <td><input id="mainOrg" type="text" class="ip1"/></td>
                <td></td>
                <td align="right" nowrap>联系人:</td>
                <td><input id="contact" type="text" class="ip1"/></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">设备名称:</td>
                <td><input id="devName" name="devName" value="${device.devName}" type="text" ltype="text" class="validate[required[设备名称不能为空]] ip1" /></td>
                <td class="required"></td>
                <td align="right">设备管理地址:</td>
                <td><input id="devIpAddr" name="devIpAddr" value="${device.devIpAddr}" type="text" ltype="text" class="validate[required[设备管理地址不能为空],custom[ipv4]] ip1" onchange="checkSwitchIp();"/></td>
                <td class="required"></td>
            </tr>
            <tr>
                <td align="right">显示名称:</td>
                <td><input id="devDisplay" name="devDisplay" value="${device.devDisplay}" type="text" ltype="text" class="validate[required[显示名称不能为空]] ip1" /></td>
                <td class="required"></td>
                <td align="right">设备代理地址:</td>
                <td><input id="agentIp" name="agentIp" value="${device.agentIp}" type="text" ltype="text" class="validate[optional,custom[ipv4]] ip1" onchange="checkSwitchIp();"/></td>
                <td ></td>
            </tr>
            
            <tr>
                <td align="right">部署位置:</td>
                <td><input id="deploySite" name="deploySite" value="${device.deploySite}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1" /></td>
                <td></td>
                <td align="right">设备区域:</td>
                <td><input id="bizZone" name="bizZone" value="${device.bizZone}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1"  /></td>
                <td></td>
            </tr>
             <tr>
                <td align="right">机柜槽位:</td>
                <td><input id="cabinetSlot" name="cabinetSlot" value="${device.deploySite}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1" /></td>
                <td></td>
                <td align="right">设备描述:</td>
                <td><input id="devDesc" name="devDesc" value="${device.devDesc}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">管理状态:</td>
                <td><s:resSelect name="adminStatus" value="${device.adminStatus}" code="Subnet_AdminStatus" ltype="select" style="width:202px"/></td>
                <td></td>
                <td align="right">工作状态:</td>
                <td><s:resSelect name="opStatus" value="${device.opStatus}" code="Subnet_OperStatus" ltype="select" style="width:202px"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">产品厂商:</td>
                <td>
                <select id="vendorId" name="vendorId" style="width:202px" onchange="refreshClassCode();getOSName()">
                      <option value="-1">请选择</option>
                 </select>
                </td>
                <td></td>
                <td align="right">设备分类:</td>
                <td>
                 <select id="devClassCode" name="devClassCode" style="width:202px" onchange="refreshProdType()">
                      <option value="-1">请选择</option>
                  </select>
                </td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right">产品类型:</td>
                <td>
                 <select id="prodTypeID" name="prodTypeID" style="width:202px" onchange="refreshProdSeries()">
                    <option value="-1">请选择</option>
                 </select>
                </td>
               <td></td>
               <td align="right">产品系列:</td>
               <td>
                  <select id="prodSeries" name="prodSeries" style="width:202px" onchange="refreshProdModel()">
                  </select>
               </td>  
               <td></td>
            </tr>
            
            <tr>
                <td align="right">产品型号:</td>
                 <td>
                  <select id="modelOid" name="modelOid" style="width:202px">
                  </select>
               </td>
                <td></td>
                <td align="right">产品序列号:</td>
                <td><input id="productSN"  name="productSN" value="${device.productSN}" type="text" class="ip1" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">操作系统类型:</td>
                <td>
                 <select id="osType" name="osType" style="width:202px" onchange="getOSVersion()">
                  </select>
                </td>
                <td></td>
                <td align="right">操作系统名称:</td>
                <td><input id="osName" name="osName" value="${device.osName}" type="text" class="ip1" /></td>
                <td></td>
            </tr>
            
             <tr>
               <td align="right">版本分类:</td>
                <td>
                <select id="osVersion" name="osVersion" style="width:202px" onchange="getDevAccessPrompt_ID();refeshosFeature()">
                  </select>
                </td>
                <td></td>
               <td align="right">版本特征:</td>
                <td>
                <select id="osFeature" name="osFeature" style="width:202px" >
                  </select>
                </td>
                <td></td>
             </tr>
             
              <tr>
               <td align="right">软件版本:</td>
                <td><input id="osRelease" name="osRelease" value="${device.osRelease}" type="text" class="ip1" /></td>
                <td></td>
                  
                <td align="right">FDB获取方式:</td>
                <td>
                 <select id="getMacMode" name="getMacMode" style="width:202px">
                <c:choose>
                  <c:when test="${device.getMacMode==1}">
                     <option value="1" selected="selected">Snmp</option>
                   <option value="2">CLI</option>
                  </c:when>
                  <c:otherwise>
                    <option value="1">Snmp</option>
                   <option value="2" selected=>CLI</option>
                  </c:otherwise>
                </c:choose>
                  
                 </select>
                </td>
                <td></td>
             </tr>
            
            
            <tr>
                <td align="right">SNMP配置信息:</td>
                <td colspan=3>
                <select id="snmpCredId" name="snmpCredId" style="width:202px" onchange="getSnmpCredInfo()">
                  </select>
                </td>
                <td colspan=2><input id="bnSnmpCfg" type="button" value="配置SNMP信息" class="l-button" style="width:108px;"/></td>
            </tr>
            <tr class="devSnmp123 dN">
                <td align="right">Snmp版本:</td>
                <td><input id="snmpVersion" value="${snmpInfo.snmpVersion}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">Snmp端口:</td>
                <td><input id="snmpPort" value="${snmpInfo.snmpPort}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp12 dN">
                <td align="right">SNMP只读串:</td>
                <td><input id="snmpRoStr" value="${snmpInfo.snmpRoStr}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">SNMP读写串:</td>
                <td><input id="snmpRwStr" value="${snmpInfo.snmpRwStr}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">凭证名称:</td>
                <td><input id="credName" value="${snmpInfo.credName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">凭证描述:</td>
                <td><input id="credDesc" value="${snmpInfo.credDesc}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">用户名:</td>
                <td><input id="credUserName" value="${snmpInfo.credUserName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">上下文名称:</td>
                <td><input id="credContextName" value="${snmpInfo.credContextName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">认证协议:</td>
                <td><input id="credAuthProt" value="${snmpInfo.credAuthProt}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">认证密码:</td>
                <td><input id="credAuthPassword" value="${snmpInfo.credAuthPassword}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">加密协议:</td>
                <td><input id="credEncryptProt" value="${snmpInfo.credEncryptProt}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">加密密码:</td>
                <td><input id="credEncryptPassword" value="${snmpInfo.credEncryptPassword}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">访问方式:</td>
                <td colspan=3>
                <select id="devAccessId" name="devAccessId" style="width:202px" onchange="getAccessInfo()">
                  </select>
                </td>
                <td colspan=2><input id="bnAccessCfg" type="button" value="配置访问方式" class="l-button" style="width:108px;" /></td>
            </tr>
            <tr class="devAccess dN">
                <td align="right">访问工具:</td>
                <td><input id="accessTool" value="${accessInfo.accessTool}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">访问端口:</td>
                <td><input id="accessPort" value="${accessInfo.accessPort}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devAccess dN">
                <td align="right">用户类型:</td>
                <td><input id="userType" value="${accessInfo.userPrivilege}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">用户名称:</td>
                <td><input id="userName" value="${accessInfo.userName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devAccess dN">
                <td align="right">密码:</td>
                <td><input id="passWord" value="${accessInfo.passWord}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">特权密码:</td>
                <td><input id="enablePassWord" value="${accessInfo.enablePassWord}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">缺省网关:</td>
                <td><input id="gatewayIP" name="gatewayIP" value="${device.gatewayIP}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1" /></td>
                <td></td>
                <td align="right" nowrap>网关Snmp只读串:</td>
                <td><input id="gatewaySnmp" name="gatewaySnmp" value="${device.gatewaySnmp}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">网关设备类型:</td>
                <td><s:resSelect name="gatewayType"  id="gatewayType" value="${device.gatewayType}" code="dev_Type" ltype="select" style="width:202px"/></td>
                <td></td>
               	<td align="right" nowrap >ARP表获取方式：</td>
				<td>
<!-- 				<input id="arpGetMethodSelect" type="text" class="ip1" /> -->
				
				 <select id="getArpMode" name="getArpMode" style="width:202px">
                <c:choose>
                  <c:when test="${device.getArpMode==1}">
                   <option value="1" selected="selected">snmp</option>
                   <option value="2">telnet</option>
                   <option value="3">ssh</option>
                  </c:when>
                  <c:when test="${device.getArpMode==2}">
                   <option value="1" >snmp</option>
                   <option value="2" selected="selected">telnet</option>
                   <option value="3">ssh</option>
                  </c:when>
                  <c:otherwise>
                   <option value="1" >snmp</option>
                   <option value="2">telnet</option>
                   <option value="3" selected="selected">ssh</option>
                  </c:otherwise>
                </c:choose>
                  
                 </select>
				
				</td>
                <td></td>
            </tr>
            <tr>
            	<td colspan=6 align="center">
            	<table align="center">
            		<tr>
	            	<td><br /><%--<input id="bnTest" type="button" value="测试" class="mg6 dN" />
	            	--%>
	            	<br /></td>
	            	<td><input id="bnSave" type="submit" value="保 存" class="l-button" /></td>
	            	<c:if test="${action=='add'}">
	                <td><input id="bnSaveAdd" type="submit" value="保存并继续添加"  class="l-button" /></td>
	                </c:if>
	                <td><input id="bnClose" type="button" value="取 消" class="l-button" /></td>
            		</tr>
            	</table>
            	<div style="margin:0 auto;width:96%;">
					<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:center">
		           		<span id="searching">
		           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;测试中...
		           		</span>
		            </div>
		            <div id="trMsg" class="dN" >
		            	<div id="maingrid"  style="border:1px solid #CCC;"></div> 
		            </div>
				</div>
                </td>
            </tr>
		</table>
    </form>
</body>
</html>

