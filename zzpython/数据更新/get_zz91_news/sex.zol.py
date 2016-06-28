#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://xiaohua.zol.com.cn/"
listconf['list_url']="http://xiaohua.zol.com.cn/chengren/2.html"
listconf['re_html']=r'<ul class="article-list">(.*?)<div class="aside">'
listconf['re_list']=r'<span class="article-title">(.*?)</span>'
listconf['re_content']=r'<div class="article-text">(.*?)        </div>'
listconf['re_encode']="gbk"
listconf['re_contentpage']=r'<div id="page">(.*?)</div>'
listconf['re_contentpagestr']=None
listconf['re_contentpagenum']=1
listconf['keywords']="成人笑话"

pagelist=range(1,2)
listconf['list_url']="http://xiaohua.zol.com.cn/chengren/(*).html"
listconf['keywords']="成人笑话"
get_sexlist(36,1,listconf['list_url'],pagelist,listconf)

