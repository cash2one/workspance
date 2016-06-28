#-*- coding:utf-8 -*-
#中塑在线
from getnews2 import get_net_news

def test_info_21cp():
    main_url='http://info.21cp.com'
    re_html=r'<div class="borderTno fontSize14 list">(.*?)<div class="showpage">'
    re_list=r'<a class=""(.*?)<td class="">'
    re_time=r'<span class="cGray" style="float: right;">.*?</span>'
    re_content=r'<div class=content>(.*?)</div>'
    re_hand=[]
    re_hand.append(r'<ul class="prevNext">.*?</ul>')
    re_hand.append(r'<h2>.*?</h2>')
    re_hand.append(r'<A.*?pdf</A>')
    typeid=185
    typeid2=155
    url='http://info.21cp.com/industry/news/'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'',re_content,re_hand)
    typeid=188
    typeid2=155
    url='http://info.21cp.com/industry/fagui/'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'',re_content,re_hand)

if __name__=="__main__":
    test_info_21cp()