package com.raf.airportticketservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long flightId;
    private Long userId;
    private Date date;

    public Purchase(Long flightId, Long userId, Date date) {
        this.flightId = flightId;
        this.userId = userId;
        this.date = date;
    }

    public Purchase() {}

    public Long getFlightId() {
        return flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
