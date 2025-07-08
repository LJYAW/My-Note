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
<title>Tomcat指标监测</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>

	<script type="text/javascript">
	$(function (){

        <c:if test="${result=='success'}">
        // var data = ${saveData};
        // window.parent.saveOK(data);

        window.parent.saveOK();
        window.parent.closeDlg();
        </c:if>

		$("input").filter(".ip1").ligerTextBox({ width: 202 });
        // $("#formSave").ligerForm({inputWidth:200});
        // $("#formSave").validationEngine({promptPosition: "centerRight"});

        $("#formSave").ligerForm({inputWidth:200});
        $("#formSave").validationEngine({promptPosition: "centerRight"});

        <c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
        </c:if>
        $("#formSave").validationEngine();
        //表单验证
        $("#formSave").click(function(check) {
            if($("#formSave").validationEngine()){
            }else{
                check.preventDefault();//此处阻止提交表单
            }
        });

    });
  

      function sendform(){
		   var devIpAddr;
		   if($("#devIpAddr").val()!="" && $("#devIpAddr").val()!=null){
		   devIpAddr=$("#devIpAddr").val();
		   }else{
		   devIpAddr="ip_kong";
		   }
		   var content;
		   if($("#content").val()!="" && $("#content").val()!=null){
		   content=$("#content").val();
		   }else{
		   content="!@!@";
		   }
      
      
       // var str = startTime+","+endTime+","+facilitys+","+severity+","+devIpAddr+","+content;

       // window.parent.saveOK(str);
       // window.location.href="${ctx}/fas/res/tom/index/showTomcatMessage.do";
	   // window.parent.closeDlg();


      
    }

    function checkIP(){
		if($("#devIpAddr").val()!="" && $("#devIpAddr").val()!=null){
		   var devIpAddr=$("#devIpAddr").val();
		   if (!ipok(devIpAddr)) {

				$.ligerDialog.warn('请输入合法IP地址');
               $("#devIpAddr").val("");
			}
		}
    }

    function cleckLoading(){
        $("#trTxt").removeClass("dN");
        $("#trMsg").addClass("dN");

    }

</script>

</head>
<body>
	<form id="formSave"  action="${ctx}/fas/res/mdlware/app/index/showTomcatMessage.do" method="post">
		

	<div style="margin:0 auto;">
		<div id="trTxt" class="dN" style="border:0px;height:100px;line-heigth:30px;text-align:center;margin-top:50px;">
           		<span id="searching">
           			<img src="${ctx}/img/loading.gif" alt="#" />&nbsp;&nbsp;开启监测中...
           		</span>
		</div>
		<div id="trMsg"  >
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
					<td align="right">服务器IP：</td>
					<td>
						<input id="devIpAddr" name="devIpAddr" placeholder="请输入ip地址" type="text"  class="validate[required[IP不能为空]]  ip1" onchange="checkIP();"/>
					</td>
					<td ></td>

					<td align="right">监测端口：</td>
					<td>
						<input id="content" name="content" placeholder="请输入要监测的端口号" type="text"  class="validate[required[端口号不能为空],custom[onlyNumberSp]]  ip1"/>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">用户名：</td>
					<td>
						<input id="userName" name="userName" placeholder="请输入用户名" type="text" class="validate[required[用户名不能为空],custom[onlyLetterNumber]]  ip1" />
					</td>
					<td ></td>

					<td align="right">密码：</td>
					<td>
						<input id="psWord" name="psWord" placeholder="请输入密码" type="password"  class="validate[required[密码不能为空],custom[onlyLetterNumber]]  ip1" />
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">中间件类型：</td>
					<td>
						<select name="middleType" id="middleType" style="width:204px">
							<option value="0" selected="selected">应用中间件</option>
							<option value="1">消息中间件</option>
						</select>

					</td>
					<td></td>

					<td align="right">间隔时间：</td>
					<td>
						<select name="intervaltime" id="intervaltime" style="width:204px">
							<%--<option value="30Second" selected="selected">30秒</option>--%>
							<option value="1Min">1分钟</option>
							<option value="5Min" selected="selected">5分钟</option>
<%--							<option value="10Min">10分钟</option>--%>
							<option value="30Min">30分钟</option>
							<option value="1Hours" >1小时</option>
						</select>

					</td>
					<td></td>
				</tr>


				<tr>
					<td colspan=6 align="center" style="padding:10px">
						<input id="bnSave" type="submit" value="监 测" onclick="cleckLoading();"  class="l-button mg6" />
					</td>
				</tr>
			</table>
	<br/>
		</div>
	</div>
	</form>

</body>
</html>

