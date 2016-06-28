#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
import simplejson
from django.http import HttpResponse,HttpResponseRedirect,HttpResponsePermanentRedirect
import MySQLdb,sys,os,memcache,settings,urllib,re,time,datetime,random,hashlib,requests
from django.core.cache import cache
import memcache
from zz91tools import subString,filter_tags,formattime,int_to_datetime
from zz91page import *
from zz91db_ast import companydb
from sphinxapi import *
from settings import spconfig,appurl
from zz91tools import getYesterday,getpastoneday

dbc=companydb()

reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/public_function.py")
execfile(nowpath+"/func/trade_function.py")
execfile(nowpath+"/func/qianbao_function.py")
execfile(nowpath+"/func/myrc_function.py")
execfile(nowpath+"/func/ldb_weixin_function.py")

zzq=zzqianbao()
zzms=mshop()
zzt=zztrade()
zzm=zmyrc()

#----首页
def index(request):
    host=getnowurl(request)
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    #----判断是否为来电宝,跳转到来电宝钱包
#    viptype=zzq.getviptype(company_id)
#    if viptype=='10051003':
#        isldb=1
#        return HttpResponseRedirect('/ldb_weixin/index.html')

    #----第一次登录钱包送20
#    getfee20=zzq.getsendfee(company_id,20,6)
    
    outfeeall2=zzq.getoutfeeall(company_id)
    #----余额
    blance=zzq.getqianbaoblance(company_id)
    #----累计充值
    infeeyd=zzq.getinfeegmt(company_id,ftype='(5)')
    #----总进账
    infeeall=zzq.getinfeegmt(company_id,notftype="(5)")
    #----昨日消费
    outfeeyd=zzq.getoutfeeyd(company_id)
    #----总消费
    outfeeall=zzq.getoutfeeall(company_id)
    
    #4-1号调价
    now = int(time.time())
    if now>1459440000:
        paymoney=300
    else:
        paymoney=100
    ##返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'blance':blance,'infeeyd':infeeyd,'infeeyd':infeeyd,'infeeall':infeeall,'outfeeyd':outfeeyd,'outfeeall':outfeeall}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('qianbao/index.html',locals())
#进账接口
def sendfee(request):
    #mc = memcache.Client(['192.168.110.119:11211'],debug=0)
    #scretkey=mc.get("scretkey")
    #key=request.GET.get('key')
    #if str(scretkey)==str(key):
    company_id=request.GET.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    ftype=request.GET.get('ftype')
    fee=request.GET.get('fee')
    more=request.GET.get('more')
    zzq.getsendfee(company_id,fee,ftype,more=more)
    return HttpResponse(company_id)
#出账扣费
def payfee(request):
    company_id=request.GET.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    ftype=request.GET.get('ftype')
    fee=request.GET.get('fee')
    more=request.GET.get('more')
    result=zzq.getpayfee(company_id=company_id,ftype=ftype,more=more)
    return HttpResponse(result)

def zhangdannore(request):
    
    timarg=request.GET.get('timarg')
    page=request.GET.get('page')
    if page:
        page=int(page)
    else:
        page=1
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    payfeelist=zzq.getpayfeelist(company_id,(page-1)*10,10,timarg)
    listall=payfeelist['list']
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(payfeelist, ensure_ascii=False))
    return render_to_response('qianbao/zhangdannore.html',locals())

#----账单
def zhangdan(request):
    #host=getnowurl(request)
    #backurl=request.META.get('HTTP_REFERER','/')
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    timarg=request.GET.get('timarg')
    payfeelist=zzq.getpayfeelist(company_id,0,10,timarg)
    listall=payfeelist['list']
    count=payfeelist['count']
    gmtdate=time.strftime('%Y-%m-01 00:00:',time.localtime(time.time()))
    #----本月进账
    infeegmtnowmonth=zzq.getinfeegmt(company_id,notftype="(5)")
    #----本月消费
    outfeegmtnowmonth=zzq.getoutfeegmt(company_id)
    #----本月充值
    outfee5gmtnowmonth=zzq.getinfeegmt(company_id,ftype='(5)')
    
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'infeegmtnowmonth':infeegmtnowmonth,'outfeegmtnowmonth':outfeegmtnowmonth,'outfee5gmtnowmonth':outfee5gmtnowmonth,'payfeelist':payfeelist}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('qianbao/zhangdan.html',locals())
#----充值
def chongzhi(request):
    #backurl=request.META.get('HTTP_REFERER','/')
    #host=getnowurl(request)
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    appsystem=request.GET.get("appsystem")
    paytype=request.GET.get("paytype")
    userimei=request.GET.get("userimei")
    isios=None
    if (appsystem=="iOS"):
        isios=1
    backurl="http://app.zz91.com/qianbao/chongzhisuc.html"
    companycontact=zzq.getcompanycontact(company_id)
    mobile=companycontact['mobile']
    contact=companycontact['contact']
    #4-1号调价
    now = int(time.time())
    if now>1459440000:
        paymoney=300
    else:
        paymoney=100
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'paytype':paytype,'companycontact':companycontact,'backurl':backurl,'company_id':company_id}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('qianbao/chongzhi.html',locals())
def chongzhisuc(request):
    return render_to_response('qianbao/chongzhisuc.html',locals())
#----进账说明
def intxt(request):
    backurl=request.META.get('HTTP_REFERER','/')
    listall=zzq.getftypelist("23")
    return render_to_response('qianbao/intxt.html',locals())
#----消费说明
def outtxt(request):
    backurl=request.META.get('HTTP_REFERER','/')
    return render_to_response('qianbao/outtxt.html',locals())
#----商城
def shop(request):
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    backurl=request.META.get('HTTP_REFERER','/')
    host=getnowurl(request)
    #listall=zzm.getmyproductslist(frompageCount=0,limitNum=1,company_id=company_id)
    #offerlist=listall['list']
    #if offerlist:
    #    offid1=offerlist[0]['proid']
    #----获取用户余额
    qianbaoblance=zzq.getqianbaoblance2(company_id)
    #----判断是否在微信推广中
    gmt_created=datetime.datetime.now()
#    is_wxtg=zzms.getis_wxtg(company_id,gmt_created,paytype=10)
    is_wxtg=''
    return render_to_response('qianbao/shop.html',locals())
#----商城简介
def simptxt(request):
    backurl=request.META.get('HTTP_REFERER','/')
    host=getnowurl(request)
    company_id=request.GET.get("company_id")
    return render_to_response('qianbao/simptxt.html',locals())
def oflist(request):
    host=getnowurl(request)
    company_id=request.GET.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    limitNum=7
    listall=zzm.getmyproductslist(frompageCount=0,limitNum=limitNum,company_id=company_id)
    count=listall['count']
    offerlist=listall['list']
#    if offerlist:
#        offid1=offerlist[0]['id']
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(listall, ensure_ascii=False))
    return render_to_response('qianbao/oflist.html',locals())
def offmore(request):
    company_id=request.GET.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    if company_id:
        page=request.GET.get('page')
        if page:
            page=int(page)
        else:
            page=1
        limitNum=7
        listall=zzm.getmyproductslist(frompageCount=(page-1)*limitNum,limitNum=limitNum,company_id=company_id)
        offerlist=listall['list']
        #返回json数据
        datatype=request.GET.get("datatype")
        if datatype=="json":
            return HttpResponse(simplejson.dumps(offerlist, ensure_ascii=False))
        return render_to_response('qianbao/offmore.html',locals())
#支付宝支付
def zz91pay(request):
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    total_fee = request.POST.get('total_fee')
    subject = request.POST.get('subject')
    mobile=request.POST.get("mobile")
    contact=request.POST.get("contact")
    todate=datetime.datetime.now()
    today=todate.strftime( '%Y%m%d')
    t=random.randrange(100000,999999)
    out_trade_no=str(today)+str(t)
    is_success="F"
    payreturn_url=request.POST.get("payreturn_url")
    paytype=request.POST.get("paytype")
    #total_fee=0.1
    #中断返回
    merchant_url="http://m.zz91.com/qianbao/"
    gmt_created=datetime.datetime.now()
    valu=[out_trade_no,subject,total_fee,contact,mobile,is_success,payreturn_url,paytype,company_id,gmt_created]
    sql="insert into pay_order(out_trade_no,subject,total_fee,contact,mobile,is_success,payreturn_url,paytype,company_id,gmt_created) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    dbc.updatetodb(sql,valu)
    
    #payload=str({'out_trade_no':out_trade_no,'subject':subject,'total_fee':total_fee,'merchant_url':merchant_url})
    payload="out_trade_no="+out_trade_no+"&total_fee="+total_fee+"&subject="+subject;
    messagedata={'err':'false','errkey':'','type':'chongzhi','content':payload}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
    #alipay = Alipay(pid='2088511051388426', key='ovtvgwuew1zdfmbiydepr0k9m8b25exp', seller_email='zhifu@asto-inc.com')
    #payurl=alipay.create_direct_pay_by_user_url(out_trade_no=out_trade_no, subject=subject, total_fee=total_fee, return_url='http://m.zz91.com/zz91payreturn_url.html', notify_url='http://m.zz91.com/zz91payverify_notify.html')
    #return HttpResponseRedirect(payurl)
def zz91paysubmit(request):
    out_trade_no=request.GET.get("out_trade_no")
    total_fee=request.GET.get("total_fee")
    subject=request.GET.get("subject")
    #subject="手机钱包充值"
    merchant_url="http://m.zz91.com/qianbao/"
    
    payload={'out_trade_no':out_trade_no,'subject':subject,'total_fee':total_fee,'merchant_url':merchant_url}
    #payload=simplejson.dumps(payload, ensure_ascii=False)
    #return HttpResponse(payload)
    r= requests.post("http://www.zz91.com/pay/alipayapi.jsp",data=payload)
    return HttpResponse(r.content)
def zz91payrequery(request):
    requestlist='seller_id="zhifu@asto-inc.com"&partner="2088511051388426"&out_trade_no="20141018140245"&subject="DCloud项目捐赠"&body="DCloud致力于打造HTML5最好的移动开发工具，包括终端的Runtime、云端的服务和IDE，同时提供各项配套的开发者服务。"&total_fee="10"&_input_charset="UTF-8"&service="mobile.securitypay.pay"&payment_type="1"&quantity="1"&it_b_pay="1d"&show_url="http://demo.dcloud.net.cn/helloh5/payment/"&return_url="http://demo.dcloud.net.cn/helloh5/payment/"&notify_url="http://demo.dcloud.net.cn/helloh5/payment/"&sign="Cw3mklTi9ZJVsVDdcPvm4TtOEcBFM0fiTAvt%2FJgo58QfWpa8eNqWCElE18TT3OivwLd%2BRL%2FrL9IW1VSF3WnlOc2vzUX4fhQg0rPBya8Bgu4%2BeEsSWU1oFOzz%2BBnmSAkcqLBv4i%2Fi2E4qajFkTMXS0jouN05GchOXkVGL%2FMnz8gw%3D"&sign_type="RSA"'
    return HttpResponse(requestlist)
#在线商城
def qianbaopay(request):
    company_id=request.GET.get("company_id")
    errflag='true'
    errtext='系统错误'
    blanceflag='1'
    if not company_id or str(company_id)=="0":
        errflag='true'
        errtext='未登录，请重新登录！'
    if company_id:
        paytype=str(request.GET.get('paytype'))
        money=request.GET.get('money')
        proid=request.GET.get('proid')
        mobile=request.GET.get('mobile')
        if money:
            money=int('-'+str(money))
        #----手机钱包付费
        gmt_end=''
        gmt_created=datetime.datetime.now()
        gmt_date=datetime.date.today()
        #供求置顶
        if paytype=='9':
#            result=1
            type='10431004'
            sql2='select id from products_keywords_rank where product_id=%s and type=%s and is_checked=0'
            result2=dbc.fetchonedb(sql2,[proid,type])
            if result2:
                errflag='true'
                errtext='该供求已经设置过置顶操作，请选择其他供求。'
            else:
                result=zzq.getpayfee(company_id=company_id,product_id=proid,ftype=paytype)
                if result==1:
                    company_account=zzq.getcompany_account(company_id)
                    sql='insert into products_keywords_rank(product_id,is_checked,gmt_created,gmt_modified,company_id,apply_account,type,bz) values(%s,%s,%s,%s,%s,%s,%s,%s)'
                    dbc.updatetodb(sql,[proid,0,gmt_created,gmt_created,company_id,company_account,type,mobile])
                    errflag='false'
                    errtext='恭喜您，您已经设置成功！'
                else:
                    errflag='true'
                    errtext='余额不足，请充值！'
                    blanceflag='0'
        #微信优质信息推广
        elif paytype=='10':
#            result=1
            sql2='select id from shop_product_wxtg where company_id=%s and is_check=0'
            result2=dbc.fetchonedb(sql2,[company_id])
            if result2:
                errflag='true'
                errtext='该供求已经设置过置顶操作，请选择其他供求。'
            else:
                result=zzq.getpayfee(company_id=company_id,product_id=proid,ftype=paytype)
                if result==1:
                    sql='insert into shop_product_wxtg(company_id,mobile,is_check,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s)'
                    dbc.updatetodb(sql,[company_id,mobile,'0',gmt_date,gmt_created,gmt_created])
                    errflag='false'
                    errtext='恭喜您，您已经设置成功！'
                else:
                    errflag='true'
                    errtext='余额不足，请充值！'
                    blanceflag='0'
                
        #显示联系方式
        elif paytype=='11':
            sql2='select id from shop_showphone where company_id=%s and gmt_end>=%s order by id desc'
            result=dbc.fetchonedb(sql2,[company_id,gmt_created])
            if result:
                errflag='true'
                errtext='您已经开通了显示联系方式的服务，请不必重复开通。'
            result=zzq.getpayfee(company_id=company_id,ftype=paytype,fee=money)
            if result==1:
                gmt_begin=int(time.time())
                if money==-5:
                    gmt_end=gmt_begin+(3600*24)
                if money==-25:
                    gmt_end=gmt_begin+(3600*24*7)
                if money==-120:
                    gmt_end=gmt_begin+(3600*24*30)
                if money==-1200:
                    gmt_end=gmt_begin+(3600*24*365)
                gmt_begin=int_to_datetime(gmt_begin)
                gmt_end=int_to_datetime(gmt_end)
                sql='insert into shop_showphone(company_id,gmt_begin,gmt_end,gmt_created,gmt_date) values(%s,%s,%s,%s,%s)'
                dbc.updatetodb(sql,[company_id,gmt_begin,gmt_end,gmt_created,gmt_date])
                errflag='false'
                errtext='恭喜您，您已经成功开通显示联系方式的服务！'
            else:
                errflag='true'
                errtext='余额不足，请充值！'
                blanceflag='0'
        #购买商务大全
        elif paytype=='16':
            baoming=request.GET.get('baoming')
            result=zzq.getpayfee(company_id=company_id,ftype=paytype)
            if str(result)=="1":
                sql='insert into shop_baoming(company_id,content,gmt_created,paytype) values(%s,%s,%s,%s)'
                dbc.updatetodb(sql,[company_id,baoming,gmt_created,paytype])
                errflag='false'
                errtext='恭喜您，您已经购买成功，我们会尽快和您联系！'
            elif result=="nomoney":
                errflag='true'
                errtext='余额不足，请充值！'
                blanceflag='0'
        #移动端列表页广告
        elif paytype=='17':
            baoming=request.GET.get('baoming')
            result=zzq.getpayfee(company_id=company_id,ftype=paytype)
            if result==1:
                sql='insert into shop_baoming(company_id,content,gmt_created,paytype) values(%s,%s,%s,%s)'
                dbc.updatetodb(sql,[company_id,baoming,gmt_created,paytype])
                errflag='false'
                errtext='恭喜您，您已经成功开通显示联系方式的服务！'
            elif result=="nomoney":
                errflag='true'
                errtext='余额不足，请充值！'
                blanceflag='0'
        #再生资源商务大全App抢购价
        elif paytype=='20':
            baoming=request.GET.get('baoming')
            result=zzq.getpayfee(company_id=company_id,ftype=paytype,more=1)
            if str(result)=="1":
                sql='insert into shop_baoming(company_id,content,gmt_created,paytype) values(%s,%s,%s,%s)'
                dbc.updatetodb(sql,[company_id,baoming,gmt_created,paytype])
                errflag='false'
                errtext='恭喜您，您已经购买成功，我们会尽快和您联系！'
            elif result=="nomoney":
                errflag='true'
                errtext='余额不足，请充值！'
                blanceflag='0'
            elif result=="havepay":
                errflag='true'
                errtext='你已经购买了该服务！'
        #再生钱包100元礼包抢购价
        elif paytype=='21':
            result=zzq.getpayfee(company_id=company_id,ftype=paytype,more=1)
            if str(result)=="1":
                return HttpResponse('qianbaosuc')
            elif result=="nomoney":
                return HttpResponse('nomoney')
            elif result=="havepay":
                return HttpResponse('havepay')
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'err':errflag,'errtext':errtext,'blanceflag':blanceflag}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return HttpResponse('0')
def qianbaobaoblance(request):
    company_id=request.GET.get("company_id")
    baoblance=0
    if company_id:
        baoblance=zzq.getqianbaoblance(company_id)
    return HttpResponse(baoblance)

#抢购页面
def qg(request):
    gid=request.GET.get("gid")
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    list=zzq.getadgoods(gid=gid)
    if list:
        paytype=list['billing_Class_ID']
        paymoney=zzq.getpay_wallettypefee(list['billing_Class_ID'])
    qianbaoblance=zzq.getqianbaoblance(company_id)
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'list':list,'paytype':paytype,'paymoney':paymoney,'qianbaoblance':qianbaoblance}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('qianbao/qg.html',locals())
def qg_tourl(request):
    gid=request.GET.get("gid")
    if gid[0:1]=="p":
        return HttpResponseRedirect("/detail/?id="+str(gid[1:len(gid)]))
    list=zzq.getadgoods(gid=gid)
    tourl=list['tourl']
    return HttpResponseRedirect(tourl)
#签到
def qiandao(request):
    company_id=request.GET.get("company_id")
    """
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken):
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    """
    gmt_created=datetime.datetime.now()
    gmt_date=datetime.date.today()
    sql="select id from app_qiandao where company_id=%s and gmt_date=%s"
    result=dbc.fetchonedb(sql,[company_id,gmt_date])
    if result:
        messagedata={'err':'true','errkey':'已经签到'}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
    else:
        sql="insert into app_qiandao(company_id,gmt_date,gmt_created) values(%s,%s,%s)"
        dbc.updatetodb(sql,[company_id,gmt_date,gmt_created])
        ftype="29"
        fee="0.5"
        more=1
        zzq.getsendfee(company_id,fee,ftype,more=more)
        messagedata={'err':'false','errkey':''}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
    