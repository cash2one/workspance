#-*- coding:utf-8 -*- 
import urllib,urllib2,re,os,random,sys
from simptools import time1,time2,time3,newspath,imgpath
import bs4
from bs4 import BeautifulSoup
reload(sys)
sys.setdefaultencoding('utf8')

def get_url_content(url,arg='',re_encode=''):#突破网站防爬
    if 'f139.c' in url:
        i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                     "Referer": 'http://www.baidu.com'}
        req = urllib2.Request(url, headers=i_headers)
        html=urllib2.urlopen(req).read()
    else:
        try:
            i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                         "Referer": url}
            req = urllib2.Request(url, headers=i_headers)
            html=urllib2.urlopen(req).read()
        except:
            html=None
    if 'hc360.com' in url:
        if arg=='huiconga':
            html=html.decode('gb2312', 'ignore')
        else:
            html=html.decode('gbk', 'ignore')
    if 'chinapaper.net' in url:
        html=html.decode('gbk', 'ignore')
    if 'cs.com.cn' in url:
        if html:
            html=html.decode('gbk', 'ignore')
    if 'metalscrap.com' in url:
        if html:
            html=html.decode('gbk', 'ignore')
    if '1688.com' in url:
        if html:
            html=html.decode('gbk', 'ignore')
    if '21cp.com' in url:
        if html:
            html=html.decode('gbk', 'ignore')
    
    if html:
        html=html.replace("data-original=","src=")
    if re_encode:
        try:
            html=html.decode(re_encode,'ignore')
        except:
            html=html
    return html

def get_content(re_py,html):
    if html:
        urls_pat=re.compile(re_py,re.DOTALL)
        content=re.findall(urls_pat,html)
        for content in content:
            return content
def get_contentpagenum(url,page_html,re_contentpagenum="",re_contentpagestr=""):
    if page_html and re_contentpagenum:
        soup = BeautifulSoup(page_html)
        page_url=soup.findAll('a')
        num=page_url[len(page_url)-re_contentpagenum].text
        if num:
            page_list=range(2,int(num)+1)
            return page_list
    if re_contentpagestr:
        num=get_content(re_contentpagestr,page_html)
        if num:
            page_list=range(2,int(num)+1)
            return page_list
    """
    if "sex.fh21.com.cn" in url:
        soup = BeautifulSoup(page_html)
        page_url=soup.findAll('a')
        page_list=range(2,len(page_url))
        return page_list
    if "hebei.sina.com.cn" in url:
        soup = BeautifulSoup(page_html)
        page_url=soup.findAll('a')
        page_list=range(2,len(page_url)+1)
        return page_list
    if "wap.fh21.com.cn" in url:
        num=get_content("<span class='total'>(.*?)</span>",page_html)
        if num:
            page_list=range(2,int(num)+1)
            return page_list
    if "www.sky6-6.com" in url:
        num=get_content("<li><a>共(.*?)页",page_html)
        if num:
            page_list=range(2,int(num)+1)
            return page_list
    """
def hand_content(re_py,content):
    urls_pat=re.compile(re_py,re.DOTALL)
    e_content=re.findall(urls_pat,content)
    for e_content in e_content:
        content=content.replace(e_content,'')
    return content
def hand_contentimg(content):
    content=content.lower()
    content=content.replace("IMG",'img').replace("SRC",'src')
    re_py1='<img.*?src="([^"]+)"'
    re_py='<img.*?>'
    #content=content.lower()
    urls_pat=re.compile(re_py,re.DOTALL)
    img_urls=re.findall(urls_pat,content)
    
    urls_pat1=re.compile(re_py1,re.DOTALL)
    img_urls1=re.findall(urls_pat1,content)
    if not img_urls1:
        re_py1='<img.*?src=([^"]+) .*?>'
        urls_pat1=re.compile(re_py1,re.DOTALL)
        img_urls1=re.findall(urls_pat1,content)
    if not img_urls1:
        re_py1="<img.*?src='(.*?)'"
        urls_pat1=re.compile(re_py1,re.DOTALL)
        img_urls1=re.findall(urls_pat1,content)
        
    i=0
    
    if not img_urls1:
        urls_pat1=re.compile('<img.*?data-original="([^"]+)"',re.DOTALL)
        img_urls1=re.findall(urls_pat1,content)
    for img_url in img_urls:
        #print img_url
        if i<=len(img_urls1):
            content=content.replace(img_url,'<img src="'+img_urls1[i]+'"><br />')
        i=i+1
    return content

def hand_content2(re_py,content):
    urls_pat=re.compile(re_py,re.DOTALL)
    e_content=re.findall(urls_pat,content)
    for e_content in e_content:
        content=content.replace(e_content,'&nbsp;&nbsp;&nbsp;&nbsp;')
    return content

def get_img_name(img):#获得图片名
    img_name=img[-20:]
    img_name=img_name.replace('/','')
    img_name=img_name.replace('-','')
    return img_name

def get_img_url(html):#获得图片url
    html=html.lower()
    if html:
        html=html.replace("data-original=","src=").replace("IMG",'img').replace("SRC",'src')
        #soup = BeautifulSoup(html)
        #imglist=[]
        #for img_url in soup.findAll('img'):
            #imglist.append(img_url['src'])
        #return imglist
        
        re_py3=r'<img.*?src="(.*?)".*?>'
        urls_pat3=re.compile(re_py3)
        img_url3=re.findall(urls_pat3,html)
        if img_url3:
            return img_url3
        re_py3=r"<img.*?src='(.*?)'.*?>"
        urls_pat3=re.compile(re_py3)
        img_url3=re.findall(urls_pat3,html)
        if img_url3:
            return img_url3
        re_py3=r'<img.*?src=(.*?) .*?>'
        urls_pat3=re.compile(re_py3)
        img_url3=re.findall(urls_pat3,html)
        if img_url3:
            return img_url3

def get_a_url(html,main_url=""):
    html=html.lower()
    re_py=r'<a.*?href="([^"]+)"'
    urls_pat=re.compile(re_py)
    arg_url=re.findall(urls_pat,html)
    re_py1=r'<A.*?href="([^"]+)"'
    urls_pat1=re.compile(re_py1)
    arg_url1=re.findall(urls_pat1,html)
    re_py2=r"<a.*?href='([^\"]+)'"
    urls_pat2=re.compile(re_py2)
    arg_url2=re.findall(urls_pat2,html)
    
    re_py3=r"<a.*?href=([^\"]+) target.*?"
    urls_pat3=re.compile(re_py3)
    arg_url3=re.findall(urls_pat3,html)
    if arg_url:
        if main_url in arg_url[0]:
            return arg_url[0]
        else:
            return main_url+arg_url[0]
    if arg_url1:
        if main_url in arg_url1[0]:
            return arg_url1[0]
        else:
            return main_url+arg_url1[0]
    if arg_url2:
        if main_url in arg_url2[0]:
            return arg_url2[0]
        else:
            return main_url+arg_url2[0]
    if arg_url3:
        if main_url in arg_url3[0]:
            return arg_url3[0]
        else:
            return main_url+arg_url3[0]
    
def get_inner_a(html):
    html=html.lower()
    re_py=r'<a.*?>([^"]+)</a>'
    urls_pat=re.compile(re_py)
    arg_url=re.findall(urls_pat,html)
    re_py2=r'<a.*?>([^"]+)</a>'
    urls_pat2=re.compile(re_py2)
    arg_url2=re.findall(urls_pat2,html)
    if arg_url:
        return arg_url[0]
    if arg_url2:
        return arg_url2[0]

def get_title_url(html):
    html=html.lower()
    re_py=r'<a.*?title="([^"]+)"'
    urls_pat=re.compile(re_py)
    img_url=re.findall(urls_pat,html)
    re_py2=r'<a.*?title="([^"]+)"'
    urls_pat2=re.compile(re_py2)
    img_url2=re.findall(urls_pat2,html)
    if img_url:
        return img_url[0]
    if img_url2:
        return img_url2[0]

def get_local_path(main_url,url):
    imgdir_dic={
                'http://news.steelcn.com':'steelcn/',
                'http://www.zhijinsteel.com':'zhijinsteel/',
                'http://www.96369.net':'96369/',
                'http://www.f139.c':'f139/',
                'http://paper.f139.c':'f139/',
                'http://www.100ppi.com/news/':'100ppi/',
                'http://info.glinfo.com':'glinfo/',
                'http://paper.sci99.com/news/':'sci99/',
                'http://www.xj91.com.cn/zhuanjia/':'xj91/',
                'http://www.ometal.com':'ometal/',
                'http://news.plastic.com.cn':'plastic/',
                'http://www.chinapaper.net':'chinapaper/',
                'http://china.worldscrap.com':'worldscrap/',
                'http://info.plas.hc360.com':'hc360/',
                'http://china.worldscrap.com/modules/cn/paper/':'metalscrap/',
                'http://www.cs.com.cn':'cscom/',
                'http://www.chinascrap.com/':'chinascrap/',
                'http://info.21cp.com':'info21cp/',
                'http://www.esuliao.com':'esuliao/',
                'http://www.metalscrap.com.cn/chinese/':'metalscrapc',
                'http://www.86pla.com':'86pla/',
                'http://www.w7000.com':'w7000/',
                'http://www.rdqh.com':'rdqh/',
                'http://news.usteel.com':'usteel/',
                'http://sex.fh21.com.cn':'fh21/',
                'http://wap.fh21.com.cn':'wapsex/',
                'http://hebei.sina.com.cn':'hebeisina/',
                }
    get_pathes=imgdir_dic[main_url]
    return get_pathes

def replace_relative_img(url,html,main_url=""):#把相对图片地址替换成绝对地址
    net_img_url=[]
    crra010='http://www.crra010.com'
    w7000='http://www.w7000.com'
    xkxm='http://www.xkxm.com'
    cscom='http://www.cs.com.cn'
    ometal='http://www.ometal.com'
    esuliao='http://www.esuliao.com'
    sex='http://www.esuliao.com'
    if  esuliao in url:
        net_img_url=esuliao
    if  ometal in url:
        net_img_url=ometal
    if  crra010 in url:
        net_img_url=crra010
    if w7000 in url:
        net_img_url=w7000
    if xkxm in url:
        net_img_url=xkxm
    if sex in url:
        net_img_url=sex
    if cscom in url:
        net_img_url=str(re.findall(r'.*//.*/.*/.*/.*/',url)[0])
    if main_url:
        net_img_url=main_url
    img_url=get_img_url(html)
    if net_img_url:
        for img_url in img_url:
            if net_img_url not in img_url:#筛选相对地址和绝对地址混搭情况
                img_url2=net_img_url+img_url
                if cscom in url:
                    img_url2=net_img_url+img_url[2:]
                html=re.sub(img_url,img_url2,html)
    return html
    
def replace_img(main_url,url,html):#替换图片url
    html=replace_relative_img(url,html,main_url=main_url)
    img_urls=get_img_url(html)
    get_pathes=get_local_path(main_url,url)
    cms_path2='uploads/uploads/media/img_news/'+str(get_pathes)
    cms_path='uploads/uploads/media/img_news/'+str(get_pathes)+'/'+time2+'/'
    path2=imgpath+cms_path2
    path=imgpath+cms_path
    
    new_path = os.path.join(path2,time2)
    if not os.path.isdir(new_path):
        os.makedirs(new_path)
    
    for img_url in img_urls:
        imgtype=img_url.split('.')
        imgtypes=''
#        print img_url
        if imgtype:
            imgtypes=imgtype[-1]
        if imgtypes:
            img_name=str(random.randint(000000000,999999999))+'.'+imgtypes
        else:
            img_name=str(random.randint(000000000,999999999))+'.jpg'
#        print img_name
        file_name=os.path.join(path,img_name)
        try:
            urllib.urlretrieve(img_url,file_name)#图片下载
#            print 'img ok'
        except:
            return ''
        txt="img download ok" + time3
        f=open(newspath+ time2 +'-out.txt','ab')
        print >>f,txt
        f.close()
        img_path='/'+cms_path+img_name#本地图片路径
#        if img_url and len(img_url)<200:
        html=re.sub(img_url,img_path,html)#替换html的图片src为本地路径
    return html
def replace_img_sex(main_url,url,html):#替换图片url
    #html=replace_relative_img(url,html,main_url=main_url)
    img_urls=get_img_url(html)
    #get_pathes=get_local_path(main_url,url)
    #cms_path2='uploads/uploads/media/img_news/'+str(get_pathes)
    cms_path2="uploads/zjfriend/"
    #cms_path='uploads/uploads/media/img_news/'+str(get_pathes)+'/'+time2+'/'
    cms_path='uploads/zjfriend/'+time2+'/'
    path2=imgpath+cms_path2
    path=imgpath+cms_path
    
    new_path = os.path.join(path2,time2)
    if not os.path.isdir(new_path):
        os.makedirs(new_path)
    
    #soup = BeautifulSoup(html)
    #for img_url in soup.findAll('img'):
    for img_url in img_urls:
        #img_url=img_url['src']
        
        if main_url in img_url:
            img_url=img_url
        else:
            if "http://" in img_url:
                img_url=img_url
            else:
                img_url=main_url+img_url
        imgtype=img_url.split('.')
        imgtypes=''
        print "开始抓取"+img_url
        if imgtype:
            imgtypes=imgtype[-1]
        if imgtypes:
            img_name=str(random.randint(000000000,999999999))+'.'+imgtypes
        else:
            img_name=str(random.randint(000000000,999999999))+'.jpg'
#        print img_name
        file_name=os.path.join(path,img_name)
        try:
            i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                     "Referer": main_url}
            req = urllib2.Request(img_url, headers=i_headers)
            data=urllib2.urlopen(req).read()
            open(file_name, "wb").write(data)
            #urllib.urlretrieve(img_url,file_name)#图片下载
        except:
            return ''
        txt="img download ok" + time3
        print txt
        img_path='http://img.zjfriend.com/'+cms_path+img_name#本地图片路径
        html=html.encode('utf-8')
        html=html.replace(img_url,img_path)
#        if img_url and len(img_url)<200:
        #html=re.sub(img_url,img_path,html)#替换html的图片src为本地路径
    return html

def get_html_script(html):#移除script
    if '<script' in html:
        re_py=r'<script.*?</script>'
        urls_pat=re.compile(re_py,re.DOTALL)
        html_script=re.findall(urls_pat,html)
        return html_script

def get_html_a(html):
    if '<a' in html:
        re_py=r'<a.*?>'
        urls_pat=re.compile(re_py,re.DOTALL)
        html_a=re.findall(urls_pat,html)
        return html_a
   
def strcmp(s, t):
    if len(s) > len(t):
        s, t = t, s
    #第一步
    n = len(s)
    m = len(t)
    if not m : return n
    if not n : return m
    #第二步
    v0 = [ i for i in range(0, m+1) ]
    v1 = [ 0 ] * (m+1)
    #第三步
    cost = 0
    for i in range(1, n+1):
        v1[0] = i
        for j in range(1, m+1):
            #第四步,五步
            if s[i-1] == t[j-1]:
                cost = 0
            else:
                cost = 1
            #第六步
            a = v0[j] + 1
            b = v1[j-1] + 1
            c = v0[j-1] + cost
            v1[j] = min(a, b, c)
        v0 = v1[:]
    #第七步
    return v1[m]
def getsimilarity(s,t):
    count=float(strcmp(s,t))
    if len(s)<len(t):
        return ((len(t)-count)/len(t))*100
    else:
        return ((len(s)-count)/len(s))*100