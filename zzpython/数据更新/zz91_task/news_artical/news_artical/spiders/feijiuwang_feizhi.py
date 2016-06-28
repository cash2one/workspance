# -*- coding: utf-8 -*-
import urllib,datetime,time
from time import sleep
import re,urllib2,urllib
from scrapy.selector import Selector
try:  
    from scrapy.spider import Spider  
except:  
    from scrapy.spider import BaseSpider as Spider
from scrapy.contrib.spiders import CrawlSpider,Rule
from scrapy.contrib.linkextractors.sgml import SgmlLinkExtractor
from news_artical.items import feijiuwang_feizhi
import sys
reload(sys)
sys.setdefaultencoding('utf8')

from function import savedb,getimglist

class dmozSpider(CrawlSpider):
    name = "feijiuwang_feizhi"
    download_delay = 1
    allowed_domains = ["feijiu.net"]
    start_urls=["http://news.feijiu.net/news-p-cid1.4-.html",]
    
    def into(self,url,pubdate):
        sleep(1)
        i_headers = {
                     'Connection': 'Keep-Alive',
                     'Accept':'image/webp,*/*;q=0.8',
                     'Accept-Language':'zh-CN,zh;q=0.8,en;q=0.6',
                     "User-Agent":"Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36"
                     }
        request = urllib2.Request(url,headers=i_headers)
        response = urllib2.urlopen(request)
        html=response.read().decode('gb2312', 'ignore')
        
        title=''
        artical=''
        pattern_all=re.compile(r'<div class="maincont">(.*?)</article>',re.S)
        res_all=re.findall(pattern_all,html)
        if res_all:
        
            pattern_title=re.compile(r'<h1>(.*?)</h1>',re.S)
            res_title=re.findall(pattern_title,res_all[0])
            if res_title:
                title=re.sub("<[^>]*?>","",res_title[0]).strip()
                
            pattern_artical=re.compile(r'<article class="article">(.*?)<div class="Source">',re.S)
            res_artical=re.findall(pattern_artical,res_all[0])
            if res_artical:
                artical=getimglist(res_artical[0])
        return {'title':title,'pubdate':pubdate,'artical':artical}
    
    def parse(self, response):
        sel = Selector(response)
        
        current_date=time.strftime("%m+%d-")
        current_date=current_date.replace('+','月')
        current_date=current_date.replace('-','日')
        
        #current_date='08月13日'
        pubdatelist=sel.xpath("//*[@class='sharecont mt20  clearfix']/span/text()").extract()
        titllist=sel.xpath("//*[@class='listwrap']/article/h4/a/@href").extract()
        a=0
        listall=[]   
        for pubdate in pubdatelist:
            pubdate=pubdate.strip()
            pubdate=str(pubdate)
            if pubdate>=current_date:
                titleurl=str(titllist[a])
                list=self.into(titleurl,pubdate)
                
                main_url=""
                typeid=185
                typeid2=156
                url_a=titleurl
                title=list['title']
                content=list['artical']
                savedb(typeid,typeid2,main_url,url_a,title,content)
                
                listall.append(list)
            a=a+1
        return
