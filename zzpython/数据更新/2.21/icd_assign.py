#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import time,datetime
from datetime import timedelta, date
import struct
import os
from sphinxapi import *

#---数据库连接和配置
nowpath="/usr/apps/python"
import pymssql
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='rcu_crm',charset=None)
cursor=conn.cursor()
execfile(nowpath+"/inc.py")

def assign():
	sql="select lastcontacttime1,lastcontacttime2,lastlogintime1,lastlogintime2,regtime1,regtime2,province,togonghaitime1,togonghaitime2,telcount1,notelcount1,telcount2,notelcount2,comrank,trade,star5,star4,logincount1,logincount2,adminreg,assign_time,telpersoncount1,telpersoncount2,id,assigncount from icd_gonghai_assign where assignflag=0"
	cursor.execute(sql)
	results = cursor.fetchall()
	if results:
		for a in results:
			lastcontacttime1=a[0]
			lastcontacttime2=a[1]
			lastlogintime1=a[2]
			lastlogintime2=a[3]
			regtime1=a[4]
			regtime2=a[5]
			province=changezhongwen(a[6])
			togonghaitime1=a[7]
			togonghaitime2=a[8]
			telcount1=a[9]
			telnocount1=a[10]
			telcount2=a[11]
			telnocount2=a[12]
			comrank=a[13]
			trade=changezhongwen(a[14])
			star5=a[15]
			star4=a[16]
			logincount1=a[17]
			logincount2=a[18]
			adminreg=a[19]
			assign_time=a[20]
			telpersoncount1=a[21]
			telpersoncount2=a[22]
			id=a[23]
			assigncount=a[24]

			#销售数
			sqlu="select count(id) from users where userid like  '13__' and closeflag=1 and assigngonghai=1 and chatflag=1 and chatclose=1"
			cursor.execute(sqlu)
			reusers=cursor.fetchone()
			if reusers:
				usercount=reusers[0]
			if (usercount>0):
				port = 9315
				
				cl = SphinxClient()
				cl.SetServer ('192.168.2.21', port)
				cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
				cl.SetFilter('personid',[0])
				cl.SetFilter('zst',[0])
				cl.SetFilter('vap',[0])
				
				query=""
				
				#最后联系时间
				if (lastcontacttime1):
					fromlasttelf=datediff('1970-1-1',lastcontacttime1)
				else:
					fromlasttelf=0

				if (lastcontacttime2):
					fromlasttelt=datediff('1970-1-1',lastcontacttime2)
				else:
					fromlasttelt=datediff('1970-1-1',getToday().strftime( '%-Y-%-m-%-d'))

				
				if (lastcontacttime1):
					cl.SetFilterRange('teldate1',int(fromlasttelf),int(fromlasttelt))
					
				#最后登录时间
				if (lastlogintime1):
					Lfromdatef=datediff('1970-1-1',lastlogintime1)
				else:
					Lfromdatef=0

				if (lastlogintime2):
					Lfromdatet=datediff('1970-1-1',lastlogintime2)
				else:
					Lfromdatet=datediff('1970-1-1',getToday().strftime( '%-Y-%-m-%-d'))

				if (lastlogintime1):
					cl.SetFilterRange('lastlogintime1',int(Lfromdatef),int(Lfromdatet))
				#注册时间
				if (regtime1):
					Regfromdate1=datediff('1970-1-1',regtime1)
				else:
					Regfromdate1=0

				if (regtime2):
					Regtodate1=datediff('1970-1-1',regtime2)
				else:
					Regtodate1=datediff('1970-1-1',getToday().strftime( '%-Y-%-m-%-d'))

				if (regtime1):
					cl.SetFilterRange('com_regtime1',int(Regfromdate1),int(Regtodate1))
				#省份
				if (province and province!='' and province!='请选择...'):
					query+='@(com_province,com_add) '+province

				#丢公海时间
				if (togonghaitime1):
					togonghaitime1=datediff('1970-1-1',togonghaitime1)
				else:
					togonghaitime1=0
				if (togonghaitime2):
					togonghaitime2=datediff('1970-1-1',togonghaitime2)
				else:
					togonghaitime2=datediff('1970-1-1',getToday().strftime( '%-Y-%-m-%-d'))
				if (togonghaitime1):
					cl.SetFilterRange('outtime',int(togonghaitime1),int(togonghaitime2))
					
				#联系次数
				if (telcount1 and telcount2):
					cl.SetFilterRange('telcount',int(telcount1),int(telcount2))
				#无效联系次数
				if (telnocount1 and telnocount2):
					cl.SetFilterRange('telnocount',int(telnocount1),int(telnocount2))
				
				#联系人数
				if (telpersoncount1 and telpersoncount2):
					cl.SetFilterRange('telpersoncount',int(telpersoncount1),int(telpersoncount2))
				#客户等级
				if (comrank and comrank!=""):
					frank=float(comrank)-0.01
					trank=float(comrank)
					cl.SetFilterFloatRange('com_rank',frank,trank)
				#主营业务
				if (trade and trade!='' and trade!=' '):
					query+='@com_productslist_en '+trade
		
				#登录次数
				if (logincount1 and logincount2):
					cl.SetFilterRange('logincount',int(logincount1),int(logincount2))
					
				#是否录入
				if(adminreg=="1"):
					cl.SetFilterRange('addcompany',1,10000000)
				#曾4、5星
				if (star4):
					cl.SetFilter('have4star',[1])
				if (star5):
					cl.SetFilter('have5star',[1])

				cl.SetSortMode( SPH_SORT_EXTENDED,"com_regtime2 desc")
				cl.SetLimits (0,10000,10000)
				res = cl.Query (query,'crminfo')
				listcount=0
				personid=0
				n=0
				m=0

				if (res):
					listcount=res['total_found']
					print "分配总是："+str(listcount)
					listcount_all=listcount
					if res.has_key('matches'):
						tagslist=res['matches']
						listall=[]
						for match in tagslist:
							com_id=match['id']
							#是否已经存在公海分配库里
							sqlp="select com_id from crm_assign_gonghai where com_id="+str(com_id)+""
							cursor.execute(sqlp)
							assingflag_gonghai=cursor.fetchone()
							
							sqlp="select com_id from crm_assign where com_id="+str(com_id)+""
							cursor.execute(sqlp)
							assingflag=cursor.fetchone()
							if (assingflag==None and personid!=None and assingflag_gonghai==None and m<=int(assigncount)):
								sqlt="select min(id) from users where userid like  '13__' and closeflag=1 and assigngonghai=1 and chatflag=1 and chatclose=1 and id>"+str(personid)+""
								cursor.execute(sqlt)
								puser=cursor.fetchone()
								if puser:
									personid=puser[0]
								n=n+1
								if (personid!=None):
									sqli="insert into crm_assign_gonghai (com_id,personid,pid) values(%s,%s,%s)"
									cursor.execute(sqli,(com_id,personid,id))
									conn.commit()
									m=m+1

									sqli="insert into crm_assign (com_id,personid) values(%s,%s)"
									cursor.execute(sqli,(com_id,personid))
									conn.commit()
									sqlo="insert into crm_assignHistory(com_id,personid,SDetail,mypersonid) values("+str(com_id)+",0,'gonghai system assign',"+str(personid)+")"
									cursor.execute(sqlo)
									conn.commit()
									if (n>=usercount):
										personid=0
										n=0	
			sqlp="select count(0) from crm_assign_gonghai where pid="+str(id)+""
			cursor.execute(sqlp)
			assingcount=cursor.fetchone()[0]
			if (listcount==assingcount):
				sqlh="update icd_gonghai_assign set assignflag=1 where id="+str(id)
				cursor.execute(sqlh)
				conn.commit()
	
assign()		