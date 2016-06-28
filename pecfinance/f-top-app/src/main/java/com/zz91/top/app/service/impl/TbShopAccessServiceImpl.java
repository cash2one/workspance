/**
 * 
 */
package com.zz91.top.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shop;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.response.ShopGetResponse;
import com.zz91.top.app.config.TopConfig;
import com.zz91.top.app.domain.TbShopAccess;
import com.zz91.top.app.dto.TbShopAccessDto;
import com.zz91.top.app.persist.TbShopAccessMapper;
import com.zz91.top.app.persist.TbShopMapper;
import com.zz91.top.app.service.TbShopAccessService;
import com.zz91.util.http.HttpUtils;

/**
 * @author mays
 *
 */
@Component("shopService")
public class TbShopAccessServiceImpl implements TbShopAccessService {

	@Resource
	private TbShopAccessMapper tbShopAccessMapper;
	@Resource
	private TopConfig topConfig;
	@Resource
	private TbShopMapper tbShopMapper;
	
	@Override
	public Integer createOrUpdateShopToken(TbShopAccessDto dto) {
		
		JSONObject jobj=dto.getTbResponse();
		TbShopAccess shopAccess=new TbShopAccess();
		shopAccess.setAccessToken(jobj.getString("access_token"));
		shopAccess.setTokenType(jobj.getString("token_type"));
		shopAccess.setExpiresIn(jobj.getInt("expires_in"));
		shopAccess.setRefreshToken(jobj.getString("refresh_token"));
		shopAccess.setReExpiresIn(jobj.getInt("re_expires_in"));
		shopAccess.setR1ExpiresIn(jobj.getInt("r1_expires_in"));
		shopAccess.setR2ExpiresIn(jobj.getInt("r2_expires_in"));
		shopAccess.setW1ExpiresIn(jobj.getInt("w1_expires_in"));
		shopAccess.setW2ExpiresIn(jobj.getInt("w2_expires_in"));
		shopAccess.setTaobaoUserId(jobj.getString("taobao_user_id"));
		try {
			shopAccess.setTaobaoUserNick(URLDecoder.decode(jobj.getString("taobao_user_nick"), HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		dto.setShopAccess(shopAccess);
		
		TaobaoClient client=new DefaultTaobaoClient(topConfig.getUrl(), topConfig.getAppKey(), topConfig.getAppSecret());
		ShopGetRequest req=new ShopGetRequest();
		req.setFields("sid,cid,title,nick,desc,bulletin,pic_path,created,modified");
		req.setNick(shopAccess.getTaobaoUserNick());
		try {
			ShopGetResponse response = client.execute(req);
			Shop shop=response.getShop();
			
			shopAccess.setSid(shop.getSid());
//		shopAccess.setSid(0l);
//			
			//将 shop 信息写入DB
			Integer countShop=tbShopMapper.countShopBySid(shop.getSid());
			if(countShop==null || countShop.intValue()<=0){
				
				tbShopMapper.insertByTb(shop);
			}
			
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		Integer countShop = tbShopAccessMapper.countAccess(shopAccess.getTaobaoUserId());
		Integer result=0;
		if(countShop!=null && countShop.intValue() > 0){
			result = tbShopAccessMapper.update(shopAccess);
		}else{
			tbShopAccessMapper.insert(shopAccess);
			result = shopAccess.getId();
		}
		
		return result;
	}

}
