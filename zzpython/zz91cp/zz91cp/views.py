#-*- coding:utf-8 -*-
import MySQLdb,settings,codecs,os,sys,datetime,time,random
from django.utils.http import urlquote
from django.shortcuts import render_to_response
from django.template.loader import get_template
from django.template import Context
from django.http import HttpResponse,HttpResponseRedirect,HttpResponseForbidden,HttpResponseNotFound
from django.core.paginator import Paginator,InvalidPage,EmptyPage,PageNotAnInteger
from zz91settings import SPHINXCONFIG,limitpath
from zz91tools import mobileuseragent
from datetime import timedelta, date 
from django.core.cache import cache
from zz91conn import database_mongodb
from zz91db_ast import companydb
dbc=companydb()
#import bs4
#from bs4 import BeautifulSoup
import shutil
try:
    import cPickle as pickle
except ImportError:
    import pickle

from math import ceil
from sphinxapi import *
from zz91page import *
reload(sys) 
sys.setdefaultencoding('utf-8') 

nowpath=os.path.dirname(__file__)
execfile(nowpath+"/conn.py")
execfile(nowpath+"/commfunction.py")
execfile(nowpath+"/function.py")

wmh=weimenhu()

def default(request):
    host = request.META['HTTP_HOST']
    hidfloat=1
    #最新微门户和热门搜索
    messagelist=wmh.getnewandhot()
    listnewest=messagelist['newest']
    listhot=messagelist['hot']
    companylist=wmh.getcomplist()
    #废金属价格
    priceInfo_feijs=wmh.getpricelist_daohang(kname='金属'.decode('utf-8'),limitcount=8,titlelen=50)
    lef_rig=wmh.leftandright(priceInfo_feijs)
    priceInfo_feijs_left=lef_rig['left']
    priceInfo_feijs_right=lef_rig['right']
   
    #废塑料价格
    priceInfo_feisl=wmh.getpricelist_daohang(kname='塑料'.decode('utf-8'),limitcount=8,titlelen=50)
    lef_rig=wmh.leftandright(priceInfo_feisl)
    priceInfo_feisl_left=lef_rig['left']
    priceInfo_feisl_right=lef_rig['right']

    #废纸价格       
    priceInfo_feizhi=wmh.getpricelist_daohang(kname='纸'.decode('utf-8'),limitcount=8,titlelen=50)
    lef_rig=wmh.leftandright(priceInfo_feizhi)
    priceInfo_feizhi_left=lef_rig['left']
    priceInfo_feizhi_right=lef_rig['right']
    
    #废橡胶价格
    priceInfo_feixj=wmh.getpricelist_daohang(kname='橡胶'.decode('utf-8'),limitcount=8,titlelen=50)
    lef_rig=wmh.leftandright(priceInfo_feixj)
    priceInfo_feixj_left=lef_rig['left']
    priceInfo_feixj_right=lef_rig['right']

    #废二手设备
    priceInfo_jixshebei=wmh.getpricelist_daohang(kname='机械 设备'.decode('utf-8'),limitcount=8,titlelen=50)
    lef_rig=wmh.leftandright(priceInfo_jixshebei)
    priceInfo_jixshebei_left=lef_rig['left']
    priceInfo_jixshebei_right=lef_rig['right']

    #废电器
    priceInfo_feidz=wmh.getpricelist_daohang(kname='电子'.decode('utf-8'),limitcount=8,titlelen=50)
    lef_rig=wmh.leftandright(priceInfo_feidz)
    priceInfo_feidz_left=lef_rig['left']
    priceInfo_feidz_right=lef_rig['right']
    #类别列表
    #--废金属
    #-----钢铁最终类
    gangtie=wmh.getlastcategory(category_code='10001019100110001000')
    #-----稀有金属贵金属最终类
    xiyou=wmh.getlastcategory(category_code='10001019100110001004')
    #-----有色金属 最终类
    youse=wmh.getlastcategory(category_code='10001019100110001002')
    #-----金属混合/复合料  硅 最终类
    hunhe=wmh.getlastcategory(category_code='10001019100110001003')
    
    #--废塑料
    #-----通用废塑料 类
    tysl=wmh.getlastcategory(category_code='10001019100110011001')
    #-----工程废塑料  类
    gcsl=wmh.getlastcategory(category_code='10001019100110011002')
    #-----塑料颗粒  类
    slkl=wmh.getlastcategory(category_code='10001019100110011003')
    #-----特种废塑料  塑料混合/复合料  类
    tzsl=wmh.getlastcategory(category_code='10001019100110011004')
    
    #--废纺织品
    #-----化纤类
    huaqian=wmh.getlastcategory(category_code='10001019100110021001')
    #-----皮革类
    pige=wmh.getlastcategory(category_code='10001019100110021002')
    #-----丝 牛仔布  家纺废料  类
    si=wmh.getlastcategory(category_code='10001019100110021003')
    #-----废玻璃废木  化工废料  服务 类
    fbl=wmh.getlastcategory(category_code='10001019100110021004')
    
    #--包装废纸   工业废纸
    #-----包装废纸  工业废纸 类
    bzfz=wmh.getlastcategory(category_code='10001019100110031001')
    #-----印刷废纸  办公废纸 类
    ysfz=wmh.getlastcategory(category_code='10001019100110031002')
    #-----生活废纸  特种废纸  废纸制品 类
    shfz=wmh.getlastcategory(category_code='10001019100110031003')
    #-----美废  欧废  日废  纸浆  卡纸  纸边 类
    meifei=wmh.getlastcategory(category_code='10001019100110031004')
    
    #-- 二手设备
    #-----工程设备  类
    gcsb=wmh.getlastcategory(category_code='10001019100110041001')
    #-----化工设备 类
    hgsb=wmh.getlastcategory(category_code='10001019100110041002')
    #-----制冷设备  冶炼设备  类
    zlsb=wmh.getlastcategory(category_code='10001019100110041003')
    #-----交通工具  二手商品  类
    jtgj=wmh.getlastcategory(category_code='10001019100110041004')
    
    #-- 废电子电器   废橡胶   废轮胎
    #-----废电子  办公设备  类
    fdz=wmh.getlastcategory(category_code='10001019100110051001')
    #-----废电器 类
    fdq=wmh.getlastcategory(category_code='10001019100110051002')
    #-----废橡胶  合成橡胶   类
    fxj=wmh.getlastcategory(category_code='10001019100110051003')
    #-----再生胶  废轮胎  废橡胶处理设备  类
    zsj=wmh.getlastcategory(category_code='10001019100110051004')
    
    cp_link=wmh.getdaohanglist(10221000)
    
    return render_to_response('index2.html',locals())

def cp(request , pingyin):
    host = request.META.get("HTTP_HOST")
    moaddr=request.META.get("REMOTE_ADDR")
    if moaddr=="115.29.35.147":
    #if pingyin=="feisuliaobaojia":
        return HttpResponseNotFound(request.META.get("REMOTE_PORT"))
    if (pingyin=="carveout"):
        return carveout(request)
    pingyinlist=getpingyinattribute(pingyin)
    cpchickNum(pingyin)
    keywords=""
    if pingyinlist:
        pingyinname=pingyinlist['label']
        keywords=pingyinlist['keywords']
        if not keywords:
            return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
        if keywords:
            keywords=keywords.replace(" ","")
    else:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    if pingyin=="jiqingluanlunxiaoshuo":
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    mingang=getmingganword(keywords)
    if mingang:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    if "毛片" in keywords:
        return HttpResponseRedirect("http://www.zjfriend.com")
    plist1=getindexofferlist(keywords,0,4)
    plist2=getindexofferlist(keywords,1,4)
    companypricelist=getcompanypricelist(kname=keywords,limitcount=6,titlelen=20,searchmode=2)
    pricelist=getindexpricelist(kname=keywords,limitcount=15,searchmode=2)
    #if (pricelist==[]):
        #pricelist=getcompanypricelist(kname=keywords,limitcount=15)
    bbslist=getindexbbslist(kname=keywords,limitcount=15,searchmode=2)
    plist_pic=getindexofferlist_pic(kname=keywords,pdt_type=None,limitcount=8)
    pcompanylist=getcompanyindexcomplist(keywords,8)
    
    newjoincomplist=getsycompanylist(keywords,0,3,None,10)
    
    #tagslist=newtagslist(keywords,40)
    cplist=getcplist(keywords,50)
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mindex.html',locals())
    return render_to_response('index.html',locals())

def price(request,pingyin):
    cpchickNum(pingyin)
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    mingang=getmingganword(keywords)
    if mingang:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    plist_pic=getindexofferlist_pic(kname=keywords,pdt_type=None,limitcount=3)
    salesproducts=getindexofferlist(keywords,0,9)
    buyproducts=getindexofferlist(keywords,1,9)
    arealist=getarealist("10011000")
    pcompanylist=getcompanyindexcomplist(keywords,8)
    pricelist=getcompanypricelist(kname=keywords,limitcount=12,company=1,searchmode=2)
    cplist=getcplist(keywords,50)
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mprice.html',locals())
    return render_to_response('price.html',locals())
def pricemore(request,pingyin,page):
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    cplist=getcplist(keywords,50)
    province = request.GET.get("province")
    if (province=='None' or province==None):
        province=""
    pcompanylist=getcompanyindexcomplist(keywords,8)
    salesproducts=getindexofferlist(keywords,0,9)
    buyproducts=getindexofferlist(keywords,1,9)
    arealist=getarealist("10011000")
    if (page==None or page==0):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(20)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(5)
    before_range_num = funpage.before_range_num(9)
    
    pricelist=getcompanypricelistmore(kname=keywords,frompageCount=frompageCount,limitNum=limitNum,titlelen=50,company=1,province=province,searchmode=2)
    listcount=0
    if (pricelist):
        listall=pricelist['list']
        listcount=pricelist['count']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mpricemore.html',locals())
    return render_to_response('pricemore.html',locals())
def trade(request,pingyin,page=''):
    cpchickNum(pingyin)
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    mingang=getmingganword(keywords)
    if mingang:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    companypricelist=getcompanypricelist(keywords,16,20,searchmode=2)
    pcompanylist=getcompanyindexcomplist(keywords,3)
    bbslist=getindexbbslist(kname=keywords,limitcount=7)
    prolist=getproductslist(kname=keywords,frompageCount=0,limitNum=10)
    ptype=request.GET.get("ptype")
    if (ptype==None):
        ptype=""
    ptab1="ms-title-left"
    ptab2="ms-title-right"
    ptab3="ms-title-right"
    if (ptype=="1"):
        ptab1="ms-title-right"
        ptab2="ms-title-left"
        ptab3="ms-title-right"
    if (ptype=="0"):
        ptab1="ms-title-right"
        ptab2="ms-title-right"
        ptab3="ms-title-left"
    
    pingyinlist=getpingyinattribute(pingyin)
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    cplist=getcplist(keywords,50)
    customlist=getcompanypricelist(keywords,16,20)
    bbslist=getindexbbslist(kname=keywords,limitcount=7)
    if not page:
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(20)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(5)
    before_range_num = funpage.before_range_num(9)
    
    prolist=getproductslist(kname=keywords,frompageCount=frompageCount,limitNum=limitNum,ptype=ptype)
    
    listcount=0
    if (prolist):
        listall=prolist['list']
        listcount=prolist['count']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        cplist=getcplist(keywords,50)
        return render_to_response('mobile/mtrade.html',locals())
    return render_to_response('trade.html',locals())
def trademore(request,pingyin,page):
    ptype=request.GET.get("ptype")
    if (ptype==None):
        ptype=""
    ptab1="ms-title-left"
    ptab2="ms-title-right"
    ptab3="ms-title-right"
    if (ptype=="1"):
        ptab1="ms-title-right"
        ptab2="ms-title-left"
        ptab3="ms-title-right"
    if (ptype=="0"):
        ptab1="ms-title-right"
        ptab2="ms-title-right"
        ptab3="ms-title-left"
    
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    cplist=getcplist(keywords,50)
    customlist=getcompanypricelist(keywords,16,20,searchmode=2)
    bbslist=getindexbbslist(kname=keywords,limitcount=7)
    if (page==None or page==0):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(20)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(5)
    before_range_num = funpage.before_range_num(9)
    
    prolist=getproductslist(kname=keywords,frompageCount=frompageCount,limitNum=limitNum,ptype=ptype)
    
    listcount=0
    if (prolist):
        listall=prolist['list']
        listcount=prolist['count']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mtrademore.html',locals())
    return render_to_response('trademore.html',locals())
def company(request,pingyin):
    cpchickNum(pingyin)
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    mingang=getmingganword(keywords)
    if mingang:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    cplist=getcplist(keywords,50)
    salesproducts=getindexofferlist(keywords,0,15)
    buyproducts=getindexofferlist(keywords,1,15)
    pcompanylist=getcompanyindexcomplist(keywords,3)
    companylist=getcompanylist(keywords,0,4,4)['list']
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mcompany.html',locals())
    return render_to_response('company.html',locals())
def companymore(request,pingyin,page):
    
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    cplist=getcplist(keywords,50)
    salesproducts=getindexofferlist(keywords,0,15)
    buyproducts=getindexofferlist(keywords,1,15)
    if (page==None or page==0):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(10)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(5)
    before_range_num = funpage.before_range_num(9)
    
    companylist=getcompanylist(keywords,frompageCount,limitNum,20000)
    listcount=0
    if (companylist):
        listall=companylist['list']
        listcount=companylist['count']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mcompanymore.html',locals())
    return render_to_response('companymore.html',locals())
def picture(request,pingyin):
    cpchickNum(pingyin)
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    mingang=getmingganword(keywords)
    if mingang:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    cplist=getcplist(keywords,50)
    salesproducts=getindexofferlist(keywords,0,9)
    buyproducts=getindexofferlist(keywords,1,9)
    pcompanylist=getcompanyindexcomplist(keywords,8)
    piclist=getproductslist(kname=keywords,frompageCount=0,limitNum=24,havepic=1)
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mpicture.html',locals())
    return render_to_response('picture.html',locals())
def picturemore(request,pingyin,page):
    
    pingyinlist=getpingyinattribute(pingyin)
    if not pingyinlist:
        return HttpResponseNotFound("<h1>FORBIDDEN</h1>")
    pingyinname=pingyinlist['label']
    keywords=pingyinlist['keywords']
    cplist=getcplist(keywords,50)
    pcompanylist=getcompanyindexcomplist(keywords,8)
    salesproducts=getindexofferlist(keywords,0,9)
    buyproducts=getindexofferlist(keywords,1,9)
    if (page==None or page==0):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(16)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(5)
    before_range_num = funpage.before_range_num(9)
    
    piclist=getproductslist(kname=keywords,frompageCount=frompageCount,limitNum=limitNum,havepic=1)
    listcount=0
    if (piclist):
        listall=piclist['list']
        listcount=piclist['count']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    #----进入手机模版
    #agent=request.META['HTTP_USER_AGENT']
    #agentflag=mobileuseragent(agent)
    #if agentflag:
    mobileflag=request.GET.get("mobileflag")
    if mobileflag:
        return render_to_response('mobile/mpicturemore.html',locals())
    return render_to_response('picturemore.html',locals())
    
#优质客户推荐
def carveout(request):
    lb1=gettjhex1()
    lb2=gettjhex2()
    lb3=gettjhex3()
    companycount=getvipcompanycount()
    companylist1=getindexcompanylist_pic(keywords="金属",num=8)
    companylist2=getindexcompanylist_pic(keywords="塑料",num=8)
    companylist3=getindexcompanylist_pic(keywords="纺织品|废纸|二手设备|电子电器|橡胶|轮胎|服务",num=8)
    return render_to_response('carveout/index.html',locals())

def carveoutmore(request,keywords,page):
    lb1=gettjhex1()
    lb2=gettjhex2()
    lb3=gettjhex3()
    companycount=getvipcompanycount()
    t=request.GET.get("t")
    keywords_hex=keywords
    if (keywords=="c"):
        keywords="废 !金属 & !塑料"
    else:
        keywords=getjiemi(keywords)
    if (page==None):
        page=1
    
    funpage = zz91page()
    limitNum=funpage.limitNum(16)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(5)
    before_range_num = funpage.before_range_num(9)
    companylist=getindexcompanylist_pic(keywords=keywords,num=None,frompageCount=frompageCount,limitNum=16)
    listcount=0
    if (companylist):
        listall=companylist['list']
        listcount=companylist['listcount']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    
    return render_to_response('carveout/list.html',locals())
    
#普通客户推荐
def commoncustomer(request):
    lb1=gettjhex1()
    lb2=gettjhex2()
    lb3=gettjhex3()
    
    companycount=getvipcompanycount()
    companylist1=getcommoncompanylist(keywords="金属",num=8)
    companylist2=getcommoncompanylist(keywords="塑料",num=8)
    companylist3=getcommoncompanylist(keywords="纺织品|废纸|二手设备|电子电器|橡胶|轮胎|服务",num=8)
    return render_to_response('carveout/common.html',locals())

def commoncustomermore(request,keywords,page):
    lb1=gettjhex1()
    lb2=gettjhex2()
    lb3=gettjhex3()
    companycount=getvipcompanycount()
    t=request.GET.get("t")
    keywords_hex=keywords
    if (keywords=="c"):
        keywords="废 !金属 & !塑料"
    else:
        keywords=getjiemi(keywords)
    if (page==None):
        page=1
    
    funpage = zz91page()
    limitNum=funpage.limitNum(16)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(4)
    before_range_num = funpage.before_range_num(4)
    companylist=getcommoncompanylist(keywords=keywords,num=None,frompageCount=frompageCount,limitNum=16,pic=None,companyflag="1")
    company6=getcommoncompanylist(keywords=keywords,num=None,frompageCount=0,limitNum=6,pic=None,companyflag=1)
    listcount=0
    if (companylist):
        listall=companylist['list']
        listcount=companylist['listcount']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    
    return render_to_response('carveout/commonlist.html',locals())

#微信2014开春专题
def weixin2014(request):
    page=request.GET.get("page")
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(16)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(5)
    before_range_num = funpage.before_range_num(9)
    companylist=getweixincomplist(frompageCount=frompageCount,limitNum=limitNum)
    listcount=0
    if (companylist):
        listall=companylist['list']
        listcount=companylist['listcount']
        if (int(listcount)>1000000):
            listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    return render_to_response('weixin2014/index.html',locals())
    
#再生通数据导出
def outzstcomplist(request):
    kname=request.GET.get("kname")
    port = SPHINXCONFIG['port']
    cl = SphinxClient()
    cl.SetServer ( SPHINXCONFIG['serverid'], port )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    cl.SetSortMode( SPH_SORT_EXTENDED,"membership_code desc,gmt_start desc" )
    cl.SetFilter('apply_status',[1])
    cl.SetLimits (0,10000,20000)
    if (kname):
        res = cl.Query ('@(name,business,address,sale_details,buy_details,tags,area_name,area_province) '+kname,'company')
    else:
        res = cl.Query ('','company')
    if res:
        if res.has_key('matches'):
            tagslist=res['matches']
            listall_comp=[]
            for match in tagslist:
                id=match['id']
                #年限
                sql2="select sum(zst_year) from crm_company_service where company_id=%s and apply_status=1"
                cursor.execute(sql2,[id])
                zstNumvalue = cursor.fetchone()
                if zstNumvalue:
                    zst_year=zstNumvalue[0]
                attrs=match['attrs']
                compname=attrs['compname']
                viptype=str(attrs['membership_code'])
                membership="普通会员"
                if (viptype == '10051000'):
                    membership='普通会员'
                if (viptype == '10051001'):
                    membership='再生通'
                if (viptype == '1725773192'):
                    membership='银牌品牌通'
                if (viptype == '1725773193'):
                    membership='金牌品牌通'
                if (viptype == '1725773194'):
                    membership='钻石品牌通'
                pbusiness=attrs['pbusiness']
                parea_province=attrs['parea_province']
                industry_code=attrs['industry_code']
                industry_name=getcategoryname(industry_code)
                service_code=attrs['service_code']
                service_name=getcategoryname(service_code)
                list1={'id':id,'viptype':viptype,'zst_year':zst_year,'compname':compname,'business':pbusiness,'area_province':parea_province,'membership':membership,'industry_name':industry_name,'service_name':service_name}
                listall_comp.append(list1)
    return render_to_response('outzstcomplist.html',locals())
    closeconn()
def gettabelfild(n):
    if n==1:
        label=["品名","品 种","品种","品种","名称","产品名称","货物名称","废料名称","子类"]
    if n==2:
        label=["价格","最高","不含税价（元/吨）","参考价","均价","成交价（元）","最低价","最高价","平均价","价格（元/吨）","最低价（元/吨）","价格(元/吨)","价格区间","收购价（元/吨）","销售价（元/吨）","今日均价","最高/最低价"]
    if n==3:
        label=["地区","省份","交货地点","区域","厂家(产地)","地域"]
    if n==4:
        label=["含量","材质","产地/牌号","规格","牌号"]
    if n==5:
        label=["涨跌","走势"]
    if n==6:
        label=["成交单位"]
    if n==7:
        label=["备注","说明"]
    return label
#----报价表格分析

def gettablelist1(content):
    content=content.lower()
    content=content.replace("<th","<td")
    content=content.replace("</th>","</td>")
    arrtable=content.split("<table")
    listall=[]
    tablestr=""
    for table in arrtable:
        tablestr+="<table border=1>"
        arrtr=table.split("<tr")
        i=1
        trow=[]
        for row in arrtr[1:]:
            tablestr+="<tr>"
            arrtd=row.split("<td")
            j=1
            for tr in arrtd[1:]:
                tr="<td "+tr
                trsss=tr.split("</td>")
                tr=trsss[0]+"</td>"
                trs=tr
                tr = BeautifulSoup(tr)
                textname=tr.text.encode("utf-8")
                textname=filter_tags(textname.replace(" ","")).strip('\n')

                
                if ("rowspan" in trs):
                    rowspan=tr.td['rowspan']
                    #trow.append([i,j])
                    for a in range(i+1,int(rowspan)+i):
                        trow.append([a,j,textname])
                else:
                    rowspan=1
                
                if ("colspan" in trs):
                    colspan=tr.td['colspan']
                else:
                    colspan=1
                        
                tablestr+="<td >"
                tablestr+=textname
                tablestr+="</td>"
                if int(colspan)>1:
                    for r in range(1,int(colspan)):
                        tablestr+="<td>"
                        tablestr+=textname
                        tablestr+="</td>"
                j+=1
            tablestr+="</tr>"
            i+=1
        tablestr+="</table>"

    return {'content':tablestr,'trow':trow}
def getpricetable2(content,trow):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
        tablestr+="<table border=1>"
        i=1
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8")
                for l in trow:
                    if l[0]==i and l[1]==j:
                        tablestr+="<td >"
                        tablestr+=l[2]
                        tablestr+="</td>"
                tablestr+="<td >"
                tablestr+=textname
                tablestr+="</td>"
                j+=1
            i+=1
    return tablestr
                
def pricetable(request):
    priceid=request.GET.get("priceid")
    sql="select content,tags,title from price where id=%s and is_checked=1"
    cursor.execute(sql,[priceid])
    alist = cursor.fetchone()
    if alist:
        content=alist[0]
        title=alist[2]
        list=gettablelist1(content)
        listall=getpricetable2(list['content'],list['trow'])
        #return render_to_response('pricetable.html',locals())
        return HttpResponse(listall)
def viewer_404(request):
    t = get_template('404.html')
    html = t.render(Context())
    return HttpResponseNotFound(html)
def viewer_500(request):
    t = get_template('404.html')
    html = t.render(Context())
    return HttpResponseNotFound(html)
"""
#微门户
def weimenhu(request):
    messagelist=getnewandhot()
    
    listnewest=messagelist['newest']
    listhot=messagelist['hot']
    companylist=getcomplist()
    
    return render_to_response('weimenhu.html',locals())
"""



        