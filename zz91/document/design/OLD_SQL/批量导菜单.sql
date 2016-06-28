INSERT INTO kasai_objects SELECT admin_menu.code FROM admin_menu;
UPDATE kasai_objects SET id=CONCAT('/zz91/adminmenu/',id) WHERE id NOT LIKE '%kasai%'