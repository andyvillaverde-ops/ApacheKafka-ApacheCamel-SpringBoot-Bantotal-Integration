package com.andyvillaverde.scoring.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andyvillaverde.scoring.processor.CreditScoringProcessor;

@Component
public class CreditScoringRoute
        extends RouteBuilder {

    @Autowired
    private CreditScoringProcessor creditScoringProcessor;

    @Override
    public void configure() throws Exception {

        from("kafka:credit.requested"
                + "?brokers={{kafka.bootstrap.servers}}"
                + "&groupId=credit-scoring-group")
            .routeId("credit-requested-consumer")
            .log("Credit Request Received: ${body}")
            /*
             * ============================================
             * WORKERS / PARALELISMO
             * ============================================
             */
            .threads()
	            .poolSize(1)
	            .maxPoolSize(5)
	            .threadName("credit-scoring-worker")
	            .log("Thread=${threadName} Processing=${body}")
            .unmarshal().json(  com.andyvillaverde.scoring.model
            					.CreditRequestEvent.class)
            .process(creditScoringProcessor)
            .marshal().json()
            .to("kafka:credit.scored"
                    + "?brokers={{kafka.bootstrap.servers}}")
            .log("Credit Score Published");
        
    }
}