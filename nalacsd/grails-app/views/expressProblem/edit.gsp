

<%@ page import="com.nala.csd.ExpressProblem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'expressProblem.label', default: 'ExpressProblem')}" />
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
            <g:hasErrors bean="${expressProblemInstance}">
            <div class="errors">
                <g:renderErrors bean="${expressProblemInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${expressProblemInstance?.id}" />
                <g:hiddenField name="version" value="${expressProblemInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="expressProblem.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expressProblemInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${expressProblemInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="express"><g:message code="expressProblem.express.label" default="Express" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expressProblemInstance, field: 'express', 'errors')}">
                                    <g:select name="express.id" from="${com.nala.csd.Express.list()}" optionKey="id" value="${expressProblemInstance?.express?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="logisticsID"><g:message code="expressProblem.logisticsID.label" default="Logistics ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expressProblemInstance, field: 'logisticsID', 'errors')}">
                                    <g:textField name="logisticsID" value="${expressProblemInstance?.logisticsID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="problemType"><g:message code="expressProblem.problemType.label" default="Problem Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expressProblemInstance, field: 'problemType', 'errors')}">
                                    <g:textField name="problemType" value="${expressProblemInstance?.problemType}" />
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
