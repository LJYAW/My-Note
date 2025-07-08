<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据库监测指标</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
</head>
<body> 
	<form:form id="formSave"  action="" method="post">
		
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr>
                <td align="right" nowrap>缓存线程数:</td>
                <td>
                	<input id="threadsCached" name="threadsCached" value="${indicator.threadsCached}" type="text" readonly="readonly"  class="ip1" />
                </td>
                <td></td>
                <td align="right"nowrap>当前开放的线程数:</td>
                <td>
                	<input id="threadsConnected" name="threadsConnected" value="${indicator.threadsConnected}" type="text" readonly="readonly"  class="ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>创建线程数:</td>
                <td>
                	<input id="threadsCreated" name="threadsCreated" value="${indicator.threadsCreated}" type="text" readonly="readonly"  class="ip1" />
                </td>
                <td></td>
                <td align="right"nowrap>当前运行线程数:</td>
                <td>
                	<input id="threadsRunning" name="threadsRunning" value="${indicator.threadsRunning}" readonly="readonly" class="ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>设置的最大连接数:</td>
                <td>
                	<input id="maxConnections" name="maxConnections" value="${indicator.maxConnections}" class="ip1" readonly="readonly" />
                </td>
                <td></td>
                <td align="right"nowrap>响应的连接数:</td>
                <td>
                	<input id="maxUsedConnections" name="maxUsedConnections" value="${indicator.maxUsedConnections}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>终止客户数:</td>
                <td>
                	<input id="abortedClients" name="abortedClients" value="${indicator.abortedClients}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right">每秒查询数量:</td>
                <td>
                	<input id="questions" name="questions" value="${indicator.questions}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right"nowrap>主键联合执行次数:</td>
                <td>
                	<input id="selectFullJoin" name="selectFullJoin" value="${indicator.selectFullJoin}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right"nowrap>执行全表搜索查询的数量:</td>
                <td>
                	<input id="selectScan" name="selectScan" value="${indicator.selectScan}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right"nowrap>提交的事务数量:</td>
                <td>
                	<input id="comCommit" name="comCommit" value="${indicator.comCommit}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right"nowrap>回滚的事务数量:</td>
                <td>
                	<input id="comRollback" name="comRollback" value="${indicator.comRollback}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right"nowrap>查询缓存的限制:</td>
                <td>
                	<input id="queryCacheLimit" name="queryCacheLimit" value="${indicator.queryCacheLimit}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right" nowrap>查询缓存块的最小的容量:</td>
                <td>
                	<input id="queryCacheMinResUnit" name="queryCacheMinResUnit" value="${indicator.queryCacheMinResUnit}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right"nowrap>查询缓存大小 :</td>
                <td>
                	<input id="queryCacheSize" name="queryCacheSize" value="${indicator.queryCacheSize}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right" nowrap>缓存类型:</td>
                <td>
                	<input id="queryCacheType" name="queryCacheType" value="${indicator.queryCacheType}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right"nowrap>查询缓存的界限:</td>
                <td>
                	<input id="queryCacheLimit" name="queryCacheLimit" value="${indicator.queryCacheLimit}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right" >MyISAM表进行写操作时,是否返回cache结果:</td>
                <td>
                	<input id="queryCacheWlockInvalidate" name="queryCacheWlockInvalidate" value="${indicator.queryCacheWlockInvalidate}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right"nowrap>表示立即释放表锁数:</td>
                <td>
                	<input id="tableLocksImmediate" name="tableLocksImmediate" value="${indicator.tableLocksImmediate}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right" nowrap>表示需要等待的表锁数:</td>
                <td>
                	<input id="tableLocksWaited" name="tableLocksWaited" value="${indicator.tableLocksWaited}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right"nowrap>行锁阻塞程度:</td>
                <td>
                	<input id="rowLock" name="rowLock" value="${indicator.rowLock}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right" nowrap>慢查询数量:</td>
                <td>
                	<input id="slowQueries" name="slowQueries" value="${indicator.slowQueries}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSave" type="button" value="关闭" class="l-button mg6" onclick="window.parent.saveOK();"/>
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

