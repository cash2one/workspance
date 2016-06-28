

<%@ page import="com.nala.csd.ChajianCode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chajianCode.label', default: 'ChajianCode')}" />
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
            <g:hasErrors bean="${chajianCodeInstance}">
            <div class="errors">
                <g:renderErrors bean="${chajianCodeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${chajianCodeInstance?.id}" />
                <g:hiddenField name="version" value="${chajianCodeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="chajianCode.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianCodeInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${chajianCodeInstance?.name}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">使用对象</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianCodeInstance, field: 'codeForTable', 'errors')}">
                                    <g:select name="codeForTable" from="${com.nala.csd.CodeForTableEnum?.values()*.getDescription()}" keys="${com.nala.csd.CodeForTableEnum?.values()*.name()}" value="${chajianCodeInstance?.codeForTable?.name() }" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
