#!/usr/bin/env python   
#coding=utf-8   
  
  
import sys
import codecs
import time,datetime
import struct
import math
import os


reload(sys)
sys.setdefaultencoding('utf-8')
sys.path.append('/mnt/pythoncode/zz91public/')

import pymongo
import MySQLdb 
from zz91conn import *
from zz91settings import *
from inc import *

conn = database_huanbao()
cursor=conn.cursor()

mongodb=database_mongodbconn().updateinfo
#-----链接表
updatelog=mongodb["updatelog"]

selectfild="id, uid, cid, title, details, category_code, group_id, photo_cover, province_code, area_code, total_num, total_units, price_num, price_units, price_from, price_to, use_to, used_product, tags, tags_sys, details_query, property_query, message_count, view_count, favorite_count, plus_count, html_path, integrity, gmt_publish, gmt_refresh, valid_days, gmt_expired, del_status, pause_status, check_status, check_admin, check_refuse, gmt_check, gmt_created, gmt_modified, info_come_from"
sql="delete from trade_supply_treeDays where DATEDIFF(CURDATE(),gmt_refresh)>3"
cursor.execute(sql)

sql="SELECT max_doc_id FROM counter1 WHERE id=2"
cursor.execute(sql)
resultsq = cursor.fetchone()
lastupdate=resultsq[0]

sql="select "+selectfild+" from trade_supply where DATEDIFF(CURDATE(),gmt_refresh)<=3 and UNIX_TIMESTAMP(gmt_refresh)>"+str(lastupdate)+""
#sql="select "+selectfild+" from trade_supply where id>10261541"
cursor.execute(sql)
results = cursor.fetchall()
if results:
	zdlen=len(results[0])
	print len(results)
	for a in results:
		value=[]
		values=""
		valuess=""
		arrselectfild=selectfild.split(",")
		for b in range(0, zdlen):
			value.append(a[b])
			values+="%s,"
			valuess+=arrselectfild[b]+"=%s,"
		sqlq="select id from trade_supply_treeDays where id="+str(a[0])
		cursor.execute(sqlq)
		resultsq = cursor.fetchone()
		if resultsq==None:
			sqlp="insert into trade_supply_treeDays("+selectfild+") values("+values[0:len(values)-1]+")"
			cursor.execute(sqlp,value)
            conn.commit()
		else:
			sqlp="update trade_supply_treeDays set "+valuess[0:len(valuess)-1]+" where id="+str(a[0])+""
			cursor.execute(sqlp,value)
            conn.commit()

	sql="select UNIX_TIMESTAMP(MAX(gmt_refresh)) from trade_supply where DATEDIFF(CURDATE(),gmt_refresh)<=3"
	cursor.execute(sql)
	resultsq = cursor.fetchone()
	lastupdate=resultsq[0]
	print lastupdate
	sql="update counter1 set max_doc_id=%s where id=2"
	cursor.execute(sql,[lastupdate])
    conn.commit()