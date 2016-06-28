<tr class="returnGoodsConfirm" style="display: none;">
    <td valign="top" class="name">
        <label for="returnGoodsConfirm">收到退货待确认项</label>
    </td>
    <td valign="top" class="value">
        <g:each in="${com.nala.csd.jinGuanShouHou.ReturnGoodsConfirm.list()}" var="confirm" status="index">
            <input type="checkbox" name="returnGoodsConfirmId" value="${confirm.id}" ${jinGuanShouHouInstance?.confirms?.contains(confirm) ? ' checked="checked"' : ''} />&nbsp;${confirm.name}&nbsp;
        </g:each>
    </td>
</tr>
