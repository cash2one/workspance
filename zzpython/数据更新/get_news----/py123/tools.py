#-*- coding:utf-8 -*- 
import time,re
from getnews import get_img_url,get_html_a,remove_content_a,replace_img,remove_script,hand_content
time3=time.strftime('%m月%d日',time.localtime(time.time()))
time3=re.sub('0','',time3)
time3=re.sub('8','7',time3)

def timestamp_datetime2(value):
    format = '%Y-%m-%d'
    value = time.localtime(value)
    dt = time.strftime(format, value)
    return dt

def huicong_hand():
    re_hand=[]
    re_hand.append(r'上一页.*?下一页')
    re_hand.append(r'<IMG.*?201401021426085045.jpg">')
    re_hand.append(r'<img.*?20120706.jpg">')
    re_hand.append(r'<img.*?hc_logo_end.jpg">')
    re_hand.append(r"<img.*?endpage_logo_app.jpg'/>")
    re_hand.append(r'<span class="relRead">.*?</span>')
    re_hand.append(r'<P class="editorN">.*?</P>')
    re_hand.append(r'<IMG.*?201401151013566076.jpg.*?>')
    re_hand.append(r"<a.*?/www.app.hc360.com/tg/index.html.*?</a>")
    re_hand.append(r"<a.*?itunes.apple.com/cn/app/hui-cong-zi-xun.*?</a>")
    re_hand.append(r"<a href=.*?</a><br/>")
    re_hand.append(r"<A href=.*?</A><br/>")
    return re_hand

def hand_fujian(fujj):
    ff=''
    for f in fujj:
        if '<img' in f or '<IMG' in f:
            ff=re.sub(r'<img.*?>','',f)
            ff=re.sub(r'<IMG.*?>','',f)
        else:
            ff=f
    return ff
    
def hand_content_end(main_url,url,content):
    if 'http://info.glinfo.com' in url:
        fujian=''
        re_py=r'附件：<a.*?href="([^"]+)"'
        urls_pat=re.compile(re_py)
        fujian_url=re.findall(urls_pat,content)
        if fujian_url:
            fujian=fujian_url[0]
    if 'http://www.metalscrap.com.cn/chinese/' in url:
        re_py=r'附件.*?</P>'
        urls_p=re.compile(re_py)
        fujj=re.findall(urls_p,content)
        ff=hand_fujian(fujj)
        content=re.sub(r'附件：.*?</P>','附件：',content)
  
    content=remove_content_a(content)
    if 'http://www.chinascrap.com/' in url and '附件：' in content:
        content='' 
    if 'http://www.metalscrap.com.cn/chinese/' in url and '附件：' in content:
        content=re.sub('附件：',ff,content)
    if 'http://info.glinfo.com' in url and fujian:
        re_py='附件：.*?</p>'
        urls_pat=re.compile(re_py)
        fuj=re.findall(urls_pat,content)
        if fuj:
            jian=fuj[0]
            jian=jian[9:-4]
            content=re.sub(jian,'<a href="'+fujian+'">'+jian+'</a>',content)
    content=remove_script(content)
    img_url=get_img_url(content)
    if img_url:
        content=replace_img(main_url,url,content)
    if content:
        if 'http://info.plas.hc360.com' in url:
            if '慧聪塑料网讯<STRONG>：' in content:
                re_py=r'慧聪塑料网讯<STRONG>：'
                content='<STRONG>'+hand_content(re_py,content)
            re_py=r'慧聪塑料网讯：'
            content=hand_content(re_py,content)
        if '.f139.c' in main_url:
            fubao='富宝资讯'+time3+'消息:'
            urls_pat=re.compile(fubao,re.DOTALL)
            zixun=re.findall(urls_pat,content)
            if zixun:
                content=re.sub(fubao,'',content)
            fubao='富宝资讯'+time3+'消息：'
            urls_pat=re.compile(fubao,re.DOTALL)
            zixun=re.findall(urls_pat,content)
            if zixun:
                content=re.sub(fubao,'',content)
            fubao2='(.富宝.*研究小组.*)'
            urls_pat=re.compile(fubao2,re.DOTALL)
            xiaozu=re.findall(urls_pat,content)
            if xiaozu:
                if '</p>' in xiaozu[0]:
                    content=re.sub(fubao2,'</p>',content)
                else:
                    content=re.sub(fubao2,'',content)
            if '富宝资讯' in content:
                content=re.sub('富宝资讯.*?日消息','',content)
        if 'http://china.worldscrap.com' in url:
            content=content+'</tr>'
            content=content.replace('（worldscrap.com）','')
            content=content.replace('（worldscrap.com','')
            content=content.replace('WH）','')
            content=content.replace('（worldscrap.）','')
            
        hand_all=[]
        hand_all.append(r'<div.*?>')
        hand_all.append(r'</div>')
        hand_all.append(r'<DIV.*?>')
        hand_all.append(r'</DIV>')
        for h in hand_all:
            content=hand_content(h,content)
        if 'http://www.100ppi.com' in url:
            content=re.sub(r'\(文章来源：.*?\)','',content)
            content=re.sub(r'\生意社.*?讯','',content)
        if 'http://www.cs.com.cn' in url:
            content=re.sub(r'\（科创信息.*?\）','',content)
            content=re.sub(r'\(科创信息.*?\)','',content)
            content=re.sub(r'\（注意：.*?\）','',content)
        if '.f139.c' in main_url:
            content=re.sub(r'资料来源.*?</p>','',content)+'</p>'
        return content
    