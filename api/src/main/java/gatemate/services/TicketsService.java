package gatemate.services;

import java.util.List;

import gatemate.entities.Ticket;

public interface TicketsService {

    public Ticket getTicket(Long ticketId);

    public List<Ticket> getTickets(Long userId);

    public Ticket createTicket(Long userId, String iataFlight);

    public List<Ticket> getALLTickets();

    public void deleteAllTickets();
}
