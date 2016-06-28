<%
usercontent=request.Form("usercontent")
str="可以通过Ctrl+A 然后复制到excel里<table border=1 id='userlist'>"
str=str&"<tbody >"
str=str&"    <tr>"
str=str&"      <td>应聘岗位</td>"
str=str&"      <td>姓名</td>"
str=str&"      <td>性别</td>"
str=str&"      <td>最高学历</td>"
str=str&"      <td>手机</td>"
str=str&"      <td>邀约记录</td>"
str=str&"      <td>初试记录</td>"
str=str&"      <td>复试记录</td>"
str=str&"      <td>报到记录</td>"
str=str&"      <td>简历来源</td>"
str=str&"      <td>录入时间</td>"
str=str&"      <td>面谈时间</td>"
str=str&"      <td>录入者</td>"
str=str&"      <td>所有者</td>"
str=str&"    </tr>"
str=str&usercontent
str=str&"</tbody>"
str=str&"</table>"

response.Write(str)
%>
