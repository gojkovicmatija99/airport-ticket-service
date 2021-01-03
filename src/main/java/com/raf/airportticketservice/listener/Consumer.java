package com.raf.airportticketservice.listener;

import com.raf.airportticketservice.service.impl.PurchaseService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private PurchaseService purchaseService;

    public Consumer(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @JmsListener(destination = "tickets.queue")
    public void consume(String flightId) {
        purchaseService.cancelTickets(Long.parseLong(flightId));
    }
}
