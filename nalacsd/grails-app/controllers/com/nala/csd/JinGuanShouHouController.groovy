package com.nala.csd

import com.nala.common.utils.DateUtils
import com.nala.csd.jinGuanShouHou.Refund
import com.nala.csd.jinGuanShouHou.Remit
import com.nala.csd.jinGuanShouHou.Resend
import com.nala.csd.jinGuanShouHou.ReturnGoods
import com.nala.csd.common.utils.ParamsUtil
import com.nala.csd.jinGuanShouHou.Coupon
import com.nala.csd.jinGuanShouHou.ReturnGoodsConfirm

class JinGuanShouHouController {

	def dataExportService
	
	def jinGuanShouHouCookieService

    def userOperateService

    def springSecurityService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        def userId = springSecurityService?.currentUser?.id
        log.debug ("当前用户的用户indexid"+userId)

        redirect(action: "list", params: params, userId:userId)
    }



    //在建立中差评时，都增加了了相应的action
    def evaIndex = {
        redirect(action: "evaList", params: params)
    }

    //中差评导出14天以内的数据
    def evaExport = {
        Date daysAgo = DateUtils.getDaysAgo(14)
        def list = JinGuanShouHou.createCriteria().list(){
            ge('dateCreated',daysAgo)
            eq('tag','eva')
        }
        response.setHeader("Content-disposition", "attachment; filename=tradeAnalyze.${params.extension}")

        dataExportService.execlExport(params,request,response,list,fields,labels)
    }

    /**
     * 中差评导出当前数据
     */
    def evaExportCurData = {

        Map searchParams = ParamsUtil.resetParamsMap(params.searchParams)
        searchParams["max"] = 10000

        def list = getEvaSearchList(searchParams)

        // def tmpList = changeFeng2Yuan(list)

        response.setHeader("Content-disposition", "attachment; filename=tradeAnalyze.${params.extension}")

        dataExportService.execlExport(params,request,response,list,fields,labels)
    }



    def evaDelete = {
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        if (jinGuanShouHouInstance) {
            try {
                jinGuanShouHouInstance.delete(flush: true)
                userOperateService.operateMethodSelect(jinGuanShouHouInstance  ,this.actionName)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
                redirect(action: "evaList")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
                redirect(action: "evaShow", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
            redirect(action: "evaList")
        }
    }




    def evaSearch = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.offset = params.offset ? params.offset : 0
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"

        def searchlist = getEvaSearchList(params)

        // find someone who can not finished his job
        Map<Hero, Integer> heroJobs
        if (params.solveStatus){
            def solvelist = JinGuanShouHou.createCriteria().list(){
                SolveStatusEnum cur = SolveStatusEnum.getByCode(params.solveStatus)
                eq("tag","eva")
                eq('solveStatus', cur)
            }
            heroJobs = new HashMap<Hero, Integer>()
            solvelist.each{
                // 处理人
                Hero hero = it.solveCS
                Integer jobCount = 0
                if (heroJobs.containsKey(hero)){
                    jobCount = heroJobs.get(hero)
                }
                jobCount++
                heroJobs.put(hero, jobCount)
            }
        }


        render(view: "evaList", model: [jinGuanShouHouInstanceList: searchlist, jinGuanShouHouInstanceTotal: searchlist.totalCount, heroJobs: heroJobs, params: params])
    }


    def evaUpdate = {
        JinTmallSolveType solveType = JinTmallSolveType.get(params.solveType)
        params.solveType = solveType
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        if (jinGuanShouHouInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (jinGuanShouHouInstance.version > version) {

                    jinGuanShouHouInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou')] as Object[], "Another user has updated this JinGuanShouHou while you were editing")
                    render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                    return
                }
            }

            // 收到退货待确认项
            jinGuanShouHouInstance.confirms.clear()
            if (params.returnGoodsConfirmId){
                if(params.returnGoodsConfirmId.class.isArray()) {
                    List<String> idList = params.returnGoodsConfirmId
                    // // log.debug("idList.size() = " + idList.size())
                    if (idList.size() > 0){
                        idList.each {
                            def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(it))
                            if (returnGoodsConfirm){
                                jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                            }
                        }
                    }
                }else{
                    def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(params.returnGoodsConfirmId))
                    if (returnGoodsConfirm){
                        jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                    }

                }

            }

            // 问题类型选择了错发、漏发，并且有处理人，一定要填问题原因
            Integer qs = Integer.parseInt(params['questionStatus.id'])
            if (qs == 19 || qs == 18){
                if (params.solveCS.id){
                    if (!params.questionReason.id){
                        flash.message = "注意：问题类型是错发或者漏发，并且有处理人，一定要填问题原因！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                        return
                    }
                }

            }

            String curDate = DateUtils.parseToWebFormat2(new Date())
            // 解决方式从无到有，自动加解决时间
            if (params.solveType){
                if (!jinGuanShouHouInstance.solveType){
                    jinGuanShouHouInstance.solveTime = curDate
                }
            }

            // 处理人从无到有，加上联系时间
            if (params.solveCS?.id){
                if (!jinGuanShouHouInstance.solveCS){
                    jinGuanShouHouInstance.contactTime = curDate
                }
            }

            jinGuanShouHouInstance.properties = params

            if (jinGuanShouHouInstance.contactTime){
                // 计算时间间隔
                Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
                jinGuanShouHouInstance.lianxiJiange = DateUtils.DaysBetween(jinGuanShouHouInstance.dateCreated, contactDate)
            }

            if (jinGuanShouHouInstance.solveTime && jinGuanShouHouInstance.contactTime){
                // 计算时间间隔
                Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
                Date solveDate = DateUtils.parseToDate(jinGuanShouHouInstance.solveTime)
                jinGuanShouHouInstance.jiejueJiange = DateUtils.DaysBetween(contactDate, solveDate)
            }

            // 退货信息
            def returnGoods
            if (jinGuanShouHouInstance.returnGoods
                    || params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("4")))){
                returnGoods = jinGuanShouHouInstance.returnGoods
                if (!returnGoods){
                    returnGoods = new ReturnGoods()
                }
                returnGoods.properties = params

                if (params.returnDateStr){
                    Date returnDate = DateUtils.parseToDayDate(params.returnDateStr)
                    returnGoods.returnDate = returnDate
                }

                if (params.returnWuliuNo){
                    returnGoods.wuliuNo = params.returnWuliuNo
                }

                if (params.postageNumStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.postageNumStr)
                        fNum = fNum * 100
                        returnGoods.postageNum = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                        flash.message = "退货邮费金额错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
                        return
                    }
                }

                if (returnGoods.detail
                        || returnGoods.returnDate
                        || returnGoods.wuliuNo
                        || returnGoods.postageRole
                        || returnGoods.postageNum){
                    if (!returnGoods.save(flush: true)){
                        returnGoods.errors.each {
                            log.error it
                        }
                        flash.message = "保存退货信息失败！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
                        return
                    }else{
                        userOperateService.operateMethodSelect(returnGoods  ,this.actionName)
                    }
                    jinGuanShouHouInstance.returnGoods = returnGoods
                }
            }

            // 补发信息
            def resend
            if (jinGuanShouHouInstance.resend
                    || params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("2")) || params.solveType.id.equals(Long.parseLong("8")))){
                resend = jinGuanShouHouInstance.resend
                if (!resend){
                    resend = new Resend()
                }
                resend.properties = params

                if (params.bufaDateStr){
                    Date returnDate = DateUtils.parseToDayDate(params.bufaDateStr)
                    resend.date = returnDate
                }

                if (params.bufaWuliuNo){
                    resend.wuliuNo = params.bufaWuliuNo
                }

                if (params.bufaPostageNumStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.bufaPostageNumStr)
                        fNum = fNum * 100
                        resend.postageNum = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                        flash.message = "补发邮费金额错误！"
                        if (returnGoods){
                            render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods, resend: resend])
                        }else{
                            render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])
                        }

                        return
                    }
                }

                if (resend.date
                        || resend.wuliuNo
                        || resend.postageNum
                        || resend.goodsDetail
                        || resend.address
                        || resend.resender){
                    if (!resend.save(flush: true)){
                        resend.errors.each {
                            log.error it
                        }
                        flash.message = "保存补发信息失败！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])
                        return
                    }else{
                        userOperateService.operateMethodSelect(resend ,this.actionName)
                    }
                    jinGuanShouHouInstance.resend = resend
                }
            }

            // 打款信息
            def remit
            if (jinGuanShouHouInstance.remit
                    || params.solveType && (params.solveType.id.equals(Long.parseLong("4")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("6")) || params.solveType.id.equals(Long.parseLong("3")))){
                remit = jinGuanShouHouInstance.remit
                if (!remit){
                    remit = new Remit()
                }
                remit.properties = params

                if (params.remitDateStr){
                    Date remitDate = DateUtils.parseToDate(params.remitDateStr)
                    remit.remitDate = remitDate
                }

                if (params.remitNumProductStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumProductStr)
                        fNum = fNum * 100
                        remit.remitNumProduct = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumProductStr = ${params.remitNumProductStr} to double error."
                        flash.message = "打款金额(产品)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                        return
                    }
                }
                if (params.remitNumPostageStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumPostageStr)
                        fNum = fNum * 100
                        remit.remitNumPostage = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumPostageStr = ${params.remitNumPostageStr} to double error."
                        flash.message = "打款金额(邮费)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                        return
                    }
                }

                if (params.remitNumCommissionStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumCommissionStr)
                        fNum = fNum * 100
                        remit.remitNumCommission = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumCommissionStr = ${params.remitNumCommissionStr} to double error."
                        flash.message = "打款手续费错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])
                        return
                    }
                }

                if (remit.remitDate
                        || remit.remitNumPostage
                        || remit.remitNumProduct
                        || remit.remitNumCommission
                        || remit.remitOperator
                        || remit.remitStatusEnum){
                    if (!remit.save(flush: true)){
                        remit.errors.each {
                            log.error it
                        }
                        flash.message = "保存打款信息失败！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])
                        return
                    }else{
                        userOperateService.operateMethodSelect(remit ,this.actionName)
                    }
                    jinGuanShouHouInstance.remit = remit
                }

            }

            // 退款信息
            def refund
            if (jinGuanShouHouInstance.refund
                    || params.solveType && (params.solveType.id.equals(Long.parseLong("7")) || params.solveType.id.equals(Long.parseLong("5")))){
                refund = jinGuanShouHouInstance.refund
                if (!refund){
                    refund = new Refund()
                }
                refund.properties = params

                if (params.refundDateStr){
                    Date refundDate = DateUtils.parseToDate(params.refundDateStr)
                    refund.refundDate = refundDate
                }

                if (params.refundNumProductStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.refundNumProductStr)
                        fNum = fNum * 100
                        refund.refundNumProduct = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.refundNumProductStr = ${params.refundNumProductStr} to double error."
                        flash.message = "退款金额(产品)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])

                        return
                    }
                }

                if (params.refundNumPostageStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.refundNumPostageStr)
                        fNum = fNum * 100
                        refund.refundNumPostage = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.refundNumPostageStr = ${params.refundNumPostageStr} to double error."
                        flash.message = "退款金额(邮费)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])

                        return
                    }
                }

                if (refund.refundDate
                        || refund.refundNumProduct
                        || refund.refundNumPostage
                        || refund.refundOperator){
                    if (!refund.save(flush: true)){
                        refund.errors.each {
                            log.error it
                        }
                        flash.message = "保存退款信息失败！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])
                        return
                    }else{
                        userOperateService.operateMethodSelect(refund ,this.actionName)
                    }
                    jinGuanShouHouInstance.refund = refund
                }
            }

            // 处理方式选择了“赠送优惠券”，增加优惠券信息
            def coupon
            if (jinGuanShouHouInstance.coupon || (params.solveType && (params.solveType.id.equals(Long.parseLong("11"))))){
                coupon = new Coupon(params)

                if (params.couponDateStr){
                    Date couponDate = DateUtils.parseToDate(params.couponDateStr)
                    coupon.couponDate = couponDate
                }

                if (params.couponNumStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.couponNumStr)
                        fNum = fNum * 100
                        coupon.couponNum = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.couponNumStr = ${params.couponNumStr} to double error."
                        flash.message = "优惠券金额错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, coupon: coupon])

                        return
                    }
                }

                if (coupon.couponDate
                        || coupon.couponNum
                        || coupon.couponOperator){
                    if (!coupon.save(flush: true)){
                        coupon.errors.each {
                            log.error it
                        }
                        flash.message = "保存优惠券信息失败！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                        return
                    }else{
                        userOperateService.operateMethodSelect(coupon ,this.actionName)
                    }
                    jinGuanShouHouInstance.coupon = coupon
                }
            }

            // 如果变更问题类型，记得清空问题原因
            if (jinGuanShouHouInstance.questionStatus){
                def questionCuofa = QuestionType.findByQuestionDescription('错发')
                def questionLoufa = QuestionType.findByQuestionDescription('漏发')
                if (jinGuanShouHouInstance.questionStatus != questionCuofa && jinGuanShouHouInstance.questionStatus != questionLoufa){
                    jinGuanShouHouInstance.questionReason = null
                }
            }



            if (!jinGuanShouHouInstance.hasErrors() && jinGuanShouHouInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(jinGuanShouHouInstance  ,this.actionName)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), jinGuanShouHouInstance.id])}"
                redirect(action: "evaShow", id: jinGuanShouHouInstance.id)
            }
            else {
                render(view: "evaEdit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
            redirect(action: "evaList")
        }
    }


    def evaEdit = {
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        if (!jinGuanShouHouInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
            redirect(action: "list")
        }
        else {
            // log.debug 'evaEdit==================='
            def allQuestion = QuestionType.getAll()
            def userId = springSecurityService?.currentUser?.id
            log.debug ("当前用户的用户id"+userId)
            return [jinGuanShouHouInstance: jinGuanShouHouInstance,userId: userId]
        }
    }

    def evaShow = {
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        if (!jinGuanShouHouInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
            redirect(action: "evaList")
        }
        else {
            [jinGuanShouHouInstance: jinGuanShouHouInstance]
        }
    }


    def evaSave = {
        def solveType
        if (params.solveType){
            solveType = JinTmallSolveType.get(Integer.valueOf(params.solveType))
        }
        params.solveType = solveType

        def jinGuanShouHouInstance = new JinGuanShouHou(params)
        Date curDate = new Date()
        String curDateStr = DateUtils.parseToWebFormat2(curDate)

        // 自动加解决时间
        if (params.solveType && !params.solveType.toString().equals("none")){
            jinGuanShouHouInstance.solveTime = curDateStr
        }

        // 有处理人，加上联系时间
        if (params.solveCS?.id){
            jinGuanShouHouInstance.contactTime = curDateStr
        }

        if (jinGuanShouHouInstance.contactTime){
            // 计算时间间隔
            Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
            if (jinGuanShouHouInstance.dateCreated == null){
                jinGuanShouHouInstance.dateCreated = curDate
            }
            jinGuanShouHouInstance.lianxiJiange = DateUtils.DaysBetween(jinGuanShouHouInstance.dateCreated, contactDate)
        }

        if (jinGuanShouHouInstance.solveTime && jinGuanShouHouInstance.contactTime){
            // 计算时间间隔
            Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
            Date solveDate = DateUtils.parseToDate(jinGuanShouHouInstance.solveTime)
            jinGuanShouHouInstance.jiejueJiange = DateUtils.DaysBetween(contactDate, solveDate)
        }

        // 收到退货待确认项
        if (params.returnGoodsConfirmId){
            if(params.returnGoodsConfirmId.class.isArray()) {
                List<String> idList = params.returnGoodsConfirmId
                // log.debug("idList.size() = " + idList.size())
                if (idList.size() > 0){
                    idList.each {
                        def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(it))
                        if (returnGoodsConfirm){
                            // println("add returnGoodsConfirm: " + returnGoodsConfirm.name)
                            jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                        }
                    }
                }
            }else{
                def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(params.returnGoodsConfirmId))
                if (returnGoodsConfirm){
                    // println("add returnGoodsConfirm: " + returnGoodsConfirm.name)
                    jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                }
            }

        }

        // 问题类型快速选择
        if (params.questionStatusDescription){
            def questionType = QuestionType.findByQuestionDescription(params.questionStatusDescription)
            if (questionType){
                jinGuanShouHouInstance.questionStatus = questionType
            }else{
                flash.message = "未找到匹配的问题类型"
                render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                return
            }
        }

        //快速選擇打款操作人
        if (params.remitHero){
            def remitHero = Hero.findByName("params.remitHero")
            if (remitHero){
                jinGuanShouHouInstance.remit = remitHero
            }else{
                flash.message = "未找到匹配的打款操作人"
                render(view:"create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                return
            }
        }

        // 写cookie
        jinGuanShouHouCookieService.setJinGuanShouHouToCookie(jinGuanShouHouInstance, response)

        // 处理方式选择了"换货补发" or "退货打款", 增加退货信息
        def returnGoods
        if (params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("4")))){
            returnGoods = new ReturnGoods(params)

            if (params.returnDateStr){
                Date returnDate = DateUtils.parseToDayDate(params.returnDateStr)
                returnGoods.returnDate = returnDate
            }

            if (params.returnWuliuNo){
                returnGoods.wuliuNo = params.returnWuliuNo
            }

            if (params.postageNumStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.postageNumStr)
                    fNum = fNum * 100
                    returnGoods.postageNum = fNum.intValue()
                }catch(Exception e){
                    log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                    flash.message = "退货邮费金额错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
                    return
                }
            }

            if (returnGoods.detail
                    || returnGoods.returnDate
                    || returnGoods.wuliuNo
                    || returnGoods.postageRole
                    || returnGoods.postageNum){
                if (!returnGoods.save(flush: true)){
                    returnGoods.errors.each {
                        log.error it
                    }
                    flash.message = "保存退货信息失败！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
                    return
                }else{
                    userOperateService.operateMethodSelect(returnGoods  ,this.actionName)
                }
                jinGuanShouHouInstance.returnGoods = returnGoods
            }
        }

        // 处理方式选择了“直接补发” or “换货补发”，增加补发信息
        def resend
        if (params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("2")) || params.solveType.id.equals(Long.parseLong("9")))){
            resend = new Resend(params)

            if (params.bufaDateStr){
                Date returnDate = DateUtils.parseToDayDate(params.bufaDateStr)
                resend.date = returnDate
            }

            if (params.bufaWuliuNo){
                resend.wuliuNo = params.bufaWuliuNo
            }

            if (params.bufaPostageNumStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.bufaPostageNumStr)
                    fNum = fNum * 100
                    resend.postageNum = fNum.intValue()
                }catch(Exception e){
                    log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                    flash.message = "补发邮费金额错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])

                    return
                }
            }

            if (resend.date
                    || resend.wuliuNo
                    || resend.postageNum
                    || resend.goodsDetail
                    || resend.address
                    || resend.resender){
                if (!resend.save(flush: true)){
                    resend.errors.each {
                        log.error it
                    }
                    flash.message = "保存补发信息失败！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])
                    return
                }else{
                    userOperateService.operateMethodSelect(resend  ,this.actionName)
                }
                jinGuanShouHouInstance.resend = resend
            }

        }

        // 处理方式选择了“退货打款” or “退货退款” or “直接打款”，增加打款信息
        def remit
        if (params.solveType
                && (params.solveType.id.equals(Long.parseLong("4")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("6")) || params.solveType.id.equals(Long.parseLong("3")))){
            remit = new Remit(params)

            if (params.remitDateStr){
                Date remitDate = DateUtils.parseToDate(params.remitDateStr)
                remit.remitDate = remitDate
            }

            if (params.remitNumProductStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumProductStr)
                    fNum = fNum * 100
                    remit.remitNumProduct = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumProductStr = ${params.remitNumProductStr} to double error."
                    flash.message = "打款金额(产品)错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                    return
                }
            }
            if (params.remitNumPostageStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumPostageStr)
                    fNum = fNum * 100
                    remit.remitNumPostage = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumPostageStr = ${params.remitNumPostageStr} to double error."
                    flash.message = "打款金额(邮费)错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                    return
                }
            }
            if (params.remitNumCommissionStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumCommissionStr)
                    fNum = fNum * 100
                    remit.remitNumCommission = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumCommissionStr = ${params.remitNumCommissionStr} to double error."
                    flash.message = "打款手续费错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                    return
                }
            }

            if (remit.remitDate
                    || remit.remitNumPostage
                    || remit.remitNumProduct
                    || remit.remitNumCommission
                    || remit.remitOperator
                    || remit.remitStatusEnum){
                if (!remit.save(flush: true)){
                    remit.errors.each {
                        log.error it
                    }
                    flash.message = "保存打款信息失败！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])
                    return
                }else{
                    userOperateService.operateMethodSelect(remit  ,this.actionName)
                }
                jinGuanShouHouInstance.remit = remit
            }
        }

        // 处理方式选择了“退货退款” or “直接退款”，增加退款信息
        def refund
        if (params.solveType && (params.solveType.id.equals(Long.parseLong("7")) || params.solveType.id.equals(Long.parseLong("5")))){
            refund = new Refund(params)

            if (params.refundDateStr){
                Date refundDate = DateUtils.parseToDate(params.refundDateStr)
                refund.refundDate = refundDate
            }

            if (params.refundNumProductStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.refundNumProductStr)
                    fNum = fNum * 100
                    refund.refundNumProduct = fNum.intValue()
                }catch(Exception e){
                    log.error "params.refundNumProductStr = ${params.refundNumProductStr} to double error."
                    flash.message = "退款金额(产品)错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])

                    return
                }
            }

            if (params.refundNumPostageStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.refundNumPostageStr)
                    fNum = fNum * 100
                    refund.refundNumPostage = fNum.intValue()
                }catch(Exception e){
                    log.error "params.refundNumPostageStr = ${params.refundNumPostageStr} to double error."
                    flash.message = "退款金额(邮费)错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])

                    return
                }
            }

            if (refund.refundDate
                    || refund.refundNumPostage
                    || refund.refundNumProduct
                    || refund.refundOperator){
                if (!refund.save(flush: true)){
                    refund.errors.each {
                        log.error it
                    }
                    flash.message = "保存退款信息失败！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])
                    return
                }else{
                    userOperateService.operateMethodSelect(refund,this.actionName)
                }
                jinGuanShouHouInstance.refund = refund
            }
        }

        // 处理方式选择了“赠送优惠券”，增加优惠券信息
        def coupon
        if (params.solveType && (params.solveType.id.equals(Long.parseLong("11")))){
            coupon = new Coupon(params)

            if (params.couponDateStr){
                Date couponDate = DateUtils.parseToDate(params.couponDateStr)
                coupon.couponDate = couponDate
            }

            if (params.couponNumStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.couponNumStr)
                    fNum = fNum * 100
                    coupon.couponNum = fNum.intValue()
                }catch(Exception e){
                    log.error "params.couponNumStr = ${params.couponNumStr} to double error."
                    flash.message = "优惠券金额错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, coupon: coupon])

                    return
                }
            }

            if (coupon.couponDate
                    || coupon.couponNum
                    || coupon.couponOperator){
                if (!coupon.save(flush: true)){
                    coupon.errors.each {
                        log.error it
                    }
                    flash.message = "保存优惠券信息失败！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                    return
                }else{
                    userOperateService.operateMethodSelect(coupon  ,this.actionName)
                }
                jinGuanShouHouInstance.coupon = coupon
            }
        }

        // 问题类型选择了错发、漏发，并且有处理人，一定要填问题原因
        if (jinGuanShouHouInstance.questionStatus && (jinGuanShouHouInstance.questionStatus.id == 18 || jinGuanShouHouInstance.questionStatus.id == 19)){
            if (jinGuanShouHouInstance.solveCS != null){
                if (jinGuanShouHouInstance.questionReason == null){
                    flash.message = "注意：问题类型是错发或者漏发，并且有处理人，一定要填问题原因！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                    return
                }
            }
        }
        if (params.tag == 'eva') {
            jinGuanShouHouInstance.tag = params.tag
        }
        // log.debug '---------------'
        // log.debug params.tag
        // log.debug params.wuliNo
        if (params.wuliNo){
            jinGuanShouHouInstance.wuliuNo = params.wuliNo
        }

        if (jinGuanShouHouInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(jinGuanShouHouInstance  ,this.actionName)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), jinGuanShouHouInstance.id])}"
            redirect(action: "evaShow", id: jinGuanShouHouInstance.id)
        }
        else {
            render(view: "evaCreate", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
        }
    }


    def evaCreate = {
        // log.debug '------------------'

        def jinGuanShouHouInstance = new JinGuanShouHou()
        jinGuanShouHouInstance.properties = params

        jinGuanShouHouInstance.dateCreated = new Date()

        // cookie预存创建
        def createCSId = request.getCookie("jinGuanShouHou.createCS.id")
        if (createCSId){
            Hero hero = Hero.get(createCSId)
            if (hero){
                jinGuanShouHouInstance.createCS = hero
            }
        }

        // ${com.nala.csd.QuestionType.list()*.questionDescription}
        def questionTypeData = ""
        QuestionType.list().each {
            questionTypeData += "\""
            questionTypeData += it.questionDescription
            questionTypeData += "\", "
        }

        def userId = springSecurityService?.currentUser?.id
        return [jinGuanShouHouInstance: jinGuanShouHouInstance, questionTypeData: questionTypeData, userId: userId]
    }




    def evaList = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"

        //def jinGuanShouHouInstanceList = JinGuanShouHou.list(params)
        def jinGuanShouHouInstanceList =  JinGuanShouHou.createCriteria().list(params){
            eq("tag", 'eva')
        }
        // object id list
        def idList = new ArrayList<String>()

        jinGuanShouHouInstanceList.each{
            idList.add(it.id)
        }

       render(view: 'evaList',model: [jinGuanShouHouInstanceList: jinGuanShouHouInstanceList, jinGuanShouHouInstanceTotal:jinGuanShouHouInstanceList.totalCount, idList: idList] )
    }





    /**
     * 售后入口
     */
    def shouhouList = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        // log.debug('参数列表'+params.tab)

        def searchList = JinGuanShouHou.createCriteria().list(params) {
            if (params.tab){
                if (params.tab == "weiwanjie"){
                    // 完成进度=未完成
                    SolveStatusEnum cur = SolveStatusEnum.getByCode(0)
                    eq('solveStatus', cur)
                }else if (params.tab == "daiqueren"){
                    // 货款操作记录=收到退货待确认
                    MoneyRecordTypeEnum cur = MoneyRecordTypeEnum.getByCode(10)
                    eq('moneyRecordType', cur)

                    SolveStatusEnum solveStatusEnum = SolveStatusEnum.getByCode(0)
                    eq('solveStatus', solveStatusEnum)

                }else if (params.tab == "cuowu"){
                    // 货款操作记录=信息有误
                    MoneyRecordTypeEnum cur = MoneyRecordTypeEnum.getByCode(4)
                    eq('moneyRecordType', cur)

                    SolveStatusEnum solveStatusEnum = SolveStatusEnum.getByCode(0)
                    eq('solveStatus', solveStatusEnum)

                }else if (params.tab == "wanjie"){
                    // 完成进度=完成
                    SolveStatusEnum cur = SolveStatusEnum.getByCode(1)
                    eq('solveStatus', cur)
                }
            }
        }
        
        // 计算2个count
        int daiquerenCount = 0
        int cuowuCount = 0
        if (params.tab != "daiqueren"){
            def tmpResult = JinGuanShouHou.executeQuery("select count(id) from JinGuanShouHou j where j.solveStatus=\'" + SolveStatusEnum.no + "\' and j.moneyRecordType=\'" + MoneyRecordTypeEnum.returnWait + "\'");
            daiquerenCount = tmpResult.get(0)
        }
        if (params.tab != "cuowu"){
            def tmpResult = JinGuanShouHou.executeQuery("select count(id) from JinGuanShouHou j where j.solveStatus=\'" + SolveStatusEnum.no + "\' and j.moneyRecordType=\'" + MoneyRecordTypeEnum.wrong + "\'");
            cuowuCount = tmpResult.get(0)
        }
        // log.debug ('tab标识值'+params.tab)
        // log.debug ('searchList的长度'+searchList.totalCount)
        render(view: 'shouhou', model: [jinGuanShouHouInstanceList: searchList, jinGuanShouHouInstanceTotal: searchList.totalCount, daiquerenCount: daiquerenCount, cuowuCount: cuowuCount, params: params])
    }

    /**
     * 货款入口
     */
    def moneyList = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"

        def searchList = JinGuanShouHou.createCriteria().list(params) {
            if (params.tab){
                if (params.tab == "tuikuan"){
                    // 处理方式=直接退款 or 货款操作记录=收到退货可退款
                    or{
//                     SolveTypeEnum solveTypeEnum = SolveTypeEnum.getByCode(6)
                        JinTmallSolveType solveType = JinTmallSolveType.get(7)
                        eq('solveType', solveType)

                        MoneyRecordTypeEnum moneyRecordTypeEnum = MoneyRecordTypeEnum.getByCode(7)
                        eq('moneyRecordType', moneyRecordTypeEnum)
                    }
                }else if (params.tab == "dakuan"){
                    // 处理方式=直接打款 or 货款操作记录=收到退货可打款
                    or{
//                        SolveTypeEnum solveTypeEnum = SolveTypeEnum.getByCode(5)
                        JinTmallSolveType solveType = JinTmallSolveType.get(6)
                        eq('solveType', solveType)

                        MoneyRecordTypeEnum moneyRecordTypeEnum = MoneyRecordTypeEnum.getByCode(6)
                        eq('moneyRecordType', moneyRecordTypeEnum)
                    }
                }else if (params.tab == "bufa"){
                    // 处理方式=直接补发 or 处理方式=下次补发 or 货款操作记录=收到退货可打款可补发 or 货款操作记录=收到退货可补发 or 货款操作记录=已下单可补发
                    or{
//                        SolveTypeEnum solveTypeEnum = SolveTypeEnum.getByCode(1)
                        JinTmallSolveType solveType = JinTmallSolveType.get(2)
                        eq('solveType', solveType)
//                        solveTypeEnum = SolveTypeEnum.getByCode(8)
                        solveType = JinTmallSolveType.get(9)
                        eq('solveType', solveType)

                        MoneyRecordTypeEnum moneyRecordTypeEnum = MoneyRecordTypeEnum.getByCode(8)
                        eq('moneyRecordType', moneyRecordTypeEnum)
                        moneyRecordTypeEnum = MoneyRecordTypeEnum.getByCode(9)
                        eq('moneyRecordType', moneyRecordTypeEnum)
                        moneyRecordTypeEnum = MoneyRecordTypeEnum.getByCode(13)
                        eq('moneyRecordType', moneyRecordTypeEnum)
                    }
                }else if(params.tab == "zhongchap"){
                    or{
                        JinTmallSolveType solveType = JinTmallSolveType.get(16)
                        eq('solveType',solveType )
                    }
                }

                SolveStatusEnum cur = SolveStatusEnum.getByCode(0)
                eq('solveStatus', cur)
            }
        }

        // 计算1个count
        int dakuanCount = 0
        int zhongchapCount = 0
        if (params.tab != "dakuan"){
            def tmpResult = JinGuanShouHou.executeQuery("select count(id) from JinGuanShouHou j where j.solveStatus=\'" + SolveStatusEnum.no + "\' and (j.solveType=\'" + JinTmallSolveType.get(6).id + "\' or j.moneyRecordType=\'" + MoneyRecordTypeEnum.returnDakuan + "\')");
            def zhongchap = JinGuanShouHou.executeQuery("select count(id) from JinGuanShouHou j where j.solveStatus=\'"+ SolveStatusEnum.no +"\' and j.solveType=\'"+ JinTmallSolveType.get(16).id +"\'")
            zhongchapCount = zhongchap.get(0)
            dakuanCount = tmpResult.get(0)
        }

        render(view: 'moneyList', model: [jinGuanShouHouInstanceList: searchList, jinGuanShouHouInstanceTotal: searchList.totalCount, dakuanCount: dakuanCount, zhongchapCount:zhongchapCount])
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
		params.sort = params.sort ? params.sort : "dateCreated"
		params.order = params.order ? params.order : "desc"

        def userId = springSecurityService?.currentUser?.id
		def jinGuanShouHouInstanceList = JinGuanShouHou.list(params)

		// object id list
		def idList = new ArrayList<String>()
		jinGuanShouHouInstanceList.each{
			idList.add(it.id)
		}
        log.debug ("当前用户的用户listid"+userId)

        [jinGuanShouHouInstanceList: jinGuanShouHouInstanceList, jinGuanShouHouInstanceTotal: JinGuanShouHou.count(), idList: idList, userId:userId]
    }

    def create = {
        def jinGuanShouHouInstance = new JinGuanShouHou()
        jinGuanShouHouInstance.properties = params
		
		jinGuanShouHouInstance.dateCreated = new Date()


		// cookie预存创建
		def createCSId = request.getCookie("jinGuanShouHou.createCS.id")
		if (createCSId){
			Hero hero = Hero.get(createCSId)
			if (hero){
				jinGuanShouHouInstance.createCS = hero
			}
		}

        // ${com.nala.csd.QuestionType.list()*.questionDescription}
        def questionTypeData = ""
        QuestionType.list().each {
            questionTypeData += "\""
            questionTypeData += it.questionDescription
            questionTypeData += "\", "
        }

        log.debug ("问题类型" + questionTypeData)

        def heroName = ""
        Hero.list().each {
           heroName += "\""
           heroName += it?.name
           heroName += "\","
        }
        log.debug ("英雄" + heroName)
        def userId = springSecurityService?.currentUser?.id

        return [jinGuanShouHouInstance: jinGuanShouHouInstance, questionTypeData: questionTypeData, userId: userId, heroName: heroName]
    }

    def save = {
        // log.debug ('从save表单传过来的params数据'+params)
        def solveType
        if (params.solveType){
            solveType = JinTmallSolveType.get(Integer.valueOf(params.solveType))
        }

        params.solveType = solveType
        def jinGuanShouHouInstance = new JinGuanShouHou(params)

		Date curDate = new Date()
		String curDateStr = DateUtils.parseToWebFormat2(curDate)
		
		// 自动加解决时间
		if (params.solveType && !params.solveType.toString().equals("none")){
			jinGuanShouHouInstance.solveTime = curDateStr
		}
		
		// 有处理人，加上联系时间		
		if (params.solveCS?.id){
			jinGuanShouHouInstance.contactTime = curDateStr
		}
		
		if (jinGuanShouHouInstance.contactTime){
			// 计算时间间隔
			Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
			if (jinGuanShouHouInstance.dateCreated == null){
				jinGuanShouHouInstance.dateCreated = curDate
			}
			jinGuanShouHouInstance.lianxiJiange = DateUtils.DaysBetween(jinGuanShouHouInstance.dateCreated, contactDate)
		}
		
		if (jinGuanShouHouInstance.solveTime && jinGuanShouHouInstance.contactTime){
			// 计算时间间隔
			Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
			Date solveDate = DateUtils.parseToDate(jinGuanShouHouInstance.solveTime)
			jinGuanShouHouInstance.jiejueJiange = DateUtils.DaysBetween(contactDate, solveDate)
		}

        // 收到退货待确认项
        if (params.returnGoodsConfirmId){
            if(params.returnGoodsConfirmId.class.isArray()) {
                List<String> idList = params.returnGoodsConfirmId
                // log.debug("idList.size() = " + idList.size())
                if (idList.size() > 0){
                    idList.each {
                        def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(it))
                        if (returnGoodsConfirm){
                            // println("add returnGoodsConfirm: " + returnGoodsConfirm.name)
                            jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                        }
                    }
                }
            }else{
                def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(params.returnGoodsConfirmId))
                if (returnGoodsConfirm){
                    // println("add returnGoodsConfirm: " + returnGoodsConfirm.name)
                    jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                }
            }

        }

        // 问题类型快速选择
        if (params.questionStatusDescription){
            def questionType = QuestionType.findByQuestionDescription(params.questionStatusDescription)
            if (questionType){
                jinGuanShouHouInstance.questionStatus = questionType
            }else{
                flash.message = "未找到匹配的问题类型"
                render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                return
            }
        }
		
		// 写cookie
		jinGuanShouHouCookieService.setJinGuanShouHouToCookie(jinGuanShouHouInstance, response)
		
		// 处理方式选择了"换货补发" or "退货打款", 增加退货信息
		def returnGoods
		if (params.solveType && (params.solveType.id.equals(Long.parseLong("3")) ||  params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("4")))){
			returnGoods = new ReturnGoods(params)
				
			if (params.returnDateStr){
				Date returnDate = DateUtils.parseToDayDate(params.returnDateStr)
				returnGoods.returnDate = returnDate
			}
			
			if (params.returnWuliuNo){
				returnGoods.wuliuNo = params.returnWuliuNo
			}
			
			if (params.postageNumStr){
				Float fNum = 0.0
				try {
					fNum = Float.parseFloat(params.postageNumStr)
					fNum = fNum * 100
					returnGoods.postageNum = fNum.intValue()
				}catch(Exception e){
					log.error "params.postageNumStr = ${params.postageNumStr} to double error."
					flash.message = "退货邮费金额错误！"
					render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
					return
				}
			}
			
			if (returnGoods.detail
				|| returnGoods.returnDate
				|| returnGoods.wuliuNo
				|| returnGoods.postageRole
				|| returnGoods.postageNum){
				if (!returnGoods.save(flush: true)){
					returnGoods.errors.each {
                        log.error it
                    }
                    flash.message = "保存退货信息失败！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
                    return
                }else{
                    userOperateService.operateMethodSelect(returnGoods  ,this.actionName)
                }
				jinGuanShouHouInstance.returnGoods = returnGoods
			}
		}

		// log.debug ('参数'+params.solveType)
        // // log.debug ('参数'+params.solveType.id.class)

		// 处理方式选择了“直接补发” or “换货补发”，增加补发信息 params.solveType.equals("huanhuo") || params.solveType.equals("bufa") || params.solveType.equals("xiacibufa")
		def resend
		if (params.solveType && (params.solveType.id.equals(Long.parseLong("3"))|| params.solveType.id.equals(Long.parseLong("2"))||params.solveType.id.equals(Long.parseLong("9")) )){
			resend = new Resend(params)
			// log.debug '--------------直接补发或换货补发'
            if (params.bufaDateStr){
                // log.debug ("时间"+params.bufaDateStr)
				Date returnDate = DateUtils.parseToDayDate(params.bufaDateStr)
				resend.date = returnDate
			}
			
			if (params.bufaWuliuNo){
				resend.wuliuNo = params.bufaWuliuNo
			}
			
			if (params.bufaPostageNumStr){
				Float fNum = 0.0
				try {
					fNum = Float.parseFloat(params.bufaPostageNumStr)
					fNum = fNum * 100
					resend.postageNum = fNum.intValue()
				}catch(Exception e){
					log.error "params.postageNumStr = ${params.postageNumStr} to double error."
					flash.message = "补发邮费金额错误！"
					render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])
					
					return
				}
			}
			
			if (resend.date
				|| resend.wuliuNo
				|| resend.postageNum
				|| resend.goodsDetail
				|| resend.address
				|| resend.resender){
				if (!resend.save(flush: true)){
                    resend.errors.each {
						log.error it
					}
					flash.message = "保存补发信息失败！"
					render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])
					return
				}else{
                    userOperateService.operateMethodSelect(resend  ,this.actionName)
                }
				jinGuanShouHouInstance.resend = resend
			}
			
		}
		
		// 处理方式选择了“退货打款” or “退货退款” or “直接打款”，增加打款信息  params.solveType.equals("tuihuodakuan") || params.solveType.equals("tuihuotuikuan") || params.solveType.equals("zhijiedakuan") || params.solveType.equals("huanhuo")
		def remit
		if (params.solveType
			&& (params.solveType.id.equals(Long.parseLong("4")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("6")) || params.solveType.id.equals(Long.parseLong("3")))){
			remit = new Remit(params)
			
			if (params.remitDateStr){
				Date remitDate = DateUtils.parseToDate(params.remitDateStr)
				remit.remitDate = remitDate
			}
			
			if (params.remitNumProductStr){
				Float fNum = 0.0
				try {
					fNum = Float.parseFloat(params.remitNumProductStr)
					fNum = fNum * 100
					remit.remitNumProduct = fNum.intValue()
				}catch(Exception e){
					log.error "params.remitNumProductStr = ${params.remitNumProductStr} to double error."
					flash.message = "打款金额(产品)错误！"
					render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])
					
					return
				}
			}
            if (params.remitNumPostageStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumPostageStr)
                    fNum = fNum * 100
                    remit.remitNumPostage = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumPostageStr = ${params.remitNumPostageStr} to double error."
                    flash.message = "打款金额(邮费)错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                    return
                }
            }
            if (params.remitNumCommissionStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumCommissionStr)
                    fNum = fNum * 100
                    remit.remitNumCommission = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumCommissionStr = ${params.remitNumCommissionStr} to double error."
                    flash.message = "打款手续费错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                    return
                }
            }

			if (remit.remitDate
				|| remit.remitNumPostage
                || remit.remitNumProduct
                || remit.remitNumCommission
				|| remit.remitOperator
                || remit.remitStatusEnum){
				if (!remit.save(flush: true)){
                   remit.errors.each {
						log.error it
					}
					flash.message = "保存打款信息失败！"
					render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])
					return
				}else{
                    userOperateService.operateMethodSelect(remit  ,this.actionName)
                }
				jinGuanShouHouInstance.remit = remit
			}
		}
		
		// 处理方式选择了“退货退款” or “直接退款”，增加退款信息 params.solveType.equals("zhijietuikuan") || params.solveType.equals("tuihuotuikuan")
		def refund
		if (params.solveType && (params.solveType?.id.equals(Long.parseLong("7")) || params.solveType?.id.equals(Long.parseLong("5")))){
			refund = new Refund(params)
			
			if (params.refundDateStr){
				Date refundDate = DateUtils.parseToDate(params.refundDateStr)
				refund.refundDate = refundDate
			}
			
			if (params.refundNumProductStr){
				Float fNum = 0.0
				try {
					fNum = Float.parseFloat(params.refundNumProductStr)
					fNum = fNum * 100
					refund.refundNumProduct = fNum.intValue()
				}catch(Exception e){
					log.error "params.refundNumProductStr = ${params.refundNumProductStr} to double error."
					flash.message = "退款金额(产品)错误！"
					render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])
					
					return
				}
			}

            if (params.refundNumPostageStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.refundNumPostageStr)
                    fNum = fNum * 100
                    refund.refundNumPostage = fNum.intValue()
                }catch(Exception e){
                    log.error "params.refundNumPostageStr = ${params.refundNumPostageStr} to double error."
                    flash.message = "退款金额(邮费)错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])

                    return
                }
            }

			if (refund.refundDate
				|| refund.refundNumPostage
                || refund.refundNumProduct
				|| refund.refundOperator){
				if (!refund.save(flush: true)){
                    refund.errors.each {
						log.error it
					}
					flash.message = "保存退款信息失败！"
					render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])
					return
				}else{
                    userOperateService.operateMethodSelect(refund,this.actionName)
                }
				jinGuanShouHouInstance.refund = refund
			}
		}

        // 处理方式选择了“赠送优惠券”，增加优惠券信息
        def coupon
        if (params.solveType && (params.solveType?.id.equals(Long.parseLong("11")))){
            coupon = new Coupon(params)

            if (params.couponDateStr){
                Date couponDate = DateUtils.parseToDate(params.couponDateStr)
                coupon.couponDate = couponDate
            }

            if (params.couponNumStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.couponNumStr)
                    fNum = fNum * 100
                    coupon.couponNum = fNum.intValue()
                }catch(Exception e){
                    log.error "params.couponNumStr = ${params.couponNumStr} to double error."
                    flash.message = "优惠券金额错误！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, coupon: coupon])

                    return
                }
            }

            if (coupon.couponDate
                    || coupon.couponNum
                    || coupon.couponOperator){
                if (!coupon.save(flush: true)){
                    coupon.errors.each {
                        log.error it
                    }
                    flash.message = "保存优惠券信息失败！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                    return
                }else{
                    userOperateService.operateMethodSelect(coupon  ,this.actionName)
                }
                jinGuanShouHouInstance.coupon = coupon
            }
        }

        // 问题类型选择了错发、漏发，并且有处理人，一定要填问题原因
        if (jinGuanShouHouInstance.questionStatus && (jinGuanShouHouInstance.questionStatus.id == 18 || jinGuanShouHouInstance.questionStatus.id == 19)){
            if (jinGuanShouHouInstance.solveCS != null){
                if (jinGuanShouHouInstance.questionReason == null){
                    flash.message = "注意：问题类型是错发或者漏发，并且有处理人，一定要填问题原因！"
                    render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                    return
                }
            }
        }

        if (jinGuanShouHouInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(jinGuanShouHouInstance  ,this.actionName)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), jinGuanShouHouInstance.id])}"
            redirect(action: "show", id: jinGuanShouHouInstance.id)
        }
        else {
            render(view: "create", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
        }
    }

    def show = {
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        if (!jinGuanShouHouInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
            redirect(action: "list", userId:userId)
        }
        else {
            def userId = springSecurityService?.currentUser?.id
            log.debug ("当前用户的用户showid"+userId)
            [jinGuanShouHouInstance: jinGuanShouHouInstance,userId: userId]
        }
    }

    def edit = {
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        if (!jinGuanShouHouInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
            redirect(action: "list",  userId:userId)
        }
        else {
			def allQuestion = QuestionType.getAll()
            def userId = springSecurityService?.currentUser?.id
            log.debug ("当前用户的用户id"+userId)
            return [jinGuanShouHouInstance: jinGuanShouHouInstance,userId: userId]
        }
    }

    def update = {
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        if (params.solveType){
            JinTmallSolveType solveType = JinTmallSolveType.get(Integer.valueOf(params.solveType))
            params.solveType = solveType
        }
        if (jinGuanShouHouInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (jinGuanShouHouInstance.version > version) {
                    
                    jinGuanShouHouInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou')] as Object[], "Another user has updated this JinGuanShouHou while you were editing")
                    render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                    return
                }
            }

            // 收到退货待确认项
            jinGuanShouHouInstance.confirms.clear()
            if (params.returnGoodsConfirmId){
                if(params.returnGoodsConfirmId.class.isArray()) {
                    List<String> idList = params.returnGoodsConfirmId
                    // log.debug("idList.size() = " + idList.size())
                    if (idList.size() > 0){
                        idList.each {
                            def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(it))
                            if (returnGoodsConfirm){
                                jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                            }
                        }
                    }
                }else{
                    def returnGoodsConfirm = ReturnGoodsConfirm.get(Long.parseLong(params.returnGoodsConfirmId))
                    if (returnGoodsConfirm){
                        jinGuanShouHouInstance.addToConfirms(returnGoodsConfirm)
                    }

                }

            }

            // 问题类型选择了错发、漏发，并且有处理人，一定要填问题原因
            Integer qs = Integer.parseInt(params['questionStatus.id'])
            if (qs == 19 || qs == 18){
                if (params.solveCS.id){
                    if (!params.questionReason.id){
                        flash.message = "注意：问题类型是错发或者漏发，并且有处理人，一定要填问题原因！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                        return
                    }
                }

            }
			
			String curDate = DateUtils.parseToWebFormat2(new Date())
			// 解决方式从无到有，自动加解决时间
			if (params.solveType){
				if (!jinGuanShouHouInstance.solveType){
					jinGuanShouHouInstance.solveTime = curDate
				}
			}
			
			// 处理人从无到有，加上联系时间
			if (params.solveCS.id){
				if (!jinGuanShouHouInstance.solveCS){
					jinGuanShouHouInstance.contactTime = curDate
				}
			}
			
			jinGuanShouHouInstance.properties = params
			
			if (jinGuanShouHouInstance.contactTime){
				// 计算时间间隔
				Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
				jinGuanShouHouInstance.lianxiJiange = DateUtils.DaysBetween(jinGuanShouHouInstance.dateCreated, contactDate)
			}
			
			if (jinGuanShouHouInstance.solveTime && jinGuanShouHouInstance.contactTime){
				// 计算时间间隔
				Date contactDate = DateUtils.parseToDate(jinGuanShouHouInstance.contactTime)
				Date solveDate = DateUtils.parseToDate(jinGuanShouHouInstance.solveTime)
				jinGuanShouHouInstance.jiejueJiange = DateUtils.DaysBetween(contactDate, solveDate)
			}

			// 退货信息
			def returnGoods
			if (jinGuanShouHouInstance.returnGoods
				|| params.solveType && (params.solveType?.id.equals(Long.parseLong("3")) || params.solveType?.id.equals(Long.parseLong("5")) || params.solveType?.id.equals(Long.parseLong("4")))){
				returnGoods = jinGuanShouHouInstance.returnGoods
				if (!returnGoods){
					returnGoods = new ReturnGoods()
				}
				returnGoods.properties = params
					
				if (params.returnDateStr){
					Date returnDate = DateUtils.parseToDayDate(params.returnDateStr)
					returnGoods.returnDate = returnDate
				}
				
				if (params.returnWuliuNo){
					returnGoods.wuliuNo = params.returnWuliuNo
				}
				
				if (params.postageNumStr){
					Float fNum = 0.0
					try {
						fNum = Float.parseFloat(params.postageNumStr)
						fNum = fNum * 100
						returnGoods.postageNum = fNum.intValue()
					}catch(Exception e){
						log.error "params.postageNumStr = ${params.postageNumStr} to double error."
						flash.message = "退货邮费金额错误！"
						render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
						return
					}
				}
				
				if (returnGoods.detail
					|| returnGoods.returnDate
					|| returnGoods.wuliuNo
					|| returnGoods.postageRole
					|| returnGoods.postageNum){
					if (!returnGoods.save(flush: true)){
                        returnGoods.errors.each {
							log.error it
						}
						flash.message = "保存退货信息失败！"
						render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods])
						return
					}else{
                        userOperateService.operateMethodSelect(returnGoods  ,this.actionName)
                    }
					jinGuanShouHouInstance.returnGoods = returnGoods
				}
			}
			
			// 补发信息
			def resend
			if (jinGuanShouHouInstance.resend
				|| params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("2")) || params.solveType.id.equals(Long.parseLong("9")))){
				resend = jinGuanShouHouInstance.resend
				if (!resend){
					resend = new Resend()
				}
                log.debug ("此处有个问题"+params)
                if(params.resender?.id == "")
                    resend.resender = null
				resend.properties = params

                if (params.bufaDateStr){
					Date returnDate = DateUtils.parseToDayDate(params.bufaDateStr)
					resend.date = returnDate
				}else{
                    resend.date = null
                }

				if (params.bufaWuliuNo){
					resend.wuliuNo = params.bufaWuliuNo
				}
				
				if (params.bufaPostageNumStr){
					Float fNum = 0.0
					try {
						fNum = Float.parseFloat(params.bufaPostageNumStr)
						fNum = fNum * 100
						resend.postageNum = fNum.intValue()
					}catch(Exception e){
						log.error "params.postageNumStr = ${params.postageNumStr} to double error."
						flash.message = "补发邮费金额错误！"
						if (returnGoods){
							render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, returnGoods: returnGoods, resend: resend])
						}else{
							render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])
						}
						
						return
					}
				}
				if (resend.date
					|| resend.wuliuNo
					|| resend.postageNum
					|| resend.goodsDetail
					|| resend.address
					|| resend.resender){
					if (!resend.save(flush:true)){
						resend.errors.each {
							log.error it
						}
						flash.message = "保存补发信息失败！"
						render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, resend: resend])
                    return
                    }else{
                        userOperateService.operateMethodSelect(resend ,this.actionName)
                    }
                    jinGuanShouHouInstance.resend = resend
				}
			}
			
			// 打款信息
			def remit
			if (jinGuanShouHouInstance.remit
				|| params.solveType && (params.solveType?.id.equals(Long.parseLong("4")) || params.solveType?.id.equals(Long.parseLong("5")) || params.solveType?.id.equals(Long.parseLong("6")) || params.solveType?.id.equals(Long.parseLong("3")))){
				remit = jinGuanShouHouInstance.remit
				if (!remit){
					remit = new Remit()
				}
				remit.properties = params
				
				if (params.remitDateStr){
					Date remitDate = DateUtils.parseToDate(params.remitDateStr)
					remit.remitDate = remitDate
				}

                if (params.remitNumProductStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumProductStr)
                        fNum = fNum * 100
                        remit.remitNumProduct = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumProductStr = ${params.remitNumProductStr} to double error."
                        flash.message = "打款金额(产品)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                        return
                    }
                }
                if (params.remitNumPostageStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumPostageStr)
                        fNum = fNum * 100
                        remit.remitNumPostage = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumPostageStr = ${params.remitNumPostageStr} to double error."
                        flash.message = "打款金额(邮费)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])

                        return
                    }
                }

                if (params.remitNumCommissionStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumCommissionStr)
                        fNum = fNum * 100
                        remit.remitNumCommission = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumCommissionStr = ${params.remitNumCommissionStr} to double error."
                        flash.message = "打款手续费错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])
                        return
                    }
                }
				
				if (remit.remitDate
                    || remit.remitNumPostage
                    || remit.remitNumProduct
                    || remit.remitNumCommission
					|| remit.remitOperator
                    || remit.remitStatusEnum){
					if (!remit.save(flush: true)){
                       remit.errors.each {
							log.error it
						}
						flash.message = "保存打款信息失败！"
						render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, remit: remit])
						return
					}else{
                        userOperateService.operateMethodSelect(remit ,this.actionName)
                    }
					jinGuanShouHouInstance.remit = remit
				}
				
			}
			
			// 退款信息
			def refund
			if (jinGuanShouHouInstance.refund
				|| params.solveType && (params.solveType.id.equals(Long.parseLong("7")) || params.solveType.id.equals(Long.parseLong("5")))){
				refund = jinGuanShouHouInstance.refund
				if (!refund){
					refund = new Refund()
				}
				refund.properties = params
				
				if (params.refundDateStr){
					Date refundDate = DateUtils.parseToDate(params.refundDateStr)
					refund.refundDate = refundDate
				}

                if (params.refundNumProductStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.refundNumProductStr)
                        fNum = fNum * 100
                        refund.refundNumProduct = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.refundNumProductStr = ${params.refundNumProductStr} to double error."
                        flash.message = "退款金额(产品)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])

                        return
                    }
                }

                if (params.refundNumPostageStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.refundNumPostageStr)
                        fNum = fNum * 100
                        refund.refundNumPostage = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.refundNumPostageStr = ${params.refundNumPostageStr} to double error."
                        flash.message = "退款金额(邮费)错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])

                        return
                    }
                }
				
				if (refund.refundDate
					|| refund.refundNumProduct
                    || refund.refundNumPostage
					|| refund.refundOperator){
					if (!refund.save(flush: true)){
						refund.errors.each {
							log.error it
						}
						flash.message = "保存退款信息失败！"
						render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, refund: refund])
						return
					}else{
                        userOperateService.operateMethodSelect(refund ,this.actionName)
                    }
					jinGuanShouHouInstance.refund = refund
				}
			}

            // 处理方式选择了“赠送优惠券”，增加优惠券信息
            def coupon
            if (jinGuanShouHouInstance.coupon || (params.solveType && (params.solveType.id.equals(Long.parseLong("11"))))){
                coupon = new Coupon(params)

                if (params.couponDateStr){
                    Date couponDate = DateUtils.parseToDate(params.couponDateStr)
                    coupon.couponDate = couponDate
                }

                if (params.couponNumStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.couponNumStr)
                        fNum = fNum * 100
                        coupon.couponNum = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.couponNumStr = ${params.couponNumStr} to double error."
                        flash.message = "优惠券金额错误！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance, coupon: coupon])

                        return
                    }
                }

                if (coupon.couponDate
                        || coupon.couponNum
                        || coupon.couponOperator){
                    if (!coupon.save(flush: true)){
                        coupon.errors.each {
                            log.error it
                        }
                        flash.message = "保存优惠券信息失败！"
                        render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
                        return
                    }else{
                        userOperateService.operateMethodSelect(coupon ,this.actionName)
                    }
                    jinGuanShouHouInstance.coupon = coupon
                }
            }
			
			// 如果变更问题类型，记得清空问题原因
			if (jinGuanShouHouInstance.questionStatus){				
				def questionCuofa = QuestionType.findByQuestionDescription('错发')
				def questionLoufa = QuestionType.findByQuestionDescription('漏发')				
				if (jinGuanShouHouInstance.questionStatus != questionCuofa && jinGuanShouHouInstance.questionStatus != questionLoufa){
					jinGuanShouHouInstance.questionReason = null
				}				
			}



            if (!jinGuanShouHouInstance.hasErrors() && jinGuanShouHouInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(jinGuanShouHouInstance  ,this.actionName)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), jinGuanShouHouInstance.id])}"
                def userId = springSecurityService?.currentUser?.id
                redirect(action: "show", id: jinGuanShouHouInstance.id,userId:userId)
            }
            else {
                render(view: "edit", model: [jinGuanShouHouInstance: jinGuanShouHouInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def jinGuanShouHouInstance = JinGuanShouHou.get(params.id)
        log.info("params-->${params}")
        if (jinGuanShouHouInstance) {
            try {
                jinGuanShouHouInstance.delete(flush: true)
                userOperateService.operateMethodSelect(jinGuanShouHouInstance  ,this.actionName)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
                redirect(action: "list",  params: [tp: "true"])
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinGuanShouHou.label', default: 'JinGuanShouHou'), params.id])}"
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
		// log.debug('进入search'+ params.tab)
		def searchlist = getSearchList(params)
		
		// find someone who can not finished his job
		Map<Hero, Integer> heroJobs		
		if (params.solveStatus){
			def solvelist = JinGuanShouHou.createCriteria().list(){
				SolveStatusEnum cur = SolveStatusEnum.getByCode(params.solveStatus)
				eq('solveStatus', cur)
			}
			heroJobs = new HashMap<Hero, Integer>()
			solvelist.each{
				// 处理人
				Hero hero = it.solveCS
				Integer jobCount = 0
				if (heroJobs.containsKey(hero)){
					jobCount = heroJobs.get(hero)										
				}
				jobCount++
				heroJobs.put(hero, jobCount)
			}
		}
		
		
		render(view: "list", model: [jinGuanShouHouInstanceList: searchlist, jinGuanShouHouInstanceTotal: searchlist.totalCount, heroJobs: heroJobs, params: params])
	}
	
	/**
	 * 依据传入的参数进行搜索
	 * @param params
	 * @return
	 */
	def getSearchList(Map params) {
		def searchlist = JinGuanShouHou.createCriteria().list(params){
			// 顾客id
			if (params.userId){
				like('userId', "%" + params.userId + "%")
			}
			// 订单编号
			if (params.tradeId){
				like('tradeId', "%" + params.tradeId + "%")
			}
			// 处理人
			if (params.solveCSId){
				Hero hero = Hero.get(params.solveCSId)
				eq('solveCS', hero)
			}
			// 发起人
			if (params.createCSId){
				Hero hero = Hero.get(params.createCSId)
				eq('createCS', hero)
			}
			// 问题类型
			if (params.questionStatusId){
				int typeId = Integer.parseInt(params.questionStatusId)
				def type = QuestionType.get(typeId)
				eq('questionStatus', type)
			}
			// 商品明细
			if (params.itemDetail){
				like('itemDetail', "%" + params.itemDetail + "%")
			}

            //增加分别各个状态
            if (params.tab){
                // log.debug '进去'
                if (params.tab == "weiwanjie"){
                    // 完成进度=未完成
                    SolveStatusEnum cur = SolveStatusEnum.getByCode(0)
                    eq('solveStatus', cur)
                }else if (params.tab == "daiqueren"){
                    // 货款操作记录=收到退货待确认
                    MoneyRecordTypeEnum cur = MoneyRecordTypeEnum.getByCode(10)
                    eq('moneyRecordType', cur)

                    SolveStatusEnum solveStatusEnum = SolveStatusEnum.getByCode(0)
                    eq('solveStatus', solveStatusEnum)

                }else if (params.tab == "cuowu"){
                    // 货款操作记录=信息有误
                    MoneyRecordTypeEnum cur = MoneyRecordTypeEnum.getByCode(4)
                    eq('moneyRecordType', cur)

                    SolveStatusEnum solveStatusEnum = SolveStatusEnum.getByCode(0)
                    eq('solveStatus', solveStatusEnum)

                }else if (params.tab == "wanjie"){
                    // 完成进度=完成
                    SolveStatusEnum cur = SolveStatusEnum.getByCode(1)
                    eq('solveStatus', cur)
                }
            }








			// 退回单号
			if (params.wuliuNo){
                // 先用退回单号去搜索退货信息
                // 然后用退货信息去搜索售后记录
                def returnGoodsList = getReturnGoodsByWuliuNo(params.wuliuNo)
                if (returnGoodsList){
                    or{
                        returnGoodsList.each {
                            // log.debug('add search condition: returnGoods.wuliuNo = ' + it.wuliuNo)
                            eq('returnGoods', it)
                        }
                    }
                }
			}



			// 联系优先级
			if (params.contactStatus){
				ContactStatusEnum cur = ContactStatusEnum.getByCode(params.contactStatus)
				eq('contactStatus', cur)
			}




//			// 处理方式
//			if (params.solveType){
//				SolveTypeEnum cur = SolveTypeEnum.getByCode(params.solveType)
//				eq('solveType', cur)
//			}

            // 联系优先级
            if (params.solveType){
               def solveType = JinTmallSolveType.get(Integer.valueOf(params.solveType))
               eq('solveType', solveType)
            }

			// 货款操作记录
			if (params.moneyRecordType){
				MoneyRecordTypeEnum cur = MoneyRecordTypeEnum.getByCode(params.moneyRecordType)
				eq('moneyRecordType', cur)
			}
			// 完成进度
			if (params.solveStatus){
				SolveStatusEnum cur = SolveStatusEnum.getByCode(params.solveStatus)
				eq('solveStatus', cur)
			    // log.debug('完成进度'+cur)
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
			// 解决时间
			if (params.solveTimeStart && params.solveTimeEnd){
				ge('solveTime',params.solveTimeStart)
				le('solveTime',params.solveTimeEnd)
			}
		}
		
		return searchlist;
	}

    def getEvaSearchList(Map params) {
        def searchlist = JinGuanShouHou.createCriteria().list(params){
            // 顾客id
            if (params.userId){
                like('userId', "%" + params.userId + "%")
            }
            // 订单编号
            if (params.tradeId){
                like('tradeId', "%" + params.tradeId + "%")
            }
            // 处理人
            if (params.solveCSId){
                Hero hero = Hero.get(params.solveCSId)
                eq('solveCS', hero)
            }
            // 发起人
            if (params.createCSId){
                Hero hero = Hero.get(params.createCSId)
                eq('createCS', hero)
            }
            // 问题类型
            if (params.questionStatusId){
                int typeId = Integer.parseInt(params.questionStatusId)
                def type = QuestionType.get(typeId)
                eq('questionStatus', type)
            }
            // 商品明细
            if (params.itemDetail){
                like('itemDetail', "%" + params.itemDetail + "%")
            }
//            // 退回单号
//            if (params.wuliuNo){
//                // 先用退回单号去搜索退货信息
//                // 然后用退货信息去搜索售后记录
//                def returnGoodsList = getReturnGoodsByWuliuNo(params.wuliuNo)
//                if (returnGoodsList){
//                    or{
//                        returnGoodsList.each {
//                            // log.debug('add search condition: returnGoods.wuliuNo = ' + it.wuliuNo)
//                            eq('returnGoods', it)
//                        }
//                    }
//                }
//            }

            // 退回单号
            if (params.wuliuNo){
                like('wuliuNo', "%" + params.wuliuNo + "%")
            }

            // 联系优先级
            if (params.contactStatus){
                ContactStatusEnum cur = ContactStatusEnum.getByCode(params.contactStatus)
                eq('contactStatus', cur)
            }
            // 处理方式
            if (params.solveType){
//                SolveTypeEnum cur = SolveTypeEnum.getByCode(params.solveType)
//                eq('solveType', cur)
                  JinTmallSolveType solveType = JinTmallSolveType.get(Integer.valueOf(params.solveType))
                  eq('solveType', solveType)

            }
            // 货款操作记录
            if (params.moneyRecordType){
                MoneyRecordTypeEnum cur = MoneyRecordTypeEnum.getByCode(params.moneyRecordType)
                eq('moneyRecordType', cur)
            }
            // 完成进度
            if (params.solveStatus){
                SolveStatusEnum cur = SolveStatusEnum.getByCode(params.solveStatus)
                eq('solveStatus', cur)
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
            // 解决时间
            if (params.solveTimeStart && params.solveTimeEnd){
                ge('solveTime',params.solveTimeStart)
                le('solveTime',params.solveTimeEnd)
            }
            eq("tag", "eva")
        }
        // log.debug searchlist.totalCount
        return searchlist;
    }





    /**
     * 搜索匹配的退货信息
     * @param wuliuNo 物流单号
     */
    def getReturnGoodsByWuliuNo(String wuliuNo){
        def searchlist
        if (wuliuNo){
            searchlist = ReturnGoods.createCriteria().list(){
                like('wuliuNo', "%" + wuliuNo + "%")
            }
        }
        return searchlist
    }

    public static List fields = [
            "dateCreated",
            "createCS.name",
            "faqiTime",
            "userId",
            "tradeId",
            "contactStatus.description",
            "tel",
            "zhidingTime",
            "questionStatus.questionDescription",
            "questionReason.name",
            "itemTitle",
            "itemCode",
            "sku",
            "itemNum",
            "solveCS.name",
            "contactTime",
            "solveTime",
            "contactTimes.description",
            "itemDetail",
            "solveType.name",
            "moneyRecordType.description",
            "confirms.list()*.name",
            "wrongReason",
            "solveStatus.description",
            "noSolveReason",
            "wuliuNo",
            "lianxiJiange",
            "jiejueJiange",
            "returnGoods.detail",
            "returnGoods.returnDate",
            "returnGoods.wuliuNo",
            "returnGoods.used",
            "returnGoods.postageRole",
            "returnGoods.postageNum",
            "resend.date",
            "resend.wuliuNo",
            "resend.postageNum",
            "resend.goodsDetail",
            "resend.address",
            "resend.resender.name",
            "remit.remitDate",
            "remit.remitNum",
            "remit.remitOperator.name",
            "remit.remitNumProduct",
            "remit.remitNumPostage",
            "remit.remitNumCommission",
            "refund.refundDate",
            "refund.refundNum",
            "refund.refundOperator.name",
            "refund.refundNumProduct",
            "refund.refundNumPostage"
    ]
    public static Map labels = [
            "dateCreated":"创建时间",
            "createCS.name":"创建人",
            "faqiTime":"发起时间",
            "userId":"顾客id",
            "tradeId":"订单编号",
            "contactStatus.description":"联系优先级",
            "tel":"联系电话",
            "zhidingTime":"指定时间",
            "questionStatus.questionDescription":"问题类型",
            "questionReason.name":"问题原因",
            "itemTitle":"产品简称",
            "itemCode":"产品代码",
            "sku":"产品规格",
            "itemNum":"产品数量",
            "solveCS.name":"处理人",
            "contactTime":"联系时间",
            "solveTime":"解决时间",
            "contactTimes.description":"联系次数",
            "itemDetail":"商品明细",
            "solveType.name":"处理方式",
            "moneyRecordType.description":"货款操作记录",
            "confirms.list()*.name":"退货待确认项",
            "wrongReason":"信息错误说明",
            "solveStatus.description":"完成进度",
            "noSolveReason":"未完成说明",
            "wuliuNo":"退回单号",
            "lianxiJiange":"创建时间到联系时间的间隔",
            "jiejueJiange":"联系时间到解决时间的间隔",
            "returnGoods.detail":"收到退货明细",
            "returnGoods.returnDate":"收到退货日期",
            "returnGoods.wuliuNo":"退货快递和单号",
            "returnGoods.used":"是否使用",
            "returnGoods.postageRole":"邮费承担方",
            "returnGoods.postageNum":"邮费金额(分)",
            "resend.date":"补发日期",
            "resend.wuliuNo":"补发单号",
            "resend.postageNum":"补发邮费(分)",
            "resend.goodsDetail":"补发产品明细",
            "resend.address":"补发地址",
            "resend.resender.name":"补发人",
            "remit.remitDate":"打款时间",
            "remit.remitNum":"打款金额(分)",
            "remit.remitOperator.name":"打款操作人",
            "remit.remitNumProduct":"打款金额(产品)(分)",
            "remit.remitNumPostage":"打款金额(邮费)(分)",
            "remit.remitNumCommission":"打款手续费(分)",
            "refund.refundDate":"退款时间",
            "refund.refundNum":"退款金额(分)",
            "refund.refundOperator.name":"退款操作人",
            "refund.refundNumProduct":"退款金额(产品)(分)",
            "refund.refundNumPostage":"退款金额(邮费)(分)"
    ]

    /**
     * 把列表中的金额单位由分转换成分
     * @param list
     * @return
     */
    def changeFeng2Yuan(def list){
        def tmpList
        if (list && list.size() > 0){
            list.each {
                tmpList.add(it)

                if (it.returnGoods && it.returnGoods.postageNum){
                    it.returnGoods.postageNum = it.returnGoods.postageNum / 100
                }
                if (it.resend && it.resend.postageNum){
                    it.resend.postageNum = it.resend.postageNum / 100
                }
                if (it.remit){
                    if (it.remit.remitNum){
                        it.remit.remitNum = it.remit.remitNum / 100
                    }
                    if (it.remit.remitNumProduct){
                        it.remit.remitNumProduct = it.remit.remitNumProduct / 100
                    }
                    if (it.remit.remitNumCommission){
                        it.remit.remitNumCommission = it.remit.remitNumCommission / 100
                    }
                    if (it.remit.remitNumPostage){
                        it.remit.remitNumPostage = it.remit.remitNumPostage / 100
                    }
                }
                if (it.refund){
                    if (it.refund.refundNum){
                        it.refund.refundNum = it.refund.refundNum / 100
                    }
                    if (it.refund.refundNumProduct){
                        it.refund.refundNumProduct = it.refund.refundNumProduct / 100
                    }
                    if (it.refund.refundNumPostage){
                        it.refund.refundNumPostage = it.refund.refundNumPostage / 100
                    }
                }

            }
        }
        return list
    }

	/**
	 * 导出所有数据
	 */
	def export = {
		Date daysAgo = DateUtils.getDaysAgo(14)
		def list = JinGuanShouHou.createCriteria().list(){
			ge('dateCreated',daysAgo)
		}
		// def list = JinGuanShouHou.getAll()
        // def tmpList = changeFeng2Yuan(list)
		
		response.setHeader("Content-disposition", "attachment; filename=tradeAnalyze.${params.extension}")
		
		dataExportService.execlExport(params,request,response,list,fields,labels)
	}
	
	/**
	 * 导出当前数据
	 */
	def exportCurData = {

		Map searchParams = ParamsUtil.resetParamsMap(params.searchParams)
		searchParams["max"] = 10000
		
		def list = getSearchList(searchParams)

        // def tmpList = changeFeng2Yuan(list)

		response.setHeader("Content-disposition", "attachment; filename=tradeAnalyze.${params.extension}")
		
		dataExportService.execlExport(params,request,response,list,fields,labels)
	}

}
