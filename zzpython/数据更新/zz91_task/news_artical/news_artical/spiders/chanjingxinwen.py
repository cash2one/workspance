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
from news_artical.items import chanjingxinwen
import sys
reload(sys)
sys.setdefaultencoding('utf8')

from function import savedb,getimglist

class dmozSpider(CrawlSpider):
    name = "chanjingxinwen"
    download_delay = 1
    allowed_domains = ["steelcn.cn"]
    start_urls=["http://news.steelcn.cn/qita.html",]
      
    def into(self,url):
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
        time=''
        artical=''
        pattern_all=re.compile(r'<div class="art_nav">(.*?)<div class="art_tags">',re.S)
        res_all=re.findall(pattern_all,html)
        if res_all:
            #print res_all[0]
            pattern_title=re.compile(r'<h1>(.*?)</h1>',re.S)
            res_title=re.findall(pattern_title,res_all[0])
            if res_title:
                title=re.sub("<[^>]*?>","",res_title[0]).strip()
            
            pattern_time=re.compile(r'<i>(.*?)</i>',re.S)
            res_time=re.findall(pattern_time,res_all[0])
            if res_time:
                time=re.sub("<[^>]*?>","",res_time[0]).strip()
                #print time
                
            pattern_artical=re.compile(r'<div class="art_main">(.*?)</div>',re.S)
            res_artical=re.findall(pattern_artical,res_all[0])
            if res_artical:
                artical=getimglist(res_artical[0])
        return {'title':title,'time':time,'artical':artical}
    
    def parse(self, response):
        sel = Selector(response)
        
        current_date=time.strftime("%m+%d-")
        current_date=current_date.replace('+','月')
        current_date=current_date.replace('-','日')
        
        #current_date='08月14日'
        pubdatelist=sel.xpath("//*[@class='list']/ul/li/span/text()").extract()
        titllist=sel.xpath("//*[@class='list']/ul/li/a/@href").extract()
        a=0
        listall=[]   
        for pubdate in pubdatelist:
            pubdate=pubdate.strip()
            pubdate=str(pubdate)
            if pubdate>=current_date:
                titleurl=str(titllist[a])
                list=self.into(titleurl)
                main_url=""
                typeid=185
                typeid2=153
                url_a=titleurl
                title=list['title']
                content=list['artical']
                savedb(typeid,typeid2,main_url,url_a,title,content)
                listall.append(list)
            a=a+1
        #插入到记事本
        for list in listall:
            print'标题:',list['title']
            print'\n'
  
        return