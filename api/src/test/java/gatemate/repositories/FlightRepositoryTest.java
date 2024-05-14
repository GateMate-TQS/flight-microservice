package gatemate.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import gatemate.entities.Flight;

@DataJpaTest
class FlightRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private FlightRepository flightRepository;

  @Test
  @DisplayName("Test to find flight by id")
  void whenFindFlightByExistingId_thenReturnFlight() {
    Flight flight = new Flight();
    entityManager.persistAndFlush(flight);
    Flight flightdb = flightRepository.findById(flight.getId()).orElse(null);

    assertThat(flightdb).isNotNull();
    assertThat(flightdb.getId()).isEqualTo(flight.getId());
    assertThat(flightdb.getFlightNumber()).isEqualTo(flight.getFlightNumber());
  }

  @Test
  @DisplayName("Test to find flight by invalid id")
  void whenInvalidId_thenReturnNull() {
    Flight flightdb = flightRepository.findById(-1L).orElse(null);

    assertThat(flightdb).isNull();
  }

  @Test
  @DisplayName("Test to find all flights")
  void givenSetOfFlights_whenFindAll_thenReturnSet() {
    Flight flight1 = new Flight();
    flight1.setFlightNumber("FirstFlight");
    Flight flight2 = new Flight();
    flight2.setFlightNumber("SecondFlight");

    entityManager.persistAndFlush(flight1);
    entityManager.persistAndFlush(flight2);

    List<Flight> flightList = flightRepository.findAll();
    assertThat(flightList)
        .hasSize(2)
        .extracting(Flight::getFlightNumber)
        .contains(flight1.getFlightNumber(), flight2.getFlightNumber());
  }

  @Test
  @DisplayName("Test to find flight by flight Iata")
  void whenFindByFlightIata_thenReturnFlight() {
    Flight flight = new Flight();
    flight.setFlightIata("AA123");

    entityManager.persistAndFlush(flight);

    Flight flightdb = flightRepository.findByFlightIata(flight.getFlightIata());

    assertThat(flightdb).isNotNull();
    assertThat(flightdb.getFlightIata()).isEqualTo(flight.getFlightIata());
  }

  @Test
  @DisplayName("Test to find flight by invalid flight Iata")
  void whenFindByInvalidFlightIata_thenReturnNull() {
    Flight flight = new Flight();
    flight.setFlightIata("AA123");

    entityManager.persistAndFlush(flight);

    Flight flightdb = flightRepository.findByFlightIata("AA124");

    assertThat(flightdb).isNull();
  }
}
