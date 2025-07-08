// 页面工具栏
function showJTopoToobar(stage){
	var toobarDiv = $('<div class="jtopo_toolbar">').html(''
		+'<input type="radio" name="modeRadio" value="normal" checked id="r1"/>'
		+'<label for="r1"> 默认</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="select" id="r2"/><label for="r2"> 框选</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="drag" id="r3"/><label for="r3"> 平移</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="edit" id="r4"/><label for="r4"> 编辑</label>'
		+'&nbsp;&nbsp;<input class="btn btn-default" style="padding: 6px 8px 3px;" type="button" id="centerButton" value="居中显示"/>'
		+'<input class="btn btn-default" style="padding: 6px 8px 3px;" type="button" id="fullScreenButton" value="全屏显示"/>'
		+'<input class="btn btn-default"  style="padding: 6px 8px 3px;" type="button" id="zoomOutButton" value=" 放 大 " />'
		+'<input  class="btn btn-default" style="padding: 6px 8px 3px;" type="button" id="zoomInButton" value=" 缩 小 " />'
		+'&nbsp;<input type="checkbox" id="zoomCheckbox"/><label for="zoomCheckbox">鼠标缩放</label>'
		+'&nbsp;<input class="form-control" style="width:100px;height:26px;display:inline;padding:0px;" type="text" id="findText" value="" onkeydown="findButton.click()">'
		+'&nbsp;<input class="btn btn-default"  style="padding: 6px 8px 3px;" type="button" id="findButton" value=" 查 询 ">'
		+'&nbsp;<input class="btn btn-default"  style="padding: 6px 8px 3px;" type="button" id="exportButton" value="导出PNG">'
		
		//自定义扩展的事件方法
		+'&nbsp;<input class="btn btn-default" style="padding: 6px 8px 3px;" type="button" value="保 存" name="saveTopo" id="saveTopo">'
		+'&nbsp;<input class="btn btn-default" style="padding: 6px 8px 3px;" type="button" value="刷 新" name="refreshTopo" id="refreshTopo">'
		+'&nbsp;<input class="btn btn-default" style="padding: 6px 8px 3px;" type="button" value="测试" name="testSubmit" id="testSubmit" onclick="getData()">'
		+'&nbsp;<select onChange="changeLayout()" id="layoutTopo" name="layoutTopo" class="form-control" style="width:75px;height:28px;display:inline;padding:0px;font-size:12px;">'
		+'<option value="default">默认布局</option>'
		+'<option value="circle">圆形布局</option>'
		+'<option value="tree">树形布局</option>'
		+'<option value="tree_circle">混合布局</option>'
		+'</select>'
		
//		+'&nbsp;&nbsp;<select onChange="changeAlign()" id="align" name="align" class="form-control" style="width:80px;height:28px;display:inline;padding:0px;">'
//		+'<option value="align_left">左对齐</option>'
//		+'<option value="align_right">右对齐</option>'
//		+'<option value="align_top">上对齐</option>'
//		+'<option value="align_bottom">下对齐</option>'
//		+'</select>'
		
		
		+'&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../icon/alignleft.png" style="cursor:pointer" title="左对齐" onclick="changeAlign(1)">'
		+'&nbsp;&nbsp;<img src="../../icon/alignright.png" style="cursor:pointer" title="右对齐" onclick="changeAlign(2)">'
		+'&nbsp;&nbsp;<img src="../../icon/aligntop.png" style="cursor:pointer" title="上对齐" onclick="changeAlign(3)">'
		+'&nbsp;&nbsp;<img src="../../icon/alignbottom.png" style="cursor:pointer" title="下对齐" onclick="changeAlign(4)">'
		+'&nbsp;&nbsp;<img src="../../icon/aligncenter.png" style="cursor:pointer" title="上下居中" onclick="changeAlign(5)">'
		+'&nbsp;&nbsp;<img src="../../icon/alignmiddle.png" style="cursor:pointer" title="左右居中" onclick="changeAlign(6)">'
		
	);
		
	$('#content').prepend(toobarDiv);

	// 工具栏按钮处理
	$("input[name='modeRadio']").click(function(){		
		stage.mode = $("input[name='modeRadio']:checked").val();
		
		//自定义的编辑
		var smode=$("input[name='modeRadio']:checked").val();
		$("#disMode").val(smode);
		$("#action").val("edit");
		$("#topoForm").submit();
	});
	$('#centerButton').click(function(){
		stage.centerAndZoom(); //缩放并居中显示
	});
	$('#zoomOutButton').click(function(){
		stage.zoomOut();
	});
	$('#zoomInButton').click(function(){
		stage.zoomIn();
	});
	$('#exportButton').click(function(){
		stage.saveImageInfo();
	});
	$('#zoomCheckbox').click(function(){
		if($('#zoomCheckbox').attr('checked')){
			stage.wheelZoom = 0.85; // 设置鼠标缩放比例
		}else{
			stage.wheelZoom = null; // 取消鼠标缩放比例
		}
	});
	$('#fullScreenButton').click(function(){
		runPrefixMethod(stage.canvas, "RequestFullScreen")
	});

	// 查询
	$('#findButton').click(function(){
		var text = $('#findText').val().trim();
		var nodes = stage.find('node[text="'+text+'"]');
		if(nodes.length > 0){
			var node = nodes[0];
			node.selected = true;
			var location = node.getCenterLocation();
			// 查询到的节点居中显示
			stage.setCenter(location.x, location.y);
			
			function nodeFlash(node, n){
				if(n == 0) {
					node.selected = false;
					return;
				};
				node.selected = !node.selected;
				setTimeout(function(){
					nodeFlash(node, n-1);
				}, 300);
			}
			
			// 闪烁几下
			nodeFlash(node, 6);
		}
	});
	
	
}

//布局风格
//function changeLayout(sence){
//	var val=$("#layoutTopo  option:selected").val();
//		
//	if(val=="circle"){
//		circle(sence);
//	}else if(val=="tree"){
//		
//	}else if(val=="tree_circle"){
//		
//	}else{
//		
//	}
//}



var runPrefixMethod = function(element, method) {
	var usablePrefixMethod;
	["webkit", "moz", "ms", "o", ""].forEach(function(prefix) {
		if (usablePrefixMethod) return;
		if (prefix === "") {
			// 无前缀，方法首字母小写
			method = method.slice(0,1).toLowerCase() + method.slice(1);
		}
		var typePrefixMethod = typeof element[prefix + method];
		if (typePrefixMethod + "" !== "undefined") {
			if (typePrefixMethod === "function") {
				usablePrefixMethod = element[prefix + method]();
			} else {
				usablePrefixMethod = element[prefix + method];
			}
		}
	}
);

return usablePrefixMethod;
};
/*
runPrefixMethod(this, "RequestFullScreen");
if (typeof window.screenX === "number") {
var eleFull = canvas;
eleFull.addEventListener("click", function() {
	if (runPrefixMethod(document, "FullScreen") || runPrefixMethod(document, "IsFullScreen")) {
		runPrefixMethod(document, "CancelFullScreen");
		this.title = this.title.replace("退出", "");
	} else if (runPrefixMethod(this, "RequestFullScreen")) {
		this.title = this.title.replace("点击", "点击退出");
	}
});
} else {
alert("浏览器不支持");
}*/





function addNode(n){
	var node = new JTopo.Node(n.nodeName);
    var img='../../icon/' + n.iconImage;
      if(img)
      	node.setImage(img, true);
      else
      	node.setImage(imgUrl, true);
      node.setLocation(scene.width * Math.random(), scene.height * Math.random());
      if(n.devStatus!=null&&n.devStatus>0){
         if(n.devStatus<10){
           node.alarm = "0"+n.devStatus;
         }else{
            node.alarm = n.devStatus;
         }
      }
      node.textPosition='Bottom_Center';
      node.fontColor = "255,255,255";
     // node.borderWidth = 1; // 边框的宽度
     // node.borderColor  = '220,20,60';
      node._id=n.nodeId;
      node.scaleX=n.width;
      node.scaleY=n.height;
      scene.add(node);
      //给节点添加事件
      if(disMode=="edit"){
      
	        node.addEventListener('mouseup', function(event){
	              currentNode = this;
				  handler(event,'node');
			});
      }
      return node;
  }

//树形布局
function tree(scene){
	scene.clear();	
	var graph=null;
	var nodes=null;
		 $.ajax({
	    		type : 'post',
	    		url: '../../topo/graph/getData.do',
	    		dataType: 'json',
	    		cache : false, 
	    		async: false,
	    		success: function(data) {
	       			if(data.graphStr!=null&&data.nodeStr!=null){
	       				graph=data.graphStr;
	       				nodes=data.nodeStr;
	       			 	return;
	       			}
	    		}
	    	});
	
	 var img='../../icon/' + nodes[0].iconImage;
	 var cloudNode = new JTopo.Node(nodes[0].nodeName);
     cloudNode.setLocation(360,130);  
     cloudNode.setImage(img,true);
     cloudNode.layout = {type: 'tree', width:180, height: 100}
     cloudNode._id=nodes[0].nodeId;
     cloudNode.scaleX=nodes[0].width;
     cloudNode.scaleY=nodes[0].height;
     
//     if(nodes[0].devStatus!=null&&nodes[0].devStatus>0){
//         
//         if(nodes[0].devStatus<10){
//        	 cloudNode.alarm = "0"+nodes[0].devStatus;
//         }else{
//        	 cloudNode.alarm = nodes[0].devStatus;
//         }
//      }
     
     
     scene.add(cloudNode);
     
     //给节点添加事件
     if(disMode=="edit"){
     
    	 cloudNode.addEventListener('mouseup', function(event){
	              currentNode = this;
				  handler(event,'node');
			});
     }
     
     for(var i=1; i<=4; i++){
         var node = new JTopo.Node(nodes[i].nodeName);
         var img='../../icon/' + nodes[i].iconImage;
         node.setImage(img,true);
         node.setLocation(nodes[i].posX, nodes[i].posY);
         node._id=nodes[i].nodeId;
         node.scaleX=nodes[i].width;
         node.scaleY=nodes[i].height;
         node.layout = {type: 'tree', width:50, height: 100};
         
//         if(nodes[i].devStatus!=null&&nodes[i].devStatus>0){
//             
//             if(n.devStatus<10){
//               node.alarm = "0"+nodes[i].devStatus;
//             }else{
//                node.alarm = nodes[i].devStatus;
//             }
//          }
         
         scene.add(node);   
         //给节点添加事件
         if(disMode=="edit"){
         
   	        node.addEventListener('mouseup', function(event){
   	              currentNode = this;
   				  handler(event,'node');
   			});
         }
         
         var link = new JTopo.Link(cloudNode, node);
         scene.add(link);
     }
     
     
     for(var k=5; k<nodes.length; k++){
         var vmNode = new JTopo.Node(nodes[k].nodeName);
         var img='../../icon/' + nodes[k].iconImage;
         vmNode.setImage(img,true);
         vmNode.setLocation(nodes[k].posX, nodes[k].posY);
         vmNode._id=nodes[k].nodeId;
         vmNode.scaleX=nodes[k].width;
         vmNode.scaleY=nodes[k].height;
         
//         if(nodes[k].devStatus!=null&&nodes[k].devStatus>0){
//             
//             if(nodes[k].devStatus<10){
//            	 vmNode.alarm = "0"+nodes[k].devStatus;
//             }else{
//            	 vmNode.alarm = nodes[k].devStatus;
//             }
//          }
         
         scene.add(vmNode); 
         //给节点添加事件
         if(disMode=="edit"){
         
        	 vmNode.addEventListener('mouseup', function(event){
   	              currentNode = this;
   				  handler(event,'node');
   			});
         }
         scene.add(new JTopo.Link(node, vmNode));                            
     }
     
     
     JTopo.layout.layoutNode(scene, cloudNode, true);
}

//圆形布局
function circle(scene){
	scene.clear();
	var graph=null;
	var nodes=null;
		 $.ajax({
	    		type : 'post',
	    		url: '../../topo/graph/getData.do',
	    		dataType: 'json',
	    		cache : false, 
	    		async: false,
	    		success: function(data) {
	       			if(data.graphStr!=null&&data.nodeStr!=null){
	       				graph=data.graphStr;
	       				nodes=data.nodeStr;
	       			 	return;
	       			}
	    		}
	    	});
	
	
	var cloudNode = new JTopo.Node(nodes[0].nodeName);
	 var img='../../icon/' + nodes[0].iconImage;
	 
    //cloudNode.setSize(12, 12);
    cloudNode.setLocation(360,230);   
    cloudNode.setImage(img,true);
    cloudNode.layout = {type: 'circle', radius: 150};
    cloudNode._id=nodes[0].nodeId;
    cloudNode.scaleX=nodes[0].width;
    cloudNode.scaleY=nodes[0].height;
    
//    if(nodes[0].devStatus!=null&&nodes[0].devStatus>0){
//        
//        if(nodes[0].devStatus<10){
//        	cloudNode.alarm = "0"+nodes[0].devStatus;
//        }else{
//        	cloudNode.alarm = nodes[0].devStatus;
//        }
//     }
    
    scene.add(cloudNode);
    
    //给节点添加事件
    if(disMode=="edit"){
    
   	 cloudNode.addEventListener('mouseup', function(event){
	              currentNode = this;
				  handler(event,'node');
			});
    }
    
    for(var i=1; i<=4; i++){
        var node = new JTopo.Node(nodes[i].nodeName);
        var img='../../icon/' + nodes[i].iconImage;
        node.setImage(img,true);
        node.setLocation(nodes[i].posX, nodes[i].posX);
        node.layout = {type: 'circle', radius: 80};
        node._id=nodes[i].nodeId;
        node.scaleX=nodes[i].width;
        node.scaleY=nodes[i].height;
        
//        if(nodes[i].devStatus!=null&&nodes[i].devStatus>0){
//            
//            if(nodes[i].devStatus<10){
//              node.alarm = "0"+nodes[i].devStatus;
//            }else{
//               node.alarm = nodes[i].devStatus;
//            }
//         }
        
        scene.add(node);  
        
        //给节点添加事件
        if(disMode=="edit"){
        
        	node.addEventListener('mouseup', function(event){
    	              currentNode = this;
    				  handler(event,'node');
    			});
        }
        var link = new JTopo.Link(cloudNode, node);
        scene.add(link);
    }
    
    for(var j=5; j<nodes.length; j++){
        var vmNode = new JTopo.Node(nodes[j].nodeName);
        vmNode.setLocation(nodes[j].posX, nodes[j].posX);
        var img='../../icon/' + nodes[j].iconImage;
        vmNode.setImage(img,true);
        vmNode._id=nodes[j].nodeId;
        vmNode.scaleX=nodes[j].width;
        vmNode.scaleY=nodes[j].height;
        
//        if(nodes[j].devStatus!=null&&nodes[j].devStatus>0){
//            
//            if(nodes[j].devStatus<10){
//            	vmNode.alarm = "0"+nodes[j].devStatus;
//            }else{
//            	vmNode.alarm = nodes[j].devStatus;
//            }
//         }
//        
        scene.add(vmNode); 
      //给节点添加事件
        if(disMode=="edit"){
        
        	vmNode.addEventListener('mouseup', function(event){
    	              currentNode = this;
    				  handler(event,'node');
    			});
        }
        scene.add(new JTopo.Link(node, vmNode));                            
    }
    JTopo.layout.layoutNode(scene, cloudNode, true);
}

//混合布局

function tree_circle(scene){
	 scene.clear();
	 var graph=null;
	 var nodes=null;
			 $.ajax({
		    		type : 'post',
		    		url: '../../topo/graph/getData.do',
		    		dataType: 'json',
		    		cache : false, 
		    		async: false,
		    		success: function(data) {
		       			if(data.graphStr!=null&&data.nodeStr!=null){
		       				graph=data.graphStr;
		       				nodes=data.nodeStr;
		       			 	return;
		       			}
		    		}
		    	});
	 
	 
	 var cloudNode = new JTopo.Node(nodes[0].nodeName);
     //cloudNode.setSize(12, 12);
     cloudNode.setLocation(460, 280); 
     var img='../../icon/' + nodes[0].iconImage;
     cloudNode.setImage(img,true);
     cloudNode._id=nodes[0].nodeId;
     cloudNode.scaleX=nodes[0].width;
     cloudNode.scaleY=nodes[0].height;
     cloudNode.layout = {type: 'circle', radius: 160};
     
//     if(nodes[0].devStatus!=null&&nodes[0].devStatus>0){
//         
//         if(nodes[0].devStatus<10){
//        	 cloudNode.alarm = "0"+nodes[0].devStatus;
//         }else{
//        	 cloudNode.alarm = nodes[0].devStatus;
//         }
//      }
     
     scene.add(cloudNode);
     //给节点添加事件
     if(disMode=="edit"){
     
    	 cloudNode.addEventListener('mouseup', function(event){
 	              currentNode = this;
 				  handler(event,'node');
 			});
     }
     for(var i=1; i<=4; i++){
         var node = new JTopo.Node(nodes[i].nodeName);
         node.setLocation(nodes[i].posX, nodes[i].posY);
         if(i == 2){
             node.layout = {type: 'tree', direction: 'top', width: 50, height: 90};
         }else if(i == 1){
             node.layout = {type: 'tree', direction: 'left', width: 50, height: 90};
         }else{
             node.layout = {type: 'circle', radius: 60};
         }
         var img='../../icon/' + nodes[i].iconImage;
         node.setImage(img,true);
         node._id=nodes[i].nodeId;
         node.scaleX=nodes[i].width;
         node.scaleY=nodes[i].height;
         
//         if(nodes[i].devStatus!=null&&nodes[i].devStatus>0){
//             
//             if(nodes[i].devStatus<10){
//               node.alarm = "0"+nodes[i].devStatus;
//             }else{
//                node.alarm = nodes[i].devStatus;
//             }
//          }
         
         scene.add(node);  
         //给节点添加事件
         if(disMode=="edit"){
         
        	 node.addEventListener('mouseup', function(event){
     	              currentNode = this;
     				  handler(event,'node');
     			});
         }
         var link = new JTopo.Link(cloudNode, node);
         scene.add(link);
         
     }
     

     for(var j=5; j<nodes.length; j++){
         var vmNode = new JTopo.Node(nodes[j].nodeName);
         vmNode.setLocation(nodes[j].posX, nodes[i].posY);
         var img='../../icon/' + nodes[j].iconImage;
         vmNode.setImage(img,true);
         vmNode._id=nodes[j].nodeId;
         vmNode.scaleX=nodes[j].width;
         vmNode.scaleY=nodes[j].height;
         
//         if(nodes[j].devStatus!=null&&nodes[j].devStatus>0){
//             
//             if(nodes[j].devStatus<10){
//            	 vmNode.alarm = "0"+nodes[j].devStatus;
//             }else{
//            	 vmNode.alarm = nodes[j].devStatus;
//             }
//          }
         
         scene.add(vmNode);
         //给节点添加事件
         if(disMode=="edit"){
         
         	vmNode.addEventListener('mouseup', function(event){
     	              currentNode = this;
     				  handler(event,'node');
     			});
         }
         scene.add(new JTopo.Link(node, vmNode));                            
     }
     JTopo.layout.layoutNode(scene, cloudNode, true);
}


