#-*- coding:utf-8 -*-
class mshop:
    def __init__(self):
        self.dbc=dbc
    def getis_wxtg(self,company_id):
        sql='select id from shop_product where company_id=%s and is_check=0'
        result=self.dbc.fetchonedb(sql,[company_id])
        if result:
            return result[0]
class zzqianbao:
    #----初始化ast数据库
    def __init__(self):
        self.dbc=dbc
    def getviptype(self,company_id):
        sql='select membership_code from company where id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])
        if result:
            return result[0]
    def getsendfee(self,company_id,fee,ftype,more=""):
        sqlc="select begin_time,end_time,maxfee from pay_wallettype where id=%s"
        resultgg=self.dbc.fetchonedb(sqlc,[ftype])
        timeall=datetime.datetime.now()
        if resultgg:
            begin_time=resultgg[0]
            end_time=resultgg[1]
            maxfee=resultgg[2]
        else:
            return 0.00
        payinsert=1
        if begin_time and end_time:
            if timeall>=begin_time and timeall<end_time:
                payinsert=1
            else:
                payinsert=0
        if (payinsert==1):
            sql='select id from pay_mobileWallet where company_id=%s and fee=%s and ftype=%s'
            result=self.dbc.fetchonedb(sql,[company_id,fee,ftype])
            if not result:
                if not ftype:
                    ftype=6
                if not fee:
                    fee=20
                gmt_date=datetime.date.today()
                gmt_created=datetime.datetime.now()
                
                sql2='insert into pay_mobileWallet(company_id,fee,ftype,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s)'
                self.dbc.updatetodb(sql2,[company_id,fee,ftype,gmt_date,gmt_created,gmt_created])
            else:
                
                #多次获得钱包
                if more and more!="None":
                    gmt_date=datetime.date.today()
                    gmt_created=datetime.datetime.now()
                    #判断当天最大进账数
                    insertfee=0
                    nowfeeall=self.getinfeedate(company_id,gmt_begin=gmt_date,ftype="("+str(ftype)+")")
                    if not nowfeeall:
                        nowfeeall=0
                    if float(maxfee)>0:
                        if float(nowfeeall)<float(maxfee):
                            insertfee=1
                    else:
                        insertfee=1
                    if insertfee==1:
                        sql2='insert into pay_mobileWallet(company_id,fee,ftype,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s)'
                        self.dbc.updatetodb(sql2,[company_id,fee,ftype,gmt_date,gmt_created,gmt_created])
        
    def getcompanycontact(self,company_id):
        sql='select contact,mobile from company_account where company_id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])
        list={'contact':'','mobile':''}
        if result:
            contact=result[0]
            mobile=result[1]
            list={'contact':contact,'mobile':mobile}
        return list
    def getpayfeelist(self,company_id,frompageCount='',limitNum='',timarg=''):
        argument=[company_id]
        sqlarg=''
        if timarg:
            if timarg=='1':
                sqlarg=' and gmt_date>=%s'
                argument.append(getpastoneday(30))
            elif timarg=='2':
                sqlarg=' and gmt_date>=%s'
                argument.append(getpastoneday(90))
            elif timarg=='3':
                sqlarg=' and gmt_date>=%s'
                argument.append(getpastoneday(365))
            elif timarg=='4':
                sqlarg=' and gmt_date<%s'
                argument.append(getpastoneday(365))
        sql1='select count(0) from pay_mobileWallet where company_id=%s'+sqlarg
        sql='select id,fee,ftype,gmt_date,product_id from pay_mobileWallet where company_id=%s'+sqlarg
        sql=sql+' order by gmt_created desc limit '+str(frompageCount)+','+str(limitNum)
        count=self.dbc.fetchnumberdb(sql1,argument)
        resultlist=self.dbc.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            id=result[0]
            fee=result[1]
            if fee>0:
                fee='+'+str('%.2f'%fee)
            else:
                fee=('%.2f'%fee)
            ftype=result[2]
            gmt_date=formattime(result[3],1)
            product_id=result[4]
            ftypename=self.getftypename(ftype)
            list={'id':id,'fee':fee,'ftype':ftype,'gmt_date':gmt_date,'ftypename':ftypename,'product_id':product_id}
            listall.append(list)
        return {'list':listall,'count':count}
    def getftypename(self,id):
        sql='select name from pay_wallettype where id=%s'
        result=self.dbc.fetchonedb(sql,[id])
        if result:
            return result[0]
    #钱包类型
    def getftypelist(self,subid):
        sql='select id,name,fee from pay_wallettype where subid=%s'
        result=self.dbc.fetchalldb(sql,[subid])
        if result:
            listall=[]
            for l in result:
                list={'id':l[0],'name':l[1],'fee':l[2]}
                listall.append(list)
            return listall
    def getqianbaoblance(self,company_id):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])[0]
        if result:
            if result<=0:
                return '0.00'
            else:
                return '%.2f'%result
        return '0.00'
    def getqianbaoblance2(self,company_id):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])[0]
        if result:
            return result
        return 0.0
    def getpayfee(self,company_id="",forcompany_id="",product_id="",ftype="",more=None,fee=None):
        #----判断该客户是被送了20元
        sql4='select id from pay_mobileWallet where company_id=%s and ftype=6'
        idsend20=self.dbc.fetchonedb(sql4,[company_id])
        if idsend20:
            #----手机钱包开张送20活动,优先扣这20元
            sql3='select sum(fee) from pay_mobileWallet where company_id=%s and fee<0'
            outfeeall=self.dbc.fetchonedb(sql3,[company_id])[0]
            if not outfeeall:
                outfeeall=0.0
            if ftype=='1' and outfeeall>-20:
                ftype='7'
        if not fee:
            fee=self.getpay_wallettypefee(ftype)
        if not fee:
            return "nomoney"
        if more:
            sql='select id from pay_mobileWallet where company_id=%s and ftype=%s'
            result=self.dbc.fetchonedb(sql,[company_id,ftype])
            if result:
                return "havepay"
        #----账户余额大于当前消费金额才可以进行交易
        sql4='select sum(fee) from pay_mobileWallet where company_id=%s'
        blance=self.dbc.fetchonedb(sql4,[company_id])[0]
        
        if blance>=-fee:
            gmt_date=datetime.date.today()
            gmt_created=datetime.datetime.now()
            argument=[company_id,forcompany_id,product_id,fee,ftype,gmt_date,gmt_created,gmt_created]
            sql='insert into pay_mobileWallet(company_id,forcompany_id,product_id,fee,ftype,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s,%s)'
            self.dbc.updatetodb(sql,argument)
            if ftype in ['1','7']:
                argument2=[forcompany_id,company_id,product_id,0.5,2,gmt_date,gmt_created,gmt_created]
                sql2='insert into pay_mobileWallet(company_id,forcompany_id,product_id,fee,ftype,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s,%s)'
                self.dbc.updatetodb(sql2,argument2)
            return 1
        else:
            return "nomoney"
    def getpay_wallettypefee(self,id):
        sql='select fee from pay_wallettype where id=%s'
        result=self.dbc.fetchonedb(sql,[id])
        if result:
            return result[0]
    def getisseecompany(self,company_id,forcompany_id):
        if company_id==forcompany_id:
            return 2
        iszst=self.getiszstcompany(company_id)
        if iszst:
            return 2
        sql2='select membership_code from company where id=%s'
        result2=self.dbc.fetchonedb(sql2,[company_id])
        if result2:
            membership_code=result2[0]
#            if membership_code in ['10051001','100510021000','100510021001','100510021002']:
#                return 2
            if membership_code=='10051003':
                sql3='select id from phone_click_log where company_id=%s and target_id=%s'
                result3=self.dbc.fetchonedb(sql3,[company_id,forcompany_id])
                if result3:
                    return 4
        sql='select id from pay_mobileWallet where company_id=%s and forcompany_id=%s'
        result=self.dbc.fetchonedb(sql,[company_id,forcompany_id])
        if result:
            return 1
    #----判断是否为再生通
    def getiszstcompany(self,company_id):
        if company_id:
            sqll="select id from crm_company_service where company_id=%s and crm_service_code in('1000','1006') and apply_status=1 and DATEDIFF(CURDATE(),`gmt_end`)<=0"
            zstresult=self.dbc.fetchonedb(sqll,[company_id])
            if zstresult:
                return 1
            else:
                return None
        else:
            return None
    def getinfeegmt(self,company_id,gmt_begin='',gmt_end='',ftype='',notftype=''):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s and fee>0 and ftype=2'
        argument=[company_id]
        if gmt_begin:
            argument.append(gmt_begin)
            sql+=' and gmt_date>=%s'
        if gmt_end:
            argument.append(gmt_end)
            sql+=' and gmt_date<%s'
        if ftype:
            sql=sql.replace('and ftype=2',' and ftype in '+ftype)
        if notftype:
            sql=sql.replace('and ftype=2','')
            sql+=' and ftype not in '+notftype+''
        result=self.dbc.fetchonedb(sql,argument)[0]
        if result:
            return '%.2f'%result
        return 0.0
    def getinfeedate(self,company_id,gmt_begin='',ftype='',notftype=''):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s and fee>0 and ftype=2'
        argument=[company_id]
        if gmt_begin:
            argument.append(gmt_begin)
            sql+=' and gmt_date=%s'
        if ftype:
            sql=sql.replace('and ftype=2',' and ftype in '+ftype)
        if notftype:
            sql=sql.replace('and ftype=2','')
            sql+=' and ftype not in '+notftype+''
        result=self.dbc.fetchonedb(sql,argument)[0]
        if not result:
            result=0
        return result
    def getoutfeegmt(self,company_id,gmt_begin='',gmt_end=''):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s and fee<0'
        argument=[company_id]
        if gmt_begin:
            argument.append(gmt_begin)
            sql+=' and gmt_date>=%s'
        if gmt_end:
            sql+=' and gmt_date<%s'
            argument.append(gmt_end)
        result=self.dbc.fetchonedb(sql,argument)[0]
        if result:
            return '%.2f'%float(str(result)[1:])
        return 0.0
    
    def getinfeeyd(self,company_id):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s and gmt_date=%s and fee>0 and ftype=2'
        result=self.dbc.fetchonedb(sql,[company_id,getYesterday()])[0]
        if result:
            return '%.2f'%result
        return '0.00'
    def getinfeeall(self,company_id):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s and fee>0 and ftype=2'
        result=self.dbc.fetchonedb(sql,[company_id])[0]
        if result:
            return '%.2f'%result
        return '0.00'
    def getoutfeeyd(self,company_id):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s and gmt_date=%s and fee<0'
        result=self.dbc.fetchonedb(sql,[company_id,getYesterday()])[0]
        if result:
            return '%.2f'%float(str(result)[1:])
        return '0.00'
    def getoutfeeall(self,company_id):
        sql='select sum(fee) from pay_mobileWallet where company_id=%s and fee<0'
        result=self.dbc.fetchonedb(sql,[company_id])[0]
        if result:
            return '%.2f'%float(str(result)[1:])
        return '0.00'
    #判断首次安装app时间
    def getfistinstallapp(self,account):
        sql="select id from oauth_access where open_type='app.zz91.com' and DATEDIFF(CURDATE(),gmt_created)<=7 and target_account=%s and closeflag=0"
        result=self.dbc.fetchonedb(sql,[account])
        if result:
            return 1
        else:
            return None
    #---首页广告模块-
    def getadgoods(self,bclassid="",adtype="",adposition="",gid=""):
        sevalue=[]
        list=None
        sql="select id,goodsName,billing_Class_ID,start_Time,end_Time,original_Price,present_Price,sales_Num,left_Num,pic,status,release_Time,tuijian,goodsname_fu,tourl,ad_type,ad_position,havenum from app_goods where id>0 and status=1"
        if bclassid:
            sql+=" and billing_Class_ID=%s "
            sevalue.append(int(bclassid))
        if adtype:
            sql+=" and ad_type=%s "
            sevalue.append(int(adtype))
        if adposition:
            sql+=" and ad_position=%s "
            sevalue.append(int(adposition))
        if gid:
            sql+=" and id=%s "
            sevalue.append(int(gid))
        sql+=" order by start_Time asc"
        result=self.dbc.fetchonedb(sql,sevalue)
        if result:
            id=result[0]
            goodsName=result[1]
            goodsname_fu=result[13]
            if not goodsname_fu:
                goodsname_fu=""
            billing_Class_ID=result[2]
            start_Time=formattime(result[3],0)
            end_Time=formattime(result[4],0)
            original_Price=result[5]
            present_Price=result[6]
            sales_Num=result[7]
            left_Num=result[8]
            pic=result[9]
            if not pic:
                pic=""
            else:
                pic="http://img3.zz91.com/300x200/"+pic.replace("http://img1.zz91.com/","")
            status=str(result[10])
            statustxt=""
            if status=="0":
                statustxt='已下架'
            if status=="1":
                statustxt='已上架'
            release_Time=formattime(result[11],0)
            tuijian=str(result[12])
            tuijiantxt=""
            if tuijian=="1":
                tuijiantxt='已推荐'
            if tuijian=="0":
                tuijiantxt='未推荐'
            tourl=result[14]
            if tourl:
                tourl=tourl.strip()
                if "keywords=" in tourl:
                    adkeywords=tourl.replace("/offerlist/?keywords=", "")
                    tourl="/offerlist/?keywords="+getjiami(adkeywords)
            if not tourl:
                tourl=""
            ad_type=result[15]
            ad_position=result[16]
            havenum=result[17]
            if not havenum:
                havenum=0
            list={'id':id,'goodsName':goodsName,'goodsname_fu':goodsname_fu,'billing_Class_ID':billing_Class_ID,'start_Time':start_Time,'end_Time':end_Time,'original_Price':original_Price,'present_Price':present_Price,'sales_Num':sales_Num,'left_Num':left_Num,'pic':pic,'statustxt':statustxt,'status':status,'release_Time':release_Time,'tuijiantxt':tuijiantxt,'tourl':tourl,'ad_type':ad_type,'ad_position':ad_position,'havenum':havenum}
        return list
            