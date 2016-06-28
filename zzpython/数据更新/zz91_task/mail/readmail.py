#-*- encoding: utf-8 -*-
#author : rayment
#CreateDate : 2013-01-24

import imaplib
import email
#设置命令窗口输出使用中文编码
import sys,re
reload(sys)
sys.setdefaultencoding('utf8')

   
#字符编码转换方法
def my_unicode(s, encoding):
    if encoding:
        return unicode(s, encoding)
    else:
        return unicode(s)

#获得字符编码方法
def get_charset(message, default="ascii"):
    #Get the message charset
    return message.get_charset()
    return default

#解析邮件方法（区分出正文与附件）
def parseEmail(msg, mypath):
    mailContent = None
    contenttype = None
    suffix =None
    for part in msg.walk():
        if not part.is_multipart():
            contenttype = part.get_content_type()   
            filename = part.get_filename()
            charset = get_charset(part)
            #是否有附件
            if filename:
                h = email.Header.Header(filename)
                dh = email.Header.decode_header(h)
                fname = dh[0][0]
                encodeStr = dh[0][1]
                if encodeStr != None:
                    if charset == None:
                        fname = fname.decode(encodeStr, 'gbk')
                    else:
                        fname = fname.decode(encodeStr, charset)
                data = part.get_payload(decode=True)
                print('Attachment : ' + fname)
    
            else:
                if contenttype in ['text/plain']:
                    suffix = '.txt'
                if contenttype in ['text/html']:
                    suffix = '.htm'
                if charset == None:
                    mailContent = part.get_payload(decode=True)
                else:
                    mailContent = part.get_payload(decode=True).decode(charset)         
    return  (mailContent, suffix)

#获取邮件方法
def getMail(mailhost, account, password, diskroot, port = 993, ssl = 1):
    mypath = diskroot + ':\\'
    #是否采用ssl
    if ssl == 1:
        imapServer = imaplib.IMAP4_SSL(mailhost, port)
    else:
        imapServer = imaplib.IMAP4(mailhost, port)
    imapServer.login(account, password)
    imapServer.select()
    #邮件状态设置，新邮件为Unseen
    #Message statues = 'All,Unseen,Seen,Recent,Answered, Flagged'
    resp, items = imapServer.search(None, "Unseen")
    number = 1
    for i in items[0].split():
        #print i
        #get information of email
        resp, mailData = imapServer.fetch(i, "(RFC822)")   
        mailText = mailData[0][1]
        msg = email.message_from_string(mailText)
        ls = msg["From"].split(' ')
        strfrom = ''
        strdate=''
        if(len(ls) == 2):
            fromname = email.Header.decode_header((ls[0]).strip('\"'))
            strfrom = 'From : ' + my_unicode(fromname[0][0], fromname[0][1]) + ls[1]
        else:
            strfrom = 'From : ' + msg["From"]
            strdate = 'Date : ' + msg["Date"]
        subject = email.Header.decode_header(msg["Subject"])
        sub = my_unicode(subject[0][0], subject[0][1])
        strsub = 'Subject : ' + sub
        mailContent, suffix = parseEmail(msg, mypath)
        #命令窗体输出邮件基本信息
        #print '\n'
        #print 'No : ' + str(number)
        #print strfrom
        #print strdate
        #print strsub
        #print 'Content:'
        mailcontent= mailContent.decode('gbk')
        a1=mailcontent.split("<td>商品名称： </td>")
        a2=a1[1]
        a3=a2.split("</td>")
        a4=filter_tags(a3[0]).replace("\r\n", '').replace(" ","")
        print a4
    imapServer.close()
    imapServer.logout()
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

if __name__ =="__main__":
    #邮件保存在e盘
    mypath ='e'
    print 'begin to get email...'
    getMail('pop.qq.com', 'zhifu@asto-inc.com', 'zj88friend', mypath, 993, 1)
    #126邮箱登陆没用ssl
    #getMail('imap.126.com', 'xxxxxxxxx@126.com', 'xxxxxxxxxx', mypath, 143, 0)
    print 'the end of get email.'