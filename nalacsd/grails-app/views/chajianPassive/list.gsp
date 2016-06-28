
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
                    <g:link action="create" params="[storeType:'true']"> <g:message code="default.new.label" args="['TP店查件被动记录列表']" /></span> </g:link>
             </g:if>
             <g:else>
                <g:link action="create" params="[storeType:'false']"> <g:message code="default.new.label" args="['查件被动记录列表']" /></span></g:link>
             </g:else>
          </span>
        </div>
        <div class="body">
        	<h1></h1>
            <fieldset>
				<legend>查找记录：</legend>
				<br />
				<g:form action="search" >
                店铺 <g:select name="store_id" from="${stores*.name}" value="${params?.store_id }" keys="${stores*.id}" noSelection="['':'']"  />
				顾客ID <input name="userId" value="${params.userId }" >
				订单编号 <input name="tradeId" value="${params.tradeId }" >
				发起人 <g:select name="createCSId" value="${params.createCSId }" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" noSelection="['':'']"  />
				处理人 <g:select name="solveCSId" value="${params.solveCSId }" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" noSelection="['':'']"  />
				跟踪人 <g:select name="followCSId" value="${params.followCSId }" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" noSelection="['':'']"  />
				查件代码 <g:select name="chajianCode_id" from="${com.nala.csd.ChajianCode.list()*.name}" keys="${com.nala.csd.ChajianCode.list()*.id}" value="${params.chajianCode_id}" noSelection="['': '']" /><br/>
				物流单号 <g:textField name="logistics" value="${params?.logistics}" />
				手机号 <g:textField name="mobile" value="${params?.mobile}" />
				联系优先级 <g:select name="contactStatus" value="${params.contactStatus }" from="${com.nala.csd.ContactStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactStatusEnum?.values()*.getCode()}" noSelection="['':'']"  />
				通知方式 <g:select name="notifyMode_id" from="${com.nala.csd.NotifyMode.list()*.name}" keys="${com.nala.csd.NotifyMode.list()*.id}" value="${params?.notifyMode_id}" noSelection="['': '']" />
				是否跟踪 <g:checkBox name="followUp" value="${params?.followUp}" />
				是否签收 <g:checkBox name="signUp" value="${params?.signUp}" /><br/>
				登记时间 <input value="${params.dateCreatedStart }" id="dateCreatedStart" name="dateCreatedStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.dateCreatedEnd }" id="dateCreatedEnd" name="dateCreatedEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />  
				联系时间 <input value="${params.contactTimeStart }" id="contactTimeStart" name="contactTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.contactTimeEnd }" id="contactTimeEnd" name="contactTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
				解决时间 <input value="${params.solveTimeStart }" id="solveTimeStart" name="solveTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.solveTimeEnd }" id="solveTimeEnd" name="solveTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
				跟踪时间 <input value="${params.updateTimeStart }" id="updateTimeStart" name="updateTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.updateTimeEnd }" id="updateTimeEnd" name="updateTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
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
            <h1><g:message code="default.list.label" args="['查件被动表记录']" />  [${chajianPassiveInstanceTotal }]</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="paginateButtons">
                <g:paginate total="${chajianPassiveInstanceTotal}" params="${params }" />
            </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="id" />
                        
                        	<g:sortableColumn property="dateCreated" title="登记日期" />
                        	
                        	<th><g:message code="chajianPassive.store.label" default="店铺" /></th>
                        
                            <g:sortableColumn property="userId" title="${message(code: 'chajianPassive.userId.label', default: '顾客ID')}" />
                            
                            <g:sortableColumn property="contactStatus" title="联系优先级" />
                        
                            <th><g:message code="chajianPassive.chajianCode.label" default="查件代码" /></th>
                        
                            <g:sortableColumn property="logistics" title="物流单号" />
                            
                            <g:sortableColumn property="mobile" title="手机号" />
                            
                            <th><g:message code="chajianPassive.createCS.label" default="发起人" /></th>
                            
                            <th><g:message code="chajianPassive.solveCS.label" default="处理人" /></th>
                            
                            <th><g:message code="chajianPassive.followCS.label" default="跟踪人" /></th>
                            
                            <g:sortableColumn property="contactTime" title="联系时间" />
                            
                            <g:sortableColumn property="solveTime" title="解决时间" />
                            
                            <g:sortableColumn property="updateTime" title="跟踪时间" />
                            
                            <g:sortableColumn property="lianxiJiange" title="联系间隔" />
                            
                            <g:sortableColumn property="jiejueJiange" title="解决间隔" />
                            
                            <th><g:message code="chajianPassive.notifyMode.label" default="通知方式" /></th>
                            
                            <g:sortableColumn property="followUp" title="是否跟踪" />
                            
                            <g:sortableColumn property="signUp" title="是否签收" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${chajianPassiveInstanceList}" status="i" var="chajianPassiveInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" target="_blank" id="${chajianPassiveInstance.id}" >${chajianPassiveInstance.id}</g:link></td>
                        
                        	<td><g:formatDate format="yyyy-MM-dd HH:mm" date="${chajianPassiveInstance?.dateCreated}"/></td>
                        	
                        	<td>${fieldValue(bean: chajianPassiveInstance, field: "store.name")}</td>
                        	
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "userId")}</td>
                            
                            <td>${chajianPassiveInstance.contactStatus?.getDescription()}</td>
                        
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "chajianCode.name")}</td>
                            
                            <td>${chajianPassiveInstance.logisticsForList()}</td>
                            
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "mobile")}</td>
                        
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "createCS.name")}</td>
                        
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "solveCS.name")}</td>
                            
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "followCS.name")}</td>
                            
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "contactTime")}</td>
                            
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "solveTime")}</td>
                            
                            <td><g:formatDate format="yyyy-MM-dd HH:mm" date="${chajianPassiveInstance?.updateTime}"/></td>
                            
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "lianxiJiange")}</td>
                            
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "jiejueJiange")}</td>
                            
                            <td>${fieldValue(bean: chajianPassiveInstance, field: "notifyMode.name")}</td>
                            
                            <g:if test="${chajianPassiveInstance.followUp }"><td>是</td></g:if><g:else><td>否</td></g:else>                            
                            
                            <g:if test="${chajianPassiveInstance.signUp }"><td>是</td></g:if><g:else><td>否</td></g:else>                            
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>            
        </div>
        <div class="paginateButtons">
            <g:paginate total="${chajianPassiveInstanceTotal}" params="${params }" />
        </div>        
    </body>
</html>
