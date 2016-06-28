package com.nala.csd

import com.nala.common.utils.DateUtils

class UserOperateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = params.sort ? params.sort : "id"
        params.order = params.order ? params.order : "desc"
        [userOperateInstanceList: UserOperate.list(params), userOperateInstanceTotal: UserOperate.count()]
    }

//    def create = {
//        def userOperateInstance = new UserOperate()
//        userOperateInstance.properties = params
//        return [userOperateInstance: userOperateInstance]
//    }


    def search = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.offset = params.offset ? params.offset : 0
      //  params.sort = params.sort ? params.sort : "id"
      //  params.order = params.order ? params.order : "desc"
        def userOperateList = UserOperate.createCriteria().list(params){

            //登陆用户hero
            if(params.hero){
                Hero hero = Hero.get(Integer.valueOf(params.hero))
                eq('hero', hero)
            }

            //登陆时间loginTimeStart,登陆时间loginTimeEnd
            if (params.loginTimeStart){
                def loginTimeStart = DateUtils.parseToDates(params.loginTimeStart)
                gt('loginTime', loginTimeStart)
            }

            if (params.loginTimeEnd){
                def loginTimeEnd = DateUtils.parseToDates(params.loginTimeEnd)
                lt('loginTime', loginTimeEnd)
            }

            //操作类型memo
            if (params.memo){

             Integer code = Integer.valueOf(params.memo)
             def memo = MemoEnum.getByCode(code)
            // TradeStatusEnum.values().find{status->status.description == paramsCase.status}
            // eq('memo', MemoEnum.values().find {status->status == params.memo.status})
                eq('memo', memo)
            }

            //操作的表tabName

            if (params.tabName){
                Integer code = Integer.valueOf(params.tabName)
                def tabName = TabEnum.getByCode(code)
                eq('tabName', tabName)
            }

            //字段号tId

            if (params.tId){
                eq('tId', params.tId)
            }

            //操作时间dateStart,操作时间dateEnd
            if (params.dateStart){
                def dateStart = DateUtils.parseToDates(params.dateStart)
                gt('date', dateStart)
            }

            if (params.dateEnd){
                def dateStart =  DateUtils.parseToDates(params.dateEnd)
                lt('date', dateStart)
            }

            //登陆clientIp

            if (params.clientIp){
                params.client = String.valueOf(params.clientIp).trim()
                like('clientIp', '%'+params.clientIp+'%')
            }

           order('id', 'desc')
        }
       render (view:'list', model:[userOperateInstanceList: userOperateList, userOperateInstanceTotal: userOperateList.count()])
    }



//    def save = {
//        def userOperateInstance = new UserOperate(params)
//        if (userOperateInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), userOperateInstance.id])}"
//            redirect(action: "show", id: userOperateInstance.id)
//        }
//        else {
//            render(view: "create", model: [userOperateInstance: userOperateInstance])
//        }
//    }

    def show = {
        def userOperateInstance = UserOperate.get(params.id)
        if (!userOperateInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userOperateInstance: userOperateInstance]
        }
    }

//    def edit = {
//        def userOperateInstance = UserOperate.get(params.id)
//        if (!userOperateInstance) {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), params.id])}"
//            redirect(action: "list")
//        }
//        else {
//            return [userOperateInstance: userOperateInstance]
//        }
//    }

//    def update = {
//        def userOperateInstance = UserOperate.get(params.id)
//        if (userOperateInstance) {
//            if (params.version) {
//                def version = params.version.toLong()
//                if (userOperateInstance.version > version) {
//
//                    userOperateInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userOperate.label', default: 'UserOperate')] as Object[], "Another user has updated this UserOperate while you were editing")
//                    render(view: "edit", model: [userOperateInstance: userOperateInstance])
//                    return
//                }
//            }
//            userOperateInstance.properties = params
//            if (!userOperateInstance.hasErrors() && userOperateInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), userOperateInstance.id])}"
//                redirect(action: "show", id: userOperateInstance.id)
//            }
//            else {
//                render(view: "edit", model: [userOperateInstance: userOperateInstance])
//            }
//        }
//        else {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), params.id])}"
//            redirect(action: "list")
//        }
//    }

    def delete = {
        def userOperateInstance = UserOperate.get(params.id)
        if (userOperateInstance) {
            try {
                userOperateInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userOperate.label', default: 'UserOperate'), params.id])}"
            redirect(action: "list")
        }
    }
}
