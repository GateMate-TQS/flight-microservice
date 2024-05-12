package gatemate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatemate.entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}