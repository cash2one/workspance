#-*- coding:utf-8 -*-
from code_utf8 import *
import urllib,urllib2,datetime,time,re,random,jieba

#--判断手机端还是pc
def mobileuseragent(agent):
    data=["Nokia",#诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0 (Nokia5800 XpressMusic)UC AppleWebkit(like Gecko) Safari/530  
    "SAMSUNG",#三星手机 SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1  
    "MIDP-2",#j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2 NokiaE75-1 /110.48.125 Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like Gecko) Safari/413  
    "CLDC1.1",#M600/MIDP2.0/CLDC1.1/Screen-240X320  
    "SymbianOS",#塞班系统的，  
    "MAUI",#MTK山寨机默认ua  
    "UNTRUSTED/1.0",#疑似山寨机的ua，基本可以确定还是手机  
    "Windows CE",#Windows CE，Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; IEMobile 7.11)  
    "iPhone",#iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9 (KHTML like Gecko) Mobile/8B117  
    "iPad",#iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10  
    "Android",#Android是否也转wap？Mozilla/5.0 (Linux; U; Android 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7) AppleWebKit/530.17 (KHTML like Gecko) Version/4.0 Mobile Safari/530.17  
    "BlackBerry",#BlackBerry8310/2.7.0.106-4.5.0.182  
    "UCWEB",#ucweb是否只给wap页面？ Nokia5800 XpressMusic/UCWEB7.5.0.66/50/999  
    "ucweb",#小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE 6.0;) Opera ucweb-squid  
    "BREW",#很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId: 40105; Lang: zhcn) ucweb-squid  
    "J2ME",#很奇怪的ua，只有J2ME四个字母  
    "YULONG",#宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0  
    "YuLong",#还是宇龙  
    "COOLPAD",#宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0  
    "TIANYU",#天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320  
    "TY-",#天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0  
    "K-Touch",#还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0  
    "Haier",#海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007 Browser/WAP2.0  
    "DOPOD",#多普达手机  
    "Lenovo",# 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22 Profile/MIDP2.0 Configuration/CLDC1.1  
    "LENOVO",# 联想手机，比如：LENOVO-P780/176A  
    "HUAQIN",#华勤手机  
    "AIGO-",#爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0  
    "CTC/1.0",#含义不明  
    "CTC/2.0",#含义不明  
    "CMCC",#移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0  
    "DAXIAN",#大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0  
    "MOT-",#摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006 Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1 Software/R533_G_11.10.54R  
    "SonyEricsson",# 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0 (compatible; MSIE 6.0; Symbian OS; 405) Opera 8.65 [zh-CN]  
    "GIONEE",#金立手机  
    "HTC",#HTC手机  
    "ZTE",#中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile  
    "HUAWEI",#华为手机，  
    "webOS",#palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN) AppleWebKit/532.2 (KHTML like Gecko) Version/1.0 Safari/532.2 Pre/1.0  
    "GoBrowser",#3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290 Safari  
    "IEMobile",#Windows CE手机自带浏览器，  
    "WAP2.0"]#支持wap 2.0的
    #agent=request.META['HTTP_USER_AGENT']
    for list in data:
        if list in agent:
            return 1

proxylist = (
            '211.167.112.14:80',
            '210.32.34.115:8080',
            '115.47.8.39:80',
            '211.151.181.41:80',
            '219.239.26.23:80',
            '219.157.200.18:3128',
            '219.159.105.180:8080',
            '1.63.18.22:8080',
            '221.179.173.170:8080',
            '125.39.66.153:80',
            '125.39.66.151:80',
            '61.152.108.187:80',
            '222.217.99.153:9000',
            '125.39.66.146:80',
            '120.132.132.119:8080',
            '119.7.221.137:82',
            '117.41.182.188:8080',
            '202.116.160.89:80',
            '221.7.145.42:8080',
            '211.142.236.131:80',
            '119.7.221.136:80',
            '211.151.181.41:80',
            '125.39.66.131:80',
            '120.132.132.119:8080',
            '112.5.254.30:80',
            '106.3.98.82:80',
            '119.4.250.105:80',
            '123.235.12.118:8080',
            '124.240.187.79:80',
            '182.48.107.219:9000',
            '122.72.2.180:8080',
            '119.254.90.18:8080',
            '124.240.187.80:83',
            '110.153.9.250:80',
            '202.202.1.189:80',
            '58.67.147.205:8080',
            '111.161.30.228:80',
            '122.72.76.130:80',
            '122.72.2.180:80',
            '202.112.113.7:80',
            '218.108.85.59:81',
            '211.144.72.154:80',
            '119.254.88.53:8080',
            '121.14.145.132:82',
            '114.80.149.183:80',
            '111.161.30.239:80',
            '182.48.107.219:9000',
            '122.72.0.28:80',
            '125.39.68.131:80',
            '118.244.190.6:80',
            '120.132.132.119:88',
            '211.167.112.15:82',
            '221.2.80.126:8888',
            '219.137.229.214:3128',
            '125.39.66.131:80',
            '61.181.22.157:80',
            '115.25.216.6:80',
            '119.7.221.137:82',
            '221.195.42.195:8080',
            '119.254.88.53:8080',
            '219.150.254.158:8080',
            '113.9.163.101:8080',
            '222.89.154.14:9000',
            '114.141.162.53:8080',
            '218.5.74.199:3128',
            '61.152.108.187:80',
            '218.76.159.133:80',
            '59.34.57.88:8080',
            '118.244.190.34:80',
            '59.172.208.189:8080',
            '116.236.216.116:8080',
            '111.161.30.233:80',
            '220.248.237.234:8080',
            '121.14.145.132:82',
            '202.114.205.125:8080'
            )
def geturllibfunction(url):
    randomsz=proxylist[random.randint(0,len(proxylist)-1)]
    proxies = {'': randomsz}
    opener = urllib.FancyURLopener(proxies)
    f = opener.open(url)
    html=f.read()
    return html

def gethtmlfunction(url):
    randomsz=proxylist[random.randint(0,len(proxylist)-1)]
    #print randomsz
    enable_proxy = True
    proxy_handler = urllib2.ProxyHandler({"http" :randomsz})
    null_proxy_handler = urllib2.ProxyHandler({})
    if enable_proxy:
        opener = urllib2.build_opener(proxy_handler)
    else:
        opener = urllib2.build_opener(null_proxy_handler)
    urllib2.install_opener(opener)
    #设置代理end    
    header = {'Accept-Charset':'GBK,utf-8;q=0.7,*;q=0.3','User-Agent' : 'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.151 Safari/534.16'}
    #Debug Log star
    httpHandler = urllib2.HTTPHandler(debuglevel=1)
    httpsHandler = urllib2.HTTPSHandler(debuglevel=1)
    opener = urllib2.build_opener(httpHandler, httpsHandler)
    urllib2.install_opener(opener)
    #Debug Log end
    request = urllib2.Request(url,headers = header)
    res = urllib2.urlopen(request) 
    html = res.read()
    res.close() 
    return html

#----select下拉条件筛选排序(列表里面如果包含字典需要tp="1",key如果不等于"id",也要传过来)
def getsortlist(id,listdir,tp='',key='id'):
    jnum=-1
    if tp=='1':
        for ldr in listdir:
            jnum+=1
            if id==ldr[key]:
                nnum=jnum
    else:
        for ldr in listdir:
            jnum+=1
            if id==ldr:
                nnum=jnum
    listdir.insert(0,listdir[nnum])
    del listdir[nnum+1]
    return listdir
#----根据列表的索引,获得审核状态0,1,2的<optiopn>列表
def getoptionlist(num,ckmlist):
    checklist2=[]
    for index,ck2 in enumerate(ckmlist):
        if num==index:
            checklist2.append({'num':index,'ckm':ck2})
    for index,ckl in enumerate(ckmlist):
        if not num==index:
            checklist2.append({'num':index,'ckm':ckl})
    return checklist2

def getnowurl(request):
    host=request.path_info
    qstring=request.META.get('QUERY_STRING','/')
    qstring=qstring.replace("&","^and^")
    return host+"?"+qstring
def get_inner_a(html):
    re_py=r'<a.*?>([^"]+)</a>'
    urls_pat=re.compile(re_py)
    arg_url=re.findall(urls_pat,html)
    re_py2=r'<a.*?>([^"]+)</a>'
    urls_pat2=re.compile(re_py2)
    arg_url2=re.findall(urls_pat2,html)
    if arg_url:
        return arg_url[0]
    if arg_url2:
        return arg_url2[0]
def get_a_url(html):
    re_py=r'<a.*?href="([^"]+)"'
    urls_pat=re.compile(re_py)
    arg_url=re.findall(urls_pat,html)
    re_py2=r'<A.*?href="([^"]+)"'
    urls_pat2=re.compile(re_py2)
    arg_url2=re.findall(urls_pat2,html)
    re_py2=r"<a.*?href='([^\"]+)'"
    urls_pat2=re.compile(re_py2)
    arg_url2=re.findall(urls_pat2,html)
    if arg_url:
        return arg_url[0]
    if arg_url2:
        return arg_url2[0]
def hand_content(re_py,content):
    urls_pat=re.compile(re_py,re.DOTALL)
    e_content=re.findall(urls_pat,content)
    for e_content in e_content:
        content=content.replace(e_content,'')
    return content
def remove_content_a(html):#移除a链接
    html=re.sub('<A.*?>','',html)
    html=re.sub('</A>','',html)
    html=re.sub('<a.*?>','',html)
    html=re.sub('</a>','',html)
    return html
def remove_content_div(html):#移除a链接
    html=re.sub('<DIV.*?>','',html)
    html=re.sub('</DIV>','',html)
    html=re.sub('<div.*?>','',html)
    html=re.sub('</div>','',html)
    return html
def remove_script(html):#移除script
    if '<script' in html:
        re_py=r'<script.*?</script>'
        urls_pat=re.compile(re_py,re.DOTALL)
        img_url=re.findall(urls_pat,html)
        for img_url in img_url:
            html=html.replace(img_url,'')
    if '<style' in html:
        re_py=r'<style.*?</style>'
        urls_pat=re.compile(re_py,re.DOTALL)
        img_url=re.findall(urls_pat,html)
        for img_url in img_url:
            html=html.replace(img_url,'')
    return html
def remove_iframe(html):#移除iframe
    if '<iframe' in html:
        re_py=r'<iframe.*?</iframe>'
        urls_pat=re.compile(re_py,re.DOTALL)
        img_url=re.findall(urls_pat,html)
        for img_url in img_url:
            html=html.replace(img_url,'')
    return html
def get_lexicon(strt):
    result = jieba.cut(strt)
    participle=" / ".join(result)
    lexicon=participle.split(" / ")
    return lexicon
def gettags(title):
    lexicon=get_lexicon(title)
    tags=''
    for lec in lexicon:
        if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
            tags=tags+lec+','
    tags=tags[:-1]
    return tags

def get_url_content2(url):
    user_agent = 'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'    
    values = {'name' : 'WHY',    
              'location' : 'SDU',    
              'language' : 'Python' }    
    headers = { 'User-Agent' : user_agent }    
    data = urllib.urlencode(values)    
    req = urllib2.Request(url, data, headers)    
    response = urllib2.urlopen(req)    
    the_page = response.read()   
    return the_page
#----获得html
def get_url_content(url,arg=''):#突破网站防爬
    if 'f139.com' in url:
        i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                     "Referer": 'http://www.baidu.com'}
        req = urllib2.Request(url, headers=i_headers)
        html=urllib2.urlopen(req).read()
    else:
        html=urllib.urlopen(url).read()
    if arg==1:
        return html
    if 'goepe.com' in url:
        html=html.decode('gbk','ignore')
    if 'hc360.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'sina.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if '1688.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'feijiu.net' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'l-zzz.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'chinapaper.net' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'cs.com.cn' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'metalscrap.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if '21cp.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'zgfp.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'ometal.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'jrj.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'hexun.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    if 'ly10000.com' in url:
        html=html.decode('gbk','ignore').encode('utf-8')
    return html

#格式化字符串
def formattime(value,flag=''):
    if value:
        if (flag==1):
            return value.strftime('%Y-%m-%d')
        else:
            return value.strftime('%Y-%m-%d %H:%M:%S')
    else:
        return ''
#----20140101连续格式时间分割
def getsplitime(timedate):
    return timedate[:4]+'-'+timedate[4:6]+'-'+timedate[6:8]
#----获得当前是哪一年
def getnowyear():
    return time.strftime('%Y',time.localtime(time.time()))
#----获得当前是哪个月
def getnowmonth():
    return time.strftime('%m',time.localtime(time.time()))
#---获得当月的总天数
def getnowmohthdaysnumb(year,month):
    monthRange = calendar.monthrange(int(year),int(month))
    return monthRange[1]
#----获得当月的时间列表
def getnowmonthdayslist():
    nowyear=getnowyear()
    nowmonth=getnowmonth()
    number=getnowmohthdaysnumb(nowyear,nowmonth)
    listall=[]
    for numb in range(1,number+1):
        numb=str(numb)
        if len(numb)==1:
            numb='0'+numb
        alldate=nowyear+nowmonth+numb
        list={'alldate':alldate,'numb':numb}
        listall.append(list)
    return {'list':listall,'nowyear':nowyear,'nowmonth':nowmonth}

#----所有时间转换函数
def str_to_date(stringDate):
    if not ':' in stringDate:
        stringDate=stringDate+' 00:00:00'
    return datetime.datetime.strptime(stringDate,"%Y-%m-%d %H:%M:%S").date()
def str_to_datetime(stringDate):
    if not ':' in stringDate:
        stringDate=stringDate+' 00:00:00'
    return datetime.datetime.fromtimestamp(time.mktime(time.strptime(stringDate,"%Y-%m-%d %H:%M:%S")))
def str_to_int(stringDate):
    if not ':' in stringDate:
        stringDate=stringDate+' 00:00:00'
    return int(time.mktime(time.strptime(stringDate,"%Y-%m-%d %H:%M:%S")))
def int_to_str(intDate):
    return time.strftime('%Y-%m-%d', time.localtime(intDate))
def int_to_str2(intDate):
    return time.strftime('%m-%d', time.localtime(intDate))
def int_to_strall(intDate):
    return time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(intDate))
def int_to_date(intDate):
    return str_to_date(int_to_strall(intDate))
def int_to_datetime(intDate):
    return str_to_datetime(int_to_strall(intDate))
def date_to_str(dttime):
    return dttime.strftime('%Y-%m-%d')
def date_to_strall(dttime):
    return dttime.strftime('%Y-%m-%d %H:%M:%S')
def date_to_int(dttime):
    return str_to_int(date_to_strall(dttime))
def get_str_time():
    return time.strftime('%Y-%m-%d',time.localtime(time.time()))
def get_str_timeall():
    return time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
#----获得某一天的明天
def getnextdate(stringDate,arg=''):
    datestrdate=str_to_date(stringDate)
    days=datetime.timedelta(days=1)
    rdates=datestrdate+days
    if arg==1:
        rdates=date_to_str(rdates)
    return rdates
#----获得过去几天函数
def getpastday(numb,arg=''):
    listnumb=range(1,numb+1)
    today=datetime.date.today()
    listall=[]
    for lnumb in listnumb:
        days=datetime.timedelta(days=lnumb)
        ddays=today-days
        if arg==1:
            ddays=ddays.strftime('%Y-%m-%d')
        elif arg==2:
            ddays=ddays.strftime('%m-%d')
        listall.append(ddays)
    return listall
#----第前几天
def getpastoneday(numb):
    today=datetime.date.today()
    days=datetime.timedelta(days=numb)
    nowday=today-days
    return nowday
#----获得过去相差一天时间列表字典
def getdatelist(numb):
    listnumb=range(1,numb+1)
    today=datetime.date.today()
    listall=[]
    for lnumb in listnumb[::-1]:
        days=datetime.timedelta(days=lnumb)
        gmt_begin=today-days
        gmt_end=gmt_begin+datetime.timedelta(days=1)
        list={'gmt_begin':gmt_begin,'gmt_end':gmt_end}
        listall.append(list)
    return listall
#----获得两段时间差的列表(返回时间字符串列表)
def gettimedifference(datebegin,dateend):
    if str(datebegin).isdigit()==False and type(datebegin)==str:
        datebegin=str_to_int(datebegin)
    if type(datebegin)==datetime.datetime:
        datebegin=date_to_int(datebegin)
    if str(dateend).isdigit()==False and type(dateend)==str:
        dateend=str_to_int(dateend)
    if type(dateend)==datetime.datetime:
        dateend=date_to_int(dateend)
    timelist2=[]
    timedifference=(dateend-datebegin)/(3600*24)
    datebegin2=datebegin
    timelist=range(1,timedifference+1)
    for tl in timelist:
        timelist2.append(int_to_str(datebegin2))
        datebegin2=datebegin2+3600*24
    timelist2.append(int_to_str(dateend))
    return timelist2

#时间戳转为格式化字符串
def timestamp_datetime(value,time):
    format = '%Y-%m-%d'
    value = time.localtime(value)
    dt = time.strftime(format, value)
    return dt
#----获得昨天datetime类型
def getYesterday():
    today=datetime.date.today()   
    oneday=datetime.timedelta(days=1)   
    yesterday=today-oneday    
    return yesterday
def getTomorrow():
    today=datetime.date.today()   
    oneday=datetime.timedelta(days=1)   
    yesterday=today+oneday    
    return yesterday
#----获得今天datetime类型
def getToday():
    return datetime.date.today()
#----获得不带0的日期
def getsimptime(arg='%m月,%d日'):
    arglist=arg.split(',')
    arg1=arglist[0]
    arg2=arglist[1]
    timed1=time.strftime(arg1,time.localtime(time.time()))
    timed2=time.strftime(arg2,time.localtime(time.time()))
    if timed1[:1]=='0':
        timed1=timed1[1:]
    if timed2[:1]=='0':
        timed2=timed2[1:]
    simptime=timed1+timed2
    return simptime
#----秒数转化为00:00:00类型
def getnub_tostr(s):
    s1=s%60
    m=s/60
    h=m/60
    if h>0:
        m=m-(h*60)
    numb=str(h)+':'+str(m)+':'+str(s1)
    return numb

#----更新到数据库
def updatetodb(sql,conn,cursor,argument):
    cursor.execute(sql,argument)
    conn.commit()
#----查询所有数据
def fetchalldb(sql,cursor,argument=''):
    if argument:
        cursor.execute(sql,argument)
    else:
        cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        return resultlist
    else:
        return []
#----查询一条数据
def fetchonedb(sql,cursor,argument=''):
    if argument:
        cursor.execute(sql,argument)
    else:
        cursor.execute(sql)
    result=cursor.fetchone()
    if result:
        return result
#----查询一条总数
def fetchnumberdb(sql,cursor,argument=''):
    if argument:
        cursor.execute(sql,argument)
    else:
        cursor.execute(sql)
    result=cursor.fetchone()
    if result:
        return result[0]
    else:
        return 0
#----获得html
def getwebhtml(baidu_url):
    html=urllib.urlopen(baidu_url).read()
    return html
#----传入正则找出内容
def get_content(re_py,html):
    urls_pat=re.compile(re_py,re.DOTALL)
    content=re.findall(urls_pat,html)
    if content:
        return content[0]
    
#加密
def getjiami(strword):
    if strword:
        return strword.encode('utf8').encode("hex")
    else:
        return ''

def getjiemi(strword):
    if strword:
        return strword.decode("hex").decode('utf8')
    else:
        return ''

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

def subString(string,length):   
    if length >= len(string):   
        return string   
    result = ''  
    i = 0  
    p = 0  
    while True:   
        ch = ord(string[i])   
        #1111110x   
        if ch >= 252:   
            p = p + 6  
        #111110xx   
        elif ch >= 248:   
            p = p + 5  
        #11110xxx   
        elif ch >= 240:   
            p = p + 4  
        #1110xxxx   
        elif ch >= 224:   
            p = p + 3  
        #110xxxxx   
        elif ch >= 192:
            p = p + 2  
        else:   
            p = p + 1       
        if p >= length:   
            break;
        else:   
            i = p   
    return string[0:i]







url_list=[
      {'typeid':4,'type':'tradedetail','url':'http://trade.zz91.com/productdetails'},
      {'typeid':3,'type':'tradelist','url':'http://trade.zz91.com/trade/s'},
      {'typeid':2,'type':'trade','url':'http://trade.zz91.com/'},
      {'typeid':7,'type':'pricedetail','url':'http://price.zz91.com/priceDetails'},
      {'typeid':6,'type':'pricelist','url':'http://price.zz91.com/priceSearch.htm'},
      {'typeid':5,'type':'price','url':'http://price.zz91.com/'},
      {'typeid':8,'type':'tags','url':'http://tags.zz91.com'},
      {'typeid':9,'type':'daohang','url':'http://daohang.zz91.com'},
      {'typeid':10,'type':'huzhu','url':'http://huzhu.zz91.com'},
      {'typeid':13,'type':'newsdetail','url':'newsdetail'},
      {'typeid':12,'type':'newslist','url':'list/tags'},
      {'typeid':11,'type':'news','url':'http://news.zz91.com/'},
      {'typeid':15,'type':'myrc','url':'http://myrc.zz91.com/'},
      {'typeid':18,'type':'company','url':'http://company.zz91.com/'},
      {'typeid':21,'type':'map','url':'http://map.zz91.com/'},
      {'typeid':22,'type':'zhanhui','url':'http://zhanhui.zz91.com/'},
      {'typeid':20,'type':'tech','url':'http://tech.zz91.com/'},
      {'typeid':99,'type':'other','url':'baidu.com'},
      {'typeid':1,'type':'www','url':'http://www.zz91.com'},
      {'typeid':1,'type':'www','url':'http://jinshu.zz91.com/'},
      {'typeid':1,'type':'www','url':'http://china.zz91.com/'},
      ]