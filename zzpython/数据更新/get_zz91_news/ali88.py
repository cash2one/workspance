#-*- coding:utf-8 -*-
#瑞达期货
from getnews2 import get_net_news

def test_ali88():
    main_url='http://info.1688.com'
    re_html=r'<dl class="li23 listcontent1">(.*?)<div class="cl">'
    re_list=r'<li>.*?</a>'
    re_time=''
    re_content=r'<div class="d-content">(.*?)</div>'
    re_hand=[]
    typeid=186
    typeid2=155
    url='http://info.1688.com/tags_list/v5003220-l18935.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)

test_ali88()