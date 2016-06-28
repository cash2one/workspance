// JavaScript Document
<!--
function Jtrim(str)
{
	var tag=-1;
	array1=str.split("");
	for(var i=0;i<array1.length;i++)
	{
		if(array1[i]!=" ")
		{
			tag=i;
			break;
		}
	}
	if(tag==-1)
	{
		return "";
	}
	else
	{
		var  tempstr=str.substring(tag);
		array111=tempstr.split("");
		array11=new Array();
		var devstr="";
		for(var k=0;k<array111.length;k++)
		{
			array11[k]=array111[array111.length-1-k];
			devstr=devstr+array11[k];
		}		
		var tag1=-1;
		array11=devstr.split("");
		for(var j=0;j<array11.length;j++)
		{
			if(array11[j]!=" ")
			{
				tag1=j;
				break;
			}
		}
		var echostr="";
		for(var l=array11.length-1;l>=tag1;l--)
		{
			echostr=echostr+array11[l];
		}
		return echostr;
	}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function changebgcolor(objtochange)
{
	objtochange.style.background="#DDEEFF";
}
function changebackbgcolor(objtochange)
{
	objtochange.style.background="#FFFFFF";
}

function changebgcolor1(objtochange)
{
	objtochange.style.background="#FDF3CE";//#CCFFCC
}
function changebackbgcolor1(objtochange)
{
	objtochange.style.background="#FFFFFF";
}
function deletetext(obj)
{
if(obj.value=="请不要在留言内容（此方格内）中留下您公司的信息或联系方式，否则对方客户有可能收不到您所发送的信息") obj.value="";
}
function isVaildEmail_of(s)
{
	var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/g ;
	if(reg.test(s))
	{
		return true;
	}
	else
	{
		return false;
	}
}
    var infoboxOkClass="sbtishi_ok";
	var infoboxNOClass="sbtishi_err";
	var infoboxWaClass="sbtishi_on";
	
    var msgInfo	= new Array();
	msgInfo["ccontactp"]= '请输入真实姓名。';
	msgInfo["cemail"]= '<b>重要！</b>我们需要您通过邮箱完成注册，请填写常用的电子邮箱，这也是客户联系您的首选方式！<br>没有电子邮箱？点此免费获取：<a href=\"http://www.126.com\" target=\"_blank\" class=\"NOL\"><font color=\"#ff0000\">网易邮箱<\/font><\/a>';
	msgInfo["cmobile"]= '建议您填写，以便潜在客户及时与您取得联系！<br>ZZ91再生网不绑定任何收费服务！';
	msgInfo["cname"]= '*国内注册企业请用中文完整填写在工商局注册的全称。<br> 如：浙江苍南县清源再生棉纺厂。<br>*无商号的个体经营者填写执照上的姓名，并标注个体经营。如：个体经营（张三）'
	msgInfo["cadd"]= '请在此填写公司地址，<br>如：浙江省杭州市西湖大道239耀江广厦B座。';
	msgInfo["cprovince"]= '请输入省份！<br>如：浙江';
	msgInfo["province"]= '请选择省份！';
	msgInfo["city"]= '请选择城市！';
	msgInfo["czip"]= '请输入邮编';
	msgInfo["ctel"]= '如果要输入多个固定电话号码，请使用"/"分隔；分机号码用"-"分隔';
	msgInfo["czip"]= '请输入邮编';
	msgInfo["compass"]= '密码由6-20个英文字母(区分大小写)或数字组成，建议采用易记、难猜的英文数字组合。';
	msgInfo["compasscomfirm"]= '请再输入一遍您上面填写的密码。';
	msgInfo["cmobile"]= '请输入手机号码。';
	msgInfo["compusername"]= '请输入用户名(以英文字母开头的4-20个字符，不能用中文)。';
	msgInfo["ckeywords"]= '请选择主营业务。';
	msgInfo["cfitkeywords"]= '请输入主营产品，多个产品用逗号隔开。';
	var msgerrInfo	= new Array();
	msgerrInfo["ccontactp"]= '请输入真实姓名。（不能是纯阿拉伯数字）';
	msgerrInfo["cemail"]= '电子邮箱格式不正确，请输入正确的电子邮箱地址!';
	msgerrInfo["cmobile"]= '建议您填写，以便潜在客户及时与您取得联系！<br>ZZ91再生网不绑定任何收费服务！';
	msgerrInfo["cname"]= '请输入公司名称。（不能是纯阿拉伯数字）'
	msgerrInfo["cadd"]= '请在此填写公司地址，<br>如：浙江省杭州市西湖大道239耀江广厦B座。（不能是纯阿拉伯数字）';
	msgerrInfo["cprovince"]= '请输入省份！<br>如：浙江';
	msgerrInfo["province"]= '请选择省份！';
	msgerrInfo["city"]= '请选择城市！';
	msgerrInfo["czip"]= '请输入邮编';
	msgerrInfo["ctel"]= '请正确输入电话号码';
	msgerrInfo["compass"]= '您设置的密码有误。密码由6-20个英文字母(区分大小写)或数字组成';
	msgerrInfo["compasscomfirm"]='两次输入的密码不一致！请再输入一遍您上面填写的密码。'
	msgerrInfo["cmobile"]= '请正确输入手机号码。';
	msgerrInfo["compusername"]= '请正确输入用户名。';
	msgerrInfo["ckeywords"]= '请选择主营业务。';
	msgerrInfo["cfitkeywords"]= '请输入主营产品，多个产品用逗号隔开。';
function validateValue(obj){
    	switch(obj.id){
        	case "cemail":
            	focuscomname(obj,1);
            	break;	
        	default:
            	focuscomname(obj,0);
            	break;
    	}
	}
function validateValueout(obj){
		//alert(document.getElementById(obj.id).value)
    	switch(obj.id){
        	case "cemail":
            	outemail(obj,1);
            	break;
			case "cname":
            	checkstrName(obj,1);
            	break;
			case "compusername":
				outusername(obj,1);
				break;
			case "compass":
            	outpass(obj,1);
            	break;
			case "compasscomfirm":
            	outpasscomfirm(obj,1);
            	break;
			case "ccontactp":
            	checkstrName(obj,1);
            	break;
			case "ctel":
            	outcomtel(obj,1);
            	break;
			case "cmobile":
            	outcommobile(obj,1);
            	break;
			case "province":
            	outprovince(obj,1);
            	break;
			case "city":
            	outprovince(obj,1);
            	break;
			case "cadd":
            	checkstrName(obj,1);
            	break;
			case "czip":
            	outcomname(obj,1);
            	break;
			case "cfitkeywords":
            	outcomname(obj,1);
            	break;
        	default:
            	outcomname(obj,0);
            	break;
    	}
	}
	//************************************
	function focuscomname(obj,evnt){
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		infobox.className = infoboxWaClass;
		infobox.innerHTML=msgInfo[obj.name];
	}
	function outcomname(obj,evnt){
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		if (Jtrim(document.getElementById(obj.id).value)!='')
		{
			infobox.className = infoboxOkClass;
			infobox.innerHTML='填写正确！';
		}else
		{
			if (document.getElementById(obj.id).value=="")
			{
				infobox.className = infoboxWaClass;
				infobox.innerHTML="系统没有检测到您的输入！";
				return false;
			}
			if (evnt==1)
			{
				infobox.className = infoboxNOClass;
				infobox.innerHTML=msgerrInfo[obj.name];
			}else{
				
				infobox.className = infoboxOkClass;
			}
		}
	}
	function outprovince(obj,evnt){
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
			if (document.getElementById(obj.id).value=="省份" || document.getElementById(obj.id).value=="城市")
			{
				infobox.className = infoboxWaClass;
				infobox.innerHTML="请选择省份和城市！";
				return false;
			}else{
				infobox.innerHTML='填写正确！';
				infobox.className = infoboxOkClass;
			}
	}
	function outcomtel(obj,evnt){
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		if (Jtrim(document.getElementById(obj.id).value)!='' && document.getElementById(obj.id).value.length>6 && document.getElementById("ctel1").value>=2)
		{
			
			infobox.className = infoboxOkClass;
			infobox.innerHTML='填写正确！';
			checkTel(obj)
		}else
		{
			if (document.getElementById(obj.id).value=="")
			{
				infobox.className = infoboxWaClass;
				infobox.innerHTML="系统没有检测到您的输入！";
				return false;
			}
			if (evnt==1)
			{
				infobox.className = infoboxNOClass;
				infobox.innerHTML=msgerrInfo[obj.name];
			}else{
				infobox.className = infoboxOkClass;
			}
		}
	}
	function outcommobile(obj,evnt){
	
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		if (Jtrim(document.getElementById(obj.id).value)!='' && document.getElementById(obj.id).value.length>=11)
		{
			
			infobox.className = infoboxOkClass;
			infobox.innerHTML='填写正确！';
			checkMobile(obj)
		}else
		{
			if (document.getElementById(obj.id).value=="")
			{
				infobox.className = infoboxWaClass;
				infobox.innerHTML="系统没有检测到您的输入！";
				return false;
			}
			if (evnt==1)
			{
				infobox.className = infoboxNOClass;
				infobox.innerHTML=msgerrInfo[obj.name];
				
			}else{
				infobox.className = infoboxOkClass;
			}
		}
	}
	//判断用户名是否存在
	function outusername(obj,evnt)
	{
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
			if (evnt==1)
			{
				if (document.getElementById(obj.id).value=="")
				{
					infobox.className = infoboxWaClass;
					infobox.innerHTML="系统没有检测到您的输入！";
					return false;
				}
			    checkUsername(obj)
			}else{
				infobox.className = infoboxOkClass;
			}
	}
	
	
	function outemail(obj,evnt){
	
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
			if (evnt==1)
			{
				if (document.getElementById(obj.id).value=="")
				{
					infobox.className = infoboxWaClass;
					infobox.innerHTML="系统没有检测到您的输入！";
					return false;
				}
			    checkEmail(obj)
			}else{
				infobox.className = infoboxOkClass;
			}
	}
	function outpass(obj,evnt){
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
			if (evnt==1)
			{
				if(!validatePassword(obj))
				{
				infobox.innerHTML=msgerrInfo[obj.name];
				infobox.className = infoboxNOClass;
				}else
				{
					if (document.getElementById(obj.id).value!="")
					{
					infobox.className = infoboxOkClass;
					infobox.innerHTML="填写正确！";
					}
				}
			}else{
				infobox.className = infoboxOkClass;
			}
	}
	function checkstrName(obj,evnt){
	
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
			if (evnt==1)
			{
				if(!validateStr(obj))
				{
					infobox.innerHTML=msgerrInfo[obj.name];
					infobox.className = infoboxNOClass;
				}else
				{
					if (document.getElementById(obj.id).value!="")
					{
					infobox.className = infoboxOkClass;
					infobox.innerHTML="填写正确！";
					}
				}
			}else{
				infobox.className = infoboxOkClass;
			}
	}
	function outpasscomfirm(obj,evnt){
	
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
			if (evnt==1)
			{
				if(!validateSafePassword(obj))
				{
				infobox.innerHTML=msgerrInfo[obj.name];
				infobox.className = infoboxNOClass;
				}else
				{
					if (document.getElementById(obj.id).value!="")
					{
					infobox.className = infoboxOkClass;
					infobox.innerHTML="填写正确！";
					}
				}
			}else{
				infobox.className = infoboxOkClass;
			}
	}
	//************************
	function checkEmail(obj) {
		var email = document.getElementById(obj.id).value;
	
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		if (email == "") {
			return false;
		}else
		{
			email = email.toLowerCase();
			if(!isVaildEmail_of(email))
			{
			infobox.innerHTML = msgerrInfo[obj.name];
			infobox.className = infoboxNOClass;
			return false;
			}else{
			document.checkForm.action = "checkemail.asp?email="+escape(email);
			infobox.innerHTML = "检查中，请稍后......";
			infobox.className = "sbtishi_on";
			document.checkForm.submit();
			return true;
			}
		}
	}
	//判断手机是否存在
	function checkMobile(obj) {
		var mobile = document.getElementById(obj.id).value;
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		if (mobile == "") {
			return false;
		}else
		{
			mobile = mobile.toLowerCase();
			document.checkForm.action = "checkMobile.asp?mobile="+escape(mobile);
			infobox.innerHTML = "检查中，请稍后......";
			infobox.className = "sbtishi_on";
			document.checkForm.submit();
			return true;
		}
	}
	function checkTel(obj) {
		var tel = document.getElementById(obj.id).value;
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		if (tel == "") {
			return false;
		}else
		{
			
			tel = tel.toLowerCase();
			document.checkForm.action = "checktel.asp?tel="+escape(tel);
			infobox.innerHTML = "检查中，请稍后......";
			infobox.className = "sbtishi_on";
			document.checkForm.submit();
			return true;
		}
	}
	function checkUsername(obj) {
		var username = document.getElementById(obj.id).value;
		var infoboxinfo=obj.id+"Info"
		var infobox= document.getElementById(infoboxinfo);
		if (username == "") {
			return false;
		}else
		{
			username = username.toLowerCase();
			document.checkForm.action = "checkusername.asp?username="+escape(username);
			infobox.innerHTML = "检查中，请稍后......";
			infobox.className = "sbtishi_on";
			document.checkForm.submit();
			return true;
		}
	}
    function validateSafePassword(obj){
    	var str = document.getElementById(obj.id).value;
    	if(str != document.getElementById("compass").value) return false;
    	return true;
	}
	function validatePassword(obj){
    	var str = document.getElementById(obj.id).value;
    	if(!checkByteLength(str,6,20)) return false;															
    	var patn1 =   /^[a-zA-Z0-9_]+$/;
    	if(!patn1.test(str) ) return false;
    	return true; 
	}
	function validateStr(obj){
    	var str = document.getElementById(obj.id).value;
    	var patn1 =   /^[0-9]+$/;
    	if(patn1.test(str) ) return false;
    	return true; 
	}
	function validateTel(obj){
    	var str = document.getElementById(obj.id).value;
    	var patn1 =/\d{4}/;
		alert(patn1.test(str))
    	if(patn1.test(str) ) return false;
    	return true; 
	}
	function checkByteLength(str,minlen,maxlen) {
		if (str == null) return false;
		var l = str.length;
		var blen = 0;
		for(i=0; i<l; i++) {
			if ((str.charCodeAt(i) & 0xff00) != 0) {
				blen ++;
			}
			blen ++;
		}
		if (blen > maxlen || blen < minlen) {
			return false;
		}
		return true;
	}      
//***********************
function isVaildEmail_of(s)
{
	var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/g ;
	if(reg.test(s))
	{
		return true;
	}
	else
	{
		return false;
	}
}
//***********************
/*******************************************\
  游戏人省市联动菜单类(2006-06-20)
  This JavaScript was writen by Dron.
  @2003-2008 Ucren.com All rights reserved.
\*******************************************/
	function Dron_City(obj1,obj2,val)
	{
		var country = document.getElementById(obj1);
		var city = document.getElementById(obj2);
		var countryData = "广东|广西|北京|海南|福建|天津|湖南|湖北|河南|河北|山东|山西|黑龙江|辽宁|上海|甘肃|青海|新疆|西藏|宁夏|四川|云南|吉林|内蒙古|陕西|安徽|贵州|江苏|重庆|浙江|江西|台湾|香港|澳门".split("|");
		var cityData = [
			"城市".split("|"),
			"潮州|东莞|佛山|广州|河源|惠州|江门|揭阳|茂名|梅州|清远|汕头|汕尾|韶关|深圳|阳江|云浮|湛江|肇庆|中山|珠海".split("|"),
			"百色|北海|崇左|防城港|桂林|贵港|河池|贺州|来宾|柳州|南宁|钦州|梧州|玉林".split("|"),
			"北京".split("|"),
			"白沙黎族自治县|保亭黎族苗族自治县|昌江黎族自治县|澄迈县|定安县|东方|海口|乐东黎族自治县|临高县|陵水黎族自治县|琼海|琼中黎族苗族自治县|三亚|屯昌县|万宁|文昌|五指山|儋州".split("|"),
			"福州|龙岩|南平|宁德|莆田|泉州|三明|厦门|漳州".split("|"),
			"天津".split("|"),
			"常德|长沙|郴州|衡阳|怀化|娄底|邵阳|湘潭|湘西土家族苗族自治州|益阳|永州|岳阳|张家界|株洲".split("|"),
			"鄂州|恩施土家族苗族自治州|黄冈|黄石|荆门|荆州|潜江|神农架林区|十堰|随州|天门|武汉|仙桃|咸宁|襄樊|孝感|宜昌".split("|"),
			"安阳|鹤壁|济源|焦作|开封|洛阳|南阳|平顶山|三门峡|商丘|新乡|信阳|许昌|郑州|周口|驻马店|漯河|濮阳".split("|"),
			"保定|沧州|承德|邯郸|衡水|廊坊|秦皇岛|石家庄|唐山|邢台|张家口".split("|"),
			"滨州|德州|东营|菏泽|济南|济宁|莱芜|聊城|临沂|青岛|日照|泰安|威海|潍坊|烟台|枣庄|淄博".split("|"),
			"长治|大同|晋城|晋中|临汾|吕梁|朔州|太原|忻州|阳泉|运城".split("|"),
			"大庆|大兴安岭|哈尔滨|鹤岗|黑河|鸡西|佳木斯|牡丹江|七台河|齐齐哈尔|双鸭山|绥化|伊春".split("|"),
			"鞍山|本溪|朝阳|大连|沈阳|丹东|抚顺|阜新|葫芦岛|锦州|辽阳|盘锦|铁岭|营口".split("|"),
			"上海".split("|"),
			"白银|定西|甘南藏族自治州|嘉峪关|金昌|酒泉|兰州|临夏回族自治州|陇南|平凉|庆阳|天水|武威|张掖".split("|"),
			"果洛藏族自治州|海北藏族自治州|海东|海南藏族自治州|海西蒙古族藏族自治州|黄南藏族自治州|西宁|玉树藏族自治州".split("|"),
			"阿克苏|阿拉尔|巴音郭楞蒙古自治州|博尔塔拉蒙古自治州|昌吉回族自治州|哈密|和田|喀什|克拉玛依|克孜勒苏柯尔克孜自治州|石河子|图木舒克|吐鲁番|乌鲁木齐|五家渠|伊犁哈萨克自治州".split("|"),
			"阿里|昌都|拉萨|林芝|那曲|日喀则|山南".split("|"),
			"固原|石嘴山|吴忠|中卫|银川".split("|"),
			"阿坝藏族羌族自治州|巴中|成都|达州|德阳|甘孜藏族自治州|广安|广元|乐山|凉山彝族自治州|眉山|绵阳|南充|内江|攀枝花|遂宁|雅安|宜宾|资阳|自贡|泸州".split("|"),
			"保山|楚雄彝族自治州|大理白族自治州|德宏傣族景颇族自治州|迪庆藏族自治州|红河哈尼族彝族自治州|昆明|丽江|临沧|怒江傈傈族自治州|曲靖|思茅|文山壮族苗族自治州|西双版纳傣族自治州|玉溪|昭通".split("|"),
			"白城|白山|长春|吉林|四平|辽源|松原|通化|延边朝鲜族自治州".split("|"),
			"阿拉善盟|巴彦淖尔盟|包头|赤峰|鄂尔多斯|呼和浩特|呼伦贝尔|通辽|乌海|乌兰察布|锡林郭勒盟|兴安盟".split("|"),
			"安康|宝鸡|汉中|商洛|铜川|渭南|西安|咸阳|延安|榆林".split("|"),
			"安庆|蚌埠|巢湖|池州|滁州|阜阳|合肥|淮北|淮南|黄山|六安|马鞍山|宿州|铜陵|芜湖|宣城|亳州".split("|"),
			"安顺|毕节|贵阳|六盘水|黔东南苗族侗族自治州|黔南布依族苗族自治州|黔西南布依族苗族自治州|铜仁|遵义".split("|"),
			"常州|淮安|连云港|南京|南通|苏州|宿迁|泰州|无锡|徐州|盐城|扬州|镇江".split("|"),
			"重庆".split("|"),
			"杭州|湖州|嘉兴|金华|丽水|宁波|绍兴|台州|温州|舟山|衢州".split("|"),
			"抚州|赣州|吉安|景德镇|九江|南昌|萍乡|上饶|新余|宜春|鹰潭".split("|"),
			"台北|高雄|台中|台南|基隆|新竹".split("|"),
			"香港|九龙|新界".split("|"),
			"澳门".split("|")
			//"亚州|北美|南美|欧洲|非洲|大洋洲".split("|")
		];
		var valdata=val.split("|");
		this.init = function ()
		{
			//添加省
			NowIndex=0
			country.length = 0;
			var opt = document.createElement("option");opt.innerHTML = opt.value = "省份";
			country.appendChild(opt);
			for(var i=0;i<countryData.length;i++)
			{
			    if (valdata[0]==countryData[i])
				{
				NowIndex=i+1
				}
				var opt = document.createElement("option");opt.innerHTML = opt.value = countryData[i];
				country.appendChild(opt);
			}
			country.selectedIndex = NowIndex;
			//添加市
			cityNowIndex=0
			if (NowIndex>0)
			{
			city.length = 0;
			var NowgetCity = cityData[NowIndex];
				var opt = document.createElement("option");opt.innerHTML = opt.value = "城市";
			    city.appendChild(opt);
				if(valdata.length>0)
					{
					exiatval=1
					}else
					{
					exiatval=0
					}
					
				for(var i=0;i<NowgetCity.length;i++)
				{
				    if (exiatval==1)
					{
						if (valdata[1]==NowgetCity[i])
						{
						cityNowIndex=i+1
						}
					}
					var opt = document.createElement("option");
					opt.innerHTML = opt.value = NowgetCity[i];
					city.appendChild(opt);
					
				}
			}
			else
			{
			var opt = document.createElement("option");opt.innerHTML = opt.value = "城市";
			city.appendChild(opt);
			}
			city.selectedIndex = cityNowIndex;
			//改变省
			country.onchange = function ()
			{
			    
				var getCity = cityData[this.selectedIndex];
				city.length = 0;
				var opt = document.createElement("option");opt.innerHTML = opt.value = "城市";
			    city.appendChild(opt);
				for(var i=0;i<getCity.length;i++)
				{
					var opt = document.createElement("option");
					opt.innerHTML = opt.value = getCity[i];
					city.appendChild(opt);
				}

				//if(getCity.length!=1)
				//{
					//var opt = document.createElement("option");
					//opt.innerHTML = opt.value = "其它";
					//city.appendChild(opt);
				//}
				city.selectedIndex = 0;
			}
		}
	}
function SelfXY(e){
	e= e || window.event;
    var yScrolltop;
    var xScrollleft;
    if (self.pageYOffset || self.pageXOffset) {
        yScrolltop = self.pageYOffset;
        xScrollleft = self.pageXOffset;
    } else if (document.documentElement && document.documentElement.scrollTop || document.documentElement.scrollLeft ){     // Explorer 6 Strict
        yScrolltop = document.documentElement.scrollTop;
        xScrollleft = document.documentElement.scrollLeft;
    } else if (document.body) {// all other Explorers
        yScrolltop = document.body.scrollTop;
        xScrollleft = document.body.scrollLeft;
    }
    arrayPageScroll = new Array(xScrollleft + e.clientX ,yScrolltop + e.clientY) 
    return arrayPageScroll;
}
function showPrompt(e){
	var obj=document.getElementById('NewSort1');
	
	arrMouse=SelfXY(e);
	obj.style.left=arrMouse[0]+"px";
	obj.style.top=arrMouse[1]-85+"px";
	obj.style.display="";
}
function hiddenPrompt(){
	document.getElementById('NewSort1').style.display='none';
}
//***********************
//-->
