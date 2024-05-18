package gatemate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import gatemate.entities.AirportFlight;
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
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");

    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");

    lenient().when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2, flight3));
    lenient().when(flightRepository.findByFlightIata("AA123")).thenReturn(flight1);
  }

  @Test
  @DisplayName("Test to find all flights")
  void whenFindAll_thenReturnFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");

    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");

    List<Flight> found = flightServiceImpl.getFlights(null, null, null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(3).extracting(Flight::getFlightIata).contains(flight1.getFlightIata(),
        flight2.getFlightIata(), flight3.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights from JFK to LAX and flight IATA AA123")
  void whenFindFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    List<Flight> found = flightServiceImpl.getFlights("JFK", "LAX", null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights to LAX and flight IATA AA123")
  void whenFindFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    List<Flight> found = flightServiceImpl.getFlights(null, "LAX", null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights from JFK and flight IATA AA123")
  void whenFindFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    List<Flight> found = flightServiceImpl.getFlights("JFK", null, null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights from JFK to LAX")
  void whenFindFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    List<Flight> found = flightServiceImpl.getFlights("JFK", "LAX", null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights with flight IATA AA123")
  void whenFindFilteredFlightsWithFlightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    List<Flight> found = flightServiceImpl.getFlights(null, null, null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights with flight IATA AA456")
  void whenFindFilteredFlightsWithFlightIataAA456_thenReturnFilteredFlightList() {
    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");

    List<Flight> found = flightServiceImpl.getFlights(null, null, null, "AA456");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight2.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights from JFK")
  void whenFindFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");

    List<Flight> found = flightServiceImpl.getFlights("JFK", null, null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(2).extracting(Flight::getFlightIata).contains(flight1.getFlightIata(),
        flight3.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights of company American Airlines")
  void whenFindFilteredFlightsCompanyAmericanAirlines_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    List<Flight> found = flightServiceImpl.getFlights(null, null, "American Airlines", null);

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Test to find filtered flights but not found")
  void whenFindFilteredFlights_thenReturnNotFoundFlights() {
    List<Flight> found = flightServiceImpl.getFlights("JFK", "LAX", null, "AA456");

    verify(flightRepository, VerificationModeFactory.times(1)).findAll();
    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Test to find flight info with flight IATA AA123")
  void whenFindFlightInfoWithFlightIataAA123_thenReturnFlightInfo() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    Flight found = flightServiceImpl.getFlightInfo("AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByFlightIata("AA123");
    assertThat(found).isNotNull();
    assertThat(found.getFlightIata()).isEqualTo(flight1.getFlightIata());
    assertThat(found.getOrigin().getIata()).isEqualTo(flight1.getOrigin().getIata());
    assertThat(found.getDestination().getIata()).isEqualTo(flight1.getDestination().getIata());
    assertThat(found.getAirline()).isEqualTo(flight1.getAirline());
  }

  @Test
  @DisplayName("Test to find flight info with invalid flight IATA")
  void whenFindFlightInfoWithInvalidFlightIata_thenReturnNull() {
    Flight found = flightServiceImpl.getFlightInfo("AA456");

    verify(flightRepository, VerificationModeFactory.times(1)).findByFlightIata("AA456");
    assertThat(found).isNull();
  }
}
