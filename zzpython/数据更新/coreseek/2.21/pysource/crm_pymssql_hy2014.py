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
			('newemail', {'type':'text'} ),
			('membertype', {'type':'integer'} ),
			('com_id', {'type':'integer'} ),
			('cname', {'type':'text'} ),
			('comkeywords', {'type':'integer'} ),
			('js1', {'type':'text'} ),
			('js2', {'type':'text'} ),
			('sl1', {'type':'text'} ),
			('sl2', {'type':'text'} ),
			('province', {'type':'text'} ),
			('cproductslist', {'type':'text'} ),
			('ccontactp', {'type':'text'} ),
			('ctel', {'type':'text'} ),
			('cmobile', {'type':'text'} ),
			('cadd', {'type':'text'} ),	
			('pcheck', {'type':'integer'} ),
			('huangye_qukan', {'type':'integer'} ),
		]

	def GetFieldOrder(self): #字段的优先顺序
		return ('com_email')
		
	def Connected(self):   #如果是数据库，则在此处做数据库连接
		if self.conn==None:	   
			self.conn = pymssql.connect(host=r'192.168.2.2', user='rcu_crm', password='fdf@$@#dfdf9780@#1.kdsfd', database='rcu_crm', as_dict=True)
			self.cur = self.conn.cursor()
			sql = "SELECT  id,com_email,newemail,membertype,com_id,cname,comkeywords,js1,js2,sl1,sl2,province,cproductslist,ccontactp,ctel,cmobile,cadd,pcheck,huangye_qukan FROM huangye_list where huangye_qukan='2014'"
			self.cur.execute(sql)
			self.data = [ row for row in self.cur]
		pass

	def NextDocument(self):   #取得每一个文档记录的调用
		if self.idx < len(self.data):
			item = self.data[self.idx]
			self.id = self.threadid = item[0] #'docid':True
			if (item[1] and item[1]!=""):
				self.com_email = item[1].decode('GB18030','ignore').encode('utf-8')
			else:
				self.com_email = ''
			if (item[2] and item[2]!=""):
				self.newemail = item[2].decode('GB18030','ignore').encode('utf-8')
			else:
				self.newemail = ''
			self.membertype = item[3]
			self.com_id = item[4]
			if (item[5]):
				self.cname = item[5].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cname = ''
			self.comkeywords = item[6]
			if (item[7]):
				self.js1 = item[7].decode('GB18030','ignore').encode('utf-8')
			else:
				self.js1 = ''
			if (item[8]):
				self.js2 = item[8].decode('GB18030','ignore').encode('utf-8')
			else:
				self.js2 = ''
			if (item[9]):
				self.sl1 = item[9].decode('GB18030','ignore').encode('utf-8')
			else:
				self.sl1 = ''
			if (item[10]):
				self.sl2 = item[10].decode('GB18030','ignore').encode('utf-8')
			else:
				self.sl2 = ''
				
			if (item[11]):
				self.province = "otherprovince"+item[11].decode('GB18030','ignore').encode('utf-8')
			else:
				self.province = ''
			if (item[12]):
				self.cproductslist = "otherlist"+item[12].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cproductslist = ''
			if (item[13]):
				self.ccontactp = item[13].decode('GB18030','ignore').encode('utf-8')
			else:
				self.ccontactp = ''
			if (item[14]):
				self.ctel = item[14].decode('GB18030','ignore').encode('utf-8')
			else:
				self.ctel = ''
			if (item[15]):
				self.cmobile = item[15].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cmobile = ''
			if (item[16]):
				self.cadd = "otherprovince"+item[16].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cadd = ''
			self.pcheck = item[17]
			self.huangye_qukan = item[18]
			
			
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
