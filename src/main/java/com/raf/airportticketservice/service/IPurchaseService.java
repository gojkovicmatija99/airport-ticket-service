package com.raf.airportticketservice.service;

import com.raf.airportticketservice.domain.Purchase;

import java.util.List;

public interface IPurchaseService {
    List<Purchase> getBoughtTickets(Long userId);
    Boolean cancelTickets(Long flightId);
}
