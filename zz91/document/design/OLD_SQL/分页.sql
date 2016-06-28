DELIMITER $$;
DROP PROCEDURE IF EXISTS `sp_page`$$;
CREATE PROCEDURE `sp_page`(
 in _pagecurrent int,/*当前页*/
 in _pagesize int,/*每页的记录数*/
 in _ifelse varchar(1000),/*显示字段*/
 in _where varchar(1000),/*条件*/
 in _order varchar(1000)/*排序*/
)
COMMENT '分页存储过程'
BEGIN
 if _pagesize<=1 then 
  set _pagesize=20;
 end if;
 if _pagecurrent < 1 then 
  set _pagecurrent = 1; 
 end if;
 
 set @strsql = concat('select ',_ifelse,' from ',_where,' ',_order,' limit ',_pagecurrent*_pagesize-_pagesize,',',_pagesize); 
 prepare stmtsql from @strsql; 
 execute stmtsql; 
 deallocate prepare stmtsql; 

 set @strsqlcount=concat('select count(1) as count from ',_where);/*count(1) 这个字段最好是主键*/
 prepare stmtsqlcount from @strsqlcount; 
 execute stmtsqlcount; 
 deallocate prepare stmtsqlcount; 
END$$;
DELIMITER $$;
