<tr class="remit">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label>打款金额(产品)(售后)</label>
	</td>
	<g:if test="${tmallShouhouInstance?.remit?.remitNumProduct}"><td valign="top" class="value"><g:textField name="remitNumProductStr" value="${tmallShouhouInstance?.remit?.remitNumProduct/100 }" />(元)</td></g:if>
	<g:else><td valign="top" class="value"><g:textField name="remitNumProductStr" value="" />(元)</td></g:else>
</tr>
<tr class="remit">
    <td valign="top" class="name"><label>打款金额(邮费)(售后)</label>
    </td>
    <g:if test="${tmallShouhouInstance?.remit?.remitNumPostage}"><td valign="top" class="value"><g:textField name="remitNumPostageStr" value="${tmallShouhouInstance?.remit?.remitNumPostage/100 }" />(元)</td></g:if>
    <g:else><td valign="top" class="value"><g:textField name="remitNumPostageStr" value="" />(元)</td></g:else>
</tr>
<tr class="remit">
    <td valign="top" class="name"><label>打款手续费</label>
    </td>
    <g:if test="${tmallShouhouInstance?.remit?.remitNumCommission}"><td valign="top" class="value"><g:textField name="remitNumCommissionStr" value="${tmallShouhouInstance?.remit?.remitNumCommission/100 }" />(元)</td></g:if>
    <g:else><td valign="top" class="value"><g:textField name="remitNumCommissionStr" value="" />(元)</td></g:else>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label for="remitOperator">打款操作人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: remit, field: 'remitOperator', 'errors')}">
    <g:if test="${tmallShouhouInstance?.remit?.remitOperator?.id}">
		<g:select name="remitOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
			keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
			value="${tmallShouhouInstance?.remit?.remitOperator?.id }" noSelection="['':'']" />
	</g:if>
        </td>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label>打款时间</label>
	</td>
	<td valign="top" class="value"><input id="remitDateStr"
		name="remitDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${tmallShouhouInstance?.remit?.remitDate}"/>" />
	</td>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
