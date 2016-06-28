#!/usr/bin/env python
#coding=utf-8
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
import array
try:
    import cPickle as pickle
except ImportError:
    import pickle
reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="crm_assign"

def getsalesStat(com_id):
	if (str(com_id) == 'None' or com_id==0):
		return '0'
	else:
		sql="select Com_ContactLastTime,contactnext_time,EmailNext_Time from crm_service where com_id="+str(com_id)+""
		cursor1.execute(sql)
		newcode=cursor1.fetchone()
		if (newcode == None):
			return None
		else:
			return newcode
#nav_column_config=[{"id":"gsjs","t":"公司介绍","d":true},{"id":"zxgq","t":"最新供求","d":true},{"id":"gsdt","t":"公司动态","d":true},{"id":"cxda","t":"诚信档案","d":false},{"id":"zxly","t":"在线留言","d":true},{"id":"lxfs","t":"联系方式","d":true}]
def getnavName(mc):
	mcc=mc
	if (mc=='js'):
		mcc= 'gsjs'
	if (mc=='gq'):
		mcc= 'zxgq'
	if (mc=='cp'):
		mcc= 'cpzt'
	if (mc=='dt'):
		mcc= 'gsdt'
	if (mc=='cx'):
		mcc= 'cxda'
	if (mc=='zs'):
		mcc= 'ryzs'
	if (mc=='pj'):
		mcc= 'kfpj'
	if (mc=='ly'):
		mcc= 'zxly'
	if (mc=='lx'):
		mcc= 'lxfx'
	return mcc
def getxlvalue(value):
	value=value.replace('[','').replace(']','').replace("'","")
	aa=value.split(',')
	d={}
	c = [i.split(':') for i in value.split(',')]
	for i in c:
		if (len(i)>1):
			navvalue=getnavName(i[1])
		else:
			navvalue=''
		if (navvalue=='true'):
			navvalue=True
		if (navvalue=='false'):
			navvalue=False
		d[i[0]]=navvalue
		
	return d
	
def getnavvalue(value):
	value=value.replace('[{','').replace('}]','')
	dd=[]
	arr1=value.split('},{')
	for n in arr1:
		dd.append(getxlvalue(n))
	return dd
def getheaderConfigsplit(value,sp):
	dd=[]
	valuelist=value.split(sp)
	for i in valuelist:
		dd.append({'id':i})
	return dd
def getsideBarConfigsplit(value,sp):
	dd=[]
	valuelist=value.split(sp)
	for i in valuelist:
		ii=i.split('***')
		dd.append({'id':ii[0],'title':ii[1]})
	return dd
			
sql="select  id,com_id,isFormal,isDefault,enterpriseType"
sql=sql+",memberCategoryId,columnID,colorStyle,layout,widthStyle"
sql=sql+",navigator,background,flashIndex,companyNameCnStyle,companyNameEnStyle"
sql=sql+",logoPic,themeSlogan1,themeSlogan2,themePic,customPicHeight"
sql=sql+",customThemePic,headerConfig,sideBarConfig,mainConfig,navColumnConfig"
sql=sql+",customWidgetId,convert(bigint,gmt_editdate) as gmt_editdate from myrc_esite where com_id in (select com_id from comp_zstinfo where com_check=1)"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	old_id=str(a[0])
	company_id=str(a[1])
	is_formal=changezhongwen(a[2])
	flashindex1=str(a[12])
	flashindex1=flashindex1.replace('url','filePath')
	#flashindex={"filePath":"","width":700,"height":450,"bgColor":"#ffffff","displayed":False}
	flashindex=getxlvalue(flashindex1)
	flashindex=str(flashindex).replace("'",'"')
	if (flashindex=='{}'):
		flashindex=None
	#print flashindex
	logo_pic1=str(a[15])
	if (logo_pic1=='None'):
		logo_pic1='http://img0.zz91.com/front/images/esite/logo.gif'
	else:
		logo_pic1='http://img.zz91.com/'+logo_pic1
	logo_pic={"url":""+logo_pic1+"","height":58}
	logo_pic=str(logo_pic)
	if (logo_pic=='{}'):
		logo_pic=None
	#logoPic={"url":""+logo_pic1+"","height":58}
	
	nav_column_config='[{"id":"gsjs","t":"'+changezhongwen('公司介绍')+'","d":true},{"id":"zxgq","t":"'+changezhongwen('最新供求')+'","d":true},{"id":"gsdt","t":"'+changezhongwen('公司动态')+'","d":true},{"id":"cxda","t":"'+changezhongwen('诚信档案')+'","d":true},{"id":"zxly","t":"'+changezhongwen('在线留言')+'","d":true},{"id":"lxfs","t":"'+changezhongwen('联系方式')+'","d":true}]'
	nav_column_config1=changezhongwen(a[24])
	
	if (nav_column_config1!='' and nav_column_config1!='None'):
		nav_column_config=getnavvalue(nav_column_config1)
		strvalue='['
		for t in nav_column_config:
			strvalue+='{"id":"'+t['id']
			if ('t' in t):
				strvalue+='","t":"'+t['t']
			else:
				strvalue+='","t":"'+changezhongwen('公司介绍')
			strvalue+='","d":'
			if ('d' in t):
				if (str(t['d'])==''):
					strvalue+='false},'
				else:
					strvalue+=str(t['d'])+'},'
			else:
				strvalue+='true},'
		nav_column_config=strvalue[0:len(strvalue)-1]+']'
	#print nav_column_config
	nav_column_config='[{"id":"gsjs","t":"'+changezhongwen('公司介绍')+'","d":true},{"id":"zxgq","t":"'+changezhongwen('最新供求')+'","d":true},{"id":"gsdt","t":"'+changezhongwen('公司动态')+'","d":true},{"id":"cxda","t":"'+changezhongwen('诚信档案')+'","d":true},{"id":"zxly","t":"'+changezhongwen('在线留言')+'","d":true},{"id":"lxfs","t":"'+changezhongwen('联系方式')+'","d":true}]'
	
	#nav_column_config=[{"id":"gsjs","t":"公司介绍","d":true},{"id":"zxgq","t":"最新供求","d":true},{"id":"gsdt","t":"公司动态","d":true},{"id":"cxda","t":"诚信档案","d":false},{"id":"zxly","t":"在线留言","d":true},{"id":"lxfs","t":"联系方式","d":true}]
	is_default=str(a[3])
	if(is_default=='None'):
		is_default='Y'
	
	operation='saveFormal'
	mycolumn='sy'
	title=''
	position=str(a[8])
	if (position=='None'):
		position='layout_left'
	slogan=changezhongwen(a[16])
	slogan=slogan.split(';')
	slogan=slogan[0].replace("content:'","").replace("'","")
	sub_slogan=changezhongwen(a[17])
	sub_slogan=sub_slogan.split(';')
	sub_slogan=sub_slogan[0].replace("content:'","").replace("'","")
	is_transparent='Y'
	page_width=str(a[9])
	if (page_width=='None' or page_width==''):
		page_width='952'
	
	style_content=[{"cn":"bodyCont","sl":{"border-color":"#648eb6","background":"#ffffff","border-style":"solid"}}
	,{"cn":"bodyContTitle","sl":{"background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_titleBg.gif) repeat-x"}}
	,{"cn":"imgBorder","sl":{"border-color":"#7aa0c8","border-style":"dotted"}}
	,{"cn":"mainTextColor","sl":{"color":"#000000"}}
	,{"cn":"titleLinkColor","sl":{"color":"#ffffff"}}
	,{"cn":"topicLink","sl":{"color":"#27598e"}}
	,{"cn":"headerMenuBorder","sl":{"border-color":"#cccccc","background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_lmyBg.gif) repeat-x"}}
	,{"cn":"headerMenuList","sl":{"color":"#ffffff"}}
	,{"cn":"headerMenuLiCheck","sl":{"border-color":"#ffffff","background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_lmnBg.gif) repeat-x","color":"#27598e"}}
	,{"cn":"headerMenuBottom","sl":{"border-bottom-color":"#ffffff"}}
	,{"cn":"topbaner","sl":{"background":"#ffffff"}}
	,{"cn":"headTopic","sl":{"height":"200px","background":"url(http://img0.zz91.com/front/images/esite/banner/banner_8.jpg) repeat"}}
	,{"cn":"inBg","sl":{"background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_inBg.jpg) repeat"}}
	,{"cn":"bodyBg","sl":{"background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_outBg.gif) repeat"}}
	,{"cn":"description","sl":{"padding-left":"50px","padding-top":"80px"}}
	,{"cn":"chinaname","sl":{"font-family":"楷体_GB2312","font-size":"26px","font-weight":"bold","color":"#000000","font-style":"italic"}}
	,{"cn":"enname","sl":{"font-family":"Verdana","font-size":"20px","font-weight":"bold","color":"#000000","font-style":"italic"}}
	,{"cn":"topDesc","sl":{"font-family":"楷体_GB2312","font-size":"26px","font-weight":"bold","color":"#000000","font-style":"italic"}}
	,{"cn":"bottomDesc","sl":{"font-family":"Verdana","font-size":"20px","font-weight":"bold","color":"#000000","font-style":"italic"}}]
	"""
	style_content=[{"cn":"bodyCont","sl":{"border-color":"#648eb6","background":"#ffffff","border-style":"solid"}},
	{"cn":"bodyContTitle","sl":{"background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_titleBg.gif) repeat-x"}},
	{"cn":"imgBorder","sl":{"border-color":"#7aa0c8","border-style":"dotted"}},
	{"cn":"mainTextColor","sl":{"color":"#000000"}},
	{"cn":"titleLinkColor","sl":{"color":"#ffffff"}},
	{"cn":"topicLink","sl":{"color":"#27598e"}},
	{"cn":"headerMenuBorder","sl":{"border-color":"#cccccc","background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_lmyBg.gif) repeat-x"}},
	{"cn":"headerMenuList","sl":{"color":"#ffffff"}},
	{"cn":"headerMenuLiCheck","sl":{"border-color":"#ffffff","background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_lmnBg.gif) repeat-x","color":"#27598e"}},
	{"cn":"headerMenuBottom","sl":{"border-bottom-color":"#ffffff"}},
	{"cn":"topbaner","sl":{"background":"#ffffff"}},
	{"cn":"headTopic","sl":{"height":"200px","background":"url(http://img0.zz91.com/front/images/esite/banner/banner_8.jpg) repeat"}},
	{"cn":"inBg","sl":{"background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_inBg.jpg) repeat"}},
	{"cn":"bodyBg","sl":{"background":"url(http://img0.zz91.com/front/images/esite/topic/jz1_outBg.gif) repeat"}},
	{"cn":"description","sl":{"padding-left":"50px","padding-top":"80px"}},
	{"cn":"chinaname","sl":{"font-family":"楷体_GB2312","font-size":"26px","font-weight":"bold","color":"#000000","font-style":"italic"}},
	{"cn":"enname","sl":{"font-family":"Verdana","font-size":"20px","font-weight":"bold","color":"#000000","font-style":"italic"}},
	{"cn":"topDesc","sl":{"font-family":"楷体_GB2312","font-size":"26px","font-weight":"bold","color":"#000000","font-style":"italic"}},
	{"cn":"bottomDesc","sl":{"font-family":"Verdana","font-size":"20px","font-weight":"bold","color":"#000000","font-style":"italic"}}]
	"""

	over_alllayout1='{"headerConfig":[{"id":"company_name"},{"id":"top_nav"},{"id":"theme_pic"}],"sideBarConfig":[{"id":"category_nav","title":"供应产品分类"},{"id":"search_in_site","title":"供求搜索"},{"id":"friend_link","title":"友情链接"},{"id":"contact_side","title":"联系方式"}],"mainConfig":[{"id":"company_index","title":"关于我们"},{"id":"contact_index","title":"联系方式"},{"id":"all_offer_index","title":"最新供求"},{"id":"newslist_index","title":"公司动态"}]}'
	over_alllayout={"headerConfig":[{"id":"company_name"},{"id":"top_nav"},{"id":"theme_pic"}],"sideBarConfig":[{"id":"category_nav","title":"供应产品分类"},{"id":"search_in_site","title":"供求搜索"},{"id":"friend_link","title":"友情链接"},{"id":"contact_side","title":"联系方式"}],"mainConfig":[{"id":"company_index","title":"关于我们"},{"id":"contact_index","title":"联系方式"},{"id":"all_offer_index","title":"最新供求"},{"id":"newslist_index","title":"公司动态"}]}
	stralout='{"headerConfig":'
	
	headerConfig=changezhongwen(a[21])
	
	if (headerConfig=='None' or headerConfig==''):
		headerConfig=[{"id":"company_name"},{"id":"top_nav"},{"id":"theme_pic"}]
	else:
		headerConfig=getheaderConfigsplit(headerConfig,'|||')
		over_alllayout['headerConfig']=headerConfig
	stralout+=str(headerConfig).replace("'",'"').replace(' ','')
	
	
	sideBarConfig=changezhongwen(a[22])
	#sideBarConfig=sideBarConfig.replace('company_side','company_index')
	if (sideBarConfig!='' and sideBarConfig!='None'):
		sideBarConfig=getsideBarConfigsplit(sideBarConfig,'|||')
		nn=0
		for e in sideBarConfig:
			if (e['id']=='company_side'):
				del sideBarConfig[nn]
			nn=nn+1
		over_alllayout['sideBarConfig']=sideBarConfig
	
		stralout+=',"sideBarConfig":[{"id":"category_nav","title":"'+changezhongwen("供应产品分类")+'"},{'
		for n in sideBarConfig:
			stralout+='"id":"'+n['id']+'",'
			stralout+='"title":"'+n['title']+'"},{'
		stralout=stralout[0:len(stralout)-3]
		stralout+='}]'
	else:
		stralout+=',"sideBarConfig":[{"id":"category_nav","title":"'+changezhongwen("供应产品分类")+'"},{"id":"search_in_site","title":"'+changezhongwen("供求搜索")+'"},{"id":"friend_link","title":"'+changezhongwen("友情链接")+'"},{"id":"contact_side","title":"'+changezhongwen("联系方式")+'"}]'
		
	
	
	mainConfig=changezhongwen(a[23])

	if (mainConfig!='' and mainConfig!='None'):
		mainConfig=getsideBarConfigsplit(mainConfig,'|||')
		over_alllayout['mainConfig']=mainConfig
		
		stralout+=',"mainConfig":[{'
		for n in mainConfig:
			if (n['id']!='zx_index' and n['id']!='cert_index' and n['id']!='all_pro_index'):
				stralout+='"id":"'+n['id']+'",'
				stralout+='"title":"'+n['title']+'"},{'
			
		stralout=stralout[0:len(stralout)-3]
		
		stralout+='}]}'
		#over_alllayout=str(over_alllayout).replace("'",'"')
	else:
		stralout+=',"mainConfig":[{"id":"company_index","title":"'+changezhongwen("关于我们")+'"},{"id":"contact_index","title":"'+changezhongwen("联系方式")+'"},{"id":"all_offer_index","title":"'+changezhongwen("最新供求")+'"},{"id":"newslist_index","title":"'+changezhongwen("公司动态")+'"}]}'
	over_alllayout=stralout
	
	
	customThemePic=str(a[20])
	themePic=changezhongwen(str(a[18]))

	if (len(themePic)>0 and themePic!='None' and themePic!=' ' and themePic!=''):
		customThemePic='http://img.zz91.com/esite/images/banner/banner_'+themePic+'.jpg'
	else:
		if (customThemePic=='None' or customThemePic==''):
			customThemePic='http://img.zz91.com/esite/images/banner/banner_8.jpg'
			style_content[11]['sl']['height']="200px"
		else:
			customThemePic='http://img.zz91.com/'+customThemePic
			style_content[11]['sl']['height']=changezhongwen(str(a[19]))+"px"
		
	style_content[11]['sl']['background']="url("+customThemePic+") repeat"
	style_content=str(style_content).replace("'",'"')
	
	delete_style_id=''
	custom_widgets='{}'
	cover=''
	addstyle_self=''
	
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()

	value=[is_formal, flashindex, logo_pic, nav_column_config, is_default
	, operation, mycolumn, title, position, slogan, sub_slogan, is_transparent, page_width
	, style_content, over_alllayout, delete_style_id, custom_widgets, cover, addstyle_self
	, gmt_created, gmt_modified,company_id]
	
	
	#-判断是否已经导
	sql="select count(0) from esite_config where company_id="+str(company_id)+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update esite_config set is_formal=%s, flashindex=%s, logo_pic=%s, nav_column_config=%s, is_default=%s	, operation=%s, mycolumn=%s, title=%s, position=%s, slogan=%s, sub_slogan=%s, is_transparent=%s, page_width=%s	, style_content=%s, over_alllayout=%s, delete_style_id=%s, custom_widgets=%s, cover=%s, addstyle_self=%s	, gmt_created=%s, gmt_modified=%s where company_id=%s"
		cursor.execute(sql,value);
		updateflag="更新"
	else:
		sql="insert into esite_config(is_formal, flashindex, logo_pic, nav_column_config, is_default	, operation, mycolumn, title, position, slogan, sub_slogan, is_transparent, page_width	, style_content, over_alllayout, delete_style_id, custom_widgets, cover, addstyle_self	, gmt_created, gmt_modified,company_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[3])
	i=i+1
	
try:
	#读取最新更新时间
	#if (editdate!=0):
		#sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		#cursor1.execute(sql)
		#conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e
#print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate crmcompany")
conn.close() 
conn_rcu.close()