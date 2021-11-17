package com.apache.camel.classcomponent;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class MethodCallUsingClassComponent {

    public static void main(String[] args) throws Exception {

        CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:start")
                        .to("class:com.apache.camel.classcomponent.MyService?method=doSomething");

            }
        });
        camelContext.start();

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello from Class Component");
    }
}
