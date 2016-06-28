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
updatetablename="offer_updateCountry"
execfile("/usr/apps/python/inc.py")
connt = MySQLdb.connect(host='192.168.110.118', user='asto_crm', passwd='zj88friend',db='asto_crm',charset='utf8')   
cursort = connt.cursor()
conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()

#获得国家编号
def getcountry(com_id):
	sql="select com_ctr_id from comp_info where com_id="+str(com_id)+""
	cursor1.execute(sql)
	oldidstr=cursor1.fetchone()
	if (oldidstr == None):
		return '0'
	else:
		return oldidstr[0]

sql="select top 100000 com_id,pdt_id from products where not exists(select com_id from comp_info where com_ctr_id=1  and products.com_id=com_id)"
cursor1.execute(sql)
results = cursor1.fetchall()
i=0
for a in results:
	com_id=str(a[0])
	country=getcountry(com_id)
	pdt_id=str(a[1])
	valueupdate=[country,pdt_id]
	sql="update products set country=%s where pdt_id=%s"
	cursort.execute(sql,valueupdate);
	print changezhongwen("成功"+str(i))
	i+=1

try:
	print changezhongwen("更新成功")
	connt.close()
except Exception, e:
	print e