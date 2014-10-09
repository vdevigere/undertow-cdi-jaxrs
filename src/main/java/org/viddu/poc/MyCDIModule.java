package org.viddu.poc;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class MyCDIModule {

    @Named("Single")
    @Produces
    public String message() {
        return "I am single";
    }
}
