
<%@ page import="com.nala.csd.B2CConsult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'b2CConsult.label', default: 'B2CConsult')}" />
        <title><g:message code="default.list.label" args="['B2C顾客咨询登记']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['B2C顾客咨询登记']" /></g:link></span>
        </div>
        <div class="body">
        	<h1></h1>
            <fieldset>
				<legend>查找记录：</legend>
				<br />
				<g:form action="search" >
				会员ID <input name="userId" value="${params.userId }" >
				发起人 <g:select name="createCSId" value="${params.createCSId }" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" noSelection="['':'']"  />
				咨询方式 <g:select name="questionSourceId" from="${com.nala.csd.QuestionSource.list()*.name}" keys="${com.nala.csd.QuestionSource.list()*.id}" value="${params?.questionSourceId}" noSelection="['':'']" />
				电话 <input name="phone" value="${params.phone }" >
				姓名 <input name="name" value="${params.name }" ><br />		
				咨询类型 <g:select name="questionStatusId" from="${com.nala.csd.QuestionType.list()*.questionDescription}" keys="${com.nala.csd.QuestionType.list()*.id}" value="${params?.questionStatusId}" noSelection="['':'']" />
				订单编号 <input name="tradeId" value="${params.tradeId }" >				
				处理结果 <g:select name="b2CConsultResultId"  value="${params.b2CConsultResultId }" from="${com.nala.csd.B2CConsultResult?.list()*.name}" keys="${com.nala.csd.B2CConsultResult?.list()*.id}" noSelection="['':'']"  />
				未解决说明 <input name="noSolveReason" value="${params.noSolveReason }" ><br />
				登记时间 <input value="${params.dateCreatedStart }" id="dateCreatedStart" name="dateCreatedStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.dateCreatedEnd }" id="dateCreatedEnd" name="dateCreatedEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />  
				<input type="submit" value="&nbsp;▶&nbsp;搜索&nbsp;◀&nbsp;" class="edit">
				</g:form><br/>
              	<g:form method="post">
        	  		<input type="hidden" name="format" value="excel">
              		<input type="hidden" name="extension" value="xls">
              		<g:actionSubmit value="导出14天内数据" action="export" />
              	</g:form>              	
			</fieldset>
            <h1><g:message code="default.list.label" args="['B2C顾客咨询登记']" /> [数量: ${b2CConsultInstanceTotal }]</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'b2CConsult.id.label', default: 'Id')}" />
                            <g:sortableColumn property="dateCreated" title="登记时间" />
                        	<g:sortableColumn property="createCS" title="发起人" />
                            <th><g:message code="b2CConsult.questionSource.label" default="咨询方式" /></th>
                            <g:sortableColumn property="userId" title="${message(code: 'b2CConsult.userId.label', default: '会员ID')}" />                           
                            <g:sortableColumn property="phone" title="${message(code: 'b2CConsult.phone.label', default: '电话')}" />
                            <g:sortableColumn property="name" title="${message(code: 'b2CConsult.name.label', default: '姓名')}" />
                            <th><g:message code="b2CConsult.questionStatus.label" default="咨询类型" /></th>
                            <g:sortableColumn property="tradeId" title="${message(code: 'b2CConsult.tradeId.label', default: '订单编号')}" />
                            <th><g:message code="b2CConsult.b2CConsultResult.label" default="处理结果" /></th>
                        	<g:sortableColumn property="noSolveReason" title="${message(code: 'b2CConsult.tradeId.label', default: '未解决说明')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${b2CConsultInstanceList}" status="i" var="b2CConsultInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${b2CConsultInstance.id}">${fieldValue(bean: b2CConsultInstance, field: "id")}</g:link></td>
                            <td><g:formatDate format="MM-dd HH:mm" date="${b2CConsultInstance?.dateCreated}"/></td>
                        	<td>${b2CConsultInstance.createCS?.name}</td>
                            <td>${b2CConsultInstance.questionSource?.name}</td>
                            <td>${fieldValue(bean: b2CConsultInstance, field: "userId")}</td>
                            <td>${fieldValue(bean: b2CConsultInstance, field: "phone")}</td>
                            <td>${fieldValue(bean: b2CConsultInstance, field: "name")}</td>
                            <td>${b2CConsultInstance.questionStatus?.questionDescription}</td>
                        	<td>${fieldValue(bean: b2CConsultInstance, field: "tradeId")}</td>
                        	<td>${b2CConsultInstance.b2CConsultResult?.name}</td>
                        	<td>${b2CConsultInstance.noSolveReasonForList()}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${b2CConsultInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
