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
  public List<Flight> getScheduledFlights(String from, String to, String company, String flightIata) {
    return flightRepository.findByStatus("scheduled").stream()
        .filter(flight -> from == null || flight.getOrigin().getIata().equals(from))
        .filter(flight -> to == null || flight.getDestination().getIata().equals(to))
        .filter(flight -> company == null || flight.getAirline().equals(company))
        .filter(flight -> flightIata == null || String.valueOf(flight.getFlightIata()).equals(flightIata))
        .toList();
  }

  @Override
  public List<Flight> getActiveFlights(String from, String to, String company, String flightIata) {
    return flightRepository.findByStatus("active").stream()
        .filter(flight -> from == null || flight.getOrigin().getIata().equals(from))
        .filter(flight -> to == null || flight.getDestination().getIata().equals(to))
        .filter(flight -> company == null || flight.getAirline().equals(company))
        .filter(flight -> flightIata == null || String.valueOf(flight.getFlightIata()).equals(flightIata))
        .filter(flight -> flight.getLiveData() != null)
        .toList();
  }

  @Override
  public Flight getFlightInfo(String flightIata) {
    return flightRepository.findByFlightIata(flightIata);
  }

  @Override
  public List<Flight> getAllFlights() {
    return flightRepository.findAll();
  }

}
