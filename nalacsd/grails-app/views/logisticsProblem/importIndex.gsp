
<%@ page import="com.nala.csd.LogisticsProblem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logisticsProblem.label', default: 'LogisticsProblem')}" />
        <title><g:message code="default.list.label" args="['快递问题件']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['快递问题件']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="importIndex">导入快递问题件</g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
	        <g:form action="importExcel" method="post" enctype="multipart/form-data">
	            <h1>1.选择快递公司</h1><br />            	
	            	<g:select name="express.id" from="${com.nala.csd.Express.list()*.name}" keys="${com.nala.csd.Express.list()*.id}" noSelection="['':'']"  />
	            <h1>2.选择文件（要求Excel格式的问题快递数据）</h1><br />
						<input type="file" name="excel" />
						<input type="submit" />
	            </div>
            </g:form>
        </div>
    </body>
</html>
