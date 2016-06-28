
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
            <span class="menuButton">
                <g:if test="${tp}">
                    <g:link class="list" action="list" params="[storeType:'true']"><g:message code="default.list.label" args="['Tp查件被动表记录']" /></g:link></span>
                </g:if>
                <g:else>
                    <g:link class="list" action="list" params="[storeType:'false']"><g:message code="default.list.label" args="['查件被动表记录']" /></g:link></span>
                </g:else>
            <span class="menuButton">
                <g:if test="${tp}">
                    <g:link class="create" action="create"  params="[storeType:'true']"><g:message code="default.new.label" args="['TP查件被动表记录']" /></g:link></span>
                </g:if>
                <g:else>
                    <g:link class="create" action="create"  params="[storeType:'false']"><g:message code="default.new.label" args="['查件被动表记录']" /></g:link></span>
                </g:else>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="['查件被动表记录']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "id")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.createCS.label" default="创建人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${chajianPassiveInstance?.createCS?.id}">${chajianPassiveInstance?.createCS?.name.encodeAsHTML()}</g:link></td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.dateCreated.label" default="创建时间" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chajianPassiveInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                    	<tr class="prop">
                    	    <td valign="top" class="name"><g:message code="chajianPassive.store.label" default="店铺" /></td>
                            
                            <td valign="top" class="value"><g:link controller="store" action="show" id="${chajianPassiveInstance?.store?.id}">${chajianPassiveInstance?.store?.name?.encodeAsHTML()}</g:link></td>
                    	
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.userId.label" default="顾客ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "userId")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.tradeId.label" default="订单编号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "tradeId")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.contactStatus.label" default="联系优先级" /></td>
                            
                            <td valign="top" class="value">${chajianPassiveInstance?.contactStatus?.getDescription()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.userId.label" default="查件代码" /></td>
                            
                            <td valign="top" class="value"><g:link controller="chajianCode" action="show" id="${chajianPassiveInstance?.chajianCode?.id}">${chajianPassiveInstance?.chajianCode?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.logistics.label" default="物流单号" /></td>
                            
                            <td valign="top" class="value"><textarea disabled="disabled" name="logistics" rows="2" cols="20">${chajianPassiveInstance?.logistics}</textarea></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.results.label" default="查询备注" /></td>
                            
                            <td valign="top" class="value"><textarea disabled="disabled" name="results" rows="2" cols="20">${chajianPassiveInstance?.results}</textarea></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.solveCS.label" default="处理人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${chajianPassiveInstance?.solveCS?.id}">${chajianPassiveInstance?.solveCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.contactTime.label" default="联系时间" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "contactTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.solveTime.label" default="解决时间" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "solveTime")}</td>
                            
                        </tr>
                        
                                                <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.solveTime.label" default="跟踪时间" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "updateTime")}</td>
                            
                        </tr>
                        
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.mobile.label" default="手机号码" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "mobile")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.notifyMode.label" default="通知方式" /></td>
                            
                            <td valign="top" class="value"><g:link controller="notifyMode" action="show" id="${chajianPassiveInstance?.notifyMode?.id}">${chajianPassiveInstance?.notifyMode?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.followUp.label" default="是否跟踪" /></td>
                            
                            <g:if test="${chajianPassiveInstance.followUp }"><td>是</td></g:if><g:else><td>否</td></g:else>                            
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.followCS.label" default="跟踪人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${chajianPassiveInstance?.followCS?.id}">${chajianPassiveInstance?.followCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.signUp.label" default="是否签收" /></td>
                            
                            <g:if test="${chajianPassiveInstance.signUp }"><td>是</td></g:if><g:else><td>否</td></g:else>                            
                            
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.lianxiJiange.label" default="联系间隔" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "lianxiJiange")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chajianPassive.jiejueJiange.label" default="解决间隔" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chajianPassiveInstance, field: "jiejueJiange")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${chajianPassiveInstance?.id}" />
                    <g:hiddenField name="params_prv" value="${params}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
