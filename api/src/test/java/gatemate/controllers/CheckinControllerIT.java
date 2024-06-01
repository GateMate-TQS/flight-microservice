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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.isA;

import gatemate.entities.Ticket;
import gatemate.services.TicketsService;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest
class CheckinControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketsService ticketsService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setIataFlight("iataFlight");
    }

    @AfterEach
    void clearDatabase() {
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
    @DisplayName("GET /checkin/Alltickets should return all tickets")
    void getAllTicketsShouldReturnAllTickets() {
        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/alltickets")
                .then()
                .statusCode(200)
                .body("", hasSize(0));

        ticketsService.createTicket(1L, "iataFlight");

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
        ticketsService.createTicket(1L, "iataFlight");

        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/1")
                .then()
                .statusCode(200)
                .body("userId", is(1))
                .body("iataFlight", is("iataFlight"))
                // seat é do tipo string random
                .body("seat", isA(String.class));
    }

    @Test
    @DisplayName("GET /checkin/{ticketId} with invalid id should return 404 Not Found")
    void getTicketWithInvalidIdShouldreturnNotFound() {
        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/1")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("GET /checkin/user/{userId} with valid id should return tickets")
    void getTicketsWithValidIdShouldReturnTickets() {
        ticketsService.createTicket(1L, "iataFlight");

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
                .get("/checkin/user/1")
                .then()
                .statusCode(404);
    }
}
