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

import gatemate.entities.Flight;
import gatemate.services.FlightService;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FlightService flightService;

  @BeforeEach
  void setUp() throws Exception {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  @DisplayName("Test to get all flights")
  void whenGetAllFlights_thenReturnFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");

    when(flightService.getFlights(null, null, null)).thenReturn(Arrays.asList(flight1, flight2));

    RestAssuredMockMvc.given()
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
        .body("[1].flightIata", is(flight2.getFlightIata()))
        .and()
        .body("[1].origin", is(flight2.getOrigin()))
        .and()
        .body("[1].destination", is(flight2.getDestination()));

    verify(flightService, times(1)).getFlights(null, null, null);
  }

  @Test
  @DisplayName("Test to get filtered flights")
  void whenGetFilteredFlights_thenReturnFilteredFlightList() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");

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
