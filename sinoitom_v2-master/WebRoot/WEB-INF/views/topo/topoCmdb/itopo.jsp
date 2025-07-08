<!DOCTYPE HTML>
<%@page import="com.sino.base.common.Global"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>业务关系图</title>

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
<%-- <script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> --%>


<style type="text/css">
.right li a{
	cursor: pointer;
}
    
</style>


</head>
</html>

</HEAD>
<BODY oncontextmenu="return false" style="overflow: hidden;">
<!-- 修改节点ip的框框 -->
	<textarea id="jtopo_textfield" style="display:none;width: 100px; position: absolute;" onkeydown="if(event.keyCode==13)this.blur();" onchange="textNodeChange()"></textarea>
<ul id="contextMenu" style="display: none;" class="right">
    <li><a>关联实体资源</a></li>
    <li><a>影响范围</a></li>
    <li><a>删除节点</a></li>
</ul>
<ul id="linkmenu" style="display: none;" class="right">
<!-- 	<li><a>查看线路信息</a></li> -->
	<li><a>修改影响系数</a></li>
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
<div id="relateMesg" class="mesgRelate">
</div>
<div id="lineMesg"></div>
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
				
				<div id="trTxt" class="dN" style="margin-top:300px;margin-left:650px;position:absolute;z-index: 9999">
	           		<span id="searching">
	           			<img src="${ctx}/img/loading2.gif" alt="#" />&nbsp;&nbsp;
	           		</span>
               </div>
			
					<DIV id="content" style="margin-left:240px;width:1366px">
						<canvas ondrop="drop(event)" ondragover="allowDrop(event)" width="1366" height="768"  id="canvas"></canvas>
					</DIV>
			</DIV>
		</CENTER>
		
		
		<div id="selected"></div>
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

<script src="${ctx}/static/itopo/js/itopo.js"></script>
<script src="${ctx}/static/itopo/js/my.js"></script>
<script src="${ctx}/static/itopo/js/JToolbar_Cmdb.js" type="text/javascript"></script>
<script src="${ctx}/static/qunee/js/svgIcon.js"></script>

<script type="text/javascript">
    var graph,nodes,links,trunkMap;
    var xgap=110;
	  var ygap=0;
	   var rootNodeX=100;
 	   var rootNodeY=50;
 	     var levelCount; //每层节点个数
	   var pNode;
	   var graphObj;
	   var unilines;
	   var levelNodes;
	   var depth;
	   var aloneNodeIds=[];  //自动加载时，孤立节点id数组
 	    var nodeIds=[];//
 	   
 	   
    var nodeImgWidth=60;
    var nodeImgHeight=60;
    var svgImgPath="<%=Global.LOAD_DEV_IMG_URL%>";
    var graphID='${topoGraph.graphId}';
    var currentNode = null;
    var leftwidth=parseInt($("#content").css("margin-left"))=="undefind"?0:parseInt($("#content").css("margin-left"));
                    
    var topheight;
    $(function(){
	    //加载操作栏
		showJTopoToobar(null,'${ctx}');		
		graph =new Graph("canvas");
		topheight=$(".jtopo_toolbar").height()=="undefind"?0:$(".jtopo_toolbar").height()
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
				var icon = $('<img style="margin:5px;width:32px;height:32px;cursor:pointer"  objType="'+objType[1]+'" title="'+objTypeName+'" src="<%=Global.LOAD_DEV_IMG_URL%>' + objicon + '" draggable="true" ondragstart="drag(event)" image="<%=Global.LOAD_DEV_IMG_URL%>' + objicon + '" type="Node" label="'+objTypeName+'">');
				
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
		

	   loadTopo(graphID);
// 		getAutoData();
// 		autoLoad('1574220973882');
 
	 
	  graph.canvas.onclick=function (e){
	  }
	 
	 //修改节点信息
     var obj;
    var textfield = $("#jtopo_textfield");
    graph.canvas.ondblclick=function(event){
        obj=graph.getElementByMouseEvent(event);
        ////alert(obj.textId);
        if(obj == null) return;
   
   		if(obj.elementType=="TextNode"){
   		   textfield.css({
            top: event.pageY,
            left: event.pageX
       	 	}).show().attr('value', obj.name).focus().select(); 
   		}
          
    };
    
    graph.canvas.onclick=function(event){

        var obj=graph.getElementByMouseEvent(event);
         if(obj){
            if(obj.elementType=="TextRank"&&graph.isDblText){
              if(obj == null) return;
   
                textfield.css({
                    width:"200px",
                    height:"50px",
                    top: event.pageY,
                    left: event.pageX,
                    textAlign:"left"
                }).show().attr('value', obj.name).focus().select();
            
                textfield[0].text = obj;
            }else if(obj.elementType=="TextVertical"&&graph.isDblText){
              if(obj == null) return;
   
                textfield.css({
                    width:"20px",
                    height:"300px",
                    top: event.pageY,
                    left: event.pageX,
                    textAlign:"center"
                }).show().attr('value', obj.name).focus().select();
            
                textfield[0].text = obj;
            }
         }
        textfield[0].value = "";
    }	 
    
  $("#jtopo_textfield").blur(function(event){
    	var val= textfield.hide().val();
		if(val!=null && val!=undefined && val!=''){
		
            var saveNodes=[];
            var nodeObj = new Object();
			var node=getNodeByTextNodeId(obj.textId);
			nodeObj.node_Name=val;
			nodeObj.node_ID=node.id;
			if(node.nodeInfo!=null){
			   nodeObj.nodeInfo=node.nodeInfo;
			}
			nodeObj.posX=node.x;
			nodeObj.posY=node.y;
			nodeObj.namePosX=obj.x;
			nodeObj.namePosY=obj.y;
			nodeObj.topoType="node";
            saveNodes.push(nodeObj);
            var nodeStr = JSON.stringify(saveNodes);
			var params ={'nodeStr':nodeStr,'graphId':graphID};
			$.ajax({
				url : "${ctx}/cmdb/graph/saveNode.do",
				type:'post',         //数据发送方式
				dataType:'json',     //接受数据格式
				data:params,         //要传递的数据
				success:function(data) {
				if(data.result=='success')
					window.parent.$.ligerDialog.success("保存成功");
					location.reload();
				}
			});
			
		}else {//if(obj.elementType=="LAN"||obj.elementType=="LanNode")
			alert("结点名称不能空！");
		}
   });  
		
});
 var scrollFunc = function (e) {  
        e = e || window.event;  
        if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件               
            if (e.wheelDelta > 0) { //当滑轮向上滚动时  
                      graph.childs.forEach(function(e){
                	if(e.elementType=="Node"){
                		e.x*=graph.scaleX;
	                	e.y*=graph.scaleY;
	                	e.width*=graph.scaleX;
	                	e.height*=graph.scaleY;
	                	e.updateNode(e);
                	}else if(e.elementType=="TextNode"){
		  				e.x*=graph.scaleX;
		  				e.y*=graph.scaleX;
		  				e.x1*=graph.scaleX;
		  				e.y1*=graph.scaleX;
		  				e.size*=graph.scaleX;
		  				e.updateNode(e);
	  				}else if(e.elementType=="RelateLine"){
                    	e.x*=graph.scaleX;
                    	e.y*=graph.scaleY;
                    	//e.size*=graph.scaleX;
                    	e.updateRelateLine(e);
                	}else if(e.elementType=="Edge"){
  						e.size*=graph.scaleX;
                    	e.dist*=graph.scaleX;
  					}else if(e.elementType=="LAN"){
                    	e.x1*=graph.scaleX;
                    	e.y1*=graph.scaleY;
                    	e.x2*=graph.scaleX;
                    	e.y2*=graph.scaleY;
                    	e.updateLan(e);
                	}else if(e.elementType=="LanNode"){
                    	e.x1*=graph.scaleX;
                    	e.y1*=graph.scaleY;
                    	e.x2*=graph.scaleX;
                    	e.y2*=graph.scaleY;
                    	e.updateLanNode(e);
                	}else if(e.elementType=="EdgeBroken"){
                    e.x1*=graph.scaleX;
                    e.y1*=graph.scaleY;
                    e.x2*=graph.scaleX;
                    e.y2*=graph.scaleY;
                    e.x3*=graph.scaleX;
                    e.y3*=graph.scaleY;
                    e.x4*=graph.scaleX;
                    e.y4*=graph.scaleY;
                    e.size*=graph.scaleX;
                    e.updateBrokenEdge(e);
                }
                	
                });
            }  
            if (e.wheelDelta < 0) { //当滑轮向下滚动时  
             	 graph.childs.forEach(function(e){
                	if(e.elementType=="Node"){
                		e.x/=graph.scaleX;
	                	e.y/=graph.scaleY;
	                	e.width/=graph.scaleX;
	                	e.height/=graph.scaleY;
	                	e.updateNode(e);
                	}else if(e.elementType=="TextNode"){
		  				e.x/=graph.scaleX;
		  				e.y/=graph.scaleX;
		  				e.x1/=graph.scaleX;
		  				e.y1/=graph.scaleX;
		  				e.size/=graph.scaleX;
		  				e.updateNode(e);
	  				}else if(e.elementType=="RelateLine"){
                    	e.x/=graph.scaleX;
                    	e.y/=graph.scaleY;
                    	e.size/=graph.scaleX;
                    	e.updateRelateLine(e);
                	}else if(e.elementType=="Edge"){
  						e.size/=graph.scaleX;
                    	e.dist/=graph.scaleX;
  					}else if(e.elementType=="LAN"){
                    	e.x1/=graph.scaleX;
                    	e.y1/=graph.scaleY;
                    	e.x2/=graph.scaleX;
                    	e.y2/=graph.scaleY;
                    	e.updateLan(e);
                	}else if(e.elementType=="LanNode"){
                    	e.x1/=graph.scaleX;
                    	e.y1/=graph.scaleY;
                    	e.x2/=graph.scaleX;
                    	e.y2/=graph.scaleY;
                    	e.updateLanNode(e);
                	}else if(e.elementType=="EdgeBroken"){
                    e.x1/=graph.scaleX;
                    e.y1/=graph.scaleY;
                    e.x2/=graph.scaleX;
                    e.y2/=graph.scaleY;
                    e.x3/=graph.scaleX;
                    e.y3/=graph.scaleY;
                    e.x4/=graph.scaleX;
                    e.y4/=graph.scaleY;
                    e.size/=graph.scaleX;
                    e.updateBrokenEdge(e);
                }
                });
            }  
        } else if (e.detail) {  //Firefox滑轮事件  
            if (e.detail> 0) { //当滑轮向上滚动时  
                graph.childs.forEach(function(e){
                	if(e.elementType=="Node"){
                		e.x*=graph.scaleX;
	                	e.y*=graph.scaleY;
	                	e.width*=graph.scaleX;
	                	e.height*=graph.scaleY;
	                	e.updateNode(e);
                	}else if(e.elementType=="TextNode"){
		  				e.x*=graph.scaleX;
		  				e.y*=graph.scaleX;
		  				e.x1*=graph.scaleX;
		  				e.y1*=graph.scaleX;
		  				e.size*=graph.scaleX;
		  				e.updateNode(e);
	  				}else if(e.elementType=="RelateLine"){
                    	e.x*=graph.scaleX;
                    	e.y*=graph.scaleY;
                    	e.size*=graph.scaleX;
                    	e.updateRelateLine(e);
                	}else if(e.elementType=="Edge"){
  						e.size*=graph.scaleX;
                    	e.dist*=graph.scaleX;
  					}else if(e.elementType=="LAN"){
                    	e.x1*=graph.scaleX;
                    	e.y1*=graph.scaleY;
                    	e.x2*=graph.scaleX;
                    	e.y2*=graph.scaleY;
                    	e.updateLan(e);
                	}else if(e.elementType=="LanNode"){
                    	e.x1*=graph.scaleX;
                    	e.y1*=graph.scaleY;
                    	e.x2*=graph.scaleX;
                    	e.y2*=graph.scaleY;
                    	e.updateLanNode(e);
                	}else if(e.elementType=="EdgeBroken"){
                    e.x1*=graph.scaleX;
                    e.y1*=graph.scaleY;
                    e.x2*=graph.scaleX;
                    e.y2*=graph.scaleY;
                    e.x3*=graph.scaleX;
                    e.y3*=graph.scaleY;
                    e.x4*=graph.scaleX;
                    e.y4*=graph.scaleY;
                    e.size*=graph.scaleX;
                    e.updateBrokenEdge(e);
                }
                });
            }  
            if (e.detail< 0) { //当滑轮向下滚动时  

                  graph.childs.forEach(function(e){
                	if(e.elementType=="Node"){
                		e.x/=graph.scaleX;
	                	e.y/=graph.scaleY;
	                	e.width/=graph.scaleX;
	                	e.height/=graph.scaleY;
	                	e.updateNode(e);
                	}else if(e.elementType=="TextNode"){
		  				e.x/=graph.scaleX;
		  				e.y/=graph.scaleX;
		  				e.x1/=graph.scaleX;
		  				e.y1/=graph.scaleX;
		  				e.size/=graph.scaleX;
		  				e.updateNode(e);
	  				}else if(e.elementType=="RelateLine"){
                    	e.x/=graph.scaleX;
                    	e.y/=graph.scaleY;
                    	e.size/=graph.scaleX;
                    	e.updateRelateLine(e);
                	}else if(e.elementType=="Edge"){
  						e.size/=graph.scaleX;
                    	e.dist/=graph.scaleX;
  					}else if(e.elementType=="LAN"){
                    	e.x1/=graph.scaleX;
                    	e.y1/=graph.scaleY;
                    	e.x2/=graph.scaleX;
                    	e.y2/=graph.scaleY;
                    	e.updateLan(e);
                	}else if(e.elementType=="LanNode"){
                    	e.x1/=graph.scaleX;
                    	e.y1/=graph.scaleY;
                    	e.x2/=graph.scaleX;
                    	e.y2/=graph.scaleY;
                    	e.updateLanNode(e);
                	}else if(e.elementType=="EdgeBroken"){
                    e.x1/=graph.scaleX;
                    e.y1/=graph.scaleY;
                    e.x2/=graph.scaleX;
                    e.y2/=graph.scaleY;
                    e.x3/=graph.scaleX;
                    e.y3/=graph.scaleY;
                    e.x4/=graph.scaleX;
                    e.y4/=graph.scaleY;
                    e.size/=graph.scaleX;
                    e.updateBrokenEdge(e);
                }
                });
            }  
        }  

        graph.drawData();

    }  
    //给页面绑定滑轮滚动事件  
    if (document.addEventListener) {//firefox  
        document.addEventListener('DOMMouseScroll', scrollFunc, false);  
    }  
    //滚动滑轮触发scrollFunc方法  //ie 谷歌  
    window.onmousewheel = document.onmousewheel = scrollFunc; 
	

	function textNodeChange(){}
	
	  //递归加载子节点
  var nodeIds=[];//
  function loadSubNodes(pNodeId,level){
     
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
//  	      ygap+=parenttopo.y;//xgap*i,(rootNodeY+ygap)
 	      
//  	    }
// 			else if(levelCount>1){
//  	      if(levelCount%2==0){ //偶数个节点
 	        
//  	      }else{  //奇数个节点
 	        
//  	      }
//  	    }
		 
		  
		 if(!findNodeById(subNodeIds[i])){
		       nodeIds.push(subNodeIds[i]);
		       var topoSubNode=createNode(subnode,rootNodeX+(xgap*i),(rootNodeY+ygap));
		       aloneNodeIds.remove(subnode.id);
 	   		   var parenttopo=getNodeById(pNode.id);
 	            createText(topoSubNode, subnode.resName, rootNodeX+(xgap*i), (rootNodeY+ygap+60));
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
	   if(nodeid==nodes[i].id){
	      return nodes[i];
	   }
	  }
	}

	//获取subNodeid集
	function getSubNodeIds(pnodeId){
	  var subNodeIds=new Array();
	 	 for(var i=0;i<unilines.length;i++){
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
</script>
