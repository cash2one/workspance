#-*- coding:utf-8 -*-
#生意社
from getnews2 import get_net_news
            
def test_100ppi():
    main_url='http://www.100ppi.com/news/'
    re_title=r'<ul style="clear:both">(.*?)</div>'
    re_content=r'<div class="nd-c" style="overflow:hidden;width:588px;">(.*?)</div>'
    re_hand=[]
    typeid=185
    typeid2=156
    url='http://www.100ppi.com/news/list-11--45-1.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
