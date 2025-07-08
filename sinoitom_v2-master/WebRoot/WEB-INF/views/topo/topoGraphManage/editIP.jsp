<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>拓扑图编辑</title>
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
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">

var canOrgSelect = false;
$(function ()
	{	
	 $("#formSave input").filter(".ip1").ligerTextBox({width: 200});
	 $("#formSave").validationEngine({promptPosition: "centerRight"});
	 
	 
	//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
	});

	

function submitForm(){

        var nodeName=$("#nodeName").val();
        var nodeId=$("#nodeId").val();
        if(nodeName){
         	$.ajax({
    	        url:'${ctx}/topo/topoNode/saveNodeIP.do?nodeId='+nodeId+'&nodeName='+nodeName,
    	        dataType:'json',
    	        success:function(data){
    	      		if(data.result=="success"){
    	      			 window.parent.loadTopo(data.graphId);
    	      		 window.parent.closeDlg();
    	      		}
    	        }
    	      });
        }else{
        	window.parent.$.ligerDialog.warn("IP不能为空");
        }
   
    
	}


    

    


</script>

</head>
<body>
	<form id="formSave" action="" method="post" style="overflow:hidden;">
		<input type="hidden" name="nodeId" id="nodeId" value="${node.nodeId }" />

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
				<td align="right">设备IP:</td>
				<td>
				<input id="nodeName" name="nodeName" value="${node.nodeName}" type="text" ltype="text" class="validate[required[不能为空],custom[number]] ip1" />
				</td>
				<td></td>
				
			</tr>


			<tr>
				<td align="center" colspan=3>
					<div class="save">
						<input id="bnSave" type="button" value="保 存" class="l-button mg6"
							onclick="submitForm()" />
					</div></td>
			</tr>

			<tr>



			</tr>
			<tr>
				<td align="center" colspan=6><div class="load1">
						<div>
							<img src="" id="pic1" style="width:100%;height:auto;">
						</div>
					</div>
				</td>
			</tr>
			
		</table>
	</form>
</body>
</html>

