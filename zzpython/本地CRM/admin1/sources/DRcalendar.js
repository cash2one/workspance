/*******************************************\
	DRcalendar 1.0
	Author:Dron(程序),Aoao(界面)
	QQ:100004400,2222342
	Ucren.com Rotui.net
	转载或应用请保留版权信息
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
	writeln("<th class=\"su\">日</th>");
	writeln("<th>一</th>");
	writeln("<th>二</th>");
	writeln("<th>三</th>");
	writeln("<th>四</th>");
	writeln("<th>五</th>");
	writeln("<th class=\"st\">六</th>");
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
	document.write("<option value=\"" +i+ "\">" +i+ "年</option>");
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
	document.writeln("<option value=\"" +i+ "\">" +i+ "月</option>");
}
document.writeln("</select>")
document.writeln("</div>");

var MRcalendar = document.getElementById("MRcalendar");//得到日期表格ID
var MRcalendarYearbar = MRcalendar.getElementsByTagName("THEAD")[0].getElementsByTagName("TR")[0].getElementsByTagName("TH")[1];//年份控件
var MRcalendarMonthbar = MRcalendar.getElementsByTagName("THEAD")[0].getElementsByTagName("TR")[0].getElementsByTagName("TH")[2];//月份控件
var MRcalendarDatebar = MRcalendar.getElementsByTagName("TBODY")[0];//日控件
var chageyearbar = document.getElementById("chageyearbar");//选择年控件
var chagemonthbar = document.getElementById("chagemonthbar");//选择月控件

//填充到TD
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

//得到标准日期的 n 天前后 n 天后的日期
function showdate(dat,n)
{
	dat = dat.split("-");
	var uom = new Date(new Date(dat[0]*1,dat[1]*1-1,dat[2]*1)-0+n*86400000);
	uomdate = uom.getFullYear() + "-" +  (uom.getMonth()+1) + "-" + uom.getDate();
	uomday = uom.getDay();
	return uomdate + "," + uomday;
}

//得到某天月份年份
function setmday(dat)
{
	mrday = showdate(dat,0).split(",")[1]*(-1);
	mdat = dat;
	dat = dat.split("-");
	var tod = new Date(dat[0]*1,dat[1]*1-1,dat[2]*1)
	var tod1 = tod.getFullYear();
	var tod2 = tod.getMonth() + 1;
	MRcalendarYearbar.innerHTML = tod1 + "年";
	MRcalendarMonthbar.innerHTML = tod2 + "月";
	for(var i=0;i<=41;i++)
	{
		var xdate = showdate(mdat,(mrday+i));
		xdate = xdate.split(",")[0];
		xyear = xdate.split("-")[0];
		xmonth = xdate.split("-")[1];
		xdate = xdate.split("-")[2];
		xxdate=xyear + "-" + xmonth + "-" + xdate
		//alert (xxdate)
		mstr = "<a href=\"#\" onclick=\"parent.window.location='../admin_tel_tongji.asp?from_date="+xxdate+"&to_date="+xxdate+"';return false;\" title=\"" +xyear+"年"+xmonth+"月"+xdate+"日"+ "\">" +xdate+ "</a>";
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

//前一年，后一年
function setyearcha(n)
{
	mrxzyear = mrxzyear*1;
	mrxzyear += n;setmday(mrxzyear+"-"+mrxzmonth+"-"+mrxzdate);
}

//前一月，后一月
function setmoncha(n)
{
	mrxzmonth += n;
	if(mrxzmonth==0){mrxzmonth = 12;mrxzyear -= 1;}
	if(mrxzmonth==13){mrxzmonth = 1;mrxzyear += 1;}
	setmday(mrxzyear+"-"+mrxzmonth+"-"+mrxzdate);
}
//年被单击
MRcalendarYearbar.onclick = function()
{
	if(this.innerHTML.indexOf("SELECT")==-1)
	{
		var v = this.innerHTML.replace("年","");
		this.innerHTML = chageyearbar.innerHTML;
		this.getElementsByTagName("select")[0].value = v;
		this.getElementsByTagName("select")[0].focus();
	}
}
//月被单击
MRcalendarMonthbar.onclick = function()
{
	if(this.innerHTML.indexOf("SELECT")==-1)
	{
		var v = this.innerHTML.replace("月","");
		this.innerHTML = chagemonthbar.innerHTML;
		this.getElementsByTagName("select")[0].value = v;
		this.getElementsByTagName("select")[0].focus();
	}
}
//初始化
var todaydate,mrxzyear,mrxzmonth,mrxzdate;
(function startMR()
{
	todaydate = new Date();//得到今天
	mrxzyear = todaydate.getFullYear();//默认年份
	mrxzmonth = todaydate.getMonth() + 1;//默认月份
	mrxzdate = 1;//默认日
	setmday(mrxzyear+"-"+mrxzmonth+"-"+mrxzdate);//默认显示今月
})();