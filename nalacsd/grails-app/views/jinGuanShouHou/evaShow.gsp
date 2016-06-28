
<%@ page import="com.nala.csd.JinGuanShouHou" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou')}" />
        <title><g:message code="default.show.label" args="['金冠店售后登记记录_中差评页面']" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">首页</a></span>
            <span class="menuButton"><g:link class="list" action="evaList">金冠店中差评_售后记录列表</g:link></span>
            <span class="menuButton"><g:link class="create" action="evaCreate">新建中差评_售后记录</g:link></span>
        </div>
        <div class="body">
            <h1>金冠店售后记录详情</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "id")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.dateCreated.label" default="登记时间" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.dateCreated}"/></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.createCS.label" default="发起人" /></td>
                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.createCS?.name.encodeAsHTML()}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.userId.label" default="顾客ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "userId")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.tradeId.label" default="订单编号" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "tradeId")}</td>
                            
                        </tr>
                    
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="jinGuanShouHou.tel.label" default="联系电话" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "tel")}</td>--}%
                            %{----}%
                        %{--</tr>--}%
                    %{----}%
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="jinGuanShouHou.solveCS.label" default="处理人" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${jinGuanShouHouInstance?.solveCS?.name}</td>--}%
                            %{----}%
                        %{--</tr>--}%
                    
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="jinGuanShouHou.zhidingTime.label" default="指定时间" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "zhidingTime")}</td>--}%
                            %{----}%
                        %{--</tr>--}%
                    %{----}%
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="jinGuanShouHou.contactStatus.label" default="联系优先级" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${jinGuanShouHouInstance?.contactStatus?.getDescription()}</td>--}%
                            %{----}%
                        %{--</tr>--}%

                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="jinGuanShouHou.contactTime.label" default="联系时间" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "contactTime")}</td>--}%
                            %{----}%
                        %{--</tr>--}%
                    %{----}%
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="jinGuanShouHou.contactTimes.label" default="联系次数" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${jinGuanShouHouInstance?.contactTimes?.getDescription()}</td>--}%
                            %{----}%
                        %{--</tr>--}%
                        %{----}%
                        %{--<g:if test="${jinGuanShouHouInstance.lianxiJiange >= 0 }">--}%
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name">联系间隔</td>--}%
                            %{--<td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "lianxiJiange")}分钟</td>--}%
                        %{--</tr>--}%
                        %{--</g:if>--}%
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.itemDetail.label" default="商品明细" /></td>
                            
                            <td valign="top" class="value"><textarea disabled="disabled" name="itemDetail" rows="2" cols="20">${fieldValue(bean: jinGuanShouHouInstance, field: "itemDetail")}</textarea></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.moneyRecordType.label" default="货款操作记录" /></td>
                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.moneyRecordType?.getDescription()}</td>
                            
                        </tr>
                    
                        <g:if test="${jinGuanShouHouInstance.confirms}">
                            <tr class="prop">
                                <td valign="top" class="name">收到退货待确认项</td>
                                <td valign="top" class="value">
                                <g:each in="${jinGuanShouHouInstance.confirms}" var="confirm" status="index">
                                    ${index + 1}: ${confirm.name}&nbsp;&nbsp;&nbsp;&nbsp;
                                </g:each>
                                </td>
                            </tr>
                        </g:if>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.questionStatus.label" default="问题类型" /></td>

                            <td valign="top" class="value">${jinGuanShouHouInstance?.questionStatus?.questionDescription}</td>

                        </tr>
                        
                        <g:if test="${jinGuanShouHouInstance?.questionReason}">
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.questionReason.label" default="问题类型" /></td>
                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.questionReason?.name}</td>
                            
                        </tr>
                        </g:if>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.solveStatus.label" default="完成进度" /></td>
                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.solveStatus?.getDescription()}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.noSolveReason.label" default="未完成说明" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "noSolveReason")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.solveType.label" default="处理方式" /></td>
                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.solveType?.name}</td>
                            
                        </tr>
                        
                        <g:if test="${jinGuanShouHouInstance?.returnGoods }">
                        <tr class="prop">
                            <td valign="top" class="name">----------</td>
                            <td valign="top" class="value">-------------------------------------------------------------------</td>
                        </tr>                        
                        <tr class="prop">
                            <td valign="top" class="name">收到退货明细</td>
                            <td valign="top" class="value"><textarea disabled="disabled" name="detail" rows="2" cols="20">${jinGuanShouHouInstance?.returnGoods?.detail}</textarea></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">收到退货日期</td>
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.returnGoods?.returnDate}"/></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">退货快递和单号</td>
                            <td valign="top" class="value">${jinGuanShouHouInstance?.returnGoods?.wuliuNo }</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">是否使用</td>
                            <g:if test="${jinGuanShouHouInstance?.returnGoods?.used }"><td>是</td></g:if><g:else><td>否</td></g:else>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">邮费承担方</td>
                            <td valign="top" class="value">${jinGuanShouHouInstance?.returnGoods?.postageRole }</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">邮费金额</td>
                            <g:if test="${jinGuanShouHouInstance?.returnGoods?.postageNum }"><td valign="top" class="value">${jinGuanShouHouInstance?.returnGoods?.postageNum/100 }(元)</td></g:if>
                            <g:else><td valign="top" class="value">(元)</td></g:else>                            
                        </tr>
                        </g:if>
                        <g:if test="${jinGuanShouHouInstance?.resend }">
                        <tr class="prop">
                            <td valign="top" class="name">----------</td>
                            <td valign="top" class="value">-------------------------------------------------------------------</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">补发产品明细</td>
                            <td valign="top" class="value"><textarea disabled="disabled" name="goodsDetail" rows="2" cols="20">${jinGuanShouHouInstance?.resend?.goodsDetail}</textarea></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">补发日期</td>
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${jinGuanShouHouInstance?.resend?.date}"/></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">补发单号</td>
                            <td valign="top" class="value">${jinGuanShouHouInstance?.resend?.wuliuNo }</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">补发地址</td>
                            <td valign="top" class="value">${jinGuanShouHouInstance?.resend?.address }</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">补发邮费金额</td>
                            <g:if test="${jinGuanShouHouInstance?.resend?.postageNum }"><td valign="top" class="value">${jinGuanShouHouInstance?.resend?.postageNum/100 }(元)</td></g:if>
							<g:else><td valign="top" class="value">(元)</td></g:else>                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">补发人</td>                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.resend?.resender?.name}</td>                            
                        </tr>
                        </g:if>
                        <g:if test="${jinGuanShouHouInstance.refund }">
                        <tr class="prop">
                            <td valign="top" class="name">----------</td>
                            <td valign="top" class="value">-------------------------------------------------------------------</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">退款金额(产品)</td>
                            <g:if test="${jinGuanShouHouInstance?.refund?.refundNumProduct}"><td valign="top" class="value">${jinGuanShouHouInstance?.refund?.refundNumProduct/100 }(元)</td></g:if>
                            <g:else><td valign="top" class="value">(元)</td></g:else>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">退款金额(邮费)</td>
                            <g:if test="${jinGuanShouHouInstance?.refund?.refundNumPostage}"><td valign="top" class="value">${jinGuanShouHouInstance?.refund?.refundNumPostage/100 }(元)</td></g:if>
                            <g:else><td valign="top" class="value">(元)</td></g:else>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">退款操作人</td>                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.refund?.refundOperator?.name}</td>                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">退款时间</td>
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.refund?.refundDate}"/></td>
                        </tr>
                        </g:if>
                        <g:if test="${jinGuanShouHouInstance.remit }">
                        <tr class="prop">
                            <td valign="top" class="name">----------</td>
                            <td valign="top" class="value">-------------------------------------------------------------------</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">打款金额(产品)</td>
                            <g:if test="${jinGuanShouHouInstance?.remit?.remitNumProduct}"><td valign="top" class="value">${jinGuanShouHouInstance?.remit?.remitNumProduct/100 }(元)</td></g:if>
                            <g:else><td valign="top" class="value">(元)</td></g:else>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">打款金额(邮费)</td>
                            <g:if test="${jinGuanShouHouInstance?.remit?.remitNumPostage}"><td valign="top" class="value">${jinGuanShouHouInstance?.remit?.remitNumPostage/100 }(元)</td></g:if>
                            <g:else><td valign="top" class="value">(元)</td></g:else>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">打款金额(手续费)</td>
                            <g:if test="${jinGuanShouHouInstance?.remit?.remitNumCommission}"><td valign="top" class="value">${jinGuanShouHouInstance?.remit?.remitNumCommission/100 }(元)</td></g:if>
                            <g:else><td valign="top" class="value">(元)</td></g:else>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">状态</td>
                            <td valign="top" class="value">${jinGuanShouHouInstance?.remit?.remitStatusEnum?.description}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">打款操作人</td>                            
                            <td valign="top" class="value">${jinGuanShouHouInstance?.remit?.remitOperator?.name}</td>                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">打款时间</td>
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.remit?.remitDate}"/></td>
                        </tr>
                        </g:if>
                        <g:if test="${jinGuanShouHouInstance.coupon }">
                            <tr class="prop">
                                <td valign="top" class="name">----------</td>
                                <td valign="top" class="value">-------------------------------------------------------------------</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">优惠券金额</td>
                                <g:if test="${jinGuanShouHouInstance?.coupon?.couponNum}"><td valign="top" class="value">${jinGuanShouHouInstance?.coupon?.couponNum/100 }(元)</td></g:if>
                                <g:else><td valign="top" class="value">(元)</td></g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">赠送人</td>
                                <td valign="top" class="value">${jinGuanShouHouInstance?.coupon?.couponOperator?.name}</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">赠送时间</td>
                                <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.coupon?.couponDate}"/></td>
                            </tr>
                        </g:if>
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="jinGuanShouHou.solveTime.label" default="解决时间" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "solveTime")}</td>--}%
                            %{----}%
                        %{--</tr>--}%
                        
                        %{--<g:if test="${jinGuanShouHouInstance.jiejueJiange >= 0 }">--}%
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name">解决间隔</td>--}%
                            %{--<td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "jiejueJiange")}分钟</td>--}%
                        %{--</tr>--}%
                        %{--</g:if>--}%
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="jinGuanShouHou.wrongReason.label" default="信息错误说明" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "wrongReason")}</td>
                            
                        </tr>
                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="jinGuanShouHou.wrongReason.label" default="退回单号" /></td>

                        <td valign="top" class="value">${fieldValue(bean: jinGuanShouHouInstance, field: "wuliuNo")}</td>

                    </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${jinGuanShouHouInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="evaEdit" value="编辑" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="evaDelete" value="删除" onclick="return confirm('你确定吗?');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
