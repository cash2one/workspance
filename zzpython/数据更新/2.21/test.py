import re
import requests,urllib2
import urllib
url="http://www.baidu.com/baidu?word=%E9%AB%98%E4%BB%B7%E5%9B%9E%E6%94%B6%E9%94%A1%E6%B8%A3&ie=utf-8"
#f = urllib.urlopen(url)
#html = f.read()
#print html

r = requests.get("http://www.baidu.com/link?url=688t3LhJbbdbEV2JFPxQ59VfQVTlhVeyUmj7bU8oeYL76LI2YC_vGtuwuJLb8RpMeqEKc7Z2ZI9t0-Icxf5-Eq&wd=&eqid=83a38f0a000a182c0000000455fd218b")
text= r.text
print text


