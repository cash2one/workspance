package com.nala.csd

class QuestionSourceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [questionSourceInstanceList: QuestionSource.list(params), questionSourceInstanceTotal: QuestionSource.count()]
    }

    def create = {
        def questionSourceInstance = new QuestionSource()
        questionSourceInstance.properties = params
        return [questionSourceInstance: questionSourceInstance]
    }

    def save = {
        def questionSourceInstance = new QuestionSource(params)
        if (questionSourceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), questionSourceInstance.id])}"
            redirect(action: "show", id: questionSourceInstance.id)
        }
        else {
            render(view: "create", model: [questionSourceInstance: questionSourceInstance])
        }
    }

    def show = {
        def questionSourceInstance = QuestionSource.get(params.id)
        if (!questionSourceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), params.id])}"
            redirect(action: "list")
        }
        else {
            [questionSourceInstance: questionSourceInstance]
        }
    }

    def edit = {
        def questionSourceInstance = QuestionSource.get(params.id)
        if (!questionSourceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [questionSourceInstance: questionSourceInstance]
        }
    }

    def update = {
        def questionSourceInstance = QuestionSource.get(params.id)
        if (questionSourceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (questionSourceInstance.version > version) {
                    
                    questionSourceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'questionSource.label', default: 'QuestionSource')] as Object[], "Another user has updated this QuestionSource while you were editing")
                    render(view: "edit", model: [questionSourceInstance: questionSourceInstance])
                    return
                }
            }
            questionSourceInstance.properties = params
            if (!questionSourceInstance.hasErrors() && questionSourceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), questionSourceInstance.id])}"
                redirect(action: "show", id: questionSourceInstance.id)
            }
            else {
                render(view: "edit", model: [questionSourceInstance: questionSourceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def questionSourceInstance = QuestionSource.get(params.id)
        if (questionSourceInstance) {
            try {
                questionSourceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'questionSource.label', default: 'QuestionSource'), params.id])}"
            redirect(action: "list")
        }
    }
}
