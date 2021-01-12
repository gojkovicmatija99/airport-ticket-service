package com.raf.airportticketservice.controller;

import com.raf.airportticketservice.domain.Purchase;
import com.raf.airportticketservice.service.IPurchaseService;
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
    private IPurchaseService purchaseService;

    public PurchaseController(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @CrossOrigin
    @GetMapping("/bought")
    public ResponseEntity<List<Purchase>> getBoughtTickets(@RequestHeader(value = "Authorization") String token) {
        try {
            List<Purchase> purchases = purchaseService.getBoughtTickets(token);
            return new ResponseEntity(purchases, HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @GetMapping("/ticket/{flightId}")
    public ResponseEntity<Double> buyTicket(@PathVariable Long flightId, @RequestHeader(value = "Authorization") String token) {
        try {
            Double price = purchaseService.buyTicket(flightId, token);
            if(price != 0.0)
                return new ResponseEntity(price, HttpStatus.OK);
            else
                return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
