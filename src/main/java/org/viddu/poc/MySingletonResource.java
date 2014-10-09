package org.viddu.poc;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/singleton")
public class MySingletonResource {

    @Named("Single")
    @Inject
    private String message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloSingleton(){
        return message;
    }
}
