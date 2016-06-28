#-*- coding:utf-8 -*-
#瑞达期货
from getnews2 import get_net_news

def test_ruida():
    main_url='http://www.rdqh.com'
    re_html=r'<div class="t3"></div>(.*?)<div class="c2">'
    re_list=r'<div class="tt">.*?</div>.*?</div>'
    re_time=''
    re_content=r'<div style="text-align: right; padding-right: 1em;">(.*?)<div>'
    re_hand=[]
    typeid=186
    typeid2=155
    url='http://www.rdqh.com/search-%E8%BF%9E%E5%A1%91.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)
    url='http://www.rdqh.com/search-PVC.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)
    url='http://www.rdqh.com/search-PTA.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)
    typeid=186
    typeid2=156
    url='http://www.rdqh.com/search-%E6%A9%A1%E8%83%B6.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)

test_ruida()