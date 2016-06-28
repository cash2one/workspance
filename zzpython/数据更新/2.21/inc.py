def updatelastid(tab,id):
	#sql="update import_table set lastid="+id+" where tablename='"+tab+"'"
	#cursor1.execute(sql)
	f = open("/usr/apps/python/text/"+tab+".txt",'w')
	f.write(id)
	f = open("/usr/apps/python/text/"+tab+".txt",'r')
	f.close()
def getlastid(tab):
	if not (os.path.exists("/usr/apps/python/text/"+tab+".txt")):
		f = open("/usr/apps/python/text/"+tab+".txt",'w')
		f.write('0')
	f = open("/usr/apps/python/text/"+tab+".txt",'r')
	return f.read()
	f.close()
def changezhongwen(strvalue):
	if(strvalue == None):
		tempstr=""
	else:
		tempstr=strvalue.decode('GB18030','ignore').encode('utf-8')
	return tempstr
def changezw(strvalue):
	if(strvalue == None):
		tempstr=""
	else:
		tempstr=str(strvalue).encode('gbk','ignore')
		tempstr=tempstr.replace(",",";").replace("'","^")
		tempstr=tempstr.replace('"',"^")
		#tempstr=tempstr.replace('[',"[[]")
		#tempstr=tempstr.replace('_',"[_]")
		tempstr=tempstr.replace('%',"%%")
		#tempstr=tempstr.replace('(',"[(]")
		#tempstr=tempstr.replace(')',"[)]")
	return tempstr
#昨天
def getYesterday():
	today=datetime.date.today()   
	oneday=datetime.timedelta(days=1)   
	yesterday=today-oneday    
	return yesterday  

#今天     
def getToday():   
	return datetime.date.today()     
 
#获取给定参数的前几天的日期，返回一个list   
def getDaysByNum(num):   
	today=datetime.date.today()   
	oneday=datetime.timedelta(days=1)       
	li=[]        
	for i in range(0,num):   
		#今天减一天，一天一天减   
		today=today-oneday   
		#把日期转换成字符串   
		#result=datetostr(today)   
		li.append(datetostr(today))   
	return li   
 
#将字符串转换成datetime类型  
def strtodatetime(datestr,format):       
	return datetime.datetime.strptime(datestr,format)   
 
#时间转换成字符串,格式为2008-08-02   
def datetostr(date):     
	return   str(date)[0:10]   
 
#两个日期相隔多少天，例：2008-10-03和2008-10-01是相隔两天  
def datediff(beginDate,endDate):   
	format="%Y-%m-%d";
	bd=strtodatetime(str(beginDate)[0:10],format)   
	ed=strtodatetime(str(endDate)[0:10],format)       
	oneday=datetime.timedelta(days=1)   
	count=0 
	while bd!=ed:   
		ed=ed-oneday   
		count+=1 
	return count   
 
#获取两个时间段的所有时间,返回list  
def getDays(beginDate,endDate):   
	format="%Y-%m-%d";   
	bd=strtodatetime(beginDate,format)   
	ed=strtodatetime(endDate,format)   
	oneday=datetime.timedelta(days=1)    
	num=datediff(beginDate,endDate)+1    
	li=[]   
	for i in range(0,num):    
		li.append(datetostr(ed))   
		ed=ed-oneday   
	return li   
 
#获取当前年份 是一个字符串  
def getYear():   
	return str(datetime.date.today())[0:4]    
 
#获取当前月份 是一个字符串  
def getMonth():   
	return str(datetime.date.today())[5:7]   
 
#获取当前天 是一个字符串  
def getDay():
	return str(datetime.date.today())[8:10]      
def getNow():   
	return datetime.datetime.now()
def filter_tags(htmlstr):
    #先过滤CDATA
    re_cdata=re.compile('//<!\[CDATA\[[^>]*//\]\]>',re.I) #匹配CDATA
    re_script=re.compile('<\s*script[^>]*>[^<]*<\s*/\s*script\s*>',re.I)#Script
    re_style=re.compile('<\s*style[^>]*>[^<]*<\s*/\s*style\s*>',re.I)#style
    re_br=re.compile('<br\s*?/?>')#处理换行
    re_h=re.compile('</?\w+[^>]*>')#HTML标签
    re_comment=re.compile('<!--[^>]*-->')#HTML注释
    s=re_cdata.sub('',htmlstr)#去掉CDATA
    s=re_script.sub('',s) #去掉SCRIPT
    s=re_style.sub('',s)#去掉style
    s=re_br.sub('\n',s)#将br转换为换行
    s=re_h.sub('',s) #去掉HTML 标签
    s=re_comment.sub('',s)#去掉HTML注释
    #去掉多余的空行
    blank_line=re.compile('\n+')
    s=blank_line.sub('\n',s)
    s=replaceCharEntity(s)#替换实体
    return s
##替换常用HTML字符实体.
#使用正常的字符替换HTML中特殊的字符实体.
#你可以添加新的实体字符到CHAR_ENTITIES中,处理更多HTML字符实体.
#@param htmlstr HTML字符串.
def replaceCharEntity(htmlstr):
    CHAR_ENTITIES={'nbsp':' ','160':' ',
                'lt':'<','60':'<',
                'gt':'>','62':'>',
                'amp':'&','38':'&',
                'quot':'"','34':'"',}
   
    re_charEntity=re.compile(r'&#?(?P<name>\w+);')
    sz=re_charEntity.search(htmlstr)
    while sz:
        entity=sz.group()#entity全称，如&gt;
        key=sz.group('name')#去除&;后entity,如&gt;为gt
        try:
            htmlstr=re_charEntity.sub(CHAR_ENTITIES[key],htmlstr,1)
            sz=re_charEntity.search(htmlstr)
        except KeyError:
            #以空串代替
            htmlstr=re_charEntity.sub('',htmlstr,1)
            sz=re_charEntity.search(htmlstr)
    return htmlstr