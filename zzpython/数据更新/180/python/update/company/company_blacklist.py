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
updatetablename="company_blacklist"
sql = "select id,com_id from comp_blacklist order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=0
for a in results:
	id=str(a[0])
	company_id=a[1]
	value=[1,company_id]
	sql="update company set is_block=%s where id=%s"
	cursor.execute(sql,value);
	print 'suc'+str(i)
	i=i+1
conn.close() 
conn_rcu.close()
conn_others.close()
