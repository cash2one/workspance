#-*- coding:utf-8 -*-
#----抓取和讯网汇率
from public import *
#from zz91db_ast import companydb
#from zz91db_130 import otherdb
from zz91db_test import testdb
from zz91tools import date_to_str,get_content,get_inner_a,get_a_url,get_inner_a,gettags,filter_tags
import os,datetime,time,re,urllib2

dbt=testdb()
#dbc=otherdb()
nowpath=os.path.dirname(__file__)
execfile(nowpath+"getfunction.py")
zzz=zzmodel()
def get_url_content(url):
    i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                 "Referer": 'http://www.baidu.com'}
    req = urllib2.Request(url, headers=i_headers)
    html=urllib2.urlopen(req).read()
    try:
        html2=html.decode('gbk').encode('utf-8')
    except:
        html2=html
    return html2
    
def gethtml(typeid,page):
#    htmlbegin=get_url_content('http://cidian.feijiu.net/cscd-p'+str(page)+'-cid96.154-.html')#二手设备参数,typid=1,最大28页
#    htmlbegin=get_url_content('http://cidian.feijiu.net/cscd-p'+str(page)+'-cid96.102-.html')#废塑料参数,typid=2,最大6页
#    htmlbegin=get_url_content('http://cidian.feijiu.net/cscd-p'+str(page)+'-cid96.103-.html')#废纸参数,typid=3,最大4页
#    htmlbegin=get_url_content('http://cidian.feijiu.net/cscd-p'+str(page)+'-cid96.97-.html')#火车头参数,typid=4,最大5页
#    htmlbegin=get_url_content('http://baodian.feijiu.net/hybd-p'+str(page)+'-cid6.176-.html')#二手设备宝典,typid=5,最大373页
#    htmlbegin=get_url_content('http://baodian.feijiu.net/hybd-p'+str(page)+'-cid6.7-.html')#废金属宝典,typid=6,最大34页
#    htmlbegin=get_url_content('http://baodian.feijiu.net/hybd-p'+str(page)+'-cid6.9-.html')#废纸宝典,typid=7,最大9页
    htmlbegin=get_url_content('http://baodian.feijiu.net/hybd-p'+str(page)+'-cid6.8-.html')#废塑料宝典,typid=8,最大21页
    htmlbody=get_content('<ul class="list1">(.*?)</div>',htmlbegin)
    sql='insert into bbs_zhishi(typeid,title,keywords,content,content_query,sortrank,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)'
    alistall=htmlbody.split('</li>')
    for alist in alistall[::-1]:
        title=get_inner_a(alist)
#        print title
        sql2='select id from bbs_zhishi where title=%s and typeid=%s'
        result=dbt.fetchonedb(sql2,[title,typeid])
        if not result:
            time.sleep(9)
            ahref=get_a_url(alist)
            if ahref:
                ahref=ahref.replace("' target='_blank","")
                htmls=get_url_content(ahref)
                if htmls:
                    content=get_content('<div class="cont-text">(.*?)<div',htmls)
                    sortrank=int(time.time())
                    gmt_date=datetime.date.today()
                    gmt_created=datetime.datetime.now()
                    keywords=gettags(title)
                    content_query=filter_tags(content)
                    argument=[typeid,title,keywords,content,content_query,sortrank,gmt_date,gmt_created,gmt_created]
                    dbt.updatetodb(sql,argument)
                    '''
                    '''
#                    print title
#                    print content

def gettypeallpage():
    typeid=8
    allpage=21
    pagelist=range(1,allpage+1)
    for page in pagelist[::-1]:
        gethtml(typeid,page)
#        print page

if __name__=="__main__":
    gettypeallpage()