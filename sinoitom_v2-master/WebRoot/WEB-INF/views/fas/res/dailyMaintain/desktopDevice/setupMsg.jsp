<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>桌面设备</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
 <style type="text/css">
 .top {
     position: fixed;
        top: 0;
        left: 0;
        right: 0;
        height: 50px;
        background:#fff;
	}
	
	 body {
        margin: 0;
        padding: 0;
       }
    </style>
    <script type="text/javascript">
    
    $(function ()
	{
	 getData();
	 $(".top").hide();
	});
    function saveCfg(){
     var url=timeURL('${ctx}/config/configFile/saveConfig.do');
//      var url='${ctx}/config/configFile/saveConfig.do';
        var devIp='${switchIp}';
        var cfgContent= $("#msgValue").html();
        $.ajax({
                   url:url,
                   type:"POST",
                   dataType:'html',
                   data:{"devIp":devIp,"cfgContent":cfgContent},
                   success:function(data){
                       if(data=='success'){
                             window.parent.$.ligerDialog.success("配置文件保存成功!");
                       }
                    }
             });
    }
    
    
//     window.onload=function(){
//       $("#emptyData").hide();
// 	};
	
    
    function getData(){
     var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/setupMsg.do?switchIp='+'${switchIp}');
            $.ajax({
                   url:url,
                   type:"get",
                   dataType:'json',
                   success:function(data){
                       if(data!=null){
                        $("#emptyData").hide();
                        $(".top").show();
//                         alert(data.message);
                         if(data.message==0){
                           $("#title").text("设备"+data.key+"当前配置文件");
                             $("#msgValue").html(data.value);
                             $("#bnSave").show();
                         }else{
                              $("#message").html(data.value);
                              $("#bnSave").hide();
                         }
                           
                       }
                    }
             });
     
    }
    
   </script>
    </head>
  
  <body style="overflow:auto;">
  <div  id="emptyData" align="center">
		<img style="margin: 50px;" src="${ctx}/img/loading.gif"/>
  </div>
    <div class="top">
        <table class="tb_edit">
          <tr>
            <td align="center" colspan="3"><h3 id="title"> </h3></td>
            <td> <input id="bnSave" type="button" value="保存配置文件" class="l-button mg6" style="width:80px;height:26px;line-height:25px;" onclick="saveCfg()"/></td>
          </tr>
          <tr>
            <td colspan="2" align="center"> <h3 id="message"></h3></td>
          </tr>
        </table>
      
    </div>
   
   <div>
    <table style="width:700px;margin-top:30px">
    	<tr>
    		<td style="width:120px"></td>
<!-- 	    	<td style="font-weight:bold" align="center">${key }</td> -->
    	</tr>
    	<tr>
    		<td style="height:22px" colspan="2"></td>
    	</tr>
    	<tr>
    		<td style="width:120px"></td>
    		<td id="msgValue"></td>
    	</tr>
    </table>
    </div>
  </body>
</html>
