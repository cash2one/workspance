package com.nala.csd.event

import org.springframework.context.ApplicationListener
import com.nala.csd.UserOperate

class UserOperateListenerService implements ApplicationListener<UserOperateEvent > {

    static transactional = true
    @Override
    void onApplicationEvent(UserOperateEvent e) {
        def userOperate = new UserOperate()
        log.debug 'shanchujianting---------------'
        userOperate.loginTime = e.loginTime
        userOperate.clientIp = e.clientIp
        //userOperate.clientIp = e.clientIp
        userOperate.date = e.date
        userOperate.hero  = e.hero
        userOperate.memo = e.memo
        userOperate.tId = e.tId
        userOperate.tabName = e.tabName
        log.debug userOperate.clientIp
        log.debug userOperate.loginTime
        if(!userOperate.save(flush:true)){
            userOperate.errors.each {
                log.error "userOperate save error!"
            }
        }
    }
}
