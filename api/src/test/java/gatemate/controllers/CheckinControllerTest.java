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

import gatemate.entities.Ticket;
import gatemate.services.TicketsService;

@WebMvcTest(CheckinController.class)
class CheckinControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketsService ticketsService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setIataFlight("iataFlight");
        ticket.setSeat("A1");
    }

    @Test
    @DisplayName("POST /checkin/create with valid data should return 200 OK")
    void checkinWithValidDataShouldReturnOk() {
        when(ticketsService.createTicket(1L, "iataFlight")).thenReturn(ticket);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("userId", 1L)
                .param("iataFlight", "iataFlight")
                .when()
                .post("/checkin/create")
                .then()
                .statusCode(200);

        verify(ticketsService, times(1)).createTicket(1L, "iataFlight");
    }

    @Test
    @DisplayName("GET /checkin/alltickets with no data should return 404 Not Found")
    void getAllTicketsWithNoDataShouldReturnNotFound() {
        when(ticketsService.getALLTickets()).thenReturn(Arrays.asList());

        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/alltickets")
                .then()
                .statusCode(404);

        verify(ticketsService, times(1)).getALLTickets();
    }

    @Test
    @DisplayName("GET /checkin/alltickets with data should return 200 OK")
    void getAllTicketsWithDataShouldReturnOk() {
        when(ticketsService.getALLTickets()).thenReturn(Arrays.asList(ticket));

        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/alltickets")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", is(ticket.getId().intValue()));

        verify(ticketsService, times(1)).getALLTickets();
    }

    @Test
    @DisplayName("GET /checkin/{ticketId} with no data should return 404 Not Found")
    void getTicketWithNoDataShouldReturnNotFound() {
        when(ticketsService.getTicket(1L)).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/1")
                .then()
                .statusCode(404);

        verify(ticketsService, times(1)).getTicket(1L);
    }

    @Test
    @DisplayName("GET /checkin/{ticketId} with data should return 200 OK")
    void getTicketWithDataShouldReturnOk() {
        when(ticketsService.getTicket(1L)).thenReturn(ticket);

        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/1")
                .then()
                .statusCode(200)
                .body("id", is(ticket.getId().intValue()));

        verify(ticketsService, times(1)).getTicket(1L);
    }

    @Test
    @DisplayName("GET /checkin/user/{userId} with no data should return 404 Not Found")
    void getTicketsWithNoDataShouldReturnNotFound() {
        when(ticketsService.getTickets(1L)).thenReturn(Arrays.asList());

        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/user/1")
                .then()
                .statusCode(404);

        verify(ticketsService, times(1)).getTickets(1L);
    }

    @Test
    @DisplayName("GET /checkin/user/{userId} with data should return 200 OK")
    void getTicketsWithDataShouldReturnOk() {
        when(ticketsService.getTickets(1L)).thenReturn(Arrays.asList(ticket));

        RestAssuredMockMvc.given()
                .when()
                .get("/checkin/user/1")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", is(ticket.getId().intValue()));

        verify(ticketsService, times(1)).getTickets(1L);
    }

}
