<%
usercontent=request.Form("usercontent")
str="����ͨ��Ctrl+A Ȼ���Ƶ�excel��<table border=1 id='userlist'>"
str=str&"<tbody >"
str=str&"    <tr>"
str=str&"      <td>ӦƸ��λ</td>"
str=str&"      <td>����</td>"
str=str&"      <td>�Ա�</td>"
str=str&"      <td>���ѧ��</td>"
str=str&"      <td>�ֻ�</td>"
str=str&"      <td>��Լ��¼</td>"
str=str&"      <td>���Լ�¼</td>"
str=str&"      <td>���Լ�¼</td>"
str=str&"      <td>������¼</td>"
str=str&"      <td>������Դ</td>"
str=str&"      <td>¼��ʱ��</td>"
str=str&"      <td>��̸ʱ��</td>"
str=str&"      <td>¼����</td>"
str=str&"      <td>������</td>"
str=str&"    </tr>"
str=str&usercontent
str=str&"</tbody>"
str=str&"</table>"

response.Write(str)
%>
