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
  @DisplayName("When save Flight then return Flight")
  void whenSaveFlight_thenFindItById() {
    Flight flight = new Flight();
    flight.setFlightNumber("FirstFlight");

    Flight savedFlight = flightRepository.save(flight);

    Flight foundFlight = entityManager.find(Flight.class, savedFlight.getId());
    assertThat(foundFlight).isNotNull();
    assertThat(foundFlight.getId()).isEqualTo(savedFlight.getId());
    assertThat(foundFlight.getFlightNumber()).isEqualTo("FirstFlight");
  }

  @Test
  @DisplayName("When find FlightById with valid id then return Flight")
  void whenDeleteFlight_thenCannotFindItById() {
    Flight flight = new Flight();
    flight.setFlightNumber("SecondFlight");
    entityManager.persistAndFlush(flight);

    flightRepository.delete(flight);

    assertThat(flightRepository.findById(flight.getId())).isEmpty();
  }

  @Test
  @DisplayName("When update Flight then return Flight with updated data")
  void whenUpdateFlight_thenFlightIsUpdated() {
    Flight flight = new Flight();
    flight.setFlightNumber("ThirdFlight");
    entityManager.persistAndFlush(flight);

    Flight foundFlight = entityManager.find(Flight.class, flight.getId());
    foundFlight.setFlightNumber("UpdatedFlight");
    flightRepository.save(foundFlight);

    Flight updatedFlight = entityManager.find(Flight.class, flight.getId());
    assertThat(updatedFlight.getFlightNumber()).isEqualTo("UpdatedFlight");
  }

  @Test
  @DisplayName("When find FlightByFlightIata with valid flightIata then return Flight")
  void whenFindByFlightIata_thenReturnFlight() {
    Flight flight = new Flight();
    flight.setFlightIata("AA123");

    entityManager.persistAndFlush(flight);

    Flight foundFlight = flightRepository.findByFlightIata(flight.getFlightIata());

    assertThat(foundFlight).isNotNull();
    assertThat(foundFlight.getFlightIata()).isEqualTo("AA123");
  }

  @Test
  @DisplayName("When find FlightByInvalidFlightIata with invalid flightIata then return null")
  void whenFindByInvalidFlightIata_thenReturnNull() {
    Flight flight = new Flight();
    flight.setFlightIata("AA123");

    entityManager.persistAndFlush(flight);

    Flight foundFlight = flightRepository.findByFlightIata("AA124");

    assertThat(foundFlight).isNull();
  }

  @Test
  @DisplayName("When find FlightByUpdatedLessThan with valid time then return Flights")
  void whenFindByUpdatedLessThan_thenReturnFlights() {
    Flight flight1 = new Flight();
    flight1.setUpdated(100L);
    Flight flight2 = new Flight();
    flight2.setUpdated(200L);

    entityManager.persistAndFlush(flight1);
    entityManager.persistAndFlush(flight2);

    List<Flight> flightList = flightRepository.findByUpdatedLessThan(150L);

    assertThat(flightList)
        .hasSize(1)
        .extracting(Flight::getUpdated)
        .contains(flight1.getUpdated());
  }

  @Test
  @DisplayName("When find FlightByInvalidUpdatedLessThan with invalid time then return empty list")
  void whenFindByInvalidUpdatedLessThan_thenReturnEmptyList() {
    Flight flight1 = new Flight();
    flight1.setUpdated(100L);
    Flight flight2 = new Flight();
    flight2.setUpdated(200L);

    entityManager.persistAndFlush(flight1);
    entityManager.persistAndFlush(flight2);

    List<Flight> flightList = flightRepository.findByUpdatedLessThan(50L);

    assertThat(flightList).isEmpty();
  }

  @Test
  @DisplayName("When find FlightByStatus with valid status then return Flights")
  void whenFindByStatus_thenReturnFlights() {
    Flight flight1 = new Flight();
    flight1.setStatus("scheduled");
    Flight flight2 = new Flight();
    flight2.setStatus("active");

    entityManager.persistAndFlush(flight1);
    entityManager.persistAndFlush(flight2);

    List<Flight> flightList = flightRepository.findByStatus("scheduled");

    assertThat(flightList)
        .hasSize(1)
        .extracting(Flight::getStatus)
        .contains(flight1.getStatus());
  }

  @Test
  @DisplayName("When find FlightByInvalidStatus with invalid status then return empty list")
  void whenFindByInvalidStatus_thenReturnEmptyList() {
    Flight flight1 = new Flight();
    flight1.setStatus("scheduled");
    Flight flight2 = new Flight();
    flight2.setStatus("active");

    entityManager.persistAndFlush(flight1);
    entityManager.persistAndFlush(flight2);

    List<Flight> flightList = flightRepository.findByStatus("cancelled");

    assertThat(flightList).isEmpty();
  }
}
