#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
import base64,hashlib
from django.utils.http import urlquote


reload(sys)
sys.setdefaultencoding('utf-8')

execfile("/usr/tools/connbbs.py")
connt = MySQLdb.connect(host='192.168.110.118', user='asto_crm', passwd='zj88friend',db='asto_crm',charset='utf8')   
cursort = connt.cursor()
conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()
conn2=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
cursor2=conn2.cursor()

updatetablename="offer_info_price"
execfile("/usr/tools/inc.py") 
def getmembership_code(com_id):
	sql="select id from comp_zstinfo where com_id="+str(com_id)+" and com_check=1"
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return 0
		comtype=0
	else:
		comtype=1
		
	if (comtype == 1):
		sql="select PPTGrade from comp_ppt where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return 1
		else:
			PPTGrade=str(newcode[0])
			if(PPTGrade == '0'):
				return 1
			elif(PPTGrade == '1'):
				return 2
			elif(PPTGrade == '2'):
				return 3
			elif(PPTGrade == '3'):
				return 4
			else:
				return 1
def getcom_subname(com_id):
	sql="select com_subname from comp_info where com_id="+str(com_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode[0]
def getcom_name(com_id):
	sql="select com_name from comp_info where com_id="+str(com_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode[0]
def getpdt_price(pdt_id):
	sql="select pdt_price,pdt_price_min,pdt_price_max,pdt_unit from products_customerPrice where pdt_id="+str(pdt_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode

def getpdt_images(pdt_id):
	sql="select path from productimg_biao where pdt_id="+str(pdt_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode[0]

def getproductsid(id,code):
	sql="select label from category_products where oldid"+str(code)+"="+str(id)+" and is_assist=0"
	cursor.execute(sql)
	#print sql
	oldidstr1=cursor.fetchone()
	#print oldidstr1
	if (oldidstr1 == None):
		return '0'
	else:
		return oldidstr1[0]
def getptdsort(id,n):
	sql="select id,sort_id_b,subid,subid_s,sort_id from products_sort"+str(n)+" where pdt_id="+str(id)+" order by id desc"
	cursor1.execute(sql)
	oldidstr=cursor1.fetchone()
	if (oldidstr == None):
		return None
	else:
		sortidp1=str(oldidstr[1])
		sortidp2=str(oldidstr[2])
		sortidp3=str(oldidstr[3])
		sortidp4=str(oldidstr[4])	
	#print oldidstr
	return [[sortidp1,1],[sortidp2,2],[sortidp3,3],[sortidp4,4]]
def geclsvaluelist(oldidstr):
	notpdtid1=getproductsid(oldidstr[0][0],oldidstr[0][1])
	notpdtid2=getproductsid(oldidstr[1][0],oldidstr[1][1])
	notpdtid3=getproductsid(oldidstr[2][0],oldidstr[2][1])
	notpdtid4=getproductsid(oldidstr[3][0],oldidstr[3][1])
	notpdtid=''
	if (notpdtid1!='0'):
		notpdtid+=notpdtid1+'->'
	if (notpdtid2!='0'):
		notpdtid+=notpdtid2+'->'
	if (notpdtid3!='0'):
		notpdtid+=notpdtid3+'->'
	if (notpdtid4!='0'):
		notpdtid+=notpdtid4
	return notpdtid
def getproductsoldid(id):
	oldidstr=getptdsort(id,1)
	#print oldidstr
	if (oldidstr == None):
		notpdtid='0'
	else:
		notpdtid=geclsvaluelist(oldidstr)
	
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,2)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,3)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,4)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,5)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,6)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,10)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,12)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,14)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,15)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	return notpdtid

#隐形类别
def gethiddencode(id):
	sql="select hid from products_hiddensort where pdt_id="+str(id)+""
	cursor1.execute(sql)
	oldidstr=cursor1.fetchone()
	if (oldidstr == None):
		return '0'
	else:
		hid=oldidstr[0]
		sql="select label from category_products where oldhiddenid="+str(hid)+" and is_assist=1"
		cursor.execute(sql)
		oldidstr1=cursor.fetchone()
		if (oldidstr1 == None):
			return '0'
		else:
			return oldidstr1[0]
	
		
maxupdate=getlastid(updatetablename+"date")
if (maxupdate==''):
	maxupdate='0'
maxupid=getlastid(updatetablename)
if (maxupid==''):
	maxupid='0'
#convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') and pdt_id>"+getlastid(updatetablename)+"  pdt_time_en>=dateadd(d,-3,getdate())
sql = "select pdt_id"
sql +=" from products where pdt_id>"+getlastid(updatetablename)+" order by pdt_id asc"
cursor1.execute(sql)
results = cursor1.fetchmany(100000)


value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	
	pdt_id=str(a[0])
	
	#clslist=getproductsoldid(pdt_id)+'->'+gethiddencode(pdt_id)
	pricelist=getpdt_price(pdt_id)
	if (pricelist!=''):
		pdt_price=changezhongwen(pricelist[0])
		pdt_price_min=pricelist[1]
		pdt_price_max=pricelist[2]
		pdt_unit=changezhongwen(pricelist[3])
	else:
		pdt_price=''
		pdt_price_min=0
		pdt_price_max=0
		pdt_unit=''
	valueupdate1=[pdt_price,pdt_price_min,pdt_price_max,pdt_unit]
	#-判断是否已经导
	sql="select count(0) from products where pdt_id="+str(pdt_id)+""
	cursort.execute(sql)
	oldid=cursort.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		#sql="update products set com_id=%s,com_name=%s,pdt_kind=%s,pdt_name=%s,com_province=%s,pdt_detail=%s,pdt_time_en=%s,pdt_check=%s,com_subname=%s,viptype=%s,pdt_images=%s,pdt_keywords=%s,pdt_price=%s,clslist=%s where pdt_id="+str(pdt_id)
		sql="update products set pdt_price=%s,pdt_price_min=%s,pdt_price_max=%s,pdt_unit=%s where pdt_id="+str(pdt_id)
		#updatelastid(updatetablename,pdt_id)
		cursort.execute(sql,valueupdate1);
		updateflag="更新"
	else:
		#sql="insert into products(com_id,com_name,pdt_id,pdt_kind,pdt_name,com_province,pdt_detail,pdt_time_en,pdt_check,com_subname,viptype,pdt_images,pdt_keywords,pdt_price,clslist)"
		#sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		#cursort.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,pdt_id)
	i+=1

try:

	#updatelastid(updatetablename,str('0'))
	print changezhongwen("更新成功")
	conn.close() 
	conn1.close()
	conn2.close()
	connt.close()
except Exception, e:
	print e



