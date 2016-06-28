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
cursor_test=conn_test.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
sql="delete from local_outlogin";
cursor_test.execute(sql);
conn_test.commit();
updatetablename="java_login"
sql = "select "
sql = sql +" c.company_id,c.num_login, c.gmt_last_login"
sql = sql +" from company_account as c where DATEDIFF(CURDATE(),c.gmt_last_login)<=15 order by c.id asc"
cursor.execute(sql)
results = cursor.fetchall()
value=[]
i=0
for a in results:
	com_id=str(a[0])
	num_login=str(a[1])
	gmt_last_login=str(a[2])
	if (gmt_last_login=='None'):
		gmt_last_login='1900-1-1'
	sql="select count(0) from local_outlogin where company_id="+str(com_id)+""
	cursor_test.execute(sql)
	oldid=cursor_test.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		
	else:
		updateflag="新增"
		sql="insert into local_outlogin(company_id, num_login, gmt_last_login)"
		sql=sql+" values("+com_id+", '"+num_login+"', '"+gmt_last_login+"')"
		cursor_test.execute(sql);
		conn_test.commit()
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1

conn.close() 
cursor_test.close()
os.system("python "+os.path.abspath('.')+"/login_more.py")