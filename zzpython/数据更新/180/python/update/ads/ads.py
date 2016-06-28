#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
cursor_ads=conn_ads.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
def returnNewAdID(old_id):
	sql="select id from ad where old_id="+str(old_id)+""
	cursor_ads.execute(sql)
	newcode=cursor_ads.fetchone()
	if (newcode == None):
		return 0
	else:
		return newcode[0]
def getposition_id(old_code):
	sql="select id from ad_position where old_code="+str(old_code)+""
	cursor_ads.execute(sql)
	newcode=cursor_ads.fetchone()
	if (newcode == None):
		return 0
	else:
		return newcode[0]

def getadvertiser_id(email):
	sql="select id from advertiser where email='"+str(email)+"'"
	cursor_ads.execute(sql)
	newcode=cursor_ads.fetchone()
	if (newcode == None):
		return '35'
	else:
		return newcode[0]

def getcompinfo(com_id):
	sql="select com_name,com_email,com_contactperson,com_mobile from comp_info where com_email='"+str(com_id)+"'"
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		gmt_created=datetime.datetime.now()
		gmt_modified=datetime.datetime.now()
		newden=[changezhongwen(newcode[0]),newcode[1],changezhongwen(newcode[2]),changezhongwen(newcode[3]),gmt_created,gmt_modified,'1','N']
		sqla="select count(0) from advertiser where email='"+str(newcode[1])+"'"
		cursor_ads.execute(sqla)
		resultad=cursor_ads.fetchone()
		if (resultad[0]<=0):
			sqla="insert into advertiser(name,email,contact,phone,gmt_created,gmt_modified,category,deleted)"
			sqla=sqla+" values(%s,%s,%s,%s,%s,%s,%s,%s)"
			cursor_ads.execute(sqla,newden);
		else:
			sqla="update advertiser set name=%s,email=%s,contact=%s,phone=%s,gmt_created=%s,gmt_modified=%s,category=%s,deleted=%s where email='"+str(newcode[1])+"'"
			cursor_ads.execute(sqla,newden);
		return newcode
updatetablename="ads"
sql="select id,keywords,com_email,com_id,typeid,fromdate,todate,picurl,kcount,url,checked,gmt_created from advkeywords where typeid in (18,10,19,23,11,20,24,27)"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	old_id=str(a[0])
	com_id=str(a[3])
	compinfo=getcompinfo(str(a[2]))
	keywords=changezhongwen(str(a[1]))
	ad_description=str(a[2])
	position_id=getposition_id(str(a[4]))
	
	advertiser_id=getadvertiser_id(ad_description)
	
	if (compinfo!=''):
		ad_title=changezhongwen(compinfo[0])
	else:
		ad_title=str(a[2])
	ad_content=str(a[7]).replace('/upimages/upload','http://img1.zz91.com/ads')
	ad_target_url=str(a[9])
	checked=str(a[10])
	if (checked=='1'):
		online_status='Y'
	else:
		online_status='N'
	
	gmt_start=str(a[5])
	gmt_plan_end=str(a[6])
	review_status='Y'
	
	gmt_content_modified=datetime.datetime.now()
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	search_exact='|1:'+keywords+'|'
	
	#position_id, advertiser_id, ad_title, ad_description, ad_content, ad_target_url, online_status, gmt_start, gmt_plan_end, review_status, gmt_content_modified, gmt_created, gmt_modified, search_exact
	value=[position_id, advertiser_id, ad_title, ad_description, ad_content, ad_target_url, online_status, gmt_start, gmt_plan_end, review_status, gmt_content_modified, gmt_created, gmt_modified, search_exact,old_id]
	valueupdate=[position_id, advertiser_id, ad_title, ad_description, ad_content, ad_target_url, online_status, gmt_start, gmt_plan_end, review_status, gmt_content_modified, gmt_created, gmt_modified, search_exact,old_id]
	
	#-判断是否已经导
	sql="select count(0) from ad where old_id="+str(old_id)+""
	cursor_ads.execute(sql)
	oldid=cursor_ads.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update ad set position_id=%s, advertiser_id=%s, ad_title=%s, ad_description=%s, ad_content=%s, ad_target_url=%s, online_status=%s, gmt_start=%s, gmt_plan_end=%s, review_status=%s, gmt_content_modified=%s, gmt_created=%s, gmt_modified=%s, search_exact=%s where old_id=%s"
		cursor_ads.execute(sql,valueupdate);
		updateflag="更新"
		#更新   定位精确广告的详细信息表
		ad_id=returnNewAdID(old_id)
		ad_position_id=position_id
		exact_type_id=1
		anchor_point=keywords
		valuead=[ad_id,ad_position_id,exact_type_id,anchor_point,gmt_created, gmt_modified]
		sqla="select count(0) from ad_exact_type where ad_id="+str(ad_id)
		cursor_ads.execute(sqla)
		resultad=cursor_ads.fetchone()
		if (resultad[0]>0):
			sqla="update ad_exact_type set ad_id=%s,ad_position_id=%s,exact_type_id=%s,anchor_point=%s,gmt_created=%s, gmt_modified=%s where ad_id="+str(ad_id)+""
			cursor_ads.execute(sqla,valuead);
		else:
			sqla="insert into ad_exact_type(ad_id,ad_position_id,exact_type_id,anchor_point,gmt_created, gmt_modified)"
			sqla=sqla+" values(%s,%s,%s,%s,%s,%s)"
			cursor_ads.execute(sqla,valuead);
		#end
	else:
		sql="insert into ad(position_id, advertiser_id, ad_title, ad_description, ad_content, ad_target_url, online_status, gmt_start, gmt_plan_end, review_status, gmt_content_modified, gmt_created, gmt_modified, search_exact,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor_ads.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[5])
	i=i+1
	
try:
	#读取最新更新时间
	#if (editdate!=0):
		#sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		#cursor1.execute(sql)
		#conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e
#print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate crmcompany")
conn.close() 
conn_rcu.close()
conn_ads.close()