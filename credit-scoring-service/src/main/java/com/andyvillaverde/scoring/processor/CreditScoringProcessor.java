package com.andyvillaverde.scoring.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andyvillaverde.scoring.model.CreditRequestEvent;
import com.andyvillaverde.scoring.model.CreditScoredEvent;
import com.andyvillaverde.scoring.service.CreditScoringService;

@Component
public class CreditScoringProcessor
        implements Processor {

    @Autowired
    private CreditScoringService  creditScoringService;

    @Override
    public void process(Exchange exchange)
            throws Exception {

        CreditRequestEvent request = exchange.getIn()
        									 .getBody(CreditRequestEvent.class);

        CreditScoredEvent scoredEvent =  creditScoringService.calculateScore(request);

        exchange.getIn().setBody(scoredEvent);
    }
}