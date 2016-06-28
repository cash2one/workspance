#-*- coding:utf-8 -*-
#----废塑料早间评论
from public import *
from jinshu_price import insertto_price
#from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_content,get_inner_a,get_a_url,remove_content_a,filter_tags,gettags
import datetime,time,re
#conn=database_comp()
#cursor = conn.cursor()

timedate=time.strftime('%m月%d日',time.localtime(time.time()))
#timedate=timedate.decode('utf-8')
gmt_created=datetime.date.today()
str_time=time.strftime('%m-%d',time.localtime(time.time()))
gmt_created2=datetime.datetime.now()

def getgedihq(urlone,type,maintitle,type_id,assist_type_id):
    html_main=get_url_content(urlone)
    html_content=get_content('<div class="width635" id="list">(.*?)<div>',html_main)
    if html_content:
        html_list=html_content.split('</li>')
        for htl in html_list:
            if str_time in htl and type in htl:
                a_url=get_a_url(htl)
                title1=get_inner_a(htl)
                titles=title1.split('：')
                endtitle=maintitle+'：'+titles[1]
                htmls=get_url_content(a_url)
                if htmls:
#                    content=get_content('<span style="font-size: 14px;">&nbsp;(.*?)</span>',htmls)
                    content=get_content('<div class="text f14" id="zhengwen">(.*?)</div>',htmls)
                    content=re.sub('<p.*?>','<p>',content)
                    content=re.sub('<span.*?>','',content)
                    content=re.sub('</span>','',content)
                    content=re.sub('<div.*?>','',content)
                    content=re.sub('<input.*?>','',content)
                    content=re.sub('<!--.*?-->','',content)
                    content=re.sub('富宝资讯','',content)
                    content=re.sub('（作者.*?）','',content)
                    content=re.sub('&nbsp;','',content)
                    content=''.join(content.split())
                    tags=gettags(endtitle)
                    insertto_price(endtitle,type_id,assist_type_id,content,tags)
#                    print content
getgedihq('http://plas.f139.com/news/list.do?channelID=79&categoryID=6','PVC',timedate+'PVC早间评论',217,0)
getgedihq('http://plas.f139.com/news/list.do?channelID=79&categoryID=6','PP',timedate+'PP早间评论',217,0)
getgedihq('http://plas.f139.com/news/list.do?channelID=79&categoryID=6','PE',timedate+'PE早间评论',217,0)
getgedihq('http://plas.f139.com/news/list.do?channelID=79&categoryID=6','ABS',timedate+'ABS早间评论',217,0)
getgedihq('http://plas.f139.com/news/list.do?channelID=79&categoryID=6','PS',timedate+'PS早间评论',217,0)
