/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-15 下午02:22:14
 */
package com.ast.ast1949.domain.lucene;



/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ResourceChangesEvent extends ChangesEvent{
	 private static final long serialVersionUID = -2564620664271075927L;
	    private ResourceKey       resourceKey      = new ResourceKey();

	    public String getResourceType() {
	        return resourceKey.getResourceType();
	    }

	    public void setResourceType(String resourceType) {
	        resourceKey.setResourceType(resourceType);
	    }

	    public String getResourceName() {
	        return resourceKey.getResourceName();
	    }

	    public void setResourceName(String resourceName) {
	        resourceKey.setResourceName(resourceName);
	    }

	    public ResourceKey getResourceKey() {
	        return resourceKey;
	    }
}
