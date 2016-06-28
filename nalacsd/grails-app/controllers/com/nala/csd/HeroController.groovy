package com.nala.csd

class HeroController {

    def springSecurityService

    def heroService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        log.debug("测试开始")
        [heroInstanceList: Hero.list(params), heroInstanceTotal: Hero.count()]
    }

    def create = {
        def heroInstance = new Hero()
        heroInstance.properties = params
        return [heroInstance: heroInstance]
    }

    def save = {
        if (params.email){
            String email = params.email
            if (email.indexOf('nalashop.com') < 0){
                flash.message ="邮箱输入错误"
                render(view: "create", model: [params: params])
                return
            }
        }else{
            flash.message ="邮箱输入错误"
            render(view: "create", model: [params: params])
            return
        }
        
        if (params.password1){
            String password1 = params.password1
            String password2 = params.password2
            if (!password1.equals(password2)){
                flash.message ="两次输入的密码不一致"
                render(view: "create", model: [params: params])
                return
            }
        }else{
            flash.message ="密码不能为空"
            render(view: "create", model: [params: params])
            return
        }
        
        if (!params.role.id || !params.role){
            flash.message ="分组不能为空"
            render(view: "create", model: [params: params])
            return
        }

        def heroInstance = new Hero(params)
        heroInstance.password = params.password1

        if (heroService.createHeroAndRole(heroInstance, params)){
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'hero.label', default: 'Hero'), heroInstance.id])}"
            redirect(action: "show", id: heroInstance.id)            
        }else{
            render(view: "create", model: [heroInstance: heroInstance])
        }
    }

    def show = {
        def heroInstance = Hero.get(params.id)
        if (!heroInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])}"
            redirect(action: "list")
        }
        else {
            [heroInstance: heroInstance]
        }
    }

    def edit = {
        def heroInstance = Hero.get(params.id)
        if (!heroInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [heroInstance: heroInstance]
        }
    }

    def update = {
        log.info params
        log.debug params.accountLocked
        def heroInstance = Hero.get(params.id)
        log.info "heroInstance = ${heroInstance.accountLocked}"
        if (heroInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (heroInstance.version > version) {
                    heroInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'hero.label', default: 'Hero')] as Object[], "Another user has updated this Hero while you were editing")
                    render(view: "edit", model: [heroInstance: heroInstance])
                    return
                }
            }
            heroInstance.properties = params

            if (params.password1 && params.password2){
                if (params.password1 == params.password2){
                    heroInstance.password = params.password1
                    
                    HeroRole.removeAll(heroInstance)

                    if (heroService.createHeroAndRole(heroInstance, params)) {
                        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'hero.label', default: 'Hero'), heroInstance.id])}"
                        redirect(action: "show", id: heroInstance.id)
                    }
                    else {
                        render(view: "edit", model: [heroInstance: heroInstance])
                    }

                }else{
                    flash.message = "两次输入的密码不一致。"
                    render(view: "edit", model: [heroInstance: heroInstance])
                    return
                }
            }else{
                render(view: "edit", model: [heroInstance: heroInstance])
                return
            }

        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def heroInstance = Hero.get(params.id)
        if (heroInstance) {
            try {
                heroInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])}"
            redirect(action: "list")
        }
    }

    /**
     * 权限功能上线，初始化信息
     */
    def initHeroInfo = {
        def allHero = Hero.list()

        allHero.each {

            if (it.email=="chengp@nalashop.com"
                || it.email=="chenlp@nalashop.com"
                || it.email=="jiangm@nalashop.com"
                || it.email=="licc@nalashop.com"
                || it.email=="liuk@nalashop.com"
                || it.email=="liuz@nalashop.com"
                || it.email=="weihx@nalashop.com"
                || it.email=="zhangss@nalashop.com"
                ||it.email=="zhouc@nalashop.com"){
                // 售后
                def userRole = Role.findByAuthority('ROLE_SHOUHOU')
                HeroRole.create(it, userRole, true)

            }else if (it.email == "chenpy@nalashop.com"
                || it.email == "duxt@nalashop.com"
                || it.email == "jinyq@nalashop.com"
                || it.email == "lixl@nalashop.com"
                || it.email == "lvb@nalashop.com"
                || it.email == "wuhx@nalashop.com"
                || it.email == "zhangxm@nalashop.com"
                || it.email == "zhousm@nalashop.com"){
                // 货款
                def userRole = Role.findByAuthority('ROLE_HUOKUAN')
                HeroRole.create(it, userRole, true)

            }else{
                def userRole = Role.findByAuthority('ROLE_XIAOSHOU')
                HeroRole.create(it, userRole, true)
            }
        }
    }

    /**
     * 修改密码
     */
    def changePasswd = {
        Hero user = springSecurityService.currentUser
        if (params.password1 && params.password2){
            if (params.password1 == params.password2){
                user.password = params.password1
                if (!user.save(flush: true)){
                    flash.message = "保存失败."
                    render(view: 'changePwd')
                    return
                }
                redirect(action: 'index', controller: 'index')
                return
            }else{
                flash.message = "两次输入的密码不一致。"
                render(view: 'changePwd')
                return
            }
        }else{
            render(view: 'changePwd')
            return
        }
    }

}
