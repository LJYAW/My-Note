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
<title>Snmp工具</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
    var selectRowData = null;
	$(function ()
	{				
	    $("#emptyData").hide();
	    getParamItems();
		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		$("input").filter(".ip2").ligerTextBox({ width: 580 });
		$("#formSearch").validationEngine({promptPosition: "centerRight"});
		dataMain.Rows =null;
		gridMain = $("#maingrid").ligerGrid({
                columns: 
                     [
                     { display:'', name:'sortKey',type:'float',align:'left',width:1,hide: true},
                     { display:'SnmpOID', name:'snmpKey',type:'string',align:'left',width:200},
		             { display:'结果', name:'snmpVal',type:'string',align:'left',width:960,height:'auto'}
               	],
               	data: dataMain,
				alternatingRow: 'true',
                width: '98%',
                height:'99%',
                usePager:false,
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                }
            });
	});
	
	function snmpGet() 
	{
		//$("#taMsg").text(""); 
		if(!$("#taMsg").hasClass("dN")){
		   $("#trMsg").addClass("dN");
		}
		var devIp =$("#devIp").val();
		var snmpComStr =$("#snmpComStr").val();
		var snmpOid  =$("#snmpOid").val();
		var snmpVersion=$("#snmpVersion").val();
		var timeout=$("#timeout").val();
		var snmpPort=$("#snmpPort").val();
		$("#emptyData").show();
		$.ajax({
			url: timeURL("${ctx}/system/util/snmpGet.do"),
			data: "snmpVersion="+snmpVersion+"&timeout="+timeout+"&snmpPort="+snmpPort+"&devIp="+devIp+"&snmpComStr="+snmpComStr+"&snmpOid="+snmpOid+"&date="+new Date(),
			dataType: 'json',
			success: function(data) {
			$("#emptyData").hide();
				if( data.listSnmp!=null&&data.listSnmp!='' ){
					dataMain.Rows = data.listSnmp;
					//$("#taMsg").text(data.message); 
           			$("#trMsg").removeClass("dN");
           			loadData();
        		}
        		else{
           			$.ligerDialog.warn(data.result);
           			$("#bnSearch").removeAttr("disabled");  
        		}
			}
		});        			
	}
	
	function loadData(){
            selectRowData = null;
            gridMain.loadData();
            //gridMain.changeSort('sortKey', 'asc');
    }
	function snmpWalk() 
	{
		//$("#taMsg").text(""); 
		if(!$("#taMsg").hasClass("dN")){
		   $("#trMsg").addClass("dN");
		}
		var devIp =$("#devIp").val();
		var snmpComStr =$("#snmpComStr").val();
		var snmpOid  =$("#snmpOid").val();
		var snmpVersion=$("#snmpVersion").val();
		var timeout=$("#timeout").val();
		var snmpPort=$("#snmpPort").val();
		$("#emptyData").show();
		$.ajax({
			url: timeURL("${ctx}/system/util/snmpWalk.do"),
			data: "snmpVersion="+snmpVersion+"&timeout="+timeout+"&snmpPort="+snmpPort+"&devIp="+devIp+"&snmpComStr="+snmpComStr+"&snmpOid="+snmpOid+"&date="+new Date(),
			dataType: 'json',
			success: function(data) {
			$("#emptyData").hide();
				if( data.listSnmp!=null&&data.listSnmp!='' ){
					dataMain.Rows = data.listSnmp;
        			//$("#taMsg").text(data.message); 
            		$("#trMsg").removeClass("dN");
            		loadData();
        		}
        		else{
           			$.ligerDialog.warn(data.result);
           			$("#bnSearch").removeAttr("disabled");  
        		}
			}
		});        			
	}
	
	   function getParamItems(){
      	$.ajax({
			url: timeURL("${ctx}/system/param/item/getItemByGroupCode.do"),
			dataType: 'json',
			success: function(data) {
			 if(data!=null){
			    for(var i=0;i<data.length;i++){
			      var item=data[i];
			      if(item.paramCode=="SnmpDefaultRoString"){
			           $("#snmpComStr").val(item.valueText);
			      }
			      if(item.paramCode=="SnmpDefaultPort"){
			           $("#snmpPort").val(item.valueText);
			      }
			      if(item.paramCode=="SnmpTimeOut"){
			           $("#timeout").val(item.valueText/1000);
			      }
			    }
			 }
			}
		});
    }
</script>

</head>
<body> 
	<div class="wFull hL">snmp工具：</div>
	<form id="formSearch" action="" method="post">
		<table id="tbSearch" align="center" class="tb_edit" style="width:750px;">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
			
			<tr>
			  <td align="right"><span style="font-weight:bold;">Snmp版本：</span></td>
			  <td>
			     <select id="snmpVersion" name="snmpVersion" style="width:202px;">
			        <option value="1">V1</option>
			        <option value="2">V2c</option>
			     </select>
			  </td>
			  <td></td>
			  <td align="right"><span style="font-weight:bold;">超时时间（秒）：</span></td>
			  <td><input id="timeout" name="timeout" value="" type="text" ltype="text" class="ip1"/></td>
			  <td></td>
			</tr>
			
			<tr>
			  <td align="right"><span style="font-weight:bold;">Snmp端口：</span></td>
			  <td><input id="snmpPort" name="snmpPort" value="" type="text" ltype="text" class="ip1"/></td>
			  <td></td>
			  <td align="right" nowrap><span style="font-weight:bold;">Snmp只读团体字：</span></td>
			  <td><input id="snmpComStr" name="snmpComStr" value="" type="text" ltype="text" class="ip1"/></td>
			  <td></td>
			</tr>
			
            <tr>
                <td align="right"><span style="font-weight:bold;">设备IP地址：</span></td>
                <td colspan=4><input id="devIp" name="devIp" value="" type="text" ltype="text" class="validate[required[设备IP地址不能为空],maxSize[512]] ip2" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right"><span style="font-weight:bold;">Snmp OID：</span></td>
                <td colspan=4><input id="snmpOid" name="snmpOid" value=".1.3.6.1.2.1.1.1.0" type="text" ltype="text" class="validate[required[Snmp OID不能为空],maxSize[512]] ip2" /></td>
				<td></td>
            </tr>
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSnmpGet" type="button" value="SnmpGet"  onclick="snmpGet()" class="l-button mg6" style="width:88px;"/>
           		</td>
           		<td align="left" colspan=3 >
           		<input id="bnSnmpWalk" type="button" value="SnmpWalk" onclick="snmpWalk()" class="l-button mg6" style="width:110px;"/>
           		</td>
            </tr>
		</table>
		
		<table align="center" style="width:800px">
			<tr id="trMsg" class="dN">
            	<td>
            	<!--  
            	<textarea id="taMsg" readonly="readonly" cols="120" rows="15" class="l-textarea" style="width:550px"></textarea>
            	-->
            	<div id="taMsg" style="width:750px;height:300px">
            		<div id="maingrid" style="clear:both"></div>
            	</div>
           		</td>
            </tr>
		</table>
		<div  id="emptyData" align="center">
		 <img style="margin: 50px;" src="${ctx}/img/loading.gif"/>
  		</div>
    </form>
</body>
</html>
