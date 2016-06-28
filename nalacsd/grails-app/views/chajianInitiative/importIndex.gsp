
<%@ page import="com.nala.csd.LogisticsProblem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logisticsProblem.label', default: 'LogisticsProblem')}" />
        <title><g:message code="default.list.label" args="['主动查件记录']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['主动查件记录']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="importIndex">导入主动查件记录</g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
	        <g:form action="importExcel" method="post" enctype="multipart/form-data">
                <h1>请选择文件（要求Excel格式的异常单数据）</h1><br />
                <h1>Excel单元格数据A~D排列如下：</h1><br />
                <h1 style="color:#ff0000">店铺__顾客ID__快递单号__问题</h1><br />
                <h1 style="color:#ff0000">注意：店铺名称必须和牛盾系统中的店铺名称一样(金冠店/NALA旗舰店/B2C/拍拍/UNQ/......)</h1><br />
                <h1 style="color:#ff0000">注意：问题内容为查件代码(补发/转发/......)</h1><br /><br />
                <input type="file" name="excel" />
                <input type="submit" />
                </div>
            </g:form>
        </div>
    </body>
</html>
