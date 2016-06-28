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

def getzstYear(com_id):
	sql="select sum(yearNum) from comp_continue  where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		return str(newcode[0])

sql="select com_id,Evaluate_score,Certificate_score,remark_score,vip_score,cxcount from comp_score"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	account=getusername(company_id)
	related_id=1
	typelist=[['company_',1],['customer_',3],['credit_file',2],['service_zst_year',0]]
	for b in typelist:
		operation_key=b[0]
		integral=str(a[b[1]])
		if (b[1]==0):
			integral=getzstYear(company_id)
		if (integral=='None' or integral=='null' or integral==''):
			integral='0'
		print integral
		gmt_created=datetime.datetime.now()
		gmt_modified=datetime.datetime.now()
		value=[company_id, account, operation_key, integral, gmt_created, gmt_modified]
		valueu=[integral, gmt_modified,company_id, account]

		#-判断是否已经导
		sql="select count(0) from credit_integral_details where company_id="+str(company_id)+" and operation_key='"+str(operation_key)+"'"
		cursor.execute(sql)
		oldid=cursor.fetchone()
		oldidcount=oldid[0]
		if (oldidcount>0):
			#sql="update credit_integral_details set integral=%s, gmt_modified=%s where company_id=%s and operation_key=%s"
			#cursor.execute(sql,valueu);
			updateflag="更新"
		else:
			sql="insert into credit_integral_details(company_id, account, operation_key, integral, gmt_created, gmt_modified)"
			sql=sql + "  values(%s,%s,%s,%s,%s,%s)"
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

