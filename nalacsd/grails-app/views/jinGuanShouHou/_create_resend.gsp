<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">
		-------------------------------------------------------------------</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发产品明细(售后)</label>
	</td>
	<td valign="top" class="value"><textarea id="goodsDetail" name="goodsDetail" rows="2" cols="20">${jinGuanShouHouInstance?.resend?.goodsDetail }</textarea>
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发日期</label>
	</td>
	<td valign="top" class="value"><input id="bufaDateStr"
		name="bufaDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd" date="${jinGuanShouHouInstance?.resend?.date}"/>" />
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发单号</label>
	</td>
	<td valign="top" class="value"><g:textField id="bufaWuliuNo" name="bufaWuliuNo"
			value="${jinGuanShouHouInstance?.resend?.wuliuNo }" />
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发地址(售后)</label>
	</td>
	<td valign="top" class="value"><input type="text" id="address"
		name="address" value="${jinGuanShouHouInstance?.resend?.address }" size=100 />
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发邮费金额</label>
	</td>
	<td valign="top" class="value"><g:textField id="bufaPostageNumStr" name="bufaPostageNumStr"
			value="${jinGuanShouHouInstance?.resend?.postageNum }" />(元)</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label for="resender"><g:message
				code="jinGuanShouHou.solveCS.label" default="补发人" />
	</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: resend, field: 'resender', 'errors')}">
        <g:if test="${jinGuanShouHouInstance?.resend?.resender?.id}">
            <g:select id="resenderUser" name="resender.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${jinGuanShouHouInstance?.resend?.resender?.id }" noSelection="['':'']" />
        </g:if>
		<g:else>
            <g:select id="resenderUser" name="resender.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                       noSelection="['':'']" />
		</g:else>
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">
		-------------------------------------------------------------------</td>
</tr>