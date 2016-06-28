// JavaScript Document
function changekind(id,compkind,compkindmain)
{
switch (id) {
case "1":
var temp;
temp="<select name='"+compkind+"' id='"+compkind+"'>";
temp+="<option value=''>请选择...</option>";
temp+="<option value='10'>国外供货商</option>";
temp+="<option value='11'>国外回收贸易商</option>";
temp+="<option value='12'>码头，仓库储存商</option>";
temp+="<option value='13'>国内供应商</option>";
temp+="<option value='14'>国内回收贸易商</option>";
temp+="<option value='15'>国内加工，拆解商</option>";
temp+="<option value='16'>利废企业</option>";
temp+="<option value='17'>设备，技术，服务提供商</option>";
temp+="<option value='18'>冶炼厂</option>";
temp+="<option value='19'>电子厂</option>";
temp+="</select>";
document.getElementById(compkindmain).innerHTML=""+temp+"";
break;
case "2":
var temp;
temp="<select name='"+compkind+"' id='"+compkind+"'>";
temp+="<option value=''>请选择...</option>";
temp+="<option value='10'>国外供货商</option>";
temp+="<option value='11'>国外回收贸易商</option>";
temp+="<option value='12'>码头，仓库储存商</option>";
temp+="<option value='13'>国内供应商</option>";
temp+="<option value='14'>国内回收贸易商</option>";
temp+="<option value='15'>国内加工，拆解商</option>";
temp+="<option value='16'>利废企业</option>";
temp+="<option value='17'>设备，技术，服务提供商</option>";
temp+="<option value='20'>造粒厂</option>";
temp+="</select>";
document.getElementById(compkindmain).innerHTML=""+temp+"";
break;
case "0":
var temp;
temp="<select name='"+compkind+"' id='"+compkind+"'>";
temp+="<option value=''>请选择...</option>";
temp+="<option value='10'>国外供货商</option>";
temp+="<option value='11'>国外回收贸易商</option>";
temp+="<option value='12'>码头，仓库储存商</option>";
temp+="<option value='13'>国内供应商</option>";
temp+="<option value='14'>国内回收贸易商</option>";
temp+="<option value='15'>国内加工，拆解商</option>";
temp+="<option value='16'>利废企业</option>";
temp+="<option value='17'>设备，技术，服务提供商</option>";
temp+="</select>";
document.getElementById(compkindmain).innerHTML=""+temp+"";
break;
default :
var temp;
temp="<select name='"+compkind+"' id='"+compkind+"'>";
temp+="<option value=''>请选择...</option>";
temp+="<option value='10'>国外供货商</option>";
temp+="<option value='11'>国外回收贸易商</option>";
temp+="<option value='12'>码头，仓库储存商</option>";
temp+="<option value='13'>国内供应商</option>";
temp+="<option value='14'>国内回收贸易商</option>";
temp+="<option value='15'>国内加工，拆解商</option>";
temp+="<option value='16'>利废企业</option>";
temp+="<option value='17'>设备，技术，服务提供商</option>";
temp+="</select>";
document.getElementById(compkindmain).innerHTML=""+temp+"";
break;
}
}
