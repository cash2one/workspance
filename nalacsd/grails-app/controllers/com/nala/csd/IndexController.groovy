package com.nala.csd


class IndexController {
    def springSecurityService

    def index = {
        Hero curUser = springSecurityService.currentUser
        if (curUser){
            // 123456
            if (curUser.password.equals('e10adc3949ba59abbe56e057f20f883e')){
                redirect(controller: 'hero', action: 'changePasswd')
            }
        }
        def userId = curUser?.id

        render(view: '/index', model: [userId:userId])
    }
}
