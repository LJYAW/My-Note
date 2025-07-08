<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择目录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 

    <script type="text/javascript">
	var result = '${result}';
	var message = '${message}';
        $(function ()
        {            
    		$("#formImport").ligerForm({inputWidth:200});
    		$("#formImport").validationEngine({promptPosition: "centerRight"});	
			
            if( result=='success' && message!='' ){
            	$.ligerDialog.success(message);
            }
            if( result=='error' && message!='' ){
            	$.ligerDialog.error(message);
            }
            
            $("#bnClose").click(function(){
        		window.parent.closeDlg();
        	});
        });
        
//         function onDownload()
//         {
// 	    	window.location.href = "${ctx}/download.do?name=modelOIDImportExcel";	
//         }
        
	    function onImport()
	    {
	    	formImport.submit();
		}

    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
  	<form id="formImport" action="${ctx}/snmp/mibNode/selectDirection.do" method="post" enctype="multipart/form-data">
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
        <td align="right">选择目录:</td>
        <td colspan=4><input type="file" id="impFile" name="impFile" size="76" multiple="multiple" class="ip2" /></td>
        <td></td>
      </tr>
      <tr>
        <td align="right" colspan=3 >
          <input id="bnImport" type="submit" value="确定" style="width:80px;" class="l-button mg6" />
        </td>
        <td align="left" colspan=3>
        <input id="bnClose" type="button" value="关闭" class="l-button mg6" />
        </td>
      </tr>
    </table>
    </form>
</body>
</html>

