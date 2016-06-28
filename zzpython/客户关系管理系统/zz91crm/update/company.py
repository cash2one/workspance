#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')
nowpath=os.path.dirname(__file__)
print nowpath
execfile(nowpath+"conn.py")

def getcompany(tab):
    sql="select * from "+str(tab)+" limit 0,1000"
    cursorserver.execute(sql)
    results = cursorserver.fetchall()
    
    for list in results:
        filds=""
        vlist=""
        vals=[]
        id=list['id']
        for ll in list:
            filds+=ll+","
            vlist+="%s,"
            content=list[ll]
            vals.append(content)
        filds=filds[0:len(filds)-1]
        vlist=vlist[0:len(vlist)-1]
        sql="select id from "+str(tab)+" where id=%s"
        result=cursormy.execute(sql,[id])
        if not result:
            sql="insert into "+str(tab)+"("+str(filds)+") values("+vlist+")"
            cursormy.execute(sql,vals)
            connmy.commit()
#getcompany("company")
#getcompany("company_account")
getcompany("auth_user")