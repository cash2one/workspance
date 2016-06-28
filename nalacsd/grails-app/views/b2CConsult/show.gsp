
<%@ page import="com.nala.csd.B2CConsult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'b2CConsult.label', default: 'B2CConsult')}" />
        <title><g:message code="default.show.label" args="['B2C顾客咨询登记']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="['B2C顾客咨询登记']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['B2C顾客咨询登记']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="['B2C顾客咨询登记']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: b2CConsultInstance, field: "id")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.dateCreated.label" default="登记日期" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${b2CConsultInstance?.dateCreated}" /></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.createCS.label" default="发起人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${b2CConsultInstance?.createCS?.id}">${b2CConsultInstance?.createCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.questionSource.label" default="咨询方式" /></td>
                            
                            <td valign="top" class="value"><g:link controller="questionSource" action="show" id="${b2CConsultInstance?.questionSource?.id}">${b2CConsultInstance?.questionSource?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.userId.label" default="会员ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: b2CConsultInstance, field: "userId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.phone.label" default="电话" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: b2CConsultInstance, field: "phone")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.name.label" default="姓名" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: b2CConsultInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.questionStatus.label" default="咨询类型" /></td>
                            
                            <td valign="top" class="value"><g:link controller="questionType" action="show" id="${b2CConsultInstance?.questionStatus?.id}">${b2CConsultInstance?.questionStatus?.questionDescription}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.tradeId.label" default="订单编号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: b2CConsultInstance, field: "tradeId")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.b2CConsultResult.label" default="处理结果" /></td>
                            
                            <td valign="top" class="value"><g:link controller="b2CConsultResult" action="show" id="${b2CConsultInstance?.b2CConsultResult?.id}">${b2CConsultInstance?.b2CConsultResult?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="b2CConsult.noSolveReason.label" default="未解决说明" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: b2CConsultInstance, field: "noSolveReason")}</td>
                            
                        </tr>
                    
                        
                    
                        
                    
                        
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${b2CConsultInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
