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
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------


updatetablename="exhibit"

def getplate_category_code(cid):
	cid = str(cid).replace(' ','')
	if (str(cid) == 'None' or str(cid) == ''):
		return '0'
	else:
		sql="select code from category where code like '1037%' and old_id="+str(cid)+""
		cursor.execute(sql)
		newcode=cursor.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]
def getexhibit_category_code(cid):
	cid = str(cid).replace(' ','')
	if (str(cid) == 'None' or str(cid) == ''):
		return '0'
	else:
		sql="select code from category where code like '1038%' and old_id="+str(cid)+""
		cursor.execute(sql)
		newcode=cursor.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]
def getarea_code(ccode):
	sql="select name from area where sz_code='"+str(ccode)+"'"
	cursor1.execute(sql)
	areacode = cursor1.fetchone()
	if (areacode == None):
		areaname=''
	else:
		areaname=changezhongwen(areacode[0])
	
	areaname=areaname.replace(changezhongwen('省'),'')
	areaname=areaname.replace(changezhongwen('市'),'')
	areaname=areaname.replace(changezhongwen('区'),'')
	sql11="select code from category where label like '%"+areaname+"%'"
	cursor.execute(sql11)
	areacode = cursor.fetchone()
	if (areacode == None):
		areacode1=''
	else:
		areacode1=areacode[0]
	return areacode1

#where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') and id>"+getlastid(updatetablename)+"
sql = "select id,title,content,openTime,anotherUrl,openAdd,flag,cateID,endTime,openProvince,openCity"
sql +=" from exhibit_list  order by id asc"
cursor1.execute(sql)
results = cursor1.fetchall()


value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	name=changezhongwen(a[1])
	area_code=getarea_code(a[10])
	start_time=a[3]
	end_time=a[8]
	plate_category_code=getplate_category_code(a[6])
	exhibit_category_code=getexhibit_category_code(a[7])
	content=changezhongwen(a[2])
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	#id, name, area_code, start_time, end_time, plate_category_code, exhibit_category_code, content, gmt_created, gmt_modified
	#---------------------------
	#-------------------------------------
	value=[id,name,area_code,start_time,end_time,plate_category_code,exhibit_category_code,content,gmt_created,gmt_modified,id]
	valueupdate=[name,area_code,start_time,end_time,plate_category_code,exhibit_category_code,content,gmt_modified]
	#-判断是否已经导
	sql="select count(0) from exhibit where old_id="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update exhibit set name=%s,area_code=%s,start_time=%s,end_time=%s,plate_category_code=%s,exhibit_category_code=%s,content=%s,gmt_modified=%s where old_id="+str(id)
		cursor.execute(sql,valueupdate);
		conn.commit()
		updateflag="更新"
	else:
		sql="insert into exhibit(id,name,area_code,start_time,end_time,plate_category_code,exhibit_category_code,content,gmt_created,gmt_modified,old_id)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		conn.commit()
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,id)
	
	i+=1

try:
	#读取最新更新时间
	sql="update import_table set updatetime=(select convert(bigint,max(gmt_editdate)) from exhibit_list) where tablename='"+updatetablename+"'"
	cursor1.execute(sql)
	conn_rcu.commit()
	
	print changezhongwen("更新成功")
except Exception, e:
	print e


conn.close() 
conn_rcu.close()
conn_others.close()
