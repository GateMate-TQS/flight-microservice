package gatemate.controllers;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gatemate.services.TicketsService;
import gatemate.entities.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@AllArgsConstructor
@RequestMapping("/checkin")
public class CheckinController {
    private final TicketsService ticketsService;

    @Operation(summary = "Efetuar check-in e criar um ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-in realizado com sucesso", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Não foi possível realizar o check-in", content = @Content(schema = @Schema(implementation = String.class)))
    })
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

    @Operation(summary = "Obter informações do ticket pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado", content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket não encontrado", content = @Content)
    })
    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<Object> getTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketsService.getTicket(ticketId);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
