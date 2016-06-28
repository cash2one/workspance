<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>打款金额(产品)(售后)</label>
	</td>
	<td valign="top" class="value"><g:textField name="remitNumProductStr"
			value="${tmallShouhouInstance?.remit?.remitNumProduct }" />(元)</td>
</tr>
<tr class="remit" style="display: none;">
    <td valign="top" class="name"><label>打款金额(邮费)(售后)</label>
    </td>
    <td valign="top" class="value"><g:textField name="remitNumPostageStr"
                                                value="${tmallShouhouInstance?.remit?.remitNumPostage }" />(元)</td>
</tr>
<tr class="remit" style="display: none;">
    <td valign="top" class="name"><label>打款手续费</label>
    </td>
    <td valign="top" class="value"><g:textField name="remitNumCommissionStr"
                                                value="${tmallShouhouInstance?.remit?.remitNumCommission }" />(元)</td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label for="remitOperator">打款操作人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: remit, field: 'remitOperator', 'errors')}">
        <g:if test="${tmallShouhouInstance?.remit?.remitOperator?.id}">
            <g:select name="remitOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${tmallShouhouInstance?.remit?.remitOperator?.id }" noSelection="['':'']" />
        </g:if>
        <g:else>
            <g:select name="remitOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${userId}" noSelection="['':'']" />
        </g:else>


	</td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>打款时间</label>
	</td>
	<td valign="top" class="value"><input id="remitDateStr"
		name="remitDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${tmallShouhouInstance?.remit?.remitDate}"/>" />
	</td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
