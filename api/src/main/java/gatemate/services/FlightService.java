package gatemate.services;

import java.util.List;

import gatemate.entities.Flight;

public interface FlightService {
  public List<Flight> getFlights(String from, String to, String company, String flightIata);

  public Flight getFlightInfo(String flightIata);
}
