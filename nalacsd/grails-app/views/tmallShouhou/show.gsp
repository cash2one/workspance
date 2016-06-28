
<%@ page import="com.nala.csd.TmallShouhou" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tmallShouhou.label', default: 'TmallShouhou')}" />
        <title><g:if test="${tmallShouhouInstance.store.tp}"><g:message code="default.show.label" args="['TP店售后记录']" /></g:if><g:else><g:message code="default.show.label" args="['天猫/B2C/拍拍售后记录']" /></g:else></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:if test="${tmallShouhouInstance.store.tp}"><g:link class="list" action="list" params="[tp: 'true']"><g:message code="default.list.label" args="['TP店售后记录']" /></g:link></g:if><g:else><g:link class="list" action="list"><g:message code="default.list.label" args="['天猫/B2C/拍拍售后记录']" /></g:link></g:else></span>
            <span class="menuButton"><g:if test="${tmallShouhouInstance.store.tp}"><g:link class="create" action="create" params="[tp: 'true']"><g:message code="default.create.label" args="['TP店售后记录']" /></g:link></g:if><g:else><g:link class="create" action="create"><g:message code="default.create.label" args="['天猫/B2C/拍拍售后记录']" /></g:link></g:else></span>
        </div>
        <div class="body">
            <h1><g:if test="${tmallShouhouInstance.store.tp}"><g:message code="default.show.label" args="['TP店售后记录']" /></g:if><g:else><g:message code="default.show.label" args="['天猫/B2C/拍拍售后记录']" /></g:else></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
						<tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.dateCreated.label" default="登记时间" /></td>

                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${tmallShouhouInstance?.dateCreated}" /></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.id.label" default="Id" /></td>

                            <td valign="top" class="value">${fieldValue(bean: tmallShouhouInstance, field: "id")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.createCS.label" default="登记人" /></td>

                            <td valign="top" class="value"><g:link controller="hero" action="show" id="${tmallShouhouInstance?.createCS?.id}">${tmallShouhouInstance?.createCS?.name}</g:link></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.store.label" default="店铺" /></td>

                            <td valign="top" class="value"><g:link controller="store" action="show" id="${tmallShouhouInstance?.store?.id}">${tmallShouhouInstance?.store?.name?.encodeAsHTML()}</g:link></td>

                        </tr>


                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.userId.label" default="顾客ID" /></td>

                            <td valign="top" class="value">${fieldValue(bean: tmallShouhouInstance, field: "userId")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">订单编号</td>

                            <td valign="top" class="value">${fieldValue(bean: tmallShouhouInstance, field: "tradeId")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.questionStatus.label" default="问题类型" /></td>

                            <td valign="top" class="value"><g:link controller="questionType" action="show" id="${tmallShouhouInstance?.questionStatus?.id}">${tmallShouhouInstance?.questionStatus?.questionDescription}</g:link></td>

                        </tr>

                      <tr class="prop">
                        <td valign="top" class="name"><g:message code="tmallShouhou.questionStatus.label" default="问题类型" /></td>

                        <td valign="top" class="value"><g:link controller="questionType" action="show" id="${tmallShouhouInstance?.questionReason?.id}">${tmallShouhouInstance?.questionReason?.name}</g:link></td>

                      </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.itemDetail.label" default="商品明细" /></td>

                            <td valign="top" class="value"><textarea disabled="disabled" name="itemDetail" rows="2" cols="20">${fieldValue(bean: tmallShouhouInstance, field: "itemDetail")}</textarea></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.solveType.label" default="处理方式" /></td>

                            <td valign="top" class="value">${tmallShouhouInstance?.solveType?.name}</td>

                        </tr>

                        <g:if test="${tmallShouhouInstance?.returnGoods }">
                            <tr class="prop">
                                <td valign="top" class="name">----------</td>
                                <td valign="top" class="value">-------------------------------------------------------------------</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">收到退货明细</td>
                                <td valign="top" class="value"><textarea disabled="disabled" name="detail" rows="2" cols="20">${tmallShouhouInstance?.returnGoods?.detail}</textarea></td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">收到退货日期</td>
                                <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${tmallShouhouInstance?.returnGoods?.returnDate}"/></td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">退货快递和单号</td>
                                <td valign="top" class="value">${tmallShouhouInstance?.returnGoods?.wuliuNo }</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">是否使用</td>
                                <g:if test="${tmallShouhouInstance?.returnGoods?.used }"><td>是</td></g:if><g:else><td>否</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">邮费承担方</td>
                                <td valign="top" class="value">${tmallShouhouInstance?.returnGoods?.postageRole }</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">邮费金额</td>
                                <g:if test="${tmallShouhouInstance?.returnGoods?.postageNum }"><td valign="top" class="value">${tmallShouhouInstance?.returnGoods?.postageNum/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                        </g:if>
                        <g:if test="${tmallShouhouInstance?.resend }">
                            <tr class="prop">
                                <td valign="top" class="name">----------</td>
                                <td valign="top" class="value">-------------------------------------------------------------------</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">补发产品明细</td>
                                <td valign="top" class="value"><textarea disabled="disabled" name="goodsDetail" rows="2" cols="20">${tmallShouhouInstance?.resend?.goodsDetail}</textarea></td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">补发日期</td>
                                <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${tmallShouhouInstance?.resend?.date}"/></td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">补发单号</td>
                                <td valign="top" class="value">${tmallShouhouInstance?.resend?.wuliuNo }</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">补发地址</td>
                                <td valign="top" class="value">${tmallShouhouInstance?.resend?.address }</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">补发邮费金额</td>
                                <g:if test="${tmallShouhouInstance?.resend?.postageNum }"><td valign="top" class="value">${tmallShouhouInstance?.resend?.postageNum/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">补发人</td>
                                <td valign="top" class="value">${tmallShouhouInstance?.resend?.resender?.name}</td>
                            </tr>
                        </g:if>
                        <g:if test="${tmallShouhouInstance.refund }">
                            <tr class="prop">
                                <td valign="top" class="name">----------</td>
                                <td valign="top" class="value">-------------------------------------------------------------------</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">退款金额(产品)</td>
                                <g:if test="${tmallShouhouInstance?.refund?.refundNumProduct}"><td valign="top" class="value">${tmallShouhouInstance?.refund?.refundNumProduct/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">退款金额(邮费)</td>
                                <g:if test="${tmallShouhouInstance?.refund?.refundNumPostage}"><td valign="top" class="value">${tmallShouhouInstance?.refund?.refundNumPostage/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">退款操作人</td>
                                <td valign="top" class="value">${tmallShouhouInstance?.refund?.refundOperator?.name}</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">退款时间</td>
                                <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${tmallShouhouInstance?.refund?.refundDate}"/></td>
                            </tr>
                        </g:if>
                        <g:if test="${tmallShouhouInstance.remit }">
                            <tr class="prop">
                                <td valign="top" class="name">----------</td>
                                <td valign="top" class="value">-------------------------------------------------------------------</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">打款金额(产品)</td>
                                <g:if test="${tmallShouhouInstance?.remit?.remitNumProduct}"><td valign="top" class="value">${tmallShouhouInstance?.remit?.remitNumProduct/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">打款金额(邮费)</td>
                                <g:if test="${tmallShouhouInstance?.remit?.remitNumPostage}"><td valign="top" class="value">${tmallShouhouInstance?.remit?.remitNumPostage/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">打款金额(手续费)</td>
                                <g:if test="${tmallShouhouInstance?.remit?.remitNumCommission}"><td valign="top" class="value">${tmallShouhouInstance?.remit?.remitNumCommission/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">打款操作人</td>
                                <td valign="top" class="value">${tmallShouhouInstance?.remit?.remitOperator?.name}</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">打款时间</td>
                                <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${tmallShouhouInstance?.remit?.remitDate}"/></td>
                            </tr>
                        </g:if>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.moneyRecordType.label" default="货款操作记录" /></td>

                            <td valign="top" class="value">${tmallShouhouInstance?.moneyRecordType?.getDescription()}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.wrongReason.label" default="信息错误说明" /></td>

                            <td valign="top" class="value">${fieldValue(bean: tmallShouhouInstance, field: "wrongReason")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.solveStatus.label" default="完成进度" /></td>

                            <td valign="top" class="value">${tmallShouhouInstance?.solveStatus?.getDescription()}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.noSolveReason.label" default="未完成说明" /></td>

                            <td valign="top" class="value">${fieldValue(bean: tmallShouhouInstance, field: "noSolveReason")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tmallShouhou.wuliuNo.label" default="退回快递和单号" /></td>

                            <td valign="top" class="value">${fieldValue(bean: tmallShouhouInstance, field: "wuliuNo")}</td>

                        </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${tmallShouhouInstance?.id}" />
                    <g:if test="${tmallShouhouInstance.store.tp}">
                        <g:hiddenField name="tp" value="true"></g:hiddenField>
                    </g:if>
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
