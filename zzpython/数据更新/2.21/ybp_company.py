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

def ybpinsert():
    sql="select * from ybp"
    cursor.execute(sql)
    results = cursor.fetchall()
    value=[]
    i=0
    for a in results:
        shop_name=a[1]
        contact=a[2]
        if contact:
            contact=contact[0:100]
        weburl=a[3]
        area=a[4]
        wangwang_no=a[5]
        income=a[6]
        shop_type=a[7]
        hy_type=a[8]
        sqlc="select id from ybp_company where shop_name=%s"
        cursor.execute(sqlc,(shop_name))
        yresult=cursor.fetchone()
        if (yresult==None):
            sql="insert into ybp_company(shop_name,contact,weburl,area,wangwang_no,income,shop_type,hy_type) values(%s,%s,%s,%s,%s,%s,%s,%s)"
            cursor.execute(sql,(shop_name,contact,weburl,area,wangwang_no,income,shop_type,hy_type))
            conn.commit()
            print a[0]
ybpinsert()      