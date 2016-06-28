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


connt = MySQLdb.connect(host='192.168.110.118', user='asto_crm', passwd='zj88friend',db='asto_crm',charset='utf8')   
cursort = connt.cursor()
conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()
conn2=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
cursor2=conn2.cursor()

updatetablename="comp_info_test"
execfile("/usr/apps/python/inc.py")

def getprovincecode(strvalue):
	#地区编号
	if (strvalue == None):
		areaname=""
	else:
		areaname=strvalue
	areaname=areaname.replace(',','').replace('|',',')
	arrarea=[]
	arrarea=areaname.split(',')
	if (len(arrarea)>1):
		area1=arrarea[1]
		if (area1 == ''):
			area1=arrarea[0]
	else:
		area1=arrarea[0]
	
	area1=area1.replace("'","")
	area1=area1.replace(changezhongwen('省'),'')
	area1=area1.replace(changezhongwen('市'),'')
	
	
	sql11="select code from category where label like '%"+area1+"%'"
	
	cursor.execute(sql11)
	areacode = cursor.fetchone()
	if (areacode == None):
		areacode1=''
	else:
		areacode1=areacode[0]
	
	return areacode1


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
		
		
def getservice_code(code):
	if (str(code) == 'None'):
		return '0'
	else:
		sql="select code from category where code like '1020%' and old_code="+str(code)+""
		cursor.execute(sql)
		newcode=cursor.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]

def getmembership_code(com_id):
	sql="select id from comp_zstinfo where com_id="+str(com_id)+" and com_check=1"
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '10051000'
		comtype=0
	else:
		comtype=1
		
	if (comtype == 1):
		sql="select PPTGrade from comp_ppt where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '10051001'
		else:
			PPTGrade=str(newcode[0])
			if(PPTGrade == '0'):
				return '10051001'
			elif(PPTGrade == '1'):
				return '100510021000'
			elif(PPTGrade == '2'):
				return '100510021001'
			elif(PPTGrade == '3'):
				return '100510021002'
			else:
				return '10051001'

def getclassified_code(com_id):
	sql="select comType from comp_comType where com_id="+com_id+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		comType = newcode[0]
		if (comType == '10'):
			return '10101001'
		elif(comType == '11'):
			return '10101002'
		elif (comType == '12'):
			return '10101003'
		elif (comType == '13'):
			return '10101004'
		else:
			return '10101002'


def getforeigncode(cid):
	if (str(cid) == 'None'):
		return '0'
	else:
		sql="select code from category where code like '1001%' and old_id="+str(cid)+""
		cursor.execute(sql)
		newcode=cursor.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]
def getindustry_code(cid):
	cid = str(cid).replace(' ','')
	if (str(cid) == 'None' or str(cid) == ''):
		return '0'
		print cid
	else:
		sql="select code from category where code like '1000%' and old_id="+str(cid)+""
		cursor.execute(sql)
		newcode=cursor.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]

def getcategory_garden_id(com_id):
	sql="select Garden from comp_provinceID where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode[0]
def getcategory_garden_name(gid):
	if (str(gid)!='None'):
		sql="select meno from market_sort where code="+str(gid)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return ''
		else:
			return changezhongwen(newcode[0])
			
def getprovince_code(com_id):
	sql="select province from comp_provinceID where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode[0]
def getcity_code(com_id):
	sql="select city from comp_provinceID where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode[0]

def getregfrom_code(com_id):
	sql="select regFrom from comp_regFrom where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		cid=newcode[0]
		sql="select code from category where old_code='"+str(cid)+"' and code like '1003%'"
		cursor.execute(sql)
		newcode=cursor.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]

def getvipNumyear(com_id):
	sql="select sum(yearNum) from comp_continue  where com_id="+str(com_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode[0]

def getcxcount(com_id):
	sql="select Evaluate_score,Certificate_score,remark_score,vip_score,cxcount from comp_score where com_id="+str(com_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		numyear=getvipNumyear(com_id)
		if (numyear == None):
			#return newcode[0]+newcode[1]+newcode[2]+newcode[3]
			return '0'
		else:
			#return newcode[0]+newcode[1]+newcode[2]+newcode[3]+numyear*20
			return '0'
def getindustry_name(iid):
	if (str(iid)!='None' and str(iid)!='' and str(iid)!=' '):
		sql="select cb_chn_name from cls_b where cb_id="+str(iid)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return ''
		else:
			return changezhongwen(newcode[0])
	else:
		return ''
def getsalesandbuy(com_id):
	sql="select salestext,buytext from comp_salestype where com_id="+str(com_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return str(changezhongwen(newcode[0]))+str(changezhongwen(newcode[1]))			
#and com_id>"+getlastid(updatetablename)+"  convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') 
sql = "select top 100000 com_id,com_name,com_productslist_en,com_province,com_keywords,com_intro,com_regtime,com_subname,gmt_editdate"
sql +=" from comp_info where com_id>"+getlastid(updatetablename)+" order by com_id asc"



cursor1.execute(sql)
results = cursor1.fetchall()

sql="update import_table set updatetime=(select convert(bigint,max(gmt_editdate)) from comp_info) where tablename='"+updatetablename+"'"
cursor1.execute(sql)
conn1.commit()

value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	com_id=str(a[0])
	com_name=changezhongwen(a[1])
	com_productslist_en=changezhongwen(a[2])
	com_province=changezhongwen(a[3])
	
	com_keywords1=str(a[4])
	com_keywords2=com_keywords1.split(',')
	if (len(com_keywords2)>0):
		com_keywords=com_keywords2[0]
	else:
		com_keywords=com_keywords1
		
	
	com_intro=changezhongwen(a[5])+com_productslist_en

	#membership_code=getmembership_code(com_id)
	com_viptype=getmembership_code(com_id)
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	com_cx=getcxcount(com_id)
	province_code=getprovince_code(com_id)
	city_code=getcity_code(com_id)
	garden_code=getcategory_garden_id(com_id)
	garden_name=getcategory_garden_name(garden_code)
	com_subname=a[7]
	sales_buy=getsalesandbuy(com_id)
	industry_name=getindustry_name(com_keywords)
	#value=[id, name, business,  service_code, membership_code, classified_code, area_code, industry_code, category_garden_id, regfrom_code, introduction, regtime,  is_main, is_block, gmt_created, gmt_modified, old_id, foreign_city, business_type, buy_details, sale_details]
	value=[com_id,com_name,com_cx,com_intro,com_viptype,com_province,com_keywords,province_code,city_code,garden_code,garden_name,com_subname,com_productslist_en,sales_buy,industry_name]
	valueupdate=[com_name,com_cx,com_intro,com_viptype,com_province,com_keywords,province_code,city_code,garden_code,garden_name,com_subname,com_productslist_en,sales_buy,industry_name]
	#-判断是否已经导
	sql="select count(0) from company where com_id="+str(com_id)+""
	cursort.execute(sql)
	oldid=cursort.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update company set com_name=%s,com_cx=%s,com_intro=%s,com_viptype=%s,com_province=%s,com_keywords=%s,province_code=%s,city_code=%s,garden_code=%s,garden_name=%s,com_subname=%s,com_productslist_en=%s,sales_buy=%s,industry_name=%s where com_id="+str(com_id)
		cursort.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into company(com_id,com_name,com_cx,com_intro,com_viptype,com_province,com_keywords,province_code,city_code,garden_code,garden_name,com_subname,com_productslist_en,sales_buy,industry_name)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursort.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,com_id)
	i+=1

try:
	#读取最新更新时间
	
	
	print changezhongwen("更新成功")
except Exception, e:
	print e

conn1.close()
conn2.close()
connt.close()


