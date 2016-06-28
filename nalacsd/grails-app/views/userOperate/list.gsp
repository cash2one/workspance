
<%@ page import="com.nala.csd.UserOperate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userOperate.label', default: 'UserOperate')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <style type="text/css">
            .qingkong{
                padding-top: 20px;
            }
        </style>
        <script>
            function clearAll(){
                $.each($('.cle'), function(i, n){
                    $(this).attr('value','')
                });
            }
        </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>

        </div>
        <div class="body">

            <h1></h1>
            <fieldset>
                <legend>查找记录：</legend>
                <br />
                <g:form action="search" >
                    IP地址 <input  class="cle"  name="clientIp" value="${params?.clientIp }">
                    登陆时间 <input class="cle" value="${params?.loginTimeStart }" id="loginTimeStart" name="loginTimeStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input class="cle" value="${params?.loginTimeEnd }" id="loginTimeEnd" name="loginTimeEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
                    登陆名 <g:select class="cle" name="hero" from="${com.nala.csd.Hero.list()*.name}" value="${params?.hero }" keys="${com.nala.csd.Hero.list()*.id}" noSelection="['':'']"  />
                    操作时间 <input class="cle" value="${params?.dateStart }" id="dateStart" name="dateStart" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/> ~ <input  class="cle" value="${params?.dateEnd }" id="dateEnd" name="dateEnd" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"/><br />
                    表名称 <g:select class="cle" name="tabName" value="${params.tabName}" from="${com.nala.csd.TabEnum?.values()*.getDescription()}" keys="${com.nala.csd.TabEnum?.values()*.getCode()}" noSelection="['':'']"  /><br />
                    字段号 <input class="cle" name="tId" keys="${com.nala.csd.ErrorReasonForTrade.list()*.id}" value="${params?.tId}" noSelection="['': '']" /> <br>
                    操作类型 <g:select class="cle" name="memo" value="${params.memo}" from="${com.nala.csd.MemoEnum?.values()*.getDescription()}" keys="${com.nala.csd.MemoEnum?.values()*.getCode()}" noSelection="['':'']"  /> <br>
                    <input type="hidden" name="tp" value="${tp}" />
                    <input type="submit" value="&nbsp;▶&nbsp;搜索&nbsp;◀&nbsp;">
                </g:form>
                <div class='qingkong'><button  onclick="clearAll()">清空</button></div>
                <br/>
                %{--<g:form method="post">--}%
                    %{--<input type="hidden" name="format" value="excel">--}%
                    %{--<input type="hidden" name="tp" value="${tp}" >--}%
                    %{--<input type="hidden" name="extension" value="xls">--}%
                    %{--<g:actionSubmit value="导出14天内数据" action="export" />--}%
                %{--</g:form>--}%
            </fieldset>
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>

                            <g:sortableColumn property="id" title="${message(code: 'userOperate.id.label', default: 'ID')}" />
                            <th><g:message code="userOperate.hero.label" default="登录名" /></th>
                            <g:sortableColumn property="clientIp" title="${message(code: 'userOperate.clientIp.label', default: 'IP地址')}" />
                            <g:sortableColumn property="loginTime" title="${message(code: 'userOperate.loginTime.label', default: '登陆时间')}" />
                            <g:sortableColumn property="date" title="${message(code: 'userOperate.date.label', default: '操作时间')}" />
                            <g:sortableColumn property="tabName" title="${message(code: 'userOperate.tabName.label', default: '表名称')}" />
                            <g:sortableColumn property="tabName" title="${message(code: 'userOperate.tabName.label', default: '字段')}" />
                            <g:sortableColumn property="tabName" title="${message(code: 'userOperate.tabName.label', default: '操作类型')}" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userOperateInstanceList}" status="i" var="userOperateInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${userOperateInstance.id}">${fieldValue(bean: userOperateInstance, field: "id")}</g:link></td>
                            <td>${userOperateInstance.hero.name}</td>
                            <td>${userOperateInstance.clientIp}</td>
                            <td><g:formatDate date="${userOperateInstance.loginTime}" /></td>
                            <td><g:formatDate date="${userOperateInstance.date}" /></td>
                            <td>${userOperateInstance.tabName.description}</td>
                            <td>${fieldValue(bean: userOperateInstance, field: "tId")}</td>
                            <td>${userOperateInstance.memo.description}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${userOperateInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
