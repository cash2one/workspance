package com.nala.csd.cookie

import com.nala.csd.ChajianPassive;
import com.nala.csd.TmallShouhou

class TmallShouhouCookieService {

    static transactional = true

    def serviceMethod() {

    }
	
	def setTmallShouhouToCookie(TmallShouhou tmallShouhou, response){
		if (tmallShouhou){
			if (tmallShouhou.store){
				// 店铺
				response.setCookie("tmallShouhou.store.id", tmallShouhou.store.id + "", 60*60*24*30)
			}			
			if (tmallShouhou.createCS){
				// 创建人
				response.setCookie("tmallShouhou.createCS.id", tmallShouhou.createCS.id + "", 60*60*24*30)
			}
		}
		
	}
}
