package com.nala.csd.cookie

import com.nala.csd.B2CConsult

class B2CConsultCookieService {

    static transactional = true

    def serviceMethod() {

    }
	
	def setB2CConsultToCookie(B2CConsult b2CConsult, response){
		if (b2CConsult){						
			if (b2CConsult.createCS){
				// 创建人
				response.setCookie("b2CConsult.createCS.id", b2CConsult.createCS.id + "", 60*60*24*30)
			}
		}
		
	}
}
