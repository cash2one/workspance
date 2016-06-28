package com.nala.csd.cookie

import com.nala.csd.ChajianInitiative

class ChajianInitiativeCookieService {

    static transactional = true

    def serviceMethod() {

    }
	
	/**
	 * 把查件主动表中填写的部分信息写入到cookie
	 * 有效期30天
	 * @param chajianInitiative
	 * @return
	 */
	def setChajianInitiativeToCookie(ChajianInitiative chajianInitiative, response){
		if (chajianInitiative){
			if (chajianInitiative.store){
				// 店铺
				response.setCookie("chajianInitiative.store.id", chajianInitiative.store.id + "", 60*60*24*30)
			}
			if (chajianInitiative.chajianCode){
				// 查件代码
				response.setCookie("chajianInitiative.chajianCode.id", chajianInitiative.chajianCode.id + "", 60*60*24*30)
			}
			if (chajianInitiative.solveCS){
				// 处理人
				response.setCookie("chajianInitiative.solveCS.id", chajianInitiative.solveCS.id + "", 60*60*24*30)
			}
			if (chajianInitiative.followCS){
				// 跟踪人
				response.setCookie("chajianInitiative.followCS.id", chajianInitiative.followCS.id + "", 60*60*24*30)
			}
		}
		
	}
}
