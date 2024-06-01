package gatemate.controllers;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gatemate.entities.Flight;
import gatemate.services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class FlightController {
  private final FlightService flightService;

  @Operation(summary = "Obter voos agendados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Voos encontrados", content = @Content(schema = @Schema(implementation = Flight.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum voo encontrado", content = @Content)
  })
  @GetMapping("/scheduledFlights")
  public ResponseEntity<List<Flight>> getScheduledFlights(
      @RequestParam(name = "from", required = false) String from,
      @RequestParam(name = "to", required = false) String to,
      @RequestParam(name = "company", required = false) String company,
      @RequestParam(name = "flightIata", required = false) String flightIata) {

    List<Flight> flights = flightService.getScheduledFlights(from, to, company, flightIata);

    if (flights.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(flights, HttpStatus.OK);
    }
  }

  @Operation(summary = "Obter voos ativos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Voos encontrados", content = @Content(schema = @Schema(implementation = Flight.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum voo encontrado", content = @Content)
  })
  @GetMapping("/activeFlights")
  public ResponseEntity<List<Flight>> getActiveFlights(
      @RequestParam(name = "from", required = false) String from,
      @RequestParam(name = "to", required = false) String to,
      @RequestParam(name = "company", required = false) String company,
      @RequestParam(name = "flightIata", required = false) String flightIata) {

    List<Flight> flights = flightService.getActiveFlights(from, to, company, flightIata);

    if (flights.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(flights, HttpStatus.OK);
    }
  }

  @Operation(summary = "Obter informações do voo pelo código IATA")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Voo encontrado", content = @Content(schema = @Schema(implementation = Flight.class))),
      @ApiResponse(responseCode = "404", description = "Voo não encontrado", content = @Content)
  })
  @GetMapping("/flights/{flightIata}")
  public ResponseEntity<Flight> getFlightInfo(@PathVariable String flightIata) {
    Flight flight = flightService.getFlightInfo(flightIata);

    if (flight == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(flight, HttpStatus.OK);
    }
  }
}
