#!/usr/bin/env python	
# -*- coding: utf-8 -*-	
#导入smtplib和MIMEText	
from public import *
from time import ctime, sleep
import datetime
from datetime import timedelta, date 
import MySQLdb
import os
from zz91conn import database_comp
conn = database_comp()
cursor=conn.cursor()
def ppcautoupdate():
	sql="select company_id from phone where expire_flag=0 and exists(select company_id from crm_company_service where crm_service_code in ('1007','1008','1009','1010') and apply_status=1 and company_id=phone.company_id)"
	cursor.execute(sql)
	results = cursor.fetchall()
	for list in results:
		company_id=list[0]
		refresh_time=datetime.datetime.now()
		sqlp="update products set refresh_time=%s,gmt_modified=%s where company_id=%s"
		cursor.execute(sqlp,[refresh_time,refresh_time,company_id])
		conn.commit()
ppcautoupdate()
conn.close()
		