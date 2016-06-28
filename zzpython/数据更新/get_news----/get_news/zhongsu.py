#-*- coding:utf-8 -*-
#中国塑料网
from getnews2 import get_net_news

def test_plastic():
    main_url='http://news.plastic.com.cn'
    re_title=r'<div class="box_body li_dot">(.*?)<div class="m">'
    re_content=r'<div id="content">(.*?)</div>'
    re_hand=[]
    typeid=185
    typeid2=155
    url='http://news.plastic.com.cn/list-864/'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_plastic()