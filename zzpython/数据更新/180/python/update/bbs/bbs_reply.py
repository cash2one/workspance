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


updatetablename="bbs_reply"
print changezhongwen('更新bbs_reply')


#and id>(select lastid from import_table where tablename='bbs_reply')
sql = "select id,com_id,Ftitle,Fcontent,Ftype,Fdate,FBackdate,Fsub,Fcheck,Fcount,isNewandHot,subid,convert(bigint,gmt_editdate) from forum_bbs where subid>0 and convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
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
	subid=a[11]
	#print is_hot_post
	if (is_hot_post == None):
		isNewandHot = 0
	else:
		isNewandHot=is_hot_post
	#-------------公司账号
	sql="select com_email from comp_info where com_id="+str(com_id)
	cursor1.execute(sql)
	mail=cursor1.fetchone()
	
	if (mail == None):
		com_email='admin'
	else:
		com_email=mail[0]
	#------------------------------
	company_id=com_id
	Ftitle = Ftitle.decode('GB18030').encode('utf-8')
	Fcontent= Fcontent.decode('GB18030').encode('utf-8')
	Fcontent= Fcontent.replace('&lt;br/&gt;','<br/>').replace('&amp;nbsp;','&nbsp;')
	gmt_created=Fdate
	gmt_modified=datetime.datetime.now()
	
	
	
	#----取 post ID号
	sql="select id from bbs_post where old_forum_id="+str(subid)
	cursor.execute(sql)
	oldsubid=cursor.fetchone()
	if (oldsubid == None):
		bbs_post_id=0
	else:
		bbs_post_id=oldsubid[0]
	#-------------------------------------
	value=[company_id,com_email,bbs_post_id,Ftitle,Fcontent,Fcheck,Fdate,gmt_modified,id]
	valueupdate=[company_id,Ftitle,Fcontent,Fcheck,gmt_modified]
	#-判断是否已经导
	sql="select count(0) from bbs_post_reply where old_reply_id="+str(id)
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update bbs_post_reply set company_id=%s,title=%s,content=%s,check_status=%s,gmt_modified=%s where old_reply_id="+str(id)
		cursor.execute(sql,valueupdate);
		updateflag="更新"
		valueupdates.append(valueupdate)
	else:
		sql="insert into bbs_post_reply(company_id,account,bbs_post_id,title,content,check_status,gmt_created,gmt_modified,old_reply_id)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
		values.append(value)
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[12])
	updatelastid(updatetablename,id)
	
	i+=1
	
	
	# append方法只把value作为一个整体添加到values中 
	#values.extend(value) # extend方法会把value（元组）转换成values（列表）
try:
	#读取最新更新时间
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor2.execute(sql)
		conn_others.commit()
	updatelastid(updatetablename,'0')
	print changezhongwen("更新成功")
except Exception, e:
	print e


	
conn.close() 
conn_rcu.close()
conn_others.close()
os.system("python "+nowpath+"/bbs_post_user_id.py")	
