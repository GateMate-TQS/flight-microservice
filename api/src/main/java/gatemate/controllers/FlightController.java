package gatemate.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gatemate.entities.Flight;
import gatemate.services.FlightService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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

  @GetMapping("/all_flight")
  public ResponseEntity<List<Flight>> getAllFlights() {
    List<Flight> flights = flightService.getAllFlights();

    if (flights.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(flights, HttpStatus.OK);
    }
  }
}
