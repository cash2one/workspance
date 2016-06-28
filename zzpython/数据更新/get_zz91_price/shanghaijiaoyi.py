#-*- coding:utf-8 -*-
from public import *
from zz91conn import database_comp
from zz91tools import getToday,date_to_str,get_url_content,get_content,get_inner_a,get_a_url,filter_tags,remove_content_a
import datetime,time,re,urllib,urllib2
from bs4 import BeautifulSoup

conn=database_comp()
cursor = conn.cursor()
timedate=time.strftime('%m月%d日',time.localtime(time.time()))
gmt_created=getToday()
str_time=time.strftime('%m-%d',time.localtime(time.time()))
gmt_created2=datetime.datetime.now()    

url = "http://www.shfe.com.cn/statements/dataview.html"
search = urllib.urlencode([('dateinput','2013-07-09'),('zs','交易综述')])
req = urllib2.Request(url)
fd = urllib2.urlopen(req,search)
print fd.read()
