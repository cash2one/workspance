
<%@ page import="com.nala.csd.ChajianInitiative" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chajianInitiative.label', default: 'ChajianInitiative')}" />
        <title><g:message code="default.show.label" args="['主动查件记录']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['主动查件记录']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['主动查件记录']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="['主动查件记录']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianInitiativeInstance, field: "id")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">登记日期</td>

                            <td valign="top" class="value">${fieldValue(bean: chajianInitiativeInstance, field: "dateCreated")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.store.label" default="店铺" /></td>

                            <td valign="top" class="value"><g:link controller="store" action="show" id="${chajianInitiativeInstance?.store?.id}">${chajianInitiativeInstance?.store?.name?.encodeAsHTML()}</g:link></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.userId.label" default="用户ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianInitiativeInstance, field: "userId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.chajianCode.label" default="查件代码" /></td>
                            
                            <td valign="top" class="value"><g:link controller="chajianCode" action="show" id="${chajianInitiativeInstance?.chajianCode?.id}">${chajianInitiativeInstance?.chajianCode?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.logistics.label" default="物流单号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianInitiativeInstance, field: "logistics")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.mobile.label" default="手机号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianInitiativeInstance, field: "mobile")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.solveType.label" default="处理方式" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianInitiativeInstance, field: "solveType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.solveCS.label" default="处理人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${chajianInitiativeInstance?.solveCS?.id}">${chajianInitiativeInstance?.solveCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.contactTime.label" default="联系时间" /></td>

                            <td valign="top" class="value"><g:formatDate date="${chajianInitiativeInstance?.contactTime}" /></td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.followCS.label" default="跟踪人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${chajianInitiativeInstance?.followCS?.id}">${chajianInitiativeInstance?.followCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.followTime.label" default="跟踪时间" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chajianInitiativeInstance?.followTime}" /></td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">创建时间到联系时间的间隔</td>

                            <td valign="top" class="value">${chajianInitiativeInstance.contactInterval}(分钟)</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">联系时间到跟踪时间的间隔</td>

                            <td valign="top" class="value">${chajianInitiativeInstance.followInterval}(分钟)</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.notifyMode.label" default="通知方式" /></td>
                            
                            <td valign="top" class="value"><g:link controller="notifyMode" action="show" id="${chajianInitiativeInstance?.notifyMode?.id}">${chajianInitiativeInstance?.notifyMode?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.followUp.label" default="是否跟踪" /></td>
                            
                            <td valign="top" class="value"><g:if test="${chajianInitiativeInstance.followUp}">是</g:if><g:else>否</g:else></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianInitiative.signUp.label" default="是否签收" /></td>
                            
                            <td valign="top" class="value"><g:if test="${chajianInitiativeInstance.signUp}">是</g:if><g:else>否</g:else></td>
                            
                        </tr>
                    

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${chajianInitiativeInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
