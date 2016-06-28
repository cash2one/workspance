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

sql="select com_id,c_type,c_name,c_fromdate,c_enddate"
sql+=",c_code,c_institution,c_institution_tel,c_institution_website,c_picaddress"
sql+=",c_intro,c_check,id from comp_Certificate  order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	account=getusername(company_id)
	c_type=str(a[1])
	if (c_type=='10'):
		category_code='10401000'
	if (c_type=='11'):
		category_code='10401001'
	if (c_type=='12'):
		category_code='10401002'
	if (c_type=='13'):
		category_code='10401003'
	file_name=changezhongwen(a[2])
	start_time=str(a[3])
	end_time=str(a[4])
	file_number=changezhongwen(a[5])
	organization=changezhongwen(a[6])
	tel=changezhongwen(a[7])
	website=changezhongwen(a[8])
	pic_name='http://img.zz91.com/'+str(a[9])
	introduction=changezhongwen(a[10])
	check_status=str(a[11])
	if (check_status=='True'):
		check_status=1
	else:
		check_status=0
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	old_id=str(a[12])
	value=[company_id, account, category_code, file_name, start_time, end_time, file_number, organization, tel, website, pic_name, introduction, check_status, gmt_created, gmt_modified,old_id]

	#-判断是否已经导
	sql="select count(0) from credit_file where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update credit_file set company_id=%s, account=%s, category_code=%s, file_name=%s, start_time=%s, end_time=%s, file_number=%s, organization=%s, tel=%s, website=%s, pic_name=%s, introduction=%s, check_status=%s, gmt_created=%s, gmt_modified=%s where old_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		sql="insert into credit_file(company_id, account, category_code, file_name, start_time, end_time, file_number, organization, tel, website, pic_name, introduction, check_status, gmt_created, gmt_modified,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
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

