#-*- coding:utf-8 -*-
import MySQLdb   
import settings
import codecs,cgi
from django.utils.http import urlquote
from django.shortcuts import render_to_response
from django.http import HttpResponse,HttpResponseRedirect,HttpResponsePermanentRedirect,HttpResponseForbidden
from django.core.paginator import Paginator,InvalidPage,EmptyPage,PageNotAnInteger
import time
import random
import sys
import datetime
from datetime import timedelta, date 
import os
from django.core.cache import cache
from zz91settings import SPHINXCONFIG,limitpath
from operator import itemgetter, attrgetter
import shutil
try:
    import cPickle as pickle
except ImportError:
    import pickle

from math import ceil
import memcache
from sphinxapi import *
from zz91page import *


reload(sys)
sys.setdefaultencoding('UTF-8')

nowpath=os.path.dirname(__file__)
execfile(nowpath+"/conn.py")
execfile(nowpath+"/function.py")
imgurl="http://img0.zz91.com"
mc = cache

def default(request):
    
    return render_to_response('index2.html',locals())

def searchfirst(request):
    keywords = request.GET.get("keywords")
    ptype = request.GET.get("ptype")
    if (ptype==None):
        ptype=""
    province = request.GET.get("province")
    if (province==None):
        province=""
    posttime = request.GET.get("posttime")
    if (posttime==None):
        posttime=""
    if (ptype==None):
        ptype=""
    keywords_hex=getjiami(keywords)
    nowurl="/trade/s-"+keywords_hex+".html?ptype="+str(ptype)+"&province="+province+"&posttime="+str(posttime)
    return HttpResponsePermanentRedirect(nowurl)
def searchfirstcommon(request):
    keywords = request.GET.get("keywords")
    ptype = request.GET.get("ptype")
    if (ptype==None):
        ptype=""
    province = request.GET.get("province")
    if (province==None):
        province=""
    posttime = request.GET.get("posttime")
    if (posttime==None):
        posttime=""
    if (ptype==None):
        ptype=""
    keywords_hex=getjiami(keywords)
    nowurl="/trade/commonlist/c-"+keywords_hex+"-1.html?ptype="+str(ptype)+"&province="+province+"&posttime="+str(posttime)
    return HttpResponsePermanentRedirect(nowurl)
#---图片页面
def tradepic(request,products_id):
    list=getproductsinfo(products_id,cursor,'')
    piclist=gettradepiclist(products_id)
    otherprolist=getcompanyproductslist(0,8,list['com_id'],None)
    return render_to_response('pic.html',locals())
def tradelist(request,keywords):
    """
    if (gethextype(keywords)==False):
        keywords=keywords.encode('utf8','ignore')
        keywords_hex=keywords.encode('utf8','ignore').encode("hex")
    else:
        keywords=keywords.decode("hex")
        keywords_hex=keywords
    """
    pinyinlist=englishlist()
    
    keywords_hex=keywords
    
    keywords=keywords.decode("hex").decode('utf8','ignore')
    
    keywords=keywords.replace("astojh","#")
    keywords=keywords.replace("astoxg","/")
    
    keywords=keywords.replace("astoxf","%")
    keywords=keywords.replace("astoxl","\\")
    keywords=keywords.replace("astohg","-")
    keywords=keywords.replace("astokhl","(")
    keywords=keywords.replace("astokhr",")")
    keywords_re=keywords
    mingang=getmingganword(keywords)
    if mingang:
        return HttpResponseForbidden("<h1>FORBIDDEN</h1>")
    
    #---特殊处理 pmma广告
    if keywords.upper()=="PMMA":
        showtopad=None
    else:
        showtopad=1
    
    keywordsurlcode=urlquote(keywords)
    page = request.GET.get("page")
    cplist=getcplist(keywords,50)
    marketlist=getmarketlist(keywords)
    navlist=getnavlist(keywords=keywords)
    #keywords=keywords.replace("%","%%")
    mobileurl="/trade/?keywords="+keywords
    imgurl="http://img0.zz91.com"
    xgcategorylist=getcategorylist(kname=keywords,limitcount=20)
    #categorylist1=getindexcategorylist('1000',1)
    #categorylist2=getindexcategorylist('1001',1)
    #categorylist3=getindexcategorylist('1002',1)
    #categorylist4=getindexcategorylist('1003',1)
    #categorylist5=getindexcategorylist('1004',1)
    #categorylist6=getindexcategorylist('1005',1)
    #categorylist7=getindexcategorylist('1006',1)
    #categorylist8=getindexcategorylist('1007',1)
    #categorylist9=getindexcategorylist('1008',1)
    #categorylist10=getindexcategorylist('1009',1)
    
    firsttradetypelist=getfirsttradetype(kname=keywords)
    if (firsttradetypelist):
        firsttradelabel=firsttradetypelist['label']
        firsttradecode=firsttradetypelist['code']
        rightpricelistnav=getrightpricenav(firsttradecode[0:4])
        rightpricelist=getindexpricelist(kname=firsttradelabel,limitcount=10)
    
    provincelist=getareavalue('10011000')
    goldadlist=getgoldadlist(keywords,"48")
    #righttagslist=gettagslist(0,50,keywords)
    indexpricelist=getindexpricelist(kname=keywords,limitcount=5)
    indexbbslist=getindexbbslist(kname=keywords,limitcount=5)
    #companypricelist=getcompanypricelist(kname=keywords,limitcount=8)
    
    #相关资讯
    newslist=getnewslist(keywords=keywords,frompageCount=0,limitNum=10)
    
    page = request.GET.get("page")
    if (page == None or page=='' or page=="None"):
        page = 1

    searchname = urlquote(request.GET.get("searchname"))
    pdt_kind = request.GET.get("ptype")
    province = request.GET.get("province")
    provincecode = request.GET.get("provincecode")
    posttime = request.GET.get("posttime")
    pdtidlist = request.GET.get("pdtidlist")
    priceflag = str(request.GET.get("priceflag"))
    nopiclist = request.GET.get("nopiclist")
    tfromdate = request.GET.get("fromdate")
    ttodate = request.GET.get("todate")
    jmsearchname = request.GET.get("jmsearchname")
    fromsort = request.GET.get("fromsort")
    havepic = request.GET.get("havepic")
    isldb = request.GET.get("isldb")
    
    if (str(page)=='1' or page=='' or str(page)=='None'):
        pdtidlist=keywordsTop(keywords)
    else:
        pdtidlist=""
    #''''固定排名
    fixproducts=keywords_fix(keywords)
    if (fixproducts!=None or fixproducts!=''):
        fixplist=[]
        for b in fixproducts:
            if (b!=''):
                listfix=getproductsinfo(b[0],cursor,keywords)
                fixplist.append(listfix)
    if (page>1):
        fixplist=None
    #‘’‘’‘’‘’‘’‘’‘’‘
    if (nopiclist=='' or nopiclist==None or nopiclist=='None'):
        nopiclist=None
        offerFilterListPicInfo_class="offerFilterListPicInfo"
    else:
        offerFilterListPicInfo_class="offerFilterListPicInfo_long"

    if (pdtidlist!=None and pdtidlist!=''):
        #arrpdtidlist=pdtidlist.split(',')
        arrpdtidlist=pdtidlist
        listall=[]
        n=1
        for p in arrpdtidlist:
            if (p!=''):
                list1=getproductsinfo(p[0],cursor,keywords)
                m=1
                if (n<=1):
                    m=1
                elif(n>1 and n<=3):
                    m=2
                elif (n>3 and n<=18):
                    m=3
                if (list1!=None):
                    list1['vippaibian']=str(m)
                    n+=1
                    listall.append(list1)
        productListtop=listall
    
    #--------------------------------------------
    if province:
        province=cgi.escape(province)
    if (province=='' or province == None):
        province=''
    provinces=province
    if (province=='江浙沪'):
        provinces='浙江|江苏|上海'
    if (province=='华东区'):
        provinces='江苏|浙江|安徽|福建|山东|上海'
    if (province=='华南区'):
        provinces='云南|广东|广西|台湾|海南|福建'
    if (province=='华中区'):
        provinces='河南|湖北|湖南|四川'
    if (province=='华北区'):
        provinces='北京|天津|河北|山西|内蒙古'
    if (province=='国外'):
        provinces='美国|韩国|英国|日本|德国|爱尔兰|加拿大|西班牙|澳大利亚|新西兰|波兰|泰国|新加坡|荷兰|南非|马来西亚|津巴布韦|越南|阿联酋|意大利|法国|菲律宾|乌克兰'
#        provinces='美国|韩国|英国|日本|国外供货商|德国|爱尔兰|加拿大|西班牙|澳大利亚|其它|新西兰|波兰|泰国|新加坡|荷兰|南非|马来西亚|津巴布韦|越南|阿联酋|国|主营行业|合作伙伴 |意大利|美国废塑料价格行情|法国|菲律宾|乌克兰'
    if (pdt_kind == '' or pdt_kind == None or pdt_kind=="0"):
        pdt_type=''
        pdt_kind='0'
        pdtkindtxt="供求"
        kindclass={'lm1':'nav_on','lm2':'','lm3':''}
        seo_title=provinces+keywords+"_"+provinces+keywords+pdtkindtxt+"_"+provinces+keywords+"价格"+"_"+keywords+"产品属性介绍-"+keywords+"交易中心"
    if (pdt_kind =='1'):
        pdt_type='0'
        pdtkindtxt="供应"
        kindclass={'lm1':'','lm2':'nav_on','lm3':''}
        seo_title=pdtkindtxt+provinces+keywords+"_"+provinces+keywords+"_"+provinces+keywords+"价格"+"_"+keywords+"产品属性介绍-"+keywords+"交易中心"
    if (pdt_kind=='2'):
        pdt_type='1'
        pdtkindtxt="求购"
        kindclass={'lm1':'','lm2':'','lm3':'nav_on'}
        seo_title=pdtkindtxt+provinces+keywords+"_"+provinces+keywords+"_"+provinces+keywords+"价格"+"_"+keywords+"产品属性介绍-"+keywords+"交易中心"
    if (pdt_kind=="3"):
        pdt_type=''
    nowsearcher="offersearch_new"
    aboutproducts=getindexofferlist(keywords,pdt_type,6)
    nowpage=int(page)
    page=20*(int(page)-1)
    
    keywords2=keywords.replace(' ','')
    #keywords2=keywords2.replace(' ','')
    yuanliaoList=getyuanliaolist(kname=keywords,limitcount=4)
    keywords1=urlquote(keywords2)
    ttype = request.GET.get("ttype")
    
    keywords=keywords.replace('\\',' ')
    keywords=keywords.replace('%%',' ')
    keywords=keywords.replace('/',' ')
    keywords=keywords.replace('(',' ')
    keywords=keywords.replace(')',' ')
    keywords=keywords.replace('+',' ')
    keywords=keywords.replace('CD-R','')
    
    if (ttype==None):
        ttype=''
    if (posttime==None):
        posttime=''
    if (priceflag==None or str(priceflag)=='None'):
        priceflag=''
    if (nopiclist==None or str(nopiclist)=='None'):
        nopiclist=''
    if (havepic==None or str(havepic)=='None'):
        havepic=''
    action = '?ptype='+pdt_kind+'&province='+urlquote(province)+'&posttime='+str(posttime)+'&ttype='+str(ttype)+'&priceflag='+str(priceflag)+'&nopiclist='+str(nopiclist)+'&jmsearchname='+str(jmsearchname)+'&havepic='+str(havepic)+'&fromsort='+str(fromsort)
    #a(\d*)--b(\d*)--c(\d*)--d(\d*)--e(\d*)--f(\d*)
    #action='a'+str(pdt_kind)+'--b'+str(provincecode)+'--c'+str(posttime)+'--d'+str(priceflag)+'--e'+str(nopiclist)+'--f'+str(havepic)+''
    action="?s=1"
    others=""
    if (pdt_kind!=None and pdt_kind!=""):
        action=action + "&ptype="+ str(pdt_kind)
    if (province!=None and province!=""):
        action=action + "&province="+ urlquote(province)
    if (posttime!=None and posttime!=""):
        action=action + "&posttime="+ str(posttime)
        others=1
    if (priceflag!=None and priceflag!=""):
        action=action + "&priceflag="+ str(priceflag)
        others=1
    if (nopiclist!=None and nopiclist!=""):
        action=action + "&nopiclist="+ str(nopiclist)
        others=1
    if (havepic!=None and havepic!=""):
        action=action + "&havepic="+ str(havepic)
        others=1
        
    if (province==None or province==""):
        sprovince="所在地"
    else:
        sprovince=province
    if (posttime==None or str(posttime)==""):
        stime="时间"
    else:
        if (str(posttime)=='1'):
            stime="一天内"
        if (str(posttime)=='3'):
            stime="三天内"
        if (str(posttime)=='7'):
            stime="一周内"
        if (str(posttime)=='30'):
            stime="一月内"
        if (str(posttime)=='60'):
            stime="二月内"
    
    searchname=str(keywords1)
    searchname=searchname.replace('%28','astokhl')
    searchname=searchname.replace('%29','astokhr')
    searchname=searchname.replace('%5C','asto5c')
    searchname=searchname.replace('/','astoxg')
    searchname=searchname.replace('-','astohg')
    after_range_num = 4
    before_range_num = 3
    

    #----------------------------
    port = settings.SPHINXCONFIG['port']
    cl = SphinxClient()
    list = SphinxClient()
    
    cl.SetServer ( settings.SPHINXCONFIG['serverid'], port )
    list.SetServer ( settings.SPHINXCONFIG['serverid'], port )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    list.SetMatchMode ( SPH_MATCH_BOOLEAN )
    
    #取得总数
    nowdate=date.today()-timedelta(days=2)
    nextday=date.today()+timedelta(days=2)
    formatnowdate=time.mktime(nowdate.timetuple())
    formatnextday=time.mktime(nextday.timetuple())
    searstr=''
    if (pdt_kind !='0' and pdt_kind!="3"):
        searstr+=";filter=pdt_kind,"+pdt_type
        cl.SetFilter('pdt_kind',[int(pdt_type)])
        list.SetFilter('pdt_kind',[int(pdt_type)])
    cl.SetFilter('check_status',[1])
    list.SetFilter('check_status',[1])
    cl.SetFilter('is_pause',[0])
    list.SetFilter('is_pause',[0])
    if(havepic=='1'):
        cl.SetFilterRange('havepic',1,100)
        list.SetFilterRange('havepic',1,100)

    if (ttype == '1'):    
        cl.SetFilterRange('pdt_date',int(formatnowdate),int(formatnextday))
        list.SetFilterRange('pdt_date',int(formatnowdate),int(formatnextday))
       
        
    if (posttime =='' or posttime==None or posttime=='None'):
        searstr +=''
    else:
        pfromdate=date.today()-timedelta(days=int(posttime)+1)
        #test=str(time.mktime(pfromdate.timetuple()))
        ptodate=date.today()+timedelta(days=3)
        
        pfromdate_int=int(time.mktime(pfromdate.timetuple()))
        ptodate_int=int(time.mktime(ptodate.timetuple()))
        if (pfromdate!=None):
            cl.SetFilterRange('pdt_date',int(pfromdate_int),int(ptodate_int))
            list.SetFilterRange('pdt_date',int(pfromdate_int),int(ptodate_int))
            

    if (province ==None or province ==''):
        provincestr=''
    else:
        provincestr='&@(province,city) '+provinces

    if (priceflag == '1'):
        cl.SetFilter('length_price',[0],True)
        list.SetFilter('length_price',[0],True)
        list.SetSortMode( SPH_SORT_EXTENDED,"length_price desc,refresh_time desc" )
    elif (priceflag == '2'):
        list.SetFilter('length_price',[0],True)
        list.SetSortMode( SPH_SORT_EXTENDED,"length_price asc,refresh_time desc" )
    else:
        list.SetSortMode( SPH_SORT_EXTENDED,"refresh_time desc" )
    res = list.Query ( '@(title,label0,label1,label2,label3,label4,city,province,tags) '+keywords+provincestr+'',nowsearcher)
    if not res:
        listcount=0
    else:
        listcount=res['total_found']

    #----来电宝条件筛选
    if isldb:
        cl.SetFilter('viptype',[3])
#        list.SetFilter('viptype',[1])
#        list.SetFilter('crm_service_code',[1007,1008,1009])
 #       cl.SetFilter('crm_service_code',[1007,1008,1009])
    else:
#    cl.SetFilter('viptype_ldb',[1])
        cl.SetFilterRange('viptype',1,5)
    #cl.SetFilterRange('Prodatediff',0,3)

#    cl.SetFilterRange('viptype',1,5)
    
    #获得3天内再生通数据优先排序
    #listallvip=cache.get('list'+action)
    nowsearcherTop="offersearch_new_vip"
    cl.SetSortMode( SPH_SORT_EXTENDED,"company_id desc,refresh_time desc" )
    cl.SetLimits (0,100000,100000)
    rescount = cl.Query ( '@(title,label0,label1,label2,label3,label4,city,province,tags) '+keywords+provincestr+'',nowsearcherTop)
    pcount=0
    listall=[]
    if rescount:
        if rescount.has_key('matches'):
            tagslist=rescount['matches']
            testcom_id=0
            pcount=10000
            for match in tagslist:
                id=match['id']
                com_id=match['attrs']['company_id']
                viptype=match['attrs']['viptype']
                refresh_time=float(match['attrs']['refresh_time'])
                pdt_date=float(match['attrs']['pdt_date'])
                phone_rate=int(match['attrs']['phone_rate'])
                phone_level=float(match['attrs']['phone_level'])
                if (testcom_id==com_id):
                    pcount-=1
                else:
                    pcount=10000
                list1=[id,pcount,viptype,refresh_time,pdt_date,phone_rate,phone_level]
                listall.append(list1)
                testcom_id=com_id
                
    listallvip=sorted(listall, key=itemgetter(1,4,2,3),reverse=True)
      
    #优先排序数
    viplen=len(listallvip)
    
    #供求总数
    listcount+=int(viplen)
    #最后一页的供求数
    lastpNum=int(viplen-ceil(viplen / 20)*20)
    #开始供求数位置
    beginpage=page
    #优先排序页码
    #pageNum=0
    if (lastpNum==0):
        pageNum=int(ceil(viplen / 20))
    else:
        pageNum=int(ceil(viplen / 20)+1)
    
    #结束供求数位置
    if (int(nowpage)==int(pageNum) and lastpNum!=0):
        endpage=int(page+lastpNum)
    elif(int(nowpage)==int(pageNum) and lastpNum==0 and int(nowpage)==1):
        endpage=20
    elif(int(nowpage)==int(pageNum) and lastpNum==0):
        endpage=int(page)
    else:
        endpage=page+20
    #列出供求信息列表
    listall=[]
    for match in listallvip[beginpage:endpage]:
        list1=getproductsinfo(match[0],cursor,keywords2)
        listall.append(list1)
    productList=listall
    
    #普通供求开始数
    offsetNum=0
    limitNum=20
    if (nowpage==pageNum and lastpNum!=0):
        offsetNum=0
        limitNum=20-lastpNum
        notvip=1
    elif (nowpage==pageNum and lastpNum==0 and viplen>0):
        offsetNum=0
        limitNum=20-lastpNum
        notvip=1
    elif (nowpage==pageNum and lastpNum==0 and viplen==0):
        offsetNum=0
        limitNum=20
        notvip=1
    elif (pageNum==1 and nowpage==pageNum and lastpNum==0 and viplen>0):
        offsetNum=0
        limitNum=20
        notvip=0
    elif (nowpage>pageNum and lastpNum==0):
        offsetNum=(nowpage-pageNum-1)*20
        limitNum=20-lastpNum
        notvip=1
    elif(nowpage>pageNum and lastpNum>0):
        offsetNum=((int(nowpage)-int(pageNum)-1)*20)+(20-int(lastpNum))
        limitNum=20
        notvip=1
    elif (viplen<1):
        offsetNum=(nowpage-1)*20
        limitNum=20
        notvip=1
    else:
        notvip=0
    #优先排序供求结束页的
    #test=str(lastpNum)+'|'+str(pageNum)+'|'+str(offsetNum)+'|'+str(limitNum)
    if (offsetNum<0):
        offsetNum=0
    if (limitNum<0):
        limitNum=20
    if (nowpage==pageNum and lastpNum!=0):
        listall=productList
    else:
        if (pageNum==1 and nowpage==pageNum and lastpNum==0):
            listall=productList
            notvip=0
        else:
            listall=[]
    if (notvip==1):
        list.SetLimits(offsetNum,limitNum,100000)
        res = list.Query ( '@(title,label0,label1,label2,label3,label4,city,province,tags) '+keywords+provincestr+'',nowsearcher)
        
        if res:
            if res.has_key('matches'):
                prodlist=res['matches']
                for list in prodlist:
                    id=list['id']
                    list1=getproductsinfo(id,cursor,keywords2)
                    listall.append(list1)
                productList=listall
                
        

    #cache.set('productList', productList, 300)
    #底部页码
    #connt.close()
    try:
        page = int(request.GET.get('page',1))
        if page < 1:
            page = 1
    except ValueError:
            page = 1
    page_listcount=int(ceil(listcount / 20))+1
    page_rangep=[]
    i=1
    while (i<=page_listcount):
        pages={'number':'','nowpage':''}
        pages['number']=i
        if (i==page):
            pages['nowpage']='1'
        else:
            pages['nowpage']=None
            
        page_rangep.append(pages)
        i+=1
    if (page_listcount>1 and page>1):
        firstpage=1
    else:
        firstpage=None
    if (page<page_listcount and page_listcount>1):
        lastpage=1
    else:
        lastpage=None
    if page >= after_range_num:
        page_range = page_rangep[page-after_range_num:page + before_range_num]
    else:
        page_range = page_rangep[0:int(page) + before_range_num]
    nextpage=page+1
    prvpage=page-1
    if page>4:
        firstone=1
    if (page<page_listcount-3):
        lastone=1
    #大于500页提示
    
    if(page_listcount>500 and page>=500):
        arrtishi="提示：为了提供最相关的搜索结果，ZZ91再生网只显示500页信息，建议您重新搜索！"
    else:
        arrtishi=None
    
    return render_to_response('index.html',locals())
    
#----普通客户推荐
def commoncustomer(request):
    lb1=gettjhex1()
    lb2=gettjhex2()
    lb3=gettjhex3()
    
    companycount=getnomolcompanycount()
    companylist1=getcommoncompanylist(keywords="废金属".decode('utf8','ignore'),num=8,pic=1,companyflag=1,picwidth=200,picheight=169)
    companylist2=getcommoncompanylist(keywords="废塑料".decode('utf8','ignore'),num=8,pic=1,companyflag=1,picwidth=200,picheight=169)
    companylist3=getcommoncompanylist(keywords="纺织品|废纸|二手设备|电子电器|橡胶|轮胎|服务".decode('utf8','ignore'),num=8,pic=1,companyflag=1,picwidth=200,picheight=169)
    return render_to_response('common.html',locals())
def metal(request):
    return commoncustomermore(request,getjiami("废金属".decode('utf8','ignore')),1)
def plastic(request):
    return commoncustomermore(request,getjiami("废塑料".decode('utf8','ignore')),1)
def comprehensive(request):
    return commoncustomermore(request,getjiami("纺织品|废纸|二手设备|电子电器|橡胶|轮胎|服务".decode('utf8','ignore')),1) 
#----普通客户推荐（活跃）翻页
def commoncustomermore(request,keywords,page):
    lb1=gettjhex1()
    lb2=gettjhex2()
    lb3=gettjhex3()
    t=request.GET.get("t")
    companycount=getnomolcompanycount()
    keywords_hex=keywords
    
    if (keywords=="c"):
        keywords="废 !金属 & !塑料"
    else:
        keywords=getjiemi(keywords)
    if (page==None):
        page=1
    province = request.GET.get("province")
    pdt_kind = request.GET.get("ptype")
    havepic = request.GET.get("havepic")
    price = request.GET.get("price")
    keywords1=urlquote(keywords).decode('utf8','ignore')
    if (province ==None or province ==''):
        provincestr=''
        provincetext="所在地"
    else:
        provincestr='&@(province,city) '+province
        provincetext=province
    
    action="?n=1" 
    if (province!=None and province!=""):
        action=action + "&province="+ urlquote(province)
    if (pdt_kind == '' or pdt_kind == None or pdt_kind=="0"):
        pdt_type=None
        pdt_kind='0'
        pdtkindtxt="供求"
        pdt_kind=""
        kindclass={'lm1':'navon','lm2':'','lm3':''}
    if (pdt_kind =='1'):
        pdt_type='0'
        pdtkindtxt="供应"
        kindclass={'lm1':'','lm2':'navon','lm3':''}
    if (pdt_kind=='2'):
        pdt_type='1'
        pdtkindtxt="求购"
        kindclass={'lm1':'','lm2':'','lm3':'navon'}
    action=action + "&ptype="+str(pdt_kind)
    if (havepic=="1"):
        pic="1"
        actionpic=action.replace("&havepic="+ str(havepic),"")
        action=action + "&havepic="+str(havepic)
    else:
        actionpic=action
        pic=None
        
    if (price=="1"):
        price="1"
        action=action + "&price="+str(price)
        actionprice=action.replace("&price="+ str(price),"")
    else:
        actionprice=action
        price=None
    
    if (province!=None and province!=""): 
        actionprovince=action.replace("&province="+ urlquote(province),"")
    else:
        actionprovince=action
        
    
    xgcategorylist=getcategorylist(kname=keywords,limitcount=20)
    provincelist=getareavalue('10011000')
    navlist=getnavlist_tj(keywords=keywords)
    company6=getcommoncompanylist(keywords=keywords,num=None,frompageCount=0,limitNum=6,pic=None,companyflag=1)
    firsttradetypelist=getfirsttradetype(kname=keywords)
    if (firsttradetypelist):
        firsttradelabel=firsttradetypelist['label']
        firsttradecode=firsttradetypelist['code']
        rightpricelistnav=getrightpricenav(firsttradecode[0:4])
        rightpricelist=getindexpricelist(kname=firsttradelabel,limitcount=10)
    #companypricelist=getcompanypricelist(kname=keywords,limitcount=8)
    indexpricelist=getindexpricelist(kname=keywords,limitcount=5)
    indexbbslist=getindexbbslist(kname=keywords,limitcount=5)
    
    funpage = zz91page()
    limitNum=funpage.limitNum(16)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(4)
    before_range_num = funpage.before_range_num(4)
    companylist=getcommoncompanylist(keywords=keywords+provincestr,num=None,frompageCount=frompageCount,limitNum=16,ptype=pdt_type,pic=pic,price=price,companyflag="1")
    
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
    
    return render_to_response('commonlist.html',locals())
    
def companyinfo(request):
    company_id = request.GET.get("company_id")
    compinfo=getcompanydetail(None,company_id)
    return render_to_response('companyinfo.html',locals())
def companyinfos(request):
    company_id = request.GET.get("company_id")
    compinfo=getcompanydetail(None,company_id)
    return render_to_response('companyinfos.html',locals())
def categoryinfo(request):
    categorycode = request.GET.get("categorycode")
    if categorycode=="20091000":
        categorylist=getindexcategorylist_yl(categorycode,1)
    else:
        categorylist=getindexcategorylist(categorycode,1)
    return render_to_response('categorylist.html',locals())

#供求列表跳转
def offerlist(request):
    id1=request.GET.get('id1')
    id2=request.GET.get('id2')
    id3=request.GET.get('id3')
    id4=request.GET.get('id4')
    sqls=""
    if (id1!="0" and id1):
        sqls=" oldid1="+str(id1)
    if (id2!="0" and id2):
        sqls=" oldid2="+str(id2)
    if (id3!="0" and id3):
        sqls=" oldid3="+str(id3)
    if (id4!="0" and id4):
        sqls=" oldid4="+str(id4)
    sql="select label from category_products where "+sqls
    cursor.execute(sql)
    nlist = cursor.fetchone()
    if nlist:
        label=nlist[0]
        keywords_hex=getjiami(label)
        nowurl="http://trade.zz91.com/trade/s-"+keywords_hex+".html"
    else:
        nowurl="http://trade.zz91.com"
    return HttpResponseRedirect(nowurl)

#获得5条原料供求
def yuanliaolist(request):
    keywords=request.GET.get('keywords')
    yuanliaoList=getyuanliaolist(kname=keywords,limitcount=5)
    return  render_to_response('yuanliaolist.html',locals())