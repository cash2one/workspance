<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>----------</label></td>
	<td valign="top" class="value">-------------------------------------------------------------------</td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>收到退货明细</label></td>
	<td valign="top" class="value"><textarea id="detail" name="detail" rows="2" cols="20">${jinGuanShouHouInstance?.returnGoods?.detail }</textarea></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>收到退货日期</label></td>
	<td valign="top" class="value"><input id="returnDateStr"
		name="returnDateStr" type="text"
		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 160px"
		value="<g:formatDate format="yyyy-MM-dd" date="${jinGuanShouHouInstance?.returnGoods?.returnDate}"/>" />
	</td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>退货快递和单号</label></td>
	<td valign="top" class="value"><g:textField name="returnWuliuNo" id="returnWuliuNo"
			value="${jinGuanShouHouInstance?.returnGoods?.wuliuNo }" /></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>是否使用</label></td>
	<td valign="top" class="value"><g:checkBox name="used"
			value="${jinGuanShouHouInstance?.returnGoods?.used }" /></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>邮费承担方(售后)</label></td>
	<td valign="top" class="value"><g:textField name="postageRole"  id="postageRole"
			value="${jinGuanShouHouInstance?.returnGoods?.postageRole }" /></td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>邮费金额(售后)</label></td>
	<td valign="top" class="value"><g:textField name="postageNumStr"  id="postageNumStr"
			value="${jinGuanShouHouInstance?.returnGoods?.postageNum }" />(元)</td>
</tr>
<tr class="returnGoods" style="display: none;">
	<td valign="top" class="name"><label>----------</label></td>
	<td valign="top" class="value">
		-------------------------------------------------------------------</td>
</tr>