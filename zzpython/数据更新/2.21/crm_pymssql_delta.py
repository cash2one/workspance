# -*- coding:utf-8 -*-
# coreseek3.2 python source演示操作mssql数据库
# author: HonestQiao
# date: 2010-06-01 10:05

from os import path
import os
import codecs
import sys
import pymssql
import datetime

class mainSource(object):
	def __init__(self, conf):
		self.conf =  conf
		self.idx = 0
		self.data = []
		self.conn = None
		self.cur = None

	def GetScheme(self):  #获取结构，docid、文本、整数
		return [
			('id' , {'docid':True, } ),
			('com_email', {'type':'text'} ),
			('com_name', {'type':'text'} ),
			('com_tel', {'type':'text'} ),
			('com_province', {'type':'text'} ),
			('com_mobile', {'type':'text'} ),
			('vipflag', {'type':'integer'} ),
			('com_rank', {'type':'float'} ),
			('com_regtime', {'type':'text'} ),
			('lastlogintime', {'type':'integer'} ),
			('teldate', {'type':'integer'} ),
			('contacttype', {'type':'integer'} ),
			('com_ctr_id', {'type':'integer'} ),
			('com_zip', {'type':'text'} ),
			('com_contactperson', {'type':'text'} ),
			('com_desi', {'type':'text'} ),
			('com_add', {'type':'text'} ),
			('com_intro', {'type':'text'} ),
			('com_productslist_en', {'type':'text'} ),
			('personid', {'type':'integer'} ),
			('com_regtime1', {'type':'integer'} ),
			('teldate1', {'type':'integer'} ),
			('lastlogintime1', {'type':'integer'} ),
			('logincount', {'type':'integer'} ),
			('teldate2', {'type':'integer'} ),
			('lastlogintime2', {'type':'integer'} ),
			('com_regtime2', {'type':'integer'} ),
			('zst', {'type':'integer'} ),
			('vap', {'type':'integer'} ),
			('addcompany', {'type':'integer'} ),
			('telcount', {'type':'integer'} ),
			('telpersoncount', {'type':'integer'} ),
			('outtime', {'type':'integer'} ),
			('have4star', {'type':'integer'} ),
			('have5star', {'type':'integer'} ),
			('telnocount', {'type':'integer'} ),
			('assignflag', {'type':'integer'} ),
			
			
		]

	def GetFieldOrder(self): #字段的优先顺序
		return ('com_email')
		
	def Connected(self):   #如果是数据库，则在此处做数据库连接
		if self.conn==None:	   
			self.conn = pymssql.connect(host=r'192.168.2.2', user='rcu_crm', password='fdf@$@#dfdf9780@#1.kdsfd', database='rcu_crm', as_dict=True)
			self.cur = self.conn.cursor()
			sql = "SELECT  com_id,com_email,com_name,com_tel,com_province,com_mobile,vipflag,com_rank,com_regtime,lastlogintime,teldate,contacttype,com_ctr_id,com_zip,com_contactperson,com_desi,com_add,com_productslist_en,personid,DATEDIFF(d, '1970-01-01 00:00:00', teldate) as teldate1,DATEDIFF(d, '1970-01-01 00:00:00', lastlogintime) as lastlogintime1,com_intro,DATEDIFF(d, '1970-01-01 00:00:00', com_regtime) as com_regtime1,logincount,DATEDIFF(n, '1970-01-01 00:00:00', com_regtime) as com_regtime2,DATEDIFF(n, '1970-01-01 00:00:00', lastlogintime) as lastlogintime2,DATEDIFF(n, '1970-01-01 00:00:00', teldate) as teldate2,zst,vap,addcompany,telcount,telpersoncount,DATEDIFF(d, '1970-01-01 00:00:00', outtime) as outtime1,have4star,have5star,telnocount,assignflag FROM v_salescomp_search where DATEDIFF(CURDATE(),c.gmt_last_login)<=5"
			self.cur.execute(sql)
			self.data = [ row for row in self.cur]
		pass

	def NextDocument(self):   #取得每一个文档记录的调用
		if self.idx < len(self.data):
			item = self.data[self.idx]
			self.id = self.threadid = item[0] #'docid':True
			self.com_email = item[1].decode('GB18030','ignore').encode('utf-8')
			if (item[2]):
				self.com_name = item[2].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_name = ''
			if (item[3]):
				self.com_tel = item[3].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_tel = ''
			if (item[4]):
				self.com_province = item[4].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_province=''
			if (item[5]):
				self.com_mobile = item[5].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_mobile = ''
			self.vipflag = item[6]
			self.com_rank = item[7]
			self.com_regtime = item[8]
			self.lastlogintime = item[9]
			self.teldate = item[10]
			self.contacttype = item[11]
			self.com_ctr_id = item[12]
			if (item[13]):
				self.com_zip = item[13].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_zip=''
			if (item[14]):
				self.com_contactperson = item[14].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_contactperson = ''
			if (item[15]):
				self.com_desi = item[15].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_desi = ''
			if (item[16]):
				self.com_add = item[16].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_add = ''
			if (item[17]):
				self.com_productslist_en = item[17].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_productslist_en = ''
			if (item[21]):
				self.com_intro = item[21].decode('GB18030','ignore').encode('utf-8')[0:2000]
			else:
				self.com_intro = ''
			
			self.personid = item[18]
			
			self.teldate1 = item[19]
			if item[20]:
				self.lastlogintime1 = item[20]
			else:
				self.lastlogintime1 = -100
			self.com_regtime1 = item[22]
			self.logincount = item[23]
			self.teldate2 = item[26]
			if (str(item[25])!='None' and str(item[25])!='Null'):
				self.lastlogintime2 = item[25]
			else:
				self.lastlogintime2 = -100
			self.com_regtime2 = item[24]
			
			if (item[27]):
				self.zst = 1
			else:
				self.zst = 0
			if (item[28]):
				self.vap = 1
			else:
				self.vap = 0
			if (item[29]):
				self.addcompany = 1
			else:
				self.addcompany = 0
			if (item[30]):
				self.telcount = item[30]
			else:
				self.telcount = 0
			if (item[31]):
				self.telpersoncount = item[31]
			else:
				self.telpersoncount = 0
			if (str(item[32])!='None' and str(item[32])!='Null'):
				self.outtime = item[32]
			else:
				self.outtime = 0
			if (item[33]):
				self.have4star = 1
			else:
				self.have4star = 0
			if (item[34]):
				self.have5star = 1
			else:
				self.have5star = 0
			if (item[35]):
				self.telnocount = item[35]
			else:
				self.telnocount = 0
			if (item[36]):
				self.assignflag = 1
			else:
				self.assignflag = 0
			self.idx += 1
			return True
		else:
			return False

if __name__ == "__main__":	#直接访问演示部分
	conf = {}
	source = mainSource(conf)
	source.Connected()

	while source.NextDocument():
		print "id=%d, com_email=%s" % (source.id, source.ftitle)
	pass
#eof
