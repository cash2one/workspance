
<%@ page import="com.nala.csd.QuestionType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'questionType.label', default: 'QuestionType')}" />
        <title><g:message code="default.show.label" args="['问题类型']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">首页</a></span>
            <span class="menuButton"><g:link class="list" action="list">问题类型列表</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">新建问题类型</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="['问题类型']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="questionType.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionTypeInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="questionType.questionDescription.label" default="QD escription" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionTypeInstance, field: "questionDescription")}</td>
                            
                        </tr>
                    
                        <%--<tr class="prop">
                            <td valign="top" class="name"><g:message code="questionType.value.label" default="Value" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionTypeInstance, field: "value")}</td>
                            
                        </tr>
                    
                    --%></tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${questionTypeInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="编辑" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
