#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://health.sina.com.cn/"
listconf['list_url']="http://roll.health.sina.com.cn/health_liangxing/index_1.shtml"
listconf['re_html']=r'<div class="mainM" style="width:650px;">(.*?)<table cellspacing="0" style="margin:0 auto;">'
listconf['re_list']=r'<li>(.*?)</li>'
listconf['re_content']=r'正文内容 begin(.*?)正文内容 end'
listconf['re_encode']="gbk"
listconf['re_contentpage']=r'<div class="pagebox" id="_function_code_page">(.*?)</ul></div>'
listconf['re_contentpagestr']=None
listconf['re_contentpagenum']=2
listconf['keywords']="性保健"

pagelist=range(1,2)
pagelist.sort(reverse=True)
listconf['list_url']="http://roll.health.sina.com.cn/health_liangxing/index_(*).shtml"
listconf['keywords']="性保健"
get_sexlist(14,1,listconf['list_url'],pagelist,listconf)

