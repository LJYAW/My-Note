var proName="";
function showJTopoToobar(sta,name){
	proName=name;
	var toobarDiv;
	if(sta!=null){
		toobarDiv = $('<div class="jtopo_toolbar" style="width: auto; margin: 0px auto;">').html(''
				+'<div class="toolbar-btn toolbar-c" title="居中显示" id="centerButton" ><div class="toolbar-p toolbar-center"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="全屏显示" id="maximize"><div class="toolbar-p toolbar-full"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="放大" id="zoomIn"><div class="toolbar-p toolbar-boost"></div></div>'
			    +'<div class="toolbar-btn toolbar-co" title="缩小" id="zoomOut"><div class="toolbar-p toolbar-lessen"></div></div>'
//			    +'<div class="toolbar-btn toolbar-co" title="显示关系" id="showEdgeText"><div class="toolbar-p toolbar-on-showText"></div></div>'
//			    +'<div class="toolbar-btn toolbar-cp" title="隐藏关系" id="hideEdgeText"><div class="toolbar-p toolbar-on-hideText"></div></div>'
//						
				+'<div class="toolbar-btn toolbar-c" title="平移" id="movemode" style="background-color:#fff;"><div class="toolbar-p toolbar-move"></div>	</div>'
				+'<div class="toolbar-btn toolbar-co" title="框选" id="selectmode"><div class="toolbar-p toolbar-all"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="直线" id="createLine" ><div class="toolbar-p toolbar-on-line"></div></div>'
				//+'<div class="toolbar-btn toolbar-co" title="水平LAN" id="Lan"><div class="toolbar-p toolbar-on-wlan"></div></div>'
				//+'<div class="toolbar-btn toolbar-co" title="垂直LAN" id="dropLan"><div class="toolbar-p toolbar-on-dlan"></div></div>'
				//+'<div class="toolbar-btn toolbar-cp" title="关联" id="relateEdge"><div class="toolbar-p toolbar-on-relate"></div></div>'
//				+'<div class="toolbar-btn toolbar-co" title="横排文本" id="textRank"><div class="toolbar-p toolbar-on-textRank"></div></div>'
//				+'<div class="toolbar-btn toolbar-cp" title="竖排文本" id="textVertical"><div class="toolbar-p toolbar-on-textVertical"></div></div>'
			    +'<div class="toolbar-btn toolbar-co" title="折线" id="addBrokenEdge"><div class="toolbar-p toolbar-on-brokenLine"></div></div>'
			    +'<div class="toolbar-btn toolbar-co" title="依赖" id="addBrokenEdgeArrow"><div class="toolbar-p toolbar-on-brokenEArrow"></div></div>'
			    +'<div class="toolbar-btn toolbar-co" title="包含" id="edgeSolidArrow"><div class="toolbar-p toolbar-on-solidArrow"></div></div>'
			    +'<div class="toolbar-btn toolbar-co" title="依赖" id="edgeEmptyArrow"><div class="toolbar-p toolbar-on-solidEArrow"></div></div>'
			    +'<div class="toolbar-btn toolbar-cp" title="影响" id="dashEdgeSolidArrow"><div class="toolbar-p toolbar-on-dashArrow"></div></div>'
				

				
				+'<div class="toolbar-btn toolbar-c" title="左对齐"><div class="toolbar-register"><img src="../../icon/alignleft.png" class="bgimg" style="cursor:pointer" title="左对齐" id="alignleft" onclick="changeAlign(1)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="右对齐"><div class="toolbar-register"><img src="../../icon/alignright.png" class="bgimg" style="cursor:pointer;" title="右对齐" id="alignright" onclick="changeAlign(2)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="上对齐"><div class="toolbar-register"><img src="../../icon/aligntop.png" class="bgimg" style="cursor:pointer" title="上对齐" id="aligntop" onclick="changeAlign(3)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="下对齐"><div class="toolbar-register"><img src="../../icon/alignbottom.png" class="bgimg" style="cursor:pointer" title="下对齐" id="alignbottom" onclick="changeAlign(4)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="上下居中"><div class="toolbar-register"><img src="../../icon/aligncenter.png" class="bgimg" style="cursor:pointer" title="上下居中" id="aligncenter" onclick="changeAlign(5)"></div></div>'
				+'<div class="toolbar-btn toolbar-cp" title="左右居中"><div class="toolbar-register"><img src="../../icon/alignmiddle.png" class="bgimg" style="cursor:pointer" title="左右居中" id="alignmiddle" onclick="changeAlign(6)"></div></div>'
				
				+'<div class="toolbar-btn toolbar-c" title="修改名称" id="editmode"><div class="toolbar-edit"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="线路信息" id="showlinkDesc"><div class="toolbar-p toolbar-line"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="保存" id="saveTopo"><div class="toolbar-p toolbar-save"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="输出" id="exportImg">	<div class="toolbar-p toolbar-png"></div></div>'
				+'<div class="toolbar-btn toolbar-cp" title="刷新" id="refreshTopo"><div class="toolbar-p toolbar-refresh"></div></div>'
				+'<div class="toolbar-ipt"><input class="form-control" style="" type="text" id="findButton" placeholder="Name" ><div class="search"></div></div>'
				+'<div style="padding-top:10px;padding-right:25px;">刷新时间：<select name="refreshTime" id="refreshTime" onchange="changeTimer()"> <option value="30000">30秒</option> <option value="60000">1分钟</option> <option value="300000" >5分钟</option> </select></div>'
				
			);
	}else{
		toobarDiv = $('<div class="jtopo_toolbar" style="width: auto; margin: 0px auto;">').html(''
				+'<div class="toolbar-btn toolbar-c" title="居中显示" id="centerButton" ><div class="toolbar-p toolbar-center"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="全屏显示" id="maximize"><div class="toolbar-p toolbar-full"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="放大" id="zoomIn"><div class="toolbar-p toolbar-boost"></div></div>'
			    +'<div class="toolbar-btn toolbar-co" title="缩小" id="zoomOut"><div class="toolbar-p toolbar-lessen"></div></div>'
//			    +'<div class="toolbar-btn toolbar-co" title="显示关系" id="showEdgeText"><div class="toolbar-p toolbar-on-showText"></div></div>'
//			    +'<div class="toolbar-btn toolbar-cp" title="隐藏关系" id="hideEdgeText"><div class="toolbar-p toolbar-on-hideText"></div></div>'
//						
				+'<div class="toolbar-btn toolbar-c" title="平移" id="movemode" style="background-color:#fff;"><div class="toolbar-p toolbar-move"></div>	</div>'
				+'<div class="toolbar-btn toolbar-co" title="框选" id="selectmode"><div class="toolbar-p toolbar-all"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="直线" id="createLine" ><div class="toolbar-p toolbar-on-line"></div></div>'
			//	+'<div class="toolbar-btn toolbar-co" title="水平LAN" id="Lan"><div class="toolbar-p toolbar-on-wlan"></div></div>'
		//		+'<div class="toolbar-btn toolbar-co" title="垂直LAN" id="dropLan"><div class="toolbar-p toolbar-on-dlan"></div></div>'
		//		+'<div class="toolbar-btn toolbar-cp" title="关联" id="relateEdge"><div class="toolbar-p toolbar-on-relate"></div></div>'
//				+'<div class="toolbar-btn toolbar-co" title="横排文本" id="textRank"><div class="toolbar-p toolbar-on-textRank"></div></div>'
//				+'<div class="toolbar-btn toolbar-cp" title="竖排文本" id="textVertical"><div class="toolbar-p toolbar-on-textVertical"></div></div>'
			      +'<div class="toolbar-btn toolbar-co" title="折线" id="addBrokenEdge"><div class="toolbar-p toolbar-on-brokenLine"></div></div>'
			      +'<div class="toolbar-btn toolbar-co" title="依赖" id="addBrokenEdgeArrow"><div class="toolbar-p toolbar-on-brokenEArrow"></div></div>'
			      +'<div class="toolbar-btn toolbar-co" title="包含" id="edgeSolidArrow"><div class="toolbar-p toolbar-on-solidArrow"></div></div>'
			      +'<div class="toolbar-btn toolbar-co" title="依赖" id="edgeEmptyArrow"><div class="toolbar-p toolbar-on-solidEArrow"></div></div>'
			      +'<div class="toolbar-btn toolbar-cp" title="影响" id="dashEdgeSolidArrow"><div class="toolbar-p toolbar-on-dashArrow"></div></div>'

				
				+'<div class="toolbar-btn toolbar-c" title="左对齐"><div class="toolbar-register"><img src="../../icon/alignleft.png" class="bgimg" style="cursor:pointer" title="左对齐" id="alignleft" onclick="changeAlign(1)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="右对齐"><div class="toolbar-register"><img src="../../icon/alignright.png" class="bgimg" style="cursor:pointer;" title="右对齐" id="alignright" onclick="changeAlign(2)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="上对齐"><div class="toolbar-register"><img src="../../icon/aligntop.png" class="bgimg" style="cursor:pointer" title="上对齐" id="aligntop" onclick="changeAlign(3)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="下对齐"><div class="toolbar-register"><img src="../../icon/alignbottom.png" class="bgimg" style="cursor:pointer" title="下对齐" id="alignbottom" onclick="changeAlign(4)"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="上下居中"><div class="toolbar-register"><img src="../../icon/aligncenter.png" class="bgimg" style="cursor:pointer" title="上下居中" id="aligncenter" onclick="changeAlign(5)"></div></div>'
				+'<div class="toolbar-btn toolbar-cp" title="左右居中"><div class="toolbar-register"><img src="../../icon/alignmiddle.png" class="bgimg" style="cursor:pointer" title="左右居中" id="alignmiddle" onclick="changeAlign(6)"></div></div>'
				
				//+'<div class="toolbar-btn toolbar-c" title="修改名称" id="editmode"><div class="toolbar-edit"></div></div>'
				//+'<div class="toolbar-btn toolbar-co" title="线路信息" id="showlinkDesc"><div class="toolbar-p toolbar-line"></div></div>'
				+'<div class="toolbar-btn toolbar-c" title="保存" id="saveTopo"><div class="toolbar-p toolbar-save"></div></div>'
				+'<div class="toolbar-btn toolbar-co" title="输出" id="exportImg">	<div class="toolbar-p toolbar-png"></div></div>'
				+'<div class="toolbar-btn toolbar-cp" title="刷新" id="refreshTopo"><div class="toolbar-p toolbar-refresh"></div></div>'
				+'<div class="toolbar-ipt"><input class="form-control"  type="text" id="findButton" placeholder="Name" ><div class="search"></div></div>'
				+'<div style="padding-top:10px;padding-left:30px;float:left">刷新时间：<select name="refreshTime" id="refreshTime" onchange="changeTimer()"> <option value="30000">30秒</option> <option value="60000">1分钟</option> <option value="300000" >5分钟</option> </select></div>'
				
			);
	}
	
	$('#content').prepend(toobarDiv);
	
	
	//框选模式
	$('#selectmode').click(function(){
	   changeGraphModel('Select');
	   $('#movemode').css("background-color","");
	   $('#editmode').css("background-color","");
	   $('#createLine').css("background-color","");
	   $('#Lan').css("background-color","");
	   $('#dropLan').css("background-color","");
	   $('#textRank').css("background-color","");
       $('#textVertical').css("background-color","");
       $('#relateEdge').css("background-color","");
	   $('#selectmode').css("background-color","#fff");
	   $('#edgeSolidArrow').css("background-color","");
       $('#edgeEmptyArrow').css("background-color","");
       $('#dashEdgeSolidArrow').css("background-color","");      
       $('#addBrokenEdge').css("background-color","");
       $('#addBrokenEdgeArrow').css("background-color","");
	});
	
	//平移模式
	$('#movemode').click(function(){
		changeGraphModel('Move');
	   $('#selectmode').css("background-color","");
	   $('#editmode').css("background-color","");
	   $('#createLine').css("background-color","");
	   $('#Lan').css("background-color","");
	   $('#dropLan').css("background-color","");
	   $('#textRank').css("background-color","");
       $('#textVertical').css("background-color","");
       $('#relateEdge').css("background-color","");
	   $('#movemode').css("background-color","#fff");
	   $('#edgeSolidArrow').css("background-color","");
       $('#edgeEmptyArrow').css("background-color","");
       $('#dashEdgeSolidArrow').css("background-color","");      
       $('#addBrokenEdge').css("background-color","");
       $('#addBrokenEdgeArrow').css("background-color","");
	});
	
	//支持编辑
	$('#editmode').click(function(){
		graph.editable=true;//是否支持编辑
		Q.Defaults.LABEL_EDITOR_SUBMIT_WHEN_LOST_FOCUS = true;//修改文本信息时，失去焦点就提交，默认是回车提交
		
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#editmode').css("background-color","#fff");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
		
	});

	
	
	$('#centerButton').click(function(e){
		 var leftDiff=0;//左侧div宽度
		 var locXY=windowTocanvas(graph.canvas,e.pageX,e.pageY);
	     if(locXY.x!=e.pageX){
	    	 leftDiff=240;	    	 
	     }
		graph.moveToCenter(leftDiff);
		
	});
	
	
	//刷新
	$("#refreshTopo").click(function(){
		loadTopo(graphID);
	});
	
	//画直线
	$('#createLine').click(function(){
		 changeGraphModel('Edit');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#createLine').css("background-color","#fff");
		   $('#dropLan').css("background-color","");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
	});
	
	//画直线
	$('#relateEdge').click(function(){
		 changeGraphModel('relateEdge');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","#fff");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
	});
	
	//画LAN型线
	$('#Lan').click(function(){
		changeGraphModel('Lan');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","#fff");
		   $('#textRank').css("background-color","");
	       $('#textVertical').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#relateEdge').css("background-color","");
		   $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
	});
	//画节点LAN型线
	$('#dropLan').click(function(){
		changeGraphModel('dropLan');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#textRank').css("background-color","");
	       $('#textVertical').css("background-color","");
		   $('#dropLan').css("background-color","#fff");
		   $('#relateEdge').css("background-color","");
		   $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
	});
	//横向文字
	$('#textRank').click(function(){
		changeGraphModel('textRank');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#textVertical').css("background-color","");
	       $('#textRank').css("background-color","#fff");
	       $('#relateEdge').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
	});
	//竖向文字
	$('#textVertical').click(function(){
		changeGraphModel('textVertical');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#textVertical').css("background-color","#fff");
	       $('#textRank').css("background-color","");
	       $('#relateEdge').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
	});
	//实线实箭头
	$('#edgeSolidArrow').click(function(){
		changeGraphModel('EditEdgeSolidArrow');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","#fff");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
		
	});
	//实线空箭头
	$('#edgeEmptyArrow').click(function(){
		changeGraphModel('EditEdgeEmptyArrow');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","#fff");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
		
	});
	//虚线实箭头
	$('#dashEdgeSolidArrow').click(function(){
		changeGraphModel('EditDashEdgeSolidArrow');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","#fff");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","");
		
	});
	
	//折线
	$('#addBrokenEdge').click(function(){
		changeGraphModel('EditBroken');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","#fff");
	       $('#addBrokenEdgeArrow').css("background-color","");
		
	});
	
	//折线箭头
	$('#addBrokenEdgeArrow').click(function(){
		changeGraphModel('EditBrokenArrow');
		   $('#selectmode').css("background-color","");
		   $('#movemode').css("background-color","");
		   $('#createLine').css("background-color","");
		   $('#Lan').css("background-color","");
		   $('#dropLan').css("background-color","");
		   $('#editmode').css("background-color","");
		   $('#textRank').css("background-color","");
		   $('#relateEdge').css("background-color","");
	       $('#textVertical').css("background-color","");
	       $('#edgeSolidArrow').css("background-color","");
	       $('#edgeEmptyArrow').css("background-color","");
	       $('#dashEdgeSolidArrow').css("background-color","");      
	       $('#addBrokenEdge').css("background-color","");
	       $('#addBrokenEdgeArrow').css("background-color","#fff");
		
	});
	//显示线路信息
	$('#showlinkDesc').click(function(){
		 graph.forEach(function(node){
		       if(node.type=="Q.Edge"){
		    	   node.setStyle("lineA_visible", node.getStyle("lineA_visible") === false);
		    	   node.setStyle("lineB_visible", node.getStyle("lineB_visible") === false);
		    	   node.invalidate();
		       }
		      });
	});
	
	 //放大
	$('#zoomIn').click(function(){
		 graph.zoomIn(0.85);
		
	});
	//缩小
	$('#zoomOut').click(function(){
		 graph.zoomOut(0.85);
	}); 
	//点击显示隐藏线文本
	$('#showEdgeText').click(function(){
	  
	  /*for(var i=0;i<graph.EdgeText.length;i++){
	    graph.add(graph.EdgeText[i]);
	  }*/
	  
	})
	$('#hideEdgeText').click(function(){
	 /* for(var i=0;i<graph.EdgeText.length;i++){
	    graph.removeElement(graph.EdgeText[i]);
	  }*/
	  
	})
	//导出图片
	$('#exportImg').click(function(){
		 Q.showExportPanel(graph);
	});
	
	$('#findButton').keydown(function(e){
		if(e.keyCode==13){
			$('#findButton').val();
			var name=$('#findButton').val();
             graph.forEach(function (e) {
            	 if(e.type!="Q.Text"){
            		 if(e.name==name){
            			 item = graph.graphModel.getById(e.id);
                         if (!item) {
                             return false;
                         }
                         graph.setSelection(item);
                         graph.sendToTop(item);
                         var bounds = graph.getUIBounds(item);
                         if (bounds) {
                        	 graph.centerTo(bounds.cx, bounds.cy, Math.max(2, graph.scale), true);
                         }
            		 }
            	 }
		       });
		   }    
	}); 
	
	
	var flag=0;
	var oValue = window.parent.$("#index-center").height();
	var ww=screen.availWidth;
	var wh=screen.availHeight;
	$('#maximize').click(function(e){  //graph_panel
		if(oValue>0){
			if(flag%2==0){  //放大
		    	$('#leftMenu').hide();
				$('#topology').hide();
				$("#content").css("margin-left","0px");
				leftwidth=0;
				window.parent.$('#banner').css("display","none");
				window.parent.$('#menu_bar').css("display","none");
				window.parent.$('#index-bottom').css("display","none");
				window.parent.$('.l-layout-bottom').css("display","none");
				
				window.parent.$("#layout-index").css("height","100%");
				window.parent.$(".l-layout-center").css("height","100%");
				window.parent.$("#index-center").css("height","100%");
		    }else{   //缩小
		    	$('#leftMenu').show();
				$('#topology').show();
//				$("#content").css("margin-left","240px");
				leftwidth=240;
				window.parent.$('#banner').css("display","block");
				window.parent.$('#menu_bar').css("display","block");
				window.parent.$('#index-bottom').css("display","block");
				window.parent.$('.l-layout-bottom').css("display","block");
				
				window.parent.$("#layout-index").height(oValue);
				window.parent.$(".l-layout-center").height(oValue);
				window.parent.$("#index-center").height(oValue);
		    }
		}else{
			if(flag%2==0){
				$('#leftMenu').hide();
				$('#topology').hide();
				$("#content").css("margin-left","0px");
				leftwidth=0;
				window.moveTo(0,0) ;
				window.resizeTo(screen.availWidth,screen.availHeight); //窗口最大化
			}else{
				$('#leftMenu').show();
				$('#topology').show();
				$("#content").css("margin-left","240px");
				leftwidth=240;
				window.moveTo(160,100) ;
//				
				window.resizeTo(1376,850); //窗口最大化
			 
			}
		}
	    
		flag++;
		
		
		 var leftDiff=0;//左侧div宽度
		 var locXY=windowTocanvas(graph.canvas,e.pageX,e.pageY);
	     if(locXY.x!=e.pageX){
	    	 leftDiff=240;
	     }
		graph.moveToCenter(leftDiff);
//		moveCenter();
	});
	
	
	function moveCenter(){
		var bgv= $("#bgpicture").val();
		if(bgv.length>0){
			var image=new Image();
			image.src="../../icon/bgpic/"+bgv;
//			alert(image.width)
//			alert( image.height);
			graph.centerTo(image.width/2, image.height/2);
			
		}else{
			graph.moveToCenter();  //居中显示f
		}
	}
	
	
	
	
	//保存
	$('#saveTopo').click(function () {
		  var saveNodes=[];
		  var saveTexts=[];
		  var saveLinks=[];
		  var saveLans=[];
		  var saveLanNodes=[];
		  var sceneObj=new Object();
		  sceneObj.graphId=graphID;
		  var saveGraph=JSON.stringify(sceneObj);
		 
			graph.childs.forEach(
		      function(node){
		        if(node.elementType=="Node"){
		            var nodeObj = new Object();
		            var textNode=getTextNodeByNode(node.id);
		            nodeObj.node_Name=textNode.name;
		            nodeObj.node_ID=node.id;
		             if(node.nodeInfo!=null){
		               nodeObj.nodeInfo=node.nodeInfo;
		            }
		          	nodeObj.posX=node.x;
					nodeObj.posY=node.y;
					nodeObj.namePosX=textNode.x+textNode.textWidth/2;
					nodeObj.namePosY=textNode.y;
		            saveNodes.push(nodeObj);
		        }else if(node.elementType=="Edge"){
//		           alert(node.from);
		           var lineObj = new Object();
		           lineObj.nodeAId=node.nodeAId;
		           lineObj.nodeAName=node.nodeAName;
		           lineObj.nodeBId=node.nodeBId;
		           lineObj.nodeBName=node.nodeBName;
		           lineObj.lineName=node.name;
		           if(node.id!=null){
		             lineObj.linkID=node.id;
		           }
		           
		           saveLinks.push(lineObj);
		        }else if(node.elementType=="LanNode"){
		        	var lanNodeObj=new Object();
		        	lanNodeObj.nodeId=node.nodeId;
		        	lanNodeObj.lanId=node.lanId;
		        	lanNodeObj.posX=node.x2;
		        	lanNodeObj.posY=node.y2;
		        	
		        	lanNodeObj.id=node.id;
		        	var textNode=getTextNodeByLanNode(node.id+1);
		        	if(textNode){
		        		lanNodeObj.startName=node.startName;
		        		lanNodeObj.sNamePosX=textNode.x+textNode.textWidth/2;
		        		lanNodeObj.sNamePosY=textNode.y;
		        	}
		        	
		        	var textNode2=getTextNodeByLanNode(node.id);
		        	if(textNode2){
		        		lanNodeObj.endName=node.endName;
		        		lanNodeObj.eNamePosX=textNode2.x+textNode2.textWidth/2;
		        		lanNodeObj.eNamePosY=textNode2.y;
		        	}
		        	saveLanNodes.push(lanNodeObj);
		        	
		        }else if(node.elementType=="LAN"){
		        	var lanObj=new Object();
		        	lanObj.posX1=node.x1;
		        	lanObj.posY1=node.y1;
		        	lanObj.posX2=node.x2;
		        	lanObj.posY2=node.y2;
		        	lanObj.lanName=node.name;
		        	lanObj.id=node.id;
		        	
		        	var textNode=getTextNodeByLan(lanObj.id);
		        	if(textNode){
		        		lanObj.namePosX=textNode.x+textNode.textWidth/2;
		        		lanObj.namePosY=textNode.y;
		        	}
		        	
		        	saveLans.push(lanObj);
		        }
		        
		      }
		    );
		    var nodesStr = JSON.stringify(saveNodes);
		   	var linksStr = JSON.stringify(saveLinks);
		 	var lanStr = JSON.stringify(saveLans);
		 	var lanNodeStr = JSON.stringify(saveLanNodes);
		   	var jsonGraph = JSON.stringify(sceneObj);
		   	var params ={'nodesStr':nodesStr,'linksStr':linksStr,'graphStr':saveGraph,'lanStr':lanStr,'lanNodeStr':lanNodeStr};
		   	$.ajax({
						   url : '../../topo/graphManage/saveTopo.do',
					       type:'post',         //数据发送方式
					       dataType:'json',     //接受数据格式
					       data:params,         //要传递的数据
					       success:function(data) {
					       		if(data.result=='success')
									window.parent.$.ligerDialog.success("保存成功");
					       		loadTopo(graphID);
							 }
					 });
		   	
		 });
	
	
	//右键菜单
	

	
}


function saveTopo(){
	  var saveNodes=[];
	  var saveTexts=[];
	  var saveLinks=[];
	  var saveLans=[];
	  var saveLanNodes=[];
	  var sceneObj=new Object();
	  sceneObj.graphId=graphID;
	  var saveGraph=JSON.stringify(sceneObj);
	 
		graph.childs.forEach(
	      function(node){
	        if(node.elementType=="Node"){
	            var nodeObj = new Object();
	            var textNode=getTextNodeByNode(node.id);
	            nodeObj.node_Name=textNode.name;
	            nodeObj.node_ID=node.id;
	             if(node.nodeInfo!=null){
	               nodeObj.nodeInfo=node.nodeInfo;
	            }
	          	nodeObj.posX=node.x;
				nodeObj.posY=node.y;
				nodeObj.namePosX=textNode.x+textNode.textWidth/2;
				nodeObj.namePosY=textNode.y;
	            saveNodes.push(nodeObj);
	        }else if(node.elementType=="Edge"){
//	           alert(node.from);
	           var lineObj = new Object();
	           lineObj.nodeAId=node.nodeAId;
	           lineObj.nodeAName=node.nodeAName;
	           lineObj.nodeBId=node.nodeBId;
	           lineObj.nodeBName=node.nodeBName;
	           lineObj.lineName=node.name;
	           if(node.id!=null){
	             lineObj.linkID=node.id;
	           }
	           
	           saveLinks.push(lineObj);
	        }else if(node.elementType=="LanNode"){
	        	var lanNodeObj=new Object();
	        	lanNodeObj.nodeId=node.nodeId;
	        	lanNodeObj.lanId=node.lanId;
	        	lanNodeObj.posX=node.x2;
	        	lanNodeObj.posY=node.y2;
	        	
	        	lanNodeObj.id=node.id;
	        	var textNode=getTextNodeByLanNode(node.id+1);
	        	if(textNode){
	        		lanNodeObj.startName=node.startName;
	        		lanNodeObj.sNamePosX=textNode.x+textNode.textWidth/2;
	        		lanNodeObj.sNamePosY=textNode.y;
	        	}
	        	
	        	var textNode2=getTextNodeByLanNode(node.id);
	        	if(textNode2){
	        		lanNodeObj.endName=node.endName;
	        		lanNodeObj.eNamePosX=textNode2.x+textNode2.textWidth/2;
	        		lanNodeObj.eNamePosY=textNode2.y;
	        	}
	        	saveLanNodes.push(lanNodeObj);
	        	
	        }else if(node.elementType=="LAN"){
	        	var lanObj=new Object();
	        	lanObj.posX1=node.x1;
	        	lanObj.posY1=node.y1;
	        	lanObj.posX2=node.x2;
	        	lanObj.posY2=node.y2;
	        	lanObj.lanName=node.name;
	        	lanObj.id=node.id;
	        	
	        	var textNode=getTextNodeByLan(lanObj.id);
	        	if(textNode){
	        		lanObj.namePosX=textNode.x+textNode.textWidth/2;
	        		lanObj.namePosY=textNode.y;
	        	}
	        	
	        	saveLans.push(lanObj);
	        }
	        
	      }
	    );
	    var nodesStr = JSON.stringify(saveNodes);
	   	var linksStr = JSON.stringify(saveLinks);
	 	var lanStr = JSON.stringify(saveLans);
	 	var lanNodeStr = JSON.stringify(saveLanNodes);
	   	var jsonGraph = JSON.stringify(sceneObj);
	   	var params ={'nodesStr':nodesStr,'linksStr':linksStr,'graphStr':saveGraph,'lanStr':lanStr,'lanNodeStr':lanNodeStr};
	   	$.ajax({
					   url : '../../topo/graphManage/saveTopo.do',
				       type:'post',         //数据发送方式
				       dataType:'json',     //接受数据格式
				       data:params,         //要传递的数据
				       success:function(data) {
//				       		if(data.result=='success')
//								window.parent.$.ligerDialog.success("保存成功");
						 }
				 });
	   	
	 }


var timer2;
function changeTimer(){
	  var time= $("#refreshTime").val();
     if(timer2!=null){
    	 clearInterval(timer2);
     }
     timer2 = setInterval("loadTopo()",time);
   }


//告警文本信息
var alarmUI;
function alarmLabelUI(){
     alarmUI = new Q.LabelUI();
     alarmUI.position = Q.Position.RIGHT_TOP;
     alarmUI.anchorPosition = Q.Position.LEFT_BOTTOM;
     alarmUI.border = 1;
     alarmUI.backgroundGradient = Q.Gradient.LINEAR_GRADIENT_VERTICAL;
     alarmUI.backgroundColor="#FF0000";
     alarmUI.padding = new Q.Insets(2, 5);
     alarmUI.showPointer = true;
     alarmUI.offsetY = -10;
     alarmUI.offsetX = -10;
     alarmUI.rotatable = false;
     alarmUI.showOnTop = true;
}

//显示告警信息
function showAlarm(type){
   if(type=="show"){
    graph.forEach(function(element){
     if(element.type=="Q.Node"&&element.devStatus>0){//&&element.devStatus>0
             alarmLabelUI();
             element.addUI(alarmUI, [{ property : "label2", propertyType : Q.Consts.PROPERTY_TYPE_CLIENT, bindingProperty : "data" }]);
     		 element.set("label2", element.devStatus.toString());//element.devStatus.toString()
             element.setStyle(Q.Styles.RENDER_COLOR, "#FF0000");
             element.setStyle(Q.Styles.RENDER_COLOR_BLEND_MODE, Q.Consts.BLEND_MODE_GRAY);
           
     }
    });
     alarmType="hide";
   }else{
    graph.forEach(function(element){
     if(element.type=="Q.Node"&&element.devStatus>0){
             element.setStyle(Q.Styles.RENDER_COLOR, "");
             alarmLabelUI();
             element.addUI(alarmUI, [{ property : "label2", propertyType : Q.Consts.PROPERTY_TYPE_CLIENT, bindingProperty : "data" }]);
     		 element.set("label2", "");
     }
    });
     alarmType="show";
   }
   
}

//创建节点
function createNode(node,x,y){
   var n=new Node(node.nodeName, x, y);
   n.id=node.nodeId;
   n.objType=node.objType;
   n.setSize(nodeImgWidth,nodeImgHeight);
   if(node.devStatus==-1){
	   if(node.devTypeCode==2){
		   node.objIcon="switch_red.svg";
	   }else if(node.devTypeCode==3){
		   node.objIcon="router_red.svg";
	   }else if(node.devTypeCode==4){
		   
	   }
   }else if(node.devStatus>0){
	   if(node.devTypeCode==2){
		   node.objIcon="switch_yellow.svg";
	   }else if(node.devTypeCode==3){
		   node.objIcon="router_yellow.svg";
	   }else if(node.devTypeCode==4){
		   
	   }
   }
   n.devStatus=node.devStatus;
   n.image=svgImgPath+node.objIcon;
   graph.add(n);
   
//   n.addEventListener('mouseup', function(event){
//	});
   return n;
}

//	创建文本节点
function createText(host, name, x, y, anchorPosition, w, h, fontSize, fontColor, backgroundColor){
	var text = new TextNode(name, x, y);
	text.textId=host.id;
	graph.add(text);
	return text;
}

//创建拖拽节点
function createDragNode(name,x,y,image){
      var node=new Node(name,x,y);
      node.setSize(nodeImgWidth,nodeImgHeight);
      node.id=new Date().getTime();
      node.image=image;
      graph.add(node);
      return node;
}	

//创建edge
function drawLine(name,lineId,from ,to){
  var lineBrandWidth=name;
  lineBrandWidth=netbandwidth(Number(lineBrandWidth));
  var edge=graph.createEdge(lineBrandWidth,from ,to);
  edge.lineBandWidth=name;
  edge.lineId=lineId;
  edge.setStyle(Q.Styles.ARROW_TO,false);
  
//	  edge.setStyle(Q.Styles.EDGE_COLOR,"red");//连线颜色
//	  edge.setStyle(Q.Styles.EDGE_WIDTH,1);//连线宽度 
//	  edge.validateEdgeBundle = function(){
//     var edgeBundle = this.getEdgeBundle(true);
//     if(!edgeBundle || edgeBundle.agentEdge != this){
//         resetEdgeStyles(this);
//         return;
//     }
//     setBundleStyles(this, edgeBundle);
// };
  
  return edge;
}

//根据Topo_line的 信息，在拓扑图上 画线;
//flag = 0: 只画线 ，flag=1: 画线 并 显示线路接口 信息
function drawLineAndIfName(bandwidth,fromNode,fromIfName,toNode,toIfName,flag){
  name=netbandwidth(Number(name));
  var edge=graph.createEdge(name,from ,to);
  edge.setStyle(Q.Styles.ARROW_TO,false);
  return edge;
}


//从数据库中load数据
function loadTopo(graphID){
	$("#trTxt").removeClass("dN");
    graph.clear();
    graph.childs=[];
    var lines,loadNodes,lineInfos,linesInfoArray,lans,lanNodes;
     var nodeArray=new Array();
         $.ajax({
    		type : 'post',
    		url: proName+'/topo/graphManage/getData.do?graphID='+graphID,
    		dataType: 'json',
    		cache : false, 
    		async: false,
    		success: function(data) {
       			if(data.graphStr!=null&&data.nodeStr!=null){
       				lines= data.lineStr;//该画布所有的线
       				loadNodes=data.nodeStr;//该画布所有的节点
       				lineInfos=data.lineInfoStr;//该画布中线所连接的ab节点
//       				lans=data.lanStr;
//       				lanNodes=data.lanNodeStr;
       				
//       				trunkMap=data.trunkMap;
//       			 	return;
       			}
    		}
    	});
         if(lineInfos){
        	 linesInfoArray=lineInfos.split(";");
         }
    	
    	for(var i=0;i<loadNodes.length;i++){
    	  var n=loadNodes[i];
    	   var node= createNode(n,n.posX,n.posY);
		   nodeArray.push(node);
    	   createText(node, n.nodeName, n.namePosX,n.namePosY);//创建节点跟随文本节点
    	}
    	
    	
    	var edgeArray=[];
    	for(var i=0;i<lines.length;i++){
    	   var link=lines[i];
    	   var topoNodeA=getNodeById(link.nodeAId);
    	   var topoNodeB=getNodeById(link.nodeBId);
		   if(topoNodeA!=null&&topoNodeB!=null){
//			  var edge=new Edge(link.ifNameA+"</br>"+link.nodeAName+"</br></br>"+link.ifNameB+"</br>"+link.nodeBName,topoNodeA,topoNodeB);
			  var edge=new Edge(link.lineName,topoNodeA,topoNodeB);
		      edge.id=link.lineId;
//		      if(topoNodeA.devStatus<0||topoNodeB.devStatus<0){
//		    	  edge.color="#BC0C15";
//		      }
		      graph.add(edge);
		      edgeArray.push(edge);
		      
		   }
    	}
    	
    	
//    	   for(var key in trunkMap){
//    		   var trunkEdge=[];
//    		   for(var i in trunkMap[key]){ 
//    			     for(var j=0;j<edgeArray.length;j++){
//    			    	 var link=edgeArray[j];
//    			    	 if(trunkMap[key][i]==link.id){
//    			    		 trunkEdge.push(link);
//    			    	 }
//    			     }
//    			  } 
//    		   if(trunkEdge.length>1){
//    			   var nodeA=getNodeById(trunkEdge[0].nodeAId);
//    			   var nodeB=getNodeById(trunkEdge[0].nodeBId);
//    			   var nodeAId=key.split("<>")[1];
//    			   var nodeAName=key.split("<>")[2];
//    			   var nodeBId=key.split("<>")[3];
//    			   var nodeBName=key.split("<>")[4];
//    			   var leftName;
//    			   var rigthName;
//    			   if(nodeA.x<nodeB.x){
//    				   leftName=nodeAName;
//    				   rigthName=nodeBName;
//    			   }else{
//    				   leftName=nodeBName;
//    				   rigthName=nodeAName;
//    			   }
//    			   var rLine=new RelateLine(leftName,rigthName,trunkEdge);
//    			   rLine.id=key.split("<>")[0];
//    			   graph.add(rLine);
//    		   }
//    	   }
    	
    	
//    	for(var i=0;i<lans.length;i++){
//    		var l=lans[i];
//    		var lan=new LAN(l.lanName,l.posX1,l.posY1,l.posX2,l.posY2);
//    		lan.id=l.id;
//    		graph.add(lan);
//    		
//            var lanTextNode=new TextNode(l.lanName,l.namePosX,l.namePosY);
//             
//    		 lanTextNode.textId =l.id;
//             graph.add(lanTextNode);
//    	}
//    	
//    	for(var i=0;i<lanNodes.length;i++){
//    		var n=lanNodes[i];
//    		var topoNode=getNodeById(n.nodeId);
//    		var lan=getLanById(n.lanId);
//    		if(topoNode&&lan){
//    			var lanNode=new LanNode(n.name,topoNode,lan);
//        		lanNode.id=n.id;
//        		lanNode.startName=n.startName;
//        		lanNode.sNamePosX=n.sNamePosX;
//        		lanNode.sNamePosY=n.sNamePosY;
//        		lanNode.endName=n.endName;
//        		lanNode.eNamePosX=n.eNamePosX;
//        		lanNode.eNamePosY=n.eNamePosY;
//        		graph.add(lanNode);
//        		
//        		 if(n.startName){
//        			 var lanNodeText1=new TextNode(n.startName,n.sNamePosX,n.sNamePosY);
//             		lanNodeText1.textId =parseInt(lanNode.id)+1;
//             		graph.add(lanNodeText1);
//        		 }
//        			
//            		
//        		 if(n.endName){
//        			 var lanNodeText2=new TextNode(n.endName,n.eNamePosX,n.eNamePosY);
//             		lanNodeText2.textId =lanNode.id;
//             		graph.add(lanNodeText2);
//        		 }
//        		
//        		
//    		}
//    		
//    	}
    	
    	$("#trTxt").addClass("dN");
  }

function loadTopoByGraphID(graphID){
    graph.clear();
    var lines,loadNodes,lineInfos,linesInfoArray;
    
     var nodeArray=new Array();
         $.ajax({
    		type : 'get',
    		url: proName+'/topo/graph/getDataById.do?graphId='+graphID,
    		dataType: 'json',
    		cache : false, 
    		async: false,
    		success: function(data) {
       			if(data.graphStr!=null&&data.nodeStr!=null){
       				lines= data.lineStr;
//        				graphObj=data.graphStr;
       				loadNodes=data.nodeStr;
       				lineInfos=data.lineInfoStr;
       			 	return;
       			}
    		}
    	});
    	
    	
    	linesInfoArray=lineInfos.split(";");
    	
    	for(var i=0;i<loadNodes.length;i++){
    	  var n=loadNodes[i];
    	   var node= createNode(n,n.posX,n.posY);
//     	   node.layoutType = Q.Consts.LAYOUT_TYPE_TWO_SIDE;
		   nodeArray.push(node);
    	   createText(node, n.nodeName, n.namePosX, n.namePosY);//创建节点跟随文本节点
    	   
    	}
    	
    	
    	for(var i=0;i<lines.length;i++){
    	   var link=lines[i];
    	   var topoNodeA=getNodeById(link.nodeAId);
    	   var topoNodeB=getNodeById(link.nodeBId);
    	   
		   if(topoNodeA!=null&&topoNodeB!=null){
		      var edge=drawLine(link.lineBandwidth,link.lineId,topoNodeA,topoNodeB);
		      addLabelUI(link,edge);
		   }
    	  
    	}
    	
     }


function getAutoData(){
	$.ajax({
	    		type : 'post',
	    		url: proName+'/topo/graphManage/autoLoad.do?graphId='+graphID,
	    		dataType: 'json',
	    		cache : false, 
	    		async: false,
	    		success: function(data) {
	       				links= data.lineStr;
	       				graphObj=data.graphStr;
	       				nodes=data.nodeStr;
					 levelNodes=data.result.lineArray;
	 				unilines=data.result.unilinelist;
	 				depth=data.result.depth;
	 				trunkMap=data.trunkMap;
	    		}
	    	});
	    	
	    	for(var i=0;i<nodes.length;i++){
	    	  aloneNodeIds.push(nodes[i].nodeId);
	    	}
    		
   }
//通过节点ID 返回 该节点对应的 文本节点ID
function getTextNodeByNode(nodeid){
  var textNode;
  graph.childs.forEach(function(node){
   if(node.elementType=="TextNode"){
      if(node.textId==nodeid){
        textNode= node;
      }
   }
  });
  
  return textNode;
}

//从nodes中获取 node
function getNodeByNodes(nodeId){
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].nodeId==nodeId){
			return nodes[i];
		}
	}
	return null;
}

//图片拖拽事件
function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
//	    ev.dataTransfer.setData("image", ev.target.getAttribute("image"));
//	    ev.dataTransfer.setData("type", ev.target.getAttribute("type"));
//	    ev.dataTransfer.setData("label", ev.target.getAttribute("label"));
//	    ev.dataTransfer.setData("objType", ev.target.getAttribute("objType"));
    ev.dataTransfer.setData("text", ev.target.getAttribute("image")+","+ev.target.getAttribute("type")+","+ev.target.getAttribute("label")+","+ev.target.getAttribute("objType"));
    
}
	
function drop(ev) {
    ev.preventDefault();
	var text=ev.dataTransfer.getData("text");
	var eles=text.split(",");
	
	var image = eles[0];
    var type = eles[1];
    var label = eles[2];
    var objType=eles[3];
    var locXY=windowTocanvas(graph.canvas,ev.pageX,ev.pageY);
    var x=locXY.x;
    var y=locXY.y;
    if(type == "Node"){
        var node =createDragNode(label,x,y,image);
        node.nodeInfo=objType;
        var objtype=objType.substring(objType.lastIndexOf("#")+1,objType.length);
        node.objType=objtype;  //用来区分创建的node对象类型  0：子图；1：节点；2：线路；3：网段
        node.id=new Date().getTime();
        createText(node, label, node.x+node.width/2,node.y+node.height+14);
    }
    
}

/* 右键菜单处理   Node  */	
$("#contextMenu a").click(function(evt){
	var text = $(this).text();
	if(text == '删除该节点'){
		window.parent.$.ligerDialog.confirm('是否确认删除该节点信息！', function (yes){
			if(yes) {
					if(currentNode.nodeInfo!=null){  //新拖拽节点，还未入库
					   graph.removeElement(currentNode);
					}else{   //数据库中load出来的数据
					   $.ajax({
					      url:proName+"/topo/topoNode/deleteNode.do?nodeId="+currentNode.id+'&graphId='+graphID,
					      dataType:'html',
					      success:function(data){
					         if(data=="success"){
//					             window.parent.$.ligerDialog.success("节点删除成功!");
					            graph.removeElement(currentNode);
//					        	 graph.drawData();
					         }
					      }
					   });
					}
			}
		});
		
	}
	if(text == '删除该设备'){
	  window.parent.$.ligerDialog.confirm('是否确认删除该设备信息！', function (yes){
			if(yes) {
				 if(currentNode.nodeInfo!=null){
				    graph.removeElement(currentNode);
				 }else{
				   $.ajax({
				      url:proName+"/topo/topoNode/deleteDevice.do?nodeId="+currentNode.id+'&graphId='+graphID,
				      dataType:'html',
				      success:function(data){
				        if(data=="success"){
//					            window.parent.$.ligerDialog.success("设备删除成功!");
					            graph.removeElement(currentNode);
					         }else{
					        	 window.parent.$.ligerDialog.warn("未曾搜索到该设备");
					         }
				      }
				   });
				 }
			}
		});
	}
	if(text == '修改设备IP'){
		$.ajax({
	        url:proName+'/topo/topoNode/getByID.do?nodeID='+currentNode.id,
	        dataType:'json',
	        async:false,
	        success:function(data){
//	          if(data.devStr!=null&&data.devStr!=""){
	            var node=data.nodeStr;
	            if(node!=null){  
//	                if(node.devTypeCode==2||node.devTypeCode==5){  //交换机，路由交换
//	                    var url = timeURL(proName+'/topo/topoNode/edit.do?id='+node.devID+'&topoEdit=1');
	                    var url = timeURL(proName+'/topo/topoNode/edit.do?id='+node.nodeId);
							window.parent.showDlg('修改设备IP', 400, 200, url); 
//	                }else{  //路由器及其他设备
//	                	var url = timeURL(proName+'/fas/res/net/device/edit.do?id='+node.devID+'&topoEdit=1');
//							window.parent.showDlg('路由器信息', 800, 650, url);    
//	            } 
//	            }
	          }
		else{
	               window.parent.$.ligerDialog.warn("未曾搜索到该设备");
	          }
	        }
	      });
		
	}
	
	if(text == '顺时针旋转'){
		currentNode.rotate += 0.5;
	}else if(text == '逆时针旋转'){
		currentNode.rotate -= 0.5;
	}else if(text == '放大'){
	   
 		 var localXY = graph.globalToLocal(evt);
 		 graph.zoomIn(localXY.x, localXY.y, true);
		
	}else if(text == '缩小'){
		 var localXY = graph.globalToLocal(evt);
 		 graph.zoomOut(localXY.x, localXY.y, true);
		
	}else if(text == '创建节点文字'){
		newNodeText(currentNode.x+50,currentNode.y-30,currentNode._id,'创建文字节点','');
	}else if(text == '查看设备信息'){
	      $.ajax({
	        url:proName+'/topo/topoNode/getByID.do?nodeID='+currentNode.id,
	    	dataType:'json',
	        async:false,
	        success:function(data){
	          if(data.devStr!=null&&data.devStr!=""){
	            var node=data.nodeStr;
	            if(node.devClassCode==13){  
//	                if(node.devTypeCode==2||node.devTypeCode==5){  //交换机 ,路由交换
//	                   var url = timeURL(proName+'/fas/res/net/switch/view.do?id='+node.devID);
//	                   window.parent.showDlg('交换机信息', 800, 600,url); 
//	                }else{  //路由器及其他设备
//	                	var url = timeURL(proName+'/fas/res/net/device/view.do?id='+node.devID);
//						window.parent.showDlg('路由器信息', 800,600, url);    
//	           	 } 
	            	var url = proName+'/fas/res/net/devices/view.do?id='+node.devID;
	         		layer_show('设备信息', url, 800, 500);     
	            }
	          }else{
	             window.parent.$.ligerDialog.warn("未曾搜索到该设备");
	          }
	            
	        }
	      });
	    
	}else if(text == '修改设备信息'){
	 	    $.ajax({
	        url:proName+'/topo/topoNode/getByID.do?nodeID='+currentNode.id,
	        dataType:'json',
	        async:false,
	        success:function(data){
	          if(data.devStr!=null&&data.devStr!=""){
	            var node=data.nodeStr;
	            if(node.devClassCode==13){  
//	                if(node.devTypeCode==2||node.devTypeCode==5){  //交换机，路由交换
//	                    var url = timeURL(proName+'/fas/res/net/switch/edit.do?id='+node.devID+'&topoEdit=1');
//							window.parent.showDlg('修改交换机', 800, 650, url); 
//	                }else{  //路由器及其他设备
//	                	var url = timeURL(proName+'/fas/res/net/device/edit.do?id='+node.devID+'&topoEdit=1');
//							window.parent.showDlg('路由器信息', 800, 650, url);    
//	            } 
	            	var url = proName+'/fas/res/net/devices/edit.do?id='+node.devID+'&topoEdit=1';
	         		layer_show("修改设备", url, 800, 500);   
	            }
	          }else{
	               window.parent.$.ligerDialog.warn("未曾搜索到该设备");
	          }
	        }
	      });
	 	
	}else if(text == '查看性能指标'){
	    $.ajax({
	        url:proName+'/topo/topoNode/getByID.do?nodeID='+currentNode.id,
	        dataType:'json',
	        async:false,
	        success:function(data){
	          if(data.devStr!=null&&data.devStr!=""){
	           var node=data.nodeStr;
	            if(node.devClassCode==13){  
					 onTrendTotal(node.devID,'cpu_Percent1Min','',node.devTypeCode);
	            }
	          }else{
	              window.parent.$.ligerDialog.warn("未曾搜索到该设备");
	          }
	        }
	      });
	}else if(text == '查看设备告警'){
	     $.ajax({
	        url:proName+'/topo/topoNode/getByID.do?nodeID='+currentNode.id,
	        dataType:'json',
	        async:false,
	        success:function(data){
	          if(data.devStr!=null&&data.devStr!=""){
	           var node=data.nodeStr;
			 		var url= timeURL(proName+'/alarm/analysis/alermLogsMain.do?date='+new Date()+'&resID='+node.devID);
	   			 window.open(url,'', 'height=460, width=1280, top=160, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
	          }else{
	              window.parent.$.ligerDialog.warn("未曾搜索到该设备");
	          }
	        }
	      });
	}else if(text=='查看设备接口'){
	    $.ajax({
	        url:proName+'/topo/topoNode/getByID.do?nodeID='+currentNode.id,
	        dataType:'json',
	        async:false,
	        success:function(data){
	          if(data.devStr!=null&&data.devStr!=""){
	           var node=data.nodeStr;
		             if(node.devClassCode==13){  
	                var url = proName+'/fas/res/net/devices/ifDetail.do?id='+node.devID;
	    			window.open(url,'', 'height=400, width=960, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
		           }
	          }else{
	              window.parent.$.ligerDialog.warn("未曾搜索到该设备");
	          }
	        }
	      });
	}else if(text=='Telnet访问'){
		$("#contextMenu").hide();
		$("#trTxt").removeClass("dN");
		 $.ajax({
		        url:proName+'/topo/topoNode/telnetAccess.do?nodeId='+currentNode.id,
		        dataType:'json',
		        async:false,
		        success:function(data){
		        	$("#trTxt").addClass("dN");
		          if(data.result.length>0){
		        	  window.parent.$.ligerDialog.warn(data.result);
		          }
		        }
		      });
		
	}
	$("#contextMenu").hide();
});

/* 右键菜单处理   line  */	
$("#linkmenu a").click(function(evt){
  var text = $(this).text();
  if(text=='编辑线路信息'){
	  //alert(currentNode.id+"id");
	  //if(currentNode.lineId!=null){   //数据存已经存在该条line
	  if(currentNode.id!=null){   //数据存已经存在该条line
		//alert(currentNode.id+"+++");
		 var url = timeURL(proName+'/topo/topoLine/editLine.do?lineId='+currentNode.id+'&graphId='+graphID);
//	  		window.parent.showDlg('编辑线路信息', 800, 380, url);    
	  		layer_show("编辑线路信息", url, 800, 500);   
		}else{ //新建line还没入库
			//alert("...");
		    var nodeAId=currentNode.nodeAId;
		    var nodeBId=currentNode.nodeBId;
		     var url = timeURL(proName+'/topo/topoLine/editNewLine.do?nodeAId='+nodeAId+'&nodeBId='+nodeBId+'&graphId='+graphID);
//	  		window.parent.showDlg('编辑线路信息', 800, 380, url);  
	  		layer_show("编辑线路信息", url, 800, 500);  
	   }
    }else if(text=='查看线路信息'){
            var url = timeURL(proName+'/topo/topoLine/view.do?lineId='+currentNode.id+'&graphId='+graphID);
//	  		window.parent.showDlg('查看线路信息', 800, 380, url);    
	  		layer_show("查看线路信息", url, 800, 500);   
    }else if(text=='删除连线'){
    
      window.parent.$.ligerDialog.confirm('是否确认删除该连线！', function (yes){
        if(yes){
            if(currentNode.id!=null){
		         $.ajax({
		           url:proName+"/topo/topoLine/deleteLine.do?lineId="+currentNode.id+'&graphId='+graphID+'&type='+currentNode.elementType,
		           dataType:'json',
		           success:function(data){
		              if(data.result=="success"){
	  						 window.parent.$.ligerDialog.success("连线删除成功!");
	 						 graph.removeElement(currentNode);
	 						 	saveTopo();
	 						}
						}
		         });
		       }
            	else{
		         graph.removeElement(currentNode);
		       }
        	}
      	});
    }
    $("#linkmenu").hide();
});

/* 右键菜单处理   subnet  */	
$("#subNetmenu a").click(function(evt){
  var text = $(this).text();
  if(text=='查看子拓扑图'){
    var url = timeURL(proName+'/topo/graph/subnetGraph.do?nodeId='+currentNode.id);
		window.open(url,'', 'height=550, width=1280, top=160, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
    
  }else if(text=='编辑子拓扑图'){
     showPopup(1460,900);
     getGraph(graphID); 
     
     var saveNodes=[];
     var nodeObj = new Object();
	 var node=currentNode;
	 
	 var textNode=getTextNodeByNode(node.id);	            
	 nodeObj.node_Name=node.name;
	 nodeObj.node_ID=node.id;
	 if(node.nodeInfo!=null){
		nodeObj.nodeInfo=node.nodeInfo;
	 }
	 nodeObj.posX=node.x;
	 nodeObj.posY=node.y;
	 nodeObj.namePosX=textNode.x;
	 nodeObj.namePosY=textNode.y;
     saveNodes.push(nodeObj);
     
     subnetStr = JSON.stringify(saveNodes);
  }else if(text=='删除子拓扑图'){
    window.parent.$.ligerDialog.confirm('是否确认删除该子拓扑图！', function (yes){
				if(yes) {
						if(currentNode.nodeInfo!=null){  //新拖拽节点，还未入库
						   graph.removeElement(currentNode);
						}else{   //数据库中load出来的数据
						   $.ajax({
						      url:proName+"/topo/topoNode/deleteNode.do?nodeId="+currentNode.id+"&graphId="+graphID,
						      dataType:'html',
						      success:function(data){
						         if(data=="success"){
//						             window.parent.$.ligerDialog.success("节点删除成功!");
						            graph.removeElement(currentNode);
						         }
						      }
						   });
						}
				}
			});
  }
  
  $("#subNetmenu").hide();
});


function onTrendTotal(id,param,endTime,devType){
		var url = timeURL(proName+'/fas/analysis/performance/perMonitor/trends.do?deviceId='+id+'&devType='+devType+'&queryParam='+param+'&endTime='+endTime+'&initQuery=yes&queryType=total');
	window.parent.parent.showDlg('趋势', 890, 560, url);  
} 

function autoLoad(seedNodeId){
	   loadSubNodes(seedNodeId,0);
	   for(var i=0;i<aloneNodeIds.length;i++){
	     // var aloneNode=getNodeById(aloneNodeIds[i]);
	      var aloneNode=getNodeByNodes(aloneNodeIds[i]);
	      if(aloneNode){
	    	  var topoParentNode=createNode(aloneNode,rootNodeX+(i+nodeIds.length)*xgap,(rootNodeY+ygap));
		 	  createText(topoParentNode, aloneNode.nodeName, rootNodeX+(i+nodeIds.length)*xgap, (rootNodeY+ygap+60));
	      }
	   }
	   
	   var edgeArray=[];
	   
	   for(var i=0;i<links.length;i++){
	    	   var link=links[i];
	    	   var topoNodeA=getNodeById(link.nodeAId);
	    	   var topoNodeB=getNodeById(link.nodeBId);
	    	   
			   if(topoNodeA!=null&&topoNodeB!=null){
//	    	      var edge=new Edge(link.ifNameA+"</br>"+link.nodeAName+"</br></br>"+link.ifNameB+"</br>"+link.nodeBName,topoNodeA,topoNodeB);
//				  var contexts=link.lineName.split("_");
	    	      var edge=new Edge(link.lineName,topoNodeA,topoNodeB);
			      edge.id=link.lineId;
			      graph.add(edge);
			      edgeArray.push(edge);
			   }
	    	  
	    	} 
	   
}

//节点对齐

//取节点最小值
function getMin(tmp,coords){
 	var min = tmp[0];
	if(coords=='x'){
 	  for(var i=1;i<tmp.length;i++){ 
		  if(min.x>tmp[i].x)
		     min=tmp[i];
		  }
 	}else{
 	   for(var i=1;i<tmp.length;i++){ 
		  if(min.y>tmp[i].y)
		     min=tmp[i];
		  }
 	}
  return min;
}

//取节点最大值
function getMax(tmp,coords){
 	var max = tmp[0];
 	if(coords=='x'){
 	  for(var i=1;i<tmp.length;i++){ 
		  if(max.x<tmp[i].x)
		     max=tmp[i];
		  }
 	}else{
 	   for(var i=1;i<tmp.length;i++){ 
		  if(max.y<tmp[i].y)
		     max=tmp[i];
		  }
 	}
  return max;
}




//通过节点的nodeId 获取图元node
function getNodeById(nodeId){
	var targetNode;
	 for(var i=0;i<graph.childs.length;i++){
	 	var obj=graph.childs[i];
	 
	 	if(obj.elementType=="Node"&&nodeId==obj.id){
	 	
	 		targetNode=obj;
	 		break;
	 	}
	 }
  
  return targetNode;
}


//获取LAN
function getLanById(lanId){
	
	var targetLan;
	 for(var i=0;i<graph.childs.length;i++){
	 	var obj=graph.childs[i];
	 	if(obj.elementType=="LAN"&&lanId==obj.id){
	 		targetLan=obj;
	 		break;
	 	}
	 }
  
  return targetLan;
}

//自动加载时，每次创建一个节点时，都要去遍历该数组，看是否已经存在该节点
function findNodeById(nodeid){
  var flag=false;
 nodeIds= uniqueArray(nodeIds);
  for(var i=0;i<nodeIds.length;i++){
    if(nodeIds[i]==nodeid){
      flag=true;
      break;
    }
  }
  return flag;
}


//页面辅助功能  ，最后需要抽出来单独作为一个JS文件
//框选模式
//
  $(function(){
  		$('#select').click(function(){
	   changeGraphModel('Select');
	   $('#move').css("background-color","");
	   $('#addEdge').css("background-color","");
	   // $('#createLLine').css("background-color","");
	   $('#select').css("background-color","#fff");
	   
	   
	});
	
	//平移模式
	$('#move').click(function(){
		changeGraphModel('Move');
	   $('#select').css("background-color","");
	   $('#addEdge').css("background-color","");
	   // $('#createLLine').css("background-color","");
	   $('#move').css("background-color","#fff");
	   
	});

	//画直线
	$('#addEdge').click(function(){
			changeGraphModel('Edit');
		   $('#select').css("background-color","");
		   $('#move').css("background-color","");
		   // $('#createLLine').css("background-color","");
		   $('#addEdge').css("background-color","#fff");
		 
	});





  });	   

function closeWin(){
	layer.closeAll();
}


function saveSubnet(){
    var selectGraphId=$("#graphName").find("option:selected").val();
    var selectGraphName=$("#graphName").find("option:selected").text();
    saveSubnetNode(graphID,selectGraphId,selectGraphName,subnetStr);
}
