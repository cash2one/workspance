<%
url1=LCASE(request.ServerVariables("HTTP_REFERER"))
'jm=request.Cookies("jm")
'str=request.Cookies("str")
nn=instr(url1,"baojia_save_test")
if nn>0 then
%>
uncompile();
function uncompile() 
    {  
	var cc=1;
	var ns=0;
	var v=0;
	ss=mstr;
	document.write("<br>"+ss);
	for(var i=0;i<nst.length;i++){ 
	if (mstr.substring(cc, cc+1)==nst.substring(i,i+1)){
	var mm=(cc-ns);
	if (mm<1){mm=1;}
	ss=ss.substr(0,mm)+ss.substring(mm+1, ss.length);
	ns++;
	}
	cc=cc+2;
	}
	document.write ("<br>nns="+ss.length);
	ss=compjb(ss);
	document.write("<br>���ܣ�<br>"+ss);
	document.write("<br><%=url1%><br>");
	document.write("<br><%=nn%><br>");
}

function compjb(bb)
{
//aa=escape(ncontent)
//bb=unescape(aa)
text=bb.replace(/%zz91/g,"&nbsp;");
text = text.replace(/%m1yt/g, "<");
text = text.replace(/%cs8t/g, ">");
text = text.replace(/%m2t/g, "td");
text = text.replace(/%91d/g, "TD");
text = text.replace(/%z91r/g, "tr");
text = text.replace(/%z91c/g, "tr");
text = text.replace(/%zblea/g, "table");
text = text.replace(/%zble/g, "table");
text = text.replace(/%26/g, "&");
text = text.replace(/%3D/g, "=");
text = text.replace(/%22/g, "'");
return text;
}
<%else%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<HTML><HEAD><TITLE>�޷��ҵ���ҳ</TITLE>
<META HTTP-EQUIV="Content-Type" Content="text/html; charset=GB2312">
<STYLE type="text/css">
  BODY { font: 9pt/12pt ���� }
  H1 { font: 12pt/15pt ���� }
  H2 { font: 9pt/12pt ���� }
  A:link { color: red }
  A:visited { color: maroon }
</STYLE>
</HEAD><BODY><TABLE width=500 border=0 cellspacing=10><TR><TD>

<h1>�޷��ҵ���ҳ</h1>
������������ҳ������Ѿ�ɾ������������ʱ�����á�
<hr>
<p>�볢�����²�����</p>
<ul>
<li>ȷ��������ĵ�ַ������ʾ����վ��ַ��ƴд�͸�ʽ��ȷ����</li>
<li>���ͨ���������Ӷ������˸���ҳ��������վ����Ա��ϵ��֪ͨ���Ǹ����ӵĸ�ʽ����ȷ��
</li>
<li>����<a href="javascript:history.back(1)">����</a>��ť������һ�����ӡ�</li>
</ul>
<h2>HTTP ���� 404 - �ļ���Ŀ¼δ�ҵ���<br>Internet ��Ϣ���� (IIS)</h2>
<hr>
<p>������Ϣ��Ϊ����֧����Ա�ṩ��</p>
<ul>
<li>ת�� <a href="http://go.microsoft.com/fwlink/?linkid=8180">Microsoft ��Ʒ֧�ַ���</a>����������&ldquo;HTTP&rdquo;��&ldquo;404&rdquo;�ı��⡣</li>
<li>��&ldquo;IIS ����&rdquo;������ IIS ������ (inetmgr) �з��ʣ���Ȼ����������Ϊ&ldquo;��վ����&rdquo;��&ldquo;�����������&rdquo;��&ldquo;�����Զ��������Ϣ&rdquo;�����⡣</li>
</ul>

</TD></TR></TABLE></BODY></HTML>
<%end if%>
