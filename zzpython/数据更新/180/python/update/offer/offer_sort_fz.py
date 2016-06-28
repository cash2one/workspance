#!/usr/bin/env python   
#coding=utf-8   
######################################   
#   
# @author migle   
# @date 2010-01-17   
#   
######################################   
#   
# MySQLdb ≤È—Ø   
#   
#######################################   
  
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

cursor.execute("TRUNCATE TABLE `products_search_associate_keywords`;")

sql = "select id,cb_link from cls_cbSub order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
linkvalue=[]
	
for a in results:
	id=str(a[0])
	linkvalue=a[1]
	sql="select code from category_products where oldid2="+str(id)+""
	cursor.execute(sql)
	pcode=cursor.fetchone()
	if (pcode != None):
		ppcode=pcode[0]
	else:
		ppcode='0'
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	if (linkvalue != None):
		linkvalue=linkvalue.replace('£¨',',').replace(' ','')
		linkvalue=linkvalue.split(',')
		for b in range(len(linkvalue)):
			pname = changezhongwen(linkvalue[b])
			print pname
			value=[ppcode,pname,0,gmt_created,gmt_modified]
			sql="insert into products_search_associate_keywords (category_products_code,keyword,is_assist,gmt_created,gmt_modified)"
			sql=sql+"values(%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);

			
			
sql = "select id,cb_link from cls_cbSub_s order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
linkvalue=[]
	
for a in results:
	id=str(a[0])
	linkvalue=a[1]
	sql="select code from category_products where oldid3="+str(id)+""
	cursor.execute(sql)
	pcode=cursor.fetchone()
	if (pcode != None):
		ppcode=pcode[0]
	else:
		ppcode='0'
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	if (linkvalue != None):
		linkvalue=linkvalue.replace('£¨',',').replace(' ','')
		linkvalue=linkvalue.split(',')
		for b in range(len(linkvalue)):
			pname = changezhongwen(linkvalue[b])
			print pname
			value=[ppcode,pname,0,gmt_created,gmt_modified]
			sql="insert into products_search_associate_keywords (category_products_code,keyword,is_assist,gmt_created,gmt_modified)"
			sql=sql+"values(%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);


			
			
sql = "select cs_id,cb_link from cls_s order by cs_id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
linkvalue=[]
	
for a in results:
	id=str(a[0])
	linkvalue=a[1]
	sql="select code from category_products where oldid4="+str(id)+""
	cursor.execute(sql)
	pcode=cursor.fetchone()
	if (pcode != None):
		ppcode=pcode[0]
	else:
		ppcode='0'
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	if (linkvalue != None):
		linkvalue=linkvalue.replace('£¨',',').replace(' ','')
		linkvalue=linkvalue.split(',')
		for b in range(len(linkvalue)):
			pname = changezhongwen(linkvalue[b])
			print pname
			value=[ppcode,pname,0,gmt_created,gmt_modified]
			sql="insert into products_search_associate_keywords (category_products_code,keyword,is_assist,gmt_created,gmt_modified)"
			sql=sql+"values(%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);
		
		
sql = "select id,cb_link from cls_hiddenSort order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
linkvalue=[]
	
for a in results:
	id=str(a[0])
	linkvalue=a[1]
	sql="select code from category_products where oldhiddenid="+str(id)+""
	cursor.execute(sql)
	pcode=cursor.fetchone()
	if (pcode != None):
		ppcode=pcode[0]
	else:
		ppcode='0'
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	if (linkvalue != None):
		linkvalue=linkvalue.replace('£¨',',').replace(' ','')
		linkvalue=linkvalue.split(',')
		for b in range(len(linkvalue)):
			pname = changezhongwen(linkvalue[b])
			print pname
			value=[ppcode,pname,1,gmt_created,gmt_modified]
			sql="insert into products_search_associate_keywords (category_products_code,keyword,is_assist,gmt_created,gmt_modified)"
			sql=sql+"values(%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);
			
conn.close() 
conn1.close()
conn2.close()
