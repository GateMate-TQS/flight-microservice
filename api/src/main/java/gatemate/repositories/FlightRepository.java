package gatemate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gatemate.entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
  Flight findByFlightIata(String flightIata);

  @Query("SELECT f FROM Flight f WHERE f.updated < :time")
  List<Flight> findByUpdatedLessThan(@Param("time") Long time);

}