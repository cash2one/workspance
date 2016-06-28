/*行业动态*/
UPDATE bbs_post SET bbs_post_category_id=1
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('废料百态','热门评论 ','废金属论坛 ','管理杂谈','网商故事','招聘专栏','资讯评论','报价评论','行情评论','废金属交流','塑料论坛','废塑料交流','塑料颗粒坛','PET交流专区',
'综合废料论坛','废纸论坛','废橡胶论坛','其它废料')));

/*行业知识*/
UPDATE bbs_post SET bbs_post_category_id=2
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('废料专家','创业.成功','废料产业之声','地方之声','商业防骗论坛','防骗宝典','曝光台','再生技术')));

/*ASTO动态*/
UPDATE bbs_post SET bbs_post_category_id=3
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('ZZ91学堂','ZZ91视频中心','ZZ91操作宝','成功分享','ZZ91网站公告','促销活动','网站新功能',
 '线下活动','网站公告', '互助看台','互助月刊','互助动态','新手上路','常见问题')));

/* 30-40 */
UPDATE bbs_post SET bbs_post_category_id=4
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('休闲娱乐','轻松一刻','生活常识','娱乐八卦')));

/* 所有的资讯，焦点关注归到行业动态*/
UPDATE bbs_post SET bbs_post_category_id=1 where old_news_id is not null;