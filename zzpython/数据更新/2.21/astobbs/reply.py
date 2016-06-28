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
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='zz91_bbs',charset=None)
cursor=conn.cursor()
connmy = MySQLdb.connect(host='192.168.2.21', user='root', passwd='zj88friend',db='phpwind',charset='utf8')     
cursormy = connmy.cursor()
execfile("/usr/apps/python/inc.py")
#--------------------------------------

def getusername(uid):
	sql="select username from bbs_users where id="+str(uid)+""
	cursor.execute(sql)
	newcodeid=cursor.fetchone()
	if (newcodeid == None):
		return '0'
	else:
		return newcodeid[0]
sql = "select id,r_title,r_content,r_replytime,r_subjectid,r_userid,r_boardid,DATEDIFF(s, '1970-01-01 00:00:00', r_replytime) from bbs_reply  order by id asc"
#print sql
cursor.execute(sql)
results = cursor.fetchall()

value=[]
i=0
for a in results:
	id=str(a[0])
	title=changezhongwen(a[1])
	content=changezhongwen(a[2])
	
	pid=id
	boardid=str(a[6])
	if (boardid=="7"):
		nboardid="9"
	if (boardid=="8"):
		nboardid="10"
	if (boardid=="1"):
		nboardid="11"
	if (boardid=="2"):
		nboardid="12"
	if (boardid=="3"):
		nboardid="13"
	if (boardid=="4"):
		nboardid="8"
	fid=nboardid
	tid=str(a[4])
	userid=str(a[5])
	
	author=getusername(userid)
	authorid=userid
	postdate=str(a[7])
	
	subject=title
	
	

	
	
	content=content.replace("/bbs/upload/","http://192.168.2.6:8081/bbs/upload/")
	content=content.replace("http://192.168.2.200:8081","http://192.168.2.6:8081")
	sql="select count(0) from pw_posts where pid='"+str(id)+"'"
	cursormy.execute(sql)
	oldid=cursormy.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"

	else:
		updateflag="新增"
		sql="INSERT INTO pw_posts (pid, fid, tid, aid, author, authorid, icon, postdate, subject, userip, ifsign, buy, alterinfo, remindinfo, leaveword, ipfrom, ifconvert, ifwordsfb, ifcheck, content, ifmark, ifreward, ifshield, anonymous, ifhide, frommob) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		value=[pid, fid, tid, 0, author, authorid, 0, postdate, subject, '61.234.184.252', 1, '', '', '', '', '浙江省杭州市\r\n', 1, 1, 1, content, '', 0, 0, 0, 0, 0]
		
		
		cursormy.execute(sql,value);

	
	print updateflag+"成功"

