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

#cursor.execute("TRUNCATE TABLE `products_series`;")

sql = "select id,sname,sdetail,com_id,ord from products_series order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
	
for a in results:
	id=str(a[0])
	company_id=a[3]
	
	sql="select com_email from comp_info where com_id="+str(company_id)+""
	cursor1.execute(sql)
	pcode=cursor1.fetchone()
	if (pcode != None):
		account=pcode[0]
	else:
		account='admin'
	NAME=changezhongwen(a[1])
	series_details=changezhongwen(a[2])
	series_order=a[4]
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()

	value=[company_id,account,NAME,series_details,series_order,gmt_created,gmt_modified]
	sql="insert into products_series (company_id,account,NAME,series_details,series_order,gmt_created,gmt_modified)"
	sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
	#cursor.execute(sql,value);
	print id

cursor.execute("TRUNCATE TABLE `products_series_contacts`;")

sql = "select id,pdt_id,com_id,Series_ID from products_series_list order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
	
for a in results:
	id=str(a[0])
	products_id=str(a[1])
	products_series_id=a[3]

	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()

	value=[products_id,products_series_id,gmt_created,gmt_modified]
	sql="insert into products_series_contacts(products_id,products_series_id,gmt_created,gmt_modified)"
	sql=sql+"values(%s,%s,%s,%s)"
	cursor.execute(sql,value);
	print id
			

			
conn.close() 
conn1.close()
conn2.close()
