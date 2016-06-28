package com.nala.csd

class DealPriorityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [dealPriorityInstanceList: DealPriority.list(params), dealPriorityInstanceTotal: DealPriority.count()]
    }

    def create = {
        def dealPriorityInstance = new DealPriority()
        dealPriorityInstance.properties = params
        return [dealPriorityInstance: dealPriorityInstance]
    }

    def save = {
        def dealPriorityInstance = new DealPriority(params)
        if (dealPriorityInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), dealPriorityInstance.id])}"
            redirect(action: "show", id: dealPriorityInstance.id)
        }
        else {
            render(view: "create", model: [dealPriorityInstance: dealPriorityInstance])
        }
    }

    def show = {
        def dealPriorityInstance = DealPriority.get(params.id)
        if (!dealPriorityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), params.id])}"
            redirect(action: "list")
        }
        else {
            [dealPriorityInstance: dealPriorityInstance]
        }
    }

    def edit = {
        def dealPriorityInstance = DealPriority.get(params.id)
        if (!dealPriorityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [dealPriorityInstance: dealPriorityInstance]
        }
    }

    def update = {
        def dealPriorityInstance = DealPriority.get(params.id)
        if (dealPriorityInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (dealPriorityInstance.version > version) {

                    dealPriorityInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'dealPriority.label', default: 'DealPriority')] as Object[], "Another user has updated this DealPriority while you were editing")
                    render(view: "edit", model: [dealPriorityInstance: dealPriorityInstance])
                    return
                }
            }
            dealPriorityInstance.properties = params
            if (!dealPriorityInstance.hasErrors() && dealPriorityInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), dealPriorityInstance.id])}"
                redirect(action: "show", id: dealPriorityInstance.id)
            }
            else {
                render(view: "edit", model: [dealPriorityInstance: dealPriorityInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def dealPriorityInstance = DealPriority.get(params.id)
        if (dealPriorityInstance) {
            try {
                dealPriorityInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dealPriority.label', default: 'DealPriority'), params.id])}"
            redirect(action: "list")
        }
    }
}
