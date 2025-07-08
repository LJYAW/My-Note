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
<title>产品指标项</title>
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
	     var flag;
	     var vendorId='${prodInd.vendorId}';
  		 var indcheck='${indcheck}';
  		 var action='${action}';
	     if(vendorId!=null&&vendorId!=-1){
	       flag=1;
	     }else{
	       flag=0;
	     }
	     if(action=="add"){
   		 flag=0;
  		 }
  		 
  		 if(action=="saveAndContinue"){
   		 if(indcheck==1){
     	  flag=1;
    		}else{
    	  flag=0;
    		}
  		 }
	     getIndType(flag);
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		if(action != "saveAndContinue"){
			window.parent.parent.saveOK(action, data);
			window.parent.parent.closeDlg();
			
		}
		window.parent.parent.saveOK(action, data);
		$.ligerDialog.success("保存成功");
		</c:if>

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
		
	    if('${action}' == "edit"){
	    	document.getElementById('svc').style.display='none';
	    }
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
		
		refreshIndGroup();
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
                      refreshClassCode();
                      refreshOsType();
                  }
              });
  	}
  	
  	//产品分类
 	function refreshClassCode() {
  	var vendorId=$("#vendorId").find("option:selected").val();
          $.getJSON(
              "${ctx}/cmdb/prodType/getCodeByVendor.do?vendorId="+vendorId,
              function (result) {
                  if (result.pClass != null) {
                      $("#resClassCode").empty();
                      for (var i = 0; i < result.pClass.length; i++) {
                          var pClass = result.pClass[i];
                          if(pClass.prodClassCode=='${prodInd.resClassCode}'){
                            $("#resClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" prodClassID=\"" + pClass.prodClassID + "\" selected>" + pClass.prodClassName + "</option>");
                          }else{
                            $("#resClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" prodClassID=\"" + pClass.prodClassID + "\">" + pClass.prodClassName + "</option>");
                          }
                          
                      }
                      refreshProdType();
                      refreshClassName();
                  }
              });
  	}
  	
  	//刷新分类名称
  	function refreshClassName(){
  	  var resClassName=$("#resClassCode").find("option:selected").text();
  	  $("#resClassName").val(resClassName);
  	}
  	
  	//产品类型
  	function refreshProdType() {
  	var vendorId=$("#vendorId").find("option:selected").val();
  	var prodClassCode=$("#resClassCode").find("option:selected").val();
          $.getJSON(
              "${ctx}/cmdb/prodType/getProdTypeByVendorAndClassCode.do?vendorId="+vendorId+"&prodClassCode="+prodClassCode,
              function (result) {
                  if (result.prodType != null) {
                      $("#resTypeCode").empty();
                      for (var i = 0; i < result.prodType.length; i++) {
                          var prodType = result.prodType[i];
                          if(prodType.typeCode=='${prodInd.resTypeCode}'){
                            $("#resTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\" selected>" + prodType.dispName + "</option>");
                          }else{
                            $("#resTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\">" + prodType.dispName + "</option>");
                          }
                          
                      }
                     //refreshProdSeries();
                     refreshProdModel();
                     refreshTypeName();
                  }
              });
  	}	
  	
  	//刷新类型名称
  	function refreshTypeName(){
  	 var typeName=$("#resTypeCode").find("option:selected").text();
  	 $("#resTypeName").val(typeName);
  	}
  	//刷新产品系列
//   	function refreshProdSeries(){
//   	var vendorId=$("#vendorID").find("option:selected").val();
//   	var prodClassCode=$("#prodClassCode").find("option:selected").val();
//   	var prodTypeId=$("#prodTypeID").find("option:selected").val();
//   	  $.getJSON(
//               "${ctx}/cmdb/vendorProdSeries/getData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId,
//               function (result) {
//                   if (result.seriesList != null) {
//                       $("#prodSeriesID").empty();
//                       for (var i = 0; i < result.seriesList.length; i++) {
//                           var series = result.seriesList[i];
//                           if(series.prodSeriesID=='${prodModel.prodSeriesID}'){
//                             $("#prodSeriesID").append("<option  value=\"" + series.prodSeriesID + "\" selected>" + series.prodSeries + "</option>");
//                           }else{
//                             $("#prodSeriesID").append("<option  value=\"" + series.prodSeriesID + "\">" + series.prodSeries + "</option>");
//                           }
                          
//                       }
                     
//                   }
//               });
  	
//   	}
  	
  	//刷新产品型号
  	function refreshProdModel(){
  	var vendorId=$("#vendorId").find("option:selected").val();
  	var prodClassCode=$("#resClassCode").find("option:selected").val();
  	var prodTypeId=$("#resTypeCode").find("option:selected").attr("typeId");
  	  $.getJSON(
              "${ctx}/cmdb/vendorProdModel/getData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId,
              function (result) {
                  if (result.modelOIDlist != null) {
                      $("#prodModelID").empty();
                      $("#prodModelID").append("<option value=\"-1\" modelOID=\"X\" selected>所有型号</option>");
                      for (var i = 0; i < result.modelOIDlist.length; i++) {
                          var model = result.modelOIDlist[i];
                          if(model.prodModelID=='${prodInd.prodModelID}'){
                            $("#prodModelID").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\" selected>" + model.prodModelName + "</option>");
                          }else{
                            $("#prodModelID").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\">" + model.prodModelName + "</option>");
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

    //根据厂商名称  查   操作系统类型
    function refreshOsType() {
        var vendorId = $("#vendorId").find("option:selected").val();
        $.getJSON(
            "${ctx}/cmdb/vendorOSVersion/getOSNameById.do?vendorID=" + vendorId,
            function (result) {
                if (result.vendorOSVersionlist != null) {

                    $("#osType").empty();
                    for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
                        var osType = result.vendorOSVersionlist[i];
                        if (osType.osType == '${prodInd.osType}') {
                            $("#osType").append("<option  value=\"" + osType.osType + "\" selected>" + osType.osType + "</option>");
                        } else {
                            $("#osType").append("<option  value=\"" + osType.osType + "\" >" + osType.osType + "</option>");
                        }

                    }
                }
                refreshOsVersion();
            });
    }

    //根据厂商名称和操作系统类型   查 软件版本
    function refreshOsVersion() {
        var vendorId = $("#vendorId").find("option:selected").val();
        var osType = $("#osType").find("option:selected").val();
        $.getJSON(
            "${ctx}/cmdb/vendorOSVersion/getOsVersionByVendorIdAndOsType.do?vendorID=" + vendorId + "&osName=" + osType,
            function (result) {
                if (result.list != null) {
                    $("#osVersion").empty();
                    $("#osVersion").append("<option value=\"V.R\" selected>V.R(所有版本)</option>");
                    for (var i = 0; i < result.list.length; i++) {
                        var prodType = result.list[i];
                        if (prodType.osVersion == '${prodInd.osVersion}') {
                            $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\" selected>" + prodType.osVersion + "</option>");
                        } else {
                            $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\">" + prodType.osVersion + "</option>");
                        }

                    }
                }
            });

    }

  	
    //刷新指标组
    function refreshIndGroup(){
     var indClassCode=$("#indClassCode").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/snmpOid/getIndGroup.do?indClassCode="+indClassCode,
              function (result) {
                  if (result.indGroups != null) {
                      $("#indGroupID").empty();
                      for (var i = 0; i < result.indGroups.length; i++) {
                          var group = result.indGroups[i];
                          if(group.id=='${prodInd.indGroupID}'){
                            $("#indGroupID").append("<option  value=\"" + group.id + "\" selected>" + group.groupName + "</option>");
                          }else{
                            $("#indGroupID").append("<option  value=\"" + group.id + "\">" + group.groupName + "</option>");
                          }
                          
                      }
                     refreshIndItems();
                     refreshIndGroupName();
                  }
              });
    }
  	
  	//刷新指标组名称
  	function refreshIndGroupName(){
  	  var groupName=$("#indGroupID").find("option:selected").text();
  	 $("#indGroupName").val(groupName);
  	}
  	//刷新指标项
  	
  	function refreshIndItems(){
     var indClassCode=$("#indClassCode").find("option:selected").val();
     var indGroupID=$("#indGroupID").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/snmpOid/getIndItems.do?indClassCode="+indClassCode+"&indGroupID="+indGroupID,
              function (result) {
                  if (result.indItems != null) {
                      $("#indItemID").empty();
                      for (var i = 0; i < result.indItems.length; i++) {
                          var item = result.indItems[i];
                          if(item.id=='${prodInd.indItemID}'){
                            $("#indItemID").append("<option  value=\"" + item.id + "\"  indItemName=\"" + item.indItemName + "\" remark=\"" + item.remark + "\" valueType=\"" + item.valueType + "\" measureUnit=\"" + item.measureUnit + "\" length=\"" + item.length + "\" decimals=\"" + item.decimals + "\"  selected>" + item.indicatorItem + "</option>");
                          }else{
                            $("#indItemID").append("<option  value=\"" + item.id + "\"  indItemName=\"" + item.indItemName + "\" remark=\"" + item.remark + "\" valueType=\"" + item.valueType + "\" measureUnit=\"" + item.measureUnit + "\" length=\"" + item.length + "\" decimals=\"" + item.decimals + "\" >" + item.indicatorItem + "</option>");
                          }
                          
                      }
                       
                      
                       refreshIndItem();
                      if('${action}'=="add"||'${action}'=="saveAndContinue"){
                       refreshIndItemProt();
                      }
                     
                  }
              });
    }
    
     function refreshIndItem(){
      $("#indItemName").val($("#indItemID").find("option:selected").attr("indItemName"));
      $("#indicatorItem").val($("#indItemID").find("option:selected").text());
    }
    
    //刷新指标项属性值
    function refreshIndItemProt(){
       
				    $("#valueType").val($("#indItemID").find("option:selected").attr("valueType"));
				    $("#measureUnit").val($("#indItemID").find("option:selected").attr("measureUnit"));
				    $("#length").val($("#indItemID").find("option:selected").attr("length"));
				    $("#decimals").val($("#indItemID").find("option:selected").attr("decimals"));
				     $("#remark").val($("#indItemID").find("option:selected").attr("remark"));
    }
    
    //保存并继续
    function saveAndContinue(){
    	$("#action").val("saveAndContinue");
    	$("#formSave").submit();
    }
    
    //保存
    function add(){
    	if("edit" != '${action}'){
	    	$("#action").val("add");
    	}
    	$("#formSave").submit();
    }
  	
  	 	
 $(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });
  
  function getIndType(flag){  //0
   if(flag==0){
      $("input[name='indcheck']:eq(0)").attr("checked",0); 
      $("input[name='indcheck']:eq(1)").removeAttr("checked");
   }else{
      $("input[name='indcheck']:eq(1)").attr("checked",1); 
      $("input[name='indcheck']:eq(0)").removeAttr("checked");
   }
  
    var indcheck=$('input[name="indcheck"]:checked ').val(); 
   if(indcheck==0){  //通用
     $("#special1").hide();
     $("#special3").hide();
     $("#special2").hide();
   }else{    //专用指标
     $("#special1").show();
     $("#special3").show();
     $("#special2").show();
   }
  }
  
  
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodIndItem" action="${ctx}/cmdb/snmpOid/save.do" method="post">
	<input type="hidden" id="action" name="action" value="${action}"/>
	<input type="hidden" name="id" value="${id}"/>
	
	<input type="hidden" id="resClassName" name="resClassName" value=""/>
	<input type="hidden" id="resTypeName" name="resTypeName" value=""/>
	<input type="hidden" id="prodModel" name="prodModel" value=""/>
	<input type="hidden" id="modelOID" name="modelOID" value=""/>
	<input type="hidden" id="indGroupName" name="indGroupName" value=""/>
	<input type="hidden" id="indItemName" name="indItemName" value=""/>
	<input type="hidden" id="indicatorItem" name="indicatorItem" value=""/>
	<input type="hidden" id="status" name="status" value="0"/>
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
				<td class="td_w1" nowrap="nowrap">监测对象分类：</td>
				<td class="td_w2" colspan="3">
					<input type="radio" checked="checked" name="indcheck" value="0" onclick="getIndType(0)"/>通用指标
					&nbsp; &nbsp; 
					<input type="radio"  name="indcheck" value="1" onclick="getIndType(1)"/>专用指标
				</td>
				<td class="td_w3" colspan="2"></td>
			</tr>
			<tr>
            	<td>&nbsp;&nbsp;</td>
			</tr>
			
			  <tr id="special1">
                <td align="right">厂商名称：</td>
                <td>
                 <select id="vendorId" name="vendorId" style="width:202px" onchange="refreshClassCode();refreshOsType();">
                    <option value="-1">请选择</option>
                 </select>
                </td>
                <td></td>
               <td align="right">产品分类 ：</td>
                <td>
                  <select id="resClassCode" name="resClassCode" style="width:202px" onchange="refreshProdType();refreshClassName()">
                      <option value="-1">请选择</option>
                  </select>
                </td>
                <td></td>
            </tr>
             <tr  id="special2">
                <td align="right">产品类型：</td>
                <td>
                 <select id="resTypeCode" name="resTypeCode" style="width:202px" onchange=" refreshProdModel();refreshTypeName();">
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


            <tr id="special3">
                <td align="right" nowrap>操作系统类型：</td>
                <td>
                    <select id="osType" name="osType" style="width:202px" onchange="refreshOsVersion()">

                    </select>
                </td>
                <td></td>
                <td align="right">软件版本：</td>
                <td>
                    <select id="osVersion" name="osVersion" style="width:202px" onchange="refreshOsFeature()">
                    </select>
                </td>
                <td></td>
            </tr>
            
            
            <tr>
                <td align="right" nowrap="nowrap">监测对象分类：</td>
                <td>
                  <s:resSelect id="indClassCode" name="indClassCode" value="${prodInd.indClassCode}" code="Cmdb_IndicatorClass" ltype="select" style="width:200px;" onchange="refreshIndGroup();"/>
                </td>
                <td></td>
                
                <td align="right">指标组：</td>
                <td>
                <select id="indGroupID" name="indGroupID" style="width:200px;" onchange="refreshIndItems();refreshIndGroupName();">
                </select>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">指标项：</td>
                <td>
                <select id="indItemID" name="indItemID" style="width:200px;" onchange="refreshIndItemProt();refreshIndItem();">
                </select>
                </td>
                <td></td>
                <td align="right">指标说明：</td>
                <td><input id="remark" name="remark" value="${prodInd.remark}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right" nowrap="nowrap">Snmp对象名称：</td>
                <td><input id="snmpObjName" name="snmpObjName" value="${prodInd.snmpObjName}" type="text" ltype="text" class=" ip1"/></td>
                <td colspan="4"></td>
            </tr>
            <tr>
                <td align="right">SnmpOID：</td>
                <td colspan="4"><input id="snmpOID" name="snmpOID" value="${prodInd.snmpOID}" type="text" ltype="text" class="ip2" /></td>
                <td></td>
            </tr>
            <tr>
               <td align="right">获取方式：</td>
			   <td>
			     <select style="width:202px" id="getMethod" name="getMethod">
			     <c:choose>
			        <c:when test="${prodInd.getMethod=='SnmpWalk'}">
				          <option value="SnmpGet" >SnmpGet</option>
				          <option value="SnmpWalk" selected="selected">SnmpWalk</option>
			        </c:when>
			     <c:otherwise>
			            <option value="SnmpGet" selected="selected">SnmpGet</option>
				        <option value="SnmpWalk">SnmpWalk</option>
			     </c:otherwise>
			     </c:choose>
			    
			       
			     </select>
			  </td>
			  <td></td>
               <td align="right">OID标志：</td>
			   <td>
			     <select style="width:202px" id="oidFlag" name="oidFlag">
			     <c:choose>
			        <c:when test="${prodInd.oidFlag==2}">
				        <option value="1" >直接获取</option>
				        <option value="2" selected="selected">计算获取</option>
			        </c:when>
			     <c:otherwise>
			        <option value="1" selected="selected">直接获取</option>
				    <option value="2">计算获取</option>
			     </c:otherwise>
			     </c:choose>
			     </select>
			  </td>
			  <td></td>
            </tr>
            <tr>
                <td align="right">取值类型：</td>
                <td><input id="valueType" name="valueType" value="${prodInd.valueType}" type="text" ltype="text" class=" ip1"/></td>
                <td ></td>
                
                <td align="right">度量单位：</td>
                <td><input id="measureUnit" name="measureUnit" value="${prodInd.measureUnit}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">取值长度：</td>
                <td><input id="length" name="length" value="${prodInd.length}" type="text" ltype="text" class="ip1"/></td>
                <td ></td>
                
                <td align="right">小数位数：</td>
                <td><input id="decimals" name="decimals" value="${prodInd.decimals}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            
            <tr>
            	<td>&nbsp;&nbsp;</td>
            </tr>
      
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSave" type="button" value="保 存" class="l-button mg6" onclick="add()"/>
           		</td>
           		<td align="left" colspan=3 >
           		<input id="svc" type="button" value="保存并继续" class="l-button mg6" style="width:70px" onclick="saveAndContinue()"/>
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

