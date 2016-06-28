#-*- coding:utf-8 -*-
#慧聪网
from tools import huicong_hand
from getnews2 import get_net_news

def test_hc360():
    main_url='http://info.plas.hc360.com'
    re_title=r'<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:15px;">(.*?)<table width="298" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:10px;">'
    re_content=r'<div id="artical">(.*?)</div>'
    re_hand=huicong_hand()
    typeid=185
    typeid2=155
    url='http://info.plas.hc360.com/list/yaowen_list.shtml'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=187
    typeid2=155
    url='http://info.plas.hc360.com/list/list_jszs.shtml'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=186
    typeid2=155
    url='http://info.plas.hc360.com/list/scfxdg.shtml'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    typeid=188
    typeid2=155
    url='http://info.plas.hc360.com/list/list_zengcefg.shtml'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)

test_hc360()