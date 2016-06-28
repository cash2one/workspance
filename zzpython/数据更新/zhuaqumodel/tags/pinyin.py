#-*- coding:utf-8 -*-
import re

def convert(ch):#获取一个汉字转为英文
    length = 3 #utf-8，汉字占用3字节
    with open(r'convert-utf-8.txt') as f:
        for line in f:
            if ch.encode('utf-8') in line:
                return line[length:len(line)-2]
            
def split_str(chinese):#传入的汉字分成一个一个的
    c=[]
    lc=1
    len_c=range(0,len(chinese)/lc)
    for lenn in len_c:
        ag=lenn*lc
        ed=lenn*lc+lc
        c.append(chinese[ag:ed])
    return c

def is_chinese(uchar):
        if uchar >= u'\u4e00' and uchar<=u'\u9fa5':
            return True
        else:
            return False

def chinese_abstract(str):#每个汉字转为拼音,再拼接起来
    ddd=''
    cccl=split_str(str)
    for ccc in cccl:
        if is_chinese(ccc)==True:
            ss=convert(ccc)
            if ss:
                ss=ss.replace('0','')
                ss=ss.replace('1','')
                ss=ss.replace('2','')
                ss=ss.replace('3','')
                ss=ss.replace('4','')
                ss=ss.replace('5','')
                ss=ss.replace('6','')
                ss=ss.replace('7','')
                ss=ss.replace('8','')
                ss=ss.replace('9','')
            else:
                ss=''
        else:
            ss=ccc
        if ',' in ss:
            ss=re.sub(',.*','',ss)[:-1]
        ddd+=ss
    return ddd
