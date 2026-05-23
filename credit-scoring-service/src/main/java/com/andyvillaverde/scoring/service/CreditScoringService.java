package com.andyvillaverde.scoring.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.andyvillaverde.scoring.model.CreditRequestEvent;
import com.andyvillaverde.scoring.model.CreditScoredEvent;

@Service
public class CreditScoringService {

    public CreditScoredEvent calculateScore(CreditRequestEvent request)
            throws Exception {

        // Simula procesamiento pesado
        Thread.sleep(3000);

        Random random = new Random();

        int score = 500 + random.nextInt(350);

        CreditScoredEvent event =
                new CreditScoredEvent();

        event.setCustomerId(
                request.getCustomerId());

        event.setScore(score);

        if(score >= 700) {
            event.setRiskLevel("LOW");
        } else {
            event.setRiskLevel("HIGH");
        }

        return event;
    }
}