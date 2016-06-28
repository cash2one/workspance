#-*- coding:utf-8 -*-
#世界再生网
from getnews2 import get_net_news
            
def test_worldscrap():
    main_url='http://china.worldscrap.com/modules/cn/paper/'
    re_title=r'<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0">(.*?)</table>'
    re_content=r'<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">(.*?)</tr>'
    re_hand=[]
    re_hand.append('世界再生网讯')
    typeid=185
    typeid2=156
    url='http://china.worldscrap.com/modules/cn/paper/cndick_index.php?sort=6-3'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=186
    typeid2=156
    url='http://china.worldscrap.com/modules/cn/paper/cndick_index.php?sort=6-4'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)

test_worldscrap()