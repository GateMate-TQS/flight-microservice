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
  @DisplayName("When save Seats then return Seats")
  void whenSaveSeats_thenFindItById() {
    Seats seats = new Seats();
    seats.setMaxCols(6);
    seats.setMaxRows(10);

    Seats savedSeats = seatsRepository.save(seats);

    Seats foundSeats = entityManager.find(Seats.class, savedSeats.getId());
    assertThat(foundSeats).isNotNull();
    assertThat(foundSeats.getId()).isEqualTo(savedSeats.getId());
    assertThat(foundSeats.getMaxCols()).isEqualTo(6);
    assertThat(foundSeats.getMaxRows()).isEqualTo(10);
  }

  @Test
  @DisplayName("When find SeatsById with valid id then return Seats")
  void whenDeleteSeats_thenCannotFindItById() {
    Seats seats = new Seats();
    seats.setMaxCols(4);
    seats.setMaxRows(20);
    entityManager.persistAndFlush(seats);

    seatsRepository.delete(seats);

    assertThat(seatsRepository.findById(seats.getId())).isEmpty();
  }

  @Test
  @DisplayName("When update Seats then return Seats with updated data")
  void whenUpdateSeats_thenSeatsIsUpdated() {
    Seats seats = new Seats();
    seats.setMaxCols(8);
    seats.setMaxRows(12);
    entityManager.persistAndFlush(seats);

    Seats foundSeats = entityManager.find(Seats.class, seats.getId());
    foundSeats.setMaxCols(9);
    foundSeats.setMaxRows(15);
    seatsRepository.save(foundSeats);

    Seats updatedSeats = entityManager.find(Seats.class, seats.getId());
    assertThat(updatedSeats.getMaxCols()).isEqualTo(9);
    assertThat(updatedSeats.getMaxRows()).isEqualTo(15);
  }

  @Test
  @DisplayName("When find SeatsByMaxCols with valid maxCols then return Seats")
  void whenFindAllSeats_thenReturnAllSeats() {
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
