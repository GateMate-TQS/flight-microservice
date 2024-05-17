package gatemate.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;

import gatemate.entities.Flight;
import gatemate.repositories.FlightRepository;

class FlightDataConsumerTest {

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private FlightRepository flightRepository;

  @InjectMocks
  private FlightDataConsumer flightDataConsumer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void receiveMessage_shouldDeleteOldFlightsAndSaveNewFlight() throws IOException {
    // Arrange
    String testMessage = "{\"data\":[{\"flight\":{\"number\":\"123\",\"iata\":\"FL123\"},\"airline\":{\"name\":\"Test Airline\"},\"flight_status\":\"scheduled\",\"departure\":{\"iata\":\"JFK\",\"icao\":\"KJFK\",\"airport\":\"John F Kennedy Intl\",\"terminal\":\"4\",\"gate\":\"B20\",\"scheduled\":\"2023-01-01T10:00:00Z\"},\"arrival\":{\"iata\":\"LAX\",\"icao\":\"KLAX\",\"airport\":\"Los Angeles Intl\",\"terminal\":\"5\",\"gate\":\"C30\",\"scheduled\":\"2023-01-01T13:00:00Z\"}}]}";
    Map<String, Object> messageMap = new HashMap<>();
    Map<String, Object> dataMap = new HashMap<>();
    Map<String, Object> flightMap = new HashMap<>();
    flightMap.put("number", "123");
    flightMap.put("iata", "FL123");
    dataMap.put("flight", flightMap);
    dataMap.put("airline", Collections.singletonMap("name", "Test Airline"));
    dataMap.put("flight_status", "scheduled");
    dataMap.put("departure", new HashMap<String, Object>() {
      {
        put("iata", "JFK");
        put("icao", "KJFK");
        put("airport", "John F Kennedy Intl");
        put("terminal", "4");
        put("gate", "B20");
        put("scheduled", "2023-01-01T10:00:00Z");
      }
    });
    dataMap.put("arrival", new HashMap<String, Object>() {
      {
        put("iata", "LAX");
        put("icao", "KLAX");
        put("airport", "Los Angeles Intl");
        put("terminal", "5");
        put("gate", "C30");
        put("scheduled", "2023-01-01T13:00:00Z");
      }
    });
    messageMap.put("data", Collections.singletonList(dataMap));

    when(objectMapper.readValue(testMessage, HashMap.class)).thenReturn((HashMap<String, Object>) messageMap);
    when(flightRepository.findByFlightIata("FL123")).thenReturn(null);

    // Act
    flightDataConsumer.receiveMessage(testMessage);

    // Assert
    verify(flightRepository, times(1)).findByUpdatedLessThan(any(Long.class));
    verify(flightRepository, times(1)).save(any(Flight.class));
  }

  @Test
  void receiveMessage_shouldUpdateExistingFlight() throws IOException {
    // Arrange
    String testMessage = "{\"data\":[{\"flight\":{\"number\":\"123\",\"iata\":\"FL123\"},\"airline\":{\"name\":\"Test Airline\"},\"flight_status\":\"scheduled\",\"departure\":{\"iata\":\"JFK\",\"icao\":\"KJFK\",\"airport\":\"John F Kennedy Intl\",\"terminal\":\"4\",\"gate\":\"B20\",\"scheduled\":\"2023-01-01T10:00:00Z\"},\"arrival\":{\"iata\":\"LAX\",\"icao\":\"KLAX\",\"airport\":\"Los Angeles Intl\",\"terminal\":\"5\",\"gate\":\"C30\",\"scheduled\":\"2023-01-01T13:00:00Z\"}}]}";
    Map<String, Object> messageMap = new HashMap<>();
    Map<String, Object> dataMap = new HashMap<>();
    Map<String, Object> flightMap = new HashMap<>();
    flightMap.put("number", "123");
    flightMap.put("iata", "FL123");
    dataMap.put("flight", flightMap);
    dataMap.put("airline", Collections.singletonMap("name", "Test Airline"));
    dataMap.put("flight_status", "scheduled");
    dataMap.put("departure", new HashMap<String, Object>() {
      {
        put("iata", "JFK");
        put("icao", "KJFK");
        put("airport", "John F Kennedy Intl");
        put("terminal", "4");
        put("gate", "B20");
        put("scheduled", "2023-01-01T10:00:00Z");
      }
    });
    dataMap.put("arrival", new HashMap<String, Object>() {
      {
        put("iata", "LAX");
        put("icao", "KLAX");
        put("airport", "Los Angeles Intl");
        put("terminal", "5");
        put("gate", "C30");
        put("scheduled", "2023-01-01T13:00:00Z");
      }
    });
    messageMap.put("data", Collections.singletonList(dataMap));

    when(objectMapper.readValue(testMessage, HashMap.class)).thenReturn((HashMap<String, Object>) messageMap);
    Flight existingFlight = new Flight();
    when(flightRepository.findByFlightIata("FL123")).thenReturn(existingFlight);

    // Act
    flightDataConsumer.receiveMessage(testMessage);

    // Assert
    verify(flightRepository, times(1)).findByUpdatedLessThan(any(Long.class));
    verify(flightRepository, times(1)).save(existingFlight);
  }
}
