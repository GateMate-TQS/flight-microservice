package gatemate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import gatemate.entities.Flight;
import gatemate.repositories.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {
  @Autowired
  private final FlightRepository flightRepository;

  public FlightServiceImpl(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Override
  public List<Flight> getFlights(String from, String to, String flightIata) {
    return flightRepository.findAll().stream()
        .filter(flight -> from == null || flight.getOrigin().getIata().equals(from))
        .filter(flight -> to == null || flight.getDestination().getIata().equals(to))
        .filter(flight -> flightIata == null || String.valueOf(flight.getFlightIata()).equals(flightIata))
        .toList();
  }

  @Override
  public Flight getFlightInfo(String flightIata) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getFlightInfo'");
  }

  @Override
  public JsonNode fetchFlightInfo(String flightIata) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'fetchFlightInfo'");
  }

  @Override
  public Flight purchaseTicket(String flightIata, String seatClass, int numberOfTickets) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'purchaseTicket'");
  }
}
