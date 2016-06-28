#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
import simplejson
from django.http import HttpResponse,HttpResponseRedirect,HttpResponsePermanentRedirect
import MySQLdb,sys,os,memcache,settings,urllib,re,datetime,time
from django.core.cache import cache
from zz91db_ast import companydb
from zz91db_2_news import newsdb
from settings import spconfig,appurl
from zz91tools import subString,filter_tags,formattime
from sphinxapi import *
import Image,ImageDraw,ImageFont,ImageFilter
try:
    import cStringIO as StringIO
except ImportError:
    import StringIO
    
from trade import otherimgupload
     
     
dbc=companydb()
dbn=newsdb()
reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/public_function.py")
execfile(nowpath+"/func/huzhu_function.py")

zzh=zzhuzhu()

#互助列表
def huzhu(request):
    host=getnowurl(request)
    nowlanmu="<a href='/huzhu/'>再生互助</a>"
    showpost=1
    category_id=request.GET.get("category_id")
    datetype=request.GET.get("datetype")
    htype=request.GET.get("htype")
    bbs_post_assist_id=request.GET.get("bbs_post_assist_id")
    keywords=request.GET.get("keywords")
    username=request.session.get("username",None)
    company_id=request.session.get("company_id",None)
    page=request.GET.get("page")
    if page==None or page=='':
        page=1
    if (keywords!=None):
        webtitle=keywords+"-互助列表"
    if (str(keywords)=='None'):    
        keywords=None
        webtitle="互助列表"
        datefirst=1
    
    """
    if datetype==None:
        datetype="1"
    timenow201=int(time.time())
    if datetype=="1":
        timehw=int(time.time())-3600*24
    if datetype=="2":
        timehw=int(time.time())-3600*24*7
    if datetype=="3":
        timehw=int(time.time())-3600*24*30
    #----每日每周每月
    """
    serverida=spconfig['serverid']
    #置顶
    topcode=None
    if category_id=="1":
        topcode="10041010"
    if category_id=="2":
        topcode="10041011"
    if category_id=="3":
        topcode="10041012"
    bbstop=zzh.gettopbbslist(topcode)
    bbslistall=zzh.getbbslist(keywords,(int(page)-1)*20,20,category_id,datetype=datetype,htype=htype,bbs_post_assist_id=bbs_post_assist_id)
    bbslistall['bbstop']=bbstop
    datatype=request.GET.get("datatype")
    if datatype=="json":
        return HttpResponse(simplejson.dumps(bbslistall, ensure_ascii=False))
    
    bbslist=bbslistall['list']
    bbslistcount=bbslistall['count']
    if (bbslistcount==1):
        morebutton='style=display:none'
    else:
        morebutton=''

    return render_to_response('huzhu/huzhu.html',locals())

def huzhu_imgload(request):
    return render_to_response('huzhu/imgload.html',locals())

def huzhupost(request):
    company_id=request.GET.get("company_id")
    usertoken=request.GET.get("usertoken")
    appsystem=request.GET.get("appsystem")
    if not getloginstatus(company_id,usertoken) and appsystem=="Android":
        return HttpResponse("nologin")
    #判断是否已经填写了昵称
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    mynickname=zzh.getcompanynickname(company_id)
    if not mynickname or mynickname=="":
        nickname=request.GET.get("nickname")
        if nickname:
            zzh.addcompanynickname(nickname,company_id,username)
    #判断是否填写关注行业
    myguanzhu=zzh.gethuzhuguanzhu(company_id)
    if not myguanzhu or myguanzhu=="":
        myguanzhu=request.REQUEST.getlist("myguanzhu")
        if myguanzhu:
            zzh.addmyzhuzhuguanzhu(myguanzhu,company_id,username)

    category_id=18
    return render_to_response('huzhu/huzhupost2.html',locals())
    
def huzhumore(request):
    username=request.session.get("username",None)
    category_id=request.GET.get("category_id")
    datetype=request.GET.get("datetype")
    keywords=request.GET.get("keywords")
    htype=request.GET.get("htype")
    bbs_post_assist_id=request.GET.get("bbs_post_assist_id")
    if (str(keywords)=='None'):    
        keywords=None
    page=request.GET.get("page")
    if page==None or page=='':
        page=1
    if (str(category_id)=='None'):
        category_id=None
    if (str(htype)=='None'):
        htype=None
    if (str(bbs_post_assist_id)=='None'):
        bbs_post_assist_id=None 
    """
    timenow201=int(time.time())
    if datetype=="1":
        timehw=int(time.time())-3600*24
    if datetype=="2":
        timehw=int(time.time())-3600*24*7
    if datetype=="3":
        timehw=int(time.time())-3600*24*30
    """
    bbslistall=zzh.getbbslist(keywords,(int(page)-1)*20+1,20,category_id,htype=htype,bbs_post_assist_id=bbs_post_assist_id)
    bbslist=bbslistall['list']
    return render_to_response('huzhu/huzhumore.html',locals())

#查看帖子
def huzhuview(request,id):
    host=getnowurl(request)
    showpost=None
    nowlanmu="<a href='/huzhu/'>再生互助</a>"
    done = request.path
    suc=request.GET.get("suc")
    err=request.GET.get("err")
    username=request.session.get("username",None)
    mycompany_id=request.session.get("company_id",None)
    gmt_created=datetime.datetime.now()
    
    if mycompany_id!=None:
        
        sql="update bbs_invite set is_viewed='1',answercheck=1,gmt_created=%s where company_id=%s and post_id=%s"
        dbc.updatetodb(sql,[gmt_created,mycompany_id,id])
        sqlp="select id from bbs_post_viewed where company_id=%s and bbs_post_id=%s"
        alist = dbc.fetchonedb(sqlp,[mycompany_id,id])
        if alist:
            sql="update bbs_post_viewed set is_viewed=1,gmt_created=%s where company_id=%s and bbs_post_id=%s"
            dbc.updatetodb(sql,[gmt_created,mycompany_id,id])
        else:
            sql="insert into bbs_post_viewed(gmt_created,company_id,bbs_post_id,is_viewed) values(%s,%s,%s,%s)"
            dbc.updatetodb(sql,[gmt_created,mycompany_id,id,1])
        mynickname=zzh.getusername(mycompany_id)
    replycount=0
    sql="select title,content,account,gmt_created,bbs_post_category_id,company_id from bbs_post where id=%s"
    alist = dbc.fetchonedb(sql,str(id))
    if alist:
        title=alist[0]
        content=alist[1]
        if (title!=None):
            webtitle=title+"-互助列表"
        company_id=alist[5]
        nickname=zzh.getusername(company_id)
        sqlp="select file_path from bbs_post_upload_file where bbs_post_id=%s"
        presult=dbc.fetchalldb(sqlp,[id])
        picurllist=[]
        if presult:
            for ll in presult:
                plist={'file_path':ll[0]}
                picurllist.append(plist)
        gmt_created=alist[3].strftime( '%-Y-%-m-%-d %-H:%-M:%-S')
        bbs_post_category_id=alist[4]
        category_id=bbs_post_category_id
        content=content.replace("http://huzhu.zz91.com/viewReply","http://m.zz91.com/huzhuview/viewReply")
        content=replacepic(content)
        content=zzh.replacetel(content)
        content=zzh.replaceurl(content)
        if content=="":
            content==None
        replycount=zzh.gethuzhureplaycout(id)
    else:
        return HttpResponse("err")

    listall_reply=zzh.replylist(id,0,10)
    if (int(replycount)>10):
        moreflag=1
    else:
        moreflag=None
    datatype=request.GET.get("datatype")
    if datatype=="json":
        jsonlist={'title':title,'content':content,'company_id':company_id,'nickname':nickname,'gmt_created':gmt_created,'replycount':replycount,'picurllist':picurllist,'listall_reply':listall_reply,'moreflag':moreflag,'category_id':category_id}
        return HttpResponse(simplejson.dumps(jsonlist, ensure_ascii=False))
    
    return render_to_response('huzhu/huzhuview.html',locals())
#---查看更多回复
def replymore(request):
    page=request.GET.get("page")
    type=request.GET.get("type")
    if page==None:
        page=1
    postid=request.GET.get("postid")
    replyid=request.GET.get("replyid")
    if type=="0":
        listall_reply=zzh.replylist(postid,(int(page)-1)*10,10)
        datatype=request.GET.get("datatype")
        if datatype=="json":
            return HttpResponse(simplejson.dumps(listall_reply, ensure_ascii=False))
        return render_to_response('huzhu/replymore.html',locals())
    if type=="1":
        listall_reply=zzh.replyreplylist(replyid,(int(page)-1)*10,10)
        datatype=request.GET.get("datatype")
        if datatype=="json":
            return HttpResponse(simplejson.dumps(listall_reply, ensure_ascii=False))
        return render_to_response('huzhu/replyreplymore.html',locals())
    
    return render_to_response('huzhu/replymore.html',locals())
#----保存发布帖子
def huzhupostsave(request):
    bbs_post_id = request.POST.get('category_id')
    title = request.POST.get('title')
    company_id = request.POST.get('company_id')
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    #picidlist = request.POST['picidlist']
    if (bbs_post_id==None or bbs_post_id==""):
        bbs_post_id=1
    content = request.POST.get('content')
    piclist=otherimgupload(request)
    
    if content:
        rpl1=re.findall('[0-9\ ]+',content)
        for r1 in rpl1:
            if len(r1)>10:
                content=content.replace(r1,r1[:-3]+'***')
#        content1=filter(str.isdigit,content.encode('utf8'))
#        if content1 and len(content1)>=10:
#            content2=content1[-3:]
#            content=content.replace(content2,'***')
#    return HttpResponse(content)
    #title=content[0:100]
    #return HttpResponse(simplejson.dumps({'c':content}, ensure_ascii=False))
    gmt_created=datetime.datetime.now()
    gmt_modified=datetime.datetime.now()
    company_id=request.POST.get("company_id")
    username=getaccount(company_id)
    if not title:
        messagedata={'err':'true','errkey':'请填写标题！','type':'posthuzhu'}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
    bbs_post_assist_id=24
    if (content and content!=""):
        #内容里插入图片
        if piclist:
            for p in piclist:
                if p:
                    picurl=p['path']
                    content+='<br /><img src="'+picurl+'"><br />'
        bbs_user_profiler_id=zzh.getprofilerid(username)
        if (bbs_user_profiler_id==None):
            bbs_user_profiler_id=1
        value=[company_id,bbs_user_profiler_id,username,bbs_post_id,title,content,0,1,gmt_created,gmt_modified,gmt_modified,gmt_modified,1,bbs_post_assist_id]
        sql="insert into bbs_post(company_id,bbs_user_profiler_id,account,bbs_post_category_id,title,content,is_del,check_status,gmt_created,gmt_modified,post_time,reply_time,postsource,bbs_post_assist_id) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        result=dbc.updatetodb(sql,value)
        if result:
            postid=result[0]
            #保存图片
            if piclist:
                for p in piclist:
                    sql="update other_piclist set source_id=%s where id=%s"
                    dbc.updatetodb(sql,[postid,p['id']])
        """
        if picidlist!="":
            sql_bbs_post='SELECT max(id) from bbs_post where title=%s'
            result=dbc.fetchonedb(sql_bbs_post,[title])
            if result:
                bbs_post_id=result[0]
                sql_pic='update bbs_post_upload_file set bbs_post_id=%s where id in (%s)'
                dbc.updatetodb(sql_pic,[bbs_post_id,picidlist])
        #return HttpResponseRedirect("/huzhu/?datetype=1")
        """
        messagedata={'err':'false','errkey':'','type':'posthuzhu'}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
    else:
        messagedata={'err':'true','errkey':'请填写提问内容！','type':'posthuzhu'}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
#----回复帖子
def huzhu_replay(request):
    showpost=1
    nowlanmu="<a href='/huzhu/'>再生互助</a>"
    company_id=request.POST.get("company_id")
    if not company_id or str(company_id)=="0":
        return HttpResponse("err")
    bbs_post_id = request.POST.get('bbs_post_id')
    tocompany_id = request.POST.get('tocompany_id')
    bbs_post_reply_id=request.POST.get('bbs_post_reply_id')
    title=request.POST.get('title')
    content = request.POST.get('content')
    gmt_created=datetime.datetime.now()
    gmt_modified=datetime.datetime.now()
    account=getaccount(company_id)
    piclist=otherimgupload(request)

    if (content and content!="" and company_id):
        #内容里插入图片
        if piclist:
            for p in piclist:
                if p:
                    picurl=p['path']
                    content+='<br /><img src="'+picurl+'"><br />'
        value=[company_id,account,title,bbs_post_id,content,0,1,gmt_created,gmt_modified,1,bbs_post_reply_id,tocompany_id]
        sql="insert into bbs_post_reply(company_id,account,title,bbs_post_id,content,is_del,check_status,gmt_created,gmt_modified,postsource,bbs_post_reply_id,tocompany_id) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        dbc.updatetodb(sql,value)
        sql="update bbs_post set reply_time=%s,reply_count=reply_count+1,gmt_modified=%s where id=%s"
        result=dbc.updatetodb(sql,[gmt_modified,gmt_modified,bbs_post_id])
        if result:
            postid=result[0]
            #保存图片
            if piclist:
                for p in piclist:
                    sql="update other_piclist set source_id=%s where id=%s"
                    dbc.updatetodb(sql,[postid,p['id']])
        """
        if picidlist!="" and picidlist:
            sql_bbs_post='SELECT max(id) from bbs_post_reply where bbs_post_id=%s'
            result=dbc.fetchonedb(sql_bbs_post,[bbs_post_id])
            if result:
                bbs_post_reply_id=result[0]
                sql_pic='update bbs_post_upload_file set bbs_post_reply_id=%s where id in (%s)'
                dbc.updatetodb(sql_pic,[bbs_post_reply_id,picidlist])
        """
        #updatepostviewed(tocompany_id,bbs_post_id)
        #----更新弹窗
        #updateopenfloat(tocompany_id,0)
        messagedata={'err':'false','errkey':'','type':'huzhu'}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))
    else:
        messagedata={'err':'true','errkey':'系统错误，请重试','type':'huzhu'}
        return HttpResponse(simplejson.dumps(messagedata, ensure_ascii=False))

