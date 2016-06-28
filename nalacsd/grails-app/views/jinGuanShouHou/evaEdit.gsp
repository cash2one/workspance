

<%@ page import="com.nala.csd.JinGuanShouHou" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou')}" />
        <title><g:message code="default.edit.label" args="['金冠店售后登记记录_中差评页面']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">首页</a></span>
            <span class="menuButton"><g:link class="list" action="list">金冠店中差评_售后记录列表</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">新建中差评_售后记录</g:link></span>
        </div>
        <div class="body">
            <h1>编辑金冠店售后记录</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${jinGuanShouHouInstance}">
            <div class="errors">
                <g:renderErrors bean="${jinGuanShouHouInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" onsubmit="return testNull()" >
                <g:hiddenField name="id" value="${jinGuanShouHouInstance?.id}" />
                <g:hiddenField name="version" value="${jinGuanShouHouInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                  %{--<label for="contactStatus"><g:message code="jinGuanShouHou.contactStatus.label" default="联系优先级" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'contactStatus', 'errors')}">--}%
                                    %{--<g:select name="contactStatus" from="${com.nala.csd.ContactStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactStatusEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.contactStatus?.name()}" noSelection="['':'']" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                  %{--<label for="contactTimes"><g:message code="jinGuanShouHou.contactTimes.label" default="联系次数" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'contactTimes', 'errors')}">--}%
                                    %{--<g:select name="contactTimes" from="${com.nala.csd.ContactTimesEnum?.values()*.getDescription()}" keys="${com.nala.csd.ContactTimesEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.contactTimes?.name()}" noSelection="['':'']" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createCS"><g:message code="jinGuanShouHou.createCS.label" default="发起人" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'createCS', 'errors')}">
                                    <g:select name="createCS.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${jinGuanShouHouInstance.createCS.id }"  />
                                </td>
                            </tr>
                        
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                  %{--<label for="solveCS"><g:message code="jinGuanShouHou.solveCS.label" default="处理人" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'solveCS', 'errors')}">--}%
                                    %{--<g:select name="solveCS.id" from="${com.nala.csd.Hero.list()*.name}" keys="${com.nala.csd.Hero.list()*.id}" value="${jinGuanShouHouInstance?.solveCS?.id}"  noSelection="['':'']" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        %{----}%
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="itemDetail"><g:message code="jinGuanShouHou.itemDetail.label" default="商品明细" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'itemDetail', 'errors')}">
                                    <textarea name="itemDetail" rows="2" cols="20">${jinGuanShouHouInstance?.itemDetail}</textarea>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="moneyRecordType"><g:message code="jinGuanShouHou.moneyRecordType.label" default="货款操作记录" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'moneyRecordType', 'errors')}">
                                    <g:select class="moneyRecordType" name="moneyRecordType" from="${com.nala.csd.MoneyRecordTypeEnum?.values()*.getDescription()}" keys="${com.nala.csd.MoneyRecordTypeEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.moneyRecordType?.name()}" noSelection="['':'']" />
                                </td>
                            </tr>
                            <g:render template="/jinGuanShouHou/returnGoodsConfirm"/>
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
                                  <label for="questionStatus"><g:message code="jinGuanShouHou.questionStatus.label" default="问题类型" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'questionStatus', 'errors')}">
                                    <g:select class="questionStatus" name="questionStatus.id" from="${com.nala.csd.QuestionType.list()*.questionDescription}" keys="${com.nala.csd.QuestionType.list()*.id}" value="${jinGuanShouHouInstance?.questionStatus?.id}" noSelection="['':'']" />
                                </td>
                            </tr>
                            
                            <g:render template="/jinGuanShouHou/questionReason"/>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="solveStatus"><g:message code="jinGuanShouHou.solveStatus.label" default="完成进度" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'solveStatus', 'errors')}">
                                    <g:select name="solveStatus" from="${com.nala.csd.SolveStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.SolveStatusEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.solveStatus?.name()}" noSelection="['':'']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="solveType"><g:message code="jinGuanShouHou.solveType.label" default="处理方式" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'solveType', 'errors')}">
                                    <g:select class="origen" name="solveType" from="${com.nala.csd.JinTmallSolveType.list().name}" keys="${com.nala.csd.JinTmallSolveType.list().id}" value="${jinGuanShouHouInstance?.solveType?.id}" noSelection="['':'']" />
                                </td>
                            </tr>
                        	<g:if test="${jinGuanShouHouInstance?.returnGoods }"><g:render template="/jinGuanShouHou/edit_returnGoods"/></g:if><g:else><g:render template="/jinGuanShouHou/create_returnGoods"/></g:else>
                        	<g:if test="${jinGuanShouHouInstance?.resend }"><g:render template="/jinGuanShouHou/edit_resend"/></g:if><g:else><g:render template="/jinGuanShouHou/create_resend"/></g:else>
                        	<g:if test="${jinGuanShouHouInstance?.refund }"><g:render template="/jinGuanShouHou/edit_refund"/></g:if><g:else><g:render template="/jinGuanShouHou/create_refund"/></g:else>
                        	<g:if test="${jinGuanShouHouInstance?.remit }"><g:render template="/jinGuanShouHou/edit_remit"/></g:if><g:else><g:render template="/jinGuanShouHou/create_remit"/></g:else>
                            <g:if test="${jinGuanShouHouInstance?.coupon }"><g:render template="/jinGuanShouHou/edit_coupon"/></g:if><g:else><g:render template="/jinGuanShouHou/create_coupon"/></g:else>
                            %{--<tr class="prop">--}%
                                %{--<td valign="top" class="name">--}%
                                  %{--<label for="tel"><g:message code="jinGuanShouHou.tel.label" default="联系电话" /></label>--}%
                                %{--</td>--}%
                                %{--<td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'tel', 'errors')}">--}%
                                    %{--<g:textField name="tel" value="${jinGuanShouHouInstance?.tel}" />--}%
                                %{--</td>--}%
                            %{--</tr>--}%
                        
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
                                <label for="wrongReason"><g:message code="jinGuanShouHou.wrongReason.label" default="退回单号" /></label>
                             </td>
                             <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'wuliuNo', 'errors')}">
                                <g:textField name="wuliuNo" value="${jinGuanShouHouInstance?.wuliuNo}" />
                             </td>
                         </tr>

                            <script>
                            function testNull(){

                                if($("#solveType").val() == 2){
                                    //直接补发
                                    if($("#goodsDetail").val().trim() == ""){
                                        alert("补发产品明细(售后)不能为空");
                                        return false;
                                    }

                                }else if($("#solveType").val() == 3){
                                    //换货补发
                                    if($("#goodsDetail").val().trim() == ""){
                                        alert("补发产品明细(售后)不能为空");
                                        return false;
                                    }

                                    if($("#remitNumProductStr").val().trim()  == ""){
                                        alert("打款金额(产品)(售后)");
                                        return false;
                                    }
//                                     if($("#remitNumPostageStr").val().trim()  == ""){
//                                         alert("打款金额(邮费)(售后)不能为空");
//                                         return false;
//                                     }
                                }else if($("#solveType").val() == 4){
                                    //退货打款
//                                      if($("#detail").val().trim() == ""){
//                                          alert("收到退货明不能为空");
//                                          return false;
//                                      }
//                                      if($("#returnDateStr").val().trim()  == ""){
//                                          alert("收到退货日期不能为空");
//                                          return false;
//                                      }
//                                      if($("#returnWuliuNo").val().trim()  == ""){
//                                          alert("退货快递和单号不能为空");
//                                          return false;
//                                      }
//                                      if($("#postageRole").val().trim()  == ""){
//                                          alert("邮费承担方(售后)不能为空");
//                                          return false;
//                                      }
//                                      if($("#postageNumStr").val().trim()  == ""){
//                                          alert("邮费金额(售后)不能为空");
//                                          return false;
//                                      }
//


                                    if($("#remitNumProductStr").val().trim()  == ""){
                                        alert("打款金额(产品)(售后)");
                                        return false;
                                    }
//                                      if($("#remitNumPostageStr").val().trim()  == ""){
//                                          alert("打款金额(邮费)(售后)不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitNumCommissionStr").val().trim()  == ""){
//                                          alert("打款手续费不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitStatusEnum").val().trim()  == ""){
//                                          alert("状态不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitOperator").val().trim()  == ""){
//                                          alert("打款操作人不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitDateStr").val().trim()  == ""){
//                                          alert("打款时间不能为空");
//                                          return false;
//                                      }
                                    //退货退款
                                }else if($("#solveType").val() ==5){
//                                      if($("#detail").val().trim()  == ""){
//                                          alert("收到退货明不能为空");
//                                          return false;
//                                      }
//                                      if($("#returnDateStr").val().trim() == ""){
//                                          alert("收到退货日期不能为空");
//                                          return false;
//                                      }
//                                      if($("#returnWuliuNo").val().trim()  == ""){
//                                          alert("退货快递和单号不能为空");
//                                          return false;
//                                      }
//                                      if($("#postageRole").val().trim()  == ""){
//                                          alert("邮费承担方(售后)不能为空");
//                                          return false;
//                                      }
//                                      if($("#postageNumStr").val().trim()  == ""){
//                                          alert("邮费承担方(售后)不能为空");
//                                          return false;
//                                      }


                                    if($("#refundNumProductStr").val().trim()  == ""){
                                        alert("退款金额(产品)(售后)不能为空");
                                        return false;
                                    }
//                                      if($("#refundNumPostageStr").val().trim() == ""){
//                                          alert("退款金额(邮费)(售后)不能为空");
//                                          return false;
//                                      }
//                                      if($("#refundOperator").val().trim()  == ""){
//                                          alert("退款操作人不能为空");
//                                          return false;
//                                      }
//                                      if($("#refundDateStr").val().trim() == ""){
//                                          alert("退款时间不能为空");
//                                          return false;
//                                      }


//                                      if($("#remitNumProductStr").val().trim()  == ""){
//                                          alert("打款金额(产品)(售后)");
//                                          return false;
//                                      }
//                                      if($("#remitNumPostageStr").val().trim()  == ""){
//                                          alert("打款金额(邮费)(售后)不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitNumCommissionStr").val().trim() == ""){
//                                          alert("打款手续费不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitStatusEnum").val().trim() == ""){
//                                          alert("状态不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitOperator").val().trim()  == ""){
//                                          alert("打款操作人不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitDateStr").val().trim()  == ""){
//                                          alert("打款时间不能为空");
//                                          return false;
//                                      }    //直接打款
                                }else if($("#solveType").val() ==6){
                                    if($("#remitNumProductStr").val().trim()  == ""){
                                        alert("打款金额(产品)(售后)");
                                        return false;
                                    }
//                                      if($("#remitNumPostageStr").val().trim()  == ""){
//                                          alert("打款金额(邮费)(售后)不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitNumCommissionStr").val().trim()  == ""){
//                                          alert("打款手续费不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitStatusEnum").val().trim()  == ""){
//                                          alert("状态不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitOperator").val().trim()  == ""){
//                                          alert("打款操作人不能为空");
//                                          return false;
//                                      }
//                                      if($("#remitDateStr").val().trim()  == ""){
//                                          alert("打款时间不能为空");
//                                          return false;
//                                      }
                                    //直接退款
                                }else if($("#solveType").val() ==7){
                                    if($("#refundNumProductStr").val().trim()  == ""){
                                        alert("退款金额(产品)(售后)不能为空");
                                        return false;
                                    }
//                                      if($("#refundNumPostageStr").val().trim()  == ""){
//                                          alert("退款金额(邮费)(售后)不能为空");
//                                          return false;
//                                      }
//                                      if($("#refundOperator").val().trim()  == ""){
//                                          alert("退款操作人不能为空");
//                                          return false;
//                                      }
//                                      if($("#refundDateStr").val().trim() == ""){
//                                          alert("退款时间不能为空");
//                                          return false;
//                                      }
                                }
                                return true;
                            }
                        </script>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="evaUpdate" value="更新" /></span>
                </div>
            </g:form>
        </div>
        <g:render template="/jinGuanShouHou/show_solveType"/>
    </body>
</html>
