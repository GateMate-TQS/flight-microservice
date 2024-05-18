package gatemate.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import gatemate.entities.Seats;

@DataJpaTest
class SeatsRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private SeatsRepository seatsRepository;

  @Test
  @DisplayName("Find seats by id")
  void whenFindSeatsByExistingId_thenReturnFlight() {
    Seats seats = new Seats();
    entityManager.persistAndFlush(seats);
    Seats seatsdb = seatsRepository.findById(seats.getId()).orElse(null);

    assertThat(seatsdb).isNotNull();
    assertThat(seatsdb.getId()).isEqualTo(seats.getId());
  }

  @Test
  @DisplayName("Find seats by invalid id")
  void whenInvalidId_thenReturnNull() {
    Seats seatsdb = seatsRepository.findById(-1L).orElse(null);

    assertThat(seatsdb).isNull();
  }

  @Test
  @DisplayName("Find all seats")
  void givenSetOfFlights_whenFindAll_thenReturnSet() {
    Seats seats1 = new Seats();
    seats1.setMaxCols(6);
    seats1.setMaxRows(10);
    Seats seats2 = new Seats();
    seats2.setMaxCols(4);
    seats2.setMaxRows(20);

    entityManager.persistAndFlush(seats1);
    entityManager.persistAndFlush(seats2);

    List<Seats> seatsList = seatsRepository.findAll();
    assertThat(seatsList)
        .hasSize(2)
        .extracting(Seats::getMaxCols)
        .contains(seats1.getMaxCols(), seats2.getMaxCols());
  }
}
