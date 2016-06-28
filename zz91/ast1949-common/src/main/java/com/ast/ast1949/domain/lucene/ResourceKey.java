/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-15 下午02:22:49
 */
package com.ast.ast1949.domain.lucene;

import com.ast.ast1949.util.Assert;


/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ResourceKey {
	private static final long serialVersionUID = -7208511448495190596L;
    private String            resourceType;
    private String            resourceName;

    public static void validate(ResourceKey resourceKey) {
        Assert.notNull(resourceKey, "resourceKey can not be null.");
        Assert.hasText(resourceKey.getResourceName(), "resourceKey.resourceName can not be empty.");
    }

    public ResourceKey(String resourceType, String resourceName) {
        Assert.hasText(resourceName, "resourceName can not be empty.");
        this.resourceType = resourceType;
        this.resourceName = resourceName;
    }

    public ResourceKey() {

    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setResourceName(String resourceName) {
        Assert.hasText(resourceName, "resourceName can not be empty.");
        this.resourceName = resourceName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
        result = prime * result + ((resourceType == null) ? 0 : resourceType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourceKey other = (ResourceKey) obj;
        if (resourceName == null) {
            if (other.resourceName != null)
                return false;
        } else if (!resourceName.equals(other.resourceName))
            return false;
        if (resourceType == null) {
            if (other.resourceType != null)
                return false;
        } else if (!resourceType.equals(other.resourceType))
            return false;
        return true;
    }
}
