#-*- coding:utf-8 -*-
#橡胶91
from getnews2 import get_net_news
            
def test_xj91():
    main_url='http://www.xj91.com.cn/zhuanjia/'
    re_title=r'''<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#2B2E31">(.*?)<table width='500'>'''
    re_content=r'<td height="198" align="left" valign="top">(.*?)</table>'
    re_hand=[]
    typeid=187
    typeid2=156
    url='http://www.xj91.com.cn/zhuanjia/wz_more.asp?zjw_bigclassname=%BC%BC%CA%F5%CE%C4%D5%AA'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)