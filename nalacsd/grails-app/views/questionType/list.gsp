
<%@ page import="com.nala.csd.QuestionType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'questionType.label', default: 'QuestionType')}" />
        <title>问题类型列表</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">首页</a></span>
            <span class="menuButton"><g:link class="create" action="create">新建问题类型</g:link></span>
        </div>
        <div class="body">
            <h1>问题类型列表</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'questionType.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="questionDescription" title="${message(code: 'questionType.questionDescription.label', default: 'QD escription')}" />
                        
                            <%--<g:sortableColumn property="value" title="${message(code: 'questionType.value.label', default: 'Value')}" />
                        
                        --%></tr>
                    </thead>
                    <tbody>
                    <g:each in="${questionTypeInstanceList}" status="i" var="questionTypeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${questionTypeInstance.id}">${fieldValue(bean: questionTypeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: questionTypeInstance, field: "questionDescription")}</td>
                        
                            <%--<td>${fieldValue(bean: questionTypeInstance, field: "value")}</td>
                        
                        --%></tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${questionTypeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
