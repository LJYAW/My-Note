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
                           $("#vendorId").val(vendor.dispName);
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
 	    });
 	    
 
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodIndItem" action="${ctx}/cmdb/snmpOid/save.do" method="post">
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
                <input id = "vendorId" name="vendorId" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
               <td align="right">产品分类 ：</td>
                <td>
                <input value="${prodInd.resClassName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
            </tr>
            
             <tr id="special2">
                <td align="right">产品类型：</td>
                <td>
                <input value="${prodInd.resTypeName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
               <td></td>
               <td align="right">产品型号：</td>
               <td>
               	  <input value="${prodInd.prodModel}" type="text" ltype="text" class=" ip1" readonly/>
               </td>  
               <td></td>
            </tr>

            <tr id="special3">
                <td align="right" nowrap>操作系统类型：</td>
                <td>
                    <input value="${prodInd.osType}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
                <td align="right">软件版本：</td>
                <td>
                    <input value="${prodInd.osVersion}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right" nowrap="nowrap">监测对象分类：</td>
                <td>
                <input value="${prodInd.indClassName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
                
                <td align="right">指标组：</td>
                <td>
                <input value="${prodInd.indGroupName}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">指标项：</td>
                <td>
                <input value="${prodInd.indicatorItem}" type="text" ltype="text" class=" ip1" readonly/>
                </td>
                <td></td>
                <td align="right">指标说明：</td>
                <td><input id="remark" name="remark" value="${prodInd.remark}" type="text" ltype="text" class="ip1"  readonly/></td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right" nowrap="nowrap">Snmp对象名称：</td>
                <td><input id="snmpObjName" name="snmpObjName" value="${prodInd.snmpObjName}" type="text" ltype="text" class=" ip1" readonly/></td>
                <td colspan="4"></td>
            </tr>
            <tr>
                <td align="right">SnmpOID：</td>
                <td colspan="4"><input id="snmpOID" name="snmpOID" value="${prodInd.snmpOID}" type="text" ltype="text" class="ip2"  readonly/></td>
                <td></td>
            </tr>
            <tr>
               <td align="right">获取方式：</td>
			   <td>
			   <input value="${prodInd.getMethod}" type="text" ltype="text" class=" ip1" readonly/>
			   </td>
			  <td></td>
               <td align="right">OID标志：</td>
			   <td>
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
            	<td>&nbsp;&nbsp;</td>
            </tr>
      
		</table>
    </form:form>
</body>
</html>

