#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
from django.http import HttpResponse,HttpResponseRedirect,HttpResponsePermanentRedirect
import MySQLdb,sys,os,memcache,settings,urllib,re,time,datetime,md5,hashlib,random
from django.core.cache import cache
import simplejson
#from commfunction import subString,filter_tags,replacepic,formattime
#from function import getnowurl
from zz91page import *
from zz91db_ast import companydb
from zz91db_2_news import newsdb
from zz91db_zzlog import zzlogdb
from zz91conn import database_mongodb
from sphinxapi import *
from settings import spconfig,appurl
from zz91settings import SPHINXCONFIG
from zz91tools import formattime,subString,filter_tags,int_to_str,str_to_int

dbc=companydb()
dbn=newsdb()
dbzzlog=zzlogdb()
#连接loginfo集合（表）
dbmongo=database_mongodb()
 


reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/public_function.py")
execfile(nowpath+"/func/news_function.py")
execfile(nowpath+"/func/inquiry_function.py")
execfile(nowpath+"/func/huzhu_function.py")
execfile(nowpath+"/func/qianbao_function.py")
execfile(nowpath+"/func/message_function.py")
execfile(nowpath+"/func/order_function.py")
execfile(nowpath+"/func/price_function.py")
execfile(nowpath+"/func/myrc_function.py")
execfile(nowpath+"/func/company_function.py")

zzn=zznews()
zzi=zzinquiry()
zzh=zzhuzhu()
zzq=zzqianbao()
zzm=zzmessage()
zzo=zzorder()
zzmyrc=zmyrc()
zzprice=zprice()


def pricelist(request):
    company_id=request.GET.get("company_id")
    clientid=request.GET.get('clientid')
    mypricecollect=zzmyrc.get_mypricecollectid(company_id)
    mybusi=zzmyrc.get_mybusinesscollect(company_id)
    l=[99990]
    for a in mypricecollect:
        l.append(int(a))
    if not l or l==[99990]:
        pricelist=None
        pricelist=zzprice.getpricelist(frompageCount=0,limitNum=5,allnum=5)
    else:
        ll=getallpricecategroy(l)
        pricelist=zzprice.getpricelist(frompageCount=0,limitNum=5,allnum=5,category_id=ll)
    return HttpResponse(simplejson.dumps(pricelist, ensure_ascii=False))
#城市联动
def provincejson(request):
    list=provincejs()
    #return HttpResponse(list)
    return HttpResponse(simplejson.dumps(list, ensure_ascii=False))