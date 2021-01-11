package com.raf.airportticketservice.service;

import com.raf.airportticketservice.domain.Purchase;

import java.util.List;

public interface IPurchaseService {
    List<Purchase> getBoughtTickets(String token);
    Boolean cancelTickets(Long flightId);
    Long buyTicket(Long ticketId, String token);
}
