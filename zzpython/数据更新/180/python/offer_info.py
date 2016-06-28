#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
import base64,hashlib
from django.utils.http import urlquote
import random
import shutil
try:
    import cPickle as pickle
except ImportError:
    import pickle


reload(sys)
sys.setdefaultencoding('utf-8')
updatetablename="offer_info"
execfile("/usr/apps/python/inc.py")
connt = MySQLdb.connect(host='192.168.110.118', user='asto_crm', passwd='zj88friend',db='asto_crm',charset='utf8')   
cursort = connt.cursor()


#隐形类别

#是否暂停发布

def _key_to_file(key):
	path = str(int(int(key) / 1000))
	return '/usr/data/offerlist/'+path+'/'
def getproid(id):
	fname = _key_to_file(id)
	if not (os.path.exists(fname+str(id)+'.pkl')):
		return None
	else:
		pkl_file = open(fname+str(id)+'.pkl','rb')
		return pickle.load(pkl_file)
		pkl_file.close()
def updateproid(id,list):
	fname = _key_to_file(id)
	if not os.path.exists(fname):
		os.makedirs(fname)
	output = open(fname+str(id)+".pkl", 'wb')
	pick = pickle.Pickler(output)
	pick.dump(list)
	output.close()

def getcompinfo(pdtid,cursort):
	sql2="select com_id,com_name,pdt_id,pdt_kind,pdt_name,com_province,pdt_detail,pdt_time_en,com_subname,viptype,pdt_images,pdt_price,pdt_price_min,pdt_price_max,pdt_unit from products where pdt_id="+str(pdtid)
	cursort.execute(sql2)
	productlist = cursort.fetchone()
	#productlist = Models.Productlist.objects.filter(pdt_id=pdtid).values('com_id','com_name','pdt_id','pdt_kind','pdt_name','com_province','pdt_detail','pdt_time_en','pdt_check','com_subname','viptype','pdt_images','pdt_price','pdt_price_min','pdt_price_max','pdt_unit')
	
	list1=''
	if productlist:
		com_id=productlist[0]
		com_name=productlist[1]
		pdt_id=productlist[2]
		pdt_kind=productlist[3]
		pdt_name=productlist[4]
		pdt_name1=productlist[4]
		com_province=productlist[5]
		
		arrcom_province=com_province.split('|')
		if (len(arrcom_province)>1):
			if (arrcom_province[0]==arrcom_province[1]):
				com_province=arrcom_province[0]
			else:
				com_province=arrcom_province[0]+'|'+arrcom_province[1]

		pdt_detail=productlist[6]
		
		
		pdt_time_en=productlist[7]
		com_subname=productlist[8]
		viptype=str(productlist[9])
		pdt_images=productlist[10]
		pdt_price=productlist[11]
		pdt_price_min=productlist[12]
		pdt_price_max=productlist[13]
		pdt_unit=productlist[14]
		
		pdt_price=''
		if (pdt_price_min>0):
			pdt_price=str(int(pdt_price_min))
		if (pdt_price_max>0):
			pdt_price+='-'+str(int(pdt_price_max))
		pdt_price+=pdt_unit
		
		if (pdt_images == '' or pdt_images == '0'):
			pdt_images='../cn/img/noimage.gif'
			pdt_imagesflag = None
		else:
			pdt_images='http://img.zz91.com/cn/offer_picShow.asp?picurl=/upimages/'+pdt_images
			pdt_imagesflag=1
		arrviptype={'vippic':'','vipname':'','vipsubname':'','vipcheck':''}
		if (viptype == '0'):
			arrviptype['vippic']=None
			arrviptype['vipname']='普通会员'
		if (viptype == '1'):
			arrviptype['vippic']='http://img.zz91.com/zz91images/recycle.gif'
			arrviptype['vipname']='再生通'
		if (viptype == '2'):
			arrviptype['vippic']='http://img.zz91.com/zz91images/pptSilver.gif'
			arrviptype['vipname']='银牌品牌通'
		if (viptype == '3'):
			arrviptype['vippic']='http://img.zz91.com/zz91images/pptGold.gif'
			arrviptype['vipname']='金牌品牌通'
		if (viptype == '4'):
			arrviptype['vippic']='http://img.zz91.com/zz91images/pptDiamond.gif'
			arrviptype['vipname']='钻石品牌通'
		
		if (viptype == '0'):
			arrviptype['vipcheck']=None
		else:
			arrviptype['vipcheck']=1
		arrviptype['vipsubname'] = com_subname
		if (com_province != None):
			com_province=com_province.replace('|','<br>')
		
		arrpdt_kind={'kindtxt':'','kindclass':''}
		if (str(pdt_kind) == '2'):
			arrpdt_kind['kindtxt']='求购'
			arrpdt_kind['kindclass']='buy'
		else:
			arrpdt_kind['kindtxt']='供应'
			arrpdt_kind['kindclass']='sell'
		#pdt_time_en.strftime('%Y-%m-%d')
		pdt_time_en=pdt_time_en.strftime('%Y/%m/%d')
		if (pdt_price==None):
			pdt_price=''
		n = random.randint(1,6)
		list1={'com_id':com_id,'com_name':com_name,'pdt_id':pdt_id,'pdt_kind':arrpdt_kind,'pdt_name':pdt_name,'com_province':com_province,'pdt_detail':pdt_detail,'pdt_time_en':pdt_time_en,'com_subname':com_subname,'vipflag':'','pdt_images':pdt_images,'pdt_price':pdt_price,'vippaibian':'','pdt_name1':pdt_name1,'wordsrandom':n}
		list1['vipflag']=arrviptype
		updateproid(pdtid,list1)
	


maxupid=getlastid(updatetablename)
if (maxupid==''):
	maxupid='0'


#sql="select pdt_id from products where pdt_id>"+maxupid+""
sql="select pdt_id from products where pdt_time_en>=DATE_SUB(CURDATE(),INTERVAL 2 DAY)"
print sql
cursort.execute(sql)
results = cursort.fetchmany(200000)
i=0
for a in results:
	pdt_id=str(a[0])
	getcompinfo(pdt_id,cursort)
	updateflag="更新"
	updatelastid(updatetablename,pdt_id)
	print changezhongwen(updateflag+"成功"+str(i))
	i+=1

try:
	#读取最新更新时间

	
	print changezhongwen("更新成功")
	connt.close()
except Exception, e:
	print e



