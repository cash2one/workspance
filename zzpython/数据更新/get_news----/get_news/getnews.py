#-*- coding:utf-8 -*- 
from bs4 import BeautifulSoup
import urllib,urllib2,re,os,chardet,time
time2=time.strftime('%Y-%m-%d',time.localtime(time.time()))
time3=time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))

def get_url_content(url):#突破网站防爬
    i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                 "Referer": 'http://www.baidu.com'}
    try:
        req = urllib2.Request(url, headers=i_headers)
        html=urllib2.urlopen(req).read()
    except:
        try:
            html=urllib2.urlopen(url).read()
        except:
            txt="url can't open "  + url
            f=open('print/'+ time2 +'-out.txt','ab')
            print >>f,txt
            f.close()
            html=''
            return html

    try:
        encoding=chardet.detect(html)['encoding']
    except:
        encoding=''
    if encoding=='gb2312' or encoding=='gbk' or encoding=='GB2312':
        try:
            html=html.decode('gbk').encode('utf-8')
        except:
            html=html.decode('gb2312').encode('utf-8')
    return html

def get_urls(url,re_py):#获得a链接列表
    html=get_url_content(url)
    urls_pat=re.compile(re_py,re.DOTALL)
    siteUrls=re.findall(urls_pat,html)
    for a in siteUrls:
        soup=BeautifulSoup(a)
        urls=soup.find_all('a')
        return urls

def get_content(re_py,html):
    urls_pat=re.compile(re_py,re.DOTALL)
    content=re.findall(urls_pat,html)
    for content in content:
        return content
    
def hand_content(re_py,content):
    urls_pat=re.compile(re_py,re.DOTALL)
    e_content=re.findall(urls_pat,content)
    for e_content in e_content:
        content=content.replace(e_content,'')
    return content

def hand_content2(re_py,content):
    urls_pat=re.compile(re_py,re.DOTALL)
    e_content=re.findall(urls_pat,content)
    for e_content in e_content:
        content=content.replace(e_content,'&nbsp;&nbsp;&nbsp;&nbsp;')
    return content

def get_soup(url):
    html=urllib2.urlopen(url).read()
    soup=BeautifulSoup(html)
    return soup

def get_soup2(re_py,html):
    urls_pat=re.compile(re_py,re.DOTALL)
    siteUrls=re.findall(urls_pat,html)
    for content in siteUrls:
        soup=BeautifulSoup(content)
        return soup
    
def get_img_name(img):#获得图片名
    img_name=img[-20:]
    img_name=img_name.replace('/','')
    img_name=img_name.replace('-','')
    return img_name

def get_img_url(html):#获得图片url
    re_py=r'<img.*?src="([^"]+)"'
    urls_pat=re.compile(re_py)
    img_url=re.findall(urls_pat,html)
    re_py2=r'<IMG.*?src="([^"]+)"'
    urls_pat2=re.compile(re_py2)
    img_url2=re.findall(urls_pat2,html)
    if img_url:
        return img_url
    if img_url2:
        return img_url2

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
                'http://www.w7000.com':'w7000/'
                }
    get_pathes=imgdir_dic[main_url]
    return get_pathes

def replace_relative_img(url,html):#把相对图片地址替换成绝对地址
    net_img_url=[]
    crra010='http://www.crra010.com'
    w7000='http://www.w7000.com'
    xkxm='http://www.xkxm.com'
    cscom='http://www.cs.com.cn'
    ometal='http://www.ometal.com'
    if  ometal in url:
        net_img_url=ometal
    if  crra010 in url:
        net_img_url=crra010
    if w7000 in url:
        net_img_url=w7000
    if xkxm in url:
        net_img_url=xkxm
    if cscom in url:
        net_img_url=str(re.findall(r'.*//.*/.*/.*/.*/',url)[0])
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
    html=replace_relative_img(url,html)
    img_urls=get_img_url(html)
    get_pathes=get_local_path(main_url,url)
    cms_path2='uploads/uploads/media/img_news/'+str(get_pathes)
    cms_path='uploads/uploads/media/img_news/'+str(get_pathes)+time2+'/'
    path2='/var/www/zz91news/'+cms_path2
    path='/var/www/zz91news/'+cms_path
    
    new_path = os.path.join(path2,time2)
    if not os.path.isdir(new_path):
        os.makedirs(new_path)
    
    for img_url in img_urls:
        img_name=get_img_name(img_url)
        file_name=os.path.join(path,img_name)
        try:
           urllib.urlretrieve(img_url,file_name)#图片下载
           print "img download ok"
           txt="img download ok" + time3
           f=open('print/'+ time2 +'-out.txt','ab')
           print >>f,txt
           f.close()
        except:
            txt="img download arror" + url + time3
            f=open('print/'+ time2 +'-out.txt','ab')
            print >>f,txt
            f.close()
            html=''
        if html:
            img_path='/'+cms_path+img_name#本地图片路径
            if (img_url and img_url!="" and img_path and len(img_url)<200):
                html=re.sub(img_url,img_path,html)#替换html的图片src为本地路径
    return html

def get_html_script(html):#移除script
    if '<script' in html:
        re_py=r'<script.*?</script>'
        urls_pat=re.compile(re_py,re.DOTALL)
        html_script=re.findall(urls_pat,html)
        return html_script
    
def remove_script(html):#移除script
    if '<script' in html:
        re_py=r'<script.*?</script>'
        urls_pat=re.compile(re_py,re.DOTALL)
        img_url=re.findall(urls_pat,html)
        for img_url in img_url:
            html=html.replace(img_url,'')
    return html

def get_html_a(html):
    if '<a' in html:
        re_py=r'<a.*?>'
        urls_pat=re.compile(re_py,re.DOTALL)
        html_a=re.findall(urls_pat,html)
        return html_a

def remove_content_a(html):#移除a链接
    html=re.sub('<A.*?>','',html)
    html=re.sub('</A>','',html)
    html=re.sub('<a.*?>','',html)
    html=re.sub('</a>','',html)
    return html
   
#def remove_content_a(html):#移除a链接
#    if '<A' in html:
#        html=html.replace('<A','<a')
#        html=html.replace('</A>','</a>')
#    html_a=get_html_a(html)
#    if html_a:
#        for html_a in html_a:
#            html=html.replace(html_a,'')
#            html=html.replace('</a>','')
#            html=html.replace('</A>','')
#    return html
