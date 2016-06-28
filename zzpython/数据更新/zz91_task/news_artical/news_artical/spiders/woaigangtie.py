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
from news_artical.items import woaigangtie
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from function import savedb,getimglist
class dmozSpider(CrawlSpider):
    name = "woaigangtie"
    download_delay = 1
    allowed_domains = ["52steel.com"]
    start_urls=["http://www.52steel.com/newslist.aspx?lm=1401&cs=0&pz=0&gc=0&pg=1",
                "http://www.52steel.com/newslist.aspx?lm=1401&cs=0&pz=0&gc=0&pg=2",
                ]
    
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
        pattern_all=re.compile(r'<div class="article">(.*?)<div id="related"></div>',re.S)
        res_all=re.findall(pattern_all,html)
        if res_all:
        
            pattern_title=re.compile(r'<h1>(.*?)</h1>',re.S)
            res_title=re.findall(pattern_title,res_all[0])
            if res_title:
                title=re.sub("<[^>]*?>","",res_title[0]).strip()
                #print title
                
            pattern_artical=re.compile(r'<div id="text">(.*?)<div id="tags">',re.S)
            res_artical=re.findall(pattern_artical,res_all[0])
            if res_artical:
                article_filter=res_artical[0].replace(" [需要查看更多数据，请免费试用爱钢数据]","")
                article_filter=article_filter.replace("</div>","")
                artical=getimglist(article_filter)
        return {'title':title,'pubdate':pubdate,'artical':artical}
    
    
    
    def parse(self, response):
        sel = Selector(response)
        
        current_date=time.strftime("%Y-%m-%d")
        #current_date='2015-08-11'
        pubdatelist=sel.xpath("//*[@class='midcol']/div/div/ul/li/span/text()").extract()
        titleurllist=sel.xpath("//*[@class='midcol']/div/div/ul/li/a/@href").extract()
        title_zh=sel.xpath("//*[@class='midcol']/div/div/ul/li/a/text()").extract()
        for n in xrange(0,len(titleurllist)):
            pubdate=pubdatelist[n]
            # pubdate=pubdate[:11]
            pubdate=pubdate.strip()
            pubdate=str(pubdate)        
            if pubdate>=current_date:
            #判断是否是今天的news
                titleurl=str(titleurllist[n])
                titleurl='http://www.52steel.com/'+titleurl
                if '铜' in title_zh[n].decode('utf-8') or '铝' in title_zh[n].decode('utf-8') or '铅' in title_zh[n].decode('utf-8') or '锌' in title_zh[n].decode('utf-8') or '镍' in title_zh[n].decode('utf-8') or '钢' in title_zh[n].decode('utf-8') or '煤' in title_zh[n].decode('utf-8') or '有色金属' in title_zh[n].decode('utf-8'):
                    #判断是否有上述关键字
                    list=self.into(titleurl,pubdate)
                    main_url=""
                    typeid=185
                    typeid2=154
                    url_a=titleurl
                    title=list['title']
                    content=list['artical']
                    savedb(typeid,typeid2,main_url,url_a,title,content)
        return
        
        
        
        
        '''
        a=0
        listall=[]   
        for pubdate in pubdatelist:
            #pubdate=pubdate[:11]
            pubdate=pubdate.strip()
            pubdate=str(pubdate)
            if pubdate>=current_date:
                titleurl=str(titllist[a])
                titleurl='http://www.52steel.com/'+titleurl
                print a
                print 'ook' 
                print pubdate
                print titleurl
                list=self.into(titleurl,pubdate)
                main_url=""
                typeid=185
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
        '''