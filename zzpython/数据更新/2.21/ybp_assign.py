#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')
nowpath="/usr/apps/python"
execfile(nowpath+"/conn.py")
execfile(nowpath+"/inc.py")

def ybpassign():
    sql="select id from users where assignybp=1"
    cursor.execute(sql)
    results = cursor.fetchall()
    if results:
        for a in results:
            personid=a[0]
            sqli="insert into ybp_assign (personid,cid) select top 2 "+str(personid)+",id from ybp_company where area like '%上海%' and not exists(select cid from ybp_assign where cid=ybp_company.id) and not exists(select cid from ybp_gonghai where cid=ybp_company.id)"
            print sqli
            cursor.execute(sqli)
            conn.commit()
            print personid
    conn.commit()        
ybpassign()  