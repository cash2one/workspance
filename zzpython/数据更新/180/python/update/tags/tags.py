#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
from django.utils.http import urlquote

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor_tags=conn_tags.cursor()
cursor_mysql_tags=conn_mysql_tags.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="tags"


sql="select id,kname,kcount,ksearchcount"
sql=sql+" from tags_info order by id asc"
cursor_tags.execute(sql)
results = cursor_tags.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	id=a[0]
	tags=changezhongwen(a[1])
	tags_encode=urlquote(tags)
	click_count=a[2]
	search_count=a[3]
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	old_id=str(a[0])
	#title, content, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account, batch_send_type, export_status, export_person, is_rubbish, is_viewed, is_sender_del, send_time, gmt_created, gmt_modified, is_replyed, is_receiver_del
	value=[tags, tags_encode, click_count, search_count, gmt_created, gmt_modified,old_id]
	#print value
	#-判断是否已经导
	tags=tags.replace('\\','.')
	print id
	sql="select count(0) from tags where tags='"+str(tags)+"'"
	cursor_mysql_tags.execute(sql)
	oldid=cursor_mysql_tags.fetchone()
	oldidcount=oldid[0]
	valueu=[id,tags]
	if (oldidcount>0):
		sql="update tags set old_id=%s where tags=%s"
		cursor_mysql_tags.execute(sql,valueu);
		updateflag="更新"
	else:
		sql="insert into tags(tags, tags_encode, click_count, search_count, gmt_created, gmt_modified,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s)"
		cursor_mysql_tags.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	i=i+1
	
try:
	#读取最新更新时间
	print changezhongwen("更新成功")
except Exception, e:
	print e
#print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate crmcompany")
conn.close() 
conn_tags.close()

