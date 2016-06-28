package com.nala.csd.cookie

import com.nala.csd.JinGuanShouHou;


class JinGuanShouHouCookieService {

	static transactional = true

	def serviceMethod() {
	}

	/**
	 * 把金冠售后表中填写的部分信息写入到cookie
	 * 有效期30天
	 * @param jinGuanShouHou
	 * @return
	 */
	def setJinGuanShouHouToCookie(JinGuanShouHou jinGuanShouHou, response){
		if (jinGuanShouHou){
			if (jinGuanShouHou.createCS){
				// 创建人
				response.setCookie("jinGuanShouHou.createCS.id", jinGuanShouHou.createCS.id + "", 60*60*24*30)
			}
		}
	}
}
