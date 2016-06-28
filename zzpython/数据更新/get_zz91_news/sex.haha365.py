#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://www.haha365.com/"
listconf['list_url']="http://www.haha365.com/Adult_joke/index_2.htm"
listconf['re_html']=r'<div class="r_c">(.*?)<div class="r_b"></div>'
listconf['re_list']=r'<h3>(.*?)</h3>'
listconf['re_content']=r'<div id="endtext">(.*?)<div class="bg_t1"></div>'
listconf['re_encode']="gbk"
listconf['re_contentpage']=r'<div id="page">(.*?)</div>'
listconf['re_contentpagestr']=None
listconf['re_contentpagenum']=1
listconf['keywords']="成人笑话"

pagelist=range(1,2)
listconf['list_url']="http://www.haha365.com/Adult_joke/index_(*).htm"
listconf['keywords']="成人笑话"
get_sexlist(36,1,listconf['list_url'],pagelist,listconf)

