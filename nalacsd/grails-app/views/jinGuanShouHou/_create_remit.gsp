<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>打款金额(产品)(售后)</label>
	</td>
	<td valign="top" class="value"><g:textField id="remitNumProductStr" name="remitNumProductStr" value="${jinGuanShouHouInstance?.remit?.remitNumProduct }" />(元)</td>
</tr>
<tr class="remit" style="display: none;">
    <td valign="top" class="name"><label>打款金额(邮费)(售后)</label>
    </td>
    <td valign="top" class="value"><g:textField id="remitNumPostageStr" name="remitNumPostageStr" value="${jinGuanShouHouInstance?.remit?.remitNumPostage }" />(元)</td>
</tr>
<tr class="remit" style="display: none;">
    <td valign="top" class="name"><label>打款手续费</label>
    </td>
    <td valign="top" class="value"><g:textField  id="remitNumCommissionStr" name="remitNumCommissionStr" value="${jinGuanShouHouInstance?.remit?.remitNumCommission }" />(元)</td>
</tr>
<tr class="remit" style="display: none;">
    <td valign="top" class="name"><label>状态</label>
    </td>
    <td valign="top" class="value">
        <g:select name="remitStatusEnum" from="${com.nala.csd.jinGuanShouHou.RemitStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.jinGuanShouHou.RemitStatusEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.remit?.remitStatusEnum?.name() }" noSelection="['':'']" />
    </td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label for="remitOperator">打款操作人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: remit, field: 'remitOperator', 'errors')}">
        <g:if test="${jinGuanShouHouInstance?.remit?.remitOperator?.id }">
            <g:select id="remitOperator" name="remitOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${jinGuanShouHouInstance?.remit?.remitOperator?.id }" noSelection="['':'']" />
        </g:if>
        <g:else>
            <g:select id="remitOperator" name="remitOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"  noSelection="['':'']" />
        </g:else>
	</td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>打款时间</label>
	</td>
	<td valign="top" class="value"><input id="remitDateStr"	name="remitDateStr" type="text"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.remit?.remitDate}"/>" />
	</td>
</tr>
<tr class="remit" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
