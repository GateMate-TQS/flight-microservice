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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.entities.Ticket;
import gatemate.entities.Aircraft;
import gatemate.repositories.TicketsRepository;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketsRepository ticketsRepository;

    @Mock
    private FlightService flightsService;

    @InjectMocks
    private TicketsServiceImpl ticketsService;

    private Ticket ticket;
    private Flight flight;
    private Aircraft aircraft;
    private Seats seats;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setUserId(1L);
        ticket.setIataFlight("IATA");
        ticket.setSeat("1A");

        seats = new Seats();
        seats.setMaxRows(1);
        seats.setMaxCols(3);
        seats.setOccuped("");

        aircraft = new Aircraft();
        aircraft.setSeats(seats);

        flight = new Flight();
        flight.setAircraft(aircraft);
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

        assertThat(allTickets).hasSize(2);
    }

    @Test
    @DisplayName("Test save ticket")
    void testSaveTicket() {
        when(flightsService.getFlightInfo("IATA")).thenReturn(flight);
        when(ticketsRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket savedTicket = ticketsService.createTicket(1L, "IATA");

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

        assertThat(foundTickets).hasSize(2);
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

    @Test
    @DisplayName("Test save ticket with occupied seat")
    void testSaveTicketWithOccupiedSeat() {
        seats.setOccuped("1A,1B,1C"); // Assuming these seats are already occupied
        when(flightsService.getFlightInfo("IATA")).thenReturn(flight);
        lenient().when(ticketsRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket savedTicket = ticketsService.createTicket(1L, "IATA");

        assertThat(savedTicket).isNull(); // The ticket should not be saved because all the seats are occupied
    }

    @Test
    @DisplayName("Test delete all tickets when no tickets exist")
    void testDeleteAllTicketsWhenNoTicketsExist() {
        doNothing().when(ticketsRepository).deleteAll();

        ticketsService.deleteAllTickets();

        verify(ticketsRepository, VerificationModeFactory.times(1)).deleteAll();
    }

    @Test
    @DisplayName("Test get non-existing ticket")
    void testGetNonExistingTicket() {
        when(ticketsRepository.findById(999L)).thenReturn(Optional.empty());

        Ticket found = ticketsService.getTicket(999L);

        assertThat(found).isNull();
    }

}
