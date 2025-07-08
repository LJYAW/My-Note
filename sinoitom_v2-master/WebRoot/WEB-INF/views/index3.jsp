<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${s:paramVal("SystemName")}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index3.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">

var layout = null;
var tab = null;
var accordion = null;
var tree = null;

var dlg = null;

var topDivHeight = 60;

$(function(){
    //布局
	layout = $("#layout-index").ligerLayout({topHeight:60, leftWidth:200, bottomHeight:20, space:2,
		allowTopResize:false, allowBottomResize:false, onHeightChanged:f_heightChanged});
	
    var height = $(".l-layout-center").height();
    //Tab
    $("#index-center").ligerTab({ height: height });
    
    //面板
    $("#index-accordion").ligerAccordion({ height: height - 24, speed: null });
    
    //树
    var treeData = ${treeMenuData};
    $("#index-treeMenu").ligerTree({
        checkbox: false,
        slide: false,
        nodeWidth: 100,
        attribute: ['code','name','id','url'],
        textFieldName: 'name',
        data: treeData,
        onSelect: function(node)
        {
            if (!node.data.url) return;
            var tabid = $(node.target).attr("tabid");
            if (!tabid)
            {
            	tabid = node.data.id;
                $(node.target).attr("tabid", tabid);
            }
            f_addTab(tabid, node.data.name, node.data.url);
        }
    });


    tab = $("#index-center").ligerGetTabManager();
    accordion = $("#index-accordion").ligerGetAccordionManager();
    tree = $("#index-treeMenu").ligerGetTreeManager();

    $("#open_show").toggle(function(){
		$("#index-top").hide();
		$(this).removeClass("open").addClass("close");
		$(this).attr("title","放出页眉");
		var h1 = $("#index-center").height();
		var h2 = $(".l-layout-bottom").position().top;
		h1 = h1 + topDivHeight;
		h2 = h2 + topDivHeight;
		$("#layout-index").height(h1);
		$(".l-layout-left").height(h1);
		$("#index-accordion").height(h1);
		$(".l-layout-center").height(h1);
		$("#index-center").height(h1);
		$(".l-layout-bottom").css("top", h2);
	    if (tab){
	    	var h3 = $(".l-tab-content").height();
	    	h3 = h3 + topDivHeight;
			$(".l-tab-content").height(h3);
			$(".l-tab-content-item").height(h3);
	    }
	},function(){
		$("#index-top").show();
		$(this).removeClass("close").addClass("open");
		$(this).attr("title","收起页眉");
		var h1 = $("#index-center").height();
		var h2 = $(".l-layout-bottom").position().top;
		h1 = h1 - topDivHeight;
		h2 = h2 - topDivHeight;
		$("#layout-index").height(h1);
		$(".l-layout-left").height(h1);
		$("#index-accordion").height(h1);
		$(".l-layout-center").height(h1);
		$("#index-center").height(h1);
		$(".l-layout-bottom").css("top", h2);
	    if (tab){
	    	var h3 = $(".l-tab-content").height();
	    	h3 = h3 - topDivHeight;
			$(".l-tab-content").height(h3);
			$(".l-tab-content-item").height(h3);
	    }
	});
    
	$("#index-loading").hide();
	
// 	onDeadline();
// 	onlineCheck();
// 	setInterval(onlineCheck, 30000); 

});

function onlineCheck() {
	$.ajax({
		url: timeURL("online.do"),
		dataType: 'json',
		success: function(data) {
			$("#onlineNum").text("在线用户数："+data.onlineNum);			
			$("#flowSpeed").text(data.flowSpeed);	
			if( data.flowColor == "red" ){
				$("#flowSpeed").removeClass("cU").addClass("cR");
			}
			else{
				$("#flowSpeed").removeClass("cR").addClass("cU");
			}
		}
	});        			
}

function onDeadline() {
	$.ajax({
		url: timeURL("getDeadline.do"),
		dataType: 'json',
		success: function(data) {
			if(data.deadline!=null&&data.deadline!=''){
				if(data.deadline=='无效'){
					$("#deadlineWarn").css({display:''});
					$("#deadline").text(data.deadline).css({color:'red'});
				}
				else if(data.deadline=='过期'){
					$("#deadlineWarn").css({display:''});
					$("#deadline").text(data.deadline).css({color:'red'});
				}
				else if(parseInt(data.deadline)>0&&parseInt(data.deadline)<90){
					$("#deadlineWarn").css({display:''});
					$("#deadline").text(data.deadline+' 天').css({color:'red'});
				}else{
					$("#deadlineWarn").css({display:'none'});
				}
			}else{
				$("#deadlineWarn").css({display:'none'});
			}			
		}
	});        			
}

function selectMenu(menuId, treeCode)
{
  var tabList = document.getElementById("topMenu").getElementsByTagName("li");
  var url = "subMenu.do?treeCode=";
  for(var i=0; i <tabList.length; i++){
    var menuLi = document.getElementById("topMenu_"+i);
    if (tabList[i].id == menuId){
	  if( menuLi != null )
		  menuLi.className = "current"; 
        
	  url = url + treeCode;     
    }else{
	  if( menuLi != null )
		  menuLi.className = ""; 
    }
  } 
  
	$.ajax({
		url: timeURL(url),
		dataType: 'json',
		success: function(data) {
			tree.clear();
			tree.set('data',data);
			layout.setLeftCollapse(false);				
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
            if(XMLHttpRequest.status=='200' || XMLHttpRequest.status=='403'){
    			alert("登录超时！"); 
    			window.location.href='${ctx}/login.jsp';
            }else{
            	alert("后台获取数据出错！");
            }
         }
	});        			
}

function doMenu(id, name, url)
{
    if (!id) return;
    if (!name) return;
    if (!url) return;

    f_addTab(id, name, url);
}

function f_heightChanged(options)
{
    if (tab)
        tab.addHeight(options.diff);
    if (accordion && options.middleHeight - 24 > 0)
        accordion.setHeight(options.middleHeight - 24);
}

function f_addTab(tabid, text, url)
{ 
    tab.addTabItem({ tabid:tabid, text:text, url:url });
} 

function showDlg(title, width, height, url)
{
	dlg = $.ligerDialog.open({ title:title, width:width, height:height, url:url, isHidden:false, showMax: true, isResize: true });
}

function saveOK(action, data)
{
	var tabid = tab.getSelectedTabItemID();
	var ifwin = $('iframe[name="'+tabid+'"]')[0].contentWindow;
	ifwin.saveOK(action, data); 
}

function multiSaveOK(data)
{
	var tabid = tab.getSelectedTabItemID();
	var ifwin = $('iframe[name="'+tabid+'"]')[0].contentWindow;
	ifwin.multiSaveOK(data); 
}

function moveOK()
{
	var tabid = tab.getSelectedTabItemID();
	var ifwin = $('iframe[name="'+tabid+'"]')[0].contentWindow;
	ifwin.moveOK(); 
	
	closeDlg();
}

function closeDlg()
{
	if( dlg ){
		dlg.close();
	}
}

function onAbout()
{
	var url = timeURL('onAbout.do');
	window.showDlg('About us', 700, 400, url);
}
</script>

<style type="text/css">
.menu_nav .nav_content{padding-left:${s:paramVal('index3MenuPdLeft')};}
.menu_nav .nav_content li{padding-left:${s:paramVal('index3MenuLiPdLeft')};padding-right:${s:paramVal('index3MenuLiPdRight')};}
</style>

</head>
<body>
	<div id="index-loading" class="pageloading"></div> 
    <div id="index-top">
    	<div class="top_logo"></div>
		<div class="fR top_qbar" style="width:400px;">
			<table width="100%">
				<tr>
					<td class="hR">
					<div class="user">当前用户：${LOGIN_USER.userName}&nbsp;&nbsp;
					<font class="c">当前流速：</font><font id="flowSpeed"></font></div>
			<ul class="fR">
<!-- 				<li>当前用户：${LOGIN_USER.userName}&nbsp;&nbsp;</li> -->
				<li class="home"><a href="#" onclick="doMenu('home', '系统首页', 'main.do');">首页</a>|</li>
				<li class="configure"><a href="#" onclick="doMenu('setup', '个人设置', 'system/user/setupGo.do');">设置</a>|</li>
				<li class="help_topic"><a href="#" onclick="onAbout();">关于</a>|</li>
				<li class="help_view"><a href="logout.do">退出</a></li>
			</ul>
					</td>
				</tr>
			</table>
			<table width="100%">
				<tr>
					<td>
						<div class="fR hL" id="deadlineWarn" style="width:150px;padding-left:23px;display:none">
							<font class="cU">license有效期：</font><font id="deadline"></font>
						</div>
					</td>
<!-- 					<td> -->
<!-- 						<div class="fR hL" style="width:165px;"> -->
<!-- 							<font class="cU">当前流速：</font><font id="flowSpeed"></font> -->
<!-- 						</div> -->
<!-- 					</td> -->
				</tr>
			</table>
		</div>
	</div>
	<div>

<!--nav,start-->
<div class="menu_navcc">
<div class="menu_nav">
<ul id="topMenu" class="nav_content">
<c:forEach items="${topMenuList}" var="menu" varStatus="status" >
<li id="topMenu_${status.index}" class="${menu.treeCode==CURRENT_LEFT_MENU_ROOT_CODE ? 'current':''}">
<a href="#" onclick="selectMenu('topMenu_${status.index}', '${menu.treeCode}');" >
<span class="sty1"></span>
<span class="sty2">${menu.menuName}</span>
<span class="sty3"></span>
</a>
</li>
</c:forEach>
</ul>
<div id="open_show" class="fR open" title="收起页眉"></div>
</div>
</div>
<!--nav,end-->

    </div>
	<div id="layout-index">
		<div position="left" title="功能子菜单" id="index-accordion" class="l-scroll">
			<ul id="index-treeMenu"></ul>
		</div>
		<div position="center" id="index-center">
			<div tabid="home" title="系统首页" style="height:300px">
				<iframe frameborder="0" name="home" id="home" src="main.do"></iframe>
			</div>
		</div>
		<div position="bottom" id="index-bottom">
			<table width="100%">
				<tr>
					<td width="15%" class="pd4"><font id="onlineNum"></font></td>
					<td width="70%" class="pd4">${s:paramVal("SystemCopyRight")}</td>
					<td width="15%" class="pd4"></td>
				</tr>
			</table>	
		</div>
	</div>
</body>
</html>
