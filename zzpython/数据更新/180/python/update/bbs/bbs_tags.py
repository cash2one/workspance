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

#---���ݿ����Ӻ�����
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
cursor2=conn_news.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------

updatetablename="bbs_tags"

print changezhongwen('����bbs_tags')

sql = "select id,old_forum_code,old_news_code,account from bbs_post order by id asc"
cursor.execute(sql)
results = cursor.fetchall()

value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	old_forum_code=str(a[1])
	old_news_code=str(a[2])
	account=str(a[3])
	#�µı�ǩID
	if (old_forum_code == None):
		sql="select id from bbs_tags where old_news_code='"+old_news_code+"'"
		cursor.execute(sql)
		newtags=cursor.fetchone()
	else:
		sql="select id from bbs_tags where old_forum_code='"+old_forum_code+"'"
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
		#sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_forum_id,old_forum_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count)"
		#sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		#cursor.execute(sql,value);
		updateflag="����"
		#values.append(value)
		sql="insert into bbs_post_relate_tags(bbs_post_id,bbs_tags_id)"
		sql=sql+"values(%s,%s)"
		cursor.execute(sql,value);
		print changezhongwen(updateflag+"�ɹ�"+str(i))
	
	i+=1
	
	
	# append����ֻ��value��Ϊһ��������ӵ�values�� 
	#values.extend(value) # extend�������value��Ԫ�飩ת����values���б�
	

	
print changezhongwen("���³ɹ�")


conn.close() 
conn_rcu.close()
conn_news.close()


