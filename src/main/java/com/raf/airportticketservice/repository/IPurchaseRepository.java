package com.raf.airportticketservice.repository;

import com.raf.airportticketservice.domain.Purchase;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId(Long userId);
    List<Purchase> findByFlightId(Long flightId);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE purchase SET canceled=1 WHERE flight_id = :flightId", nativeQuery = true)
    Integer setCanceled(@Param("flightId") Long flightId);
}
