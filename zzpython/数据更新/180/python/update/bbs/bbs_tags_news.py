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

execfile("/usr/tools/connbbs.py") 

conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()

conn2=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu_news',charset=None)
cursor2=conn2.cursor()


sql = "select id,old_news_code from bbs_post where old_news_code>0 order by id asc"
cursor.execute(sql)
results = cursor.fetchall()

value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	old_news_code=str(a[1])
	
	#新的标签ID
	sql="select id from bbs_tags where old_news_code='"+old_news_code+"'"
	cursor.execute(sql)
	newtags=cursor.fetchone()
	if (newtags == None):
		newtagsid="0"
	else:
		newtagsid=str(newtags[0])
	#---------------------------
	#-------------------------------------
	value=[id,newtagsid]
	#-判断是否已经导
	sql="select count(0) from bbs_post_relate_tags where bbs_post_id="+id+" and bbs_tags_id="+newtagsid+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag=""
		valueupdates.append(valueupdate)
	else:
		updateflag="新增"
		values.append(value)
	#print updateflag+"成功"+str(i)
	
	i+=1
	
	
	# append方法只把value作为一个整体添加到values中 
	#values.extend(value) # extend方法会把value（元组）转换成values（列表）
try:
	
	#新增数据
	sql="insert into bbs_post_relate_tags(bbs_post_id,bbs_tags_id)"
	sql=sql+"values(%s,%s)"
	cursor.executemany(sql,values);
	
	print "更新成功"
except Exception, e:
	print e


conn.close() 
conn1.close()
conn2.close()
