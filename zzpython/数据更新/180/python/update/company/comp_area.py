#!/usr/bin/env python   
#coding=utf-8   
 
import MySQLdb   
import pymssql
import sys
import codecs
import datetime
import struct
import os

reload(sys)
#type = sys.getfilesystemencoding()
sys.setdefaultencoding('utf-8')
execfile("/usr/tools/inc.py") 
execfile("/usr/tools/connbbs.py") 

conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()

conn2=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
cursor2=conn2.cursor()


sql = "select ctr_id,ctr_chn_name from countrys where ctr_id>1 order by ctr_id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=1000
ii=2
cbcode='1001'
for a in results:
	id=str(a[0])
	label=changezhongwen(a[1])
	show_index=ii
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	old_id=id
	i+=1
	ii+=1
	newcode=str(cbcode)+str(i)
	
	value=[newcode,label,show_index,gmt_created,gmt_modified,old_id]
	print newcode
	sql="select count(0) from category where old_id="+str(id)+" and CODE LIKE '________' and CODE LIKE '1001%'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into category(code,label,show_index,gmt_created,gmt_modified,old_id)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		print 'suc'

conn.close() 
conn1.close()
conn2.close()
