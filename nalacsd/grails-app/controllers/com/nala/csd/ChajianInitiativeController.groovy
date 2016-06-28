package com.nala.csd

import com.nala.common.utils.DateUtils

class ChajianInitiativeController {

    def dataExportService

    def chajianInitiativeCookieService

    def dataImportService

    def userOperateService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"

        // 筛选后的查件代码
        def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.initiative)

        [chajianInitiativeInstanceList: ChajianInitiative.list(params), chajianInitiativeInstanceTotal: ChajianInitiative.count(), chajianCodeList: chajianCodeList]
    }

    def create = {
        def chajianInitiativeInstance = new ChajianInitiative()
        chajianInitiativeInstance.properties = params

        // cookie预存店铺id
        def storeId = request.getCookie("chajianInitiative.store.id")
        if (storeId){
            def store = Store.get(storeId)
            if (store){
                chajianInitiativeInstance.store = store
            }
        }

        // cookie预存查件code代码
        def chajianCodeId = request.getCookie("chajianInitiative.chajianCode.id")
        if (chajianCodeId){
            ChajianCode chajianCode = ChajianCode.get(chajianCodeId)
            if (chajianCode){
                chajianInitiativeInstance.chajianCode = chajianCode
            }
        }

        // cookie预存处理人
        def solveCSId = request.getCookie("chajianInitiative.solveCS.id")
        if (solveCSId){
            Hero solveCS = Hero.get(solveCSId)
            if (solveCS){
                chajianInitiativeInstance.solveCS = solveCS
            }
        }

        // cookie预存跟踪人
        def followCSId = request.getCookie("chajianInitiative.followCS.id")
        if (followCSId){
            Hero followCS = Hero.get(followCSId)
            if (followCS){
                chajianInitiativeInstance.followCS = followCS
            }
        }

        // 筛选后的查件代码
        def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.initiative)

        return [chajianInitiativeInstance: chajianInitiativeInstance, chajianCodeList: chajianCodeList]
    }

    def save = {
        def chajianInitiativeInstance = new ChajianInitiative(params)

        Date curDate = new Date()
        chajianInitiativeInstance.dateCreated = curDate

        if (params.solveCS.id){
            // 有处理人，加上联系时间
            chajianInitiativeInstance.contactTime = curDate
        }

        if (params.followCS.id){
            // 有跟踪人，加上处理时间
            chajianInitiativeInstance.followTime = curDate
        }

        if (chajianInitiativeInstance.contactTime){
            // 计算时间间隔
            chajianInitiativeInstance.contactInterval = DateUtils.DaysBetween(chajianInitiativeInstance.dateCreated, chajianInitiativeInstance.contactTime)
        }

        if (chajianInitiativeInstance.followTime && chajianInitiativeInstance.contactTime){
            // 计算时间间隔
            chajianInitiativeInstance.followInterval = DateUtils.DaysBetween(chajianInitiativeInstance.contactTime, chajianInitiativeInstance.followTime)
        }

        // 写cookie
        chajianInitiativeCookieService.setChajianInitiativeToCookie(chajianInitiativeInstance, response)

        if (chajianInitiativeInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(chajianInitiativeInstance ,this.actionName)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), chajianInitiativeInstance.id])}"
            redirect(action: "show", id: chajianInitiativeInstance.id)
        }
        else {
            // 筛选后的查件代码
            def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.initiative)
            render(view: "create", model: [chajianInitiativeInstance: chajianInitiativeInstance, chajianCodeList: chajianCodeList])
        }
    }

    def show = {
        def chajianInitiativeInstance = ChajianInitiative.get(params.id)
        if (!chajianInitiativeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), params.id])}"
            redirect(action: "list")
        }
        else {
            [chajianInitiativeInstance: chajianInitiativeInstance]
        }
    }

    def edit = {
        def chajianInitiativeInstance = ChajianInitiative.get(params.id)

        // 筛选后的查件代码
        def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.initiative)

        if (!chajianInitiativeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [chajianInitiativeInstance: chajianInitiativeInstance, chajianCodeList: chajianCodeList]
        }
    }

    def update = {
        def chajianInitiativeInstance = ChajianInitiative.get(params.id)
        if (chajianInitiativeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (chajianInitiativeInstance.version > version) {

                    chajianInitiativeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'chajianInitiative.label', default: 'ChajianInitiative')] as Object[], "Another user has updated this ChajianInitiative while you were editing")
                    render(view: "edit", model: [chajianInitiativeInstance: chajianInitiativeInstance])
                    return
                }
            }

            Date curDate = new Date()
            // 跟踪人从无到有，自动加解决时间
            if (params.followCS.id){
                if (!chajianInitiativeInstance.followCS){
                    chajianInitiativeInstance.followTime = curDate
                }
            }

            // 处理人从无到有，加上联系时间
            if (params.solveCS.id){
                if (!chajianInitiativeInstance.solveCS){
                    chajianInitiativeInstance.contactTime = curDate
                }
            }

            chajianInitiativeInstance.properties = params

            if (chajianInitiativeInstance.contactTime){
                // 计算时间间隔
                chajianInitiativeInstance.contactInterval = DateUtils.DaysBetween(chajianInitiativeInstance.dateCreated, chajianInitiativeInstance.contactTime)
            }

            if (chajianInitiativeInstance.followTime && chajianInitiativeInstance.contactTime){
                // 计算时间间隔
                chajianInitiativeInstance.followInterval = DateUtils.DaysBetween(chajianInitiativeInstance.contactTime, chajianInitiativeInstance.followTime)
            }

            if (!chajianInitiativeInstance.hasErrors() && chajianInitiativeInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(chajianInitiativeInstance ,this.actionName)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), chajianInitiativeInstance.id])}"
                redirect(action: "show", id: chajianInitiativeInstance.id)
            }
            else {
                render(view: "edit", model: [chajianInitiativeInstance: chajianInitiativeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def chajianInitiativeInstance = ChajianInitiative.get(params.id)
        if (chajianInitiativeInstance) {
            try {
                chajianInitiativeInstance.delete(flush: true)
                userOperateService.operateMethodSelect(chajianInitiativeInstance ,this.actionName)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianInitiative.label', default: 'ChajianInitiative'), params.id])}"
            redirect(action: "list")
        }
    }

    /**
     * 搜索
     */
    def search = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.offset = params.offset ? params.offset : 0
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"

        log.debug params

        def searchlist = ChajianInitiative.createCriteria().list(params){
            // 店铺
            if (params.store_id){
                Store store = Store.get(params.store_id)
                eq('store', store)
            }
            // 顾客id
            if (params.userId){
                like('userId', "%" + params.userId + "%")
            }
            // 查件代码
            if (params.chajianCode_id){
                ChajianCode chajianCode = ChajianCode.get(params.chajianCode_id)
                eq('chajianCode', chajianCode)
            }
            // 订单编号
            if (params.logistics){
                like('logistics', "%" + params.logistics + "%")
            }
            // 处理人
            if (params.solveCSId){
                Hero hero = Hero.get(params.solveCSId)
                eq('solveCS', hero)
            }
            // 跟踪人
            if (params.followCSId){
                Hero hero = Hero.get(params.followCSId)
                eq('followCS', hero)
            }
            // 物流单号
            if (params.mobile){
                like('mobile', '%' + params.mobile + '%')
            }
            // 处理方式
            if (params.solveType){
                like('solveType', '%' + params.solveType + '%')
            }
            // 通知方式
            if (params.notifyMode_id){
                NotifyMode notifyMode = NotifyMode.get(params.notifyMode_id)
                eq('notifyMode', notifyMode)
            }
            // 跟踪
            if (params.followUp){
                boolean followUp = params.followUp
                eq('followUp', followUp)
            }
            // 签收
            if (params.signUp){
                boolean signUp = params.signUp
                eq('signUp', signUp)
            }
            // 登记时间
            if (params.dateCreatedStart && params.dateCreatedEnd){
                Date start = DateUtils.parseToDates(params.dateCreatedStart)
                Date end = DateUtils.parseToDates(params.dateCreatedEnd)
                ge('dateCreated',start)
                le('dateCreated',end)
            }
            // 联系时间
            if (params.contactTimeStart && params.contactTimeEnd){
                ge('contactTime',params.contactTimeStart)
                le('contactTime',params.contactTimeEnd)
            }
            // 跟踪时间
            if (params.followTimeStart && params.followTimeEnd){
                Date start = DateUtils.parseToDates(params.followTimeStart)
                Date end = DateUtils.parseToDates(params.followTimeEnd)
                ge('followTime',start)
                le('followTime',end)
            }



        }

        // 筛选后的查件代码
        def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.initiative)

        render(view: "list", model: [chajianInitiativeInstanceList: searchlist, chajianInitiativeInstanceTotal: searchlist.totalCount, params: params, chajianCodeList: chajianCodeList])
    }

    /**
     * 导出excel
     */
    def export = {
//		println params

        Date daysAgo = DateUtils.getDaysAgo(14)
        def list = ChajianInitiative.createCriteria().list(){
            ge('dateCreated',daysAgo)
        }

        response.setHeader("Content-disposition", "attachment; filename=chajianInitiative.${params.extension}")
        List fields = [
                "dateCreated",
                "store.name",
                "userId",
                "chajianCode.name",
                "logistics",
                "mobile",
                "solveType",
                "solveCS.name",
                "followCS.name",
                "contactTime",
                "followTime",
                "notifyMode.name",
                "followUp",
                "signUp",
                "contactInterval",
                "followInterval"
        ]
        Map labels = [
                "dateCreated":"创建时间",
                "store.name":"店铺",
                "userId":"顾客ID",
                "chajianCode.name":"查件代码",
                "logistics":"物流单号",
                "mobile":"手机号码",
                "solveType":"处理方式",
                "solveCS.name":"处理人",
                "followCS.name":"跟踪人",
                "contactTime":"联系时间",
                "followTime":"跟踪时间",
                "notifyMode.name":"提醒方式",
                "followUp":"是否跟踪",
                "signUp":"是否签收",
                "contactInterval":"联系间隔",
                "followInterval":"解决间隔"
        ]
        dataExportService.execlExport(params,request,response,list,fields,labels)
    }

    def importIndex = { render(view:"importIndex") }

    /**
     * 导入问题件
     */
    def importExcel = {
        List<ChajianInitiative> list
        if(params.excel){
            request.setCharacterEncoding("gbk")
            def f = request.getFile('excel')
            log.debug "file size: " + f.getSize()
            if(!f.empty) {
                log.debug "获取excel中的数据"

                try{
                    list = dataImportService.importChajianInitiative(f.getInputStream())
                    log.debug "list.size = " + list.size()
                }catch(Exception e){
                    log.error e
                    flash.message = '导入的文件类型或格式不正确'
                    render(view:"importIndex")
                    return
                }
            }
        }

        // render(view:"importIndex")
        def listCount = list ? list.size() : 0
        flash.message = "共上传成功${listCount}条数据！"
        redirect(action: "list")
    }
}
