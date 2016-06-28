<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>----------</label></td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>收到退货明细</label></td>
	<td valign="top" class="value"><textarea name="detail" rows="2" cols="20">${tmallShouhouInstance?.returnGoods?.detail }</textarea></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>收到退货日期</label></td>
	<td valign="top" class="value"><input id="returnDateStr"
		name="returnDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd" date="${tmallShouhouInstance?.returnGoods?.returnDate}"/>" />
	</td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>退货快递和单号</label></td>
	<td valign="top" class="value"><g:textField name="returnWuliuNo"
			value="${tmallShouhouInstance?.returnGoods?.wuliuNo }" /></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>是否使用</label></td>
	<td valign="top" class="value"><g:checkBox name="used"
			value="${tmallShouhouInstance?.returnGoods?.used }" /></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>邮费承担方(售后)</label></td>
	<td valign="top" class="value"><g:textField name="postageRole"
			value="${tmallShouhouInstance?.returnGoods?.postageRole }" /></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>邮费金额(售后)</label></td>
	<td valign="top" class="value"><g:textField name="postageNumStr"
			value="${tmallShouhouInstance?.returnGoods?.postageNum }" />(元)</td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>----------</label></td>
	<td valign="top" class="value">
		-------------------------------------------------------------------</td>
</tr>