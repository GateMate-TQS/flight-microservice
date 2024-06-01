package gatemate.controllers;

import lombok.AllArgsConstructor;

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
            @RequestParam String iataFlight) {
        Ticket createdTicket = ticketsService.createTicket(userId, iataFlight);
        if (createdTicket != null) {
            return ResponseEntity.ok("Checked in. Ticket ID: " + createdTicket.getId());
        } else {
            return ResponseEntity.badRequest().body("Unable to check in. Seats may be unavailable.");
        }
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<?> getTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketsService.getTicket(ticketId);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
