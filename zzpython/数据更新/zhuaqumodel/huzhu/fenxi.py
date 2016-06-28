#-*- coding:utf-8 -*-
#----抓取和讯网汇率
from public import *
#from zz91db_ast import companydb
#from zz91db_130 import otherdb
from zz91db_test import testdb
dbt=testdb()

def getfenxi():
    sql='select id,content from bbs_zhishi where typeid=9'
    sql1='update bbs_zhishi set content=%s where id=%s'
    resultlist=dbt.fetchalldb(sql)
    for result in resultlist:
        id=result[0]
        content=result[1]
    #    content=content.replace('pic/images','http://xueyuan.qqzssl.com/xueYuan/pic/images')
        content=content.replace('http://xueyuan.qqzssl.com/xueYuan/http://xueyuan.qqzssl.com/xueYuan/pic/images','http://xueyuan.qqzssl.com/xueYuan/pic/images')
        dbt.updatetodb(sql1,[content,id])
        print id
        print content
def getfenxi2():
    sql='select id,title from bbs_zhishi where typeid=11'
    sql1='update bbs_zhishi set title=%s where id=%s'
    resultlist=dbt.fetchalldb(sql)
    for result in resultlist:
        id=result[0]
        title=result[1]
    #    content=content.replace('pic/images','http://xueyuan.qqzssl.com/xueYuan/pic/images')
        title=title.replace('<SPAN class=STYLE1>','')
        title=title.replace('</SPAN>','')
        dbt.updatetodb(sql1,[title,id])
        print id
        print title

#getfenxi()
getfenxi2()