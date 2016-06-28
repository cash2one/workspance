import com.nala.csd.Role
import com.nala.csd.Hero
import com.nala.csd.HeroRole

class BootStrap {

    def init = { servletContext ->

//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('其他');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('直接补发');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('换货补发');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('退货打款');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('退货退款');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('直接打款');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('直接退款');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('自然解决');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('下次补发');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('10天内未收到退货');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('赠送优惠券');
//        INSERT INTO jin_tmall_solve_type(NAME)VALUES('旺旺留言');
//
//
//        update jin_guan_shou_hou set solve_type_id = '1' where solve_type='none';
//        update jin_guan_shou_hou set solve_type_id = '2' where solve_type='bufa';
//        update jin_guan_shou_hou set solve_type_id = '3' where solve_type='huanhuo';
//        update jin_guan_shou_hou set solve_type_id = '4' where solve_type='tuihuodakuan';
//        update jin_guan_shou_hou set solve_type_id = '5' where solve_type='tuihuotuikuan';
//        update jin_guan_shou_hou set solve_type_id = '6' where solve_type='zhijiedakuan';
//        update jin_guan_shou_hou set solve_type_id = '7' where solve_type='zhijietuikuan';
//        update jin_guan_shou_hou set solve_type_id = '8' where solve_type='ziranjiejue';
//        update jin_guan_shou_hou set solve_type_id = '9'  where solve_type='xiacibufa';
//        update jin_guan_shou_hou set solve_type_id = '10' where solve_type='notreceive';
//        update jin_guan_shou_hou set solve_type_id = '11' where solve_type='givecoupon';
//        update jin_guan_shou_hou set solve_type_id = '12' where solve_type='wangwang';
//
//        update tmall_shouhou set solve_type_id = '1' where solve_type='none';
//        update tmall_shouhou set solve_type_id = '2' where solve_type='bufa';
//        update tmall_shouhou set solve_type_id = '3' where solve_type='huanhuo';
//        update tmall_shouhou set solve_type_id = '4' where solve_type='tuihuodakuan';
//        update tmall_shouhou set solve_type_id = '5' where solve_type='tuihuotuikuan';
//        update tmall_shouhou set solve_type_id = '6' where solve_type='zhijiedakuan';
//        update tmall_shouhou set solve_type_id = '7' where solve_type='zhijietuikuan';
//        update tmall_shouhou set solve_type_id = '8' where solve_type='ziranjiejue';
//        update tmall_shouhou set solve_type_id = '9'  where solve_type='xiacibufa';
//        update tmall_shouhou set solve_type_id = '10' where solve_type='notreceive';
//        update tmall_shouhou set solve_type_id = '11' where solve_type='givecoupon';
//        update tmall_shouhou set solve_type_id = '12' where solve_type='wangwang';


        if(!Role.findByAuthority('ROLE_ADMIN')){
            def role= new Role()
            role.authority = 'ROLE_ADMIN'
            role.name = '管理员'
            role.save(flush:true)
        }

        if(!Hero.findByEmail("admin@nalashop.com")){
            println('start create admin user')
            def adminUser = new Hero(name: '管理员', enabled: true, password: 'nala', email: 'admin@nalashop.com', username: 'admin')
            if (!adminUser.save(flush: true)){
                adminUser.errors.each{
                    println it
                }
            }else{
                println('admin user create success.')
            }

            def adminRole =  Role.findByAuthority('ROLE_ADMIN')

            HeroRole.create(adminUser, adminRole, true)
        }


    }
    def destroy = {
    }
}
