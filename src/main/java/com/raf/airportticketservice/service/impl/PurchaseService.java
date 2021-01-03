package com.raf.airportticketservice.service.impl;

import com.raf.airportticketservice.domain.Purchase;
import com.raf.airportticketservice.repository.PurchaseRepository;
import com.raf.airportticketservice.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.List;

@Service
public class PurchaseService implements IPurchaseService {
    private PurchaseRepository purchaseRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Queue usersQueue;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public List<Purchase> getBoughtTickets(Long userId) {
        return purchaseRepository.findByUserId(userId);
    }

    @Override
    public Boolean cancelTickets(Long flightId) {
        purchaseRepository.setCanceled(flightId);
        List<Purchase> canceled = purchaseRepository.findByFlightId(flightId);
        if(canceled.size() == 0)
            return true;
        for(Purchase current:canceled) {
            //TODO: Svakom korisniku poslati mejl, oduzeti milje
            jmsTemplate.convertAndSend(usersQueue, current.getUserId().toString());
        }
        return true;
    }
}
