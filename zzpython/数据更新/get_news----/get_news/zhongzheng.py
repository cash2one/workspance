#-*- coding:utf-8 -*-
#中证网
from getnews2 import get_net_news
            
def test_zhongzheng():
    main_url='http://www.cs.com.cn'
    re_title=r'<div class="column-top linkblack">(.*?)<div class="z_list_page">'
    re_content=r'<div class="Dtext z_content" id=ozoom1 style="ZOOM: 100%">(.*?)</div>'
    re_hand=[]
    typeid=186
    typeid2=154
    url='http://www.cs.com.cn/qhsc/zzqh/'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=175
    typeid2=0
    url='http://www.cs.com.cn/xwzx/jr/'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_zhongzheng()