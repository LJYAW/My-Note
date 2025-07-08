<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>指标采集命令详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet"
          type="text/css"/>
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js"
            type="text/javascript" charset="utf-8"></script>
    <script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script type="text/javascript">

        $(function () {
            var parseMode = $("#parseMode").val();
            if (parseMode == 1) {
                $("#parseMode").val("正则表达式");
                $(".regex").show();
                $(".targetKey").show();
                $(".stringMatch").hide();
                $(".stringSplit").hide();
                $(".template").hide();
            } else if (parseMode == 2) {
                $("#parseMode").val("字符串匹配");
                $(".regex").hide();
                $(".targetKey").show();
                $(".stringMatch").show();
                $(".stringSplit").hide();
                $(".template").hide();
            } else if (parseMode == 3) {
                $("#parseMode").val("字符分割");
                $(".regex").hide();
                $(".targetKey").show();
                $(".stringMatch").hide();
                $(".stringSplit").show();
                $(".template").hide();
            }else if (parseMode == 4) {
                $("#parseMode").val("模版解析");
                $(".template").show();
                $(".targetKey").hide();
                $(".regex").hide();
                $(".stringMatch").hide();
                $(".stringSplit").hide();
            }
            $("#formSave input").filter(".ip1").ligerTextBox({width: 200});
            $("input").filter(".ip2").ligerTextBox({width: 553});
        });


    </script>

</head>
<body>
<form:form id="formSave" action="" method="post">

    <table align="center" class="tb_edit">
        <tr>
            <td class="td_w1"></td>
            <td class="td_w2"></td>
            <td class="td_w3"></td>
            <td class="td_w1"></td>
            <td class="td_w2"></td>
            <td class="td_w3"></td>
        </tr>

        </tr>

        <td align="right">厂商名称：</td>

        <td><input id="vendorId" name="vendorId" type="text" value="${cmdbVendor.dispName}" class=" ip1"
                   readonly="readonly"/></td>
        <td></td>
        <td align="right">产品分类 ：</td>

        <td><input id="devClassName" name="devClassName" value="${prodChkCmdItems.devClassName}" type="text"
                   ltype="text" class=" ip1" readonly="readonly"/></td>
        <td></td>
        </tr>
        <tr>
            <td align="right">产品类型：</td>
            <td><input id="devTypeName" name="devTypeName" value="${prodChkCmdItems.devTypeName}" type="text"
                       ltype="text" class=" ip1" readonly="readonly"/></td>
            <td></td>
            <td align="right">产品型号：</td>
            <td><input id="prodModel" name="prodModel" value="${prodChkCmdItems.prodModel}" type="text" ltype="text"
                       class=" ip1" readonly="readonly"/></td>
            <td></td>
        </tr>


        <tr>
            <td align="right">操作系统类型：</td>
            <td><input id="osType" name="osType" value="${prodChkCmdItems.osType}" type="text" ltype="text" class=" ip1"
                       readonly="readonly"/></td>
            <td></td>
            <td align="right">软件版本：</td>
            <td><input id="osVersion" name="osVersion" value="${prodChkCmdItems.osVersion}" type="text" ltype="text"
                       class=" ip1" readonly="readonly"/></td>
            <td></td>
        </tr>
        <tr>
            <td align="right">监测对象：</td>
            <td>
                <input id="indClassName" name="indClassName" value="${prodChkCmdItems.indClassName}" type="text"
                       ltype="text" class=" ip1" readonly="readonly"/>
            </td>
            <td></td>

            <td align="right">指标组：</td>
            <td>
                <input id="indGroupName" name="indGroupName" value="${prodChkCmdItems.indGroupName}" type="text"
                       ltype="text" class=" ip1" readonly="readonly"/>
            </td>
            <td></td>
        </tr>

        <tr>
            <td align="right">指标项：</td>
            <td>
                <input id="indItemEnName" name="indItemEnName" value="${prodChkCmdItems.indItemEnName}" type="text"
                       ltype="text" class=" ip1" readonly="readonly"/>
            </td>
            <td></td>
            <td align="right">指标名称：</td>
            <td><input id="indItemName" name="indItemName" value="${prodChkCmdItems.indItemName}" type="text"
                       ltype="text" class="ip1" readonly="readonly"/></td>
            <td></td>
        </tr>

        <tr>
            <td align="right">取值类型：</td>
            <td><input id="valueType" name="valueType" value="${prodChkCmdItems.valueType}" type="text" ltype="text"
                       class=" ip1" readonly="readonly"/></td>
            <td></td>

            <td align="right">度量单位：</td>
            <td><input id="measureUnit" name="measureUnit" value="${prodChkCmdItems.measureUnit}" type="text"
                       ltype="text" class="ip1" readonly="readonly"/></td>
            <td></td>
        </tr>

        <tr>
            <td align="right">取值长度：</td>
            <td><input id="length" name="length" value="${prodChkCmdItems.length}" type="text" ltype="text" class="ip1"
                       readonly="readonly"/></td>
            <td></td>

            <td align="right">小数位数：</td>
            <td><input id="decimals" name="decimals" value="${prodChkCmdItems.decimals}" type="text" ltype="text"
                       class="ip1" readonly="readonly"/></td>
            <td></td>
        </tr>
        <tr>
            <td align="right">指标说明：</td>
            <td colspan="4"><input id="remark" name="remark" value="${prodChkCmdItems.remark}" type="text" ltype="text"
                                   class="ip2" readonly="readonly"/></td>
            <td></td>
        </tr>
        <tr>
            <td align="right">采集命令：</td>
            <td colspan="4">
                <input id="checkCmd" name="checkCmd" value="${prodChkCmdItems.checkCmd}" type="text" ltype="text"
                       class="ip2" readonly="readonly"/>
            </td>
            <td></td>
        </tr>

        <tr>
            <td align="right">结果样本：</td>
            <td colspan="4">
                <textarea style="width:553px;height:150px;" id="resultSample" name="resultSample"
                          readonly="readonly">${prodChkCmdItems.resultSample}</textarea>
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="right" nowrap>指标解析方式：</td>
            <td>
                <input id="parseMode" name="parseMode" value="${prodChkCmdItems.parseMode}" type="text" ltype="text"
                       class=" ip1" readonly="readonly"/>
            </td>
            <td></td>
            <td class="targetKey" align="right">目标行关键字：</td>
            <td class="targetKey">
                <input id="resultKeyWords" name="resultKeyWords" value="${prodChkCmdItems.resultKeyWords}" type="text" ltype="text"
                       class="ip1"/>
            </td>
            <td></td>
        </tr>
        <tr class="template">
            <td align="right">空行分隔符：</td>
            <td>
                <input id="nullLineDelimiter" name="blankLineDelimiter" value="${prodChkCmdItems.blankLineDelimiter}" type="text" readonly class="ip1"/>
            </td>
            <td></td>
            <td align="right">行分隔符：</td>
            <td><input id="lineDelimiter" name="lineDelimiter" value="${prodChkCmdItems.lineDelimiter}" type="text" readonly ltype="text" class="ip1"/></td>
            <td></td>
        </tr>
        <tr class="template">
            <td align="right">字段分隔符：</td>
            <td>
                <input id="fileDelimiter" name="fileDelimiter" value="${prodChkCmdItems.fileDelimiter}" type="text" ltype="text" readonly class="ip1"/>
            </td>
            <td></td>
            <td align="right">key/value分隔符：</td>
            <td><input id="kvDelimiter" name="kvDelimiter" value="${prodChkCmdItems.kvDelimiter}" type="text" ltype="text" readonly class="ip1"/></td>
            <td></td>
        </tr>
        <tr class="regex">
            <td align="right">正则解析规则：</td>
            <td colspan="4" class="regex">
                <input id="regEx" name="regEx" value="${prodChkCmdItems.regEx}" type="text" ltype="text" readonly class="ip1"/>
            </td>
            <td></td>
        </tr>
        <tr class="regex">
            <td align="right">group序列号：</td>
            <td>
                <input id="regExGroupNum" name="regExGroupNum" value="${prodChkCmdItems.regExGroupNum}" type="text" readonly class="ip1"/>
            </td>
            <td></td>
        </tr>
        <tr class="stringMatch">
            <td align="right">开始关键字：</td>
            <td><input id="startKeyWords" name="startKeyWords" value="${prodChkCmdItems.startKeyWords}" type="text" readonly ltype="text" class="ip1"/></td>
            <td></td>

            <td align="right">结束关键字：</td>
            <td><input id="endKeyWords" name="endKeyWords" value="${prodChkCmdItems.endKeyWords}" type="text" readonly ltype="text" class="ip1"/></td>
            <td></td>
        </tr>
        <tr class="stringSplit">
            <td align="right">分隔符：</td>
            <td><input id="delimiter" name="delimiter" value="${prodChkCmdItems.delimiter}" type="text" readonly ltype="text" class="ip1"/></td>
            <td></td>

            <td align="right">取值索引号：</td>
            <td><input id="valueIndex" name="valueIndex" value="${prodChkCmdItems.valueIndex}" type="text" readonly ltype="text" class="ip1"/></td>
            <td></td>
        </tr>
    </table>
</form:form>
</body>
</html>

