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
<title>资源确认</title>
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

	    $("#formSave input").filter(".ip1").ligerTextBox({width: 200});
 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
 		$("#formSave").validationEngine({promptPosition: "centerRight"});	
 		$("#formSave").validationEngine({promptPosition: "centerRight"});
 		
 		$.getJSON(
              "${ctx}/cmdb/prodType/getVendor.do?date="+new Date(),
              function (result) {
                  if (result.vendors != null) {
                      for (var i = 0; i < result.vendors.length; i++) {
                          var vendor = result.vendors[i];
                          if(vendor.vendorID=='${prodInd.vendorId}'){
                           $("#vendorName").val(vendor.dispName);
                          }
                          
                      }
                  }
              });
              
              
              var vendorId='${prodInd.vendorId}';
              if(vendorId==-1){ //通用指标
                  $("#special1").hide();
     			 $("#special2").hide();
     			 $("#special3").hide();
              }else{   //专用指标
                 $("#special1").show();
    			 $("#special2").show();
    			 $("#special3").show();
              }
        refreshOsType(vendorId);

 	    });

    function refreshOsType(vendorId) {
        // var vendorId = $("#vendorId").find("option:selected").val();
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
                refreshOsVersion(vendorId);
            });
    }

    //根据厂商名称和操作系统类型   查 软件版本
    function refreshOsVersion(vendorId) {
        // var vendorId = $("#vendorId").find("option:selected").val();
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

    //保存
    function add(){

        var vendorId='${prodInd.vendorId}';
        var indcheck;
        if(vendorId=="-1"){
            indcheck = 0;
        }else{
            indcheck = 1;
        }
        $("#indcheck").val(indcheck);
        $("#vendorId").val(vendorId);
        $("#formSave").submit();
    }
 	    
 
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodIndItem" action="${ctx}/cmdb/snmpOid/checkSave.do" method="post">
        <input type="hidden" id="indcheck" name="indcheck" value=""/>
        <input type="hidden" id="vendorId" name="vendorId" value=""/>
        <input type="hidden" id="resClassCode" name="resClassCode" value="${prodInd.resClassCode}"/>
        <input type="hidden" id="resTypeCode" name="resTypeCode" value="${prodInd.resTypeCode}"/>
        <input type="hidden" id="prodModelID" name="prodModelID" value="${prodInd.prodModelID}"/>
        <input type="hidden" id="modelOID" name="modelOID" value="${prodInd.modelOID}"/>
        <input type="hidden" id="indClassCode" name="indClassCode" value="${prodInd.indClassCode}"/>
        <input type="hidden" id="indGroupID" name="indGroupID" value="${prodInd.indGroupID}"/>
        <input type="hidden" id="indItemID" name="indItemID" value="${prodInd.indItemID}"/>
        <input type="hidden" id="indItemName" name="indItemName" value="${prodInd.indItemName}"/>
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr id="special1">
                <td align="right">厂商名称：</td>
                <td>
                <input id = "vendorName" name="vendorName" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
               <td align="right">产品分类 ：</td>
                <td>
                <input id = "resClassName" name="resClassName" value="${prodInd.resClassName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
            </tr>
            
             <tr id="special2">
                <td align="right">产品类型：</td>
                <td>
                <input id = "resTypeName" name="resTypeName" value="${prodInd.resTypeName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
               <td></td>
               <td align="right">产品型号：</td>
               <td>
               	  <input id = "prodModel" name="prodModel" value="${prodInd.prodModel}" type="text" ltype="text" class=" ip1" readonly/>
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
                <input id="indClassName" name="indClassName" value="${prodInd.indClassName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
                
                <td align="right">指标组：</td>
                <td>
                <input id="indGroupName" name="indGroupName" value="${prodInd.indGroupName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">指标项：</td>
                <td>
                <input id="indicatorItem" name="indicatorItem" value="${prodInd.indicatorItem}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
                <td align="right">指标说明：</td>
                <td><input id="remark" name="remark" value="${prodInd.remark}" type="text" ltype="text" class="ip1"  readonly/></td>
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
			   <input id="getMethod" name="getMethod" value="${prodInd.getMethod}" type="text" ltype="text" class=" ip1" readonly/>
			   </td>
			  <td></td>
               <td align="right">OID标志：</td>
			   <td>
                   <input type="hidden" id ="oidFlag" name = "oidFlag" value="${prodInd.oidFlag}"  />
			     <c:choose>
			        <c:when test="${prodInd.oidFlag==2}">
			        	<input value="计算获取" type="text" ltype="text" class=" ip1" readonly/>
			        </c:when>
			     <c:otherwise>
			     		<input value="直接获取" type="text" ltype="text" class=" ip1" readonly/>
			     </c:otherwise>
			     </c:choose>
			  </td>
			  <td></td>
            </tr>
            <tr>
                <td align="right">取值类型：</td>
                <td><input id="valueType" name="valueType" value="${prodInd.valueType}" type="text" ltype="text" class=" ip1" readonly/></td>
                <td ></td>
                
                <td align="right">度量单位：</td>
                <td><input id="measureUnit" name="measureUnit" value="${prodInd.measureUnit}" type="text" ltype="text" class="ip1"  readonly/></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">取值长度：</td>
                <td><input id="length" name="length" value="${prodInd.length}" type="text" ltype="text" class="ip1" readonly/></td>
                <td ></td>
                
                <td align="right">小数位数：</td>
                <td><input id="decimals" name="decimals" value="${prodInd.decimals}" type="text" ltype="text" class="ip1"  readonly/></td>
                <td></td>
            </tr>
            

            <tr>
                <td align="center" colspan=6 >
                    <input id="bnSave" type="button" value="扩展保存" class="l-button mg6" onclick="add()"/>
                </td>
            </tr>
		</table>
    </form:form>
</body>
</html>

