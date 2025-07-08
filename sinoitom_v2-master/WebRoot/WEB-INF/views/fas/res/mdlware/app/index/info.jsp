<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Tomcat测试</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>

    <script src="${ctx}/static/formatter.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/js/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">

        var dataMain = {};
        var gridMain;
        var selectRowData = null;
        var dlgSearch = null;

        var devIpAddr = '${devIpAddr}';
        var content = '${content}';
        var tomcatVersion = '${tomcatVersion}';

        $(function ()
        {
            $("#toptoolbar").ligerToolBar({
                items:
                    [
                        { text:'详情', click:showView, icon:'view'},
                        { line:true },
                        { text:'刷新', click:onRefresh, icon:'refresh' }
                    ]
            });

            dataMain.Rows = ${jsonListData};
            gridMain = $("#maingrid").ligerGrid({
                columns:
                    [
                        { display:'服务器IP', name:'devIpAddr', width:100, align:'left' },
                        { display:'监测端口', name:'content', width:70, align:'left' },
                        { display:'监测时间间隔(毫秒)', name:'intervaltime', width:150, align:'left' },
                        { display:'总物理内存', name:'totalMem', width:120, align:'left' },
                        { display:'空闲物理内存', name:'freeMem', width:120, align:'left' },
                        { display:'总交换空间', name:'swapTotal', width:120, align:'left' },

                        { display:'空闲交换空间', name:'swapFree', width:120, align:'left' },
                        { display:'提交的虚拟内存', name:'committedMem', width:150, align:'left' },

                        { display:'监测时间', name:'monitorTime', width:150, align:'left'}  //,type:'dataFormat[Y-m-d H:i:s:sss]' (之时间类型为Timestamp类型)

                    ],
                data: dataMain,
                checkbox: 'true',
                alternatingRow: 'true',
                width: '100%',
                height:'100%',
                pageSize: '20',
                pageSizeOptions: [10, 20, 30, 50, 100],
                onSelectRow: function (data, index, dom){
                    selectRowData = data;
                }
            });


        });


        function onRefresh(){
            window.location.href='${ctx}/fas/res/mdlware/app/index/info.do?devIpAddr='+devIpAddr+'&content='+content+'&tomcatVersion='+tomcatVersion;
        }

        function showView() {
            var rows = gridMain.getSelectedRows();

            if (rows.length == 0) {

                $.ligerDialog.warn('请选择一条服务器信息！');

                return;
            } else if(rows.length > 1) {

                $.ligerDialog.warn('只能选择一条服务器信息进行查看！');

                return;
            } else {

                showTomcatView(rows[0].id,rows[0].devIpAddr,rows[0].content);

            }
        }

        function showTomcatView(id,devIpAddr,content) {

            var url = timeURL('${ctx}/fas/res/mdlware/app/index/showTomcatView.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content);

            // window.parent.showDlg('详情', 900, 800,url);
            window.open(url,'', 'height=700, width=1000, top=100, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');

        }


    </script>
</head>
<body style="padding:0; overflow:hidden;">
<div id="toptoolbar"></div>
<div id="maingrid"></div>

</body>
</html>
