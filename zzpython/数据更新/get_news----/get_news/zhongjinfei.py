#-*- coding:utf-8 -*-
#中国金属废料网
from getnews2 import get_net_news
            
def test_china_scrap():
    main_url='http://www.chinascrap.com/'
    re_title=r'<div class="Row">(.*?)<div id="Foot">'
    re_content=r'<div id="spcontent" class="Row">(.*?)</div>'
    re_hand=[]
    typeid=186
    typeid2=154
    url='http://www.chinascrap.com/analyze.htm'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=188
    typeid2=154
    url='http://www.chinascrap.com/More/11993841E3B989E8.htm'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_china_scrap()