#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import time,datetime
import struct
import os
import urllib,urllib2,re,requests
reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
#parentpath=os.path.abspath('../')
#nowpath=os.path.abspath('.')
nowpath="/usr/apps/python"
execfile(nowpath+"/conn.py")
execfile(nowpath+"/inc.py")
#--------------------------------------
def getwebhtml(baidu_url):
	if baidu_url:
		f = urllib.urlopen(baidu_url)
		html = f.read()
		return html
	else:
		return ''
def getsid(kid):
	sqls="select id from seo_keywordslist where id="+str(kid)+""
	cursor.execute(sqls)
	result=cursor.fetchone()
	if result:
		return result[0]
def getbaidulist(html):
    html=html.split('<div id="content_left">')
    if len(html1)>1:
        html1=html[1]
        html2=html1.split('<div style="clear:both;height:0;"></div>')
        html3=html2[0]
    else:
        html3=""
    return html3
   
def get_url_content2(url):#突破网站防爬
    i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                 "Referer": 'http://www.baidu.com'}
    req = urllib2.Request(url, headers=i_headers)
    html=urllib2.urlopen(req).geturl()
    return html
def get_url_content(url):#突破网站防爬
    i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                 "Referer": 'http://www.baidu.com'}
    req = urllib2.Request(url, headers=i_headers)
    html=urllib2.urlopen(req).read()
    return html

def get_content(re_py,html):
    urls_pat=re.compile(re_py,re.DOTALL)
    content=re.findall(urls_pat,html)
    return content
def zqweb(k,id,check_url):
	html=""
	weblist=[0,10,20,30,40,50,60,70,80,90]
	
	def saveh(html,id,check_url):	
		re_py=r'href = "http://www.baidu.com/link(.*?)"'
		htmlaa=html.split('<h3 class="t">')
		#html=html.split('href = "http://www.baidu.com/link?url=')
		#htmlaa=get_content(re_py,html)
		i=0
		c=0
		for list in htmlaa:
			#list=list.replace("<b>","")
			#list=list.replace("</b>","")
			if (list.find(check_url)>0):
				c=1
				savedb(id,c,i)
				return i
				break
			"""
			#kthml=get_content(re_py,list)
			if list:
				try:
					kt=get_url_content2("http://www.baidu.com/link"+list)
				except:
					break
				print kt
				if check_url in kt:
					c=1
					savedb(id,c,i)
					return i
					break
			"""
			i=i+1
	def savedb(id,c,i):
		if (c==0):
			i=100
		if (i<=10):
			dbtype=1
		else:
			dbtype=0
		sql="update seo_keywordslist set baidu_ranking="+str(i)+",dbtype="+str(dbtype)+" where id="+str(id)
		cursor.execute(sql)
		conn.commit()
		#-----更新历史记录
		sqlk="select * from seo_keywords_history where kid="+str(id)+" and kdate='"+str(getToday())+"' and baidu_ranking="+str(i)+" and ktype='check_pai'"
		cursor.execute(sqlk)
		result=cursor.fetchone()
		if (result==None):
			sqlp="insert into seo_keywords_history(sid,kid,ktype,baidu_ranking,kdate) values("+str(getsid(id))+","+str(id)+",'check_pai',"+str(i)+",'"+str(getToday())+"')"
			cursor.execute(sqlp)
			conn.commit()
	nodb=0
	for l in weblist:
		baidu_url="http://www.baidu.com/s?q1="+k.encode('utf-8')+"&pn="+str(l)
		nhtml=getwebhtml(baidu_url)
		html=html+nhtml
		if (nhtml.find(check_url)>0):
			break
	rehtml=saveh(html,id,check_url)
	baiduranking=100
	if rehtml:
		baiduranking=rehtml
	print baiduranking
	if baiduranking==100:
		savedb(id,0,100)

def gxkeywords():
	sql="select sid,keywords,com_msb,id from seo_keywordslist"
	cursor.execute(sql)
	result=cursor.fetchall()
	if (result):
		for list in result:
			sid=list[0]
			keywords=list[1]
			com_msb=list[2]
			id=list[3]
			zqweb(changezhongwen(keywords),id,com_msb)
			print changezhongwen(keywords)

def gxdb():
	sql="select id from seo_list"
	cursor.execute(sql)
	result=cursor.fetchall()
	if (result):
		for list in result:
			sid=list[0]
			sqld="select id from seo_keywordslist where dbtype=0 and sid="+str(sid)+""
			cursor.execute(sqld)
			resultd=cursor.fetchone()
			if resultd==None:
				sqlp="update seo_list set dbflag=1 where id="+str(sid)+""
				cursor.execute(sqlp)
				conn.commit()
			else:
				sqlp="update seo_list set dbflag=0 where id="+str(sid)+""
				cursor.execute(sqlp)
				conn.commit()
				print changezhongwen("更新成功")
gxkeywords()
gxdb()
