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
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------



updatetablename="bbs_post"

print changezhongwen('����bbs_post')



#and id>(select lastid from import_table where tablename='"+updatetablename+"')
sql = "select id,com_id,Ftitle,Fcontent,Ftype,Fdate,FBackdate,Fsub,Fcheck,Fcount,isNewandHot,convert(bigint,gmt_editdate) from forum_bbs where subid=0 and convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') order by gmt_editdate asc"
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
	Fsub=str(a[7])
	Fcheck=str(a[8])
	Fcount=str(a[9])
	is_hot_post=a[10]
	#print is_hot_post
	if (is_hot_post == None):
		isNewandHot = 0
	else:
		isNewandHot=is_hot_post
		
	if (FBackdate == None):
		FBackdate=Fdate
	#-------------��˾�˺�
	sql="select Com_UserName,com_email from Comp_Loading where com_id="+str(com_id)
	cursor1.execute(sql)
	mail=cursor1.fetchone()
	if (mail == None):
		com_email='admin'
		Com_UserName=''
	else:
		Com_UserName=mail[0]
		com_email=mail[1]
	if (str(Com_UserName)=='null' or str(Com_UserName)=='' or Com_UserName == None):
		com_email=com_email
	else:
		com_email=Com_UserName
	#------------------------------
	company_id=com_id
	Ftitle = Ftitle.decode('GB18030').encode('utf-8')
	Fcontent= Fcontent.decode('GB18030').encode('utf-8')
	Fcontent= Fcontent.replace('&lt;br/&gt;','<br/>').replace('&amp;nbsp;','&nbsp;').replace('admin.zz91.com','img.zz91.com')
	gmt_created=Fdate
	gmt_modified=datetime.datetime.now()
	
	#�ظ���
	sql="select count(0) from forum_bbs where subid="+str(id)
	cursor2.execute(sql)
	replycount=cursor2.fetchone()
	reply_count=replycount[0]
	#---------------------------
	#-------------------------------------
	if (isNewandHot>1):
		is_hot_post=1
	else:
		is_hot_post=0
	value=[company_id,com_email,Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,id,Ftype,isNewandHot,is_hot_post,gmt_created,gmt_modified,Fcheck,reply_count]
	valueupdate=[Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,Ftype,is_hot_post,gmt_modified,Fcheck,reply_count]
	#-�ж��Ƿ��Ѿ���
	sql="select count(0) from bbs_post where old_forum_id="+str(id)
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_forum_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s where old_forum_id="+str(id)
		cursor.execute(sql,valueupdate);
		updateflag="����"
		valueupdates.append(valueupdate)
	else:
		sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_forum_id,old_forum_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="����"
		values.append(value)
	print changezhongwen(updateflag+"�ɹ�"+str(i))
	updatelastid(updatetablename,id)
	i+=1
	editdate=str(a[11])
	
	# append����ֻ��value��Ϊһ��������ӵ�values�� 
	#values.extend(value) # extend�������value��Ԫ�飩ת����values���б�
try:
	#��ȡ���¸���ʱ��
	
	updatelastid(updatetablename,'0')
	#��������
	sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_forum_id,old_forum_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count)"
	sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
	#cursor.executemany(sql,values);
	#��������
	sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_forum_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s where old_forum_id=%s"
	#cursor.executemany(sql,valueupdates);
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor2.execute(sql)
		conn_others.commit()
	print changezhongwen("���³ɹ�")
except Exception, e:
	print e

	

sql="UPDATE bbs_post SET bbs_post_category_id=1 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags "
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags "
sql+="WHERE NAME IN('���ϰ�̬','�������� ','�Ͻ�����̳ ','������̸','���̹���','��Ƹר��','��Ѷ����','��������','��������','�Ͻ�������','������̳','�����Ͻ���','���Ͽ���̳','PET����ר��',"
sql+="'�ۺϷ�����̳','��ֽ��̳','������̳','��������')));"

cursor.execute(sql)

sql="UPDATE bbs_post SET bbs_post_category_id=2 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags "
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags "
sql+="WHERE NAME IN('����ר��','��ҵ.�ɹ�','���ϲ�ҵ֮��','�ط�֮��','��ҵ��ƭ��̳','��ƭ����','�ع�̨','��������')));"

sql+="UPDATE bbs_post SET bbs_post_category_id=4 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags "
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags "
sql+="WHERE NAME IN('ZZ91ѧ��','ZZ91��Ƶ����','ZZ91������','�ɹ�����','ZZ91��վ����','�����','��վ�¹���',"
sql+="'���»','��վ����', '������̨','�����¿�','������̬','������·','��������')));"

sql+="UPDATE bbs_post SET bbs_post_category_id=3 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags " 
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags " 
sql+="WHERE NAME IN('��������','����һ��','���ʶ','���ְ���')));"

sql+="UPDATE bbs_post SET bbs_post_category_id=1 where old_news_id is not null;"
sql+="UPDATE bbs_post SET bbs_post_category_id=1 where bbs_post_category_id is null;"
cursor.execute(sql)
sql="delete from bbs_post_daily_statistics"
#cursor.execute(sql)
#sql="insert into bbs_post_daily_statistics(bbs_post_id,daily_visited_count,daily_reply_count,statistical_time) select id,visited_count,reply_count,post_time from bbs_post where DATEDIFF(now(),post_time)<=1 "
#cursor.execute(sql)
#TRUNCATE TABLE bbs_post_daily_statistics;insert into bbs_post_daily_statistics(bbs_post_id,daily_visited_count,daily_reply_count,statistical_time) select id,visited_count,reply_count,post_time from bbs_post where DATEDIFF(now(),post_time)<=1

#24Сʱ����

conn.close() 
conn_rcu.close()
conn_others.close()
os.system("python "+nowpath+"/bbs_reply.py")
