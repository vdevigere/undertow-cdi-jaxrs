package org.viddu.poc;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.RoleBasedSecurityFeature;

public class MyApplication extends Application {

    @Inject
    MySingletonResource mySingletonResource;

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
        resources.add(OAuthContainerRequestFilter.class);
        resources.add(HelloResource.class);
        resources.add(RoleBasedSecurityFeature.class);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> resources = new LinkedHashSet<Object>();
        resources.add(mySingletonResource);
        return resources;
    }

}
