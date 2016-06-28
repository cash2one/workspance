/****************************************************************
        模块：	通用web页面检查程序
        输入：  
        说明：  使用前在页面上构造好要检查的页面元素及要求即可
        作者：	sammi
        时间：	2002-7-16 11:04
        版权：  本程序所有权利归深圳腾讯计算机系统有限公司所有，
                未经授权或许可，任何人不得以任何形式进行复制、分发、使用。
                All rights reserved。
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
		document.write("男");
	else if(sex == "1")
		document.write("女");
	else 
		document.write("不限");
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
		document.write("先生");
	else
		document.write("小姐");
	
}

function getCountry(Num)
{
	country = new Array("中华人民共和国","阿尔巴尼亚","阿尔及利亚","阿富汗","阿根廷","阿拉伯联合酋长国","阿鲁巴",
		"阿曼","阿塞拜疆","埃及","埃塞俄比亚","爱尔兰","爱沙尼亚","安道尔","安哥拉","安圭拉岛","安提瓜和巴布达",
		"奥地利","澳大利亚","巴巴多斯","巴布亚新几内亚","巴哈马","巴基斯坦","巴拉圭","巴林","巴拿马","巴西","白俄罗斯","百慕大群岛","保加利亚","北马里亚纳群岛",
		"贝宁","比利时","冰岛","波多黎各","波兰","波斯尼亚和黑塞哥维那","玻利维亚","伯利兹","博茨瓦纳","不丹","不列颠印度洋属土","布基纳法索","布隆迪","布韦岛","朝鲜","赤道几内亚","丹麦","德国","东帝汶","多哥","多米尼克","多米尼克共和国","俄罗斯","厄瓜多尔",
		"厄立特里亚","法国","法国南部和南极州","法罗群岛","法属波利尼西亚","法属圭亚那","梵蒂冈","菲律宾","斐济群岛","芬兰","佛得角群岛","福克兰群岛（马尔维纳斯群岛）","冈比亚","刚果","刚果民主共和国","哥伦比亚","哥斯达黎加","格林纳达","格陵兰","格鲁吉亚","古巴","瓜德罗普岛（法属）","关岛","圭亚那",
		"哈萨克斯坦","海地","韩国","荷兰","荷属安的列斯群岛","赫德和麦克唐纳群岛","洪都拉斯","基里巴斯","吉布提","吉尔吉斯斯坦","几内亚","几内亚比绍","加拿大","加纳","加蓬","柬埔寨","捷克共和国","津巴布韦","喀麦隆","卡塔尔","开曼群岛","科科斯群岛","科摩罗","科特迪瓦",
		"科威特","克罗地亚（赫尔瓦次卡）","肯尼亚","库克群岛","拉脱维亚","莱索托","老挝","黎巴嫩","立陶宛","利比里亚","利比亚","列支敦士登","留尼汪岛","卢森堡","卢旺达","罗马尼亚","马达加斯加岛","马尔代夫","马耳他","马拉维","马来西亚","马里","马其顿共和国","马绍尔群岛","马提尼克岛","马约特岛",
		"毛里求斯","毛里塔尼亚","美国","美属萨摩亚","美属维尔京群岛","美属小奥特兰群岛","蒙古","蒙特塞拉特(英)","孟加拉国","秘鲁","密克罗尼西亚","缅甸","摩尔多瓦","摩洛哥","摩纳哥","莫桑比克","墨西哥","纳米比亚","南非","南极洲","南乔治亚和南桑德威奇群岛","南斯拉夫","瑙鲁","尼泊尔","尼加拉瓜",
		"尼日尔","尼日利亚","纽埃","挪威","诺福克岛","帕劳","皮特克恩群岛","葡萄牙","日本","瑞典","瑞士","萨尔瓦多","萨摩亚","塞拉利昂","塞内加尔","塞浦路斯","塞舌尔群岛","沙特阿拉伯","圣诞岛","圣多美和普林西比","圣赫勒拿岛","圣基茨和尼维斯","圣卢西亚","圣马力诺","圣皮埃尔岛和密克隆岛","圣文森特和格林纳丁斯",
		"斯里兰卡","斯洛伐克","斯洛文尼亚","斯瓦尔巴群岛和扬马延","斯威士兰","苏丹","苏里南","所罗门群岛","索马里","塔吉克斯坦","泰国","坦桑尼亚","汤加","特克斯群岛和凯科斯群岛","特立尼达和多巴哥","突尼斯","图瓦卢","土耳其","土库曼斯坦","托克劳","瓦利斯群岛和富图纳群岛","瓦努阿图",
		"危地马拉","委内瑞拉","文莱","乌干达","乌克兰","乌拉圭","乌兹别克斯坦","西班牙","希腊","新加坡","新喀里多尼亚","新西兰","匈牙利","叙利亚","牙买加","亚美尼亚","也门","伊拉克","伊朗","以色列","意大利","印度","印度尼西亚","英国","英属维尔京群岛","约旦","越南","赞比亚","乍得","直布罗陀","智利","中非共和国");		
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
	city = new Array("北京","天津","上海","重庆","河北省","河南省","黑龙江省","吉林省","辽宁省","山东省","内蒙古","江苏省","安徽省",
		"山西省","陕西省","甘肃省","浙江省","江西省","湖北省","湖南省","贵州省","四川省","云南省","新疆","宁夏","青海省",
		"西藏","广西","广东省","福建省","海南省","台湾省","香港","澳门");
	
	var num = aNum%100;
	document.write(city[num]);
}

function getIntent(intentNum)
{
	intent = new Array("视情况而定","终身伴侣","恋人","笔友","游伴","聊友");
	document.write(intent[intentNum]);	
}

function getSomatotype(SomatotypeNum)
{
	Somatotype = new Array("苗条/纤细","匀称","健壮","略胖","游伴","肥硕");
	document.write(Somatotype[SomatotypeNum]);	
}

function getBlood(BloodNum)
{
	Blood = new Array("其他","A","B","AB","O");
	document.write(Blood[BloodNum]);	
}

function getMarital(MaritalNum)
{
	Marital = new Array("未婚","已婚","离异无子女","离异有子女","分居","丧偶");
	document.write(Marital[MaritalNum]);	
}

function getVocation(VocationNum)
{
	Vocation = new Array("","保险业","采矿/能源","餐饮/宾馆","宠物/动物","电讯业","房地产","服务行业","服装业","公益组织","广告业","航空航天","化学/化工","建康/保健","建筑业","教育/培训","计算机软件","计算机系统","计算机硬件","金属冶炼","警察/消防","军人","会计","美容/形体","媒体/出版","木材/造纸","零售/批发","农业","旅游业","司法/律师","司机","体育运动","学术科研","演艺娱乐","医疗服务","艺术/设计","银行/金融","因特网","音乐/舞蹈","邮政/快递","运输业","政府机关","制造/机械","咨询服务","其它");
	document.write(Vocation[VocationNum]);	
}

function getEducation(EducationNum)
{
	Education = new Array("小学","初中","高中","专科","学士","硕士/双学士","博士");
	document.write(Education[EducationNum]);	
}

function getIncome(IncomeNum)
{
	Income = new Array("保密","1000元以下/月","1000-2000元/月","2001-4000元/月","4001-8000元/月","8001-20000元/月","20000元以上/月");
	document.write(Income[IncomeNum]);	
}

function getPosition(PositionNum)
{
	Position = new Array("","执行官/经理","专家","教授/老师","技术人员/工程师","服务人员","行政干部","销售/市场","艺术家","自由职业者","演员/歌星","学生","失业","离/退休","主妇","普通职员","其他");
	document.write(Position[PositionNum]);	
}

function getLanguage(LanguageNum)
{
	var iBit;
	var iFirst;
	Language = new Array("普通话","上海话","广东话","英语","日语","韩语","法语","意大利语","德语","西班牙语","俄语","阿拉伯语");

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
	Dress = new Array("简单朴素","大胆前卫","正式装","半正式装");
	document.write(Dress[DressNum]);	
}

function getDrink(DrinkNum)
{
	Drink = new Array("滴酒不沾","偶尔喝一点","有时喝一些","经常喝","爱喝果酒","爱喝啤酒","爱喝白酒");
	document.write(Drink[DrinkNum]);	
}

function getSmoke(SmokeNum)
{
	Smoke = new Array("不吸烟","偶尔","有时","经常");
	document.write(Smoke[SmokeNum]);
}

function getCharactor(CharactorValue)
{
	var iFirst;
	Charactor = new Array("性格外向","性格内向","活泼开朗","沉默寡言","幽默","稳重","轻浮","冲动","坚强","脆弱","幼稚","成熟","能说会道","自私","真诚","独立","依赖","任性","自负","自卑","温柔体贴","神经质","拜金","小心翼翼","暴躁","倔强","逆来顺受","不拘小节","婆婆妈妈","交际广泛","豪爽","害羞","狡猾善变","耿直","虚伪","乐观向上","悲观消极","郁郁寡欢","孤僻","难以琢磨","胆小怕事","敢做敢当","助人为乐","老实","守旧","敏感","迟钝","武断","果断","优柔寡断","暴力倾向","刻薄","损人利己","附庸风雅","时喜时悲","患得患失","快言快语","豪放不羁","多愁善感","循规蹈矩");
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
	Interest = new Array("美食","唱歌","跳舞","电影","音乐","戏剧","聊天","拍拖","电脑","因特网","游戏","绘画","书法","雕塑","时尚","异性","阅读","塑身","体育运动","旅游","政治","购物","宗教","赚钱");
	
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
	Sport = new Array("游泳","网球","羽毛球","乒乓球","赛车","F1","篮球","排球","足球","登山","围棋","国际象棋","象棋","五子棋","万智牌","桥牌","武术","搏击","滑板","自行车","旱冰","溜冰","滑雪","高尔夫","田径","旅游","爬山");
	
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
	Season = new Array("春季","春夏之交","夏季","夏秋之交","秋季","秋冬之交","冬季","冬春之交");

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
	Movie = new Array("爱情片","音乐片","战争片","历史剧","戏剧","科幻片","动画片","喜剧","恐怖片","悬疑片","灾难片","探险片","动作片");

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
	Music = new Array("摇滚/重金属","爵士/蓝调","Hip Hop/节拍","古典音乐","乡村/民谣","流行音乐","轻音乐","民族音乐");

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
	Food = new Array("川菜","潮粤菜","湘菜","淮扬菜","京鲁菜","东北菜","上海菜","日本料理","韩国料理","法国菜","意大利风味","海鲜","快餐","健康食品","营养滋补");

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
	Colour = new Array("红色","橙色","黄色","绿色","青色","蓝色","紫色","黑色","白色","灰色");

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
	photoStatus = new Array("无","有");
	if(status==1)
		document.write(photoStatus[1]);
	else
		document.write(photoStatus[0]);
		
}

function getListname(seek)
{
	listname = new Array("我追求的人","我的追求者","个人珍藏");
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
	if(itype!=0)//绑定，是高级用户
		document.write("<img src='/images/gao.gif' border='0' title='该好友为高级用户'>");
}

function getMobileuser(itype)
{
	if(itype==1||itype==3)//有手机，是移动高级用户
		document.write("<img src='/images/mobile.gif' border='0' title='该好友为移动高级用户'>");
}


function getPhoto(itype)
{
	if(itype==1)//绑定，是高级用户
		document.write("<img src='/images/look2.gif' width='10' height='12' title='该朋友有照片'>");
}

function goStaticpage(formname,allNum,goNum,url)
{
var gopage=new Number(goNum);
var allpage=new Number(allNum);

	if(gopage<=0 || gopage>allpage)
	{
		alert('对不起，您输入的页面超过了有效范围，请重新输入！');
		return false;
	}
	formname.action=url;
	return true;
}

function getgiftclass(itype)
{
	listname = new Array("所有类别","动感爱情礼物","大胆表白","二人世界","枕边夜话","富贵人家","圣诞礼物","新年礼物");
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
	listname = new Array("送礼物","一对一发起约会","一对一响应约会","一对一发起通知到响应者手机","一对多发起约会","一对多响应约会","一对多响应约会发起约会者选择是否通知","一对多确认约会","留言到面板");
	document.write(listname[itype]);	
}
function getdateclass(itype)
{
	listname = new Array("所有类型","一对一男性发起约会数目","一对一女性发起约会数目","一对一男性响应约会数目","一对一女性响应约会数目","一对一发起者选择通知到响应者手机数目","一对多男性发起约会数目","一对多女性发起约会数目","一对多男性响应约会数目","一对多女性响应约会数目","一对多男性确认约会数目","一对多女性确认约会数目");
	document.write(listname[itype]);	
}
function getmobilestatus(itype)
{
	listname = new Array("有手机","无手机");
	if(itype==1)	document.write(listname[0]);	
	else if(itype==0)	document.write(listname[1]);	
	else 
	document.write("");
}
function getuserstatus(itype)
{
	listname = new Array("开通","关闭","冻结");
	if(itype==0)	document.write(listname[0]);	
	else if(itype==1)	document.write(listname[1]);	
	else if(itype==2)	document.write(listname[2]);	
	else 
	document.write("");
}
function getpaymethod(itype)
{
	listname = new Array("QQ卡支付","Q 币支付","手机支付");
	if(itype==1)	document.write(listname[0]);	
	else if(itype==2)	document.write(listname[1]);	
	else if(itype==3)	document.write(listname[2]);	
	else 
	document.write("");
}

function getoptype(itype)
{
	listname = new Array("注册/开通","开通","关闭","冻结");
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
	 document.write("<a href='/cgi-bin/qlove_admin_setseniorstatus?qqnum="+qqnum+"&status=2'> 冻结</a></p> ");
	if(type==2)
	 document.write("<a href='/cgi-bin/qlove_admin_setseniorstatus?qqnum="+qqnum+"&status=0'> 开通</a></p> ");
}
function GetDownStyle(userid)
{
		var v_url="/cgi-bin/qlove_sendmms_para?foruserid="+userid;
 		window.open(v_url, "newwindow", "width=478,height=408,resizable=yes");
}
