package gatemate.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import gatemate.entities.AirportFlight;

@DataJpaTest
class AirportFlightRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private AirportFlightRepository airportFlightRepository;

  @Test
  @DisplayName("When save AirportFlight then return AirportFlight")
  void whenSaveAirportFlight_thenFindItById() {
    AirportFlight airportFlight = new AirportFlight();
    airportFlight.setName("Lisbon");

    AirportFlight savedAirportFlight = airportFlightRepository.save(airportFlight);

    AirportFlight foundAirportFlight = entityManager.find(AirportFlight.class, savedAirportFlight.getId());
    assertThat(foundAirportFlight).isNotNull();
    assertThat(foundAirportFlight.getId()).isEqualTo(savedAirportFlight.getId());
    assertThat(foundAirportFlight.getName()).isEqualTo("Lisbon");
  }

  @Test
  @DisplayName("When find AirportFlightById with valid id then return AirportFlight")
  void whenDeleteAirportFlight_thenCannotFindItById() {
    AirportFlight airportFlight = new AirportFlight();
    airportFlight.setName("Madrid");
    entityManager.persistAndFlush(airportFlight);

    airportFlightRepository.delete(airportFlight);

    assertThat(airportFlightRepository.findById(airportFlight.getId())).isEmpty();
  }

  @Test
  @DisplayName("When update AirportFlight then return AirportFlight with updated data")
  void whenUpdateAirportFlight_thenAirportFlightIsUpdated() {
    AirportFlight airportFlight = new AirportFlight();
    airportFlight.setName("Paris");
    entityManager.persistAndFlush(airportFlight);

    AirportFlight foundAirportFlight = entityManager.find(AirportFlight.class, airportFlight.getId());
    foundAirportFlight.setName("London");
    airportFlightRepository.save(foundAirportFlight);

    AirportFlight updatedAirportFlight = entityManager.find(AirportFlight.class, airportFlight.getId());
    assertThat(updatedAirportFlight.getName()).isEqualTo("London");
  }
}
