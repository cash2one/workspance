#!/usr/bin/env python   
#coding=utf-8   

import MySQLdb   
import pymssql
import sys
import codecs
import datetime
import struct
import os
from sphinxapi import *
reload(sys)
#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
#cursor1=conn_rcu.cursor()
#cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------

sql = "select keyword from category_keywords_search order by id asc"
cursor.execute(sql)
results = cursor.fetchall()
value=[]
for a in results:
	#-判断是否已经导
	keyword=a[0]
	searchCount=0
	keyword = keyword.replace('/',' ')
	keyword = keyword.replace('\\',' ')
	keyword = keyword.replace('-',' ')
	port = 9312
	cl = SphinxClient()
	cl.SetServer ( '192.168.110.120', port )
	cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
	#cl.SetSortMode( SPH_SORT_EXTENDED,"viptype desc,pdt_time desc" )
	#cl.SetLimits (1,20,100000)
	res = cl.Query ('@(pdt_name,pdt_keywords,com_province,clslist) '+keyword,'mysql,products_vip')
	if res:
		searchCount=res['total_found']
	print keyword+'('+str(searchCount)+')'
	sql="select count(0) from category_keywords_search where keyword='"+keyword+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		value=[keyword,searchCount,keyword]
		sql="update category_keywords_search set keyword=%s,searchCount=%s where keyword=%s"
		cursor.execute(sql,value);
	else:
		value=[keyword,searchCount]
		sql="insert into category_keywords_search(keyword,searchCount) values(%s,%s)"
		cursor.execute(sql,value);


conn.close()
