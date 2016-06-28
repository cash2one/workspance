import MySQLdb
import time
import sys,re
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
reload(sys) 
sys.setdefaultencoding('utf-8') 
import bs4
from bs4 import BeautifulSoup
##过滤HTML中的标签
#将HTML中标签等信息去掉
#@param htmlstr HTML字符串.
def filter_tags(htmlstr):
    #先过滤CDATA
    re_cdata=re.compile('//<!\[CDATA\[[^>]*//\]\]>',re.I) #匹配CDATA
    re_script=re.compile('<\s*script[^>]*>[^<]*<\s*/\s*script\s*>',re.I)#Script
    re_style=re.compile('<\s*style[^>]*>[^<]*<\s*/\s*style\s*>',re.I)#style
    re_br=re.compile('<br\s*?/?>')#处理换行
    re_h=re.compile('</?\w+[^>]*>')#HTML标签
    re_comment=re.compile('<!--[^>]*-->')#HTML注释
    s=re_cdata.sub('',htmlstr)#去掉CDATA
    s=re_script.sub('',s) #去掉SCRIPT
    s=re_style.sub('',s)#去掉style
    s=re_br.sub('\n',s)#将br转换为换行
    s=re_h.sub('',s) #去掉HTML 标签
    s=re_comment.sub('',s)#去掉HTML注释
    #去掉多余的空行
    blank_line=re.compile('\n+')
    s=blank_line.sub('\n',s)
    s=replaceCharEntity(s)#替换实体
    return s

##替换常用HTML字符实体.
#使用正常的字符替换HTML中特殊的字符实体.
#你可以添加新的实体字符到CHAR_ENTITIES中,处理更多HTML字符实体.
#@param htmlstr HTML字符串.
def replaceCharEntity(htmlstr):
    CHAR_ENTITIES={'nbsp':' ','160':' ',
                'lt':'<','60':'<',
                'gt':'>','62':'>',
                'amp':'&','38':'&',
                'quot':'"','34':'"',}
   
    re_charEntity=re.compile(r'&#?(?P<name>\w+);')
    sz=re_charEntity.search(htmlstr)
    while sz:
        entity=sz.group()#entity全称，如&gt;
        key=sz.group('name')#去除&;后entity,如&gt;为gt
        try:
            htmlstr=re_charEntity.sub(CHAR_ENTITIES[key],htmlstr,1)
            sz=re_charEntity.search(htmlstr)
        except KeyError:
            #以空串代替
            htmlstr=re_charEntity.sub('',htmlstr,1)
            sz=re_charEntity.search(htmlstr)
    return htmlstr
def gettypename(typeid):
    if typeid:
        sql="select search_label from price_category where id=%s"
        cursor.execute(sql,[typeid])
        blist = cursor.fetchone()
        if blist:
            if blist[0]==None:
                return ""
            return blist[0]
        else:
            return ""
    else:
        return ""
def gettypenamelabel(typeid):
    if typeid:
        sql="select name from price_category where id=%s"
        cursor.execute(sql,[typeid])
        blist = cursor.fetchone()
        if blist:
            if blist[0]==None:
                return ""
            return blist[0]
        else:
            return ""
    else:
        return ""
def gettabelfild(n):
    if n==1:
        label=["品名","单品","品 种","品种","品种","品种名称","品目","名称","产品名称","货物名称","废料名称","子类","合约名","钢厂名称","英文品名","品名/牌号"]
    if n==2:
        label=["价格","价 格","含税价格","不含税价（元/公斤）","最高","不含税价（元/吨）","参考价","均价","成交价（元）","最低价","最高价","平均价","价格（元/吨）","最低价（元/吨）","价格(元/吨)","价格区间","收购价（元/吨）","销售价（元/吨）","今日均价","最高/最低价","最新价","成交金额","买卖价","昨结算","开盘","最低","今日价格（元/吨）","昨日价格（元/吨）","开市价","价格范围","昨收市价","昨昨结算价","今日报价","上次报价","报价(元/吨)","出厂价（元/吨）","出厂价（元/吨)","价格 USD/T","最低价（英镑吨）","最高价（英镑吨）","价格usd/t","最低价（英镑／吨）","最高价（英镑／吨）","到岸价（美圆/吨）","广东港口到岸价（美圆/吨）","广东港口到岸价（美元/吨）","最低价格","最高价格","最低/最高价","报价","价格 RMB/T","价格USD/T","价格RMB/T"]
    if n==3:
        label=["地区","省份","交货地点","区域","厂家(产地)","地域","钢厂","产地","仓库","交割地","厂商","报价市场","生产商","报价机构","城市"]
    if n==4:
        label=["含量","材质","产地/牌号","规格","牌号","持仓量","成交量","类别","产品描述","种类","报价类型","品牌","克重"]
    if n==5:
        label=["涨跌","走势","涨跌幅","涨跌（元/吨）","日涨跌","周涨跌"]
    if n==6:
        label=["成交单位","单位"]
    if n==7:
        label=["备注","说明","价格条款"]
    return label
def getfildtext():
    textv=[]
    for l in range(1,8):
        for ll in gettabelfild(l):
            textv.append(ll)
            
    return textv

def isfield(s):
    ss=['title','typename','type_id','assist_type_id','label','priceid','label1','label2','spec','spec1','spec2','price','price1','price2','price3','price4','price5','price6','area','area1','area2','postdate','num','othertext','qushi','othertext1','qushi1','unit']
    if s in ss:
        return 1
    else:
        return None
#判断字符串是否有中文    
def has_chinese_charactar(content):
    '''
    python判断是否是中文需要满足u'[\u4e00-\u9fa5]+'，
    需要注意如果正则表达式的模式中使用unicode，那么
    要匹配的字符串也必须转换为unicode，否则肯定会不匹配。
    '''
    iconvcontent = unicode(content)
    zhPattern = re.compile(u'[\u4e00-\u9fa5]+')
    match = zhPattern.search(iconvcontent)
    res = False
    if match:
        res = True
    return res

def savefiledname(o,priceid,textname,filedname):
    if o!="":
        sql="select id from price_titlefild where priceid=%s and filed=%s"
        cursor.execute(sql,[priceid,filedname])
        blist = cursor.fetchone()
        if blist==None:
            sqla="insert into price_titlefild(priceid,name,filed) values(%s,%s,%s)"
            cursor.execute(sqla,[priceid,textname,filedname])
            conn.commit()

#----报价表格分析
def gettablelist1(content):
    content=content.lower()
    content=content.replace("<th","<td")
    content=content.replace("</th>","</td>")
    arrtable=content.split("<table")
    listall=[]
    tablestr=""
    for table in arrtable:
        tablestr+="<table border=1>"
        arrtr=table.split("<tr")
        i=1
        trow=[]
        for row in arrtr[1:]:
            tablestr+="<tr>"
            arrtd=row.split("<td")
            j=1
            for tr in arrtd[1:]:
                tr="<td "+tr
                trsss=tr.split("</td>")
                tr=trsss[0]+"</td>"
                trs=tr
                tr = BeautifulSoup(tr)
                textname=tr.text.encode("utf-8")
                textname=filter_tags(textname.replace(" ","")).strip('\n')

                
                if ("rowspan" in trs):
                    rowspan=tr.td['rowspan']
                    #trow.append([i,j])
                    for a in range(i+1,int(rowspan)+i):
                        trow.append([a,j,textname])
                else:
                    rowspan=1
                
                if ("colspan" in trs):
                    colspan=tr.td['colspan']
                else:
                    colspan=1
                        
                tablestr+="<td >"
                tablestr+=textname
                tablestr+="</td>"
                if int(colspan)>1:
                    for r in range(1,int(colspan)):
                        tablestr+="<td>"
                        tablestr+=textname
                        tablestr+="</td>"
                j+=1
            tablestr+="</tr>"
            i+=1
        tablestr+="</table>"
    return {'content':tablestr,'trow':trow}

def getpricetable2(content,trow,type_id):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
        if table.findAll('tr'):
            tablestr+="<table border=1>"
            i=1
            if type_id==46:
                tablestr+="<tr><td>品名</td><td>最高价</td><td>最低价</td></tr>"
            for row in table.findAll('tr'):
                tablestr+="<tr>"
                j=1
                for tr in row.findAll('td'):
                    textname=tr.text.encode("utf-8")
                    textname=''.join(textname.split())
                    for l in trow:
                        if l[0]==i and l[1]==j:
                            tablestr+="<td >"
                            tablestr+=l[2]
                            tablestr+="</td>"
                    tablestr+="<td >"
                    tablestr+=textname
                    tablestr+="</td>"
                    j+=1
                i+=1
                tablestr+="</tr>"
            tablestr+="</table>"
    return tablestr
def getpricetable3(content,priceid):
    soup = BeautifulSoup(content)
    listall=[]
    tablist=[]
    fieldtrnumexists=0
    fieldtrnumexists22=0
    for table in soup.findAll('table'):
        listfild=[]
        listfildtext=[]
        listfildnum=[]
        trnum=1
        fieldtrnum=1
        for row in table.findAll('tr'):
            fdnum=1
            listtr=[]
            tdnum=1
            fileext=0
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8").upper()
                #print textname
                if (textname in getfildtext()) and fieldtrnumexists==0:
                    fieldtrnum=trnum
                    tdlist={'name':textname,'filed':''}
                    #print  getfildtext()
                    o=''
                    if textname in gettabelfild(1):
                        o='label'
                        tdlist['filed']=o
                       
                    if textname.upper() in gettabelfild(2):
                        o="price"
                        tdlist['filed']=o
                    else:
                        if "价格" in textname:
                            o="price"
                            tdlist['filed']=o
                        
                    if textname in gettabelfild(3):
                        o="area"
                        tdlist['filed']=o
                       
                    if textname in gettabelfild(4):
                        o="spec"
                        tdlist['filed']=o
                    if textname in gettabelfild(5):
                        o="qushi"
                        tdlist['filed']=o
                    if textname in gettabelfild(6):
                        o="unit"
                        tdlist['filed']=o
                    if textname in gettabelfild(7):
                        o="othertext"
                        tdlist['filed']=o
                    #print textname
                    if o:
                        filedname=o
                        listfild.append(o)
                        listfildtext.append(textname)
                        listfildnum.append(tdnum)
                        fileext=1

                else:
                    
                    if trnum>=int(fieldtrnum)+1:
                        if fieldtrnumexists22==0 and listfild:
                            listfild=getfiledtype(listfild,listfild,priceid,listfildtext)
                            fieldtrnumexists22=1
                        if listfild:
                            
                            if tdnum in listfildnum:
                                
                                nnnum=listfildnum.index(tdnum)
                                filed=listfild[nnnum]
                                 
                                if (isfield(filed) and textname!=""):
                                   
                                    tdlist={'name':textname,'filed':filed}
                                    listtr.append(tdlist) 
                tdnum+=1
            if fileext==1:
                fieldtrnumexists=1
            trnum+=1
            if listtr:
                tablist.append(listtr)
        if tablist:
            listall.append(tablist)        
    return listall
def getfiledtype(listfild,reallist,priceid,listfildtext):
    list=[]
    list1=[]
    i=0
    for l in listfild:
        if l in list:
            flen=list1.count(l)
            list.append(l+str(flen))
            listfild[i]=l+str(flen)
            ll=l+str(flen)
        else:
            list.append(l)
            ll=l
        list1.append(l)
        textname=listfildtext[i]
        filedname=ll
        savefiledname(textname,priceid,textname,filedname)
        i+=1
    return list 


def pricetable(priceid):
    sql="select content,tags,title,gmt_created,type_id,assist_type_id,tags from price where id=%s"
    cursor.execute(sql,[priceid])
    alist = cursor.fetchone()
    if alist:
        content=alist[0]
        if content!="":
            title=alist[2]
            gmt_created=alist[3]
            arrdate=title.split("日")
            if len(arrdate)>0:
                pyear=str(gmt_created)[0:4]
                pmonth=arrdate[0].replace("月","-")
                if pmonth in ("12-31","12-30","12-29") and (str(gmt_created)[6:10] in ("1-1","1-2","1-3","1-4","1-5","1-6","1-7","1-8","1-9","1-10")):
                    pyear=str(int(pyear)-1)
                pmonth=pmonth[-5:]    
                postdate=pyear+"-"+pmonth
            type_id=alist[4]
            if "余姚塑料城" in title:
                type_id="324"
            assist_type_id=alist[5]
            tags=alist[6]
            if tags==None:
                tags=""
            typename=gettypename(type_id)+gettypename(assist_type_id)+tags
            list=gettablelist1(content)
            
            content=getpricetable2(list['content'],list['trow'],type_id)
            listall=getpricetable3(content,priceid)
            sqla=""
            for list1 in listall:
                ii=0
                for list in list1:
                    if list:
                        ali='priceid,title,num,postdate,typename,gmt_modified,type_id,assist_type_id'
                        ali_u='postdate=%s,typename=%s,gmt_modified=%s,type_id=%s,assist_type_id=%s'
                        ali1='%s,%s,%s,%s,%s,%s,%s,%s'
                        gmt_modified=datetime.datetime.now()
                        alivalue=[priceid,title,ii,postdate,typename,gmt_modified,type_id,assist_type_id]
                        alivalue_u=[postdate,typename,gmt_modified,type_id,assist_type_id]
                        noinsert=0
                        for l in list:
                            if l:
                                filed=l['filed']
                                if (filed!="0" and filed!=""):
                                    ali+=','+filed
                                    ali_u+=','+filed+'=%s'
                                    ali1+=',%s'
                                    alivalue.append(l['name'].strip())
                                    alivalue_u.append(l['name'].strip())
                                    if l['name'].strip()=="品名":
                                        noinsert=1
                        
                        sqlb="select id from price_list where priceid=%s and num=%s"
                        cursor.execute(sqlb,[priceid,ii])
                        blist = cursor.fetchone()
                        if blist==None:
                            if noinsert==0:
                                sqla="insert into price_list("+str(ali)+") values ("+ali1+")" 
                                cursor.execute(sqla,alivalue)
                                conn.commit()
                        else:
                            sqla="update price_list set "+ali_u+" where id="+str(blist[0])
                            cursor.execute(sqla,alivalue_u)
                            conn.commit()
                        ii+=1
def updatearea(pid,assist_type_id):
    sql="select id from price_list where area is null and priceid=%s"
    cursor.execute(sql,[pid])
    resultlist=cursor.fetchall()
    if resultlist:
        assist_type=gettypenamelabel(assist_type_id)
        for list in resultlist:
            id=list[0]
            sqla="update price_list set area=%s where id=%s"    
            cursor.execute(sqla,[assist_type,id])
            conn.commit()
            print id