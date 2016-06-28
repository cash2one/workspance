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
updatetablename="comp_link"
def getusername(com_id):
	sql="select Com_UserName,Com_Email from comp_loading where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return 'admin'
	else:
		username=changezhongwen(str(newcode[0]))
		email=changezhongwen(str(newcode[1]))
		if (username == 'None' or username==''):
			username=email
		return username
sql="select com_id,lname,ladd,id from comp_friendLinks  order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	account=getusername(company_id)
	link_name=changezhongwen(a[1])
	link_address=changezhongwen(a[2])
	old_id=str(a[3])
	value=[company_id,account,link_name,link_address,old_id]

	#-判断是否已经导
	sql="select count(0) from esite_friend_link where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update esite_friend_link set company_id=%s,account=%s,link_name=%s,link_address=%s where old_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		sql="insert into esite_friend_link(company_id,account,link_name,link_address,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s)"
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

