#-*- coding:utf-8 -*-
#----系统消息推送定时任务

def getapp_companylist(dbc):
    sql='select company_id from app_company'
    resultlist=dbc.fetchalldb(sql)
    listall=[]
    for result in resultlist:
        listall.append(result[0])
    return listall

def getapp_message(dbc,datetoday,nowtime):
    sql='select id from app_message where gmt_created>=%s'
    resultlist=dbc.fetchalldb(sql,[datetoday])
    sql2='insert into app_message_view(mid,company_id,is_views,gmt_created) values(%s,%s,%s,%s)'
    sql3='select id from app_message_view where mid=%s and company_id=%s'
    for result in resultlist:
        mid=result[0]
        app_companylist=getapp_companylist(dbc)
#        company_id=123456
        for company_id in app_companylist:
            argument=[mid,company_id,0,nowtime]
            result3=dbc.fetchonedb(sql3,[mid,company_id])
            if not result3:
                dbc.updatetodb(sql2,argument)

if __name__=="__main__":
    getapp_message()