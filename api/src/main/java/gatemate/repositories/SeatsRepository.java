package gatemate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatemate.entities.Seats;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Long> {
}