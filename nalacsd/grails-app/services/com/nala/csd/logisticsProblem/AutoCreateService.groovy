package com.nala.csd.logisticsProblem

import com.nala.csd.ExpressProblem
import com.nala.csd.LogisticsProblem

class AutoCreateService {

    static transactional = true

    def serviceMethod(List<ExpressProblem> list) {
		if (list){
			log.debug "list.size = $list.size"
			
			list.each {
				LogisticsProblem lp = new LogisticsProblem()
				lp.dateCreated = new Date()
				lp.expressProblem = it
				if (!lp.save(flush:true)){
					log.error("Save LogisticsProblem error, express problem id is: " + it.id)
				}
			}
		}
    }
}
