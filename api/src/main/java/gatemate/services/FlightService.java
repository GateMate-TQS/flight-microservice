package gatemate.services;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import gatemate.entities.Flight;

public interface FlightService {
  public List<Flight> getFlights(String from, String to, String flightIata);

  public Flight getFlightInfo(String flightIata);

  public JsonNode fetchFlightInfo(String flightIata);

  public Flight purchaseTicket(String flightIata, String seatClass, int numberOfTickets);
}
