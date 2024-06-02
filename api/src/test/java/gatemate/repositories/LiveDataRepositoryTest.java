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
  @DisplayName("When save LiveData then return LiveData")
  void whenSaveLiveData_thenFindItById() {
    LiveData liveData = new LiveData();
    liveData.setAltitude(100);

    LiveData savedLiveData = liveDataRepository.save(liveData);

    LiveData foundLiveData = entityManager.find(LiveData.class, savedLiveData.getId());
    assertThat(foundLiveData).isNotNull();
    assertThat(foundLiveData.getId()).isEqualTo(savedLiveData.getId());
    assertThat(foundLiveData.getAltitude()).isEqualTo(100);
  }

  @Test
  @DisplayName("When find LiveDataById with valid id then return LiveData")
  void whenDeleteLiveData_thenCannotFindItById() {
    LiveData liveData = new LiveData();
    liveData.setAltitude(200);
    entityManager.persistAndFlush(liveData);

    liveDataRepository.delete(liveData);

    assertThat(liveDataRepository.findById(liveData.getId())).isEmpty();
  }

  @Test
  @DisplayName("When update LiveData then return LiveData with updated data")
  void whenUpdateLiveData_thenLiveDataIsUpdated() {
    LiveData liveData = new LiveData();
    liveData.setAltitude(300);
    entityManager.persistAndFlush(liveData);

    LiveData foundLiveData = entityManager.find(LiveData.class, liveData.getId());
    foundLiveData.setAltitude(400);
    liveDataRepository.save(foundLiveData);

    LiveData updatedLiveData = entityManager.find(LiveData.class, liveData.getId());
    assertThat(updatedLiveData.getAltitude()).isEqualTo(400);
  }

  @Test
  @DisplayName("When find LiveDataByAltitude with valid altitude then return LiveData")
  void whenFindAllLiveData_thenReturnAllLiveData() {
    LiveData liveData1 = new LiveData();
    liveData1.setAltitude(500);
    LiveData liveData2 = new LiveData();
    liveData2.setAltitude(600);

    entityManager.persistAndFlush(liveData1);
    entityManager.persistAndFlush(liveData2);

    List<LiveData> liveDataList = liveDataRepository.findAll();

    assertThat(liveDataList)
        .hasSize(2)
        .extracting(LiveData::getAltitude)
        .contains(liveData1.getAltitude(), liveData2.getAltitude());
  }
}
