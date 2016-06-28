#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://love.heima.com/"
listconf['list_url']="http://love.heima.com/html/c47/indexp2.shtm"
listconf['re_html']=r'<span id="dlNews" style="display:inline-block;width:100%;">(.*?)<div class="pager">'
listconf['re_list']=r'</a></b>] (.*?) </span><br />'
listconf['re_content']=r'<div id="content"> <br>(.*?)<div style="">'
listconf['re_encode']=""
listconf['re_contentpage']=r'<div id="page">(.*?)</div>'
listconf['re_contentpagestr']=None
listconf['re_contentpagenum']=1
listconf['keywords']="情感驿站"
"""
pagelist=range(1,20)
listconf['list_url']="http://love.heima.com/html/c47/indexp(*).shtm"
listconf['keywords']="情感驿站"
get_sexlist(40,1,listconf['list_url'],pagelist,listconf)
"""

pagelist=range(1,2)
listconf['list_url']="http://love.heima.com/html/c156/indexp(*).shtm"
listconf['keywords']="性爱技巧"
get_sexlist(22,1,listconf['list_url'],pagelist,listconf)


