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

#---���ݿ����Ӻ�����
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
cursor2=conn_news.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
#and td_id>(select lastid from import_table where tablename='"+updatetablename+"')
updatetablename="bbs_guanzhu"

print changezhongwen('����bbs_guanzhu')
sql = "select td_id,0,td_title,cast(td_content AS TEXT) AS td_content,td_sort,td_time,fdate,td_flag,td_count,1,keywords,convert(bigint,gmt_editdate) from guanzhu where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
cursor2.execute(sql)
results = cursor2.fetchall()
editdate=0
value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	com_id=str(a[1])
	Ftitle=str(a[2])
	Fcontent=str(a[3])
	Ftype=str(a[4])
	Fdate=str(a[5])
	FBackdate=str(a[6])
	Fcheck=str(a[7])
	Fcount=str(a[8])
	is_hot_post=a[9]
	tags=changezhongwen(a[10])
	if (is_hot_post == None):
		isNewandHot = 0
	else:
		isNewandHot=is_hot_post
	
	com_email='admin'
	#------------------------------
	company_id=0
	Ftitle = changezhongwen(Ftitle)
	Fcontent= changezhongwen(Fcontent)
	gmt_created=Fdate
	gmt_modified=datetime.datetime.now()
	
	if (FBackdate == None):
		FBackdate=Fdate
	#�ظ���
	reply_count=0
	#---------------------------
	#-------------------------------------
	value=[company_id,com_email,Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,id,Ftype,isNewandHot,isNewandHot,gmt_created,gmt_modified,Fcheck,reply_count,tags]
	valueupdate=[Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,Ftype,isNewandHot,gmt_modified,Fcheck,reply_count,tags]
	#-�ж��Ƿ��Ѿ���
	sql="select count(0) from bbs_post where old_guanzhu_id="+str(id)
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_news_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s,tags=%s where old_guanzhu_id="+str(id)
		cursor.execute(sql,valueupdate);
		updateflag="����"
		valueupdates.append(valueupdate)
	else:
		sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_guanzhu_id,old_news_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count,tags)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="����"
		values.append(value)
	print changezhongwen(updateflag+"�ɹ�"+str(i))
	updatelastid(updatetablename,id)
	editdate=str(a[11])
	i+=1
	
	
	# append����ֻ��value��Ϊһ��������ӵ�values�� 
	#values.extend(value) # extend�������value��Ԫ�飩ת����values���б�
try:
	#��ȡ���¸���ʱ��
	
	updatelastid(updatetablename,'0')
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor2.execute(sql)
		conn_news.commit()
	print changezhongwen("���³ɹ�")
except Exception, e:
	print e


conn.close() 
conn_rcu.close()
conn_news.close()
#os.system("python "+nowpath+"/bbs_news_pinlun.py")
os.system("python "+nowpath+"/bbs_baojia.py")
