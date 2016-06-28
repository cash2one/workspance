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
updatetablename="customer_vote"
def getusername(com_id):
	sql="select Com_UserName,Com_Email from comp_loading where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return 'guest'
	else:
		username=changezhongwen(str(newcode[0]))
		email=changezhongwen(str(newcode[1]))
		if (username == 'None' or username==''):
			username=email
		return username

sql="select com_id,fname,emails,topic,infos"
sql+=",sh,reply,subtime,telcontent,id "
sql+="from crm_serviceword  order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	if (company_id=='0'):
		company_id=None
	account=getusername(company_id)
	if (account=='guest'):
		account=''
	email=changezhongwen(a[2])
	category=0
	title=changezhongwen(a[3])
	content=changezhongwen(a[4])
	reply_content=changezhongwen(a[8])
	gmt_reply=changezhongwen(a[2])
	check_status=str(a[5])
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	old_id=str(a[9])
	comname=changezhongwen(a[1])
	
	value=[company_id, account, email, category, title, content, reply_content, gmt_created, gmt_reply, gmt_modified, check_status,comname ,old_id ]

	#-判断是否已经导
	sql="select count(0) from feedback where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update feedback set company_id=%s, account=%s, email=%s, category=%s, title=%s, content=%s, reply_content=%s, gmt_created=%s, gmt_reply=%s, gmt_modified=%s, check_status=%s,comname=%s where old_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		sql="insert into feedback(company_id, account, email, category, title, content, reply_content, gmt_created, gmt_reply, gmt_modified, check_status,comname ,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
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
os.system("python "+nowpath+"/sales_tel.py")
