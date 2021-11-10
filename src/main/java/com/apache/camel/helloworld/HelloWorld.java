package com.apache.camel.helloworld;

import com.apache.camel.helloworld.route.HelloWorldRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new HelloWorldRoute());
        context.start();
    }
}
