#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')
conn_rcu=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor_rcu=conn_rcu.cursor()
conn = MySQLdb.connect(host='192.168.110.130', user='zzother', passwd='jmLt9zGdXa59vrLM',db='zzother',charset='utf8')
cursor=conn.cursor()
def isNum(value):
    try:
        value + 1
    except TypeError:
        return False
    else:
        return True
def changezhongwen(strvalue):
    if(strvalue == None):
        tempstr=""
    else:
        if str(strvalue).isalnum():
            tempstr=strvalue
        else:
            if isNum(strvalue):
                tempstr=strvalue
            else:
                tempstr=strvalue.decode('GB18030','ignore').encode('utf-8')
    return tempstr
def linkup():
    sql="select title,addr,glink,ord,lb from TB_guanggao where flag=1"
    cursor_rcu.execute(sql)
    results = cursor_rcu.fetchall()
    if results:
        for list in results:
            name=changezhongwen(list[0])
            if list[1]:
                pic="http://www.zz91.com"+list[1]
            else:
                pic=""
            url=list[2]
            sortrank=list[3]
            typeid="76"
            wtype="3"
            gmt_created=datetime.datetime.now()
            updatetime=datetime.datetime.now()
            lb=list[4]
            if str(lb)=="10":
                typeid="74"
            if str(lb)=="11":
                typeid="76"
            sqla="select id from website where name=%s and typeid=%s and wtype=%s"
            cursor.execute(sqla,[name,typeid,wtype])
            resultad=cursor.fetchone()
            if resultad==None:
                sqlb="insert into website(name,pic,url,sortrank,typeid,wtype,updatetime) values(%s,%s,%s,%s,%s,%s,%s)"
                cursor.execute(sqlb,[name,pic,url,sortrank,typeid,wtype,updatetime])
                print sqlb
            
linkup()
            