#-*- coding:utf-8 -*- 
#金属废料网--某些报错原因:错误页面
from getnews2 import get_net_news

def test_metal_waste():
    main_url='http://www.metalscrap.com.cn/chinese/'
    re_html=r'<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" >(.*?)</table>'
    re_list=r'<tr.*?>(.*?)</tr>'
    re_time=r'<span class="cGray" style="float: right;">.*?</span>'
    re_content=r'<div align="left">(.*?)<table width="100%"  border="0" cellspacing="0" cellpadding="0" style="margin:30px 0px">'
    re_hand=[]
    typeid=176
    typeid2=0
    url='http://www.metalscrap.com.cn/chinese/News_List.asp?typeid=310'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139a',re_content,re_hand)
    typeid=186
    typeid2=154
    url='http://www.metalscrap.com.cn/chinese/News_List.asp?typeid=320'
    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139a',re_content,re_hand)
#    typeid=188
#    typeid2=154
#    url='http://www.metalscrap.com.cn/chinese/News_List.asp?typeid=322'
#    get_net_news(main_url,url,typeid,typeid2,re_html,re_list,'f139a',re_content,re_hand)
test_metal_waste()