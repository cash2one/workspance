#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
from django.http import HttpResponse,HttpResponseRedirect
from zz91tools import *
import MySQLdb,os,StringIO,qrcode
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/conn.py")
execfile(nowpath+"/func/function.py")
conn=database()
cursor = conn.cursor()
#feiliao新增
from django.core.cache import cache
from zz91settings import SPHINXCONFIG
from settings import spconfig
from zz91db_ast import companydb
from zz91db_2_news import newsdb
from sphinxapi import *
from zz91page import *
dbc=companydb()
dbn=newsdb()
reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/feiliao123_function.py")
zzfei=zz91feiliao()

#---前台页面
def feiliao(request):
    websitelist=getwebsitelist(0,30,'','',1,'',1,1)
    if websitelist:
        recommendlist=websitelist['list']
    webtypelist=getindextypelist(1)
    return render_to_response('feiliao.html',locals())
#---更多
def listmore(request,typeid=''):
    typelist=getnexttype(typeid)
    typename=gettypename(typeid)
    return render_to_response('listmore.html',locals())

def verifycode(request):
    arg=request.GET.get('arg')
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    qr.add_data(arg)
    qr.make(fit=True)
    img = qr.make_image()
    mstream = StringIO.StringIO()
    img.save(mstream, "GIF")
    mstream.closed
    return HttpResponse(mstream.getvalue(),'image/gif')

#feiliao123改版
#--首页
def index(request):
    #网址导航栏目
    webtype=getnexttype(1)
    #废金属行情报价
    jinshu_area=zzfei.getprlist(0,6,15,'',[40,279,308,41,328,45,43,44,46,47,49,50],'','','1')['list']
    #废塑料行情报价
    suliao_guonei=zzfei.getpricedblist(0,9,20)['list']
    #综合废料行情报价
    zonghe_zongshu=zzfei.getprlist(0,6,8,'',[218,219])['list']
    #废料资讯
    columnid=zzfei.getcolumnid()
    hotnewslist=zzfei.getnewslistlatest(num=6,typeid=columnid)
    newsalllist=zzfei.getnewslist(allnum=1)
    #新公司--塑料
    company_suliao=zzfei.getcompanylist("塑料",0,7,7)
    #新公司--金属
    company_jinshu=zzfei.getcompanylist("金属",0,7,7)
    #新公司--综合
    company_zonghe=zzfei.getcompanylist("橡胶|废纸|电子电器|服务",0,7,7)
    return render_to_response('feiliao123/index.html',locals())
#--更多页
def feiliao123_more(request,typeid=''):
    typelist=getnexttype(typeid)
    typename=gettypename(typeid)
    return render_to_response('feiliao123/feiliao123_more.html',locals())
#--交易
def trade(request):
    #热门关键词
    #有图片的供求
    productlist=zzfei.getproductslist()
    
    #底部交易报价
    pricelist_suliao=zzfei.getpricelist_company(kname='废塑料',frompageCount=0,limitNum=7)
    listall_suliao=pricelist_suliao['list']
    pricelist_jinshu=zzfei.getpricelist_company(kname='废金属',frompageCount=0,limitNum=7)
    listall_jinshu=pricelist_jinshu['list']
    pricelist_feizhi=zzfei.getpricelist_company(kname='废纸',frompageCount=0,limitNum=7)
    listall_feizhi=pricelist_feizhi['list']
    pricelist_zonghe=zzfei.getpricelist_company(kname='综合',frompageCount=0,limitNum=7)
    listall_zonghe=pricelist_zonghe['list']
    return render_to_response('feiliao123/trade.html',locals())

#--资讯
def news(request):
    page=request.GET.get('page')
    kinds4=zzfei.getfuzhuindexlist()
    #id列表
    columnid=zzfei.getcolumnid()
    #获得最新
    #hotnewslist=zzfei.getnewslistlatest(num=3,typeid=columnid,has_txt=140)
    latesdnews=zzfei.getlatestnews()
    return render_to_response('feiliao123/news.html',locals())