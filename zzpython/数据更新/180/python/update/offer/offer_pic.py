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

def getparentcode(code):
	sql="select id from products_albums where oldcode='"+str(code)+"'"
	cursor.execute(sql)
	newcodeid=cursor.fetchone()
	if (newcodeid == None):
		return '0'
	else:
		return newcodeid[0]
updatetablename="offer_pic"
print changezhongwen('更新'+updatetablename)
sql = "select id,pdt_id,pname,path,picfirst,havename,RecommendFlag,isTop,albumType,convert(bigint,gmt_editdate) from productimg_biao where pdt_id>0 and convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') order by gmt_editdate asc"
cursor1.execute(sql)
results = cursor1.fetchall()

editdate=0
value=[]
i=0
for a in results:
	id=str(a[0])
	product_id=a[1]
	name=changezhongwen(a[2])
	pic_address='products/'+str(a[3])
	is_default=str(a[4])
	album_id=getparentcode(a[8])
	is_cover=a[7]
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()


	value=[name,product_id,pic_address,album_id,is_default,is_cover,gmt_created,gmt_modified]
	valueupdate=[product_id,pic_address]
	sql="select count(0) from products_pic where pic_address='"+str(pic_address)+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		if (str(product_id)!='0'):
			sql="update products_pic set product_id=%s where pic_address=%s"
			cursor.execute(sql,valueupdate);
	else:
		sql="insert into  products_pic(name,product_id,pic_address,album_id,is_default,is_cover,gmt_created,gmt_modified)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
	updatelastid(updatetablename,id)
	editdate=str(a[9])
	i=i+1
	print i

if (editdate!=0):
	sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
	cursor1.execute(sql)
	conn_rcu.commit()
conn.close() 
conn_rcu.close()
conn_others.close()
os.system("python "+nowpath+"/offer_albums.py")
