#-*- coding:utf-8 -*-
#中国纸业网
from getnews2 import get_net_news

def test_chinapaper():
    main_url='http://www.chinapaper.net'
    re_title=r'<div class="four">(.*?)<ul class="list_b">'
    re_content=r'<div id="content">(.*?)</div>'
    re_hand=[]
    typeid=185
    typeid2=156
    url='http://www.chinapaper.net/news/list-5.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=187
    typeid2=156
    url='http://www.chinapaper.net/news/list.php?catid=10'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=186
    typeid2=156
    url='http://www.chinapaper.net/news/list.php?catid=13'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=188
    typeid2=156
    url='http://www.chinapaper.net/news/list-9.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_chinapaper()