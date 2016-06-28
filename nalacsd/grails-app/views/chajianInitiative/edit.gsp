

<%@ page import="com.nala.csd.ChajianInitiative" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chajianInitiative.label', default: 'ChajianInitiative')}" />
        <title><g:message code="default.edit.label" args="['主动查件记录']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['主动查件记录']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['主动查件记录']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="['主动查件记录']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${chajianInitiativeInstance}">
            <div class="errors">
                <g:renderErrors bean="${chajianInitiativeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${chajianInitiativeInstance?.id}" />
                <g:hiddenField name="version" value="${chajianInitiativeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="store"><g:message code="chajianInitiative.store.label" default="店铺" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'store', 'errors')}">
                                    <g:select name="store.id" from="${com.nala.csd.Store.list()*.name}" keys="${com.nala.csd.Store.list()*.id}" value="${chajianInitiativeInstance?.store?.id}"  />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userId"><g:message code="chajianInitiative.userId.label" default="用户ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'userId', 'errors')}">
                                    <g:textField name="userId" value="${chajianInitiativeInstance?.userId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="chajianCode"><g:message code="chajianInitiative.chajianCode.label" default="查件代码" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'chajianCode', 'errors')}">
                                    <g:select name="chajianCode.id" from="${chajianCodeList*.name}" keys="${chajianCodeList*.id}" value="${chajianInitiativeInstance?.chajianCode?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="logistics"><g:message code="chajianInitiative.logistics.label" default="物流单号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'logistics', 'errors')}">
                                    <g:textField name="logistics" value="${chajianInitiativeInstance?.logistics}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mobile"><g:message code="chajianInitiative.mobile.label" default="手机号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'mobile', 'errors')}">
                                    <g:textField name="mobile" value="${chajianInitiativeInstance?.mobile}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="solveType"><g:message code="chajianInitiative.solveType.label" default="处理方式" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'solveType', 'errors')}">
                                    <g:textField name="solveType" value="${chajianInitiativeInstance?.solveType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="solveCS"><g:message code="chajianInitiative.solveCS.label" default="处理人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'solveCS', 'errors')}">
                                    <g:select name="solveCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${chajianInitiativeInstance?.solveCS?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="followCS"><g:message code="chajianInitiative.followCS.label" default="跟踪人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'followCS', 'errors')}">
                                    <g:select name="followCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" value="${chajianInitiativeInstance?.followCS?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="notifyMode"><g:message code="chajianInitiative.notifyMode.label" default="通知方式" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'notifyMode', 'errors')}">
                                    <g:select name="notifyMode.id" from="${com.nala.csd.NotifyMode.list()*.name}" keys="${com.nala.csd.NotifyMode.list()*.id}" value="${chajianInitiativeInstance?.notifyMode?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="followUp"><g:message code="chajianInitiative.followUp.label" default="是否跟踪" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'followUp', 'errors')}">
                                    <g:checkBox name="followUp" value="${chajianInitiativeInstance?.followUp}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="signUp"><g:message code="chajianInitiative.signUp.label" default="是否签收" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianInitiativeInstance, field: 'signUp', 'errors')}">
                                    <g:checkBox name="signUp" value="${chajianInitiativeInstance?.signUp}" />
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
