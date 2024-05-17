package gatemate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import gatemate.entities.Aircraft;
import gatemate.entities.Flight;
import gatemate.repositories.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {
  @Autowired
  private final FlightRepository flightRepository;
  private JedisPool pool;

  public FlightServiceImpl(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
    this.pool = new JedisPool("redis", 6379);
  }

  @Override
  public List<Flight> getFlights(String from, String to, String flightIata) {
    return flightRepository.findAll().stream()
        .filter(flight -> from == null || flight.getOrigin().equals(from))
        .filter(flight -> to == null || flight.getDestination().equals(to))
        .filter(flight -> flightIata == null || String.valueOf(flight.getFlightIata()).equals(flightIata))
        .toList();
  }

  @Override
  public Flight getFlightInfo(String flightIata) {
    // TODO Auto-generated method stub
    return flightRepository.findByFlightIata(flightIata).orElse(null);
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
