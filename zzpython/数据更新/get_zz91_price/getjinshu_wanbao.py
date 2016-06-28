#-*- coding:utf-8 -*-
#----废金属晚报
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_url_content2,get_content,get_inner_a,get_a_url,filter_tags,gettags,hand_content,remove_content_a,remove_script,remove_iframe
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()
timedate=time.strftime('%m月%d日',time.localtime(time.time()))
timedate=timedate.decode('utf-8')
gmt_created=datetime.date.today()
gmt_created2=datetime.datetime.now()
str_time=time.strftime('%m-%d',time.localtime(time.time()))
def getshfeprice():
    p_time=time.strftime('%Y%m%d',time.localtime(time.time()))
    #print p_time
    contents=""
    geturlone='http://www.shfe.com.cn/data/dailydata/'+str(p_time)+'zs.html'
    html=get_url_content(geturlone,arg=1).lower()
    ghtml=get_content('<div style="margin: 0cm 0cm 0pt 4pt; text-indent: 12pt"><span style="font-size: 12pt; color: windowtext">期铜</span></div>.*?<div style="margin: 0cm 0cm 0pt 4pt; text-indent: 12pt"><span style="font-size: 12pt; color: windowtext">期铅</span></div>',html)
    if ghtml:
        contents=ghtml.replace('<div style="margin: 0cm 0cm 0pt 4pt; text-indent: 12pt"><span style="font-size: 12pt; color: windowtext">期铅</span></div>','')
        contents=re.sub(' style=".*?"','',contents)
        contents=re.sub('<span.*?>','',contents)
        contents=re.sub('</span>','',contents)
    hhtml=get_content('<div style="margin: 0cm 0cm 0pt 4pt; text-indent: 12pt"><span style="font-size: 12pt; color: windowtext">螺纹钢</span></div>.*?<div style="margin: 0cm 0cm 0pt 4pt; text-indent: 12pt"><span style="font-size: 12pt; color: windowtext">线材</span></div>',html)
    if ghtml:
        contents=contents+ghtml.replace('<div style="margin: 0cm 0cm 0pt 4pt; text-indent: 12pt"><span style="font-size: 12pt; color: windowtext">线材</span></div>','')
        contents=re.sub(' style=".*?"','',contents)
        contents=re.sub('<span.*?>','',contents)
        contents=re.sub('</span>','',contents)
    return contents
def sinaget():
    geturlone='http://finance.sina.com.cn/stock/index.shtml'
    html_area=get_url_content(geturlone,arg=1).lower()
    html_alist=re.findall('<h2>.*?</h2>',html_area)
    contents=getshfeprice()
    if html_alist:
        for hlts in html_alist:
            hlt=get_content('<a.*>沪指.*?</a>',hlts)
            if hlt:
                mainlist=hlt.split('<a')
                mainlist='<a'+mainlist[-1]
                title=get_inner_a(mainlist)
                a_url=get_a_url(mainlist)
                htmls=get_url_content(a_url,arg=1)
                if htmls:
                    content1=filter_tags(get_content('<p>　　新浪财经讯.*?</p>',htmls))
                    if not content1:
                        content1=""
                    content=get_content('截至收盘，沪指报.*?。',htmls)
                    if not content:
                        content=get_content('截止收盘，沪指报.*?。',htmls)
                    if not content:
                        content=get_content('截至尾盘，沪指报.*?。',htmls)
                    if not content:
                        content=get_content('截至发稿，沪指报.*?。',htmls)
                    if not content:
                        content=get_content('<!-- 原始正文start -->.*?<p>　　<strong>板块方面</strong></p>',htmls)
                    content='<div>'+content1+'</div><div>'+content+'</div>'
                    
                    if content:
                        content=content.replace('<!-- 原始正文start -->','')
                        content=content.replace('<p>　　<strong>板块方面</strong></p>','')
                        content=content.replace('新浪财经讯','')
                        
                        contents=contents+content
                        print contents
    content='<p><span style="color: rgb(0, 0, 255);">一、行情快报</span></p><p>【股市行情】'+contents+'</p>'
    return content

def sinagetone(urlone,numb):
    try:
        html_area=get_url_content(urlone).lower()
    except:
        return ''
    html_list=re.findall('<li>.*?</li>',html_area)
    js=0
    contentall=''
    for als in html_list:
        #print urlone
        newtimes=''
        newtimes=get_content(r"<span>(.*?)</span>",als)
        newtimes=re.findall(r'..月..',str(newtimes))
        if newtimes:
            newtimes=newtimes[0]
            print newtimes
            newtimes=newtimes.replace('月','-')
        if str_time==newtimes:
            title=get_inner_a(als)
            if title:
                title='<p>'+title+'</p>'
            else:
                continue
            a_url=get_a_url(als)
#            print title
            print a_url
            try:
                htmls=get_url_content(a_url,arg=1)
            except:
                continue
            #print htmls
            content=get_content('<!-- 原始正文start -->(.*?)<!-- 原始正文end -->',htmls)
            if content:
                #print content
                content=re.sub('<div.*?>','',content)
                content=re.sub('</div>','',content)
                content=re.sub('<!--.*?-->','',content)
                content=re.sub('<link.*?>','',content)
                content=re.sub('<img.*?>','',content)
                content=re.sub('<span.*?>','',content)
                content=re.sub('</span>','',content)
                content=re.sub('<strong>','',content)
                content=re.sub('</strong>','',content)
                content=re.sub('<p>　　','<p>',content)
                content=remove_script(content)
                content=remove_iframe(content)
                content=remove_content_a(content)
                content=''.join(content.split())
                content=re.sub('<pclass="art_keywords">.*?</p>','',content)
                if len(content)>500 and js<numb:
                    js+=1
                    contentall+=title+content
#    print contentall
    return contentall
    
def sinagetlist():
    guoneititle='<p><span style="color: rgb(0, 0, 255);">五、宏观经济</span></p>'
    guonei=sinagetone('http://roll.finance.sina.com.cn/finance/gncj/hgjj/index.shtml?qq-pf-to=pcqq.c2c',2)
    guojititle='<p><span style="color: rgb(0, 0, 255);">六、全球观察</span></p>'
    guoji1=sinagetone('http://roll.finance.sina.com.cn/finance/gjcj/ozjj/index.shtml',1)
    guoji2=sinagetone('http://roll.finance.sina.com.cn/finance/gjcj/mzjj/index.shtml',1)
    if not guoji2:
        guoji2=sinagetone('http://roll.finance.sina.com.cn/finance/gjcj/yzjj/index.shtml',1)
        if not guoji2:
            guoji1=sinagetone('http://roll.finance.sina.com.cn/finance/gjcj/ozjj/index.shtml',2)
    if not guoji1:
        guoji1=sinagetone('http://roll.finance.sina.com.cn/finance/gjcj/yzjj/index.shtml',1)
        
    endall=''
    if guonei:
        endall+=guoneititle+guonei
    if guoji1:
        endall+=guojititle+guoji1
    if guoji2:
        endall+=guoji2
#    if guoji3:
#        endall+=guoji3
    #print endall
    return endall
    
def hexunget():
    #geturlone='http://futures.hexun.com/'
    geturlone='http://futures.hexun.com/industrynews/'
    html_area=get_url_content(geturlone).lower()
#    print html_area
    hlt=get_content('<div class="temp01">.*?<div class="line3" >',html_area)
    contentall=''
    if hlt:
        hltlist=hlt.split('</li><li>')
        js=0
        for hlist in hltlist:
            #print hlist
            urlli=hlist.split('</span>')
            hlist=urlli[1]
            #hlist=hlist+'</a>'
            title=get_inner_a(hlist)
            a_url=get_a_url(hlist)
            if title and a_url and js<3:
#                print '---------'
                print title
#                print a_url
                a_url=a_url.replace("/p://","http://").replace("file://","")
                print a_url
                htmls=get_url_content(a_url).lower()
                if htmls:
                    title=get_content('<h1>(.*?)</h1>',htmls)
                    if title:
                        title='<p>'+title+'</p>'
                    content=get_content('<div id="artibody" class="art_context">.*?</div>',htmls)
                    if content:
                        content=re.sub('<div.*?>','',content)
                        content=re.sub('<img.*?>','',content)
                        content=re.sub('</div>','',content)
                        content=re.sub('</strong>','',content)
                        content=re.sub('<strong>','',content)
                        content=re.sub('<span.*?>','',content)
                        content=re.sub('<p>.*?记者 .*?</p>','',content)
                        content=re.sub('</span>','',content)
                        content=re.sub('\（责任编辑.*?\）','',content)
                        content=remove_script(content)
                        content=remove_iframe(content)
                        content=remove_content_a(content)
                        content=re.sub('<p>　　','<p>',content)
                        content=''.join(content.split())
                        content=re.sub('<a.*?>','',content)
                        
                        if '<table' in content:
                            continue
                        if len(content)>500:
#                            print title
#                            print content
                            js+=1
                            contentall+=title+content
    if contentall:
        contentall='<p><span style="color: rgb(0, 0, 255);">二、国内焦点</span></p>'+contentall
#    print contentall
    return contentall


def getruida(urlone):
    main_url='http://www.rdqh.com'
    html_area=get_url_content(urlone)
    html_alist=get_content(r'<ul class="contact_list">(.*?)</ul>',html_area)
    #print html_alist
    if html_alist:
        html_hq=''
        html_bj=''
        urls_pat=re.compile(r'<li>.*?</li>',re.DOTALL)
        alist=re.findall(urls_pat,html_alist)
        for als in alist:
            
            arearname=''
    #        print '---------'
            newtimes=''
            newtimes=get_content(r'<em>(.*?)</em>',als)
            newtime=als.replace(' ','')
            if 'rdqh.com' in urlone:
                simptime=newtimes.split('-')
                mothtime=simptime[1]
                if int(mothtime)<10:
                    mothtime='0'+mothtime
                daytime=simptime[2]
                if int(daytime)<10:
                    daytime='0'+daytime
                newtimes=mothtime + '-' +daytime
            #print newtimes
#            str_time='07-03'
            if str_time==newtimes:
    #            print str_time
                title=filter_tags(get_content(r'</em><p>(.*?)</p>',als))
                #print title
                if not title:
                    continue
                a_url=get_a_url(als)
                print a_url
                a_url='http://www.rdqh.com/'+a_url
                if 'rdqh.com' in urlone:
                    title=title.replace('瑞达期货:','')
                    title=title.replace('瑞达期货：','')
                    #a_url=re.findall('.*?html',a_url)[0]
                htmls=get_url_content(a_url)
                #contents=get_content(r'<div style="text-align: right; padding-right: 1em;">(.*?)<div>',htmls)
                
                contents=get_content(r'<div class="work_article1">(.*?)瑞达期货研究院',htmls)
                if not contents:
                    continue
                utf8_list=[
                       'http://news.steelcn.com',
                       'http://www.f139.c',
                       'http://paper.f139.c',
                       'http://www.esuliao.com',
                       'http://www.rdqh.com',
                       'http://www.96369.net',
                       ]
                if 'rdqh.com' in urlone:
                    contents=hand_content('免责声明.*',contents)
                    #title=re.sub('<span.*?>','',title)
                    #title=re.sub('</span>','',title)
                    contents=re.sub('<span.*?>','',contents)
                    contents=re.sub('</span>','',contents)
                    contents=re.sub('<FONT.*?>','',contents)
                    contents=re.sub('<font.*?>','',contents)
                    contents=re.sub('</font>','',contents)
                    contents=re.sub('</div>','',contents)
                    contents=re.sub('<p.*?>','<p>',contents)
                    contents=re.sub('<b.*?>','',contents)
                    contents=re.sub('<b>','',contents)
                    contents=re.sub('</b>','',contents)
                    contents=re.sub('<p>&nbsp;</p>','',contents)
                    contents=re.sub('&nbsp;','',contents)
                    contents=remove_script(contents)
                    contents=remove_iframe(contents)
                    contents=remove_content_a(contents)
                    #contents=re.sub('</p><p></p><p></p><p','',contents)
                    
                if urlone=='http://www.rdqh.com/content/search?act=search&k=title&q=%E9%93%9C':
                    contents1=get_content(r'<p.*?>.*?行情研判：(.*?)</p>',contents)
                    contents=get_content(r'<p.*?>.*?外盘走势：(.*?)<p.*?>.*?市场因素分析：',contents)
                    if contents:
                        contents=re.sub('现货方面：','',contents)
                        contents=re.sub('内盘走势：','',contents)
                        contents='<p>'+contents+'<p>'+contents1+'</p>'
                print title
                if urlone=='http://www.rdqh.com/content/search?act=search&k=title&q=%E9%93%9D':
                    contents1=get_content(r'<p.*?>.*?行情研判：(.*?)</p>',contents)
                    contents=get_content(r'<p.*?>.*?外盘走势：(.*?)<p.*?>.*?市场因素分析：',contents)
                    if contents:
                        contents=re.sub('现货方面：','',contents)
                        contents=re.sub('内盘走势：','',contents)
                        contents='<p>'+contents+'<p>'+contents1+'</p>'
                if urlone=='http://www.rdqh.com/content/search?act=search&k=title&q=%E9%93%85':
                    contents1=get_content(r'<p.*?>.*?行情研判：(.*?)</p>',contents)
                    #contents=get_content(r'<p>外盘方面：(.*?)<p>消息面：',contents)
                    contents=get_content(r'<p.*?>.*?外盘方面：(.*?)行情研判：',contents)
                    #contents=re.sub('现货方面：','',contents)
                    #contents=re.sub('内盘走势：','',contents)
                    #contents=re.sub('内盘方面：','',contents)
                    if not contents1:
                        contents1=""
                    contents='<p>'+contents+'<p>'+contents1+'</p>'
                if urlone=='http://www.rdqh.com/content/search?act=search&k=title&q=%E9%94%8C':
                    
                    contents=re.sub('<p>消息面：.*?</p>','',contents)
                    contents=re.sub('行情研判：','',contents)
                    contents=re.sub('内盘走势：','',contents)
                    contents=re.sub('现货方面：','',contents)
                    contents=contents[:-4]
                if urlone=='http://www.rdqh.com/content/search?act=search&k=title&q=%E9%92%A2':
                    contents=re.sub('<p>（3）消息面：.*?</p>','',contents)
                    contents=re.sub('<p>基本面：</p>','',contents)
                    contents=re.sub('<p>小结：</p>','',contents)
                    contents=re.sub('国内期市：','',contents)
                    contents=contents[:-4]
                if urlone=='http://www.rdqh.com/content/search?act=search&k=title&q=%E9%87%91':
                    if '外盘走势：' in contents:
                        contents1=get_content(r'<p.*?>外盘走势：</p>(.*?)<p.*?>消息面：',contents)
                        contents2=get_content(r'<p.*?>行情研判：</p>(.*?)</p.*?>',contents)
                        if contents1 and contents2:
                            contents=contents1+contents2+'</p>'
                            contents=re.sub('<p.*?>内盘走势：</p>','',contents)
                    else:
                        continue
                if contents:
                    contents=''.join(contents.split())
                    print contents
    #                print '------'
                    return contents

def shanghaijiaoyi():
#    html_area=get_url_content(u'http://124.127.243.216/searchAction.do?queryString=交易综述'.encode('gbk')).lower()
    html_area=get_url_content('http://124.127.243.216/statements/doclist_103_103002003_0_1.htm').lower()
    if html_area:
#        html_area=html_area.decode('gbk').encode('utf-8')
        print html_area
        '''
        html_list=html_area.split('</tr>')
        for als in html_list:
            newtimes=''
            newtime=als.replace(' ','')
            str_time='07-03'
            if str_time in newtime:
#                print '---------'
                a_url=get_a_url(als)
                a_url='http://124.127.243.216/'+a_url
                print a_url
                htmls=get_url_content2(a_url).decode('gbk')
                print htmls
                
                contents=get_content(r'<td  class="hanggao">(.*?)</td>',htmls)
                if contents:
                    print contents
                '''
                
            

def getruidalist():
    ruidaall=''
    tong=getruida('http://www.rdqh.com/content/search?act=search&k=title&q=%E9%93%9C')
    lv=getruida('http://www.rdqh.com/content/search?act=search&k=title&q=%E9%93%9D')
    qian=getruida('http://www.rdqh.com/content/search?act=search&k=title&q=%E9%93%85')
    xin=getruida('http://www.rdqh.com/content/search?act=search&k=title&q=%E9%94%8C')
    jin=getruida('http://www.rdqh.com/content/search?act=search&k=title&q=%E9%87%91')
    gang=getruida('http://www.rdqh.com/content/search?act=search&k=title&q=%E9%92%A2')
    if tong:
        ruidaall+='<p>铜：</p>'+tong
    if lv:
        ruidaall+='<p>铝：</p>'+lv
    if qian:
        ruidaall+='<p>铅：</p>'+qian
    if xin:
        ruidaall+='<p>锌：</p>'+xin
    if jin:
        ruidaall+='<p>黄金：</p>'+jin
    if gang:
        ruidaall+='<p><span style="color: rgb(0, 0, 255);">四、钢材市场</span></p>'+gang
    if ruidaall:
        ruidaall='<p><span style="color: rgb(0, 0, 255);">三、有色金属</span></p>'+ruidaall
    #print ruidaall
    return ruidaall

def getwanbao():
    title_end=timedate+'废金属行情晚报'
    sql2='select id from price where title=%s and gmt_created>=%s'
    cursor.execute(sql2,[title_end,gmt_created])
    result=cursor.fetchone()
    if not result:
        listall=[]
        content1=sinaget()
        if not content1:
            content1='<p>---------新浪沪指报缺内容---------网址: http://finance.sina.com.cn/stock/index.shtml</p>'
        content2=hexunget()
        if not content2:
            content2='<p>---------和讯金属资讯缺内容---------网址: http://futures.hexun.com/</p>'
        content3=getruidalist()
        if not content3:
            content3='<p>---------瑞达金属评论缺内容---------网址: http://www.rdqh.com/pinglun.aspx</p>'
        content4=sinagetlist()
        if not content4:
            content4='<p>---------新浪财经---------网址: http://roll.finance.sina.com.cn/finance/gncj/gncj/index.shtml , http://finance.sina.com.cn/world/</p>'
        contentall=content1+content2+content3+content4
        #    print content3
        #        price_id=result[0]
        #        content_query=filter_tags(contentall)
        #        sql='update price set content=%s,content_query=%s where id=%s'
        #        cursor.execute(sql,[contentall,content_query,price_id])
        #        conn.commit()
        #    else:
        content_query=filter_tags(contentall)
        type_id=32
        assist_type_id=281
        is_checked=0
        is_issue=0
        real_click_number=0
        ip=0
        is_remark=1
        tags='金属,金属行情,金属行情晚报,行情,行情晚报,晚报'
        argument=[title_end,type_id,assist_type_id,contentall,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,is_remark,content_query]
        sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
        cursor.execute(sql,argument)
        conn.commit()
    return 53


if __name__=="__main__":
    #sinagetlist()
    getwanbao()
    conn.close()
#    print sinagetlist()
#    jin=getruida('http://www.rdqh.com/search-%E9%87%91.html')
#    shanghaijiaoyi()
#    getruidalist()
#    getruida('http://www.rdqh.com/search-%E9%93%9C.html')