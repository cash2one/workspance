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
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="company_domain"
sql = "select com_id,com_subname from comp_info where com_id>"+getlastid(updatetablename)+" and vipflag>=1 and vip_check=1 order by com_id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
i=0
for a in results:
	id=str(a[0])
	domain_zz91=str(changezhongwen(a[1]))
	sql="update company set domain_zz91='"+domain_zz91+"' where id="+id+""
	cursor.execute(sql,value);
	conn.commit()
	print 'suc'+str(i)
	i=i+1
	updatelastid(updatetablename,id)

conn.close() 
conn_rcu.close()
