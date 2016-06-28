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
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py")
cursor1=conn_rcu.cursor()
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")


updatetablename="company_customers_group"
sql = "select id,com_id,Group_Name from comp_Customer_Group where id>"+getlastid(updatetablename)+" order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=0
for a in results:
	id=str(a[0])
	Group_Name=changezhongwen(a[2])
	company_id=a[1]
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	
	value=[Group_Name,company_id,id,gmt_created,gmt_modified]
	sql="select count(0) from company_customers_group where old_id="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into company_customers_group(name,company_id,old_id,gmt_created,gmt_modified)"
		sql=sql+"values(%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		conn.commit()
		print 'suc'+str(i)
	i+=1
	updatelastid(updatetablename,id)

conn.close() 
conn1.close()
conn2.close()
