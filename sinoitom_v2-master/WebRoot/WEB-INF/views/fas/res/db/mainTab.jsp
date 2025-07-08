<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>综合查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />

<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-en.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>
	
	<script type="text/javascript">
		var height;
        $(function (){
			height=$(document).height() - 42;
			$("#myTabContent").height(height);
			
			var iframes = document.getElementsByTagName('iframe');
			for(var i=0;i<iframes.length;i++){
			  iframes[i].setAttribute('height',height);
			}
        });

        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
        }
        $(document).ready(window.onresize=function(){
            var wh=windowHeight();
            document.getElementById("getConfigParam").style.height = (wh-12)+"px";
            document.getElementById("getOperaStatus").style.height = (wh-32)+"px";
            document.getElementById("getPerformance").style.height = (wh-32)+"px";

        });


        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
        }


		function showTab(str){
			var id=str.split('#')[1];
			$("#"+id).css('display','block'); 
			var iframes = document.getElementsByTagName('iframe');
			for(var i=0;i<iframes.length;i++){
				if(id!=iframes[i].name){
					
				  $("#"+iframes[i].name).css('display','none'); 
				}else{
					window.frames[id].location.reload();
				}
			}
		}
		
		

	</script>
</head>
<body style="padding:0px; overflow:hidden;">
<form name="sysForm" id="sysForm" >
	<DIV>
		<div  position="center">
		  <ul id="myTab" class="nav nav-tabs">
		   	<li class="active"><a href="#getConfigParam" data-toggle="tab" onclick="showTab(this.href)">配置参数</a></li>
			<li><a href="#getOperaStatus" data-toggle="tab" onclick="showTab(this.href)">运行状态</a></li>
			<li ><a href="#getPerformance"  data-toggle="tab"  onclick="showTab(this.href)">性能指标</a></li>
		  </ul>
		</div>
	
		<div id="myTabContent">
			<div class="tab-pane fade in active" id="getConfigParam" style="display: block">
				 <iframe frameborder="0" name="getConfigParam"  src="${ctx}/fas/res/db/index/toGetConfigParam.do?dbsId=${dbsId }" style="width:100%;" ></iframe>
			</div>
			<div class="tab-pane fade" id="getOperaStatus" style="display: none">
				<iframe frameborder="0" name="getOperaStatus" src="${ctx}/fas/res/db/index/toGetOperaStatus.do?dbsId=${dbsId }" style="width:100%" ></iframe>
			</div>
			<div class="tab-pane fade" id="getPerformance" style="display: none" >
				 <iframe frameborder="0" name="getPerformance" src="${ctx}/fas/res/db/index/toGetPerformance.do?dbsId=${dbsId }" style="width:100%;"></iframe>
			</div>
		</div>
		
	</DIV>
	
</form>
</body>
</html>