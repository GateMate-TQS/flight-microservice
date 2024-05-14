package gatemate.controllers;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;

import gatemate.entities.Flight;
import gatemate.repositories.FlightRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest
public class FlightControllerIT {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private FlightRepository flightRepository;

  @BeforeEach
  void setUp() {
    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin("JFK");
    flight1.setDestination("LAX");

    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin("LAX");
    flight2.setDestination("JFK");

    flightRepository.save(flight1);
    flightRepository.save(flight2);

    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  void whenGetAllFlights_thenReturnFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");
    Flight flight2 = flightRepository.findByFlightIata("AA456");

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
  }
}
