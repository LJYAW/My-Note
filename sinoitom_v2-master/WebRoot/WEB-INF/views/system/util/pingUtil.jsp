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
<title>Ping</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<!-- 		<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-common.css" rel="stylesheet" type="text/css" /> -->
		
		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		<!-- ace settings handler -->
		<script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
<!-- 		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-en.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>
		<style type="text/css">
			.ip1{
		         width:200px;
		      }
				
		</style>

<script type="text/javascript">
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
    var selectRowData = null;
	$(function (){	
		dataMain.Rows =null;
	});
	
	function snmpGet(){
		$("#trMsg").html('');
		$("#trTxt").removeClass("dN");
		var pingIp =$("#pingIp").val();
		$.ajax({
			url: "${ctx}/system/util/ping.do",
			data: "pingIp="+pingIp+"&date="+new Date(),
			dataType: 'json',
			success: function(data) {
				$("#trTxt").addClass("dN");
				if( data.pingResult!=null&&data.pingResult!='' ){
           			$("#trMsg").removeClass("dN");
           			$("#trMsg").html(data.pingResult);
        		}
        		else{
           			layer.msg(data.result,{icon: 7,time:1500});
           			$("#trMsg").addClass("dN"); 
        		}
			}
		});        			
	}
	
	function loadData(){
            selectRowData = null;
            gridMain.loadData();
    }
</script>

</head>
<body > 
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
          <ul class="breadcrumb">
                <li class="active">${titleContent }</li>
          </ul>
    </div>	
	<div class="wFull hL"></div>
	<form id="formSearch" action="" method="post">
		<table id="tbSearch" align="center" class="tb_edit">
			<tr>
				<td width="160px"></td>
				<td colspan=2 width="250px"></td>
				<td width="300px"></td>
			</tr>
			<tr>
				<td colspan="4" align="left"><span style="font-weight:bold;">ping工具：</span></td>
			</tr>
            <tr>
                <td align="right"><span style="font-weight:bold;">IP地址：</span></td>
                <td colspan=2><input id="pingIp" name="pingIp" value="" type="text" ltype="text" class="validate[required]"  /></td>
                <td align="left">请直接输入合法IP地址！</td>
            </tr>
            <tr>
           		<td align="right" ></td>
           		<td align="center" colspan=2 >
           		<input id="bnSnmpGet" type="button" value="执行"  onclick="snmpGet()" class="l-button bn2 mg6" style="width:88px;"/>
           		</td>
           		<td align="right" ></td>
            </tr>
		</table>
		<div style="margin:0 auto;width:460px;">
			<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:center">
           		<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;Ping...
           		</span>
            </div>
            <span id="trMsg" class="dN" >
            </span>
		</div>
    </form>
</body>
</html>
