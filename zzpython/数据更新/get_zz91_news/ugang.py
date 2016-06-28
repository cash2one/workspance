#-*- coding:utf-8 -*-
#U钢材网
from getnews2 import get_net_news

def test_usteel():
    main_url='http://news.usteel.com'
    re_html=r' <div class="list_Contant">(.*?)</div>'
    re_list=r'<li>(.*?)</li>'
    re_time=''
    re_content=r'<div class="show_cnt show_cnt1">(.*?)<div class="commend">'
    re_hand=[]
    typeid=185
    typeid2=153
    url='http://news.usteel.com/hangye/list-35-1.html'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand)

if __name__=="__main__":
    test_usteel()