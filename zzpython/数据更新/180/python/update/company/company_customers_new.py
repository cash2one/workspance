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
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py")
cursor1=conn_rcu.cursor()
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")


def getvipcomp(cid):
	if (str(cid) == 'None'):
		return '2'
	else:
		sql="select id from comp_zstinfo where com_id="+str(cid)+" and com_check=1"
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '2'
		else:
			return '3'
def getgroupid(cid):
	sql="select id from company_customers_group where old_id="+str(id)+""
	cursor.execute(sql)
	newcode=cursor.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode[0]
def getcompany_customers_group_id(cid):
	sql="select GroupID from comp_Customer where Self_CID="+str(id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		group_id=getgroupid(newcode[0])
def getcompany_id(cid):
	sql="select com_id from comp_Customer where Self_CID="+str(cid)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '0'
	else:
		return newcode[0]
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
def getforeigncode(cid):
	if (str(cid) == 'None'):
		return '0'
	else:
		if (str(cid) == '1'):
			return '1000'
		else:
			sql="select code from category where code like '1001%' and old_id="+str(cid)+""
			cursor.execute(sql)
			newcode=cursor.fetchone()
			if (newcode == None):
				return '0'
			else:
				return newcode[0]
def getusername(com_id):
	sql="select Com_UserName,Com_Email from comp_loading where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return 'admin'
	else:
		username=changezhongwen(str(newcode[0]))
		email=changezhongwen(str(newcode[1]))
		if (username == 'None' or username==''):
			username=email
		return username
updatetablename="company_customers"

sql = "select cuscom_id,com_id from comp_Customer where self_cid=0 order by id asc"

cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
valueupdate=[]
i=0
for a in results:
	com_id=str(a[1])
	cuscom_id=str(a[0])
	sql="select com_contactperson,com_station,com_mobile,com_email,com_desi,com_tel,com_fax,com_name,com_ctr_id,com_province,com_add,com_zip from comp_info where com_id="+str(cuscom_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode != None):
		company_contacts_id='0'
		company_customers_group_id='0'
		name=changezhongwen(newcode[0])
		position=changezhongwen(newcode[1])
		mobile=changezhongwen(newcode[2])
		email=changezhongwen(str(newcode[3]))
		sex=changezhongwen(str(newcode[4]))
		if (sex == changezhongwen('先生')):
			sex='1'
		else:
			sex='0'
		tel_country_code=''
		tel_area_code=''
		tel=changezhongwen(str(newcode[5]))
		fax_country_code=''
		fax_area_code=''
		fax=changezhongwen(str(newcode[6]))
		company=changezhongwen(str(newcode[7]))
		country_code=getforeigncode(str(newcode[8]))
		area_code=getprovincecode(str(newcode[9]))
		address=changezhongwen(str(newcode[10]))
		post_code=changezhongwen(str(newcode[11]))
		company_id=com_id
		account=getusername(company_id)
		rank=0
		status=0
		remark=''
		gmt_created=datetime.datetime.now()
		gmt_modified=datetime.datetime.now()
	
		value=[company_contacts_id, company_customers_group_id, name, position, mobile, email, sex, tel_country_code, tel_area_code, tel, fax_country_code, fax_area_code, fax, company, country_code, area_code, address, post_code, rank, status, remark, company_id,  gmt_created, gmt_modified,id,account,cuscom_id]
		#cid,com_contactperson,com_station,com_mobile,com_email,com_desi,com_tel,com_fax,com_name,com_ctr_id,com_province,com_add,com_zip,rank,status,remark

		sql="select count(0) from company_customers where old_comid="+str(cuscom_id)+" and company_id="+com_id+""
		cursor.execute(sql)
		oldid=cursor.fetchone()
		oldidcount=oldid[0]
		if (oldidcount>0):
			#sql="update company_customers set username=%s,password=%s,email=%s,gmt_modified=%s,gmt_login=%s,num_login=%s where old_id="+str(id)+""
			#cursor.execute(sql,valueupdate);
			updateflag="更新"
		else:
			sql="insert into company_customers(company_contacts_id, company_customers_group_id, name, position, mobile, email, sex, tel_country_code, tel_area_code, tel, fax_country_code, fax_area_code, fax, company, country_code, area_code, address, post_code, rank, status, remark, company_id,  gmt_created, gmt_modified,old_id,account,old_comid)"
			sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);
			updateflag="新增"
		
		
		print changezhongwen(updateflag+"成功"+str(i))
		i+=1
	
try:
	#读取最新更新时间
	#sql="update import_table set updatetime=(select convert(bigint,max(gmt_editdate)) from comp_Customer_Info) where tablename='"+updatetablename+"'"
	#cursor1.execute(sql)
	#conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e

conn.close() 
conn_rcu.close()
conn_others.close()
