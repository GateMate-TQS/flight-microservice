package gatemate.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

import gatemate.entities.Aircraft;

@DataJpaTest
class AircraftRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private AircraftRepository aircraftRepository;

  @Test
  @DisplayName("When save Aircraft then return Aircraft")
  void whenSaveAircraft_thenFindItById() {
    Aircraft aircraft = new Aircraft();
    aircraft.setAircraftType("Boeing 747");

    Aircraft savedAircraft = aircraftRepository.save(aircraft);

    Aircraft foundAircraft = entityManager.find(Aircraft.class, savedAircraft.getId());
    assertThat(foundAircraft).isNotNull();
    assertThat(foundAircraft.getId()).isEqualTo(savedAircraft.getId());
    assertEquals("Boeing 747", foundAircraft.getAircraftType());
  }

  @Test
  @DisplayName("When find AircraftById with valid id then return Aircraft")
  void whenDeleteAircraft_thenCannotFindItById() {
    Aircraft aircraft = new Aircraft();
    aircraft.setAircraftType("Boeing 777");
    entityManager.persistAndFlush(aircraft);

    aircraftRepository.delete(aircraft);

    assertThat(aircraftRepository.findById(aircraft.getId())).isEmpty();
  }

  @Test
  @DisplayName("When update Aircraft then return Aircraft with updated data")
  void whenUpdateAircraft_thenAircraftIsUpdated() {
    Aircraft aircraft = new Aircraft();
    aircraft.setAircraftType("Airbus A380");
    entityManager.persistAndFlush(aircraft);

    Aircraft foundAircraft = entityManager.find(Aircraft.class, aircraft.getId());
    foundAircraft.setAircraftType("Airbus A350");
    aircraftRepository.save(foundAircraft);

    Aircraft updatedAircraft = entityManager.find(Aircraft.class, aircraft.getId());
    assertThat(updatedAircraft.getAircraftType()).isEqualTo("Airbus A350");
  }
}
