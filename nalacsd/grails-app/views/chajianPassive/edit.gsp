

<%@ page import="com.nala.csd.ChajianPassive" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chajianPassive.label', default: 'ChajianPassive')}" />
        <title><g:if test="${tp}"><g:message code="default.list.label" args="['TP店查件被动表']" /></g:if><g:else><g:message code="default.list.label" args="['查件被动表']" /></g:else></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['查件被动表记录']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['查件被动表记录']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="['查件被动表记录']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${chajianPassiveInstance}">
            <div class="errors">
                <g:renderErrors bean="${chajianPassiveInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${chajianPassiveInstance?.id}" />
                <g:hiddenField name="version" value="${chajianPassiveInstance?.version}" />
                <g:hiddenField name="params_prv" value="${params_prv}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createCS"><g:message code="chajianPassive.createCS.label" default="创建人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'createCS', 'errors')}">
                                    <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${chajianPassiveInstance?.createCS?.id}"  />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="store"><g:message code="chajianPassive.store.label" default="店铺" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'store', 'errors')}">
                                    <g:select name="store.id" from="${stores*.name}" keys="${stores*.id}" value="${chajianPassiveInstance?.store?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userId"><g:message code="chajianPassive.userId.label" default="顾客ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'userId', 'errors')}">
                                    <g:textField name="userId" value="${chajianPassiveInstance?.userId}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tradeId"><g:message code="chajianPassive.tradeId.label" default="订单编号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'tradeId', 'errors')}">
                                    <g:textField name="tradeId" value="${chajianPassiveInstance?.tradeId}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contactStatus"><g:message code="chajianPassive.contactStatus.label" default="联系优先级" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'contactStatus', 'errors')}">
                                    <g:select name="contactStatus" from="${com.nala.csd.ContactStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactStatusEnum?.values()*.name()}" value="${chajianPassiveInstance?.contactStatus?.name()}" noSelection="['':'']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="chajianCode"><g:message code="chajianPassive.userId.label" default="查件代码" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'chajianCode', 'errors')}">
                                    <g:select name="chajianCode.id" from="${chajianCodeList*.name}" keys="${chajianCodeList*.id}" value="${chajianPassiveInstance?.chajianCode?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="logistics"><g:message code="chajianPassive.logistics.label" default="物流单号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'logistics', 'errors')}">
                                    <textarea name="logistics" rows="2" cols="20">${chajianPassiveInstance?.logistics}</textarea>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="results"><g:message code="chajianPassive.results.label" default="查询备注" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'results', 'errors')}">                                    
                                    <textarea name="results" rows="2" cols="20">${chajianPassiveInstance?.results}</textarea>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="solveCS"><g:message code="chajianPassive.solveCS.label" default="处理人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'solveCS', 'errors')}">
                                    <g:select name="solveCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" value="${chajianPassiveInstance?.solveCS?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mobile"><g:message code="chajianPassive.mobile.label" default="手机号码" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'mobile', 'errors')}">
                                    <g:textField name="mobile" value="${chajianPassiveInstance?.mobile}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="notifyMode"><g:message code="chajianPassive.notifyMode.label" default="通知方式" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'notifyMode', 'errors')}">
                                    <g:select name="notifyMode.id" from="${com.nala.csd.NotifyMode.list()*.name}" keys="${com.nala.csd.NotifyMode.list()*.id}" value="${chajianPassiveInstance?.notifyMode?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="followUp"><g:message code="chajianPassive.followUp.label" default="是否跟踪" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'followUp', 'errors')}">
                                    <g:checkBox name="followUp" value="${chajianPassiveInstance?.followUp}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="followCS"><g:message code="chajianPassive.followCS.label" default="跟踪人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'followCS', 'errors')}">
                                    <g:select name="followCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" value="${chajianPassiveInstance?.followCS?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="signUp"><g:message code="chajianPassive.signUp.label" default="是否签收" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chajianPassiveInstance, field: 'signUp', 'errors')}">
                                    <g:checkBox name="signUp" value="${chajianPassiveInstance?.signUp}" />
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
