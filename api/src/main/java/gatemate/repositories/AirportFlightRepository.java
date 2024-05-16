package gatemate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatemate.entities.AirportFlight;

@Repository
public interface AirportFlightRepository extends JpaRepository<AirportFlight, Long> {

}