package gatemate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import gatemate.entities.Flight;
import gatemate.repositories.FlightRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
  @Mock
  private FlightRepository flightRepository;
  @InjectMocks
  private FlightServiceImpl flightServiceImpl;

  @BeforeEach
  public void setUp() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");

    when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2));
  }

  @Test
  @DisplayName("Test to find all flights")
  void whenFindAll_thenReturnFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");

    List<Flight> found = flightServiceImpl.getFlights(null, null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(2).extracting(Flight::getFlightIata).contains(flight1.getFlightIata(),
        flight2.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights")
  void whenFindFilteredFlights_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    List<Flight> found = flightServiceImpl.getFlights("JFK", "LAX", "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());

    found = flightServiceImpl.getFlights(null, "LAX", "AA123");

    verify(flightRepository, VerificationModeFactory.times(2)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());

    found = flightServiceImpl.getFlights("JFK", null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(3)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());

    found = flightServiceImpl.getFlights("JFK", "LAX", null);

    verify(flightRepository, VerificationModeFactory.times(4)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());

    found = flightServiceImpl.getFlights(null, null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(5)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights but not found")
  void whenFindFilteredFlights_thenReturnNotFoundFlights() {
    List<Flight> found = flightServiceImpl.getFlights("JFK", "LAX", "AA456");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).isEmpty();
  }
}
