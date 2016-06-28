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
cursor_test=conn_test.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="company_service"
sql = "select id,company_id,crm_service_code,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,membership_code,apply_status,apply_group"
sql = sql +" from crm_company_service"

cursor.execute(sql)
results = cursor.fetchall()
value=[]
i=0
for a in results:
	id=str(a[0])
	company_id=str(a[1])
	crm_service_code=str(a[2])
	gmt_pre_start=str(a[3])
	gmt_pre_end=str(a[4])
	gmt_signed=str(a[5])
	gmt_start=str(a[6])
	gmt_end=str(a[7])
	membership_code=str(a[8])
	apply_status=str(a[9])
	apply_group=str(a[10])

	sql="select count(0) from company_service where id="+str(id)+""
	cursor_test.execute(sql)
	oldid=cursor_test.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		sql="update company_service set company_id="+company_id+",crm_service_code='"+crm_service_code+"',gmt_pre_start='"+gmt_pre_start+"',gmt_pre_end='"+gmt_pre_end+"',gmt_signed='"+gmt_signed+"',gmt_start='"+gmt_start+"',gmt_end='"+gmt_end+"',membership_code='"+membership_code+"',apply_status='"+apply_status+"',apply_group='"+apply_group+"'"
		sql=sql+" where id="+str(id)+""
		cursor_test.execute(sql);
		conn_test.commit()
	else:
		updateflag="新增"
		sql="insert into company_service(id,company_id,crm_service_code,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,membership_code,apply_status,apply_group)"
		sql=sql+" values("+id+","+company_id+",'"+crm_service_code+"','"+gmt_pre_start+"','"+gmt_pre_end+"','"+gmt_signed+"','"+gmt_start+"','"+gmt_end+"','"+membership_code+"','"+apply_status+"','"+apply_group+"')"
		cursor_test.execute(sql);
		conn_test.commit()
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1

conn.close() 
cursor_test.close()
os.system("python "+os.path.abspath('.')+"/login.py")