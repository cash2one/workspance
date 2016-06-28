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
from news_artical.items import fubaotienie
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from function import savedb,getimglist
class dmozSpider(CrawlSpider):
    name = "fubaotienie"
    download_delay = 1
    allowed_domains = ["f139.com"]
    start_urls=["http://news.f139.com/list.do?channelID=36&categoryID=24&isMain=false",]
    
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
        html=response.read().decode('utf-8', 'ignore')
        
        title=''
        artical=''
        pattern_all=re.compile(r'<div class="width635">(.*?)<p style=" color:#666">',re.S)
        res_all=re.findall(pattern_all,html)
        if res_all:
            pattern_title=re.compile(r'<h1 class="h1_1">(.*?)</h1>',re.S)
            res_title=re.findall(pattern_title,res_all[0])
            if res_title:
                title=re.sub("<[^>]*?>","",res_title[0]).strip()
                #print title
                
            pattern_artical=re.compile(r'<div class="text f14" id="zhengwen">(.*?)<!-- 订阅 -->',re.S)
            res_artical=re.findall(pattern_artical,str(res_all[0]))
            if res_artical:
                artical=getimglist(res_artical[0])
        return {'title':title,'pubdate':pubdate,'artical':artical}
    
    
    
    def parse(self, response):
        sel = Selector(response)
        
        current_date=time.strftime("%Y-%m-%d")
        #current_date='2015-09-08'
        pubdatelist=sel.xpath("//*[@id='list']/ul/li/span/text()").extract()
        titllist=sel.xpath("//*[@id='list']/ul/li/a/@href").extract()
        a=0
        listall=[]   
        for pubdate in pubdatelist:
            pubdate=pubdate[:12]
            pubdate=pubdate.strip()
            pubdate=str(pubdate)
            if pubdate>=current_date:
                titleurl=str(titllist[a])
                titleurl='http://news.f139.com'+titleurl
                list=self.into(titleurl,pubdate)
                main_url=""
                typeid=186
                typeid2=154
                url_a=titleurl
                title=list['title']
                content=list['artical']
                savedb(typeid,typeid2,main_url,url_a,title,content)
                listall.append(list)
            a=a+1
        #插入到记事本
        for list in listall:
            print'标题:',list['title']
            print'时间:',list['pubdate']
            print'内容:',list['artical']
            print '\n'
            print'\n'
        return
        
        