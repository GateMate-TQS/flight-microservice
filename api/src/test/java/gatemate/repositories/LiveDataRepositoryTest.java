package gatemate.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import gatemate.entities.LiveData;

@DataJpaTest
class LiveDataRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private LiveDataRepository liveDataRepository;

  @Test
  @DisplayName("Find live data by id")
  void whenFindSeatsByExistingId_thenReturnFlight() {
    LiveData liveData = new LiveData();
    entityManager.persistAndFlush(liveData);
    LiveData liveDatadb = liveDataRepository.findById(liveData.getId()).orElse(null);

    assertThat(liveDatadb).isNotNull();
    assertThat(liveDatadb.getId()).isEqualTo(liveData.getId());
  }

  @Test
  @DisplayName("Find live data by invalid id")
  void whenInvalidId_thenReturnNull() {
    LiveData liveDatadb = liveDataRepository.findById(-1L).orElse(null);

    assertThat(liveDatadb).isNull();
  }

  @Test
  @DisplayName("Find all live data")
  void givenSetOfFlights_whenFindAll_thenReturnSet() {
    LiveData liveData1 = new LiveData();
    liveData1.setAltitude(100);
    LiveData liveData2 = new LiveData();
    liveData2.setAltitude(200);

    entityManager.persistAndFlush(liveData1);
    entityManager.persistAndFlush(liveData2);

    List<LiveData> seatsList = liveDataRepository.findAll();
    assertThat(seatsList)
        .hasSize(2)
        .extracting(LiveData::getAltitude)
        .contains(liveData1.getAltitude(), liveData2.getAltitude());
  }
}
