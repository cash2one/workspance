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
cursor2=conn_news.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------

def getpricecode(oldcode):
	sql="select id from price_category where old_code="+str(oldcode)+""
	cursor.execute(sql)
	newcode=cursor.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode[0]
def getmetcode(met):
	if (met == '1'):
		return '53'
	if (met == '2'):
		return '54'
	if (met == '3'):
		return '57'
	if (met == '4'):
		return '55'
	if (met == '5'):
		return '58'
	if (met == '6'):
		return '56'
	if (met == '7'):
		return '59'
	if (met == '8'):
		return '179'
	if (met == '9'):
		return '180'
	if (met == '10'):
		return '181'

def getmetarea(metarea):
	if (metarea == '1'):
		return '203'
	if (metarea == '2'):
		return '204'
	if (metarea == '3'):
		return '205'
		
#and td_id>(select lastid from import_table where tablename='"+updatetablename+"')
#where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') and td_id>"+getlastid(updatetablename)+"
updatetablename="bbs_price"
print changezhongwen('更新price')
#  convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') 
sql = "select td_id,0,td_title,cast(td_content AS TEXT) AS td_content,cls,td_time,fdate,td_flag,td_count,jd,seoDesc,met,keywords,metarea,convert(bigint,gmt_editdate) from baojia_baojia where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
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
	seoDesc=a[10]
	met=a[11]
	tags=changezhongwen(str(a[12]))
	metarea=a[13]
	if (met != None):
		assist_type_id=getmetcode(str(met))
	
	if (assist_type_id == None):
		assist_type_id=getmetarea(str(metarea))
	if (is_hot_post == None):
		isNewandHot = 0
	else:
		isNewandHot=is_hot_post
	
	com_email='admin'
	#------------------------------
	company_id=0
	Ftitle = changezhongwen(Ftitle)
	Fcontent= changezhongwen(Fcontent)
	seoDesc=changezhongwen(str(seoDesc))
	
	if (FBackdate == None):
		FBackdate=Fdate
	
	if (seoDesc == None):
		seoDesc=null
		
	gmt_created=Fdate
	gmt_modified=datetime.datetime.now()
	
	type_id=getpricecode(Ftype)
	#回复数
	reply_count=0
	#---------------------------
	#-------------------------------------
	value=[Ftitle,type_id,assist_type_id,Fcontent,Fcheck,Fdate,Fdate,Fcount,Fcount,id,tags,gmt_modified]
	valueupdate=[Ftitle,type_id,assist_type_id,Fcontent,Fcheck,Fdate,Fcount,seoDesc,gmt_modified,tags]
	#-判断是否已经导
	sql="select count(0) from price where old_id="+str(id)
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update price set title=%s,type_id=%s,assist_type_id=%s,content=%s,is_checked=%s,gmt_created=%s,click_number=%s,seo_details=%s,gmt_modified=%s,tags=%s where old_id="+str(id)
		cursor.execute(sql,valueupdate);
		updateflag="更新"
		valueupdates.append(valueupdate)
	else:
		sql="insert into price(title,type_id,assist_type_id,content,is_checked,gmt_order,gmt_created,click_number,real_click_number,old_id,tags,gmt_modified)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
		values.append(value)
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,id)
	editdate=str(a[14])
	i+=1
	
	
	# append方法只把value作为一个整体添加到values中 
	#values.extend(value) # extend方法会把value（元组）转换成values（列表）
try:
	#读取最新更新时间
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor2.execute(sql)
		conn_news.commit()
	updatelastid(updatetablename,'0')
	print changezhongwen("成功")
except Exception, e:
	print e

#os.system("python bbs_tags.py")	

conn.close() 
conn_rcu.close()
conn_news.close()
os.system("python "+nowpath+"/bbs_baojia_pinlun.py")