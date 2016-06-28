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
			('membertype', {'type':'integer'} ),
			('com_id', {'type':'integer'} ),
			('cname', {'type':'text'} ),
			('comkeywords', {'type':'integer'} ),
			('province', {'type':'text'} ),
			('cproductslist', {'type':'text'} ),
			('ccontactp', {'type':'text'} ),
			('ctel', {'type':'text'} ),
			('cmobile', {'type':'text'} ),
			('cadd', {'type':'text'} ),		
		]

	def GetFieldOrder(self): #字段的优先顺序
		return ('com_email')
		
	def Connected(self):   #如果是数据库，则在此处做数据库连接
		if self.conn==None:	   
			self.conn = pymssql.connect(host=r'192.168.2.2', user='rcu_crm', password='fdf@$@#dfdf9780@#1.kdsfd', database='rcu_crm', as_dict=True)
			self.cur = self.conn.cursor()
			sql = "SELECT  id,com_email,membertype,com_id,cname,comkeywords,province,cproductslist,ccontactp,ctel,cmobile,cadd FROM huangye_list"
			self.cur.execute(sql)
			self.data = [ row for row in self.cur]
		pass

	def NextDocument(self):   #取得每一个文档记录的调用
		if self.idx < len(self.data):
			item = self.data[self.idx]
			self.id = self.threadid = item[0] #'docid':True
			self.com_email = item[1].decode('GB18030','ignore').encode('utf-8')
			self.membertype = item[2]
			self.com_id = item[3]
			if (item[4]):
				self.cname = item[4].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cname = ''
			self.comkeywords = item[5]
			if (item[6]):
				self.province = item[6].decode('GB18030','ignore').encode('utf-8')
			else:
				self.province = ''
			if (item[7]):
				self.cproductslist = "otherlist"+item[7].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cproductslist = ''
			if (item[8]):
				self.ccontactp = item[8].decode('GB18030','ignore').encode('utf-8')
			else:
				self.ccontactp = ''
			if (item[9]):
				self.ctel = item[9].decode('GB18030','ignore').encode('utf-8')
			else:
				self.ctel = ''
			if (item[10]):
				self.cmobile = item[10].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cmobile = ''
			if (item[11]):
				self.cadd = "otherprovince"+item[11].decode('GB18030','ignore').encode('utf-8')
			else:
				self.cadd = ''
			
			
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
