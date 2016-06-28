

<%@ page import="com.nala.csd.ErrorTrade" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'errorTrade.label', default: 'ErrorTrade')}" />
        <title><g:if test="${tp}"><g:message code="default.list.label" args="['TP店异常单记录列表']" /></g:if><g:else><g:message code="default.list.label" args="['异常单记录列表']" /></g:else></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton">
                <g:if test="${tp}">
                    <g:link class="list" action="list" params="[storeType:'true']"><g:message code="default.list.label" args="['TP店异常单记录列表']" /></g:link></span>
                </g:if >
                <g:else>
                    <g:link class="list" action="list" params="[storeType:'false']"><g:message code="default.list.label" args="['异常单记录列表']" /></g:link></span>
                </g:else>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="['异常单']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${errorTradeInstance}">
            <div class="errors">
                <g:renderErrors bean="${errorTradeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="store"><g:message code="errorTrade.store.label" default="店铺" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'store', 'errors')}">
                                     <g:select name="store.id" from="${stores*.name}" keys="${stores*.id}" value="${errorTradeInstance?.store?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userId"><g:message code="errorTrade.userId.label" default="顾客ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'userId', 'errors')}">
                                    <g:textField name="userId" value="${errorTradeInstance?.userId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tradeId"><g:message code="errorTrade.tradeId.label" default="订单编号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'tradeId', 'errors')}">
                                    <g:textField name="tradeId" value="${errorTradeInstance?.tradeId}" />
                                </td>
                            </tr>


                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tradeId"><g:message code="errorTrade.tradeId.label" default="处理优先级" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'dealPriority', 'errors')}">
                                    <g:select name="dealPriority" from="${com.nala.csd.DealPriority.list()*.name}" keys="${com.nala.csd.DealPriority.list()*.id}" value="${errorTradeInstance?.dealPriority?.id}"/>
                                </td>
                            </tr>


                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="errorReason"><g:message code="errorTrade.errorReason.label" default="异常原因" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'errorReason', 'errors')}">
                                    <g:select name="errorReason.id" from="${com.nala.csd.ErrorReasonForTrade.list()*.name}" keys="${com.nala.csd.ErrorReasonForTrade.list()*.id}" value="${errorTradeInstance?.errorReason?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        	
                        	<tr class="prop">
	                            <td valign="top" class="name">----------</td>
    	                        <td valign="top" class="value">-------------------------------------------------------------------</td>
	                        </tr>
	                    	<tr class="prop">
	                            <td valign="top" class="name">新增商品</td>
	                            <td valign="top" class="value">请先创建异常单，然后在异常单编辑页面新增商品。</td>
	                        </tr>	                    	
	                        <tr class="prop">
	                            <td valign="top" class="name">----------</td>
	                            <td valign="top" class="value">-------------------------------------------------------------------</td>
	                        </tr>
                        	
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="buyerRemarks"><g:message code="errorTrade.buyerRemarks.label" default="买家备注" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'buyerRemarks', 'errors')}">
                                    <g:textField name="buyerRemarks" value="${errorTradeInstance?.buyerRemarks}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sellerRemarks"><g:message code="errorTrade.sellerRemarks.label" default="卖家备注" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'sellerRemarks', 'errors')}">
                                    <g:textField name="sellerRemarks" value="${errorTradeInstance?.sellerRemarks}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content"><g:message code="errorTrade.content.label" default="异常描述" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'content', 'errors')}">
                                    <textarea name="content" rows="2" cols="20">${errorTradeInstance?.content}</textarea>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="createCS"><g:message code="errorTrade.createCS.label" default="发起人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'createCS', 'errors')}">
                                    <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${errorTradeInstance?.createCS?.id}" noSelection="['': '']" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="contactTimes"><g:message code="errorTrade.contactTimes.label" default="联系次数" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'contactTimes', 'errors')}">
                                    <g:select name="contactTimes" from="${com.nala.csd.ContactTimesEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactTimesEnum?.values()*.name()}" value="${errorTradeInstance?.contactTimes?.name() }" noSelection="['':'']"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="solveCS"><g:message code="errorTrade.solveCS.label" default="处理人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'solveCS', 'errors')}">
                                    <g:select name="solveCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" value="${errorTradeInstance?.solveCS?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="solveStatus"><g:message code="errorTrade.solveStatus.label" default="完成进度" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'solveStatus', 'errors')}">
                                    <g:select name="solveStatus" from="${com.nala.csd.SolveStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveStatusEnum?.values()*.name()}" value="${errorTradeInstance?.solveStatus?.name() }" noSelection="['':'']"  />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="solveType">处理方式</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'solveType', 'errors')}">
                                    <g:select name="solveType.id" from="${com.nala.csd.SolveType.list()*.name}" keys="${com.nala.csd.SolveType.list()*.id}" value="${errorTradeInstance?.solveType?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="receiver">收货人</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'receiver', 'errors')}">
                                    <g:textField name="receiver" value="${errorTradeInstance?.receiver}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="address">收货地址</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'address', 'errors')}">                                    
                                    <input type="text" id="address"	name="address" value="${errorTradeInstance?.address }" size=100 />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="phone">电话</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: errorTradeInstance, field: 'phone', 'errors')}">
                                    <g:textField name="phone" value="${errorTradeInstance?.phone}" />
                                </td>
                            </tr>
                            
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <input type="hidden" name="storeType" value="${tp}">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
