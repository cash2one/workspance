import shutil
import sys,os
import time,datetime
from time import ctime, sleep
import urllib
from threading import Timer

execfile("/usr/apps/python/conn.py") 
execfile("/usr/apps/python/inc.py")

def incalldata():
	#from-exten-sip,from-trunk-sip
	sql="select id,calldate,src,dst,dcontext,duration,billsec,userfield from cdr where disposition='ANSWERED' and lastapp='Dial' order by id desc limit 0,100"
	cursormy.execute(sql)
	results = cursormy.fetchall()
	for a in results:
		sqlc="select id from record_list where uniqueid='"+str(a[0])+"'"
		cursor.execute(sqlc)
		recordlist=cursor.fetchone()
		if (recordlist==None):
			id=a[0]
			startime=a[1]
			caller=a[2]
			accountcode=""
			answeredtime=a[6]
			called=a[3]
			dcontext=a[4]
			type=1
			userfield=a[7]
			if (dcontext=='from-exten-sip'):
				type=1
			else:
				type=2
			fid=userfield
			monitorfile=""
			if (fid!=None and fid!=""):
				sqlr="select filename from voicefiles where associate='"+str(fid)+"'"
				cursormy.execute(sqlr)
				flist=cursormy.fetchone()
				if (flist):
					monitorfile=flist[0]
				else:
					monitorfile=''
				print monitorfile
			sqli="insert into record_list(uniqueid,startime,caller,accountcode,answeredtime,called,type,monitorfile) "
			sqli+=" values('"+str(id)+"','"+str(startime)+"','"+str(caller)+"','"+str(accountcode)+"',"+str(answeredtime)+",'"+str(called)+"',"+str(type)+",'"+str(monitorfile)+"')"
			print sqli
			cursor.execute(sqli);
			conn.commit()
		else:
			id=a[0]
			userfield=a[7]
			fid=userfield
			monitorfile=""
			if (fid!=None and fid!=""):
				sqlr="select filename from voicefiles where associate='"+str(fid)+"'"
				cursormy.execute(sqlr)
				flist=cursormy.fetchone()
				if (flist):
					monitorfile=flist[0]
				else:
					monitorfile=''
			sqli="update record_list set monitorfile='"+monitorfile+"' where uniqueid="+str(id)+""
			cursor.execute(sqli);
			conn.commit()
			print id
incalldata()
"""
def timer_cb():
	global timer
	timer = Timer(5, timer_cb)
	incalldata()
	timer.start()
timer = Timer(5, timer_cb)  
timer.start()
sleep(3)
"""

