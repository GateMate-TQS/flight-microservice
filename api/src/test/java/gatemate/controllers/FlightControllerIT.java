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
import gatemate.entities.AirportFlight;
import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.repositories.FlightRepository;

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

  @BeforeEach
  void setUp() {
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
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);

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
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK to LAX and flight IATA AA123")
  void whenGetFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

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
  }

  @Test
  @DisplayName("Test to get filtered flights to LAX and flight IATA AA123")
  void whenGetFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

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
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK and flight IATA AA123")
  void whenGetFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

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
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK to LAX")
  void whenGetFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

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
  }

  @Test
  @DisplayName("Test to get filtered flights with flight IATA AA123")
  void whenGetFilteredFlightsFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

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
  }

  @Test
  @DisplayName("Test to get filtered flights with flight IATA AA456")
  void whenGetFilteredFlightsFLightIataAA456_thenReturnFilteredFlightList() {
    Flight flight2 = flightRepository.findByFlightIata("AA456");

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
  }

  @Test
  @DisplayName("Test to get filtered flights from JFK")
  void whenGetFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");
    Flight flight3 = flightRepository.findByFlightIata("AA789");

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
