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
<title>关联产品厂商</title>
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
		
		window.parent.$.ligerDialog.success("设备关联成功!");
		 window.close();
		//window.parent.parent.saveOK(action, data);
		 
		</c:if>

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
		
	    refreshVendor();
	
	    $("#formSave input").filter(".ip1").ligerTextBox({width: 200});
 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
 		$("#formSave").validationEngine({promptPosition: "centerRight"});	
 		$("#formSave").validationEngine({promptPosition: "centerRight"});
 		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
		
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
                          if(vendor.vendorID=='${prodInd.vendorId}'){
                           $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                          }else{
                           $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                          }
                          
                      }
                      refreshProdModel();
                  }
              });
  	}
  	
  	
  	
  	
  	
  	//刷新产品型号
  	function refreshProdModel(){
  	var vendorId=$("#vendorId").find("option:selected").val();
  	  $.getJSON(
              "${ctx}/cmdb/vendorProdModel/getDataByVendorID.do?vendorId="+vendorId,
              function (result) {
                  if (result.modelOIDlist != null) {
                      $("#prodModelID").empty();
                      $("#prodModelID").append("<option value=\"-1\" modelOID=\"X\" selected>所有型号</option>");
                      for (var i = 0; i < result.modelOIDlist.length; i++) {
                          var model = result.modelOIDlist[i];
                          if(model.prodModelID=='${prodInd.prodModelID}'){
                            $("#prodModelID").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.modelOID + "\" selected>" + model.prodModel + "</option>");
                          }else{
                            $("#prodModelID").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.modelOID + "\">" + model.prodModel + "</option>");
                          }
                          
                      }
                     refreshModelName();
                  }
              });
  	}
  	
  	//刷新型号名称
  	function refreshModelName(){
  	 var prodModel=$("#prodModelID").find("option:selected").text();
  	 $("#prodModel").val(prodModel);
  	 var modelOID=$("#prodModelID").find("option:selected").attr("modelOID");
  	 $("#modelOID").val(modelOID);
  	}
  	
  	
  	 	
 $(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodIndItem" action="${ctx}/cmdb/snmpOid/saveRelaProdIndItems.do" method="post">
	<input type="hidden" name="devIndItemsIds" value="${devIndItemsIds}"/>
	
	<input type="hidden" id="prodModel" name="prodModel" value=""/>
	<input type="hidden" id="modelOID" name="modelOID" value=""/>
	
	
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
                <td>
                 <select id="vendorId" name="vendorId" style="width:202px" onchange="refreshProdModel();">
                    <option value="-1">请选择</option>
                 </select>
                </td>
                <td></td>
              <td align="right">产品型号：</td>
               <td>
                  <select id="prodModelID" name="prodModelID" style="width:202px" onchange="refreshModelName();">
                  </select>
               </td>  
               <td></td>
            </tr>
            
            
            <tr>
            	<td>&nbsp;&nbsp;</td>
            </tr>
      
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="确认关联" class="button mg6" />
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

