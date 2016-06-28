#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://www.xxxsfh.com/"
listconf['list_url']="http://www.xxxsfh.com/xiaohua-page-2.htm"
listconf['re_html']=r'<ul class="listinfo">(.*?)</ul>'
listconf['re_list']=r'<li>(.*?)</li>'
listconf['re_content']=r'<div class="maincon cfix">(.*?)</div>.*?<div id="page">'
listconf['re_encode']="gbk"
listconf['re_contentpage']=r'<div id="page">(.*?)</div>'
listconf['re_contentpagestr']=None
listconf['re_contentpagenum']=1
listconf['keywords']="成人笑话"

pagelist=range(1,2)
listconf['list_url']="http://www.xxxsfh.com/xiaohua-page-(*).htm"
listconf['keywords']="成人笑话"
get_sexlist(36,1,listconf['list_url'],pagelist,listconf)

