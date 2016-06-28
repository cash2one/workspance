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
cursor_mysql_reborn=conn_mysql_reborn.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------



#convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')
updatetablename="sms_subscribe"


sql = "select id, company_id, account,  mobile,   gmt_created, gmt_modified,company_name,contact_person from sms_subscribe where id>"+getlastid(updatetablename)+" order by id asc"
cursor_mysql_reborn.execute(sql)
results = cursor_mysql_reborn.fetchall()

value=[]
i=0
for a in results:
	id=str(a[0])
	company_id='0'
	account=str(a[2])
	
	mobile=str(a[3])
	
	gmt_created=str(a[4])
	gmt_modified=str(a[5])
	company_name=''
	contact_person=''
	if(a[6]!=None):
		company_name=str(a[6]).encode('gb2312','ignore').replace("'","")
	if(a[7]!=None):
		contact_person=str(a[7]).encode('gb2312','ignore').replace("'","")

	#print company_name + contact_person
	
	#value=[id, company_id, account,  mobile,   gmt_created, gmt_modified,company_name,contact_person]
	
	sql="select count(0) from sms_subscribe where id='"+str(id)+"'"
	cursor_sms.execute(sql)
	oldid=cursor_sms.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		#sql="update score_change_details set company_id=%s,name=%s,rules_code=%s,score=%s,gmt_created=%s,gmt_modified=%s where old_id="+str(id)+""
		#cursor.execute(sql,value);
	else:
		updateflag="新增"
		sql="insert into sms_subscribe(id, company_id, account, mobile, gmt_created, gmt_modified,company_name,contact_person)"
		sql=sql+"values("+id+", "+company_id+", '"+account+"', '"+mobile+"', '"+gmt_created+"', '"+gmt_modified+"','"+company_name+"','"+contact_person+"')"
		cursor_sms.execute(sql);
		conn_sms.commit()
	updatelastid(updatetablename,id)
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1

conn.close() 
cursor_sms.close()
cursor_mysql_reborn.close()
os.system("python "+nowpath+"/sms_dingzhi.py")
