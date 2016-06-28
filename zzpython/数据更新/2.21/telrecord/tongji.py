import shutil
import sys,os
import time,datetime
from time import ctime, sleep
import urllib
from threading import Timer

execfile("/usr/apps/python/conn.py") 
execfile("/usr/apps/python/inc.py")

def tongji():
	nowdate=datetime.datetime.now().strftime('%Y-%m-%d')
	sql="select id,userid from users where userid like '13%' and closeflag=1  order by id desc"
	cursor.execute(sql)
	results = cursor.fetchall()
	for a in results:
		userid=a[1]
		personid=a[0]
		sqlc="select id from crm_Salestongji where personid="+str(personid)+" and tdates='"+str(nowdate)+"'"
		cursor.execute(sqlc)
		tongjlist=cursor.fetchone()
		if (tongjlist==None):
			sqlp="insert into crm_Salestongji(personid,userid,Tdate,tdates) values("+str(personid)+","+str(userid)+",'"+str(nowdate)+"','"+str(nowdate)+"')"
			cursor.execute(sqlp);
			conn.commit()
			#print "aa"
		sqls=" not EXISTS(select null from comp_sales where com_type=13 and com_id=temp_salescomp.com_id)"
		sqls+=" "
		wherestr=" where tdates='"+str(nowdate)+"' and personid="+str(personid)+""
		sql1="update crm_Salestongji set developCount=(select count(0) from temp_salescomp WHERE "+sqls+" and not EXISTS(select null from Comp_ZSTinfo where com_id=temp_salescomp.com_id)  and EXISTS(select com_id from crm_to4star where com_id=temp_salescomp.com_id and personid="+str(personid)+" and fdate='"+str(nowdate)+"')) "+wherestr
		sql2="update crm_Salestongji set ContactCount=(select count(0) from temp_salescomp WHERE "+sqls+" and EXISTS(select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="+str(personid)+" and DATEDIFF(day,teldate,'"+str(nowdate)+"')=0 and contacttype =13)) "+wherestr
		sql3="update crm_Salestongji set CallonlineCount=(select sum(recordTime) from crm_callRecord where personid="+str(personid)+" and DATEDIFF(day,startTime,'"+str(nowdate)+"')=0) "+wherestr
		sql4="update crm_Salestongji set CallCount=(select count(0) from crm_callRecord where personid="+str(personid)+" and DATEDIFF(day,startTime,'"+str(nowdate)+"')=0) "+wherestr
		cursor.execute(sql1);
		cursor.execute(sql2);
		cursor.execute(sql3);
		cursor.execute(sql4);
		conn.commit()
		print "suc"+str(personid)
			
tongji()
			
			
			
			
			
			