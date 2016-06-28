#-*- coding:utf-8 -*-
#中国金属废料网
from getnews2 import get_net_news
            
def test_quanqiujin():
    main_url='http://www.ometal.com'
    re_title=r'<div align="center">(.*?)</div>'
    re_content=r'<div id="fontzoom">(.*?)</div>'
    re_hand=[]
    re_hand.append(r'<span style="font-size:12px;">.*?</span> ')
    re_hand.append(r'<font color="#FF0000" class="font105pt">.*?</font>')
    typeid=188
    typeid2=154
    url='http://www.ometal.com/bin/new/more.asp?t=12&pos=1759511&act=pre&page=1'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_quanqiujin()