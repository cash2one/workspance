#-*- coding:utf-8 -*-
from django.shortcuts import render_to_response
from django.http import HttpResponse,HttpResponseRedirect
import simplejson,sys,os,urllib,re,datetime,time
from conn import crmdb
from zz91page import *
from sphinxapi import *
db = crmdb()
reload(sys)
sys.setdefaultencoding('UTF-8')
nowpath=os.path.dirname(__file__)
execfile(nowpath+"/func/company_function.py")
execfile(nowpath+"/func/crmtools.py")
zzc=customer()
#行业字典
INDUSTRY_LABEL={
                '10001000':'废塑料',
                '10001001':'废金属',
                '10001002':'废纸',
                '10001003':'废旧轮胎与废橡胶',
                '10001004':'废纺织品与废皮革',
                '10001005':'废电子电器',
                '10001006':'废玻璃',
                '10001007':'废旧二手设备',
                '10001008':'其他废料',
                '10001009':'服务',
                '10001010':'塑料原料',
                }
#我的所有客户
def icdlist(request):
    username=request.session.get('username',default=None)
    user_id=request.session.get('user_id',default=None)
    if not username or not user_id:
        return HttpResponseRedirect("relogin.html")
    #判断当前登录用户的权限（权限为组长级以上查看全部和分配客户）
    has_auth=zzc.is_hasauth(user_id=user_id)
    request_url=request.META.get('HTTP_REFERER','/')
    page=request.GET.get('page')
    if not page:
        page=1
    searchlist={}
    companyname=request.GET.get('companyname')
    contact=request.GET.get('contact')
    mobile=request.GET.get('mobile')
    industry=request.GET.get('industry')
    if industry:
        industry_txt=INDUSTRY_LABEL[industry]
    last_login_time_begin=request.GET.get('last_login_time_begin')
    last_login_time_end=request.GET.get('last_login_time_end')
    if companyname:
        searchlist['companyname']=companyname
    if contact:
        searchlist['contact']=contact
    if mobile:
        searchlist['mobile']=mobile
    if industry:
        searchlist['industry']=industry
    if last_login_time_begin:
        searchlist['last_login_time_begin']=last_login_time_begin
    if last_login_time_end:
        searchlist['last_login_time_end']=last_login_time_end
    searchurl=urllib.urlencode(searchlist)
    funpage=zz91page()
    limitNum=funpage.limitNum(10)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(3)
    before_range_num = funpage.before_range_num(6)
    #获得客户
    if has_auth==0:
        allcustomer=zzc.get_allcustomer(user_id=user_id,frompageCount=frompageCount,limitNum=limitNum,companyname=companyname,contact=contact,mobile=mobile,industry=industry,last_login_time_begin=last_login_time_begin,last_login_time_end=last_login_time_end)
    else:
        allcustomer=zzc.get_allcustomer(frompageCount=frompageCount,limitNum=limitNum,companyname=companyname,contact=contact,mobile=mobile,industry=industry,last_login_time_begin=last_login_time_begin,last_login_time_end=last_login_time_end)
    #获得销售人员列表(selection)
    allsalesman=zzc.get_allsalesman()
    listcount=allcustomer['listcount']
    listall=allcustomer['listall']
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
    return render_to_response("icdhtml/icdlist.html",locals())
#添加其他联系人页面
def otherperson(request):
    #编辑时取值
    editid=request.GET.get('edit')
    company_id=request.GET.get('company_id')
    if editid:
        sql="select company_id,name,sex,tel,mobile,station,fax,email,address,bz,user_id,gmt_modified,gmt_created from kh_othercontact where id=%s"
        result=db.fetchonedb(sql,[editid])
    return render_to_response("icdhtml/otherperson.html",locals())
#添加其他联系人
def add_otherperson(request):
    user_id=request.session.get('user_id',default=None)
    company_id=request.POST.get('company_id')
    name=request.POST.get('other_name')
    sex=request.POST.get('other_sex')
    tel=request.POST.get('other_tel')
    mobile=request.POST.get('other_mobile')
    station=request.POST.get('other_station')
    fax=request.POST.get('other_fax')
    email=request.POST.get('other_email')
    address=request.POST.get('other_address')
    bz=request.POST.get('other_bz')
    gmt_modified=datetime.datetime.now()
    gmt_created=gmt_modified
    editflag=request.POST.get('editflag')
    if editflag:
        sql='update kh_othercontact set company_id=%s,name=%s,sex=%s,tel=%s,mobile=%s,station=%s,fax=%s,email=%s,address=%s,bz=%s,user_id=%s,gmt_modified=%s where id=%s'
        db.updatetodb(sql,[company_id,name,sex,tel,mobile,station,fax,email,address,bz,user_id,gmt_modified,editflag])
    else:
        sql='insert into kh_othercontact (company_id,name,sex,tel,mobile,station,fax,email,address,bz,user_id,gmt_modified,gmt_created) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
        db.updatetodb(sql,[company_id,name,sex,tel,mobile,station,fax,email,address,bz,user_id,gmt_modified,gmt_created])
    return HttpResponseRedirect("crm_cominfoedit.html?company_id="+str(company_id))
    res={'res':'suc'}
    res=simplejson.dumps(res,ensure_ascii=False)
    return HttpResponse(res)
#删除其他联系人
def del_otherperson(request):
    otherperson_id=request.GET.get('otherperson_id')
    user_id=request.session.get('user_id',default=None)
    company_id=request.GET.get('company_id')
    sql='delete from kh_othercontact where id=%s and user_id=%s'
    db.updatetodb(sql,[otherperson_id,user_id])
    #request_url=request.META.get('HTTP_REFERER','/')
    return HttpResponseRedirect("crm_cominfoedit.html?company_id="+str(company_id))

#销售记录列表(iframe)
def tellist(request):
    #翻页
    page=request.GET.get('page')
    if not page:
        page=1
    funpage=zz91page()
    limitNum=funpage.limitNum(3)
    nowpage=funpage.nowpage(int(page))
    frompageCount=funpage.frompageCount()
    after_range_num = funpage.after_range_num(3)
    before_range_num = funpage.before_range_num(6)
    #参数获得
    request_url=request.META.get('HTTP_REFERER','/')
    company_id=request.GET.get("company_id")
    telflag=request.GET.get("telflag")
    telsalelistall=zzc.gettelsalelist(frompageCount,limitNum,company_id=company_id)
    telsalelist=telsalelistall['list']
    listcount=telsalelistall['count']
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
    
    user_id=request.session.get('user_id',default=None)#销售id
    is_admin=zzc.get_is_admin(user_id)#是否为管理员权限
    #删除销售记录(管理员权限拥有)
    del_flag=request.GET.get("del")
    com_id=request.GET.get("com_id")
    id=request.GET.get("id")
    if del_flag:
        sql='delete from kh_tel where id=%s and company_id=%s'
        db.updatetodb(sql,[id,com_id])
        return HttpResponseRedirect(request_url)
    return render_to_response("icdhtml/tellist.html",locals())
#添加销售记录成功
def addtellist(request):
    company_id=request.POST.get('company_id')
    contacttype=request.POST.get('contacttype')
    teltime=datetime.datetime.now()#当前电话时间
    user_id=request.POST.get('user_id')
    rank=request.POST.get('com_rank')
    telflag=request.POST.get('telflag')
    contactnexttime=request.POST.get('contactnexttime')
    nocontacttype=request.POST.get('c_Nocontact')
    detail=request.POST.get('detail')
    gmt_created=teltime
    sql='insert into kh_tel (company_id,contacttype,teltime,user_id,rank,telflag,contactnexttime,nocontacttype,detail,gmt_created) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
    db.updatetodb(sql,[company_id,contacttype,teltime,user_id,rank,telflag,contactnexttime,nocontacttype,detail,gmt_created])
    sql2='select id from kh_sales where company_id=%s'
    result=db.fetchonedb(sql2,[company_id])
    if result:
        #有，则更新哦
        sql3='update kh_sales set company_id=%s,rank=%s,contactnexttime=%s,lastteltime=%s,contacttype=%s,gmt_modified=%s where id=%s'
        db.updatetodb(sql3,[company_id,rank,contactnexttime,gmt_created,contacttype,gmt_created,result['id']])
    else:
        #没有,则插入
        sql3='insert into kh_sales (company_id,rank,contactnexttime,lastteltime,contacttype,gmt_created,gmt_modified) values (%s,%s,%s,%s,%s,%s,%s)'
        db.updatetodb(sql3,[company_id,rank,contactnexttime,gmt_created,contacttype,gmt_created,gmt_created])
    res={'res':'suc'}
    res=simplejson.dumps(res, ensure_ascii=False)
    return HttpResponse(res)

#新的分配
def addnew_assign(request):
    companylist=request.GET.getlist('companyid')
    userid=request.GET.get('userid')
    gmt_created=datetime.datetime.now()
    for company in companylist:
        #如果该公司下没有任何销售，则插入
        sql="insert into kh_assign (company_id,user_id,gmt_created) SELECT %s,%s,%s from dual WHERE not exists (select * from kh_assign where kh_assign.company_id=%s)"
        db.updatetodb(sql,[company,userid,gmt_created,company])
    return HttpResponse("1")
#该公司详情
def crm_cominfoedit(request):
    #db.closedb()
    request_url=request.META.get('HTTP_REFERER','/')
    user_id=request.session.get('user_id',default=None)#销售id
    company_id=request.GET.get('company_id')
    companyinfo=zzc.getcompanyinfo(company_id=company_id)
    provincelist=zzc.getprovincelist()
    #获得额外联系人
    otherpersonlist=zzc.getotherperson(company_id=company_id)
    
    #return HttpResponse(otherpersonlist)
    return render_to_response("icdhtml/crm_cominfoedit.html",locals())
#保存公司详情
def save_crm_cominfoedit(request):
    company_id=request.POST.get("company_id")
    #公司信息
    company_name=request.POST.get("company_name")
    address=request.POST.get("address")
    address_zip=request.POST.get("address_zip")
    website=request.POST.get("website")
    industry_code=request.POST.get("industry_code")
    service_code=request.POST.get("service_code")
    introduction=request.POST.get("introduction")
    business=request.POST.get("business")
    business_type=request.POST.get("business_type")#主营方向
    sale_details=request.POST.get("sale_details")
    buy_details=request.POST.get("buy_details")
    area_code=request.POST.get("area_code")
    #账户信息
    tel_country_code=request.POST.get("tel_country_code")
    tel_area_code=request.POST.get("tel_area_code")
    tel=request.POST.get("tel")
    mobile=request.POST.get("mobile")
    contact=request.POST.get("contact")
    position=request.POST.get("position")
    sex=request.POST.get("sex")
    email=request.POST.get("email")
    fax_country_code=request.POST.get("fax_country_code")
    fax_area_code=request.POST.get("fax_area_code")
    fax=request.POST.get("fax")
    
    sql1="update company set name=%s,area_code=%s,address=%s,address_zip=%s,website=%s,industry_code=%s,service_code=%s,introduction=%s,business=%s,business_type=%s,sale_details=%s,buy_details=%s where id=%s"
    db.updatetodb(sql1,[company_name,area_code,address,address_zip,website,industry_code,service_code,introduction,business,business_type,sale_details,buy_details,company_id])
    sql2="update company_account set tel_country_code=%s,tel_area_code=%s,tel=%s,mobile=%s,contact=%s,position=%s,sex=%s,email=%s,fax_country_code=%s,fax_area_code=%s,fax=%s where company_id=%s"
    db.updatetodb(sql2, [tel_country_code,tel_area_code,tel,mobile,contact,position,sex,email,fax_country_code,fax_area_code,fax,company_id])
    res={'res':'suc'}
    res=simplejson.dumps(res, ensure_ascii=False)
    return HttpResponse(res)

#获得地点
def getsite(request):
    sitecode=request.GET.get('sitecode')
    sitelist=zzc.getsitelist(sitecode=sitecode)
    sitelist=simplejson.dumps(sitelist, ensure_ascii=False)
    return HttpResponse(sitelist)
#重新登录
def relogin(request):
    return render_to_response("icdhtml/relogin.html",locals())
#返回
def returnpage(request):
    request_url=request.GET.get('request_url')
    return HttpResponseRedirect(request_url)