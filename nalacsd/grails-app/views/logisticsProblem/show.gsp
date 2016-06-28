
<%@ page import="com.nala.csd.LogisticsProblem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logisticsProblem.label', default: 'LogisticsProblem')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list" params="${params }"><g:message code="default.list.label" args="['快递问题件']" /></g:link></span>            
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "id")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.dateCreated.label" default="创建时间" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${logisticsProblemInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.store.label" default="店铺" /></td>
                            
                            <td valign="top" class="value"><g:link controller="store" action="show" id="${logisticsProblemInstance?.store?.id}">${logisticsProblemInstance?.store?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.userId.label" default="顾客ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "userId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.solveTime.label" default="解决时间" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "solveTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.remark.label" default="备注" /></td>
                            
                            <td valign="top" class="value"><textarea disabled="disabled" name="remark" rows="2" cols="20">${fieldValue(bean: logisticsProblemInstance, field: "remark")}</textarea></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.solveType.label" default="处理方式" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "solveType")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.solveCS.label" default="处理人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${logisticsProblemInstance?.solveCS?.id}">${logisticsProblemInstance?.solveCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.results.label" default="处理结果" /></td>
                            
                            <td valign="top" class="value"><textarea disabled="disabled" name="results" rows="2" cols="20">${fieldValue(bean: logisticsProblemInstance, field: "results")}</textarea></td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.mobile.label" default="手机号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "mobile")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">收件信息</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "receiveInfo")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">发货日期</td>

                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${logisticsProblemInstance?.sendGoodsDate}"/></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.notifyMode.label" default="通知方式" /></td>
                            
                            <td valign="top" class="value"><g:link controller="notifyMode" action="show" id="${logisticsProblemInstance?.notifyMode?.id}">${logisticsProblemInstance?.notifyMode?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.followUp.label" default="是否跟踪" /></td>
                            
                            <g:if test="${logisticsProblemInstance.followUp }"><td>是</td></g:if><g:else><td>否</td></g:else>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.followCS.label" default="跟踪人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${logisticsProblemInstance?.followCS?.id}">${logisticsProblemInstance?.followCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.followTime.label" default="跟踪时间" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "followTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.signUp.label" default="是否签收" /></td>
                            
                            <g:if test="${logisticsProblemInstance.signUp }"><td>是</td></g:if><g:else><td>否</td></g:else>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="logisticsProblem.jiejueJiange.label" default="解决间隔" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: logisticsProblemInstance, field: "jiejueJiange")}</td>
                            
                        </tr>
                        
                 		<tr class="prop">
                            <td valign="top" class="name">物流公司</td>
                            
                            <td valign="top" class="value"><g:link controller="express" action="show" id="${logisticsProblemInstance?.expressProblem?.express.id}">${logisticsProblemInstance?.expressProblem?.express?.name?.encodeAsHTML()}</g:link></td>
                 		
                         </tr>
                                                 
                         <tr class="prop">
                            <td valign="top" class="name">物流单号</td>
                            
                            <td valign="top" class="value">${logisticsProblemInstance.expressProblem.logisticsID}</td>
                         
                         </tr>
                         
                         <tr class="prop">
                            <td valign="top" class="name">问题类型</td>
                            
                            <td valign="top" class="value">${logisticsProblemInstance.expressProblem.problemType}</td>
                            
                         </tr>
                         
                         <tr class="prop">
                            <td valign="top" class="name">问题描述</td>
                            
                            <td valign="top" class="value"><textarea disabled="disabled" name="expressProblem_description" rows="2" cols="20">${logisticsProblemInstance.expressProblem.description}</textarea></td>
                            
                         </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${logisticsProblemInstance?.id}" />
					<g:hiddenField name="params_prv" value="${params}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
