<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>实时监测</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/picdisplay.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/js/plugin/calendar/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar-en.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugin/calendar/datePatternCheck.js"></script>

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script src="${ctx}/js/timeControl.js" type="text/javascript"></script>
    <script src="${ctx}/js/timePeriod.js" type="text/javascript"></script>
    <script type="text/javascript">
        var selTab="tabitem1";
        var devIpAddr = '${devIpAddr}';
        var content = '${content}';

        <%--var service = '${service}';--%>
        var id = '${id}';
        var num = 0;
        var num2 = 0;
        var num3 = 0;
        var num4 = 0;

        $(function ()
        {
            $("#lineTab").ligerTab({
                dblClickToClose: false,
                onAfterSelectTabItem: function (tabid)
                {
                    if(tabid=="tabitem1"){
                        selTab="tabitem1";
                        num++;
                        if(num==0){
                            $("#lineItem_iframe").attr("src",'${ctx}/fas/res/mdlware/app/index/showLine.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content);
                        }
                    }
                    else if(tabid=="tabitem2"){
                          selTab="tabitem2";
                          if(num2==0){
                              $("#memoryItem_iframe").attr("src", '${ctx}/fas/res/mdlware/app/index/showMemory.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content);
                          }
                        num2++;
                    }
	               else if(tabid=="tabitem3"){
	                   selTab="tabitem3";
	                   if(num3==0){
                           $("#threadItem_iframe").attr("src", '${ctx}/fas/res/mdlware/app/index/showThread.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content);
                       }
                        num3++;
                    }
	               else if(tabid=="tabitem4"){
	                   selTab="tabitem4";
	                   if(num4==0){
                           $("#loadedClassItem_iframe").attr("src", '${ctx}/fas/res/mdlware/app/index/showLoadedClass.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content);
                       }
                       num4++;
	               }
                }

            });

            $("#lineItem_iframe").attr("src",'${ctx}/fas/res/mdlware/app/index/showLine.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content);
        });

        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
        }
        $(document).ready(window.onresize=function(){
            var wh=windowHeight();
            document.getElementById("memoryItem_iframe").style.height = (wh-32)+"px";
            document.getElementById("lineItem_iframe").style.height = (wh-32)+"px";
            document.getElementById("threadItem_iframe").style.height = (wh-32)+"px";
            document.getElementById("loadedClassItem_iframe").style.height = (wh-32)+"px";
        });

        //collectorTab  collectorIndicator   collectorItem_iframe  collector  collector_iframe
    </script>
</head>
<body style="padding:0px; overflow:hidden;">
<div id="lineTab" style="width:99%;overflow:hidden; border:1px solid #A3C0E8; ">

    <div id ="line" name="line" title="概览" >
        <iframe frameborder="0" id="lineItem_iframe" src="" class="whFull"></iframe>
    </div>
    <div id="memory" name="memory"  title="内存"  >
        <iframe frameborder="0" id="memoryItem_iframe" src="" class="whFull"></iframe>
    </div>
    <div id="thread" name="thread"  title="线程"  >
        <iframe frameborder="0" id="threadItem_iframe" src="" class="whFull"></iframe>
    </div>
    <div id="loadedClass" name="loadedClass"  title="类"  >
        <iframe frameborder="0" id="loadedClassItem_iframe" src="" class="whFull"></iframe>
    </div>


</div>
</body>
</html>