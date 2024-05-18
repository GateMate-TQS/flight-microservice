package gatemate.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import gatemate.entities.Aircraft;

@DataJpaTest
class AircraftRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private AircraftRepository aircraftRepository;

  @Test
  @DisplayName("Find aircraft by id")
  void whenFindFlightByExistingId_thenReturnFlight() {
    Aircraft aircraft = new Aircraft();
    entityManager.persistAndFlush(aircraft);

    Aircraft aircraftdb = aircraftRepository.findById(aircraft.getId()).orElse(null);

    assertThat(aircraftdb).isNotNull();
    assertThat(aircraftdb.getId()).isEqualTo(aircraftdb.getId());
  }

  @Test
  @DisplayName("Find aircraft by invalid id")
  void whenInvalidId_thenReturnNull() {
    Aircraft aircraftdb = aircraftRepository.findById(-1L).orElse(null);

    assertThat(aircraftdb).isNull();
  }

  @Test
  @DisplayName("Find all aircrafts")
  void givenSetOfFlights_whenFindAll_thenReturnSet() {
    Aircraft aircraft1 = new Aircraft();
    aircraft1.setAircraftType("Boeing 747");
    Aircraft aircraft2 = new Aircraft();
    aircraft2.setAircraftType("Boeing 777");

    entityManager.persistAndFlush(aircraft1);
    entityManager.persistAndFlush(aircraft2);

    List<Aircraft> aircraftList = aircraftRepository.findAll();
    assertThat(aircraftList)
        .hasSize(2)
        .extracting(Aircraft::getAircraftType)
        .contains(aircraft1.getAircraftType(), aircraft2.getAircraftType());
  }
}
