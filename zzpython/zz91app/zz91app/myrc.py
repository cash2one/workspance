#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
import simplejson
from django.http import HttpResponse,HttpResponseRedirect,HttpResponsePermanentRedirect
import MySQLdb,sys,os,memcache,settings,urllib,re,datetime,time,random,hashlib
from django.core.cache import cache
from zz91db_ast import companydb
from zz91db_2_news import newsdb
from zz91conn import database_mongodb
from zz91tools import int_to_strall,formattime
from settings import spconfig,appurl
from sphinxapi import *
from zz91page import *
dbc=companydb()
dbn=newsdb()
reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/public_function.py")
execfile(nowpath+"/func/qianbao_function.py")
execfile(nowpath+"/func/message_function.py")
execfile(nowpath+"/func/myrc_function.py")
execfile(nowpath+"/func/weixin_function.py")

zzm=zmyrc()
zzw=zzweixin()
zzq=zzqianbao()
zzms=zzmessage()
dbmongo=database_mongodb()

#生意管家---------------------------------------
def myrc_index(request):
    myrc=1
    webtitle="生意管家"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    #留言量
    qcount=zzms.getnoviewmessagecount(company_id,1)
    #qcount=20
    faceurl=None
    sql="select picture_path from bbs_user_profiler where company_id=%s"
    piclist=dbc.fetchonedb(sql,[company_id])
    faceurl=None
    if piclist:
        if piclist[0]:
            faceurl="http://img3.zz91.com/100x100/"+piclist[0]
    """
    account=zzm.getcompanyaccount(company_id)
    sql1="select count(0) from inquiry where receiver_account=%s and is_viewed=0"
    result1=dbc.fetchonedb(sql1,[account])
    leavewordscount=None
    if result1:
        leavewordscount=result1[0]
        if leavewordscount==0:
            leavewordscount=None
    """
    #----余额
    blance=zzq.getqianbaoblance(company_id)
    #是否来电宝
    ldbvalue=getldbphone(company_id)
    #是否签到
    gmt_date=datetime.date.today()
    sql="select id from app_qiandao where company_id=%s and gmt_date=%s"
    result=dbc.fetchonedb(sql,[company_id,gmt_date])
    if result:
        qiandao=1
    else:
        qiandao=None

    if company_id:
        sql="select company_id,account,contact,sex from company_account where company_id=%s"
        listc=dbc.fetchonedb(sql,[company_id])
        if listc:
            company_id=listc[0]
            contact=listc[2]
            sex=listc[3]
            if str(sex)=="0":
                if ("先生" not in contact) and ("女士" not in contact):
                    contact+="先生"
            else:
                if ("先生" not in contact) and ("女士" not in contact):
                    contact+="女士"
    list={'contact':contact,'blance':blance,'qcount':qcount,'ldbvalue':ldbvalue,'qiandao':qiandao,'faceurl':faceurl}
    ##返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(list, ensure_ascii=False))
    return render_to_response('myrc/index.html',locals())

#----我的社区
def myrc_mycommunity(request):
    webtitle="消息中心"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    account=zzm.getcompanyaccount(company_id)
    """
    #----更新弹窗
    zzm.updateopenfloat(company_id,1);
    #判断是否已经填写了昵称
    mynickname=zzm.getcompanynickname(company_id)
    if not mynickname or mynickname=="":
        nickname=request.GET.get("nickname")
        if nickname:
            addcompanynickname(nickname,company_id,username)
    #判断是否填写关注行业
    myguanzhu=zzm.gethuzhuguanzhu(company_id)
    if not myguanzhu or myguanzhu=="":
        myguanzhu=request.REQUEST.getlist("myguanzhu")
        if myguanzhu:
            zzm.addmyzhuzhuguanzhu(myguanzhu,company_id,username)
    """
    invitelist=zzm.getbbs_invite(company_id)
    if invitelist:
        invitecount=len(invitelist)
    else:
        invitecount=0
        
    #huzhureply=zzm.getmessgelist(company_id,0,10)
    #if huzhureply:
        #listall=huzhureply['list']
        #replycount=int(huzhureply['count'])+invitecount
    
    page=request.GET.get("page")
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(5)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(2)
    before_range_num = funpage.before_range_num(9)
    qlistall=zzm.getmessgelist(company_id,frompageCount,limitNum)
    listall=qlistall['list']
    listcount=qlistall['count']
    replycount=int(listcount)+invitecount
    if (int(listcount)>100000):
        listcount=100000
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    ##返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'qlistall':qlistall,'invitelist':invitelist}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('myrc/mycommunity.html',locals())
#
def myrc_mycommunitydel(request):
    pid=request.POST.get("pid")
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    ptype=request.POST.get("ptype")
    post_id=request.POST.get("post_id")
    gmt_created=gmt_modified=datetime.datetime.now()
    messagedata=""
    if ptype=="invitedel":
        sql="select id from bbs_invite where id=%s"
        returnone=dbc.fetchonedb(sql,[pid])
        if returnone:
            sql="update bbs_invite set is_del=1 and answercheck=3 where id=%s"
            dbc.updatetodb(sql,[pid])
        else:
            sql="insert into bbs_invite(post_id,company_id,is_del,answercheck,gmt_created) values(%s,%s,%s,%s,%s)"
            dbc.updatetodb(sql,[post_id,company_id,1,3,gmt_created])
        messagedata={'err':'false','errkey':'','type':'mycommunitydel'}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
def myrc_mypostsave(request):
    company_id=request.session.get("company_id",None)
    username=request.session.get("username",None)
    return HttpResponse("suc")

def myrc_myreplysave(request):
    pid=request.GET.get("pid")
    ptype=request.GET.get("ptype")
    company_id=request.GET.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    username=request.session.get("username",None)
    if username and company_id==None:
        company_id=getcompanyid(username)
        request.session['company_id']=company_id
    if (username==None or company_id==None):
        return HttpResponseRedirect("/login/")
    
    tocompany_id=request.GET.get("tocompany_id")
    account=getcompanyaccount(company_id)
    bbs_post_id=request.GET.get("post_id")
    title="回复:"+getbbspost_title(bbs_post_id)
    content=request.GET.get("replycontent")
    check_status=1
    gmt_created=gmt_modified=datetime.datetime.now()
    if ptype=="invitereply":
        valu=[company_id,account,title,tocompany_id,bbs_post_id,content,check_status,gmt_created,gmt_modified,1]
        sql="insert into bbs_post_reply(company_id,account,title,tocompany_id,bbs_post_id,content,check_status,gmt_created,gmt_modified,postsource) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.execute(sql,valu)
    if ptype=="postreply":
        valu=[company_id,account,title,tocompany_id,bbs_post_id,content,check_status,gmt_created,gmt_modified,pid,1]
        sql="insert into bbs_post_reply(company_id,account,title,tocompany_id,bbs_post_id,content,check_status,gmt_created,gmt_modified,bbs_post_reply_id,postsource) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.execute(sql,valu)
    updatepostviewed(tocompany_id,bbs_post_id)
    #----更新弹窗
    updateopenfloat(tocompany_id,0)
    sql="update bbs_post set reply_time=%s,reply_count=reply_count+1,gmt_modified=%s where id=%s"
    cursor.execute(sql,[gmt_modified,gmt_modified,bbs_post_id])
    return HttpResponse("suc")

#-我的提问
def myrc_mypost(request):
    webtitle="我的提问"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    #account=zzm.getcompanyaccount(company_id)
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")

    
    page=request.GET.get("page")
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(5)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(2)
    before_range_num = funpage.before_range_num(9)
    qlistall=zzm.getmyquestion(company_id,frompageCount,limitNum)
    listall=qlistall['list']
    listcount=qlistall['count']

    if (int(listcount)>100000):
        listcount=100000
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    ##返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(qlistall, ensure_ascii=False))
    return render_to_response('myrc/mypost.html',locals())
#我的回复
def myrc_myreply(request):
    webtitle="我的回复"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    page=request.GET.get("page")
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(5)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(2)
    before_range_num = funpage.before_range_num(9)
    qlistall=zzm.getmyreply(company_id,frompageCount,limitNum)
    listall=qlistall['list']
    listcount=qlistall['count']

    if (int(listcount)>100000):
        listcount=100000
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    ##返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(qlistall, ensure_ascii=False))
    return render_to_response('myrc/myreply.html',locals())

#---刷新供求
def products_refresh(request):
    gmt_created=datetime.datetime.now()
    proid=request.POST.get("proid")
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    if proid=="0" or proid==None:
        messagedata={'err':'true','errkey':'请选择一条供求刷新'}
    else:
        messagedata={'err':'false','errkey':'','type':'proreflush'}
        sql="update products set refresh_time=%s,gmt_modified=%s where id in ("+str(proid)+") and company_id = %s"
        dbc.updatetodb(sql,[gmt_created,gmt_created,company_id])
        
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#---刷新供求
def products_refreshall(request):
    gmt_created=datetime.datetime.now()
    company_id=request.GET.get("company_id")
    if not company_id:
        company_id=request.POST.get("company_id")
    if company_id=="0" or company_id==None:
        messagedata={'err':'true','errkey':'请选择一条供求刷新'}
    else:
        messagedata={'err':'false','errkey':'','type':'proreflush'}
        sql="update products set refresh_time=%s,gmt_modified=%s where company_id = %s"
        dbc.updatetodb(sql,[gmt_created,gmt_created,company_id])
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#---暂不发布
def products_stop(request):
    gmt_created=datetime.datetime.now()
    proid=request.POST.get("proid")
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    if proid=="0" or proid==None:
        messagedata={'err':'true','errkey':'请选择一条供求'}
    else:
        messagedata={'err':'false','errkey':'','type':'prostop'}
        sql="update products set is_pause=1,gmt_modified=%s where id in ("+str(proid)+") and company_id = %s"
        dbc.updatetodb(sql,[gmt_created,company_id])
        
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#---重新发布
def products_start(request):
    gmt_created=datetime.datetime.now()
    proid=request.POST.get("proid")
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    if proid=="0" or proid==None:
        messagedata={'err':'true','errkey':'请选择一条供求信息后操作！'}
    else:
        messagedata={'err':'false','errkey':'','type':'prorestart'}
        sql="update products set is_pause=0,gmt_modified=%s where id in ("+str(proid)+") and company_id = %s"
        dbc.updatetodb(sql,[gmt_created,company_id])
        
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#定制首页(一期)
def orderindex(request):
    host=getnowurl(request)
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    return render_to_response('order/index.html',locals())
#商机定制
def orderbusiness(request):
    host=getnowurl(request)
    ordercategorylist=zzm.getordercategorylistmain()
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    if company_id:
        mybusinesscollect=zzm.get_mybusinesscollectid(company_id)
        listall=[]
        for li in ordercategorylist:
            childlist=li['childlist']
            listall1=[]
            parentcheck=0
            for list in childlist:
                list1={'title':list['title'],'id':list['id']}
                id=list['id']
                if str(id) in mybusinesscollect:
                    list1['selected']=1
                    parentcheck=1
                listall1.append(list1)
            list2={'code':li['code'],'label':li['label'],'childlist':listall1}
            if parentcheck==1:
                list2['selected']=1
            listall.append(list2)
        ordercategorylist=listall
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(ordercategorylist, ensure_ascii=False))
    return render_to_response('order/business.html',locals())
#新版app行情定制
def myorderprice(request):
    host=getnowurl(request)
    company_id=request.POST.get("company_id")
    usertoken=request.POST.get("usertoken")
    appsystem=request.POST.get("appsystem")
    datatype=request.POST.get("datatype")
    if company_id:
        mypriceorderlist=zzm.getmyorderprice(company_id)
        if datatype=="json":
            if mypriceorderlist==[]:
                myprice=None
            else:
                myprice=mypriceorderlist
            messagedata={'err':'false','errkey':'','type':'mycollect','listall':myprice}
            return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#新版app行情定制保存
def myorderprice_save(request):
    company_id=request.POST.get("company_id")
    usertoken=request.POST.get("usertoken")
    appsystem=request.POST.get("appsystem")
    datatype=request.POST.get("datatype")
    label=request.POST.get("label")
    category_id=request.POST.get("category_id")
    assist_id=request.POST.get("assist_id")
    keywords=request.POST.get("keywords")
    if not company_id:
        company_id=request.GET.get("company_id")
        label=request.GET.get("label")
        category_id=request.GET.get("category_id")
        assist_id=request.GET.get("assist_id")
        keywords=request.GET.get("keywords")
    list={
          'label':label,
          'company_id':company_id,
          'category_id':category_id,
          'assist_id':assist_id,
          'keywords':keywords}
    result=zzm.savemyorderprice(list)
    if result:
        messagedata={'err':'false','errkey':'','type':'savecollect','orderid':result}
    else:
        messagedata={'err':'true','errkey':'已经添加','type':'savecollect'}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#新版 app 删除定制
def myorderprice_del(request):
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    datatype=request.GET.get("datatype")
    orderid=request.GET.get("orderid")
    sql="delete from app_order_price where id=%s and company_id=%s"
    result=dbc.updatetodb(sql,[orderid,company_id])
    if result:
        messagedata={'err':'false','errkey':'','type':'delcollect','orderid':result}
    else:
        messagedata={'err':'true','errkey':'已经添加','type':'delcollect'}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#新版app 我的供求定制
def myordertrade(request):
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    datatype=request.GET.get("datatype")
    result=zzm.getmyordertrade(company_id)
    messagedata={'err':'false','errkey':'','type':'mycollect','listall':result}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#新版app 供求定制
def myordertrade_save(request):
    company_id=request.POST.get("company_id")
    usertoken=request.POST.get("usertoken")
    appsystem=request.POST.get("appsystem")
    datatype=request.POST.get("datatype")
    otype=request.POST.get("otype")
    timelimit=request.POST.get("timelimit")
    keywordslist=request.POST.get("keywordslist")
    provincelist=request.POST.get("provincelist")
    list={
          'otype':otype,
          'company_id':company_id,
          'timelimit':timelimit,
          'keywordslist':keywordslist,
          'provincelist':provincelist}
    result=zzm.savemyordertrade(list)
    messagedata={'err':'false','errkey':'','type':'savecollect','orderid':result}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))

#行情定制
def orderprice(request):
    host=getnowurl(request)
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    datatype=request.GET.get("datatype")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    if company_id:
        mypricecollect=zzm.get_mypricecollectid(company_id)
        if datatype=="json":
            jsonlist={'listall':mypricecollect}
            return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
        category1=[40,328,41,45,44,47,43,48,51,279,46,308,206,69,70,71,72,79,80,81,210,83,84,86,208,32,33,216]
        category2=[40,328,41,45,44,47,43,48,51,279,46,308,206,69,70,71,72]
        
        sql="select name,id from price_category where parent_id='1' and showflag=1"
        alist = dbc.fetchalldb(sql)
        if alist:
            listall=[]
            for a in alist:
                id=a[1]
                sql="select name,id from price_category where parent_id=%s and showflag=1"
                blist = dbc.fetchalldb(sql,[id])
                if blist:
                    listb=[]
                    parentcheck2=None
                    for bl in blist:
                        idb=bl[1]
                        sql="select name,id from price_category where parent_id=%s and showflag=1"
                        clist = dbc.fetchalldb(sql,[idb])
                        if clist:
                            listc=[]
                            parentcheck1=None
                            for cl in clist:
                                idc=cl[1]
                                namec=cl[0]
                                if str(idc) in mypricecollect:
                                    checkflag=1
                                    parentcheck1=1
                                    parentcheck2=1
                                else:
                                    checkflag=None
                                list_c={'id':idc,'name':namec,'checked':checkflag}
                                listc.append(list_c)
                        nameb=bl[0]
                        if clist:
                            list_b={'id':idb,'name':nameb,'checked':parentcheck1}
                            list_b['listc']=listc
                            listb.append(list_b)
                name=a[0]
                #childmemu=zzm.getcate(id)
                if listb:
                    list={'id':id,'name':name,'checked':parentcheck2}
                    list['listb']=listb
                    listall.append(list)
            #返回json数据
            
            if datatype=="json":
                jsonlist={'listall':listall,'alist':alist}
                return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('order/price.html',locals())
#保存定制
def save_collect(request):
    host=getnowurl(request)
    #获取定制类型,1是商机,2是行情
    collect_type=request.POST.get('collect_type')
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    #获取定制信息
    #customp=request.POST.getlist('ordervalue')
    ordervalue=request.POST.get('ordervalue')
    ordervaluelist=request.POST.get('ordervaluelist')
    llist=ordervalue
    #for l in ordervalue:
        #llist=llist+","+l
    #llist=llist[1:]
    gmt_created=datetime.datetime.now()
        #保存定制信息进入数据库
    if ordervaluelist or ordervalue:
        zzm.savecollect(company_id,llist,gmt_created,collect_type,keywordslist=ordervaluelist)
    else:
        zzm.savecollect(company_id,"",gmt_created,collect_type,keywordslist="")
    messagedata={'err':'false','errkey':'','type':'savecollect'}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))

#我的定制
def myrc_collect(request):
    
    webtitle="我的定制商机"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    #获得商机定制信息
    mybusinesscollect=zzm.get_mybusinesscollect(company_id)
    mypricecollect=zzm.get_mypricecollect(company_id)
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'mybusinesscollect':mybusinesscollect,'mypricecollect':mypricecollect}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('myrc/myrc_collect2.html',locals())
#我的定制
def myrc_collectprice(request):
    webtitle="我的行情定制"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    #获得商机定制信息
    mypricecollect=zzm.get_mypricecollect(company_id)
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(mypricecollect, ensure_ascii=False))
    return render_to_response('myrc/myrc_collect3.html',locals())
#我的定制
def myrc_collectmain(request):
    webtitle="我的定制商机"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    weixinautologin(request,request.GET.get("weixinid"))
    if (company_id==None):
        return HttpResponseRedirect("/login/?done=/myrc_collectmain/")
    #获得商机定制信息
    #mybusinesscollect=get_mybusinesscollect(company_id)
    #mypricecollect=get_mypricecollect(company_id)
    return render_to_response('myrc/myrc_collectmain.html',locals())
#我的询盘
def myrc_leavewords(request):
    webtitle="生意管家"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    
    sendtype=request.GET.get("sendtype")
    if sendtype==None:
        sendtype="0"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    account=zzm.getcompanyaccount(company_id)
    #----更新弹窗
    #zzm.updateopenfloat(company_id,1)
    sql1="select count(0) from inquiry where receiver_account=%s and is_viewed=0"
    result1=dbc.fetchonedb(sql1,[account])
    if result1:
        alist1=result1[0]
    page=request.GET.get("page")
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(5)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(2)
    before_range_num = funpage.before_range_num(9)
    qlistall=zzm.getleavewordslist(frompageCount,limitNum,company_id,str(sendtype))
    qlist=qlistall['list']
    qlistcount=qlistall['count']
    listcount=qlistcount
    if (int(listcount)>100000):
        listcount=100000
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(qlistall, ensure_ascii=False))
    return render_to_response('myrc/leavewords.html',locals())

def myrc_backquestion(request):
    webtitle="留言回复"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    account=zzm.getcompanyaccount(company_id)
    
    sendcompany_id=request.GET.get("sendcom_id")
    inquired_id=request.GET.get("inquired_id")

    sendflag=0
    if str(sendcompany_id)!=str(company_id):
        sendflag=1
    
    #getupdatelookquestion(inquired_id)
    qlist=zzm.getleavewords(inquired_id)
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={"sendflag":sendflag,'qlist':qlist}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('myrc/backquestion.html',locals())
def myrc_backquestionsave(request):
    webtitle="留言回复"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    account=zzm.getcompanyaccount(company_id)
    
    send_username=account
    send_company_id=company_id
    
    re_company_id = request.POST.get('sendcompany_id')
    
    title='我对贵公司的产品感兴趣！'
    content = request.POST.get('content')
    
    if content=="":
        messagedata={'err':'true','errkey':'请填写回复的内容！','type':'questionback'}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
    
    be_inquired_type=2
    be_inquired_id=request.POST.get('be_inquired_id')
    inquired_type=0
    inquired_id=request.POST.get('inquired_id')
    sender_account=send_username
    receiver_account=zzm.getcompanyaccount(re_company_id)
    #return HttpResponse(receiver_account)
    send_time=datetime.datetime.now()
    gmt_created=datetime.datetime.now()
    gmt_modified=datetime.datetime.now()
    value=[title,content,be_inquired_type,be_inquired_id,inquired_type,sender_account,receiver_account,send_time,gmt_created,gmt_modified,inquired_id]
    sql="insert into inquiry(title,content,be_inquired_type,be_inquired_id,inquired_type,sender_account,receiver_account,send_time,gmt_created,gmt_modified,inquired_id) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    dbc.updatetodb(sql,value)
    #----更新弹窗
    #updateopenfloat(re_company_id,0)
    messagedata={'err':'false','errkey':'回复成功','type':'questionback'}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#我的收藏夹
def myrc_favorite(request):
    webtitle="生意管家"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    company_id=request.GET.get("company_id")
    
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    #company_id=1310082
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    checkStatus=request.GET.get("checkStatus")
    if (checkStatus=="" or checkStatus==None):
        checkStatus=1
    
    page=request.GET.get("page")
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(20)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(2)
    before_range_num = funpage.before_range_num(9)
    qlistall=zzm.getfavoritelist(frompageCount=frompageCount,limitNum=limitNum,company_id=company_id,checkStatus=checkStatus)
    qlist=qlistall['list']
    qlistcount=qlistall['count']
    listcount=qlistcount
    if (int(listcount)>100000):
        listcount=100000
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    
    #供求信息数量
    sql1="select count(0) from myfavorite where company_id=%s and (favorite_type_code=10091006 or favorite_type_code=10091000 or favorite_type_code=10091001 or favorite_type_code=10091007 )"                                            
    result1=dbc.fetchonedb(sql1,[company_id])
    if result1:
        alist1=result1[0]
    #公司信息数量    
    sql2="select count(0) from myfavorite where company_id=%s and (favorite_type_code=10091002 or favorite_type_code=10091003 or favorite_type_code=10091004) "
    result2=dbc.fetchonedb(sql2,[company_id])
    if result2:
        alist2=result2[0]
    #报价信息数量    
    sql3="select count(0) from myfavorite where company_id=%s and favorite_type_code=10091004 "
    result3=dbc.fetchonedb(sql3,[company_id])
    if result3:
        alist3=result3[0]
    #互助社区数量
    sql4="select count(0) from myfavorite where company_id=%s and favorite_type_code=10091005 "
    result4=dbc.fetchonedb(sql4,[company_id])
    if result4:
        alist4=result4[0]
    #资讯中心数量
    sql5="select count(0) from myfavorite where company_id=%s and favorite_type_code=10091012 "
    result5=dbc.fetchonedb(sql5,[company_id])
    if result5:
        alist5=result5[0]   
    
    if page>1:
        #返回json数据
        datatype=request.GET.get("datatype")
        if datatype=="json":
            return HttpResponse(simplejson.dumps(qlistall, ensure_ascii=False))
        return render_to_response('myrc/favorite_more.html',locals())
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(qlistall, ensure_ascii=False))
    return render_to_response('myrc/favorite.html',locals())

def del_favorite(request):
    company_id=request.POST.get("company_id")
    id=request.POST.get("id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    sql="delete from myfavorite where id=%s and company_id=%s"
    dbc.updatetodb(sql,[id,company_id])
    messagedata={'err':'false','errkey':'删除成功','type':'favorite'}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))

#----生意管家 我的供求信息
def myrc_products(request):
    webtitle="生意管家"
    nowlanmu="<a href='/myrc_index/'>生意管家</a>"
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    page=request.GET.get("page")
    checkStatus=request.GET.get("checkStatus")
    if (checkStatus=="" or checkStatus==None):
        checkStatus=1
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(5)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(2)
    before_range_num = funpage.before_range_num(9)
    qlistall=zzm.getmyproductslist(frompageCount=frompageCount,limitNum=limitNum,company_id=company_id,checkStatus=checkStatus)
    qlist=qlistall['list']
    qlistcount=qlistall['count']
    listcount=qlistcount
    if (int(listcount)>100000):
        listcount=100000
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    
    sql1="select count(0) from products where company_id=%s and check_status=1 and is_del=0 and is_pause=0 and refresh_time<expire_time"
    result1=dbc.fetchonedb(sql1,[company_id])
    if result1:
        alist1=result1[0]
    sql0="select count(0) from products where company_id=%s and check_status=0 and is_del=0 and is_pause=0 and refresh_time<expire_time"
    result0=dbc.fetchonedb(sql0,[company_id])
    if result0:
        alist0=result0[0]
    sql2="select count(0) from products where company_id=%s and check_status=2 and is_del=0 and is_pause=0 and refresh_time<expire_time"
    result2=dbc.fetchonedb(sql2,[company_id])
    if result2:
        alist2=result2[0]
    sql3="select count(0) from products where company_id=%s and is_pause=1 and is_del=0 "
    result3=dbc.fetchonedb(sql3,[company_id])
    if result3:
        alist3=result3[0]
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'checkStatus':checkStatus,'qlistall':qlistall,'count':{'c0':alist0,'c1':alist1,'c2':alist2,'c3':alist3}}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('myrc/products.html',locals())

#----修改供求
def products_update(request):
    saveform="products_updatesave"
    proid=request.GET.get("proid")
    randid = random.randint(10000, 99999)
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")

    myproductsbyid=zzm.getmyproductsbyid(proid)
    if myproductsbyid:
        categorycode=myproductsbyid['categorycode']
        category=categorycode
        title=myproductsbyid['title']
        quantity=myproductsbyid['quantity']
        ptype=products=myproductsbyid['products_type_code']
        quantity_unit=myproductsbyid['quantity_unit']
        price=myproductsbyid['price']
        validity=myproductsbyid['validity']
        if price:
            pricetype='1'
        else:
            pricetype='0'
        price_unit=myproductsbyid['price_unit']
        details=myproductsbyid['details']
    
    piclist=zzm.getmyproductspic(proid)
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'piclist':piclist,'myproductsbyid':myproductsbyid}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    return render_to_response('myrc/pro_edit.html',locals())
#我的通讯录
def my_addressbook(request):
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    page=request.GET.get("page")
    if (page==None):
        page=1
    funpage = zz91page()
    limitNum=funpage.limitNum(10)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(2)
    before_range_num = funpage.before_range_num(9)
    qlistall=zzm.getmyaddressbooklist(frompageCount=frompageCount,limitNum=limitNum,company_id=company_id)
    qlist=qlistall['list']
    qlistcount=qlistall['count']
    listcount=qlistcount
    if (int(listcount)>100000):
        listcount=100000
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    #返回json数据
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'list':qlist,'listcount':listcount}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
#加入通信录
def join_addressbook(request):
    company_id=request.POST.get("company_id")
    usertoken=request.POST.get("usertoken")
    appsystem=request.POST.get("appsystem")
    forcompany_id=request.POST.get("forcompany_id")
    sql="select id from company_addressbook where company_id=%s and forcompany_id=%s"
    result=dbc.fetchonedb(sql,[company_id,forcompany_id])
    gmt_created=datetime.datetime.now()
    if not result:
        sqlc="insert into company_addressbook(company_id,forcompany_id,gmt_created) values(%s,%s,%s)"
        dbc.updatetodb(sqlc,[company_id,forcompany_id,gmt_created])
        messagedata={'err':'false','errkey':'加入成功！'}
    else:
        messagedata={'err':'true','errkey':'已经加入！'}
    return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
