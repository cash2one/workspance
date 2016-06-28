
/******************************************/
/*功能：鼠标移到控件的变化                */
/*参数：                                  */
/*返回：无                                */
/******************************************/
function oow(url) {	
         //alert ("modaldealog_body.asp?url="+url+"&rt="+new Date().getTime())
		OW(url,'full');
	}
function ooww(url) {	
         //alert ("modaldealog_body.asp?url="+url+"&rt="+new Date().getTime())
		OW("modaldealog_body.asp?"+url,'modal');
	}
function MO(e)
{
if (!e)
var e=window.event;
var S=e.srcElement;
while (S.tagName!="TD")
{S=S.parentNode;}
S.className="t";
}

/******************************************/
/*功能：鼠标移开控件的变化                */
/*参数：                                  */
/*返回：无                                */
/******************************************/
function MU(e)
{
if (!e)
var e=window.event;
var S=e.srcElement;
while (S.tagName!="TD")
{S=S.parentNode;}
S.className="p";
}


/* Common List Functions */
//highlight line
/******************************************/
/*功能：高亮线显示                        */
/*参数：无                                */
/*返回：无                                */
/******************************************/
function DoHL()
{
var e=window.event.srcElement;
while (e.tagName!="TR"){e=e.parentNode;}
if (e.className!='sl') e.className='hl';
}

/******************************************/
/*功能：低亮线显示                        */
/*参数：无                                */
/*返回：无                                */
/******************************************/
function DoLL()
{
var e=window.event.srcElement;
while (e.tagName!="TR"){e=e.parentNode;}
if (e.className!='sl')	e.className='';
}

function DoLLOther()
{
var e=window.event.srcElement;
while (e.tagName!="TR"){e=e.parentNode;}
alter(e.className);
if (e.className=='')	e.className='alter';
}

/******************************************/
/*功能：选择线显示                        */
/*参数：无                                */
/*返回：无                                */
/******************************************/
function DoSL()
{
var TB=e=window.event.srcElement;
while (TB.tagName!="TABLE")
{TB=TB.parentNode;}
for (var i=0;i<TB.rows.length;i++){
	if(TB.rows[i].className=='sl')TB.rows[i].className='';}
while (e.tagName!="TR"){e=e.parentNode;}
e.className=(e.className=='sl')?'':'sl';
}

/******************************************/
/*功能：select line                       */
/*参数：                                  */
/*返回：                                  */
/******************************************/
function hL(E){
while (E.tagName!="TR")
{E=E.parentNode;}
E.className="sl";
}
/******************************************/
/*功能：diselect line                     */
/*参数：                                  */
/*返回：                                  */
/******************************************/
function dL(E){
while (E.tagName!="TR")
{E=E.parentNode;}
E.className="";
}

/* Common Form Functions */
var aCh="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
var dCh="0123456789.";
var asCh=aCh + dCh + "!\"#$%&'()*+,-./:;<=>?@[\]^_`{}~";
var folderID="";
ie=document.all?1:0

/******************************************/
/*功能： 转移地址                         */
/*参数： 需要转移的地址                   */
/*返回： 无                               */
/******************************************/
function G(UR)
{
if(UR)
	location.href=UR;	//+"&"+_UM;
}

/******************************************/
/*功能：判断输入的字符串是否为数字型      */
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function isNumber(S){
var Num=dCh;
for (var i=0; i < S.length; i++)
{
	if (Num.indexOf(S.charAt(i)) == -1)
		return false;
}
return true;
}

/******************************************/
/*功能：判断输入的字符串是否为Alpha数字型 */
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function isAlphaNum(S){
	var AlphaNum=aCh + dCh;
	for (var i=0; i < S.length; i++)
	{
		if (AlphaNum.indexOf(S.charAt(i)) == -1)
			return false;
	}
	return true;
}

/******************************************/
/*功能：判断输入的字符串是否为ASCII       */
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function isASCII(S){
	for (var i=0; i < S.length; i++)
	{
		if (asCh.indexOf(S.charAt(i)) == -1)
			return false;
	}
	return true;
}

/******************************************/
/*功能：判断输入的字符串是否为正确的Email */
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function isEmail(S) {
	var pass=0;
	if (window.RegExp) {
		var tempS="a";
		var tempReg=new RegExp(tempS);
		if (tempReg.test(tempS)) pass=1;
	}
	if (!pass) 
		return (S.indexOf(".") > 2) && (S.indexOf("@") > 0);
	var r1=new RegExp("(@.*@)|(\\.\\.)|(@\\.)|(^\\.)");
	var r2=new RegExp("^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\}\\~]*[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\}\\~]\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");
	return (!r1.test(S) && r2.test(S));
}

/******************************************/
/*功能：判断输入的字符串是否为短日期格式  */
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function isDate(str){ 
var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/; 
var r = str.match(reg); 
if(r==null)return false; 
var d= new Date(r[1], r[3]-1,r[4]); 
var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate() 
return newStr==str 
} 
// yyyy-mm-dd hh:mm:ss

/******************************************/
/*功能：判断输入的字符串是否为短日期时间格式*/
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function isDateTime(str){ 
var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
var r = str.match(reg); 
if(r==null)return false; 
var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds() 
return newStr==str 
} 


/******************************************/
/*功能：判断输入的字符串是否为正确的Email */
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function ValidateEmail(S)
{
	if (typeof(S) == "undefined"||S.value=="")return true;
	if (S.value=="")return true;
	if(isEmail(S.value))
	{
		return true;
	}
	else
	{
		alert("电子邮件（EMail）地址填写错误！");
		S.focus();
		return false;
	}
}

/******************************************/
/*功能：判断输入的字符串是否为数字型      */
/*参数：字符串                            */
/*返回：Ture、False                       */
/******************************************/
function ValidateNumber(S)
{
	if (typeof(S) == "undefined"||S.value=="")return true;
	if(isNumber(S.value))
	{
		return true;
	}
	else
	{
		alert("数字填写错误！");
		S.focus();
		return false;
	}
}

/******************************************/
/*功能：判断按键值是否有效的数字范围的值（0~9、-、.）*/
/*参数：无                                 */
/*返回：无                                 */
/******************************************/
function MaskNumber()
{
if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) 
|| (window.event.keyCode == 13) || (window.event.keyCode == 46) 
|| (window.event.keyCode == 45)))
{
//alert('kjasdlfkjasd');
window.event.keyCode = 0 ;
}
} 

function getmsg(){
if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) 
 
))
{
//alert('kjasdlfkjasd');
window.event.keyCode = 0 ;
}
}




/******************************************/
/*功能：判断输入的字符串是否为正确的短日期时间格式*/
/*参数：字符串                            */
/*返回：True、False                       */
/******************************************/
function ValidateDateTime(S)
{
	if (typeof(S) == "undefined"||S.value=="")return true;
	if(isDateTime(S.value))
	{
		return true;
	}
	else
	{
		alert("日期时间格式错误！正确格式为：YYYY-MM-DD HH:MM:SS");
		S.focus();
		return false;
	}
}

/******************************************/
/*功能：判断输入的字符串是否为正确的短日期格式*/
/*参数：字符串                            */
/*返回：True、False                       */
/******************************************/
function ValidateDate(S)
{
	if (typeof(S) == "undefined"||S.value=="")return true;
	if(isDate(S.value))
	{
		return true;
	}
	else
	{
		alert("日期格式错误！正确格式为：YYYY-MM-DD");
		S.focus();
		return false;
	}
}

/******************************************/
/*功能：设定Check项                       */
/*参数：S 控件源                          */
/*返回：                                  */
/******************************************/
function setcheck(S)
{
	if (typeof(S) == "undefined")return true;
var SC = eval('document.all.' + S);
	//if (S.checked == "false")
	//{
		SC.value = "0";
	//}
}

/******************************************/
/*功能：货币格式转换（小写-〉大写）       */
/*参数：需要转换的币值                    */
/*返回：大写货币                          */
/******************************************/
function convertCurrency(currencyDigits) {
if (trim(currencyDigits) == "") return "";
// Constants:
 var MAXIMUM_NUMBER = 99999999999.99;
 // Predefine the radix characters and currency symbols for output:
 var CN_ZERO = "零";
 var CN_ONE = "壹";
 var CN_TWO = "贰";
 var CN_THREE = "叁";
 var CN_FOUR = "肆";
 var CN_FIVE = "伍";
 var CN_SIX = "陆";
 var CN_SEVEN = "柒";
 var CN_EIGHT = "捌";
 var CN_NINE = "玖";
 var CN_TEN = "拾";
 var CN_HUNDRED = "佰";
 var CN_THOUSAND = "仟";
 var CN_TEN_THOUSAND = "万";
 var CN_HUNDRED_MILLION = "亿";
 var CN_SYMBOL = "";//人民币
 var CN_DOLLAR = "元";
 var CN_TEN_CENT = "角";
 var CN_CENT = "分";
 var CN_INTEGER = "整";
// Variables:
 var integral; // Represent integral part of digit number.
 var decimal; // Represent decimal part of digit number.
 var outputCharacters; // The output result.
 var parts;
 var digits, radices, bigRadices, decimals;
 var zeroCount;
 var i, p, d;
 var quotient, modulus;
 
// Validate input string:
 currencyDigits = currencyDigits.toString();
 if (currencyDigits == "") {
  //alert("Empty input!");
  return "";
 }
 if (currencyDigits.match(/[^,.\d]/) != null) {
  alert("Invalid characters in the input string!");
  return "";
 }
 if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
  alert("Illegal format of digit number!");
  return "";
 }
 
// Normalize the format of input digits:
 currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
 currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
 // Assert the number is not greater than the maximum number.
 if (Number(currencyDigits) > MAXIMUM_NUMBER) {
  alert("Too large a number to convert!");
  return "";
 }
 
// Process the coversion from currency digits to characters:
 // Separate integral and decimal parts before processing coversion:
 parts = currencyDigits.split(".");
 if (parts.length > 1) {
  integral = parts[0];
  decimal = parts[1];
  // Cut down redundant decimal digits that are after the second.
  decimal = decimal.substr(0, 2);
 }
 else {
  integral = parts[0];
  decimal = "";
 }
 // Prepare the characters corresponding to the digits:
 digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
 radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
 bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
 decimals = new Array(CN_TEN_CENT, CN_CENT);
 // Start processing:
 outputCharacters = "";
 // Process integral part if it is larger than 0:
 if (Number(integral) > 0) {
  zeroCount = 0;
  for (i = 0; i < integral.length; i++) {
   p = integral.length - i - 1;
   d = integral.substr(i, 1);
   quotient = p / 4;
   modulus = p % 4;
   if (d == "0") {
    zeroCount++;
   }
   else {
    if (zeroCount > 0)
    {
     outputCharacters += digits[0];
    }
    zeroCount = 0;
    outputCharacters += digits[Number(d)] + radices[modulus];
   }
   if (modulus == 0 && zeroCount < 4) {
    outputCharacters += bigRadices[quotient];
   }
  }
  outputCharacters += CN_DOLLAR;
 }
 // Process decimal part if there is:
 if (decimal != "") {
  for (i = 0; i < decimal.length; i++) {
   d = decimal.substr(i, 1);
   if (d != "0") {
    outputCharacters += digits[Number(d)] + decimals[i];
   }
  }
 }
 // Confirm and return the final output string:
 if (outputCharacters == "") {
  outputCharacters = CN_ZERO + CN_DOLLAR;
 }
 if (decimal == "") {
  outputCharacters += CN_INTEGER;
 }
 outputCharacters = CN_SYMBOL + outputCharacters;
 return outputCharacters;
}

/******************************************/
/*功能：弹出窗体                          */
/*参数：URL 地址,TYPE 模式/全屏,SC 是否显示滚动条*/
/*      iW 宽度,iH 高度,TOP 头部位置,LEFT 左边位置,*/
/*      R 改变大小,S 状态栏显示,T,TB 帮助显示*/
/*返回：无                                */
/******************************************/
function OW(URL,TYPE,SC,iW,iH,TOP,LEFT,R,S,T,TB)
{
	var sF="dependent=yes,resizable=no,toolbar=no,status=no,directories=no,menubar=no,";

	sF+="scrollbars="+(SC?SC:"NO")+",";
	
	if (TYPE=="full"){
		sF+=" Width=1010,";
		sF+=" Height=750,";
		sF+=" Top=0,";
		sF+=" Left=0,";
		window.open(URL, "_blank", "");
		return;
	}
	
	if (TYPE=="modal"){
		sF ="resizable:yes; status:no; scroll:yes;";
		sF+=" dialogWidth:800px;";
		sF+=" dialogHeight:570px;";
			if(parent.length<2){
				sF+="dialogTop:"+(parseInt(parent.dialogTop)+25)+"px;";					
				sF+="dialogLeft:"+(parseInt(parent.dialogLeft)+25)+"px;";
				}
		
		var alpha = '?';
		var _URL = URL;
		var unique = (new Date()).getTime();
		//如果没有参数		
		if (_URL.indexOf(alpha) == -1){
			_URL+= "?time="+unique;
		}
		else
		{
		//如果带有参数，含有‘?’
			_URL+= "&time="+unique;
		}
		//alert(_URL.indexOf(alpha));alert(_URL);
		return window.showModelessDialog(_URL,window,sF);
	}
	else
	{
		sF+=" Width=800,";
		sF+=" Height=570,";
		sF+=" Top=50,";
		sF+=" Left=50,";
	
		window.open(URL, "_blank", sF, false);
	
	}
}


/**
 * Sets a Cookie with the given name and value.
 *
 * name       Name of the cookie
 * value      Value of the cookie
 * [expires]  Expiration date of the cookie (default: end of current session)
 * [path]     Path where the cookie is valid (default: path of calling document)
 * [domain]   Domain where the cookie is valid
 *              (default: domain of calling document)
 * [secure]   Boolean value indicating if the cookie transmission requires a
 *              secure transmission
 */
function setCookie(name, value, expires, path, domain, secure)
{
    document.cookie= name + "=" + escape(value) +
        ((expires) ? "; expires=" + expires.toGMTString() : "") +
        ((path) ? "; path=" + path : "") +
        ((domain) ? "; domain=" + domain : "") +
        ((secure) ? "; secure" : "");
}

/**
 * Gets the value of the specified cookie.
 *
 * name  Name of the desired cookie.
 *
 * Returns a string containing value of specified cookie,
 *   or null if cookie does not exist.
 */
function getCookie(name,defaultvalue)
{
    var dc = document.cookie;
    var prefix = name + "=";
    var begin = dc.indexOf("; " + prefix);
    if (begin == -1)
    {
        begin = dc.indexOf(prefix);
        if (begin != 0) {
			if (defaultvalue) {return defaultvalue;}else{return null;}
		}
	}
	else
	{
		begin += 2;
	}
	var end = document.cookie.indexOf(";", begin);
    if (end == -1)
    {
        end = dc.length;
    }
    return unescape(dc.substring(begin + prefix.length, end));
}

/**
 * Deletes the specified cookie.
 *
 * name      name of the cookie
 * [path]    path of the cookie (must be same as path used to create cookie)
 * [domain]  domain of the cookie (must be same as domain used to create cookie)
 */
function deleteCookie(name, path, domain)
{
    if (getCookie(name))
    {
        document.cookie = name + "=" + 
            ((path) ? "; path=" + path : "") +
            ((domain) ? "; domain=" + domain : "") +
            "; expires=Thu, 01-Jan-70 00:00:01 GMT";
    }
}

/******************************************/
/*功能：列表排序                          */
/*参数：列                                */
/*返回：无                                */
/******************************************/
function Orderby(col){
	var sfrm = document.searchform;
	if(!col) return;
	if(col==sfrm._orderby.value) {sfrm._sort.value=(sfrm._sort.value=="ASC")?"DESC":"ASC";}
	else {
		sfrm._orderby.value=col;
		sfrm._sort.value="ASC";
	}
	sfrm.submit();
}

/******************************************/
/*功能：字符串替换                        */
/*参数：strOrg 被替换串、strFind 、strReplace 替换值*/
/*返回：替换后的字符串                    */
/******************************************/
function ReplaceAll(strOrg,strFind,strReplace){
var index = 0;
while(strOrg.indexOf(strFind,index) != -1){
strOrg = strOrg.replace(strFind,strReplace);
index = strOrg.indexOf(strFind,index);
}
return strOrg;
}

//去除字符串的头尾空格
function trim(strSource){
 var i;
 var sTemp;
 var iIndex;
 i=strSource.length;
 if (i==0) return "";
 sTemp=strSource;
 for (iIndex=0;iIndex<i;iIndex++){
   if (strSource.substring(iIndex,iIndex+1)==" "){
   }else{
     sTemp=strSource.substring(iIndex,i);
     break;
   }
 }
 if(iIndex==i) return "";
 for (iIndex=sTemp.length;iIndex>0;iIndex--){
   if (sTemp.substring(iIndex-1,iIndex)==" "){
   }else{
     sTemp=sTemp.substring(0,iIndex);
     break;
   }
 }
 
 return sTemp;
}

//隐藏操作结果提示信息
function hideSuccessText() {
	//alert('ddd');
	//var currentStatus = document.all('successText').style.display;
	//document.all('successText').style.display = currentStatus == 'inline' ? 'none' : 'inline';
	
	var currentStatus = document.all('successText').style.display;
	document.all('successText').style.display = currentStatus == 'none' ? 'inline' : 'none';
	currentStatus = document.all('cancelButton').style.display;
	document.all('cancelButton').style.display = currentStatus == 'none' ? 'inline' : 'none';
	//document.all('successText').style.display = 'none' ;
	//document.all('cancelButton').style.display = 'none' ;
	
}


function hideLayer(theButton) {
	//var currentStatus = document.all('friendRemind').style.display;
	//document.all('friendRemind').style.display = currentStatus == 'inline' ? 'none' : 'inline';
	
	document.all(theButton).style.display = 'none';
	
}
function showAdvancedFunctionLayer(theElement) {
	var buttonText = theElement.outerText;
	var isShow = (buttonText == "显示更多高级功能" ? true : false);
	document.all('advancedFunctionLayer').style.display = isShow ? 'inline' : 'none';
	theElement.innerHTML = isShow ? '<img id="controlButton" name="controlButton" src="/images/bt1.gif" border="0" align="absbottom">隐藏高级功能</a>' : '<img id="controlButton" name="controlButton" src="/images/bt2.gif" border="0" align="absbottom">显示更多高级功能</a>';
}



function showHideTableListLayer(theButton) {
	var currentStatus = document.all('controlLayer').style.display;
	document.all('controlLayer').style.display = currentStatus == 'inline' ? 'none' : 'inline';
	document.all('controlButton').src = currentStatus == 'inline' ? '/images/bt2.gif' : '/images/bt1.gif';
	document.all('controlButton').alt = currentStatus == 'inline' ? '收缩' : '展开';
	changeMainFrameHeight();
}	

function showAdvancedSearchLayer(theElement, startRow) {
	var isShow = theElement.value == '显示高级搜索>>>'
	var searchTable = document.all('advancedSearchTable');
	var rows = searchTable.rows;
  for (i = startRow; i < rows.length; i++) {
  	searchTable.rows(i).style.display = isShow ? 'inline' : 'none';
  }
  theElement.value = isShow ? '隐藏高级搜索<<<' : '显示高级搜索>>>';
}

function showAdvancedSearchLayer1(theElement, startRow) {
	var isShow = theElement.value == '显示更多搜索条件>>>'
	var searchTable = document.all('advancedSearchTable');
	var rows = searchTable.rows;
  for (i = startRow; i < rows.length; i++) {
  	searchTable.rows(i).style.display = isShow ? 'inline' : 'none';
  }
  theElement.value = isShow ? '隐藏更多搜索条件<<<' : '显示更多搜索条件>>>';
}
	
window.onload = function() {
		/*	if (parent.document.all('cardframe')) {
		parent.document.all('cardframe').height=document.body.scrollHeight;
	}*/
		if (parent.document.getElementById('cardframe')) {
		parent.document.getElementById('cardframe').height=document.body.scrollHeight;
	}

	/*if (parent.parent.document.all('mainFrame')) {
		parent.parent.document.all('mainFrame').height=parent.document.body.scrollHeight;
	}*/
	if (document.getElementById('mainFrame')) {
		var objFrame = document.getElementById('mainFrame');
		if (objFrame.Document && objFrame.Document.body.scrollHeight){ 
			objFrame.height = objFrame.Document.body.scrollHeight; 	
		}
	}

	if (parent.document.getElementById('mainFrame')) {
		var objFrame = parent.document.getElementById('mainFrame');
		if (objFrame.Document && objFrame.Document.body.scrollHeight){ 
			objFrame.height = objFrame.Document.body.scrollHeight; 	
		}
	}
	
	if (parent.parent.document.getElementById('mainFrame')) {
		parent.parent.document.getElementById('mainFrame').height=parent.document.body.scrollHeight;
	}

}


function changeMainFrameHeight() {
	if (document.getElementById('cardframe')) {
		var objFrame = document.getElementById('cardframe');
		if (objFrame.Document && objFrame.Document.body.scrollHeight){ 
			objFrame.height = objFrame.Document.body.scrollHeight; 	
		}
	}

	if (parent.document.getElementById('cardframe')) {
		var objFrame = parent.document.getElementById('cardframe');
		if (objFrame.Document && objFrame.Document.body.scrollHeight){ 
			objFrame.height = objFrame.Document.body.scrollHeight; 	
		}
	}
	
	if (document.getElementById('mainFrame')) {
		var objFrame = document.getElementById('mainFrame');
		if (objFrame.Document && objFrame.Document.body.scrollHeight){ 
			objFrame.height = objFrame.Document.body.scrollHeight; 	
		}
	}

	if (parent.document.getElementById('mainFrame')) {
		var objFrame = parent.document.getElementById('mainFrame');
		if (objFrame.Document && objFrame.Document.body.scrollHeight){ 
			objFrame.height = objFrame.Document.body.scrollHeight; 	
		}
	}
	
	if (parent.parent.document.getElementById('mainFrame')) {
		parent.parent.document.getElementById('mainFrame').height=parent.document.body.scrollHeight;
	}
}


/******************************************/
/*功能：textarea字数检测									*/
/*textLimitCheck(id,最小长度,最大长度)    */
/*参数：无                                */
/*返回：无                                */
/******************************************/
	function checkContactDetailInput (record,maxLength) {
	    if (record.value.length > maxLength)
		{
			alert("该字段最多允许输入"+maxLength+"个字符\r多余的字符将被删除.");
			record.value = record.value.substring(0, maxLength);
			record.focus();
		}
	}
	
	
	
			var checkOk = new Array();
			checkOk[0] = "12288";
			checkOk[1] = "8364";
			function isOkChar(ch)
			{
				for (j = 0;  j < checkOk.length;  j++)
					if (ch == checkOk[j])
					{
						return true;
					}
				return false;
				
			}
			function textLimitCheck(thisArea, minLength,maxLength)
			{
						var len=0;
						var index=0;
						var length=0;
						for (i = 0;  i < thisArea.value.length;  i++)
					  { 				
						ch = thisArea.value.charCodeAt(i);				
						 if (ch > 256 && !isOkChar(ch)){      
							  len+=2;  //中文
						}else{
						   len+=1;
						}
						if (len<=maxLength){
						 		index=i;
								length=len;
						 }else{
						     break;
						 }
					}
						if (len > maxLength)
						{
							alert("该字段最多允许输入"+maxLength+"个字符\r多余的字符将被删除.");
							thisArea.value = thisArea.value.substring(0, index+1);
							thisArea.focus();
							return false;
						}
						if (len < minLength)
						{
							alert("记录说明字段不能少于"+minLength+"个字符.");
							thisArea.focus();
							return false;
						}
						return true;
						
				}
				
/**
 * 检测email
 * http://javascript.internet.com
 */
function checkEmail(emailStr) {
   if (emailStr.length == 0) {
       return true;
   }
   var emailPat=/^(.+)@(.+)$/;
   var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
   var validChars="\[^\\s" + specialChars + "\]";
   var quotedUser="(\"[^\"]*\")";
   var ipDomainPat=/^(\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})$/;
   var atom=validChars + '+';
   var word="(" + atom + "|" + quotedUser + ")";
   var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
   var domainPat=new RegExp("^" + atom + "(\\." + atom + ")*$");
   var matchArray=emailStr.match(emailPat);
   if (matchArray == null) {
       return false;
   }
   var user=matchArray[1];
   var domain=matchArray[2];
   if (user.match(userPat) == null) {
       return false;
   }
   var IPArray = domain.match(ipDomainPat);
   if (IPArray != null) {
       for (var i = 1; i <= 4; i++) {
          if (IPArray[i] > 255) {
             return false;
          }
       }
       return true;
   }
   var domainArray=domain.match(domainPat);
   if (domainArray == null) {
       return false;
   }
   var atomPat=new RegExp(atom,"g");
   var domArr=domain.match(atomPat);
   var len=domArr.length;
   if ((domArr[domArr.length-1].length < 2) ||
       (domArr[domArr.length-1].length > 3)) {
       return false;
   }
   if (len < 2) {
       return false;
   }
   return true;
}				
/******************************************/
/*功能：检测中文									*/
/*textLimitCheck(id,最小长度,最大长度)    */
/*参数：无                                */
/*返回：无                                */
/******************************************/
function checkCN(thisArea,theID){
    for (i = 0;  i < thisArea.value.length;  i++){
	ch = thisArea.value.charCodeAt(i);					
	if (ch > 256 && !isOkChar(ch)){
	    alert(theID+" 字段不允许输入中文");  
	    thisArea.focus();    
	    return false;  //中文
	}	
   }
   return true;				
}


/******************************************/
/*功能：回车焦点跳转			  								*/	
/*                                        */
/*参数：无                                	*/
/*返回：无                                	*/
/******************************************/
function jsCtrlBSKey(e) {
    var e=window.event;
    if (e.keyCode == 13 && e.srcElement.type!="button" && e.srcElement.type!="submit" && e.srcElement.type!="textarea") 
	{
	      e.keyCode = 9;
	      return;
	}
}
function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	if (menu)
	{
	for(var i=0;i<=menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
	}
	}
}
function selectCheckBox(boxname,thevalue)
{
	var boxes = document.form1.elements[boxname];
	for(var i=0;i<boxes.length;i++){
		if(thevalue.indexOf(boxes[i].value)>=0)
		{
			boxes[i].checked = true;
		}
	}
}
function UTF8UrlEncode(input){    
   
        var output = "";    
   
        var currentChar = '';    
   
        for(var counter = 0; counter < input.length; counter++){    
   
            currentChar = input.charCodeAt(counter);    
   
            if((0 <= currentChar) && (currentChar <= 127))    
   
                output = output + UTF8UrlEncodeChar(currentChar);    
   
            else   
   
                output = output + encodeURIComponent(input.charAt(counter));    
   
        }    
   
        var reslut = output.toUpperCase();    
        return reslut.replace(/%26/, "%2526");     
    } 
function UTF8UrlEncodeChar(input){    

	if(input <= 0x7F) return "%" + input.toString(16);    

	var leadByte = 0xFF80;    

	var hexString = "";    

	var leadByteSpace = 5;    

	while(input > (Math.pow(2, leadByteSpace + 1) - 1)){    

		hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;    

		leadByte = (leadByte >> 1);    

		leadByteSpace--;    

		input = input >> 6;    

	}    

	return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString).toUpperCase();    

}

function geturlcode(url)
{
	
	if (window.XMLHttpRequest)
	{ 
		// code for IE7+, Firefox, Chrome, Opera, Safari 
		//适用于IE7以上版本浏览器，火狐浏览器，Chrome浏览器，Opera浏览器，Safari浏览器
		xmlhttp=new XMLHttpRequest(); 
	} 
	else if (window.ActiveXObject)
	{ 
		// code for IE6, IE5 
		//适用于IE6，IE5
		xmlhttp=new ActiveXObject( "Microsoft.XMLHTTP");
	} 
	else
	{ 
	} 
	//发送XMLHTTP请求
	
	var url=decodeURI("show/showhtml.asp?fromurl="+url)
	xmlhttp.open("post",url,false);
	//alert(url)
	///xmlhttp.setRequestHeader("Content-Type","utf-8");
	//xmlhttp.setRequestHeader("content-type","text/html");
	xmlhttp.send(null);
	
	if(xmlhttp.readyState==4) 
	{ 
		
		if(xmlhttp.status==200) 
		{ 
			var textvalueNow=xmlhttp.responseText;
			//alert(textvalueNow);
			return textvalueNow;
		}
	}
}

