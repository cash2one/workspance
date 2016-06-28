
<%@ page import="com.nala.csd.UserOperate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userOperate.label', default: 'UserOperate')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            %{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>--}%
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.id.label" default="字段号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userOperateInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.hero.label" default="登陆名" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${userOperateInstance?.hero?.id}">${userOperateInstance?.hero?.name}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.clientIp.label" default="IP地址" /></td>
                            
                            <td valign="top" class="value">${userOperateInstance?.clientIp}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.loginTime.label" default="登陆时间" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${userOperateInstance?.loginTime}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.date.label" default="操作时间" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${userOperateInstance?.date}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.tabName.label" default="表名称" /></td>
                            
                            <td valign="top" class="value">${userOperateInstance?.tabName?.description}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.tId.label" default="字段号" /></td>
                            
                            <td valign="top" class="value">${userOperateInstance?.tId}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userOperate.memo.label" default="操作类型" /></td>
                            
                            <td valign="top" class="value">${userOperateInstance?.memo?.description}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${userOperateInstance?.id}" />
                    %{--<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>--}%
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
