#-*- coding:utf-8 -*- 
from django.http import HttpResponse
from django.shortcuts import render_to_response
from list_tool import get_kinds,get_now_column,get_column_list,get_hot_thisweek_type,choose_six,special_six,get_kind_list,get_kinds_one,get_special_te
from news import get_news_all,get_one_new,get_content,focus_three,get_flag_f,get_hot_img,get_one_result
from tools import get_img_url

def column_index(request):
    special_te=get_special_te()
#    guonei="guonei"
#    list_6=[1,1,1,1,1,1]
    hot_img=get_hot_img()
    #获得8个主栏目
    column_list=get_column_list()
    #获得4个副栏目id
    kid4=get_kind_list('4id')
    #获得4个主栏目id
    col4=get_kind_list()
    col_req=request.META['PATH_INFO'].replace('/','')
    
    
    #获得当前栏目
    now_column=get_now_column(col_req)
    #获得栏目id
    id=now_column['id']
    url2=now_column['url2']
    
    #本周热门资讯(栏目首页 和 类别首页)
    if id in kid4:
        hot_thisweek=get_hot_thisweek_type(id,'4id')
        focus1=1
    else:
        hot_thisweek=get_hot_thisweek_type(id)
        
    deputy_focus=''
    if id in kid4:
        #主栏目头条
        deputy_focus=focus_three(id,'4id')
    else:
        #副栏目头条
        deputy_focus=focus_three(id)
        
    if deputy_focus:
        deputy_focus1=deputy_focus[0]
        if len(deputy_focus)>1:
            deputy_focus2=deputy_focus[1]
        if len(deputy_focus)>2:
            deputy_focus3=deputy_focus[2]
        
    if id in kid4:
        special=special_six([id],'4kid')
    else:
        special=special_six([id],'4id')
        
    if special:
        special1=special[0]
        if len(special)>1:
            special2=special[1]
        if len(special)>2:
            special3=special[2]
        if len(special)>3:
            special4=special[3]
        if len(special)>4:
            special5=special[4]
        if len(special)>5:
            special6=special[5]
        
    #6条推荐
    if id in kid4:
        choose6=choose_six(id,url2,'4id')
    else:
        choose6=choose_six(id,url2)
    #幻灯图片
    if id in kid4:
        deputy_topic_img=get_flag_f(id)
    else:
        deputy_topic_img=get_flag_f(id,'f')
    
    if id in kid4:
        #4个主栏目
        kind_list=get_column_list('c4id')
    else:
        #4个辅助类别
        kind_list=get_kind_list('list4')
    kind_list1=kind_list[0]
    kind_list2=kind_list[1]
    kind_list3=kind_list[2]
    kind_list4=kind_list[3]
        
    
    
    kinds4h=[]
    for k4 in kind_list:
        k_id=k4['id']
        typename=k4['typename']
        url2=k4['url2']
        
        if id in kid4:
            titles_h=get_kinds_one(k_id,'1',id)
        else:
            titles_h=get_kinds_one(k_id,'',id)
        
        list={'k_id':k_id,'typename':typename,'url2':url2,'titles_h':titles_h}
        kinds4h.append(list)
    kinds4h_1=kinds4h[0]
    kinds4h_2=kinds4h[1]
    kinds4h_3=kinds4h[2]
    kinds4h_4=kinds4h[3]
    
    kinds4=[]
    for k4 in kind_list:
        kind_id=k4['id']
        if id in kid4:
            kinds_6=get_kinds(kind_id,'1',id)
        else:
            kinds_6=get_kinds(kind_id,'2',id)
        list={'titles_6':kinds_6}
        kinds4.append(list)
    kinds4_1=kinds4[0]
    kinds4_2=kinds4[1]
    kinds4_3=kinds4[2]
    kinds4_4=kinds4[3]
#    webtitle=now_column['typename']+",废料资讯,废料新闻,再生资源资讯,再生资源行业动态,废料知识 - ZZ91再生网".decode("utf8")
#    webkeywords=now_column['typename']+",废料资讯,废料新闻,再生资源资讯,再生资源新闻,再生资源行业动态,再生资源企业新闻".decode("utf8")
#    webdescription=now_column['typename']+",ZZ91再生网商业资讯，实时发布最新行业资讯，助你掌握废金属、废塑料、综合废料等行业资讯".decode("utf8")
    if col_req=='hangye':
        webtitle='废料行业资讯_废料行业新闻_再生资源行业资讯_再生资源行业动态-zz91再生网资讯中心'
        webkeywords='废料行业资讯，废料行业新闻，再生资源行业资讯，再生资源行业动态'
        webdescription='zz91再生网资讯中心行业资讯频道，倾情为废金属、废金属、废塑料、综合废料等各个废料行业提供实时的行业资讯动态。'
    if col_req=='pinlun':
        webtitle='废料评论预测-zz91再生网资讯中心'
        webkeywords='废料评论预测，废料评论，废料预测 '
        webdescription='zz91再生网资讯中心评论预测频道，快速发布关于废金属、废塑料、综合废料的市场前景以及专家评论预测，让您随时知悉市场动态。'
    if col_req=='tech':
        webtitle='废料技术文库_废料基础知识_再生资源知识-zz91再生网资讯中心'
        webkeywords='废料知识，废料技术文库，废料基础知识，再生资源知识'
        webdescription='zz91再生网资讯中心技术文库频道，为您带来各种关于废金属、废塑料和综合废料的专业基础知识，私人定制您所在行业的知识宝库。'
    
    if id in kid4:
        webtitle=now_column['typename']+'新闻资讯-zz91再生网资讯中心'.decode("utf8")
        webkeywords=now_column['typename']+'新闻，'.decode("utf8")+now_column['typename']+'资讯，'.decode("utf8")+now_column['typename']+'资讯中心'.decode("utf8")
        webdescription='zz91再生网资讯中心，不间断地为您发布关于'.decode("utf8")+now_column['typename']+'的新闻资讯，让您能够快速获取'.decode("utf8")+now_column['typename']+'方面的最新新闻资讯。'.decode("utf8")
    
#    if id in kid4:
#        col_req=''
    return render_to_response('cloumn_pg.html',locals())