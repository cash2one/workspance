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
updatetablename="crm_assign"

def getsalesStat(com_id):
	if (str(com_id) == 'None' or com_id==0):
		return '0'
	else:
		sql="select Com_ContactLastTime,contactnext_time,EmailNext_Time from crm_service where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return None
		else:
			return newcode

sql="select top 10000 a.com_id,b.name,a.id,convert(bigint,a.gmt_editdate) as gmt_editdate from Crm_ServiceAssign as a left join users as b on a.personid=b.id "
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	cs_account=str(a[1])
	
	salesStat=getsalesStat(company_id)
	if (salesStat!=None):
		gmt_visit=salesStat[0]
		gmt_next_visit_phone=salesStat[1]
		gmt_next_visit_email=salesStat[2]
	else:
		gmt_visit=''
		gmt_next_visit_phone=''
		gmt_next_visit_email=''
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	old_id=str(a[2])
	value=[company_id,cs_account,gmt_created,gmt_modified,gmt_visit,gmt_next_visit_phone,gmt_next_visit_email,old_id]
	valueupdate=[company_id,cs_account,gmt_created,gmt_modified,gmt_visit,gmt_next_visit_phone,gmt_next_visit_email,old_id]
	
	#-判断是否已经导
	sql="select count(0) from crm_cs where company_id="+str(company_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update crm_cs set company_id=%s,cs_account=%s,gmt_created=%s,gmt_modified=%s,gmt_visit=%s,gmt_next_visit_phone=%s,gmt_next_visit_email=%s where old_id=%s"
		cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into crm_cs(company_id,cs_account,gmt_created,gmt_modified,gmt_visit,gmt_next_visit_phone,gmt_next_visit_email,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[3])
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
#print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate crmcompany")
conn.close() 
conn_rcu.close()
os.system("python "+nowpath+"/leaveword.py")
