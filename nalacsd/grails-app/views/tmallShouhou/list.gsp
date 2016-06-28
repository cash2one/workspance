
<%@ page import="com.nala.csd.TmallShouhou" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tmallShouhou.label', default: 'TmallShouhou')}" />
        <title><g:if test="${tp}"><g:message code="default.list.label" args="['TP店售后记录']" /></g:if><g:else><g:message code="default.list.label" args="['天猫/B2C/拍拍售后记录']" /></g:else></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create" params="${params}"><g:if test="${tp}"><g:message code="default.new.label" args="['TP店售后记录']" /></g:if><g:else><g:message code="default.new.label" args="['天猫/B2C/拍拍售后记录']" /></g:else></g:link></span>
        </div>
        <div class="body">
        	<h1></h1>
        	<fieldset>
				<legend>查找记录：</legend>
				<br />
				<g:form action="search" >
				顾客ID <input name="userId" value="${params.userId }" >
                订单编号 <input name="tradeId" value="${params.tradeId }" >
				登记人 <g:select name="createCSId" value="${params.createCSId }" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" noSelection="['':'']"  />
				店铺 <g:select name="store_id" from="${stores*.name}" value="${params?.store_id }" keys="${stores*.id}" noSelection="['':'']"  />
				问题类型 <g:select name="questionStatusId" from="${com.nala.csd.QuestionType.list()*.questionDescription}" keys="${com.nala.csd.QuestionType.list()*.id}" value="${params?.questionStatusId}" noSelection="['':'']" />
				商品明细 <input name="itemDetail" value="${params.itemDetail }" >
				未完成说明 <input name="noSolveReason" value="${params.noSolveReason }" ><br/>
				处理方式 <g:select name="solveType" value="${params.solveType }" from="${com.nala.csd.JinTmallSolveType.list().name}" keys="${com.nala.csd.JinTmallSolveType.list().id}" noSelection="['':'']"  />
				货款操作记录 <g:select name="moneyRecordType"  value="${params.moneyRecordType }" from="${com.nala.csd.MoneyRecordTypeEnum?.values()*.getDescription()}" keys="${com.nala.csd.MoneyRecordTypeEnum?.values()*.getCode()}" noSelection="['':'']"  />
				信息错误说明 <input name="wrongReason" value="${params.wrongReason }" >
				完成进度 <g:select name="solveStatus" value="${params.solveStatus }" from="${com.nala.csd.SolveStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveStatusEnum?.values()*.getCode()}" noSelection="['':'']" />
                退回快递和单号 <input name="wuliuNo" value="${params.wuliuNo }" ><br />
				登记时间 <input value="${params.dateCreatedStart }" id="dateCreatedStart" name="dateCreatedStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.dateCreatedEnd }" id="dateCreatedEnd" name="dateCreatedEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
                <input type="hidden" name="tp" value="${tp}" />
				<input type="submit" value="&nbsp;▶&nbsp;搜索&nbsp;◀&nbsp;" class="edit">
				</g:form><br/>
                <g:if test="${actionName.equals('search') }">
                    <g:form controller="tmallShouhou" action="exportCurData" method="post">
                        <input type="hidden" name="tp" value="${tp}" />
                        <input type="hidden" name="format" value="excel">
                        <input type="hidden" name="extension" value="xls">
                        <input type="hidden" name="searchParams" value="${params.encodeAsHTML() }">
                        <g:actionSubmit value="导出当前列表数据" action="exportCurData" />
                    </g:form>
                </g:if><g:else>
                    <g:form controller="tmallShouhou" action="export" method="post">
                        <input type="hidden" name="tp" value="${tp}" />
                        <input type="hidden" name="format" value="excel">
                        <input type="hidden" name="extension" value="xls">
                        <g:actionSubmit value="导出14天内数据" action="export" />
                    </g:form>
                </g:else>

			</fieldset>
            <h1><g:if test="${tp}"><g:message code="default.list.label" args="['TP店售后记录']" /></g:if><g:else><g:message code="default.list.label" args="['天猫/B2C/拍拍售后记录']" /></g:else> [数量: ${tmallShouhouInstanceTotal }] </h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <div class="paginateButtons">
                <g:paginate total="${tmallShouhouInstanceTotal}" params="${params}" />
            </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'tmallShouhou.id.label', default: 'Id')}" />
                        	<g:sortableColumn property="dateCreated" title="登记时间" />
                        	<g:sortableColumn property="createCS" title="登记人" />
                        	<th><g:message code="tmallShouhou.store.label" default="店铺" /></th>
                            <g:sortableColumn property="userId" title="顾客ID" />
                            <g:sortableColumn property="tradeId" title="订单编号" />
                            <th><g:message code="tmallShouhou.questionStatus.label" default="问题类型" /></th>
                        	<g:sortableColumn property="itemDetail" title="商品明细" />
                        	<g:sortableColumn property="solveType" title="处理方式" />
                        	<g:sortableColumn property="moneyRecordType" title="货款操作记录" />
                            <g:sortableColumn property="wrongReason" title="信息错误说明" />
                            <g:sortableColumn property="solveStatus" title="完成进度" />
                            <g:sortableColumn property="noSolveReason" title="未完成说明" />
                            <g:sortableColumn property="wuliuNo" title="退回快递和单号" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tmallShouhouInstanceList}" status="i" var="tmallShouhouInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" target="_blank" id="${tmallShouhouInstance.id}">${fieldValue(bean: tmallShouhouInstance, field: "id")}</g:link></td>
                        	<td><g:formatDate format="MM-dd HH:mm" date="${tmallShouhouInstance?.dateCreated}"/></td>
                        	<td>${tmallShouhouInstance.createCS.name}</td>
                        	<td>${fieldValue(bean: tmallShouhouInstance, field: "store.name")}</td>
                            <td>${com.nala.common.utils.StringUtils.maxLength(tmallShouhouInstance.userId, 12) }</td>
                            <td>${fieldValue(bean: tmallShouhouInstance, field: "tradeId")}</td>
                            <td>${tmallShouhouInstance.questionStatus?.questionDescription}</td>
                            <td>${tmallShouhouInstance.itemDeailForList()}</td>
                        	<td>${tmallShouhouInstance.solveType?.name}</td>
                        	<td>${tmallShouhouInstance.moneyRecordType?.getDescription()}</td>
                            <td>${tmallShouhouInstance.wrongReasonForList()}</td>
                            <td>${tmallShouhouInstance.solveStatus?.getDescription()}</td>
                            <td>${tmallShouhouInstance.noSolveReasonForList()}</td>                            
                            <td>${com.nala.common.utils.StringUtils.maxLength(tmallShouhouInstance.wuliuNo, 12) }</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tmallShouhouInstanceTotal}" params="${params }" />
            </div>
        </div>
    </body>
</html>
