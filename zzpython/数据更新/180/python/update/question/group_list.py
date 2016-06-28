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
updatetablename="groupList"

sql="select id,com_id,groupid,qid"
sql=sql+" from Comp_Question_Group order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	group_id=str(a[2])
	sqlp="select id from inquiry_group where old_id="+group_id
	cursor.execute(sqlp)
	newcode=cursor.fetchone()
	if (newcode == None):
		gid=0
	else:
		gid=newcode[0]
	qid=str(a[3])
	
	value=[gid, qid]
	if (gid>0):
		sql="update inquiry set group_id=%s where old_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
		print changezhongwen(updateflag+"成功"+str(i))
		i=i+1
	
try:
	#读取最新更新时间
	print changezhongwen("更新成功")
except Exception, e:
	print e
conn.close() 
conn_rcu.close()

