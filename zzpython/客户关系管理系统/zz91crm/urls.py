#from django.conf.urls import *
from django.conf.urls.defaults import *
import settings
#系统管理
urlpatterns = patterns('zz91crm.useradmin',
	(r'^main.html$', 'main'),#主框架页面
	
	#---团队管理
	(r'^user_category.html$','user_category'),#团队管理列表页
	(r'^user_category_data.html$','user_category_data'),#团队管理数据
	(r'^user_category_add.html$','user_category_add'),#新建团队
	(r'^user_category_del.html$','user_category_del'),#删除团队新
	(r'^usercategoryedit.html$','usercategoryedit'),#编辑团队
	(r'^usercategoryeditok.html$','usercategoryeditok'),#编辑团队确认
	(r'^changestatus_usercate.html$','changestatus_usercate'),#改变菜单状态，开通还是冻结
	#---员工列表
	(r'^userlist.html$','userlist'),
	(r'^adduser1.html$','adduser'),
	(r'^adduserok.html$','adduserok'),
	(r'^edituser1.html$','edituser'),#编辑用户
	(r'^deluser.html$','adduser'),#删除用户
	(r'^del_alluser.html$','del_alluser'),#一键删除所选用户
	(r'^changestatus_user.html$','changestatus_user'),#改变用户状态，开通还是冻结
	#---角色权限管理
	(r'^auth.html$','auth'),
	(r'^addauth.html$','addauth'),
	(r'^addauthok.html$','addauthok'),
	(r'^auth_category_del.html$','auth_category_del'),
	(r'^auth_category_set.html$','auth_category_set'),#编辑拥有权限
	(r'^auth_category_setok.html$','auth_category_setok'),#编辑拥有权限确认
	#---菜单管理
	(r'^menu.html$','menu'),#菜单列表
	(r'^addmenu_all.html$','addmenu_all'),#添加菜单
	(r'^addmenuok.html$','addmenuok'),#添加菜单成功
	(r'^editmenu.html$','editmenu'),#编辑菜单
	(r'^changestatus_menu.html$','changestatus_menu'),#改变菜单状态，开通还是冻结
)
#登录模块
urlpatterns += patterns('zz91crm.login',
	(r'^$','login'),#用户登录
	(r'^login.html$','login'),#用户登录
	(r'^logincheck.html$','logincheck'),#核对
	(r'^logout.html$','logout'),#退出
)

#我的客户（再生通）
urlpatterns += patterns('zz91crm.icdlist',
	#---我的所有客户（再生通）
	(r'^icd/icdlist.html$','icdlist'),#客户列表
	(r'^icd/addnew_assign.html$','addnew_assign'),#新的分配
	(r'^icd/addnew_assign.html$','addnew_assign'),#拉黑
	(r'^icd/example.html$','example'),#测死
	(r'^icd/crm_cominfoedit.html$','crm_cominfoedit'),#编辑该公司详情
	(r'^icd/save_crm_cominfoedit.html$','save_crm_cominfoedit'),#保存公司详情
	(r'^icd/getsite.html$','getsite'),#获得地点
	(r'^icd/relogin.html$','relogin'),#重新登录
	(r'^icd/returnpage.html$','returnpage'),#返回
	(r'^icd/otherperson.html$','otherperson'),#其他联系人显示框
	(r'^icd/add_otherperson.html$','add_otherperson'),#添加与编辑其他联系人
	(r'^icd/del_otherperson.html$','del_otherperson'),#删除其他联系人
	(r'^icd/tellist.html$','tellist'),#销售记录列表(iframe)
	(r'^icd/addtellist.html$','addtellist'),#添加销售记录
)

#---css引入
urlpatterns += patterns('',
	(r'^(?!admin)(?P<path>.*)$','django.views.static.serve',{'document_root':settings.STATIC_ROOT}),
)