#-*- coding:utf-8 -*-
#中国塑料机械网
from getnews2 import get_net_news

def test_86pla():
    main_url='http://www.86pla.com'
    re_title=r'<div class="anewslist-left-b">(.*?)<div class="newlist_center_fanye">'
    re_content=r'<div class="newshow_fontshow">(.*?)<div class="newspage">'
    re_hand=[]
    re_hand.append(r'<br /><br /><p style="color:#808080.*?</p></div>')
    re_hand.append(r'<strong>.*?</strong>')
    re_hand.append(r'\(来源：.*?\)')
    typeid=185
    typeid2=155
    url='http://www.86pla.com/news/T14/list.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=187
    typeid2=155
    url='http://www.86pla.com/news/T13/list.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)

test_86pla()