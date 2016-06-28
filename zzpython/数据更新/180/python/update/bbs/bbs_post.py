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

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------



updatetablename="bbs_post"

print changezhongwen('更新bbs_post')



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
	#-------------公司账号
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
	
	#回复数
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
	#-判断是否已经导
	sql="select count(0) from bbs_post where old_forum_id="+str(id)
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_forum_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s where old_forum_id="+str(id)
		cursor.execute(sql,valueupdate);
		updateflag="更新"
		valueupdates.append(valueupdate)
	else:
		sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_forum_id,old_forum_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
		values.append(value)
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,id)
	i+=1
	editdate=str(a[11])
	
	# append方法只把value作为一个整体添加到values中 
	#values.extend(value) # extend方法会把value（元组）转换成values（列表）
try:
	#读取最新更新时间
	
	updatelastid(updatetablename,'0')
	#新增数据
	sql="insert into bbs_post(company_id,account,title,content,is_show,post_time,visited_count,reply_time,old_forum_id,old_forum_code,is_hot_post,post_type,gmt_created,gmt_modified,check_status,reply_count)"
	sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
	#cursor.executemany(sql,values);
	#更新数据
	sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_forum_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s where old_forum_id=%s"
	#cursor.executemany(sql,valueupdates);
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor2.execute(sql)
		conn_others.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e

	

sql="UPDATE bbs_post SET bbs_post_category_id=1 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags "
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags "
sql+="WHERE NAME IN('废料百态','热门评论 ','废金属论坛 ','管理杂谈','网商故事','招聘专栏','资讯评论','报价评论','行情评论','废金属交流','塑料论坛','废塑料交流','塑料颗粒坛','PET交流专区',"
sql+="'综合废料论坛','废纸论坛','废橡胶论坛','其它废料')));"

cursor.execute(sql)

sql="UPDATE bbs_post SET bbs_post_category_id=2 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags "
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags "
sql+="WHERE NAME IN('废料专家','创业.成功','废料产业之声','地方之声','商业防骗论坛','防骗宝典','曝光台','再生技术')));"

sql+="UPDATE bbs_post SET bbs_post_category_id=4 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags "
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags "
sql+="WHERE NAME IN('ZZ91学堂','ZZ91视频中心','ZZ91操作宝','成功分享','ZZ91网站公告','促销活动','网站新功能',"
sql+="'线下活动','网站公告', '互助看台','互助月刊','互助动态','新手上路','常见问题')));"

sql+="UPDATE bbs_post SET bbs_post_category_id=3 "
sql+="WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags " 
sql+="WHERE bbs_tags_id IN(SELECT id FROM bbs_tags " 
sql+="WHERE NAME IN('休闲娱乐','轻松一刻','生活常识','娱乐八卦')));"

sql+="UPDATE bbs_post SET bbs_post_category_id=1 where old_news_id is not null;"
sql+="UPDATE bbs_post SET bbs_post_category_id=1 where bbs_post_category_id is null;"
cursor.execute(sql)
sql="delete from bbs_post_daily_statistics"
#cursor.execute(sql)
#sql="insert into bbs_post_daily_statistics(bbs_post_id,daily_visited_count,daily_reply_count,statistical_time) select id,visited_count,reply_count,post_time from bbs_post where DATEDIFF(now(),post_time)<=1 "
#cursor.execute(sql)
#TRUNCATE TABLE bbs_post_daily_statistics;insert into bbs_post_daily_statistics(bbs_post_id,daily_visited_count,daily_reply_count,statistical_time) select id,visited_count,reply_count,post_time from bbs_post where DATEDIFF(now(),post_time)<=1

#24小时热帖

conn.close() 
conn_rcu.close()
conn_others.close()
os.system("python "+nowpath+"/bbs_reply.py")
