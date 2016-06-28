from public import *
from conn import database,databasesex
import random,time,jieba,re,os,sys,datetime
from pinyin import chinese_abstract
connsex=databasesex()
cursorsex=connsex.cursor()
reload(sys)
sys.setdefaultencoding('utf8')
def get_lexicon(strt):
    result = jieba.cut(strt,cut_all=False)
    participle=" / ".join(result)
    lexicon=participle.split(" / ")
    return lexicon

def gettags(title):
    #title分词
    if title:
        lexicon=get_lexicon(title)
        keywords=''
        for lec in lexicon:
            if 'nbsp' in lec:
                continue
            if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
                keywords=keywords+lec+','
                sql='select id from tags where name=%s'
                cursorsex.execute(sql,[lec])
                resultl=cursorsex.fetchone()
                if not resultl:
                    gmt_created=datetime.datetime.now()
                    tpinyin=chinese_abstract(lec)
                    sql='insert into tags(name,gmt_created,pinyin) values(%s,%s,%s)'
                    cursorsex.execute(sql,[lec,gmt_created,tpinyin])
                    connsex.commit()
        keywords=keywords[:-1]
        print keywords
def getsexlist():
    sql="select title from dede_archives limit 0,30000"
    cursorsex.execute(sql)
    result=cursorsex.fetchall()
    for list in result:
        gettags(list[0])
getsexlist()