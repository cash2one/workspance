<tr class="coupon" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="coupon" style="display: none;">
	<td valign="top" class="name"><label>优惠券金额</label>
	</td>
    <g:if test="${jinGuanShouHouInstance?.coupon?.couponNum}"><td valign="top" class="value"><g:textField id="couponNumStr" name="couponNumStr"	value="${jinGuanShouHouInstance?.coupon?.couponNum/100 }" />(元)</td></g:if>
    <g:else><td valign="top" class="value"><g:textField name="couponNumStr"	value="" />(元)</td></g:else>
</tr>
<tr class="coupon" style="display: none;">
	<td valign="top" class="name"><label>赠送人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: jinGuanShouHouInstance.coupon, field: 'couponOperator', 'errors')}">
            <g:select id="couponOperator" name="couponOperator.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}" keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}" value="${jinGuanShouHouInstance?.coupon?.couponOperator?.id }" noSelection="['':'']" />
	</td>
</tr>
<tr class="coupon" style="display: none;">
	<td valign="top" class="name"><label>赠送时间</label>
	</td>
	<td valign="top" class="value"><input id="couponDateStr"	name="couponDateStr" type="text"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd HH:mm" date="${jinGuanShouHouInstance?.coupon?.couponDate}"/>" />
	</td>
</tr>
<tr class="coupon" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
