#-*- coding:utf-8 -*-
#中国钢材网
from getnews import get_net_news

def get_goepe():
    main_url='http://www.goepe.com/news/'
    re_html=r'<div class="list_block">(.*?)<div class="page">'
    re_list=r'<li>(.*?)</li>'
    re_time=''
    re_content=r'<div id="content">(.*?)<div class="shengming">'
    re_hand=[]
    url='http://www.goepe.com/news/search.php?fenlei=10'
    get_net_news(main_url,url,re_html,re_list,re_time,re_content,re_hand)
    url='http://www.goepe.com/news/search.php?fenlei=30'
    get_net_news(main_url,url,re_html,re_list,re_time,re_content,re_hand)
    url='http://www.goepe.com/news/search.php?fenlei=50'
    get_net_news(main_url,url,re_html,re_list,re_time,re_content,re_hand)

if __name__=="__main__":
    get_goepe()
