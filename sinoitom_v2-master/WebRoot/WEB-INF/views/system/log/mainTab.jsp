<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统日志</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

    <script type="text/javascript">
    	var tab =null;
    	var orgId='${orgId}';
        var initType='systemLog';
        $(function ()
        {
        	$("#layout-index").ligerLayout({leftWidth:200, space:2});
        	tab = $("#navtab1").ligerTab({ 
            	dblClickToClose: false,
            	onAfterSelectTabItem: function (tabid){
            	 tabChange(tabid);
	            }
            });
            fleshShow(initType,"0");
        });
                
        function tabChange(tabid){
     	  if(tabid=="tabitem1"){
       	    initType = "systemLog";
       	    fleshShow(initType,"0")
          }
           else if(tabid=="tabitem2"){
       	    initType = "deviceLog";
       	     fleshShow(initType,"1")
          }
          else if(tabid=="tabitem3"){
       	    initType = "switchLog";
       	     fleshShow(initType,"2")
          }
          
        }
        

		
		function fleshShow(initType,oprType) {

			 if(initType=="systemLog"){
               	$("#systemLog_iframe").attr("src", '${ctx}/system/log/main.do?oprType='+oprType);
            }
            else if(initType=="deviceLog"){
               	$("#deviceLog_iframe").attr("src", '${ctx}/system/log/main.do?oprType='+oprType);
            }
            else if(initType=="switchLog"){
            	$("#switchLog_iframe").attr("src", '${ctx}/system/log/main.do?oprType='+oprType);
            }
		}
        

        
		function windowHeight() {
		    var de = document.documentElement;
		    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
		}
		$(document).ready(window.onresize=function(){
				var wh=windowHeight();
				document.getElementById("systemLog").style.height = (wh-25)+"px";
				document.getElementById("deviceLog").style.height = (wh-25)+"px";
				document.getElementById("switchLog").style.height = (wh-25)+"px";
		});
    </script>
</head>
<body>
	<div id="layout-index">
			<div id="navtab1">
				<div id="systemLog" title="系统操作日志">
					<iframe frameborder="0" id="systemLog_iframe" src="#" class="whFull"></iframe>
				</div>
				<div id="deviceLog" title="路由器操作日志">
					<iframe frameborder="0" id="deviceLog_iframe" src="#" class="whFull"></iframe>
				</div>
				<div id="switchLog" title="交换机操作日志" >
					<iframe frameborder="0" id="switchLog_iframe" src="#" class="whFull"></iframe>
				</div>
				
			</div>
	</div>
</body>
</html>