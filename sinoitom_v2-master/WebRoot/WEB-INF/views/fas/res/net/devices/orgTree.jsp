<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${s:paramVal("SystemName")}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/definedBar.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>

<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

<script src="${ctx}/static/jquery/jquery-1.8.3.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
<style type="text/css"> 
        .l-tree .l-tree-icon-none img{width:16px; height:16px; margin:3px;}
</style>
<script type="text/javascript">
	var tab = null;
	var accordion = null;
	var tree = null;
	var layout = null;
	var id = "";
	var orgStruct="";
	var manager = null;
	 var checkBox=false;
	$(function() {
	  initDivHeight();//初始化div高度
		layout = $("#layout-index").ligerLayout({leftWidth : 200,space : 2,isLeftCollapse : false});
		 $("#user-tree").ligerTree({
       checkbox: false,
       slide: true,
       onCheck:onCheck,
       textFieldName: 'orgShortName', 
       nodeWidth:150,
       onSelect : function(node) {
				id = node.data.id;
				fleshShow(id);
			}
    });   
		
	
		manager = $("#user-tree").ligerGetTreeManager();
		fleshShow(id);
		getTreeData();
        window.onresize=function(){
        	initDivHeight();
		}
		$("#checkOrg").val(checkBox);
	});
	
	
	 function getTreeData(){
		 	manager.loading.show();//显示loading
		 	var url =  timeURL('${ctx}/system/organ/getSubOrg.do');
			$.ajax({
				url : url,
				data : "date=" + new Date(),
				dataType : 'json',
				success : function(data) {
					//替换#，给图标路径重新赋值
					var jsonToStr=JSON.stringify(data);
					jsonToStr=jsonToStr.replace(/\#/g,"${ctx}")
					var strToJson=JSON.parse(jsonToStr);
					manager.clear();
					manager.setData(strToJson);
            		manager.loading.hide();
				}
			});
		 }
	function fleshShow(id) {
	   var checkBox;
    	if($("#chargeRatio").attr('checked')==undefined){
    	 checkBox=false;
    	}else{
    	 checkBox=true;
    	}
      $("#user_iframe").attr("src",'${ctx}/fas/res/net/devices/main.do?orgId='+ id+'&checkBox='+checkBox+'&menuId='+'${menuId}'+'&devType=2');

	}
	
	
		  
	   function refresh(){
		    window.location.reload();
			return false;
		 }
			 


	 function saveOK(action, id){
		window.getActWin().saveOK(action, id); 
	} 

	
	 function initDivHeight(){
   		var wh=windowHeight();
   		document.getElementById("tree-index").style.height = (wh-28)+"px";
    }
	function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
	}
	
	 function saveOK(action, data){
       		var editor = document.getElementById("user_iframe").contentWindow;
			editor.saveOK(action, data); 
		} 
		
 function getKeyValChk()
        {
       		var orgIds="";
	        var ids=new Array();
	        var notes = manager.getChecked();
	        if(notes != null && notes.length>0){
 		         for(var j=0;j<notes.length;j++){
 		          var fid=notes[j].data.id;
 		               	ids.push(fid);
		           }
	        }
	        for(var i=0,n=ids.length;i<n;i++){
	         if(i!=n-1){
	           orgIds+=ids[i]+";";
	         }else{
	           orgIds+=ids[i]+"";
	         }
	        }
	        return orgIds;
        }
        
	  function onCheck(note){
	       var ids=getKeyValChk();
	       $("#id").val(ids);
	  }	
	  
	  function checkedBox(){

    	if($("#chargeRatio").attr('checked')==undefined){
    	 checkBox=false;
    	}else{
    	 checkBox=true;
    	}
    	$("#checkOrg").val(checkBox);
}		
</script>
</head>
<body>
<form name="sysForm" id="sysForm" >
<input type="hidden" name="id" id="id" value="${id}"/>
<input type="hidden" name="checkOrg" id="checkOrg" value=""/>
</form>
	<div id="layout-index" style="float:left;">
		<div position="left"  id="tree-index" class="l-scroll l-accordion-content" style="border:1px solid #ccc; overflow:auto; clear:both;" title="<table><tr style='line-height: 22px;'>
				<td></td><td><span>机构信息</span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			    <TD align=left>
					<INPUT type='checkbox' id='chargeRatio' name='chargeRatio' onclick='checkedBox()'/>
				</TD>
				<TD align=left >&nbsp;包括下属机构</TD>
				</tr></table>" >
	       <ul id="user-tree">机构信息</ul>
	    </div>
	    
	    
		<div position="center" id="index-center" >
			<div id="ifSummary" title="机构信息" class="whFull" >
				<iframe frameborder="0" id="user_iframe" src="#"
					class="whFull"></iframe>
			</div>
			
		</div>

	</div>
</body>
</html>
