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


updatetablename="offer_price"

print changezhongwen('更新'+updatetablename)
def getprovincecode(strvalue):
	#地区编号
	if (strvalue == None):
		areaname=""
	else:
		areaname=strvalue
	areaname=areaname.replace('|',',')
	arrarea=[]
	arrarea=areaname.split(',')
	if (len(arrarea)>1):
		area1=arrarea[1]
	else:
		area1=arrarea[0]
		
	sql11="select code from category where label like '%"+area1+"%'"
	cursor.execute(sql11)
	areacode = cursor.fetchone()
	if (areacode == None):
		areacode1=''
	else:
		areacode1=areacode[0]
	return areacode1

def getpricecode(str1,str2,str3):
	strcode=str(str3)
	if (strcode == '0'):
		strcode=str(str2)
	if(strcode == '0'):
		strcode=str(str1)
	if(strcode == '0'):
		strcode='0'
	sql="select count(0) from cate_customerprice where code='"+str(strcode)+"'"
	cursor1.execute(sql)
	[count,]=cursor1.fetchone()
	if (count > 0):
		sql="select id from cate_customerprice where code='"+str(strcode)+"'"
		count=cursor1.execute(sql)
		[oldcodeid,]=cursor1.fetchone()
	else:
		oldcodeid='0'
	sql="select code from category_company_price where old_id="+str(oldcodeid)+""
	cursor.execute(sql)
	newcode=cursor.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode

def getexpiretime(expirtime,posttime):
	if (expirtime == None):
		return posttime
	elif(expirtime == '-1'):
		return "9999-12-31 23:59:59"
	elif(expirtime == ''):
		return posttime
	elif(expirtime.isdigit()):
		d1=datetime.date(posttime.year, posttime.month, posttime.day)
		d2=datetime.timedelta(days=int(expirtime))
		return d1+d2
	else:
		return posttime
		
		
		

sql = "select id,com_id,pdt_id,pdt_kind,pdt_name,pdt_time_en,pdt_price,pdt_price_min,pdt_price_max,pdt_unit,pdt_detail,typeid,fdate,pdt_expired,pdt_addr,pcheck,pdt_sort1,pdt_sort2,pdt_sort3,gmt_editdate,convert(bigint,gmt_editdate)"
sql +=" from products_customerPrice where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') order by gmt_editdate asc"
cursor1.execute(sql)
results = cursor1.fetchall()

editdate=0

value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	com_id=str(a[1])
	pdt_id=str(a[2])
	pdt_kind=str(a[3])
	pdt_name=changezhongwen(str(a[4]))
	pdt_time_en=str(a[5])
	pdt_price=changezhongwen(str(a[6]))
	pdt_price_min=str(a[7])
	pdt_price_max=str(a[8])
	pdt_unit=changezhongwen(str(a[9]))
	pdt_detail=changezhongwen(a[10])
	typeid=a[11]
	fdate=a[12]
	pdt_expired=a[13]
	pdt_addr=changezhongwen(a[14])
	pcheck=a[15]
	pdt_sort1=a[16]
	pdt_sort2=a[17]
	pdt_sort3=a[18]
	gmt_editdate=a[19]
	#print is_hot_post
	if (typeid == None):
		typeid = 0
	#-------------公司账号
	if (pdt_time_en==None):
		pdt_time_en="1900-01-01 00:00:00"
	
	sql="select com_email from comp_info where com_id="+str(com_id)
	cursor1.execute(sql)
	mail=cursor1.fetchone()
	
	if (mail == None):
		com_email='admin'
	else:
		com_email=mail[0]
	#---------------------------------
	expired_time=getexpiretime(pdt_expired,fdate)
	#print (str(pdt_sort1)+'|'+str(pdt_sort2)+'|'+str(pdt_sort3))
	area_code=getprovincecode(pdt_addr)
	[category_company_price_code,]=getpricecode(pdt_sort1,pdt_sort2,pdt_sort3)
	#------------------------------
	company_id=com_id
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	#id,com_id,pdt_id,pdt_kind,pdt_name,pdt_time_en,pdt_price,pdt_price_min,pdt_price_max,pdt_unit,pdt_detail,typeid,fdate,pdt_expired,pdt_addr,pcheck,pdt_sort1,pdt_sort2,pdt_sort3,gmt_editdate
	#---------------------------
	#-------------------------------------
	value=[company_id,com_email,pdt_id,pdt_name,category_company_price_code,pdt_price,pdt_unit,pdt_price_min,pdt_price_max,area_code,pdt_detail,pcheck,fdate,fdate,expired_time,fdate,gmt_created,gmt_modified,id]
	
	#valueupdate=[Ftitle,Fcontent,Fcheck,Fdate,Fcount,FBackdate,Ftype,isNewandHot,gmt_modified,Fcheck,reply_count]
	#-判断是否已经导
	sql="select count(0) from company_price where old_id="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update bbs_post set title=%s,content=%s,is_show=%s,post_time=%s,visited_count=%s,reply_time=%s,old_forum_code=%s,is_hot_post=%s,gmt_modified=%s,check_status=%s,reply_count=%s where old_forum_id="+str(id)
		#cursor.execute(sql,valueupdate);
		updateflag="更新"
		valueupdates.append(valueupdate)
	else:
		sql="insert into company_price(company_id,account,product_id,title,category_company_price_code,price,price_unit,min_price,max_price,area_code,details,is_checked,check_time,post_time,expired_time,refresh_time,gmt_created,gmt_modified,old_id)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		
		cursor.execute(sql,value);
		updateflag="新增"
		values.append(value)
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,id)
	editdate=str(a[20])
	i+=1

try:
	#读取最新更新时间
	
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor2.execute(sql)
		conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e


conn.close() 
conn_rcu.close()
conn_others.close()
