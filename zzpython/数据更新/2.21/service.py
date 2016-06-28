#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')

nowpath="/usr/apps/python"
execfile(nowpath+"/conn.py")
execfile(nowpath+"/inc.py")
#--------------------------------------
updatetablename="service"
print "更新"+updatetablename
#sql="select  company_id,gmt_start,gmt_end,apply_status"
#sql=sql+" from v_company_service where membership_code<>'None'"
#where DATEDIFF(CURDATE(),gmt_modified)<=150 
sql = "select id,company_id,crm_service_code,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,membership_code,apply_status,apply_group "
sql = sql +" from crm_company_service where DATEDIFF(CURDATE(),gmt_modified)<=150  order by gmt_modified desc"
cursorserver.execute(sql)
results = cursorserver.fetchall()
i=0
for a in results:
	id=str(a[0])
	company_id=str(a[1])
	crm_service_code=str(a[2])
	gmt_pre_start=str(a[3])
	gmt_pre_end=str(a[4])
	gmt_signed=str(a[5])
	gmt_start=str(a[6])
	gmt_end=str(a[7])
	membership_code=str(a[8])
	apply_status=str(a[9])
	apply_group=str(a[10])
	if (gmt_start=='None'):
		gmt_start='1900-01-01'
	if (gmt_end=='None'):
		gmt_end='1900-01-01'
	if (apply_status=='None'):
		apply_status='1'
	sql="select count(0) from company_service where id="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		sql="update company_service set company_id="+company_id+",crm_service_code='"+crm_service_code+"',gmt_pre_start='"+gmt_pre_start+"',gmt_pre_end='"+gmt_pre_end+"',gmt_signed='"+gmt_signed+"',gmt_start='"+gmt_start+"',gmt_end='"+gmt_end+"',membership_code='"+membership_code+"',apply_status='"+apply_status+"',apply_group='"+apply_group+"'"
		sql=sql+" where id="+str(id)+""
		cursor.execute(sql);
		conn.commit()
	else:
		updateflag="新增"
		sql="insert into company_service(id,company_id,crm_service_code,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,membership_code,apply_status,apply_group)"
		sql=sql+" values("+id+","+company_id+",'"+crm_service_code+"','"+gmt_pre_start+"','"+gmt_pre_end+"','"+gmt_signed+"','"+gmt_start+"','"+gmt_end+"','"+membership_code+"','"+apply_status+"','"+apply_group+"')"
		cursor.execute(sql);
		conn.commit()

	sqls = "select id,company_id,crm_service_code,gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,membership_code,apply_status,apply_group "
	sqls = sqls +" from crm_company_service where company_id="+str(company_id)+" and (crm_service_code='1000' or crm_service_code='1006') and apply_status=1 order by gmt_modified desc limit 0,1"
	cursorserver.execute(sqls)
	resultss = cursorserver.fetchone()
	if resultss:
		gmt_start=str(resultss[6])
		gmt_end=str(resultss[7])
		apply_status=str(resultss[9])
		if (gmt_start=='None'):
			gmt_start='1900-01-01'
		if (gmt_end=='None'):
			gmt_end='1900-01-01'
		if (apply_status=='None'):
			apply_status='1'
		sql="select count(0) from temp_salescomp where com_id="+str(company_id)+""
		cursor.execute(sql)
		oldid=cursor.fetchone()
		oldidcount=oldid[0]
		if (oldidcount>0):
			sql="update comp_info set vipflag=2,vip_check="+apply_status+",vip_datefrom='"+gmt_start+"',vip_dateto='"+gmt_end+"' where com_id="+company_id+""
			cursor.execute(sql);
			conn.commit()
			sql="update temp_salescomp set vipflag=2,vip_check="+apply_status+",vip_datefrom='"+gmt_start+"',vip_dateto='"+gmt_end+"' where com_id="+company_id+""
			cursor.execute(sql);
			conn.commit()
			updateflag="更新成功"
		else:
			updateflag=""
	print updateflag+str(i)
	i=i+1
sql="select company_id,cs_account from crm_cs"
cursorserver.execute(sql)
results = cursorserver.fetchall()
for a in results:
	sqlc="select company_id from crm_cs where company_id=%s"
	cursor.execute(sqlc,(a[0]))
	result=cursor.fetchone()
	if not result:
		sqlp="insert into crm_cs(company_id,cs_account) values("+str(a[0])+",'"+str(a[1])+"')"
		cursor.execute(sqlp);
		conn.commit()
conn.close()
connserver.close()
conn_mysql_sms.close()
cursor_mysql_reborn.close()
os.system("python /usr/apps/python/login.py")

