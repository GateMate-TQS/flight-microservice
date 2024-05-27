package gatemate.controllers;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gatemate.services.TicketsService;
import gatemate.entities.Ticket;

@RestController
@AllArgsConstructor
@RequestMapping("/checkin")
public class CheckinController {
    private final TicketsService ticketsService;

    @PostMapping("/create")
    public ResponseEntity<String> checkin(
            @RequestParam Long userId,
            @RequestParam String iataFlight,
            @RequestParam String seat) {
        ticketsService.createTicket(userId, iataFlight, seat);
        return new ResponseEntity<>("Checked in", HttpStatus.OK);
    }

    @GetMapping("/Alltickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketsService.getALLTickets();

        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        }
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketsService.getTicket(ticketId);

        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable Long userId) {
        List<Ticket> tickets = ticketsService.getTickets(userId);

        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        }
    }

}
