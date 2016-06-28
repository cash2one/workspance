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
updatetablename="zst_info"

def getmembership_code(com_id):
	sql="select id from comp_zstinfo where com_id="+str(com_id)+" and com_check=1"
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '10051000'
	else:
		sql="select PPTGrade from comp_ppt where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '10051001'
		else:
			PPTGrade=str(newcode[0])
			if(PPTGrade == '0'):
				return '10051001'
			elif(PPTGrade == '1'):
				return '100510021000'
			elif(PPTGrade == '2'):
				return '100510021001'
			elif(PPTGrade == '3'):
				return '100510021002'
			else:
				return '10051001'

sql="select com_id,firstDateFrom,continueDateFrom,currentContinueDateFrom,dateTo,remark,id,whichContinue,convert(bigint,continueDateFrom) as a,convert(bigint,currentContinueDateFrom) as b from comp_Continue"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	crm_service_code='1000'
	whichContinue=a[7]
	if (whichContinue>0):
		signed_type=1
	else:
		signed_type=0
	gmt_pre_start=str(a[1])
	gmt_pre_end=str(a[2])
	gmt_end=str(a[4])
	
		
	gmt_signed=str(a[3])
	
	fa=a[8]
	fb=a[9]
	if (fb>fa):
		gmt_start=gmt_signed
	else:
		gmt_start=gmt_pre_end
		
	if (gmt_pre_end=='None'):
		gmt_start=gmt_pre_start
		
	if (gmt_signed=='None'):
		gmt_signed=gmt_pre_start
		
	apply_status=1
	category=0
	membership_code=getmembership_code(company_id)
	remark=changezhongwen(a[5])
	old_id=str(a[6])
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	value=[company_id,crm_service_code,signed_type,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,apply_status,category,membership_code,remark,gmt_created,gmt_modified,old_id]
	valueupdate=[company_id,crm_service_code,signed_type,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,apply_status,category,membership_code,remark,gmt_created,gmt_modified,old_id]
	
	#-判断是否已经导
	sql="select count(0) from crm_company_service where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update crm_company_service set company_id=%s,crm_service_code=%s,signed_type=%s,gmt_pre_start=%s,gmt_pre_end=%s,gmt_signed=%s,gmt_start=%s,gmt_end=%s,apply_status=%s,category=%s,membership_code=%s,remark=%s,gmt_created=%s,gmt_modified=%s where old_id=%s"
		cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into crm_company_service(company_id,crm_service_code,signed_type,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,apply_status,category,membership_code,remark,gmt_created,gmt_modified,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	i=i+1
	
try:
	#读取最新更新时间
	#if (editdate!=0):
		#sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		#cursor1.execute(sql)
		#conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e

conn.close() 
conn_rcu.close()
os.system("python "+nowpath+"/company_profile.py")
