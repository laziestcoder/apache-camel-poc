package com.apache.camel.producerandconsumer;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProduceAndConsume {

    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
//                                System.out.println("This is processor");
                                String message = exchange.getIn().getBody(String.class);
                                message += " - modified";
//                                exchange.getOut().setBody(message);
                                exchange.getIn().setBody(message);
                            }
                        })
                        .to("seda:end");
            }
        });

        camelContext.start();

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello from Apache Camel:Producer");

        ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        String message = consumerTemplate.receiveBody("seda:end", String.class);

        System.out.println("Consumer: " + message);
    }
}
