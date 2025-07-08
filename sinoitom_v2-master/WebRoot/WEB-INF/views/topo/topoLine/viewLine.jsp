<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>拓扑线编辑</title>
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
    
    var nodeAid='${nodeA.devID}';
    var nodeADevType='${nodeA.devTypeCode}';
    var nodeBid='${nodeB.devID}';
    var nodeBDevType='${nodeB.devTypeCode}';
    
	$(function ()
	{				
		$("input").filter(".ip1").ligerTextBox({ width: 196 });
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		
		
		 var ifBandwidthA="${line.ifBandwidthA}";
		 var ifBandwidthB= "${line.ifBandwidthB}"
		 
		  $("#ifBandwidthA").val(ifBandwidthA/1000000+" Mbps");
		   $("#ifBandwidthB").val(ifBandwidthB/1000000+" Mbps");
		 
		 if(ifBandwidthA>ifBandwidthB){
          $("#numVal").val(ifBandwidthB/1000000+" Mbps");
     	 }else{
          	$("#numVal").val(ifBandwidthA/1000000+" Mbps");
      	 }
	});
	

</script>

</head>
<body> 
	<form id="formSave" modelAttribute="topoLine" action="" method="post">
	<input type="hidden" id="ifIndexA" name="ifIndexA"  value=""/>
	<input type="hidden" id="ifIndexB" name="ifIndexB"  value=""/>
	<input type="hidden" id="lineId" name="lineId"  value="${line.lineId }"/>
	<input type="hidden" id="graphId" name="graphId"  value="${graphId }"/>
	<input type="hidden" id="nodeAId" name="nodeAId"  value="${nodeA.nodeId }"/>
	<input type="hidden" id="nodeBId" name="nodeBId"  value="${nodeB.nodeId }"/>
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
			
<!-- 			<tr style="line-height: 30px;"> -->
<!--                 <td colspan=5 style="text-align: center"> -->
<!--                 <span><h3>节点接口信息</h3></span> -->
<!--                 </td> -->
<!--                 <td></td> -->
<!-- 			</tr> -->
			
			 <tr>
                <td align="right" nowrap>A端节点类型：</td>
                <td><input id="nodeAType" name="nodeAType"  value="${nodeA.devTypeName}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端节点类型：</td>
                <td><input id="nodeBType" name="nodeBType" value="${nodeB.devTypeName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
            
             <tr>
                <td align="right">A端节点名字：</td>
                <td><input id="nodeAName" name="nodeAName" value="${line.nodeAName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端节点名字：</td>
                <td><input id="nodeBName" name="nodeBName" value="${line.nodeBName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
            
             <tr>
                <td align="right">A端接口名称：</td>
                <td>
				 <input id="ifNameA" name="ifNameA" value="${line.ifNameA}" type="text" ltype="text" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right">B端接口名称：</td>
                <td>
				 <input id="ifNameB" name="ifNameB" value="${line.ifNameB}" type="text" ltype="text" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr> 
            
            </tr> 
            
              <tr>
                <td align="right">A端接口IP：</td>
                <td><input id="ifIpA" name="ifIpA" value="${line.ifIpA}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端接口IP：</td>
                <td><input id="ifIpB" name="ifIpB" value="${line.ifIpB}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
            
              <tr>
                <td align="right">A端接口带宽：</td>
                <td><input id="ifBandwidthA" name="ifBandwidthA" value="" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端接口带宽：</td>
                <td><input id="ifBandwidthB" name="ifBandwidthB" value="" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
			
<!-- 			<tr style="line-height: 30px;"> -->
<!--                 <td colspan=5 style="text-align: center"> -->
<!--                 <span><h3>线路信息</h3></span> -->
<!--                 </td> -->
<!--                 <td></td> -->
<!-- 			</tr> -->
			
            <tr>
                <td align="right">线路带宽：</td>
                <td>
                <input id="numVal" name="numVal" value="" style="width:130px;" type="text" ltype="text" class=" ip1" readonly="readonly" />
<!-- 					<select id="unitVal" name="unitVal" style="width:60px"> -->
<!-- 						<option value="Bps">Bps</option> -->
<!-- 						<option value="Kbps">Kbps</option> -->
<!-- 						<option value="Mbps" selected>Mbps</option> -->
<!-- 						<option value="Gbps">Gbps</option> -->
<!-- 						<option value="Tbps">Tbps</option> -->
<!-- 					</select> -->
				</td>
                <td></td>
            </tr>   
            
            
            
            <tr>
                <td align="right">线路名称：</td>
                <td colspan=4><input id="lineName" name="lineName" value="${line.lineName}" type="text" ltype="text" class="ip2"  readonly="readonly"/></td>
                <td></td>
            </tr>
            
		</table>
    </from>
</body>
</html>

