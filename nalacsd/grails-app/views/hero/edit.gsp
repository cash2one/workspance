

<%@ page import="com.nala.csd.Hero" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'hero.label', default: 'Hero')}" />
        <title><g:message code="default.edit.label" args="['客服人员']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['客服人员']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['客服人员']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="['客服人员']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${heroInstance}">
            <div class="errors">
                <g:renderErrors bean="${heroInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${heroInstance?.id}" />
                <g:hiddenField name="version" value="${heroInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">邮箱</td>

                            <td valign="top" class="value">${fieldValue(bean: heroInstance, field: "email")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">姓名</td>

                            <td valign="top" class="value">${fieldValue(bean: heroInstance, field: "name")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="role">分组</label>
                            </td>
                            <td valign="top" class="value">
                                <g:select name="role.id" from="${com.nala.csd.Role.list()*.name}" keys="${com.nala.csd.Role.list()*.id}" value="${heroInstance.getFirstAuthorities().id}" />
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="password">密码</label>
                            </td>
                            <td valign="top" class="value">
                                <input type="password" name="password1" value="${params.password1}" />
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="password">确认密码</label>
                            </td>
                            <td valign="top" class="value">
                                <input type="password" name="password2" value="${params.password2}" />
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">锁定</td>
                            <td valign="top" class="value">
                                   <g:checkBox name="accountLocked" value="${heroInstance?.accountLocked}" />
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
