<tr class="questionReason" style="display: none;">
    <td valign="top" class="name">
        <label for="questionReason"><g:message code="jinGuanShouHou.questionReason.label" default="问题原因" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: jinGuanShouHouInstance, field: 'questionReason', 'errors')}">
        <g:if test="${request.getRequestURI().split('/')[2] == 'jinGuanShouHou'}">
            <g:select name="questionReason.id" from="${com.nala.csd.jinGuanShouHou.QuestionReason.list()*.name}" keys="${com.nala.csd.jinGuanShouHou.QuestionReason.list()*.id}" value="${jinGuanShouHouInstance?.questionReason?.id }" noSelection="['':'']" />
        </g:if>
        <g:else>
            <g:select name="questionReason.id" from="${com.nala.csd.jinGuanShouHou.QuestionReason.list()*.name}" keys="${com.nala.csd.jinGuanShouHou.QuestionReason.list()*.id}" value="${tmallShouhouInstance?.questionReason?.id }" noSelection="['':'']" />
        </g:else>
    </td>
</tr>