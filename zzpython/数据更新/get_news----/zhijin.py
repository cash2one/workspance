#-*- coding:utf-8 -*-
#志金钢铁
from getnews2 import get_net_news

def test_zhijin_steel():
    main_url='http://www.zhijinsteel.com'
    re_title=r'<ul class="list lh24 f14">(.*?)<div id="pages" class="text-c">'
    re_content=r'<div class="summary" >(.*?)<div id="pages" class="text-c">'
    re_hand=[]
    typeid=185
    typeid2=153
    url='http://www.zhijinsteel.com/zixun/xghy/index.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=175
    typeid2=0
    url='http://www.zhijinsteel.com/zixun/gnxw/index.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
