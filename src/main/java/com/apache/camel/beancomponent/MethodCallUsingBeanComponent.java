package com.apache.camel.beancomponent;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class MethodCallUsingBeanComponent {

    public static void main(String[] args) throws Exception {

        MyBeanService beanService = new MyBeanService();

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myBeanService", beanService);

        CamelContext camelContext = new DefaultCamelContext(registry);

        camelContext.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:start")
                        .to("bean:myBeanService?method=doSomething");

            }
        });
        camelContext.start();

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello from Bean Component");
    }
}
