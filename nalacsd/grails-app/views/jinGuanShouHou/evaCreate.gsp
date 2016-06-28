<%@ page import="com.nala.csd.JinGuanShouHou" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou')}" />
        <title><g:message code="default.create.label" args="['金冠店售后登记记录_中差评页面']" /></title>

        <link rel="stylesheet" href="${resource(dir:'css',file:'jquery.autocomplete.css')}" />

        <g:javascript library="jquery.autocomplete" />
        <g:javascript library="localdata" />
        <script type="text/javascript">
            var qustions = [
                ${questionTypeData}
            ];

            $().ready(function() {
                $("#suggest1").focus().autocomplete(qustions);
                $("#clear").click(function() {
                    $(":input").unautocomplete();
                });
            });
        </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">首页</a></span>
            <span class="menuButton"><g:link class="list" action="evaList">金冠店中差评_售后登记记录列表</g:link></span>
        </div>
        <div class="body">
            <h1>新增金冠售后登记记录</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${jinGuanShouHouInstance}">
            <div class="errors">
                <g:renderErrors bean="${jinGuanShouHouInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="evaSave" >
                <div class="dialog">
                    <table>
                        <tbody>
							<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">登记时间</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'dateCreated', 'errors')}">
                                    <input id="dateCreated" name="dateCreated" value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.dateCreated}"/>" style="width:160px" disabled="disabled" />
                                </td>
                            </tr>        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="createCS"><g:message code="jinGuanShouHou.createCS.label" default="发起人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'createCS', 'errors')}">
                                    <g:if test="${jinGuanShouHouInstance?.createCS?.id}">
                                        <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${jinGuanShouHouInstance?.createCS?.id }" noSelection="['':'']"  />
                                    </g:if>
                                    <g:else>
                                        <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${userId }" noSelection="['':'']"  />
                                    </g:else>
                                </td>
                            </tr>    
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userId"><g:message code="jinGuanShouHou.userId.label" default="顾客ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'userId', 'errors')}">
                                    <g:textField name="userId" value="${jinGuanShouHouInstance?.userId}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tradeId"><g:message code="jinGuanShouHou.tradeId.label" default="订单编号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'tradeId', 'errors')}">
                                    <g:textField name="tradeId" value="${jinGuanShouHouInstance?.tradeId}" />
                                </td>
                            </tr>
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                    %{--<label for="contactStatus"><g:message code="jinGuanShouHou.contactStatus.label" default="联系优先级" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'contactStatus', 'errors')}">--}%
                                    %{--<g:select name="contactStatus" from="${com.nala.csd.ContactStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactStatusEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.contactStatus?.name() }" noSelection="['':'']"  />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        	%{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                    %{--<label for="tel"><g:message code="jinGuanShouHou.tel.label" default="联系电话" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'tel', 'errors')}">--}%
                                    %{--<g:textField name="tel" value="${jinGuanShouHouInstance?.tel}" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                    %{--<label for="zhidingTime"><g:message code="jinGuanShouHou.zhidingTime.label" default="指定时间" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'zhidingTime', 'errors')}">--}%
                                    %{--<input id="zhidingTime" name="zhidingTime" type="text" style="width:160px" value="${jinGuanShouHouInstance?.zhidingTime }" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="questionStatus"><g:message code="jinGuanShouHou.questionStatus.label" default="问题类型" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'questionStatus', 'errors')}">
                                    <g:select class="questionStatus" name="questionStatus.id" from="${com.nala.csd.QuestionType.list()*.questionDescription}" keys="${com.nala.csd.QuestionType.list()*.id}" value="${jinGuanShouHouInstance?.questionStatus?.id}"  />
                                    &nbsp;&nbsp;&nbsp;&nbsp;快速选择:&nbsp;&nbsp;&nbsp;&nbsp;<input name="questionStatusDescription" type="text" id="suggest1" />
                                </td>
                            </tr>
                            
                            <g:render template="/jinGuanShouHou/questionReason"/>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="itemDetail"><g:message code="jinGuanShouHou.itemDetail.label" default="商品明细" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'itemDetail', 'errors')}">
                                    <textarea name="itemDetail" rows="2" cols="20">${jinGuanShouHouInstance?.itemDetail}</textarea>
                                </td>
                            </tr>
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                    %{--<label for="solveCS"><g:message code="jinGuanShouHou.solveCS.label" default="处理人" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'solveCS', 'errors')}">--}%
                                    %{--<g:select name="solveCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" value="${jinGuanShouHouInstance?.solveCS?.id }" noSelection="['':'']" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                    %{--<label for="contactTimes"><g:message code="jinGuanShouHou.contactTimes.label" default="联系次数" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'contactTimes', 'errors')}">--}%
                                    %{--<g:select name="contactTimes" from="${com.nala.csd.ContactTimesEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactTimesEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.contactTimes?.name() }" noSelection="['':'']"  />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="solveType"><g:message code="jinGuanShouHou.solveType.label" default="处理方式" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'solveType', 'errors')}">
                                    <g:select class="origen" name="solveType" from="${com.nala.csd.JinTmallSolveType.list().name}" keys="${com.nala.csd.JinTmallSolveType.list().id}" value="${params.solveType }" noSelection="['':'']"  />
                                </td>
                            </tr>
                            <g:render template="/jinGuanShouHou/create_returnGoods"/>
                            <g:render template="/jinGuanShouHou/create_resend"/>
                            <g:render template="/jinGuanShouHou/create_refund"/>
                            <g:render template="/jinGuanShouHou/create_remit"/>
                            <g:render template="/jinGuanShouHou/create_coupon"/>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="moneyRecordType"><g:message code="jinGuanShouHou.moneyRecordType.label" default="货款操作记录" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'moneyRecordType', 'errors')}">
                                    <g:select class="moneyRecordType" name="moneyRecordType" from="${com.nala.csd.MoneyRecordTypeEnum?.values()*.getDescription()}" keys="${com.nala.csd.MoneyRecordTypeEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.moneyRecordType?.name() }" noSelection="['':'']" />
                                </td>
                            </tr>
                            <g:render template="/jinGuanShouHou/returnGoodsConfirm"/>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="wrongReason"><g:message code="jinGuanShouHou.wrongReason.label" default="信息错误说明" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'wrongReason', 'errors')}">
                                    <g:textField name="wrongReason" value="${jinGuanShouHouInstance?.wrongReason}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="solveStatus"><g:message code="jinGuanShouHou.solveStatus.label" default="完成进度" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'solveStatus', 'errors')}">
                                    <g:select name="solveStatus" from="${com.nala.csd.SolveStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveStatusEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.solveStatus?.name() }" noSelection="['no':'未完成']"  />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="noSolveReason"><g:message code="jinGuanShouHou.noSolveReason.label" default="未完成说明" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'noSolveReason', 'errors')}">
                                    <g:textField name="noSolveReason" value="${jinGuanShouHouInstance?.noSolveReason}" />
                                </td>
                            </tr>
                           <tr class="prop">
                            <td valign="top" class="name">
                                <label for="noSolveReason"><g:message code="jinGuanShouHou.noSolveReason.label" default="退回单号" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'wuliuNo', 'errors')}">
                                <g:textField name="wuliNo" value="${jinGuanShouHouInstance?.wuliuNo}" />
                            </td>
                          </tr>
                           <input type="hidden" name="tag" value="eva">
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="创建" /></span>
                </div>
            </g:form>
        </div>
	<g:render template="/jinGuanShouHou/show_solveType"/>        
    </body>
</html>
