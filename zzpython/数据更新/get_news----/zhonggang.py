#-*- coding:utf-8 -*-
#中国钢材网
from getnews2 import get_net_news

def test_china_steel():
    main_url='http://news.steelcn.com'
    re_title=r'<div class="list">(.*?)<div id="Fenye">'
    re_content=r'<div class="art_main">(.*?)<div class="art_tags">'
    re_hand=[]
    re_hand.append(r'<strong><a href="http://hq.steelcn.com.*?</a></strong>')
    typeid=185
    typeid2=153
    url='http://news.steelcn.com/domestic.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=175
    typeid2=0
    url='http://news.steelcn.com/guonei.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=176
    typeid2=0
    url='http://news.steelcn.com/guoji.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)

#test_china_steel()