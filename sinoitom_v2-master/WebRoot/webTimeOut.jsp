<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
    <head>
        <meta charset="utf-8">
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		
        <script type="text/javascript" src="jquery-1.7.2.min.js"></script>
        <style type="text/css">
            
            html body{
                font-size:14px;
                font-family: "微软雅黑";
            }
            /* .repLogin{
                width:300px;
                height:100px;
                border:1px solid #ccc;
                box-shadow: 1px 1px 5px #ccc;
                border-radius:10px;
                text-align: center;
                vertical-align: middle;
                margin:0 auto;
            }
            
            .repLogin span{
                line-height:50px;
                display: block;
                text-align: center;
            }
            .repLogin button{
                width:70px;
                height:25px;
                background-color: #ccc ;
                border:0;
                box-shadow: 0;
                cursor: pointer;
                float: right;
                margin-right:30px;
                margin-top:10px;
                border-radius: 5px;
                background-color: #1b93d1;
                color:#fff;
            } */
        </style>
        <script type="text/javascript">
        
        $(function(){
           $('.alert_message').hide();
           $('html').click(function(event) {
               $('.alert_message').stop().slideDown();
           });
        });

        function toLogin(){
        	top.window.location.href='${ctx}/webCustomer/toLogin.do';
	        	if( typeof(window.opener)=='object' ){
	        	  window.opener.location.href=window.opener.location.href;
	        	  window.opener.location.reload();
	        	  window.close();
	        }
        }
        </script>
    </head>
    <body>
        <div class="alert_message">
            <span class="alert_message_span">登录超时，或未登录。请重新登录</span>
            <button class="alert_message_button" onclick="toLogin()">确定</button>
        </div>
        
    </body>
</html>