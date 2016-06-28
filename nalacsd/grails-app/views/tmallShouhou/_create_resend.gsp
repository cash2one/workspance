<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">
		-------------------------------------------------------------------</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发产品明细(售后)</label>
	</td>
	<td valign="top" class="value"><textarea name="goodsDetail" rows="2" cols="20">${tmallShouhouInstance?.resend?.goodsDetail }</textarea>
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发日期</label>
	</td>
	<td valign="top" class="value"><input id="bufaDateStr"
		name="bufaDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd" date="${tmallShouhouInstance?.resend?.date}"/>" />
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发单号</label>
	</td>
	<td valign="top" class="value"><g:textField name="bufaWuliuNo"
			value="${tmallShouhouInstance?.resend?.wuliuNo }" />
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发地址(售后)</label>
	</td>
	<td valign="top" class="value"><input type="text" id="address"
		name="address" value="${tmallShouhouInstance?.resend?.address }" size=100 />
	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>补发邮费金额</label>
	</td>
	<td valign="top" class="value"><g:textField name="bufaPostageNumStr"
			value="${tmallShouhouInstance?.resend?.postageNum }" />(元)</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label for="resender">补发人</label></td>
	<td valign="top"
		class="value ${hasErrors(bean: resend, field: 'resender', 'errors')}">
        <g:if test="${tmallShouhouInstance?.resend?.resender?.id}">
            <g:select name="resender.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${tmallShouhouInstance?.resend?.resender?.id }" noSelection="['':'']" />
        </g:if>
        <g:else>
            <g:select name="resender.id" from="${com.nala.csd.Hero.findAllByAccountLocked(false)*.name}"
                      keys="${com.nala.csd.Hero.findAllByAccountLocked(false)*.id}"
                      value="${userId}" noSelection="['':'']" />
        </g:else>

	</td>
</tr>
<tr class="resend" style="display: none;">
	<td valign="top" class="name"><label>----------</label>
	</td>
	<td valign="top" class="value">
		-------------------------------------------------------------------</td>
</tr>