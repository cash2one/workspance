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
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="company_yidong"
sql = "select com_id from comp_association where AssociatedName='允许发送短信' order by com_id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=0
for a in results:
	company_id=str(a[0])
	crm_service_code="10001000"
	sqlc="select gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end from crm_company_service where company_id="+company_id+" order by gmt_start desc"
	cursor.execute(sqlc)
	returnvalue=cursor.fetchone()
	if returnvalue:
		gmt_pre_start=returnvalue[0]
		gmt_pre_end=returnvalue[1]
		gmt_signed=returnvalue[2]
		gmt_start=returnvalue[3]
		gmt_end=returnvalue[4]
	apply_status="1"
	category="0"
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	value=[company_id,crm_service_code,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,apply_status,category,gmt_created,gmt_modified]
	sql="select company_id from crm_company_service where crm_service_code=10001000 and company_id="+company_id+""
	cursor.execute(sql)
	returnvalue=cursor.fetchone()
	if returnvalue:
		a=1
	else:
		sqla="insert into crm_company_service(company_id,crm_service_code,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,apply_status,category,gmt_created,gmt_modified)"
		sqla=sqla+" values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sqla,value);
	print 'suc'+str(i)
	i=i+1
conn.close() 
conn_rcu.close()
