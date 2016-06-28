<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短信客户添加</title>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="../js/province_utf8.js"></SCRIPT>
<SCRIPT language=javascript src="../js/compkind.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
form
{
	margin:0px;
	padding:0px;
}
a {
	font-size: 12px;
}
body,td,th {
	font-size: 12px;
}
-->
</style>
</head>

<body>
<br />
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="50">该公司未存在，请填写以下信息添加公司</td>
  </tr>
</table>
<table width="90%"  border="0" align="center" cellpadding="4" cellspacing="0" class=se id=ListTable>
<script language="javascript">
function chkfrm(frm)
{
	if(frm.cname.value.length<=0)
	{
		alert("请输入公司名称!");
		frm.cname.focus();
		return false;
	}
	
    if (frm.ckeywords.value=="")
	{
		alert("请选择所处行业！")
		frm.ckeywords.focus()
		return false;
	}
	if(frm.ccontactp.value.length<=0)
	{
		alert("请输入联系人!");
		frm.ccontactp.focus();
		return false;
	}
	if (frm.countryselect.value=="1")
	{
		if(frm.province1.value=="")
		{
			alert("请选择省份！");
			frm.province1.focus();
			return false;
		}
		if(frm.city1.value=="")
		{
			alert("请选择城市！");
			frm.city1.focus();
			return false;
		}
	}
	
	if(frm.cemail.value.length<=0)
	{
		alert("请输入电子邮件!");
		frm.cemail.focus();
		return false;
	}
	if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.cemail.value)){  
		alert("电子邮箱格式错误");
		frm.cemail.focus();
		return  false;
	}  
	
}
function enteremail(frm)
{
	if (frm.cmobile.value=="" && frm.ctel.value=="")
	{
		alert ("请输入联系方式！")
	}else
	{
	    if (frm.cmobile.value!="")
		{
			frm.cemail.value=frm.cmobile.value+"@zz91.com"
		}else
		{
			frm.cemail.value=frm.ctel.value+"@zz91.com"
		}
	}
}
function enterComp(frm)
{
	if (frm.ccontactp.value=="")
	{
		alert ("请输入联系人！")
	}else
	{
		frm.cname.value=frm.ccontactp.value+"(个体经营)"
	}
}
                </script>
                  <form name="form1" method="post" action="http://admin.zz91.com/admin1/localcontrol/sms_comp_save.asp" onSubmit="return chkfrm(this)">
                   
                    <tr bgcolor="#D9ECFF">
                      <td height="30" colspan="4" class="tbar"><span class="bold"><strong>备注 <font color="#FF0000">* </font>为必填项</strong></span></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td width="80" align="right"><font color="#FF0000">*</font> 公司名称：                      
                        <input type="hidden" name="sid" id="sid" value="<%=request.Form("sid")%>" />
                        <input type="hidden" name="personid" id="personid" value="<%=session("personid")%>">
                      <input type="hidden" name="addtype" id="addtype" value="<%=request("addtype")%>"></td>
                      <td><input name="cname" type="text" id="cname" class=text value="" size="30" maxlength="96">
                      <input type="button" class="button" onclick="enterComp(this.form)" value="默认生成" /></td>
                      <td width="80" align="right" nowrap><font color="#FF0000">*</font> 联系人：</td>
                      <td nowrap><input class=text name="ccontactp" type="text" id="ccontactp" size="20" maxlength="48" value="<%=request.Form("com_contactperson")%>">
                      称呼：
                      <input type="radio" name="cdesi" id="cdesi" <%=ccf1%> value="先生">
                          先生 
                          <input type="radio" name="cdesi" id="cdesi" <%=ccf2%> value="女士">
                          女士</td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right"><font color="#FF0000">* </font> 所处行业：</td>
                      <td>
                      <select name="ckeywords" id="ckeywords" style="width:150px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');getprovincevalue()" >
                          <option value="">请选择</option>
                          <option value="1">废金属</option>
                          <option value="2">废塑料</option>
                          <option value="3">废旧轮胎与废橡胶</option>
                          <option value="4">废纺织品与废皮革</option>
                          <option value="5">废纸</option>
                          <option value="6">废电子电器</option>
                          <option value="10">废玻璃</option>
                          <option value="12">废旧二手设备</option>
                          <option value="14">其他废料</option>
                          <option value="15">服务</option>
                        </select>
                      </td>
                      <td align="right">公司类型：</td>
                      <td>
                      <div id="ckindmain" style="float:left"></div>
                      <script>changekind("","ckind","ckindmain")</script>
                      </td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">国家/地区：</td>
                      <td>
                      <script>
						function selectcountry(id)
						{
							if (id=="1")
							{
								document.getElementById("othercountrys").style.display="none"
								document.getElementById("mycountry").style.display=""
							}
							else
							{
								document.getElementById("othercountrys").style.display=""
								document.getElementById("mycountry").style.display="none"
								document.getElementById("othercity").value=""
							}
						}
						</script>
                      <select name="countryselect" id="countryselect" onChange="selectcountry(this.value);">
                          <option value="1" >中国</option>
                          <option value="0" >其他国家/地区</option>
                      </select></td>
                      <td align="right"><font color="#FF0000">* </font> 省市：</td>
                      <td>
                      <div id="othercountrys" style="display:none">
                        <select name="ccountry" id="ccountry" onChange="selecttextname(this)">
						
                          <option value="2" >台湾</option>
                        
                          <option value="3" >香港</option>
                        
                          <option value="218" >美国</option>
                        
                          <option value="223" >越南</option>
                        
                          <option value="214" >乌克兰</option>
                        
                          <option value="209" >土耳其</option>
                        
                          <option value="156" >尼日利亚</option>
                        
                          <option value="162" >巴基斯坦</option>
                        
                          <option value="168" >菲律宾</option>
                        
                          <option value="169" >波兰</option>
                        
                          <option value="182" >新加坡</option>
                        
                          <option value="16" >澳大利亚</option>
                        
                          <option value="43" >加拿大</option>
                        
                          <option value="67" >英国</option>
                        
                          <option value="100" >印度</option>
                        
                          <option value="101" >印尼</option>
                        
                          <option value="102" >伊朗</option>
                        
                          <option value="105" >以色列</option>
                        
                          <option value="111" >约旦</option>
                        
                          <option value="115" >朝鲜</option>
                        
                          <option value="116" >韩国</option>
                        
                          <option value="132" >马来西亚</option>
                        
                          <option value="99" >冰岛</option>
                        
                          <option value="119" >老挝</option>
                        
                          <option value="134" >马里</option>
                        
                          <option value="145" >缅甸</option>
                        
                          <option value="147" >瑙鲁</option>
                        
                          <option value="109" >日本</option>
                        
                          <option value="81" >德国</option>
                        
                          <option value="83" >加纳</option>
                        
                          <option value="85" >希腊</option>
                        
                          <option value="89" >关岛</option>
                        
                          <option value="95" >海地</option>
                        
                          <option value="65" >埃及</option>
                        
                          <option value="74" >斐济</option>
                        
                          <option value="75" >芬兰</option>
                        
                          <option value="76" >法国</option>
                        
                          <option value="79" >加篷</option>
                        
                          <option value="48" >乍得</option>
                        
                          <option value="49" >智利</option>
                        
                          <option value="52" >刚果</option>
                        
                          <option value="56" >古巴</option>
                        
                          <option value="59" >捷克</option>
                        
                          <option value="60" >丹麦</option>
                        
                          <option value="21" >巴林</option>
                        
                          <option value="28" >贝宁</option>
                        
                          <option value="30" >不丹</option>
                        
                          <option value="36" >巴西</option>
                        
                          <option value="37" >汶莱</option>
                        
                          <option value="178" >沙特</option>
                        
                          <option value="187" >南非</option>
                        
                          <option value="167" >秘鲁</option>
                        
                          <option value="159" >挪威</option>
                        
                          <option value="160" >阿曼</option>
                        
                          <option value="196" >苏丹</option>
                        
                          <option value="204" >泰国</option>
                        
                          <option value="205" >多哥</option>
                        
                          <option value="199" >瑞典</option>
                        
                          <option value="200" >瑞士</option>
                        
                          <option value="226" >也门</option>
                        
                          <option value="231" >蒙古</option>
                        
                          <option value="229" >赞比亚</option>
                        
                          <option value="188" >西班牙</option>
                        
                          <option value="175" >卢旺达</option>
                        
                          <option value="32" >伯奈尔</option>
                        
                          <option value="40" >布隆迪</option>
                        
                          <option value="42" >喀麦隆</option>
                        
                          <option value="29" >百慕大</option>
                        
                          <option value="24" >巴布达</option>
                        
                          <option value="26" >比利时</option>
                        
                          <option value="27" >伯利兹</option>
                        
                          <option value="22" >孟加拉</option>
                        
                          <option value="15" >阿鲁巴</option>
                        
                          <option value="20" >巴哈马</option>
                        
                          <option value="18" >奥地利</option>
                        
                          <option value="4" >阿富汗</option>
                        
                          <option value="61" >吉布提</option>
                        
                          <option value="57" >古拉索</option>
                        
                          <option value="51" >科摩罗</option>
                        
                          <option value="45" >佛得角</option>
                        
                          <option value="80" >冈比亚</option>
                        
                          <option value="94" >圭亚那</option>
                        
                          <option value="91" >根西岛</option>
                        
                          <option value="92" >几内亚</option>
                        
                          <option value="86" >格陵兰</option>
                        
                          <option value="112" >柬埔寨</option>
                        
                          <option value="124" >利比亚</option>
                        
                          <option value="8" >安道尔</option>
                        
                          <option value="9" >安哥拉</option>
                        
                          <option value="10" >安圭拉</option>
                        
                          <option value="11" >安提瓜</option>
                        
                          <option value="13" >阿根廷</option>
                        
                          <option value="14" >亚美尼亚</option>
                        
                          <option value="19" >阿塞邦疆</option>
                        
                          <option value="23" >巴巴多斯</option>
                        
                          <option value="25" >白俄罗斯</option>
                        
                          <option value="31" >玻利维亚</option>
                        
                          <option value="33" >波斯尼亚</option>
                        
                          <option value="35" >博茨瓦纳</option>
                        
                          <option value="38" >保加利亚</option>
                        
                          <option value="46" >开曼群岛</option>
                        
                          <option value="50" >哥伦比亚</option>
                        
                          <option value="53" >库克群岛</option>
                        
                          <option value="55" >克罗地亚</option>
                        
                          <option value="58" >塞浦路斯</option>
                        
                          <option value="62" >多米尼加</option>
                        
                          <option value="64" >厄瓜多尔</option>
                        
                          <option value="66" >萨尔瓦多</option>
                        
                          <option value="70" >爱沙尼亚</option>
                        
                          <option value="73" >法罗群岛</option>
                        
                          <option value="82" >格鲁吉亚</option>
                        
                          <option value="87" >格林纳达</option>
                        
                          <option value="88" >瓜德罗普</option>
                        
                          <option value="90" >危地马拉</option>
                        
                          <option value="107" >象牙海岸</option>
                        
                          <option value="97" >洪都拉斯</option>
                        
                          <option value="98" >匈牙利</option>
                        
                          <option value="103" >伊拉克</option>
                        
                          <option value="104" >爱尔兰</option>
                        
                          <option value="106" >意大利</option>
                        
                          <option value="108" >牙买加</option>
                        
                          <option value="110" >泽西岛</option>
                        
                          <option value="113" >肯尼亚</option>
                        
                          <option value="114" >基里巴斯</option>
                        
                          <option value="117" >科威特</option>
                        
                          <option value="120" >拉脱维亚</option>
                        
                          <option value="121" >黎巴嫩</option>
                        
                          <option value="122" >莱索托</option>
                        
                          <option value="123" >利比里亚</option>
                        
                          <option value="125" >列支敦士登</option>
                        
                          <option value="126" >立陶宛</option>
                        
                          <option value="127" >卢森堡</option>
                        
                          <option value="128" >马斯顿</option>
                        
                          <option value="131" >马拉维</option>
                        
                          <option value="133" >马尔代夫</option>
                        
                          <option value="135" >马耳他 </option>
                        
                          <option value="136" >毛里裘斯</option>
                        
                          <option value="138" >马提尼克</option>
                        
                          <option value="140" >马约特岛</option>
                        
                          <option value="141" >墨西哥</option>
                        
                          <option value="142" >摩纳哥</option>
                        
                          <option value="143" >摩洛哥</option>
                        
                          <option value="144" >莫桑比克 </option>
                        
                          <option value="146" >纳米比亚</option>
                        
                          <option value="148" >尼泊尔</option>
                        
                          <option value="149" >荷兰</option>
                        
                          <option value="153" >新西兰</option>
                        
                          <option value="154" >尼加拉瓜</option>
                        
                          <option value="155" >尼日尔</option>
                        
                          <option value="157" >纽埃岛</option>
                        
                          <option value="163" >巴勒斯坦</option>
                        
                          <option value="164" >巴拿马</option>
                        
                          <option value="166" >巴拉圭</option>
                        
                          <option value="130" >葡萄牙</option>
                        
                          <option value="170" >波多黎各</option>
                        
                          <option value="171" >卡塔尔</option>
                        
                          <option value="172" >留尼旺达</option>
                        
                          <option value="173" >罗马尼亚</option>
                        
                          <option value="174" >俄罗斯</option>
                        
                          <option value="176" >塞班岛</option>
                        
                          <option value="179" >塞内加尔</option>
                        
                          <option value="180" >塞舌尔</option>
                        
                          <option value="181" >塞拉利昂</option>
                        
                          <option value="183" >斯洛伐克</option>
                        
                          <option value="186" >索马里 </option>
                        
                          <option value="189" >斯里兰卡</option>
                        
                          <option value="192" >圣基茨</option>
                        
                          <option value="193" >圣卢西亚</option>
                        
                          <option value="194" >圣马丁岛</option>
                        
                          <option value="195" >圣文豪特</option>
                        
                          <option value="197" >苏里兰</option>
                        
                          <option value="198" >斯威士兰</option>
                        
                          <option value="201" >叙利亚</option>
                        
                          <option value="202" >塔希提</option>
                        
                          <option value="203" >坦桑尼亚</option>
                        
                          <option value="206" >汤加</option>
                        
                          <option value="208" >突尼斯</option>
                        
                          <option value="212" >图瓦卢</option>
                        
                          <option value="215" >阿联酋</option>
                        
                          <option value="213" >乌干达</option>
                        
                          <option value="216" >乌拉圭</option>
                        
                          <option value="220" >瓦努阿图</option>
                        
                          <option value="221" >梵蒂冈</option>
                        
                          <option value="222" >委内瑞拉</option>
                        
                          <option value="224" >维珍岛</option>
                        
                          <option value="225" >西萨摩亚</option>
                        
                          <option value="227" >南斯拉夫</option>
                        
                          <option value="228" >扎伊尔</option>
                        
                          <option value="230" >津巴布韦</option>
                        
                          <option value="235" >其他</option>
                        
                          <option value="236" >其他国家</option>
                        
                          <option value="150" >荷兰安的列斯群岛</option>
                        
                          <option value="34" >波斯尼亚和黑塞哥维那 </option>
                        
                          <option value="211" >特克斯和凯科斯群岛</option>
                        
                          <option value="207" >特立尼达和多巴哥</option>
                        
                          <option value="177" >圣多美和普林西比</option>
                        
                          <option value="165" >巴布亚新几内亚</option>
                        
                          <option value="78" >法属波利尼西亚</option>
                        
                          <option value="12" >安提瓜和巴布达 </option>
                        
                          <option value="63" >多米尼加共和国</option>
                        
                          <option value="219" >乌兹别克斯坦</option>
                        
                          <option value="217" >美属维尔群岛</option>
                        
                          <option value="84" >直布罗陀海峡</option>
                        
                          <option value="118" >吉尔吉斯斯坦</option>
                        
                          <option value="158" >北马绍尔群岛</option>
                        
                          <option value="191" >圣万斯达裘斯</option>
                        
                          <option value="151" >荷属安的列斯</option>
                        
                          <option value="152" >新喀里多尼亚</option>
                        
                          <option value="190" >圣巴夫林米</option>
                        
                          <option value="5" >阿尔巴尼亚</option>
                        
                          <option value="6" >阿尔及利亚</option>
                        
                          <option value="7" >美属萨摩亚</option>
                        
                          <option value="39" >布基纳法索</option>
                        
                          <option value="210" >土库曼斯坦</option>
                        
                          <option value="185" >所罗门群岛</option>
                        
                          <option value="184" >斯洛文尼亚</option>
                        
                          <option value="44" >加那利群岛</option>
                        
                          <option value="47" >中非共和国</option>
                        
                          <option value="54" >哥斯达黎加</option>
                        
                          <option value="68" >赤道几内亚</option>
                        
                          <option value="69" >厄立特里亚</option>
                        
                          <option value="139" >毛里坦尼亚</option>
                        
                          <option value="72" >福克兰群岛</option>
                        
                          <option value="71" >埃塞俄比亚</option>
                        
                          <option value="77" >法属圭亚拉</option>
                        
                          <option value="137" >马绍尔群岛</option>
                        
                          <option value="129" >马达加斯加</option>
                        
                          <option value="96" >黑塞哥维那</option>
                        
                          <option value="93" >几内亚比绍</option>
                        
                          <option value="232" >哈萨克斯坦</option>
                        
                        </select>
                          <input name="othercity" type="text" id="othercity" class="textt" onClick="selecttextname(this)" size="20" />
                        </div>
                        <div id="mycountry">
                        <font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">无园区</font>
                        <input type="hidden" name="province" id="province">
                        <input type="hidden" name="city" id="city">
                        
                        
                        <script>
                            //地区及园区
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=provinceID%>";
                            var Fvalue2="<%=cityID%>";
                            var Fvalue3="<%=GardenID%>";
                            var hyID="ckeywords";//行业ID号
                            getprovincevalue();
                        </script>
                        <script>
                        function getprovincename()
                        {
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>
                        <input type="hidden" name="cprovince" id="cprovince" value="0">
                        </div>
                      </td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">地址：</td>
                      <td><input name="cadd" type="text" class=text id="cadd" size="50" maxlength="20"></td>
                      <td align="right">邮编：</td>
                      <td><input class=text name="czip" type="text" id="czip" size="20" maxlength="48"></td>
                    </tr>
					
					
					
                    
                    <tr bgcolor="#FFFFFF">
                      <td align="right">电话：</td>
                      <td><input name="ctel" type="text" id="ctel" class=text size="30" maxlength="96"></td>
                      <td align="right"><font color="#FF0000">*</font> 手机：</td>
                      <td><input name="cmobile" type="text" id="cmobile" class=text size="30" maxlength="96" value="<%=request.Form("com_mobile")%>"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">传真：</td>
                      <td><input name="cfax" type="text" class=text id="cfax" size="30" maxlength="96"></td>
                      <td align="right"><font color="#FF0000">*</font> 电子邮箱：</td>
                      <td nowrap><input name="cemail" class=text type="text" id="cemail" size="30" maxlength="48" <%=redo%>>
                        <input name="Submit" type="button" class="button" onClick="enteremail(this.form)" value="生成"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">网站：</td>
                      <td><input name="cweb" class=text type="text" id="cweb" size="30" maxlength="255"></td>
                      <td>&nbsp;</td>
                      <td>如果没有email请用 (手机或电话@zz91.com)</td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" nowrap>公司简介：</td>
                      <td colspan="3" align="left"><textarea name="cintroduce" cols="60" rows="5" id="cintroduce"></textarea></td>
                    </tr>
                   
                    <tr align="center" bgcolor="#FFFFFF">
                      <td align="right">主营业务：</td>
                      <td colspan="3" align="left"><textarea name="cproductslist_en" cols="50" rows="4" id="cproductslist_en"></textarea></td>
                    </tr>
                    <tr align="center" bgcolor="#FFFFFF">
                      <td height="100" colspan="4" ><input name="Submit" type="submit" class="button" value=" 第一步保存客户信息 "></td>
                    </tr>
					
                    
                  </form>
</table>
</body>
</html>
