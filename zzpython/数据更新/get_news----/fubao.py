#-*- coding:utf-8 -*-
#富宝
from getnews2 import get_net_news

def test_f139():
    main_url='http://www.f139.c'
    re_title=r'<div class="width635" id="list">(.*?)<div align="center">'
    re_content=r'<div id="zhengwen">(.*?)</div>'
    re_hand=[]
    re_hand.append(r'<p class="center">.*?</p>')
    re_hand.append(r'<h1.*?</h1>')
    typeid=185
    typeid2=153
    url='http://www.f139.cn/news/list.do?channelID=115&orCategoryID=24,25'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=186
    typeid2=153
    url='http://www.f139.cn/news/list.do?channelID=97&orCategoryID=3,6,7,11'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=187
    typeid2=153
    url='http://www.f139.cn/news/list.do?channelID=115&categoryID=79'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
    typeid=185
    typeid2=156
    main_urlp='http://paper.f139.c'
    url='http://paper.f139.com/news/list.do?channelID=94&categoryID=444'
    get_net_news(main_urlp,url,typeid,typeid2,re_title,re_content,re_hand)
    
    re_title='<div class="width635 left" id="list">(.*?)<div align="center">'

    typeid=185
    typeid2=154
    url='http://www.f139.com/news/list.do?pageSize=30&count=9250&page=1&categoryID=24&channelID=1'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=186
    typeid2=154
    url='http://www.f139.com/news/list.do?pageSize=30&count=8889&page=1&categoryID=12&channelID=1'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=187
    typeid2=154
    url='http://www.f139.com/news/list.do?categoryID=78&channelID=1'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=188
    typeid2=154
    url='http://www.f139.com/news/list.do?channelID=1&categoryID=25'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_f139()