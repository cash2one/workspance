/**
 * 
 */
package com.zz91.ep.util;

import com.zz91.ep.dto.AreaDto;
import com.zz91.util.cache.CodeCachedUtil;

/**
 * @author mays
 *
 */
public class AreaUtil {
	
	public static AreaDto buildArea(String areaCode){
		AreaDto dto=new AreaDto();
		dto.setAreaCode(areaCode);
		
		do {
			if(areaCode==null || "".equals(areaCode)){
				break;
			}
			
			int arealn=areaCode.length();
			
			if(arealn<8){
				break;
			}
			dto.setCountry(getValue(areaCode, 8));
			
			if(arealn<12){
				break;
			}
			
			dto.setProvince(getValue(areaCode, 12));
			
			if(arealn<16){
				break;
			}
			dto.setCity(getValue(areaCode, 16));
			
			if(arealn<20){
				break;
			}
			dto.setArea(getValue(areaCode, 20));
			
		} while (false);
		
		return dto;
	}
	
	private static String getValue(String areaCode, int cutln){
		return CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, areaCode.substring(0, cutln));
	}
	
}
