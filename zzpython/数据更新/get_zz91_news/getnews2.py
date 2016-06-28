#-*- coding:utf-8 -*-
import re,time,os,sys
from getnews import get_content,hand_content,hand_contentimg,get_url_content,replace_img,replace_img_sex,get_img_url,get_title_url,get_a_url,get_inner_a,get_contentpagenum
from simptools import time1,time2,time3,newspath,remove_script,remove_content_a,savetime,filter_tags
from savetodb import savedb,savedbsex,get_new,get_new2,get_new3,get_new4,get_similaritybody,seoreplace
import bs4
from bs4 import BeautifulSoup
reload(sys)
sys.setdefaultencoding('utf8')

def get_sexlist(typeid,typeid2,url,pagelist,listconf):
    main_url=listconf['main_url']
    re_html=listconf['re_html']
    re_list=listconf['re_list']
    re_time=''
    re_content=listconf['re_content']
    re_hand=[]
    re_encode=listconf['re_encode']
    re_contentpage=listconf['re_contentpage']
    re_contentpagenum=listconf['re_contentpagenum']
    re_contentpagestr=listconf['re_contentpagestr']
    if 'keywords' in listconf:
        keywords=listconf['keywords']
    else:
        keywords=""
    for p in pagelist:
        purl=url.replace("(*)",str(p))
        print purl
        simpget_news(main_url,purl,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand,re_contentpage=re_contentpage,re_encode=re_encode,re_contentpagenum=re_contentpagenum,re_contentpagestr=re_contentpagestr,keywords=keywords)
    
def simpget_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand,re_contentpage="",re_encode="",re_contentpagenum="",re_contentpagestr="",keywords=""):
    title=''
    contents='1'
    htmla=get_url_content(url,re_encode=re_encode)
    urls_pat=re.compile(re_html,re.DOTALL)
    htmla1=re.findall(urls_pat,htmla)
    if htmla1:
        html_a=htmla1[0]
    else:
        html_a=''
    urls_pat=re.compile(re_list,re.DOTALL)
    alist=re.findall(urls_pat,html_a)
    newtime=''
    for als in alist:
        newtimes=savetime
        title=get_inner_a(als)
        if title:
            title=filter_tags(title.replace(' ',''))
        else:
            continue
        print title    
        a_url=get_a_url(als,main_url=main_url)
        if not a_url:
            continue
        print a_url
        result=get_new4(title)
        if result:
            continue
        print 1
        htmls=get_url_content(a_url,re_encode=re_encode)
        contents=get_content(re_content,htmls)
        print contents
        if not contents:
            continue
        print a_url
        if re_contentpage:
            page_html=get_content(re_contentpage,htmls)
            if page_html:
                page_url=get_contentpagenum(a_url,page_html,re_contentpagenum=re_contentpagenum,re_contentpagestr=re_contentpagestr)
                if page_url:
                    for p in page_url:
                        a2=re.sub('.html','_'+str(p)+'.html',a_url)
                        print a2
                        htmls2=get_url_content(a2,re_encode=re_encode)
                        content2=get_content(re_content,htmls2)
                        if content2:
                            contents=contents+content2
        
        contents=hand_content("<span class='total'>(.*?)</span>",contents)
        contents=hand_content('<div class="pageStyle">(.*?)</div>',contents)
        contents=remove_script(contents)
        contents=hand_content('<style.*?</style>',contents)
        contents=contents.replace('两性:<a href="http://sex.fh21.com.cn/" target="_blank">http://sex.fh21.com.cn/</a>','')
        contents=hand_content('<p>　　<strong>小编推荐：</strong></p>.*?</div>',contents)
        contents=hand_content('<p>　　<span style="color: rgb(128, 0, 128);"><span style="font-size: larger;"><strong>猜你喜欢：</strong></span></span></p>.*?</div>',contents)
        img_url=get_img_url(contents)
        if img_url:
            contents=replace_img_sex(main_url,a_url,contents)
        contents=hand_contentimg(contents)
        #continue
        #title=title.encode('utf-8')
        #contents=contents.encode('utf-8')
        savedbsex(typeid,typeid2,main_url,a_url,title,contents,keywords=keywords)

def get_net_news(main_url,url,typeid,typeid2,re_html,re_list,re_time,re_content,re_hand):
    title=''
    contents='1'
    htmla=get_url_content(url)
    if re_time=='huiconga':
        htmla=get_url_content(url,'huiconga')
#    print htmla
    urls_pat=re.compile(re_html,re.DOTALL)
    htmla1=re.findall(urls_pat,htmla)
    if htmla1:
        html_a=htmla1[0]
    else:
        html_a=''
#    print html_a
    urls_pat=re.compile(re_list,re.DOTALL)
    alist=re.findall(urls_pat,html_a)
    newtime=''
    for als in alist:
#        print '-------'
#        print als
        newtimes=''
#        newtime=als.replace(' ','')
        newtime=''.join(als.split())
#        print newtime
        if '21cp.com' in url:
            newtimes=re.findall('<font color="red">([^"]+)</font>',als)
            if newtimes:
                newtimes=newtimes[0]
        if '100ppi.com' in url:
            newtimes=newtime[-22:-17]
        if 'chinapaper.net' in url:
            newtimes=newtime[25:30]
        if 'hc360.com' in url:
            newtimes=newtime[-18:-16]+'-'+newtime[-15:-13]
        if 'steelcn.com' in url:
            newtimes=newtime[8:10]+'-'+newtime[13:15]
        if re_time=='f139a':
            newtimes=newtime[44:49]
        if re_time=='f139b':
#            newtimes=newtime[51:56]
            newtimes=newtime[44:49]
        if 'cs.com.cn' in url:
            newtimes=newtime[20:25]
        if 'metalscrap.com' in url:
            newtimes=newtime[-12:-7]
        if 'esuliao.com' in url:
            newtimes=newtime[45:50]
#瑞达期货的日期格式2014-3-12,其他网站2014-03-12
        if 'rdqh.com' in url:
            newtimes=newtime[-16:-6]
            simptime=newtimes.split('-')
            mothtime=simptime[1]
            if int(mothtime)<10:
                mothtime='0'+mothtime
            daytime=simptime[2]
            if int(daytime)<10:
                daytime='0'+daytime
            newtimes=mothtime + '-' +daytime
        if '1688.com' in url:
            newtimes=newtime[25:30]
        if '96369.net' in url:
            newtimes=newtime[-31:-26]
        if 'usteel.com' in url:
            newtimes=newtime[21:26]
#        print newtimes
#        savetime='04-15'
        if newtimes==savetime:
#            print newtimes
            if '21cp.com' in url:
                als='<a '+als
            title=get_inner_a(als)
            if title:
                title=title.replace(' ','')
            else:
                continue
            if 'rdqh.com' in url:
                title=title.replace('瑞达期货:','')
                title=title.replace('瑞达期货：','')
#            print title
            a_url=get_a_url(als)
#            print a_url
            if not a_url:
                continue
            if url=='http://www.cs.com.cn/qhsc/zzqh/':
                if 'PVC' in title:
                    typeid2=155
                elif 'PTA'.decode('utf8') in title:
                    typeid2=155
                elif '连塑'.decode('utf8') in title:
                    typeid2=155
                elif '橡胶'.decode('utf8') in title:
                    typeid2=156
                elif '铜'.decode('utf8') in title:
                    typeid2=154
                elif '铝'.decode('utf8') in title:
                    typeid2=154
                elif '锌'.decode('utf8') in title:
                    typeid2=154
                elif '期货'.decode('utf8') in title:
                    typeid2=154
                else:
                    continue
            if 'cs.com.cn' in url:
                a_url=url+a_url[2:]
            if 'http://' not in a_url:
                if re_time=='f139b':
                    a_url=main_url+'om'+a_url
                else:
                    a_url=main_url+a_url
                    if 'rdqh.com' in url:
                        a_url=re.findall('.*?html',a_url)[0]
#            print a_url
#            txt='进入重复文章对比...'
#            f=open(newspath+ time2 +'-out.txt','ab')
#            print >>f,txt
#            f.close()
#            print title
            result=get_new(title,typeid)
            if result:
                continue
#            result2=get_new2(title,typeid)
#            if result2:
#                continue
            
            htmls=get_url_content(a_url)
#            print htmls
            contents=get_content(re_content,htmls)
#            print contents
            if not contents:
                continue
                
            utf8_list=[
                       'http://news.steelcn.com',
                       'http://news.usteel.com',
                       'http://www.f139.c',
                       'http://paper.f139.c',
                       'http://www.esuliao.com',
                       'http://www.rdqh.com',
                       'http://www.96369.net',
                       'http://www.100ppi.com/news/',
                       ]
#            if main_url in utf8_list:
#                txt=title+time3+a_url
#            else:
#                txt=title.encode('utf-8')+time3+a_url.encode('utf-8')
#            f=open(newspath+ time2 +'-out.txt','ab')
#            print >>f,txt
#            f.close()
            if 'rdqh.com' in url:
                contents=hand_content('免责声明.*',contents)
                title=re.sub('<span.*?>','',title)
                title=re.sub('</span>','',title)
                contents=re.sub('<span.*?>','',contents)
                contents=re.sub('</span>','',contents)
                contents=re.sub('<FONT.*?>','',contents)
                contents=re.sub('<font.*?>','',contents)
                contents=re.sub('</font>','',contents)
#           print contents
            if 'f139.c' in url:
                contents=hand_content('富宝资讯.*?消息：',contents)
                contents=hand_content('富宝资讯.*?消息:',contents)
                contents=hand_content('\(富宝.*?\)',contents)
                contents=hand_content('富宝资讯.*?消息：',contents)
                if 'f139.c' in url and '<a class="cRed"' in contents:
                    urls_pat=re.compile('<a class="cBlue',re.DOTALL)
                    siteUrls=re.findall(urls_pat,contents)
                    page=range(2,len(siteUrls)+2)
#                        print page
                    for p in page:
                        a2=re.sub('.html','_'+str(p)+'.html',a_url)
                        htmls2=get_url_content(a2)
                        content2=get_content(re_content,htmls2)
                        contents=contents+content2
                    re_py=r'<strong>.*?</strong>.*?<br />'
                    contents=hand_content(re_py,contents)
                    re_py=r'<strong>.*?</strong>'
                    contents=hand_content(re_py,contents)
            
            if 'cs.com.cn' in url:
                if re.findall('<img.*?oldsrc.*?src',contents):
                    contents=contents.replace('oldsrc','zz91')
            if 'metalscrap.com' in url:
                contents=hand_content('</td>',contents)
                contents=hand_content('</tr>',contents)
                contents=hand_content('</table>',contents)
            if 'chinapaper.net' in url:
                contents=hand_content('<p><strong>.*?</strong>',contents)
            if 'steelcn.com' in url:
                contents=hand_content('<div style="margin:auto;">.*?</div>',contents)
                contents=hand_content('资料来源：.*',contents)
                contents=hand_content('</div>',contents)
            if 'hc360.com' in url:
                contents=re.sub(r'<P align=center>.*','',contents)
                contents=re.sub(r'<P style="FONT-SIZE: 12px" align=left>.*','',contents)
                contents=re.sub(r'<P><img src="http://style.org.hc360.com/css_hy/images/end_logo/hc_logo_end.jpg">.*','',contents)
                contents=re.sub(r'<P><p align="left">.*','',contents)
                contents=re.sub(r'<P class="editorN">.*?</P>','',contents)
                contents=re.sub(r'<img src="http://style.org.hc360.com/css_hy/images/end_logo/hc_logo_end.jpg">.*','',contents)
                contents=remove_content_a(contents)
                contents=remove_script(contents)
                contents=re.sub(r'慧聪塑料网讯：'.decode('utf-8'),'',contents)
                contents=re.sub(r'慧聪塑料网讯:'.decode('utf-8'),'',contents)
                if '<IMG' in contents[-100:]:
                    contents=re.sub(r'<P>.*?<IMG.*?>','',contents)
                if '下一页'.decode('utf-8') in contents:
                    
                    continue
#                            num=1
#                            while num<9:
#                                num=num+1
#                                url2=a_url[:-6]+'-'+str(num)+'.shtml'
##                                print url2
#                                htmls2=get_url_content(url2)
##                                print htmls2
#                                contents2=get_content(re_content,htmls2)
#                                if contents2:
##                                    print contents2
#                                    contents=contents+contents2
#                                    contents=re.sub(r'<P align=center>.*','',contents)
#                                    contents=re.sub(r'<P style="FONT-SIZE: 12px" align=left>.*','',contents)
#                                    contents=re.sub(r'<P><img src="http://style.org.hc360.com/css_hy/images/end_logo/hc_logo_end.jpg">.*','',contents)
#                                    contents=re.sub(r'<P><p align="left">.*','',contents)
#                                    contents=re.sub(r'<P class="editorN">.*?</P>','',contents)
##                                    contents=remove_content_a(contents)
##                                    contents=remove_script(contents)
##                                    print url2
#                                    txt=url2
#                                    f=open(newspath+ time2 +'-out.txt','ab')
#                                    print >>f,txt
#                                    f.close()
#                                else:
##                                    print title
##                                    print contents
##                                    print 'break'
#                                    break
            if 'usteel.com' in url:
                contents=re.sub('&amp;ldquo;','',contents)
                contents=re.sub('&ldquo;','',contents)
                contents=re.sub(u'&rdquo;','',contents)
            img_url=get_img_url(contents)
#           print img_url
            if img_url:
                contents=replace_img(main_url,a_url,contents)
            replace_p=[
                       'http://www.96369.net',
                       'http://www.metalscrap.com.cn/chinese/',
                       'http://info.21cp.com',
                       'http://news.plastic.com.cn',
                       'http://www.chinapaper.net',
                       'http://news.usteel.com',
                       ]
            if main_url in replace_p:
                contents=re.sub('<p>','<p class="em2">',contents)
                contents=re.sub('<P>','<P class="em2">',contents)
            if 'esuliao.com' in url:
                contents=re.sub('<div.*?>','<p>',contents)
                contents=re.sub('</div>','</p>',contents)
                contents=re.sub('<DIV.*?>','<P>',contents)
                contents=re.sub('</DIV>','</P>',contents)
            else:
                contents=re.sub('<div.*?>','',contents)
                contents=re.sub('</div>','',contents)
                contents=re.sub('<DIV.*?>','',contents)
                contents=re.sub('</DIV>','',contents)
            contents=remove_content_a(contents)
            contents=remove_script(contents)
            contents=hand_content('<style type="text/css">.*?</style>',contents)
            if '21cp.com' in url:
                contents=re.sub(u'【.*?】','',contents)
#                contents=hand_content('\【<strong>中塑在线讯</strong>\】',contents)
            if main_url in utf8_list:
                title=title
                contents=contents
            else:
                title=title.encode('utf-8')
                contents=contents.encode('utf-8')
#            print title
#            print contents
            if not contents:
                continue
#            result_similaritytitle=get_similaritytitle(title.decode('utf-8'),a_url)
#            if result_similaritytitle:
#                continue
            result_similaritybody=get_similaritybody(contents,a_url)
            if result_similaritybody:
                continue
#            title=seoreplace(title)
#            contents=seoreplace(contents)
#            print contents
#            print title
#            break
            txt=title + '---------'
            f=open(newspath+ time2 +'-out.txt','ab')
            print >>f,txt
            f.close()
            
#            print contents
            savedb(typeid,typeid2,main_url,a_url,title,contents)
            '''
            '''
