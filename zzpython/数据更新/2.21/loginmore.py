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
updatetablename="loginmore"
print "更新"+updatetablename
#sql="select  company_id,gmt_target, login_count"
#sql=sql+" from analysis_login"
sql = "select "
sql = sql +" company_id,gmt_target, login_count"
sql = sql +" from analysis_login where DATEDIFF(CURDATE(),gmt_target)<=10 order by id asc"
cursorserver.execute(sql)
results = cursorserver.fetchall()
i=0
for a in results:
	company_id=str(a[0])
	gmt_target=str(a[1])
	login_count=str(a[2])
	
	if (gmt_target=='None'):
		gmt_target='1900-01-01'
	
	#-判断是否已经导
	sql="select count(0) from Comp_LoginCount where com_id="+str(company_id)+" and FDate='"+gmt_target+"'"
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
	else:
		sql="insert into Comp_LoginCount(Com_ID, FDate, LCount,AlCount)"
		sql=sql+" values("+company_id+", '"+gmt_target+"', "+login_count+",0)"
		cursor.execute(sql);
		conn.commit()
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	i=i+1
	
sql="update crm_update set updatetime=getdate() where id=1"
cursor.execute(sql);
conn.commit()
try:
	#读取最新更新时间
	print changezhongwen("更新成功")
except Exception, e:
	print e
conn.close()
connserver.close()
conn_mysql_sms.close()
cursor_mysql_reborn.close()
os.system("python /usr/apps/python/seo_ranking.py")
