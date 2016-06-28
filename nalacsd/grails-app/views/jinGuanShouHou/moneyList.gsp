
<%@ page import="com.nala.csd.JinGuanShouHou" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou')}" />
    <title>金冠店售后记录列表_货款页面</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">首页</a></span>
    <span class="menuButton"><g:link class="create" action="create">新建售后记录</g:link></span>
    <span class="menuButton">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#"><font color="green">货款页面：</font></a></span>
    <span class="menuButton"><a href="/jinGuanShouHou/moneyList?tab=tuikuan">退款</a></span>
    <span class="menuButton"><a href="/jinGuanShouHou/moneyList?tab=dakuan">打款(<font color="red"><g:if test="${dakuanCount > 0}">${dakuanCount}</g:if><g:else>${jinGuanShouHouInstanceTotal}</g:else></font>)</a></span>
    <span class="menuButton"><a href="/jinGuanShouHou/moneyList?tab=bufa">补发</a></span>
    <span class="menuButton"><a href="/jinGuanShouHou/moneyList?tab=zhongchap">中差评（<font color="red"><g:if test="${zhongchapCount > 0}">${zhongchapCount}</g:if></font>)</a></span>
</div>
<div class="body">
    <h1></h1>
    <fieldset>
        <legend>查找记录：</legend>
        <br />
        <g:form action="search" >
            顾客ID <input name="userId" value="${params.userId }" >
            订单编号 <input name="tradeId" value="${params.tradeId }" >
            发起人 <g:select name="createCSId" value="${params.createCSId }" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" noSelection="['':'']"  />
            处理人 <g:select name="solveCSId" value="${params.solveCSId }" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" noSelection="['':'']"  />
            问题类型 <g:select name="questionStatusId" from="${com.nala.csd.QuestionType.list()*.questionDescription}" keys="${com.nala.csd.QuestionType.list()*.id}" value="${params?.questionStatusId}" noSelection="['':'']" />
            商品明细 <input name="itemDetail" value="${params.itemDetail }" ><br/>
            联系优先级 <g:select name="contactStatus" value="${params.contactStatus }" from="${com.nala.csd.ContactStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactStatusEnum?.values()*.getCode()}" noSelection="['':'']"  />
            处理方式 <g:select name="solveType" value="${params.solveType }" from="${com.nala.csd.JinTmallSolveType.list().name}" keys="${com.nala.csd.JinTmallSolveType.list().id}" noSelection="['':'']"  />
            货款操作记录 <g:select name="moneyRecordType"  value="${params.moneyRecordType }" from="${com.nala.csd.MoneyRecordTypeEnum?.values()*.getDescription()}" keys="${com.nala.csd.MoneyRecordTypeEnum?.values()*.getCode()}" noSelection="['':'']"  />
            完成进度 <g:select name="solveStatus" value="${params.solveStatus }" from="${com.nala.csd.SolveStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveStatusEnum?.values()*.getCode()}" noSelection="['':'']" />
            退回单号 <input name="wuliuNo" value="${params.wuliuNo }" ><br />
            登记时间 <input value="${params.dateCreatedStart }" id="dateCreatedStart" name="dateCreatedStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.dateCreatedEnd }" id="dateCreatedEnd" name="dateCreatedEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
            联系时间 <input value="${params.contactTimeStart }" id="contactTimeStart" name="contactTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.contactTimeEnd }" id="contactTimeEnd" name="contactTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
            解决时间 <input value="${params.solveTimeStart }" id="solveTimeStart" name="solveTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input value="${params.solveTimeEnd }" id="solveTimeEnd" name="solveTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
            <input type="submit" value="&nbsp;▶&nbsp;搜索&nbsp;◀&nbsp;" class="edit">
        </g:form><br/>
        <g:if test="${actionName.equals('search') }">
            <g:form method="post">
                <input type="hidden" name="format" value="excel">
                <input type="hidden" name="extension" value="xls">
                <input type="hidden" name="searchParams" value="${params.encodeAsHTML() }">
                <g:actionSubmit value="导出当前列表数据" action="exportCurData" />
            </g:form>
        </g:if><g:else>
        <g:form method="post">
            <input type="hidden" name="format" value="excel">
            <input type="hidden" name="extension" value="xls">
            <g:actionSubmit value="导出14天内数据" action="export" />
        </g:form>
    </g:else>

    </fieldset>
    <h1>金冠店售后记录列表
    <g:if test="${params.tab=='tuikuan'}">::::退款::::</g:if>
    <g:elseif test="${params.tab=='dakuan'}">::::打款::::</g:elseif>
    <g:elseif test="${params.tab=='bufa'}">::::补发::::</g:elseif>
    <g:elseif test="${params.tab=='zhongchap'}">::::中差评::::</g:elseif>
    [数量: ${jinGuanShouHouInstanceTotal }] </h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <g:if test="${heroJobs }">
            <table>
                <thead>
                <tr>
                    <td>处理人</td>
                    <g:if test="${params.solveStatus.equals('0') }"><td>未完成数量</td></g:if><g:else if test="${params.solveStatus.equals('1') }"><td>已完成数量</td></g:else>
                </tr>
                </thead>
                <tbody>
                <g:each in="${heroJobs?.entrySet()}" var="entry" status="i" >
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${entry?.key?.name}</td>
                        <td>${entry?.value }</td>
                    </tr>
                </g:each>
                </tbody>
            </table><br />
        </g:if>
        <table>
            <thead>
            <tr>
                <g:sortableColumn property="id" title="${message(code: 'jinGuanShouHou.id.label', default: 'Id')}" />
                <g:sortableColumn property="dateCreated" title="登记时间" />
                <g:sortableColumn property="createCS" title="发起人" />
                <g:sortableColumn property="userId" title="顾客ID" />
                <g:sortableColumn property="questionStatus" title="问题类型" />
                <g:sortableColumn property="itemDetail" title="商品明细" />
                <g:sortableColumn property="solveCS" title="处理人" />
                <g:sortableColumn property="contactTime" title="联系时间" />
                <g:sortableColumn property="solveTime" title="解决时间" />
                <g:sortableColumn property="contactStatus" title="联系优先级" />
                <g:sortableColumn property="solveType" title="处理方式" />
                <g:sortableColumn property="moneyRecordType" title="货款操作记录" />
                <g:sortableColumn property="solveStatus" title="完成进度" />
                <g:sortableColumn property="solveStatus" title="联系间隔" />
                <g:sortableColumn property="solveStatus" title="解决间隔" />
            </tr>
            </thead>
            <tbody>
            <g:each in="${jinGuanShouHouInstanceList}" status="i" var="jinGuanShouHouInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td><g:link action="show" target="_blank" id="${jinGuanShouHouInstance.id}">${fieldValue(bean: jinGuanShouHouInstance, field: "id")}</g:link></td>
                    <td><g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.dateCreated}"/></td>
                    <td>${jinGuanShouHouInstance.createCS.name}</td>
                    <td>${jinGuanShouHouInstance.userId}</td>
                    <td>${jinGuanShouHouInstance.questionStatus?.questionDescription}</td>
                    <td>${jinGuanShouHouInstance.itemDeailForList()}</td>
                    <td>${jinGuanShouHouInstance.solveCS?.name}</td>
                    <td>${jinGuanShouHouInstance.contactTime}</td>
                    <td>${jinGuanShouHouInstance.solveTime}</td>
                    <td>${jinGuanShouHouInstance.contactStatus?.getDescription()}</td>
                    <td>${jinGuanShouHouInstance.solveType?.name}</td>
                    <td>${jinGuanShouHouInstance.moneyRecordType?.getDescription()}</td>
                    <td>${jinGuanShouHouInstance.solveStatus?.getDescription()}</td>
                    <td>
                        <g:if test="${jinGuanShouHouInstance.lianxiJiange >= 0}">
                            ${jinGuanShouHouInstance.lianxiJiange}分钟
                        </g:if>
                    </td>
                    <td>
                        <g:if test="${jinGuanShouHouInstance.jiejueJiange >= 0}">
                            ${jinGuanShouHouInstance.jiejueJiange}分钟
                        </g:if>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${jinGuanShouHouInstanceTotal}" params="${params }" />
    </div>
</div>
</body>
</html>
