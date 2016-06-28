#-*- coding:utf-8 -*-
#----抓取谷瀑新闻函数
from public import *
import re,time,os,urllib,urllib2,os,random
from zz91conn import database_huanbao
from zz91tools import get_url_content,get_lexicon,gettags,getpastoneday,int_to_datetime,getToday
from zz91settings import imgpath,newspath,pyuploadpath,huanbaopyimgurl,huanbaopyuploadpath
conn=database_huanbao()
cursor = conn.cursor()
time3=time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
time2=time.strftime('%Y-%m-%d',time.localtime(time.time()))
time1=int(time.time())

outcountrylist=["国外","蒙古","朝鲜","韩国","日本","菲律宾","越南","老挝","柬埔寨","缅甸","泰国","马来西亚","文莱","新加坡","印度尼西亚"," 东帝汶","尼泊尔","不丹","孟加拉国","印度","巴基斯坦","斯里兰卡","马尔代夫","哈萨克斯坦","吉尔吉斯斯坦","塔吉克斯坦","乌兹别克斯坦","土库曼斯坦","阿富汗","伊拉克","伊朗","叙利亚","约旦","黎巴嫩","以色列","巴勒斯坦","沙特阿拉伯","巴林","卡塔尔","科威特","阿拉伯联合酋长国（阿联酋）","阿曼","也门","格鲁吉亚","亚美尼亚","阿塞拜疆","土耳其","塞浦路斯","芬兰","瑞典","挪威","冰岛","丹麦","爱沙尼亚","拉脱维亚","立陶宛","白俄罗斯","俄罗斯","乌克兰","摩尔多瓦","波兰","捷克","斯洛伐克","匈牙利","德国","奥地利","瑞士","列支敦士登","英国","爱尔兰","荷兰","比利时","卢森堡","法国","摩纳哥","罗马尼亚","保加利亚","塞尔维亚","马其顿","阿尔巴尼亚","希腊","斯洛文尼亚","克罗地亚","波斯尼亚和墨塞哥维那","乍得","中非","喀麦隆","赤道几内亚","加蓬","刚果","刚果共和国（即：刚果（布））","刚果民主共和国（即：刚果（金））","圣多美及普林西比","毛里塔尼亚","西撒哈拉（注：未独立，详细请看：）","塞内加尔","冈比亚","马里","布基纳法索","几内亚","几内亚比绍","佛得角","塞拉利昂","利比里亚","科特迪瓦","加纳","多哥","贝宁","尼日尔","加那利群岛（西）","赞比亚","安哥拉","津巴布韦","马拉维","莫桑比克","博茨瓦纳","纳米比亚","南非","斯威士兰","莱索托","马达加斯加","科摩罗","毛里求斯","留尼旺（法）","圣赫勒拿（英）","澳大利亚","新西兰","巴布亚新几内亚","所罗门群岛","瓦努阿图","密克罗尼西亚","马绍尔群岛","帕劳","瑙鲁","基里巴斯","图瓦卢","萨摩亚","斐济群岛","汤加","库克群岛（新）","关岛（美）","新喀里多尼亚（法）","法属波利尼西亚","皮特凯恩岛（英）","瓦利斯与富图纳（法）","纽埃（新）","托克劳（新）","美属萨摩亚","北马里亚纳（美）","加拿大","美国","墨西哥","格陵兰","危地马拉","伯利兹","萨尔瓦多","洪都拉斯","尼加拉瓜","哥斯达黎加","巴拿马","巴哈马","古巴","牙买加","海地","多米尼加共和国","安提瓜和巴布达","圣基茨和尼维斯","多米尼克","圣卢西亚","圣文森特和格林纳丁斯","格林纳达","巴巴多斯","特立尼达和多巴哥","波多黎各（美）","英属维尔京群岛","美属维尔京群岛","安圭拉（英）","蒙特塞拉特（英）","瓜德罗普（法）","马提尼克（法）","荷属安的列斯","阿鲁巴（荷）","特克斯和凯科斯群岛（英）","开曼群岛（英）","百慕大（英）","哥伦比亚","委内瑞拉","圭亚那","法属圭亚那","苏里南","厄瓜多尔","秘鲁","玻利维亚","巴西","智利","阿根廷","乌拉圭","巴拉圭"]
sityname=["A","阿坝","阿拉善","阿里","安康","安庆","鞍山","安顺","安阳","澳门","B","北京","白银","保定","宝鸡","保山","包头","巴中","北海","蚌埠","本溪","毕节","滨州","百色","亳州","C","重庆","成都","长沙","长春","沧州","常德","昌都","长治","常州","巢湖","潮州","承德","郴州","赤峰","池州","崇左","楚雄","滁州","朝阳","D","大连","东莞","大理","丹东","大庆","大同","大兴安岭","德宏","德阳","德州","定西","迪庆","东营","E","鄂尔多斯","恩施","鄂州","F","福州","防城港","佛山","抚顺","抚州","阜新","阜阳","G","广州","桂林","贵阳","甘南","赣州","甘孜","广安","广元","贵港","果洛","H","杭州","哈尔滨","合肥","海口","呼和浩特","海北","海东","海南","海西","邯郸","汉中","鹤壁","河池","鹤岗","黑河","衡水","衡阳","河源","贺州","红河","淮安","淮北","怀化","淮南","黄冈","黄南","黄山","黄石","惠州","葫芦岛","呼伦贝尔","湖州","菏泽","J","济南","佳木斯","吉安","江门","焦作","嘉兴","嘉峪关","揭阳","吉林","金昌","晋城","景德镇","荆门","荆州","金华","济宁","晋中","锦州","九江","酒泉","K","昆明","开封","L","兰州","拉萨","来宾","莱芜","廊坊","乐山","凉山","连云港","聊城","辽阳","辽源","丽江","临沧","临汾","临夏","临沂","林芝","丽水","六安","六盘水","柳州","陇南","龙岩","娄底","漯河","洛阳","泸州","吕梁","M","马鞍山","茂名","眉山","梅州","绵阳","牡丹江","N","南京","南昌","南宁","宁波","南充","南平","南通","南阳","那曲","内江","宁德","怒江","P","盘锦","攀枝花","平顶山","平凉","萍乡","莆田","濮阳","Q","青岛","黔东南","黔南","黔西南","庆阳","清远","秦皇岛","钦州","齐齐哈尔","泉州","曲靖","衢州","R","日喀则","日照","S","上海","深圳","苏州","沈阳","石家庄","三门峡","三明","三亚","商洛","商丘","上饶","山南","汕头","汕尾","韶关","绍兴","邵阳","十堰","朔州","四平","绥化","遂宁","随州","宿迁","宿州","T","天津","太原","泰安","泰州","台州","唐山","天水","铁岭","铜川","通化","通辽","铜陵","铜仁","台湾","W","武汉","乌鲁木齐","无锡","威海","潍坊","文山","温州","乌海","芜湖","乌兰察布","武威","梧州","X","厦门","西安","西宁","襄樊","湘潭","湘西","咸宁","咸阳","孝感","邢台","新乡","信阳","新余","忻州","西双版纳","宣城","许昌","徐州","香港","锡林郭勒","兴安","Y","银川","雅安","延安","延边","盐城","阳江","阳泉","扬州","烟台","宜宾","宜昌","宜春","营口","益阳","永州","岳阳","榆林","运城","云浮","玉树","玉溪","玉林","Z","杂多县","赞皇县","枣强县","枣阳市","枣庄","泽库县","增城市","曾都区","泽普县","泽州县","札达县","扎赉特旗","扎兰屯市","扎鲁特旗","扎囊县","张北县","张店区","章贡区","张家港","张家界","张家口","漳平市","漳浦县","章丘市","樟树市","张湾区","彰武县","漳县","张掖","漳州","长子县","湛河区","湛江","站前区","沾益县","诏安县","召陵区","昭平县","肇庆","昭通","赵县","昭阳区","招远市","肇源县","肇州县","柞水县","柘城县","浙江","镇安县","振安区","镇巴县","正安县","正定县","正定新区","正蓝旗","正宁县","蒸湘区","正镶白旗","正阳县","郑州","镇海区","镇江","浈江区","镇康县","镇赉县","镇平县","振兴区","镇雄县","镇原县","志丹县","治多县","芝罘区","枝江市","芷江侗族自治县","织金县","中方县","中江县","钟楼区","中牟县","中宁县","中山","中山区","钟山区","钟山县","中卫","钟祥市","中阳县","中原区","周村区","周口","周宁县","舟曲县","舟山","周至县","庄河市","诸城市","珠海","珠晖区","诸暨市","驻马店","准格尔旗","涿鹿县","卓尼","涿州市","卓资县","珠山区","竹山县","竹溪县","株洲","株洲县","淄博","子长县","淄川区","自贡","秭归县","紫金县","自流井区","资溪县","资兴市","资阳"]


def get_net_news(main_url,url,re_html,re_list,re_time,re_content,re_hand):
    title=''
    contents='1'
    htmla=get_url_content(url)
#    print htmla
    urls_pat=re.compile(re_html,re.DOTALL)
    htmla1=re.findall(urls_pat,htmla)
    if htmla1:
        html_a=htmla1[0]
    else:
        html_a=''
#    print html_a
    urls_pat=re.compile(re_list,re.DOTALL)
    alist=re.findall(urls_pat,htmla)
    newtime=''
    for als in alist:
        try:
        #        print '-------'
        #        print als
            newtimes=''
            newtime=als.replace(' ','')
            if 'goepe.com' in url:
                newtimes=newtime[-33:-28]
    #        print newtimes
    #            savetime='06-25'
            savetime=time.strftime('%m-%d',time.localtime(time.time()))
            if newtimes==savetime:
    #            print newtimes
                title=get_inner_a(als)
    #            if title:
    #                title=title.replace(' ','')
                if not title:
                    continue
    #            print title
                if not url=='http://www.goepe.com/news/search.php?fenlei=30':
                    title=seoreplace(title.encode('utf-8')).decode('utf-8')
                result=get_new(title)
                if result:
                    continue
                a_url=get_a_url(als)
                if not a_url:
                    continue
                if 'http://' not in a_url:
                    a_url=main_url+a_url
                sql="select id from zhuaqu_url where url=%s"
                cursor.execute(sql,[a_url])
                resultlist=cursor.fetchone()
                if resultlist:
                    continue
                else:
                    sql="insert into zhuaqu_url(url) values(%s)"
                    cursor.execute(sql,[a_url])
                    conn.commit()
    #            print a_url
                htmls=get_url_content(a_url)
    #            print htmls
                contents=get_content(re_content,htmls)
    #            print contents
                
                if not contents:
                    continue
                    
                img_url=get_img_url(contents)
    #           print img_url
                if img_url:
    #                print title
                    contents=replace_img(main_url,a_url,contents)
                    img_url2=get_img_url2(contents)
                    for im2 in img_url2:
                        contents=re.sub(im2,'<center>'+im2+'</center>',contents)
    #                        img_url2=img_url2[0]
                replace_p=[
                           'http://www.chinapaper.net',
                           ]
                if main_url in replace_p:
                    contents=re.sub('<p>','<p class="em2">',contents)
                    contents=re.sub('<P>','<P class="em2">',contents)
                contents=remove_content_a(contents)
                contents=remove_script(contents)
                contents=hand_content('<style type="text/css">.*?</style>',contents)
                if 'goepe.com' in url:
                    zhaiyao=re.findall("<div class='zaiyao'>.*?</div>",htmls)
                    if zhaiyao:
                        zhaiyao=zhaiyao[0]
                        zhaiyao=re.sub('<div.*?>','',zhaiyao)
                        zhaiyao=re.sub('</div>','',zhaiyao)
                        if is_chinese(zhaiyao[-1:])==True:
                            zhaiyao2=re.findall(zhaiyao+u'.*?。',htmls)
                            if zhaiyao2:
                                zhaiyao=zhaiyao2[0]
                            else:
                                zhaiyao=zhaiyao+u'。'
                    laiyuan=re.findall(u'来源:.*?</div>',htmls)
                    if laiyuan:
                        laiyuan=laiyuan[0]
                        laiyuan=re.sub(u'来源:','',laiyuan)
                        laiyuan=re.sub('</div>','',laiyuan)
    #                contents=contents[:50].replace(' ','')+contents[50:]
                    contents=re.sub('<span.*?>','',contents)
                    contents=re.sub('</span>','',contents)
                    contents=re.sub('<div.*?>','',contents)
                    contents=re.sub('</div>','',contents)
                    contents=re.sub(u'谷瀑环保编后语：.*','',contents)
#                    contents=''.join(contents.split())
                    contents=u'<font face="arial" size="2">'+contents+u'</font>'
    
    #            print title
    #            print zhaiyao
    #            print laiyuan
                news_code=0
                if url=='http://www.goepe.com/news/search.php?fenlei=10':
                    for clist in outcountrylist:
                        if clist in title or len(contents.split(clist))>3 or re.match('^[a-zA-Z]+$',laiyuan):
                            news_code=100010001001
                            break
                        else:
                            news_code=100010001000
                elif url=='http://www.goepe.com/news/search.php?fenlei=30':
                    for sname in sityname:
                        if len(contents.split(sname))>3 or sname in title:
                            news_code=100010011001
                            break
                        else:
                            news_code=100010011000
                elif url=='http://www.goepe.com/news/search.php?fenlei=50':
                    if  u'水处理' in title:
                        news_code=100010021000
                    elif  u'空气处理' in title:
                        news_code=100010021001
                    else:
                        #热门产品
                        news_code=100010021003
    #            print news_code
    #            print zhaiyao
    #            print contents
                if not contents:
                    continue
                result_title=get_similaritytitle(title,a_url)
                if result_title:
                    continue
                result_zhaiyao=get_similarityzhaiyao(zhaiyao,a_url)
                if result_zhaiyao:
                    continue
                
    #            contents=seoreplace(contents)
    #            print title
    #            print contents
    #                txt=title + '---------'
    #                f=open(newspath+ time2 +'-out.txt','ab')
    #                print >>f,txt
    #                f.close()
    #            try:
                savedb(news_code,title,zhaiyao,contents,laiyuan)
        except:
#            print e
            txt=title + u'---------error'
            f=open(newspath+ time2 +'-huanbao.txt','ab')
            print >>f,txt
            f.close()

def is_chinese(uchar):
        if uchar >= u'\u4e00' and uchar<=u'\u9fa5':
            return True
        else:
            return False

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

def get_img_name(img):#获得图片名
    img_name=img[-20:]
    img_name=img_name.replace('/','')
    img_name=img_name.replace('-','')
    return img_name

def get_img_url(html):#获得图片url
    if html:
        re_pya=r'<img.*?>'
        urls_pata=re.compile(re_pya)
        htmls=re.findall(urls_pata,html)
        if not htmls:
            re_pyb=r'<IMG.*?>'
            urls_patb=re.compile(re_pyb)
            htmls=re.findall(urls_patb,html)
        if htmls:
            htmls=htmls[0]
            re_py=r'<img.*?src="([^"]+)"'
            urls_pat=re.compile(re_py)
            img_url=re.findall(urls_pat,htmls)
            if img_url:
                return img_url
    #        re_py=r'<img.*?src="([^"]+)">'
    #        urls_pat=re.compile(re_py)
    #        img_url=re.findall(urls_pat,htmls)
    #        if img_url:
    #            return img_url
            re_py2=r'<IMG.*?src="([^"]+)"'
            urls_pat2=re.compile(re_py2)
            img_url2=re.findall(urls_pat2,htmls)
            if img_url2:
                return img_url2
            re_py3=r'<img.*?src=([^"]+)'
            urls_pat3=re.compile(re_py3)
            img_url3=re.findall(urls_pat3,htmls)
            if img_url3:
                return img_url3
            re_py3=r'<IMG.*?src=([^"]+)'
            urls_pat3=re.compile(re_py3)
            img_url3=re.findall(urls_pat3,htmls)
            if img_url3:
                return img_url3

def get_img_url2(html):
    re_pya=r'<img.*?>'
    urls_pata=re.compile(re_pya)
    htmls=re.findall(urls_pata,html)
    if not htmls:
        re_pyb=r'<IMG.*?>'
        urls_patb=re.compile(re_pyb)
        htmls=re.findall(urls_patb,html)
    if htmls: 
        return htmls
    else:
        return []
        

def get_a_url(html):
    re_py=r'<a.*?href="([^"]+)"'
    urls_pat=re.compile(re_py)
    arg_url=re.findall(urls_pat,html)
    re_py2=r'<A.*?href="([^"]+)"'
    urls_pat2=re.compile(re_py2)
    arg_url2=re.findall(urls_pat2,html)
    re_py2=r"<a.*?href='([^\"]+)'"
    urls_pat2=re.compile(re_py2)
    arg_url2=re.findall(urls_pat2,html)
    if arg_url:
        return arg_url[0]
    if arg_url2:
        return arg_url2[0]
    
def get_inner_a(html):
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
                'http://www.goepe.com/news/':'goepe',
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
    esuliao='http://www.esuliao.com'
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
#----下载替换图片
def replace_img(main_url,url,html):#替换图片url
    html=replace_relative_img(url,html)
    img_urls=get_img_url(html)
    get_pathes=get_local_path(main_url,url)
    path=huanbaopyuploadpath+'img_news/'+str(get_pathes)+'/'+time2+'/'
    
#    print main_url
#    print path
#    new_path = os.path.join(path,time2)
    if not os.path.isdir(path):
        os.makedirs(path)
    
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
#        try:
        urllib.urlretrieve(img_url,file_name)#图片下载
#            print 'img ok'
#        except:
#            return ''
        txt="img download ok" + time3
        f=open(newspath+ time2 +'-out.txt','ab')
        print >>f,txt
        f.close()
        img_path=huanbaopyimgurl+'img_news/'+str(get_pathes)+'/'+time2+'/'+img_name#本地图片路径
#        if img_url and len(img_url)<200:
        html=re.sub(img_url,img_path,html)#替换html的图片src为本地路径
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

def remove_script(html):#移除script
    if '<script' in html:
        re_py=r'<script.*?</script>'
        urls_pat=re.compile(re_py,re.DOTALL)
        img_url=re.findall(urls_pat,html)
        for img_url in img_url:
            html=html.replace(img_url,'')
    return html

def remove_content_a(html):#移除a链接
    html=re.sub('<A.*?>','',html)
    html=re.sub('</A>','',html)
    html=re.sub('<a.*?>','',html)
    html=re.sub('</a>','',html)
    return html

def remove_content_div(html):#移除a链接
    html=re.sub('<DIV.*?>','',html)
    html=re.sub('</DIV>','',html)
    html=re.sub('<div.*?>','',html)
    html=re.sub('</div>','',html)
    return html

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

def get_similaritytitle(title,url):
    gmt_created=getToday()
    sql='select title from news where gmt_created>=%s'
    cursor.execute(sql,[gmt_created])
    resultlist=cursor.fetchall()
    listall=[]
    if resultlist:
        for result in resultlist:
            title1=result[0]
            similarity=getsimilarity(title,title1)
            if similarity>60:
#                print title
#                print description
    #            print '---------'
#                print description1
#                print 'description similarity ' + str(similarity) +' %' + ' ' + url
#                txt='same content'+str(similarity)+'% '+time3+url
#                f=open(newspath+ time2 +'-outsame.txt','ab')
#                print >>f,txt
#                f.close()
                listall=1
    return listall

def get_similarityzhaiyao(title,url):
    gmt_created=getToday()
    sql='select description from news where gmt_created>=%s'
    cursor.execute(sql,[gmt_created])
    resultlist=cursor.fetchall()
    listall=[]
    if resultlist:
        for result in resultlist:
            title1=result[0]
            similarity=getsimilarity(title,title1)
            if similarity>60:
                listall=1
    return listall

def get_new(title):
    time3d=getpastoneday(3)
    sql='select id from news where title=%s and gmt_publish>=%s'
    cursor.execute(sql,[title,time3d])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist

def convert(ch):
    length = 0
    with open(r'seoword5.txt') as f:
        for line in f:
            if ch in line:
                sst=line.split(',')[0]
                if ch==sst:
#                    print ch
                    return line[length:len(line)]
def seoreplace(html):
    lexicon=get_lexicon(html)
    for lex in lexicon:
        lex=lex.encode('utf8')
        ss=convert(lex)
        if ss and len(lex)>1 and re.match('[ \u4e00 -\u9fa5]+',lex)==None:
            sst=ss.split(',')
            if len(sst)>2:
                number=random.randint(1,(len(sst)-1))
                sst=sst[number]
            else:
                sst=sst[-1]
            sst="".join(sst.split())
            lex="".join(lex.split())
            html=html.replace(lex,sst)
    seolist=open(r'seokeywords.txt')
    for line1 in seolist:
        lineall=len(line1)
        line1=line1[0:lineall]
        sst3=line1.split(',')
        sst1=sst3[0]
        sst2=sst3[1]
        if sst1 in html:
            sst2=''.join(sst2.split())
            html=html.replace(sst1,sst2)
            return html
    seolist.close()
    return html

listtype={'153':191,'154':192,'155':193,'156':194}

def get_keywords1(typeid2):
    reid=listtype[str(typeid2)]
    sql='select typename from dede_arctype where reid=%s'
    cursor.execute(sql,[reid])
    resultlist=cursor.fetchall()
    listall=[]
    if resultlist:
        for result in resultlist:
            listall.append(result[0])
    return listall

def savedb(news_code,title,zhaiyao,content,news_source):
    #title分词
    sortrankl=random.randint(0,3600)
    lexicon=get_lexicon(title)
    keywords=gettags(title)
    if content:
        description=filter_tags(content)[:330]
    pubdate=int_to_datetime(time1-sortrankl)
    if not zhaiyao:
        zhaiyao=''
    argument=[title,title,news_code,zhaiyao,content,description,keywords,news_source,170,1,'auto','auto',pubdate,pubdate,pubdate]
    sql='insert into news(title,title_index,category_code,description,details,details_query,tags,news_source,view_count,pause_status,admin_account,admin_name,gmt_publish,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
    cursor.execute(sql,argument)
    conn.commit()
    img_url=get_img_url(content)#获得新闻图片
    if img_url:
        sql2='select max(id) from news'
        cursor.execute(sql2)
        result2=cursor.fetchone()
        if result2:
            news_id=result2[0]
            img_url=img_url[0]
#            print title
            sql="insert into photo(uid,cid,photo_album_id,path,target_id,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s)"
            cursor.execute(sql,[0,0,-2,img_url,news_id,pubdate,pubdate])
            conn.commit()
