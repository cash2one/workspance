#-*- coding:utf-8 -*-
#慧聪网
from getnews2 import get_net_news

def test_hc360():
    main_url='http://info.plas.hc360.com'
    re_html=r'<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:15px;">(.*?)<table width="298" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:10px;">'
    re_list=r'<tr>(.*?)</tr>'
    re_time='<font class=pt_date>(.*?)</font>'
    re_content=r'<div id="artical">(.*?)</div>'
    re_hand=[]
#    typeid=185
#    typeid2=155
#    url='http://info.plas.hc360.com/list/yaowen_list.shtml'
#    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'huiconga',re_content,re_hand)
    typeid=187
    typeid2=155
    url='http://info.plas.hc360.com/list/list_jszs.shtml'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)
#    typeid=186
#    typeid2=155
#    url='http://info.plas.hc360.com/list/list_scfxdg_scfx.shtml'
#    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)
    typeid=188
    typeid2=155
    url='http://info.plas.hc360.com/list/list_zengcefg.shtml'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)

test_hc360()