package com.raf.airportticketservice.controller;

import com.raf.airportticketservice.domain.Purchase;
import com.raf.airportticketservice.service.impl.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/bought/{id}")
    public ResponseEntity<List<Purchase>> getBoughtTickets(@PathVariable Long id) {
        try {
            List<Purchase> purchases = purchaseService.getBoughtTickets(id);
            return new ResponseEntity(purchases, HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
