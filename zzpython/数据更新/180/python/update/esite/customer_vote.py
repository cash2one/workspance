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
		return 'admin'
	else:
		username=changezhongwen(str(newcode[0]))
		email=changezhongwen(str(newcode[1]))
		if (username == 'None' or username==''):
			username=email
		return username

sql="select tocom_id,fromcom_id,R_type,R_Auditing,R_Auditing_time,R_time,R_remark,R_explain,R_explainTime,R_check,id from comp_remark  order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	from_company_id=str(a[1])
	from_account=getusername(from_company_id)
	to_company_id=str(a[0])
	R_type=str(a[2])
	if (R_type=='2'):
		status=0
	if (R_type=='0'):
		status=1
	if (R_type=='-2'):
		status=2
	print status
	content=changezhongwen(a[6])
	reply_content=changezhongwen(a[7])
	check_status=str(a[9])
	if (check_status=='True'):
		check_status=1
	else:
		check_status=0
	
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	old_id=str(a[0])
	value=[from_company_id,from_account,to_company_id,status,content,reply_content,check_status,gmt_created,gmt_modified,old_id]

	#-判断是否已经导
	sql="select count(0) from credit_customer_vote where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update credit_customer_vote set from_company_id=%s,from_account=%s,to_company_id=%s,status=%s,content=%s,reply_content=%s,check_status=%s,gmt_created=%s,gmt_modified=%s where old_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		sql="insert into credit_customer_vote(from_company_id,from_account,to_company_id,status,content,reply_content,check_status,gmt_created,gmt_modified,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[5])
	i=i+1
	
try:
	print changezhongwen("更新成功")
except Exception, e:
	print e
conn.close() 
conn_rcu.close()

