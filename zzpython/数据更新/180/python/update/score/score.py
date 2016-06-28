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
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------


def getrules_code(code):
	sql="select rules_code,name from score_exchange_rules where old_rules_code='"+str(code)+"'"
	cursor.execute(sql)
	newcodeid=cursor.fetchone()
	if (newcodeid == None):
		return [0,'']
	else:
		return newcodeid
#convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')
updatetablename="score"


sql = "select company_id,score_type_id,exchange_type_id,trade_url,score,is_exchanged,gmt_created,gmt_modified,id from score_system order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()

sql="update import_table set updatetime=(select convert(bigint,max(gmt_editdate)) from score_system) where tablename='"+updatetablename+"'"
cursor1.execute(sql)
conn_rcu.commit()

value=[]
i=0
for a in results:
	company_id=str(a[0])
	score_type_id=a[1]
	exchange_type_id=str(a[2])
	trade_url=str(a[3])
	score=str(a[4])
	is_exchanged=str(a[5])
	gmt_created=str(a[6])
	gmt_modified=str(a[7])
	id=str(a[8])
	#gmt_modified=datetime.datetime.now()
	if (score_type_id=='35' or score_type_id=='36' ):
		score_type_id='26'
	
	rules_codevalue=getrules_code(score_type_id)
	name=rules_codevalue[1]
	rules_code=rules_codevalue[0]
	value=[company_id,name,rules_code,score,gmt_created,gmt_modified]
	value_insert=[company_id,name,rules_code,score,gmt_created,gmt_modified,id]
	
	
	sql="select count(0) from score_change_details where old_id='"+str(id)+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		#sql="update score_change_details set company_id=%s,name=%s,rules_code=%s,score=%s,gmt_created=%s,gmt_modified=%s where old_id="+str(id)+""
		#cursor.execute(sql,value);
	else:
		updateflag="新增"
		sql="insert into  score_change_details(company_id,name,rules_code,score,gmt_created,gmt_modified,old_id)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value_insert);
	updatelastid(updatetablename,id)
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1

conn.close() 
conn_rcu.close()
conn_others.close()
