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
execfile("/usr/tools/inc.py") 
execfile("/usr/tools/connbbs.py") 
#conn = MySQLdb.connect(host='192.168.110.118', user='kang', passwd='astozz91jiubao',db='import',charset='utf8')   
#cursor = conn.cursor()

conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()

conn2=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
cursor2=conn2.cursor()
updatetablename="offer_sortttemp"
sql = "select pdt_id as id,pdt_kind from v_products_allinfo where pdt_id>"+getlastid(updatetablename)+" order by pdt_id asc"
cursor1.execute(sql)
results = cursor1.fetchall()
value=[]
for a in results:
	id=str(a[0])
	a3=str(a[1])
	if (a3 == '1'):
		a3='10331000'
	else:
		a3='10331001'

	print id
	value=[a3]
	sql="update products set products_type_code=%s"
	sql=sql+"where id="+str(id)
	cursor.execute(sql,value);
	updatelastid(updatetablename,id)


conn.close() 
conn1.close()
conn2.close()
