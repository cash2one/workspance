#-*- coding:utf-8 -*-
#富宝
from getnews2 import get_net_news

def test_f139():
    main_url='http://www.f139.c'
    re_html=r'<div class="width635" id="list">(.*?)<div align="center">'
    re_list=r'<li>(.*?)</li>'
    re_time=r'<span class="cGray" style="float: right;">.*?</span>'
    re_content=r'<div id="wenzhang">(.*?)</div>'
    re_hand=[]
    
    '''
    typeid=185
    typeid2=153
    url='http://www.f139.cn/news/list.do?channelID=115&orCategoryID=24,25'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_content,re_hand)
    typeid=186
    typeid2=153
    url='http://www.f139.cn/news/list.do?channelID=97&orCategoryID=3,6,7,11'
    re_content=r'<div id="zhengwen">(.*?)</div>'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139b',re_content,re_hand)
#    typeid=187
#    typeid2=153
#    url='http://www.f139.cn/news/list.do?channelID=115&categoryID=79'
#    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    '''
    re_content=r'<div class="text f14" id="zhengwen">(.*?)</div>'
    '''
    '''
    typeid=185
    typeid2=156
    main_urlp='http://paper.f139.c'
    url='http://paper.f139.com/news/list.do?channelID=94&categoryID=444'
    get_net_news(main_urlp,url,typeid,typeid2,re_html,re_list,'f139a',re_content,re_hand)
    re_html='<div class="width635 left" id="list">(.*?)<div align="center">'
    typeid=185
    typeid2=154
    url='http://www.f139.com/news/list.do?categoryID=24&channelID=1'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139b',re_content,re_hand)
    typeid=186
    typeid2=154
    url='http://www.f139.com/news/list.do?channelID=1&categoryID=12'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139b',re_content,re_hand)
    typeid=187
    typeid2=154
    url='http://www.f139.com/news/list.do?categoryID=78&channelID=1'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139b',re_content,re_hand)
    typeid=188
    typeid2=154
    url='http://www.f139.com/news/list.do?channelID=1&categoryID=25'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139b',re_content,re_hand)
    '''
    '''
test_f139()