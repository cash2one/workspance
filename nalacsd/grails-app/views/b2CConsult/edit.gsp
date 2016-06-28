

<%@ page import="com.nala.csd.B2CConsult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'b2CConsult.label', default: 'B2CConsult')}" />
        <title><g:message code="default.edit.label" args="['B2C顾客咨询登记']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['B2C顾客咨询登记']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['B2C顾客咨询登记']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="['B2C顾客咨询登记']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${b2CConsultInstance}">
            <div class="errors">
                <g:renderErrors bean="${b2CConsultInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${b2CConsultInstance?.id}" />
                <g:hiddenField name="version" value="${b2CConsultInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="createCS"><g:message code="b2CConsult.createCS.label" default="发起人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'createCS', 'errors')}">
                                    %{--<g:select name="createCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" optionKey="id" value="${b2CConsultInstance?.createCS?.id}"  />--}%
                                    <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" optionKey="id" value="${b2CConsultInstance?.createCS?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="questionSource"><g:message code="b2CConsult.questionSource.label" default="咨询方式" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'questionSource', 'errors')}">
                                    <g:select name="questionSource.id" from="${com.nala.csd.QuestionSource.list()*.name}" keys="${com.nala.csd.QuestionSource.list()*.id}" optionKey="id" value="${b2CConsultInstance?.questionSource?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userId"><g:message code="b2CConsult.userId.label" default="会员ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'userId', 'errors')}">
                                    <g:textField name="userId" value="${b2CConsultInstance?.userId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="phone"><g:message code="b2CConsult.phone.label" default="电话" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'phone', 'errors')}">
                                    <g:textField name="phone" value="${b2CConsultInstance?.phone}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="b2CConsult.name.label" default="姓名" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${b2CConsultInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="questionStatus"><g:message code="b2CConsult.questionStatus.label" default="咨询类型" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'questionStatus', 'errors')}">
                                    <g:select name="questionStatus.id" from="${com.nala.csd.QuestionType.list()*.questionDescription}" keys="${com.nala.csd.QuestionType.list()*.id}" optionKey="id" value="${b2CConsultInstance?.questionStatus?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tradeId"><g:message code="b2CConsult.tradeId.label" default="订单编号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'tradeId', 'errors')}">
                                    <g:textField name="tradeId" value="${b2CConsultInstance?.tradeId}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="b2CConsultResult"><g:message code="b2CConsult.b2CConsultResult.label" default="处理结果" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'b2CConsultResult', 'errors')}">
                                    <g:select name="b2CConsultResult.id" from="${com.nala.csd.B2CConsultResult.list()*.name}" keys="${com.nala.csd.B2CConsultResult.list()*.id}" optionKey="id" value="${b2CConsultInstance?.b2CConsultResult?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="noSolveReason"><g:message code="b2CConsult.noSolveReason.label" default="未解决说明" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: b2CConsultInstance, field: 'noSolveReason', 'errors')}">
                                    <g:textField name="noSolveReason" value="${b2CConsultInstance?.noSolveReason}" />
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
