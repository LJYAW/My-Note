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
<title>原操作系统----改操作系统类型</title>
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
                          
                          if(vendor.vendorID=='${os.vendorID}'){
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
                          if(osType.osClassCode=='${os.osClassCode}'){
                            $("#osClassCode").append("<option  value=\"" + osType.osClassCode + "\"selected >" + osType.osClassName + "</option>");
                          }else{
                            $("#osClassCode").append("<option  value=\"" + osType.osClassCode + "\" >" + osType.osClassName + "</option>");
                          }
                          
                      }
                      //refreshClassCode();
                  }
                  var osClassName=$("#osClassCode").find("option:selected").text();
                  $("#osClassName").val(osClassName);
              });
}  	

$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });
  
  function changeVendorName(){
    var vendorName=$("#vendorID").find("option:selected").text();
                  $("#vendorName").val(vendorName);
  }
  
  function checkOSName(){
     var vendorID=$("#vendorID").find("option:selected").val();
     var osName=$("#osType").val();
     
      $.getJSON(
              "${ctx}/cmdb/os/checkOsName.do?vendorId="+vendorID+"&osName="+osName,
              function (result) {
                  if (result.osNames != null&&result.osNames !="") {
                       window.parent.$.ligerDialog.error('该操作系统已存在,请重新填写！'); 
                       $("#osType").val("");
                     }   
              });
  }

</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="os" action="${ctx}/cmdb/os/save.do" method="post">
	<input type="hidden" name="action" value="${action}"/>
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" name="osClassName" id="osClassName" value=""/>
	<input type="hidden" name="vendorName" id="vendorName" value=""/>
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
                <td> <select id="vendorID" name="vendorID" style="width:202px" onchange="refreshOsType();changeVendorName()">
                    <option value="-1">请选择</option>
                </td>
                <td></td>
                <td align="right">操作系统分类：</td>
                <td>
                 <select id="osClassCode" name="osClassCode" style="width:202px">
                    <option value="-1">请选择</option>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">操作系统类型：</td>
                <td><input id="osType" name="osType" value="${os.osType}" type="text" ltype="text" class="validate[required[操作系统不能为空]] ip1" onblur="checkOSName()"/></td>
                <td class="required"></td>
                <td align="right">操作系统描述：</td>
                <td><input id="description" name="description" value="${os.description}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
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

