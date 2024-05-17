package gatemate.controllers;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gatemate.entities.Flight;
import gatemate.services.FlightService;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class FlightController {
  private final FlightService flightService;

  @GetMapping("/flights")
  public ResponseEntity<List<Flight>> getFlights(
      @RequestParam(name = "from", required = false) String from,
      @RequestParam(name = "to", required = false) String to,
      @RequestParam(name = "company", required = false) String company,
      @RequestParam(name = "flightIata", required = false) String flightIata) {

    List<Flight> flights = flightService.getFlights(from, to, flightIata);

    if (flights.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(flights, HttpStatus.OK);
    }
  }

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
