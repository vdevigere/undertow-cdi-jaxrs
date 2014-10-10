package org.viddu.poc;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@PermitAll
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("LOGGED_IN")
    public String sayHello(){
        return "Hello World";
    }
}
