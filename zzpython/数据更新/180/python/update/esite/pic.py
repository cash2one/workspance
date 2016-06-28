#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="comp_pic"
sql="select com_id,path,Description,id from mysite_compPic  order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	com_id=str(a[0])
	path="myrc"+changezhongwen(a[1])
	Description=changezhongwen(a[2])
	old_id=str(a[3])
	value=[com_id,path,Description,old_id]

	#-判断是否已经导
	sql="select count(0) from company_upload_file where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update company_upload_file set company_id=%s,filepath=%s,remark=%s where old_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		sql="insert into company_upload_file(company_id,filepath,remark,old_id)"
		sql=sql + "  values(%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	i=i+1
	
try:
	print changezhongwen("更新成功")
except Exception, e:
	print e
conn.close() 
conn_rcu.close()

