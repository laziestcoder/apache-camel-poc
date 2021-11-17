package com.apache.camel.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class SQLOperation {

    public static void main(String[] args) throws Exception {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3307/camel");
        dataSource.setUser("camel");
        dataSource.setPassword("camel@123");

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myCamelDataSource", dataSource);

        CamelContext camelContext = new DefaultCamelContext(registry);
        camelContext.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:start")
                        .to("jdbc:myCamelDataSource")
                        .bean(new QueryResultHandler(), "printResult");
            }
        });
        camelContext.start();

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "select * from employee");
    }
}
