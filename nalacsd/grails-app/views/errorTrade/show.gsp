
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
            <g:if test="${tp}">
                <g:link action="create" params="[storeType:'true']"> <span><g:message code="default.new.label" args="['TP店异常单记录列表']" /></span> </g:link>
            </g:if>
            <g:else>
                <g:link action="create" params="[storeType:'false']"></span><g:message code="default.new.label" args="['异常单记录列表']" /></span></g:link>
            </g:else>
        %{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['异常单']" /></g:link></span>--}%
        <span class="menuButton">
            <g:if test="${tp}">
                <g:link class="create" action="importIndex" params="[storeType:'true']">导入TP店异常单</g:link></span>
            </g:if >
            <g:else>
                <g:link class="create" action="importIndex"  params="[storeType:'false']">导入异常单</g:link></span>
            </g:else>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="['异常单']" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.dateCreated.label" default="登记日期" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${errorTradeInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.store.label" default="店铺" /></td>
                            
                            <td valign="top" class="value"><g:link controller="store" action="show" id="${errorTradeInstance?.store?.id}">${errorTradeInstance?.store?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.userId.label" default="顾客ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "userId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.tradeId.label" default="订单编号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "tradeId")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.tradeId.label" default="处理优先级" /></td>

                            <td valign="top" class="value">${errorTradeInstance?.dealPriority?.name}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.reason.label" default="异常原因" /></td>
                            
                            <td valign="top" class="value"><g:link controller="errorReasonForTrade" action="show" id="${errorTradeInstance?.errorReason?.id}">${errorTradeInstance?.errorReason?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    	
                    	<tr class="prop">
                            <td valign="top" class="name">----------</td>
                            <td valign="top" class="value">---------------------------------------------------------------------</td>
                        </tr>
                    	<g:each in="${errorTradeInstance.items }" var="item">
                    	<tr class="prop">
                            <td valign="top" class="name">商品名称</td>
                            <td valign="top" class="value">${item.name}</td>
                        </tr>
                    	<tr class="prop">
                            <td valign="top" class="name">商品SKU</td>
                            <td valign="top" class="value">${item.sku}</td>
                        </tr>
                    	<tr class="prop">
                            <td valign="top" class="name">规格编码</td>
                            <td valign="top" class="value">${item.code}</td>
                        </tr>
                    	<tr class="prop">
                            <td valign="top" class="name">购买数量</td>
                            <td valign="top" class="value">${item.num}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">----------</td>
                            <td valign="top" class="value">---------------------------------------------------------------------</td>
                        </tr>
                    	</g:each>
                    	
						<tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.buyerRemarks.label" default="买家备注" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "buyerRemarks")}</td>
                            
                        </tr>
                                            
						<tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.sellerRemarks.label" default="卖家备注" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "sellerRemarks")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.content.label" default="异常描述" /></td>
                            
                            <td valign="top" class="value"><textarea disabled="disabled" name="content" rows="2" cols="20">${errorTradeInstance?.content}</textarea></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.createCS.label" default="发起人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${errorTradeInstance?.createCS?.id}">${errorTradeInstance?.createCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.contactTimes.label" default="联系次数" /></td>
                            
                            <td valign="top" class="value">${errorTradeInstance?.contactTimes?.getDescription()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.solveCS.label" default="处理人" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${errorTradeInstance?.solveCS?.id}">${errorTradeInstance?.solveCS?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.solveTime.label" default="解决时间" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "solveTime")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.solveTime.label" default="解决间隔" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "jiejueJiange")} 分钟</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="errorTrade.solveStatus.label" default="完成进度" /></td>
                            
                            <td valign="top" class="value">${errorTradeInstance?.solveStatus?.getDescription()}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">处理方式</td>
                            
                            <td valign="top" class="value"><g:link controller="solveType" action="show" id="${errorTradeInstance?.solveType?.id}">${errorTradeInstance?.solveType?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">收货人</td>                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "receiver")}</td>                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">收货地址</td>                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "address")}</td>                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">电话</td>                            
                            <td valign="top" class="value">${fieldValue(bean: errorTradeInstance, field: "phone")}</td>                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${errorTradeInstance?.id}" />
                    <g:hiddenField name="params_prv" value="${params}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
