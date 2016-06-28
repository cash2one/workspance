#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://qg.nrsfh.com/"
listconf['list_url']="http://sex.39.net/zajq/xjq/index.html"
listconf['re_html']=r'<ul class="listinfo">(.*?)</ul>'
listconf['re_list']=r'<li>(.*?)</li>'
listconf['re_content']=r'<div class="bodycss">(.*?)<div class="adminnam">'
listconf['re_encode']="gbk"
listconf['re_contentpage']=r'<div class="dede_pages"><ul class="pagelist">(.*?)</ul></div>'
listconf['re_contentpagestr']=None
listconf['re_contentpagenum']=2
listconf['keywords']="婚外情"

pagelist=range(1,2)
listconf['list_url']="http://qg.nrsfh.com/hwq/list_22_(*).html"
listconf['keywords']="婚外情"
get_sexlist(8,1,listconf['list_url'],pagelist,listconf)

pagelist=range(1,2)
listconf['keywords']="夫妻生活"
listconf['list_url']="http://qg.nrsfh.com/hunyin/list_20_(*).html"
get_sexlist(7,1,listconf['list_url'],pagelist,listconf)

pagelist=range(1,2)
listconf['keywords']="名人情事"
listconf['list_url']="http://qg.nrsfh.com/mrqs/list_190_(*).html"
get_sexlist(34,1,listconf['list_url'],pagelist,listconf)

pagelist=range(1,2)
listconf['keywords']="一夜情"
listconf['list_url']="http://qg.nrsfh.com/onenight/list_264_(*).html"
get_sexlist(10,1,listconf['list_url'],pagelist,listconf)
