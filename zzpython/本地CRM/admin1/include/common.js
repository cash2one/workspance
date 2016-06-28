/****************************************************************
        ģ�飺	ͨ��webҳ�������
        ���룺  
        ˵����  ʹ��ǰ��ҳ���Ϲ����Ҫ����ҳ��Ԫ�ؼ�Ҫ�󼴿�
        ���ߣ�	sammi
        ʱ�䣺	2002-7-16 11:04
        ��Ȩ��  ����������Ȩ����������Ѷ�����ϵͳ���޹�˾���У�
                δ����Ȩ����ɣ��κ��˲������κ���ʽ���и��ơ��ַ���ʹ�á�
                All rights reserved��
****************************************************************/

//if the value is number return true
function IsNumber(value){
	var str=new String(value);
	var NUM=new String("0123456789");
	for(var I=0;I<str.length;I++){
		if(NUM.indexOf(str.charAt(I))<0)
			return false;
	}
	return true;
}

function FillYear(yearID,d){
	var yearList=document.all(yearID);
//	for(var I=2003;I<2015;I++){
	for(var I=2003;I>1930;I--){
		var oOption = document.createElement("OPTION");
		oOption.text=""+I;
		oOption.value=""+I;
		if(I==1979)	oOption.selected=true;
		yearList.add(oOption);
	}
}



function FillMonth(monthID){
	var monthList=document.all(monthID);
	for(var I=1;I<13;I++){
	var oOption = document.createElement("OPTION");
		if(I<10){
			oOption.text="0"+I;
			oOption.value="0"+I;
		}else{
			oOption.text=""+I;
			oOption.value=""+I;
		}
		monthList.add(oOption);
	}
}

function FillDay(dayID,maxDay){
	var count=document.all(dayID).length;
	for(var I=0;I<count;I++)
		document.all(dayID).remove(0);
		
	for(var I=1;I<=maxDay;I++){
	var oOption = document.createElement("OPTION");
		if(I<10){
			oOption.text="0"+I;
			oOption.value="0"+I;
		}else{
			oOption.text=""+I;
			oOption.value=""+I;
		}
			
		document.all(dayID).add(oOption);
	}		
}

//FillYear in Dateing 2002-09-09 By sammi
function FillYearInDate(yearID){
	var yearList=document.all(yearID);
	mydate=new Date();
	myyear= mydate.getYear();
	for(var I=2002;I<=2015;I++){
		var oOption = document.createElement("OPTION");
		oOption.text=""+I;
		oOption.value=""+I;
		if((myyear+1)==new Number(I))	oOption.selected=true;
		yearList.add(oOption);
	}
}

function FillMonthInDate(monthID){
	var monthList=document.all(monthID);
	mydate=new Date();
	mymonth=mydate.getMonth()+1;
	//alert(mymonth+1);
	for(var I=1;I<13;I++){
	var oOption = document.createElement("OPTION");
		if(I<10){
			oOption.text="0"+I;
			oOption.value="0"+I;
		}else{
			oOption.text=""+I;
			oOption.value=""+I;
		}
		if((mymonth+1)==new Number(I))	oOption.selected=true;
		monthList.add(oOption);
	}
	
}

function FillDayInDate(dayID,maxDay){
	var count=document.all(dayID).length;
	mydate=new Date();
	mymonth=mydate.getMonth()+1;
	myday= mydate.getDate();		
	for(var I=0;I<count;I++)
		document.all(dayID).remove(0);
		
	for(var I=1;I<=maxDay;I++){
	var oOption = document.createElement("OPTION");
		if(I<10){
			oOption.text="0"+I;
			oOption.value="0"+I;
		}else{
			oOption.text=""+I;
			oOption.value=""+I;
		}		
		if((myday+5)==new Number(I))	oOption.selected=true;
		if( myday+5>30 )	
		{
			var selectName = document.FORM.month;
			for(var i=0;i<selectName.options.length;i++)
			{
				if(selectName.options[i].value==mymonth+1)
					selectName.options[i].selected=true;
			}   
		}
		document.all(dayID).add(oOption);
	}		
}

function FillDayInDate1(dayID,maxDay){
	var count=document.all(dayID).length;
	for(var I=0;I<count;I++)
		document.all(dayID).remove(0);
		
	for(var I=1;I<=maxDay;I++){
	var oOption = document.createElement("OPTION");
		if(I<10){
			oOption.text="0"+I;
			oOption.value="0"+I;
		}else{
			oOption.text=""+I;
			oOption.value=""+I;
		}
		mydate=new Date();
		myday= mydate.getDate();		
		if((myday+1)==new Number(I))	oOption.selected=true;
		document.all(dayID).add(oOption);
	}		
}


function YearChange(yearID,monthID,dayID){
	document.all(monthID).selectedIndex=0;
	MonthChange(yearID,monthID,dayID);
}

function MonthChange(yearID,monthID,dayID){
	var nowYear=new Number(document.all(yearID).value);
	var nowMonth=new Number(document.all(monthID).value);
	var maxDay=30;
	
	if(IsLeapYear(nowYear)){
	 if (nowMonth==2)
		maxDay=29;	
	}else{
	 if (nowMonth==2)
		maxDay=28;	
	}
 	if((nowMonth==1) || (nowMonth==3) || (nowMonth==5) || (nowMonth==7) || (nowMonth==8) || (nowMonth==10) || (nowMonth==12))
		maxDay=31;
	FillDay(dayID,maxDay);
	document.all(dayID).selectedIndex=0;		
}

function GetNowDate(yearID,monthID,dayID){
	return document.all(yearID).value+document.all(monthID).value+document.all(dayID).value;
}
//when's format is YYYYMMDD
function ChangeDate(yearID,monthID,dayID,when){
	var str=new String(when);
	var year=str.substring(0,4);
	var month=str.substring(4,6);
	var day=str.substring(6,8);

	Find(yearID,year)
	Find(monthID,month)
	Find(dayID,day)
}	

function Find(id,value){
	for(var I=0;I<document.all(id).length;I++){
		if(document.all(id).options(I).value==value){
			document.all(id).selectedIndex=I;
			break;
		}
	}		
}

<!--- is the Year LeapYear? True is Leap --->
function IsLeapYear(Year) 
{
	if(Math.round(Year/4) == Year/4){
		if(Math.round(Year/100) == Year/100){
			if(Math.round(Year/400) == Year/400)
				return true;
			else return false;
		}else return true;
	}
	return false;
}

//Get age from birthday



//Get constellation from birthday


function getSex(sex)
{
	if(sex == "0")
		document.write("��");
	else if(sex == "1")
		document.write("Ů");
	else 
		document.write("����");
}

function getSexColor(sex)
{
	if(sex == "0")
		document.write("<font color='0000FF'>");
	else
		document.write("<font color='FF0000'>");
	
}

function getCall(sex)
{
	if(sex == "0")
		document.write("����");
	else
		document.write("С��");
	
}

function getCountry(Num)
{
	country = new Array("�л����񹲺͹�","����������","����������","������","����͢","����������������","��³��",
		"����","�����ݽ�","����","���������","������","��ɳ����","������","������","��������","����ϺͰͲ���",
		"�µ���","�Ĵ�����","�ͰͶ�˹","�Ͳ����¼�����","�͹���","�ͻ�˹̹","������","����","������","����","�׶���˹","��Ľ��Ⱥ��","��������","����������Ⱥ��",
		"����","����ʱ","����","�������","����","��˹���Ǻͺ�����ά��","����ά��","������","��������","����","���е�ӡ��������","�����ɷ���","��¡��","��Τ��","����","���������","����","�¹�","������","���","�������","������˹��͹�","����˹","��϶��",
		"����������","����","�����ϲ����ϼ���","����Ⱥ��","��������������","����������","��ٸ�","���ɱ�","쳼�Ⱥ��","����","��ý�Ⱥ��","������Ⱥ�������ά��˹Ⱥ����","�Ա���","�չ�","�չ��������͹�","���ױ���","��˹�����","�����ɴ�","������","��³����","�Ű�","�ϵ����յ���������","�ص�","������",
		"������˹̹","����","����","����","����������˹Ⱥ��","�յº��������Ⱥ��","�鶼��˹","�����˹","������","������˹˹̹","������","�����Ǳ���","���ô�","����","����","����կ","�ݿ˹��͹�","��Ͳ�Τ","����¡","������","����Ⱥ��","�ƿ�˹Ⱥ��","��Ħ��","���ص���",
		"������","���޵��ǣ��ն��ߴο���","������","���Ⱥ��","����ά��","������","����","�����","������","��������","������","��֧��ʿ��","��������","¬ɭ��","¬����","��������","����˹�ӵ�","�������","�����","����ά","��������","����","����ٹ��͹�","���ܶ�Ⱥ��","������˵�","��Լ�ص�",
		"ë����˹","ë��������","����","������Ħ��","����ά����Ⱥ��","����С������Ⱥ��","�ɹ�","����������(Ӣ)","�ϼ�����","��³","�ܿ���������","���","Ħ������","Ħ���","Ħ�ɸ�","Īɣ�ȿ�","ī����","���ױ���","�Ϸ�","�ϼ���","�������Ǻ���ɣ������Ⱥ��","��˹����","�³","�Ჴ��","�������",
		"���ն�","��������","Ŧ��","Ų��","ŵ���˵�","����","Ƥ�ؿ˶�Ⱥ��","������","�ձ�","���","��ʿ","�����߶�","��Ħ��","��������","���ڼӶ�","����·˹","�����Ⱥ��","ɳ�ذ�����","ʥ����","ʥ��������������","ʥ�����õ�","ʥ���ĺ���ά˹","ʥ¬����","ʥ����ŵ","ʥƤ���������ܿ�¡��","ʥ��ɭ�غ͸����ɶ�˹",
		"˹������","˹�工��","˹��������","˹�߶���Ⱥ����������","˹��ʿ��","�յ�","������","������Ⱥ��","������","������˹̹","̩��","̹ɣ����","����","�ؿ�˹Ⱥ���Ϳ���˹Ⱥ��","�������Ͷ�͸�","ͻ��˹","ͼ��¬","������","������˹̹","�п���","����˹Ⱥ���͸�ͼ��Ⱥ��","��Ŭ��ͼ",
		"Σ������","ί������","����","�ڸɴ�","�ڿ���","������","���ȱ��˹̹","������","ϣ��","�¼���","�¿��������","������","������","������","�����","��������","Ҳ��","������","����","��ɫ��","�����","ӡ��","ӡ��������","Ӣ��","Ӣ��ά����Ⱥ��","Լ��","Խ��","�ޱ���","է��","ֱ������","����","�зǹ��͹�");		
	Num = Num%100;
	document.write(country[Num]);
}
 
function getPlace(placeNum)
{
	if(placeNum=='-1')	
	{
		document.write('---');
		return;
	}
	var aNum=new Number(placeNum);
	city = new Array("����","���","�Ϻ�","����","�ӱ�ʡ","����ʡ","������ʡ","����ʡ","����ʡ","ɽ��ʡ","���ɹ�","����ʡ","����ʡ",
		"ɽ��ʡ","����ʡ","����ʡ","�㽭ʡ","����ʡ","����ʡ","����ʡ","����ʡ","�Ĵ�ʡ","����ʡ","�½�","����","�ຣʡ",
		"����","����","�㶫ʡ","����ʡ","����ʡ","̨��ʡ","���","����");
	
	var num = aNum%100;
	document.write(city[num]);
}

function getIntent(intentNum)
{
	intent = new Array("���������","�������","����","����","�ΰ�","����");
	document.write(intent[intentNum]);	
}

function getSomatotype(SomatotypeNum)
{
	Somatotype = new Array("����/��ϸ","�ȳ�","��׳","����","�ΰ�","��˶");
	document.write(Somatotype[SomatotypeNum]);	
}

function getBlood(BloodNum)
{
	Blood = new Array("����","A","B","AB","O");
	document.write(Blood[BloodNum]);	
}

function getMarital(MaritalNum)
{
	Marital = new Array("δ��","�ѻ�","��������Ů","��������Ů","�־�","ɥż");
	document.write(Marital[MaritalNum]);	
}

function getVocation(VocationNum)
{
	Vocation = new Array("","����ҵ","�ɿ�/��Դ","����/����","����/����","��Ѷҵ","���ز�","������ҵ","��װҵ","������֯","���ҵ","���պ���","��ѧ/����","����/����","����ҵ","����/��ѵ","��������","�����ϵͳ","�����Ӳ��","����ұ��","����/����","����","���","����/����","ý��/����","ľ��/��ֽ","����/����","ũҵ","����ҵ","˾��/��ʦ","˾��","�����˶�","ѧ������","��������","ҽ�Ʒ���","����/���","����/����","������","����/�赸","����/���","����ҵ","��������","����/��е","��ѯ����","����");
	document.write(Vocation[VocationNum]);	
}

function getEducation(EducationNum)
{
	Education = new Array("Сѧ","����","����","ר��","ѧʿ","˶ʿ/˫ѧʿ","��ʿ");
	document.write(Education[EducationNum]);	
}

function getIncome(IncomeNum)
{
	Income = new Array("����","1000Ԫ����/��","1000-2000Ԫ/��","2001-4000Ԫ/��","4001-8000Ԫ/��","8001-20000Ԫ/��","20000Ԫ����/��");
	document.write(Income[IncomeNum]);	
}

function getPosition(PositionNum)
{
	Position = new Array("","ִ�й�/����","ר��","����/��ʦ","������Ա/����ʦ","������Ա","�����ɲ�","����/�г�","������","����ְҵ��","��Ա/����","ѧ��","ʧҵ","��/����","����","��ְͨԱ","����");
	document.write(Position[PositionNum]);	
}

function getLanguage(LanguageNum)
{
	var iBit;
	var iFirst;
	Language = new Array("��ͨ��","�Ϻ���","�㶫��","Ӣ��","����","����","����","�������","����","��������","����","��������");

	iBit = 1;iFirst = 1;
  	for(var i=0;i<Language.length;i++)
	{
		if (i>0) iBit = iBit*2;
		if((LanguageNum & iBit) == iBit)
		{
			if (iFirst==0) document.write(",");
			document.write(Language[i]);
			iFirst = 0;
		}
		
	}  
}

function getDress(DressNum)
{
	Dress = new Array("������","��ǰ��","��ʽװ","����ʽװ");
	document.write(Dress[DressNum]);	
}

function getDrink(DrinkNum)
{
	Drink = new Array("�ξƲ�մ","ż����һ��","��ʱ��һЩ","������","���ȹ���","����ơ��","���Ȱ׾�");
	document.write(Drink[DrinkNum]);	
}

function getSmoke(SmokeNum)
{
	Smoke = new Array("������","ż��","��ʱ","����");
	document.write(Smoke[SmokeNum]);
}

function getCharactor(CharactorValue)
{
	var iFirst;
	Charactor = new Array("�Ը�����","�Ը�����","���ÿ���","��Ĭ����","��Ĭ","����","�ḡ","�嶯","��ǿ","����","����","����","��˵���","��˽","���","����","����","����","�Ը�","�Ա�","��������","����","�ݽ�","С������","����","��ǿ","����˳��","����С��","��������","���ʹ㷺","��ˬ","����","�ƻ��Ʊ�","��ֱ","��α","�ֹ�����","��������","�����ѻ�","��Ƨ","������ĥ","��С����","�����ҵ�","����Ϊ��","��ʵ","�ؾ�","����","�ٶ�","���","����","����Ѷ�","��������","�̱�","��������","��ӹ����","ʱϲʱ��","���û�ʧ","���Կ���","���Ų��","����Ƹ�","ѭ�浸��");
	iFirst = 1;
	for(i=0; i<=CharactorValue.length-1 ;i++)
	{
		if(CharactorValue.substring(i,i+1)=="1")
		{
			if (iFirst==0) document.write(",");
			document.write(Charactor[i]);
			iFirst=0;
		}
	}
}


function getInterest(InterestValue)
{
	var iFirst;
	Interest = new Array("��ʳ","����","����","��Ӱ","����","Ϸ��","����","����","����","������","��Ϸ","�滭","�鷨","����","ʱ��","����","�Ķ�","����","�����˶�","����","����","����","�ڽ�","׬Ǯ");
	
	iFirst = 1;
	for(i=0; i<=InterestValue.length-1 ;i++)
	{
		if(InterestValue.substring(i,i+1)=="1")
		{
			if (iFirst==0) document.write(",");
			document.write(Interest[i]);
			iFirst=0;
		}
	}
}

function getSport(SportValue)
{
	var iFirst;
	Sport = new Array("��Ӿ","����","��ë��","ƹ����","����","F1","����","����","����","��ɽ","Χ��","��������","����","������","������","����","����","����","����","���г�","����","���","��ѩ","�߶���","�ﾶ","����","��ɽ");
	
	iFirst = 1;
	for(i=0; i<=SportValue.length-1 ;i++)
	{
		if(SportValue.substring(i,i+1)=="1")
		{
			if (iFirst==0) document.write(",");
			document.write(Sport[i]);
			iFirst=0;
		}
	}
}

function getSeason(SeasonNum)
{
	var iBit;
	var iFirst;
	Season = new Array("����","����֮��","�ļ�","����֮��","�＾","�ﶬ֮��","����","����֮��");

	iBit = 1;iFirst = 1;
  	for(var i=0;i<Season.length;i++)
	{
		if (i>0) iBit = iBit*2;
		if((SeasonNum & iBit) == iBit)
		{
			if (iFirst==0) document.write(",");
			document.write(Season[i]);
			iFirst = 0;
		}
	}  
}

function getMovie(MovieNum)
{
	var iBit;
	var iFirst;
	Movie = new Array("����Ƭ","����Ƭ","ս��Ƭ","��ʷ��","Ϸ��","�ƻ�Ƭ","����Ƭ","ϲ��","�ֲ�Ƭ","����Ƭ","����Ƭ","̽��Ƭ","����Ƭ");

	iBit = 1;iFirst = 1;
  	for(var i=0;i<Movie.length;i++)
	{
		if (i>0) iBit = iBit*2;
		if((MovieNum & iBit) == iBit)
		{
			if (iFirst==0) document.write(",");
			document.write(Movie[i]);
			iFirst = 0;
		}
	}  
}

function getMusic(MusicNum)
{
	var iBit;
	var iFirst;
	Music = new Array("ҡ��/�ؽ���","��ʿ/����","Hip Hop/����","�ŵ�����","���/��ҥ","��������","������","��������");

	iBit = 1;iFirst = 1;
  	for(var i=0;i<Music.length;i++)
	{
		if (i>0) iBit = iBit*2;
		if((MusicNum & iBit) == iBit)
		{
			if (iFirst==0) document.write(",");
			document.write(Music[i]);
			iFirst = 0;
		}
	}  
}

function getFood(FoodNum)
{
	var iBit;
	var iFirst;
	Food = new Array("����","������","���","�����","��³��","������","�Ϻ���","�ձ�����","��������","������","�������ζ","����","���","����ʳƷ","Ӫ���̲�");

	iBit = 1;iFirst = 1;
  	for(var i=0;i<Food.length;i++)
	{
		if (i>0) iBit = iBit*2;
		if((FoodNum & iBit) == iBit)
		{
			if (iFirst==0) document.write(",");
			document.write(Food[i]);
			iFirst = 0;
		}
	}  
}



function getColour(ColourNum)
{
	var iBit;
	var iFirst;
	Colour = new Array("��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ");

	iBit = 1;iFirst = 1;
  	for(var i=0;i<Colour.length;i++)
	{
		if (i>0) iBit = iBit*2;
		if((ColourNum & iBit) == iBit)
		{
			if (iFirst==0) document.write(",");
			document.write(Colour[i]);
			iFirst = 0;
		}
	}  
}

function getphotoStatus(status)
{
	photoStatus = new Array("��","��");
	if(status==1)
		document.write(photoStatus[1]);
	else
		document.write(photoStatus[0]);
		
}

function getListname(seek)
{
	listname = new Array("��׷�����","�ҵ�׷����","�������");
	var num;
	if(seek=="love")	document.write(listname[0]);	
	else if(seek=="loveme")	document.write(listname[1]);	
	else if(seek=="favor")	document.write(listname[2]);	
	else 
	document.write("");
}

function getPhotoUrl(userid,suffix,sParam)
{
 a = Math.floor(userid/1000000);
 b = Math.floor((userid%1000000)/1000);
 document.write("<img src='http://qlove.tencent.com/upload/photo/"+a+"/"+b+"/"+userid+suffix+"' "+sParam);
}

function getUserUrl(userid,sParam)
{
 a = Math.floor(userid/1000000);
 b = Math.floor((userid%1000000)/1000);
 document.write("<a href='http://qlove.tencent.com/love/"+a+"/"+b+"/"+userid+".htm' "+sParam+ ">");
}

function DoSubmit()
{
  with (frmPage){
  	iBegNum = (Ppage.value-1) * Pnum.value;
	window.location="qlove_admin_list?begnum="+iBegNum+"&club="+Pclub.value+"&st="+form1.st[form1.st.selectedIndex].value+"&sc="+form1.sc.value;
  }
  return;
}

function turnit(ss)
	{

	 if (ss.style.display=="none") 
	  {ss.style.display="";
	   
	}

	 else
	  {ss.style.display="none"; 
	   
	  }
	
	} 

function turnimg(ii)
{
	var test;
	
	for(i=1;i<28;i++)
	{
		test = "img" + i + ".src='images/qqq13.gif'";
		eval(test); 
	}
	ii.src="images/qqq131.gif";
} 
	
function getHighuser(itype)
{
	if(itype!=0)//�󶨣��Ǹ߼��û�
		document.write("<img src='/images/gao.gif' border='0' title='�ú���Ϊ�߼��û�'>");
}

function getMobileuser(itype)
{
	if(itype==1||itype==3)//���ֻ������ƶ��߼��û�
		document.write("<img src='/images/mobile.gif' border='0' title='�ú���Ϊ�ƶ��߼��û�'>");
}


function getPhoto(itype)
{
	if(itype==1)//�󶨣��Ǹ߼��û�
		document.write("<img src='/images/look2.gif' width='10' height='12' title='����������Ƭ'>");
}

function goStaticpage(formname,allNum,goNum,url)
{
var gopage=new Number(goNum);
var allpage=new Number(allNum);

	if(gopage<=0 || gopage>allpage)
	{
		alert('�Բ����������ҳ�泬������Ч��Χ�����������룡');
		return false;
	}
	formname.action=url;
	return true;
}

function getgiftclass(itype)
{
	listname = new Array("�������","���а�������","�󵨱��","��������","���ҹ��","�����˼�","ʥ������","��������");
	if(itype==0)	document.write(listname[0]);	
	else if(itype==1)	document.write(listname[1]);	
	else if(itype==2)	document.write(listname[2]);	
	else if(itype==3)	document.write(listname[3]);	
	else if(itype==4)	document.write(listname[4]);	
	else if(itype==5)	document.write(listname[5]);	
	else if(itype==6)	document.write(listname[6]);	
	else if(itype==7)	document.write(listname[7]);	
	else 
	document.write("");
}

function getsinglepaytype(itype)
{
	listname = new Array("������","һ��һ����Լ��","һ��һ��ӦԼ��","һ��һ����֪ͨ����Ӧ���ֻ�","һ�Զ෢��Լ��","һ�Զ���ӦԼ��","һ�Զ���ӦԼ�ᷢ��Լ����ѡ���Ƿ�֪ͨ","һ�Զ�ȷ��Լ��","���Ե����");
	document.write(listname[itype]);	
}
function getdateclass(itype)
{
	listname = new Array("��������","һ��һ���Է���Լ����Ŀ","һ��һŮ�Է���Լ����Ŀ","һ��һ������ӦԼ����Ŀ","һ��һŮ����ӦԼ����Ŀ","һ��һ������ѡ��֪ͨ����Ӧ���ֻ���Ŀ","һ�Զ����Է���Լ����Ŀ","һ�Զ�Ů�Է���Լ����Ŀ","һ�Զ�������ӦԼ����Ŀ","һ�Զ�Ů����ӦԼ����Ŀ","һ�Զ�����ȷ��Լ����Ŀ","һ�Զ�Ů��ȷ��Լ����Ŀ");
	document.write(listname[itype]);	
}
function getmobilestatus(itype)
{
	listname = new Array("���ֻ�","���ֻ�");
	if(itype==1)	document.write(listname[0]);	
	else if(itype==0)	document.write(listname[1]);	
	else 
	document.write("");
}
function getuserstatus(itype)
{
	listname = new Array("��ͨ","�ر�","����");
	if(itype==0)	document.write(listname[0]);	
	else if(itype==1)	document.write(listname[1]);	
	else if(itype==2)	document.write(listname[2]);	
	else 
	document.write("");
}
function getpaymethod(itype)
{
	listname = new Array("QQ��֧��","Q ��֧��","�ֻ�֧��");
	if(itype==1)	document.write(listname[0]);	
	else if(itype==2)	document.write(listname[1]);	
	else if(itype==3)	document.write(listname[2]);	
	else 
	document.write("");
}

function getoptype(itype)
{
	listname = new Array("ע��/��ͨ","��ͨ","�ر�","����");
	if(itype==1)	document.write(listname[0]);	
	else if(itype==2)	document.write(listname[1]);	
	else if(itype==3)	document.write(listname[2]);	
	else if(itype==4)	document.write(listname[3]);	
	else 
	document.write("");
}
function getseniorop(type,qqnum)
{
	if(type==0)
	 document.write("<a href='/cgi-bin/qlove_admin_setseniorstatus?qqnum="+qqnum+"&status=2'> ����</a></p> ");
	if(type==2)
	 document.write("<a href='/cgi-bin/qlove_admin_setseniorstatus?qqnum="+qqnum+"&status=0'> ��ͨ</a></p> ");
}
function GetDownStyle(userid)
{
		var v_url="/cgi-bin/qlove_sendmms_para?foruserid="+userid;
 		window.open(v_url, "newwindow", "width=478,height=408,resizable=yes");
}
