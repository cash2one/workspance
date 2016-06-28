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

def getvipcomp(cid):
	if (str(cid) == 'None'):
		return '2'
	else:
		sql="select id from comp_zstinfo where com_id="+str(cid)+" and com_check=1"
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '2'
		else:
			return '3'
updatetablename="auth_user"

sql = "select Com_UserName,Com_PWsafe,Com_Email,com_id,convert(bigint,gmt_editdate) from comp_loading order by id desc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
valueupdate=[]
value1=[]
valueupdate1=[]

editdate=0

i=0
for a in results:
	username=changezhongwen(str(a[0]))
	
	password=str(a[1])
	email=changezhongwen(str(a[2]))
	old_company_id=str(a[3])
	if (username == 'None'):
		username=email
	if (username == ''):
		username=email
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	blocked=0
	username=username.replace('\\','')
	email=email.replace('\\','')

	value=[username,password,email,gmt_created,gmt_modified,old_company_id]
	#valueupdate=[username,password,email,gmt_modified,gmt_login,num_login]
	valueupdate=[password,gmt_modified,username]
	
	sql="select count(0) from auth_user where username='"+username+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		#sql="update auth_user set password=%s,gmt_modified=%s where username=%s"
		#cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		email=email.replace('\\','')
		sql1="select count(0) from auth_user where email='"+email+"'"
		cursor.execute(sql1)
		eresault=cursor.fetchone()
		eresaultcount=eresault[0]
		if (eresaultcount<=0):
			sql="insert into auth_user(username,password,email,gmt_created,gmt_modified,old_company_id)"
			sql=sql+"values(%s,%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);
			updateflag="新增"
	editdate=str(a[4])
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1
	
try:
	#读取最新更新时间
	if (editdate!='0'):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		#cursor1.execute(sql)
		#conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e

conn.close() 
conn_rcu.close()
conn_others.close()
#os.system("python "+nowpath+"/comp_info.py")
