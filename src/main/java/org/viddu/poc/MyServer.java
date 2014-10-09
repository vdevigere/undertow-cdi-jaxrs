package org.viddu.poc;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class MyServer {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();

    public MyServer(Integer port, String host) {
        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
        server.start(serverBuilder);
    }

    public DeploymentInfo deployApplication(String appPath, Class<? extends Application> applicationClass) {
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(applicationClass.getName());
        return server.undertowDeployment(deployment, appPath);
    }

    public void deploy(DeploymentInfo deploymentInfo) throws ServletException {
        server.deploy(deploymentInfo);
    }

    public static void main(String[] args) throws ServletException {
        MyServer myServer = new MyServer(8080, "0.0.0.0");
        DeploymentInfo di = myServer.deployApplication("/rest", MyApplication.class)
                .setClassLoader(MyServer.class.getClassLoader())
                .setContextPath("/myApp")
                .setDeploymentName("My Application")
                .addServlets(Servlets.servlet("helloServlet", org.viddu.poc.HelloServlet.class).addMapping("/hello"));
        myServer.deploy(di);
    }
}
