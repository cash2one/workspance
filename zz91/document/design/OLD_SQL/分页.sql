DELIMITER $$;
DROP PROCEDURE IF EXISTS `sp_page`$$;
CREATE PROCEDURE `sp_page`(
 in _pagecurrent int,/*��ǰҳ*/
 in _pagesize int,/*ÿҳ�ļ�¼��*/
 in _ifelse varchar(1000),/*��ʾ�ֶ�*/
 in _where varchar(1000),/*����*/
 in _order varchar(1000)/*����*/
)
COMMENT '��ҳ�洢����'
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

 set @strsqlcount=concat('select count(1) as count from ',_where);/*count(1) ����ֶ����������*/
 prepare stmtsqlcount from @strsqlcount; 
 execute stmtsqlcount; 
 deallocate prepare stmtsqlcount; 
END$$;
DELIMITER $$;
