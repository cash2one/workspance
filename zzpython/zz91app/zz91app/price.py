#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
from django.http import HttpResponse,HttpResponseRedirect,HttpResponsePermanentRedirect
import MySQLdb,sys,os,memcache,settings,urllib,re,time,datetime,simplejson
from datetime import timedelta,date
from django.core.cache import cache

from zz91tools import subString,filter_tags,formattime,int_to_str
from zz91page import *
from zz91db_ast import companydb
from sphinxapi import *
from settings import spconfig,appurl

spconfig=settings.SPHINXCONFIG
dbc=companydb()

reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/public_function.py")
execfile(nowpath+"/func/price_function.py")
zzprice=zprice()

def index(request):
    host=getnowurl(request)
    webtitle="行情报价"
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    return render_to_response('price/index.html',locals())


#----报价列表
def pricelist(request,category_id='',assist_id=''):
    host=getnowurl(request)
    appsystem=request.GET.get("appsystem")
    company_id=request.GET.get('company_id')
    localhtmlflag=request.GET.get('localhtmlflag')
    orderflag=request.GET.get('orderflag')
    if '.html' in host:
        iscm=1
    alijsload="1"
    
    
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    if not category_id:
        category_id=request.GET.get("category_id")
#    searchlist={}
    webtitle="行情报价"
    assistvalue=None
    categoryvalue=None
    if category_id:
        categoryvalue=[int(category_id)]
        categoryvalue=getallpricecategroy([int(category_id),99999])
        category_label=zzprice.getcategory_label(category_id)
        mulu1='> <a href="/priceindex/'+str(category_id)+'.html">'+category_label+'</a>'
        webtitle+=category_label
        #记录搜索记录
        clientid=request.GET.get("clientid")
        updatesearchKeywords(clientid,company_id,category_label,ktype="price")
    if assist_id=="":
        assist_id=request.GET.get("assist_id")
    if assist_id:
        assist_label=zzprice.getcategory_label(assist_id)
        mulu3='> <a href="?assist_id='+assist_id+'">'+assist_label+'</a>'
        assistvalue=[int(assist_id)]
        webtitle+="-"+assist_label
    username=request.session.get("username",None)
    keywords=request.GET.get("keywords")
    if (gethextype(keywords)==False):
        keywords_hex=getjiami(keywords)
    else:
        keywords=getjiemi(keywords)
        keywords_hex=getjiami(keywords)
    if (keywords!=None):
        mulu2='> <a href="?keywords='+keywords+'">'+keywords+'</a>'
        keywords=keywords.replace("报价","")
        keywords=keywords.replace("价格","")
        webtitle=keywords
        webtitle+="-"+keywords
    if (str(keywords)=='None'):    
        keywords=None
    if (str(category_id)=='None'):
        category_id=None
    if (category_id==None and keywords==None):
        categoryvalue=[]
    if (str(category_id)=='1'):
        labelstyle1="class=chk"
        labelstyle2=""
        labelstyle3=""
    if (str(category_id)=='2'):
        labelstyle1=""
        labelstyle2="class=chk"
        labelstyle3=""
    if (str(category_id)=='3'):
        labelstyle1=""
        labelstyle2=""
        labelstyle3="class=chk"
    arealist=[]
    if category_id in ["40","42","41","52","44","45","47","43"]:
        showarea=1
        if category_id=="40":
            arealist="江浙沪,广东,南海 ,临沂,汨罗,北京,长葛,清远,四川,天津,安徽,河北,成都,江西,山东,辽宁,贵州,重庆,台州,陕西,福建,云南"
        if category_id=="42":
            arealist="江浙沪,山东,河南,天津,四川,湖北,江西,山西,广东,福建"
        if category_id=="41":
            arealist="废铝,江浙沪,广东,南海,长葛,汨罗,临沂,天津,福建,四川,清远,宁波,河北,台州,山东,云南,重庆,陕西"
        if category_id=="52":
            arealist="全国,山东,陕西,长葛,江浙沪,广东,四川,重庆"
        if category_id=="44":
            arealist="江浙沪,广东,南海,临沂,台州,宁波,天津,清远"
        if category_id=="45":
            arealist="江浙沪,南海,临沂,天津,广东 "
        if category_id=="47":
            arealist="江浙沪,临沂,南海"
        if category_id=="43":
            arealist="江浙沪,南海,广东,天津,河北,临沂"
        arealist=arealist.split(",")
    #定制商机
    if orderflag:
        orderlist=zzprice.getmyorderprice(company_id)
        categoryvalue=[]
        assistvalue=[]
        keywordslist=""
        if not orderlist:
            resultlist={"error_code":1,"reason":"您定制的内容暂无信息","result":''}
            response = HttpResponse(simplejson.dumps(resultlist, ensure_ascii=False))
            return response
        if orderlist:
            for list in orderlist:
                category_id=list['category_id']
                if category_id:
                    categoryvalue.append(category_id)
                assist_id=list['assist_id']
                if assist_id:
                    assistvalue.append(assist_id)
                keywords=list['keywords']
                if keywords:
                    if (gethextype(keywords)==False):
                        keywords=keywords
                    else:
                        keywords=getjiemi(keywords)
                    keywordslist+=str(keywords)+"|"
                
            if keywordslist:
                keywordslist=keywordslist[0:len(keywordslist)-1]
            keywords=keywordslist
        
    
    pricelistall=zzprice.getpricelist(keywords=keywords,frompageCount=0,limitNum=20,category_id=categoryvalue,allnum=20,assist_type_id=assistvalue)
    pricelist=pricelistall['list']
    
    pricelistcount=pricelistall['count']
    if (pricelistcount==1):
        morebutton='style=display:none'
    else:
        morebutton=''
    datatype=request.GET.get("datatype")
    if str(localhtmlflag)=="1" or datatype=="json":
        resultlist={"error_code":0,"reason":"","result":pricelist,"queryString":{"category_id":category_id,"keywords":keywords_hex},"lastpage":"",'listcount':pricelistcount,'arealist':arealist}
        response = HttpResponse(simplejson.dumps(resultlist, ensure_ascii=False))
        return response

    return render_to_response('price/list.html',locals())
#----报价列表 更多
def pricemore(request,category_id='',assist_id=''):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    username=request.session.get("username",None)
    if not category_id:
        category_id=request.GET.get("category_id")
    keywords=request.GET.get("keywords")
    if (gethextype(keywords)==False):
        keywords_hex=getjiami(keywords)
    else:
        keywords=getjiemi(keywords)
        keywords_hex=getjiami(keywords)
    if not assist_id:
        assist_id=request.GET.get("assist_id")
    assistvalue=None
    if assist_id:
        assistvalue=[int(assist_id)]
    if (keywords!=None):
        webtitle=keywords+"报价列表"
    else:
        webtitle="报价列表"
    if (str(keywords)=='None'):
        keywords=None
    page=request.GET.get("page")
    if page==None:
        page=0
    if (str(category_id)=='None'):
        category_id=None
    if category_id:
        category_id=int(category_id)
    categoryvalue=[category_id]
    if not category_id:
        categoryvalue=None
    pricelistall=zzprice.getpricelist(keywords=keywords,frompageCount=int(page)*20,limitNum=20,category_id=categoryvalue,allnum=1000,assist_type_id=assistvalue)
    pricelist=pricelistall['list']
    ##返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(pricelist, ensure_ascii=False))
    return render_to_response('price/listmore.html',locals())

#----行情报价最终页
def details(request):
    host=getnowurl(request)
    alijsload="1"
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    id=request.GET.get("id")
    company_id=request.GET.get("company_id")
    category_id=request.GET.get("category_id")
    keywords=request.GET.get("keywords")
    if (gethextype(keywords)==False):
        keywords_hex=getjiami(keywords)
    else:
        keywords=getjiemi(keywords)
        keywords_hex=getjiami(keywords)
    assist_id=request.GET.get("assist_id")
    searchkeyword=''
    category_id2=[]
    assist_id2=[]
    priceabout=[]
    arg=''
    if category_id:
        category_label=zzprice.getcategory_label(category_id)
        parent_id=zzprice.parent_id(category_id)
        parent_id2=zzprice.parent_id(parent_id)
        parent_id3=zzprice.parent_id(parent_id2)
        category_id2=[int(category_id)]
        if parent_id==5 or parent_id2==5 or parent_id3==5:
            searchkeyword='废金属'
            arg=1
        elif parent_id==6 or parent_id2==6 or parent_id3==6:
            searchkeyword='废塑料'
            arg=2
        elif parent_id==7 or parent_id2==7 or parent_id3==7:
            searchkeyword='废纸'
        elif parent_id==8 or parent_id2==8 or parent_id3==8:
            searchkeyword='废橡胶'
        elif parent_id==213 or parent_id2==213 or parent_id3==213:
            searchkeyword='原油'
        mulu1='> <a href="/priceindex/'+str(category_id)+'.html">'+category_label+'</a>'
    if keywords:
        webtitle=keywords
#        if arg==1:
#            searchkeyword=keywords
        mulu2='> <a href="/price/?keywords='+keywords+'">'+keywords+'</a>'
    if assist_id:
        assist_id2=[int(assist_id)]
        assist_label=zzprice.getcategory_label(assist_id)
        if arg==1:
#            searchkeyword=assist_label
            priceabout=zzprice.getpricelist('',0,7,category_id2,7,assist_id2,1)['list']
            searchkeyword=category_label
        elif arg==2:
            searchkeyword=assist_label
            priceabout=zzprice.getpricelist('',0,7,category_id2,7,assist_id2,2)['list']
        if assist_id!=0 and assist_label:
            mulu3='> <a href="/price/?assist_id='+str(assist_id)+'">'+str(assist_label)+'</a>'
    username=request.session.get("username",None)
    type_id=''
    sql="select title,content,gmt_created,type_id,assist_type_id from price where id=%s and is_checked=1"
    alist = zzprice.dbc.fetchonedb(sql,[id])
    listall=[]
    if alist:
        title=alist[0]
        content=alist[1]
        type_id=alist[3]
        assist_type_id=alist[4]
        if not category_id:
            category_id=type_id
            category_label=zzprice.getcategory_label(category_id)
            assist_type_label=zzprice.getcategory_label(assist_type_id)
            searchkeyword=category_label
            parent_id=zzprice.parent_id(category_id)
            parent_id2=zzprice.parent_id(parent_id)
            parent_id3=zzprice.parent_id(parent_id2)
            category_id2=[int(category_id)]
            if parent_id==5 or parent_id2==5 or parent_id3==5:
#                searchkeyword='废金属'
                arg=1
            elif parent_id==6 or parent_id2==6 or parent_id3==6:
#                searchkeyword='废塑料'
                searchkeyword=assist_type_label
                arg=2
            elif parent_id==7 or parent_id2==7 or parent_id3==7:
                searchkeyword='废纸'
            elif parent_id==8 or parent_id2==8 or parent_id3==8:
                searchkeyword='废橡胶'
            elif parent_id==213 or parent_id2==213 or parent_id3==213:
                searchkeyword='原油'
        webtitle=title
        
        gmt_created=alist[2].strftime( '%-Y-%-m-%-d %-H:%-M:%-S')
        if str(type_id) in ("25","51","137"):
            content=zzprice.getwebprice(id)
        content=replacepic(content)
        #content=content.replace("http://price.zz91.com/priceDetails_","http://app.zz91.com/priceviews/")
        content=content.replace("http://price.zz91.com/","http://app.zz91.com/")
        list={'title':title,'content':content,'gmt_created':gmt_created}
        favoriteflag=0
        if company_id:
            favoriteflag=isfavorite(id,'10091004',company_id)
        list['favoriteflag']=favoriteflag
        listall.append(list)
    feizhibbsd=zzprice.getbbslist(searchkeyword,10)
    feizhibbs=None
    if feizhibbsd:
        feizhibbs=feizhibbsd['list']
    zhibuy=None
    #zhibuy=zzprice.offerlist(searchkeyword,'1',5)
    #zhioffer=zzprice.offerlist(searchkeyword,'0',3)
    if searchkeyword==None:
        searchkeyword=""
    if not priceabout:
        if category_id:
            priceabout=zzprice.getpricelist(searchkeyword,0,7,[int(category_id)],7)['list']
    ##返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'listall':listall,'priceabout':priceabout,'feizhibbs':feizhibbs,'offerlist':zhibuy}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('price/details.html',locals())

#----企业报价
def compdetails(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    id=request.GET.get("id")
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    webtitle="商家报价"
    sql='select title,details,category_company_price_code,refresh_time from company_price where id=%s'
    alist = zzprice.dbc.fetchonedb(sql,[id])
    if alist:
        title=alist[0]
        webtitle=title
        content=alist[1]
        category_cprice_code=alist[2]
        gmt_created=formattime(alist[3],3)
        sql2='select label from category_company_price where code=%s'
        alist2 = zzprice.dbc.fetchonedb(sql2,[category_cprice_code])
        if alist2:
            clabel=alist2[0]
            priceabout=zzprice.getpricelist(clabel,0,7,'',7)['list']
            zhioffer=zzprice.offerlist(clabel,'0',5)
            feizhibbsd=zzprice.getbbslist(clabel,10)
            if feizhibbsd:
                feizhibbs=feizhibbsd['list']
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'alist':alist,}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('price/compdetails.html',locals())

def qihuo(request):
    host=getnowurl(request)
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    webtitle="期货"
    return render_to_response('price/qihuo.html',locals())
def youse(request):
    host=getnowurl(request)
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    webtitle="有色"
    return render_to_response('price/youse.html',locals())
def jinshuarea(request):
    host=getnowurl(request)
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    webtitle="废金属全国各地价格"
#    jinshulist=zzprice.getprice_clist(3)
    return render_to_response('price/jinshuarea.html',locals())
def suliaoarea(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    webtitle="废塑料全国各地价格"
    host=getnowurl(request)
    return render_to_response('price/suliaoarea.html',locals())
def suliaoxinliao(request):
    webtitle="废塑料新料"
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    host=getnowurl(request)
    return render_to_response('price/suliaoxinliao.html',locals())
def areasuliao(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    host=getnowurl(request)
    webtitle="各地废塑料行情"
    return render_to_response('price/areasuliao.html',locals())
def suliaoqihuo(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    host=getnowurl(request)
    webtitle="塑料期货"
    return render_to_response('price/suliaoqihuo.html',locals())
def suliaozaishengliao(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    host=getnowurl(request)
    webtitle="塑料再生料价格"
    return render_to_response('price/suliaozaishengliao.html',locals())
def meiguosuliao(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    host=getnowurl(request)
    webtitle="美国废塑料价格"
    return render_to_response('price/meiguosuliao.html',locals())
def ouzhousuliao(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    host=getnowurl(request)
    webtitle="欧洲废塑料价格"
    return render_to_response('price/ouzhousuliao.html',locals())
def feizhidongtai(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    host=getnowurl(request)
    webtitle="废纸行情动态"
    return render_to_response('price/feizhidongtai.html',locals())
def feizhiarea(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    webtitle="废纸各地价格"
    host=getnowurl(request)
    return render_to_response('price/feizhiarea.html',locals())
def feizhiriping(request):
    nowlanmu="<a href='/priceindex/'>行情报价</a>"
    webtitle="废纸日评"
    host=getnowurl(request)
    return render_to_response('price/feizhiriping.html',locals())