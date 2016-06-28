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
#--------------------------------------

def getparentcode(code):
	sql="select id from products_albums where oldcode='"+str(code)+"'"
	cursor.execute(sql)
	newcodeid=cursor.fetchone()
	if (newcodeid == None):
		return '0'
	else:
		return newcodeid[0]

sql = "select id,code,meno from AlbumType order by code asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]

for a in results:
	id=str(a[0])
	code=a[1]
	meno=changezhongwen(a[2])
	if (len(str(code)) == 2):
		parent_id='0'
	else:
		parent_id=getparentcode(str(code)[0:2])
	print parent_id
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()

	#print recbcode
	value=[meno,parent_id,gmt_created,gmt_modified,code,0]
	#print value
	sql="select count(0) from products_albums where oldcode="+str(code)+" "
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into  products_albums(name,parent_id,gmt_created,gmt_modified,oldcode,is_delete)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		print ("suc")


conn.close() 
conn_rcu.close()
conn_others.close()