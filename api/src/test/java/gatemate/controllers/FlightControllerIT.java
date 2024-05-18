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
import gatemate.entities.LiveData;
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
    Seats seats4 = new Seats();
    seats4.setMaxCols(6);
    seats4.setMaxRows(10);
    Seats seats5 = new Seats();
    seats5.setMaxCols(6);
    seats5.setMaxRows(10);
    Seats seats6 = new Seats();
    seats6.setMaxCols(6);
    seats6.setMaxRows(10);
    Seats seats7 = new Seats();
    seats7.setMaxCols(6);
    seats7.setMaxRows(10);

    Aircraft aircraft1 = new Aircraft();
    aircraft1.setAircraftType("Boeing 777");
    aircraft1.setSeats(seats1);
    Aircraft aircraft2 = new Aircraft();
    aircraft2.setAircraftType("Boeing 777");
    aircraft2.setSeats(seats2);
    Aircraft aircraft3 = new Aircraft();
    aircraft3.setAircraftType("Boeing 777");
    aircraft3.setSeats(seats3);
    Aircraft aircraft4 = new Aircraft();
    aircraft4.setAircraftType("Boeing 777");
    aircraft4.setSeats(seats4);
    Aircraft aircraft5 = new Aircraft();
    aircraft5.setAircraftType("Boeing 777");
    aircraft5.setSeats(seats5);
    Aircraft aircraft6 = new Aircraft();
    aircraft6.setAircraftType("Boeing 777");
    aircraft6.setSeats(seats6);
    Aircraft aircraft7 = new Aircraft();
    aircraft7.setAircraftType("Boeing 777");
    aircraft7.setSeats(seats7);

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
    AirportFlight origin4 = new AirportFlight();
    origin4.setIata("JFK");
    AirportFlight destination4 = new AirportFlight();
    destination4.setIata("LAX");
    AirportFlight origin5 = new AirportFlight();
    origin5.setIata("LAX");
    AirportFlight destination5 = new AirportFlight();
    destination5.setIata("JFK");
    AirportFlight origin6 = new AirportFlight();
    origin6.setIata("JFK");
    AirportFlight destination6 = new AirportFlight();
    destination6.setIata("MIA");
    AirportFlight origin7 = new AirportFlight();
    origin7.setIata("JFK");
    AirportFlight destination7 = new AirportFlight();
    destination7.setIata("MIA");

    LiveData liveData4 = new LiveData();
    liveData4.setAltitude(100);
    LiveData liveData5 = new LiveData();
    liveData5.setAltitude(100);
    LiveData liveData6 = new LiveData();
    liveData6.setAltitude(100);

    Flight flight1 = new Flight();
    flight1.setFlightIata("AA123");
    flight1.setOrigin(origin1);
    flight1.setDestination(destination1);
    flight1.setAirline("American Airlines");
    flight1.setPrice(200);
    flight1.setStatus("scheduled");
    Flight flight2 = new Flight();
    flight2.setFlightIata("AA456");
    flight2.setOrigin(origin2);
    flight2.setDestination(destination2);
    flight2.setAirline("TAP Air Portugal");
    flight2.setPrice(300);
    flight2.setStatus("scheduled");
    Flight flight3 = new Flight();
    flight3.setFlightIata("AA789");
    flight3.setOrigin(origin3);
    flight3.setDestination(destination3);
    flight3.setAirline("TAP Air Portugal");
    flight3.setPrice(400);
    flight3.setStatus("scheduled");
    Flight flight4 = new Flight();
    flight4.setFlightIata("BB123");
    flight4.setOrigin(origin4);
    flight4.setDestination(destination4);
    flight4.setAirline("American Airlines");
    flight4.setAircraft(aircraft4);
    flight4.setPrice(200);
    flight4.setStatus("active");
    flight4.setLiveData(liveData4);
    Flight flight5 = new Flight();
    flight5.setFlightIata("BB456");
    flight5.setOrigin(origin5);
    flight5.setDestination(destination5);
    flight5.setAirline("TAP Air Portugal");
    flight5.setAircraft(aircraft5);
    flight5.setPrice(300);
    flight5.setStatus("active");
    flight5.setLiveData(liveData5);
    Flight flight6 = new Flight();
    flight6.setFlightIata("BB789");
    flight6.setOrigin(origin6);
    flight6.setDestination(destination6);
    flight6.setAirline("TAP Air Portugal");
    flight6.setAircraft(aircraft6);
    flight6.setPrice(400);
    flight6.setStatus("active");
    flight6.setLiveData(liveData6);
    Flight flight7 = new Flight();
    flight7.setFlightIata("BB947");
    flight7.setOrigin(origin7);
    flight7.setDestination(destination7);
    flight7.setAirline("TAP Air Portugal");
    flight7.setAircraft(aircraft7);
    flight7.setPrice(400);
    flight7.setStatus("active");

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);
    flightRepository.save(flight4);
    flightRepository.save(flight5);
    flightRepository.save(flight6);
    flightRepository.save(flight7);

    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @AfterEach
  void clearDatabase() {
    flightRepository.deleteAll();
  }

  @Test
  @DisplayName("Get scheduled flights")
  void whenGetScheduledFlights_thenReturnFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");
    Flight flight2 = flightRepository.findByFlightIata("AA456");
    Flight flight3 = flightRepository.findByFlightIata("AA789");

    RestAssuredMockMvc.given()
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get scheduled filtered flights from JFK to LAX and flight IATA AA123")
  void whenGetScheduledFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "AA123")
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get filtered scheduled flights to LAX and flight IATA AA123")
  void whenGetScheduledFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

    RestAssuredMockMvc.given()
        .param("to", "LAX")
        .param("flightIata", "AA123")
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get scheduled filtered flights from JFK and flight IATA AA123")
  void whenGetScheduledFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("flightIata", "AA123")
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get scheduled filtered flights from JFK to LAX")
  void whenGetScheduledFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get scheduled filtered flights with flight IATA AA123")
  void whenGetScheduledFilteredFlightsFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

    RestAssuredMockMvc.given()
        .param("flightIata", "AA123")
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get scheduled filtered flights with flight IATA AA456")
  void whenGetScheduledFilteredFlightsFLightIataAA456_thenReturnFilteredFlightList() {
    Flight flight2 = flightRepository.findByFlightIata("AA456");

    RestAssuredMockMvc.given()
        .param("flightIata", "AA456")
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get scheduled filtered flights from JFK")
  void whenGetScheduledFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");
    Flight flight3 = flightRepository.findByFlightIata("AA789");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .when()
        .get("/scheduledFlights")
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
  @DisplayName("Get scheduled filtered flights of company American Airlines")
  void whenGetScheduledFilteredFlightsCompanyAmericanAirlines_thenReturnFilteredFlightList() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

    RestAssuredMockMvc.given()
        .param("company", "American Airlines")
        .when()
        .get("/scheduledFlights")
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
  }

  @Test
  @DisplayName("Get scheduled filtered flights but not found")
  void whenGetScheduledFilteredFlightsNotFound_thenReturnNotFound() {
    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "AA456")
        .when()
        .get("/scheduledFlights")
        .then()
        .assertThat()
        .statusCode(404);
  }

  @Test
  @DisplayName("Get active flights")
  void whenGetActiveFlights_thenReturnFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");
    Flight flight5 = flightRepository.findByFlightIata("BB456");
    Flight flight6 = flightRepository.findByFlightIata("BB789");

    RestAssuredMockMvc.given()
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(3))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()))
        .and()
        .body("[1].flightIata", is(flight5.getFlightIata()))
        .and()
        .body("[1].origin.iata", is(flight5.getOrigin().getIata()))
        .and()
        .body("[1].destination.iata", is(flight5.getDestination().getIata()))
        .and()
        .body("[2].flightIata", is(flight6.getFlightIata()))
        .and()
        .body("[2].origin.iata", is(flight6.getOrigin().getIata()))
        .and()
        .body("[2].destination.iata", is(flight6.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active filtered flights from JFK to LAX and flight IATA BB123")
  void whenGetActiveFilteredFlightsFromJFKToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "BB123")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active scheduled flights to LAX and flight IATA BB123")
  void whenGetActiveFilteredFlightsToLAXFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");

    RestAssuredMockMvc.given()
        .param("to", "LAX")
        .param("flightIata", "BB123")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active filtered flights from JFK and flight IATA BB123")
  void whenGetActiveFilteredFlightsFromJFKFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("flightIata", "BB123")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active filtered flights from JFK to LAX")
  void whenGetActiveFilteredFlightsFromJFKToLAX_thenReturnFilteredFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active filtered flights with flight IATA BB123")
  void whenGetActiveFilteredFlightsFLightIataAA123_thenReturnFilteredFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");

    RestAssuredMockMvc.given()
        .param("flightIata", "BB123")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active filtered flights with flight IATA BB456")
  void whenGetActiveFilteredFlightsFLightIataAA456_thenReturnFilteredFlightList() {
    Flight flight5 = flightRepository.findByFlightIata("BB456");

    RestAssuredMockMvc.given()
        .param("flightIata", "BB456")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight5.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight5.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight5.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active filtered flights from JFK")
  void whenGetActiveFilteredFlightsFromJFK_thenReturnFilteredFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");
    Flight flight6 = flightRepository.findByFlightIata("BB789");

    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(2))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()))
        .and()
        .body("[1].flightIata", is(flight6.getFlightIata()))
        .and()
        .body("[1].origin.iata", is(flight6.getOrigin().getIata()))
        .and()
        .body("[1].destination.iata", is(flight6.getDestination().getIata()));
  }

  @Test
  @DisplayName("Get active filtered flights of company American Airlines")
  void whenGetActiveFilteredFlightsCompanyAmericanAirlines_thenReturnFilteredFlightList() {
    Flight flight4 = flightRepository.findByFlightIata("BB123");

    RestAssuredMockMvc.given()
        .param("company", "American Airlines")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .and()
        .body("", hasSize(1))
        .and()
        .body("[0].flightIata", is(flight4.getFlightIata()))
        .and()
        .body("[0].origin.iata", is(flight4.getOrigin().getIata()))
        .and()
        .body("[0].destination.iata", is(flight4.getDestination().getIata()))
        .and()
        .body("[0].airline", is(flight4.getAirline()));
  }

  @Test
  @DisplayName("Get active filtered flights but not found")
  void whenGetActiveFilteredFlightsNotFound_thenReturnNotFound() {
    RestAssuredMockMvc.given()
        .param("from", "JFK")
        .param("to", "LAX")
        .param("flightIata", "AA456")
        .when()
        .get("/activeFlights")
        .then()
        .assertThat()
        .statusCode(404);
  }

  @Test
  @DisplayName("Get flight info with flight Iata AA123")
  void whenGetFlightInfoByFlightIata_thenReturnFlight() {
    Flight flight1 = flightRepository.findByFlightIata("AA123");

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
  }

  @Test
  @DisplayName("Get flight info by invalid flight Iata")
  void whenGetFlightInfoByInvalidFlightIata_thenReturnNotFound() {
    RestAssuredMockMvc.given()
        .when()
        .get("/flights/AA124")
        .then()
        .assertThat()
        .statusCode(404);
  }
}
