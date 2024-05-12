package gatemate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

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
  public List<Flight> getAllFlights() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllFlights'");
  }

  @Override
  public List<Flight> getFilteredFlights(String from, String to, String company, String flightIata) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getFilteredFlights'");
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
}
