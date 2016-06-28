drop table if exists `lucene_sequence`;
drop table if exists `lucene_resource_changes`;
drop table if exists `lucene_resources`;

create table `lucene_sequence` (
  `name` varchar(50) not null,
  `current_value` int(20) not null,
  `increment` int(10) not null default '1',
  primary key  (`name`)
) engine=innodb default charset=utf8;

create table `lucene_resource_changes` (
  `id` int(20) not null auto_increment,
  `gmt_create`   datetime not null,
  `gmt_modified` datetime not null,
  `resource_type` varchar(255) not null,
  `resource_name` varchar(255) not null,
  `primary_id` int(20) not null,
  `changes`     varchar(10) not null,
  `seq`  int(20) not null default 1,
  `accept_died_evt` char(1) not null default '0',
  primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8;

create table `lucene_resources` (
  `id` int(20) not null,                     
  `gmt_create`    datetime not null,         
  `gmt_modified`  datetime not null,         
  `resource_type` varchar(255) not null,     
  `resource_name` varchar(255) not null,     
  `description`   varchar(255),              
  `version`  int(20) not null default  1,    
  primary key (`id`)
) engine=innodb default charset=utf8;

insert into lucene_sequence value('products',1,1);
insert into lucene_resources value(1,now(),now(),'','products','products',1);

## function
delimiter $$
drop function if exists `currval`$$
create function `currval`(seq_name varchar(50)) returns int(20)
begin
	declare value integer;
	set value = 0;
	select current_value into value
	from lucene_sequence
	where name = seq_name;
	return value;
end$$
delimiter ;

delimiter $$
drop function if exists `nextval`$$
create function `nextval`(seq_name varchar(50)) returns int(20)
begin
	update lucene_sequence
	set current_value = current_value + increment
	where name = seq_name;
	return currval(seq_name);
end$$

delimiter ;

## trigger
drop trigger if exists `t_afterinsert_on_products`;
drop trigger if exists `t_afterupdate_on_products`;
delimiter $$

create
    /*[definer = { user | current_user }]*/
    trigger `ast`.`t_afterinsert_on_products`  after insert
    on `ast`.`products`
    for each row begin
	insert lucene_resource_changes(gmt_create,gmt_modified,resource_name,primary_id,seq) values(now(),now(),'products',new.id,nextval('products'));
    end$$

delimiter ;

delimiter $$

create
    /*[definer = { user | current_user }]*/
    trigger `ast`.`t_afterupdate_on_products`  after update
    on `ast`.`products`
    for each row begin
	insert lucene_resource_changes(gmt_create,gmt_modified,resource_name,primary_id,seq) values(now(),now(),'products',new.id,nextval('products'));
    end$$

delimiter ;