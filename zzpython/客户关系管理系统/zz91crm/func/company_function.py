#-*- coding:utf-8 -*-
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
SERVICE_LABEL={
               '10201001':'国外供货商',
               '10201002':'国外回收贸易商',
               '10201003':'码头，仓库储存商',
               '10201004':'国内供应商',
               '10201005':'国内回收贸易商',
               '10201006':'国内加工，拆解商',
               '10201007':'利废企业',
               '10201008':'设备，技术，服务提供商',
               '10201009':'冶炼厂',
               '10201010':'电子厂',
               '10201011':'造粒厂',
               }
SEX_LABEL={
           '0':'先生',
           '1':'女士',
           }
class customer:
    def __init__(self):
        self.db=db
    #判断当前登录用户的权限（权限为组长级以上查看全部和分配客户）
    def is_hasauth(self,user_id=''):
        sql='select auth_category_id from user where id=%s'
        result=db.fetchonedb(sql,[user_id])
        auth_category_id=result['auth_category_id']
        auth_category_id=auth_category_id[:-1]
        if len(auth_category_id)==1 and int(auth_category_id)==4:
            return 0
        else:
            return 1
    #获得所有客户
    def get_allcustomer(self,user_id="",frompageCount="",limitNum="",companyname="",contact="",mobile="",industry="",last_login_time_begin="",last_login_time_end=""):
        indexname="company"
        servername="127.0.0.1"
        serverport=9315
        cl=SphinxClient()
        cl.SetServer(servername,serverport)
        cl.SetMatchMode(SPH_MATCH_BOOLEAN)
        cl.SetSortMode(SPH_SORT_EXTENDED,'id desc')
        cl.SetLimits(frompageCount,limitNum)
        if (user_id):
            cl.SetFilter('user_id',[int(user_id)])
        if (mobile):
            cl.SetFilter('mobile',[int(mobile)])
        if (industry):
            cl.SetFilter('industry_code',[int(industry)])
        if last_login_time_begin and last_login_time_end:
            last_login_time_begin=time.mktime(time.strptime(last_login_time_begin, "%Y-%m-%d"))
            last_login_time_end=time.mktime(time.strptime(last_login_time_end, "%Y-%m-%d"))
            cl.SetFilterRange('last_login_time',int(last_login_time_begin),int(last_login_time_end))
        if (companyname or contact):
            res=cl.Query('@(name,) '+companyname,indexname)
        else:
            res=cl.Query('',indexname)
        listall=[]
        listcount=0
        if res:
            if res.has_key('matches'):
                itemlist=res['matches']
                for match in itemlist:
                    id=match['id']
                    attrs=match['attrs']
                    regtime=timestamp_to_date(int(attrs['regtime']))
                    last_login_time=timestamp_to_date(int(attrs['last_login_time']))
                    gmt_visit_time=timestamp_to_date(int(attrs['gmt_visit_time']))
                    companyinfo=self.getcompanyinfo_byid(id)
                    accountinfo=self.getaccountinfo_byid(id)
                    try:
                        name=companyinfo['name']
                    except TypeError:
                        name=""
                    try:
                        star=companyinfo['star']
                    except TypeError:
                        star=""
                    try:
                        email=accountinfo['email']
                    except TypeError:
                        email=""
                    try:
                        num_login=accountinfo['num_login']
                    except TypeError:
                        num_login=0
                    try:
                        contact=accountinfo['contact']
                    except TypeError:
                        contact=''
                    try:
                        tel_country_code=accountinfo['tel_country_code']
                    except TypeError:
                        tel_country_code=''
                    try:
                        tel_area_code=accountinfo['tel_area_code']
                    except TypeError:
                        tel_area_code=''
                    try:
                        tel=accountinfo['tel']
                    except TypeError:
                        tel=''
                    try:
                        mobile=accountinfo['mobile']
                    except TypeError:
                        mobile=''
                    #省份
                    area_code=attrs['area_code']
                    area_code_length=attrs['area_code_length']
                    areatxt=self.getarea(area_code=area_code, area_code_length=area_code_length)['label']
                    area_code=area_code[:-4]
                    #获得对应的销售人员
                    try:
                        salesman=self.getsalesman_bycompid(company_id=id)['salesman']
                    except:
                        salesman=''
                    list={'id':id,'name':name,'star':star,'last_login_time':last_login_time,'regtime':regtime,'email':email,'num_login':num_login,'contact':contact,'tel_country_code':tel_country_code,'tel_area_code':tel_area_code,'tel':tel,'mobile':mobile,'area_code':area_code,'areatxt':areatxt,'salesman':salesman,'gmt_visit_time':gmt_visit_time}
                    listall.append(list)
            listcount=res['total_found']
        return {"listall":listall,"listcount":listcount}
    #根据id获得公司信息
    def getcompanyinfo_byid(self,id):
        sql='select name,area_code,star,address,address_zip,website,business,introduction,business_type,sale_details,sale_details from company where id=%s'
        result=db.fetchonedb(sql,[id])
        return result
    #根据id获得账户信息
    def getaccountinfo_byid(self,id):
        sql='select email,num_login,contact,position,tel_country_code,tel_area_code,tel,mobile,fax_country_code,fax_area_code,fax,sex from company_account where company_id=%s'
        result=db.fetchonedb(sql,[id])
        return result
    #获得省份
    def getarea(self,area_code="",area_code_length=""):
        sql0='select label from category where code=%s'
        result0=db.fetchonedb(sql0,[area_code])
        return result0
    #获得所有销售人员
    def get_allsalesman(self):
        sql="select code,label from user_category where code like '11__' and closeflag=0 order by ord desc"
        result=db.fetchalldb(sql)
        listall=[]
        for res in result:
            code=res['code']
            cate_label=res['label']
            thiscateman=self.getinthiscateman(code)
            list={"cate_label":cate_label,"thiscateman":thiscateman}
            listall.append(list)
        return listall
    #获得团队下所有人员
    def getinthiscateman(self,code):
        sql="select id,username from user where user_category_code=%s and closeflag=0"
        result=db.fetchalldb(sql,[code])
        return result
    #获得companyid对应的销售人员
    def getsalesman_bycompid(self,company_id):
        sql='select u.username as salesman from user as u left join kh_assign as k on k.user_id=u.id where k.company_id=%s'
        result=db.fetchonedb(sql,[company_id])
        return result
    #获得所有行业
    def get_industry(self):
        sql='select code,label from category where parent_code=1000'
        result=db.fetchalldb(sql)
        return result
    
    #获得客户的省份城市
    def get_area_txt(self,code):
        sql='select label from category where code=%s'
        result=db.fetchonedb(sql,[code])
        return result['label']
    
    #获得公司详情
    def getcompanyinfo(self,company_id=""):
        indexname="company"
        servername="127.0.0.1"
        serverport=9315
        cl=SphinxClient()
        cl.SetServer(servername,serverport)
        cl.SetMatchMode(SPH_MATCH_BOOLEAN)
        cl.SetFilter('company_id',[int(company_id)])
        res=cl.Query('',indexname)
        if res:
            if res.has_key('matches'):
                itemlist=res['matches']
                match=itemlist[0]
                attrs=match['attrs']
                industry_code=attrs['industry_code']
                service_code=attrs['service_code']
                companyinfo=self.getcompanyinfo_byid(company_id)
                accountinfo=self.getaccountinfo_byid(company_id)
                company_name=companyinfo['name']
                area_code=companyinfo['area_code']
                province_code=area_code[0:12]
                city_code=area_code[0:16]
                province_txt=self.get_area_txt(province_code)
                city_txt=self.get_area_txt(city_code)
                try:
                    address=companyinfo['address']
                except TypeError:
                    address=''
                try:
                    address_zip=companyinfo['address_zip']
                except:
                    address_zip=""
                try:
                    tel_country_code=accountinfo['tel_country_code']
                except:
                    tel_country_code=""
                try:
                    tel_area_code=accountinfo['tel_area_code']
                except TypeError:
                    tel_area_code=''
                try:
                    tel=accountinfo['tel']
                except TypeError:
                    tel=''
                try:
                    mobile=accountinfo['mobile']
                except TypeError:
                    mobile=''
                try:
                    fax_country_code=accountinfo['fax_country_code']
                except TypeError:
                    fax_country_code=''
                try:
                    fax_area_code=accountinfo['fax_area_code']
                except TypeError:
                    fax_area_code=''
                try:
                    fax=accountinfo['fax']
                except TypeError:
                    fax=''
                try:
                    email=accountinfo['email']
                except TypeError:
                    email=''
                try:
                    sex=accountinfo['sex']
                    sex_txt=SEX_LABEL[str(sex)]
                except KeyError:
                    sex=''
                    sex_txt='未填'
                try:
                    website=companyinfo['website']
                except TypeError:
                    website=""
                try:
                    contact=accountinfo['contact']
                except TypeError:
                    contact=""
                try:
                    position=accountinfo['position']
                except TypeError:
                    position=""
                try:
                    business=companyinfo['business']
                except TypeError:
                    business=''
                try:
                    introduction=companyinfo['introduction']
                except TypeError:
                    introduction=''
                try:
                    business_type=companyinfo['business_type']
                except TypeError:
                    business_type=''
                try:
                    sale_details=companyinfo['business_type']
                except:
                    sale_details=''
                try:
                    buy_details=companyinfo['buy_details']
                except:
                    buy_details=''
                try:
                    industry_code_txt=INDUSTRY_LABEL[str(industry_code)]
                except KeyError:
                    industry_code_txt=''
                try:
                    service_code_txt=SERVICE_LABEL[str(service_code)]
                except KeyError:
                    service_code_txt=''
#                 area_code
#                 area_code=attrs['area_code']
                #最近登录
                last_login_time=timestamp_to_date(int(attrs['last_login_time']))
                #注册时间
                regtime=timestamp_to_date(int(attrs['regtime']))
                #登录次数
                try:
                    num_login=accountinfo['num_login']
                except TypeError:
                    num_login=0
                list={'company_name':company_name,'industry_code':industry_code,'industry_code_txt':industry_code_txt,'service_code':service_code,'service_code_txt':service_code_txt,'address':address,'address_zip':address_zip,'tel_country_code':tel_country_code,'tel_area_code':tel_area_code,'tel':tel,'mobile':mobile,'fax_country_code':fax_country_code,'fax_area_code':fax_area_code,'fax':fax,'email':email,'website':website,'contact':contact,'position':position,'business':business,'introduction':introduction,'business_type':business_type,'sale_details':sale_details,'buy_details':buy_details,'last_login_time':last_login_time,'num_login':num_login,'regtime':regtime,'sex':sex,'sex_txt':sex_txt,'area_code':area_code,'province_code':province_code,'city_code':city_code,'province_txt':province_txt,'city_txt':city_txt}
                return list

    #获得省份
    def getprovincelist(self):
        sql='select code,label from category where parent_code=10011000'
        result=db.fetchalldb(sql)
        return result
    #获得省份，城市，区县
    def getsitelist(self,sitecode=''):
        sql='select code,label from category where parent_code=%s'
        result=db.fetchalldb(sql,[sitecode])
        return result
    #获得当前公司的所有销售记录
    def gettelsalelist(self,frompageCount,limitNum,company_id=""):
        sql1="select count(0) from kh_tel where company_id=%s"
        sql="select a.id,a.teltime,a.contacttype,a.nocontacttype,a.contactnexttime,b.username,a.rank,a.detail,a.company_id from kh_tel as a left outer join user as b on a.user_id=b.id where a.company_id=%s"
        sql=sql+' order by id desc limit '+str(frompageCount)+','+str(limitNum)
        count=db.fetchnumberdb(sql1,[company_id])
        resultlist=db.fetchalldb(sql,[company_id])
        for dic in resultlist:
            contacttypetxt=''
            nocontacttypetxt=''
            contacttype=dic['contacttype']
            nocontacttype=dic['nocontacttype']
            if contacttype==13:
                contacttypetxt='有效联系'
            if contacttype==12:
                contacttypetxt='无效联系'
            if nocontacttype==1:
                nocontacttypetxt='无人接听'
            if nocontacttype==2:
                nocontacttypetxt='号码错误'
            if nocontacttype==3:
                nocontacttypetxt='停机'
            if nocontacttype==4:
                nocontacttypetxt='关机'
            if nocontacttype==5:
                nocontacttypetxt='无进展'
            dic['contacttypetxt']=contacttypetxt
            dic['nocontacttypetxt']=nocontacttypetxt
            #时间格式转换
            dic['teltime']=formattime(dic['teltime'],0)
            dic['contactnexttime']=formattime(dic['contactnexttime'],0)
        return {'list':resultlist,'count':count}
        
    #获得额外联系人
    def getotherperson(self,company_id=""):
        sql="select id,company_id,name,sex,station,tel,fax,mobile,email,address,gmt_created,bz,gmt_modified from kh_othercontact where company_id=%s"
        resultlist=db.fetchalldb(sql,[company_id])
        for dic in resultlist:
            sextxt=""
            if dic['sex']=='0':
                sextxt="男"
            if dic['sex']=='1':
                sextxt="女"
            dic['sextxt']=sextxt
            #时间格式转换
            dic['gmt_created']=formattime(dic['gmt_created'],0)
            dic['gmt_modified']=formattime(dic['gmt_modified'],0)
        return resultlist
    
    #判断当前用户是否为管理员权限
    def get_is_admin(self,user_id):
        sql="select auth_category_id from user where id=%s"
        result=db.fetchonedb(sql,[user_id])
        if result:
            auth_category_id=result['auth_category_id']
            if "1" in auth_category_id:
                return 1
            else:
                return 0
#     #获得客户的省份城市
#     def kh_province_and_city(self,company_id):
#         sql1='select area_code from company where id=%s'
#         result1=db.fetchonedb(sql1,[company_id])
#         province_code=result1['area_code'][0:11]
#         city_code=result1['area_code'][12:15]
#         sql2='select label from category where code=%s'
#         result2=db.fetchonedb(sql2,[province_code])
#         province_txt=result2['label']
#         result3=db.fetchonedb(sql2,[city_code])
#         city_txt=result3['label']
#         return {'province_code':province_code,'city_code':city_code,'province_txt':province_txt,'city_txt':city_txt}
#         