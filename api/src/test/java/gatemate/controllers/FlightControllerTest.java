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
    Seats seats = new Seats();
    seats.setMaxCols(6);
    seats.setMaxRows(10);

    Aircraft aircraft = new Aircraft();
    aircraft.setAircraftType("Boeing 777");
    aircraft.setSeats(seats);

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin("JFK");
    flight3.setDestination("MIA");

    when(flightService.getFlights(null, null, null)).thenReturn(Arrays.asList(flight1, flight2, flight3));

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
        .body("[0].origin", is(flight1.getOrigin()))
        .and()
        .body("[0].destination", is(flight1.getDestination()))
        .and()
        .body("[1].flightIata", is(flight2.getFlightIata()))
        .and()
        .body("[1].origin", is(flight2.getOrigin()))
        .and()
        .body("[1].destination", is(flight2.getDestination()))
        .and()
        .body("[2].flightIata", is(flight3.getFlightIata()))
        .and()
        .body("[2].origin", is(flight3.getOrigin()))
        .and()
        .body("[2].destination", is(flight3.getDestination()));

    verify(flightService, times(1)).getFlights(null, null, null);
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK to LAX and flight IATA AA123")
  void whenGetFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    when(flightService.getFlights("JFK", "LAX", "AA123")).thenReturn(Arrays.asList(flight1));

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
        .body("[0].origin", is(flight1.getOrigin()))
        .and()
        .body("[0].destination", is(flight1.getDestination()));

    verify(flightService, times(1)).getFlights("JFK", "LAX", "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights to LAX and flight IATA AA123")
  void whenGetFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    when(flightService.getFlights(null, "LAX", "AA123")).thenReturn(Arrays.asList(flight1));

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
        .body("[0].origin", is(flight1.getOrigin()))
        .and()
        .body("[0].destination", is(flight1.getDestination()));

    verify(flightService, times(1)).getFlights(null, "LAX", "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK and flight IATA AA123")
  void whenGetFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    when(flightService.getFlights("JFK", null, "AA123")).thenReturn(Arrays.asList(flight1));

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
        .body("[0].origin", is(flight1.getOrigin()))
        .and()
        .body("[0].destination", is(flight1.getDestination()));

    verify(flightService, times(1)).getFlights("JFK", null, "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK to LAX")
  void whenGetFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    when(flightService.getFlights("JFK", "LAX", null)).thenReturn(Arrays.asList(flight1));

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
        .body("[0].origin", is(flight1.getOrigin()))
        .and()
        .body("[0].destination", is(flight1.getDestination()));

    verify(flightService, times(1)).getFlights("JFK", "LAX", null);
  }

  @Test
  @DisplayName("Test to get filtered flights with flight IATA AA123")
  void whenGetFilteredFlightsWithFlightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    when(flightService.getFlights(null, null, "AA123")).thenReturn(Arrays.asList(flight1));

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
        .body("[0].origin", is(flight1.getOrigin()))
        .and()
        .body("[0].destination", is(flight1.getDestination()));

    verify(flightService, times(1)).getFlights(null, null, "AA123");
  }

  @Test
  @DisplayName("Test to get filtered flights with flight IATA AA456")
  void whenGetFilteredFlightsWithFlightIataAA456_thenReturnFilteredFlightList() {
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");

    when(flightService.getFlights(null, null, "AA456")).thenReturn(Arrays.asList(flight2));

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
        .body("[0].origin", is(flight2.getOrigin()))
        .and()
        .body("[0].destination", is(flight2.getDestination()));

    verify(flightService, times(1)).getFlights(null, null, "AA456");
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK")
  void whenGetFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin("JFK");
    flight3.setDestination("MIA");

    when(flightService.getFlights("JFK", null, null)).thenReturn(Arrays.asList(flight1, flight3));

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
        .body("[0].origin", is(flight1.getOrigin()))
        .and()
        .body("[0].destination", is(flight1.getDestination()))
        .and()
        .body("[1].flightIata", is(flight3.getFlightIata()))
        .and()
        .body("[1].origin", is(flight3.getOrigin()))
        .and()
        .body("[1].destination", is(flight3.getDestination()));

    verify(flightService, times(1)).getFlights("JFK", null, null);
  }

  @Test
  @DisplayName("Test to get filtered flights but not found")
  void whenGetFilteredFlightsButNotFound_thenReturnNotFound() {
    when(flightService.getFlights("JFK", "LAX", "AA456")).thenReturn(Arrays.asList());

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "AA456")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(404);

    verify(flightService, times(1)).getFlights("JFK", "LAX", "AA456");
  }
}
