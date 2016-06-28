#-*- coding:utf-8 -*-
import re,datetime,time
from zz91tools import int_to_str,formattime,getnub_tostr,str_to_date,date_to_str,getpastoneday
from zz91db_ast import companydb
from zz91db_130 import otherdb

class myrc:
    def __init__(self):
        self.db=otherdb()
        self.dbc=companydb()
    def getmyrc_operatypelist(self):
        sql='select id,name from data_myrc_operatype'
        resultlist=self.db.fetchalldb(sql)
        listall=[]
        for result in resultlist:
            id=result[0]
            name=result[1]
            list={'id':id,'name':name}
            listall.append(list)
        return listall
    def getmyrc_operalist(self,frompageCount='',limitNum='',optype_id='',account='',gmt_begin=''):
        sql1='select count(0) from data_myrc where id>0'
        sql='select id,operatype_id,account,pro_id,gmt_created,ip from data_myrc where id>0'
        argument=[]
        if optype_id:
            sql1=sql1+' and operatype_id=%s'
            sql=sql+' and operatype_id=%s'
            argument.append(optype_id)
        if account:
            sql1=sql1+' and account=%s'
            sql=sql+' and account=%s'
            argument.append(account)
        if gmt_begin:
            sql1=sql1+' and gmt_date=%s'
            sql=sql+' and gmt_date=%s'
            argument.append(gmt_begin)
        count=self.db.fetchnumberdb(sql1,argument)
        sql=sql+' order by id desc limit '+str(frompageCount)+','+str(limitNum)
        resultlist=self.db.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            operatype_id=result[1]
            operatype=self.getoperatype(operatype_id)
            account=result[2]
            comp_id=self.getcomp_id(account)
            comp_name=self.getcomp_name(comp_id)
            pro_id=result[3]
            pro_title=self.getpro_title(pro_id)
            if not pro_title:
                pro_title=''
            list={'id':result[0],'operatype_id':operatype_id,'pro_id':pro_id,'pro_title':pro_title,'operatype':operatype,'comp_id':comp_id,'comp_name':comp_name,'account':account,'gmt_created':formattime(result[4]),'ip':result[5]}
            listall.append(list)
        return {'list':listall,'count':count}

    def getoperatype(self,id):
        sql='select name from data_myrc_operatype where id=%s'
        result=self.db.fetchonedb(sql,[id])
        if result:
            return result[0]
    def delopera(self,gmt_begin):
        sql='delete from data_myrc where gmt_date=%s'
        self.db.updatetodb(sql,[gmt_begin])

    def getcomp_name(self,comp_id):
        sql='select name from company where id=%s'
        result=self.dbc.fetchonedb(sql,[comp_id])
        if result:
            return result[0]

    def getcomp_id(self,account):
        sql='select company_id from company_account where account=%s'
        result=self.dbc.fetchonedb(sql,[account])
        if result:
            return result[0]

    def getpro_title(self,pro_id):
        sql='select title from products where id=%s'
        result=self.dbc.fetchonedb(sql,[pro_id])
        if result:
            return result[0]
    #----关闭连接
    def closedb(self):
        companydb.closedb()
        otherdb.closedb()

class company():
    def __init__(self):
        self.db=otherdb()
        self.dbc=companydb()
    #-----获得公司发布详情数据
    def getcompany_data(self,frompageCount,limitNum,gmt_begin='',gmt_end='',company_name='',is_senior='',pubtype='',procode='',order=''):
        sqlarg=''
        argument=[]
        if pubtype=='1':
            if gmt_begin:
                sqlarg+=' and refresh_time>="'+gmt_begin+' 00:00:00"'
#                argument.append(gmt_begin)
            if gmt_end:
                sqlarg+=' and refresh_time<"'+gmt_end+' 00:00:00"'
#                argument.append(gmt_end)
        elif pubtype=='2':
            if gmt_begin:
                sqlarg+=' and send_time>="'+gmt_begin+' 00:00:00"'
#                argument.append(gmt_begin)
            if gmt_end:
                sqlarg+=' and send_time<"'+gmt_end+' 00:00:00"'
#                argument.append(gmt_end)
        else:
            if gmt_begin:
                sqlarg+=' and gmt_date>="'+gmt_begin+' 00:00:00"'
#                argument.append(gmt_begin)
            if gmt_end:
                sqlarg+=' and gmt_date<"'+gmt_end+' 00:00:00"'
        if not gmt_begin and not gmt_end:
            timeall=time.strftime('%H:%M:%S',time.localtime(time.time()))
            if timeall>'16:30:00':
                pastoneday1=getpastoneday(1)
                pastoneday2=datetime.date.today()
            else:
                pastoneday1=getpastoneday(2)
                pastoneday2=getpastoneday(1)
            sqlarg+=' and gmt_date>="'+date_to_str(pastoneday1)+'"'
            sqlarg+=' and gmt_date<"'+date_to_str(pastoneday2)+'"'
        if is_senior:
            sqlarg+=' and is_senior='+is_senior
        if procode:
            sqlarg+=' and industry_code='+procode
#            sqlarg+=' and is_senior=%s'
#            argument.append(is_senior)
        if company_name:
            sqlarg+=' and company_name like "%'+company_name+'%"'
#            argument.append(company_name)
        sql1='select count(distinct company_id) from data_company where id>0 and offnum>0'+sqlarg
        count=self.db.fetchnumberdb(sql1,argument)
        sql='select id,company_id,account,company_name,sum(pro_num),sum(offnum),sum(buynum),sum(pro_pass_num),sum(pro_back_num),sum(get_ask_num),sum(send_ask_num),refresh_time,send_time,gmt_date,is_senior from data_company where id>0'+sqlarg
        sql+=' group by company_id'
        if order:
            sql+=' order by '+order
        else:
            sql+=' order by id desc'
        sql+=' limit '+str(frompageCount)+','+str(limitNum)
        resultlist=self.db.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            id=result[0]
            company_id=result[1]
            account=result[2]
            company_name=result[3]
            pro_num=result[4]
            offnum=result[5]
            buynum=result[6]
            pro_pass_num=result[7]
            pro_back_num=result[8]
            get_ask_num=result[9]
            send_ask_num=result[10]
            refresh_time=formattime(result[11])
            send_time=formattime(result[12])
            gmt_date=formattime(result[13],1)
            issenior=result[14]
            if pro_num==0:
                pro_pass_lv='0%'
            else:
                pro_pass_lv=str(pro_pass_num*100/pro_num)[:6]+'%'
            list={'id':id,'company_id':company_id,'account':account,'company_name':company_name,'pro_num':pro_num,'offnum':offnum,'buynum':buynum,'pro_pass_num':pro_pass_num,'pro_back_num':pro_back_num,'pro_pass_lv':pro_pass_lv,'get_ask_num':get_ask_num,'send_ask_num':send_ask_num,'refresh_time':refresh_time,'send_time':send_time,'gmt_date':gmt_date,'issenior':issenior}
            if not offnum==0:
                listall.append(list)
        return {'list':listall,'count':count}
    def getcompanyprodetail(self,company_id,type,procode='',nowdate='',gmt_begin='',gmt_end=''):
        account=self.getaccount(company_id)
        argument=[company_id]
        sqlarg=''
        sqlarg2=''
        idlist=[]
        if gmt_begin:
            sqlarg+=' and gmt_created>=%s'
            argument.append(gmt_begin)
        if gmt_end:
            sqlarg+=' and gmt_created<%s'
            argument.append(gmt_end)
        if nowdate and not gmt_begin and not gmt_end:
            nowdate=str_to_date(nowdate)
            enddate=nowdate+datetime.timedelta(days=1)
            sqlarg+=' and gmt_created>=%s and gmt_created<%s'
            argument.append(nowdate)
            argument.append(enddate)
        sql2='select count(0) from inquiry where be_inquired_id=%s'+sqlarg
        result1=self.dbc.fetchonedb(sql2,argument)
        listall=[]
        count=0
        if result1:
            company_name=self.getcompany_name(company_id)
            
            export_inquiry=self.getexport_inquiry(company_id)
            
            get_ask_num=result1[0]

            if type=='1':
                pro_type='供应'
                sql1='select count(0) from products where company_id=%s and products_type_code=10331000'+sqlarg+sqlarg2
                sql='select id,title,real_time from products where company_id=%s and products_type_code=10331000'+sqlarg+sqlarg2
                argument+=idlist
            if type=='2':
                pro_type='求购'
                sql1='select count(0) from products where company_id=%s and products_type_code=10331001'+sqlarg+sqlarg2
                sql='select id,title,real_time from products where company_id=%s and products_type_code=10331001'+sqlarg+sqlarg2
                argument+=idlist
            if type=='3':
                pro_type='供求总数'
                sql1='select count(0) from products where company_id=%s'+sqlarg+sqlarg2
                sql='select id,title,real_time from products where company_id=%s'+sqlarg+sqlarg2
                argument+=idlist
            if type=='4':
                pro_type='供求通过'
                sql1='select count(0) from products where company_id=%s and check_status=1'+sqlarg+sqlarg2
                sql='select id,title,real_time from products where company_id=%s and check_status=1'+sqlarg+sqlarg2
                argument+=idlist
            if type=='5':
                pro_type='供求退回'
                sql1='select count(0) from products where company_id=%s and check_status=2'+sqlarg+sqlarg2
                sql='select id,title,real_time from products where company_id=%s and check_status=2'+sqlarg+sqlarg2
                argument+=idlist
            if type=='6':
                pro_type='发询盘'
                sql1='select count(0) from inquiry where sender_account=%s'+sqlarg
                sql='select id,title,gmt_created from inquiry where sender_account=%s'+sqlarg
                argument=[account]+argument[1:]
            if type=='7':
                pro_type='收询盘'
                sql1='select count(0) from inquiry where be_inquired_id=%s'+sqlarg
                sql='select id,title,gmt_created from inquiry where be_inquired_id=%s'+sqlarg
            if procode:
                idlist=self.getproidlist(procode,gmt_begin,gmt_end,nowdate)
                if 'products' in sql:
                    sql1+=' and id in %s'
                    sql+=' and id in %s'
                    argument+=[idlist]
            count=self.dbc.fetchnumberdb(sql1,argument)
            resultlist=self.dbc.fetchalldb(sql,argument)
            for result in resultlist:
                id=result[0]
                title=result[1]
                real_time=formattime(result[2])
                list={'id':id,'title':title,'real_time':real_time,'export_inquiry':export_inquiry,'company_id':company_id,'company_name':company_name,'get_ask_num':get_ask_num,'pro_type':pro_type}
                listall.append(list)
        return {'list':listall,'count':count}
    def getcompany_name(self,id):
        sql='select name from company where id=%s'
        result=self.dbc.fetchonedb(sql,[id])
        if result:
            return result[0]
    def getaccount(self,company_id):
        sql='select account from company_account where company_id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])
        if result:
            return result[0]
    def getexport_inquiry(self,company_id):
        sql='select count(0) from products_export_inquiry where from_company_id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])
        if result:
            return result[0]
    def getproidlist(self,procode,gmt_begin='',gmt_end='',nowdate=''):
        argument=[]
        sqlarg=''
        if gmt_begin:
            sqlarg+=' and gmt_created>=%s'
            argument.append(gmt_begin)
        if gmt_end:
            sqlarg+=' and gmt_created<%s'
            argument.append(gmt_end)
        if nowdate and not gmt_begin and not gmt_end:
#            nowdate=str_to_date(nowdate)
            enddate=nowdate+datetime.timedelta(days=1)
            sqlarg+=' and gmt_created>=%s and gmt_created<%s'
            argument.append(nowdate)
            argument.append(enddate)
        sql='select id,category_products_main_code from products where id>0'+sqlarg
        resultlist=self.dbc.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            category_products_main_code=result[1]
            if category_products_main_code:
                category_code=category_products_main_code[:4]
                if category_code==procode:
                    id=result[0]
                    listall.append(id)
        return listall
    #----关闭连接
    def closedb(self):
        companydb.closedb()
        otherdb.closedb()
        
class searchkeyword():
    def __init__(self):
        self.db=otherdb()
        self.dbc=companydb()
    def getsearchkeyword(self,frompageCount,limitNum,gmt_begin='',gmt_end='',account='',ip='',keyword='',company_name='',islog='',is_senior='',industry_code='',order=''):
        sqlarg=''
        argument=[]
        if gmt_begin:
            sqlarg+=' and gmt_date>="'+gmt_begin+'"'
#            sqlarg+=' and gmt_date>=%s'
#            argument.append(gmt_begin)
        if gmt_end:
            sqlarg+=' and gmt_date<"'+gmt_end+'"'
#            sqlarg+=' and gmt_date<=%s'
#            argument.append(gmt_end)
        if account:
            sqlarg+=' and account="'+account+'"'
#            sqlarg+=' and account=%s'
#            argument.append(account)
        if ip:
            sqlarg+=' and ip="'+ip+'"'
#            sqlarg+=' and ip=%s'
#            argument.append(ip)
        if keyword:
            sqlarg+=' and keyword like "%'+keyword+'%"'
#            argument.append(keyword)
        if company_name:
            sqlarg+=' and company_name="'+company_name+'"'
#            sqlarg+=' and company_name=%s'
#            argument.append(company_name)
        if industry_code:
            sqlarg+=' and industry_code="'+industry_code+'"'
#            sqlarg+=' and industry_code=%s'
#            argument.append(industry_code)
        if is_senior:
            sqlarg+=' and is_senior='+is_senior
#            sqlarg+=' and is_senior=%s'
#            argument.append(is_senior)
        if islog:
            if islog=='1':
                sqlarg+=' and account is not null'
            else:
                sqlarg+=' and account is null'
                
        sql1='select count(0) from data_ip_search where id>0'+sqlarg
        count=self.db.fetchnumberdb(sql1,argument)
        sql='select id,keyword,account,url,ip,gmt_date,company_id,company_name,sum(keyword_count),sum(keyword_allcount),sum(search_count),score,is_senior from data_ip_search where id>0'+sqlarg
        sql+=' group by keyword,ip'
        if order:
            sql+=' order by '+order
        else:
            sql+=' order by gmt_date desc'
        sql+=' limit '+str(frompageCount)+','+str(limitNum)
        resultlist=self.db.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            id=result[0]
            keyword=result[1]
            account=result[2]
            url=result[3]
            ip=result[4]
            gmt_date=formattime(result[5])
            company_id=result[6]
            company_name=result[7]
            keyword_count=result[8]
            keyword_allcount=result[9]
            search_count=result[10]
            score=result[11]
            issenior=result[12]
            if score:
                score=str(score)+'%'
#            score=''
#            if keyword_count and keyword_allcount:
#                score=str(float(keyword_count)*100/keyword_allcount)+'%'
            keyword_score=''
            if keyword_allcount and count:
                keyword_score=str(keyword_allcount)+'/'+str(count)
#            if not account:
#                account=self.getaccountbyip(ip)
            list={'id':id,'keyword':keyword,'account':account,'company_id':company_id,'company_name':company_name,'ipaccount':'','keyword_count':keyword_count,'keyword_allcount':keyword_allcount,'score':score,'keyword_score':keyword_score,'search_count':search_count,'url':url,'ip':ip,'gmt_date':gmt_date,'issenior':issenior}
            listall.append(list)
        return {'list':listall,'count':count}
    def getaccountbyip(self,ip):
        sql='select account_id from data_ip where numb=%s'
        result=self.db.fetchonedb(sql,[ip])
        if result:
            account_id=result[0]
            sql1='select numb from data_account where id=%s'
            result1=self.db.fetchonedb(sql1,[account_id])
            if result1:
                return result1[0]
        return ''
    #----主营行业列表
    def getindustrylist(self):
        sql='select code,label from category where parent_code=1000'
        resultlist=self.dbc.fetchalldb(sql)
        listall=[]
        for result in resultlist:
            list={'code':result[0],'label':result[1]}
            listall.append(list)
        return listall
    #----获得主营行业label
    def getclabel(self,code):
        sql='select label from category where code=%s'
        result=self.dbc.fetchonedb(sql,[code])
        if result:
            return result[0]
    #----关闭连接
    def closedb(self):
        companydb.closedb()
        otherdb.closedb()

class product():
    def __init__(self):
        self.db=otherdb()
        self.dbc=companydb()
    def getprocategorylist(self):
        listall=[]
        sql='select code,label from category_products where id<11'
        resultlist=self.dbc.fetchalldb(sql)
        listall=[]
        for result in resultlist:
            list={'code':result[0],'label':result[1]}
            listall.append(list)
        return listall
    def getproductlist(self,gmt_begin='',gmt_end='',type_code='',is_senior=''):
        if type_code=='10331003':
            type_code=''
        sqlarg=''
        argument=[]
        if gmt_begin:
            sqlarg+=' and gmt_date>=%s'
            argument.append(gmt_begin)
        if gmt_end:
            sqlarg+=' and gmt_date<%s'
            argument.append(gmt_end)
        if not gmt_begin and not gmt_end:
            timeall=time.strftime('%H:%M:%S',time.localtime(time.time()))
            if timeall>'16:30:00':
                pastoneday=getpastoneday(1)
            else:
                pastoneday=getpastoneday(2)
            sqlarg+=' and gmt_date=%s'
            argument.append(pastoneday)
        if type_code:
            sqlarg+=' and type_code=%s'
            argument.append(type_code)
        if is_senior:
            sqlarg+=' and is_senior=%s'
            argument.append(is_senior)
        sql1='select sum(pro_numb) from data_product where id>0'+sqlarg
        count=self.db.fetchnumberdb(sql1,argument)
        countall_pass=0
        countall_back=0
        sql='select id,type_code,category_code,sum(pro_numb),sum(pro_pass),sum(pro_back),is_senior,gmt_date from data_product where id>0'+sqlarg
        sql+=' group by category_code'
        sql+=' order by gmt_date desc'
        resultlist=self.db.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            type_codes=''
            id=result[0]
            if type_code:
                type_codes=result[1]
            else:
                type_codes='全部'
            category_code=result[2]
            category_name=self.getcategory_name(category_code)
            pro_numb=result[3]
            pro_pass=result[4]
            countall_pass+=pro_pass
            pro_back=result[5]
            countall_back+=pro_back
            is_senior=result[6]
            gmt_date=formattime(result[7],1)
            if count==0:
                pro_score='0%'
            else:
                pro_score=str(pro_numb*100/count)[:6]+'%'
            if pro_numb==0:
                pass_score=0
            else:
                pass_score=0
                pass_score=str(pro_pass*100/pro_numb)[:6]+'%'
            list={'id':id,'type_code':type_codes,'category_code':category_code,'category_name':category_name,'pro_score':pro_score,'pass_score':pass_score,'pro_numb':pro_numb,'pro_pass':pro_pass,'pro_back':pro_back,'is_senior':is_senior,'gmt_date':gmt_date}
            listall.append(list)
        return {'list':listall,'countall':count,'countall_pass':countall_pass,'countall_back':countall_back}
    def getcategory_name(self,code):
        sql='select label from category_products where code=%s'
        result=self.dbc.fetchonedb(sql,[code])
        if result:
            return result[0]
    #----关闭连接
    def closedb(self):
        companydb.closedb()
        otherdb.closedb()

class login:
    def __init__(self):
        self.db=otherdb()
        self.dbc=companydb()
    def getlogindatalist(self,frompageCount,limitNum,gmt_begin='',gmt_end='',logcount_begin='',logcount_end='',main_area_code='',industry_code='',company_name='',is_senior='',order='',addarg=''):
        sqlarg=''
        argument=[]
        if addarg:
            sqlarg+=addarg
        if gmt_begin:
            sqlarg+=' and gmt_date>="'+gmt_begin+'"'
#            argument.append(gmt_begin)
        if gmt_end:
            sqlarg+=' and gmt_date<"'+gmt_end+'"'
#            argument.append(gmt_end)
        if main_area_code:
            sqlarg+=' and main_area_code='+main_area_code
#            sqlarg+=' and main_area_code=%s'
#            argument.append(main_area_code)
        if industry_code:
            sqlarg+=' and industry_code='+industry_code
#            sqlarg+=' and industry_code=%s'
#            argument.append(industry_code)
        if is_senior:
            sqlarg+=' and is_senior='+is_senior
#            sqlarg+=' and is_senior=%s'
#            argument.append(is_senior)
        if company_name:
            sqlarg+=' and company_name like "%'+company_name+'%"'
#            sqlarg+=' and company_name like "%'+'%s'+'%"'
#            argument.append(company_name)
        sql1='select count(distinct company_id) from data_login where id>0'+sqlarg
        sql='select id,account,company_id,company_name,industry_code,area_code,main_area_code,sum(log_count),last_logtime,how_logtime,regtime,gmt_date,is_senior from data_login'
        sql+=' where id>0 '+sqlarg+' group by company_id'
        if logcount_begin and logcount_end:
            sql+=' having sum(log_count)>='+logcount_begin
            sql+=' and sum(log_count)<'+logcount_end
        count=self.db.fetchnumberdb(sql1,argument)
        if order:
            sql+=' order by '+order
        else:
            sql+=' order by gmt_date desc'
        sql+=' limit '+str(frompageCount)+','+str(limitNum)
        resultlist=self.db.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            id=result[0]
            account=result[1]
            company_id=result[2]
            company_name=result[3]
            industry_code=result[4]
            industry_label=self.getclabel(industry_code)
            area_code=result[5]
            main_area_code=result[6]
            area=self.getclabel(area_code)
            main_area=self.getclabel(main_area_code)
            log_count=result[7]
            last_logtime=formattime(result[8])
            ltime=result[9]
            how_logtime=getnub_tostr(ltime)
#            how_logtime=str(ltime/3600)+':'+str(ltime/60)+':'+str(ltime%60)
            
#            how_logtime=int_to_strall(how_logtime)
#            if how_logtime<60:
#                how_logtime=str(how_logtime)
#            else:
#                how_logtime=str(how_logtime/60)
            regtime=formattime(result[10])
            gmt_date=formattime(result[11],1)
            issenior=result[12]
            list={'id':id,'account':account,'company_id':company_id,'company_name':company_name,'industry_code':industry_code,'industry_label':industry_label,'area':area,'main_area':main_area,'log_count':log_count,'last_logtime':last_logtime,'how_logtime':how_logtime,'regtime':regtime,'gmt_date':gmt_date,'issenior':issenior}
            listall.append(list)
        return {'list':listall,'count':count}
    def getlogindetails(self,frompageCount,limitNum,company_id,gmt_begin='',gmt_end=''):
        sqlarg=''
        argument=[company_id]
        if gmt_begin:
            sqlarg+=' and gmt_created>=%s'
            argument.append(gmt_begin)
        if gmt_end:
            sqlarg+=' and gmt_created<%s'
            argument.append(gmt_end)
        sql1='select count(0) from data_login_detail where company_id=%s'+sqlarg
        sql='select id,gmt_created from data_login_detail where company_id=%s'+sqlarg
        sql+=' order by gmt_created desc'
        sql+=' limit '+str(frompageCount)+','+str(limitNum)
        count=self.db.fetchnumberdb(sql1,argument)
        resultlist=self.db.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            id=result[0]
            gmt_created=formattime(result[1])
            company_detail=self.getcompany_detail(company_id)
            if company_detail:
                company_name=company_detail['company_name']
                industry_code=company_detail['industry_code']
                industry_label=self.getclabel(industry_code)
                membership_code=company_detail['membership_code']
                regtime=company_detail['regtime']
                area_code=company_detail['area_code']
                area=self.getclabel(area_code)
                main_area_code=area_code[:12]
                main_area=self.getclabel(main_area_code)
                list={'id':id,'gmt_created':gmt_created,'gmt_date':formattime(result[1],1),'company_id':company_id,'company_name':company_name,'industry_label':industry_label,'area':area,'main_area':main_area,'regtime':regtime}
                listall.append(list)
        return {'list':listall,'count':count}
    def getcompany_detail(self,id):
        sql='select name,industry_code,membership_code,area_code,regtime from company where id=%s'
        result=self.dbc.fetchonedb(sql,[id])
        if result:
            list={'company_name':result[0],'industry_code':result[1],'membership_code':result[2],'area_code':result[3],'regtime':formattime(result[4])}
            return list
    #----获得主营行业label
    def getclabel(self,code):
        sql='select label from category where code=%s'
        result=self.dbc.fetchonedb(sql,[code])
        if result:
            return result[0]
    #----关闭连接
    def closedb(self):
        companydb.closedb()
        otherdb.closedb()