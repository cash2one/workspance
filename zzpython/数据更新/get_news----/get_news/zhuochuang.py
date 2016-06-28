#-*- coding:utf-8 -*-
#卓创资讯
from getnews2 import get_net_news
            
def test_sci99():
    main_url='http://paper.sci99.com/news/'
    re_title=r'<ul class="ul_w690">(.*?)</ul>'
    re_content=r'<div style="font-size: 14px; margin: 15px; color: #000;">(.*?)</div>'
    re_hand=[]
    typeid=185
    typeid2=156
    url='http://paper.sci99.com/news/?cid=66'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_sci99()
