#-*- coding:utf-8 -*-
#中国再生资源交易网--某些:有登录锁,有无效页面
from getnews2 import get_net_news

def test_china_rrdeal():
    main_url='http://www.crra010.com'
    re_title=r'<div class="mainNewsList">(.*?)<div class="nextPage">'
    re_content=r'<div style="clear:both"></div>(.*?)<span id="sourceRight">'
    re_hand=[]
    re_hand.append(r'<strong>中再网参考观点：</strong>')
    re_hand.append(r'<strong>中再网参考观点</strong>：')
    re_hand.append(r'中再交易网.*?www.crra010.com.*?2014年1月16日')
    typeid=176
    typeid2=0
    url='http://www.crra010.com/index.php/Article/articleList/cateId/12'
    get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand)
    
test_china_rrdeal()