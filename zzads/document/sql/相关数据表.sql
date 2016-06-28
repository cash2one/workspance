广告信息：
	position_id:	广告位ID
	advertiser_id:	广告主信息ID
	ad_title:	广告标题，可能会在广告展示的时候使用
	ad_description:	广告详细描述
	ad_content:	广告内容（根据提供的素材生成的最终广告）素材地址ad_content
	ad_target_url:	广告跳转目标URL
	online_status:	广告上下线状态Y：表示上线N：表示下线
	gmt_start:	广告生效时间
	gmt_plan_end:	计划下线时间.可以不设置
	remark:		备注
	applicant:	申请人
	reviewer:	审核人
	review_status:	广告审核状态:U:表示未审核Y:表示审核通过N:表示不通过
	review_comment:	审核说明（主要是不通过原因）
	designer
	designer_status
	gmt_created
	gmt_modified
广告
｛。。。。。。。。。。。
提供的资料
广告素材信息：
	id
	ad_id,
	素材名称
	素材类别
	素材文件路径
	素材说明
 
｝
广告投放位置（广告位）：ad_position
	id,
	parent_id,
	name,
	request_url,
	--put_type 广告投放类型：0 普通，1 精确（）
	delivery_style_id,	显示方式
	payment_type	付费方式()
	width		
	height		

广告（投放方式）显示方式：弹出，轮询，固定 ad_delivery_style
	id
	name		投放方式
	js_function	js内容
	modifier	最后修改人
广告精确投放条件：(关键字，语言，分类)exact_ad_type
	id,
	exact_name
	js_function（param_value1,param_value2,param_value3,...）
	java_key
	
	－－param_type:key:exact_put_type,name:精确投放类型参数
	－－param:	param_type_key:exact_put_type,
			param_name	关键字
			param_key	keywords
			param_value	参数值
广告位＿精确投放条件 position_exact_ads
	position_id
	exact_put_id
分类参数表：
	param_type:
		key		分类键
		name		分类名称
		gmt_created	创建时间
		creator		创建人
	param:
		param_name	参数名称
		param_type_key	参数分类
		param_key	参数键名
		param_value	参数值
		sort		排序
		is_use		是否在用 1在用，0停用
		remark		备注
		gmt_created	创建时间
		creator		创建人
		primary(param_type_key,param_key,param_value)

行为记录：请求的URL，网站（域名，站点IP），IP，时间，广告位｛模块，类别，位置，显示方式，｝，广告，行为｛点击，展示｝，关键字 

广告系统中的角色
客户，
审核人，
设计人，
系统维护人员，（主要是相关类型参数的增删改）
系统管理员，（）
广告系统中用户信息
广告系统中的权限信息
广告信息相关权限：
	申请（添加广告信息）
	审核（修改广告信息中的审核状态字段，审核人，生效时间，下线时间）
素材信息相关权限：
	新增素材信息
	修改素材信息
	删除素材信息
广告位权限
类别管理权限

角色＿权限表
角色＿用户表


关于网络广告定价模式的一组常用术语：

CPA (Cost-per-Action) ：
每次行动的费用，即根据每个访问者对网络广告所采取的行动收费的定价模式。对于用户行动有特别的定义，包括形成一次交易、获得一个注册用户、或者对网络广告的一次点击等。

CPC (Cost-per-click)： 
每次点击的费用。根据广告被点击的次数收费。如关键词广告一般采用这种定价模式。

CPM（Cost per Thousand Impressions）：
每千次印象费用。广告条每显示1000次（印象）的费用。CPM是最常用的网络广告定价模式之一。

CPO (Cost-per-Order) ：
也称为Cost-per-Transaction，即根据每个订单/每次交易来收费的方式。

PPC（Pay-per-Click）：
是根据点击广告或者电子邮件信息的用户数量来付费的一种网络广告定价模式。

PPL（Pay-per-Lead）：
根据每次通过网络广告产生的引导付费的定价模式。例如，广告客户为访问者点击广告完成了在线表单而向广告服务商付费。这种模式常用于网络会员制营销模式中为联盟网站制定的佣金模式。

PPS（Pay-per-Sale）：
根据网络广告所产生的直接销售数量而付费的一种定价模式 。

CPTM (Cost per Targeted Thousand Impressions) ：
经过定位的用户（如根据人口统计信息定位）的千次印象费用。CPTM与CPM的区别在于，CPM是所有用户的印象数，而CPTM只是经过定位的用户的印象数。

营销效果是指，销售额(CPS, Cost for Per Sale)、引导数(CPL, Cost for Per Lead)、点击数（CPC, Cost for Per Click）等。
按照以上营销的实际效果付费统称为按营销效果付费（CPA, Cost for Action）。
CPL(cost per lead)，即为点击广告引导用户到达指定页面成功支付佣金的计费方式；
CPA(cost per action)，即为引导用户到达后注册、下载等行动成功支付佣金的计费方式；
CPS(cost per sale)，即为按照销售成功支付佣金的计费方式。

请求：时间，请求URL｛包括参数列表（精确信息在此解析）｝，IP，行为［点击｜展示］，类型［pos:广告位ID｜ad:广告ID］
if(类型==pos:广告位ID){
	if(hasExactInfo){
		list exact ad
	}else{
		list ad in pos
	}
}

select count(1) as c from position_exact_ads where posistion_id=#pos_id#
hasExactInfo= c>0

--选择广告位上所有的精确广告类型
select eat.*,ead.*
from exact_ad_type eat,
position_exact_ads pos_e_a,
ad,
exact_ad_details ead
where pos_e_a.position_id=#pos_id# --广告位
and eat.id=pos_e_a.exact_put_id	--广告位上的精确广告类型
and ad.position_id=pos_e_a.position_id --广告位上的所有广告
and ad.id=ead.ad_id	--精确广告上的条件
and eat.id=ead.exact_put_id	--精确广告的定位信息
and ad.online_status='Y'; --上线的广告

如果在此位置上根据关键字找不相匹配的广告，是否显示此广告位上的别的广告

use zzads;
select eat.*,ead.*
from exact_ad_type eat,
position_exact_ads pos_e_a,
ad,
exact_ad_details ead
where pos_e_a.position_id=1
and eat.id=pos_e_a.exact_put_id
and ad.position_id=pos_e_a.position_id
and ad.id=ead.ad_id
and eat.id=ead.exact_put_id
and ad.online_status='Y';

















