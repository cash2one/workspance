#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="zst_info"

def getmembership_code(com_id):
	sql="select id from comp_zstinfo where com_id="+str(com_id)+" and com_check=1"
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return '10051000'
	else:
		sql="select PPTGrade from comp_ppt where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return '10051001'
		else:
			PPTGrade=str(newcode[0])
			print PPTGrade
			if(PPTGrade == '0'):
				return '10051001'
			elif(PPTGrade == '1'):
				return '100510021000'
			elif(PPTGrade == '2'):
				return '100510021001'
			elif(PPTGrade == '3'):
				return '100510021002'
			else:
				return '10051001'

sql="select com_id from comp_zstinfo where com_check=1"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	membership_code=getmembership_code(company_id)
	value=[membership_code,company_id]
	sql="update company set membership_code=%s,zst_flag=1 where id=%s"
	cursor.execute(sql,value);
	print changezhongwen("成功"+str(i))
	i=i+1
	
try:

	print changezhongwen("更新成功")
except Exception, e:
	print e

conn.close() 
conn_rcu.close()
os.system("python "+nowpath+"/company_blacklist.py")

