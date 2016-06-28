#!/usr/bin/env python   
#coding=utf-8   

import MySQLdb   
import pymssql
import sys
import codecs
import datetime,md5,hashlib
import struct
import os

reload(sys)
#type = sys.getfilesystemencoding()
sys.setdefaultencoding('utf-8')
#---数据库连接和配置
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='zz91_bbs',charset=None)
cursor=conn.cursor()
connmy = MySQLdb.connect(host='192.168.2.21', user='root', passwd='zj88friend',db='phpwind',charset='utf8')     
cursormy = connmy.cursor()
execfile("/usr/apps/python/inc.py")
#--------------------------------------
#cursormy.execute("delete from pw_members where uid>4")
sql = "select id,username,nickname,pwd,truename,sex,email,regtime from bbs_users where id=207 order by id asc"
#print sql
cursor.execute(sql)
results = cursor.fetchall()

value=[]
i=0
for a in results:
	id=str(a[0])
	username=changezhongwen(a[1])
	print username
	nickname=changezhongwen(a[2])
	pwd=str(a[3])
	md5pwd = hashlib.md5(pwd)
	md5pwd = md5pwd.hexdigest()
	password=md5pwd
	print password
	truename=changezhongwen(a[4])
	if (a[5]!=None):
		sex=int(a[5])
		gender=sex+1
	else:
		sex=0
		gender=0
	email=str(a[6])
	#regtime=str(a[7])
	groupid='-1'
	memberid='8'
	icon='none.gif|1|||'
	regdate='1341897244'
	yz='1'
	medals='1'
	userstatus='1024'
	apartment='330104'
	introduce=""
	aliww=''
	banpm=''
	signature=""
	honor=truename
	
	
	value=[id,username,email,password,groupid,memberid,icon,regdate,yz,medals,userstatus,apartment,signature,introduce,aliww,banpm,gender,honor]
	
	
	sql="select count(0) from pw_members where  username='"+str(username)+"'"
	cursormy.execute(sql)
	oldid=cursormy.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		#sql="update score_change_details set company_id=%s,name=%s,rules_code=%s,score=%s,gmt_created=%s,gmt_modified=%s where old_id="+str(id)+""
		#cursor.execute(sql,value);
	else:
		updateflag="新增"
		sql="insert into pw_members(uid,username,email,password,groupid,memberid,icon,regdate,yz,medals,userstatus,apartment,signature,introduce,aliww,banpm,gender,honor) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursormy.execute(sql,value);
	
	regip="61.234.184.252"
	
	sql="select count(0) from pw_memberdata where uid='"+str(id)+"'"
	cursormy.execute(sql)
	oldid=cursormy.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		#sql="update score_change_details set company_id=%s,name=%s,rules_code=%s,score=%s,gmt_created=%s,gmt_modified=%s where old_id="+str(id)+""
		#cursor.execute(sql,value);
	else:
		updateflag="新增"
		sql="INSERT INTO pw_memberdata (uid, postnum, digests, rvrc, money, credit, currency, lastvisit, thisvisit, lastpost, onlinetime, monoltime, todaypost, monthpost, uploadtime, uploadnum, follows, fans, newfans, newreferto, newcomment, onlineip, starttime, postcheck, pwdctime, f_num, creditpop, jobnum, lastmsg, lastgrab, punch, shafa, newnotice, newrequest, bubble) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		value=[id, 2, 0, 10, 12, 0, 0, 1344497216, 1344497216, 1344495287, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, '61.234.184.252|1344497216|6', 0, '', 0, 0, '', 4, 0, 0, 0, 1, 2, 0, '']
		cursormy.execute(sql,value);

	print updateflag+"成功"

