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

updatetablename="comp_info"

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
	else:
		sql="select PPTGrade from comp_ppt where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '10051001'
		else:
			PPTGrade=str(newcode[0])
			print PPTGrade
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
	sql="select Garden from comp_provinceID where com_id="+com_id+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		cid=newcode[0]
		sql="select id from category_garden where old_code='"+str(cid)+"'"
		cursor.execute(sql)
		newcode=cursor.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]
def getregfrom_code(com_id):
	sql="select regFrom from comp_regFrom where com_id="+com_id+""
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

def getvipcomp(cid):
	if (str(cid) == 'None'):
		return '0'
	else:
		sql="select id from comp_zstinfo where com_id="+str(cid)+" and com_check=1"
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '0'
		else:
			return '1'
def getvipyearcount(cid):
	if (str(cid)=='None'):
		return '0'
	else:
		sql="select sum(yearNum) from comp_continue  where com_id="+cid+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '0'
		else:
			return newcode[0]
#and com_id>"+getlastid(updatetablename)+"
sql = "select com_id,com_name,com_productslist_en,com_kind,com_ctr_id,com_province,com_keywords,com_intro,com_regtime,com_add,com_zip,com_subname,convert(bigint,gmt_editdate)"
sql +=" from comp_info where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
#com_regtime>'2011-11-10 10:21:16' and com_regtime<'2011-11-16'
sql = "select com_id,com_name,com_productslist_en,com_kind,com_ctr_id,com_province,com_keywords,com_intro,com_regtime,com_add,com_zip,com_subname,convert(bigint,gmt_editdate)"
sql +=" from comp_info where  com_regtime>'2011-11-16 11:27:17'  order by gmt_editdate asc"

cursor1.execute(sql)
results = cursor1.fetchall()


editdate=0
value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	com_id=str(a[0])
	com_name=changezhongwen(a[1])
	com_productslist_en=changezhongwen(a[2])
	com_kind=str(a[3])
	com_ctr_id=str(a[4])
	com_province=changezhongwen(a[5])
	com_keywords1=str(a[6])
	address=changezhongwen(a[9])
	address_zip=changezhongwen(a[10])
	com_keywords2=com_keywords1.split(',')
	if (len(com_keywords2)>0):
		com_keywords=com_keywords2[0]
	else:
		com_keywords=com_keywords1
	
	com_intro=changezhongwen(a[7])
	com_regtime=a[8]
	domain_zz91=changezhongwen(a[11])
	service_code=getservice_code(com_kind)
	membership_code=getmembership_code(com_id)
	classified_code=getclassified_code(com_id)
	foreign_city=''
	
	if (com_ctr_id !='1'):
		foreign_city=com_province
		area_code=getforeigncode(com_ctr_id)
	else:
		area_code=getprovincecode(com_province)
	
	industry_code=getindustry_code(com_keywords)
	category_garden_id=getcategory_garden_id(com_id)
	regfrom_code=getregfrom_code(com_id)
	is_main=1
	is_block=0
	zst_flag=getvipcomp(com_id)
	zst_year=getvipyearcount(com_id)
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	sql="select salestype,salestext,buytext from comp_salestype where com_id="+com_id+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		business_type=0
		buy_details=''
		sale_details=''
	else:
		business_type=newcode[0]
		buy_details=changezhongwen(newcode[2])
		sale_details=changezhongwen(newcode[1])
		
	#value=[id, name, business,  service_code, membership_code, classified_code, area_code, industry_code, category_garden_id, regfrom_code, introduction, regtime,  is_main, is_block, gmt_created, gmt_modified, old_id, foreign_city, business_type, buy_details, sale_details]
	value=[ com_name, com_productslist_en,  service_code, membership_code, classified_code, area_code, industry_code, category_garden_id, regfrom_code, com_intro, com_regtime,   is_block, gmt_created, gmt_modified, com_id, foreign_city, business_type, buy_details, sale_details,zst_flag,zst_year,address,address_zip,domain_zz91]
	valueupdate=[com_name,com_productslist_en,service_code, membership_code, classified_code, area_code, industry_code, category_garden_id, regfrom_code, com_intro,  gmt_modified, foreign_city, business_type, buy_details, sale_details,zst_flag,zst_year,address,address_zip,domain_zz91]
	#-判断是否已经导
	sql="select count(0) from company where old_id="+str(com_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		#sql="update company set name=%s, business=%s,  service_code=%s, membership_code=%s, classified_code=%s, area_code=%s, industry_code=%s, category_garden_id=%s, regfrom_code=%s, introduction=%s, gmt_modified=%s, foreign_city=%s, business_type=%s, buy_details=%s, sale_details=%s,zst_flag=%s,zst_year=%s,address=%s,address_zip=%s,domain_zz91=%s where id="+str(com_id)
		#cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into company(name, business,  service_code, membership_code, classified_code, area_code, industry_code, category_garden_id, regfrom_code, introduction, regtime,   is_block, gmt_created, gmt_modified, old_id, foreign_city, business_type, buy_details, sale_details,zst_flag,zst_year,address,address_zip,domain_zz91)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[12])
	i+=1

try:
	#读取最新更新时间
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		#cursor1.execute(sql)
		#conn_rcu.commit()
	
	print changezhongwen("更新成功")
except Exception, e:
	print e


conn.close() 
conn_rcu.close()
conn_others.close()
#os.system("python "+nowpath+"/company_contacts.py")
