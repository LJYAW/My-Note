<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资源信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
  var initType="";
  var tab = null;
  var accordion = null;
  var tree = null;
  var layout=null;
  var unitId="";
  var manager = null;
  var menu;
  var actionNodeID;
  var navtab=null;
  $(function(){
     initDivHeight();//初始化div高度
	 layout = $("#layout-index").ligerLayout({leftWidth:200, space:2,isLeftCollapse:false});
     var treeData=${treeData};
     $("#vendor-tree").ligerTree({
       checkbox: false,
       slide: true,
       btnClickToToggleOnly: true,
       nodeWidth: 100,
       data: treeData,
       idFieldName :'id',
       parentIDFieldName :'pid',
        onSelect:function(node){
        	unitId=node.data.id;
       		 fleshShow(unitId);
        }
    });
     navtab = $("#navtab1").ligerGetTabManager();
     $("#prodClass_iframe").attr("src", '${ctx}/cmdb/prodClass/main.do?id='+unitId);
    manager = $("#vendor-tree").ligerGetTreeManager();
    window.onresize=function(){
        initDivHeight();
	}
  });
  
   function initDivHeight(){
   		var wh=windowHeight();
   		document.getElementById("tree-index").style.height = (wh-15)+"px";
   		
    }
	function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
	}
	

 function fleshShow(id){
        $("#prodClass_iframe").attr("src", '${ctx}/cmdb/prodClass/main.do?id='+id); 
   }

  function saveOK(){
          $("#prodClass_iframe").attr("src", '${ctx}/cmdb/prodClass/main.do?id='+unitId);
		}

 
</script>
</head>
<body>
	
   <div id="toptoolbar" ></div> 
	<div id="layout-index">
    <div position="left" id="tree-index" class="l-scroll l-accordion-content" title="<table><tr style='line-height: 22px;cursor: pointer;'>
				<td onclick='refreshs()'><img src='${ctx}/static/ligerUI/skins/icons/refresh.gif'/><span>刷新</span></td>
				</tr></table>" style="border:1px solid #ccc; overflow:auto; clear:both; ">
			 <ul id="vendor-tree">厂商信息</ul>
	</div>
	
	
	<div position="center" id="index-center">
			<div id="ifSummary" title="产品分类" class="whFull">
				<iframe frameborder="0" id="prodClass_iframe" src="" class="whFull"></iframe>
			</div>
	</div>
</div>
</body>
</html>
