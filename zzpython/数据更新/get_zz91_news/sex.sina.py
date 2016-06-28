#-*- coding:utf-8 -*-
#新浪 两性
import sys
from getnews2 import get_sexlist
reload(sys)
sys.setdefaultencoding('utf8')

listconf={}
listconf['main_url']="http://hebei.sina.com.cn/"
listconf['list_url']="http://hebei.sina.com.cn/z/health/sex/xingjiqiao.shtml"
listconf['re_html']=r'<div class="d_list_txt">(.*?)</ul>'
listconf['re_list']=r'<li>(.*?)</li>'
listconf['re_content']=r'<div class="main-body">(.*?)<span id="_function_code_page">'
listconf['re_encode']="gbk"
listconf['re_contentpage']=r'<p align=right>(.*?)</p></span>'
listconf['re_contentpagestr']=r"<span class='total'>(.*?)</span>"
listconf['re_contentpagenum']=None

#get_sexlist(22,1,listconf['list_url'],'',listconf)
pagelist=[1]
listconf['list_url']="http://hebei.sina.com.cn/z/health/sex/xingzhishi.shtml"
get_sexlist(11,1,listconf['list_url'],pagelist,listconf)

listconf['list_url']="http://hebei.sina.com.cn/z/health/sex/liangxingbaojian.shtml"
get_sexlist(14,1,listconf['list_url'],pagelist,listconf)

listconf['list_url']="http://hebei.sina.com.cn/z/health/sex/liangxingqinggan.shtml"
get_sexlist(7,1,listconf['list_url'],pagelist,listconf)

listconf['list_url']="http://hebei.sina.com.cn/z/health/sex/shengzhijiankang.shtml"
get_sexlist(23,1,listconf['list_url'],pagelist,listconf)

