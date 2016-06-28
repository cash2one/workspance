#-*- coding:utf-8 -*-
import socket
import struct,sys
reload(sys)
sys.setdefaultencoding('UTF-8')

from socket import *
host = '121.40.103.220'
port = 5000
bufsize = 1024
addr = (host,port)
client = socket(AF_INET,SOCK_STREAM)
client.settimeout(2)
try :
    client.connect(addr)
except :
    print 'Unable to connect'
    sys.exit()
client.send("{'company_id':74545,'questionnum':0}")
"""
while True:
    data = raw_input()
    if not data or data=='exit':
        break
    client.send('%s\r\n' % data)
    data = client.recv(bufsize)
    if not data:
        break
    print data.strip()
"""
client.close()
sys.exit()