#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://www.jijidi.com/"
listconf['list_url']="http://www.jijidi.com/fuqixiaohua/index_2.html"
listconf['re_html']=r'<ul class="piclist longList">(.*?)<li class="piclist2"> </li>'
listconf['re_list']=r'<li class="piclist(.*?)<span class="new"></span>'
listconf['re_content']=r'<div class="p20">(.*?)<div class="tag">'
listconf['re_encode']=""
listconf['re_contentpage']=r'<div id="page">(.*?)</div>'
listconf['re_contentpagestr']=None
listconf['re_contentpagenum']=1
listconf['keywords']="成人笑话"

pagelist=range(2,3)
listconf['list_url']="http://www.jijidi.com/fuqixiaohua/index_(*).html"
listconf['keywords']="成人笑话"
get_sexlist(36,1,listconf['list_url'],pagelist,listconf)

