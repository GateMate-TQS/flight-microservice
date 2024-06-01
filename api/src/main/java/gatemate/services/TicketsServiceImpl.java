package gatemate.services;

import java.util.List;

import org.springframework.stereotype.Service;

import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.entities.Ticket;
import gatemate.repositories.TicketsRepository;

@Service
public class TicketsServiceImpl implements TicketsService {

    private final TicketsRepository ticketsRepository;

    private final FlightService flightsService;

    public TicketsServiceImpl(TicketsRepository ticketsRepository, FlightService flightsService) {
        this.ticketsRepository = ticketsRepository;
        this.flightsService = flightsService;
    }

    @Override
    public Ticket getTicket(Long ticketId) {
        return ticketsRepository.findById(ticketId).orElse(null);
    }

    @Override
    public List<Ticket> getTickets(Long userId) {
        return ticketsRepository.findByUserId(userId);
    }

    @Override
    public Ticket createTicket(Long userId, String iataFlight) {
        Flight flight = flightsService.getFlightInfo(iataFlight);
        Seats seats = flight.getAircraft().getSeats();
        int rows = seats.getMaxRows();
        int cols = seats.getMaxCols();

        // seat assignment
        String seat = null;
        for (int i = 1; i <= rows; i++) {
            if (seat != null) {
                break;
            }
            for (int j = 1; j <= cols; j++) {
                String row = String.valueOf(i);
                char col = (char) (j + 64);

                if (!seats.getOccuped().contains(row + col)) {
                    seat = row + col;
                    break;
                }
            }
        }

        seats.setOccuped(seats.getOccuped() + seat + ",");
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setIataFlight(iataFlight);
        ticket.setSeat(seat);
        return ticketsRepository.save(ticket);
    }

    @Override
    public List<Ticket> getALLTickets() {
        return ticketsRepository.findAll();
    }

    @Override
    public void deleteAllTickets() {
        ticketsRepository.deleteAll();
    }

}
