/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-20
 */
package com.ast.ast1949.web.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component
public class InitialAsto1949Client {

//	private RpcIoHandler client;
//	private RpcServiceBuilder builder;

//	@Autowired
//	private AdsGroupsService adsGroupsService;
//	@Autowired
//	private AdsService adsService;


	@PostConstruct
	public void startup(){
//		Set<RpcAddress> targets = new HashSet<RpcAddress>();
//		targets.add(new RpcSocketAddress("localhost",9010));
//		client = new RpcClientIoHandlerImpl(new RpcConfiguration("tcp://localhost:9010/client"));
//		client.startup();
//
//		builder = new RpcServiceBuilderImpl();
//		adsGroupsService = builder.buildRemoteServiceProxy(AdsGroupsService.class, targets, null, client, false);
	}

	@PreDestroy
	public void shutdown(){
//		client.shutdown();
	}
}
