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
conn = MySQLdb.connect(host='192.168.110.118', user='kang', passwd='astozz91jiubao',db='ast',charset='utf8')
cursor=conn.cursor()
conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()


updatetablename="offer_info_del_new"
execfile("/usr/apps/python/inc.py")
#
if (getlastid(updatetablename)==""):
	updatelastid(updatetablename,'0')
sql = "select pdt_id,convert(bigint,fdate)"
sql +=" from products_delFlag  order by pdt_id asc"

cursor1.execute(sql)
results = cursor1.fetchall()


value=[]
i=0
for a in results:
	pdt_id=str(a[0])
	
	#-判断是否已经导
	sql="select count(0) from products where id="+str(pdt_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	value=[pdt_id]
	if (oldidcount>0):
		sql="update products set is_del=1 where id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		updateflag="新增"
	i+=1
	print changezhongwen(updateflag+"成功"+str(i))
try:
	#读取最新更新时间
	conn1.close()
	conn.close()
except Exception, e:
	print e



