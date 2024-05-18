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
import gatemate.entities.LiveData;
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
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");
    flight2.setPrice(300);
    flight2.setStatus("scheduled");

    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");
    flight3.setPrice(400);
    flight3.setStatus("scheduled");

    LiveData liveData4 = new LiveData();
    liveData4.setAltitude(100);
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");
    flight4.setLiveData(liveData4);

    LiveData liveData5 = new LiveData();
    liveData5.setAltitude(100);
    AirportFlight origin5 = new AirportFlight();
    origin5.setIata("LAX");
    AirportFlight destination5 = new AirportFlight();
    destination5.setIata("JFK");
    Flight flight5 = new Flight();
    flight5.setFlightIata("BB456");
    flight5.setOrigin(origin5);
    flight5.setDestination(destination5);
    flight5.setAirline("TAP Air Portugal");
    flight5.setPrice(300);
    flight5.setStatus("active");
    flight5.setLiveData(liveData5);

    LiveData liveData6 = new LiveData();
    liveData6.setAltitude(100);
    AirportFlight origin6 = new AirportFlight();
    origin6.setIata("JFK");
    AirportFlight destination6 = new AirportFlight();
    destination6.setIata("MIA");
    Flight flight6 = new Flight();
    flight6.setFlightIata("BB789");
    flight6.setOrigin(origin6);
    flight6.setDestination(destination6);
    flight6.setAirline("TAP Air Portugal");
    flight6.setPrice(400);
    flight6.setStatus("active");
    flight6.setLiveData(liveData6);

    AirportFlight origin7 = new AirportFlight();
    origin7.setIata("JFK");
    AirportFlight destination7 = new AirportFlight();
    destination7.setIata("MIA");
    Flight flight7 = new Flight();
    flight7.setFlightIata("BB947");
    flight7.setOrigin(origin7);
    flight7.setDestination(destination7);
    flight7.setAirline("TAP Air Portugal");
    flight7.setPrice(400);
    flight7.setStatus("active");

    lenient().when(flightRepository.findByStatus("scheduled")).thenReturn(Arrays.asList(flight1, flight2, flight3));
    lenient().when(flightRepository.findByStatus("active"))
        .thenReturn(Arrays.asList(flight4, flight5, flight6, flight7));
    lenient().when(flightRepository.findByFlightIata("AA123")).thenReturn(flight1);
  }

  @Test
  @DisplayName("Find scheduled flights")
  void whenFindScheduled_thenReturnFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");
    flight2.setPrice(300);
    flight2.setStatus("scheduled");

    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");
    flight3.setPrice(400);
    flight3.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights(null, null, null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(3).extracting(Flight::getFlightIata).contains(flight1.getFlightIata(),
        flight2.getFlightIata(), flight3.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights from JFK to LAX and flight IATA AA123")
  void whenFindScheduledFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights("JFK", "LAX", null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights to LAX and flight IATA AA123")
  void whenFindScheduledFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights(null, "LAX", null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights from JFK and flight IATA AA123")
  void whenFindScheduledFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights("JFK", null, null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights from JFK to LAX")
  void whenFindScheduledFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights("JFK", "LAX", null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights with flight IATA AA123")
  void whenFindScheduledFilteredFlightsWithFlightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights(null, null, null, "AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights with flight IATA AA456")
  void whenFindScheduledFilteredFlightsWithFlightIataAA456_thenReturnFilteredFlightList() {
    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");
    flight2.setPrice(300);
    flight2.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights(null, null, null, "AA456");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight2.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights from JFK")
  void whenFindScheduledFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");
    flight3.setPrice(400);
    flight3.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights("JFK", null, null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(2).extracting(Flight::getFlightIata).contains(flight1.getFlightIata(),
        flight3.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights of company American Airlines")
  void whenFindScheduledFilteredFlightsCompanyAmericanAirlines_thenReturnFilteredFlightList() {
    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    List<Flight> found = flightServiceImpl.getScheduledFlights(null, null, "American Airlines", null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight1.getFlightIata());
  }

  @Test
  @DisplayName("Find scheduled filtered flights but not found")
  void whenFindScheduledFilteredFlights_thenReturnNotFoundFlights() {
    List<Flight> found = flightServiceImpl.getScheduledFlights("JFK", "LAX", null, "AA456");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("scheduled");
    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Find active flights")
  void whenFindActive_thenReturnFlightList() {
    LiveData liveData4 = new LiveData();
    liveData4.setAltitude(100);
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");
    flight4.setLiveData(liveData4);

    LiveData liveData5 = new LiveData();
    liveData5.setAltitude(100);
    AirportFlight origin5 = new AirportFlight();
    origin5.setIata("LAX");
    AirportFlight destination5 = new AirportFlight();
    destination5.setIata("JFK");
    Flight flight5 = new Flight();
    flight5.setFlightIata("BB456");
    flight5.setOrigin(origin5);
    flight5.setDestination(destination5);
    flight5.setAirline("TAP Air Portugal");
    flight5.setPrice(300);
    flight5.setStatus("active");
    flight5.setLiveData(liveData5);

    LiveData liveData6 = new LiveData();
    liveData6.setAltitude(100);
    AirportFlight origin6 = new AirportFlight();
    origin6.setIata("JFK");
    AirportFlight destination6 = new AirportFlight();
    destination6.setIata("MIA");
    Flight flight6 = new Flight();
    flight6.setFlightIata("BB789");
    flight6.setOrigin(origin6);
    flight6.setDestination(destination6);
    flight6.setAirline("TAP Air Portugal");
    flight6.setPrice(400);
    flight6.setStatus("active");
    flight6.setLiveData(liveData6);

    List<Flight> found = flightServiceImpl.getActiveFlights(null, null, null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(3).extracting(Flight::getFlightIata).contains(flight4.getFlightIata(),
        flight5.getFlightIata(), flight6.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights from JFK to LAX and flight IATA BB123")
  void whenFindActiveFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights("JFK", "LAX", null, "BB123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight4.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights to LAX and flight IATA BB123")
  void whenFindActiveFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights(null, "LAX", null, "BB123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight4.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights from JFK and flight IATA BB123")
  void whenFindActiveFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights("JFK", null, null, "BB123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight4.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights from JFK to LAX")
  void whenFindActiveFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights("JFK", "LAX", null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight4.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights with flight IATA BB123")
  void whenFindActiveFilteredFlightsWithFlightIataAA123_thenReturnFilteredFlightList() {
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights(null, null, null, "BB123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight4.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights with flight IATA BB456")
  void whenFindActiveFilteredFlightsWithFlightIataAA456_thenReturnFilteredFlightList() {
    AirportFlight origin5 = new AirportFlight();
    origin5.setIata("LAX");
    AirportFlight destination5 = new AirportFlight();
    destination5.setIata("JFK");
    Flight flight5 = new Flight();
    flight5.setFlightIata("BB456");
    flight5.setOrigin(origin5);
    flight5.setDestination(destination5);
    flight5.setAirline("TAP Air Portugal");
    flight5.setPrice(300);
    flight5.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights(null, null, null, "BB456");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight5.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights from JFK")
  void whenFindActiveFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");

    AirportFlight origin6 = new AirportFlight();
    origin6.setIata("JFK");
    AirportFlight destination6 = new AirportFlight();
    destination6.setIata("MIA");
    Flight flight6 = new Flight();
    flight6.setFlightIata("BB789");
    flight6.setOrigin(origin6);
    flight6.setDestination(destination6);
    flight6.setAirline("TAP Air Portugal");
    flight6.setPrice(400);
    flight6.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights("JFK", null, null, null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(2).extracting(Flight::getFlightIata).contains(flight4.getFlightIata(),
        flight6.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights of company American Airlines")
  void whenFindActiveFilteredFlightsCompanyAmericanAirlines_thenReturnFilteredFlightList() {
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setPrice(200);
    flight4.setStatus("active");

    List<Flight> found = flightServiceImpl.getActiveFlights(null, null, "American Airlines", null);

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).hasSize(1).extracting(Flight::getFlightIata).contains(flight4.getFlightIata());
  }

  @Test
  @DisplayName("Find active filtered flights but not found")
  void whenFindActiveFilteredFlights_thenReturnNotFoundFlights() {
    List<Flight> found = flightServiceImpl.getActiveFlights("JFK", "LAX", null, "BB456");

    verify(flightRepository, VerificationModeFactory.times(1)).findByStatus("active");
    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Find flight info with flight IATA AA123")
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
    flight1.setPrice(200);
    flight1.setStatus("scheduled");

    Flight found = flightServiceImpl.getFlightInfo("AA123");

    verify(flightRepository, VerificationModeFactory.times(1)).findByFlightIata("AA123");
    assertThat(found).isNotNull();
    assertThat(found.getFlightIata()).isEqualTo(flight1.getFlightIata());
    assertThat(found.getOrigin().getIata()).isEqualTo(flight1.getOrigin().getIata());
    assertThat(found.getDestination().getIata()).isEqualTo(flight1.getDestination().getIata());
    assertThat(found.getAirline()).isEqualTo(flight1.getAirline());
  }

  @Test
  @DisplayName("Find flight info with invalid flight Iata")
  void whenFindFlightInfoWithInvalidFlightIata_thenReturnNull() {
    Flight found = flightServiceImpl.getFlightInfo("AA456");

    verify(flightRepository, VerificationModeFactory.times(1)).findByFlightIata("AA456");
    assertThat(found).isNull();
  }
}
