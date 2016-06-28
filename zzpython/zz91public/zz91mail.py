# -*- coding: utf-8 -*-
#!/usr/bin/python
import smtplib
from email.MIMEText import MIMEText
from email.Utils import formatdate
from email.Header import Header

class zz91mail:
    #----网易发邮件
    def mail163(self,fromMail,username,password,mailto_list,subject,body):
        smtpHost = 'smtp.163.com'
        smtpPort = '25'
        sslPort  = '465'
        encoding = 'utf-8'
        mail = MIMEText(body.encode(encoding),'html',encoding)
        mail['Subject'] = Header(subject,encoding)
        mail['From'] = fromMail
        mail['To'] =  ";".join(mailto_list)
        mail['Date'] = formatdate()
        smtp = smtplib.SMTP(smtpHost,smtpPort)
        smtp.ehlo()
        try:
            smtp.login(username,password)
        except:
            return 1
        try:
            smtp.sendmail(fromMail,mailto_list,mail.as_string())
        except:
            return 2
        smtp.close()
        return 0
