<tr class="refund" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="refund" style="display: none;">
	<td valign="top" class="name"><label>退款金额(产品)(售后)</label>
	</td>
	<td valign="top" class="value"><g:textField id="refundNumProductStr" name="refundNumProductStr"
			value="${jinGuanShouHouInstance?.refund?.refundNumProduct }" />(元)</td>
</tr>
<tr class="refund" style="display: none;">
    <td valign="top" class="name"><label>退款金额(邮费)(售后)</label>
    </td>
    <td valign="top" class="value"><g:textField id="refundNumPostageStr" name="refundNumPostageStr"
                                                value="${jinGuanShouHouInstance?.refund?.refundNumPostage }" />(元)</td>
</tr>
<tr class="refund" style="display: none;">
	<td valign="top" class="name"><label for="refundOperator">退款操作人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: refund, field: 'refundOperator', 'errors')}">
        <g:if test="${jinGuanShouHouInstance?.refund?.refundOperator?.id }">
            <g:select id="refundOperator" name="refundOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${jinGuanShouHouInstance?.refund?.refundOperator?.id }" noSelection="['':'']" />
        </g:if>
		<g:else>
            <g:select id="refundOperator" name="refundOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                       noSelection="['':'']" />
		</g:else>
	</td>
</tr>
<tr class="refund" style="display: none;">
	<td valign="top" class="name"><label>退款时间</label>
	</td>
	<td valign="top" class="value"><input id="refundDateStr"
		name="refundDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.refund?.refundDate}"/>" />
	</td>
</tr>
<tr class="refund" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
