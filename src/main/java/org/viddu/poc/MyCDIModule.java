package org.viddu.poc;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class MyCDIModule {

    @Named("Single")
    @Produces
    public String message() {
        return "I am single";
    }

    @Produces
    public Config getAppConfiguration(){
        return ConfigFactory.load();
    }

    @Produces
    public ObjectMapper getMapper(){
        return new ObjectMapper();
    }
}
