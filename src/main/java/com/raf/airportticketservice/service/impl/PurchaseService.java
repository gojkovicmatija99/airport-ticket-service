package com.raf.airportticketservice.service.impl;

import com.raf.airportticketservice.domain.Purchase;
import com.raf.airportticketservice.repository.PurchaseRepository;
import com.raf.airportticketservice.service.IPurchaseService;
import com.raf.airportticketservice.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.Date;
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
            String queueItem = "cancel:" + current.getUserId().toString();
            jmsTemplate.convertAndSend(usersQueue, queueItem);
        }
        return true;
    }

    @Override
    public Boolean buyTicket(Long flightId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        ResponseEntity<Object> responseEntity = UtilsMethods.sendGetHeader("http://localhost:8081/get_userId", headers);
        Long userId = ((Integer) responseEntity.getBody()).longValue();
        Date currentDate = new Date();
        Purchase purchase = new Purchase(flightId, userId, currentDate);
        purchaseRepository.save(purchase);
        String queueItem = "miles:" + userId+","+flightId;
        jmsTemplate.convertAndSend(usersQueue,queueItem);
        return true;
    }
}
