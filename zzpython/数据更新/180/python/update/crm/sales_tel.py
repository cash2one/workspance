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
updatetablename="sales_tel"


sql="select a.id,a.com_id,b.name,a.teldate,a.ContaType,a.contactType,a.contactKind,a.Detail,a.com_rank "
sql=sql + " ,a.contactNext_time,a.contactflag,a.contactinfo1,a.contactinfo2,a.contactinfo3,a.contactinfo4,a.contactinfo5,a.contactinfo6,a.solveType"
sql=sql + " ,a.Business,a.EmailNext_time,a.ActiveDo,datediff(s,'2005-01-01',teldate) as gmt_editdate from comp_tel as a left join users as b on a.personid=b.id where datediff(s,'2005-01-01',teldate)>(select updatetime from import_table where tablename='"+updatetablename+"') order by teldate asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[1])
	cs_account=str(a[2])
	gmt_next_visit_phone=str(a[9])
	gmt_next_visit_email=str(a[19])
	ContaType=str(a[4])
	if (ContaType!=None):
		is_visit_phone=ContaType[0:1]
		if (is_visit_phone=='1'):
			is_visit_phone=1
		else:
			is_visit_phone=0
		is_visit_email=ContaType[2:1]
		if (is_visit_email=='3'):
			is_visit_email=1
		else:
			is_visit_email=0
		is_visit_sms=ContaType[1:1]
		if (is_visit_sms=='2'):
			is_visit_sms=1
		else:
			is_visit_sms=0
		
	contactflag=str(a[10])
	call_type=0
	if (contactflag=='4'):
		call_type=1
	if (contactflag=='1'):
		call_type=0
	if (contactflag=='2'):
		call_type=2
	if (contactflag=='3'):
		call_type=3
	
	contacttype=str(a[5])
	situation=2
	if (contacttype=='12'):
		situation=0
	if (contacttype=='13'):
		situation=1
	if (contacttype=='14'):
		situation=2
	
	ActiveDo=str(a[20])
	operation=0
	if (ActiveDo=='1'):
		operation=3
	if (ActiveDo=='2'):
		operation=2
	if (ActiveDo=='3'):
		operation=1
	if (ActiveDo=='4'):
		operation=0
	
	contactInfo1=str(a[10])
	operation_details=changezhongwen(contactInfo1)
	
	Business=str(a[18])
	transaction=3
	if (Business=='1'):
		transaction=0
	if (Business=='2'):
		transaction=1
	if (Business=='3'):
		transaction=2
	if (Business=='4'):
		transaction=3
	transaction_details=changezhongwen(a[11])
	
	feedback=changezhongwen(a[12])
	suggestion=changezhongwen(a[13])
	
	solveType=str(a[17])
	issue_status=solveType
	
	issue_details=changezhongwen(a[14])
	visit_target=changezhongwen(a[15])
	star=str(a[8])
	remark=changezhongwen(a[7])
	
	#id, cs_account, company_id, gmt_next_visit_phone, gmt_next_visit_email, is_visit_phone, is_visit_email, is_visit_sms, 
	#call_type, situation, operation, operation_details, transaction, transaction_details, feedback, suggestion, 
	#issue_status, issue_details, visit_target, star, remark, gmt_created, gmt_modified, old_id
	
	gmt_created=str(a[3])
	gmt_modified=datetime.datetime.now()
	old_id=str(a[0])
	
	
	value=[cs_account, company_id, gmt_next_visit_phone, gmt_next_visit_email, is_visit_phone, is_visit_email, is_visit_sms, call_type, situation, operation, operation_details, transaction, transaction_details, feedback, suggestion, issue_status, issue_details, visit_target, star, remark, gmt_created, gmt_modified, old_id]
	valueupdate=[cs_account, company_id, gmt_next_visit_phone, gmt_next_visit_email, is_visit_phone, is_visit_email, is_visit_sms, call_type, situation, operation, operation_details, transaction, transaction_details, feedback, suggestion, issue_status, issue_details, visit_target, star, remark, gmt_created, gmt_modified, old_id]
	
	#-判断是否已经导
	sql="select count(0) from crm_cs_log where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update crm_cs_log set cs_account=%s, company_id=%s, gmt_next_visit_phone=%s, gmt_next_visit_email=%s, is_visit_phone=%s, is_visit_email=%s, is_visit_sms=%s, call_type=%s, situation=%s, operation=%s, operation_details=%s, transaction=%s, transaction_details=%s, feedback=%s, suggestion=%s, issue_status=%s, issue_details=%s, visit_target=%s, star=%s, remark=%s, gmt_created=%s, gmt_modified=%s where old_id=%s"
		cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into crm_cs_log(cs_account, company_id, gmt_next_visit_phone, gmt_next_visit_email, is_visit_phone, is_visit_email, is_visit_sms, call_type, situation, operation, operation_details, transaction, transaction_details, feedback, suggestion, issue_status, issue_details, visit_target, star, remark, gmt_created, gmt_modified, old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[21])
	i=i+1
	
try:
	#读取最新更新时间
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor1.execute(sql)
		conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e
#print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate crmcompany")
conn.close() 
conn_rcu.close()
os.system("python "+nowpath+"/sales_tel_added.py")
