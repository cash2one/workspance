

<%@ page import="com.nala.csd.Hero" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'hero.label', default: 'Hero')}" />
        <title><g:message code="default.create.label" args="['客服人员']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['客服人员']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="['客服人员']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${heroInstance}">
            <div class="errors">
                <g:renderErrors bean="${heroInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">企业邮箱(nalashop.com结尾的那个)</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: heroInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${params?.email}" />
                                    <input type="hidden" name="username" value="" id="username"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="hero.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: heroInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${params?.name}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="role">分组</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:select name="role.id" from="${com.nala.csd.Role.list()*.name}" keys="${com.nala.csd.Role.list()*.id}" value="" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password">密码</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:textField name="password1" value="${params.password1}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password">确认密码</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:textField name="password2" value="${params.password2}" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
