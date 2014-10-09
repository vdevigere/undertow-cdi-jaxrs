package org.viddu.poc;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;

public class MyServer {

    private final Undertow.Builder serverBuilder;
    private final ServletContainer container;

    public MyServer() {
        this.serverBuilder = Undertow.builder().addHttpListener(8080, "0.0.0.0");
        this.container = Servlets.defaultContainer();
    }

    public Undertow configure(DeploymentInfo deploymentInfo) throws ServletException {
        DeploymentManager manager = container.addDeployment(deploymentInfo);
        manager.deploy();
        PathHandler pathHandler = Handlers.path().addPrefixPath("/myApp", manager.start());
        serverBuilder.setHandler(pathHandler);
        return serverBuilder.build();
    }

    public static void main(String[] args) throws ServletException {
        MyServer myServer = new MyServer();
        DeploymentInfo di = Servlets.deployment()
                .setClassLoader(MyServer.class.getClassLoader())
                .setContextPath("/myApp")
                .setDeploymentName("My Application")
                .addServlets(Servlets.servlet("helloServlet", org.viddu.poc.HelloServlet.class)
                        .addMapping("/hello"));
        myServer.configure(di).start();
    }
}
