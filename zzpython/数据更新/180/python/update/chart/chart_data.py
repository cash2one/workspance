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


updatetablename="chart_data"


print changezhongwen('更新'+updatetablename)


#------------函数
def gettypechange(code):
	sql="select id from chart_category  where old_code="+str(code)+""
	cursor_mysql.execute(sql)
	resultinfo=cursor_mysql.fetchone()
	if (resultinfo == None):
		return ''
	else:
		return str(resultinfo[0])
def getchart_infoid(id):
	sql="select id from charts_info where old_id="+str(id)+""
	cursor_mysql.execute(sql)
	resultinfo=cursor_mysql.fetchone()
	if (resultinfo == None):
		return '0'
	else:
		return str(resultinfo[0])
def getcurve_title(code):
	chartcodeid=gettypechange(code)
	sql="select name from chart_category  where id="+str(chartcodeid)+""
	cursor_mysql.execute(sql)
	resultinfo=cursor_mysql.fetchone()
	if (resultinfo == None):
		return ''
	else:
		return str(resultinfo[0])
		
sql = "select id,Curve_date,Curve_code_little,Curve_price1,Curve_price2,Curve_price3,Curve_code,mid from curve_list where id>"+getlastid(updatetablename)+" order by id asc"
cursor_news.execute(sql)
results = cursor_news.fetchall()

value_insert=[]
value_update=[]
i=0
for a in results:
	id=str(a[0])
	old_id=id
	Curve_date=str(a[1])
	Curve_code_little=str(a[2])
	Curve_price1=str(a[3])
	Curve_price2=str(a[4])
	Curve_price3=str(a[5])
	Curve_code=str(a[6])
	mid=str(a[7])
	
	#name=getcurve_title(curve_Code)
	name=changezhongwen('金属价格')
	chart_category_id=gettypechange(Curve_code_little)
	
	value=Curve_price1
	chart_info_id=getchart_infoid(mid)
	
	gmt_created=datetime.datetime.now()
	gmt_modified =datetime.datetime.now()
	
	value_insert=[chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id]
	value_update=[chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id]
	
	#-判断是否已经导
	sql="select count(0) from chart_data where old_id="+str(id)+""
	cursor_mysql.execute(sql)
	oldid=cursor_mysql.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		if (Curve_code=='10'):
			if (Curve_price1!='0'):
				chaname=changezhongwen('金属价格（10：00）')
				value_update=[chart_category_id,chaname,value,chart_info_id,gmt_created,gmt_modified,old_id]
				sql="update chart_data set chart_category_id=%s,name=%s,value=%s,chart_info_id=%s,gmt_created=%s,gmt_modified=%s,old_id=%s where old_id="+str(id)+" and name='"+str(chaname)+"'"
				cursor_mysql.execute(sql,value_update);
				updateflag="更新"
			if (Curve_price2!='0'):
				chaname=changezhongwen('金属价格（11：40）')
				value_update=[chart_category_id,chaname,value,chart_info_id,gmt_created,gmt_modified,old_id]
				sql="update chart_data set chart_category_id=%s,name=%s,value=%s,chart_info_id=%s,gmt_created=%s,gmt_modified=%s,old_id=%s where old_id="+str(id)+" and name='"+str(chaname)+"'"
				cursor_mysql.execute(sql,value_update);
				updateflag="更新"
			if (Curve_price3!='0'):
				chaname=changezhongwen('金属价格（14：55）')
				value_update=[chart_category_id,chaname,value,chart_info_id,gmt_created,gmt_modified,old_id]
				sql="update chart_data set chart_category_id=%s,name=%s,value=%s,chart_info_id=%s,gmt_created=%s,gmt_modified=%s,old_id=%s where old_id="+str(id)+" and name='"+str(chaname)+"'"
				cursor_mysql.execute(sql,value_update);
				updateflag="更新"
				
		else:
			value_update=[chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id]
			sql="update chart_data set chart_category_id=%s,name=%s,value=%s,chart_info_id=%s,gmt_created=%s,gmt_modified=%s,old_id=%s where old_id="+str(id)
			cursor_mysql.execute(sql,value_update);
			updateflag="更新"
		
	else:
		updateflag="新增"
		if (Curve_code=='10'):
			if (Curve_price1!='0'):
				chaname=changezhongwen('金属价格（10：00）')
				sql="insert into chart_data(chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id)"
				sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
				value_insert=[chart_category_id,chaname,value,chart_info_id,gmt_created,gmt_modified,old_id]
				cursor_mysql.execute(sql,value_insert);
			if (Curve_price2!='0'):
				chaname=changezhongwen('金属价格（11：40）')
				sql="insert into chart_data(chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id)"
				sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
				value_insert=[chart_category_id,chaname,value,chart_info_id,gmt_created,gmt_modified,old_id]
				cursor_mysql.execute(sql,value_insert);
			if (Curve_price3!='0'):
				chaname=changezhongwen('金属价格（14：55）')
				sql="insert into chart_data(chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id)"
				sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
				value_insert=[chart_category_id,chaname,value,chart_info_id,gmt_created,gmt_modified,old_id]
				cursor_mysql.execute(sql,value_insert);
		else:
			value_insert=[chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id]
			sql="insert into chart_data(chart_category_id,name,value,chart_info_id,gmt_created,gmt_modified,old_id)"
			sql=sql+"values(%s,%s,%s,%s,%s,%s,%s)"
			cursor_mysql.execute(sql,value_insert);
			updateflag="新增"
		
	
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,id)
	i+=1
	
	
print changezhongwen("更新成功")


conn_news.close() 
conn_mysql.close()