<tr class="refund">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label>退款金额(产品)(售后)</label>
	</td>
	<g:if test="${tmallShouhouInstance?.refund?.refundNumProduct}"><td valign="top" class="value"><g:textField name="refundNumProductStr"	value="${tmallShouhouInstance?.refund?.refundNumProduct/100 }" />(元)</td></g:if>
	<g:else><td valign="top" class="value"><g:textField name="refundNumProductStr"	value="" />(元)</td></g:else>
</tr>
<tr class="refund">
    <td valign="top" class="name"><label>退款金额(邮费)(售后)</label>
    </td>
    <g:if test="${tmallShouhouInstance?.refund?.refundNumPostage}"><td valign="top" class="value"><g:textField name="refundNumPostageStr"	value="${tmallShouhouInstance?.refund?.refundNumPostage/100 }" />(元)</td></g:if>
    <g:else><td valign="top" class="value"><g:textField name="refundNumPostageStr"	value="" />(元)</td></g:else>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label for="refundOperator">退款操作人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: refund, field: 'refundOperator', 'errors')}">
        <g:if test="${tmallShouhouInstance?.refund?.refundOperator?.id}">
            <g:select name="refundOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${tmallShouhouInstance?.refund?.refundOperator?.id }" noSelection="['':'']" />
        </g:if>
        <g:else>
            <g:select name="refundOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${userId}" noSelection="['':'']" />
        </g:else>
		</td>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label>退款时间</label>
	</td>
	<td valign="top" class="value"><input id="refundDateStr"
		name="refundDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${tmallShouhouInstance?.refund?.refundDate}"/>" />
	</td>
</tr>
<tr class="refund">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
