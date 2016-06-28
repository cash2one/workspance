#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
import base64,hashlib
from django.utils.http import urlquote
import random
import shutil
try:
    import cPickle as pickle
except ImportError:
    import pickle


reload(sys)
sys.setdefaultencoding('utf-8')

#execfile("/usr/tools/connbbs.py")
connt = MySQLdb.connect(host='192.168.110.118', user='asto_crm', passwd='zj88friend',db='asto_crm',charset='utf8')   
cursort = connt.cursor()
conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()
conn2=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
cursor2=conn2.cursor()

updatetablename="offer_info_stop"
execfile("/usr/apps/python/inc.py")
#
sql="DELETE FROM products_stop"
cursort.execute(sql)
sql = "select pdt_id"
sql +=" from products_stop  order by pdt_id asc"
cursor1.execute(sql)
results = cursor1.fetchmany(100000)


value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	pdt_id=str(a[0])
	value=[pdt_id]
	sql="insert into products_stop(pdt_id)"
	sql=sql+"values(%s)"
	cursort.execute(sql,value);
	updateflag="新增"
	"""
	#-判断是否已经导
	sql="select count(0) from products_stop where pdt_id="+str(pdt_id)+""
	cursort.execute(sql)
	oldid=cursort.fetchone()
	oldidcount=oldid[0]
	value=[pdt_id]

	if (oldidcount>0):
		
		updateflag="更新"
	else:
		sql="insert into products_stop(pdt_id)"
		sql=sql+"values(%s)"
		cursort.execute(sql,value);
		updatelastid(updatetablename,str(a[1]))
		updateflag="新增"
	"""
	i+=1
	print changezhongwen(updateflag+"成功"+str(i))
try:
	#读取最新更新时间
	
	
	conn1.close()
	conn2.close()
	connt.close()
except Exception, e:
	print e



