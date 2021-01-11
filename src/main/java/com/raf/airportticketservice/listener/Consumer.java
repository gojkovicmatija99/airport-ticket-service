package com.raf.airportticketservice.listener;

import com.raf.airportticketservice.service.IPurchaseService;
import com.raf.airportticketservice.service.impl.PurchaseService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private IPurchaseService purchaseService;

    public Consumer(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @JmsListener(destination = "tickets.queue")
    public void consume(String flightId) {
        purchaseService.cancelTickets(Long.parseLong(flightId));
    }
}
