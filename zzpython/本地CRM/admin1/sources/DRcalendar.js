/*******************************************\
	DRcalendar 1.0
	Author:Dron(����),Aoao(����)
	QQ:100004400,2222342
	Ucren.com Rotui.net
	ת�ػ�Ӧ���뱣����Ȩ��Ϣ
\*******************************************/
with(document)
{
	writeln("<table id=\"MRcalendar\">");
	//writeln("<caption onclick=parent.plus('t1')>____ MRcalendar ____</caption>");
	writeln("<thead>");
	writeln("<tr>");
	writeln("<th class=\"q\">?</th>");
	writeln("<th colspan=\"3\"></th>");
	writeln("<th colspan=\"2\"></th>");
	writeln("<th class=\"c\" ><a href=\"#\" onclick=parent.plus('t1') title=\"close\"><font  >x</font></a></th>");
	writeln("</tr>");
	writeln("<tr>");
	writeln("<th class=\"su\">��</th>");
	writeln("<th>һ</th>");
	writeln("<th>��</th>");
	writeln("<th>��</th>");
	writeln("<th>��</th>");
	writeln("<th>��</th>");
	writeln("<th class=\"st\">��</th>");
	writeln("</tr>");
	writeln("</thead>");
	writeln("<tbody>");
}
for(var i=1;i<=6;i++)
{
	document.writeln("<tr>");
	for(var j=1;j<=7;j++)
	{
		with(document)
		{
			writeln("<td></td>");
		}
	}
	document.writeln("</tr>");
}
with(document)
{
	writeln("</tbody>");
	writeln("<tfoot>");
	writeln("<tr>");
	writeln("<td><a href=\"#\" onclick=\"setyearcha(-1);return false;\" ><<</a></td>");
	writeln("<td><a href=\"#\" onclick=\"setmoncha(-1);return false;\" ><</a></td>");
	writeln("<td colspan=\"3\"><a href=\"#\" onclick=\"startMR();return false;\" >Today</a></td>");
	writeln("<td><a href=\"#\" onclick=\"setmoncha(1);return false;\" >></a></td>");
	writeln("<td><a href=\"#\" onclick=\"setyearcha(1);return false;\" >>></a></td>");
	writeln("</tr>");
	writeln("</tfoot>");
	writeln("</table>");
	writeln("<div id=\"chageyearbar\" style=\"display:none\">");
	writeln("<select onblur=\"setmday(mrxzyear+\'-\'+mrxzmonth+\'-\'+mrxzdate)\" onchange=\"mrxzyear=this.value;setmday(mrxzyear+\'-\'+mrxzmonth+\'-\'+mrxzdate);\">");
}
for(var i=1956;i<=2056;i++)
{
	document.write("<option value=\"" +i+ "\">" +i+ "��</option>");
}
with(document)
{
	writeln("</select>");
	writeln("</div>");
}
document.writeln("<div id=\"chagemonthbar\" style=\"display:none;\">");
document.writeln("<select style=\"width:42px\" onblur=\"setmday(mrxzyear+\'-\'+mrxzmonth+\'-\'+mrxzdate)\" onchange=\"mrxzmonth=this.value;setmday(mrxzyear+\'-\'+mrxzmonth+\'-\'+mrxzdate);\">");
for(var i=1;i<=12;i++)
{
	document.writeln("<option value=\"" +i+ "\">" +i+ "��</option>");
}
document.writeln("</select>")
document.writeln("</div>");

var MRcalendar = document.getElementById("MRcalendar");//�õ����ڱ��ID
var MRcalendarYearbar = MRcalendar.getElementsByTagName("THEAD")[0].getElementsByTagName("TR")[0].getElementsByTagName("TH")[1];//��ݿؼ�
var MRcalendarMonthbar = MRcalendar.getElementsByTagName("THEAD")[0].getElementsByTagName("TR")[0].getElementsByTagName("TH")[2];//�·ݿؼ�
var MRcalendarDatebar = MRcalendar.getElementsByTagName("TBODY")[0];//�տؼ�
var chageyearbar = document.getElementById("chageyearbar");//ѡ����ؼ�
var chagemonthbar = document.getElementById("chagemonthbar");//ѡ���¿ؼ�

//��䵽TD
function innerHTMLtoTD(n,str,sty)
{
	var y = Math.floor(n/7);
	var x = n - (y*7);
	MRcalendarDatebar.getElementsByTagName("TR")[y].getElementsByTagName("TD")[x].innerHTML = str;
	if(sty!="")
	{
		MRcalendarDatebar.getElementsByTagName("TR")[y].getElementsByTagName("TD")[x].className = sty;
	}
	if(MRcalendarDatebar.getElementsByTagName("TR")[y].getElementsByTagName("TD")[x].className!="no")
	{
		if(x==0)
		{
			MRcalendarDatebar.getElementsByTagName("TR")[y].getElementsByTagName("TD")[x].className = "su";
		}
		if(x==6)
		{
			MRcalendarDatebar.getElementsByTagName("TR")[y].getElementsByTagName("TD")[x].className = "st";
		}
	}
}

//�õ���׼���ڵ� n ��ǰ�� n ��������
function showdate(dat,n)
{
	dat = dat.split("-");
	var uom = new Date(new Date(dat[0]*1,dat[1]*1-1,dat[2]*1)-0+n*86400000);
	uomdate = uom.getFullYear() + "-" +  (uom.getMonth()+1) + "-" + uom.getDate();
	uomday = uom.getDay();
	return uomdate + "," + uomday;
}

//�õ�ĳ���·����
function setmday(dat)
{
	mrday = showdate(dat,0).split(",")[1]*(-1);
	mdat = dat;
	dat = dat.split("-");
	var tod = new Date(dat[0]*1,dat[1]*1-1,dat[2]*1)
	var tod1 = tod.getFullYear();
	var tod2 = tod.getMonth() + 1;
	MRcalendarYearbar.innerHTML = tod1 + "��";
	MRcalendarMonthbar.innerHTML = tod2 + "��";
	for(var i=0;i<=41;i++)
	{
		var xdate = showdate(mdat,(mrday+i));
		xdate = xdate.split(",")[0];
		xyear = xdate.split("-")[0];
		xmonth = xdate.split("-")[1];
		xdate = xdate.split("-")[2];
		xxdate=xyear + "-" + xmonth + "-" + xdate
		//alert (xxdate)
		mstr = "<a href=\"#\" onclick=\"parent.window.location='../admin_tel_tongji.asp?from_date="+xxdate+"&to_date="+xxdate+"';return false;\" title=\"" +xyear+"��"+xmonth+"��"+xdate+"��"+ "\">" +xdate+ "</a>";
		if((xyear*1==todaydate.getFullYear())&&(xmonth*1==todaydate.getMonth()+1)&&(xdate*1==todaydate.getDate()))
		{
			innerHTMLtoTD(i,mstr,"today");
		}
		else if(xmonth*1!=tod2*1)
		{
			innerHTMLtoTD(i,mstr,"no");	
		}
		else
		{
			innerHTMLtoTD(i,mstr,"none");
		}
	}
}

//ǰһ�꣬��һ��
function setyearcha(n)
{
	mrxzyear = mrxzyear*1;
	mrxzyear += n;setmday(mrxzyear+"-"+mrxzmonth+"-"+mrxzdate);
}

//ǰһ�£���һ��
function setmoncha(n)
{
	mrxzmonth += n;
	if(mrxzmonth==0){mrxzmonth = 12;mrxzyear -= 1;}
	if(mrxzmonth==13){mrxzmonth = 1;mrxzyear += 1;}
	setmday(mrxzyear+"-"+mrxzmonth+"-"+mrxzdate);
}
//�걻����
MRcalendarYearbar.onclick = function()
{
	if(this.innerHTML.indexOf("SELECT")==-1)
	{
		var v = this.innerHTML.replace("��","");
		this.innerHTML = chageyearbar.innerHTML;
		this.getElementsByTagName("select")[0].value = v;
		this.getElementsByTagName("select")[0].focus();
	}
}
//�±�����
MRcalendarMonthbar.onclick = function()
{
	if(this.innerHTML.indexOf("SELECT")==-1)
	{
		var v = this.innerHTML.replace("��","");
		this.innerHTML = chagemonthbar.innerHTML;
		this.getElementsByTagName("select")[0].value = v;
		this.getElementsByTagName("select")[0].focus();
	}
}
//��ʼ��
var todaydate,mrxzyear,mrxzmonth,mrxzdate;
(function startMR()
{
	todaydate = new Date();//�õ�����
	mrxzyear = todaydate.getFullYear();//Ĭ�����
	mrxzmonth = todaydate.getMonth() + 1;//Ĭ���·�
	mrxzdate = 1;//Ĭ����
	setmday(mrxzyear+"-"+mrxzmonth+"-"+mrxzdate);//Ĭ����ʾ����
})();