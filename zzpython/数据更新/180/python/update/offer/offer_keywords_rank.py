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
#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")


sql = "select id,pdt_id,fcheck,Fromdate,Todate,Todate,SearchName from comp_keyComp order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]

for a in results:
	id=str(a[0])
	product_id=a[1]
	name=changezhongwen(a[6])
	start_time=a[3]
	end_time=a[4]
	is_checked=a[2]
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()

	#print recbcode
	value=[product_id,name,start_time,end_time,is_checked,gmt_created,gmt_modified,id]
	valueu=[start_time,end_time,is_checked,id]
	#print value
	sql="select count(0) from  products_keywords_rank where oldid="+str(id)+" "
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into products_keywords_rank(product_id,name,start_time,end_time,is_checked,gmt_created,gmt_modified,oldid)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
	else:
		sql="update products_keywords_rank set start_time=%s,end_time=%s,is_checked=%s where oldid=%s"
		cursor.execute(sql,valueu);
	print ("suc")


conn.close() 
conn_rcu.close()
conn_others.close()