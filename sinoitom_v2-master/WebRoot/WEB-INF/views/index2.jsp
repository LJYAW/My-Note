<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>${s:paramVal("SystemName")}</title>

    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/portlet.css"/>
    <!-- text fonts -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/fonts.googleapis.com.css"/>

    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${ctx }/assets/css/ace-part2.min.css" class="ace-main-stylesheet"/>
    <![endif]-->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace-skins.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css"/>
    <![endif]-->


    <script src="${ctx }/static/assets/js/jquery.min.js"></script>
    <script src="${ctx }/static/assets/js/layer.js"></script>
    <script src="${ctx }/static/assets/js/H-ui.admin.js"></script>
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->


    <script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery-ui.custom.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.ui.touch-punch.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.easypiechart.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.sparkline.index.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.flot.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.flot.pie.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.flot.resize.min.js"></script>

    <!-- ace scripts -->
    <script src="${ctx }/static/assets/js/ace-elements.min.js"></script>
    <script src="${ctx }/static/assets/js/ace.min.js"></script>


    <script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctx }/static/jquery-interface/interface.js" type="text/javascript"></script>
    <script>



        $(function () {
            // Toggle Single Portlet
            $('a.toggle').click(function () {
                    $(this).parent('div').next('div').toggle();
                    return false;
                }
            );
            // Controls Drag + Drop
            $('#columns td').Sortable(
                {
                    accept: 'portlet',
                    helperclass: 'sort_placeholder',
                    opacity: 0.7,
                    tolerance: 'intersect'
                }
            );


            $('.main-content').height($(document).height() - $('.footer').height() - $('#navbar').height());
            $('.sidebar').height($(document).height() - $('.footer').height() - $('#navbar').height());
            $('.nav-list').css("max-height", $('.sidebar').height() - 80);
            $('.main-content-inner').height($(document).height() - $('.footer').height() - $('#navbar').height());
            $('.page-content').height($('.main-content-inner').height() - $('.breadcrumbs').height() - 2);

        });

    </script>

</head>

<body class="no-skin">
<%@ include file="header.jsp" %>
<div id="sidebar" class="sidebar responsive ace-save-state display">

    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>

            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>
        </div>
        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div>
    <!-- /.sidebar-shortcuts -->

    ${menuHtmlData }


    <!-- /.nav-list -->

    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
           data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
</div>

<div class="main-content">
    <div class="main-content-inner">

        <!--                     <div class="breadcrumbs ace-save-state" id="breadcrumbs"> -->
        <!--                         <ul class="breadcrumb"> -->
        <!--                             <li> -->
        <!--                                 <i class="ace-icon fa fa-home home-icon"></i> -->
        <!--                                 <a href="#" onclick="backIndex()">Home</a> -->
        <!--                             </li> -->
        <!--                             <li class="active"></li> -->
        <!--                         </ul> -->
        <!--                     </div> -->
        <!-- portal -->


        <div class="page-content">
            <iframe id="home" name="home" src="main.do" frameborder="0" style="width:100%;height:100%"></iframe>
        </div>

        <!-- /.page-content -->
    </div>
</div>
<!-- /.main-content -->

<%@ include file="footer.jsp" %>


</body>
</html>
