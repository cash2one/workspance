
<%@ page import="com.nala.csd.Hero" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'hero.label', default: 'Hero')}" />
        <title><g:message code="default.show.label" args="['客服人员']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['客服人员']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['客服人员']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="['客服人员']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hero.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: heroInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">邮箱</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: heroInstance, field: "email")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">姓名</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: heroInstance, field: "name")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">登录名</td>

                            <td valign="top" class="value">${fieldValue(bean: heroInstance, field: "username")}@nalashop.com</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">分组</td>

                            <td valign="top" class="value">${heroInstance.getAuthorities()*.name}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">锁定</td>

                            <td valign="top" class="value">
                                <g:if test="${heroInstance.accountLocked}">
                                    是
                                </g:if>
                                <g:else>
                                    否
                                </g:else>
                            </td>

                        </tr>

                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name">过期</td>--}%

                            %{--<td valign="top" class="value"><g:if test="${heroInstance.accountExpired}">是</g:if><g:else>否</g:else></td>--}%

                        %{--</tr>--}%

                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name">激活</td>--}%

                            %{--<td valign="top" class="value"><g:if test="${heroInstance.enabled}">是</g:if><g:else>否</g:else></td>--}%

                        %{--</tr>--}%

                        <tr class="prop">
                            <td valign="top" class="name">密码</td>

                            <td valign="top" class="value"><a href="/hero/changePwd">修改密码</a></td>

                        </tr>


                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${heroInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
