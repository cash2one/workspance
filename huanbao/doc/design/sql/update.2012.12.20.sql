CREATE  TABLE IF NOT EXISTS `feedback` (

      `id` INT(20) NOT NULL AUTO_INCREMENT ,

      `details` VARCHAR(2000) NULL DEFAULT '' COMMENT '反馈信息' ,

      `screenshot` VARCHAR(250) NULL DEFAULT '' COMMENT '屏幕截图' ,

      `project` VARCHAR(45) NULL DEFAULT 'default' COMMENT '所属项目' ,

      `uid` INT(20) NULL DEFAULT 0 COMMENT '用户ID\n0：匿名' ,

      `cid` INT(20) NULL DEFAULT 0 COMMENT '用户公司ID\n0：匿名' ,

      `email` VARCHAR(45) NULL DEFAULT '' COMMENT '用户Email，可用于向用户发送反馈处理结果' ,

      `gmt_created` DATETIME NULL ,

      `gmt_modified` DATETIME NULL ,

      `response_status` CHAR(1) NULL DEFAULT 'N' COMMENT '是否对问题做出回应\nU:尚未处理\nY:已处理\nN:不处理' ,

      `gmt_response` DATETIME NULL ,

      `resolve_remark` VARCHAR(1000) NULL COMMENT '解决情况\n用于保存针对该问题的解决情况' ,

      PRIMARY KEY (`id`) ,

      INDEX `idx_project` (`project` ASC) ,

      INDEX `idx_uid` (`uid` ASC) ,

      INDEX `idx_cid` (`cid` ASC) ,

      INDEX `idx_gmt_created` (`gmt_created` ASC) ,

      INDEX `idx_gmt_response` (`gmt_response` ASC) )

    ENGINE = MyISAM

    COMMENT = '用户信息反馈'
