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
	//var objtochange=document.all.getElementsByName(obj);FDF3CE
	objtochange.style.background="#DDEEFF";
}
function changebackbgcolor(objtochange)
{
	//var objtochange=document.all.getElementsByName(obj);
	objtochange.style.background="#FFFFFF";
}

function changebgcolor1(objtochange)
{
	//var objtochange=document.all.getElementsByName(obj);
	objtochange.style.background="#FDF3CE";//#CCFFCC
}
function changebackbgcolor1(objtochange)
{
	//var objtochange=document.all.getElementsByName(obj);
	objtochange.style.background="#FFFFFF";
}
function deletetext(obj)
{
if(obj.value=="�벻Ҫ���������ݣ��˷����ڣ�����������˾����Ϣ����ϵ��ʽ������Է��ͻ��п����ղ����������͵���Ϣ") obj.value="";
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
    var msgInfo	= new Array();
	msgInfo["ccontactp"]= '��������ʵ������';
	msgInfo["cemail"]= '<b>��Ҫ��</b>������Ҫ��ͨ���������ע�ᣬ����д���õĵ������䣬��Ҳ�ǿͻ���ϵ������ѡ��ʽ��<br>û�е������䣿�����ѻ�ȡ��<a href=\"http://www.126.com\" target=\"_blank\" class=\"NOL\"><font color=\"#ff0000\">��������<\/font><\/a>';
	msgInfo["cmobile"]= '��������д���Ա�Ǳ�ڿͻ���ʱ����ȡ����ϵ��<br>�й�������Դ�����������κ��շѷ���';
	msgInfo["cname"]= '*����ע����ҵ��������������д�ڹ��̾�ע���ȫ�ơ�<br> �磺�㽭��������Դ�����޷ĳ���<br>*���̺ŵĸ��徭Ӫ����дִ���ϵ�����������ע���徭Ӫ���磺���徭Ӫ��������'
	msgInfo["cadd"]= '���ڴ���д��˾��ַ��<br>�磺�������������266��ӿ��㳡�۾���209�ҡ�';
	msgInfo["cprovince"]= '������ʡ�ݣ�<br>�磺�㽭';
	msgInfo["province"]= '��ѡ��ʡ�ݣ�';
	msgInfo["city"]= '��ѡ����У�';
	msgInfo["czip"]= '�������ʱ�';
	msgInfo["ctel"]= '���Ҫ�������̶��绰���룬��ʹ��"/"�ָ����ֻ�������"-"�ָ�';
	msgInfo["czip"]= '�������ʱ�';
	msgInfo["compass"]= '������6-20��Ӣ����ĸ(���ִ�Сд)��������ɣ���������׼ǡ��Ѳµ�Ӣ��������ϡ�';
	msgInfo["compasscomfirm"]= '��������һ����������д�����롣';
	 var msgerrInfo	= new Array();
	msgerrInfo["ccontactp"]= '��������ʵ������';
	msgerrInfo["cemail"]= '���������ʽ����ȷ����������ȷ�ĵ��������ַ!';
	msgerrInfo["cmobile"]= '��������д���Ա�Ǳ�ڿͻ���ʱ����ȡ����ϵ��<br>�й�������Դ�����������κ��շѷ���';
	msgerrInfo["cname"]= '�����빫˾����'
	msgerrInfo["cadd"]= '���ڴ���д��˾��ַ��<br>�磺�������������266��ӿ��㳡�۾���209�ҡ�';
	msgerrInfo["cprovince"]= '������ʡ�ݣ�<br>�磺�㽭';
	msgerrInfo["province"]= '��ѡ��ʡ�ݣ�';
	msgerrInfo["city"]= '��ѡ����У�';
	msgerrInfo["czip"]= '�������ʱ�';
	msgerrInfo["ctel"]= '������绰';
	msgerrInfo["compass"]= '�����õ���������������6-20��Ӣ����ĸ(���ִ�Сд)���������';
	msgerrInfo["compasscomfirm"]='������������벻һ�£���������һ����������д�����롣'
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
    	switch(obj.id){
        	case "cemail":
			    //alert (obj.id)
            	outemail(obj,1);
            	break;
			case "cname":
            	outcomname(obj,1);
            	break;
			case "compass":
            	outpass(obj,1);
            	break;
			case "compasscomfirm":
            	outpasscomfirm(obj,1);
            	break;
			case "ccontactp":
            	outcomname(obj,1);
            	break;
			case "ctel":
            	outcomname(obj,1);
            	break;
			case "cprovince":
            	outcomname(obj,1);
            	break;
			case "cadd":
            	outcomname(obj,1);
            	break;
			case "czip":
            	outcomname(obj,1);
            	break;
        	default:
            	outcomname(obj,0);
            	break;
    	}
	}
//************************************
function focuscomname(obj,evnt){
	var infoboxOkClass="sbtishi_on";
	var infoboxinfo=obj.id+"Info"
	var infobox= document.getElementById(infoboxinfo);
	infobox.className = infoboxOkClass;
	infobox.innerHTML=msgInfo[obj.name];
}
function outcomname(obj,evnt){
	var infoboxOkClass="sbtishi_off";
	var infoboxNOClass="sbtishi_err";
	var infoboxinfo=obj.id+"Info"
	var infobox= document.getElementById(infoboxinfo);
	if (Jtrim(document.getElementById(obj.id).value)!='')
	{
	    infobox.className = infoboxOkClass;
		infobox.innerHTML='��д��ȷ��';
	}else
	{
		if (evnt==1)
		{
			//infobox.className = infoboxOkClass;
		infobox.className = infoboxNOClass;
		infobox.innerHTML=msgerrInfo[obj.name];
		}else{
			
			infobox.className = infoboxOkClass;
		}
	}
}
function outemail(obj,evnt){
	var infoboxOkClass="sbtishi_off";
	var infoboxNOClass="sbtishi_err";
	var infoboxinfo=obj.id+"Info"
	var infobox= document.getElementById(infoboxinfo);
		if (evnt==1)
		{
		//infobox.className = infoboxNOClass;
		checkEmail(obj)
		//infobox.innerHTML=msgerrInfo[obj.name];
		}else{
			infobox.className = infoboxOkClass;
		}
}
function outpass(obj,evnt){
	var infoboxOkClass="sbtishi_off";
	var infoboxNOClass="sbtishi_err";
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
				infobox.innerHTML="��д��ȷ��";
				}
			}
		}else{
			infobox.className = infoboxOkClass;
		}
}
function outpasscomfirm(obj,evnt){
	var infoboxOkClass="sbtishi_off";
	var infoboxNOClass="sbtishi_err";
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
				infobox.innerHTML="��д��ȷ��";
				}
			}
		}else{
			infobox.className = infoboxOkClass;
		}
}
//************************
function checkEmail(obj) {
    var email = document.getElementById(obj.id).value;
	var infoboxOkClass="sbtishi_off";
	var infoboxNOClass="sbtishi_err";
	var infoboxinfo=obj.id+"Info"
	var infobox= document.getElementById(infoboxinfo);
	if (email == "") {
		//infobox.innerHTML=msgerrInfo[obj.name];
		//infobox.className = infoboxNOClass;
		return false;
		//document.getElementById('emailflag').value=0
	}else
	{
		email = email.toLowerCase();
		//alert (email)
		if(!isVaildEmail_of(email))
		{
		infobox.innerHTML = msgerrInfo[obj.name];
		infobox.className = infoboxNOClass;
		//document.getElementById('emailflag').value=0
		return false;
		}else{
		document.checkForm.action = "/cn/checkemail.asp?email="+escape(email);
		infobox.innerHTML = "����У����Ժ�......";
		infobox.className = "sbtishi_on";
		document.checkForm.submit();
		return true;
		}
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
  ��Ϸ��ʡ�������˵���(2006-06-20)
  This JavaScript was writen by Dron.
  @2003-2008 Ucren.com All rights reserved.
\*******************************************/
	function Dron_City(obj1,obj2,val)
	{
		var country = document.getElementById(obj1);
		var city = document.getElementById(obj2);
		var countryData = "�㶫|����|����|����|����|���|����|����|����|�ӱ�|ɽ��|ɽ��|������|����|�Ϻ�|����|�ຣ|�½�|����|����|�Ĵ�|����|����|���ɹ�|����|����|����|����|����|�㽭|����|̨��|���|����|����".split("|");
		var cityData = [
			"����".split("|"),
			"����|��ݸ|��ɽ|����|��Դ|����|����|����|ï��|÷��|��Զ|��ͷ|��β|�ع�|����|����|�Ƹ�|տ��|����|��ɽ|�麣".split("|"),
			"��ɫ|����|����|���Ǹ�|����|���|�ӳ�|����|����|����|����|����|����|����".split("|"),
			"����".split("|"),
			"��ɳ����������|��ͤ��������������|��������������|������|������|����|����|�ֶ�����������|�ٸ���|��ˮ����������|��|������������������|����|�Ͳ���|����|�Ĳ�|��ָɽ|����".split("|"),
			"����|����|��ƽ|����|����|Ȫ��|����|����|����".split("|"),
			"���".split("|"),
			"����|��ɳ|����|����|����|¦��|����|��̶|��������������������|����|����|����|�żҽ�|����".split("|"),
			"����|��ʩ����������������|�Ƹ�|��ʯ|����|����|Ǳ��|��ũ������|ʮ��|����|����|�人|����|����|�差|Т��|�˲�".split("|"),
			"����|�ױ�|��Դ|����|����|����|����|ƽ��ɽ|����Ͽ|����|����|����|���|֣��|�ܿ�|פ���|���|���".split("|"),
			"����|����|�е�|����|��ˮ|�ȷ�|�ػʵ�|ʯ��ׯ|��ɽ|��̨|�żҿ�".split("|"),
			"����|����|��Ӫ|����|����|����|����|�ĳ�|����|�ൺ|����|̩��|����|Ϋ��|��̨|��ׯ|�Ͳ�".split("|"),
			"����|��ͬ|����|����|�ٷ�|����|˷��|̫ԭ|����|��Ȫ|�˳�".split("|"),
			"����|���˰���|������|�׸�|�ں�|����|��ľ˹|ĵ����|��̨��|�������|˫Ѽɽ|�绯|����".split("|"),
			"��ɽ|��Ϫ|����|����|����|����|��˳|����|��«��|����|����|����|Ӫ��".split("|"),
			"�Ϻ�".split("|"),
			"����|����|���ϲ���������|������|���|��Ȫ|����|���Ļ���������|¤��|ƽ��|����|��ˮ|����|��Ҵ".split("|"),
			"�������������|��������������|����|���ϲ���������|�����ɹ������������|���ϲ���������|����|��������������".split("|"),
			"������|������|���������ɹ�������|���������ɹ�������|��������������|����|����|��ʲ|��������|�������տ¶�����������|ʯ����|ͼľ���|��³��|��³ľ��|�����|���������������".split("|"),
			"����|����|����|��֥|����|�տ���|ɽ��".split("|"),
			"��ԭ|ʯ��ɽ|����|����|����".split("|"),
			"���Ӳ���Ǽ��������|����|�ɶ�|����|����|���β���������|�㰲|��Ԫ|��ɽ|��ɽ����������|üɽ|����|�ϳ�|�ڽ�|��֦��|����|�Ű�|�˱�|����|�Թ�|����".split("|"),
			"��ɽ|��������������|�������������|�º���徰����������|�������������|��ӹ���������������|����|����|�ٲ�|ŭ��������������|����|˼é|��ɽ׳������������|��˫���ɴ���������|��Ϫ|��ͨ".split("|"),
			"�׳�|��ɽ|����|����|��Դ|��Դ|��ԭ|ͨ��|�ӱ߳�����������".split("|"),
			"��������|�����׶���|��ͷ|���|������˹|���ͺ���|���ױ���|ͨ��|�ں�|�����첼|���ֹ�����|�˰���".split("|"),
			"����|����|����|����|ͭ��|μ��|����|����|�Ӱ�|����".split("|"),
			"����|����|����|����|����|����|�Ϸ�|����|����|��ɽ|����|��ɽ|����|ͭ��|�ߺ�|����|����".split("|"),
			"��˳|�Ͻ�|����|����ˮ|ǭ�������嶱��������|ǭ�ϲ���������������|ǭ���ϲ���������������|ͭ��|����".split("|"),
			"����|����|���Ƹ�|�Ͼ�|��ͨ|����|��Ǩ|̩��|����|����|�γ�|����|��".split("|"),
			"����".split("|"),
			"����|����|����|��|��ˮ|����|����|̨��|����|��ɽ|����".split("|"),
			"����|����|����|������|�Ž�|�ϲ�|Ƽ��|����|����|�˴�|ӥ̶".split("|"),
			"̨��|����|̨��|̨��|��¡|����".split("|"),
			"���|����|�½�".split("|"),
			"����".split("|"),
			"����|����|����|ŷ��|����|������".split("|")
		];
		var valdata=val.split("|");
		this.init = function ()
		{
			//���ʡ
			NowIndex=0
			country.length = 0;
			var opt = document.createElement("option");opt.innerHTML = opt.value = "ʡ��";
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
			//�����
			cityNowIndex=0
			if (NowIndex>0)
			{
			city.length = 0;
			var NowgetCity = cityData[NowIndex];
				var opt = document.createElement("option");opt.innerHTML = opt.value = "����";
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
			var opt = document.createElement("option");opt.innerHTML = opt.value = "����";
			city.appendChild(opt);
			}
			city.selectedIndex = cityNowIndex;
			//�ı�ʡ
			country.onchange = function ()
			{
			    
				var getCity = cityData[this.selectedIndex];
				city.length = 0;
				var opt = document.createElement("option");opt.innerHTML = opt.value = "����";
			    city.appendChild(opt);
				for(var i=0;i<getCity.length;i++)
				{
					var opt = document.createElement("option");
					opt.innerHTML = opt.value = getCity[i];
					city.appendChild(opt);
				}

				if(getCity.length!=1)
				{
					var opt = document.createElement("option");
					opt.innerHTML = opt.value = "����";
					city.appendChild(opt);
				}
				city.selectedIndex = 0;
			}
		}
	}

//***********************
//-->