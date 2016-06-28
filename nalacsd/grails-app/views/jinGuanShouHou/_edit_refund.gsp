<tr class="refund">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label>退款金额(产品)(售后)</label>
	</td>
	<g:if test="${jinGuanShouHouInstance?.refund?.refundNumProduct}"><td valign="top" class="value"><g:textField id="refundNumProductStr"	name="refundNumProductStr"	value="${jinGuanShouHouInstance?.refund?.refundNumProduct/100 }" />(元)</td></g:if>
	<g:else><td valign="top" class="value"><g:textField name="refundNumProductStr"	value="" />(元)</td></g:else>
</tr>
<tr class="refund">
    <td valign="top" class="name"><label>退款金额(邮费)(售后)</label>
    </td>
    <g:if test="${jinGuanShouHouInstance?.refund?.refundNumPostage}"><td valign="top" class="value"><g:textField id="refundNumPostageStr" name="refundNumPostageStr"	value="${jinGuanShouHouInstance?.refund?.refundNumPostage/100 }" />(元)</td></g:if>
    <g:else><td valign="top" class="value"><g:textField name="refundNumPostageStr"	value="" />(元)</td></g:else>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label for="refundOperator">退款操作人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: refund, field: 'refundOperator', 'errors')}">
            <g:select  name="refundOperator.id" id="refundOperator" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${jinGuanShouHouInstance?.refund?.refundOperator?.id }" noSelection="['':'']" />
	</td>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label>退款时间</label>
	</td>
	<td valign="top" class="value"><input id="refundDateStr"
		name="refundDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.refund?.refundDate}"/>" />
	</td>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
