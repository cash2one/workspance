 alter table ad ADD COLUMN `expired_rent` VARCHAR(250) NOT NULL DEFAULT '' COMMENT '过期招租\n默认为\'\''  AFTER `callback_data`;
