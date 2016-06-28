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

#execfile("/usr/tools/connbbs.py")
connt = MySQLdb.connect(host='192.168.110.118', user='asto_crm', passwd='zj88friend',db='asto_crm',charset='utf8')   
cursort = connt.cursor()
conn1=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
cursor1=conn1.cursor()
conn2=pymssql.connect(host=r'192.168.110.110\asto',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
cursor2=conn2.cursor()

updatetablename="offer_info_test"
execfile("/usr/tools/inc.py") 
def getmembership_code(com_id):
	sql="select id from comp_zstinfo where com_id="+str(com_id)+" and com_check=1"
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return 0
		comtype=0
	else:
		comtype=1
		
	if (comtype == 1):
		sql="select PPTGrade from comp_ppt where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return 1
		else:
			PPTGrade=str(newcode[0])
			if(PPTGrade == '0'):
				return 1
			elif(PPTGrade == '1'):
				return 2
			elif(PPTGrade == '2'):
				return 3
			elif(PPTGrade == '3'):
				return 4
			else:
				return 1
def getcom_subname(com_id):
	sql="select com_subname from comp_info where com_id="+str(com_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode[0]
def getcom_name(com_id):
	sql="select com_name from comp_info where com_id="+str(com_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode[0]
def getpdt_price(pdt_id):
	sql="select pdt_price,pdt_price_min,pdt_price_max,pdt_unit from products_customerPrice where pdt_id="+str(pdt_id)
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode

def getpdt_images(pdt_id):
	sql="select path from productimg_biao where pdt_id="+str(pdt_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return ''
	else:
		return newcode[0]

def getproductsid(id,code):
	sql="select label from category_products where oldid"+str(code)+"="+str(id)+" and is_assist=0"
	cursort.execute(sql)
	#print sql
	oldidstr1=cursort.fetchone()
	#print oldidstr1
	if (oldidstr1 == None):
		return '0'
	else:
		return oldidstr1[0]
def getptdsort(id,n):
	sql="select id,sort_id_b,subid,subid_s,sort_id from products_sort"+str(n)+" where pdt_id="+str(id)+" order by id desc"
	cursor1.execute(sql)
	oldidstr=cursor1.fetchone()
	if (oldidstr == None):
		return None
	else:
		sortidp1=str(oldidstr[1])
		sortidp2=str(oldidstr[2])
		sortidp3=str(oldidstr[3])
		sortidp4=str(oldidstr[4])	
	#print oldidstr
	return [[sortidp1,1],[sortidp2,2],[sortidp3,3],[sortidp4,4]]
def geclsvaluelist(oldidstr):
	notpdtid1=getproductsid(oldidstr[0][0],oldidstr[0][1])
	notpdtid2=getproductsid(oldidstr[1][0],oldidstr[1][1])
	notpdtid3=getproductsid(oldidstr[2][0],oldidstr[2][1])
	notpdtid4=getproductsid(oldidstr[3][0],oldidstr[3][1])
	notpdtid=''
	if (notpdtid1!='0'):
		notpdtid+=notpdtid1+'->'
	if (notpdtid2!='0'):
		notpdtid+=notpdtid2+'->'
	if (notpdtid3!='0'):
		notpdtid+=notpdtid3+'->'
	if (notpdtid4!='0'):
		notpdtid+=notpdtid4
	return notpdtid
def getproductsoldid(id):
	oldidstr=getptdsort(id,1)
	#print oldidstr
	if (oldidstr == None):
		notpdtid='0'
	else:
		notpdtid=geclsvaluelist(oldidstr)
	
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,2)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,3)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,4)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,5)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,6)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,10)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,12)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,14)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,15)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=geclsvaluelist(oldidstr)
	return notpdtid

#隐形类别
def gethiddencode(id):
	sql="select hid from products_hiddensort where pdt_id="+str(id)+""
	cursor1.execute(sql)
	oldidstr=cursor1.fetchone()
	if (oldidstr == None):
		return '0'
	else:
		hid=oldidstr[0]
		sql="select label from category_products where oldhiddenid="+str(hid)+" and is_assist=1"
		cursort.execute(sql)
		oldidstr1=cursort.fetchone()
		if (oldidstr1 == None):
			return '0'
		else:
			return oldidstr1[0]
#是否暂停发布
def getstoppost(id):
	sql="select count(0) from products_stop where pdt_id="+str(id)+""
	cursor1.execute(sql)
	oldidstr=cursor1.fetchone()
	if (oldidstr == None):
		return '0'
	else:
		return '1'
def _key_to_file(key):
	path = str(int(int(key) / 1000))
	return '/usr/tools/pro/'+path+'/'
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
	
def prolist(pdtid):
	connt = MySQLdb.connect(host='192.168.110.118', user='asto_crm', passwd='zj88friend',db='asto_crm',charset='utf8')   
	cursort = connt.cursor()
	
	getcompinfo(pdtid,cursort)
	
maxupdate=getlastid(updatetablename+"date")
if (maxupdate==''):
	maxupdate='0'
maxupid=getlastid(updatetablename)
if (maxupid==''):
	maxupid='0'
#
#convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') and pdt_id>"+getlastid(updatetablename)+"  pdt_time_en>=dateadd(d,-3,getdate())
sql = "select com_id,0,pdt_id,pdt_kind,pdt_name,pdt_com_province,pdt_detail,pdt_time_en,pdt_check,pdt_keywords"
sql +=" from products where pdt_id>"+getlastid(updatetablename)+" order by pdt_id asc"
cursor1.execute(sql)
results = cursor1.fetchmany(100000)


value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	com_id=str(a[0])
	com_name=changezhongwen(getcom_name(com_id))
	pdt_id=str(int(a[2]))
	pdt_kind=str(a[3])
	pdt_name=changezhongwen(a[4])
	com_province=changezhongwen(a[5])
	
	pdt_detail = changezhongwen(a[6])
	

	pdt_time_en=a[7]
	pdt_check=str(a[8])
	pdt_keywords=changezhongwen(a[9])
	com_subname=getcom_subname(com_id)
	pricelist=getpdt_price(pdt_id)
	if (pricelist!=''):
		pdt_price=changezhongwen(pricelist[0])
		pdt_price_min=pricelist[1]
		pdt_price_max=pricelist[2]
		pdt_unit=changezhongwen(pricelist[3])
	else:
		pdt_price=''
		pdt_price_min=0
		pdt_price_max=0
		pdt_unit=''
	if (pdt_keywords == None or str(pdt_keywords) == 'null'):
		pdt_keywords=''
		
	clslist=getproductsoldid(pdt_id)+'->'+gethiddencode(pdt_id)
	searchkeywords=str(pdt_name)+str(pdt_keywords)+str(com_province)+str(clslist)
	viptype=getmembership_code(com_id)
	pdt_images=changezhongwen(getpdt_images(pdt_id))
	if (pdt_images==''):
		pdt_imagesflag=0
	else:
		pdt_imagesflag=1
	offerstaus=getstoppost(pdt_id)
	#pdt_name_encode=base64.b64encode(pdt_name) 
	#sql="select max(maxid) from products"
	#cursort.execute(sql)
	#maxid=cursort.fetchone()
	#maxidcount=maxid[0]+1

	value=[com_id,com_name,pdt_id,pdt_kind,pdt_name,com_province,pdt_detail,pdt_time_en,pdt_check,com_subname,viptype,pdt_images,pdt_imagesflag,pdt_keywords,pdt_price,pdt_price_min,pdt_price_max,pdt_unit,clslist,searchkeywords,offerstaus]
	valueupdate=[com_id,com_name,pdt_kind,pdt_name,com_province,pdt_detail,pdt_time_en,pdt_check,com_subname,viptype,pdt_images,pdt_imagesflag,pdt_keywords,pdt_price,pdt_price_min,pdt_price_max,pdt_unit,clslist,searchkeywords,offerstaus]
	valueupdate1=[pdt_price,pdt_time_en,pdt_check]
	#-判断是否已经导
	sql="select count(0) from products where pdt_id="+str(pdt_id)+""
	cursort.execute(sql)
	oldid=cursort.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update products set com_id=%s,com_name=%s,pdt_kind=%s,pdt_name=%s,com_province=%s,pdt_detail=%s,pdt_time_en=%s,pdt_check=%s,com_subname=%s,viptype=%s,pdt_images=%s,pdt_imagesflag=%s,pdt_keywords=%s,pdt_price=%s,pdt_price_min=%s,pdt_price_max=%s,pdt_unit=%s,clslist=%s,searchkeywords=%s,offerstaus=%s where pdt_id="+str(pdt_id)
		#sql="update products set pdt_price=%s,pdt_time_en=%s,pdt_check=%s where pdt_id="+str(pdt_id)
		#updatelastid(updatetablename,pdt_id)
		#cursort.execute(sql,valueupdate);
		getcompinfo(pdt_id,cursort)
		updateflag="更新"
	else:
		sql="insert into products(com_id,com_name,pdt_id,pdt_kind,pdt_name,com_province,pdt_detail,pdt_time_en,pdt_check,com_subname,viptype,pdt_images,pdt_imagesflag,pdt_keywords,pdt_price,pdt_price_min,pdt_price_max,pdt_unit,clslist,searchkeywords,offerstaus)"
		sql=sql+"values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		#cursort.execute(sql,value);
		getcompinfo(pdt_id,cursort)
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	updatelastid(updatetablename,pdt_id)
	i+=1

try:
	#读取最新更新时间
	"""
	sql="select convert(bigint,max(gmt_editdate)) from products_temp"
	cursor1.execute(sql)
	oldid=cursor1.fetchone()
	editdate=oldid[0]
	print str(int(editdate))
	"""
	#updatelastid(updatetablename+"date",str(int(editdate)))
	#sql="update import_table set updatetime=(select convert(bigint,max(gmt_editdate)) from products_temp) where tablename='"+updatetablename+"'"
	#cursor1.execute(sql)
	#conn1.commit()
	#updatelastid(updatetablename,str('0'))
	"""
	sql="DELETE FROM products_vip"
	cursort.execute(sql)
	sql="INSERT INTO products_vip SELECT * FROM products WHERE pdt_time_en>=DATE_SUB(CURDATE(),INTERVAL 3 DAY) AND viptype>0 AND pdt_check=1"
	cursort.execute(sql)
	"""
	print changezhongwen("更新成功")
	conn1.close()
	conn2.close()
	connt.close()
except Exception, e:
	print e



