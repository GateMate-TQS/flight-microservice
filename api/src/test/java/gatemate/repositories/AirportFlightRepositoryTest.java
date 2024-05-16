package gatemate.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import gatemate.entities.AirportFlight;

@DataJpaTest
class AirportFlightRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private AirportFlightRepository airportFlightRepository;

  @Test
  @DisplayName("Test to find airportFlight by id")
  void whenFindFlightByExistingId_thenReturnFlight() {
    AirportFlight airportFlight = new AirportFlight();
    entityManager.persistAndFlush(airportFlight);

    AirportFlight airportFlightdb = airportFlightRepository.findById(airportFlight.getId()).orElse(null);

    assertThat(airportFlightdb).isNotNull();
    assertThat(airportFlightdb.getId()).isEqualTo(airportFlightdb.getId());
  }

  @Test
  @DisplayName("Test to find airportFlight by invalid id")
  void whenInvalidId_thenReturnNull() {
    AirportFlight airportFlightdb = airportFlightRepository.findById(-1L).orElse(null);

    assertThat(airportFlightdb).isNull();
  }

  @Test
  @DisplayName("Test to find all airportFlights")
  void givenSetOfFlights_whenFindAll_thenReturnSet() {
    AirportFlight airportFlight1 = new AirportFlight();
    airportFlight1.setName("Lisbon");
    AirportFlight airportFlight2 = new AirportFlight();
    airportFlight2.setName("Madrid");

    entityManager.persistAndFlush(airportFlight1);
    entityManager.persistAndFlush(airportFlight2);

    List<AirportFlight> aircraftList = airportFlightRepository.findAll();
    assertThat(aircraftList)
        .hasSize(2)
        .extracting(AirportFlight::getName)
        .contains(airportFlight1.getName(), airportFlight2.getName());
  }
}
