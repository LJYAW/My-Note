
	function Event(){
		
		this.currentNode=null;
		this.targetNode=null;
		this.targetLan=null;
		this.isDragDrap=false;
		this.isDragSelect=false;
		this.isMoveDrap=false;// 画布平移
		this.collideNode=null;
		this.collideEdge=null;
		this.diffX=0;
		this.diffY=0;

		this.selectSX; // 框选开始X坐标
		this.selectSY; // 框选开始Y坐标
		this.selectEX; // 框选结束X坐标
		this.selectEY; // 框选结束Y坐标
		this.currentX=0;
        this.currentY=0;
		this.selectArea=null;
		this.nodeA=null;
        this.nodeB=null;
		this.selectDataXY=[];
		this.edgeRelate=[];

		this.oldLeftName;
		this.oldRightName;
		this.hoverTimer;
        this.outTimer;
	}


	function Graph(c){

		this.canvas=document.getElementById(c);
		this.ctx=this.canvas.getContext("2d");
		this.edgeMap=new Map();
		this.childs=[];
		this.elementType = "graph";
		this.event=new Event();
		this.graphModel="Move";
		this.nodeA=[];
		this.selectedData=[];
		this.brokenEdge=[];
		this.EdgeText=[];
		this.canvas.width=this.canvas.offsetWidth;
		this.canvas.height=this.canvas.offsetHeight;
		 // 放大缩小比例
		this.scaleX=0.95;
		this.scaleY=0.95;
	    this.nodeX=0;
	    this.nodeY=0;
	    this.edgeX=0;
	    this.edgeY=0;
	    this.currentX=0;
	    this.currentY=0;
	    this.nodeLanX=null;
	    this.nodeLanY=null;
	    this.currentWEdgeY1=null;
	    this.currentWEdgeY2=null;
	    this.currentHEdgeY1=null;
	    this.currentHEdgeY2=null;
	    this.edgeX1=null;
	    this.edgeY1=null;
	    this.edgeX2=null;
	    this.edgeY2=null;
	    this.moveH=false;
	    this.moveWO=false;
	    this.moveWT=false;
        this.isDblText=false;
        this.lineName=false;
        this.brokenLinename=false;
		this.add=function(obj){
			
			this.childs.push(obj);
		
			if(obj.elementType=="Node"){
				this.drawNode(obj);
// obj.paint();
			}
			else if(obj.elementType=="Edge"){
				
				graph.drawData();
			}else if(obj.elementType=="RelateLine"){
                obj.paint();
            }else if(obj.elementType=="LAN"){
                
                obj.paint();

            }else if(obj.elementType=="LanNode"){
                
                obj.paint();

            }else if(obj.elementType=="TextNode"){
				obj.paint();
			}else if(obj.elementType=="TextRank"){
                obj.paint();
            }else if(obj.elementType=="TextVertical"){
                obj.paint();
            }else if(obj.elementType=="EdgeBroken"){
                graph.drawData();
            }	
			
		}
		
		   // 移除节点
        this.removeElement=function(obj){
        	if(obj.elementType=="Node"){
        		 graph.childs.remove(obj);
        		 var textNode=getTextNodeByNode(obj.id);
        		 if(textNode){
        			 graph.childs.remove(textNode);
        		 }
        		 var lanNodes=getLanNodesByNodeId(obj.id);
        		 if(lanNodes){
        			 lanNodes.forEach(function(e){
        				 graph.childs.remove(e);
        			 });
        			
        		 }
        		 
        	}else if(obj.elementType=="Edge"){
        		 graph.childs.remove(obj);
        	}else if(obj.elementType=="TextNode"){
                graph.childs.remove(obj);
            }else if(obj.elementType=="EdgeBroken"){
                 graph.childs.remove(obj);                
            }else if(obj.elementType=="LAN"){
        		 graph.childs.remove(obj);
        		 var textNode=getTextNodeByLan(obj.id);
        		 if(textNode){
        			 graph.childs.remove(textNode);
        		 }
        		 
        		 var lanNodes=getLanNodesByLanId(obj.id);
        		 if(lanNodes){
        			 lanNodes.forEach(function(e){
        				 graph.childs.remove(e);
        	            });
        		 }
        		 
        	}else if(obj.elementType=="LanNode"){
        		 graph.childs.remove(obj);
        	}else if(obj.elementType=="TextRank"){
        		graph.childs.remove(obj);
            }else if(obj.elementType=="TextVertical"){
            	graph.childs.remove(obj);
            }else if(obj.elementType=="RelateLine"){
            	graph.childs.remove(obj);
            }
            
            graph.drawData();
        }
		
		// 获取画布上所有节点
		this.getNodes=function(){
			var array=new Array();
			for(var i=0;i<graph.childs.length;i++){
				var obj=graph.childs[i];
				if(obj.elementType=="Node"){
					array.push(obj);
				}
			}
			return array;
		}

		// 获取画布上所有连线
		this.getEdges=function(){
			var array=new Array();
			for(var i=0;i<graph.childs.length;i++){
				var obj=graph.childs[i];
				if(obj.elementType=="Edge"){
					array.push(obj);
				}
			}
			return array;
		}
		//获取画布上所有节点连线
        this.getBrokenEdges=function(){
            var array=new Array();
            for(var i=0;i<graph.childs.length;i++){
                var obj=graph.childs[i];
                if(obj.elementType=="EdgeBroken"){
                    array.push(obj);
                }
            }
            return array;
        }
		//获取画布上所有关联
        this.getRelateLines=function(){
            var array=new Array();
            for(var i=0;i<graph.childs.length;i++){
                var obj=graph.childs[i];
                if(obj.elementType=="RelateLine"){
                    array.push(obj);
                }
            }
            return array;
        }
		// 获取画布上所有虚拟连线
        this.getLans=function(){
            var array=new Array();
            for(var i=0;i<graph.childs.length;i++){
                var obj=graph.childs[i];
                if(obj.elementType=="LAN"){
                    array.push(obj);
                }
            }
            return array;
        }

        // 获取画布上所有节点特殊连线
        this.getLanNodes=function(){
            var array=new Array();
            for(var i=0;i<graph.childs.length;i++){
                var obj=graph.childs[i];
                if(obj.elementType=="LanNode"){
                    array.push(obj);
                }
            }
            return array;
        }
        
        
      // 获取画布上所有横向文本
        this.getTextRanks=function(){
            var array=new Array();
            for(var i=0;i<graph.childs.length;i++){
                var obj=graph.childs[i];
                if(obj.elementType=="TextRank"){
                    array.push(obj);
                }
            }
            return array;
        }
         // 获取画布上所有竖向文本
        this.getTextVerticals=function(){
            var array=new Array();
            for(var i=0;i<graph.childs.length;i++){
                var obj=graph.childs[i];
                if(obj.elementType=="TextVertical"){
                    array.push(obj);
                }
            }
            return array;
        }
		// 获取画布上所有文本节点
		this.getTextNodes=function(){
			var array=new Array();
			for(var i=0;i<graph.childs.length;i++){
				var obj=graph.childs[i];
				if(obj.elementType=="TextNode"){
					array.push(obj);
				}
			}
			return array;
		}

		// 获取画布上所有文本节点
		this.getTextNodes=function(){
			var array=new Array();
			for(var i=0;i<graph.childs.length;i++){
				var obj=graph.childs[i];
				if(obj.elementType=="TextNode"){
					array.push(obj);
				}
			}
			return array;
		}


		// 获取画布上所有文本节点
		this.getRelateLines=function(){
			var array=new Array();
			for(var i=0;i<graph.childs.length;i++){
				var obj=graph.childs[i];
				if(obj.elementType=="RelateLine"){
					array.push(obj);
				}
			}
			return array;
		}
		
		
		// 获取画布上的节点以及文本节点
		this.getAllNodes=function(){
			var array=new Array();
			for(var i=0;i<graph.childs.length;i++){
				var obj=graph.childs[i];
				if(obj.elementType!="Edge"&&obj.elementType!="EdgeBroken"){
					array.push(obj);
				}
			}
			return array;
		}


		this.getElement=function(){
			var elements=this.childs;
			return elements;
		}

		this.drawNode=function(node){
			var img=new Image();
        	img.src=node.image;
        	img.onload=function(){
        		graph.ctx.drawImage(img, node.x, node.y,node.width,node.height);
        	}
		}
		
		
		// 获取两节点之前连线
		this.getEdgesByTwoNodes=function(nodeA,nodeB){
			var edges=graph.getEdges();
			var targetEdges=new Array();
			for(var i=0;i<edges.length;i++){
				var edge=edges[i];
				if((edge.nodeAId==nodeA.id&&edge.nodeBId==nodeB.id)||edge.nodeAId==nodeB.id&&edge.nodeBId==nodeA.id){
					targetEdges.push(edge);
				}
			}
			return targetEdges;
		}
		
		//获取两节点之间折线连线
        this.getBrokenEdgesByTwoNodes=function(nodeA,nodeB){
            var edges=graph.getBrokenEdges();
            var targetEdges=new Array();
            for(var i=0;i<edges.length;i++){
                var edge=edges[i];
                if((edge.nodeAId==nodeA.id&&edge.nodeBId==nodeB.id)||(edge.nodeAId==nodeB.id&&edge.nodeBId==nodeA.id)){
                    targetEdges.push(edge);
                }
            }
            return targetEdges;
        }
        this.computeTextRotate = function (dx,dy,angle){
            //第一步,取得夹角,x轴正方向为0度的顺时针0-360范围
            if( dx==0 || dy==0 ){
              if( dx < 0 && dy==0){
                angle = 270;
              }
              if( dy < 0 && dx==0){
                angle = 180;
              }
              if(dx > 0 && dy==0){
                angle = 270;
              }
              if(dy > 0 && dx==0){
                angle = 180;
              }
              
            }else{
              if( dx > 0 && dy > 0){        //4
                angle = Math.abs(360-angle);
              }else if( dx > 0 && dy < 0 ){ //1
                angle = Math.abs(180 - angle);
              }else if( dx < 0 && dy > 0 ){ //3
                angle = Math.abs(180 - angle);
              }else if( dx < 0 && dy < 0 ){ //2
                angle = Math.abs(360 -angle);
              }
            }
            //第二步,转换成context.rotate适用的的y轴正方向为0度的0-360范围
            angle = angle+90 >= 360 ? (angle+90)%360 : angle+90;
            //第三步,角度变弧度
            return angle*Math.PI/180;
        }
		  this.getEdgeLableLocaltion=function(from,to){
			var dx = to.x - from.x,
			  	dy = to.y - from.y, 
			  	slope=dy/dx; 
			  	angle = (Math.atan(dx/dy)*180/Math.PI );
			  	angleR=graph.computeTextRotate(dx,dy,angle);
	        var labelX,labelY,radius,angle,slope,angleR;
	        var textWidth = graph.ctx.measureText(this.name).width, // 文字宽
	        textX1 = (from.x + from.width/2+to.x + to.width/2)/2;
            textY1 = (from.y + from.height/2+to.y + to.height/2)/2-2;
            textX=textX1-(textWidth)*Math.cos(Math.atan(dy/dx))
            // textY=textY1-(textWidth)*Math.sin(Math.atan(dy/dx))
            labelX=Math.abs(textX);
            labelY=Math.abs(textY1);	                
	        return {
	        	labelX:labelX,
	            labelY:labelY,
	            radius:radius,
	            angle:angle,
	            slope:slope
	        };
	      }

		//计算箭头平移位置
	         
	        this.computeArrowTranslate = function (dx,dy,radian,to){
	            //计算偏移
	            var ox = 0,
	                oy = 0,
	                arrawW=6,
	                arrawHalfW=arrawW/2,
	                x = to.x+to.width/2,
	                y = to.y+to.height/2,
	                radiusX = to.width/2;
	                radiusY = to.height/2;
	                angle=radian*180/Math.PI
	            if( dx == 0 ){
	                
	            	y = dy > 0 ? y-radiusY-arrawHalfW : y+radiusY-arrawHalfW ;
	                // y = dy > 0 ? y-radiusY: y+radiusY;


	            }else if( dy == 0 ){
	            	x = dx > 0 ? x-radiusX-arrawHalfW-2 : x+radiusX+arrawHalfW+2 ;
	                // x = dx > 0 ? x+radiusX: x-radiusX ;

	            }else if(Math.abs(angle)<30){
	                ox = Math.ceil( (radiusX+arrawHalfW+2)*Math.cos( Math.abs(radian) ) );
	                oy = Math.ceil( (radiusY+arrawHalfW+2)*Math.sin( Math.abs(radian) ) );

	               
	                x = dx > 0 ? x-ox : x+ox ;
	                y = dy > 0 ? y-oy : y+oy ;
	            }else if(Math.abs(angle)>30&&Math.abs(angle)<90){
	                ox = Math.ceil( (radiusX+arrawHalfW+2)*Math.cos( Math.abs(radian) ) );
	                oy = Math.ceil( (radiusY+arrawHalfW+2)*Math.sin( Math.abs(radian) ) );

	               
	                x = dx > 0 ? x-ox : x+ox ;
	                y = dy > 0 ? y-oy : y+oy ;
	            }else if(dx>0){
	                x=to.x;
	                y=to.y+to.width/2
	            }


	            else{
	                ox = Math.ceil( (radiusX-2*arrawHalfW)*Math.cos( Math.abs(radian) ) );
	                oy = Math.ceil( (radiusY-2*arrawHalfW)*Math.sin( Math.abs(radian) ) );

	               
	                x = dx > 0 ? x-ox : x+ox ;
	                y = dy > 0 ? y-oy : y+oy ;
	            }
	            return {x:x,y:y};
	        }
	          //计算箭头旋转角度
	        /*if(dx>0){
	                        graph.ctx.translate(to.x,to.y+to.width/2); //平移
	                    }else{
	                        graph.ctx.translate(to.x+to.width,to.y+to.width/2); //平移
	                    } */
	        this.computeArrowRotate = function (dx,dy,angle){
	            //第一步,取得夹角,x轴正方向为0度的顺时针0-360范围
	            if( dx==0 || dy==0 ){
	              if( dx < 0 ){
	                angle = 180;
	              }
	              if( dy < 0 ){
	                angle = 270;
	              }
	            }else{
	              if( dx > 0 && dy > 0){        //4
	                //angle = angle;
	              }else if( dx > 0 && dy < 0 ){ //1
	                angle = 360 - Math.abs(angle);
	              }else if( dx < 0 && dy > 0 ){ //3
	                angle = 180 - Math.abs(angle);
	              }else if( dx < 0 && dy < 0 ){ //2
	                angle = 180 + Math.abs(angle);
	              }
	            }
	            //第二步,转换成context.rotate适用的的y轴正方向为0度的0-360范围
	            angle = angle+90 >= 360 ? (angle+90)%360 : angle+90;
	            //第三步,角度变弧度
	            return angle*Math.PI/180;
	        }
		this.isContain=function(array,obj){
			var flag=false;
			for(var i=0;i<array.length;i++){
				if(obj==array[i]){
					flag=true;
					break;
				}
			}
			return flag;
		}

		 this.shadow=function(array){
        	for(var i=0;i<array.length;i++){
        		var obj=array[i];
        		if(obj.elementType=="Node"){
        			graph.ctx.save();
		        	var img=new Image();
		        	img.src=obj.image;
		        	graph.ctx.fillStyle =obj.fillStyle;
		        	graph.ctx.shadowColor = obj.shadowColor;
					graph.ctx.shadowOffsetX = obj.shadowOffsetX;
					graph.ctx.shadowOffsetY = obj.shadowOffsetY;
					graph.ctx.shadowBlur = obj.shadowBlur; 
		        	graph.ctx.drawImage(img, obj.x, obj.y,obj.width,obj.height);
					graph.ctx.restore();
        		}
        		
        	}
        	
        }

        this.cancelShadow=function(array){
// alert(array[0].id);
        	for(var i=0;i<array.length;i++){
        		var obj=array[i];
        		if(obj.elementType=="Node"){
        			
        			graph.ctx.save();
		        	var img=new Image();
		        	img.src=obj.image;
		        	obj.shadowColor='rgba(0,0,0,0)';
// alert(obj.shadowColor);
		        	graph.ctx.fillStyle =obj.fillStyle;
		        	graph.ctx.shadowColor = obj.shadowColor;
					graph.ctx.shadowOffsetX = obj.shadowOffsetX;
					graph.ctx.shadowOffsetY = obj.shadowOffsetY;
					graph.ctx.shadowBlur = obj.shadowBlur; 
		        	graph.ctx.drawImage(img, obj.x, obj.y,obj.width,obj.height);
					graph.ctx.restore();
        		}
        		
        	}
        }
        
    	// 放大
	  	this.zoomIn=function(){
	  		graph.childs.forEach(function(e){
            	if(e.elementType=="Node"){
            		e.x/=graph.scaleX;
                	e.y/=graph.scaleY;
                	e.width/=graph.scaleX;
                	e.height/=graph.scaleY;
                	e.updateNode(e);
            	}else if(e.elementType=="TextNode"){
	  				e.x/=graph.scaleX;
	  				e.y/=graph.scaleY;
	  				e.x1/=graph.scaleX;
	  				e.y1/=graph.scaleY;
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
  				}else if(e.elementType=="TextRank"){
                    e.x/=graph.scaleX;
                    e.y/=graph.scaleY;
                    e.size/=graph.scaleX;
                    e.updatetextRank(e);
                }else if(e.elementType=="TextVertical"){
                    e.x/=graph.scaleX;
                    e.y/=graph.scaleY;
                    e.size/=graph.scaleX;
                    e.updatetextVertical(e);
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
	  		graph.drawData();
	  	}

	  	// 缩小
	   	this.zoomOut=function(){
	   		graph.childs.forEach(function(e){
            	if(e.elementType=="Node"){
            		e.x*=graph.scaleX;
                	e.y*=graph.scaleY;
                	e.width*=graph.scaleX;
                	e.height*=graph.scaleY;
                	e.updateNode(e);
            	}else if(e.elementType=="TextNode"){
	  				e.x*=graph.scaleX;
	  				e.y*=graph.scaleY;
	  				e.x1*=graph.scaleX;
	  				e.y1*=graph.scaleY;
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
  				}else if(e.elementType=="TextRank"){
                    e.x*=graph.scaleX;
                    e.y*=graph.scaleY;
                    e.size*=graph.scaleX;
                    e.updatetextRank(e);
                }else if(e.elementType=="TextVertical"){
                    e.x*=graph.scaleX;
                    e.y*=graph.scaleY;
                    e.size*=graph.scaleX;
                    e.updatetextVertical(e);
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
	   		graph.drawData();
	   	}
        
        
		this.drawEdge=function(edge){	   

			var nodeA=getNodeById(edge.nodeAId);
			
    	   	var nodeB=getNodeById(edge.nodeBId);
    	   	
			graph.ctx.beginPath();
			
		  	graph.ctx.moveTo(nodeA.x+nodeA.width/2,nodeA.y+nodeA.height/2);
		 	graph.ctx.lineTo(nodeB.x+nodeB.width/2,nodeB.y+nodeB.height/2);
		  	graph.ctx.stroke();
		  	graph.ctx.closePath();


		  	var edge=new Edge('line',nodeA,nodeB);

		  	graph.add(edge);
		}

		this.extendCopy=function(c,p){
		  	for(var i in p){
		  		c[i]=p[i];
		  	}
		  	return c;
	  	}
		
		this.getElementsBound=function(childs){
				
			  		var left=getMin(childs,'x');
			  		var right=getMax(childs,'x');
			  		var top=getMin(childs,'y');
			  		var bottom=getMax(childs,'y');
			  		
			  		 return {  
			           left:left.x,   
			           right:right.x,
			           top:top.y,
			           bottom:bottom.y 
		       		} 
			  	}
		
		this.moveToCenter=function(leftDiff){
			var bounds=graph.getElementsBound(graph.childs);
			
// alert(bounds.left+"-"+bounds.right+"-"+bounds.top+"-"+bounds.bottom);
	        
	        var a=(bounds.right+bounds.left)/2;
	        var b=(bounds.bottom+bounds.top)/2;
	        
	        // 距离差需优化
	        var c = (graph.canvas.width-leftDiff)/2-40;
	        var d = graph.canvas.height/2-40;
	      
	        var diffx=c-a;
	        var diffy=d-b;
	        for(var i=0;i<graph.childs.length;i++){
	        	var obj=graph.childs[i];
	        	if(obj.elementType=="Node"){
	        		obj.x=obj.x+diffx;
	        		obj.y=obj.y+diffy;	        		
	        		obj.updateNode(obj);
	        	}else if(obj.elementType=="TextNode"){
	        		obj.x=obj.x+diffx;
	        		obj.y=obj.y+diffy;
	        		obj.x1=obj.x+diffx;
	        		obj.y1=obj.y+diffy;
	        		obj.updateNode(obj);
	        	}else if(obj.elementType=="LAN"){
	        		obj.x1=obj.x1+diffx;
	        		obj.y1=obj.y1+diffy;
	        		obj.x2=obj.x2+diffx;
	        		obj.y2=obj.y2+diffy;
	        		obj.updateLan(obj);
	        	}else if(obj.elementType=="LanNode"){
	        		obj.x1=obj.x1+diffx;
	        		obj.y1=obj.y1+diffy;
	        		obj.x2=obj.x2+diffx;
	        		obj.y2=obj.y2+diffy;
	        		obj.updateLanNode(obj);
	        	}
	        }
	        graph.drawData();
		}
		
		this.canvas.addEventListener('mousedown',function(e){
			var locXY=windowTocanvas(graph.canvas,e.pageX,e.pageY);
	   		var epageX=locXY.x;
	   	    var epageY=locXY.y;
//	   	    var finalEdge = null;
//	   	    alert('mousedown=='+graph.graphModel);
            if((graph.graphModel=="Move"||graph.graphModel=="BrokenLine_name")&&e.button==0){  // 平移模式时的拖动
               graph.event.collideNode=null;
               graph.event.isDragDrap = true;
               var obj=graph.getElementByMouseEvent(e);
               var objE=graph.getEdgeByMouseEvent(e);
               //  折线时鼠标点击出现移动点
               if(objE){                    
                   //折线移动点 
                   graph.selectedData=[];
                   graph.drawData();                                      
                   graph.event.collideEdge=objE;                    
                   graph.canvas.style.cursor="pointer"  
                   if(objE.elementType=="EdgeBroken"){
                   //将有移动点的折线放到数组里
                   graph.brokenEdge.push(objE);
                   var nodeA=getNodeById(objE.nodeAId);
                   var nodeB=getNodeById(objE.nodeBId);    
                   objE.EdgeBroken(nodeA,nodeB);
                   graph.currentWEdgeY1=nodeA.y;
                   graph.currentWEdgeY2=nodeA.y+nodeA.height;
                   graph.currentHEdgeY1=nodeB.y;
                   graph.currentHEdgeY2=nodeB.y+nodeB.height;
                   if((((epageY<objE.y2&&epageY>objE.y1)||(epageY<objE.y1&&epageY>objE.y2))&&(epageX<=objE.x3+2&&epageX>=objE.x3-2))){
                       graph.moveH=true;
                       graph.moveWO=false;
                       graph.moveWT=false;
                       objE.brokenDiffx1=objE.x1;
                       objE.brokenDiffx2=objE.x2;                          
                       objE.brokenDiffx3=e.pageX-objE.x3;
                       objE.brokenDiffx4=e.pageX-objE.x4;
                       objE.brokenDiffy1=objE.y1;
                       objE.brokenDiffy2=objE.y2;
                       objE.brokenDiffy3=objE.y3;
                       objE.brokenDiffy4=objE.y4;
                   }else if((((epageX<objE.x3&&epageX>objE.x1)||(epageX<objE.x1&&epageX>objE.x3))&&(epageY<=objE.y3+2&&epageY>=objE.y3-2))){
                       graph.moveWO=true;
                       graph.moveWT=false;
                       graph.moveH=false;
                       objE.brokenDiffx1=objE.x1;
                       objE.brokenDiffx2=objE.x2;                          
                       objE.brokenDiffx3=objE.x3;
                       objE.brokenDiffx4=objE.x4;
                       objE.brokenDiffy1=e.pageY-objE.y1;
                       objE.brokenDiffy2=objE.y2;
                       objE.brokenDiffy3=e.pageY-objE.y3;
                       objE.brokenDiffy4=objE.y4;
                   }else if((((epageX<objE.x2&&epageX>objE.x4)||(epageX<objE.x4&&epageX>objE.x2))&&(epageY<=objE.y4+2&&epageY>=objE.y4-2))){
                       graph.moveWT=true;
                       graph.moveWO=false;
                       graph.moveH=false;
                       objE.brokenDiffx1=objE.x1;
                       objE.brokenDiffx2=objE.x2;                          
                       objE.brokenDiffx3=objE.x3;
                       objE.brokenDiffx4=objE.x4;
                       objE.brokenDiffy1=objE.y1;
                       objE.brokenDiffy2=e.pageY-objE.y2;
                       objE.brokenDiffy3=objE.y3;
                       objE.brokenDiffy4=e.pageY-objE.y4;
                   }
                   
                   }else{
                       graph.brokenEdge=[];
                       graph.drawData(); 
                   }                   
               }else{
                   //清空数组drawData循环数组避免重复
                   graph.brokenEdge=[];
                   graph.drawData();
               }
               if(obj){
                   graph.event.collideNode=obj;
                   
                   graph.selectedData.push(obj);
                   graph.drawData();
                   graph.shadow(graph.selectedData);
                   graph.canvas.style.cursor="pointer";
                   if(obj.elementType=="Node"){
                       obj.diffy=e.pageY-obj.y;
                       obj.diffx=e.pageX-obj.x;
                       
                       // 当拖拽node时LanNode的xy坐标改变
                       for(var i=0;i<graph.childs.length;i++){
                           var LanNodes=graph.childs[i];
                           var BrokenEdges=graph.childs[i];
                           var edge=graph.childs[i];
                           var nodeA=getNodeById(BrokenEdges.nodeAId);
                           var nodeB=getNodeById(BrokenEdges.nodeBId);
                           var nodeA=getNodeById(edge.nodeAId);
                           var nodeB=getNodeById(edge.nodeBId);
                           if(LanNodes.elementType=="LanNode"){
                               LanNodes.diffx1=e.pageX-LanNodes.x1;
                               LanNodes.diffy1=e.pageY-LanNodes.y1;
                               LanNodes.diffx2=e.pageX-LanNodes.x2;
                               LanNodes.y2=LanNodes.y2;
                               var textNode=getTextNodeByLanNode(LanNodes.id);
                               var textNodeLan=getTextNodeByLanNode(LanNodes.id+1);
                               if(textNode){
                                   LanNodes.ntdiffx1=LanNodes.x1-textNode.x;
                                   LanNodes.ntdiffy1=LanNodes.y1-textNode.y;
                               }
                               if(textNodeLan){
                                   LanNodes.ntdiffx=LanNodes.x2-textNodeLan.x;
                                   LanNodes.ntdiffy=LanNodes.y2-textNodeLan.y;
                               }
                           }
                           
                           if(BrokenEdges.elementType=="EdgeBroken"){
                               if(nodeA.id==obj.id){
                                   BrokenEdges.brokenDiffx1=e.pageX-BrokenEdges.x1;
                                   BrokenEdges.brokenDiffx2=BrokenEdges.x2;
                                   BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                                   BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                                   BrokenEdges.brokenDiffy1=e.pageY-BrokenEdges.y1;
                                   BrokenEdges.brokenDiffy2=BrokenEdges.y2;
                                   BrokenEdges.brokenDiffy3=e.pageY-BrokenEdges.y3;
                                   BrokenEdges.brokenDiffy4=BrokenEdges.y4;
                               }else if(nodeB.id==obj.id){
                                   BrokenEdges.brokenDiffx1=BrokenEdges.x1;
                                   BrokenEdges.brokenDiffx2=e.pageX-BrokenEdges.x2;
                                   BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                                   BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                                   BrokenEdges.brokenDiffy1=BrokenEdges.y1;
                                   BrokenEdges.brokenDiffy2=e.pageY-BrokenEdges.y2;
                                   BrokenEdges.brokenDiffy3=BrokenEdges.y3;
                                   BrokenEdges.brokenDiffy4=e.pageY-BrokenEdges.y4;
                               }                                
                           }
                           if(edge.elementType=="Edge"){
                               if(nodeA.id==obj.id){
                                   var textNode=getTextNodeByEdge(edge,e);
                                   if(textNode){
                                       edge.ntdiffx=edge.labelX-textNode.x1;
                                       edge.ntdiffy=edge.labelY-textNode.y1;      
                                   }
                               }else if(nodeB.id==obj.id){
                                   var textNode=getTextNodeByEdge(edge,e);
                                   if(textNode){
                                       edge.ntdiffx=edge.labelX-textNode.x1;
                                       edge.ntdiffy=edge.labelY-textNode.y1;      
                                   }
                               }
                               
                           }
                       }
                       
                       var textNode=getTextNodeByNode(obj.id);
                       if(textNode){
                           obj.ntdiffx=obj.x-textNode.x;
                           obj.ntdiffy=obj.y-textNode.y;
                           // alert(graph.event.diffX+"--"+graph.event.diffY);
                       }
                       
                       
                       //关联线路文本位置变动
                       var relationLines=graph.getRelateLines();
                       
                       for(var i=0;i<relationLines.length;i++){
                       	var trunkLine=relationLines[i].relateArray[0];
                       	
                       	if(trunkLine.nodeAId==graph.event.collideNode.id||trunkLine.nodeBId==graph.event.collideNode.id){
                       		
                       		var nodeA=getNodeById(trunkLine.nodeAId);
                       		var nodeB=getNodeById(trunkLine.nodeBId);
                       		if(nodeA.x<nodeB.x){
                       			graph.event.oldLeftName=relationLines[i].leftName;
                           		graph.event.oldRightName=relationLines[i].rightName;
                       		}else{
                       			graph.event.oldLeftName=relationLines[i].rightName;
                           		graph.event.oldRightName=relationLines[i].leftName;
                       		}
                       		
                           	
//                           	console.log(graph.event.oldLeftName+graph.event.oldRightName);
                       	}
                       	
                       }
                       
                       
                      
                   }else if(obj.elementType=="TextNode"){
                       graph.nodeX=obj.x;
                       graph.nodeY=obj.y;
                   }else if(obj.elementType=="LAN"){
                       obj.diffy1=e.pageY-obj.y1;
                       obj.diffy2=e.pageY-obj.y2;
                       obj.diffx1=e.pageX-obj.x1;
                       obj.diffx2=e.pageX-obj.x2;
                       if(obj.isDragSize){
                           obj.isDragSize=false;
                       }else{
                           obj.isDragSize=true;
                       }
                       graph.drawData();
                       for(var i=0;i<graph.childs.length;i++){
                           var LanNodes=graph.childs[i];
                           if(LanNodes.elementType=="LanNode"){
                               LanNodes.x1=LanNodes.x1;
                               LanNodes.y1=LanNodes.y1;
                               LanNodes.x2=LanNodes.x1;
                               LanNodes.diffy2=e.pageY-LanNodes.y2;
                               
                               var textNode=getTextNodeByLanNode(LanNodes.id);
                               var textNodeLan=getTextNodeByLanNode(LanNodes.id+1);
                               if(textNode){
                                   LanNodes.ntdiffx1=LanNodes.x1-textNode.x;
                                   LanNodes.ntdiffy1=LanNodes.y1-textNode.y;
                               }
                               if(textNodeLan){
                                   LanNodes.ntdiffx=LanNodes.x2-textNodeLan.x;
                                   LanNodes.ntdiffy=LanNodes.y2-textNodeLan.y;
                               }
                           }

                       }
                       if((epageX<=obj.x2+4&&epageX>=obj.x2-4)&&(epageY<=obj.y2+4&&epageY>=obj.y2-4)){

                           graph.canvas.style.cursor="e-resize";
                           obj.diffX1=obj.x1;
                           obj.diffY1=obj.y1;
                           obj.diffX2=e.pageX-obj.x2;
                           obj.diffY2=obj.y2;

                       }
                       if((epageX<=obj.x1+4&&epageX>=obj.x1-4)&&(epageY<=obj.y1+4&&epageY>=obj.y1-4)){

                           graph.canvas.style.cursor="e-resize";
                           obj.ndiffX1=e.pageX-243-obj.x1;
                           obj.ndiffY1=obj.y1;
                           obj.ndiffX2=obj.x2;
                           obj.ndiffY2=obj.y2;
                       }

                       var textNode=getTextNodeByLan(obj.id);
                       if(textNode){
                           
                           if(obj.x2>obj.x1){
                               obj.ntdiffx=obj.x2-textNode.x;
                               obj.ntdiffy=obj.y2-textNode.y;
                           }else{
                               obj.ntdiffx=obj.x1-textNode.x;
                               obj.ntdiffy=obj.y1-textNode.y;
                           }
                       }
                   }else if(obj.elementType=="TextRank"){
                       obj.diffx=e.pageX-obj.x;   
                       obj.diffy=e.pageY-obj.y;
                   }else if(obj.elementType=="TextVertical"){
                       obj.diffx=e.pageX-obj.x;   
                       obj.diffy=e.pageY-obj.y;
                   }
               }
               if(!objE&&!obj){  // 点击画布空白处
                   graph.event.isDragDrap = false;
                   graph.selectedData=[];
                   graph.drawData();
                   graph.event.isMoveDrap=true;  // 移动全部

                   for(var i=0;i<graph.childs.length;i++){
                           var node=graph.childs[i];
                           var edge=graph.childs[i];
                           var Lan=graph.childs[i];
                           var LanNodes=graph.childs[i];
                           var TextRanks=graph.childs[i];
                           var TextVerticals=graph.childs[i];
                           var BrokenEdges=graph.childs[i];
                           Lan.isDragSize=false;
                           if(node.elementType=="Node"){
                               node.diffx=e.pageX-node.x;
                               node.diffy=e.pageY-node.y;                                                              
                               var textNode=getTextNodeByNode(node.id);
                               if(textNode){
                                   node.ntdiffx=node.x-textNode.x;
                                   node.ntdiffy=node.y-textNode.y;// /alert(node.x)
                               }
                          }
                           if(edge.elementType=="Edge"){
                               var textNode=getTextNodeByEdge(edge,e);
                               if(textNode){
                                   edge.ntdiffx=edge.labelX-textNode.x1;
                                   edge.ntdiffy=edge.labelY-textNode.y1;
                               }
                           }  
                           if(Lan.elementType=="LAN"){
                               Lan.diffx1=e.pageX-Lan.x1;
                               Lan.diffy1=e.pageY-Lan.y1;
                               Lan.diffx2=e.pageX-Lan.x2;
                               Lan.diffy2=e.pageY-Lan.y2;
                               var textNode=getTextNodeByLan(Lan.id);
                               if(textNode){
                            	   if(Lan.x2>Lan.x1){
                                       Lan.ntdiffx=Lan.x2-textNode.x;
                                       Lan.ntdiffy=Lan.y2-textNode.y;
                                   }else{
                                       Lan.ntdiffx=Lan.x1-textNode.x;
                                       Lan.ntdiffy=Lan.y1-textNode.y;
                                   }
                               }
                               
                          }
                           if(LanNodes.elementType=="LanNode"){
                               LanNodes.diffx1=e.pageX-LanNodes.x1;
                               LanNodes.diffy1=e.pageY-LanNodes.y1;
                               LanNodes.diffx2=e.pageX-LanNodes.x2;
                               LanNodes.diffy2=e.pageY-LanNodes.y2;
                                var textNode=getTextNodeByLanNode(LanNodes.id);
                               var textNodeLan=getTextNodeByLanNode(LanNodes.id+1);
                               if(textNode){
                                   LanNodes.ntdiffx1=LanNodes.x1-textNode.x;
                                   LanNodes.ntdiffy1=LanNodes.y1-textNode.y;
                               }
                               if(textNodeLan){
                                   LanNodes.ntdiffx=LanNodes.x2-textNodeLan.x;
                                   LanNodes.ntdiffy=LanNodes.y2-textNodeLan.y;
                               }
                           }
                           if(TextRanks.elementType=="TextRank"){
                               TextRanks.diffx=e.pageX-TextRanks.x;   
                               TextRanks.diffy=e.pageY-TextRanks.y;
                           }
                           if(TextVerticals.elementType=="TextVertical"){
                               TextVerticals.diffx=e.pageX-TextVerticals.x;   
                               TextVerticals.diffy=e.pageY-TextVerticals.y;
                           }
                           if(BrokenEdges.elementType=="EdgeBroken"){
                               BrokenEdges.brokenDiffx1=e.pageX-BrokenEdges.x1;
                               BrokenEdges.brokenDiffx2=e.pageX-BrokenEdges.x2;
                               BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                               BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                               BrokenEdges.brokenDiffy1=e.pageY-BrokenEdges.y1;
                               BrokenEdges.brokenDiffy2=e.pageY-BrokenEdges.y2;
                               BrokenEdges.brokenDiffy3=e.pageY-BrokenEdges.y3;
                               BrokenEdges.brokenDiffy4=e.pageY-BrokenEdges.y4;            
                           }
                       }
                   graph.drawData();
               }
            }else if(graph.graphModel=="Edit"){  // 添加Edge模式下的处理
               var obj=graph.getElementByMouseEvent(e);
               if(e.button==0){
                   if(obj){
                       if(!graph.event.currentNode){
                       
                           graph.event.currentNode=obj;

                       }else if(graph.event.currentNode&&!graph.event.targetNode){
                           
                           graph.event.targetNode=obj;
                           var edge=new Edge('line',graph.event.currentNode,graph.event.targetNode);                                                      
                           edge.isDrawEdgeLabel=true;
                           edge.name="线";
                           edge.subType="5";
//                         保存连线
                           saveLine(edge);
                           graph.add(edge);
                           
                           graph.drawData();
                           graph.event.currentNode=null;
                           graph.event.targetNode=null;
                       }
                       
                   }else{
                       graph.drawData();
                       graph.event.currentNode=null;
                       graph.event.targetNode=null;
                   }
               }else{
                   graph.drawData();
                   graph.event.currentNode=null;
                   graph.event.targetNode=null;
               }
               
            }else if(graph.graphModel=="EditBroken"){  //添加Edge模式下的处理
                var obj=graph.getElementByMouseEvent(e);
                if(e.button==0){
                    if(obj){
                        if(!graph.event.currentNode){
                        
                            graph.event.currentNode=obj;

                        }else if(graph.event.currentNode&&!graph.event.targetNode){
                            
                            graph.event.targetNode=obj;
                            var edge=new EdgeBroken('line',graph.event.currentNode,graph.event.targetNode);                                                        
                            edge.name="包含";                            
                            edge.isBroken=true;
                            edge.isDrawEdgeLabel=true;
                            edge.subType="4";
//                          保存连线
                            saveLine(edge);
                            graph.add(edge);
                            
                            graph.drawData();
                            graph.event.currentNode=null;
                            graph.event.targetNode=null;
                        }
                    }else{
                        graph.drawData();
                        graph.event.currentNode=null;
                        graph.event.targetNode=null;
                    }
                }else{
                    graph.drawData();
                    graph.event.currentNode=null;
                    graph.event.targetNode=null;
                }
                
             }else if(graph.graphModel=="EditBrokenArrow"){  //添加Edge模式下的处理
                 var obj=graph.getElementByMouseEvent(e);
                 if(e.button==0){
                     if(obj){
                         if(!graph.event.currentNode){
                         
                             graph.event.currentNode=obj;

                         }else if(graph.event.currentNode&&!graph.event.targetNode){
                             
                             graph.event.targetNode=obj;
//                             alert();
                             var edge=new EdgeBrokenArrow('line',graph.event.currentNode,graph.event.targetNode);                            
                             edge.name="依赖";
                             edge.isBroken=true;
                             edge.isArrow=true;
                             edge.isDrawEdgeLabel=true;
                             edge.subType="0";
//                           保存连线
                             saveLine(edge);
                             graph.add(edge);
                             graph.drawData();
                             graph.event.currentNode=null;
                             graph.event.targetNode=null;
                         }
                     }else{
                         graph.drawData();
                         graph.event.currentNode=null;
                         graph.event.targetNode=null;
                     }
                 }else{
                     graph.drawData();
                     graph.event.currentNode=null;
                     graph.event.targetNode=null;
                 }
                 
              }else if(graph.graphModel=="EditBrokenSolidArrow"){  //添加Edge模式下的处理
                  var obj=graph.getElementByMouseEvent(e);
                  if(e.button==0){
                      if(obj){
                          if(!graph.event.currentNode){
                          
                              graph.event.currentNode=obj;

                          }else if(graph.event.currentNode&&!graph.event.targetNode){
                              
                              graph.event.targetNode=obj;
                              var edge=new EdgeBrokenSolidArrow('line',graph.event.currentNode,graph.event.targetNode);                            
                              edge.color="lightblue";
                              edge.name="包含";
                              edge.size=2;
                              edge.subType="1";
                              edge.isBroken=true;
                              edge.isArrow=true;
                              graph.add(edge);
                              graph.drawData();
                              graph.event.currentNode=null;
                              graph.event.targetNode=null;
                          }
                      }else{
                          graph.drawData();
                          graph.event.currentNode=null;
                          graph.event.targetNode=null;
                      }
                  }else{
                      graph.drawData();
                      graph.event.currentNode=null;
                      graph.event.targetNode=null;
                  }
                  
               }else if(graph.graphModel=="DashEditBrokenArrow"){  //添加Edge模式下的处理
                   var obj=graph.getElementByMouseEvent(e);
                   if(e.button==0){
                       if(obj){
                           if(!graph.event.currentNode){
                           
                               graph.event.currentNode=obj;

                           }else if(graph.event.currentNode&&!graph.event.targetNode){
                               
                               graph.event.targetNode=obj;
                               var edge=new DashBrokenSolidArrow('line',graph.event.currentNode,graph.event.targetNode);                            
                               edge.color="lightblue";
                               edge.name="影响";
                               edge.size=2;
                               edge.subType="1";
                               edge.isBroken=true;
                               edge.isArrow=true;
                               graph.add(edge);
                               graph.drawData();
                               graph.event.currentNode=null;
                               graph.event.targetNode=null;
                           }
                       }else{
                           graph.drawData();
                           graph.event.currentNode=null;
                           graph.event.targetNode=null;
                       }
                   }else{
                       graph.drawData();
                       graph.event.currentNode=null;
                       graph.event.targetNode=null;
                   }
                   
                }else if(graph.graphModel=="EditEdgeSolidArrow"){  //添加Edge模式下的处理
                  var obj=graph.getElementByMouseEvent(e);
                  if(e.button==0){
                      if(obj){
                          if(!graph.event.currentNode){
                          
                              graph.event.currentNode=obj;

                          }else if(graph.event.currentNode&&!graph.event.targetNode){
                              graph.event.targetNode=obj;
                              var edge=new EdgeSolidArrow('line',graph.event.currentNode,graph.event.targetNode); 
                              edge.isArrow=true;                           
                              edge.name="包含";
                              edge.subType="1";
//                            保存连线
                              saveLine(edge);
                              graph.add(edge)
                              graph.drawData();
                              graph.event.currentNode=null;
                              graph.event.targetNode=null;
                          }
                      }else{
                          graph.drawData();
                          graph.event.currentNode=null;
                          graph.event.targetNode=null;
                      }
                  }else{
                      graph.drawData();
                      graph.event.currentNode=null;
                      graph.event.targetNode=null;
                  }
                  
               }else if(graph.graphModel=="EditEdgeEmptyArrow"){  //添加Edge模式下的处理
                   var obj=graph.getElementByMouseEvent(e);
                   if(e.button==0){
                       if(obj){
                           if(!graph.event.currentNode){
                           
                               graph.event.currentNode=obj;

                           }else if(graph.event.currentNode&&!graph.event.targetNode){
                               
                               graph.event.targetNode=obj;
                               var edge=new EdgeEmptyArrow('line',graph.event.currentNode,graph.event.targetNode);                           
                               edge.name="依赖";
                               edge.subType="2";
//                             保存连线
                               saveLine(edge);
                               graph.add(edge);
                               
                               graph.drawData();
                               graph.event.currentNode=null;
                               graph.event.targetNode=null;
                           }
                       }else{
                           graph.drawData();
                           graph.event.currentNode=null;
                           graph.event.targetNode=null;
                       }
                   }else{
                       graph.drawData();
                       graph.event.currentNode=null;
                       graph.event.targetNode=null;
                   }
                   
                }else if(graph.graphModel=="EditDashEdgeSolidArrow"){  //添加Edge模式下的处理
                    var obj=graph.getElementByMouseEvent(e);
                    if(e.button==0){
                        if(obj){
                            if(!graph.event.currentNode){
                            
                                graph.event.currentNode=obj;

                            }else if(graph.event.currentNode&&!graph.event.targetNode){
                                
                                graph.event.targetNode=obj;
                                var edge=new DashEdgeSolidArrow('line',graph.event.currentNode,graph.event.targetNode);                            
                                edge.name="影响";
                                edge.subType="3";
//                              保存连线
                                saveLine(edge);
                                graph.add(edge);
                                
                                graph.drawData();
                                graph.event.currentNode=null;
                                graph.event.targetNode=null;
                            }
                        }else{
                            graph.drawData();
                            graph.event.currentNode=null;
                            graph.event.targetNode=null;
                        }
                    }else{
                        graph.drawData();
                        graph.event.currentNode=null;
                        graph.event.targetNode=null;
                    }
                    
                 }else if(graph.graphModel=="EditDashLineEmptyArrow"){  //添加Edge模式下的处理
                     var obj=graph.getElementByMouseEvent(e);
                     if(e.button==0){
                         if(obj){
                             if(!graph.event.currentNode){
                             
                                 graph.event.currentNode=obj;

                             }else if(graph.event.currentNode&&!graph.event.targetNode){
                                 
                                 graph.event.targetNode=obj;
                                 var edge=new DashLineEmptyArrow('line',graph.event.currentNode,graph.event.targetNode);                            
                                 edge.name="";
//                               保存连线
                                 saveLine(edge);
                                 graph.add(edge);
                                 
                                 graph.drawData();
                                 graph.event.currentNode=null;
                                 graph.event.targetNode=null;
                             }
                         }else{
                             graph.drawData();
                             graph.event.currentNode=null;
                             graph.event.targetNode=null;
                         }
                     }else{
                         graph.drawData();
                         graph.event.currentNode=null;
                         graph.event.targetNode=null;
                     }
                     
                  }else if(graph.graphModel=="relateEdge"){  //添加relateEdge模式下的处理
                graph.event.edgeRelate=[];
                if(e.button==0){
                    graph.event.isRelate=true;
                    if(graph.currentX==0&&graph.currentY==0){
                        graph.currentX = epageX;
                        graph.currentY = epageY;
                    }
                    if(graph.currentX!=epageX||graph.currentY!=epageY){
                        graph.currentX = 0;
                        graph.currentY = 0;
                        graph.drawData();
                    }
                }else{
                    graph.event.isRelate=false;
                    graph.drawData();
                    graph.currentX = 0;
                    graph.currentY = 0;
                    
                }
             }else if(graph.graphModel=="Lan"){
                
                if(e.button==0){
                	if(graph.currentX==0&&graph.currentY==0){
                    	// if(e.pageX>243 && e.pageY>48){
                    		graph.currentX = epageX;
                            graph.currentY = epageY;
                    	// }else{
                    		// graph.currentX=0;
                    		// graph.currentY=0;
                    	// }
                    }
                     
                    
                      
                     if(graph.currentX!=epageX||graph.currentY!=epageY){
                    	 
                         var lan=new LAN("LAN",graph.currentX,graph.currentY,epageX,graph.currentY);
                         lan.isDragSize=true;
                         lan.id=new Date().getTime();
                         lan.name="LAN";
                         graph.add(lan);
                         if(lan.x2>lan.x1){
                             var lanNode=new TextNode(lan.name,lan.x2+15,lan.y2-5);
                         }else{
                             var lanNode=new TextNode(lan.name,lan.x1+15,lan.y1-5);
                         }
                         
                         lanNode.textId =lan.id;
                         graph.add(lanNode);
                         graph.drawData();
                     
                        graph.currentX=0;
                        graph.currentY=0;
                     }
                 
                }else{
                	graph.currentX=0;
                    graph.currentY=0;
                    graph.drawData();
                 }                
                
             
          }else if(graph.graphModel=="dropLan"){
              var obj=graph.getElementByMouseEvent(e);
              var objL=graph.getElementByMouseEventLAN(e);              
                if(e.button==0){
                  if(obj||objL){
                      if(!graph.event.currentNode){
                          graph.event.currentNode=obj;
                          graph.nodeLanX=epageX;
                          graph.nodeLanY=epageY;
                      }else if(graph.event.currentNode&&!graph.event.targetLan){
                          graph.event.targetLan=objL;
                          var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                          var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                          var ox1=graph.event.targetLan.x1;
                          var oy1=graph.event.targetLan.y1;
                          var ox2=graph.event.targetLan.x2;
                          var oy2=graph.event.targetLan.y2;
                          if((ox1<=epageX&&epageX<=ox2)&&(oy1>=epageY-10&&oy1<=epageY+10)&&(ox1<=ox&&ox<=ox2)){
                              var lanNode=new LanNode('LanNode',graph.event.currentNode,graph.event.targetLan);
                              
                          }else if((ox1>=epageX&&epageX>=ox2)&&(oy1>=epageY-10&&oy1<=epageY+10)&&(ox1>=ox&&ox>=ox2)){
                              var lanNode=new LanNode('LanNode',graph.event.currentNode,graph.event.targetLan);
                          }
                             lanNode.id=new Date().getTime();
                             graph.add(lanNode);
                             if(lanNode.y1>lanNode.y2){
                                  var lanTextNode2=new TextNode("nodeLabel",graph.event.currentNode.x+graph.event.currentNode.width+5,graph.event.currentNode.y);
                                  var lanTextNode1=new TextNode("intfLabel",graph.event.currentNode.x+graph.event.currentNode.width+5,graph.event.targetLan.y2-5);
                             }else{
                                 var lanTextNode2=new TextNode("nodeLabel",graph.event.currentNode.x+graph.event.currentNode.width+5,graph.event.currentNode.y+graph.event.currentNode.height);
                                 var lanTextNode1=new TextNode("intfLabel",graph.event.currentNode.x+graph.event.currentNode.width+5,graph.event.targetLan.y2-5);
                             }
                             lanTextNode1.textId =lanNode.id+1;
                             lanTextNode2.textId =lanNode.id;
                             graph.add(lanTextNode1);
                             graph.add(lanTextNode2);
                             graph.drawData();
                             graph.event.currentNode=null;
                             graph.event.targetLan=null;
                             graph.nodeLanX=null;
                             graph.nodeLanY=null;
                          
                      }
                  }else{
                      graph.drawData();
                      graph.event.currentNode=null;
                      graph.event.targetLan=null;
                      graph.nodeLanX=null;
                      graph.nodeLanY=null;
                      
                  } 
           }else{
                  graph.drawData();
                  graph.event.currentNode=null;
                  graph.event.targetLan=null;
                  graph.nodeLanX=null;
                  graph.nodeLanY=null;
                      
                }
                 
              
              
           }else if(graph.graphModel=="textRank"){
               var obj=graph.getElementByMouseEvent(e);
               if(e.button==0&&!obj){
                   graph.isDblText=true;
                   var textRank=new TextRank(TextRank.name,epageX,epageY);
                   textRank.name="";
                   textRank.id=new Date().getTime();
                   graph.add(textRank);
                   graph.drawData();
                       

               }
            }else if(graph.graphModel=="textVertical"){
                var obj=graph.getElementByMouseEvent(e);
                if(e.button==0&&!obj){
                    graph.isDblText=true;
                    var textVertical=new TextVertical(TextVertical.name,epageX,epageY);
                    textVertical.name="";
                    textVertical.id=new Date().getTime();
                    graph.add(textVertical);
                    graph.drawData();
                        

                }
             }else if(graph.graphModel=="Select"){  // 框选模式
               var obj=graph.getElementByMouseEvent(e);
               if(e.button==0){
                   
               if(!obj){
                   graph.selectedData=[];
                   graph.drawData();
                   graph.event.isDragSelect=true;
                   
                   if(graph.selectedData.length==0){

                       graph.event.selectArea=document.getElementById('selected');
                       // graph.event.selectArea=document.createElement('div');
                       graph.event.selectArea.style.display="none";
                       graph.event.selectSX=epageX;
                       graph.event.selectSY=epageY;
                       graph.event.selectEX=epageX;
                       graph.event.selectEY=epageY;
                       // alert(dragBoxstartX+"====="+dragBoxstartY);
                       graph.event.selectArea.style.left = e.pageX+ 'px';
                       graph.event.selectArea.style.top = e.pageY+ 'px';
                   }
                   
                   }
               else{
                       
                       if(graph.isContain(graph.selectedData,obj)){// 点击节点属于所选数组元素
                               graph.event.isDragDrap = true;
                               graph.event.isDragSelect=false;
                               graph.event.collideNode=obj;

                               for(var i=0;i<graph.selectedData.length;i++){
                                   var node=graph.selectedData[i];
                                   node.diffx=e.pageX-node.x;
                                   node.diffy=e.pageY-node.y;
                                   for(var k=0;k<graph.childs.length;k++){
                                       var BrokenEdges=graph.childs[k];
                                       var nodeA=getNodeById(BrokenEdges.nodeAId);
                                       var nodeB=getNodeById(BrokenEdges.nodeBId);
                                       if(BrokenEdges.elementType=="EdgeBroken"){
                                           
                                           if(nodeA.id==node.id){
                                           BrokenEdges.brokenDiffx1=e.pageX-BrokenEdges.x1;
                                           BrokenEdges.brokenDiffx2=BrokenEdges.x2;
                                           BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                                           BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                                           BrokenEdges.brokenDiffy1=e.pageY-BrokenEdges.y1;
                                           BrokenEdges.brokenDiffy2=BrokenEdges.y2;
                                           BrokenEdges.brokenDiffy3=e.pageY-BrokenEdges.y3;
                                           BrokenEdges.brokenDiffy4=BrokenEdges.y4;
                                           }else if(nodeB.id==node.id){
                                           BrokenEdges.brokenDiffx1=BrokenEdges.x1;
                                           BrokenEdges.brokenDiffx2=e.pageX-BrokenEdges.x2;
                                           BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                                           BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                                           BrokenEdges.brokenDiffy1=BrokenEdges.y1;
                                           BrokenEdges.brokenDiffy2=e.pageY-BrokenEdges.y2;
                                           BrokenEdges.brokenDiffy3=BrokenEdges.y3;
                                           BrokenEdges.brokenDiffy4=e.pageY-BrokenEdges.y4;
                                           }
                                           if(graph.selectedData.indexOf(nodeA) > -1&&graph.selectedData.indexOf(nodeB) > -1){
                                           BrokenEdges.brokenDiffx1=e.pageX-BrokenEdges.x1;
                                           BrokenEdges.brokenDiffx2=e.pageX-BrokenEdges.x2;
                                           BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                                           BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                                           BrokenEdges.brokenDiffy1=e.pageY-BrokenEdges.y1;
                                           BrokenEdges.brokenDiffy2=e.pageY-BrokenEdges.y2;
                                           BrokenEdges.brokenDiffy3=e.pageY-BrokenEdges.y3;
                                           BrokenEdges.brokenDiffy4=e.pageY-BrokenEdges.y4;
                                           }                                 
                                       }
                                       
                                       }
                                   var textNode=getTextNodeByNode(node.id);
                                   if(textNode){
                                       node.ntdiffx=node.x-textNode.x;
                                       node.ntdiffy=node.y-textNode.y;
                                   }
                                  
                               }

                       }else{  // 点击节点不属于所选数组元素
                               graph.canvas.style.cursor="pointer";
                               graph.selectedData.push(obj);
                               
                               graph.shadow(graph.selectedData);
                               graph.selectedData=[];
                               graph.drawData();
                               graph.event.isDragSelect=false;
                               graph.event.isDragDrap=true;
                               graph.selectedData.push(obj);
                               obj.diffx=e.pageX-obj.x;
                               obj.diffy=e.pageY-obj.y;
                               for(var i=0;i<graph.childs.length;i++){
                                   var BrokenEdges=graph.childs[i];
                                   var nodeA=getNodeById(BrokenEdges.nodeAId);
                                   var nodeB=getNodeById(BrokenEdges.nodeBId);
                                   if(BrokenEdges.elementType=="EdgeBroken"){
                                       if(nodeA.id==obj.id){
                                       BrokenEdges.brokenDiffx1=e.pageX-BrokenEdges.x1;
                                       BrokenEdges.brokenDiffx2=BrokenEdges.x2;
                                       BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                                       BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                                       BrokenEdges.brokenDiffy1=e.pageY-BrokenEdges.y1;
                                       BrokenEdges.brokenDiffy2=BrokenEdges.y2;
                                       BrokenEdges.brokenDiffy3=e.pageY-BrokenEdges.y3;
                                       BrokenEdges.brokenDiffy4=BrokenEdges.y4;
                                       }else if(nodeB.id==obj.id){
                                       BrokenEdges.brokenDiffx1=BrokenEdges.x1;
                                       BrokenEdges.brokenDiffx2=e.pageX-BrokenEdges.x2;
                                       BrokenEdges.brokenDiffx3=e.pageX-BrokenEdges.x3;
                                       BrokenEdges.brokenDiffx4=e.pageX-BrokenEdges.x4;
                                       BrokenEdges.brokenDiffy1=BrokenEdges.y1;
                                       BrokenEdges.brokenDiffy2=e.pageY-BrokenEdges.y2;
                                       BrokenEdges.brokenDiffy3=BrokenEdges.y3;
                                       BrokenEdges.brokenDiffy4=e.pageY-BrokenEdges.y4;
                                       }                                 
                                   }
                               }
                               var textNode=getTextNodeByNode(obj.id);
                               if(textNode){
                                   obj.ntdiffx=obj.x-textNode.x;
                                   obj.ntdiffy=obj.y-textNode.y;
                               }

                       }
               }                                                               
           }
         }
          
            // 右键菜单
            if(e.button==2){
	            var obj=graph.getElementByMouseEvent(e);
	            var edge=graph.getEdgeByMouseEvent(e);
	              if(obj != null){
	                if(obj.elementType=="Node"&&obj.objType==1){
	                	
	                  currentNode=obj;	
	                  $("#contextMenu").css({
	                    top: e.pageY,
	                    left: e.pageX
	                    
	                   }).show();

	                  var bodyHeight=$(window).get(0).innerHeight;
	                  var bodyWidth=$(window).get(0).innerWidth;
	                  var divHeight=$("#contextMenu").height();
	                  var divWidth=$("#contextMenu").width();
	                  var diffH=0;
	                  var diffW=10;

	                  if(bodyHeight-e.pageY<divHeight){
	                      diffH=divHeight-(bodyHeight-e.pageY);
	                  }

	                  if(bodyWidth-e.pageX<divWidth){
	                      diffW+=divWidth-(bodyWidth-e.pageX);
	                  }
	                  
	                  $("#contextMenu").css({
	                      top: e.pageY-75,
	                      left: e.pageX
	                      
	                  });
	            }
	                
	                if(obj.elementType=="Node"&&obj.objType==0){
	                	
		                  currentNode=obj;	
		                  $("#subNetmenu").css({
		                    top: e.pageY,
		                    left: e.pageX
		                   }).show();

		                  var bodyHeight=$(window).get(0).innerHeight;
		                  var bodyWidth=$(window).get(0).innerWidth;
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
		                  
		                  $("#subNetmenu").css({
		                      top: e.pageY-diffH,
		                      left: e.pageX-diffW
		                  });
		            }
	                if(obj.elementType=="LAN"||obj.elementType=="LanNode"){
	                	currentNode=obj;	
	    	            $("#linkmenu").css({
	    	                    top: e.pageY,
	    	                    left: e.pageX
	    	                   }).show();

	    	                  var bodyHeight=$(window).get(0).innerHeight;
	    	                  var bodyWidth=$(window).get(0).innerWidth;
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
	    	                  $("#linkmenu").css({
	    	                      top: e.pageY-diffH,
	    	                      left: e.pageX-diffW
	    	                  }); 
	    	        }
	               
	        }
	           
	        else if(edge!=null){
	        	
	        	currentNode=edge;	
	            $("#linkmenu").css({
	                    top: e.pageY,
	                    left: e.pageX
	                   }).show();

	                  var bodyHeight=$(window).get(0).innerHeight;
	                  var bodyWidth=$(window).get(0).innerWidth;
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
	                  $("#linkmenu").css({
	                      top: e.pageY-diffH,
	                      left: e.pageX-diffW
	                  }); 
	        }

	        }
	        if(e.button==0){
	            $("#contextMenu").hide();
	            $("#linkmenu").hide();
	            $("#subNetmenu").hide();
	            var edge=graph.getEdgeByMouseEvent(event);           
	            if(edge!=null){
	              if(edge.elementType=="Edge"&&graph.brokenLinename==true){
	                graph.removeElement(edge); 
	                var nodeA=getNodeById(edge.nodeAId);
	                var nodeB=getNodeById(edge.nodeBId); 
	                //判断关系，确定画线
	                if(edge.subType=="2"){
	                  var edge=new EdgeBrokenArrow('依赖',nodeA,nodeB);
	                      edge.isBroken=true;
	                      edge.isDrawEdgeLabel=true;
	                      graph.add(edge);                
	                }else if(edge.name=="线"){
	                  var edge=new EdgeBroken('线',nodeA,nodeB);
	                      edge.isBroken=true;
	                      edge.isDrawEdgeLabel=true;                 
	                      graph.add(edge); 
	                }else if(edge.subType=="1"){
	                  var edge=new EdgeBrokenSolidArrow('包含',nodeA,nodeB);
	                      edge.isBroken=true;
	                      edge.isDrawEdgeLabel=true;                                
	                      graph.add(edge); 
	                }else if(edge.subType=="3"){
	                  var edge=new DashBrokenSolidArrow('影响',nodeA,nodeB);
	                      edge.isBroken=true;
	                      edge.isDrawEdgeLabel=true;                 
	                      graph.add(edge);                
	                }  
	                
	              }else if(graph.lineName==true&&edge.elementType=="EdgeBroken"){
	                graph.removeElement(edge);              
	                var nodeA=getNodeById(edge.nodeAId);
	                var nodeB=getNodeById(edge.nodeBId); 
	                if(edge.subType=="0"){
	                  var edge=new EdgeEmptyArrow('依赖',nodeA,nodeB);                 
	                      graph.add(edge); 
	                }else if(edge.name=="线"){
	                  var edge=new Edge('线',nodeA,nodeB);                 
	                      graph.add(edge); 
	                }else if(edge.subType=="5"){
	                  var edge=new EdgeSolidArrow('包含',nodeA,nodeB);                 
	                      graph.add(edge); 
	                }else if(edge.subType=="6"){
	                  var edge=new DashEdgeSolidArrow('影响',nodeA,nodeB);                 
	                      graph.add(edge); 
	                }               
	              }
	              var edgeNode=new TextNode(edge.name,edge.labelX,edge.labelY);
	                  edgeNode.textId=edge.id;
	                  edgeNode.radius=edge.radius;
	                  edgeNode.isDrawEdgeLabel=true;
	                  graph.EdgeText.push(edgeNode)    
	              //折线移动点
	              if(edge.elementType=="EdgeBroken"){
	                graph.brokenEdge.push(edge);
	                graph.drawData();
	              }
	            }else{
	              graph.brokenEdge=[];
	              graph.drawData();
	            }
	          

	        }

       });

		
		this.canvas.addEventListener('mouseup',function(e){
            if(graph.graphModel=="Move"||graph.graphModel=="BrokenLine_name"){
            	if(graph.event.collideEdge){
                    if(graph.event.collideEdge.elementType=="EdgeBroken"&&graph.event.collideEdge.isArrow==true){
                        var nodeA=getNodeById(graph.event.collideEdge.nodeAId);
                        var nodeB=getNodeById(graph.event.collideEdge.nodeBId);
                        if(graph.event.collideEdge.x4<graph.event.collideEdge.x2){
                            if(graph.event.collideEdge.x4<=graph.event.collideEdge.x2-nodeB.width/2&&graph.event.collideEdge.x4>graph.event.collideEdge.x2-nodeB.width/2-15){
                                graph.event.collideEdge.x4=graph.event.collideEdge.x2-nodeB.width/2-15;
                                graph.event.collideEdge.x3=graph.event.collideEdge.x2-nodeB.width/2-15;
                                graph.drawData()
                            }
                        }else if(graph.event.collideEdge.x4>graph.event.collideEdge.x2){
                            if(graph.event.collideEdge.x4>=graph.event.collideEdge.x2+nodeB.width/2&&graph.event.collideEdge.x4<graph.event.collideEdge.x2+nodeB.width/2+15){
                                graph.event.collideEdge.x4=graph.event.collideEdge.x2+nodeB.width/2+15;
                                graph.event.collideEdge.x3=graph.event.collideEdge.x2+nodeB.width/2+15;
                                graph.drawData();
                            }
                        }
                    }
                }
                graph.selectedData=[];
                graph.brokenEdge=[];
                graph.event.isDragDrap = false;
                graph.event.collideNode=null;
                graph.event.collideEdge=null;
                graph.event.isMoveDrap=false;
                graph.canvas.style.cursor="";
                graph.currentX=0;
                graph.currentY=0;
                
            }else if(graph.graphModel=="Edit"){

            }else if(graph.graphModel=="EditBroken"){

            }else if(graph.graphModel=="EditBrokenArrow"){

            }else if(graph.graphModel=="EditEdgeSolidArrow"){

            }
            else if(graph.graphModel=="EditEdgeEmptyArrow"){

            }
            else if(graph.graphModel=="EditDashEdgeSolidArrow"){

            }else if(graph.graphModel=="EditDashEdgeEmptyArrow"){

            }else if(graph.graphModel=="relateEdge"){
                graph.event.isRelate=false;
                if((graph.currentX!=e.pageX||graph.currentY!=e.pageY)&&(graph.currentX!=0&&graph.currentY!=0)&&graph.event.nodeA!=null&&graph.event.nodeB!=null){

                    var relateLine=new RelateLine('RelateLine.left','RelateLine.right',unique(graph.event.edgeRelate)); 
                    relateLine.id=new Date().getTime();
                    graph.add(relateLine);
                    graph.drawData();
                    graph.currentX=0;
                    graph.currentY=0;
                    graph.event.nodeA=null;
                    graph.event.nodeB=null;
                }else{
                    graph.drawData();
                    graph.currentX=0;
                    graph.currentY=0;
                    graph.event.nodeA=null;
                    graph.event.nodeB=null;
                }
            }else if(graph.graphModel=="Lan"){

            }else if(graph.graphModel=="dropLan"){
            	graph.currentX=0;
                graph.currentY=0;
            }else if(graph.graphModel=="textRank"){
              
            }else if(graph.graphModel=="textVertical"){
              
            }else if(graph.graphModel=="Select"){
                graph.event.isDragSelect=false;
                graph.event.isDragDrap = false;
                graph.canvas.style.cursor="";
                if(graph.event.selectArea!=null){
                    graph.event.selectArea.style.display="none";
                }
                if(graph.selectedData.length==0){
                    for(var i=0;i<graph.childs.length;i++){
                    var obj=graph.childs[i];
                    
                    if(obj.elementType=="Node"){ // 正选
                        obj.Wx=obj.x+obj.width/2;
                        obj.HY=obj.y+obj.height/2;
                        if((obj.Wx>graph.event.selectSX&&obj.Wx<graph.event.selectEX)&&(obj.HY>graph.event.selectSY&&obj.HY<graph.event.selectEY)){
                            graph.selectedData.push(obj);
                            var textNode=getTextNodeByNode(obj.id);
                            if(textNode){
                                graph.selectedData.push(textNode);
                            }
                        }
                         if((obj.Wx>graph.event.selectEX&&obj.Wx<graph.event.selectSX)&&(obj.HY>graph.event.selectEY&&obj.HY<graph.event.selectSY)){
                            graph.selectedData.push(obj);
                            var textNode=getTextNodeByNode(obj.id);
                            if(textNode){
                                graph.selectedData.push(textNode);
                            }
                        }

                        }
                    }
                }
                graph.drawData();
                graph.shadow(graph.selectedData);
            }
        });

        this.canvas.addEventListener('mousemove',function(e){
        	var locXY=windowTocanvas(graph.canvas,e.pageX,e.pageY);
	   		var epageX=locXY.x;
	   	    var epageY=locXY.y;
        	
            // 鼠标hover的状态
            if(graph.graphModel=="Move"||graph.graphModel=="Line_name"||graph.graphModel=="BrokenLine_name"){
                var obj=graph.getElementByMouseEvent(e);
                var objE=graph.getEdgeByMouseEvent(e);
                if(obj){
                    if(obj.elementType=="LAN"){
                        graph.canvas.style.cursor="pointer";
                        if((epageX<=obj.x2+4&&epageX>=obj.x2-8)&&(epageY<=obj.y2+4&&epageY>=obj.y2-4)){
                            graph.canvas.style.cursor="e-resize";
                        }
                        if((epageX<=obj.x1+4&&epageX>=obj.x1-4)&&(epageY<=obj.y1+4&&epageY>=obj.y1-4)){
                            graph.canvas.style.cursor="e-resize";
                        }
                        $('#relateMesg').css({
                            display: 'none',
                        });
                    }else if(obj.elementType=="RelateLine"){
                        graph.canvas.style.cursor="pointer";
                        $('#relateMesg').css({
                            display: 'block',
                            left:e.pageX+10,
                            top:e.pageY+10
                        }).html("<ul class=\"relateMesgL\"><li>"+obj.leftName+"</li></ul>"+"<ul class=\"relateMesgR\"><li>"+obj.rightName+"</li></ul>");
                    }else{
                        graph.canvas.style.cursor="pointer";
                        $('#relateMesg').css({
                            display: 'none',
                        });
                    }
                }else{
                    graph.canvas.style.cursor="";
                    $('#relateMesg').css({
                        display: 'none',
                    });
                }
                if(objE){
                    graph.canvas.style.cursor="pointer";
                    $('#relateMesg').css({
                        display: 'none',
                    });
                    clearTimeout(graph.event.hoverTimer);
                    graph.event.hoverTimer=setTimeout(function(){
                    	$('#lineMesg').css({
                            left:e.pageX+15,
                            top:e.pageY+10,
//                            width:"100px",
                            height:"50px",
                            lineHeight:"10px",
                            position:"absolute",
                            display:'block'
                        }).html(objE.name);
                    },500)
                    
                }else{
                	clearTimeout(graph.event.hoverTimer);
                    $('#lineMesg').css({
                        display:'none'
                    });
                }
            }
          //折线属性下折线移动
            if(graph.graphModel=="BrokenLine_name"&&e.button==0){
                if(graph.event.isDragDrap){
                    if(graph.event.collideEdge){
                        if(graph.event.collideEdge.elementType=="EdgeBroken"){
                            var nodeA=getNodeById(graph.event.collideEdge.nodeAId);
                            var nodeB=getNodeById(graph.event.collideEdge.nodeBId); 
                            if(graph.moveH==true){
                            graph.event.collideEdge.x1=graph.event.collideEdge.brokenDiffx1;
                            graph.event.collideEdge.x2=graph.event.collideEdge.brokenDiffx2;
                            graph.event.collideEdge.x3=e.pageX-graph.event.collideEdge.brokenDiffx3;
                            graph.event.collideEdge.x4=e.pageX-graph.event.collideEdge.brokenDiffx4;
                             graph.event.collideEdge.y1=graph.event.collideEdge.brokenDiffy1;
                            graph.event.collideEdge.y2=graph.event.collideEdge.brokenDiffy2;
                            graph.event.collideEdge.y3=graph.event.collideEdge.brokenDiffy3;
                            graph.event.collideEdge.y4=graph.event.collideEdge.brokenDiffy4;
                        
                            graph.event.collideEdge.updateBrokenEdge(graph.event.collideEdge);

                            graph.drawData();
                          }else if(graph.moveWO==true&&epageY>=graph.currentWEdgeY1&&epageY<=graph.currentWEdgeY2){
                            graph.event.collideEdge.x1=graph.event.collideEdge.brokenDiffx1;
                            graph.event.collideEdge.x2=graph.event.collideEdge.brokenDiffx2;
                            graph.event.collideEdge.x3=graph.event.collideEdge.brokenDiffx3;
                            graph.event.collideEdge.x4=graph.event.collideEdge.brokenDiffx4;
                            graph.event.collideEdge.y1=e.pageY-graph.event.collideEdge.brokenDiffy1;
                            graph.event.collideEdge.y2=graph.event.collideEdge.brokenDiffy2;
                            graph.event.collideEdge.y3=e.pageY-graph.event.collideEdge.brokenDiffy3;
                            graph.event.collideEdge.y4=graph.event.collideEdge.brokenDiffy4;                        
                            graph.event.collideEdge.updateBrokenEdge(graph.event.collideEdge);
                            graph.drawData();
                          }else if(graph.moveWT==true&&epageY>=graph.currentHEdgeY1&&epageY<=graph.currentHEdgeY2){
                            
                            graph.event.collideEdge.x1=graph.event.collideEdge.brokenDiffx1;
                            graph.event.collideEdge.x2=graph.event.collideEdge.brokenDiffx2;
                            graph.event.collideEdge.x3=graph.event.collideEdge.brokenDiffx3;
                            graph.event.collideEdge.x4=graph.event.collideEdge.brokenDiffx4;
                            graph.event.collideEdge.y1=graph.event.collideEdge.brokenDiffy1;
                            graph.event.collideEdge.y2=e.pageY-graph.event.collideEdge.brokenDiffy2;
                            graph.event.collideEdge.y3=graph.event.collideEdge.brokenDiffy3;
                            graph.event.collideEdge.y4=e.pageY-graph.event.collideEdge.brokenDiffy4;
                        
                            graph.event.collideEdge.updateBrokenEdge(graph.event.collideEdge);
                            graph.drawData();
                          }    
                        }
                    }
                }
            }
            //鼠标点击拖动的状态
            if(graph.graphModel=="Move"&&e.button==0){
                
                if(graph.event.isDragDrap){
                	  if(graph.event.collideEdge){
                		  
                          if(graph.event.collideEdge.elementType=="EdgeBroken"){
                        	  
                              if(graph.moveH==true){
                              
                              graph.event.x1=graph.event.collideEdge.brokenDiffx1;
                              graph.event.x2=graph.event.collideEdge.brokenDiffx2;
                              graph.event.collideEdge.x3=e.pageX-graph.event.collideEdge.brokenDiffx3;
                              graph.event.collideEdge.x4=e.pageX-graph.event.collideEdge.brokenDiffx4;
                              graph.event.collideEdge.y1=graph.event.collideEdge.brokenDiffy1;
                              graph.event.collideEdge.y2=graph.event.collideEdge.brokenDiffy2;
                              graph.event.collideEdge.y3=graph.event.collideEdge.brokenDiffy3;
                              graph.event.collideEdge.y4=graph.event.collideEdge.brokenDiffy4;
                          
                              graph.event.collideEdge.updateBrokenEdge(graph.event.collideEdge);

                              graph.drawData();
                            }else if(graph.moveWO==true&&epageY>=graph.currentWEdgeY1&&epageY<=graph.currentWEdgeY2){
                              graph.event.collideEdge.x1=graph.event.collideEdge.brokenDiffx1;
                              graph.event.collideEdge.x2=graph.event.collideEdge.brokenDiffx2;
                              graph.event.collideEdge.x3=graph.event.collideEdge.brokenDiffx3;
                              graph.event.collideEdge.x4=graph.event.collideEdge.brokenDiffx4;
                              graph.event.collideEdge.y1=e.pageY-graph.event.collideEdge.brokenDiffy1;
                              graph.event.collideEdge.y2=graph.event.collideEdge.brokenDiffy2;
                              graph.event.collideEdge.y3=e.pageY-graph.event.collideEdge.brokenDiffy3;
                              graph.event.collideEdge.y4=graph.event.collideEdge.brokenDiffy4;                        
                              graph.event.collideEdge.updateBrokenEdge(graph.event.collideEdge);
                              graph.drawData();
                            }else if(graph.moveWT==true&&epageY>=graph.currentHEdgeY1&&epageY<=graph.currentHEdgeY2){
                              
                              graph.event.collideEdge.x1=graph.event.collideEdge.brokenDiffx1;
                              graph.event.collideEdge.x2=graph.event.collideEdge.brokenDiffx2;
                              graph.event.collideEdge.x3=graph.event.collideEdge.brokenDiffx3;
                              graph.event.collideEdge.x4=graph.event.collideEdge.brokenDiffx4;
                              graph.event.collideEdge.y1=graph.event.collideEdge.brokenDiffy1;
                              graph.event.collideEdge.y2=e.pageY-graph.event.collideEdge.brokenDiffy2;
                              graph.event.collideEdge.y3=graph.event.collideEdge.brokenDiffy3;
                              graph.event.collideEdge.y4=e.pageY-graph.event.collideEdge.brokenDiffy4;
                          
                              graph.event.collideEdge.updateBrokenEdge(graph.event.collideEdge);
                              graph.drawData();
                            }    
                          }
                          
                      }
                     if(graph.event.collideNode){
                        if(graph.event.collideNode.elementType=="Node"){
                            graph.event.collideNode.x=e.pageX-graph.event.collideNode.diffx;
                            graph.event.collideNode.y=e.pageY-graph.event.collideNode.diffy;
                            graph.event.collideNode.updateNode(graph.event.collideNode);
                            
                            // LANNode的xy坐标
                            for(var i=0;i<graph.childs.length;i++){
                                var LanNodes=graph.childs[i];
                                if(LanNodes.elementType=="LanNode"){
                                    if(LanNodes.nodeId==graph.event.collideNode.id){
                                        LanNodes.x1=e.pageX-LanNodes.diffx1;
                                        LanNodes.y1=e.pageY-LanNodes.diffy1;
                                        LanNodes.x2=e.pageX-LanNodes.diffx2;
                                        LanNodes.y2=LanNodes.y2;
                                        LanNodes.updateLanNode(LanNodes);
                                        var textNode=getTextNodeByLanNode(LanNodes.id);
                                        var textNodeLan=getTextNodeByLanNode(LanNodes.id+1);
                                        if(textNode){
                                            textNode.x=LanNodes.x1-LanNodes.ntdiffx1;
                                            textNode.y=LanNodes.y1-LanNodes.ntdiffy1;
                                            textNode.updateNode(textNode);

                                        }
                                        if(textNodeLan){
                                            textNodeLan.x=LanNodes.x2-LanNodes.ntdiffx;
                                            textNodeLan.y=LanNodes.y2-LanNodes.ntdiffy;
//                                            console.log(textNodeLan.textId)
                                            textNode.updateNode(textNodeLan);

                                        }
                                    }
                                }
                            }
                            
                            // *********
                            var edges=graph.getEdges();
                            
                            var relatEdges=[];
                            for(var i=0;i<edges.length;i++){
                                var nodeA=getNodeById(edges[i].nodeAId);
                                var nodeB=getNodeById(edges[i].nodeBId);
                                if(graph.event.collideNode.id==edges[i].nodeAId || graph.event.collideNode.id==edges[i].nodeBId){
                                     relatEdges.push(edges[i]);
                                     var textNode=getTextNodeByEdge(edges[i],e);
                                     if(textNode){
                                         textNode.x1=edges[i].labelX-edges[i].ntdiffx;
                                         textNode.y1=edges[i].labelY-edges[i].ntdiffy;
                                         textNode.updateNode(textNode);     
                                     }
                                }
                            }
                            
                            for(var i=0;i<graph.childs.length;i++){
                                var BrokenEdges=graph.childs[i];  
                                if(BrokenEdges.elementType=="EdgeBroken"){           
                                    if(BrokenEdges.nodeAId==graph.event.collideNode.id){
                                        BrokenEdges.x1=e.pageX-BrokenEdges.brokenDiffx1;
                                        BrokenEdges.x2=BrokenEdges.brokenDiffx2;
                                        BrokenEdges.x3=e.pageX-BrokenEdges.brokenDiffx3;
                                        BrokenEdges.x4=e.pageX-BrokenEdges.brokenDiffx4;
                                        BrokenEdges.y1=e.pageY-BrokenEdges.brokenDiffy1;
                                        BrokenEdges.y2=BrokenEdges.brokenDiffy2;
                                        BrokenEdges.y3=e.pageY-BrokenEdges.brokenDiffy3;
                                        BrokenEdges.y4=BrokenEdges.brokenDiffy4;
                                        BrokenEdges.updateBrokenEdge(BrokenEdges);
                                    }else if(BrokenEdges.nodeBId==graph.event.collideNode.id){
                                        BrokenEdges.x1=BrokenEdges.brokenDiffx1;
                                        BrokenEdges.x2=e.pageX-BrokenEdges.brokenDiffx2;
                                        BrokenEdges.x3=e.pageX-BrokenEdges.brokenDiffx3;
                                        BrokenEdges.x4=e.pageX-BrokenEdges.brokenDiffx4;
                                        BrokenEdges.y1=BrokenEdges.brokenDiffy1;
                                        BrokenEdges.y2=e.pageY-BrokenEdges.brokenDiffy2;
                                        BrokenEdges.y3=BrokenEdges.brokenDiffy3;
                                        BrokenEdges.y4=e.pageY-BrokenEdges.brokenDiffy4;
                                        BrokenEdges.updateBrokenEdge(BrokenEdges);
                                    }
                                    }
                            }

                            // 线路文本随动
                            for(var i=0;i<relatEdges.length;i++){
                                getTextNodeByEdge(relatEdges[i],e);
                                
                                    graph.currentX=relatEdges[i].labelX;
                                    graph.currentY=relatEdges[i].labelY;
                            }

                            // 节点文本随动
                            var textNode=getTextNodeByNode(graph.event.collideNode.id);
                            if(textNode){
                                
                                textNode.x=graph.event.collideNode.x-graph.event.collideNode.ntdiffx;
                                textNode.y=graph.event.collideNode.y-graph.event.collideNode.ntdiffy;
                                textNode.updateNode(textNode);

                            } 

                            
                            
                            //关联线路文本位置变动
                            var relationLines=graph.getRelateLines();
                            
                            for(var i=0;i<relationLines.length;i++){
                            	var trunkLine=relationLines[i].relateArray[0];
                            	
                            	if(trunkLine.nodeAId==graph.event.collideNode.id||trunkLine.nodeBId==graph.event.collideNode.id){
                            		var nodeA=getNodeById(trunkLine.nodeAId);
                            		 var nodeB=getNodeById(trunkLine.nodeBId);
                            		 if(nodeA.x<nodeB.x){
                            			 
                            			 relationLines[i].leftName=graph.event.oldLeftName;
                            			 relationLines[i].rightName=graph.event.oldRightName;
                            		 }else{
                            			 
                            			 relationLines[i].leftName=graph.event.oldRightName;
                            			 relationLines[i].rightName=graph.event.oldLeftName;
                            		 }
                            		 
                            		 relationLines[i].updateRelateLine(relationLines[i]);
                            	}
                            	
                            	
                            }
                            
                            graph.drawData();
                            graph.shadow(graph.selectedData);
                        }else if(graph.event.collideNode.elementType=="TextNode"){
                           
                            if(graph.event.collideNode.isDrawEdgeLabel){// 线的文本
                                /*if(graph.event.collideNode.radius==0){ 
                                    graph.event.collideNode.x=e.pageX-graph.event.collideNode.textWidth/2;
                                    graph.event.collideNode.y=e.pageY+graph.event.collideNode.textSize/2;
                                }else if(graph.event.collideNode.radius>0&&graph.event.collideNode.radius<1){
                                    graph.event.collideNode.x=e.pageX-graph.event.collideNode.textWidth/2+graph.event.collideNode.radius*4;
                                    graph.event.collideNode.y=e.pageY;
                                }else{
                                    graph.event.collideNode.x=e.pageX-graph.event.collideNode.textWidth/2+graph.event.collideNode.radius*4;
                                    graph.event.collideNode.y=e.pageY;
                                }
                                    graph.event.collideNode.diffx=graph.event.collideNode.x-graph.nodeX;
                                    graph.event.collideNode.diffy=graph.event.collideNode.y-graph.nodeY;
                                graph.event.collideNode.updateNode(graph.event.collideNode);
                                graph.drawData();*/
                            }else{// 节点文本
                                graph.event.collideNode.x=e.pageX-leftwidth-graph.event.collideNode.textWidth/2;
                                graph.event.collideNode.y=e.pageY-topheight+graph.event.collideNode.textSize/2;
                                graph.event.collideNode.updateNode(graph.event.collideNode);
                                graph.drawData();
                            }
                                                                
                        }else if(graph.event.collideNode.elementType=="LAN"){
                            graph.event.collideNode.x1=e.pageX-graph.event.collideNode.diffx1;
                            graph.event.collideNode.y1=e.pageY-graph.event.collideNode.diffy1;
                            graph.event.collideNode.x2=e.pageX-graph.event.collideNode.diffx2;
                            graph.event.collideNode.y2=e.pageY-graph.event.collideNode.diffy2;
                            graph.event.collideNode.updateLan(graph.event.collideNode);
                            
                            // LanNode的xy坐标
                            for(var i=0;i<graph.childs.length;i++){
                                var LanNodes=graph.childs[i];
                                if(LanNodes.elementType=="LanNode"){
                                    if(LanNodes.lanId==graph.event.collideNode.id){
                                    	LanNodes.x1=LanNodes.x1;
                                        LanNodes.y1=LanNodes.y1;
                                        LanNodes.x2=LanNodes.x2;
                                        LanNodes.y2=e.pageY-LanNodes.diffy2;
                                        LanNodes.updateLanNode(LanNodes);
                                        var textNode=getTextNodeByLanNode(LanNodes.id);
                                        var textNodeLan=getTextNodeByLanNode(LanNodes.id+1);
                                        if(textNode){
                                            textNode.x=LanNodes.x1-LanNodes.ntdiffx1;
                                            textNode.y=LanNodes.y1-LanNodes.ntdiffy1;
                                            textNode.updateNode(textNode);

                                        }
                                        if(textNodeLan){
                                            textNodeLan.x=LanNodes.x2-LanNodes.ntdiffx;
                                            textNodeLan.y=LanNodes.y2-LanNodes.ntdiffy;
//                                            console.log(textNodeLan.textId)
                                            textNode.updateNode(textNodeLan);

                                        }
                                    }
                                }
                            }
                            
                            // LAN线拉伸收缩
                            if((epageX<=graph.event.collideNode.x2+4&&epageX>=graph.event.collideNode.x2-8)&&(epageY<=graph.event.collideNode.y2+4&&epageY>=graph.event.collideNode.y2-4)){

                                graph.event.collideNode.isDragSize=true;
                                graph.canvas.style.cursor="e-resize";
                                graph.event.collideNode.y1=graph.event.collideNode.diffY1;
                                graph.event.collideNode.y2=graph.event.collideNode.diffY2;
                                graph.event.collideNode.x1=graph.event.collideNode.diffX1;
                                graph.event.collideNode.x2=e.pageX-243;
                                graph.event.collideNode.updateLan(graph.event.collideNode);
                               
                            }
                            if((epageX<=graph.event.collideNode.x1+4&&epageX>=graph.event.collideNode.x1-4)&&(epageY<=graph.event.collideNode.y1+4&&epageY>=graph.event.collideNode.y1-4)){

                                graph.event.collideNode.isDragSize=true;
                                graph.canvas.style.cursor="e-resize";
                                graph.event.collideNode.y1=graph.event.collideNode.ndiffY1;
                                graph.event.collideNode.y2=graph.event.collideNode.ndiffY2;
                                graph.event.collideNode.x1=e.pageX-243;
                                graph.event.collideNode.x2=graph.event.collideNode.ndiffX2;
                                graph.event.collideNode.updateLan(graph.event.collideNode);
                            }


                             var textNode=getTextNodeByLan(graph.event.collideNode.id);
                            if(textNode){
                                
                            	if(graph.event.collideNode.x2>graph.event.collideNode.x1){
                                    textNode.x=graph.event.collideNode.x2-graph.event.collideNode.ntdiffx;
                                    textNode.y=graph.event.collideNode.y2-graph.event.collideNode.ntdiffy;                                    
                                }else{
                                    textNode.x=graph.event.collideNode.x1-graph.event.collideNode.ntdiffx;
                                    textNode.y=graph.event.collideNode.y1-graph.event.collideNode.ntdiffy;                                    
                                }
                            	textNode.updateNode(textNode);
                            } 
                            graph.drawData();
                    }else if(graph.event.collideNode.elementType=="TextRank"){
                        graph.isDblText=false;
                        graph.event.collideNode.x=e.pageX-graph.event.collideNode.diffx;
                        graph.event.collideNode.y=e.pageY-graph.event.collideNode.diffy;
                        graph.event.collideNode.updatetextRank(graph.event.collideNode);
                        graph.drawData();
                  }else if(graph.event.collideNode.elementType=="TextVertical"){
                          graph.isDblText=false;
                        graph.event.collideNode.x=e.pageX-graph.event.collideNode.diffx;
                        graph.event.collideNode.y=e.pageY-graph.event.collideNode.diffy;
                        graph.event.collideNode.updatetextVertical(graph.event.collideNode);
                        graph.drawData();
                  }
                        
                     }
                }else if(graph.event.isMoveDrap){  // 移动所有

                         for(var i=0;i<graph.childs.length;i++){
                         var node=graph.childs[i];
                         var edge=graph.childs[i];
                         var Lan=graph.childs[i];
                         var LanNodes=graph.childs[i];
                         var TextRanks=graph.childs[i];
                         var TextVerticals=graph.childs[i];
                         var BrokenEdges=graph.childs[i];
                            if(node.elementType=="Node"){
                                node.x=e.pageX-node.diffx;
                                node.y=e.pageY-node.diffy;
                                node.updateNode(node);
                                var textNode=getTextNodeByNode(node.id);
                                if(textNode){
                                    textNode.x=node.x-node.ntdiffx;
                                    textNode.y=node.y-node.ntdiffy;
                                    textNode.updateNode(textNode);
                                }
                            }
                            if(edge.elementType=="Edge"){
                                getTextNodeByEdge(edge,e);
                            }
                            if(edge.elementType=="Edge"){
                                var textNode=getTextNodeByEdge(edge,e);
                                if(textNode){
                                    textNode.x1=edge.labelX-edge.ntdiffx;
                                    textNode.y1=edge.labelY-edge.ntdiffy;
                                    textNode.updateNode(textNode);
                                }
                            }
                            if(Lan.elementType=="LAN"){
                                Lan.x1=e.pageX-Lan.diffx1;
                                Lan.y1=e.pageY-Lan.diffy1;
                                Lan.x2=e.pageX-Lan.diffx2;
                                Lan.y2=e.pageY-Lan.diffy2;
                                Lan.updateLan(Lan);
                                var textNode=getTextNodeByLan(Lan.id);
                                if(textNode){
                                	if(Lan.x2>Lan.x1){
                                        textNode.x=Lan.x2-Lan.ntdiffx;
                                        textNode.y=Lan.y2-Lan.ntdiffy;
                                    }else{
                                        textNode.x=Lan.x1-Lan.ntdiffx;
                                        textNode.y=Lan.y1-Lan.ntdiffy;
                                    }
                                    textNode.updateNode(textNode);
                                }
                           }
                            if(LanNodes.elementType=="LanNode"){
                                LanNodes.x1=e.pageX-LanNodes.diffx1;
                                LanNodes.y1=e.pageY-LanNodes.diffy1;
                                LanNodes.x2=e.pageX-LanNodes.diffx2;
                                LanNodes.y2=e.pageY-LanNodes.diffy2;
                                LanNodes.updateLanNode(LanNodes);
                                var textNode=getTextNodeByLanNode(LanNodes.id);
                                        var textNodeLan=getTextNodeByLanNode(LanNodes.id+1);
                                        if(textNode){
                                            textNode.x=LanNodes.x1-LanNodes.ntdiffx1;
                                            textNode.y=LanNodes.y1-LanNodes.ntdiffy1;
                                            textNode.updateNode(textNode);
                                        }
                                        if(textNodeLan){
                                            textNodeLan.x=LanNodes.x2-LanNodes.ntdiffx;
                                            textNodeLan.y=LanNodes.y2-LanNodes.ntdiffy;
//                                            console.log(textNodeLan.textId)
                                            textNodeLan.updateNode(textNodeLan);

                                        }
                            }
                            if(TextRanks.elementType=="TextRank"){
                                TextRanks.x=e.pageX-TextRanks.diffx;   
                                TextRanks.y=e.pageY-TextRanks.diffy;
                            }

                            if(TextVerticals.elementType=="TextVertical"){
                                TextVerticals.x=e.pageX-TextVerticals.diffx;   
                                TextVerticals.y=e.pageY-TextVerticals.diffy;
                            }
                            if(BrokenEdges.elementType=="EdgeBroken"){  
                                BrokenEdges.x1=e.pageX-BrokenEdges.brokenDiffx1;
                                BrokenEdges.x2=e.pageX-BrokenEdges.brokenDiffx2;
                                BrokenEdges.x3=e.pageX-BrokenEdges.brokenDiffx3;
                                BrokenEdges.x4=e.pageX-BrokenEdges.brokenDiffx4;
                                BrokenEdges.y1=e.pageY-BrokenEdges.brokenDiffy1;
                                BrokenEdges.y2=e.pageY-BrokenEdges.brokenDiffy2;
                                BrokenEdges.y3=e.pageY-BrokenEdges.brokenDiffy3;
                                BrokenEdges.y4=e.pageY-BrokenEdges.brokenDiffy4;
                                BrokenEdges.updateBrokenEdge(BrokenEdges);
                            }   
                     }
                     graph.drawData();

                }

            }else if(graph.graphModel=="Edit"){
                if(graph.event.currentNode){
                    graph.clear();
                    graph.ctx.beginPath();
                    graph.drawData();
                    
                    var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                    var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                    
                    
                    graph.ctx.moveTo(ox,oy);
                    
                    graph.ctx.lineTo(e.pageX-leftwidth,e.pageY-topheight);
                    
                    
// console.log(e.pageX+"-----------"+e.pageY);
                    graph.ctx.stroke();

                }
                
            }else if(graph.graphModel=="EditBroken"){

                if(graph.event.currentNode){
                    graph.clear();
                    graph.ctx.beginPath();
                    graph.drawData();
                    var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                    var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                    graph.ctx.moveTo(ox,oy);
                    if(ox<(e.pageX-leftwidth)){
                        graph.ctx.lineTo(Math.abs((ox-(e.pageX-leftwidth))/2)+ox,oy);
                        graph.ctx.lineTo(Math.abs((ox-(e.pageX-leftwidth))/2)+ox,e.pageY-topheight);
                    }else{
                        graph.ctx.lineTo(Math.abs(ox-(ox-(e.pageX-leftwidth))/2),oy);
                        graph.ctx.lineTo(Math.abs(ox-(ox-(e.pageX-leftwidth))/2),e.pageY-topheight);
                    }
                    graph.ctx.lineTo(e.pageX-leftwidth,e.pageY-topheight);
                    graph.ctx.stroke();

                }
                
            }else if(graph.graphModel=="EditBrokenArrow"){

                if(graph.event.currentNode){
                    graph.clear();
                    graph.ctx.beginPath();
                    graph.drawData();
                    var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                    var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                    graph.ctx.moveTo(ox,oy);
                    if(ox<(e.pageX-leftwidth)){
                        graph.ctx.lineTo(Math.abs((ox-(e.pageX-leftwidth))/2)+ox,oy);
                        graph.ctx.lineTo(Math.abs((ox-(e.pageX-leftwidth))/2)+ox,e.pageY-topheight);
                    }else{
                        graph.ctx.lineTo(Math.abs(ox-(ox-(e.pageX-leftwidth))/2),oy);
                        graph.ctx.lineTo(Math.abs(ox-(ox-(e.pageX-leftwidth))/2),e.pageY-topheight);
                    }
                    graph.ctx.lineTo(e.pageX-leftwidth,e.pageY-topheight);
                    graph.ctx.stroke();

                }
                
            }else if(graph.graphModel=="EditEdgeSolidArrow"){

                if(graph.event.currentNode){
                    graph.clear();
                    graph.ctx.beginPath();
                    graph.drawData();
                    var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                    var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                    graph.ctx.moveTo(ox,oy);
                    graph.ctx.lineTo(e.pageX-leftwidth,e.pageY-topheight);
                    graph.ctx.stroke();

                }
                
            }else if(graph.graphModel=="EditEdgeEmptyArrow"){

                if(graph.event.currentNode){
                    graph.clear();
                    graph.ctx.beginPath();
                    graph.drawData();
                    var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                    var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                    graph.ctx.moveTo(ox,oy);
                    graph.ctx.lineTo(e.pageX-leftwidth,e.pageY-topheight);
                    graph.ctx.stroke();

                }
                
            }else if(graph.graphModel=="EditDashEdgeSolidArrow"){

                if(graph.event.currentNode){
                    graph.clear();
                    graph.ctx.beginPath();
                    graph.drawData();
                    var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                    var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                    graph.ctx.moveTo(ox,oy);
                    graph.ctx.lineTo(e.pageX-leftwidth,e.pageY-topheight);
                    graph.ctx.stroke();

                }
                
            }else if(graph.graphModel=="EditDashEdgeEmptyArrow"){

                if(graph.event.currentNode){
                    graph.clear();
                    graph.ctx.beginPath();
                    graph.drawData();
                    var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                    var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                    graph.ctx.moveTo(ox,oy);
                    graph.ctx.lineTo(e.pageX-leftwidth,e.pageY-topheight);
                    graph.ctx.stroke();

                }
                
            }else if(graph.graphModel=="relateEdge"){
                var objE=graph.getEdgeByMouseEvent(e);
                
                if(graph.currentX!=epageX||graph.currentY!=epageY){
                   var a=Math.abs((epageX-graph.currentX))/2;
                   var b=Math.abs(epageY-graph.currentY)/2;
                   var relateX=Math.abs((epageX-graph.currentX))/2+graph.currentX;
                   var relateY=Math.abs((epageY-graph.currentY))/2+graph.currentY;
                   var r = (a > b) ? a : b; 
                   var ratioX = a / r; //横轴缩放比率 
                   var ratioY = b / r; //纵轴缩放比率  
                }  
               if(graph.event.isRelate){
                   graph.clear();
                   graph.ctx.save();
                   graph.ctx.beginPath();
                   graph.drawData();
                   graph.ctx.scale(ratioX, ratioY); //进行缩放（均匀压缩）        
                   //从椭圆的左端点开始逆时针绘制 
                   graph.ctx.moveTo((relateX+a)/ratioX,relateY/ratioY); 
                   graph.ctx.arc(relateX/ratioX,relateY/ratioY,r, 0, 2 * Math.PI); 
                   graph.ctx.translate(relateX,relateY); 
                          
                   graph.ctx.stroke();
                   graph.ctx.restore();


                   if(objE){
                       
                        graph.event.nodeA=getNodeById(objE.nodeAId);
                        graph.event.nodeB=getNodeById(objE.nodeBId);
                        graph.event.edgeRelate.push(objE);
                   }
               }      

           
       }else if(graph.graphModel=="Lan"){

                graph.clear();
                graph.ctx.beginPath();
                graph.drawData();
                var ox=graph.currentX;
                var oy=graph.currentY;
                if(ox!=0&&oy!=0){
                    graph.ctx.moveTo(ox,oy);
                    graph.ctx.lineTo(epageX,epageY);
                    graph.ctx.stroke();
                }
                
                

            
        }else if(graph.graphModel=="dropLan"){

            if(graph.event.currentNode){
                graph.clear();
                graph.ctx.beginPath();
                graph.drawData();
                var ox=graph.event.currentNode.x+graph.event.currentNode.width/2;
                var oy=graph.event.currentNode.y+graph.event.currentNode.height/2;
                graph.ctx.moveTo(ox,oy);
                graph.ctx.lineTo(epageX,epageY);
                graph.ctx.stroke();

            }
                
        }else if(graph.graphModel=="Select"){
                
        	if(graph.event.isDragSelect){
                graph.canvas.style.cursor="crosshair";
                graph.event.selectArea.style.display="block";
                graph.event.selectEX = epageX;
                graph.event.selectEY = epageY;
                
                if(e.pageX<graph.event.selectSX||e.pageY<graph.event.selectSY){
                    
                    graph.event.selectArea.style.left = e.pageX+5+ 'px';
                    graph.event.selectArea.style.top = e.pageY+5+ 'px';
                }
                graph.event.selectArea.style.width = Math.abs(graph.event.selectEX - graph.event.selectSX) + 'px';
                graph.event.selectArea.style.height = Math.abs(graph.event.selectEY - graph.event.selectSY) + 'px';

                graph.event.selectArea.style.background='rgba(15,144,223,0.2)';
                graph.event.selectArea.style.position="absolute";
              

              } 
              if(graph.event.isDragDrap){
                
                 for(var i=0;i<graph.selectedData.length;i++){
                     var node=graph.selectedData[i];
                     var edge=graph.selectedData[i];
                        if(node.elementType=="Node"){
                            node.x=e.pageX-node.diffx;
                            node.y=e.pageY-node.diffy;

                            node.updateNode(node);
                            for(var k=0;k<graph.childs.length;k++){
                                var BrokenEdges=graph.childs[k];
                                var nodeA=getNodeById(BrokenEdges.nodeAId);  
                                var nodeB=getNodeById(BrokenEdges.nodeBId); 
                                if(BrokenEdges.elementType=="EdgeBroken"){          
                                    if(BrokenEdges.nodeAId==node.id){
                                        BrokenEdges.x1=e.pageX-BrokenEdges.brokenDiffx1;
                                        BrokenEdges.x2=BrokenEdges.brokenDiffx2;
                                        BrokenEdges.x3=e.pageX-BrokenEdges.brokenDiffx3;
                                        BrokenEdges.x4=e.pageX-BrokenEdges.brokenDiffx4;
                                        BrokenEdges.y1=e.pageY-BrokenEdges.brokenDiffy1;
                                        BrokenEdges.y2=BrokenEdges.brokenDiffy2;
                                        BrokenEdges.y3=e.pageY-BrokenEdges.brokenDiffy3;
                                        BrokenEdges.y4=BrokenEdges.brokenDiffy4;
                                        BrokenEdges.updateBrokenEdge(BrokenEdges);
                                    }else if(BrokenEdges.nodeBId==node.id){
                                        BrokenEdges.x1=BrokenEdges.brokenDiffx1;
                                        BrokenEdges.x2=e.pageX-BrokenEdges.brokenDiffx2;
                                        BrokenEdges.x3=e.pageX-BrokenEdges.brokenDiffx3;
                                        BrokenEdges.x4=e.pageX-BrokenEdges.brokenDiffx4;
                                        BrokenEdges.y1=BrokenEdges.brokenDiffy1;
                                        BrokenEdges.y2=e.pageY-BrokenEdges.brokenDiffy2;
                                        BrokenEdges.y3=BrokenEdges.brokenDiffy3;
                                        BrokenEdges.y4=e.pageY-BrokenEdges.brokenDiffy4;
                                        BrokenEdges.updateBrokenEdge(BrokenEdges);
                                    }
                                    if(graph.selectedData.indexOf(nodeA)>-1&&graph.selectedData.indexOf(nodeB)>-1){
                                        BrokenEdges.x1=e.pageX-BrokenEdges.brokenDiffx1;
                                        BrokenEdges.x2=e.pageX-BrokenEdges.brokenDiffx2;
                                        BrokenEdges.x3=e.pageX-BrokenEdges.brokenDiffx3;
                                        BrokenEdges.x4=e.pageX-BrokenEdges.brokenDiffx4;
                                        BrokenEdges.y1=e.pageY-BrokenEdges.brokenDiffy1;
                                        BrokenEdges.y2=e.pageY-BrokenEdges.brokenDiffy2;
                                        BrokenEdges.y3=e.pageY-BrokenEdges.brokenDiffy3;
                                        BrokenEdges.y4=e.pageY-BrokenEdges.brokenDiffy4;
                                        BrokenEdges.updateBrokenEdge(BrokenEdges);
                                        
                                    }      
                                }
                                }
                            var textNode=getTextNodeByNode(node.id);
                            if(textNode){
                                textNode.x=node.x-node.ntdiffx;
                                textNode.y=node.y-node.ntdiffy;
                                textNode.updateNode(textNode);
                            }
                        }
                        if(edge.elementType=="Edge"){
                            getTextNodeByEdge(edge)
                        }
                 
                 }
                 graph.drawData();
                 graph.shadow(graph.selectedData);
            }
                  
            }
            
        });

		this.getEdgeByMouseEvent=function(event){
			var edgeArray=graph.edgeMap.elements;
            var locXY=windowTocanvas(graph.canvas,event.pageX,event.pageY);
            var ox=locXY.x;
            var oy=locXY.y;
            var obj = null;
			  var edgeArray=graph.edgeMap.elements;
		      for(var i=0;i<edgeArray.length;i++){
		          var o=edgeArray[i];
		          var key=o.key;
		          var value=o.value;
		          var a=Math.abs(30*Math.sin(Math.atan(value.slope)));
                  var b=Math.abs(30*Math.cos(Math.atan(value.slope)));

		        //判断贝塞尔曲线
		          for (var t = 0.0; t <= 1.0; t += 0.01)
		        {
		            var x=key.x0*Math.pow((1-t),3)+3*key.x1*t*Math.pow((1-t),2)+3*key.x2*Math.pow(t,2)*(1-t)+key.x3*Math.pow(t,3);
		            var y=key.y0*Math.pow((1-t),3)+3*key.y1*t*Math.pow((1-t),2)+3*key.y2*Math.pow(t,2)*(1-t)+key.y3*Math.pow(t,3);
		            // alert("x="+x+"  "+"y="+y);
		            //鼠标从节点外获取线段
		            if((x>key.x0+b&&x<key.x3-b)||(y<key.y0-a&&y>key.y3+a)||(x<key.x0-b&&x>key.x3+b)){
		            	if (Math.abs(x - event.pageX+leftwidth) < 2.5 && Math.abs(y - event.pageY+topheight) < 2.5&&value.isBroken==false)
		            	{
		            		return value;
		            	}
                    
		            }
                    //判断折线时鼠标碰撞
                    if(value.isBroken==true){  
                        var nodeA=getNodeById(value.nodeAId);                      
                        var nodeB=getNodeById(value.nodeBId);                      
                        // var x1=nodeA.x+nodeA.height/2;
                        // var y1=nodeA.y+nodeA.width/2;
                        // var x2=nodeB.x+nodeB.height/2;
                        // var y2=nodeB.y+nodeB.width/2;
                        var x1=value.x1;
                        var y1=value.y1;
                        var x2=value.x2;
                        var y2=value.y2;
                        var x3=value.x3;
                        var y3=value.y3;
                        var x4=value.x4;
                        var y4=value.y4;
                        if(x1<x2&&y1<y2){                             
                            if(x3>x1){
                               //横线1段
                                if((ox>=x1&&ox<=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }  
                            }else{
                              //横线1段
                                if((ox<=x1&&ox>=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }    
                            }
                            if(x2>x4){
                                //横线2段  
                                if((ox>=x4&&ox<=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                } 
                            }else{
                               //横线2段  
                                if((ox<=x4&&ox>=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                }  
                            }
                            //竖线1段                                        
                            if((oy>=y3&&oy<=y4)&&((ox+2)>x3&&(ox-2)<x3)){
                                return value;
                            }                      
                        }else if(x1<x2&&y1>y2){
                            
                            if(x3>x1){
                               //横线1段
                                if((ox>=x1&&ox<=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }  
                            }else{
                              //横线1段
                                if((ox<=x1&&ox>=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }    
                            }
                            if(x2>x4){
                                //横线2段  
                                if((ox>=x4&&ox<=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                } 
                            }else{
                               //横线2段  
                                if((ox<=x4&&ox>=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                }  
                            }
                            //竖线1段                                        
                            if((oy>=y4&&oy<=y3)&&((ox+2)>x3&&(ox-2)<x3)){
                                return value;
                            }          
                        }else if(x1>x2&&y1>y2){
                            
                            if(x3>x1){
                               //横线1段
                                if((ox>=x1&&ox<=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }  
                            }else{
                              //横线1段
                                if((ox<=x1&&ox>=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }    
                            }
                            if(x2>x4){
                                //横线2段  
                                if((ox>=x4&&ox<=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                } 
                            }else{
                               //横线2段  
                                if((ox<=x4&&ox>=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                }  
                            }
                            //竖线1段                                        
                            if((oy<=y3&&oy>=y4)&&((ox+2)>x3&&(ox-2)<x3)){
                                return value;
                            }  
                        }else if(x1>x2&&y1<y2){
                            if(x3>x1){
                               //横线1段
                                if((ox>=x1&&ox<=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }  
                            }else{
                              //横线1段
                                if((ox<=x1&&ox>=x3)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }    
                            }
                            if(x2>x4){
                                //横线2段  
                                if((ox>=x4&&ox<=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                } 
                            }else{
                               //横线2段  
                                if((ox<=x4&&ox>=x2)&&((oy+2)>y2&&(oy-2)<y2)){
                                    return value;
                                }  
                            }
                            //竖线1段                                        
                            if((oy>=y3&&oy<=y4)&&((ox+2)>x3&&(ox-2)<x3)){
                                return value;
                            }  
                        }else if(y1==y2){
                            if(x1<x2&&x4<x2){                                
                                //横线1段
                                if((ox>=x1&&ox<=x2)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                } 
                            }else{
                               //横线1段
                                if((ox<=x1&&ox>=x2)&&((oy+2)>y1&&(oy-2)<y1)){
                                    return value;
                                }  
                            }                            
                        }
                        
                    }
                    
		        }
          
		  }		          	       
		      }
		
		

		this.drawData=function(){
			graph.clear();
			graph.edgeMap.clear();
			var edges=graph.getEdges();
			var brokenEdge=graph.brokenEdge;
			var edgeMap =new Map();
			
			edges.forEach(function(e){
				var key=e.nodeAId+e.nodeBId;
				var nodeA=getNodeById(e.nodeAId);
				var nodeB=getNodeById(e.nodeBId);
				
				if(nodeA&&nodeB){
					var lines=graph.getEdgesByTwoNodes(nodeA,nodeB);
					
					if(!edgeMap.containsKey(key)){
						edgeMap.put(key,lines);
						
					}
	                e.drawEdgeLabel(nodeA,nodeB); 
				}
				//显示隐藏线关系文本时线文本位置更新
				for(var i=0;i<graph.EdgeText.length;i++){ 
                    if(graph.EdgeText[i].textId==e.id){
                        graph.EdgeText[i].x1=e.labelX;
                        graph.EdgeText[i].y1=e.labelY;
                    }
                }   
			});
			//画折线
            var brokenEdges=graph.getBrokenEdges();
            brokenEdges.forEach(function(n){
                var nodeA=getNodeById(n.nodeAId);
                var nodeB=getNodeById(n.nodeBId);              
                n.paintEdge();
                if(n.isArrow){
                    n.drawArrow(nodeA,nodeB);
                }
                if(n.isDrawEdgeLabel==true){
                    n.drawEdgeLabel(nodeA,nodeB)
                }
                for(var i=0;i<graph.EdgeText.length;i++){ 
                    if(graph.EdgeText[i].textId==n.id){
                        graph.EdgeText[i].x1=n.labelX;
                        graph.EdgeText[i].y1=n.labelY;
                    }
                }
            })
            //折线移动点
            brokenEdge.forEach(function(n){
                var nodeA=getNodeById(n.nodeAId);
                var nodeB=getNodeById(n.nodeBId);    
                n.EdgeBroken(nodeA,nodeB);
                
            })
			var edgeElement=edgeMap.elements;
			
			for(var i=0;i<edgeElement.length;i++){
				graph.paintEdge(edgeElement[i]);
			}
			/*var LanNodes=graph.getLanNodes();
            LanNodes.forEach(function(e){
                var nodeA=getNodeById(e.nodeId);
                var lan=getLanById(e.lanId);
                if(nodeA){
                	 e.paint(nodeA);
                }
            });*/
			
			
			/*var lans=graph.getLans();
            lans.forEach(function(e){
                e.paint();
            });
            */
			var nodes=graph.getAllNodes();
			nodes.forEach(function(n){
				n.paint();
			});
			/*var relateLines=graph.getRelateLines();
            relateLines.forEach(function(n){
                n.paint();
            });*/
		}
		
		this.paintEdge=function(obj){

			var edges=obj.value;
			
			for(var i=0;i<edges.length;i++){
				var nodeA=getNodeById(edges[i].nodeAId);
				var nodeB=getNodeById(edges[i].nodeBId);
				var n=8;
                var nodeS=nodeA.x+nodeA.width/2;
                var nodeD=nodeA.y+nodeA.height/2;
                var nodeE=nodeB.x+nodeB.width/2;
                var nodeF=nodeB.y+nodeB.height/2;
                var math=Math.sqrt(Math.pow(nodeS-nodeE,2)+Math.pow(nodeD-nodeF,2));
                var angle=Math.acos((nodeD-nodeF)/math)*180/Math.PI;
                graph.ctx.setLineDash([edges[i].dashL,edges[i].dashO]);
                graph.ctx.strokeStyle=edges[i].color;

                 if(edges.length==1){  //等于一条直接画直线
                 	graph.ctx.beginPath();
                 	graph.ctx.lineWidth=edges[i].size;
                    graph.ctx.moveTo(nodeS,nodeD);
                    graph.ctx.lineTo(nodeE,nodeF);
                    var o=new Bound(nodeS,nodeD,nodeS,nodeD,nodeE,nodeF,nodeE,nodeF);
                    graph.edgeMap.put(o,edges[i]);
                    graph.ctx.stroke();
                    if(edges[i].isArrow){
                       edges[i].drawArrow(nodeA,nodeB);
                    }
                    if(edges[i].isDrawEdgeLabel){
                        edges[i].drawEdgeLabel(nodeA,nodeB);
                    }
                 }else{  //大于一条直线
                  if(edges.length%2==0){  //偶数
					if((i+1)%2==0){//对称画弧线
						 graph.ctx.beginPath(); 
						 graph.ctx.lineWidth=edges[i].size;
                         graph.ctx.moveTo(nodeS,nodeD);
                            if(angle>30&&angle<150){

                                   graph.ctx.bezierCurveTo(nodeS,nodeD+(i)*n,nodeE,nodeF+(i)*n,nodeE,nodeF);
                                   var o=new Bound(nodeS,nodeD,nodeS,nodeD+(i)*n,nodeE,nodeF+(i)*n,nodeE,nodeF);
                                   graph.edgeMap.put(o,edges[i]);
                                   

                                }else{
                                    graph.ctx.bezierCurveTo(nodeS+(i)*n,nodeD,nodeE+(i)*n,nodeF,nodeE,nodeF);
                                     var o=new Bound(nodeS,nodeD,nodeS+(i)*n,nodeD,nodeE+(i)*n,nodeF,nodeE,nodeF);
                                     graph.edgeMap.put(o,edges[i]);
                                     

                                }    
                                graph.ctx.stroke();
                                //edges[i].EdgeRelate(nodeA,nodeB,i)

					}else{
						    graph.ctx.beginPath();
						    	graph.ctx.lineWidth=edges[i].size;
                                graph.ctx.moveTo(nodeS,nodeD);
                                if(angle>30&&angle<150){
                                    graph.ctx.bezierCurveTo(nodeS,nodeD-(i+1)*n,nodeE,nodeF-(i+1)*n,nodeE,nodeF);
                                    var o=new Bound(nodeS,nodeD,nodeS,nodeD-(i+1)*n,nodeE,nodeF-(i+1)*n,nodeE,nodeF);
                                    graph.edgeMap.put(o,edges[i]);
                                    

                                }else{
                                    graph.ctx.bezierCurveTo(nodeS-(i+1)*n,nodeD,nodeE-(i+1)*n,nodeF,nodeE,nodeF);
                                    var o=new Bound(nodeS,nodeD,nodeS-(i+1)*n,nodeD,nodeE-(i+1)*n,nodeF,nodeE,nodeF);
                                    graph.edgeMap.put(o,edges[i]);
                                    

                                }    
                                graph.ctx.stroke();
                                //edges[i].EdgeRelate(nodeA,nodeB,i)
					}
				}else{//奇数
					if((i+1)%2==0){//对称画弧线
						 graph.ctx.beginPath();
						 graph.ctx.lineWidth=edges[i].size;
                         graph.ctx.moveTo(nodeS,nodeD);
                            if(angle>30&&angle<150){

                                   graph.ctx.bezierCurveTo(nodeS,nodeD+(i+1)*n,nodeE,nodeF+(i+1)*n,nodeE,nodeF);
                                   var o=new Bound(nodeS,nodeD,nodeS,nodeD+(i+1)*n,nodeE,nodeF+(i+1)*n,nodeE,nodeF);
                                   graph.edgeMap.put(o,edges[i]);
                                  

                                }else{
                                    graph.ctx.bezierCurveTo(nodeS+(i+1)*n,nodeD,nodeE+(i+1)*n,nodeF,nodeE,nodeF);
                                     var o=new Bound(nodeS,nodeD,nodeS+(i+1)*n,nodeD,nodeE+(i+1)*n,nodeF,nodeE,nodeF);
                                     graph.edgeMap.put(o,edges[i]);
                                     

                                }    
                                graph.ctx.stroke();
                                //edges[i].EdgeRelate(nodeA,nodeB,i)
					}else{
						if(i==edges.length-1){
							graph.ctx.beginPath();
							graph.ctx.lineWidth=edges[i].size;
		                    graph.ctx.moveTo(nodeS,nodeD);
		                    graph.ctx.lineTo(nodeE,nodeF);
                            var o=new Bound(nodeS,nodeD,nodeS,nodeD,nodeE,nodeF,nodeE,nodeF);
                            graph.edgeMap.put(o,edges[i]);
		                    graph.ctx.stroke();
		                    if(edges[i].isArrow){
                                edges[i].drawArrow(nodeA,nodeB);
                            }
                            if(edges[i].isDrawEdgeLabel){
                                edges[i].drawEdgeLabel(nodeA,nodeB,i);
                            }
						}else{
							 graph.ctx.beginPath();
							 graph.ctx.lineWidth=edges[i].size;
                                graph.ctx.moveTo(nodeS,nodeD);
                                if(angle>30&&angle<150){
                                    graph.ctx.bezierCurveTo(nodeS,nodeD-(i+2)*n,nodeE,nodeF-(i+2)*n,nodeE,nodeF);
                                    var o=new Bound(nodeS,nodeD,nodeS,nodeD-(i+2)*n,nodeE,nodeF-(i+2)*n,nodeE,nodeF);
                                    graph.edgeMap.put(o,edges[i]);
                                    

                                }else{
                                    graph.ctx.bezierCurveTo(nodeS-(i+2)*n,nodeD,nodeE-(i+2)*n,nodeF,nodeE,nodeF);
                                    var o=new Bound(nodeS,nodeD,nodeS-(i+2)*n,nodeD,nodeE-(i+2)*n,nodeF,nodeE,nodeF);
                                    graph.edgeMap.put(o,edges[i]);
                                   

                                }    
                                graph.ctx.stroke();
                                //edges[i].EdgeRelate(nodeA,nodeB,i)
						}
					}


				}


                 }
			}
		}


		 this.clear=function(){
			 graph.ctx.clearRect(0,0,graph.canvas.width,graph.canvas.height);
		 }

		 this.getElementByMouseEvent=function(e){
		 
		 var locXY=windowTocanvas(graph.canvas,e.pageX,e.pageY);
		
		 var ox=locXY.x;
	     var oy=locXY.y;
	     var objs=this.childs;
	     var obj = null;
	     var maxX=0;
	     var maxY=0;
	     var textFlag=false; // 判断elementArray 是否包含有TextNode
	     var elementArray=new Array();
            for(var i=0; i<objs.length; i++){

                var  element= objs[i];

                	if (element.elementType=="Node") {
                		maxX = element.x +element.width;   // 水平间距
                    	maxY = element.y + element.height;    // 垂直间距
                        // diff = Math.sqrt(diffx * diffx + diffy * diffy);
						// //勾股定理斜线距离(直线距离)
		                // console.log("diff="+diff+",diffx="+diffx+",diffy="+diffy);
		                // alert(diff); alert(node.width);
		                // console.log(element.x+"<="+ox+"<="+maxX);
		                // console.log(element.y+"<="+oy+"<="+maxY);
	                if( (element.x<=ox&&ox<=maxX)&&(element.y<=oy&&oy<=maxY)){    // 碰撞了
//	                	console.log(element.elementType);
	                    obj = element;
	                    elementArray.push(obj);
	                    // break;
	                }
                } 
                	
                	if (element.elementType=="RelateLine") {

                        //未曾旋转的坐标
                        OR=element.radius;

                        var x = element.x + (ox - element.x)*Math.cos(OR) + (oy - element.y) *Math.sin(OR);
                        
                        var y = element.y - (ox - element.x)*Math.sin(OR) + (oy - element.y) *Math.cos(OR);

                        if(Math.pow((x-element.x),2)/Math.pow((element.a+2),2)+Math.pow((y-element.y),2)/Math.pow((element.b+2),2)<1&&Math.pow((x-element.x),2)/Math.pow((element.a+2),2)+Math.pow((y-element.y),2)/Math.pow((element.b+2),2)>0.5){

                            obj = element;
                            elementArray.push(obj);
//                            console.log(element.y)
                        }
                    } 
                	 if(element.elementType=="LAN"){

                         if( (element.x1<=ox&&ox<=element.x2)&&(element.y1>=oy-2&&element.y1<=oy+2)){    // 碰撞了
                            obj = element;
                            elementArray.push(obj);
                            // break;
                        }else if((element.x1>=ox&&ox>=element.x2)&&(element.y1>=oy-2&&element.y1<=oy+2)){
                             obj = element;
                            elementArray.push(obj);
                        }
                     }
                	 if(element.elementType=="LanNode"){

                         if( (element.x1<=ox+4&&element.x1>=ox-4)&&(element.y1<=oy&&element.y2>=oy)){   // 碰撞了
                            obj = element;
                            elementArray.push(obj);
                            // break;
                        }else if((element.x1>=ox-4&&element.x1<=ox+4)&&(element.y1>=oy&&element.y2<=oy)){
                             obj = element;
                             elementArray.push(obj);
                        }
                     }
                	 
                	// 横向文本
                     if(element.elementType=="TextRank"){
                         // 计算节点文字的范围
                         maxX = element.x+element.textWidth ;   // 水平间距
                         maxY = element.y; // 垂直间距

                         if((element.x<=ox&&ox<=maxX)&&(oy>=element.y-element.size&&oy<=maxY)){    // 碰撞了
                            obj = element;
                            elementArray.push(obj);
                            // break;
                        }
                     }

                     // 竖向文本
                     if(element.elementType=="TextVertical"){
                         // 计算节点文字的范围
                         maxX = element.x+element.size ;   // 水平间距
                         maxY = element.y+element.textWidth; // 垂直间距

                         if((element.x<=ox&&ox<=maxX)&&(oy>=element.y-element.size&&oy<=maxY)){    // 碰撞了
                            obj = element;
                            elementArray.push(obj);
                            // break;
                        }
                     }
                	 
                	if(element.elementType=="TextNode"){
                		// alert(element.textWidth+"*****"+12);
                		maxX = element.x +element.textWidth;   // 水平间距
                    	maxY = element.y+15; // 垂直间距
                  // diff = Math.sqrt(diffx * diffx + diffy * diffy);
					// //勾股定理斜线距离(直线距离)
		                // console.log("diff="+diff+",diffx="+diffx+",diffy="+diffy,"textWidth="+element.textWidth);
		                // alert(diff); alert(node.width);
		                 
	                if((element.x<=ox&&ox<=maxX)&&(oy>=element.y-10&&oy<=maxY)){    // 碰撞了
	                    obj = element;
	                     elementArray.push(obj);
	                     textFlag=true;
	                    // break;
	                }
                	
                }
                    
            }

            if(elementArray.length==1){
            	return elementArray[0];
            }else if(elementArray.length>1){
            	if(textFlag==true){
            		for(var i=0;i<elementArray.length;i++){
            		if(elementArray[i].elementType=="TextNode"){
            			return elementArray[i];
            		}
           		}
             }else{
             	return elementArray[0];
             }
            	
            }
           
           // return obj;
		}

	        this.getElementByMouseEventLAN=function(e){
	         var locXY=windowTocanvas(graph.canvas,e.pageX,e.pageY);
	   		 var ox=locXY.x;
	   	     var oy=locXY.y;
	         var objs=this.childs;
	         var objL = null;
	         var maxX=0;
	         var maxY=0;
	         var textFlag=false; // 判断elementArray 是否包含有TextNode
	         var elementArray=new Array();
	            for(var i=0; i<objs.length; i++){

	                var  element= objs[i];


	                    if(element.elementType=="LAN"){

	                        if( (element.x1<=ox&&ox<=element.x2)&&(element.y1>=oy-10&&element.y1<=oy+10)){    // 碰撞了
	                           objL = element;
	                           elementArray.push(objL);
	                           // break;
	                       }else if((element.x1>=ox&&ox>=element.x2)&&(element.y1>=oy-10&&element.y1<=oy+10)){
	                            objL = element;
	                           elementArray.push(objL);
	                       }
	                    }
	            }

	           return objL;
	        }
		return graph;
	}

	function Node(name,x,y){
		this.name=name;
		this.x=x;
		this.y=y;

		this.setLocation=function(x,y){
			this.x=x;
			this.y=y;
		}
		this.image="router160x120.svg";
		
		this.scaleX=1;
		this.scaleY=1;
		this.elementType = "Node";
		this.font = "12px Consolas";
        this.fontColor = "255,255,255";
        this.dragable=true;// 默认可以拖动

        this.width=100;
        this.height=100;
        // this.blur=0.8;
        // this.shadowColor='rgba(222,122,222,'+this.blur+')';
        this.shadowColor='rgba(255,10,10,.7)';
        this.shadowOffsetX=0;
        this.shadowOffsetY=0;
        this.shadowBlur=15;

        this.diffx=0;// 框选模式拖动，计算个鼠标位置距离差
        this.diffy=0; 

        this.ntdiffx=0;// 框选模式拖动，计算节点及其文本节点距离差
        this.ntdiffy=0;

        this.fillStyle="rgba(0, 0, 0, 0.8)";
        this.setSize=function(width,height){
        	this.width=width;
			this.height=height;
        };

        this.paint=function(){
        	var img=new Image();
        	img.src=this.image;
			graph.ctx.drawImage(img, this.x, this.y,this.width,this.height);
        }


        this.updateNode = function(node){
			if( typeof node == "object"){
				if( typeof node.id != "undefined" ){
					for (var i = 0; i < graph.childs.length; i++) {
						var obj = graph.childs[i];
							if(obj.elementType=="Node"){
								if( obj.id == node.id ){
								graph.extendCopy(obj,node);		// 属性copy
								// n.originalColor = n.color;
								break;
							}
						}						
						
					}
					
				}
			}
		}

		this.drawNodeLabel=function(){
			var textWidth = graph.ctx.measureText(this.name).width; // 文字宽

			graph.ctx.fillStyle=this.fontColor;
			graph.ctx.font=this.font;
			// graph.ctx.fillText(this.name,this.x-(textWidth/2),this.y+this.size/2+this.textSize);
			graph.ctx.fillText(this.name,this.x,this.y);
		}

		
	}

	function TextNode(name,x,y){
		this.name=name;
        this.textWidth=graph.ctx.measureText(this.name).width; 
		this.elementType="TextNode";
		this.font="px 宋体";
		this.size=14;
        this.fontColor="red";
        this.textSize=14;
        this.isDrawEdgeLabel=false;
        this.radius="";
        this.angle=""
        this.x=x-this.textWidth/2;
        this.y=y;
        this.x1=x;
        this.y1=y;
        this.currentX=0;
        this.currentY=0;
        this.diffx=0;
        this.diffy=0;
		this.setLocation=function(x,y){
			this.x=x;
			this.y=y;
			
			
		}
		
		this.paint=function(){
			// var textWidth = graph.ctx.measureText(this.name).width; //文字宽

			graph.ctx.fillStyle=this.fontColor;
			

                if(this.isDrawEdgeLabel){                	
                    graph.ctx.save();
                    graph.ctx.translate(this.x1,this.y1); 
//                    graph.ctx.rotate(this.radius ); // 旋转
                    graph.ctx.font=this.size+this.font;
                    graph.ctx.fillText(this.name,0,0);
                    graph.ctx.restore();
                }else{                	
                graph.ctx.font=this.size+this.font;
                graph.ctx.fillText(this.name,this.x,this.y);// -this.textWidth/2
                this.textWidth=graph.ctx.measureText(this.name).width; 
            }
		}


		this.updateNode = function(node){
			if( typeof node == "object"){
				if( typeof node.textId != "undefined" ){
					for (var i = 0; i < graph.childs.length; i++) {
						var obj = graph.childs[i];
							if(obj.elementType=="TextNode"){
								if( obj.textId == node.textId ){
								graph.extendCopy(obj,node);		// 属性copy
								break;
							}
						}						
						
					}
					
				}
			}
		}

	}
	
	// 横向文本
    function TextRank(name,x,y){
        this.name=name;
        this.textWidth=graph.ctx.measureText(this.name).width; 
        this.elementType="TextRank";
        this.font="px 宋体";
        this.size=14;
        this.fontColor="red";
        this.textSize=14;
        this.x=x;
        this.y=y;
        this.currentX=0;
        this.currentY=0;
        this.diffx=0;
        this.diffy=0;
        this.paint=function(){
                graph.ctx.fillStyle=this.fontColor;
                graph.ctx.font=this.size+this.font;
                graph.ctx.fillText(this.name,this.x,this.y);
                this.textWidth=graph.ctx.measureText(this.name).width; 
        }

        this.updatetextRank = function(textRank){
            if( typeof textRank == "object"){
                if( typeof textRank.textId != "undefined" ){
                    for (var i = 0; i < graph.childs.length; i++) {
                        var obj = graph.childs[i];
                            if(obj.elementType=="TextRank"){
                                if( obj.textId == textRank.textId ){
                                graph.extendCopy(obj,textRank);     // 属性copy
                                break;
                            }
                        }                       
                        
                    }
                    
                }
            }
        }
    }

    // 竖向文本
    function TextVertical(name,x,y){
        this.name=name;
        this.textWidth=graph.ctx.measureText(this.name).width; 
        this.elementType="TextVertical";
        this.font="px 宋体";
        this.size=14;
        this.fontColor="red";
        this.wordSpace=2;
        // this.textSize=14;
        this.x=x;
        this.y=y;
        this.currentX=0;
        this.currentY=0;
        this.diffx=0;
        this.diffy=0;
        this.paint=function(){
                var wordL=this.name.length;
                graph.ctx.fillStyle=this.fontColor;
                graph.ctx.font=this.size+this.font;
                for(i=0; i<this.name.length; i++){
                    graph.ctx.fillText(this.name[i],this.x,this.y+this.size*i+this.wordSpace*i);
                }
                this.textWidth=graph.ctx.measureText(this.name).width+this.wordSpace*wordL;

        }

        this.updatetextVertical = function(textVertical){
            if( typeof textVertical == "object"){
                if( typeof textVertical.textId != "undefined" ){
                    for (var i = 0; i < graph.childs.length; i++) {
                        var obj = graph.childs[i];
                            if(obj.elementType=="TextVertical"){
                                if( obj.textId == textVertical.textId ){
                                graph.extendCopy(obj,textVertical);     // 属性copy
                                break;
                            }
                        }                       
                        
                    }
                    
                }
            }
        }
    }

	function Edge(name,from,to,x1,y1,x2,y2){
		
		this.name=name;
        this.elementType="Edge";
        this.nodeAId=from.id;
        this.nodeBId=to.id;
        this.color="black";
        this.stylecolor="lightblue";
        this.size=1;
        this.dashL=0;
        this.dashO=0;
        this.textSize=14;
        this.index=0;//如果两点之间多条线，判断线条弧度方向
        this.radian=15;
        this.radius="";
        this.angle="";
        this.isDrawEdgeLabel=false;
        this.isRelate=false;
        this.isArrow=false;
        this.isBroken=false;
        this.isStart=false;
        this.shadowColor='rgba(255,10,10,.7)';
        this.shadowOffsetX=5;
        this.shadowOffsetY=5;
        this.shadowBlur=5;
        this.isShadow=false;
        this.ntdiffx=0;
        this.ntdiffy=0;
        this.drawEdgeLabel=function(from,to){
	        var local=graph.getEdgeLableLocaltion(from,to);
	        this.angle=local.angle;
	        this.labelX=local.labelX;
	        this.labelY=local.labelY;
	        this.radius=local.radius;
	        this.angle=local.angle;
	        this.slope=local.slope;	       	       
       }
        
      //箭头
        this.drawArrow=function(from,to){
            // var edges=graph.getEdgesByTwoNodes(from,to);
            //计算箭头位置
            var dx = to.x - from.x,
                dy = to.y - from.y,
                //radian为一个角度的弧度值,dy/dx为直线的斜率
                radian = Math.atan(dy/dx);
                //angle  = radian *180 / Math.PI;
                 angle  = radian*Math.PI/180;
                coord = graph.computeArrowTranslate(dx,dy,radian,to);
                angleRadian = graph.computeArrowRotate(dx,dy,angle);
                
            //将画布保存到栈中
            graph.ctx.save();  
            graph.ctx.setTransform(1,0,0,1,0,0);  //恢复初始值
            if(angle==90){
                graph.ctx.translate(coord.x,coord.y-13); //平移
            }else{
                graph.ctx.translate(coord.x,coord.y); //平移
            }
            //graph.ctx.translate(coord.x,coord.y-15); //平移
            graph.ctx.rotate(angleRadian);      //旋转
            graph.ctx.beginPath();
            //graph.ctx.fillStyle=to.color;
            graph.ctx.fillStyle=this.color;
            //定位到箭头中心点
            graph.ctx.moveTo(0,-5);
            // alert(coord.y)
            graph.ctx.lineTo(5,5);
            graph.ctx.lineTo(-5,5);
            graph.ctx.lineTo(0,-5);
            graph.ctx.fill();
            graph.ctx.closePath();
            graph.ctx.restore();
        }

		// 通过一条edge获取两节点之前的其他edges
		this.relationEdgesByEdge=function(edge){

			var edges=new Array();
			var nodeAId=edge.nodeAId;
			var nodeBId=edge.nodeBId;


		}

		
	}
	//折线
    function EdgeBroken(name,from,to){
        this.name=name;
        this.elementType="EdgeBroken";
        this.nodeAId=from.id;
        this.nodeBId=to.id;
        this.color="black";
        this.stylecolor="lightblue";
        this.size=1;
        this.subType="4";
        this.isDrawEdgeLabel=false;
        this.isRelate=false;
        this.isBroken=false;
        this.isArrow=false;
        this.dashL=0;
        this.dashO=0;
        this.textSize=14;
        this.brokenDiffx1=0;
        this.brokenDiffy1=0;
        this.brokenDiffx2=0;
        this.brokenDiffy2=0;
        this.brokenDiffx3=0;
        this.brokenDiffy3=0;
        this.brokenDiffx4=0;
        this.brokenDiffy4=0;
        var nodeS=from.x+from.width/2;
        var nodeD=from.y+from.height/2;
        var nodeE=to.x+to.width/2;
        var nodeF=to.y+to.height/2;
        this.x1=nodeS;
        this.x2=nodeE;
        this.y1=nodeD;
        this.y2=nodeF;
        if(this.x1<this.x2){
            this.x3=(nodeE-nodeS)/2+nodeS;             
            this.y3=this.y1;  
            this.x4=(nodeE-nodeS)/2+nodeS;
            this.y4=this.y2;         
        }else{
            this.x3=nodeS-(nodeS-nodeE)/2;
            this.y3=this.y1;
            this.x4=nodeS-(nodeS-nodeE)/2;
            this.y4=this.y2; 
        }
        this.drawEdgeLabel=function(from,to){
            var local=graph.getEdgeLableLocaltion(from,to);
             this.labelX=local.labelX;
             this.labelY=local.labelY;
             this.radius=local.radius;
             this.angle=local.angleR;  
             this.slope=local.slope;        
        }
        //画线
        this.paintEdge=function(obj){
            graph.ctx.setLineDash([this.dashL,this.dashO]);
            graph.ctx.strokeStyle=this.color;            
            graph.ctx.beginPath();
            graph.ctx.lineWidth=this.size;
            graph.ctx.moveTo(this.x1,this.y1);            
            graph.ctx.lineTo(this.x3,this.y3);
            graph.ctx.lineTo(this.x4,this.y4);
            graph.ctx.lineTo(this.x2,this.y2);
            var o=new Bound(this.x1,this.y1,this.x2,this.y2,this.x3,this.y3,this.x4,this.y4);
            graph.edgeMap.put(o,this);
            graph.ctx.stroke();
        }
        //拖动折线时话移动点
        this.EdgeBroken=function(from,to){
            var local=graph.getEdgeLableLocaltion(from,to);
            //画线平移中心点
            var nodeS=from.x+from.width/2;
            var nodeD=from.y+from.height/2;
            var nodeE=to.x+to.width/2;
            var nodeF=to.y+to.height/2;
            var x1=(nodeE-nodeS)/2+nodeS; 
            var x2=nodeS-(nodeE-nodeS)/2;
            var y1=nodeD;
            var y2=nodeF;
            if(this.isBroken){
            	var centerX=this.x3;
                var centerY=(this.y4-this.y3)/2+this.y3; 
                if(this.x3>this.x1){
                    var centerWX=(this.x3-from.x-from.width)/2+from.x+from.width;
                }else{
                    var centerWX=(from.x-this.x3)/2+this.x3;
                }
                var centerWY=this.y3;
                if(this.x2>this.x4){
                    var centerHX=(to.x-this.x4)/2+this.x4;
                }else{
                    var centerHX=(this.x4-to.x-to.width)/2+to.x+to.width;                    
                }
                if(this.x2==this.x4){
                    var centerWX=this.x1;
                    var centerHX=this.x2;
                }
                var centerHY=this.y2;                              
                graph.ctx.strokeStyle="blue";
                graph.ctx.fillStyle="yellow";
                graph.ctx.lineWidth=1;
                graph.ctx.save();
                //竖折线移动点
                graph.ctx.beginPath();
                graph.ctx.arc(centerX,centerY,4,0,2*Math.PI);
                graph.ctx.fill();
                graph.ctx.stroke();
                graph.ctx.restore();
                //横折线1移动点
                graph.ctx.beginPath();
                graph.ctx.arc(centerWX,centerWY,4,0,2*Math.PI);
                graph.ctx.fill();
                graph.ctx.stroke();
                graph.ctx.restore();
                //横折线2移动点
                graph.ctx.beginPath();
                graph.ctx.arc(centerHX,centerHY,4,0,2*Math.PI);
                graph.ctx.fill();
                graph.ctx.stroke();
                graph.ctx.restore();
            }
        }
        this.drawArrow=function(from,to){
                        
            //计算箭头位置
            var dx = to.x - from.x,
                dy = to.y - from.y,
                angleR=graph.computeArrowRotate(dx,dy,0);
                graph.ctx.save();  
                graph.ctx.setTransform(1,0,0,1,0,0);  //恢复初始值            
              //根据圆与线相交方程式计算y  (x-a)²+(y-b)²=R²  x=this.x4;a,b为圆的中心坐标 
                var y=Math.sqrt(Math.pow(to.height/2,2)-Math.pow(this.x4-this.x2,2))+this.y2 ;
                var y1=-Math.sqrt(Math.pow(to.height/2,2)-Math.pow(this.x4-this.x2,2))+this.y2 ;
                if(Math.abs(this.x2-this.x4)<to.width/2&&this.y1>this.y2){
                    graph.ctx.translate(this.x4,y+5); //平移                    
                }else if(Math.abs(this.x2-this.x4)<to.width/2&&this.y1<this.y2){
                    graph.ctx.translate(this.x4,y1-5); //平移
                    graph.ctx.rotate(180*Math.PI/180);
                }else if(this.x2>this.x4&&this.x1<this.x2){
                    graph.ctx.translate(this.x2-to.width/2-5,this.y2); //平移
                    graph.ctx.rotate(angleR);      //旋转
                }else if(this.x2<this.x4&&this.x1>this.x2){
                    graph.ctx.translate(this.x2+to.width/2+5,this.y2); //平移
                    graph.ctx.rotate(angleR);      //旋转
                }else if(this.x1<=this.x2&&this.x2<this.x4){
                    graph.ctx.translate(this.x2+to.width/2+5,this.y2); //平移
                    graph.ctx.rotate(270*Math.PI/180);
                }else if(this.x1>=this.x2&&this.x2>this.x4){
                    graph.ctx.translate(this.x2-to.width/2-5,this.y2); //平移
                    graph.ctx.rotate(90*Math.PI/180); 
                }     

                       
            graph.ctx.beginPath();
            
            graph.ctx.strokeStyle=this.color;
            graph.ctx.fillStyle=this.color;
            //定位到箭头中心点
            graph.ctx.moveTo(0,-5);
            graph.ctx.lineTo(5,5);
            graph.ctx.lineTo(-5,5);
            graph.ctx.lineTo(0,-5);
            graph.ctx.stroke();
            graph.ctx.fill();
            graph.ctx.closePath();
            graph.ctx.restore();
        }
        this.updateBrokenEdge = function(Edge){
            if( typeof edge == "object"){
                    for (var i = 0; i < graph.childs.length; i++) {
                        var obj = graph.childs[i];
                            if(obj.elementType=="EdgeBroken"){
                                if( obj.id == edge.id ){                                 
                                    graph.extendCopy(obj,edge);     //属性copy
                                    break;
                            }
                        }                       
                        
                    }
            }
        }      
    }
    //折线空箭头
    function EdgeBrokenArrow(name,from,to,subType){
        //继承父类属性和方法
        EdgeBroken.call(this,name,from,to);
        this.subType="0";
        this.isArrow=true;
        this.drawArrow=function(from,to){
            
            //计算箭头位置
            var dx = to.x - from.x,
                dy = to.y - from.y,
                angleR=graph.computeArrowRotate(dx,dy,0);
                graph.ctx.save();  
                graph.ctx.setTransform(1,0,0,1,0,0);  //恢复初始值                           
                var y=Math.sqrt(Math.pow(to.height/2,2)-Math.pow(this.x4-this.x2,2))+this.y2 ;
                var y1=-Math.sqrt(Math.pow(to.height/2,2)-Math.pow(this.x4-this.x2,2))+this.y2 ;
                if(Math.abs(this.x2-this.x4)<to.width/2&&this.y1>this.y2){
                    graph.ctx.translate(this.x4,y+5); //平移                    
                }else if(Math.abs(this.x2-this.x4)<to.width/2&&this.y1<this.y2){
                    graph.ctx.translate(this.x4,y1-5); //平移
                    graph.ctx.rotate(180*Math.PI/180);
                }else if(this.x2>this.x4&&this.x1<this.x2){
                    graph.ctx.translate(this.x2-to.width/2-5,this.y2); //平移
                    graph.ctx.rotate(angleR);      //旋转
                }else if(this.x2<this.x4&&this.x1>this.x2){
                    graph.ctx.translate(this.x2+to.width/2+5,this.y2); //平移
                    graph.ctx.rotate(angleR);      //旋转
                }else if(this.x1<=this.x2&&this.x2<this.x4){
                    graph.ctx.translate(this.x2+to.width/2+5,this.y2); //平移
                    graph.ctx.rotate(270*Math.PI/180);
                }else if(this.x1>=this.x2&&this.x2>this.x4){
                    graph.ctx.translate(this.x2-to.width/2-5,this.y2); //平移
                    graph.ctx.rotate(90*Math.PI/180); 
                }       
                       
            graph.ctx.beginPath();
            
            graph.ctx.strokeStyle=this.color;
            graph.ctx.fillStyle="white";
            //定位到箭头中心点
            graph.ctx.moveTo(0,-5);
            graph.ctx.lineTo(5,5);
            graph.ctx.lineTo(-5,5);
            graph.ctx.lineTo(0,-5);
            graph.ctx.stroke();
            graph.ctx.fill();
            graph.ctx.closePath();
            graph.ctx.restore();
        }
    }
  //折线实箭头
    function EdgeBrokenSolidArrow(name,from,to,subType){
        //继承父类属性和方法
        EdgeBroken.call(this,name,from,to);
        this.subType=5;
        this.isArrow=true;        
    }
    //实线实箭头
	function EdgeSolidArrow(name,from,to,subType){
        //继承父类属性和方法
		Edge.call(this,name,from,to);
        this.subType="1";
        this.isArrow=true;
	}
	//虚折线实箭头
    function DashBrokenSolidArrow(name,from,to,subType){
        //继承父类属性和方法
        EdgeBroken.call(this,name,from,to);
        this.subType=6;
        this.dashL=5;
        this.dashO=3;
        this.isArrow=true;        
    }
    //实线空箭头
    function EdgeEmptyArrow(name,from,to,subType){
        Edge.call(this,name,from,to);        
        this.subType="2";
        this.isArrow=true;
        //覆盖父类的画箭头方法
        this.drawArrow=function(from,to){
                        
            //计算箭头位置
            var dx = to.x - from.x,
                dy = to.y - from.y,
                //radian为一个角度的弧度值,dy/dx为直线的斜率
                radian = Math.atan(dy/dx);
                //angle  = radian *180 / Math.PI;
                 angle  = radian*Math.PI/180;
                coord = graph.computeArrowTranslate(dx,dy,radian,to);
                angleRadian = graph.computeArrowRotate(dx,dy,angle);
                angleR=graph.computeArrowRotate(dx,dy,0);
            graph.ctx.save();  
            graph.ctx.setTransform(1,0,0,1,0,0);  //恢复初始值
            //判断是否为折线箭头平移
            if(this.isBroken==false){
                if(angle==90){
                    graph.ctx.translate(coord.x,coord.y-13); //平移
                }else{
                    graph.ctx.translate(coord.x,coord.y); //平移
                }
                graph.ctx.rotate(angleRadian);      //旋转
            }else{
                if(angle==90){
                    graph.ctx.translate(coord.x,coord.y-13); //平移
                }else{
                    if(dx>0){
                        graph.ctx.translate(to.x,to.y+to.width/2); //平移
                    }else{
                        graph.ctx.translate(to.x+to.width,to.y+to.width/2); //平移
                    }
                 }

                graph.ctx.rotate(angleR);      //旋转

            }
            graph.ctx.beginPath();
            
            graph.ctx.strokeStyle=this.color;
            graph.ctx.fillStyle="white";
            //定位到箭头中心点
            graph.ctx.moveTo(0,-5);
            graph.ctx.lineTo(5,5);
            graph.ctx.lineTo(-5,5);
            graph.ctx.lineTo(0,-5);
            graph.ctx.stroke();
            graph.ctx.fill();
            graph.ctx.closePath();
            graph.ctx.restore();
        }
    }
    //虚线实箭头
    function DashEdgeSolidArrow(name,from,to,subType){
        Edge.call(this,name,from,to);
        this.subType="3";
        this.isArrow=true;
        this.dashL=5;
        this.dashO=3;
    }
    //虚线空箭头
    function DashEdgeEmptyArrow(name,from,to,subType){
        Edge.call(this,name,from,to);
        this.subType="4";
        this.isArrow=true;
        this.dashL=5;
        this.dashO=3;
        this.drawArrow=function(from,to){            
            //计算箭头位置
            var dx = to.x - from.x,
                dy = to.y - from.y,
                //radian为一个角度的弧度值,dy/dx为直线的斜率
                radian = Math.atan(dy/dx);
                //angle  = radian *180 / Math.PI;
                 angle  = radian*Math.PI/180;
                coord = graph.computeArrowTranslate(dx,dy,radian,to);
                angleRadian = graph.computeArrowRotate(dx,dy,angle);
            //将画布保存到栈中
            graph.ctx.save();  
            graph.ctx.setTransform(1,0,0,1,0,0);  //恢复初始值
            if(angle==90){
                graph.ctx.translate(coord.x,coord.y-13); //平移
            }else{
                graph.ctx.translate(coord.x,coord.y); //平移
            }
            //graph.ctx.translate(coord.x,coord.y-15); //平移
            graph.ctx.rotate(angleRadian);      //旋转
            graph.ctx.beginPath();
            graph.ctx.fillStyle="white";
            graph.ctx.strokeStyle=this.color;
            //定位到箭头中心点
            graph.ctx.moveTo(0,-5);
            // alert(coord.y)
            graph.ctx.lineTo(5,5);
            graph.ctx.lineTo(-5,5);
            graph.ctx.lineTo(0,-5);
            graph.ctx.stroke();
            graph.ctx.fill();
            graph.ctx.closePath();
            graph.ctx.restore();
        }
    }

	// 思路：获取没重复的最右一值放入新数组 
    function unique(array){ 
    var r = []; 
    for(var i = 0, l = array.length; i < l; i++) { 
     for(var j = i + 1; j < l; j++) 
      if (array[i] === array[j]) j = ++i; 
     r.push(array[i]); 
     } 
     return r; 
    }
	//数组中对象按属性值排序
    function compare(property){
        return function(a,b){
            var value1 = a[property];
            var value2 = b[property];
            return value1 - value2;
        }
    }

          //画线关联
     function RelateLine(leftName,rightName,relatEdgeArc){
        this.elementType="RelateLine";
        this.size=2;
//        this.name=name;
        this.leftName=leftName;
        this.rightName=rightName;
        this.relateArray=relatEdgeArc;
        this.x;
        this.y;
        this.radius=relatEdgeArc[0].radius;
        this.a;
        this.b;
        //画线关联
        this.paint=function(){
            relatEdgeArc.sort(compare('y1'));
            var num=relatEdgeArc.length-1;
                if(relatEdgeArc[0].radius>1&&relatEdgeArc[num].y2==relatEdgeArc[0].y2){
                    var relateY=(relatEdgeArc[num].y2-relatEdgeArc[num].y1)/2+relatEdgeArc[num].y1;
                    var relateX=(relatEdgeArc[num].x1)/2+(relatEdgeArc[0].x2)/2;
                    var b=(relatEdgeArc[num].x2-relatEdgeArc[0].x2)+relatEdgeArc[num].dist;
                    var a=b/2;
                 }else{
                    var relateX=(relatEdgeArc[num].x2-relatEdgeArc[num].x1)/2+relatEdgeArc[num].x1;
                    var relateY=(relatEdgeArc[num].y1)/2+(relatEdgeArc[0].y2)/2;
                    var b=(relatEdgeArc[num].y2-relatEdgeArc[0].y2)+relatEdgeArc[num].dist;
                    var a=b/2;
                }
                this.x=relateX;
                this.y=relateY;
                this.a=a;
                this.b=b;
                var r = (a > b) ? a : b; 
                var ratioX = a / r; //横轴缩放比率 
                var ratioY = b / r; //纵轴缩放比率    
                
                    graph.ctx.strokeStyle="#93DB70";
                    graph.ctx.lineWidth=this.size;
                    graph.ctx.save();
                    graph.ctx.beginPath();
                    graph.ctx.translate(relateX,relateY);
                    graph.ctx.rotate(relatEdgeArc[num].radius);
                    graph.ctx.translate(-relateX,-relateY);
                    graph.ctx.scale(ratioX, ratioY); //进行缩放（均匀压缩）        
                    //从椭圆的左端点开始逆时针绘制 
                    graph.ctx.moveTo((relateX + a) / ratioX , relateY / ratioY); 
                    graph.ctx.arc(relateX / ratioX, relateY / ratioY, r, 0, 2 * Math.PI); 
                    graph.ctx.translate(relateX,relateY); 
                           
                    graph.ctx.stroke();
                    graph.ctx.restore();
        
        }
        this.updateRelateLine = function(relateLine){
            if( typeof relateLine == "object"){
                if( typeof relateLine.id != "undefined" ){
                    for (var i = 0; i < graph.childs.length; i++) {
                        var obj = graph.childs[i];
                            if(obj.elementType=="RelateLine"){
                                if( obj.id == relateLine.id ){
                                graph.extendCopy(obj,relateLine);     //属性copy
                                break;
                            }
                        }                       
                        
                    }
                    
                }
            }
        }
     }
	
	// 特殊连线
    function LAN(name,x1,y1,x2,y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.name=name;
        this.elementType="LAN";
        this.color="black";
        this.size=2;
        this.isDragSize=false;
       // this.x1=x1;

          this.paint=function(){

        	  graph.ctx.lineWidth=this.size;
              graph.ctx.strokeStyle=this.color;
              graph.ctx.fillStyle='white';
               if(this.isDragSize){
                  graph.ctx.beginPath();
                  graph.ctx.arc(this.x1+3, this.y1, 3, 0, Math.PI*2, true);
                  graph.ctx.globalCompositeOperation="destination-over";
                  graph.ctx.stroke();
                  graph.ctx.fill();
                  graph.ctx.save();
                  graph.ctx.beginPath();
                  graph.ctx.arc(this.x2-3, this.y2, 3, 0, Math.PI*2, true);
                  graph.ctx.stroke();
                  graph.ctx.fill();
                  graph.ctx.save();
              }
              graph.ctx.beginPath();
              graph.ctx.moveTo(this.x1,this.y1);
              graph.ctx.lineTo(this.x2,this.y2);
              graph.ctx.stroke();
              graph.ctx.save();

                    
          }

    this.updateLan = function(lan){
            if( typeof lan == "object"){
                    for (var i = 0; i < graph.childs.length; i++) {
                        var obj = graph.childs[i];
                            if(obj.elementType=="LAN"){
                                if( obj.id == lan.id ){
                                    // alert();
                                graph.extendCopy(obj,lan);     // 属性copy
                                break;
                            }
                        }                       
                        
                    }
            }
        }      
    }

  // 节点特殊连线
    function LanNode(name,node,lan){
    	// this.x1=graph.nodeLanX;
        // this.y1=graph.nodeLanY;
    	this.x1=node.x+node.width/2;
    	this.y1=node.y+node.height/2;
        this.x2=lan.x2;
        this.y2=lan.y2;
        this.nodeId=node.id;
        this.lanId=lan.id;
        this.node=node;
        this.lan=lan;
        
        this.name=name;
        this.elementType="LanNode";
        this.color="black";
        this.size=2;

          this.paint=function(){
        	  		
                    graph.ctx.lineWidth=this.size;
                    graph.ctx.strokeStyle=this.color;
                    graph.ctx.fillStyle="white";
                    
                    graph.ctx.beginPath();
                    graph.ctx.globalCompositeOperation="source-over";
                    graph.ctx.arc(this.x1, this.lan.y2, 3, 0, Math.PI*2, true);
                    graph.ctx.stroke();
                    graph.ctx.fill();
                    graph.ctx.save();
                    graph.ctx.beginPath();
                    graph.ctx.globalCompositeOperation="destination-over";
                    graph.ctx.moveTo(this.node.x+this.node.width/2,this.node.y+this.node.height/2);
                    graph.ctx.lineTo(this.node.x+this.node.width/2,this.lan.y2);
                    graph.ctx.stroke();
                    // console.log("this.y2"+this.y1+"--"+this.y2)
          }

    this.updateLanNode = function(lanNode){
            if( typeof lanNode == "object"){ 
               // if( typeof node.id != "undefined" ){
                    for (var i = 0; i < graph.childs.length; i++) {
                        var obj = graph.childs[i];
                            if(obj.elementType=="LanNode"){
                                if( obj.id == lanNode.id ){
                                    // alert();
                                graph.extendCopy(obj,lanNode);     // 属性copy
                                break;
                            }
                        }                       
                        
                    }
               // }
            }
        }   
       
    }
	// Node.prototype.
	// 通过节点的nodeId 获取图元node
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

function getRelateLineById(id){
	var targetNode;
	 for(var i=0;i<graph.childs.length;i++){
	 	var obj=graph.childs[i];
	 
	 	if(obj.elementType=="RelateLine"&&id==obj.id){
	 	
	 		targetNode=obj;
	 		break;
	 	}
	 }
  
  return targetNode;
}

// 通过节点的lanId 获取图元lan
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
function changeGraphModel(model){
	
	graph.graphModel=model;
		
}

// 通过nodeId找文本节点
function getTextNodeByNode(nodeId){
	var targetNode;
	 for(var i=0;i<graph.childs.length;i++){
	 	var obj=graph.childs[i];
	 	if(obj.elementType=="TextNode"&&nodeId==obj.textId){
	 		targetNode=obj;
	 		break;
	 	}
	 }
	 return targetNode;
}

// 获取Lan文本
function getTextNodeByLan(lanId){
    var targetNode;
     for(var i=0;i<graph.childs.length;i++){
        var obj=graph.childs[i];
        if(obj.elementType=="TextNode"&&lanId==obj.textId){
            targetNode=obj;
            break;
        }
     }
     return targetNode;
}

function getTextNodeByLanNode(lanNodeId){
    var targetNode;
     for(var i=0;i<graph.childs.length;i++){
        var obj=graph.childs[i];
        if(obj.elementType=="TextNode"&&lanNodeId==obj.textId){
            targetNode=obj;
            break;
        }
     }
     return targetNode;
}

// 通过文本节点Id找节点
function getNodeByTextNodeId(id){
    var targetNode;
     for(var i=0;i<graph.childs.length;i++){
        var obj=graph.childs[i];
        if(obj.elementType=="Node"&&id==obj.id){
            targetNode=obj;
            break;
        }
     }
     return targetNode;
}


// 通过文本节点找LanNode
function getLanNodeByTextNodeId(id){
	 var targetNode;
     for(var i=0;i<graph.childs.length;i++){
        var obj=graph.childs[i];
        if(obj.elementType=="LanNode"&&id==obj.id){
            targetNode=obj;
            break;
        }
     }
     return targetNode;
}
// 通过文本节点找LAN
function getLanByTextNodeId(id){
	 var targetNode;
     for(var i=0;i<graph.childs.length;i++){
        var obj=graph.childs[i];
        if(obj.elementType=="LAN"&&id==obj.id){
            targetNode=obj;
            break;
        }
     }
     return targetNode;
}

 
// 通过nodeId获取lanNode
function getLanNodesByNodeId(id){
	
	 var targetNode=new Array();
	 for(var i=0;i<graph.childs.length;i++){
	        var obj=graph.childs[i];
	        if(obj.elementType=="LanNode"&&id==obj.nodeId){
	            targetNode.push(obj);
	        }
	     }
	     return targetNode;
}

// 通过lanId获取LanNodes
function getLanNodesByLanId(lanId){
	var lanNodes=new Array();
	 for(var i=0;i<graph.childs.length;i++){
	        var obj=graph.childs[i];
	        if(obj.elementType=="LanNode"&&lanId==obj.lanId){
	            lanNodes.push(obj);
	        }
	     }
	 return lanNodes;
}

 // 上下左右对齐
 // 节点对齐
function changeAlign(val){
	var nodeArray=new Array();
	var nodes=graph.selectedData;// 获取选中图元集合对象
	for(var i=0;i<nodes.length;i++){
	  if(nodes[i].elementType=="Node"){
	    nodeArray.push(nodes[i]);
	  }
	}
	
	var firstNode=nodeArray[0];
	 if(val=="1"){// 左对齐
	 	 var xmin=getMin(nodeArray,'x');
	   	 for(var i=0;i<nodes.length;i++){
 				  if(nodes[i].elementType=="Node"){
 				        textNodeFollowByTopoNode(xmin.x,nodes[i].y,nodes[i]);
						nodes[i].setLocation(xmin.x,nodes[i].y);
						
 				  }
	  	 }
	 }else if(val=="2"){// 右对齐
	  	 var xmax=getMax(nodeArray,'x');
	   	 for(var i=0;i<nodes.length;i++){
	   	          if(nodes[i].elementType=="Node"){
					  textNodeFollowByTopoNode(xmax.x,nodes[i].y,nodes[i]);
 					 nodes[i].setLocation(xmax.x,nodes[i].y);
 					
	   	          }
		   	     
	  	 }
	 }else if(val=="3"){ // 上对齐
	  	 var ymin=getMin(nodeArray,'y');
	   	 for(var i=0;i<nodes.length;i++){
		   	      if(nodes[i].elementType=="Node"){
                    textNodeFollowByTopoNode(nodes[i].x,ymin.y,nodes[i]);
					nodes[i].setLocation( nodes[i].x,ymin.y);
 		   	        
		   	      }
	  	 }
	 }else if(val=="4"){// 下对齐
	  	 var ymax=getMax(nodeArray,'y');
	   	 for(var i=0;i<nodes.length;i++){
	   	       if(nodes[i].elementType=="Node"){
				 textNodeFollowByTopoNode(nodes[i].x,ymax.y,nodes[i]);
	   	         nodes[i].setLocation(nodes[i].x,ymax.y);
	   	         
	   	       }
		   	      
	  	 }
	 }else if(val=="5"){  // 上下居中
		for(var i=1;i<nodes.length;i++){
			if(nodes[i].elementType=="Node"){
			    textNodeFollowByTopoNode(nodes[i].x,firstNode.y,nodes[i]);
			   nodes[i].setLocation(nodes[i].x,firstNode.y);
			  
			}
	 	   
		}		
	 }else{     // 左右居中
		for(var i=1;i<nodes.length;i++){
	 	  
	 	    if(nodes[i].elementType=="Node"){
	 	    	 textNodeFollowByTopoNode(firstNode.x,nodes[i].y,nodes[i]);
	 	         nodes[i].setLocation(firstNode.x,nodes[i].y);
			}
		}		
	 }

	 graph.drawData();
}

// 取节点最小值
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

// 取节点最大值
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

function textNodeFollowByTopoNode(x,y,tagnode){
    
    var tagX=tagnode.x;
    var tagY=tagnode.y;
    var diffX=x-tagX;
    var diffY=y-tagY;
    var nodes=graph.selectedData;// 获取选中图元集合对象
    // alert(nodes);
	for(var i=0;i<nodes.length;i++){
	  var node=nodes[i];

	  if(node.elementType=="TextNode"){
   	 		if(node.textId==tagnode.id){
   	         var resX=node.x;
  			 var resY=node.y;
	       	if(node){
				  node.setLocation((resX+diffX),(resY+diffY));
	       	}
   	         
   	      }
	  }
	}
}

function Map() {  
    this.elements = new Array();  
    // 获取MAP元素个数
    this.size = function() {  
        return this.elements.length;  
    };  
    // 判断MAP是否为空
    this.isEmpty = function() {  
        return (this.elements.length < 1);  
    };  
    // 删除MAP所有元素
    this.clear = function() {  
        this.elements = new Array();  
    };  
    // 向MAP中增加元素（key, value)
    this.put = function(_key, _value) {  
        this.elements.push( {  
            key : _key,  
            value : _value  
        });  
    };  
    // 删除指定KEY的元素，成功返回True，失败返回False
    this.remove = function(_key) {  
        var bln = false;  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].key == _key) {  
                    this.elements.splice(i, 1);  
                    return true;  
                }  
            }  
        } catch (e) {  
            bln = false;  
        }  
        return bln;  
    };  
    // 获取指定KEY的元素值VALUE，失败返回NULL
    this.get = function(_key) {  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].key == _key) {  
                    return this.elements[i].value;  
                }  
            }  
        } catch (e) {  
            return null;  
        }  
    };  
    // 获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
    this.element = function(_index) {  
        if (_index < 0 || _index >= this.elements.length) {  
            return null;  
        }  
        return this.elements[_index];  
    };  
    // 判断MAP中是否含有指定KEY的元素
    this.containsKey = function(_key) {  
        var bln = false;  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].key == _key) {  
                    bln = true;  
                }  
            }  
        } catch (e) {  
            bln = false;  
        }  
        return bln;  
    };  
    // 判断MAP中是否含有指定VALUE的元素
    this.containsValue = function(_value) {  
        var bln = false;  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].value == _value) {  
                    bln = true;  
                }  
            }  
        } catch (e) {  
            bln = false;  
        }  
        return bln;  
    };  
    // 获取MAP中所有VALUE的数组（ARRAY）
    this.values = function() {  
        var arr = new Array();  
        for (i = 0; i < this.elements.length; i++) {  
            arr.push(this.elements[i].value);  
        }  
        return arr;  
    };  
    // 获取MAP中所有KEY的数组（ARRAY）
    this.keys = function() {  
        var arr = new Array();  
        for (i = 0; i < this.elements.length; i++) {  
            arr.push(this.elements[i].key);  
        }  
        return arr;  
    };  
}


function getTextNodeByEdge(edge,e){

    var targetNode;
     for(var i=0;i<graph.childs.length;i++){
        var obj=graph.childs[i];

        if(obj.elementType=="TextNode"&&edge.id==obj.textId){
            targetNode=obj;
            break;
        }
     }

    
   
    if(targetNode){

            var nodeA=getNodeById(edge.nodeAId);
            var nodeB=getNodeById(edge.nodeBId);
            var obj=graph.getEdgeLableLocaltion(nodeA,nodeB);
                // targetNode.x=obj.labelX+targetNode.diffx;
                // targetNode.y=obj.labelY+targetNode.diffy;

                    // targetNode.x=obj.labelX+targetNode.diffx;
                    // targetNode.y=obj.labelY+targetNode.diffy;
                    if(targetNode.diffx==0&&targetNode.diffy==0){
                        if(obj.radius>1){
                        targetNode.x=obj.labelX+targetNode.diffx-targetNode.textSize/2;
                        targetNode.y=obj.labelY+targetNode.diffy; 
                    }else{
                        targetNode.x=obj.labelX+targetNode.diffx-targetNode.textWidth/2;
                        targetNode.y=obj.labelY+targetNode.diffy; 
                    }
                    }else{
                        targetNode.x=obj.labelX+targetNode.diffx;
                        targetNode.y=obj.labelY+targetNode.diffy;
                    }
                   
                         
            // console.log("edge.labelX"+edge.labelX+"-----"+edge.labelY);
            // console.log("targetNode.diffx"+targetNode.diffx+"-----"+targetNode.diffy);
            // console.log("x"+targetNode.textSize);
            // console.log("obj.labelX"+obj.labelX+"-----"+obj.labelY);
            // console.log("targetNode.x"+targetNode.x+"-----"+targetNode.y);
            targetNode.radius=obj.radius;
            targetNode.angle=obj.angle;
            targetNode.updateNode(targetNode);
        
      
        
    }
   
     return targetNode;
}

function Bound(x0,y0,x1,y1,x2,y2,x3,y3){
	this.x0=x0;
	this.y0=y0;
	this.x1=x1;
	this.y1=y1;
	this.x2=x2;
	this.y2=y2;
	this.x3=x3;
	this.y3=y3;

}

 
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
    this.splice(index, 1);
    }
};
   
   
   function windowTocanvas(canvas, x, y) {  
       var bbox = canvas.getBoundingClientRect(); 
       // 用于获得页面中某个元素的左，上，右和下分别相对浏览器视窗的位置。
       return {  
           x: x - bbox.left * (canvas.width / bbox.width),   
           y: y - bbox.top * (canvas.height / bbox.height)  
       };  

   } 
   
   function saveLine(node){
	   var saveLinks=[];
	   var lineObj = new Object();
       lineObj.nodeAId=node.nodeAId;
       lineObj.nodeAName=node.nodeAName;
       lineObj.nodeBId=node.nodeBId;
       lineObj.nodeBName=node.nodeBName;
       lineObj.linkName=node.name;
       lineObj.subType=node.subType;
       if(node.id!=null){
         lineObj.linkID=node.id;
       }
       
       saveLinks.push(lineObj);
	   var linksStr = JSON.stringify(saveLinks);
	   
	   $.ajax({
		      url:proName+"/cmdb/graph/saveLine.do",
		      
		      type:"POST",
	 		  data:{"linksStr":linksStr,"graphId":graphID},
		      
		      dataType:'json',
		      success:function(data){
			         if(data.result=="success"){
			            loadTopo(graphID);
			            graph.drawData();
			         }
			      }
		   });
   }

