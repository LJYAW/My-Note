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

   <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
    <script src="${ctx }/static/assets/js/layer.js"></script>
    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script>
<script type="text/javascript">
    
    var devAId='${nodeA.devID}';
    var nodeADevType='${nodeA.devTypeCode}';
    var devBId='${nodeB.devID}';
    var nodeBDevType='${nodeB.devTypeCode}';
    
	$(function ()
	{				
		<c:if test="${result=='success'}">
		window.parent.closeWin();  
		</c:if>
        
		$("input").filter(".ip1").ligerTextBox({ width: 196 });
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		
		<c:if test="${action=='edit'}">
		 loadNodeAIf(devAId);
		 loadNodeBIf(devBId);
		</c:if>
		
	});
	
  
  function loadNodeAIf(devId){
      $.getJSON(
               "${ctx}/topo/topoLine/getNodeIf.do?devId="+devId,
               function (result) {
                   if (result.ifStr != null) {
                       $("#ifNameA").empty();
                       for (var i = 0; i < result.ifStr.length; i++) {
                       		var nodeAif=result.ifStr[i];
                       		var ifName;
                       		var ifIpA;
                       		var text;
                       			ifName=nodeAif.ifName;
                       		    text=ifName;
                       		    ifIpA=nodeAif.ipAddr;
                       		    if(ifIpA!=""&&ifIpA!=null){
                       		     ifName=ifName+"("+ifIpA+")";
                       		   }
                       		if(nodeAif.ifIndex=='${line.ifIndexA}'){
                       			$("#ifNameA").append("<option  value=\"" + text+"\" ifIndex=\"" + nodeAif.ifIndex+"\" ifSpeed=\"" + nodeAif.ifSpeed+"\" baudRate=\"" + nodeAif.baudRate+"\" ifIpA=\"" + ifIpA+"\"  selected>" +ifName + "</option>");
                       		}else{
                       			$("#ifNameA").append("<option  value=\"" + text+"\" ifIndex=\"" + nodeAif.ifIndex+"\" ifSpeed=\"" + nodeAif.ifSpeed+"\" baudRate=\"" + nodeAif.baudRate+"\" ifIpA=\"" + ifIpA+"\" >" + ifName+ "</option>");
                       		}
							
                       		
                       }
                       changeifAspeed();
                       changeLineName();
                   }
               });
  }
  
  
   function loadNodeBIf(devBId){
      $.getJSON(
               "${ctx}/topo/topoLine/getNodeIf.do?devId="+devBId,
               function (result) {
                   if (result.ifStr != null) {
                       $("#ifNameB").empty();
                       for (var i = 0; i < result.ifStr.length; i++) {
                       		var nodeBif=result.ifStr[i];
                       		var ifName;
                       		var ifIpB;
                       		var text;
                       		ifName=nodeBif.ifName;
                       		    text=ifName;
                       		    ifIpB=nodeBif.ipAddr;
                       		    if(ifIpB!=""&&ifIpB!=null){
                       		     ifName=ifName+"("+ifIpB+")";
                       		   }
                       		if(nodeBif.ifIndex=='${line.ifIndexB}'){
                       			$("#ifNameB").append("<option  value=\"" + text+"\"  ifIndex=\"" + nodeBif.ifIndex+"\"  ifSpeed=\"" + nodeBif.ifSpeed+"\" baudRate=\"" + nodeBif.baudRate+"\" ifIpB=\"" + ifIpB+"\"  ifName=\"" + ifName+"\"  selected>" + ifName + "</option>");
                       		}else{
                       			$("#ifNameB").append("<option  value=\"" + text+"\"  ifIndex=\"" + nodeBif.ifIndex+"\" ifSpeed=\"" + nodeBif.ifSpeed+"\" baudRate=\"" + nodeBif.baudRate+"\"  ifIpB=\"" + ifIpB+"\" ifName=\"" + ifName+"\">" + ifName + "</option>");
                       		}
                       		
                       		
                       }
                       changeifBspeed();
                       changeLineName();
                   }
               });
  }
  
  function changeifAspeed(){
//     var ifBandwidthA=$("#ifNameA").find("option:selected").attr("baudRate");
     var ifBandwidthA=$("#ifNameA").find("option:selected").attr("ifSpeed");
    $("#ifBandwidthA").val(ifBandwidthA);
    $("#ifSpeedA").val(ifBandwidthA/1000000+" Mbps");
    
    
     var ifIndexA=$("#ifNameA").find("option:selected").attr("ifIndex");
     $("#ifIndexA").val(ifIndexA);
     var ifIpA= $("#ifNameA").find("option:selected").attr("ifIpA");
     $("#ifIpA").val(ifIpA);
     
     
     
      var ifBandwidthB=$("#ifNameB").find("option:selected").attr("ifSpeed");
      if(ifBandwidthA>ifBandwidthB){
          $("#numVal").val(ifBandwidthB/1000000);
      }else{
          $("#numVal").val(ifBandwidthA/1000000);
      }
     
    
  }
  
  function changeifBspeed(){
//      var ifBandwidthB=$("#ifNameB").find("option:selected").attr("baudRate");
      var ifBandwidthB=$("#ifNameB").find("option:selected").attr("ifSpeed");
      
      $("#ifBandwidthB").val(ifBandwidthB);
       $("#ifSpeedB").val(ifBandwidthB/1000000+" Mbps");
       
       var ifIndexB=$("#ifNameB").find("option:selected").attr("ifIndex");
     $("#ifIndexB").val(ifIndexB);
       var ifIpB= $("#ifNameB").find("option:selected").attr("ifIpB");
     $("#ifIpB").val(ifIpB);
     
     
      var ifBandwidthA=$("#ifNameA").find("option:selected").attr("ifSpeed");
      if(ifBandwidthA>ifBandwidthB){
          $("#numVal").val(ifBandwidthB/1000000);
      }else{
          $("#numVal").val(ifBandwidthA/1000000);
      }
     
     
  }
  
  
  function changeLineName(){
     var nodeAIf=$("#ifNameA").find("option:selected").val();
     var nodeBIf=$("#ifNameB").find("option:selected").val();
     $("#lineName").val('${nodeA.nodeName}'+"("+nodeAIf+")"+"_"+'${nodeB.nodeName}'+"("+nodeBIf+")");
     
  }

</script>

</head>
<body> 
	<form id="formSave" modelAttribute="topoLine" action="${ctx}/topo/topoLine/saveLine.do" method="post">
	<input type="hidden" id="ifIndexA" name="ifIndexA"  value=""/>
	<input type="hidden" id="ifIndexB" name="ifIndexB"  value=""/>
	<input type="hidden" id="lineId" name="lineId"  value="${line.lineId }"/>
	<input type="hidden" id="graphId" name="graphId"  value="${graphId }"/>
	<input type="hidden" id="nodeAId" name="nodeAId"  value="${nodeA.nodeId }"/>
	<input type="hidden" id="nodeBId" name="nodeBId"  value="${nodeB.nodeId }"/>
	
	<input type="hidden" id="ifBandwidthA" name="ifBandwidthA"  value=""/>
	<input type="hidden" id="ifBandwidthB" name="ifBandwidthB"  value=""/>
	
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
			
			<tr style="line-height: 30px;">
                <td colspan=5 style="text-align: center">
                <span><h3>节点接口信息</h3></span>
                </td>
                <td></td>
			</tr>
			
			 <tr>
                <td align="right" nowrap>A端节点类型：</td>
                <td><input id="nodeAType" name="nodeAType" value="${nodeA.devTypeName}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端节点类型：</td>
                <td><input id="nodeBType" name="nodeBType" value="${nodeB.devTypeName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
            
             <tr>
                <td align="right">A端节点名字：</td>
                <td><input id="nodeAName" name="nodeAName" value="${nodeA.nodeName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端节点名字：</td>
                <td><input id="nodeBName" name="nodeBName" value="${nodeB.nodeName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
            
             <tr>
                <td align="right">A端接口名称：</td>
                <td>
                 <select id="ifNameA" name="ifNameA" style="width:198px;" onchange="changeifAspeed();changeLineName()">
				 </select>
                </td>
                <td></td>
                <td align="right">B端接口名称：</td>
                <td>
                 <select id="ifNameB" name="ifNameB" style="width:198px;" onchange="changeifBspeed();changeLineName()">
				 </select>
                </td>
                <td></td>
            </tr> 
            
              <tr>
                <td align="right">A端接口IP：</td>
                <td><input id="ifIpA" name="ifIpA" value="" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端接口IP：</td>
                <td><input id="ifIpB" name="ifIpB" value="" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
            
              <tr>
                <td align="right">A端接口带宽：</td>
                <td><input id="ifSpeedA" name="ifSpeedA" value="" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">B端接口带宽：</td>
                <td><input id="ifSpeedB" name="ifSpeedB" value="" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
            </tr> 
			
			<tr style="line-height: 30px;">
                <td colspan=5 style="text-align: center">
                <span><h3>线路信息</h3></span>
                </td>
                <td></td>
			</tr>
			
            <tr>
                <td align="right">线路带宽：</td>
                <td>
                <input id="numVal" name="numVal" value="" style="width:130px;" type="text" ltype="text" class="validate[custom[onlyNumberSp]]"  />
					<select id="unitVal" name="unitVal" style="width:60px">
						<option value="Bps">Bps</option>
						<option value="Kbps">Kbps</option>
						<option value="Mbps" selected>Mbps</option>
						<option value="Gbps">Gbps</option>
						<option value="Tbps">Tbps</option>
					</select>
				</td>
                <td></td>
            </tr>   
            
            
            
            <tr>
                <td align="right">线路名称：</td>
                <td colspan=4><input id="lineName" name="lineName" value="" type="text" ltype="text" class="ip2"  /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="center" colspan=5 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </from>
</body>
</html>

