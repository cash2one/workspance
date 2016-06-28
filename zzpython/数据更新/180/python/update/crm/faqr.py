#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
import random

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="faqr"

sql="select com_id,vip_datefrom,vip_dateto from comp_info where com_fqr=1"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	crm_service_code='10001001'
	apply_group=random.randrange(0,1000000000000000)
	signed_type=0
	gmt_end=str(a[2])
	gmt_signed=str(a[1])
	gmt_start=gmt_signed
	apply_status=1
	category=0
	membership_code=''
	remark=''
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	value=[company_id,crm_service_code,apply_group,signed_type,gmt_signed,gmt_start,gmt_end,apply_status,category,membership_code,remark,gmt_created,gmt_modified]
	valueupdate=[company_id,crm_service_code,apply_group,signed_type,gmt_signed,gmt_start,gmt_end,apply_status,category,membership_code,remark,gmt_created,gmt_modified]
	
	#-判断是否已经导
	sql="select count(0) from crm_company_service where company_id="+str(company_id)+" and crm_service_code='"+crm_service_code+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	valuea=[apply_group,0]
	if (oldidcount>0):
		#sql="update crm_company_service set company_id=%s,crm_service_code=%s,signed_type=%s,gmt_signed=%s,gmt_start=%s,gmt_end=%s,apply_status=%s,category=%s,membership_code=%s,remark=%s,gmt_created=%s,gmt_modified=%s where old_id=%s"
		#cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into crm_service_apply(apply_group,amount) values(%s,%s)"
		cursor.execute(sql,valuea);
		sql="insert into crm_company_service(company_id,crm_service_code,apply_group,signed_type,gmt_signed,gmt_start,gmt_end,apply_status,category,membership_code,remark,gmt_created,gmt_modified)"
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

