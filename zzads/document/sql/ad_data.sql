广告主信息
INSERT INTO `zzads`.`advertiser`(`name`,`contact`,`phone`,`email`,`remark`,`category`,`deleted`,`gmt_created`)
VALUES('test01','contact_name1','123456','test01@email.com','{remark: VARCHAR}',1,'N',now());
INSERT INTO `zzads`.`advertiser`(`name`,`contact`,`phone`,`email`,`remark`,`category`,`deleted`,`gmt_created`)
VALUES('test02','contact_name2','123456','test02@email.com','{remark: VARCHAR}',1,'N',now());
INSERT INTO `zzads`.`advertiser`(`name`,`contact`,`phone`,`email`,`remark`,`category`,`deleted`,`gmt_created`)
VALUES('test03','contact_name3','123456','test03@email.com','{remark: VARCHAR}',1,'N',now());
INSERT INTO `zzads`.`advertiser`(`name`,`contact`,`phone`,`email`,`remark`,`category`,`deleted`,`gmt_created`)
VALUES('test04','contact_name4','123456','test04@email.com','{remark: VARCHAR}',1,'N',now());
INSERT INTO `zzads`.`advertiser`(`name`,`contact`,`phone`,`email`,`remark`,`category`,`deleted`,`gmt_created`)
VALUES('test05','contact_name5','123456','test05@email.com','{remark: VARCHAR}',1,'N',now());
INSERT INTO `zzads`.`advertiser`(`name`,`contact`,`phone`,`email`,`remark`,`category`,`deleted`,`gmt_created`)
VALUES('test06','contact_name6','123456','test06@email.com','{remark: VARCHAR}',1,'N',now());
-------------------------------------------------------------------------------------------------------------------
精确广告类型：
INSERT INTO `zzads`.`exact_ad_type`(`exact_name`,`js_function`,`java_key`,`remark`,`gmt_created`)
VALUES('keywords','js_function: TEXT','java_key: VARCHAR','remark: VARCHAR',now());
INSERT INTO `zzads`.`exact_ad_type`(`exact_name`,`js_function`,`java_key`,`remark`,`gmt_created`)
VALUES('language','js_function: TEXT','java_key: VARCHAR','remark: VARCHAR',now());
INSERT INTO `zzads`.`exact_ad_type`(`exact_name`,`js_function`,`java_key`,`remark`,`gmt_created`)
VALUES('productCate','js_function: TEXT','java_key: VARCHAR','remark: VARCHAR',now());
-------------------------------------------------------------------------------------------------------------------
广告投放方式：
INSERT INTO `zzads`.`ad_delivery_style`(`name`,`js_function`,`gmt_created`)
VALUES('普通','js_function: TEXT',now());
INSERT INTO `zzads`.`ad_delivery_style`(`name`,`js_function`,`gmt_created`)
VALUES('轮询','js_function: TEXT',now());
INSERT INTO `zzads`.`ad_delivery_style`(`name`,`js_function`,`gmt_created`)
VALUES('弹出窗','js_function: TEXT',now());
--------------------------------------------------------------------------------------------------------------------
广告位信息：
INSERT INTO `zzads`.`ad_position`(`name`,`parent_id`,`request_url`,`delivery_style_id`,`sequence`,`payment_type`,`width`,`height`,`gmt_created`,`deleted`)
VALUES('zz91首页广告',0,'www.zz91.com',1,1,0,40,50,now(),'N');
INSERT INTO `zzads`.`ad_position`(`name`,`parent_id`,`request_url`,`delivery_style_id`,`sequence`,`payment_type`,`width`,`height`,`gmt_created`,`deleted`)
VALUES('zz91首页广告',0,'www.zz91.com',2,2,0,40,50,now(),'N');
INSERT INTO `zzads`.`ad_position`(`name`,`parent_id`,`request_url`,`delivery_style_id`,`sequence`,`payment_type`,`width`,`height`,`gmt_created`,`deleted`)
VALUES('zz91首页广告',0,'www.zz91.com',2,2,0,40,50,now(),'N');
---------------------------------------------------------------------------------------------------------------------
广告位＿精确投放类别信息
INSERT INTO `zzads`.`position_exact_ads`(`position_id`,`exact_put_id`,`gmt_created`)
VALUES(1,1,now());
INSERT INTO `zzads`.`position_exact_ads`(`position_id`,`exact_put_id`,`gmt_created`)
VALUES(1,2,now());

INSERT INTO `zzads`.`position_exact_ads`(`position_id`,`exact_put_id`,`gmt_created`)
VALUES(2,1,now());
INSERT INTO `zzads`.`position_exact_ads`(`position_id`,`exact_put_id`,`gmt_created`)
VALUES(2,2,now());
INSERT INTO `zzads`.`position_exact_ads`(`position_id`,`exact_put_id`,`gmt_created`)
VALUES(2,3,now());

---------------------------------------------------------------------------------------------------------------------
INSERT INTO `zzads`.`ad`
(`position_id`,
`advertiser_id`,
`ad_title`,
`ad_description`,
`ad_content`,
`ad_target_url`,
`online_status`,
`gmt_start`,
`gmt_plan_end`,
`remark`,
`applicant`,
`reviewer`,
`review_status`,
`review_comment`,
`designer`,
`design_status`,
`gmt_created`,
`gmt_modified`)
VALUES
(1,
1,
'test01_pos1',
'用户1在广告位1上投放的广告',
'ad_content: VARCHAR',
'ad_target_url: VARCHAR',
'Y',
now(),
now(),
'remark: VARCHAR',
'applicant11',
'reviewer',
'u',
'review_comment: VARCHAR',
'designer',
'0',
now(),
now()
);

INSERT INTO `zzads`.`exact_ad_details`(`ad_id`,`exact_put_id`,`anchor_point`)VALUES(
1,1,'keywords1');
INSERT INTO `zzads`.`exact_ad_details`(`ad_id`,`exact_put_id`,`anchor_point`)VALUES(
1,2,'language_en');


=====================================================================================
INSERT INTO `zzads`.`ad`
(`position_id`,
`advertiser_id`,
`ad_title`,
`ad_description`,
`ad_content`,
`ad_target_url`,
`online_status`,
`gmt_start`,
`gmt_plan_end`,
`remark`,
`applicant`,
`reviewer`,
`review_status`,
`review_comment`,
`designer`,
`design_status`,
`gmt_created`,
`gmt_modified`)
VALUES
(2,
2,
'test02_pos2',
'用户2在广告位2上投放的广告',
'ad_content: VARCHAR',
'ad_target_url: VARCHAR',
'Y',
now(),
now(),
'remark: VARCHAR',
'applicant11',
'reviewer',
'u',
'review_comment: VARCHAR',
'designer',
'0',
now(),
now()
);




