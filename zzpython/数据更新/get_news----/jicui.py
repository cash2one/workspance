#-*- coding:utf-8 -*-
#集萃塑料网
from getnews2 import get_net_news

def get_type_url(main_url,typeid):
    type_dictionary={129:'guoj',
                     130:'guon',
                     131:'renw',
                     132:'qiy',
                     133:'hangy',
                     134:'shic'}
    type=type_dictionary[typeid]
    url=main_url+'news/'+type+'.asp'
    return url

def test_ccedip():
    main_url='http://www.ccedip.com/'
    re_title=r'<div class="zx_list">(.*?)</div>'
    re_content=r'<div id="content_body">(.*?)</div>'
    re_hand=[]
    re_hand.append('<P><FONT color=#c7c7c7.*?</FONT></P>')
    re_hand.append('<BR><FONT color=#c7c7c7.*?</FONT></P>')
    typeid=[129,130,131,132,133,134]
    for typeid in typeid:
        url=get_type_url(main_url,typeid)
        get_net_news(main_url,url,typeid,re_title,re_content,re_hand)
    
test_ccedip()