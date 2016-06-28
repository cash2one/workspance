#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
from django.http import HttpResponse,HttpResponseRedirect
import simplejson,sys,os,urllib,re
from conn import crmdb
from zz91page import *
db = crmdb()
reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/admin_function.py")
zzu=useradmin()
#主框架
def main(request):
    username=request.session.get('username',default=None)
    if not username:
        return HttpResponseRedirect("login.html")
    else:
        #获得当前登录用户所用有的菜单权限
        listall=zzu.getmain(username)
        if listall['status']=='in_session_time':
            toplistall=listall['toplistall']
            position=listall['position']
            return render_to_response('main.html',locals())

#--团队管理
#团队列表
def user_category(request):
    page=request.GET.get('page')
    if not page:
        page=1
    user_category_label=request.GET.get('user_category_label')
    code=request.GET.get('code')
    if not code:
        code=''
    searchlist={}
    if user_category_label:
        searchlist['user_category_label']=user_category_label
    '''
    if code:
        searchlist['code']=code
        argument.append(''+str(code)+'__')
    '''
    searchurl=urllib.urlencode(searchlist)
    funpage=zz91page()
    limitNum=funpage.limitNum(10)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(3)
    before_range_num = funpage.before_range_num(6)
    #获得团队
    categoryall=zzu.get_user_category(frompageCount,limitNum,code,user_category_label)
    listcount=categoryall['listcount']
    listall=categoryall['listall']
    if (int(listcount)>1000000):
        listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    if len(page_range)>7:
        page_range=page_range[:7]
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    #panduan
    return render_to_response('staff/user_category.html',locals())

#添加新建团队
def user_category_add(request):
    label=request.POST.get('label')
    ord=request.POST.get('ord')
    closeflag=request.POST.get('closeflag')
    action=request.POST.get('action')
    if action=='add':
        code=request.POST.get('code')
        if not code:
            code=''
        sqlm="select right(code,2) as code from user_category where code like '"+code+"__' order by right(code,2) desc limit 0,1"
        datalist=db.fetchonedb(sqlm)
        if datalist:
            maxcode=datalist['code']
            nowcode=str(code)+str(int(maxcode)+1)
        else:
            maxcode="10"
            nowcode=str(code)+str(int(maxcode)+1)
        sql='insert into user_category (code,label,ord,closeflag) values (%s,%s,%s,%s)'
        db.updatetodb(sql,[nowcode,label,ord,closeflag])
        return HttpResponseRedirect("user_category.html")
    code=request.GET.get('code')
    return render_to_response('staff/user_category_add.html',locals())
#删除团队新
def user_category_del(request):
    id=request.GET.get('id')
    sql="delete from user_category where id=%s"
    db.updatetodb(sql,[id])
    return HttpResponseRedirect("user_category.html")

def usercategoryedit(request):
    request_url=request.META.get('HTTP_REFERER','/')
    user_category_id=request.GET.get('user_category_id')
    sql='select label,ord from user_category where id=%s'
    result=db.fetchonedb(sql,[user_category_id])
    return render_to_response('staff/user_category_edit.html',locals())
def usercategoryeditok(request):
    user_category_id=request.POST.get('user_category_id')
    label=request.POST.get('label')
    ord=request.POST.get('ord')
    request_url=request.POST.get('request_url')
    sql='update user_category set label=%s,ord=%s where id=%s'
    db.updatetodb(sql,[label,ord,user_category_id])
    return HttpResponseRedirect(request_url)
#改变团队状态，开通还是冻结
def changestatus_usercate(request):
    status=request.GET.get('status')
    id=request.GET.get('id')
    if int(status)==0:
        #动作为要关闭
        closeflag=1
        sql='update user_category set closeflag=%s where id=%s'
        db.updatetodb(sql,[closeflag,id])
    if int(status)==1:
        #动作为要打开
        closeflag=0
        sql='update user_category set closeflag=%s where id=%s'
        db.updatetodb(sql,[closeflag,id])
    request_url=request.META.get('HTTP_REFERER','/')
    return HttpResponseRedirect(request_url)

#--员工列表
def userlist(request):
    usercategorylist=zzu.getusercategory()
    authcategorylist=zzu.getauthcategory()
    page=request.GET.get('page')
    if not page:
        page=1
    searchlist={}
    username=request.GET.get('username')
    if username:
        searchlist['username']=username

    searchurl=urllib.urlencode(searchlist)
    funpage=zz91page()
    limitNum=funpage.limitNum(10)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(3)
    before_range_num = funpage.before_range_num(6)
    #获得团队
    userall=zzu.get_user(frompageCount,limitNum,username)
    listcount=userall['listcount']
    listall=userall['listall']
    if (int(listcount)>1000000):
        listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    if len(page_range)>7:
        page_range=page_range[:7]
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    return render_to_response('staff/userlist.html',locals())
def adduser(request):
    label=request.POST.get('label')
    user_category_all=zzu.getusercategory()
    auth_category_all=zzu.getauthcategory()
    usercategory_code4=zzu.getusercategory_code4()
    return render_to_response('staff/adduser.html',locals())
def adduserok(request):
    userid=request.POST.get('userid')
    username=request.POST.get('username')
    realname=request.POST.get('realname')
    password=request.POST.get('password')
    user_category_id=request.POST.getlist('user_category_id')
    user_category_id_txt=''
    for uc_id in user_category_id:
        user_category_id_txt=user_category_id_txt+str(uc_id)+','
    safepasswd=password
    user_category_code=request.POST.get('user_category')
    auth_category_id=request.POST.getlist('auth_category')
    auth_category_id_txt=''
    for ac_id in auth_category_id:
        auth_category_id_txt=auth_category_id_txt+str(ac_id)+','
    closeflag=request.POST.get('closeflag')
    if not userid:
        sql='insert into user (username,password,safepasswd,user_category_code,auth_category_id,user_category_id,realname,closeflag) values (%s,%s,%s,%s,%s,%s,%s,%s)'
        db.updatetodb(sql,[username,password,safepasswd,user_category_code,auth_category_id_txt,user_category_id_txt,realname,closeflag])
    else:
        sql='update user set username=%s,password=%s,safepasswd=%s,user_category_code=%s,auth_category_id=%s,user_category_id=%s,realname=%s,closeflag=%s where id=%s'
        db.updatetodb(sql,[username,password,safepasswd,user_category_code,auth_category_id_txt,user_category_id_txt,realname,closeflag,userid])
    return HttpResponseRedirect("userlist.html")
def edituser(request):
    user_category_all=zzu.getusercategory()
    auth_category_all=zzu.getauthcategory()
    usercategory_code4=zzu.getusercategory_code4()#团队权限
    
    userid=request.GET.get('userid')
    sql='select u.id,u.username,u.password,u.realname,u.user_category_code,u.auth_category_id,u.user_category_id,u.closeflag,c.label as user_category,a.label as auth_category from user as u left join user_category as c on u.user_category_code=c.code  left join auth_category as a on u.auth_category_id=a.id where u.id=%s'
    result=db.fetchonedb(sql,[userid])
    user_category_id=result['user_category_id']
    user_category_id=user_category_id[:-1]
    auth_category_id=result['auth_category_id']
    auth_category_id=auth_category_id[:-1]
    return render_to_response('staff/adduser.html',locals())
#改变用户状态，开通还是冻结
def changestatus_user(request):
    status=request.GET.get('status')
    id=request.GET.get('id')
    if int(status)==0:
        #动作为要关闭
        closeflag=1
        sql='update user set closeflag=%s where id=%s'
        db.updatetodb(sql,[closeflag,id])
    if int(status)==1:
        #动作为要打开
        closeflag=0
        sql='update user set closeflag=%s where id=%s'
        db.updatetodb(sql,[closeflag,id])
    request_url=request.META.get('HTTP_REFERER','/')
    return HttpResponseRedirect(request_url)
#员工一键删除
def del_alluser(request):
    checkid=request.GET.getlist('checkid')
    zzu.del_alluser(checkid)
    request_url=request.META.get('HTTP_REFERER','/')
    return HttpResponseRedirect(request_url)

#--角色权限管理
def auth(request):
    page=request.GET.get('page')
    if not page:
        page=1
    searchlist={}
    authname=request.GET.get('authname')
    if authname:
        searchlist['authname']=authname

    searchurl=urllib.urlencode(searchlist)
    funpage=zz91page()
    limitNum=funpage.limitNum(10)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(3)
    before_range_num = funpage.before_range_num(6)
    #获得团队
    authall=zzu.get_auth(frompageCount,limitNum,authname)
    listcount=authall['listcount']
    listall=authall['listall']
    if (int(listcount)>1000000):
        listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    if len(page_range)>7:
        page_range=page_range[:7]
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    return render_to_response('staff/auth.html',locals())
#添加权限
def addauth(request):
    sql='select label from menu where parent_id=0'
    topmenulist=db.fetchalldb(sql)
    sql1='select id,label from menu where menu_category=1'
    firmenulist=db.fetchalldb(sql1)
    for fir in firmenulist:
        id=fir['id']
        sql2='select label,link from menu where parent_id=%s'
        secmenulist=db.fetchalldb(sql2,[id])
        for sec in secmenulist:
            link=sec['link']
            sec_id=str(link)
            sec_id=re.sub(".html","",sec_id)
            sec['sec_id']=sec_id
        fir['secmenulist']=secmenulist
    return render_to_response('staff/addauth.html',locals())
def addauthok(request):
    auth_label=request.POST.get('auth_label')
    sql='insert into auth_category (label) values(%s)'
    db.updatetodb(sql,[auth_label])
    return HttpResponseRedirect("auth.html")

#权限删除
def auth_category_del(request):
    id=request.GET.get('id')
    sql='delete from auth_category where id=%s'
    db.updatetodb(sql,[id])
    request_url=request.META.get('HTTP_REFERER','/')
    return HttpResponseRedirect(request_url)
def auth_category_set(request):
    menulistall=zzu.getmenulistall()
    aid=request.GET.get('id')
    sql='select label,auth_code from auth_category where id=%s'
    listall=db.fetchonedb(sql,[aid])
    auth_code=listall['auth_code']
    auth_code=auth_code[:-1]
    return render_to_response('staff/auth_category_set.html',locals())
#编辑拥有权限确认
def auth_category_setok(request):
    checkid=request.GET.getlist('checkid')
    aid=request.GET.get('aid')
    authname=request.GET.get('authname')
    auth_code=''
    for id in checkid:
        auth_code=auth_code+str(id)+','
    sql="update auth_category set auth_code=%s,label=%s where id=%s"
    db.updatetodb(sql,[auth_code,authname,aid])
    return HttpResponseRedirect("auth.html")

#--菜单管理
def menu(request):
    page=request.GET.get('page')
    if not page:
        page=1
    next_id=request.GET.get('next_id')
    up_id=request.GET.get('up_id')#上一级状态
    if not next_id and not up_id:
        next_id=0
    
    searchlist={}
    menuname=request.GET.get('menuname')
    if menuname:
        searchlist['menuname']=menuname

    searchurl=urllib.urlencode(searchlist)
    funpage=zz91page()
    limitNum=funpage.limitNum(10)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(3)
    before_range_num = funpage.before_range_num(6)
    #获得菜单
    menuall=zzu.get_menu(frompageCount,limitNum,next_id,up_id,menuname)
    listcount=menuall['listcount']
    listall=menuall['listall']
    if (int(listcount)>1000000):
        listcount=1000000-1
    listcount = funpage.listcount(listcount)
    page_listcount=funpage.page_listcount()
    firstpage = funpage.firstpage()
    lastpage = funpage.lastpage()
    page_range  = funpage.page_range()
    if len(page_range)>7:
        page_range=page_range[:7]
    nextpage = funpage.nextpage()
    prvpage = funpage.prvpage()
    return render_to_response('staff/menu.html',locals())

#添加菜单
def addmenu_all(request):
    menulist=zzu.getmenulist()
    status=0#js编辑状态
    return render_to_response('staff/addmenu_all.html',locals())
def addmenuok(request):
    menuid=request.POST.get('menuid')
    menu_label=request.POST.get('menu_label')
    menu_category=request.POST.get('menu_category')
    closeflag=request.POST.get('closeflag')
    fir_parent_id=request.POST.get('fir_parent_id')
    sec_parent_id=request.POST.get('sec_parent_id')
    ord=request.POST.get('ord')
    link=request.POST.get('link')
    if not menuid:
        if int(menu_category)==0:
            sql="insert into menu (parent_id,menu_category,label,ord,closeflag) values (%s,%s,%s,%s,%s)"
            db.updatetodb(sql,[menu_category,menu_category,menu_label,ord,closeflag])
        if int(menu_category)==1:
            sql="insert into menu (parent_id,menu_category,label,ord,closeflag) values (%s,%s,%s,%s,%s)"
            db.updatetodb(sql,[fir_parent_id,menu_category,menu_label,ord,closeflag])
        if int(menu_category)==2:
            sql="insert into menu (parent_id,menu_category,label,ord,link,closeflag) values (%s,%s,%s,%s,%s,%s)"
            db.updatetodb(sql,[sec_parent_id,menu_category,menu_label,ord,link,closeflag])
    else:
        if int(menu_category)==0:
            sql='update menu set parent_id=%s,menu_category=%s,label=%s,ord=%s,closeflag=%s where id=%s'
            db.updatetodb(sql,[menu_category,menu_category,menu_label,ord,closeflag,menuid])
        if int(menu_category)==1:
            sql='update menu set parent_id=%s,menu_category=%s,label=%s,ord=%s,closeflag=%s where id=%s'
            db.updatetodb(sql,[fir_parent_id,menu_category,menu_label,ord,closeflag,menuid])
        if int(menu_category)==2:
            sql='update menu set parent_id=%s,menu_category=%s,label=%s,ord=%s,link=%s,closeflag=%s where id=%s'
            db.updatetodb(sql,[sec_parent_id,menu_category,menu_label,ord,link,closeflag,menuid])
    return HttpResponseRedirect("menu.html")
def editmenu(request):
    status=1#js编辑状态
    menuid=request.GET.get('menuid')
    menu_category=request.GET.get('menu_category')
    sql='select parent_id,label,ord,link,closeflag from menu where id=%s'
    result=db.fetchonedb(sql,[menuid])
    #获得上一级菜单
    parent_id=result['parent_id']
    if parent_id>0:
        if menu_category>0:
            sql2='select id,label from menu where id=%s'
            resultup=db.fetchonedb(sql2,[parent_id])
            upid=resultup['id']
            uplabel=resultup['label']
    menulist=zzu.getmenulist()
    return render_to_response('staff/addmenu_all.html',locals())
#改变菜单状态，开通还是冻结
def changestatus_menu(request):
    status=request.GET.get('status')
    id=request.GET.get('id')
    if int(status)==0:
        #动作为要关闭
        closeflag=1
        sql='update menu set closeflag=%s where id=%s'
        db.updatetodb(sql,[closeflag,id])
    if int(status)==1:
        #动作为要打开
        closeflag=0
        sql='update menu set closeflag=%s where id=%s'
        db.updatetodb(sql,[closeflag,id])
    request_url=request.META.get('HTTP_REFERER','/')
    return HttpResponseRedirect(request_url)