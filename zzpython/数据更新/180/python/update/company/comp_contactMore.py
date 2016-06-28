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
def getaccount(comid):
	sql="select account from company_account where company_id="+comid+""
	cursor.execute(sql)
	arraccount=cursor.fetchone()
	if (arraccount == None):
		return ''
	else:
		return arraccount[0]

sql = "select id,com_id,com_contactperson,com_desc,com_tel,com_email from comp_contactmore order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=0
for a in results:
	id=str(a[0])
	company_id=str(a[1])
	print company_id
	account=getaccount(company_id)
	name=changezhongwen(a[2])
	sex=changezhongwen(a[3])
	if (sex!='None'):
		if (sex==changezhongwen('先生')):
			sex='0'
		else:
			sex='1'
	tel=changezhongwen(a[4])
	mobile=changezhongwen(a[5])
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	value=[account,name,sex,tel,mobile,'0',gmt_created,gmt_modified,id]
	sql="select count(0) from company_account_contact where old_id='"+id+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update company_account_contact set account=%s,name=%s,sex=%s,tel=%s,mobile=%s,is_hidden=%s,gmt_created=%s,gmt_modified=%s where old_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		sql="insert into company_account_contact(account,name,sex,tel,mobile,is_hidden,gmt_created,gmt_modified,old_id) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	i=i+1
conn.close()
conn_rcu.close()
conn_others.close()
