package gatemate.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import gatemate.entities.Aircraft;
import gatemate.entities.AirportFlight;
import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.services.FlightService;

@WebMvcTest(FlightController.class)
class FlightControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FlightService flightService;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  @DisplayName("Test to get all flights")
  void whenGetAllFlights_thenReturnFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);
    Seats seats2 = new Seats();
    seats2.setMaxCols(6);
    seats2.setMaxRows(10);
    Seats seats3 = new Seats();
    seats3.setMaxCols(6);
    seats3.setMaxRows(10);

    Aircraft aircraft1 = new Aircraft();
    aircraft1.setAircraftType("Boeing 777");
    aircraft1.setSeats(seats1);
    Aircraft aircraft2 = new Aircraft();
    aircraft2.setAircraftType("Boeing 777");
    aircraft2.setSeats(seats2);
    Aircraft aircraft3 = new Aircraft();
    aircraft3.setAircraftType("Boeing 777");
    aircraft3.setSeats(seats3);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");
    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");

    when(flightService.getFlights(null, null, null, null)).thenReturn(Arrays.asList(flight1, flight2, flight3));

    RestAssuredMockMvc.given()
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(3))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()))
        .and()
        .body("[1].flightIata", is(flight2.getFlightIata()))
        .and()
        .body("[1].origin.iata", is(flight2.getOrigin().getIata()))
        .and()
        .body("[1].destination.iata", is(flight2.getDestination().getIata()))
        .and()
        .body("[2].flightIata", is(flight3.getFlightIata()))
        .and()
        .body("[2].origin.iata", is(flight3.getOrigin().getIata()))
        .and()
        .body("[2].destination.iata", is(flight3.getDestination().getIata()));

    verify(flightService, times(1)).getFlights(null, null, null, null);
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK to LAX and flight IATA AA123")
  void whenGetFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    when(flightService.getFlights("JFK", "LAX", null, "AA123")).thenReturn(Arrays.asList(flight1));

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "AA123")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()));

    verify(flightService, times(1)).getFlights("JFK", "LAX", null, "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights to LAX and flight IATA AA123")
  void whenGetFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    when(flightService.getFlights(null, "LAX", null, "AA123")).thenReturn(Arrays.asList(flight1));

    RestAssuredMockMvc.given()
        .param("to", "LAX")
        .param("flightIata", "AA123")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()));

    verify(flightService, times(1)).getFlights(null, "LAX", null, "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK and flight IATA AA123")
  void whenGetFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    when(flightService.getFlights("JFK", null, null, "AA123")).thenReturn(Arrays.asList(flight1));

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("flightIata", "AA123")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()));

    verify(flightService, times(1)).getFlights("JFK", null, null, "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK to LAX")
  void whenGetFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    when(flightService.getFlights("JFK", "LAX", null, null)).thenReturn(Arrays.asList(flight1));

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()));

    verify(flightService, times(1)).getFlights("JFK", "LAX", null, null);
  }

  @Test
  @DisplayName("Test to get filtered flights with flight IATA AA123")
  void whenGetFilteredFlightsWithFlightIataAA123_thenReturnFilteredFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    when(flightService.getFlights(null, null, null, "AA123")).thenReturn(Arrays.asList(flight1));

    RestAssuredMockMvc.given()
        .param("flightIata", "AA123")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()));

    verify(flightService, times(1)).getFlights(null, null, null, "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights with flight IATA AA456")
  void whenGetFilteredFlightsWithFlightIataAA456_thenReturnFilteredFlightList() {
    Seats seats2 = new Seats();
    seats2.setMaxCols(6);
    seats2.setMaxRows(10);

    Aircraft aircraft2 = new Aircraft();
    aircraft2.setAircraftType("Boeing 777");
    aircraft2.setSeats(seats2);

    AirportFlight origin2 = new AirportFlight();
    origin2.setIata("LAX");
    AirportFlight destination2 = new AirportFlight();
    destination2.setIata("JFK");

    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");

    when(flightService.getFlights(null, null, null, "AA456")).thenReturn(Arrays.asList(flight2));

    RestAssuredMockMvc.given()
        .param("flightIata", "AA456")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight2.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight2.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight2.getDestination().getIata()));

    verify(flightService, times(1)).getFlights(null, null, null, "AA456");
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK")
  void whenGetFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);
    Seats seats3 = new Seats();
    seats3.setMaxCols(6);
    seats3.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");
    AirportFlight origin3 = new AirportFlight();
    origin3.setIata("JFK");
    AirportFlight destination3 = new AirportFlight();
    destination3.setIata("MIA");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");

    when(flightService.getFlights("JFK", null, null, null)).thenReturn(Arrays.asList(flight1, flight3));

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(2))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()))
        .and()
        .body("[1].flightIata", is(flight3.getFlightIata()))
        .and()
        .body("[1].origin.iata", is(flight3.getOrigin().getIata()))
        .and()
        .body("[1].destination.iata", is(flight3.getDestination().getIata()));

    verify(flightService, times(1)).getFlights("JFK", null, null, null);
  }

  @Test
  @DisplayName("Test to get filtered flights of company American Airlines")
  void whenGetFilteredFlightsCompanyAmericanAirlines_thenReturnFilteredFlightList() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    when(flightService.getFlights(null, null, "American Airlines", null)).thenReturn(Arrays.asList(flight1));

    RestAssuredMockMvc.given()
        .param("company", "American Airlines")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight1.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight1.getDestination().getIata()))
        .and()
        .body("[0].airline", is(flight1.getAirline()));

    verify(flightService, times(1)).getFlights(null, null, "American Airlines", null);
  }

  @Test
  @DisplayName("Test to get filtered flights but not found")
  void whenGetFilteredFlightsButNotFound_thenReturnNotFound() {
    when(flightService.getFlights("JFK", "LAX", null, "AA456")).thenReturn(Arrays.asList());

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "AA456")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(404);

    verify(flightService, times(1)).getFlights("JFK", "LAX", null, "AA456");
  }

  @Test
  @DisplayName("Test to get flight info by flight Iata")
  void whenGetFlightInfoByFlightIata_thenReturnFlight() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);

    AirportFlight origin1 = new AirportFlight();
    origin1.setIata("JFK");
    AirportFlight destination1 = new AirportFlight();
    destination1.setIata("LAX");

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");

    when(flightService.getFlightInfo("AA123")).thenReturn(flight1);

    RestAssuredMockMvc.given()
        .when()
        .get("/flights/AA123")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("flightIata", is(flight1.getFlightIata()))
        .and()
        .body("origin.iata", is(flight1.getOrigin().getIata()))
        .and()
        .body("destination.iata", is(flight1.getDestination().getIata()));

    verify(flightService, times(1)).getFlightInfo("AA123");
  }

  @Test
  @DisplayName("Test to get flight info by invalid flight Iata")
  void whenGetFlightInfoByInvalidFlightIata_thenReturnNotFound() {
    when(flightService.getFlightInfo("AA123")).thenReturn(null);

    RestAssuredMockMvc.given()
        .when()
        .get("/flights/AA123")
        .then()
        .assertThat()
        .statusCode(404);

    verify(flightService, times(1)).getFlightInfo("AA123");
  }
}
