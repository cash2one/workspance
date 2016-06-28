#-*- coding:utf-8 -*-
import re,time
from bs4 import BeautifulSoup
from conn import savedb,get_new
from getnews import get_urls,get_content,hand_content,get_url_content
time1=time.time()
time2=time.strftime('%Y-%m-%d',time.localtime(time.time()))

def get_net_news(main_url,url,typeid,typeid2,re_title,re_content,re_hand):
    
    urls=get_urls(url,re_title)
    if not urls:
        urls=[]

    double_urls=[
                 'http://cn.reuters.com/',
                 'http://www.ccedip.com/',
                 'http://www.esuliao.com',
                 ]
    if main_url in double_urls:#双链接其中一个
        urls=urls[::2]
    if 'http://www.100ppi.com' in url:
        urls=urls[1::2]
            
    for a in urls[:5]:
#获得新闻标题
        title=a.get_text()
#!抓取标题后去掉空格
        title=title.replace(" ","")
        arg_title=0#赛选含有钢的标题
        if 'http://www.100ppi.com' in url:
            shengyishe='生意社：'.decode('utf-8')
            if shengyishe in title:
                title=title.replace(shengyishe,'')#去掉标题的网站来源
        if 'http://info.glinfo.com' in url:
            steel='钢'.decode('utf-8')
            iron='铁'.decode('utf-8')
            if steel in title or iron in title:
                arg_title=0
            else:
                arg_title=1
        if arg_title==0:
            content=[]
            if main_url=='http://www.chinascrap.com/':
                title=a.get_text()[1:]
            result=get_new(title,typeid)
            if not result:
                a=a.get('href')#获得网站的子连接
                
                if main_url=='http://www.86pla.com':
                    a=str(a)
                son_urls={
                          'http://www.96369.net':'ok',
                          'http://www.100ppi.com/news/':'ok',
                          'http://www.ometal.com':'ok',
                          'http://www.xj91.com.cn/zhuanjia/':'ok',
                          'http://info.glinfo.com':'ok',
                          'http://www.chinapaper.net':'ok',
                          'http://paper.sci99.com/news/':'ok',
                          'http://info.21cp.com':'ok',
                          'http://news.plastic.com.cn':'ok',
                          'http://china.worldscrap.com/modules/cn/paper/':'ok',
                          'http://www.w7000.com':'ok',
    #                      'http://www.crra010.com':'ok',
    #                      'http://cn.reuters.com/':'ok',
                          'http://www.esuliao.com':'ok',
    #                      'http://www.ccedip.com/':'ok',
                          'http://www.zhijinsteel.com':'ok',
                          'http://www.metalscrap.com.cn/chinese/':'ok',
                          'http://www.86pla.com':len(a)>7,
                          'http://www.f139.c':len(a)>20,
                          'http://paper.f139.c':len(a)>20,
                          'http://news.steelcn.com':len(a)>30,
    #                      'http://futures.hexun.com':len(a)>39,
    #                      'http://futures.jrj.com.cn':len(a)>45,
                          'http://www.chinascrap.com/':len(a)<39,
                          'http://info.plas.hc360.com':len(a)>25,
    #                      'http://info.finance.hc360.com/':len(a)>25,
                          'http://www.cs.com.cn':len(a)>10 and len(a)<34,
    #                      'http://www.xkxm.com/AspCodeHtml/News/':len(a)<48
                            }
                if son_urls[main_url]:#根据长度赛选需要的子url
                    if main_url == 'http://www.cs.com.cn':#处理特殊网站链接
                        a=url+a[2:]
                    if main_url not in a and 'http://' not in a:
                        if main_url == 'http://www.f139.c':#处理特殊网站链接
                            a=main_url+'om'+a
                        else:
                            a=main_url+a
                    a=a.encode('utf-8')
                    try:
                        htmls=get_url_content(a)#获得子链接下的html
                    except:
                        txt='invalid link' + a + str(typeid)
                        f=open('print/'+ time2 +'-out.txt','ab')
                        print >>f,txt
                        f.close()
                    try:
                        content=get_content(re_content,htmls)#获得子链接下的新闻内容,下面都是处理特殊网站
                        
                        if not content and '.f139.c' in main_url:
                            content=get_content(r'<div id="main">(.*?)</div>',htmls)#获得子链接下的新闻内容,下面都是处理特殊网站
                        if not content and '.f139.c' in main_url:
                            content=get_content(r'<div class="text f14" id="zhengwen">(.*?)</div>',htmls)#获得子链接下的新闻内容,下面都是处理特殊网站
                        replace_p=[
                                   'http://www.96369.net',
                                   'http://www.metalscrap.com.cn/chinese/',
                                   'http://info.21cp.com',
                                   'http://news.plastic.com.cn',
                                   'http://www.chinapaper.net',
                                   ]
                        if main_url in replace_p:
                            content=re.sub('<p>','<p class="em2">',content)
                            content=re.sub('<P>','<P class="em2">',content)
                            
                        if main_url=='http://info.21cp.com' and not content:
                            re_py=r'<div class="content overflow">(.*?)</div>'
                            content=get_content(re_py,htmls)
    #                    if main_url=='http://www.ccedip.com/':
    #                        content=content[22:49]+content[66:]
    #                    if main_url=='http://www.86pla.com':
    #                        content='&nbsp;&nbsp;&nbsp;&nbsp; '+content[68:]
                        
                        #抓取富宝网多页新闻
                        if '.f139.c' in main_url and '<a class="cRed"' in content:
                            urls_pat=re.compile('<a class="cBlue',re.DOTALL)
                            siteUrls=re.findall(urls_pat,content)
                            page=range(2,len(siteUrls)+2)
    #                        print page
                            for p in page:
                                a2=re.sub('.html','_'+str(p)+'.html',a)
                                htmls2=get_url_content(a2)
                                content2=get_content(re_content,htmls2)
                                content=content+content2
                            re_py=r'<strong>.*?</strong>.*?<br />'
                            content=hand_content(re_py,content)
                            re_py=r'<strong>.*?</strong>'
                            content=hand_content(re_py,content)
    #                    if main_url=='http://cn.reuters.com/':
    #                        if '下一页' in htmls:
    #                            a2=a+'?pageNumber=2&virtualBrandChannel=0'
    #                            htmls2=get_url_content(a2)
    #                            content2=get_content(re_content,htmls2)
    #                            content=content+content2
    #                        re_py=r'(.*?)(完)'
    #                        content2=get_content(re_py,content)
    #                        content=content2[0][:-1]
    #                    if main_url=='http://info.finance.hc360.com/':
    #                        soup=BeautifulSoup(htmls)
    #                        title=soup.h1.get_text()
    #                        result=get_new(title,typeid)
    #                        if '下一页' in htmls:
    #                            a2=re.sub('.shtml','-2.shtml',a)
    #                            htmls2=get_url_content(a2)
    #                            content2=get_content(re_content,htmls2)
    #                            content=content+content2
                    except:
                        txt="content error "  + a + 'typeid: ' + str(typeid)
                        f=open('print/'+ time2 +'-out.txt','ab')
                        print >>f,txt
                        f.close()
                    try:
                        for hand in re_hand:#循环处理匹配re的内容
                            content=hand_content(hand,content)
                        if main_url=='http://futures.hexun.com' and '版权声明' in content:#处理特殊网站
                            content=content[:-484]
                        if main_url=='http://futures.jrj.com.cn':
                            content='<P>&nbsp;&nbsp;&nbsp; '+content
                        if main_url=='http://www.xkxm.com/AspCodeHtml/News/':
                            content=content[:-50]+'</p>'
                            soup=BeautifulSoup(htmls)
                            title=soup.h3.get_text()
                            result=get_new(title,typeid)
                    except:
                        txt="hand content error "  + a
                        f=open('print/'+ time2 +'-out.txt','ab')
                        print >>f,txt
                        f.close()
                    if not result:
                        try:
                            txt=title.encode('utf-8')
                            f=open('print/'+ time2 +'-out.txt','a')
                            print >>f,txt
                            f.close()
                            content=str(content)
                            savedb(typeid,typeid2,main_url,a,title,content)#数据加入数据库+下载图片
                        except:
                            txt="pass " + a + 'typeid: ' + str(typeid)
                            f=open('print/'+ time2 +'-out.txt','ab')
                            print >>f,txt
                            f.close()