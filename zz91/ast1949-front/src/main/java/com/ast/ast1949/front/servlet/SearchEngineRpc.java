/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-20
 */
package com.ast.ast1949.front.servlet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class SearchEngineRpc {

//	private RpcServiceBuilderImpl rpcServiceBuilder ;
//	private RpcIoHandlerImpl client;
//	private String SERVER_ADDRESS = "tcp://localhost:8081/client";
//	private Set<RpcAddress> targets;

	private String clientAddress 					= "tcp://127.0.0.1";
	private Integer clientPort 						= 9991;
	private String rpcUrl							= "/client";

	private Set<String> targets 					= new HashSet<String>();
	private boolean serverFilter 					= false ;
//	private Set<RpcAddress> targetRpcAddress		= new HashSet<RpcAddress>();

	public void startup(){
//		for(String s:targets){
//			targetRpcAddress.add(new RpcSocketAddress(s.split(":")[0], Integer.valueOf(s.split(":")[1])));
//		}
//		client = new RpcClientIoHandlerImpl(
//				new RpcConfiguration(clientAddress+":"+clientPort+rpcUrl));
//		client.startup();
	}

	public void shutdown(){
//		if(client !=null){
//			client.shutdown();
//		}
	}

//	public <T> T buildRemoteService(Class<? extends T> serviceInteface){

//		if(!client.isAlive()){
//			client.startup();
//		}
//
//		if(rpcServiceBuilder == null){
//			rpcServiceBuilder = new RpcServiceBuilderImpl();
//		}
//
//		return rpcServiceBuilder.buildRemoteServiceProxy(serviceInteface, targetRpcAddress, null, client, serverFilter);
//	}

//	/**
//	 * @return the rpcServiceBuilder
//	 */
//	public RpcServiceBuilderImpl getRpcServiceBuilder() {
//		return rpcServiceBuilder;
//	}
//
//	/**
//	 * @param rpcServiceBuilder the rpcServiceBuilder to set
//	 */
//	public void setRpcServiceBuilder(RpcServiceBuilderImpl rpcServiceBuilder) {
//		this.rpcServiceBuilder = rpcServiceBuilder;
//	}

	/**
	 * @return the clientAddress
	 */
	public String getClientAddress() {
		return clientAddress;
	}

	/**
	 * @param clientAddress the clientAddress to set
	 */
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	/**
	 * @return the clientPort
	 */
	public Integer getClientPort() {
		return clientPort;
	}

	/**
	 * @param clientPort the clientPort to set
	 */
	public void setClientPort(Integer clientPort) {
		this.clientPort = clientPort;
	}

	/**
	 * @return the rpcUrl
	 */
	public String getRpcUrl() {
		return rpcUrl;
	}

	/**
	 * @param rpcUrl the rpcUrl to set
	 */
	public void setRpcUrl(String rpcUrl) {
		this.rpcUrl = rpcUrl;
	}

	/**
	 * @return the targets
	 */
	public Set<String> getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
	 */
	public void setTargets(Set<String> targets) {
		this.targets = targets;
	}

	/**
	 * @return the serverFilter
	 */
	public boolean isServerFilter() {
		return serverFilter;
	}

	/**
	 * @param serverFilter the serverFilter to set
	 */
	public void setServerFilter(boolean serverFilter) {
		this.serverFilter = serverFilter;
	}

//	/**
//	 * @return the targetRpcAddress
//	 */
//	public Set<RpcAddress> getTargetRpcAddress() {
//		return targetRpcAddress;
//	}
//
//	/**
//	 * @param targetRpcAddress the targetRpcAddress to set
//	 */
//	public void setTargetRpcAddress(Set<RpcAddress> targetRpcAddress) {
//		this.targetRpcAddress = targetRpcAddress;
//	}

//	public static void main(String[] args) {
//		SearchEngineRpc rpc = new SearchEngineRpc();
//		rpc.startup();
//		SearchProductsService service = rpc.buildRemoteService(SearchProductsService.class);
//		System.out.println(service.search("where is my product"));
//		rpc.client.shutdown();
//	}


}
