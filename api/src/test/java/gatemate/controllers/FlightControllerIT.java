package gatemate.controllers;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import gatemate.entities.Aircraft;
import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.repositories.AircraftRepository;
import gatemate.repositories.FlightRepository;
import gatemate.repositories.SeatsRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest
class FlightControllerIT {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private FlightRepository flightRepository;
  @Autowired
  private AircraftRepository aircraftRepository;
  @Autowired
  private SeatsRepository seatsRepository;

  @BeforeEach
  void setUp() {
    Seats seats = new Seats();
    seats.setMaxCols(6);
    seats.setMaxRows(10);

    seatsRepository.save(seats);

    Aircraft aircraft = new Aircraft();
    aircraft.setAircraftType("Boeing 777");
    aircraft.setSeats(seats);

    aircraftRepository.save(aircraft);

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");
    flight1.setAircraft(aircraft);
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin("JFK");
    flight3.setDestination("MIA");

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @AfterEach
  void clearDatabase() {
    flightRepository.deleteAll();
  }

  @Test
  @DisplayName("Test to get all flights")
  void whenGetAllFlights_thenReturnFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");
    Flight flight2 = flightRepository.findByFlightIata("AA456");
    Flight flight3 = flightRepository.findByFlightIata("AA789");

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
  }

  @Test
  @DisplayName("Test to get filtered flights")
  void whenGetFilteredFlights_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");
    Flight flight2 = flightRepository.findByFlightIata("AA456");
    Flight flight3 = flightRepository.findByFlightIata("AA789");

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
  }

  @Test
  @DisplayName("Test to get filtered flights but not found")
  void whenGetFilteredFlightsNotFound_thenReturnNotFound() {
    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "AA456")
        .when()
        .get("/flights")
        .then()
        .assertThat()
        .statusCode(404);
  }
}
