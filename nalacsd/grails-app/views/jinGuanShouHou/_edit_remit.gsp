<tr class="remit">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label>打款金额(产品)(售后)</label>
	</td>
	<g:if test="${jinGuanShouHouInstance?.remit?.remitNumProduct}"><td valign="top" class="value"><g:textField name="remitNumProductStr" id="remitNumProductStr" value="${jinGuanShouHouInstance?.remit?.remitNumProduct/100 }" />(元)</td></g:if>
	<g:else><td valign="top" class="value"><g:textField name="remitNumProductStr" value="" />(元)</td></g:else>
</tr>
<tr class="remit">
    <td valign="top" class="name"><label>打款金额(邮费)(售后)</label>
    </td>
    <g:if test="${jinGuanShouHouInstance?.remit?.remitNumPostage}"><td valign="top" class="value"><g:textField name="remitNumPostageStr" id="remitNumPostageStr" value="${jinGuanShouHouInstance?.remit?.remitNumPostage/100 }" />(元)</td></g:if>
    <g:else><td valign="top" class="value"><g:textField name="remitNumPostageStr" value="" />(元)</td></g:else>
</tr>
<tr class="remit">
    <td valign="top" class="name"><label>打款手续费</label>
    </td>
    <g:if test="${jinGuanShouHouInstance?.remit?.remitNumCommission}"><td valign="top" class="value"><g:textField name="remitNumCommissionStr" id="remitNumCommissionStr" value="${jinGuanShouHouInstance?.remit?.remitNumCommission/100 }" />(元)</td></g:if>
    <g:else><td valign="top" class="value"><g:textField name="remitNumCommissionStr" value="" />(元)</td></g:else>
</tr>
<tr class="remit" style="display: none;">
    <td valign="top" class="name"><label>状态</label>
    </td>
    <td valign="top" class="value">
        <g:select name="remitStatusEnum" id="remitStatusEnum" from="${com.nala.csd.jinGuanShouHou.RemitStatusEnum?.values()*.getDescription()}" keys="${com.nala.csd.jinGuanShouHou.RemitStatusEnum?.values()*.name()}" value="${jinGuanShouHouInstance?.remit?.remitStatusEnum?.name() }" noSelection="['':'']" />
    </td>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label for="remitOperator">打款操作人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: remit, field: 'remitOperator', 'errors')}">
        <g:if test="${jinGuanShouHouInstance?.remit?.remitOperator?.id }">
            <g:select name="remitOperator.id" id="remitOperator" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${jinGuanShouHouInstance?.remit?.remitOperator?.id }" noSelection="['':'']" />
        </g:if>
        <g:else>
            <g:select name="remitOperator.id" id="remitOperator" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${userId}" noSelection="['':'']" />
        </g:else>
            </td>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label>打款时间</label>
	</td>
	<td valign="top" class="value"><input id="remitDateStr"
		name="remitDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.remit?.remitDate}"/>" />
	</td>
</tr>
<tr class="remit">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
