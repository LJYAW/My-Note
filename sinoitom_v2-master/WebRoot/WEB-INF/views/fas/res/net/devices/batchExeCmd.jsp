
<!DOCTYPE html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>命令批处理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/edit.js" type="text/javascript"></script>
<script src="${ctx}/js/main.js" type="text/javascript"></script>
<script src="${ctx}/js/index.js" type="text/javascript"></script>
<script src="${ctx}/js/dateFormat.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script>
 <style type="text/css">
	
.message{
            font-size:14px;
            font-weight:bold;  /*加粗*/
            text-align: left;
            margin-top:20px;
            margin-left:200px;
        }
    </style> 
<script type="text/javascript">

		$(function() {
		});

	// 	检查是否全是空格
	function checkBlankSpace(str){
    	while(str.lastIndexOf(" ")>=0){
      	str = str.replace(" ","");
    	}
    	return str;
   }
   


	//点击配置下发向交换机发送命令
	var startTime;
	function release() {
			var ip=$("#ip").find("option:selected").text();
		
			var content = $("#content").val();
			content = $.trim(content);
			if(content=="" || content==null || content==undefined){
				window.parent.$.ligerDialog.error("配置命令不能为空！");
				return;
			}
			
			value = checkBlankSpace(content);
// 			如果输入的全是空格，那么要求重新填写
    		if(content.length>0 && value.length == 0) {
     			window.parent.$.ligerDialog.error("您填写的配置命令全是空格，这是非法数据请重新填写！");
     			return ;
   			}
   			
			var contentArr = content.split(";");
			
			
			if(contentArr.lenght==0){
				window.parent.$.ligerDialog.warn("请填写有效的配置命令！");
				return ;
			}
			
            startTime = new Date().getTime();
			$("#trMsg").removeClass("dN");
			$("#trTxt").removeClass("dN");

			$.ajax({
						url : timeURL("${ctx}/fas/res/net/devices/batchExeCmd.do"),
						type : 'POST',
						data : "ip=" + ip + "&content="+ content,
						dataType : 'json',
						success : function(data) {
							if(data != null){
								if(data.errorSb!=null&& data.errorSb!=''){
									window.parent.$.ligerDialog.warn(data.errorSb); 
								}else{
									var url = "${ctx}/fas/res/net/devices/checkLineCase.do";
				    				window.open(url,'', 'height=400, width=760, top=160, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
				    				$("#trTxt").hide();
								
								}
							}
						}
					});
	}
	var i = 0;
	function showMsg(data, size) {
// 		var resultStr = ""; 
		for(var i  = 0;i<data.length;i++){
		if (data[i].clierror != "") {
				$("#trMsg").append("</br>" + "交换机：" + data[i].ipAddress  + "&nbsp;&nbsp;"+ data[i].msg+ "&nbsp;&nbsp;" + data[i].clierror);
		} else {
			$("#trMsg").append(
					"</br>" + "交换机：" + data[i].ipAddress + "&nbsp;&nbsp;"+ data[i].msg);
		}
		}

		getScrollTop();
		i++;
		if (i < size) {
			setTimeout(function() {
				showMsg(data, size);
			}, 500);
		} 
	}

	function getScrollTop() {
		// scrollIntoView(false)元素下边框与视窗底部齐平
		document.getElementById("showlogs").scrollIntoView(false);
	}
	
	function contentOver(){
    		$("#contentHelp").prev('div').css("display","block");
    }
    function contentOut(){
   	 	$("#contentHelp").prev('div').css("display","none");
    }
</script>
</head>
<body >
	<form:form id="formSave" method="post">
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
                <td align="right">ip地址：</td>
                <td>
                	<select id="ip" name="ip">
                		<option value='${deviceIpAddr }'>${deviceIpAddr }</option>
                		<option value='${agent_IP }'>${agent_IP }</option>
                	</select>
                </td>
                <td></td>
                
            </tr>
            <tr></tr>
			<tr>
				<td align="right">请书写命令行：</td>
				<td ></td>
				<td ></td>
			</tr>
			
			<tr>
				<td align="right"></td>
				<td colspan="4">
					<textarea id="content" name="content" value="" class=" l-textarea formText " rows="10" cols="80"
						type="text" ltype="text" >show version;&#10;show ip int brief;</textarea>
				</td>
				<td ></td>
			</tr>
			<tr>
				<td></td>
				<td align="center" style="height: 40px;"><input id="bnSave"
					type="button" value="批量执行" class="l-button bn2 mg6"
					onclick="release()" style="height:25px;line-height:22px;width:75px;"/>
				</td>
				<td></td>
				<td align="center" style="height: 80px;"><input id="bnCancel"
					type="button" value="取消" class="l-button bn2 mg6"
					onclick="window.parent.closeDlg();" style="height:25px;line-height:22px;width:75px;"/></td>
			</tr>
		</table>
	</form:form>

<div id="showlogs"
	style="text-align: left;width:500px;height:auto;padding-left:15px;margin-left: auto;margin-right: auto;">
	<div id="trMsg" class="dN"></div>
	
	<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:left">
		<span id="searching"> <img style="margin: 50px;" src="${ctx}/img/loading.gif"/>&nbsp;&nbsp;正在返回执行结果，请等待...
		</span>
	</div>
</div>
	
</body>

</html>

