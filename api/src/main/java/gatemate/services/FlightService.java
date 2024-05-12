package gatemate.services;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;

import gatemate.entities.Flight;

public interface FlightService {
  public List<Flight> getAllFlights();

  public List<Flight> getFilteredFlights(String from, String to, String company, String flightIata);

  public Flight getFlightInfo(String flightIata);

  public JsonNode fetchFlightInfo(String flightIata);
}
