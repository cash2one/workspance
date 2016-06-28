#!/usr/bin/env python   
#coding=utf-8   
######################################   
#   
# @author migle   
# @date 2010-01-17   
#   
######################################   
#   
# MySQLdb ≤È—Ø   
#   
#######################################   
  
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
execfile("/usr/tools/inc.py") 
#execfile("/usr/tools/connbbs.py") 
conn = MySQLdb.connect(host='192.168.110.118', user='kang', passwd='astozz91jiubao',db='import',charset='utf8')   
cursor = conn.cursor()

conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()

conn2=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
cursor2=conn2.cursor()

def getcbidcode(code):
	if (code == '1'):
		cbcode='1000'
	if (code == '2'):
		cbcode='1001'
	if (code == '3'):
		cbcode='1002'
	if (code == '4'):
		cbcode='1003'
	if (code == '5'):
		cbcode='1004'
	if (code == '6'):
		cbcode='1005'
	if (code == '10'):
		cbcode='1006'
	if (code == '12'):
		cbcode='1007'
	if (code == '14'):
		cbcode='1008'
	if (code == '15'):
		cbcode='1009'
	return cbcode

sql = "select id,cb_subname,cb_subid,cb_id from cls_cbSub order by cb_id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=1000
cccode=''
for a in results:
	id=str(a[0])
	cname=changezhongwen(a[1])
	subid=str(a[2])
	cbid=str(a[3])
	cbcode=getcbidcode(cbid)
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	if (str(cccode)!=str(cbcode)):
		i=1000
	else:
		i+=1
	recbcode=str(cbcode)+str(i)
	#print recbcode
	value=[recbcode,cname,0,0,id,gmt_created,gmt_modified]
	sql="select count(0) from category_products where oldid2="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into category_products(code,label,is_assist,is_del,oldid2,gmt_created,gmt_modified)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
		#cursor.execute(sql,value);
	cccode=cbcode

	
	
sql = "select id,cb_subname,cb_subid,cb_id,cb_subid_s from cls_cbSub_s order by cb_subid asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=1000
cccode=''
for a in results:
	id=str(a[0])
	cname=changezhongwen(a[1])
	subid=str(a[2])
	cbid=str(a[3])
	subid_s=str(a[4])
	#cbcode=getcbidcode(cbid)
	sql="select code from category_products where oldid2="+str(subid)
	cursor.execute(sql)
	tcbcode=cursor.fetchone()
	if (tcbcode == None):
		cbcode='0'
	else:
		cbcode=tcbcode[0]
		
	#print (cbcode)
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	if (str(cccode)!=str(cbcode)):
		i=1000
	else:
		i+=1
	recbcode=str(cbcode)+str(i)
	#print recbcode
	value=[recbcode,cname,0,0,id,gmt_created,gmt_modified]
	sql="select count(0) from category_products where oldid3="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into category_products(code,label,is_assist,is_del,oldid3,gmt_created,gmt_modified)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
		#cursor.execute(sql,value);
	cccode=cbcode
	
	
	
	
sql = "select cs_id,cs_chn_name,cb_subid,cs_cb_id,cb_subid_s from cls_s where cs_default=0 and cb_subid_s>0 order by cb_subid_s asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=1000
cccode=''
for a in results:
	id=str(a[0])
	cname=changezhongwen(a[1])
	subid=str(a[2])
	cbid=str(a[3])
	subid_s=str(a[4])
	#cbcode=getcbidcode(cbid)
	sql="select code from category_products where oldid3="+str(subid_s)
	cursor.execute(sql)
	tcbcode=cursor.fetchone()
	if (tcbcode == None):
		cbcode='0'
	else:
		cbcode=tcbcode[0]
		
	#print (cbcode)
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	if (str(cccode)!=str(cbcode)):
		i=1000
	else:
		i+=1
	recbcode=str(cbcode)+str(i)
	#print recbcode
	value=[recbcode,cname,0,0,id,gmt_created,gmt_modified]
	sql="select count(0) from category_products where oldid4="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into category_products(code,label,is_assist,is_del,oldid4,gmt_created,gmt_modified)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
		#cursor.execute(sql,value);
	cccode=cbcode
	
	
	
sql = "select id,cs_name,cb_id,subid,subid_s,cs_id from cls_hiddenSort order by cs_id asc,subid_s asc,subid asc,cb_id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=1000
cccode=''
for a in results:
	id=str(a[0])
	cname=changezhongwen(a[1])
	cbid=str(a[2])
	subid=str(a[3])
	subid_s=str(a[4])
	csid=str(a[5])
	#cbcode=getcbidcode(cbid)
	if (csid == '0'):
		oldid='oldid3'
		newid=subid_s
	elif(subid_s == '0'):
		oldid='oldid2'
		newid=subid
	elif(subid == '0'):
		oldid='oldid1'
		newid=cbid
	else:
		oldid='oldid4'
		newid=csid
		
	sql="select code from category_products where "+oldid+"="+str(newid)
	cursor.execute(sql)
	tcbcode=cursor.fetchone()
	if (tcbcode == None):
		cbcode='0'
	else:
		cbcode=tcbcode[0]
		
	#print (cbcode)
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	if (str(cccode)!=str(cbcode)):
		i=1000
	else:
		i+=1
	recbcode=str(cbcode)+str(i)
	print recbcode
	value=[recbcode,cname,1,0,id,gmt_created,gmt_modified]
	sql="select count(0) from category_products where oldhiddenid="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount<=0):
		sql="insert into category_products(code,label,is_assist,is_del,oldhiddenid,gmt_created,gmt_modified)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
	cccode=cbcode
	

conn.close() 
conn1.close()
conn2.close()
