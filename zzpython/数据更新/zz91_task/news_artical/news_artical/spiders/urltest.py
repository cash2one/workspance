# -*- coding:utf-8 -*-
import urllib,datetime,time  
import requests
from bs4 import BeautifulSoup
import re
import re,urllib2,urllib
"""
b=(time.strftime("%Y-%m-%d"))
print b
print type(b)
"""
#本地图片保存地址
img_path='/home/xbin/workspace/scrapy/news_artical/news_artical/img/pvc/'
i_headers = {
                     'Connection': 'Keep-Alive',
                     'Accept':'image/webp,*/*;q=0.8',
                     'Accept-Language':'zh-CN,zh;q=0.8,en;q=0.6',
                     "User-Agent":"Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36"
                     }
request = urllib2.Request("http://www.pvc123.com/news/jyxxmu.html",headers=i_headers)
response = urllib2.urlopen(request)
html=response.read().decode('gb2312', 'ignore')

title=''
artical=''
pattern_all=re.compile(r'<div class="area_main_list">(.*?)<p class="summary">',re.S)
res_all=re.findall(pattern_all,html)
if res_all:

    pattern_title=re.compile(r'<h1>(.*?)</h1>',re.S)
    res_title=re.findall(pattern_title,res_all[0])
    if res_title:
        title=re.sub("<[^>]*?>","",res_title[0]).strip()
        #print title
        
    pattern_artical=re.compile(r'PVC(.*?)<div class="ep-source cDGray">',re.S)
    res_artical=re.findall(pattern_artical,res_all[0])
    if res_artical:
        artical=re.sub("<(?!img|br|p|/p).*?>","",res_artical[0]).strip()
        #匹配图片并下载
        #print artical
        try:
            soup=BeautifulSoup(artical)
            listall_img=soup.find_all("img") 
            print listall_img
            for list in listall_img:
                soup_imgurl=BeautifulSoup(str(list),"lxml")
                url=soup_imgurl.img["src"]
                print 'url:',url
                pic_name=url[-20:]
                print pic_name
                pic_name=pic_name.replace('/','')
                pic_name=pic_name.replace('-','')
                
                #图片下载目录
                
                local = img_path+pic_name
                urllib.urlretrieve(url,local)
                #将文章中的img标签换成本地链接
                artical=re.sub(url,local,artical)
                
            print artical
        except:
            listall_img=''
        

    
