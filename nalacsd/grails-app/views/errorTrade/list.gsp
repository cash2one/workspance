
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
                    <g:link action="create" params="[storeType:'true']"> <g:message code="default.new.label" args="['TP店异常单记录列表']" /></span> </g:link>
                </g:if>
                <g:else>
                      <g:link action="create" params="[storeType:'false']"> <g:message code="default.new.label" args="['异常单记录列表']" /></span></g:link>
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
			<h1></h1>
            <fieldset>
				<legend>查找记录：</legend>
				<br />
				<g:form action="search" >

				店铺 <g:select name="store_id" from="${stores*.name}" value="${params?.store_id }" keys="${stores*.id}" noSelection="['':'']"  />
				顾客ID <input name="userId" value="${params?.userId }" >
				发起人 <g:select name="createCSId" from="${com.nala.csd.Hero.list()*.name}" value="${params?.createCSId }" keys="${com.nala.csd.Hero.list()*.id}" noSelection="['':'']"  />
				处理人 <g:select name="solveCSId" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" value="${params?.solveCSId }" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" noSelection="['':'']"  />
				订单编号 <g:textField name="tradeId" value="${params?.tradeId}" />
				%{--处理方式 <g:select name="solveType" value="${params.solveType }" from="${com.nala.csd.SolveTypeEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveTypeEnum?.values()*.getCode()}" noSelection="['':'']"  /><br />--}%
				处理方式 <g:select name="solveType" value="${params.solveType }" from="${com.nala.csd.SolveType.list()*.name}" keys="${com.nala.csd.SolveType.list()*.id}" noSelection="['':'']"  /><br />
				异常原因 <g:select name="errorReason_id" from="${com.nala.csd.ErrorReasonForTrade.list()*.name}" keys="${com.nala.csd.ErrorReasonForTrade.list()*.id}" value="${params?.errorReason_id}" noSelection="['': '']" />
				异常描述 <g:textField name="content" value="${params?.content}" />
				商品SKU <g:textField name="itemSKUs" value="${params?.itemSKUs}" />
				完成进度 <g:select name="solveStatus" value="${params?.solveStatus }" from="${com.nala.csd.SolveStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveStatusEnum?.values()*.getCode()}" noSelection="['':'']" /><br/>
				处理优先级 <g:select name="dealPriority" value="${params?.dealPriority }" from="${com.nala.csd.DealPriority.list()*.name}" keys="${com.nala.csd.DealPriority.list()*.id}" noSelection="['':'']" /><br/>
				登记时间 <input value="${params?.dateCreatedStart }" id="dateCreatedStart" name="dateCreatedStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params?.dateCreatedEnd }" id="dateCreatedEnd" name="dateCreatedEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
                <input type="hidden" name="tp" value="${tp}" />
                <input type="submit" value="&nbsp;▶&nbsp;搜索&nbsp;◀&nbsp;">
				</g:form><br/>
				<g:form method="post">
                   <input type="hidden" name="format" value="excel">
                    <input type="hidden" name="tp" value="${tp}" >
           			<input type="hidden" name="extension" value="xls">
           			<g:actionSubmit value="导出14天内数据" action="export" />
           		</g:form>
           	</fieldset>        
            <h1><g:message code="default.list.label" args="['异常单']" />  [数量: ${errorTradeInstanceTotal }] </h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="paginateButtons">
                <g:paginate total="${errorTradeInstanceTotal}" params="${params }" />
            </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'errorTrade.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'errorTrade.dateCreated.label', default: '登记日期')}" />
                        
                            <th><g:message code="errorTrade.store.label" default="店铺" /></th>
                        
                            <g:sortableColumn property="userId" title="${message(code: 'errorTrade.userId.label', default: '顾客ID')}" />
                            
                            <g:sortableColumn property="buyerRemarks" title="买家留言" />
                            
                            <g:sortableColumn property="sellerRemarks" title="卖家留言" />
                        
                            <g:sortableColumn property="errorReason" title="${message(code: 'errorTrade.errorReason.label', default: '异常原因')}" />
                            
                            <g:sortableColumn property="content" title="异常描述" />
                            
                            <g:sortableColumn property="createCS" title="发起人" />
                            
                            <g:sortableColumn property="solveCS" title="处理人" />
                            
                            <g:sortableColumn property="contactTimes" title="联系次数" />
                            
                            <g:sortableColumn property="solveStatus" title="完成进度" />
                            
                            <g:sortableColumn property="solveType" title="处理方式" />
                            
                            <g:sortableColumn property="solvePriority" title="处理优先级" />

                            <g:sortableColumn property="jiejueJiange" title="解决间隔" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${errorTradeInstanceList}" status="i" var="errorTradeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" target="_blank" id="${errorTradeInstance.id}" >${fieldValue(bean: errorTradeInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate format="MM-dd HH:mm" date="${errorTradeInstance.dateCreated}"/></td>
                        
                            <td>${fieldValue(bean: errorTradeInstance, field: "store.name")}</td>
                        
                            <td>${errorTradeInstance.userIdForList()}</td>
                            
                            <td>${errorTradeInstance.buyerRemarksForList()}</td>
                            
                            <td>${errorTradeInstance.sellerRemarksForList()}</td>
                        
                            <td>${errorTradeInstance?.errorReason?.nameForList()}</td>
                        
                            <td>${errorTradeInstance.contentForList()}</td>
                        
                            <td>${fieldValue(bean: errorTradeInstance, field: "createCS.name")}</td>
                        
                            <td>${fieldValue(bean: errorTradeInstance, field: "solveCS.name")}</td>
                            
                            <td>${errorTradeInstance?.contactTimes?.getDescription()}</td>
                        
                            <td>${errorTradeInstance.solveStatus?.getDescription()}</td>
                            
                            <td>${fieldValue(bean: errorTradeInstance, field: "solveType.name")}</td>

                            <td>${fieldValue(bean: errorTradeInstance, field: "dealPriority.name")}</td>

                            <td>${fieldValue(bean: errorTradeInstance, field: "jiejueJiange")}<g:if test="${errorTradeInstance.jiejueJiange >= 0}"> 分钟</g:if></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${errorTradeInstanceTotal}" params="${params }" />
            </div>
        </div>
    </body>
</html>
