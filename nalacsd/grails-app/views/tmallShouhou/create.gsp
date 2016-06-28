

<%@ page import="com.nala.csd.Hero; com.nala.csd.TmallShouhou" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tmallShouhou.label', default: 'TmallShouhou')}" />
        <title><g:if test="${tp}"><g:message code="default.create.label" args="['TP店售后记录']" /></g:if><g:else><g:message code="default.create.label" args="['天猫/B2C/拍拍售后记录']" /></g:else></title>
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
                    }
            );

        </script>

    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:if test="${tp}"><g:link class="list" action="list" params="[tp: 'true']"><g:message code="default.list.label" args="['TP店售后记录']" /></g:link></g:if><g:else><g:link class="list" action="list"><g:message code="default.list.label" args="['天猫/B2C/拍拍售后记录']" /></g:link></g:else></span>
        </div>
        <div class="body">
            <h1><g:if test="${tp}"><g:message code="default.create.label" args="['TP店售后记录']" /></g:if><g:else><g:message code="default.create.label" args="['天猫/B2C/拍拍售后记录']" /></g:else></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tmallShouhouInstance}">
            <div class="errors">
                <g:renderErrors bean="${tmallShouhouInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="createCS"><g:message code="tmallShouhou.createCS.label" default="登记人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'createCS', 'errors')}">
                                    %{--<g:select name="createCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" value="${tmallShouhouInstance?.createCS?.id }" noSelection="['':'']"  />--}%

                                   <g:if test="${tmallShouhouInstance?.createCS}">
                                       <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${tmallShouhouInstance?.createCS?.id }"  />
                                   </g:if><g:else>
                                        <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${userId}"  />
                                    </g:else>

                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="store"><g:message code="tmallShouhou.store.label" default="店铺" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'store', 'errors')}">
                                    <g:select name="store.id" from="${stores*.name}" keys="${stores*.id}" value="${tmallShouhouInstance?.store?.id}" noSelection="['': '']" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userId"><g:message code="tmallShouhou.userId.label" default="顾客ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'userId', 'errors')}">
                                    <g:textField name="userId" value="${tmallShouhouInstance?.userId}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tradeId">订单编号</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'tradeId', 'errors')}">
                                    <g:textField name="tradeId" value="${tmallShouhouInstance?.tradeId}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="questionStatus"><g:message code="tmallShouhou.questionStatus.label" default="问题类型" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'questionStatus', 'errors')}">
                                    <g:select class="questionStatus" name="questionStatus.id" from="${com.nala.csd.QuestionType.list()*.questionDescription}" keys="${com.nala.csd.QuestionType.list()*.id}" value="${tmallShouhouInstance?.questionStatus?.id}" noSelection="['': '']" />
                                    &nbsp;&nbsp;&nbsp;&nbsp;快速选择:&nbsp;&nbsp;&nbsp;&nbsp;<input name="questionStatusDescription" type="text" id="suggest1" />
                                </td>
                            </tr>
                        <g:render template="/jinGuanShouHou/questionReason"/>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="itemDetail"><g:message code="tmallShouhou.itemDetail.label" default="商品明细" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'itemDetail', 'errors')}">
                                    <textarea name="itemDetail" rows="2" cols="20">${tmallShouhouInstance?.itemDetail}</textarea>
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="solveType"><g:message code="tmallShouhou.solveType.label" default="处理方式" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'solveType', 'errors')}">
                                    <g:select class="origen" name="solveType" from="${com.nala.csd.JinTmallSolveType.list().name}" keys="${com.nala.csd.JinTmallSolveType.list().id}" value="${params.solveType}" noSelection="['':'']"  />
                                </td>
                            </tr>

                            <g:render template="/tmallShouhou/create_returnGoods"/>
                            <g:render template="/tmallShouhou/create_resend"/>
                            <g:render template="/tmallShouhou/create_refund"/>
                            <g:render template="/tmallShouhou/create_remit"/>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="moneyRecordType"><g:message code="tmallShouhou.moneyRecordType.label" default="货款操作记录" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'moneyRecordType', 'errors')}">
                                    <g:select name="moneyRecordType" from="${com.nala.csd.MoneyRecordTypeEnum?.values()*.getDescription()}" keys="${com.nala.csd.MoneyRecordTypeEnum?.values()*.name()}" value="${tmallShouhouInstance?.moneyRecordType?.name() }" noSelection="['':'']" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="wrongReason"><g:message code="tmallShouhou.wrongReason.label" default="信息错误说明" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'wrongReason', 'errors')}">
                                    <g:textField name="wrongReason" value="${tmallShouhouInstance?.wrongReason}" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="solveStatus"><g:message code="tmallShouhou.solveStatus.label" default="完成进度" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'solveStatus', 'errors')}">
                                    <g:select name="solveStatus" from="${com.nala.csd.SolveStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveStatusEnum?.values()*.name()}" value="${tmallShouhouInstance?.solveStatus?.name() }" noSelection="['':'']"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="noSolveReason"><g:message code="tmallShouhou.noSolveReason.label" default="未完成说明" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'noSolveReason', 'errors')}">
                                    <g:textField name="noSolveReason" value="${tmallShouhouInstance?.noSolveReason}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="wuliuNo"><g:message code="tmallShouhou.wuliuNo.label" default="退回快递和单号" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tmallShouhouInstance, field: 'wuliuNo', 'errors')}">
                                    <g:textField name="wuliuNo" value="${tmallShouhouInstance?.wuliuNo}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    <g:render template="/jinGuanShouHou/show_solveType"/>
    </body>
</html>
