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
	document.write("<br>解密：<br>"+ss);
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
<HTML><HEAD><TITLE>无法找到该页</TITLE>
<META HTTP-EQUIV="Content-Type" Content="text/html; charset=GB2312">
<STYLE type="text/css">
  BODY { font: 9pt/12pt 宋体 }
  H1 { font: 12pt/15pt 宋体 }
  H2 { font: 9pt/12pt 宋体 }
  A:link { color: red }
  A:visited { color: maroon }
</STYLE>
</HEAD><BODY><TABLE width=500 border=0 cellspacing=10><TR><TD>

<h1>无法找到该页</h1>
您正在搜索的页面可能已经删除、更名或暂时不可用。
<hr>
<p>请尝试以下操作：</p>
<ul>
<li>确保浏览器的地址栏中显示的网站地址的拼写和格式正确无误。</li>
<li>如果通过单击链接而到达了该网页，请与网站管理员联系，通知他们该链接的格式不正确。
</li>
<li>单击<a href="javascript:history.back(1)">后退</a>按钮尝试另一个链接。</li>
</ul>
<h2>HTTP 错误 404 - 文件或目录未找到。<br>Internet 信息服务 (IIS)</h2>
<hr>
<p>技术信息（为技术支持人员提供）</p>
<ul>
<li>转到 <a href="http://go.microsoft.com/fwlink/?linkid=8180">Microsoft 产品支持服务</a>并搜索包括&ldquo;HTTP&rdquo;和&ldquo;404&rdquo;的标题。</li>
<li>打开&ldquo;IIS 帮助&rdquo;（可在 IIS 管理器 (inetmgr) 中访问），然后搜索标题为&ldquo;网站设置&rdquo;、&ldquo;常规管理任务&rdquo;和&ldquo;关于自定义错误消息&rdquo;的主题。</li>
</ul>

</TD></TR></TABLE></BODY></HTML>
<%end if%>
