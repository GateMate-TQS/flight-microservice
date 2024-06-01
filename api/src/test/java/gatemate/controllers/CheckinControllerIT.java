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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.when;

import gatemate.entities.Ticket;
import gatemate.entities.Aircraft;
import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.services.TicketsService;
import gatemate.services.FlightService;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest
class CheckinControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketsService ticketsService;

    @MockBean
    private FlightService flightsService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        // Mocking Flight and Seats
        Flight flight = new Flight();
        Seats seats = new Seats();
        Aircraft aircraft = new Aircraft();
        seats.setMaxRows(10);
        seats.setMaxCols(6);
        seats.setOccuped(""); // Initially no seats are occupied
        aircraft.setSeats(seats);
        flight.setAircraft(aircraft);

        when(flightsService.getFlightInfo("iataFlight")).thenReturn(flight);

        // Clear database before each test
        ticketsService.deleteAllTickets();

        // Create a ticket for testing
        ticket = ticketsService.createTicket(1L, "iataFlight");
    }

    @AfterEach
    void tearDown() {
        ticketsService.deleteAllTickets();
    }

    @Test
    @DisplayName("POST /checkin/create with valid data should return 200 OK")
    void checkinWithValidDataShouldReturnOk() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("userId", 1L)
                .param("iataFlight", "iataFlight")
                .when()
                .post("/checkin/create")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("GET /checkin/alltickets should return all tickets")
    void getAllTicketsShouldReturnAllTickets() {
        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/alltickets")
                .then()
                .statusCode(200)
                .body("", hasSize(1));
    }

    @Test
    @DisplayName("GET /checkin/{ticketId} with valid id should return ticket")
    void getTicketWithValidIdShouldReturnTicket() {
        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/" + ticket.getId())
                .then()
                .statusCode(200)
                .body("userId", is(1))
                .body("iataFlight", is("iataFlight"))
                .body("seat", isA(String.class));
    }

    @Test
    @DisplayName("GET /checkin/{ticketId} with invalid id should return 404 Not Found")
    void getTicketWithInvalidIdShouldReturnNotFound() {
        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/9999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("GET /checkin/user/{userId} with valid id should return tickets")
    void getTicketsWithValidIdShouldReturnTickets() {
        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/user/1")
                .then()
                .statusCode(200)
                .body("", hasSize(1));
    }

    @Test
    @DisplayName("GET /checkin/user/{userId} with invalid id should return 404 Not Found")
    void getTicketsWithInvalidIdShouldReturnNotFound() {
        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/user/9999")
                .then()
                .statusCode(404);
    }
}
