<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>IP Host管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/main.js" type="text/javascript"></script>
</head>
  <script  type="text/javascript">
	$(function (){
		<c:if test="${result=='success'}">
		//alert('${jsonListData}');
			window.parent.reloadGrid(${jsonListData});
		</c:if>
	});
	
	
	function getSearchData(){
		var url = '${ctx}/fas/res/host/ipHost/search.do';
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				onRefresh();
        			}
        		}
        	});
	}
</script>
  <body style="padding:0px; overflow:hidden; ">
    <div id="searchDlg" style="padding:0px;">
    	<form style="padding:0px;" id="formSearch" action="${ctx}/fas/res/host/ipHost/search.do" method="post">
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
                <td align="right">所属机构:</td>
                <td><s:objSelect name="filter_EQS_orgId" objName="OrgOrganization" textAtt="orgName" valueAtt="orgId" treeCodeAtt="treeCode" prompt="全部" lclass="ip1"  ltype="select"/></td>
                <td colspan="4"></td>
            </tr>
             <tr>
                <td align="right">交换机IP:</td>
                <td><input name="filter_LIKES_switchIp" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">设备IP地址:</td>
                <td><input name="filter_LIKES_ipAddr" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">MAC地址:</td>
                <td><input name="filter_LIKES_macAddr" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">信息点编号:</td>
                <td><input name="filter_LIKES_infoPointNo" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>   
            <tr>
                <td colspan=3 align="right" style="padding:10px">
                <input id="bnSearch" type="submit" value="查 询" class="l-button" /> 
                </td>
                <td colspan=3 style="padding:10px">
                <input id="bnReset" type="button" value="重 置" class="l-button" />
                </td>
            </tr>   
        </table>
        </form>
    </div>
  </body>
</html>
