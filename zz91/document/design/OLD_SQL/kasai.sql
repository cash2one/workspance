#user
select * from kasai_objects_users_roles as kour where kour.id_user='xiaoming' and kour.id_object='/zz91/adminmenu/10' 
and exists(select null from kasai_objects as ko where ko.id=kour.id_object) 
and exists(select null from kasai_users as ku where ku.id=kour.id_user)
and exists
(select null from kasai_roles_operatives as kro where kro.id_operative='zz91.adminmenu.show' and kro.id_role=kour.id_role 
and exists(select null from kasai_operatives as kop where kop.id=kro.id_operative)
);

#group
select * from kasai_objects_groups_roles as kogr where kogr.id_object='/zz91/adminmenu/10' 
and exists(select null from kasai_users_groups as kug where kug.id_group=kogr.id_group and id_user='xiaoming' )
and exists(select null from kasai_objects as ko where ko.id=kogr.id_object) 
and exists
(select null from kasai_roles_operatives as kro where kro.id_operative='zz91.adminmenu.show' and kro.id_role=kogr.id_role 
and exists(select null from kasai_operatives as kop where kop.id=kro.id_operative)
);