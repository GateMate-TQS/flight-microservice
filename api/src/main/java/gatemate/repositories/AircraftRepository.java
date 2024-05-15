package gatemate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatemate.entities.Aircraft;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}