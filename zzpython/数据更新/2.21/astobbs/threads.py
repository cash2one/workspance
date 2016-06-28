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
sql = "select id,title,content,posttime,userid,boardid,hits,replyuserid,DATEDIFF(s, '1970-01-01 00:00:00', posttime),DATEDIFF(s, '1970-01-01 00:00:00', lastreplytime),replycount from v_articles  order by id desc"
#print sql
cursor.execute(sql)
results = cursor.fetchall()

value=[]
i=0
for a in results:
	id=str(a[0])
	title=changezhongwen(a[1])
	content=changezhongwen(a[2])
	posttime=str(a[3])
	userid=str(a[4])
	boardid=str(a[5])
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
	
	hits=str(a[6])
	lastreplyid=str(a[7])
	username=getusername(userid)
	if (lastreplyid!='None'):
		lastreplyusername=getusername(lastreplyid)
	else:
		lastreplyusername=""
	intposttime=str(a[8])
	intlastpost=str(a[9])
	replycount=str(a[10])
	
	sql="select count(0) from pw_threads where tid='"+str(id)+"'"
	cursormy.execute(sql)
	oldid=cursormy.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		sql="update pw_threads set lastpost=%s, lastposter=%s, hits=%s, replies=%s where tid=%s"
		valueu=[intlastpost,lastreplyusername,hits,replycount,id]
		cursormy.execute(sql,valueu);
	else:
		updateflag="新增"
		sql="INSERT INTO pw_threads (tid, fid, icon, titlefont, author, authorid, subject, toolinfo, toolfield, ifcheck, type, postdate, lastpost, lastposter, hits, replies, favors, modelid, shares, topped, topreplays, locked, digest, special, state, ifupload, ifmail, ifmark, ifshield, anonymous, dig, fight, ptable, ifmagic, ifhide, inspect, frommob, tpcstatus, specialsort) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		value=[id, nboardid, 0, '', username, userid, title, '', '', 1, 0, intposttime, intlastpost, lastreplyusername, hits, replycount, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0]
		cursormy.execute(sql,value);
	
	content=content.replace("/bbs/upload/","http://192.168.2.6:8081/bbs/upload/")
	content=content.replace("/bbs/editor/","http://192.168.2.6:8081/bbs/editor/")
	content=content.replace("http://192.168.2.200:8081","http://192.168.2.6:8081")
	
	sql="select count(0) from pw_tmsgs where tid='"+str(id)+"'"
	cursormy.execute(sql)
	oldid=cursormy.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		updateflag="更新"
		#sql="update score_change_details set company_id=%s,name=%s,rules_code=%s,score=%s,gmt_created=%s,gmt_modified=%s where old_id="+str(id)+""
		#cursor.execute(sql,value);
	else:
		updateflag="新增"
		sql="INSERT INTO pw_tmsgs (tid, aid, userip, ifsign, buy, ipfrom, alterinfo, remindinfo, tags, ifconvert, ifwordsfb, content, form, ifmark, c_from, magic, overprint) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		value=[id, 0, '61.234.184.252', 1, '', '浙江省杭州市\r\n', '', '', '	', 1, 1, content, '', '', '', '', 0]
		cursormy.execute(sql,value);

	
	print updateflag+"成功"

