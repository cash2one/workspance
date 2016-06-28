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
from news_artical.items import huicong
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from function import savedb,getimglist
class dmozSpider(CrawlSpider):
    name = "huicong"
    download_delay = 1
    allowed_domains = ["hc360.com"]
    start_urls=["http://info.plas.hc360.com/list/yaowen_list.shtml",]
    
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
        pattern_all=re.compile(r'<div id="endInfoContent">(.*?)hc_logo_end.jpg">',re.S)
        res_all=re.findall(pattern_all,html)
        if res_all:
        
            pattern_title=re.compile(r'<h1>(.*?)</h1>',re.S)
            res_title=re.findall(pattern_title,res_all[0])
            if res_title:
                title=re.sub("<[^>]*?>","",res_title[0]).strip()
                #print title
                title=title.replace("中州期货：", "")
                title=title.replace("快讯：", "")
            pattern_artical=re.compile(r'<div id="artical">(.*?)<img src=',re.S)
            res_artical=re.findall(pattern_artical,res_all[0])
            if res_artical:
                artical=getimglist(res_artical[0])
        return {'title':title,'pubdate':pubdate,'artical':artical}
    
    
    
    def parse(self, response):
        sel = Selector(response)
        
        current_date=time.strftime("(%m/%d)")
        #current_date='2015-08-11'
        pubdatelist=sel.xpath("//*[@id='wezi141']/table[1]/tr/td[2]/font/text()").extract()
        titleurllist=sel.xpath("//*[@id='wezi141']/table[1]/tr/td[1]/a/@href").extract()
        title_zh=sel.xpath("//*[@id='wezi141']/table[1]/tr/td[1]/a/text()").extract()
        for n in xrange(0,len(titleurllist)):
            pubdate=pubdatelist[n]
            pubdate=pubdate[:11]
            pubdate=pubdate.strip()
            pubdate=str(pubdate)  
            #判断是否是今天的news      
            if pubdate>=current_date:
                titleurl=str(titleurllist[n])
                titleurl='http://info.plas.hc360.com'+titleurl
                #判断是否有上述关键字
                if '早评' not in title_zh[n].decode('utf-8') and '早报' not in title_zh[n].decode('utf-8') and '价格行情分析' not in title_zh[n].decode('utf-8') and '塑料市场行情分析汇总' not in title_zh[n].decode('utf-8') and '慧聪视角' not in title_zh[n].decode('utf-8') and '慧聪学堂' not in title_zh[n].decode('utf-8') and '市场装置动态汇总' not in title_zh[n].decode('utf-8') and '塑料下午茶' not in title_zh[n].decode('utf-8'):
                    list=self.into(titleurl,pubdate)
                    main_url=""
                    typeid=185
                    typeid2=155
                    url_a=titleurl
                    title=list['title']
                    content=list['artical']
                    if content:
                        content=content.replace('慧聪塑料网讯：','ZZ91资讯：')
                    savedb(typeid,typeid2,main_url,url_a,title,content)
        return
        
        
        '''
        a=0
        listall=[]   
        for pubdate in pubdatelist:
            pubdate=pubdate[:11]
            pubdate=pubdate.strip()
            pubdate=str(pubdate)
            if pubdate>=current_date:
                titleurl=str(titllist[a])
                titleurl='http://info.plas.hc360.com'+titleurl
                print a
                print 'ook' 
                print pubdate
                print titleurl
                list=self.into(titleurl,pubdate)
                main_url=""
                typeid=185
                typeid2=155
                url_a=titleurl
                title=list['title']
                content=list['artical']
                if content:
                    content=content.replace('慧聪塑料网讯：','ZZ91资讯：')
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
        '''