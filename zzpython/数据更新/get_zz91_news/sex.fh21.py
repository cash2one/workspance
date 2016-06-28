#-*- coding:utf-8 -*-
#飞华 两性
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from getnews2 import simpget_news
def get_sexlist(typeid,typeid2,url,page):
    main_url='http://sex.fh21.com.cn'
    re_html=r'<div class="catalog05 catalog05d">(.*?)<ul class="height20"></ul>'
    re_list=r'<p>(.*?)</p>'
    re_time=''
    re_content=r'<div class="detailc">(.*?)<div style="width:640px;margin:10px auto 30px;overflow-x:hidden;text-align:center;">'
    re_hand=[]
    re_encode="gbk"
    re_contentpage=r'<div class="pageStyle">(.*?)</div>'
    url=url+str(page)+'.html'
    re_contentpagenum=2
    
    simpget_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand,re_contentpage=re_contentpage,re_encode=re_encode,re_contentpagenum=re_contentpagenum)



get_sexlist(2,1,'http://sex.fh21.com.cn/bj/yw/list_5470_',1)
get_sexlist(3,1,'http://sex.fh21.com.cn/bj/ys/list_5471_',1)

get_sexlist(4,1,'http://sex.fh21.com.cn/bj/xawq/list_5472_',1)

get_sexlist(5,1,'http://sex.fh21.com.cn/bj/xbj/list_5473_',1)

get_sexlist(7,1,'http://sex.fh21.com.cn/xl/nx/list_5494_',1)

get_sexlist(8,1,'http://sex.fh21.com.cn/xl/qcq/list_5496_',1)

get_sexlist(9,1,'http://sex.fh21.com.cn/xl/nvx/list_5495_',1)

get_sexlist(10,1,'http://sex.fh21.com.cn/xl/fq/list_5497_',1)

get_sexlist(12,1,'http://sex.fh21.com.cn/sl/nxsl/list_5491_',1)

get_sexlist(13,1,'http://sex.fh21.com.cn/sl/hy/list_5486_',1)

get_sexlist(14,1,'http://sex.fh21.com.cn/sl/xzs/list_5487_',1)

get_sexlist(15,1,'http://sex.fh21.com.cn/sl/xxw/list_5488_',1)

get_sexlist(17,1,'http://sex.fh21.com.cn/jy/qcys/list_5506_',1)

get_sexlist(18,1,'http://sex.fh21.com.cn/jy/qcq/list_5504_',1)

get_sexlist(19,1,'http://sex.fh21.com.cn/jy/cnr/list_5503_',1)

get_sexlist(20,1,'http://sex.fh21.com.cn/jy/zln/list_5505_',1)

get_sexlist(22,1,'http://sex.fh21.com.cn/sh/xajq/list_5508_',1)

get_sexlist(23,1,'http://sex.fh21.com.cn/sl/nvsl/list_5492_',1)

get_sexlist(24,1,'http://sex.fh21.com.cn/sh/wmxa/list_8893_',1)

get_sexlist(25,1,'http://sex.fh21.com.cn/sh/xatw/list_5511_',1)

get_sexlist(27,1,'http://sex.fh21.com.cn/ask/xpl/list_8916_',1)

get_sexlist(28,1,'http://sex.fh21.com.cn/ask/xzl/list_8917_',1)

get_sexlist(29,1,'http://sex.fh21.com.cn/ask/xjk/list_8918_',1)

get_sexlist(30,1,'http://sex.fh21.com.cn/ask/xyh/list_8919_',1)

    