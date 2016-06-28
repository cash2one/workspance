#-*- coding:utf-8 -*-
#卓商塑料
from getnews2 import get_net_news

def test_w7000():
    main_url='http://www.w7000.com'
    re_title=r'<div class="fl news_lst_lt">(.*?)<div class="news_ym">'
    re_content=r'<div class="nr">(.*?)</div>'
    re_hand=[]
    re_hand.append(r'<h6.*?/h6>')
    re_hand.append(r'<h5.*?/h5>')
    re_hand.append(r'<h4.*?/h4>')
    re_hand.append(r'<p class="duty".*?/p>')
    re_hand.append(r'<p>&nbsp;</p>')
    re_hand.append(r'<img src=.*?:///.*?/>')
    typeid=185
    typeid2=155
    url='http://www.w7000.com/news/1'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=187
    typeid2=155
    url='http://www.w7000.com/news/4'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)

test_w7000()