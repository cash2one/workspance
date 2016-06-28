
<%@ page import="com.nala.csd.ChajianInitiative" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chajianInitiative.label', default: 'ChajianInitiative')}" />
        <title><g:message code="default.list.label" args="['主动查件记录']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['主动查件记录']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="importIndex">导入主动查件记录</g:link></span>
        </div>
        <div class="body">
            <h1></h1>
            <fieldset>
                <legend>查找记录：</legend>
                <br />
                <g:form action="search" >
                    用户ID <input name="userId" value="${params.userId }" >
                    店铺 <g:select name="store_id" from="${com.nala.csd.Store.list()*.name}" value="${params?.store_id }" keys="${com.nala.csd.Store.list()*.id}" noSelection="['':'']"  />
                    查件代码 <g:select name="chajianCode_id" from="${chajianCodeList*.name}" keys="${chajianCodeList*.id}" value="${params.chajianCode_id}" noSelection="['': '']" />
                    物流单号 <input name="logistics" value="${params.logistics }" ><br />
                    处理人 <g:select name="solveCSId" value="${params.solveCSId }" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" noSelection="['':'']"  />
                    跟踪人 <g:select name="followCSId" value="${params.followCSId }" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" noSelection="['':'']"  />
                    手机号 <input name="mobile" value="${params.mobile }" >
                    处理方式 <input name="solveType" value="${params.solveType }" >
                    是否跟踪 <g:checkBox name="followUp" value="${params?.followUp}" />
                    是否签收 <g:checkBox name="signUp" value="${params?.signUp}" />
                    通知方式 <g:select name="notifyMode_id" from="${com.nala.csd.NotifyMode.list()*.name}" keys="${com.nala.csd.NotifyMode.list()*.id}" value="${params?.notifyMode_id}" noSelection="['': '']" /><br/>
                    登记时间 <input value="${params.dateCreatedStart }" id="dateCreatedStart" name="dateCreatedStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.dateCreatedEnd }" id="dateCreatedEnd" name="dateCreatedEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
                    联系时间 <input value="${params.contactTimeStart }" id="contactTimeStart" name="contactTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.contactTimeEnd }" id="contactTimeEnd" name="contactTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
                    跟踪时间 <input value="${params.followTimeStart }" id="followTimeStart" name="followTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.followTimeEnd }" id="followTimeEnd" name="followTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
                    <input type="submit" value="&nbsp;▶&nbsp;搜索&nbsp;◀&nbsp;" class="edit">
                </g:form><br/>
                <g:form method="post">
                    <input type="hidden" name="format" value="excel">
                    <input type="hidden" name="extension" value="xls">
                    <g:actionSubmit value="导出14天内数据" action="export" />
                </g:form>
            </fieldset>
            <h1><g:message code="default.list.label" args="['主动查件记录']" />  [${chajianInitiativeInstanceTotal }]</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'chajianInitiative.id.label', default: 'Id')}" />

                            <g:sortableColumn property="dateCreated" title="登记日期" />

                            <th>店铺</th>
                        
                            <g:sortableColumn property="userId" title="${message(code: 'chajianInitiative.userId.label', default: '用户ID')}" />
                        
                            <th><g:message code="chajianInitiative.chajianCode.label" default="查件代码" /></th>
                        
                            <g:sortableColumn property="logistics" title="${message(code: 'chajianInitiative.logistics.label', default: '物流单号')}" />
                        
                            <g:sortableColumn property="mobile" title="${message(code: 'chajianInitiative.mobile.label', default: '手机号')}" />
                        
                            <g:sortableColumn property="solveType" title="${message(code: 'chajianInitiative.solveType.label', default: '处理方式')}" />

                            <g:sortableColumn property="solveCS" title="处理人" />

                            <g:sortableColumn property="followCS" title="跟踪人" />

                            <g:sortableColumn property="followTime" title="跟踪时间" />

                            <th><g:message code="chajianInitiative.notifyMode.label" default="通知方式" /></th>

                            <g:sortableColumn property="followUp" title="是否跟踪" />

                            <g:sortableColumn property="signUp" title="是否签收" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${chajianInitiativeInstanceList}" status="i" var="chajianInitiativeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" target="_blank" id="${chajianInitiativeInstance.id}">${fieldValue(bean: chajianInitiativeInstance, field: "id")}</g:link></td>

                            <td><g:formatDate format="MM-dd HH:mm" date="${chajianInitiativeInstance?.dateCreated}"/></td>

                            <td>${fieldValue(bean: chajianInitiativeInstance, field: "store.name")}</td>
                        
                            <td>${com.nala.common.utils.StringUtils.maxLength(chajianInitiativeInstance.userId, 10)}</td>
                        
                            <td>${chajianInitiativeInstance?.chajianCode?.name}</td>

                            <td>${com.nala.common.utils.StringUtils.maxLength(chajianInitiativeInstance.logistics, 20)}</td>

                            <td>${com.nala.common.utils.StringUtils.maxLength(chajianInitiativeInstance.mobile, 12)}</td>

                            <td>${com.nala.common.utils.StringUtils.maxLength(chajianInitiativeInstance.solveType, 20)}</td>

                            <td>${chajianInitiativeInstance.solveCS?.name}</td>

                            <td>${chajianInitiativeInstance.followCS?.name}</td>

                            <td><g:formatDate format="MM-dd HH:mm" date="${chajianInitiativeInstance?.followTime}"/></td>

                            <td>${fieldValue(bean: chajianInitiativeInstance, field: "notifyMode.name")}</td>

                            <g:if test="${chajianInitiativeInstance.followUp }"><td>是</td></g:if><g:else><td>否</td></g:else>

                            <g:if test="${chajianInitiativeInstance.signUp }"><td>是</td></g:if><g:else><td>否</td></g:else>

                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${chajianInitiativeInstanceTotal}" params="${params}" />
            </div>
        </div>
    </body>
</html>
