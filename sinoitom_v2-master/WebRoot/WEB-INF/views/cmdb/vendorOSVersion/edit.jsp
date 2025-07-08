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
<title>操作系统版本</title>
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
		window.parent.parent.closeDlg();
		</c:if>

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
		

       refreshVendor();
	
	$("#formSave input").filter(".ip1").ligerTextBox({width: 200});
 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
 		$("#formSave").validationEngine({promptPosition: "centerRight"});
 		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
 	});
	
  	
  	
  	
//   	function changeDescp(){
//   	  var codeID= $("#prodClassCode").val();
//   	  var url = timeURL('${ctx}/cmdb/prodClass/getResItem.do?id=' + codeID+'&resName='+'IT_OSType');
// 		$.ajax({
// 			url : url,
// 			data : "date=" + new Date(),
// 			dataType : 'json',
// 			success : function(data) {
// 			var obj=data.result;
// 		    $("#description").val(obj.description);
// 			}
//   	});
  	
//  } 	

function refreshVendor() {
          $.getJSON(
              "${ctx}/cmdb/vendor/getVendorByFlag.do",
              function (result) {
                  if (result.vendors != null) {
                      $("#vendorID").empty();
                      for (var i = 0; i < result.vendors.length; i++) {
                          var vendor = result.vendors[i];
                          if(vendor.vendorID=='${version.vendorID}'){
                           $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\"  vendorName=\"" + vendor.vendorName + "\" selected>" + vendor.dispName + "</option>");
                          }else{
                           $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\"  vendorName=\"" + vendor.vendorName + "\">" + vendor.dispName + "</option>");
                          }
                          
                      }
                      //refreshClassCode();
                      refreshOsType();
                  }
                  var vendorName=$("#vendorID").find("option:selected").text();
                  $("#vendorName").val(vendorName);
              });
  	}
  	
function refreshOsType(){
     var vendorID=$("#vendorID").find("option:selected").val();
    $.getJSON(
              "${ctx}/cmdb/ostype/getOsTypeByVendorID.do?vendorID="+vendorID,
              function (result) {
                  if (result.osTypes != null) {
                      $("#osClassCode").empty();
                      for (var i = 0; i < result.osTypes.length; i++) {
                          var osType = result.osTypes[i];
                          
                          if(osType.osClassCode=='${version.osClassCode}'){
                           $("#osClassCode").append("<option  value=\"" + osType.osClassCode + "\" selected>" + osType.osClassName + "</option>");
                          }else{
                           $("#osClassCode").append("<option  value=\"" + osType.osClassCode + "\" >" + osType.osClassName + "</option>");
                          }
                          
                      }
                      //refreshClassCode();
                  }
                  refreshOsName();
                  var osClassName=$("#osClassCode").find("option:selected").text();
                  $("#osClassName").val(osClassName);
              });
} 


function refreshOsName(){
var osClassCode;
 var vendorID=$("#vendorID").find("option:selected").val();
  var typeName=$("#osClassCode").find("option:selected").text();
  if(typeName==""){
   osClassCode="-1";
  }else{
    osClassCode=$("#osClassCode").find("option:selected").val();
  }
 
   $.getJSON(
              "${ctx}/cmdb/os/getByVendorAndCode.do?vendorId="+vendorID+"&typeCode="+osClassCode,
              function (result) {
                  if (result.osNames != null) {
                      $("#osTypeID").empty();
                      for (var i = 0; i < result.osNames.length; i++) {
                          var osName = result.osNames[i];
                          if(osName.osType=='${version.osType}'){
                           $("#osTypeID").append("<option  value=\"" + osName.osTypeID + "\" selected>" + osName.osType + "</option>");
                          }else{
                           $("#osTypeID").append("<option  value=\"" + osName.osTypeID + "\" >" + osName.osType + "</option>");
                          }
                           
                          
                      }
                      //refreshClassCode();
                  }
                  var osName=$("#osTypeID").find("option:selected").text();
                  $("#osType").val(osName);
              });
} 	

$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });
  
  function changeOSName(){
//     var vendorName=$("#vendorID").find("option:selected").text();
//                   $("#vendorName").val(vendorName);
  var osName=$("#osTypeID").find("option:selected").text();
                  $("#osType").val(osName);
  }
  
  
   	//验证同一厂商同一操作系统是否有重复的版本名称
  	function checkVersion(){
  	var typeCode;
  	var vendorId=$("#vendorID").find("option:selected").val();
  	
  	 var typeName=$("#osClassCode").find("option:selected").text();
	  if(typeName==""){
	   typeCode="-1";
	  }else{
	    typeCode=$("#osClassCode").find("option:selected").val();
	  }
  	
  	var osName=$("#osTypeID").find("option:selected").text();
  	var osVersion=$("#osVersion").val();
  	
  	
  	 $.getJSON(
              "${ctx}/cmdb/vendorOSVersion/checkVersion.do?vendorId="+vendorId+"&typeCode="+typeCode+"&osName="+osName+"&osVersion="+osVersion,
              function (result) {
                  if (result.versions != null&&result.versions !="") {
                       window.parent.$.ligerDialog.error('该操作系统版本已存在,请重新填写！'); 
                       $("#osVersion").val("");
                     }   
              });
  	
  	}
  


</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="os" action="${ctx}/cmdb/vendorOSVersion/save.do" method="post">
	<input type="hidden" name="action" value="${action}"/>
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" name="osClassName" id="osClassName" value=""/>
	<input type="hidden" name="osType" id="osType" value=""/>
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
                <td align="right">厂商名称：</td>
                <td> <select id="vendorID" name="vendorID" style="width:202px" onchange="refreshOsType()">
                    <option value="-1">请选择</option>
                </td>
                <td></td>
                <td align="right">操作系统分类：</td>
                <td>
                 <select id="osClassCode" name="osClassCode" style="width:202px" onchange="refreshOsName()">
                    <option value="-1">请选择</option>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">操作系统类型：</td>
                <td>
                <select id="osTypeID" name="osTypeID" style="width:202px" onchange="changeOSName()">
                    <option value="-1">请选择</option>
                </td>
                </td>
                <td></td>
                <td align="right" nowrap="nowrap">操作系统版本：</td>
                <td><input id="osVersion" name="osVersion" value="${version.osVersion}" type="text" ltype="text" class="validate[required[操作系统版本不能为空]] ip1" onblur="checkVersion()"/></td>
                <td  class="required"></td>
            </tr>
             <tr>
                <td align="right" nowrap="nowrap">操作系统特征：</td>
                <td><input id="osFeature" name="osFeature" value="${version.osFeature}" type="text" ltype="text" class="validate[required[操作系统版本不能为空]] ip1" onblur="checkVersion()"/></td>
                <td ></td>
            </tr>
            <tr>
              <td align="right">版本描述：</td>
              <td colspan="4"><input id="description" name="description" value="${version.description}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip2" /></td>
              <td></td>
            </tr>
            <tr>
            	<td>&nbsp;&nbsp;</td>
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

