#-*- coding:utf-8 -*-
#钢联资讯
from getnews2 import get_net_news

def test_glinfo():
    main_url='http://info.glinfo.com'
    re_title=r'<div class="box" id="articleList" style="height:1103px;">(.*?)<div class="page">'
    re_content=r'<div class="nad" id="a4">(.*?)<div class="text" id="btext">'
    re_hand=[]
    typeid=188
    typeid2=153
    url='http://info.glinfo.com/article/p-3917-------------1.html'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)

test_glinfo()