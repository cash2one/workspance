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

updatetablename="offer_info_del"
execfile("/usr/apps/python/inc.py")
#
if (getlastid(updatetablename)==""):
	updatelastid(updatetablename,'0')
sql = "select pdt_id,convert(bigint,fdate)"
sql +=" from products_delFlag where convert(bigint,fdate)>"+getlastid(updatetablename)+" order by pdt_id asc"
print sql
cursor1.execute(sql)
results = cursor1.fetchall()


value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	pdt_id=str(a[0])
	
	#-�ж��Ƿ��Ѿ���
	sql="select count(0) from products_del where pdt_id="+str(pdt_id)+""
	cursort.execute(sql)
	oldid=cursort.fetchone()
	oldidcount=oldid[0]
	value=[pdt_id]

	if (oldidcount>0):
		
		updateflag="����"
	else:
		sql="insert into products_del(pdt_id)"
		sql=sql+"values(%s)"
		cursort.execute(sql,value);
		updatelastid(updatetablename,str(a[1]))
		updateflag="����"
	i+=1
	print changezhongwen(updateflag+"�ɹ�"+str(i))
try:
	#��ȡ���¸���ʱ��
	
	
	conn1.close()
	conn2.close()
	connt.close()
except Exception, e:
	print e



