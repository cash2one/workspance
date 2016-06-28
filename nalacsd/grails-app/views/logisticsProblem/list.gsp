
<%@ page import="com.nala.csd.LogisticsProblem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logisticsProblem.label', default: 'LogisticsProblem')}" />
        <title><g:message code="default.list.label" args="['快递问题件']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['快递问题件']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="importIndex">导入快递问题件</g:link></span>
        </div>
        <div class="body">
        	<h1></h1>
        	<fieldset>
				<legend>查找记录：</legend>
				<br />
				<g:form action="search" >
				店铺 <g:select name="store_id" from="${com.nala.csd.Store.list()*.name}" value="${params.store_id }" keys="${com.nala.csd.Store.list()*.id}" noSelection="['':'']"  />
				顾客ID <input name="userId" value="${params.userId }" >
				物流单号 <g:textField name="expressProblem_id" value="${params?.expressProblem_id}" />
				快递公司 <g:select name="express_id" from="${com.nala.csd.Express.list()*.name}" keys="${com.nala.csd.Express.list()*.id}" value="${params.express_id}" noSelection="['': '']" /><br />
				处理方式 <input name="solveType" value="${params.solveType }" >
				通知方式 <g:select name="notifyMode_id" from="${com.nala.csd.NotifyMode.list()*.name}" keys="${com.nala.csd.NotifyMode.list()*.id}" value="${params?.notifyMode_id}" noSelection="['': '']" />
				手机号 <g:textField name="mobile" value="${params?.mobile}" />
				处理人 <g:select name="solveCSId" from="${com.nala.csd.Hero.list()*.name}" value="${params.solveCSId }" keys="${com.nala.csd.Hero.list()*.id}" noSelection="['':'']"  />
				跟踪人 <g:select name="followCSId" from="${com.nala.csd.Hero.list()*.name}" value="${params.followCSId }" keys="${com.nala.csd.Hero.list()*.id}" noSelection="['':'']"  />
				是否跟踪 <g:checkBox name="followUp" value="${params?.followUp}" />
				是否签收 <g:checkBox name="signUp" value="${params?.signUp}" /><br/>
				创建时间 <input value="${params.dateCreatedStart }" id="dateCreatedStart" name="dateCreatedStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.dateCreatedEnd }" id="dateCreatedEnd" name="dateCreatedEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />  
				解决时间 <input value="${params.solveTimeStart }" id="solveTimeStart" name="solveTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.solveTimeEnd }" id="solveTimeEnd" name="solveTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
				跟踪时间 <input value="${params.followTimeStart }" id="followTimeStart" name="followTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.followTimeEnd }" id="followTimeEnd" name="followTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
				<input type="submit" value="&nbsp;▶&nbsp;搜索&nbsp;◀&nbsp;">
				</g:form><br/>
				<g:form method="post">
      	  			<input type="hidden" name="format" value="excel">
           			<input type="hidden" name="extension" value="xls">
           			<g:actionSubmit value="导出14天内数据" action="export" />
           		</g:form>
           	</fieldset>
            <h1><g:message code="default.list.label" args="['快递问题件']" /> [${logisticsProblemInstanceTotal }]</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="paginateButtons">
                <g:paginate total="${logisticsProblemInstanceTotal}" params="${params }" />
            </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'logisticsProblem.id.label', default: 'Id')}" />
                            
                            <g:sortableColumn property="dateCreated" title="日期" />
                        
                            <th><g:message code="logisticsProblem.store.label" default="店铺" /></th>
                            
                            <g:sortableColumn property="userId" title="顾客ID" />
                            
                            <th>物流单号</th>

							<g:sortableColumn property="solveTime" title="解决时间" />
							
							<g:sortableColumn property="solveCS" title="处理人" />
							
							<g:sortableColumn property="mobile" title="手机号码" />
							
                            <g:sortableColumn property="solveType" title="${message(code: 'logisticsProblem.solveType.label', default: '处理方式')}" />
                            
                            <th>通知方式</th>
                            
                            <g:sortableColumn property="followUp" title="是否跟踪" />
                            
                            <g:sortableColumn property="followCS" title="跟踪人" />
                            
                            <g:sortableColumn property="followTime" title="跟踪时间" />
                            
                            <g:sortableColumn property="signUp" title="是否签收" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${logisticsProblemInstanceList}" status="i" var="logisticsProblemInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" target="_blank" id="${logisticsProblemInstance.id}" >${fieldValue(bean: logisticsProblemInstance, field: "id")}</g:link></td>
                            
                            <td><g:formatDate format="MM-dd HH:mm" date="${logisticsProblemInstance?.dateCreated}"/></td>
                        
                            <td>${fieldValue(bean: logisticsProblemInstance, field: "store.name")}</td>
                        
                            <td><g:if test="${logisticsProblemInstance?.userId?.length() > 10}">${logisticsProblemInstance?.userId?.substring(0,10)}...</g:if><g:else>${logisticsProblemInstance?.userId}</g:else></td>
                            
                            <td>${fieldValue(bean: logisticsProblemInstance, field: "expressProblem.express.name")}:${fieldValue(bean: logisticsProblemInstance, field: "expressProblem.logisticsID")}</td>
                            
                            <td><g:if test="${logisticsProblemInstance.solveTime}">${logisticsProblemInstance.solveTime.substring(5, logisticsProblemInstance.solveTime.length())}</g:if></td>
                            
                            <td>${fieldValue(bean: logisticsProblemInstance, field: "solveCS.name")}</td>
                            
                            <td><g:if test="${logisticsProblemInstance?.mobile?.length() > 12}">${logisticsProblemInstance?.mobile?.substring(0,12)}...</g:if><g:else>${logisticsProblemInstance?.mobile}</g:else></td>
                            
                            <td>${logisticsProblemInstance.solveTypeForList()}</td>
                            
                            <td>${fieldValue(bean: logisticsProblemInstance, field: "notifyMode.name")}</td>
                            
                            <g:if test="${logisticsProblemInstance.followUp }"><td>是</td></g:if><g:else><td>否</td></g:else>
                            
                            <td>${fieldValue(bean: logisticsProblemInstance, field: "followCS.name")}</td>
                            
                            <td><g:if test="${logisticsProblemInstance.followTime}">${logisticsProblemInstance.followTime.substring(5, logisticsProblemInstance.followTime.length())}</g:if></td>
                            
                            <g:if test="${logisticsProblemInstance.signUp }"><td>是</td></g:if><g:else><td>否</td></g:else>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${logisticsProblemInstanceTotal}"  params="${params }"/>
            </div>
        </div>
    </body>
</html>
