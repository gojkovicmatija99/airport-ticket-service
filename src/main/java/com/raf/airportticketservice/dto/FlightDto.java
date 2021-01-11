package com.raf.airportticketservice.dto;

public class FlightDto {
    private Long price;
    private Long distance;
    private Boolean isCanceled;

    public FlightDto() {

    }

    public FlightDto(Long price, Long distance, Boolean isCanceled) {
        this.price = price;
        this.distance = distance;
        this.isCanceled = isCanceled;
    }

    public Long getPrice() {
        return price;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
