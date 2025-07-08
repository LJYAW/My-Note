<!DOCTYPE HTML>
<%@page import="com.sino.base.common.Global"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>拓扑管理</title>

  <link href="${ctx}/static/bootstrap/style/bootstrap.css" rel="stylesheet">
  <link rel="stylesheet" href="${ctx}/static/bootstrap/style/font-awesome.css"> 
  
  <!-- jQuery UI -->
  <link href="${ctx}/static/bootstrap/style/style.css" rel="stylesheet">
  <LINK href="${ctx}/static/jTopo/css/base.css" rel="stylesheet" type="text/css">
  <LINK href="${ctx}/static/jTopo/css/jquery.snippet.min.css" rel="stylesheet">
  
  <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
  
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
<script src="${ctx}/js/index.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
<script src="${ctx}/js/dataFormat.js" type="text/javascript"></script>
<%-- <script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> --%>


<style type="text/css">
    
</style>


</head>
</html>

</HEAD>
<BODY oncontextmenu="return false" style="overflow: hidden;">
	<textarea id="jtopo_textfield" style="display:none;width: 60px; position: absolute;" onkeydown="if(event.keyCode==13)this.blur();" onchange="textNodeChange()"></textarea>
<ul id="contextmenu" style="display: none;" class="right">
	<li><a>查看设备信息</a></li>
	<li><a>修改设备信息</a></li>
	<li><a>查看设备接口</a></li>
	<li><a>查看性能指标</a></li>
	<li><a>查看设备告警</a></li>
    <li><a>删除该节点</a></li>
	<li id="delDev"><a>删除该设备</a></li>
	
</ul>
<ul id="linkmenu" style="display: none;" class="right">
	<li><a>查看线路信息</a></li>
	<li><a>编辑线路信息</a></li>
	<li><a>删除连线</a></li>
</ul>
<ul id="subNetmenu" style="display: none;" class="right">
	<li><a>查看子拓扑图</a></li>
	<li><a>编辑子拓扑图</a></li>
	<li><a>删除子拓扑图</a></li>
</ul>
<ul id="textmenu" style="display: none;">
	<li><a>删除Text</a></li>
</ul>
<form name="topoForm" id="topoForm" action="${ctx}/topo/graph/save.do" method="post">
	<input type="hidden" id="action" name="action" value="${action}"/>
	<input type="hidden" name="disMode" id="disMode" value="${disMode }" /> 
	<input type="hidden" id="jsonStr" name="jsonStr" value=""/>
	<input type="hidden" id="jsonLineStr" name="jsonLineStr" value=""/>
	
	<input type="hidden" id="tempNodes" name="tempNodes" value=""/>
	<input type="hidden" id="tempLinks" name="tempLinks" value=""/>
	<input type="hidden" id="selectMode" name="selectMode" value=""/>
	<input type="hidden" id="bgpicture" name="bgpicture" value=""/>
</form>
		<CENTER style='height:100%'>
		
			<DIV class="content" id="contentMain" style='height:100%'>
			
			 <DIV class="left" id="leftMenu"> 
					操作：将选中图标拖拽到右侧画布。
					<div class="sidebar">
				        <ul id="nav">
				          <c:forEach items="${plist }" var="p" varStatus="status">
				             <li class="has_sub"><a href="#"><i class="icon-screenshot"></i>${p.text }<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
				          		<ul id="${p.id }">
				           		</ul>
				             </li> 
				          </c:forEach>
				        </ul>
					</div>
				</DIV>
			
			
<!-- 				<DIV class="right"> -->
					<DIV id="content" style="margin-left:240px;height:100%;">
						<div ondrop="drop(event)" ondragover="allowDrop(event)" style="height:100%;width:100%;border: 1px solid #ccc;" id="canvas"></div>
					</DIV>
<!-- 				</DIV> -->
			</DIV>
		</CENTER>
		
		
	<div class="topology" id="topology">
	    <div class="topology_center">
	        <p class="topology_center_title"><b>关联拓扑图</b></p>
	        <div class="topology_center_select">
	            		拓扑图名称：
	             <select id="graphName"></select>
	        </div>
	        <a class="topology_center_submit topology_center_close" onclick="saveSubnet()">确定</a>
	        <a class="topology_center_off topology_center_close">X</a>
	
	    </div>
	</div>	
		
	</BODY>
</HTML>

	
	
<script src="${ctx}/static/jTopo/jquery.snippet.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jTopo/toolbar.js" type="text/javascript"></script>
<script src="${ctx}/static/json2.js" type="text/javascript"></script>
<script src="${ctx}/static/jTopo/topo.js" type="text/javascript"></script>


<script src="${ctx}/static/qunee/js/my.js"></script>

<script src="${ctx}/static/qunee/js/qunee-min-0912.js"></script>
 <script src="${ctx}/static/qunee/js/svgIcon.js"></script>
<script src="${ctx}/static/qunee/editor/libs/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/qunee/editor/libs/layout.border.js"></script>
<script src="${ctx}/static/qunee/editor/libs/bootstrap-colorpicker/js/bootstrap-colorpicker.min.js"></script>

<script src="${ctx}/static/qunee/js/FileSupport.js"></script>
<script src="${ctx}/static/qunee/js/ExportPane.js"></script>
<script src="${ctx}/static/qunee/js/graph.editor.js"></script>
<script src="${ctx}/static/qunee/js/image-background.js"></script>

	<script src="${ctx}/static/qunee/js/JToolbar.js" type="text/javascript"></script>
	<script type="text/javascript">
    var canvas,graph,nodes,links;
     var xgap=110;
	   var ygap=0;
	  var rootNodeX=0;
 	   var rootNodeY=-200;
	   var levelCount; //每层节点个数
	   var pNode;
	   var graphObj;
	   var unilines;
	   var levelNodes;
// 	   var nodes;
	   var depth;
	   var aloneNodeIds=[];  //自动加载时，孤立节点id数组
       var graphID='${topoGraph.graphId}';
    var currentNode = null;
    var subnetStr;
    var nodeImgWidth=64;
    var nodeImgHeight;
     var timer;
     var alarmType="show";
    $(function(){
	
		showJTopoToobar(null,'${ctx}');			
		canvas = document.getElementById('canvas'); 
		
		 	// 左侧图标列表
		var currTile;
		var codestr="${codesb}";
		var code_list=codestr.split(",");
		var imgs="${imgsb}";
		var img_list=imgs.split(",");
		
		for(var k=0;k<code_list.length;k++){
		      var code=code_list[k];
			  
			  for(var i = 0; i< img_list.length; i++){
				var img=img_list[i];
				var objType=img.split("|");
				var objicon=objType[0].split("-")[0];
				var objcode=objType[0].split("-")[1];
				var objTypeName='';
				if(objType[1]){
				   objTypeName=objType[1].split("#")[3];
				}
				
				var icon = $('<img style="margin:5px;width:16px;height:16px;cursor:pointer"  objType="'+objType[1]+'" title="'+objTypeName+'" src="<%=Global.LOAD_DEV_IMG_URL%>' + objicon + '" draggable="true" ondragstart="drag(event)" image="<%=Global.LOAD_DEV_IMG_URL%>' + objicon + '" type="Node" label="'+objTypeName+'">');
				
				if(code==objcode){
				  $("#"+code).append(icon);
				}
				icon.attr('tileId', objicon);
				icon.attr('objType',objType[1]);
				icon.attr('objTypeName',objTypeName);
				icon.click(function(){
					currTile = $(this);
					flag=true;
				});
				
			}
		  
		}
		
		 graph = new Q.Graph("canvas");
		 Q.Defaults.LABEL_EDITOR_SUBMIT_WHEN_LOST_FOCUS = true;// 文本输入框失去焦点时是否提交编辑内容	
	     Q.DefaultStyles[Q.Styles.ARROW_TO] = false;
		 Q.DefaultStyles[Q.Styles.SELECTION_COLOR]= "#FF0000";
		 
		 if('${topoGraph.bgPicture}'!=null&&'${topoGraph.bgPicture}'!="null"&&'${topoGraph.bgPicture}'!=""){
		   var image=new Image();
		   image.src="<%=Global.LOAD_BG_IMG_URL%>"+'${topoGraph.bgPicture}';
// 		      image.src="${ctx}/icon/"+'${topoGraph.bgPicture}';
		   if ('${topoGraph.status}' == '0') {
		 
		   		rootNodeX=image.width/2;
          		rootNodeY= ygap + 30;
		   }           
         
          $("#bgpicture").val('${topoGraph.bgPicture}');
           Q.Graph.prototype.originAtCenter =false;   //设置左上角为坐标圆点
           
           var background = new ImageBackground(graph, {
		    onload: function () {
// 		        graph.limitedBounds = background.bounds;
// 		        graph.zoomToOverview();
// 				graph.moveToCenter();
		    }
		});
		  background.image="<%=Global.LOAD_BG_IMG_URL%>"+'${topoGraph.bgPicture}';
		 showCenter(graph);
		 
       }
		 
		//添加事件
	    graph.onclick=function (e){
		}
		
		//监听创建连线事件
		graph.interactionDispatcher.addListener(function (evt) {
        if (evt.kind == Q.InteractionEvent.ELEMENT_CREATED) {// 元件创建事件
            var edge = evt.data;
            if(edge.type=="Q.Edge"){
               edge.name="line";
            }
        }
        
   		});
		
		var bodyHeight=$(window).get(0).innerHeight-46;
		$("#canvas").css("height",bodyHeight+"px");
		//绑定刷新
		$("#refreshTopo").click(function () {
			 location.reload();
// 		      loadTopo();
		      if( $("#bgpicture").val().length>0){
		        showCenter(graph);
		      }else{
		         graph.moveToCenter();
		      }
			  
	    });
			 //绑定保存
			 $("#saveTopo").click(function () {
			  var saveNodes=[];
			  var saveTexts=[];
			  var saveLinks=[];
			  var sceneObj=new Object();
			  sceneObj.graphId=graphID;
			  var saveGraph=JSON.stringify(sceneObj);
			 
				graph.graphModel.forEach(
			      function(node){
			        if(node.type=="Q.Node"){
			            var nodeObj = new Object();
			            var textNode=retTextNodeByNode(node.nodeId);
			            nodeObj.node_Name=textNode.name;
			            nodeObj.node_ID=node.nodeId;
			             if(node.nodeInfo!=null){
			               nodeObj.nodeInfo=node.nodeInfo;
			            }
			          	nodeObj.posX=node.x;
						nodeObj.posY=node.y;
						nodeObj.namePosX=textNode.x;
						nodeObj.namePosY=textNode.y;
			            saveNodes.push(nodeObj);
			        }else if(node.type=="Q.Edge"){
// 			           alert(node.from);
			           var lineObj = new Object();
			           lineObj.nodeAId=node.from.nodeId;
			           lineObj.nodeAName=node.from.name;
			           lineObj.nodeBId=node.to.nodeId;
			           lineObj.nodeBName=node.to.name;
			           lineObj.lineName=node.name;
			           if(node.lineId!=null){
			             lineObj.linkID=node.lineId;
			           }
			           
			           saveLinks.push(lineObj);
			        }
			        
			      }
			    );
			    var nodesStr = JSON.stringify(saveNodes);
			   	var linksStr = JSON.stringify(saveLinks);
			   	var jsonGraph = JSON.stringify(sceneObj);
			   	var params ={'nodesStr':nodesStr,'linksStr':linksStr,'graphStr':saveGraph};
			   	$.ajax({
							   url : '${ctx}/topo/graph/saveTopo.do',
						       type:'post',         //数据发送方式
						       dataType:'json',     //接受数据格式
						       data:params,         //要传递的数据
						       success:function(data) {
						       		if(data.result=='success')
										window.parent.$.ligerDialog.success("保存成功");
								 }
						 });
			   	
			 });
		 
		 //选中所有
// 		 graph.selectAll();

		  //右键菜单
	    graph.oncontextmenu=function (e){
	 		var element=graph.getElement(e);
	 		  var bodyHeight=$(window).get(0).innerHeight;
			  var bodyWidth=$(window).get(0).innerWidth;
	 		if(element!=null&&element.type=="Q.Node"&&element.objType==1){  //节点
	 		    var divHeight=$("#contextmenu").height();
				var divWidth=$("#contextmenu").width();
				var diffH=0;
				var diffW=10;
				if(bodyHeight-e.pageY<divHeight){
				  diffH=divHeight-(bodyHeight-e.pageY);
				}
				
				if(bodyWidth-e.pageX<divWidth){
				  diffW+=divWidth-(bodyWidth-e.pageX);
				}
				
	 		    currentNode=element;
				$("#contextmenu").css({
					top: e.pageY-diffH,
					left: e.pageX-diffW,
				}).show();	
	 		}
	 		 if(element!=null&&element.type=="Q.Node"&&element.objType==0){//子网
	 		    var divHeight=$("#subNetmenu").height();
				var divWidth=$("#subNetmenu").width();
				
				var diffH=0;
				var diffW=10;
				if(bodyHeight-e.pageY<divHeight){
				  diffH=divHeight-(bodyHeight-e.pageY);
				}
				
				if(bodyWidth-e.pageX<divWidth){
				  diffW+=divWidth-(bodyWidth-e.pageX);
				}
				
	 		    currentNode=element;
				$("#subNetmenu").css({
					top: e.pageY-diffH,
					left: e.pageX-diffW,
				}).show();
	 		}
	 		
	 		if(element!=null&&element.type=="Q.Text"){
// 	 		  alert(element.type);
	 		}
	 		
	 		if(element!=null&&element.type=="Q.Edge"){
	 		
	 		  var divHeight=$("#linkmenu").height();
				var divWidth=$("#linkmenu").width();
				var diffH=0;
				var diffW=10;
				if(bodyHeight-e.pageY<divHeight){
				  diffH=divHeight-(bodyHeight-e.pageY);
				}
				
				if(bodyWidth-e.pageX<divWidth){
				  diffW+=divWidth-(bodyWidth-e.pageX);
				}
	 		  
	 		  currentNode=element;
	 		 $("#linkmenu").css({
					top: e.pageY-diffH,
					left: e.pageX-diffW,
				}).show();	
	 		}
		}
		
		//左键隐藏菜单
		graph.onmouseup=function(e){
		 if(e.button==0){//左键
		    $("#contextmenu").hide();
		    $("#linkmenu").hide();	
		    $("#subNetmenu").hide();			
		  }
		}
		
		if('${topoGraph.status}'=="0"){
		getAutoData();
		  autoLoad('${topoGraph.seedNodeId}');
		}else{
// 		  loadTopo();
		  loadTopoByGraphID(graphID);
		 if( $("#bgpicture").val().length>0){
		        showCenter(graph);
		      }else{
		         graph.moveToCenter();
		      }
		}
// 		loadTopo();
// 		graph.moveToCenter();
		
		graph.ondrop=function(e){   //拖拽事件支持  忽删
		  
		}
		
		
		//line 捆绑事件
		graph.propertyChangeDispatcher.addListener(function(evt){
        if(evt.propertyName == 'agentEdge' && evt.source instanceof Q.EdgeBundle){
            var edgeBundle = evt.source;
            if(evt.oldValue instanceof Q.Edge){
//                 alert(evt.oldProperties);
                  resetEdgeStyles(evt.oldValue);
            }
            if(evt.value instanceof Q.Edge){
//             alert(evt.value.type);
                setBundleStyles(evt.value, edgeBundle);
            }
        }
    })
		
		
		//编辑文本节点事件
		graph.onLabelEdit=function (element, label, text, elementUI) {
        if (!text && !this.allowEmptyLabel) {
            Q.alert("Label Can't Empty");
            return false;
        }
        if (label.name == "label") {
            var reg=/^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$/;
              if(element.parent.nodeInfo!=null&&element.parent.objType==1){  //获取文本节点主节点的节点信息  ,不等于null说明为新建节点
                 if(reg.test(text)==true){
                   $.ajax({
                    url:"${ctx}/topo/topoNode/checkIpAddr.do?graphId="+graphID+"&nodeName="+text,
                    dataType:'json',
                    success:function(data){
                       if(data.flag){  //说明存在
                           alert("修改后的IP已经存在，请重新填写");
                       }else{   //说明不存在
                         element.name = text;  //修改节点名字
                        var saveNodes=[];
                        var nodeObj = new Object();
			            var textNode=element;
			            var node=element.parent;
			            
			            nodeObj.node_Name=text;
			            nodeObj.node_ID=node.nodeId;
			             if(node.nodeInfo!=null){
			               nodeObj.nodeInfo=node.nodeInfo;
			            }
			          	nodeObj.posX=node.x;
						nodeObj.posY=node.y;
						nodeObj.namePosX=element.x;
						nodeObj.namePosY=element.y;
                         saveNodes.push(nodeObj);
                          var nodeStr = JSON.stringify(saveNodes);
			              var params ={'nodeStr':nodeStr,'graphId':graphID};
					   	  $.ajax({
									   url : "${ctx}/topo/topoNode/saveNode.do",
								       type:'post',         //数据发送方式
								       dataType:'json',     //接受数据格式
								       data:params,         //要传递的数据
								       success:function(data) {
								       		if(data.result=='success')
												window.parent.$.ligerDialog.success("保存成功");
												 location.reload();
										 }
								 });
                       }
                    }
                 });
                 }else{
                    alert("无效IP");
                 }
              }else{
                 
              }
        } else {
            if (elementUI._onUIEdit(label, text) === false) {
                label.data = text;
                this.invalidateElement(element);
            }
        }
      }
		
	timer = setInterval("showAlarm(alarmType)",1000);
});
    //窗口方法缩小事件
       window.onresize = function(){
		var bodyHeight=$(window).get(0).innerHeight-46;
// 		alert(bodyHeight);
		$("#canvas").css("height",bodyHeight+"px");
// 		graph.moveToCenter();
		if( $("#bgpicture").val().length>0){
		        showCenter(graph);
		      }else{
		         graph.moveToCenter();
		      }
	} 
     
	
	
  
  
   

   
  //递归加载子节点
  var nodeIds=new Array();//
  
  function loadSubNodes(pNodeId,level){
     if(level==0){//处理topo种子节点
         var parentNode=getNodeByID('${topoGraph.seedNodeId}');
         nodeIds.push('${topoGraph.seedNodeId}');
      
 	 	 var topoParentNode=createNode(parentNode,rootNodeX,rootNodeY);
 	 	 aloneNodeIds.remove(parentNode.nodeId);
 	 	 createText(topoParentNode, parentNode.nodeName, rootNodeX, rootNodeY+30);
     }
     
      var subNodeIds=getSubNodeIds(pNodeId);
     if(subNodeIds.length>0){ //通过pNode获取到了subNode
      pNode=getNodeByID(pNodeId);
      levelCount=subNodeIds.length;
      ygap+=100;
     }
     
     
      for(var i=0;i<subNodeIds.length;i++){
 	    var subnode=getNodeByID(subNodeIds[i]);
 	   
//  	    if(levelCount==1){
//  	      xgap=parenttopo.x;
//  	      ygap+=parenttopo.y;//110*i,(rootYgap+ygap)
 	      
//  	    }
// 			else if(levelCount>1){
//  	      if(levelCount%2==0){ //偶数个节点
 	        
//  	      }else{  //奇数个节点
 	        
//  	      }
//  	    }
		 
		  
		 if(!findNodeById(subNodeIds[i])){
		       nodeIds.push(subNodeIds[i]);
		       var topoSubNode=createNode(subnode,rootNodeY+(xgap*i),(rootNodeY+ygap));
		        aloneNodeIds.remove(subnode.nodeId);
 	   		   var parenttopo=graph.getElementByName(pNode.nodeName);
 	           createText(topoSubNode, subnode.nodeName, rootNodeY+(xgap*i), (rootNodeY+ygap+30));
		 }
			
 	 }
 	 if(subNodeIds.length>0){
 	   for(var i=0;i<subNodeIds.length;i++){
 	     loadSubNodes(subNodeIds[i],1);
 	   }
 	   
 	 }
  }
  	


//数组移除某个元素
Array.prototype.remove = function(val) {
var index = this.indexOf(val);
if (index > -1) {
this.splice(index, 1);
}
};	


//通过节点ID获取节点对象
function getNodeByID(nodeid){
  for(var i=0;i<nodes.length;i++){
   if(nodeid==nodes[i].nodeId){
      return nodes[i];
   }
  }
}

//获取subNodeid集
function getSubNodeIds(pnodeId){
  var subNodeIds=new Array();
 	 for(var i=0;i<unilines.length;i++){
//  	 alert(unilines[i].nodeFrom);
 	     if(pnodeId==unilines[i].nodeFrom){
 	       subNodeIds.push(unilines[i].nodeTo);
 	     }
 	 }
 	 return uniqueArray(subNodeIds);
}




  //数组去重
 function uniqueArray(data){  
   data = data || [];  
   var a = {};  
   for (var i=0; i<data.length; i++) {  
       var v = data[i];  
       if (typeof(a[v]) == 'undefined'){  
            a[v] = 1;  
       }  
   };  
   data.length=0;  
   for (var i in a){  
        data[data.length] = i;  
   }  
   return data;  
}
   

   

    
 
   
   
   
   function saveOK(){
      location.reload();
   }
   
   
  
   
   
   
    
     function showCenter(graph){
        var image=new Image();
		 image.src="<%=Global.LOAD_BG_IMG_URL%>"+'${topoGraph.bgPicture}';
			
			if (image.complete) {
		        		callback(graph,image.width,image.height);
		    } else {
		        image.onload = function () {
		            callback(graph,image.width, image.height);
		     }
	     
     		} 
     
    } 
     function callback(graph,width,height){
        graph.centerTo(width/2,height/2); 
     }
    
</script>
