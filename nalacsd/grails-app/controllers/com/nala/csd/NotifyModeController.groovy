package com.nala.csd

class NotifyModeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [notifyModeInstanceList: NotifyMode.list(params), notifyModeInstanceTotal: NotifyMode.count()]
    }

    def create = {
        def notifyModeInstance = new NotifyMode()
        notifyModeInstance.properties = params
        return [notifyModeInstance: notifyModeInstance]
    }

    def save = {
        def notifyModeInstance = new NotifyMode(params)
        if (notifyModeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), notifyModeInstance.id])}"
            redirect(action: "show", id: notifyModeInstance.id)
        }
        else {
            render(view: "create", model: [notifyModeInstance: notifyModeInstance])
        }
    }

    def show = {
        def notifyModeInstance = NotifyMode.get(params.id)
        if (!notifyModeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), params.id])}"
            redirect(action: "list")
        }
        else {
            [notifyModeInstance: notifyModeInstance]
        }
    }

    def edit = {
        def notifyModeInstance = NotifyMode.get(params.id)
        if (!notifyModeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [notifyModeInstance: notifyModeInstance]
        }
    }

    def update = {
        def notifyModeInstance = NotifyMode.get(params.id)
        if (notifyModeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (notifyModeInstance.version > version) {
                    
                    notifyModeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'notifyMode.label', default: 'NotifyMode')] as Object[], "Another user has updated this NotifyMode while you were editing")
                    render(view: "edit", model: [notifyModeInstance: notifyModeInstance])
                    return
                }
            }
            notifyModeInstance.properties = params
            if (!notifyModeInstance.hasErrors() && notifyModeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), notifyModeInstance.id])}"
                redirect(action: "show", id: notifyModeInstance.id)
            }
            else {
                render(view: "edit", model: [notifyModeInstance: notifyModeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def notifyModeInstance = NotifyMode.get(params.id)
        if (notifyModeInstance) {
            try {
                notifyModeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'notifyMode.label', default: 'NotifyMode'), params.id])}"
            redirect(action: "list")
        }
    }
}
