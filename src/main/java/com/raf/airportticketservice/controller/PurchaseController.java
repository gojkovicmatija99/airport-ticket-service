package com.raf.airportticketservice.controller;

import com.raf.airportticketservice.domain.Purchase;
import com.raf.airportticketservice.service.impl.PurchaseService;
import com.raf.airportticketservice.utils.UtilsMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/ticket/{flightId}")
    public ResponseEntity<Boolean> buyTicket(@PathVariable Long flightId, @RequestHeader(value = "Authorization") String token) {
        try {
            purchaseService.buyTicket(flightId, token);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
