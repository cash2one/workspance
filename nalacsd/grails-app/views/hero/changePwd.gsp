<%@ page import="com.nala.csd.Hero" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>修改密码</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['客服人员']" /></g:link></span>
        </div>
        <div class="body">
            <h1>修改密码</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${heroInstance}">
            <div class="errors">
                <g:renderErrors bean="${heroInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form controller="hero" action="changePasswd" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password">新密码</label>
                                </td>
                                <td valign="top" class="value">
                                    <input type="password" name="password1" value="${params.password1}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password">确认新密码</label>
                                </td>
                                <td valign="top" class="value">
                                    <input type="password" name="password2" value="${params.password2}" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="修改" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
