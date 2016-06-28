

<%@ page import="com.nala.csd.UserOperate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userOperate.label', default: 'UserOperate')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userOperateInstance}">
            <div class="errors">
                <g:renderErrors bean="${userOperateInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${userOperateInstance?.id}" />
                <g:hiddenField name="version" value="${userOperateInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hero"><g:message code="userOperate.hero.label" default="Hero" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userOperateInstance, field: 'hero', 'errors')}">
                                    <g:select name="hero.id" from="${com.nala.csd.Hero.list()}" optionKey="id" value="${userOperateInstance?.hero?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clientIp"><g:message code="userOperate.clientIp.label" default="Client Ip" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userOperateInstance, field: 'clientIp', 'errors')}">
                                    <g:textField name="clientIp" value="${userOperateInstance?.clientIp}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="loginTime"><g:message code="userOperate.loginTime.label" default="Login Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userOperateInstance, field: 'loginTime', 'errors')}">
                                    <g:datePicker name="loginTime" precision="day" value="${userOperateInstance?.loginTime}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="date"><g:message code="userOperate.date.label" default="Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userOperateInstance, field: 'date', 'errors')}">
                                    <g:datePicker name="date" precision="day" value="${userOperateInstance?.date}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tabName"><g:message code="userOperate.tabName.label" default="Tab Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userOperateInstance, field: 'tabName', 'errors')}">
                                    <g:select name="tabName" from="${com.nala.csd.TabEnum?.values()}" keys="${com.nala.csd.TabEnum?.values()*.name()}" value="${userOperateInstance?.tabName?.name()}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tId"><g:message code="userOperate.tId.label" default="TI d" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userOperateInstance, field: 'tId', 'errors')}">
                                    <g:textField name="tId" value="${userOperateInstance?.tId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="memo"><g:message code="userOperate.memo.label" default="Memo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userOperateInstance, field: 'memo', 'errors')}">
                                    <g:select name="memo" from="${com.nala.csd.MemoEnum?.values()}" keys="${com.nala.csd.MemoEnum?.values()*.name()}" value="${userOperateInstance?.memo?.name()}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
