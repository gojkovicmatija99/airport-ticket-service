package com.raf.airportticketservice.service.impl;

import com.raf.airportticketservice.domain.Purchase;
import com.raf.airportticketservice.dto.FlightDto;
import com.raf.airportticketservice.repository.IPurchaseRepository;
import com.raf.airportticketservice.service.IPurchaseService;
import com.raf.airportticketservice.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.jms.Queue;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseService implements IPurchaseService {
    private IPurchaseRepository purchaseRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Queue usersQueue;

    @Autowired
    Queue flightsQueue;

    public PurchaseService(IPurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @CrossOrigin
    @Override
    public List<Purchase> getBoughtTickets(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        ResponseEntity<Object> responseEntity = UtilsMethods.sendGetHeader("http://localhost:8762/rest-airport-user-service/get_userId", headers);
        Long userId = ((Integer) responseEntity.getBody()).longValue();
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
    public Long buyTicket(Long flightId, String token) {
        ResponseEntity<FlightDto> responseEntity = UtilsMethods.getFlightDto("http://localhost:8762/rest-airport-flight-service/flight/get/" + flightId);
        FlightDto flightInfo = responseEntity.getBody();
        System.out.println(flightInfo.getPrice());
        if(!flightInfo.getCanceled()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            ResponseEntity<Object> userInfo = UtilsMethods.sendGetHeader("http://localhost:8762/rest-airport-user-service/get_userId", headers);
            Long userId = ((Integer) userInfo.getBody()).longValue();
            Date currentDate = new Date();
            Purchase purchase = new Purchase(flightId, userId, currentDate);
            purchaseRepository.save(purchase);

            String queueItem = "miles:" + userId + "," + flightId;
            jmsTemplate.convertAndSend(usersQueue, queueItem);
            jmsTemplate.convertAndSend(flightsQueue, flightId.toString());
            return flightInfo.getPrice();
        }
        else
            return 0l;
    }
}
