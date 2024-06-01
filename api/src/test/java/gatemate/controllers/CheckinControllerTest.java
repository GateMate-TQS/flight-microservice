package gatemate.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import gatemate.entities.Ticket;
import gatemate.services.TicketsService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.hamcrest.Matchers.*;

@WebMvcTest(CheckinController.class)
class CheckinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketsService ticketsService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setIataFlight("iataFlight");
        ticket.setSeat("A1");
    }

    @Test
    @DisplayName("POST /checkin/create with valid data should return 200 OK")
    void checkinWithValidDataShouldReturnOk() throws Exception {
        when(ticketsService.createTicket(1L, "iataFlight")).thenReturn(ticket);

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .formParam("userId", "1")
                .formParam("iataFlight", "iataFlight")
                .when()
                .post("/checkin/create")
                .then()
                .statusCode(200);

        verify(ticketsService, times(1)).createTicket(1L, "iataFlight");
    }

    @Test
    @DisplayName("POST /checkin/create with invalid data should return 400 Bad Request")
    void checkinWithInvalidDataShouldReturnBadRequest() throws Exception {
        when(ticketsService.createTicket(1L, "iataFlight")).thenReturn(null);

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .formParam("userId", "1")
                .formParam("iataFlight", "iataFlight")
                .when()
                .post("/checkin/create")
                .then()
                .statusCode(400)
                .body(equalTo("Unable to check in. Seats may be unavailable."));

        verify(ticketsService, times(1)).createTicket(1L, "iataFlight");
    }

    @Test
    @DisplayName("GET /checkin/tickets/{ticketId} with no data should return 404 Not Found")
    void getTicketWithNoDataShouldReturnNotFound() throws Exception {
        when(ticketsService.getTicket(1L)).thenReturn(null);

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .when()
                .get("/checkin/tickets/1")
                .then()
                .statusCode(404);

        verify(ticketsService, times(1)).getTicket(1L);
    }

    @Test
    @DisplayName("GET /checkin/tickets/{ticketId} with data should return 200 OK")
    void getTicketWithDataShouldReturnOk() throws Exception {
        when(ticketsService.getTicket(1L)).thenReturn(ticket);

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .when()
                .get("/checkin/tickets/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(ticket.getId().intValue()));

        verify(ticketsService, times(1)).getTicket(1L);
    }
}
