package gatemate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import gatemate.entities.Ticket;
import gatemate.repositories.TicketsRepository;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketsRepository ticketsRepository;

    @InjectMocks
    private TicketsServiceImpl ticketsService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setUserId(1L);
        ticket.setIataFlight("IATA");
        ticket.setSeat("1A");
    }

    @Test
    @DisplayName("Test get ticket by id")
    void testGetTicket() {
        when(ticketsRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(ticket));
        Ticket found = ticketsService.getTicket(1L);
        assertThat(found.getUserId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Test get all tickets")
    void testGetAllTickets() {
        Ticket ticket2 = new Ticket();
        ticket2.setUserId(2L);
        ticket2.setIataFlight("IATA2");
        ticket2.setSeat("2A");

        List<Ticket> tickets = Arrays.asList(ticket, ticket2);

        when(ticketsRepository.findAll()).thenReturn(tickets);

        List<Ticket> allTickets = ticketsService.getALLTickets();

        assertThat(allTickets.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test save ticket")
    void testSaveTicket() {
        when(ticketsRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket savedTicket = ticketsService.createTicket(1L, "IATA", "1A");

        assertThat(savedTicket).isNotNull();

        verify(ticketsRepository, VerificationModeFactory.times(1)).save(any(Ticket.class));
    }

    @Test
    @DisplayName("Test get tickets by user id")
    void testGetTicketsByUserId() {
        Ticket ticket2 = new Ticket();
        ticket2.setUserId(1L);
        ticket2.setIataFlight("IATA2");
        ticket2.setSeat("2A");

        List<Ticket> tickets = Arrays.asList(ticket, ticket2);

        when(ticketsRepository.findByUserId(1L)).thenReturn(tickets);

        List<Ticket> foundTickets = ticketsService.getTickets(1L);

        assertThat(foundTickets.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test get tickets by user id not found")
    void testGetTicketsByUserIdNotFound() {
        when(ticketsRepository.findByUserId(1L)).thenReturn(null);

        List<Ticket> foundTickets = ticketsService.getTickets(1L);

        assertThat(foundTickets).isNull();
    }

    @Test
    @DisplayName("Test get all tickets not found")
    void testGetAllTicketsNotFound() {
        when(ticketsRepository.findAll()).thenReturn(null);

        List<Ticket> allTickets = ticketsService.getALLTickets();

        assertThat(allTickets).isNull();
    }

    @Test
    @DisplayName("Test delete all tickets")
    void testDeleteAllTickets() {
        doNothing().when(ticketsRepository).deleteAll();

        ticketsService.deleteAllTickets();

        verify(ticketsRepository, VerificationModeFactory.times(1)).deleteAll();
    }

}
