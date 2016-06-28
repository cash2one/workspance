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
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor_mysql=conn_mysql.cursor()
cursor_news=conn_news.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------

updatetablename="chart_info"

print changezhongwen('更新'+updatetablename)


#------------函数
def gettypechange(code):
	if (code=='10'):
		return '1'
	if (code=='11'):
		return '2'
	if (code=='12'):
		return '3'
	if (code=='13'):
		return '4'
	if (code=='14'):
		return '5'
	if (code=='15'):
		return '6'
def getcurve_title(code):
	chartcodeid=gettypechange(code)
	sql="select name from chart_category  where id="+str(chartcodeid)+""
	cursor_mysql.execute(sql)
	resultinfo=cursor_mysql.fetchone()
	if (resultinfo == None):
		return ''
	else:
		return str(resultinfo[0])
		
sql = "select id,curve_Code,curve_date from curve_list_main where id>"+getlastid(updatetablename)+" order by id asc"
cursor_news.execute(sql)
results = cursor_news.fetchall()

value_insert=[]
value_update=[]
i=0
for a in results:
	id=str(a[0])
	old_id=id
	curve_Code=str(a[1])
	curve_date=str(a[2])
	
	title=getcurve_title(curve_Code)
	chart_category_id=gettypechange(curve_Code)
	gmt_date=curve_date
	gmt_created=datetime.datetime.now()
	gmt_modified =datetime.datetime.now()
	
	value_insert=[title,chart_category_id,gmt_date,gmt_created,gmt_modified,old_id]
	value_update=[title,chart_category_id,gmt_date,gmt_created,gmt_modified,old_id]
	
	#-判断是否已经导
	sql="select count(0) from charts_info where old_id="+str(id)+""
	cursor_mysql.execute(sql)
	oldid=cursor_mysql.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update charts_info set title=%s,chart_category_id=%s,gmt_date=%s,gmt_created=%s,gmt_modified=%s,old_id=%s where old_id='"+str(id)+"'"
		#print sql
		cursor_mysql.execute(sql,value_update);
		updateflag="更新"
	else:
		sql="insert into charts_info(title,chart_category_id,gmt_date,gmt_created,gmt_modified,old_id)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s)"
		cursor_mysql.execute(sql,value_insert);
		updateflag="新增"

	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,id)
	i+=1

print changezhongwen("更新成功")


conn_news.close() 
conn_mysql.close()

os.system("python "+nowpath+"/chart_data.py")