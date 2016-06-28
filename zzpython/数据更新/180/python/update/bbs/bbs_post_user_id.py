#!/usr/bin/env python   
#coding=utf-8   
######################################   
#   
# @author migle   
# @date 2010-01-17   
#   
######################################   
#   
# MySQLdb 查询   
#   
#######################################   
  
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


sql = "select id,account from bbs_user_profiler order by id asc"
cursor.execute(sql)
results = cursor.fetchall()

value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	account=str(a[1])
	
	sql="update bbs_post set bbs_user_profiler_id="+id+" where account='"+account+"'"
	cursor.execute(sql)
	i+=1
	


conn.close() 
conn_rcu.close()
conn_others.close()
os.system("python "+nowpath+"/bbs_news.py")	
