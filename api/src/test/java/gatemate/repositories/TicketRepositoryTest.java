package gatemate.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import gatemate.entities.Ticket;

@DataJpaTest
public class TicketRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TicketsRepository ticketRepository;

    @Test
    @DisplayName("Find ticket by id")
    void whenFindTicketByExistingId_thenReturnTicket() {
        Ticket ticket = new Ticket();
        entityManager.persistAndFlush(ticket);

        Ticket ticketdb = ticketRepository.findById(ticket.getId()).orElse(null);

        assertThat(ticketdb).isNotNull();
        assertThat(ticketdb.getId()).isEqualTo(ticket.getId());
    }

    @Test
    @DisplayName("Find ticket by invalid id")
    void whenInvalidId_thenReturnNull() {
        Ticket ticketdb = ticketRepository.findById(-1L).orElse(null);

        assertThat(ticketdb).isNull();
    }

    @Test
    @DisplayName("Find all tickets")
    void givenSetOfTickets_whenFindAll_thenReturnSet() {
        Ticket ticket1 = new Ticket();
        ticket1.setIataFlight("FirstFlight");
        Ticket ticket2 = new Ticket();
        ticket2.setIataFlight("SecondFlight");

        entityManager.persistAndFlush(ticket1);
        entityManager.persistAndFlush(ticket2);

        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList)
                .hasSize(2)
                .extracting(Ticket::getIataFlight)
                .contains(ticket1.getIataFlight(), ticket2.getIataFlight());
    }

    @Test
    @DisplayName("Delete all tickets")
    void deleteAllTickets() {
        Ticket ticket1 = new Ticket();
        ticket1.setIataFlight("FirstFlight");
        Ticket ticket2 = new Ticket();
        ticket2.setIataFlight("SecondFlight");

        entityManager.persistAndFlush(ticket1);
        entityManager.persistAndFlush(ticket2);

        ticketRepository.deleteAll();

        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).isEmpty();
    }

}
