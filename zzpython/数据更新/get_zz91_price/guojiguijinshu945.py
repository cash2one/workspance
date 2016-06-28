#-*- coding:utf-8 -*-
#----9:45国际贵金属市场行情
from public import *
from zz91conn import database_comp
from zz91tools import getYesterday,gethtmlfunction,date_to_str,get_content,get_inner_a,get_a_url,filter_tags,remove_content_a
import datetime,time,re,urllib,urllib2
from bs4 import BeautifulSoup

conn=database_comp()
cursor = conn.cursor()
timedate=time.strftime('%m月%d日',time.localtime(time.time()))
timedate2=getYesterday().strftime('%m月%d')
gmt_created=datetime.date.today()
str_time=time.strftime('%m-%d',time.localtime(time.time()))
gmt_created2=datetime.datetime.now()    

#def get_url_content(url):
#    i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
#                 "Referer": 'http://www.baidu.com'}
#    req = urllib2.Request(url, headers=i_headers)
#    html=urllib2.urlopen(req).read()
#    return html
def get_url_content(url):
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


def delsometag(html):
    html=re.sub('<span.*?>','',html)
    html=re.sub('<font.*?>','',html)
    html=re.sub('<div.*?>','',html)
    html=re.sub('</span>','',html)
    html=re.sub('</font>','',html)
    html=re.sub('</div>','',html)
    html=re.sub('<strong>','',html)
    html=re.sub('</strong>','',html)
    html=re.sub('<p></p>','',html)
    html=''.join(html.split())
    return html
    
def getjinfei(type_id,assist_type_id,tags,geturlone):
    html_area=get_url_content(geturlone)
    #print html_area
    html_alist=html_area.split('<div class="moduleBody">')
    for als in html_alist:
        
        arearname=''
#        print '---------'
        newtimes=''
        newtime=als.replace(' ','')
#        print newtime
        if '金属期市' in newtime:
#            print newtime
            title=get_inner_a(als)
#            print title
            if not title:
                continue
            a_url=get_a_url(als)
            
            #print a_url
            htmls=get_url_content(a_url.decode('utf-8'))
#            print title
#            print a_url
            if '路透伦敦' in htmls:
#                print 1
#                contents=get_content('<div id="resizeableText">(.*?)</div>',htmls)
                contents=get_content('路透伦敦.*?。.*?。',htmls)
#                print contents
                return contents

def getjinfei2(type_id,assist_type_id,tags,geturlone):
    html_area=get_url_content(geturlone)
#    print html_area
    html_alist=html_area.split('<div class="moduleBody">')
    for als in html_alist:
        
        arearname=''
#        print '---------'
        newtimes=''
        newtime=als.replace(' ','')
#        print newtime
        if '全球金市' in newtime:
#            print newtime
            title=get_inner_a(als)
            if not title:
                continue
            a_url=get_a_url(als)

            htmls=get_url_content(a_url)
#            print title
#            print a_url
            if '路透纽约' in htmls:
#                print 1
#                contents=get_content('<div id="resizeableText">(.*?)</div>',htmls)
                contents=get_content('<span id="midArticle_2"></span>.*?路透纽约.*?待续',htmls)
#                print contents
                if contents:
                    a_url2=a_url+'?pageNumber=2&virtualBrandChannel=0'
#                    print a_url2
                    htmls2=get_url_content(a_url2)
                    contents2=get_content('<div id="resizeableText">(.*?)\(完\)',htmls2)
#                    print contents2
                else:
                    contents2=''
                    contents=get_content('<div id="resizeableText">(.*?)\(完\)',htmls)
                contents1=contents+contents2
                contents1=delsometag(contents1)
                contents1=contents1.replace('COMEX黄金期货','</p><p>COMEX黄金期货')
                contents1=contents1.replace('白银期货','</p><p>白银期货')
                contents1=contents1.replace('铂金期货','</p><p>铂金期货')
                contents1=contents1.replace('COMEX钯金期货','</p><p>COMEX钯金期货')
                contents1=contents1.replace('现货金','</p><p>现货金')
                contents1=contents1.replace('现货银','</p><p>现货银')
                contents1=contents1.replace('现货铂金','</p><p>现货铂金')
                contents1=contents1.replace('现货钯金','</p><p>现货钯金')
                contents1=contents1.replace('<p></p>','')+'</p>'
                contents1=contents1.replace('待续<p>','<p>')
#                print contents1
                return contents1

def getguojiguijinshu():
    geturlone='http://search.cn.reuters.com/query/?blob=comex'
    title=timedate+'国际贵金属市场行情'
    sql2='select id from price where title=%s and gmt_created>=%s'
    cursor.execute(sql2,[title,gmt_created])
    result=cursor.fetchone()
    if not result:
        content1=getjinfei(216,0,'期铜,铜,沪铜,融资铜,铜价',geturlone)
        content2=getjinfei2(216,0,'期铜,铜,沪铜,融资铜,铜价',geturlone)
        argtime=timedate2.replace('0','')
#        if argtime not in content2:
#            return ''
        if content1 and content2:
            content1=''.join(content1.split())
            content='<p>'+content1+'</p>'+content2
#            content=content.replace('路透','ZZ91')
#            print title
#            print content
            type_id=216
            assist_type_id=0
            content_query=filter_tags(content)
            is_checked=0
            is_issue=0
            real_click_number=0
            ip=0
            is_remark=1
            tags='废金属,贵金属,国际贵金属,废金属行情,金属市场行情'
            argument=[title,type_id,assist_type_id,content,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,is_remark,content_query]
            sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
            cursor.execute(sql,argument)
            conn.commit()
#            print title
#            print content
            return 9

if __name__=="__main__":
    getguojiguijinshu()
#    getjinfei(216,0,'期铜,铜,沪铜,融资铜,铜价','http://search.cn.reuters.com/query/?blob=comex')
#    getjinfei2(216,0,'期铜,铜,沪铜,融资铜,铜价','http://search.cn.reuters.com/query/?blob=comex')