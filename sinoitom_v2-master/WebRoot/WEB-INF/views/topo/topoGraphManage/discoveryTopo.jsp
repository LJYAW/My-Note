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
		showTopoMethod();
	});
		
	
	function showTopoMethod(){
	 	var smObj = document.getElementById("createTopo2");
			 if(smObj.checked){
		     	    $("#topoMethod1").show();
		    		$("#topoMethod2").show();
		    		$("#ifGenTopo").val(1);
		   		}else{
		   		   $("#topoMethod1").hide();
		    	   $("#topoMethod2").hide();
		    	   $("#ifGenTopo").val(0);
		   		}
	}
	
	
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
        
    
    function startSearch(){
       $.ajax({
			     type: "POST",
			     url: "${ctx}/topo/graphManage/searchTopo.do",
			     contentType: "application/x-www-form-urlencoded; charset=utf-8",
			     data: $("#formSearch").serialize(),
			     dataType: "json",
			        success: function (data) {
			              }
			     });
          $("#trTxt").removeClass("dN");
          $("#showlogs").css("display","");
       timer=setInterval("showTopoLogs()",3000);//1000为1秒钟
    }
    
    function submitForm() {
		$("#formSearch").submit();
	}
	
    
//     var logStr="";
    function showTopoLogs(){
      $.ajax({
        url:"${ctx}/topo/graphManage/outputLogs.do",
        dataType:"json",
        success:function(data){
          
	          if(data){
		          var obj=data.outputstr;
		          var str = JSON.stringify(obj) ;
				  str=str.replace(new RegExp("\"",'g'),"");
		  		  if(str.length==0){
		  		      str="...";
		  		      logStr+=str;
		  		  }else{
		  		  var logStr="Topo设备搜索开始.......";
		  		      logStr+="</br>"+str;
		  		       $("#showlogs").html(logStr);
		  		  }
		  	   
		           getScrollTop();
		           if(logStr.indexOf("设备搜索发现结束")>0){  //搜索完成
		            window.parent.$.ligerDialog.success("设备搜索发现结束！");
		            clearInterval(timer);
		            $("#trTxt").addClass("dN");
// 		            window.parent.editOK(); 
		          }
				
	        }
          }
  		
      });
    }
    
	
	function getScrollTop() {
		document.getElementById("trTxt").scrollIntoView(false);
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
										<td>  种子节点IP：</td><td><input type="text" name="seedIp"/></td>
										<td></td>
										
										<td colspan="2">缺省SNMP团体字：<input type="text" name="defaultSnmpStr" id="defaultSnmpStr" /></td>
										<td></td>
									</tr>
									
									<tr>
										<td >
											<STRONG>IP地址搜索范围：</STRONG>
										</td>
										
										<td   id="moreip1" colspan="2">
											<span>核心 路由器 IP地址(IP地址之间用","分割)<span/>
										</td>
										<td></td>
									</tr>
									
									<tr>
										<td></td>
										
										<td id="moreip2" >
<!-- 											<textarea  id="moredata1" name="scan_data"  rows="5"  style="width:300%;"></textarea> -->
											<input type="text" name="scan_data" id="scan_data" style="width:339%"/>
										</td>
									</tr>
									
									<tr>
										<td >
										</td>
										
										<td   id="moreip3" colspan="2">
											<span>核心 交换机 IP地址(IP地址之间用","分割)<span/>
										</td>
										<td></td>
									</tr>
									
									<tr>
										<td></td>
										
										<td id="moreip4" >
<!-- 											<textarea  id="moredata1" name="scan_data"  rows="5"  style="width:300%;"></textarea> -->
											<input type="text" name="scan_data2" id="scan_data2" style="width:339%"/>
										</td>
									</tr>
									
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>
											辖内IP子网：
										</td>
										<td style="width:70px;">
											<input type="text" name="areaNetIp" id="areaNetIp" />
												
										</td>
										<td></td>
										<td style="width:70px;">
											子网掩码：
										</td>
										<td>
											<input type="text" name="areaNetMask" id="areaNetMask" />
										</td>
									</tr>


									
									<tr>
										<td>
											<STRONG>设备搜索参数</STRONG>
										</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											搜索深度：
										</td>
										<td style="width:70px;">
											<input name="searchDepth" style="width: 60px" value="3" maxLength="1" ">
												
										</td>
										<td></td>
										<td style="width:70px;">
											并发线程：
										</td>
										<td>
											<input name="threadNum" style="width: 60px" value="5" maxLength="3"/>
										</td>
									</tr>
									<tr>
										<td>
											超时时间：
										</td>
										<td>
											<input name="timeout" style="width: 60px" value="3000"
												maxLength="5" >
											毫秒
										</td>
										<td></td>
										<td>
											重试次数：
										</td>
										<td>
											<input name="retries" style="width: 60px" value="3"
												maxLength="1" >
										</td>
									</tr>
									<tr>
										<td>
											生成拓扑：
										</td>
										<td >
											<input type="checkbox" name="createTopo2" id="createTopo2"   onclick="showTopoMethod()" value="true">
										</td>
									</tr>
									
									<tr id="topoMethod1">
									  <td >拓扑生成机制:</td>
										<td id="topoMethod2" colspan="2">
											<input type="checkbox" name="genTopoMethod" value="CDP" >CDP
											<input type="checkbox" name="genTopoMethod" value="LLDP" >LLDP
											<input type="checkbox" name="genTopoMethod" value="IPROUTE" checked="checked">IPROUTE
<!-- 											<input type="checkbox" name="genTopoMethod" value="FDB" >FDB -->
<!-- 											<input type="checkbox" name="genTopoMethod" value="ARP" >ARP -->
										</td>
									</tr>
									<tr>
										<td colspan=4 align="center">
											<button name="btnSubmit2" class="scan_btn1 scan_btn4 scan_ml scan_gain" onclick="startSearch()" type="button" >
												开始搜索
											</button>
										</td>
									</tr>
									
<!-- 									<tr> -->
<!-- 									  <td colspan=4 align="center"> -->
<!-- 									   <div id="showlogs" style="text-align: left;padding-left:10px;width:500px;height:100px;overflow: auto;"></div> -->
<!-- 									  </td> -->
<!-- 									</tr> -->
									
								</table>
							</form>
							
							<div id="showlogs" style="text-align: left;width:500px;height:auto;padding-left:15px;margin-left: auto;margin-right: auto;display: none">
							  Topo设备搜索开始.......
							</div>
							<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:center">
				           		<span id="searching">
				           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;搜索中...
				           		</span>
    						 </div>
</body>
</html>

