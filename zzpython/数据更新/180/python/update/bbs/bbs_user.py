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
sys.setdefaultencoding('utf-8')
#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor_rcu=conn_rcu.cursor()
cursor_others=conn_others.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="bbs_user"
#
print changezhongwen('更新bbs_user')


sql = "select id,Com_ID,p_bname,p_sex,p_email,p_othercontact,p_educationLevel,p_birthday,p_home,p_incomeLevel,p_faith,p_bloodType,p_faction,p_instr,Place,p_type,pic,convert(bigint,gmt_editdate)"
sql +=" from Forum_PersonInfo where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
cursor_others.execute(sql)
results = cursor_others.fetchall()


editdate=0
value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	com_id=str(a[1])
	p_bname=changezhongwen(str(a[2]))
	p_sex=changezhongwen(str(a[3]))
	p_email=changezhongwen(str(a[4]))
	p_othercontact=changezhongwen(str(a[5]))
	p_educationLevel=changezhongwen(str(a[6]))
	p_birthday=changezhongwen(str(a[7]))
	p_home=changezhongwen(str(a[8]))
	p_incomeLevel=changezhongwen(str(a[9]))
	p_faith=changezhongwen(a[10])
	p_bloodType=changezhongwen(a[11])
	p_faction=changezhongwen(a[12])
	p_instr=changezhongwen(a[13])
	Place=changezhongwen(a[14])
	p_type=a[15]
	pic=a[16]
	#print is_hot_post
	if (p_type == None):
		p_type1 = 0
	else:
		p_type1=p_type
	#-------------公司账号
	if (p_birthday==None):
		p_birthday="1900-01-01 00:00:00"
	if (p_birthday==''):
		p_birthday="1900-01-01 00:00:00"
	sql="select account from company where id="+str(com_id)
	cursor.execute(sql)
	mail=cursor.fetchone()
	
	if (mail == None):
		com_email='admin'
	else:
		com_email=mail[0]
	sex=""
	if (p_sex == None):
		sex=""
	
	if (p_sex == changezhongwen("先生")):
		sex="1"
	
	if (p_sex==changezhongwen("女士")):
		sex="0"
		
	company_id=com_id
	
	#------------------------------
	if (company_id == None):
		company_id=0
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	
	#---------------------------
	#-------------------------------------
	value=[company_id,com_email,p_bname,sex,p_email,p_othercontact,p_othercontact,p_faction,p_birthday,p_home,p_instr,pic,gmt_created,gmt_modified,id]
	#valueupdate=[Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,Ftype,isNewandHot,gmt_modified,Fcheck,reply_count]
	#-判断是否已经导
	sql="select count(0) from bbs_user_profiler where account='"+str(com_email)+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		#sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_forum_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s where old_forum_id="+str(id)
		#cursor.execute(sql,valueupdate);
		updateflag="更新"
		valueupdates.append(valueupdate)
	else:
		sql="insert into bbs_user_profiler(company_id, account, nickname, sex,email, tel, mobile, interests, brithday, address, remark,  picture_path, gmt_created, gmt_modified,old_user_id)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
		values.append(value)
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[17])
	#updatelastid(updatetablename,id)
	i+=1

try:
	#读取最新更新时间
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor_others.execute(sql)
		conn_others.commit()
	#updatelastid(updatetablename,'0')
	sql="update bbs_user_profiler set brithday='1900-01-01 00:00:00' where brithday='0000-00-00 00:00:00'"
	cursor.execute(sql)
	print changezhongwen("更新成功")
except Exception, e:
	print e



conn.close()
conn_rcu.close()
conn_others.close()

os.system("python "+nowpath+"/bbs_post.py")
