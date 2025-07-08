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
<title>机构编辑</title>
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
		window.parent.parent.saveOK(action, data);
		window.parent.parent.closeDlg();
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
	
  	
  	
  	
  	function changeDescp(){
  	  var codeID= $("#prodClassCode").val();
  	  var url = timeURL('${ctx}/cmdb/prodClass/getResItem.do?id=' + codeID+'&resName='+'IT_ResClass');
		$.ajax({
			url : url,
			data : "date=" + new Date(),
			dataType : 'json',
			success : function(data) {
			var obj=data.result;
		    $("#description").val(obj.description);
			}
  	});
  	
 } 	
 
 //刷新厂商
 function refreshVendor() {
          $.getJSON(
              "${ctx}/cmdb/prodType/getVendor.do?date="+new Date(),
              function (result) {
                  if (result.vendors != null) {
                      $("#vendorID").empty();
                      for (var i = 0; i < result.vendors.length; i++) {
                          var vendor = result.vendors[i];
                          if(vendor.vendorID=='${series.vendorID}'){
                           $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                          }else{
                            $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                          }
                          
                      }
                      refreshClassCode();
                  }
              });
  	}
  	
  	//产品分类
 	function refreshClassCode() {
  	var vendorId=$("#vendorID").find("option:selected").val();
          $.getJSON(
              "${ctx}/cmdb/prodType/getCodeByVendor.do?vendorId="+vendorId,
              function (result) {
                  if (result.pClass != null) {
                      $("#prodClassCode").empty();
                      for (var i = 0; i < result.pClass.length; i++) {
                          var pClass = result.pClass[i];
                          if(pClass.prodClassCode=='${series.prodClassCode}'){
                           $("#prodClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" selected>" + pClass.prodClassName + "</option>");
                          }else{
                           $("#prodClassCode").append("<option  value=\"" + pClass.prodClassCode + "\">" + pClass.prodClassName + "</option>");
                          }                      
                      }
                      refreshProdType();
                  }
              });
  	}
  	
  	
  	//产品类型
  	function refreshProdType() {
  	var vendorId=$("#vendorID").find("option:selected").val();
  	var prodClassCode=$("#prodClassCode").find("option:selected").val();
          $.getJSON(
              "${ctx}/cmdb/prodType/getProdTypeByVendorAndClassCode.do?vendorId="+vendorId+"&prodClassCode="+prodClassCode,
              function (result) {
                  if (result.prodType != null) {
                      $("#prodTypeID").empty();
                      for (var i = 0; i < result.prodType.length; i++) {
                          var prodType = result.prodType[i];
                          if(prodType.prodTypeID=='${series.prodTypeID}'||prodType.typeCode=='${series.prodTypeID}'){
                           $("#prodTypeID").append("<option  value=\"" + prodType.prodTypeID + "\" selected>" + prodType.dispName + "</option>");
                          }else{
                           $("#prodTypeID").append("<option  value=\"" + prodType.prodTypeID + "\">" + prodType.dispName + "</option>");
                          }
                          
                      }
                     
                  }
              });
  	}	
  	
  	//验证同一厂商是否有重复的产品系列名称
  	function checkProdSeries(){
  	var vendorId=$("#vendorID").find("option:selected").val();
  	var seriesName=$("#prodSeries").val();
  	
  	 $.getJSON(
              "${ctx}/cmdb/vendorProdSeries/checkProdSeries.do?vendorId="+vendorId+"&seriesName="+seriesName,
              function (result) {
                  if (result.serieslist != null&&result.serieslist !="") {
                       window.parent.$.ligerDialog.error('该产品系系列已存在,请重新填写！'); 
                       $("#prodSeries").val("");
                     }   
              });
  	
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
	<form:form id="formSave" modelAttribute="prodClass" action="${ctx}/cmdb/vendorProdSeries/save.do" method="post">
	<input type="hidden" name="action" value="${action}"/>
	<input type="hidden" name="id" value="${id}"/>
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
                 <select id="vendorID" name="vendorID" style="width:202px" onchange="refreshClassCode()">
                    <option value="-1">请选择</option>
                 </select>
                </td>
                <td></td>
               <td align="right">产品分类 ：</td>
                <td>
                  <select id="prodClassCode" name="prodClassCode" style="width:202px" onchange="refreshProdType()">
                      <option value="-1">请选择</option>
                  </select>
                </td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right">产品类型：</td>
                <td>
                 <select id="prodTypeID" name="prodTypeID" style="width:202px">
                    <option value="-1">请选择</option>
                 </select>
                </td>
               <td></td>
               <td align="right">产品系列：</td>
               <td><input id="prodSeries" name="prodSeries" value="${series.prodSeries}" type="text" ltype="text" class="validate[required[产品系列不能为空]] ip1" onblur="checkProdSeries()"/></td>  
               <td class="required"></td>
            </tr>
            <tr>
                <td align="right">型号描述：</td>
                <td colspan="4"><input id="description" name="description" value="${series.description}" type="text" ltype="text"  class="validate[optional,maxSize[32]] ip2" /></td>
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

