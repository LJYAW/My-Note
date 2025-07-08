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
<style>
	a{text-decoration:none;}
</style>
<script>
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
	var timer;
	var selectRowData = null;
	
	$(function(){
		dataMain.Rows =null;
		gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'设备IP', name:'deviceIp', width:100, align:'left' },
                    { display:'发现状态', name:'status', width:80, align:'left' },
                    { display:'设备名称', name:'deviceName', width:130, align:'left' },
                    { display:'设备类型', name:'deviceType', width:110, align:'left' },
                    { display:'版本', name:'deviceVersion', width:130, align:'left' },
                    { display:'接口数量', name:'interfaceNum', width:80, align:'left' },
                    { display:'发现过程', name:'discoveryType', width:110, align:'left' }
               		],  
				data: dataMain,
				alternatingRow: 'true',
                width: '100%', 
                height:'280px',
                usePager:false,
                checkbox: false,
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
		var obj = {};
		$(".scan_add").click(function(event){//添加数据
			event.preventDefault();
			var val1 = $(".scan_data1").val().replace(" ",""),
				val2 = $(".scan_data2").val().replace(" ",""),
				val3 = $(".scan_data3").val().replace(" ",""),
				val4 = $(".scan_data4").val().replace(" ",""),
				val5 = $(".scan_data5").val().replace(" ",""),
				val6 = $("input[name='arpGetMethod']:checked").val(),
				text = val1+'-'+val2+'%23'+val3+'%23'+val4+'%23'+val5+'%23'+val6;
			//判断数据是否null
			if(val1 == "" ||!ipok(val1)){
				alert("开始地址不能为空或格式不正确!");
				return;
			}
			else if(val2 == ""||!ipok(val2)){
				alert("结束地址不能为空或格式不正确!");
				return;
			}
			else if(val1==val2){
				alert("开始地址与结束地址不能相同!");
				return;
			}
			else if(val3 == ""){
				alert("Snmp团体字不能为空!");
				return;
			}
			else if(val4 == ""||!ipok(val4)){
				alert("网关IP地址不能为空或不正确!");
				return;
			}
			else if(val5 == ""){
				alert("网关Snmp团体字不能为空!");
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
		$(".scan_gain").click(function(event){//取值
			event.preventDefault();
			var html = $(".scan_textBox p").map(function() {
				return $(this).text();
			}).get().join(";");
			if(html==null||html==''){
				alert("搜索范围不能为空！");
				return;
			}else{
				var url = timeURL('${ctx}/fas/res/net/devices/scanScopeQuery.do');
// 				alert(html);
            	$.ajax({
            		url: url,
            		data:"scope="+html+"&date="+new Date(),
            		dataType: 'json',
//             		beforeSend: function() {
// 		        		$("#bnSearch").attr("disabled","disabled");
// 						$("#trMsg").removeClass("dN");
// 		            	$("#trTxt").removeClass("dN");
// 		            	dataMain.Rows=null;
// 		            	loadData();
// 		        	},
            		success: function(data) {
                		if( data.result == "success" ){
// 		        			intvMsg = setInterval(getSearchMsg, 5000); 
		        			
		        		}
// 		        		else{
// 		           			$.ligerDialog.error('搜索失败！');
// 		           			$("#bnSearch").removeAttr("disabled");
// 		           			$("#trTxt").addClass("dN");
// 		           			$("#trMsg").addClass("dN");
// 		        		}          				
            		}
            	}); 
            	 $("#trTxt").removeClass("dN");
            	 $("#showlogs").css("display","");
       			 timer=setInterval("showTopoLogs()",2000);//1000为1秒钟     
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
	
	//输入一个input另外一个input值改变
    function keydown1(){
    	var value1=$("#defGateway").val();
  		$("#gateway").val(value1);
 	}
 	function keydown2(){
 		var value2=$("#defCommunityStr").val()
  		$("#communityStr").val(value2);
  		$("#snmpStr").val(value2);
 	}
	
	function getSearchMsg() 
	{
		$.ajax({
			url: timeURL("${ctx}/fas/res/net/search/message.do"),
			dataType: 'json',
			success: function(data) {
				if( data.result){
					dataMain.Rows=data.result;
				 	loadData();
				}
				if( data.status == 'true' ){
					clearInterval(intvMsg);
           			$.ligerDialog.success('搜索完毕！');
           			$("#trTxt").addClass("dN");
           			$("#bnSearch").removeAttr( "disabled");
           			window.parent.parent.parent.saveOK();
				}
			}
		});        			
	}
	
	
	function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
    
    // 	var logStr="";
    var flag=true;
    var count=0;
    var s=0;
    function showTopoLogs(){
      $.ajax({
        url:"${ctx}/fas/res/net/search/discoverDerviceOutputLogs.do",
        dataType:"json",
        success:function(data){
          var obj=data.outputstr;
//           var status=data.status;
  		  var str = JSON.stringify(obj) ;
		  str=str.replace(new RegExp("\"",'g'),"");
  		  if(str.length==0){
//   		      str="...";
  		      logStr+=str;
  		  }else{
  		     var logStr="设备搜索开始.......";
  		      logStr+="</br>"+str;
  		      $("#showlogs").html(logStr);
  		  }
  	   
          
          $("#showlogs").html(logStr);
          getScrollTop();
           if(logStr.indexOf("设备搜索结束")>0){  //搜索完成
            window.parent.$.ligerDialog.success("设备搜索结束！");
            clearInterval(timer);
            $("#trTxt").addClass("dN");
            window.parent.editOK(); 
          }
			
        }
      });
      count++;
    }
        
     function getScrollTop() {
		document.getElementById("showlogs").scrollIntoView(false);
	}    
</script>
</head>
<body>
<form id="formSearch" method="post">
	 	<table class="scan_datatable">
	 		<tr>
	 			<td></td>
	 			<td align="right"><span style="font-weight:bold;">缺省网关：</span></td>
	 			<td ><input type="text" class="scan_def" id="defGateway" onkeyup="keydown1()"/>&nbsp;</td>
				<td align="center"><span style="font-weight:bold;">缺省团体字：</span></td>
				<td ><input type="text"  class="scan_def" id="defCommunityStr" onkeyup="keydown2()"/>&nbsp;</td>
				<td></td>
	 		</tr>
	 		 <tr>
	 		 	<td></td>
                <td align="right"><span style="font-weight:bold;">ARP表获取方式：</span></td>
                <td colspan=3>
	                <input type="radio" name="arpGetMethod" id="snmp" value="snmp"  checked/>&nbsp;&nbsp;snmp&nbsp;&nbsp;&nbsp;&nbsp;
	                <input type="radio" name="arpGetMethod" id="telnet" value="telnet"  />&nbsp;&nbsp;telnet&nbsp;&nbsp;&nbsp;&nbsp;
	                <input type="radio" name="arpGetMethod" id="ssh" value="ssh"  />&nbsp;&nbsp;ssh
                </td>
                <td ></td>
            </tr>
			<tr class="scan_tc">
				<td width="110px"><span style="font-weight:bold;">设置搜索范围:</span></td>
				<td width="110px">开始地址</td>
				<td width="110px">结束地址</td>
				<td width="110px">Snmp团体字</td>
				<td width="110px">网关IP地址</td>
				<td width="110px">网关Snmp团体字</td>
			</tr>
			<tr>
				<td></td>
				<td ><input type="text" class="scan_data1"/>&nbsp;</td>
				<td><input type="text" class="scan_data2"/>&nbsp;</td>
				<td><input type="text" class="scan_data3" id="snmpStr" />&nbsp;</td>
				<td><input type="text" class="scan_data4" id="gateway"/>&nbsp;</td>
				<td><input type="text" class="scan_data5"/ id="communityStr"></td>
			</tr>
			<tr valign="top">
				<td></td>
				<td colspan="4"><div class="scan_textBox"></div>
				</td>
				<td class="scan_btnbox" align="center">
					<a href="" class="scan_btn scan_ml scan_add">添加</a><br />
					<a href="" class="scan_btn scan_ml scan_del">删除</a><br /> 
					<a href="" class="scan_btn scan_ml scan_up">上移</a><br /> 
					<a href=""class="scan_btn scan_ml scan_down">下移</a>
				</td>
			</tr>
			<tr class="scan_tc" height="50px">
				<td colspan="6"><a href="" class="scan_btn1 scan_btn4 scan_ml scan_gain">开始搜索</a></td>
			</tr>
		</table>
		
		<div style="margin:0 auto;width:96%;">
		<div id="showlogs" style="text-align: left;width:500px;height:auto;padding-left:15px;margin-left: auto;margin-right: auto;"></div>
			<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:center">
           		<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;搜索中...
           		</span>
            </div>
<!--             <div id="trMsg" class="dN" > -->
<!--             	<div id="maingrid" ></div>  -->
<!--             </div> -->
		
		</div>
</form>
</body>
</html>
