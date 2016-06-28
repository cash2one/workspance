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
updatetablename="sales_tel_added"

sql="select a.id,b.name,a.telid,a.teldate,a.telcontent,datediff(s,'2005-01-01',teldate) as gmt_editdate from crm_tel_renew as a left join users as b on a.personid=b.id where datediff(s,'2005-01-01',a.teldate)>(select updatetime from import_table where tablename='"+updatetablename+"') order by teldate asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	crm_cs_log_id=str(a[2])
	cs_account=str(a[1])
	content=changezhongwen(str(a[4]))
	
	gmt_created=str(a[3])
	gmt_modified=datetime.datetime.now()
	old_id=str(a[0])
	value=[crm_cs_log_id,cs_account,content,gmt_created,gmt_modified,old_id]
	valueupdate=[crm_cs_log_id,cs_account,content,gmt_created,gmt_modified,old_id]
	
	#-判断是否已经导
	sql="select count(0) from crm_cs_log_added where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update crm_cs_log_added set crm_cs_log_id=%s,cs_account=%s,content=%s,gmt_created=%s,gmt_modified=%s where old_id=%s"
		cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into crm_cs_log_added(crm_cs_log_id,cs_account,content,gmt_created,gmt_modified,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[5])
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

