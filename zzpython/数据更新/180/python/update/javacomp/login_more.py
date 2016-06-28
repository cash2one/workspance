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
sql="delete from analysis_login";
cursor_test.execute(sql);
conn_test.commit();
updatetablename="analysis_login"
sql = "select "
sql = sql +" company_id,gmt_target, login_count"
sql = sql +" from analysis_login where DATEDIFF(CURDATE(),gmt_target)<=15 order by id asc"
cursor.execute(sql)
results = cursor.fetchall()
value=[]
i=0
for a in results:
	company_id=str(a[0])
	gmt_target=str(a[1])
	login_count=str(a[2])
	if (gmt_target=='None'):
		gmt_target='1900-1-1'
	sql="select count(0) from analysis_login where company_id="+str(company_id)+" and gmt_target='"+gmt_target+"'"
	cursor_test.execute(sql)
	oldid=cursor_test.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		
	else:
		updateflag="新增"
		sql="insert into analysis_login(company_id, gmt_target, login_count)"
		sql=sql+" values("+company_id+", '"+gmt_target+"', "+login_count+")"
		cursor_test.execute(sql);
		conn_test.commit()
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1

conn.close() 
cursor_test.close()