#!/usr/bin/env python   
#coding=utf-8   
######################################   
#   
# @author migle   
# @date 2010-01-17   
#   
######################################   
#   
# MySQLdb ��ѯ   
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
	
	#�µı�ǩID
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
	#-�ж��Ƿ��Ѿ���
	sql="select count(0) from bbs_post_relate_tags where bbs_post_id="+id+" and bbs_tags_id="+newtagsid+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag=""
		valueupdates.append(valueupdate)
	else:
		updateflag="����"
		values.append(value)
	#print updateflag+"�ɹ�"+str(i)
	
	i+=1
	
	
	# append����ֻ��value��Ϊһ��������ӵ�values�� 
	#values.extend(value) # extend�������value��Ԫ�飩ת����values���б�
try:
	
	#��������
	sql="insert into bbs_post_relate_tags(bbs_post_id,bbs_tags_id)"
	sql=sql+"values(%s,%s)"
	cursor.executemany(sql,values);
	
	print "���³ɹ�"
except Exception, e:
	print e


conn.close() 
conn1.close()
conn2.close()
