#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
#parentpath=os.path.abspath('../')
#nowpath=os.path.abspath('.')
nowpath="/usr/apps/python"
execfile(nowpath+"/conn.py")
execfile(nowpath+"/inc.py")
#--------------------------------------
updatetablename="login"
print "更新"+updatetablename
sql = "select "
sql = sql +" c.company_id,c.num_login, c.gmt_last_login"
sql = sql +" from company_account as c where DATEDIFF(CURDATE(),c.gmt_last_login)<=10 order by c.id asc"
#sql="select company_id,num_login, gmt_last_login"
#sql=sql+" from local_outlogin"
cursorserver.execute(sql)
results = cursorserver.fetchall()
i=0
for a in results:
	company_id=str(a[0])
	num_login=str(a[1])
	gmt_last_login=str(a[2])
	
	#-判断是否已经导
	sql="select count(0) from comp_login where com_id="+str(company_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update comp_login set lastlogintime='"+gmt_last_login+"',logincount="+num_login+" where com_id="+company_id+""
		cursor.execute(sql);
		conn.commit()
		sql="update temp_salescomp set logincount="+num_login+",lastlogintime='"+gmt_last_login+"' where com_id="+company_id+""
		cursor.execute(sql);
		conn.commit()
		updateflag="更新"
	else:
		sql="insert into comp_login(com_id,lastlogintime,logincount) values("+company_id+",'"+gmt_last_login+"',"+num_login+")"
		cursor.execute(sql);
		conn.commit()
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	i=i+1
	
try:
	#读取最新更新时间
	print changezhongwen("更新成功")
except Exception, e:
	print e
conn.close()
connserver.close()
conn_mysql_sms.close()
cursor_mysql_reborn.close()
os.system("python /usr/apps/python/loginmore.py")

