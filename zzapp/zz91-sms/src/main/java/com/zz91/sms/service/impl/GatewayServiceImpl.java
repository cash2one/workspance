package com.zz91.sms.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.sms.common.ZZSms;
import com.zz91.sms.dao.GatewayDao;
import com.zz91.sms.domain.Gateway;
import com.zz91.sms.service.GatewayService;
import com.zz91.util.Assert;
import com.zz91.util.ClassHelper;

@Component("gatewayService")
public class GatewayServiceImpl implements GatewayService {
	@Resource
	private GatewayDao gatewayDao;

	@Override
	public Integer create(Gateway gateway) {
		if (gateway.getEnabled() == 1) {
			ZZSms zzsms = getZZSms(gateway);
			if (zzsms != null) {
				GatewayService.CACHE_GATEWAY.put(gateway.getCode(), zzsms);
			}
		}
		return gatewayDao.insert(gateway);
	}

	@Override
	public void disabled(Integer id, String code) {
		if (GatewayService.CACHE_GATEWAY.get(code) != null) {
			GatewayService.CACHE_GATEWAY.remove(code);
		}
		gatewayDao.updateEnabled(id, ENABLED_FALSE);
	}

	@Override
	public void enabled(Integer id, String code) {
		if (GatewayService.CACHE_GATEWAY.get(code) == null) {
			Gateway gateway = queryOne(id);
			ZZSms zzsms = getZZSms(gateway);
			if (zzsms != null) {
				GatewayService.CACHE_GATEWAY.put(code, zzsms);
			}
		}
		gatewayDao.updateEnabled(id, ENABLED_TRUE);
	}

	@Override
	public boolean gatwayTest(String to, Integer id) {
		return false;
	}

	@Override
	public Integer remove(Integer id) {
		Assert.notNull(id, "the id can not be null!");
		return gatewayDao.delete(id);
	}

	@Override
	public Integer update(Gateway gateway) {
		return gatewayDao.update(gateway);
	}

	@Override
	public List<Gateway> query(Integer enabled) {
		return gatewayDao.query(enabled);
	}

	@Override
	public Gateway queryOne(Integer id) {
		return gatewayDao.queryOne(id);
	}

	@Override
	public void initGateway() {
		List<Gateway> list = query(ENABLED_TRUE);
		for (Gateway obj : list) {
			ZZSms zzsms = getZZSms(obj);
			if (zzsms != null) {
				CACHE_GATEWAY.put(obj.getCode(), zzsms);
			}
		}
	}

	@Override
	public Object balance(String code) {
		ZZSms sms = (ZZSms) GatewayService.CACHE_GATEWAY.get(code);
		return sms.balance();
	}

	public ZZSms getZZSms(Gateway gateway) {
		ZZSms zzsms = null;
		try {
			zzsms = (ZZSms) ClassHelper.load(gateway.getApiClasspath(),gateway.getApiJar()).newInstance();
		} catch (MalformedURLException e) {
			zzsms = null;
		} catch (InstantiationException e) {
			zzsms = null;
		} catch (IllegalAccessException e) {
			zzsms = null;
		} catch (ClassNotFoundException e) {
			zzsms = null;
		}
		return zzsms;
	}

}
