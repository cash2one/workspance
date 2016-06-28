#-*- coding:utf-8 -*-
#西本新干线
from getnews2 import get_net_news

def test_xiben():
    main_url='http://www.96369.net'
    re_title=r'<div id="lb_c_bt">(.*?)<div id="PagerList">'
    re_content=r'<div id="context">(.*?)</div>'
    re_hand=[]
    typeid=185
    typeid2=153
    url='http://www.96369.net/news/list/14/34/'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=186
    typeid2=153
    url='http://www.96369.net/news/news_list.aspx?channelid=2&columnid=9'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_xiben()