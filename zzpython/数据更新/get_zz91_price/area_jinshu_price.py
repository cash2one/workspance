#-*- coding:utf-8 -*-
#----各个地区金属报价,来源网站http://www.zgfp.com/search/searchprice.aspx
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_content,get_inner_a,get_a_url,filter_tags
import datetime,time,re
from bs4 import BeautifulSoup

conn=database_comp()
cursor = conn.cursor()
timedate=time.strftime('%m月%d日',time.localtime(time.time()))
timedate2=time.strftime('%m月%d',time.localtime(time.time()))
gmt_created=datetime.date.today()
str_time=time.strftime('%d',time.localtime(time.time()))
gmt_created2=datetime.datetime.now()    

def getjinshutable(content):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
        tablestr+="<table border=1 cellpadding=0 cellspacing=0>"
        i=1
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8")
                if j==5:
                    if '-' in textname:
                        textname=textname.replace('-','↓')
                    else:
                        textname2=re.findall('[\d]+',textname)
                        if textname2:
                            textname2=textname2[0]
                            if int(textname2)>0:
                                textname='↑'+textname
#                            if int(textname2)<0:
#                                textname='↓'+textname
                        
                tablestr+="<td >"
                tablestr+=textname
                tablestr+="</td>"
                j+=1
            tablestr+="</tr>"
            i+=1
    tablestr=tablestr+'</table>'
    return tablestr

def getgedihq(cid,a,type_id,assist_type_id,stitle,tags,arg):
    stitle1=stitle
    if '临沂' in stitle:
        stitle1='山东'+stitle
    if '清远' in stitle:
        stitle1='广东'+stitle
    if '贵阳' in stitle:
        stitle1=stitle.replace('贵阳','贵州')
    if '四川成都地区' in stitle:
        stitle1=stitle.replace('四川成都地区','四川')
    if '云南地区' in stitle:
        stitle1=stitle.replace('云南地区','云南')
    if '辽宁地区' in stitle:
        stitle1=stitle.replace('辽宁地区','辽宁')
    if '福建地区' in stitle:
        stitle1=stitle.replace('福建地区','福建')
    if '安徽地区' in stitle:
        stitle1=stitle.replace('安徽地区','安徽')
    if '江西地区' in stitle:
        stitle1=stitle.replace('江西地区','江西')
    if '陕西西安地区' in stitle:
        stitle1=stitle.replace('陕西西安地区','陕西西安')
    title_end=timedate+stitle1+'价格'
    
    sql2='select id from price where title=%s and gmt_created>=%s'
    cursor.execute(sql2,[title_end,gmt_created])
    result=cursor.fetchone()
    if not result:
#        print title_end
        if arg==1:
            geturlone=u'http://www.zgfp.com/search/searchprice.aspx?page=1&ChannelId=8&cid='+cid+'&k=title&w=&e=2&d=&a='+a
        else:
            geturlone=u'http://www.zgfp.com/search/searchprice.aspx?page=1&ChannelId=8&cid='+cid+'&k=title&w=&e=2&d=&a='
        try:
            html_area=get_url_content(geturlone)
        except:
            return
        html_alist=get_content(u'<div id="plList" align="center">(.*?)<div class="tabad">',html_area)
        if html_alist:
            html_hq=''
            html_bj=''
            area_id=''
            alist=html_alist.split('</td><td align="center" width="150">')
            for als in alist:
                arearname=''
        #        print '---------'
                newtimes=''
                newtime=als.replace(' ','')
                newtimes=newtime[10:20]
                title=get_inner_a(als)
                shorttime=str_time.replace('0','')
                if not title:
                    continue
                if shorttime in title and stitle in title:
#                    print shorttime
                    a_url=get_a_url(als)
                    if a_url:
                        a_url=a_url.replace('..','http://www.zgfp.com/')
                    try:
                        htmls=get_url_content(a_url)
                    except:
                        continue
#                    print a_url
                    if not htmls:
                        continue
                    contents=get_content('<div id="content">(.*?)<div class="yzxx_3">',htmls)
                    if not contents:
                        continue
                    contents=getjinshutable(contents)
                    contents=re.sub('<tr><td ></td><td ></td><td ></td><td ></td><td ></td></tr>','',contents)
                    contents=re.sub('<tr><td >单位:元/吨</td></tr>','<tr><td colspan="3">单位:元/吨</td></tr>',contents)
                    content_query=filter_tags(contents)
                    is_checked=0
                    is_issue=0
                    real_click_number=0
                    ip=0
                    is_remark=1
                    
                    argument=[title_end,type_id,assist_type_id,contents,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,is_remark,content_query]
                    sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                    cursor.execute(sql,argument)
                    conn.commit()
                    return title_end+' 更新'

{'废不锈钢':39,'后台':44}
{'废铜':61,'后台':40}
{'废铝':40,'后台':41}
#----废不锈钢
{'山东临沂':5315,'后台':55}
{'天津':6900,'后台':239}
{'广东清远':5619,'后台':59}
{'宁波':5503,'后台':272}
{'台州':5511,'后台':256}
#----废铜
{'重庆':7500,'后台':251}
{'云南':7800,'后台':252}
{'西安':6202,'后台':253}
{'辽宁':5800,'后台':254}
{'福建':7400,'后台':255}
{'台州':5511,'后台':256}
{'贵阳':7702,'后台':257}
{'四川':6002,'后台':258}
{'成都':6002,'后台':259}
{'江西':7200,'后台':260}
{'安徽':7300,'后台':264}
{'北京':5100,'后台':269}
#----废铝
{'云南':7800,'后台':252}
{'四川':6002,'后台':258}
{'台州':5511,'后台':256}
{'宁波':5503,'后台':272}
{'福建':7400,'后台':255}


def area410():
    #----废不锈钢
    listall=[]
    for arg in range(0,2):
        '''
        '''
        txt18=getgedihq('39','5315',44,55,'临沂废不锈钢','废金属,废不锈钢价格,行情,废不锈钢,山东地区',arg)
        txt19=getgedihq('39','6900',44,239,'天津废不锈钢','废金属,废不锈钢价格,行情,废不锈钢,天津地区',arg)
        txt20=getgedihq('39','5619',44,59,'清远废不锈钢','废金属,废不锈钢价格,行情,废不锈钢,清远地区',arg)
        txt21=getgedihq('39','5503',44,272,'宁波废不锈钢','废金属,废不锈钢价格,行情,废不锈钢,宁波地区',arg)
        txt22=getgedihq('39','5511',44,256,'台州废不锈钢','废金属,废不锈钢价格,行情,废不锈钢,台州地区',arg)
        #----废铜
        txt1=getgedihq('61','7500',40,251,'重庆废铜','废金属,废铜价格,行情,废铜,重庆',arg)
        txt2=getgedihq('61','7800',40,252,'云南地区废铜','废金属,废铜价格,行情,废铜,云南',arg)
        txt3=getgedihq('61','6202',40,253,'陕西西安地区废铜','废金属,废铜价格,行情,废铜,陕西,西安',arg)
        txt4=getgedihq('61','5800',40,254,'辽宁地区废铜','废金属,废铜价格,行情,废铜,辽宁',arg)
        txt5=getgedihq('61','7400',40,255,'福建地区废铜','废金属,废铜价格,行情,废铜,福建',arg)
        txt6=getgedihq('61','5511',40,256,'台州废铜','废金属,废铜价格,行情,废铜,浙江,台州',arg)
        txt7=getgedihq('61','7702',40,257,'贵阳废铜','废金属,废铜价格,行情,废铜,贵州,贵阳',arg)
        txt8=getgedihq('61','6002',40,258,'四川成都地区废铜','废金属,废铜价格,行情,废铜,四川',arg)
        txt9=getgedihq('61','6002',40,259,'成都废铜','废金属,废铜价格,行情,废铜,成都',arg)
        txt10=getgedihq('61','7200',40,260,'江西地区废铜','废金属,废铜价格,行情,废铜,江西',arg)
        txt11=getgedihq('61','7300',40,264,'安徽地区废铜','废金属,废铜价格,行情,废铜,安徽',arg)
        txt12=getgedihq('61','5100',40,269,'北京废铜','废金属,废铜价格,行情,废铜,北京',arg)
        #----废铝
        txt13=getgedihq('40','7800',41,252,'云南地区废铝','废金属,废铝价格,行情,废铝,云南',arg)
        txt14=getgedihq('40','6002',41,258,'四川成都地区废铝','废金属,废铝价格,行情,废铝,四川',arg)
        txt17=getgedihq('40','7400',41,255,'福建废铝','废金属,废铝价格,行情,废铝,福建',arg)
        txt15=getgedihq('40','5511',41,256,'台州废铝','废金属,废铝价格,行情,废铝,浙江,台州',arg)
        txt16=getgedihq('40','5503',41,272,'宁波废铝','废金属,废铝价格,行情,废铝,浙江,宁波',arg)
        if txt1:
            listall.append(36)
        if txt2:
            listall.append(37)
        if txt3:
            listall.append(38)
        if txt4:
            listall.append(39)
        if txt5:
            listall.append(40)
        if txt6:
            listall.append(41)
        if txt7:
            listall.append(42)
        if txt8:
            listall.append(43)
        if txt9:
            listall.append(44)
        if txt10:
            listall.append(45)
        if txt11:
            listall.append(46)
        if txt12:
            listall.append(47)
        if txt13:
            listall.append(48)
        if txt14:
            listall.append(49)
        if txt15:
            listall.append(50)
        if txt16:
            listall.append(51)
        if txt17:
            listall.append(52)
        if txt18:
            listall.append(31)
        if txt19:
            listall.append(32)
        if txt20:
            listall.append(33)
        if txt21:
            listall.append(34)
        if txt22:
            listall.append(35)
        '''
        '''
    return listall
        
if __name__=="__main__":
    area410()