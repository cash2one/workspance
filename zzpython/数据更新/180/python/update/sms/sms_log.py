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
cursor_sms=conn_sms.cursor()
cursor_mysql_sms=conn_mysql_sms.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------


#convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')
updatetablename="sms_log"
def gettype_code(code):
	sql="select type_code from sms_stat  where id='"+str(code)+"'"
	cursor_mysql_sms.execute(sql)
	newcodeid=cursor_mysql_sms.fetchone()
	if (newcodeid == None):
		return '0'
	else:
		return newcodeid[0]

sql = "select id,mobile,gmt_created,stat_id,company_id,flag from sms_log where id>"+getlastid(updatetablename)+" order by id asc"
#print sql
cursor_mysql_sms.execute(sql)
results = cursor_mysql_sms.fetchall()

value=[]
i=0
for a in results:
	id=str(a[0])
	mobile=a[1]
	gmt_created=str(a[2])
	stat_id=str(a[3])
	company_id=str(a[4])
	flag=str(a[5])

	type_code=str(gettype_code(stat_id))
	
	value=[mobile,gmt_created,stat_id,company_id,flag]
	value_insert=[id,mobile,gmt_created,stat_id,company_id,flag]
	if (company_id=='None'):
		company_id='0'
	if (flag=='None'):
		flag='0'
	if (type_code=='None'):
		type_code='0'
	sql="select count(0) from sms_log where id='"+str(id)+"'"
	cursor_sms.execute(sql)
	oldid=cursor_sms.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		#sql="update score_change_details set company_id=%s,name=%s,rules_code=%s,score=%s,gmt_created=%s,gmt_modified=%s where old_id="+str(id)+""
		#cursor.execute(sql,value);
	else:
		updateflag="新增"
		sql="insert into sms_log(id,mobile,gmt_created,stat_id,company_id,flag,type_code)"
		sql=sql+"values("+id+",'"+mobile+"','"+gmt_created+"',"+stat_id+","+company_id+","+flag+",'"+type_code+"')"
		
		cursor_sms.execute(sql);
		conn_sms.commit()
	updatelastid(updatetablename,id)
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1

conn.close() 
cursor_sms.close()
cursor_mysql_sms.close()
os.system("python "+nowpath+"/sms_subscribe.py")
