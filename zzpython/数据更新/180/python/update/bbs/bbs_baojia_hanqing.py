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
updatetablename="bbs_baojia_hanqing"

def gettype_id(id):
	sql="select m_id from trades_no where td_id="+str(id)+""
	cursor2.execute(sql)
	newtags=cursor2.fetchone()
	if (newtags == None):
		newtagsid="0"
	else:
		newtagsid=str(newtags[0])
	nid='220'
	if (newtagsid=='6'):
		nid= '216'
	if (newtagsid=='7'):
		nid= '217'
	if (newtagsid=='8'):
		nid= '220'
	if (newtagsid=='0'):
		nid= '220'
	return nid
		

print changezhongwen('����'+updatetablename)
#and td_id>"+getlastid(updatetablename)+" 
sql = "select td_id,0,td_title,cast(td_content AS TEXT) AS td_content,td_sort,td_time,fdate,td_flag,td_count,keywords,seoDesc,convert(bigint,gmt_editdate) from trades where td_sort like '29%' and convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
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
	Ftitle=changezhongwen(str(a[2]))
	Fcontent=changezhongwen(str(a[3]))
	Ftype=str(a[4])
	Fdate=str(a[5])
	FBackdate=str(a[6])
	Fcheck=str(a[7])
	Fcount=str(a[8])
	is_hot_post=a[9]
	keywords=changezhongwen(str(a[9]))
	seoDesc=changezhongwen(str(a[10]))
	if (is_hot_post == None):
		isNewandHot = 0
	else:
		isNewandHot=is_hot_post
		
	if (FBackdate == None):
		FBackdate=Fdate
	com_email='admin'
	#------------------------------
	company_id=0
	
	gmt_created=Fdate
	gmt_modified=datetime.datetime.now()
	
	#�ظ���
	reply_count=0
	#---------------------------
	#-------------------------------------
	#value=[company_id,com_email,Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,id,Ftype,isNewandHot,isNewandHot,gmt_created,gmt_modified,Fcheck,reply_count]
	#valueupdate=[Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,Ftype,isNewandHot,gmt_modified,Fcheck,reply_count]
	
	old_hanqing_id=id
	title=Ftitle
	content=Fcontent
	type_id=gettype_id(id)
	is_checked=Fcheck
	gmt_created=Fdate
	click_number=Fcount
	seo_details=seoDesc
	gmt_modified=gmt_modified
	tags=keywords
	gmt_order=Fdate
	value=[title,type_id,content,is_checked,gmt_created,click_number,seo_details,gmt_modified,tags,old_hanqing_id,gmt_order]
	valueupdate=[title,type_id,content,is_checked,gmt_created,click_number,seo_details,gmt_modified,tags,gmt_order]
	
	#-�ж��Ƿ��Ѿ���
	sql="select count(0) from price where old_hanqing_id="+str(id)
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		
		sql="update price set title=%s,type_id=%s,content=%s,is_checked=%s,gmt_created=%s,click_number=%s,seo_details=%s,gmt_modified=%s,tags=%s,gmt_order=%s where old_hanqing_id="+str(id)
		cursor.execute(sql,valueupdate);
		updateflag="����"
		
		#sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_news_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s where old_hanqing_id="+str(id)
		#cursor.execute(sql,valueupdate);
		#updateflag="����"
		#valueupdates.append(valueupdate)
	else:
		sql="insert into price(title,type_id,content,is_checked,gmt_created,click_number,seo_details,gmt_modified,tags,old_hanqing_id,gmt_order)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="����"
		
		#sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_hanqing_id,old_news_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count)"
		#sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		#cursor.execute(sql,value);
		#updateflag="����"
		#values.append(value)
	print changezhongwen(updateflag+"�ɹ�"+str(i))
	updatelastid(updatetablename,id)
	editdate=str(a[11])
	i+=1
	
	
	#append����ֻ��value��Ϊһ��������ӵ�values�� 
	#values.extend(value) # extend�������value��Ԫ�飩ת����values���б�
try:
	#��ȡ���¸���ʱ��
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor2.execute(sql)
		conn_news.commit()
	#updatelastid(updatetablename,'0')
	print changezhongwen("�ɹ�")
except Exception, e:
	print e



conn.close() 
conn_rcu.close()
conn_news.close()
os.system("python "+nowpath+"/offer_price.py")
