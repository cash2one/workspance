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
updatetablename="question2007"
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

sql="select id,fromcom_id,tocom_id,mtitle,mcontent"
sql=sql+",mdate,mwaste,backflag,backid,mback"
sql=sql+",fromaction,fromactionid,frompdtid,sendflag,viewflag"
sql=sql+",delflag,outflag,dooutuser from comp_question_2007 where id>(select lastid from import_table where tablename='"+updatetablename+"') order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	title=changezhongwen(a[3])
	content=changezhongwen(a[4])
	conversation_group=None
	group_id=0
	fromaction=str(a[10])
	if (fromaction=='1'):
		be_inquired_type='0'
	else:
		be_inquired_type='1'
	be_inquired_id=str(a[11])
	backflag=str(a[7])
	backid=str(a[8])
	if (backflag=='1'):
		be_inquired_type='2'
		be_inquired_id=backid
	
	inquired_type=str(a[13])
	inquired_id=str(a[12])
	sender_account=getusername(str(a[1]))
	receiver_account=getusername(str(a[2]))
	if (inquired_type!='0'):
		batch_send_type=1
	else:
		batch_send_type=0
	export_status=str(a[16])
	export_person=str(a[17])
	is_rubbish=str(a[6])
	is_viewed=str(a[14])
	is_sender_del=str(a[15])
	send_time=str(a[5])
	is_replyed=str(a[9])
	is_receiver_del=0
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	old_id=str(a[0])
	#title, content, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account, batch_send_type, export_status, export_person, is_rubbish, is_viewed, is_sender_del, send_time, gmt_created, gmt_modified, is_replyed, is_receiver_del
	value=[title, content, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account, batch_send_type, export_status, export_person, is_rubbish, is_viewed, is_sender_del, send_time, gmt_created, gmt_modified, is_replyed, is_receiver_del,old_id]

	#-判断是否已经导
	sql="select count(0) from inquiry where old_id="+str(old_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		updateflag="更新"
	else:
		sql="insert into inquiry(title, content, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account, batch_send_type, export_status, export_person, is_rubbish, is_viewed, is_sender_del, send_time, gmt_created, gmt_modified, is_replyed, is_receiver_del,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[0])
	i=i+1
	
try:
	#读取最新更新时间
	if (editdate!=0):
		sql="update import_table set lastid="+editdate+" where tablename='"+updatetablename+"'"
		cursor1.execute(sql)
		conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e
#print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate crmcompany")
conn.close() 
conn_rcu.close()

