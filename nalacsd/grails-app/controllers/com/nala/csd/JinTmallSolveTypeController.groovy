package com.nala.csd

class JinTmallSolveTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [jinTmallSolveTypeInstanceList: JinTmallSolveType.list(params), jinTmallSolveTypeInstanceTotal: JinTmallSolveType.count()]
    }

    def create = {
        def jinTmallSolveTypeInstance = new JinTmallSolveType()
        jinTmallSolveTypeInstance.properties = params
        return [jinTmallSolveTypeInstance: jinTmallSolveTypeInstance]
    }

    def save = {
        def jinTmallSolveTypeInstance = new JinTmallSolveType(params)
        if (jinTmallSolveTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), jinTmallSolveTypeInstance.id])}"
            redirect(action: "show", id: jinTmallSolveTypeInstance.id)
        }
        else {
            render(view: "create", model: [jinTmallSolveTypeInstance: jinTmallSolveTypeInstance])
        }
    }

    def show = {
        def jinTmallSolveTypeInstance = JinTmallSolveType.get(params.id)
        if (!jinTmallSolveTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [jinTmallSolveTypeInstance: jinTmallSolveTypeInstance]
        }
    }

    def edit = {
        def jinTmallSolveTypeInstance = JinTmallSolveType.get(params.id)
        if (!jinTmallSolveTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [jinTmallSolveTypeInstance: jinTmallSolveTypeInstance]
        }
    }

    def update = {
        def jinTmallSolveTypeInstance = JinTmallSolveType.get(params.id)
        if (jinTmallSolveTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (jinTmallSolveTypeInstance.version > version) {

                    jinTmallSolveTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType')] as Object[], "Another user has updated this JinTmallSolveType while you were editing")
                    render(view: "edit", model: [jinTmallSolveTypeInstance: jinTmallSolveTypeInstance])
                    return
                }
            }
            jinTmallSolveTypeInstance.properties = params
            if (!jinTmallSolveTypeInstance.hasErrors() && jinTmallSolveTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), jinTmallSolveTypeInstance.id])}"
                redirect(action: "show", id: jinTmallSolveTypeInstance.id)
            }
            else {
                render(view: "edit", model: [jinTmallSolveTypeInstance: jinTmallSolveTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def jinTmallSolveTypeInstance = JinTmallSolveType.get(params.id)
        if (jinTmallSolveTypeInstance) {
            try {
                jinTmallSolveTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'jinTmallSolveType.label', default: 'JinTmallSolveType'), params.id])}"
            redirect(action: "list")
        }
    }
}
