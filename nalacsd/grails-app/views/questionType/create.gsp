

<%@ page import="com.nala.csd.QuestionType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'questionType.label', default: 'QuestionType')}" />
        <title><g:message code="default.create.label" args="['问题类型']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">首页</a></span>
            <span class="menuButton"><g:link class="list" action="list">问题类型列表</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="['问题类型']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${questionTypeInstance}">
            <div class="errors">
                <g:renderErrors bean="${questionTypeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="questionDescription"><g:message code="questionType.questionDescription.label" default="QD escription" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionTypeInstance, field: 'questionDescription', 'errors')}">
                                    <g:textField name="questionDescription" value="${questionTypeInstance?.questionDescription}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value"><g:message code="questionType.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionTypeInstance, field: 'value', 'errors')}">
                                    <%--<g:textField name="value" value="${fieldValue(bean: questionTypeInstance, field: 'value')}" />
                                --%><input name="value" id="value" value="0" type="hidden" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="新建" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
