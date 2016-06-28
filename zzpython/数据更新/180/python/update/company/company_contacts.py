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

def gettel_country_code(com_tel):
	arrcom_tel=com_tel.split('-')
	if (len(arrcom_tel)>1):
		if (len(arrcom_tel[0])<4):
			return arrcom_tel[0]
		else:
			return ''
	else:
		return ''

def gettel_area_code(com_tel):
	arrcom_tel=com_tel.split('-')
	if (len(arrcom_tel)>2):
		return arrcom_tel[1]
	else:
		return ''

def gettel(com_tel):
	arrcom_tel=com_tel.split('-')
	if (len(arrcom_tel)>2):
		arrtel = arrcom_tel[2:len(arrcom_tel)]
		strtel=''
		for i in arrtel:
			if (str(i) != ''):
				strtel = strtel + str(i) + '-' 
		return strtel
	else:
		return com_tel

def getsex(sex):
	if (sex == '' or sex  == None):
		return ''
	else:
		if (sex == changezhongwen("先生")):
			return '0'
		elif (sex == changezhongwen("女士")):
			return '1'
		else:
			return ''
def getqq(com_id):
	sql="select com_msn from comp_comIntro where com_id="+str(com_id)
	cursor1.execute(sql)
	com_msn=cursor1.fetchone()
	if (com_msn == None):
		return ''
	else:
		return com_msn[0]

def gethidden_contacts(com_id):
	sql="select id from comp_infoNotShow where com_id="+str(com_id)
	cursor1.execute(sql)
	contactsshow=cursor1.fetchone()
	# 1 不显示 0 显示
	if (contactsshow == None):
		return 0
	else:
		return contactsshow[0]

def getnewcomid(com_id):
	sql="select id from company where old_id="+str(com_id)
	cursor.execute(sql)
	contactsshow=cursor.fetchone()
	# 1 不显示 0 显示
	if (contactsshow == None):
		return 0
	else:
		return contactsshow[0]
def getis_show_email(com_id):
	sql="select id from comp_Emailshow where com_id="+str(com_id)
	cursor1.execute(sql)
	contactsshow=cursor1.fetchone()
	# 1 不显示 0 显示
	if (contactsshow == None):
		return 0
	else:
		return contactsshow[0]
updatetablename="company_contacts"

print changezhongwen('更新company_contacts')
#com_id>"+getlastid(updatetablename)+"
sql = "select com_email,com_id,com_tel,com_mobile,com_fax,com_website,com_contactperson,com_desi,com_station,com_add,com_zip,com_regtime,com_email_back,com_email_check,convert(bigint,gmt_editdate) from comp_info where  convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
sql = "select com_email,com_id,com_tel,com_mobile,com_fax,com_website,com_contactperson,com_desi,com_station,com_add,com_zip,com_regtime,com_email_back,com_email_check,convert(bigint,gmt_editdate) from comp_info where  com_regtime>'2011-11-16' order by gmt_editdate asc"
cursor1.execute(sql)
results = cursor1.fetchall()


editdate=0
value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	account=str(a[0])
	company_id=str(a[1])
	is_admin=1
	com_tel=changezhongwen(str(a[2]))
	com_tel=com_tel.replace(' ','-')
	tel_country_code=gettel_country_code(com_tel)
	tel_area_code=gettel_area_code(com_tel)
	tel=gettel(com_tel)
	mobile=changezhongwen(str(a[3]))
	com_fax=changezhongwen(str(a[4]))
	fax_country_code=gettel_country_code(com_fax)
	fax_area_code=gettel_area_code(com_fax)
	fax=gettel(com_fax)
	email=account
	website=changezhongwen(str(a[5]))
	contact=changezhongwen(a[6])
	sex=getsex(changezhongwen(str(a[7])))
	position=changezhongwen(a[8])
	#address=changezhongwen(a[9])
	#zip=changezhongwen(str(a[10]))
	qq=getqq(company_id)
	#hidden_contacts=gethidden_contacts(company_id)

	
	gmt_created=a[11]
	gmt_modified=datetime.datetime.now()
	
	info_source_code=''
	back_email=changezhongwen(str(a[12]))
	is_use_back_email=str(a[13])
	"""
	is_show_email=getis_show_email(company_id)
	if (is_show_email == None):
		is_show_email=0
	if (is_show_email>1):
		is_show_email=0
	print is_show_email
	"""
	msn=''
	#-------------公司账号
	sql="select Com_UserName,com_email,com_pw from Comp_Loading where com_id="+str(company_id)
	cursor1.execute(sql)
	mail=cursor1.fetchone()
	if (mail == None):
		com_email='admin'
		Com_UserName=''
		password=''
	else:
		Com_UserName=mail[0]
		com_email=mail[1]
		password=mail[2]
	if (str(Com_UserName)=='null' or str(Com_UserName)=='' or Com_UserName == None):
		account=changezhongwen(com_email)
	else:
		account=changezhongwen(Com_UserName)
	account=account.replace('\\','')
	sql="select logincount,lastlogintime from comp_login where com_id="+str(company_id)
	cursor1.execute(sql)
	mail=cursor1.fetchone()
	if (mail == None):
		num_login=0
		gmt_last_login=''
	else:
		num_login=mail[0]
		gmt_last_login=mail[1]
	if (num_login==None):
		num_login=0
	company_id=getnewcomid(company_id)
	#value=[account,company_id,is_admin,tel_country_code,tel_area_code,tel,mobile,fax_country_code,fax_area_code,fax,email,website,contact,sex,position,address
	#,zip,qq,msn,hidden_contacts,gmt_created,gmt_modified,info_source_code,back_email,is_use_back_email,is_show_email,password]
	value=[account,company_id,is_admin,tel_country_code,tel_area_code,tel,mobile,fax_country_code,fax_area_code,fax,email,contact,sex,position,qq,msn,gmt_created,gmt_modified,back_email,is_use_back_email,password,num_login,gmt_last_login]
	valueupdate=[account,company_id,is_admin,tel_country_code,tel_area_code,tel,mobile,fax_country_code,fax_area_code,fax,email,contact,sex,position,qq,msn,gmt_created,gmt_modified,back_email,is_use_back_email,password,num_login,gmt_last_login,account]
	#`id`, `account`, `company_id`, `contact`, `is_admin`, `tel_country_code`, `tel_area_code`, `tel`, `mobile`, `fax_country_code`, `fax_area_code`, `fax`, `email`, `sex`, `position`, `qq`, `msn`, `back_email`, `is_use_back_email`, `password`, `num_login`, `gmt_last_login`, `gmt_modified`, `gmt_created`
	valueupdate=[company_id,account]
	sql="select count(0) from company_account where account='"+account+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		#sql="update company_account set account=%s,company_id=%s,is_admin=%s,tel_country_code=%s,tel_area_code=%s,tel=%s,mobile=%s,fax_country_code=%s,fax_area_code=%s,fax=%s,email=%s,contact=%s,sex=%s,position=%s,qq=%s,msn=%s,gmt_created=%s,gmt_modified=%s,back_email=%s,is_use_back_email=%s,password=%s,num_login=%s,gmt_last_login=%s where account=%s"
		#cursor.execute(sql,valueupdate);
		#sql="update company_account set company_id=%s where account=%s"
		#cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		
		if (company_id!=0):
			sql="insert into company_account(account,company_id,is_admin,tel_country_code,tel_area_code,tel,mobile,fax_country_code,fax_area_code,fax,email,contact,sex,position,qq,msn,gmt_created,gmt_modified,back_email,is_use_back_email,password,num_login,gmt_last_login)"
			sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);
			updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[14])
	i=i+1
	#updatelastid(updatetablename,company_id)

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
#os.system("python "+nowpath+"/comp_zstinfo.py")
