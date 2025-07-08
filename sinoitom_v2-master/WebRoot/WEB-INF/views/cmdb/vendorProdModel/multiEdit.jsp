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
		var data = ${saveData};
		var action = '${action}';
		window.parent.saveOK(action,data);
		window.parent.closeDlg();
		</c:if>

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
		
	    refreshClassCode();
	
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
 

  	
  	//产品分类
 	function refreshClassCode() {
  	var vendorId=${vid};
          $.getJSON(
              "${ctx}/cmdb/prodType/getCodeByVendor.do?vendorId="+vendorId,
              function (result) {
                  if (result.pClass != null) {
                      $("#prodClassCode").empty();
                      for (var i = 0; i < result.pClass.length; i++) {
                          var pClass = result.pClass[i];
                          if(pClass.prodClassCode=='${prodModel.prodClassCode}'){
                            $("#prodClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" selected>" + pClass.prodClassName + "</option>");
                          }else{
                            $("#prodClassCode").append("<option  value=\"" + pClass.prodClassCode + "\">" + pClass.prodClassName + "</option>");
                          }
                          
                      }
                      refreshProdType();
                      //刷新产品分类名称
                      var prodClassName=$("#prodClassCode").find("option:selected").text();
  					  $("#prodClassName").val(prodClassName);
                  }
              });
  	}
  	
  	function subClassCode() {
  		var prodClassName=$("#prodClassCode").find("option:selected").text();
  		$("#prodClassName").val(prodClassName);
  	}
  	
  	//产品类型
  	function refreshProdType() {
  	var vendorId=${vid};
  	var prodClassCode=$("#prodClassCode").find("option:selected").val();
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
                     
                     var typeName=$("#prodTypeID").find("option:selected").text();
  					  $("#typeName").val(typeName);
  					  var typeCode=$("#prodTypeID").find("option:selected").attr("typeCode");
  					  $("#typeCode").val(typeCode);
                  }
              });
  	}	
  	
  	function subProdType() {
  		 var typeName=$("#prodTypeID").find("option:selected").text();
		$("#typeName").val(typeName);
		 var typeCode=$("#prodTypeID").find("option:selected").attr("typeCode");
		$("#typeCode").val(typeCode);
  	}
  	
  	//刷新产品系列
  	function refreshProdSeries(){
  	var vendorId=${vid};
  	var prodClassCode=$("#prodClassCode").find("option:selected").val();
  	var prodTypeId=$("#prodTypeID").find("option:selected").val();
  	  $.getJSON(
              "${ctx}/cmdb/vendorProdSeries/getData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId,
              function (result) {
                  if (result.seriesList != null) {
                      $("#prodSeries").empty();
                      for (var i = 0; i < result.seriesList.length; i++) {
                          var series = result.seriesList[i];
                          if(series.prodSeries=='${prodModel.prodSeries}'){
                            $("#prodSeries").append("<option  value=\"" + series.prodSeries + "\" selected>" + series.prodSeries + "</option>");
                          }else{
                            $("#prodSeries").append("<option  value=\"" + series.prodSeries + "\">" + series.prodSeries + "</option>");
                          }
                          
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
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodClass" action="${ctx}/cmdb/vendorProdModel/multiSave.do" method="post">
	<input type="hidden" name="action" value="${action}"/>
	<input type="hidden" name="ids" value="${ids}"/>
	<input type="hidden" name="vid" value="${vid}"/>
		<table align="center" class="tb_edit" style="width:750px;">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr>
              
               <td align="right"><input type="checkbox" name="checkClassCode" id="checkClassCode" value="checkClassCode"/></td>
               <td align="right">产品分类 ：</td>
               <td>
                  <select id="prodClassCode" name="prodClassCode" style="width:202px" onchange="refreshProdType();subClassCode()">
                      <option value="-1">请选择</option>
                  </select>
                  <input type="hidden" name="prodClassName" id="prodClassName" value="${prodModel.prodClassName}"/>
                </td>
                
            </tr>
            
            <tr>
            	<td align="right"><input type="checkbox" name="checkTypeID" id="checkTypeID" value="checkTypeID"/></td>
            	<td align="right">产品类型：</td>
                <td>
                 <select id="prodTypeID" name="prodTypeID" style="width:202px" onchange="refreshProdSeries();subProdType()">
                    <option value="-1">请选择</option>
                 </select>
                 <input type="hidden" name="typeName" id="typeName" value="${prodModel.typeName}"/>
                 <input type="hidden" name="typeCode" id="typeCode" value="${prodModel.typeCode}"/>
                </td>
                
                <td align="right"><input type="checkbox" name="checkSeriesID" id="checkSeriesID" value="checkSeriesID"/></td>
                <td align="right">产品系列：</td>
                <td>
                   <select id="prodSeries" name="prodSeries" style="width:202px">
                       <option value="-1">请选择</option>
                   </select>
                </td>  
            </tr>
            
             <tr>
           		<td align="center" colspan=6 >
           		</td>
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

