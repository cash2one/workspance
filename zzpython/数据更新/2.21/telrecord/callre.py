import shutil
import sys,os
import time,datetime
from time import ctime, sleep
import urllib
from threading import Timer
from sphinxapi import *

execfile("/usr/apps/python/conn.py") 
execfile("/usr/apps/python/inc.py")

def getlytel(lytel,mytel,tdate):
	sqltl="select top 1 id from record_list where "
	if (mytel!='' and mytel!=None):
		sqltl+=" ((called like '%"+lytel+"%' and caller='"+str(mytel)+"') or (caller like '%"+lytel+"%' and called='"+str(mytel)+"'))"
	else:
		sqltl+=" ((called like '%"+lytel+"%') or (caller like '%"+lytel+"%'))"
	if (tdate!=None):
		sqltl+=" and DATEDIFF(mi,startime,'"+str(tdate)+"')<30 and DATEDIFF(mi,startime,'"+str(tdate)+"')>0 "
	sqltl+="  order by id desc"
	cursor.execute(sqltl)
	recordlist=cursor.fetchone()
	if recordlist:
		return recordlist[0]
	else:
		return ''
def getusertel(personid):
	sqlc="select usertel from users where id='"+str(personid)+"'"
	cursor.execute(sqlc)
	userresults=cursor.fetchone()
	if userresults:
		return userresults[0]
	else:
		return 0
def getcompanytel(comid):
	sqlc="select com_tel,com_mobile from comp_info where com_id="+str(comid)+""
	cursor.execute(sqlc)
	comresults=cursor.fetchone()
	if comresults:
		return comresults
	else:
		return None

		
def updaterecord():
	sqlc="select top 200 id,com_id,personid,teldate from comp_tel where not exists(select telid from temp_telid where comp_tel.id=telid) order by id desc"
	cursor.execute(sqlc)
	results = cursor.fetchall()
	for a in results:
		personid=a[2]
		com_id=a[1]
		tid=a[0]
		fdate=a[3]
		mytel=getusertel(personid)
		comtellist=getcompanytel(com_id)
		if comtellist:
			comtel=comtellist[0]
			commobile=comtellist[1]
		else:
			comtel=""
			commobile=""
		comtel1=""
		comtel2=""
		comtel3=""
		comtel4=""
		comtelly=""
		if (comtel!="" and comtel!=None):
			comtel=comtel.replace(" ","").replace("-","")
			comtel1=comtel[-7:][:-1]
			comtelly=getlytel(comtel1,mytel,fdate)
		if (comtelly==""):
			if (commobile!="" and commobile!=None):
				commobile=commobile.replace(" ","")
				comtel2=commobile[-9:][:-1]
				comtelly=getlytel(comtel2,mytel,fdate)
		
		if (comtelly==""):
			sqlt="select top 1 persontel,PersonMoblie from crm_PersonInfo where com_id="+str(com_id)+""
			cursor.execute(sqlt)
			comresults=cursor.fetchone()
			if comresults:
				persontel=comresults[0]
				PersonMoblie=comresults[1]
				if (persontel!="" and persontel!=None):
					comtel3=comresults[0].replace(" ","").replace("-","")
					comtel3=comtel3[-7:][:-1]
					comtelly=getlytel(comtel3,mytel,fdate)
				if (comtelly==""):
					if (PersonMoblie!="" and PersonMoblie!=None):
						comtel4=PersonMoblie.replace(" ","")
						comtel4=comtel4[-9:][:-1]
						comtelly=getlytel(comtel4,mytel,fdate)
		
		sqlteltemp=""

		if (comtelly!=""):
			sqlteltemp+=" id="+str(comtelly)+" "
		if (sqlteltemp!=""):
			#---是CRM里的电话
			notel=0
		else:
			#---不是CRM里的电话
			notel=1
		
		if (notel==0):
			RecordNo=""
			sqlt="select id,startime,caller,accountcode,answeredtime,called,monitorfile,type from record_list where ("+sqlteltemp+")"
			#if (mytel!=""):
			#	sqlt+=" and caller='"+str(mytel)+"' "
			sqlt+=" order by id desc"
			cursor.execute(sqlt)
			comresults=cursor.fetchone()
			if comresults:
				RecordNo=comresults[0]
				CallerId=comresults[2]
				CallType=comresults[7]
				startTime=comresults[1]
				endTime=""
				recordTime=comresults[4]
				filePath=comresults[6]
				Dtmf=comresults[5]
				
				#-----写入到录音记录表
				if (RecordNo!=""):
					sqle="select id from crm_callrecord where RecordNo='"+str(RecordNo)+"'"
					cursor.execute(sqle)
					recordresults=cursor.fetchone()
					if (recordresults==None):
						#print RecordNo
						sqlq="insert into crm_callrecord(telid,com_id,personid,mytel,RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath,Dtmf) values("+str(tid)+","+str(com_id)+","+str(personid)+",'"+str(mytel)+"','"+str(RecordNo)+"','"+str(CallerId)+"','"+str(CallType)+"','"+str(startTime)+"','"+str(endTime)+"','"+str(recordTime)+"','"+filePath+"','"+Dtmf+"')"
						#print sqlq
						cursor.execute(sqlq);
						conn.commit()
						print("写入成功")

				sql="insert into temp_telid(telid) values("+str(tid)+")"
				cursor.execute(sql);
				conn.commit()

def timer_cb():
	global timer
	updaterecord()
	timer = Timer(5, timer_cb)
	timer.start()
timer = Timer(5, timer_cb)  
timer.start()
sleep(3)

#newupdaterecord()
	