import MySQLdb
import os,json,sys
import urllib,httplib
from datetime import datetime
reload(sys)
sys.setdefaultencoding('UTF-8')
sys.path.append('/mnt/pythoncode/zz91public/')
#----发手机短信
def postsms(mobile,content,template_code):
    content=content.replace("【ZZ91再生网】","")
    content=content.replace("【ZZ91再生网】","")
    content=content.replace("【ZZ91客服部】","")
    
    if template_code=="weixin":
        #---营销短信
        getString =urllib.urlencode({'Account':'asto','Mobile':str(mobile),'Password':'123456','Content':content,'Exno':'0'})
    else:
        getString =urllib.urlencode({'Account':'hzasto','Mobile':str(mobile),'Password':'123456','Content':content,'Exno':'0'})
    url="http://mt.10690404.com/send.do?"+getString
    f = urllib.urlopen(url)
    html = f.read()
    o = json.loads(html)
    codea=o['code']
    if (codea=='9001'):
        return True
    else:
        return False
def postsms_yunpian(mobile,text):
    #服务地址
    host = "yunpian.com"
    #端口号
    port = 80
    #版本号
    version = "v1"
    #查账户信息的URI
    user_get_uri = "/" + version + "/user/get.json"
    #通用短信接口的URI
    sms_send_uri = "/" + version + "/sms/send.json"
    #模板短信接口的URI
    sms_tpl_send_uri = "/" + version + "/sms/tpl_send.json"
    def get_user_info(apikey):
        """
        取账户信息
        """
        conns = httplib.HTTPConnection(host, port=port)
        conns.request('GET', user_get_uri + "?apikey=" + apikey)
        response = conns.getresponse()
        response_str = response.read()
        conns.close()
        return response_str
    
    def send_sms(apikey, text, mobile):
        """
        能用接口发短信
        """
        params = urllib.urlencode({'apikey': apikey, 'text': text, 'mobile':mobile})
        headers = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain"}
        conns = httplib.HTTPConnection(host, port=port, timeout=30)
        conns.request("POST", sms_send_uri, params, headers)
        response = conns.getresponse()
        response_str = response.read()
        conns.close()
        return response_str
    
    def tpl_send_sms(apikey, tpl_id, tpl_value, mobile):
        """
        模板接口发短信
        """
        params = urllib.urlencode({'apikey': apikey, 'tpl_id':tpl_id, 'tpl_value': tpl_value, 'mobile':mobile})
        headers = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain"}
        conns = httplib.HTTPConnection(host, port=port, timeout=30)
        conns.request("POST", sms_tpl_send_uri, params, headers)
        response = conns.getresponse()
        response_str = response.read()
        conn.close()
        return response_str
    apikey = "b0e18bf55ea73c19e3a630c4f0184e90 "
    
    #text = "亲爱的童鞋们，20年前我们在同一个地方度过了美好的初中生活，20年后我们再次相聚在同一个地方，找回我们20年前美好的记忆！钱库一中94级3班20周年同学会本周六（9月6日）下午3点整在母校相聚，恭请你的大驾！---20周年同学会筹备组【ZZ91再生网】"
    #查账户信息
    #print(get_user_info(apikey))
    #调用通用接口发短信
    html=send_sms(apikey, text, mobile)
    o = json.loads(html)
    codea=o['msg']
    if (codea=='OK'):
        return True
    else:
        return False
    #调用模板接口发短信
    #tpl_id = 1 #对应的模板内容为：您的验证码是#code#【#company#】
    #tpl_value = '#username#=1234&#company#=云片网'
    #print(tpl_send_sms(apikey, tpl_id, tpl_value, mobile))