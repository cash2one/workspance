package com.nala.csd

class SolveTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [solveTypeInstanceList: SolveType.list(params), solveTypeInstanceTotal: SolveType.count()]
    }

    def create = {
        def solveTypeInstance = new SolveType()
        solveTypeInstance.properties = params
        return [solveTypeInstance: solveTypeInstance]
    }

    def save = {
        def solveTypeInstance = new SolveType(params)
        if (solveTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'solveType.label', default: 'SolveType'), solveTypeInstance.id])}"
            redirect(action: "show", id: solveTypeInstance.id)
        }
        else {
            render(view: "create", model: [solveTypeInstance: solveTypeInstance])
        }
    }

    def show = {
        def solveTypeInstance = SolveType.get(params.id)
        if (!solveTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'solveType.label', default: 'SolveType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [solveTypeInstance: solveTypeInstance]
        }
    }

    def edit = {
        def solveTypeInstance = SolveType.get(params.id)
        if (!solveTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'solveType.label', default: 'SolveType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [solveTypeInstance: solveTypeInstance]
        }
    }

    def update = {
        def solveTypeInstance = SolveType.get(params.id)
        if (solveTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (solveTypeInstance.version > version) {
                    
                    solveTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'solveType.label', default: 'SolveType')] as Object[], "Another user has updated this SolveType while you were editing")
                    render(view: "edit", model: [solveTypeInstance: solveTypeInstance])
                    return
                }
            }
            solveTypeInstance.properties = params
            if (!solveTypeInstance.hasErrors() && solveTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'solveType.label', default: 'SolveType'), solveTypeInstance.id])}"
                redirect(action: "show", id: solveTypeInstance.id)
            }
            else {
                render(view: "edit", model: [solveTypeInstance: solveTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'solveType.label', default: 'SolveType'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def solveTypeInstance = SolveType.get(params.id)
        if (solveTypeInstance) {
            try {
                solveTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'solveType.label', default: 'SolveType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'solveType.label', default: 'SolveType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'solveType.label', default: 'SolveType'), params.id])}"
            redirect(action: "list")
        }
    }
}
