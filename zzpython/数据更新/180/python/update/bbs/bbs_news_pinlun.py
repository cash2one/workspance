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
updatetablename="bbs_pinlun"

print changezhongwen('����bbs_pinlun')
sql = "select td_id,0,td_title,cast(td_content AS TEXT) AS td_content,td_sort,td_time,fdate,td_flag,td_count,jd,keywords from baojia_pinglun where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by td_id asc"
cursor2.execute(sql)
results = cursor2.fetchall()

sql="update import_table set updatetime=(select convert(bigint,max(gmt_editdate)) from baojia_pinglun) where tablename='"+updatetablename+"'"
cursor2.execute(sql)
conn_news.commit()

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
		
	if (FBackdate == None):
		FBackdate=Fdate
	com_email='admin'
	#------------------------------
	company_id=0
	Ftitle = Ftitle.decode('GB18030').encode('utf-8')
	Fcontent= Fcontent.decode('GB18030').encode('utf-8')
	gmt_created=Fdate
	gmt_modified=datetime.datetime.now()
	
	#�ظ���
	reply_count=0
	#---------------------------
	#-------------------------------------
	value=[company_id,com_email,Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,id,Ftype,isNewandHot,isNewandHot,gmt_created,gmt_modified,Fcheck,reply_count,tags]
	valueupdate=[Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,Ftype,isNewandHot,gmt_modified,Fcheck,reply_count,tags]
	#-�ж��Ƿ��Ѿ���
	sql="select count(0) from bbs_post where old_pinlun_id="+str(id)
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_news_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s,tags=%s where old_pinlun_id="+str(id)
		cursor.execute(sql,valueupdate);
		updateflag="����"
		valueupdates.append(valueupdate)
	else:
		sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_pinlun_id,old_news_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count,tags)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="����"
		values.append(value)
	print changezhongwen(updateflag+"�ɹ�"+str(i))
	updatelastid(updatetablename,id)
	i+=1
	
	
	# append����ֻ��value��Ϊһ��������ӵ�values�� 
	#values.extend(value) # extend�������value��Ԫ�飩ת����values���б�
try:
	#��ȡ���¸���ʱ��
	
	updatelastid(updatetablename,'0')
	print changezhongwen("�ɹ�")
except Exception, e:
	print e



conn.close() 
conn_rcu.close()
conn_news.close()
os.system("python "+nowpath+"/bbs_tags.py")	
