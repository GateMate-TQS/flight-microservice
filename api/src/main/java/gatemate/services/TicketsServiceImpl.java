package gatemate.services;

import java.util.List;

import org.springframework.stereotype.Service;

import gatemate.entities.Ticket;
import gatemate.repositories.TicketsRepository;

@Service
public class TicketsServiceImpl implements TicketsService {

    private final TicketsRepository ticketsRepository;

    public TicketsServiceImpl(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
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
    public Ticket createTicket(Long userId, String iataFlight, String seat) {
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
