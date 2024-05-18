package gatemate.services;

import java.util.List;

import org.springframework.stereotype.Service;

import gatemate.entities.Flight;
import gatemate.repositories.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {
  private final FlightRepository flightRepository;

  public FlightServiceImpl(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Override
  public List<Flight> getFlights(String from, String to, String company, String flightIata) {
    return flightRepository.findAll().stream()
        .filter(flight -> from == null || flight.getOrigin().getIata().equals(from))
        .filter(flight -> to == null || flight.getDestination().getIata().equals(to))
        .filter(flight -> company == null || flight.getAirline().equals(company))
        .filter(flight -> flightIata == null || String.valueOf(flight.getFlightIata()).equals(flightIata))
        .toList();
  }
}
