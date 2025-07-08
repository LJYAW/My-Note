<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>范围搜索</title>
<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/picdisplay.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/plugin/calendar/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />


<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/timeControl.js" type="text/javascript"></script>
<script src="${ctx}/js/timePeriod.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script type="text/javascript">
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
	var selectRowData = null;
	var timer;
	$(function ()
	{	
// 		    $("#topoMethod1").hide();
// 		    $("#topoMethod2").hide();

// 			<c:if test="${result=='success'}">
// 				window.parent.editOK();    
// 				window.parent.closeDlg();
// 			</c:if>


			changeIpAddrRange();
		 	$(".scan_add").click(function(event){//添加数据
			event.preventDefault();
			
			var mode=$("#scanmode").val();
			var text="";
// 			var val1 = $(".scan_data1").val().replace(" ","");
			
// 			var	val2 = $(".scan_data2").val().replace(" ","");
			
// 			var val3=$(".scan_data4").val();
			if(mode==0){  //IP子网
			  var val1 = $("#subnetdata1").val().replace(" ","");
			  var val2 = $("#subnetdata2").val().replace(" ","");
			  var val3=$("#subnetdata3").val();
			   text=val1+'/'+val2+'@'+val3;
			   	//判断数据是否null
			if(val1 == "" ||!ipok(val1)){
				alert("开始地址不能为空或格式不正确!");
				return;
			}
			else if(val2 == ""||!ipok(val2)){
				alert("子网掩码不能为空或格式不正确!");
				return;
			}
			else if(val1==val2){
				alert("开始地址与子网掩码不能相同!");
				return;
			}
			else{
				var repeatData;
				$(".scan_textBox p").each(function(){//判断是否有重复的值
					if($(this).text() == text){
						repeatData = true;
					}
				});
			  if(repeatData){
				  alert("数据不能重复");
			  }else{
				  var newStr = '<p>'+text+'</p>';
				  $(".scan_textBox").append(newStr);
				  $(".scan_textBox p").bind("click",function(){
						$(".scan_textBox p").removeClass("scan_bg")
						$(this).addClass("scan_bg");
				  })
			  }
			}
			  
			}else if(mode==1){  //IP地址范围
			  var val1 = $("#rangedata1").val().replace(" ","");
			  var val2 = $("#rangedata2").val().replace(" ","");
			  var val3 = $("#rangedata3").val().replace(" ","");
			  var val4=$("#rangedata4").val();
			   text=val1+'-'+val2+'/'+val3+'@'+val4;
			  	//判断数据是否null
			if(val1 == "" ||!ipok(val1)){
				alert("开始地址不能为空或格式不正确!");
				return;
			}
			else if(val2 == ""||!ipok(val2)){
				alert("结束地址不能为空或格式不正确!");
				return;
			}else if(val3 == ""||!ipok(val3)){
			   alert("子网掩码不能为空或格式不正确!");
				return;
			}
			else if(val1==val2){
				alert("开始地址与结束地址不能相同!");
				return;
			}
			else{
				var repeatData;
				$(".scan_textBox p").each(function(){//判断是否有重复的值
					if($(this).text() == text){
						repeatData = true;
					}
				});
			  if(repeatData){
				  alert("数据不能重复");
			  }else{
				  var newStr = '<p>'+text+'</p>';
				  $(".scan_textBox").append(newStr);
				  $(".scan_textBox p").bind("click",function(){
						$(".scan_textBox p").removeClass("scan_bg")
						$(this).addClass("scan_bg");
				  })
			  }
			} 
			   
			}else{//多个IP地址
			     var val1 = $("#moredata1").val().replace(" ","");
			     var val2=$("#moredata2").val();
			     text=val1+'@'+val2;
			     
			     
			     	//判断数据是否null
			if(val1 == ""){
				alert("开始地址不能为空或格式不正确!");
				return;
			}
			else{
			    var iparr=val1.split(",");
			    for(var i=0;i<iparr.length;i++){
			      if(!ipok(iparr[i].replace(" ",""))){
			        alert("IP清单包含非法IP格式!");
					return;
			      }
			    }
// 			    else{
			         var repeatData;
				$(".scan_textBox p").each(function(){//判断是否有重复的值
					if($(this).text() == text){
						repeatData = true;
					}
				});
			  if(repeatData){
				  alert("数据不能重复");
			  }else{
				  var newStr = '<p>'+text+'</p>';
				  $(".scan_textBox").append(newStr);
				  $(".scan_textBox p").bind("click",function(){
						$(".scan_textBox p").removeClass("scan_bg")
						$(this).addClass("scan_bg");
				  })
			  }
// 			      }
				
			}
			     
			}
			
			
		});
		
		$(".scan_del").click(function(event){
			event.preventDefault();
			$(".scan_textBox p.scan_bg").remove();
		});
		
		$(".scan_up").click(function(event){
			event.preventDefault();
			var num1 = parseInt(site());
			if((num1-1) <0){
				return;
			}else{
				$(".scan_textBox p:eq("+(num1-1)+")").before($(".scan_textBox p.scan_bg"));
			} 
		});
		
		$(".scan_down").click(function(event){
				event.preventDefault();
				var num1 = parseInt(site());
				if((num1) > ($(".scan_textBox p").length - 1)){
					return;
				}else{
					$(".scan_textBox p:eq("+(num1+1)+")").after($(".scan_textBox p.scan_bg"));
				}
			});
		
		
		function site(){//得到选中元素的位置
			var num;//选中上移的元素
			$(".scan_textBox p").each(function(e) {
				if($(this).hasClass("scan_bg")){
					num = e;
				};
			});
			return num;
		};	
		
	});
	
	
	function getSearchMsg() 
	{
		$.ajax({
			url: timeURL("${ctx}/fas/res/net/search/message.do"),
			dataType: 'json',
			success: function(data) {
			if(data.result){
				 dataMain.Rows=data.result;
				 loadData();
			}
			if( data.status =='true' ){
					clearInterval(intvMsg);
           			$.ligerDialog.success('搜索完毕！');
           			$("#trTxt").addClass("dN");
           			$("#bnSearch").removeAttr( "disabled");
			}
			}
		});        			
	} 
	
	function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
    function getSearchScope(){
      var html = $(".scan_textBox p").map(function() {
				return $(this).text();
			}).get().join(";");
		$("#searchScope").val(html);	
    }  
    
    function defSnmp(){
      $(".scan_data3").val($("#defaultSnmpStr").val());
    }
    
    
    function startSearch(){
       $.ajax({
			     type: "POST",
			     url: "${ctx}/fas/res/net/devices/startSearch.do",
			     contentType: "application/x-www-form-urlencoded; charset=utf-8",
			     data: $("#formSearch").serialize(),
			        success: function (date) {
// 			           			window.parent.$.ligerDialog.success("收索完毕！");
			              },
			     });
       
       timer=setInterval("showTopoLogs()",3000);//1000为1秒钟
//         $("#formSearch").hide();
    }
    
    var logStr="";
    var flag=true;
    var count=0;
    var s=0;
    function showTopoLogs(){
      $.ajax({
        url:"${ctx}/fas/res/net/search/discoverDerviceOutputLogs.do",
        dataType:"json",
        success:function(data){
          var obj=data.outputstr;
          var status=data.status;
  		  var str = JSON.stringify(obj) ;
		  str=str.replace(new RegExp("\"",'g'),"");
  		  if(str.length==0){
  		      str="...";
  		      logStr+=str;
  		  }else{
  		      logStr+="</br>"+str;
  		  }
          $("#showlogs").html(logStr);
          getScrollTop();
           if(logStr.indexOf("设备搜索结束")>0){  //搜索完成
            $.ligerDialog.success('设备搜索完毕！');
            clearInterval(timer);
          }
			
        }
      });
      count++;
    }
    
    
     //转意符换成普通字符
   	 function escape2Html(str) {
	 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
	 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
	} 
	
	
	function getScrollTop() {
		document.getElementById("showlogs").scrollIntoView(false);
	} 
	
	
	function changeIpAddrRange(){
	  var mode=$("#selectMode").val();
	  $("#scanmode").val(mode);
	  if(mode==0){
	    $("#subnet1").css("display","");
	     $("#subnet2").css("display","");
	    $("#ipRange1").css("display","none");
	    $("#ipRange2").css("display","none");
	    $("#moreip1").css("display","none");
	    $("#moreip2").css("display","none");
	    
	  }else if(mode==1){
	    $("#subnet1").css("display","none");
	    $("#subnet2").css("display","none");
	    $("#ipRange1").css("display","");
	    $("#ipRange2").css("display","");
	    $("#moreip1").css("display","none");
	    $("#moreip2").css("display","none");
	  }else{
	    $("#subnet1").css("display","none");
	    $("#subnet2").css("display","none");
	    $("#ipRange1").css("display","none");
	    $("#ipRange2").css("display","none");
	    $("#moreip1").css("display","");
	    $("#moreip2").css("display","");
	  }
	}
	
	
	
	
	function defalutSnmp(){
	 var defaultSnmpStr=$("#defaultSnmpStr").val();
	 $("#subnetdata3").val(defaultSnmpStr);
	 $("#rangedata4").val(defaultSnmpStr);
	 $("#moredata2").val(defaultSnmpStr);
	 
	}
			
</script>

</head>
<body> 
							<form id="formSearch" name="formSearch" action=""  method="post">
								<table class="scan_datatable" align="center" style="margin-top: 20px;">
								 <input type="hidden" id="ifGenTopo" name="ifGenTopo"  value="" >
								  <input type="hidden" id="scanmode" name="scanmode"  value="" >
								  <input type="hidden" id="searchScope" name="searchScope" value="">
								  
									<tr>
									<td>
									  设备搜索方式：
									</td>
									  <td>
									    <select id="selectMode" name="selectMode" onchange="changeIpAddrRange()">
<!-- 											    <option value="0" selected="selected">IP子网</option> -->
											    <option value="1" >IP地址范围</option>
											    <option value="2" >IP地址</option>
											</select>
									  </td>
									</tr>
									<tr>
										<td >
											<STRONG>IP地址搜索范围：</STRONG>
										</td>
										
										
										<td colspan="2" id="ipRange1" style="display: none">
											<span>开始地址<span/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span>结束地址<span/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span>子网掩码<span/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span>SNMP团体字<span/>
										</td>
										
										
										<td colspan="2"  id="moreip1" style="display: none">
											<span>IP地址清单(多个IP地址之间用","分割)<span/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span>SNMP团体字<span/>
										</td>
										
										<td ></td>
									</tr>
									<tr>
										<td></td>
										
<!-- 										<td colspan="2"> -->
<!-- 											<input type="text" class="scan_data1" value="" name="scan_data1" style="width:110px"/> -->
<!-- 											- -->
<!-- 											<input type="text" class="scan_data2" value=""  name="scan_data2" style="width:110px"/> -->
<!-- 											@ -->
<!-- 											<input type="text" class="scan_data3"  value=""  name="scan_data3" style="width:110px"/> -->
<!-- 										</td> -->

										<td colspan="2" id="subnet2" style="display:none">
											<input type="text" class="scan_data1" value="" id="subnetdata1"  name="scan_data1" style="width:135px"/>
											
											<input type="text" class="scan_data2" value="255.255.255.0"  id="subnetdata2"  name="scan_data2" style="width:135px"/>
											
											<input type="text" class="scan_data3"  value="" id="subnetdata3"  name="scan_data3" style="width:80px"/>
										</td>
										
										<td colspan="2" id="ipRange2" style="display: none">
											<input type="text" class="scan_data1" value="" id="rangedata1"  name="scan_data1" style="width:92px"/>
											
											<input type="text" class="scan_data2" value="" id="rangedata2" name="scan_data2" style="width:92px"/>
											
											<input type="text" class="scan_data3"  value="255.255.255.0" id="rangedata3" name="scan_data3" style="width:92px"/>
											
											<input type="text" class="scan_data4"  value="" id="rangedata4" name="scan_data4" style="width:70px"/>
										</td>
										
										
										
										<td colspan="2" id="moreip2" style="display: none">
											<input type="text" class="scan_data1" value="" id="moredata1"  name="scan_data1" style="width:295px"/>
											
											<input type="text" class="scan_data2"  value="" id="moredata2" name="scan_data2" style="width:70px"/>
											
											
										</td>
										
										<td align="center">
											<a href="" class="scan_btn scan_ml scan_add">添加</a><br /> 
										</td>
									</tr>
									<tr>
										<td></td>
										<td colspan="2"><div class="scan_textBox" style="width:370px;height:60px"></div>
										<td class="scan_btnbox" align="center">
											<a href="" class="scan_btn scan_ml scan_del">删除</a><br /> 
											<a href="" class="scan_btn scan_ml scan_up">上移</a><br /> 
											<a href=""class="scan_btn scan_ml scan_down">下移</a>
										</td>
									</tr>
									
									<tr>
										<td colspan=4 align="center">
											<button name="btnSubmit2" class="scan_btn1 scan_btn4 scan_ml scan_gain" onclick="getSearchScope();startSearch()" type="button" >
												开始搜索
											</button>
										</td>
									</tr>
									
									
								</table>
							</form>
							
							<div id="showlogs" style="text-align: left;width:500px;height:auto;padding-left:15px;margin-left: auto;margin-right: auto;"></div>
</body>
</html>

