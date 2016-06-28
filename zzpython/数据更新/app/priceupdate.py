#-*- coding:utf-8 -*-
#----商行定制定时任务
from public import *
#from sphinxapi import *
from zz91db_ast import companydb

dbc=companydb()

#----更新余姚塑料城
def updateprice():
    sql='select id,title from price where type_id=110 and id>560000'
    sql2='update price set type_id=324 where id=%s'
    resultlist=dbc.fetchalldb(sql)
    for result in resultlist:
        id=result[0]
        title=result[1]
        if '余姚塑料城' in title:
            dbc.updatetodb(sql2,id)
            print id

updateprice()