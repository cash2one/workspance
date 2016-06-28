#-*- coding:utf-8 -*-
#中国塑料网_e
from getnews2 import get_net_news

def test_esuliao():
    main_url='http://www.esuliao.com'
    re_title=r'<div class="list-content">(.*?)<div class="pagination">'
    re_content=r'<div class="art-content">(.*?)</div>'
    re_hand=[]
    typeid=185
    typeid2=155
    url='http://www.esuliao.com/news/list-715.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=186
    typeid2=155
    url='http://www.esuliao.com/news/list-717.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=187
    typeid2=155
    url='http://www.esuliao.com/news/list-719.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=188
    typeid2=155
    url='http://www.esuliao.com/news/list-722.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_esuliao()