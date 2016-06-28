#!/usr/bin/env python   
#coding=utf-8   

import MySQLdb   
import pymssql
import sys
import codecs
import datetime
import struct
import os
import re

reload(sys)
#type = sys.getfilesystemencoding()
sys.setdefaultencoding('utf-8')
#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor_aqsiq=conn_aqsiq.cursor()
cursor_mysql_aqsiq=conn_mysql_aqsiq.cursor()
execfile(parentpath+"/inc.py")

def updatedb(selectfild,dbtable):
	sql="select "+selectfild+" from "+dbtable+""
	cursor_aqsiq.execute(sql)
	results = cursor_aqsiq.fetchall()
	if results:
		zdlen=len(results[0])
		for a in results:
			value=[]
			values=""
			valuess=""
			arrselectfild=selectfild.split(",")
			for b in range(0, zdlen):
				fvalue=changezhongwen(str(a[b]))
				value.append(fvalue)
				values+="%s,"
				valuess+=arrselectfild[b]+"=%s,"
				
			sqlq="select id from "+dbtable+" where id="+str(a[0])
			cursor_mysql_aqsiq.execute(sqlq)
			print str(a[0])
			resultsq = cursor_mysql_aqsiq.fetchone()
			if resultsq==None:
				sqlp="insert into "+dbtable+"("+selectfild+") values("+values[0:len(values)-1]+")"
				cursor_mysql_aqsiq.execute(sqlp,value)
			else:
				sqlp="update "+dbtable+" set "+valuess[0:len(valuess)-1]+" where id="+str(a[0])+""
				cursor_mysql_aqsiq.execute(sqlp,value)
				
#updatedb("id,Scode,Scom_name,Sstat,Sfw,Sgj","aqsiq_comp")
updatedb("aid,id,pyear,pdate,pname,ptype1,ptype2,pichi,in_comp,out_comp,goods_code,goods_name,in_num1,in_num2,in_num3,province,bz,flag","aqsiq_goods")
#updatedb("id,gid,pichi,out1,out2,out3","aqsiq_goods_biao")
#updatedb("id,ntitle,ntype,ncontent,ndate,ncount","aqsiq_news")
#updatedb("id,code,meno,admintype","aqsiq_newstype")

