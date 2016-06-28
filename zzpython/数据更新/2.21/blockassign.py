#!/usr/bin/env python   
#coding=utf-8   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
from datetime import timedelta, date

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../')
nowpath=os.path.abspath('.')
execfile(nowpath+"/conn.py")
execfile(nowpath+"/inc.py")
#--------------------------------------
updatetablename="service"

sql="select  id,zhuanzheng "
sql=sql+" from users where closeflag=1 and userid like '13%'"
cursor.execute(sql)
results = cursor.fetchall()
i=0
for a in results:
	personid=str(a[0])
	zhuanzheng=str(a[1])
	#判断4星数量
	sqla="select count(0) from Crm_To4star where fdate<'"+str(date.today()+timedelta(days=1))+"' and fdate>='"+str(date.today())+"' and personid="+str(personid)+""
	cursor.execute(sqla)
	returnvalue=cursor.fetchone()
	star4count=returnvalue[0]
	#判断5星数量
	sqla="select count(0) from Crm_To5star where fdate<'"+str(date.today()+timedelta(days=1))+"' and fdate>='"+str(date.today())+"' and personid="+str(personid)+""
	cursor.execute(sqla)
	returnvalue=cursor.fetchone()
	star5count=returnvalue[0]
	print star4count
	print star5count
	sql="select count(0) from temp_blockAssign where personid="+str(personid)+" and fdate<'"+str(date.today()+timedelta(days=1))+"' and fdate>='"+str(date.today())+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		"""
		sql="delete from temp_blockAssign where personid="+str(personid)+" and fdate<'"+str(date.today()+timedelta(days=1))+"' and fdate>'"+str(date.today())+"'"
		cursor.execute(sql);
		conn.commit()
		"""
		updateflag="更新"
	else:
		updateflag="新增"
	#print changezhongwen(updateflag+"成功"+str(i))
	i=i+1
	
try:
	#读取最新更新时间
	print changezhongwen("更新成功")
except Exception, e:
	print e
conn.close()

