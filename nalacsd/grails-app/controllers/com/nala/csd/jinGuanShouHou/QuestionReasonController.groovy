package com.nala.csd.jinGuanShouHou

class QuestionReasonController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [questionReasonInstanceList: QuestionReason.list(params), questionReasonInstanceTotal: QuestionReason.count()]
    }

    def create = {
        def questionReasonInstance = new QuestionReason()
        questionReasonInstance.properties = params
        return [questionReasonInstance: questionReasonInstance]
    }

    def save = {
        def questionReasonInstance = new QuestionReason(params)
        if (questionReasonInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), questionReasonInstance.id])}"
            redirect(action: "show", id: questionReasonInstance.id)
        }
        else {
            render(view: "create", model: [questionReasonInstance: questionReasonInstance])
        }
    }

    def show = {
        def questionReasonInstance = QuestionReason.get(params.id)
        if (!questionReasonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), params.id])}"
            redirect(action: "list")
        }
        else {
            [questionReasonInstance: questionReasonInstance]
        }
    }

    def edit = {
        def questionReasonInstance = QuestionReason.get(params.id)
        if (!questionReasonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [questionReasonInstance: questionReasonInstance]
        }
    }

    def update = {
        def questionReasonInstance = QuestionReason.get(params.id)
        if (questionReasonInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (questionReasonInstance.version > version) {
                    
                    questionReasonInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'questionReason.label', default: 'QuestionReason')] as Object[], "Another user has updated this QuestionReason while you were editing")
                    render(view: "edit", model: [questionReasonInstance: questionReasonInstance])
                    return
                }
            }
            questionReasonInstance.properties = params
            if (!questionReasonInstance.hasErrors() && questionReasonInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), questionReasonInstance.id])}"
                redirect(action: "show", id: questionReasonInstance.id)
            }
            else {
                render(view: "edit", model: [questionReasonInstance: questionReasonInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def questionReasonInstance = QuestionReason.get(params.id)
        if (questionReasonInstance) {
            try {
                questionReasonInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionReason.label', default: 'QuestionReason'), params.id])}"
            redirect(action: "list")
        }
    }
}
