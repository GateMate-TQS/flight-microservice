package gatemate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatemate.entities.Ticket;

import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {
    public List<Ticket> findByUserId(Long userId);

    public void deleteAll();
}
