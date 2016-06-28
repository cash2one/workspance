#-*- coding:utf-8 -*-
#中国钢材网
from getnews2 import get_net_news
def test_china_steel():
    main_url='http://news.steelcn.com'
    re_html=r'<div class="list">(.*?)<div id="Fenye">'
    re_list=r'<li>(.*?)</li>'
    re_time=''
    re_content=r'<div class="art_main">(.*?)<div class="art_tags">'
    re_hand=[]
    typeid=185
    typeid2=153
    url='http://news.steelcn.com/domestic.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)
    typeid=175
    typeid2=0
    url='http://news.steelcn.com/guonei.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)
    typeid=176
    typeid2=0
    url='http://news.steelcn.com/guoji.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)

test_china_steel()