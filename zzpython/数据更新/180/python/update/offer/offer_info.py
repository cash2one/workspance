#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
cursor2=conn_others.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------



updatetablename="offer_info"
print changezhongwen('更新'+updatetablename)
def getexpiretime(expirtime,posttime):
	if (expirtime == None):
		return posttime
	elif(str(expirtime) == '-1'):
		return "9999-12-31 23:59:59"
	elif(str(expirtime) == ''):
		return posttime
	elif(str(expirtime).isdigit()):
		d1=datetime.date(posttime.year, posttime.month, posttime.day)
		d2=datetime.timedelta(days=int(expirtime))
		return d1+d2
	else:
		return posttime
		
		
#3\products_type_code
#4\source_type_code
#10\is_show_in_price  在products_notShowPrice 里=0

#instruction 1、 需要手工分拣 2、可直接加工生产 0无加工要求
#adminuser>0 post_type=1 后台发布 post_type=0 前台发布
#pdt_unit  quantity_unit  由 pdt_price 进行拆分 或从 products_customerPrice 取数据
#is_pause  在 products_stop 里 =1
#category_products_main_code 、 category_products_assist_code 到 products_sort 里找
#msg_id>0 is_post_from_inquiry=1
#check_person 到product_sh里去对应数据
#pdt_check=1 then check_status=1    \  pdt_check=0 then check_status=0  \  pdt_check=3 then check_status=2    在 products_stop 里 =1  check_status=3
#pdt_check=0 then unchecked_check_status  =0 \  pdt_check>0 then unchecked_check_status=1
#unpass_reason 到 products_check  里读最近的一条原因（bz）   ----需要有历史原因
#供求审核也需要有历史审核人
#check_time  products_sh表里的fdate
#expire_time 根据 pdt_postlimittime 算时间

def getproductsid(id,code):
	sql="select code from category_products where oldid"+code+"="+str(id)+" and is_assist=0"
	cursor.execute(sql)
	#print sql
	oldidstr1=cursor.fetchone()
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
		if (str(oldidstr[4]) == '0'):
			sorttype='3'
			sortidp=str(oldidstr[3])
		elif(str(oldidstr[3]) == '0'):
			sorttype='2'
			sortidp=str(oldidstr[2])
		elif(str(oldidstr[2]) == '0'):
			sorttype='1'
			sortidp=str(oldidstr[1])
		else:
			sorttype='4'
			sortidp=str(oldidstr[4])
	#print oldidstr
	return [sortidp,sorttype]
	
def getproductsoldid(id):
	oldidstr=getptdsort(id,1)
	#print oldidstr
	if (oldidstr == None):
		notpdtid='0'
	else:
		notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,2)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,3)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,4)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,5)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,6)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,10)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,12)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,14)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
	if (notpdtid == '0'):
		oldidstr=getptdsort(id,15)
		if (oldidstr == None):
			notpdtid='0'
		else:
			notpdtid=getproductsid(oldidstr[0],oldidstr[1])
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
		sql="select code from category_products where oldhiddenid="+str(hid)+" and is_assist=1"
		cursor.execute(sql)
		oldidstr1=cursor.fetchone()
		if (oldidstr1 == None):
			return '0'
		else:
			return oldidstr1[0]

def getpricevalue(id):
	sql="select pdt_price_min,pdt_price_max,pdt_unit from products_customerPrice where pdt_id="+str(id)+""
	cursor1.execute(sql)
	oldidstr=cursor1.fetchone()
	if (oldidstr == None):
		return '0'
	else:
		return [oldidstr[0],oldidstr[1],oldidstr[2]]

def getusername(com_id):
	sql="select Com_UserName,Com_Email from comp_loading where com_id="+str(com_id)+""
	cursor1.execute(sql)
	newcode=cursor1.fetchone()
	if (newcode == None):
		return 'admin'
	else:
		username=changezhongwen(str(newcode[0]))
		email=changezhongwen(str(newcode[1]))
		if (username == 'None' or username==''):
			username=email
		return username

#sql = "select top 10000 pdt_id as id, com_id, pdt_comemail, pdt_kind, 0, pdt_name, pdt_detail, location, supplyType, supplyTotal,"
#sql +=" 0, pdt_unit, pdt_price, pdt_unit, quantity, source, standard, sourcePro, otherContain, color, "
#sql +=" [use], looks, instruction, pdt_remark, adminuser, 0, fabuflag, msg_id, 0, 0, "
#sql +=" 0, 0, pdt_check, 1, 0, 0, pdt_time, pdt_time_en, pdt_postlimittime, pdt_time, "
#sql +=" Futures,convert(bigint,gmt_editdate),noshowPrice,pstop,sh_name,sh_date"
#sql +=" from v_products_allinfo where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"
#print sql
#and pdt_id>"+getlastid(updatetablename)+"
sql = "select top 100000 pdt_id, com_id, pdt_name, pdt_detail, pdt_time, "
sql +="pdt_time_en, pdt_comemail, pdt_kind, pdt_check, pdt_keywords, "
sql +="pdt_image, pdt_price, pdt_unit, adminuser, msg_id, "
sql +="fabuflag,pdt_postlimittime, convert(bigint,gmt_editdate), Location, SupplyType, "
sql +="SupplyTotal, standard, Futures, noshowPrice, pstop,"
sql +="sh_name, sh_date, Bz, Quantity, Source, "
sql +="SourcePro, OtherContain, Color, [use], Looks, "
sql +="pdt_remark,instruction"
sql +=" from v_products_allinfo where convert(bigint,gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"')  order by gmt_editdate asc"

cursor1.execute(sql)
results = cursor1.fetchall()

editdate=0
value=[]
values=[]
valueupdate=[]
valueupdates=[]
i=0
for a in results:
	id=str(a[0])
	company_id=str(a[1])
	account=getusername(company_id)
	products_type_code=str(a[7])
	if (products_type_code == '1'):
		products_type_code='10331000'
	else:
		products_type_code='10331001'
	
	source_type_code=0
	title=changezhongwen(a[2])
	details=changezhongwen(a[3])
	location=changezhongwen(a[18])
	provide_status=str(a[19])
	total_quantity=changezhongwen(a[20])
	is_show_in_price=str(a[20])
	if (is_show_in_price!='None'):
		is_show_in_price=0
	else:
		is_show_in_price=1
	price_unit=changezhongwen(a[12])
	price=changezhongwen(a[11])
	quantity_unit=''
	quantity=changezhongwen(a[28])
	source=changezhongwen(a[29])
	specification=changezhongwen(a[21])
	origin=changezhongwen(a[30])
	impurity=changezhongwen(a[31])
	color=changezhongwen(a[32])
	useful=changezhongwen(a[33])
	appearance=changezhongwen(a[34])
	manufacture=a[36]
	if (manufacture=='1'):
		manufacture='需要手工分拣'
	if (manufacture=='2'):
		manufacture='可直接加工生产'
	if (manufacture=='3'):
		manufacture='无'
	remark=''
	post_type='1'
	is_pause=str(a[24])
	if (is_pause=='None'):
		is_pause=0
	else:
		is_pause=1
	is_post_when_view_limit=str(a[15])
	is_post_from_inquiry=str(a[14])
	if (is_post_from_inquiry=='0'):
		is_post_from_inquiry=0
	else:
		is_post_from_inquiry=1
	is_del=0
	category_products_main_code=getproductsoldid(id)
	category_products_assist_code=gethiddencode(id)
	check_person=str(a[13])
	check_status=str(a[8])
	if (check_status == '1'):
		check_status='1'
	elif(check_status == '0'):
		check_status='0'
	elif(pdt_check == '3'):
		check_status='2'
	else:
		check_status='0'
	if (is_pause == 1):
		check_status='3'
	
	unchecked_check_status=1

	unpass_reason=a[32]
	check_time=a[31]
	real_time=a[4]
	refresh_time=a[5]
	print refresh_time
	expire_time=getexpiretime(a[16],a[5])
	gmt_created=a[4]
	gmt_modified=a[5]
	arrpricevalue=getpricevalue(id)
	if (arrpricevalue != '0'):
		min_price=arrpricevalue[0]
		max_price=arrpricevalue[1]
	else:
		min_price=''
		max_price=''
	goods_type_code=''
	old_id=id
	tags=changezhongwen(a[9])
	tags_admin=''
	
	"""
	id=str(a[0])
	a1=str(a[1])
	a2=changezhongwen(a[2])
	a3=str(a[3])
	if (a3 == '1'):
		a3='10331000'
	else:
		a3='10331001'
	
	a4=str(a[4])
	a5=changezhongwen(a[5])
	
	a6=changezhongwen(a[6])
	#print a6
	a7=changezhongwen(a[7])
	a8=a[8]
	if (a8 == None):
		a8='1'
	a9=changezhongwen(a[9])
	a10=a[10]
	sql="select count(0) from products_notShowPrice where pdt_id="+id+""
	cursor1.execute(sql)
	oldid=cursor1.fetchone()
	isshowin=oldid[0]
	if (isshowin > 0):
		is_show_in_price=0
	else:
		is_show_in_price=1
	a10=is_show_in_price
	
	a11=changezhongwen(a[11])
	a12=changezhongwen(a[12])
	a13=changezhongwen(a[13])
	#print id
	arrpricevalue=getpricevalue(id)
	#print arrpricevalue
	if (arrpricevalue != '0'):
		a11=changezhongwen(arrpricevalue[2])
	
	a14=changezhongwen(a[14])
	a15=changezhongwen(a[15])
	a16=changezhongwen(a[16])
	a17=changezhongwen(a[17])
	a18=changezhongwen(a[18])
	a19=changezhongwen(a[19])
	a20=changezhongwen(a[20])
	a21=changezhongwen(a[21])
	a22=a[22]
	if (a22 == '1'):
		a22="10111000"
	elif(a22 == '2'):
		a22='10111001'
	elif(a22 == '0'):
		a22='10111002'
	else:
		a22=''
	
	a23=changezhongwen(a[23])
	a24=changezhongwen(a[24])
	if (a24 == None or a24 == ''):
		a24=0
	else:
		a24=1
	if (a24 > 0):
		a24='1'
	else:
		a24='0'
	a25=a[25]
	sql="select count(0) from products_stop where pdt_id="+id+""
	cursor1.execute(sql)
	stopid=cursor1.fetchone()
	isstop=stopid[0]
	if (isstop > 0):
		a25='1'
	else:
		a25='0'
	
	a26=a[26]
	a27=int(a[27])
	if (a27 > 0):
		a27 = '1'
	else:
		a27 = '0'
		
	a28=a[28]
	a29=a[29]
	a29=getproductsoldid(id)
	a30=a[30]
	a30=gethiddencode(id)
	#print a30
	a31=a[31]
	
	
	sql="select adminuser,fdate from product_sh where pdt_id="+str(id)
	cursor1.execute(sql)
	adminusr=cursor1.fetchone()
	if (adminusr == None):
		a31='0'
		fdate='1900-01-01 00:00:00'
	else:
		a31=adminusr[0]
		fdate=adminusr[1]
		
	pdt_check=str(a[32])
	if (pdt_check == '1'):
		a32='1'
	elif(pdt_check == '0'):
		a32='0'
	elif(pdt_check == '3'):
		a32='2'
	else:
		a32='0'
	if (a25 == '1'):
		a32='3'
	
	a33=str(a[33])
	if (pdt_check=='0'):
		a33='1'
	else:
		a33='0'
	
	a34=str(a[34])
	sql="select bz from products_check where pdt_id="+str(id)
	cursor1.execute(sql)
	bz=cursor1.fetchone()
	if (bz == None):
		a34=''
	else:
		a34=changezhongwen(bz[0])
	
	
	a35=a[35]
	a35=fdate
	
	a36=a[36]
	a37=a[37]
	a38=a[38]
	a38=getexpiretime(a38,a37)
	
	a39=a[39]
	a40=a[40]
	a41=a[41]
	a42=a[42]
	if (arrpricevalue != '0'):
		a41=arrpricevalue[0]
		a42=arrpricevalue[1]
	a43=a[43]
	if (a43 == '2'):
		a43='10361000'
	elif(a43 == '1'):
		a43='10361001'
	else:
		a43='10361002'
	a44=a[44]

	#-------------公司账号
	
	
	#------------------------------
	
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	"""
	
	#value=[id,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,a32,a33,a34,a35,a36,a37,a38,a39,a40,a41,a42,a43,a44]
	#valueupdate=[a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,a32,a33,a34,a35,a36,a37,a38,a39,a40,a41,a42,a43,a44]
	value=[id, company_id, account, products_type_code, source_type_code, title, details, location, provide_status, total_quantity
	, is_show_in_price, price_unit, price, quantity_unit, quantity, source, specification, origin, impurity, color
	, useful, appearance, manufacture, remark, post_type, is_pause, is_post_when_view_limit, is_post_from_inquiry, is_del, category_products_main_code
	, category_products_assist_code, check_person, check_status, unchecked_check_status, unpass_reason, check_time, real_time, refresh_time, expire_time, gmt_created
	, gmt_modified, min_price, max_price, goods_type_code,  tags, tags_admin,old_id]
	sql="select count(0) from products where id="+str(id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	if (oldidcount>0):
		sql="update products set id=%s, company_id=%s, account=%s, products_type_code=%s, source_type_code=%s, title=%s, details=%s, location=%s, provide_status=%s, total_quantity=%s"
		sql=sql+", is_show_in_price=%s, price_unit=%s, price=%s, quantity_unit=%s, quantity=%s, source=%s, specification=%s, origin=%s, impurity=%s, color=%s"
		sql=sql+", useful=%s, appearance=%s, manufacture=%s, remark=%s, post_type=%s, is_pause=%s, is_post_when_view_limit=%s, is_post_from_inquiry=%s, is_del=%s, category_products_main_code=%s"
		sql=sql+", category_products_assist_code=%s, check_person=%s, check_status=%s, unchecked_check_status=%s, unpass_reason=%s, check_time=%s, real_time=%s, refresh_time=%s, expire_time=%s, gmt_created=%s"
		sql=sql+", gmt_modified=%s, min_price=%s, max_price=%s, goods_type_code=%s, tags=%s, tags_admin=%s where id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
		valueupdates.append(valueupdate)
	else:
		sql = "insert into products(id, company_id, account, products_type_code, source_type_code, title, details, location, provide_status, total_quantity"
		sql=sql+", is_show_in_price, price_unit, price, quantity_unit, quantity, source, specification, origin, impurity, color"
		sql=sql+", useful, appearance, manufacture, remark, post_type, is_pause, is_post_when_view_limit, is_post_from_inquiry, is_del, category_products_main_code"
		sql=sql+", category_products_assist_code, check_person, check_status, unchecked_check_status, unpass_reason, check_time, real_time, refresh_time, expire_time, gmt_created"
		sql=sql+", gmt_modified, min_price, max_price, goods_type_code,  tags, tags_admin,old_id"
		sql +=")"
		sql=sql+"values("
		sql=sql+"%s,%s,%s,%s,%s,%s,%s,%s,%s,%s"
		sql=sql+",%s,%s,%s,%s,%s,%s,%s,%s,%s,%s"
		sql=sql+",%s,%s,%s,%s,%s,%s,%s,%s,%s,%s"
		sql=sql+",%s,%s,%s,%s,%s,%s,%s,%s,%s,%s"
		sql=sql+",%s,%s,%s,%s,%s,%s,%s)"
		
		cursor.execute(sql,value);
		updateflag="新增"
		values.append(value)
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[17])
	#updatelastid(updatetablename,id)
	i+=1

try:
	#读取最新更新时间
	print editdate
	if (editdate!='0'):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor1.execute(sql)
		conn_rcu.commit()
	
	print changezhongwen("更新成功")
except Exception, e:
	print e


conn.close() 
conn_rcu.close()
conn_others.close()
os.system("python "+nowpath+"/offer_pic.py")